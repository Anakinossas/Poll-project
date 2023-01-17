package it.zerob.poll.repository;

import it.zerob.poll.model.Polls;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PollsRepository extends CrudRepository<Polls, Long> {

    Polls findByIdPoll(Long idPoll);

    //Query that counts number of the users that didn't answer to the poll
    @Query(value = "SELECT COUNT(*) FROM USERS u\n" +
            "WHERE ROLE = 'USER' AND NOT EXISTS " +
            "(select * from requests r where r.id_user_fk = u.id_user AND r.id_poll_fk = :idPoll)", nativeQuery = true)
    Integer usersMissingByIdPoll(@Param("idPoll") Long idPoll);
}
