package test.southsystem.desafiobackvotos.service;

import java.util.Optional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import test.southsystem.desafiobackvotos.model.emuns.VoteStatus;
import test.southsystem.desafiobackvotos.model.dto.AgendaDTO;
import test.southsystem.desafiobackvotos.model.dto.CpfDTO;
import test.southsystem.desafiobackvotos.model.dto.UserDTO;
import test.southsystem.desafiobackvotos.model.dto.VoteDTO;
import test.southsystem.desafiobackvotos.model.exception.AgendaExpiredException;
import test.southsystem.desafiobackvotos.model.exception.UserAlreadyVotedException;
import test.southsystem.desafiobackvotos.model.exception.UserNotAllowedToVotedException;
import test.southsystem.desafiobackvotos.repository.VoteRepository;

@Log4j2
@Service
public class VoteService {

    private final VoteRepository voteRepository;
    private final AgendaService agendaService;
    private final UserService userService;
    private final CpfService cpfService;

    public VoteService(VoteRepository voteRepository, AgendaService agendaService, UserService userService, CpfService cpfService) {
        this.voteRepository = voteRepository;
        this.agendaService = agendaService;
        this.userService = userService;
        this.cpfService = cpfService;
    }

    public void vote(final VoteDTO voteDTO) {

        final AgendaDTO agendaDTO = agendaService.getById(voteDTO.getAgendaId());

        validateLimitTime(agendaDTO);

        validateUserVote(voteDTO, agendaDTO);

        voteRepository.save(voteDTO.toEntity());
    }

    private void validateLimitTime(final AgendaDTO agendaDTO) {
        if (agendaDTO.isAgendaExpired()) {
            throw new AgendaExpiredException("A pauta ".concat(agendaDTO.getDescription()).concat(" ja foi finalizada!"));
        }
    }

    private void validateUserVote(final VoteDTO voteDTO, final AgendaDTO agendaDTO) {

        final UserDTO userDTO = userService.getUserById(voteDTO.getUserId());

        final CpfDTO cpf = cpfService.getCpf(userDTO.getCpf());

        if (VoteStatus.UNABLE_TO_VOTE.name().equals(cpf.getStatus())) {
            throw new UserNotAllowedToVotedException();
        }

        final Optional<VoteDTO> optVoted = agendaDTO.getVotes().stream()
                .filter(i -> i.getUserId().equals(userDTO.getId()))
                .findFirst();

        if (optVoted.isPresent()) {
            throw new UserAlreadyVotedException("Seu voto ja foi computado anteriormente para a pauta: "
                    + agendaDTO.getDescription());
        }
    }
}
