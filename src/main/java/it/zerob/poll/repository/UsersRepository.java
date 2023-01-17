package it.zerob.poll.repository;

import it.zerob.poll.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface UsersRepository extends CrudRepository<Users, Long> {

    //Get the data of the user
    //from his username
    Users findByUsername(String username);

    //Get a list of users without
    //password saved in db
    List<Users> getAllByPasswordIsNull();

    //Get a list of users without
    //a survey already done
    @Query(value = "SELECT * FROM USERS u WHERE ROLE = 'USER' AND NOT EXISTS (select * from requests r where r.id_user_fk = u.id_user)", nativeQuery = true)
    List<Users> getUsersWithNoSubmit();

}
