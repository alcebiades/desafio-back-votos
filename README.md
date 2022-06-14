# Desafio back-votos SouthSystem:

A projeto foi desenvolvido atendendo os requisitos propostos no teste [link](https://github.com/rh-southsystem/desafio-back-votos).

## Ferramentas utilizada para o desenvolvimento:

* Java 11;
* spring-boot;
* Banco de dados H2 (embedded) salvando em arquivo (o arquivo do banco fica salvo na pasta "database" dentro da pasta raiz do projeto, para renovar o banco basta excluir esta pasta e rodar a aplicacao novamente);
* ActiveMQ embedded;
* A integracao com o servico externo para consulta de CPF foi feito usando o OpenFeign;

## Exectando a aplicacao:
* Para executar a aplicacao basta rodar comando "./gradlew bootRun" (lunix) ou ".\gradlew bootRun" (windows) no terminal.


### Consideracoes:

* Para a validacao da finalizacao da pauta foi criado um scheduler atraves da anotacao "@Scheduled(fixedDelay = 60000)" que fica a cada um minuto validando de a pauta fechou, caso ele tenha fechado, uma mensagem e enviada para a fila sinalizando o fechamento da pauta;
Obs: Apenas por simplicidade, escolhi manter queue e criei um listener para consumir a mensagem validando que a mensagem foi postada corretamente na fila, sabendo que, em caso de aplicacao maiores onde a a necessidade de informar mais de uma aplicacao o correto seria criar um topico no caso do ActiveMQ ou utilizar uma RoutingKey devidamente configurada no RabbitMQ ou mesmo usar o AoacheKafka que por default usa sistemas de topico.

### Requests para teste:

* As requests para teste estao no arquivo "requests.json" na pasta raiz do projeto, basta apenas importa-lo no postman e usar.


### Resposta ao questionamento:

Tarefa Bônus 4 - Versionamento da API

* Como você versionaria a API da sua aplicação? Que estratégia usar?

R: Isso depende muito do combinado entre os times do contexto, normalmente eu sigo de duas nameira, uma seria versionar na propria api tipo http://url/v3/recurso e a outra seria passar um header informando qual versao o cliente deseja usar:
A vantagem da primeira abordagem e que para o cliente fica explicito qual versao do recurso ele esta usando, porem a segunda abordagem entendo que fique mais elegante pois a URL fica mais limpa, porem essa abordagem precisa de uma documentacao bem definida para que o cliente saiba exatamente qual header passar para acessar a versao desejada.
