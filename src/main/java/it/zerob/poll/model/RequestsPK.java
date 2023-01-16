package it.zerob.poll.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.math.BigInteger;

public class RequestsPK implements Serializable {
    @Column(name = "ID_USER_FK")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger idUserFk;
    @Column(name = "ID_POLL_FK")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

        RequestsPK that = (RequestsPK) o;

        if (idUserFk != null ? !idUserFk.equals(that.idUserFk) : that.idUserFk != null) return false;
        if (idPollFk != null ? !idPollFk.equals(that.idPollFk) : that.idPollFk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idUserFk != null ? idUserFk.hashCode() : 0;
        result = 31 * result + (idPollFk != null ? idPollFk.hashCode() : 0);
        return result;
    }
}
