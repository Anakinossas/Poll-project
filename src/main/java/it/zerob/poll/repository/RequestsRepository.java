package it.zerob.poll.repository;

import it.zerob.poll.model.Requests;
import it.zerob.poll.model.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RequestsRepository extends CrudRepository<Requests, Long> {

}
