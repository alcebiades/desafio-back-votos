package test.southsystem.desafiobackvotos.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import test.southsystem.desafiobackvotos.model.dto.AgendaDTO;
import test.southsystem.desafiobackvotos.model.exception.NotFoundException;
import test.southsystem.desafiobackvotos.repository.AgendaRepository;
import test.southsystem.desafiobackvotos.repository.entity.Agenda;

@Log4j2
@Service
public class AgendaService {

    private final AgendaRepository agendaRepository;

    public AgendaService(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public AgendaDTO createAgenda(final AgendaDTO agendaDTO) {

        setupLimitTimeIfNotSupplied(agendaDTO);

        agendaDTO.setActive(Boolean.TRUE);

        final Agenda agenda = agendaRepository.save(agendaDTO.toEntity());

        return agenda.toDTO();
    }

    private void setupLimitTimeIfNotSupplied(AgendaDTO agendaDTO) {

        if (agendaDTO.getVoteLimitTime() == null) {

            final LocalDateTime defaultExpirationTime = LocalDateTime.now().plusMinutes(1L);

            agendaDTO.setVoteLimitTime(defaultExpirationTime);

            log.info("I=O tempo de expiracao para a votacao nao foi informado, por este motivo estaremos configurando" +
                    " o tempo default para mais um minuto, defaultExpirationTime={}", defaultExpirationTime);
        }
    }

    public List<AgendaDTO> getAllAgenda() {

        final List<Agenda> allAgenda = agendaRepository.findAll();

        return allAgenda.stream().map(Agenda::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public List<AgendaDTO> getAllActive() {

        final List<Agenda> allAgenda = agendaRepository.findAllActive();

        return allAgenda.stream()
                .map(Agenda::toDTO)
                .collect(Collectors.toList());
    }

    public AgendaDTO getById(final Long id) {

        final Optional<Agenda> agenda = agendaRepository.findById(id);

        return agenda
                .map(Agenda::toDTO)
                .orElseThrow(() -> new NotFoundException("Pauta nao encontrada para o id: " + id));
    }

    public void setAgendaAsInactive(AgendaDTO agenda) {
        agenda.setActive(Boolean.FALSE);
        agendaRepository.save(agenda.toEntity());
    }
}
