package it.zerob.poll.model;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "REQUESTS", schema = "POLL")
@IdClass(RequestsPK.class)
public class Requests {

    @Id
    @JoinColumn(name = "ID_USER_FK")
    @ManyToOne
    private Users idUserFk;

    @Id
    @JoinColumn(name = "ID_POLL_FK")
    @ManyToOne
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
