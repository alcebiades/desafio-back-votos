package test.southsystem.desafiobackvotos.service.jms;

import javax.jms.Message;
import javax.jms.Session;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import test.southsystem.desafiobackvotos.config.ActiveMQConfig;
import test.southsystem.desafiobackvotos.model.jms.AgendaJms;

@Profile("!test")
@Log4j2
@Service
public class ActiveMQListener {

    @JmsListener(destination = ActiveMQConfig.QUEUE)
    public void receiveMessage(@Payload AgendaJms agendaJms,
                               @Headers MessageHeaders headers,
                               Message message, Session session) {

        log.info("\n\n\n: " + agendaJms);
        log.info("\n\n++++++++++++++ DETALHES ++++++++++++++");
        log.info("\nheaders: " + headers);
        log.info("\nmessage: " + message);
        log.info("\nsession: " + session);
        log.info("\n\n\n");
    }
}
