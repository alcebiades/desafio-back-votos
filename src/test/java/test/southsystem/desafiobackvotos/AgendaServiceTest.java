package test.southsystem.desafiobackvotos;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.southsystem.desafiobackvotos.model.dto.AgendaDTO;
import test.southsystem.desafiobackvotos.service.AgendaService;

@SpringBootTest
class AgendaServiceTest {

    @Autowired
    private AgendaService agendaService;

    @Test
    void shouldCreateAnAgendaWithGivenData() {

        final LocalDateTime limitTime = LocalDateTime.now().plusHours(2);

        final AgendaDTO agenda = agendaService.createAgenda(
                AgendaDTO.builder()
                        .description("Pauta de teste")
                        .voteLimitTime(limitTime)
                        .build()
        );

        Assertions.assertEquals("Pauta de teste", agenda.getDescription());
        Assertions.assertEquals(limitTime, agenda.getVoteLimitTime());
    }

    @Test
    void shouldDefineDefaultTimeToAgenda() {

        final LocalDateTime timeStartTest = LocalDateTime.now();

        final AgendaDTO agenda = agendaService.createAgenda(
                AgendaDTO.builder()
                        .description("Pauta de teste")
                        .build()
        );

        Assertions.assertTrue(timeStartTest.isBefore(agenda.getVoteLimitTime()));
    }
}
