package de.barkeeper.repository;

import de.barkeeper.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findByEmail(String mail);

    User findByEmailOrUsername(String mail, String username);
}
