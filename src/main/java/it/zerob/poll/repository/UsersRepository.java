package it.zerob.poll.repository;

import it.zerob.poll.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

/**
 * <strong>Repository</strong> that contains method that returns one or more users
 */
public interface UsersRepository extends CrudRepository<Users, Long> {

    Users findByUsername(String username);

    List<Users> getAllByPasswordIsNull();

    /**
     * Method with a native query that search the users that didn't complete one specific poll
     * @return List of Users
     */
    @Query(value = "SELECT * FROM USERS u WHERE ROLE = 'USER' AND PASSWORD IS NOT NULL AND NOT EXISTS (select * from requests r where r.id_user_fk = u.id_user)", nativeQuery = true)
    List<Users> getUsersWithNoSubmit();

}
