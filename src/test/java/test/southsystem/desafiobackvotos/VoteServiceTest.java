package test.southsystem.desafiobackvotos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import test.southsystem.desafiobackvotos.model.dto.AgendaDTO;
import test.southsystem.desafiobackvotos.model.dto.CpfDTO;
import test.southsystem.desafiobackvotos.model.dto.UserDTO;
import test.southsystem.desafiobackvotos.model.dto.VoteDTO;
import test.southsystem.desafiobackvotos.model.emuns.VoteStatus;
import test.southsystem.desafiobackvotos.model.exception.AgendaExpiredException;
import test.southsystem.desafiobackvotos.model.exception.UserAlreadyVotedException;
import test.southsystem.desafiobackvotos.model.exception.UserNotAllowedToVotedException;
import test.southsystem.desafiobackvotos.service.AgendaService;
import test.southsystem.desafiobackvotos.service.CpfService;
import test.southsystem.desafiobackvotos.service.UserService;
import test.southsystem.desafiobackvotos.service.VoteService;

@SpringBootTest
class VoteServiceTest {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private UserService userService;

    @Autowired
    private VoteService voteService;

    @MockBean
    private CpfService cpfService;

    @Test
    void shouldCreateAnAgendaWithGivenData() {

        final int totalOfUsers = 11;

        final AgendaDTO agenda = createAnAgenda();

        final List<UserDTO> users = createAListOfUsers(totalOfUsers);

        int totalOfTrue = 0;

        for (UserDTO user : users) {

            final CpfDTO cpfDTO = new CpfDTO();
            cpfDTO.setStatus(VoteStatus.ABLE_TO_VOTE.name());

            Mockito.when(
                    cpfService.getCpf(user.getCpf())
            ).thenReturn(
                    cpfDTO
            );

            final boolean vote = user.getId() % 2 > 0;

            if (vote) {
                totalOfTrue++;
            }

            voteService.vote(
                    VoteDTO.builder()
                            .agendaId(agenda.getId())
                            .userId(user.getId())
                            .vote(vote)
                            .build()
            );
        }

        final AgendaDTO.VoteResume voteResume = agendaService.getById(agenda.getId()).getVoteResume();

        Assertions.assertEquals(totalOfTrue, voteResume.getQuantityAgree());
        Assertions.assertEquals(totalOfUsers - totalOfTrue, voteResume.getQuantityDisagree());
    }

    @Test
    void shouldThrowsAnUserAlreadyVotedExceptionWhenUserAlreadyVoted() {

        final int totalOfUsers = 1;

        final AgendaDTO agenda = createAnAgenda();

        final UserDTO user = createAListOfUsers(totalOfUsers).get(0);

        final CpfDTO cpfDTO = new CpfDTO();
        cpfDTO.setStatus(VoteStatus.ABLE_TO_VOTE.name());

        Mockito.when(
                cpfService.getCpf(user.getCpf())
        ).thenReturn(
                cpfDTO
        );

        voteService.vote(
                VoteDTO.builder()
                        .agendaId(agenda.getId())
                        .userId(user.getId())
                        .vote(Boolean.FALSE)
                        .build()
        );

        final UserAlreadyVotedException userAlreadyVotedException = Assertions.assertThrows(
                UserAlreadyVotedException.class,
                () -> voteService.vote(
                        VoteDTO.builder()
                                .agendaId(agenda.getId())
                                .userId(user.getId())
                                .vote(Boolean.TRUE)
                                .build()
                )
        );

        Assertions.assertEquals("Seu voto ja foi computado anteriormente para a pauta: "
                + agenda.getDescription(), userAlreadyVotedException.getMessage());
    }

    @Test
    void shouldThrowsAnAgendaExpiredExceptionWhenAgendaTimeIsExpired() {

        final int totalOfUsers = 1;

        final AgendaDTO agenda = agendaService.createAgenda(
                AgendaDTO.builder()
                        .description("Pauta de teste")
                        .voteLimitTime(LocalDateTime.now().minusMinutes(1))
                        .build()
        );

        final UserDTO user = createAListOfUsers(totalOfUsers).get(0);

        final AgendaExpiredException agendaExpiredException = Assertions.assertThrows(
                AgendaExpiredException.class,
                () -> voteService.vote(
                        VoteDTO.builder()
                                .agendaId(agenda.getId())
                                .userId(user.getId())
                                .vote(Boolean.TRUE)
                                .build()
                )
        );

        Assertions.assertEquals("A pauta ".concat(agenda.getDescription()).concat(" ja foi finalizada!"), agendaExpiredException.getMessage());
    }

    @Test
    void shouldThrowsAnUserNotAllowedToVotedExceptionWhenUserCpfIsNotAllowedToVoted() {

        final int totalOfUsers = 1;

        final AgendaDTO agenda = createAnAgenda();

        final UserDTO user = createAListOfUsers(totalOfUsers).get(0);

        final CpfDTO cpfDTO = new CpfDTO();
        cpfDTO.setStatus(VoteStatus.UNABLE_TO_VOTE.name());

        Mockito.when(
                cpfService.getCpf(user.getCpf())
        ).thenReturn(
                cpfDTO
        );

        final UserNotAllowedToVotedException userNotAllowedToVotedException = Assertions.assertThrows(
                UserNotAllowedToVotedException.class,
                () -> voteService.vote(
                        VoteDTO.builder()
                                .agendaId(agenda.getId())
                                .userId(user.getId())
                                .vote(Boolean.FALSE)
                                .build()
                )
        );

        Assertions.assertEquals("O usuario nao esta habilitado para votar",
                userNotAllowedToVotedException.getMessage());
    }

    private AgendaDTO createAnAgenda() {

        return agendaService.createAgenda(
                AgendaDTO.builder()
                        .description("Pauta de teste")
                        .build()
        );
    }

    private List<UserDTO> createAListOfUsers(int qty) {

        final List<UserDTO> users = new ArrayList<>();

        for (int i = 0; i < qty; i++) {
            users.add(
                    userService.createUser(
                            UserDTO.builder()
                                    .name("Usuario: " + i)
                                    .cpf("724.827.370-87")
                                    .build()
                    )
            );
        }

        return users;
    }
}
