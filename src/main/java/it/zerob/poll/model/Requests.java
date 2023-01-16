package it.zerob.poll.model;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@Table(name = "REQUESTS", schema = "POLL")
@IdClass(RequestsPK.class)
public class Requests {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_USER_FK")
    private Long idUserFk;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_POLL_FK")
    private Long idPollFk;

    public Long getIdUserFk() {
        return idUserFk;
    }

    public void setIdUserFk(Long idUserFk) {
        this.idUserFk = idUserFk;
    }

    public Long getIdPollFk() {
        return idPollFk;
    }

    public void setIdPollFk(Long idPollFk) {
        this.idPollFk = idPollFk;
    }
}
