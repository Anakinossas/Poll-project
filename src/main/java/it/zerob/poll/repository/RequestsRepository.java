package it.zerob.poll.repository;

import it.zerob.poll.model.Polls;
import it.zerob.poll.model.Requests;
import it.zerob.poll.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RequestsRepository extends CrudRepository<Requests, Long> {

    @Query("select r from Requests r WHERE r.idPollFk = :idPoll and r.idUserFk = :idUser ")
    Requests requestsDone(@Param("idPoll") Polls idPoll, @Param("idUser") Users idUser );
}
