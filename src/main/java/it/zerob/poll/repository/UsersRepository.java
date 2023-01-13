package it.zerob.poll.repository;

import it.zerob.poll.model.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UsersRepository extends CrudRepository<Users, Long> {

    Users findByUsername(String username);

    List<Users> getAllByPasswordIsNull();
}
