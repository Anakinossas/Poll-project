package it.zerob.poll.repository;

import it.zerob.poll.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UsersRepository extends CrudRepository<Users, Long> {

    Users findByUsername(String username);

    List<Users> getAllByPasswordIsNull();

    @Query(value = "SELECT * FROM USERS u WHERE ROLE = 'USER' AND NOT EXISTS (select * from requests r where r.id_user_fk = u.id_user)", nativeQuery = true)
    List<Users> getUsersWithNoSubmit();

}
