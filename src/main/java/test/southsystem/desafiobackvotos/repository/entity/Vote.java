package test.southsystem.desafiobackvotos.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import test.southsystem.desafiobackvotos.model.dto.VoteDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_vote")
public class Vote {

    @Id
    @SequenceGenerator(
            name = "vote_seq",
            sequenceName = "vote_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "vote_seq"
    )
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_agenda")
    private Agenda agenda;

    @ManyToOne
    @JoinColumn(nullable = false, name = "id_user")
    private User user;

    @Column
    private Boolean vote;

    public VoteDTO toDTO() {
        return VoteDTO.builder()
                .agendaId(agenda.getId())
                .userId(user.getId())
                .vote(vote)
                .build();
    }
}
