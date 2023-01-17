package it.zerob.poll.model;

import jakarta.persistence.*;

@Entity
public class Polls {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_POLL")
    private Long idPoll;
    @Basic
    @Column(name = "DESCRIPTION")
    private String description;

    @Basic
    @Column(name = "IS_CLOSED")
    private String is_closed;

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

    public String getIs_closed() {
        return is_closed;
    }

    public void setIs_closed(String is_closed) {
        this.is_closed = is_closed;
    }
}
