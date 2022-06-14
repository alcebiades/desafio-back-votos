package test.southsystem.desafiobackvotos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import test.southsystem.desafiobackvotos.repository.entity.Agenda;

@Repository
public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    @Query("SELECT a FROM Agenda a " +
            " JOIN a.votes"+
            " WHERE a.active = true")
    List<Agenda> findAllActive();
}
