package es.jrdarcos.japanesesrs.repository;

import es.jrdarcos.japanesesrs.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
