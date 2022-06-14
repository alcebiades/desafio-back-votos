package test.southsystem.desafiobackvotos.service.jms;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import test.southsystem.desafiobackvotos.config.ActiveMQConfig;
import test.southsystem.desafiobackvotos.model.jms.AgendaJms;

@Profile("!test")
@Log4j2
@Service
public class ActiveMQSender {

    @Autowired
    private JmsTemplate jmsTemplate;

    /**
     * Aqui a mensagem foi postada em uma QUEUE apenas por facilidade, porém para que varias aplicacoes consumam a mesma
     * mensagem a pensagem teria que ser postada em um TOPIC.
     *
     * A classe ActiveMQListener sera responsavel por consumir a mensagem
     *
     * @param agendaJms
     */
    public void send(AgendaJms agendaJms) {
        log.info("I=Enviando mensagem para a fila: " + ActiveMQConfig.QUEUE);
        jmsTemplate.convertAndSend(ActiveMQConfig.QUEUE, agendaJms);
    }
}
