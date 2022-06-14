package test.southsystem.desafiobackvotos.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.southsystem.desafiobackvotos.repository.entity.Agenda;
import test.southsystem.desafiobackvotos.repository.entity.User;
import test.southsystem.desafiobackvotos.repository.entity.Vote;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VoteDTO {

    @NotNull(message = "O campo agendaId e obrigatorio")
    private Long agendaId;
    @NotNull(message = "O campo userId e obrigatorio")
    private Long userId;
    @NotNull(message = "O campo vote e obrigatorio")
    private Boolean vote;

    public Vote toEntity() {
        return Vote.builder()
                .agenda(Agenda.builder().id(agendaId).build())
                .user(User.builder().id(userId).build())
                .vote(vote)
                .build();
    }
}
