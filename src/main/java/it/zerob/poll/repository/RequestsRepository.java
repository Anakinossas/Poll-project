package it.zerob.poll.repository;

import it.zerob.poll.model.Polls;
import it.zerob.poll.model.Requests;
import it.zerob.poll.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * <strong>Repository</strong> that implements one method with a non-native query
 */
public interface RequestsRepository extends CrudRepository<Requests, Long> {

    /**
     * Method with query that returns one request by giving poll id and user id
     * @param idPoll poll id
     * @param idUser user id
     * @return Requests object
     */

    //Query that find the request by giving poll and user, that allows to know if the user already
    //Completed the poll
    @Query("select r from Requests r WHERE r.idPollFk = :idPoll and r.idUserFk = :idUser ")
    Requests requestsDone(@Param("idPoll") Polls idPoll, @Param("idUser") Users idUser);
}
