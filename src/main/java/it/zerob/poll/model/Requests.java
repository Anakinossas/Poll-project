package it.zerob.poll.model;

import jakarta.persistence.*;

import java.math.BigInteger;

@Entity
@IdClass(RequestsPK.class)
public class Requests {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_USER_FK")
    private BigInteger idUserFk;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_POLL_FK")
    private BigInteger idPollFk;

    public BigInteger getIdUserFk() {
        return idUserFk;
    }

    public void setIdUserFk(BigInteger idUserFk) {
        this.idUserFk = idUserFk;
    }

    public BigInteger getIdPollFk() {
        return idPollFk;
    }

    public void setIdPollFk(BigInteger idPollFk) {
        this.idPollFk = idPollFk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Requests requests = (Requests) o;

        if (idUserFk != null ? !idUserFk.equals(requests.idUserFk) : requests.idUserFk != null) return false;
        if (idPollFk != null ? !idPollFk.equals(requests.idPollFk) : requests.idPollFk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUserFk != null ? idUserFk.hashCode() : 0;
        result = 31 * result + (idPollFk != null ? idPollFk.hashCode() : 0);
        return result;
    }
}
