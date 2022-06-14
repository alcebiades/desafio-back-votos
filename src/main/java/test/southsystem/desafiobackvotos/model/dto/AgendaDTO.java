package test.southsystem.desafiobackvotos.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import java.time.LocalDateTime;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.southsystem.desafiobackvotos.repository.entity.Agenda;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AgendaDTO {

    private Long id;
    @NotBlank(message = "O campo description e obrigatorio")
    private String description;
    @JsonFormat(
            shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", locale = "pt_br"
    )
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime voteLimitTime;
    private List<VoteDTO> votes;

    private Boolean active;

    public VoteResume getVoteResume() {
        return VoteResume.builder()
                .quantityAgree(votes.stream().filter(VoteDTO::getVote).count())
                .quantityDisagree(votes.stream().filter(i -> !i.getVote()).count())
                .build();
    }

    public boolean isAgendaExpired() {
        return LocalDateTime.now().isAfter(getVoteLimitTime());
    }

    @Data
    @Builder
    public static class VoteResume {
        private Long quantityAgree;
        private Long quantityDisagree;
    }

    public Agenda toEntity() {
        return Agenda.builder()
                .id(id)
                .description(description)
                .voteLimitTime(voteLimitTime)
                .active(active)
                .build();
    }

}
