package it.zerob.poll.repository;

import it.zerob.poll.model.Users;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<Users, Long> {

    Users findByEmail(String email);
}
