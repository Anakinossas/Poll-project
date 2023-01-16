package it.zerob.poll.model;

import jakarta.persistence.*;

import java.io.Serializable;

public class RequestsPK implements Serializable {
    @JoinColumn(name = "ID_USER_FK")
    @Id
    @ManyToOne()
    private Users idUserFk;
    @JoinColumn(name = "ID_POLL_FK")
    @Id
    @ManyToOne()
    private Polls idPollFk;

    public Users getIdUserFk() {
        return idUserFk;
    }

    public void setIdUserFk(Users idUserFk) {
        this.idUserFk = idUserFk;
    }

    public Polls getIdPollFk() {
        return idPollFk;
    }

    public void setIdPollFk(Polls idPollFk) {
        this.idPollFk = idPollFk;
    }

}
