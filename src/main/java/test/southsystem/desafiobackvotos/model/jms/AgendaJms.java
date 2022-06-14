package test.southsystem.desafiobackvotos.model.jms;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgendaJms {

    private Long id;
    private String description;
    private Long quantityAgree;
    private Long quantityDisagree;
}
