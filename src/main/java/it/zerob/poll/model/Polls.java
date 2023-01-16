package it.zerob.poll.model;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
public class Polls {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_POLL")
    private Long idPoll;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;

    public Long getIdPoll() {
        return idPoll;
    }

    public void setIdPoll(Long idPoll) {
        this.idPoll = idPoll;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
