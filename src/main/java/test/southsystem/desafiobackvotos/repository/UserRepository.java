package test.southsystem.desafiobackvotos.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.southsystem.desafiobackvotos.repository.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
