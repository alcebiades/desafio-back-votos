package test.southsystem.desafiobackvotos.repository.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.southsystem.desafiobackvotos.model.dto.AgendaDTO;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;
import test.southsystem.desafiobackvotos.model.dto.VoteDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_agenda")
public class Agenda {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column
    private String description;

    @Column
    private LocalDateTime voteLimitTime;

    @OneToMany(mappedBy = "agenda", fetch = FetchType.EAGER)
    private List<Vote> votes;

    @Column
    private Boolean active;

    public AgendaDTO toDTO() {
        return AgendaDTO.builder()
                .id(id)
                .description(description)
                .voteLimitTime(voteLimitTime)
                .votes(getVotes(votes))
                .active(active)
                .build();
    }

    private List<VoteDTO> getVotes(final List<Vote> votes) {

        if (votes == null)
            return new ArrayList<>();

        return votes.stream()
                .map(Vote::toDTO)
                .collect(Collectors.toList());
    }
}
