package test.southsystem.desafiobackvotos.service.schedule;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import test.southsystem.desafiobackvotos.model.dto.AgendaDTO;
import test.southsystem.desafiobackvotos.model.jms.AgendaJms;
import test.southsystem.desafiobackvotos.service.AgendaService;
import test.southsystem.desafiobackvotos.service.jms.ActiveMQSender;

@Profile("!test")
@Log4j2
@Component
public class AgendaScheduler {

    private final AgendaService agendaService;
    private final ActiveMQSender activeMQSender;

    public AgendaScheduler(AgendaService agendaService, ActiveMQSender activeMQSender) {
        this.agendaService = agendaService;
        this.activeMQSender = activeMQSender;
    }

    /**
     * A cada 1 minuto valida se a votacao da pauta finalizou, caso tenha finalizado posta uma mensagem na fila
     */
    @Scheduled(fixedDelay = 60000)
    public void checkAgendaFinish() {

        for (AgendaDTO agenda : agendaService.getAllActive()) {
            if (agenda.isAgendaExpired()) {

                final AgendaJms agendaJms = AgendaJms.builder()
                        .id(agenda.getId())
                        .description(agenda.getDescription())
                        .quantityAgree(agenda.getVoteResume().getQuantityAgree())
                        .quantityDisagree(agenda.getVoteResume().getQuantityDisagree())
                        .build();

                activeMQSender.send(agendaJms);
                agendaService.setAgendaAsInactive(agenda);
            }
        }
    }
}
