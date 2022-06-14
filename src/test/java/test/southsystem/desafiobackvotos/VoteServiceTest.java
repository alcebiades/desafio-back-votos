package test.southsystem.desafiobackvotos;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.southsystem.desafiobackvotos.model.dto.AgendaDTO;
import test.southsystem.desafiobackvotos.model.dto.UserDTO;
import test.southsystem.desafiobackvotos.model.dto.VoteDTO;
import test.southsystem.desafiobackvotos.model.exception.AgendaExpiredException;
import test.southsystem.desafiobackvotos.model.exception.UserAlreadyVotedException;
import test.southsystem.desafiobackvotos.service.AgendaService;
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

    @Test
    void shouldCreateAnAgendaWithGivenData() {

        final int totalOfUsers = 11;

        final AgendaDTO agenda = createAnAgenda();

        final List<UserDTO> users = createAListOfUsers(totalOfUsers);

        int totalOfTrue = 0;

        for (UserDTO user : users) {

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
                                    .build()
                    )
            );
        }

        return users;
    }
}
