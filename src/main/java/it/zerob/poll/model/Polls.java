package it.zerob.poll.model;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
public class Polls {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_POLL")
    private BigInteger idPoll;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;

    public BigInteger getIdPoll() {
        return idPoll;
    }

    public void setIdPoll(BigInteger idPoll) {
        this.idPoll = idPoll;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Polls polls = (Polls) o;

        if (idPoll != null ? !idPoll.equals(polls.idPoll) : polls.idPoll != null) return false;
        if (description != null ? !description.equals(polls.description) : polls.description != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idPoll != null ? idPoll.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }
}
