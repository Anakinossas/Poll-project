package it.zerob.poll.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

public class RequestsPK implements Serializable {
    @Column(name = "ID_USER_FK")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Users idUserFk;
    @Column(name = "ID_POLL_FK")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
