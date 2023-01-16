package it.zerob.poll.model;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigInteger;

public class RequestsPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "ID_USER_FK")
    @Id
    private Users idUserFk;

    @ManyToOne
    @JoinColumn(name = "ID_POLL_FK")
    @Id

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
