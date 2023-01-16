package it.zerob.poll.repository;

import it.zerob.poll.model.Polls;
import org.springframework.data.repository.CrudRepository;

public interface PollsRepository extends CrudRepository<Polls, Long> {

    Polls findByIdPoll(Long idPoll);
}
