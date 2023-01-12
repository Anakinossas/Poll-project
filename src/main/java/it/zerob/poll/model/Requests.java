package it.zerob.poll.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
@Table(name = "REQUESTS", schema = "POLL")
public class Requests {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID_REQ")
    private Long idReq;

    @JoinColumn(name = "ID_USER_FK")
    @Nullable
    @ManyToOne()
    private Users idUserFk;

    public Long getIdReq() {
        return idReq;
    }

    public void setIdReq(Long idReq) {
        this.idReq = idReq;
    }

    public Users getIdUserFk() {
        return idUserFk;
    }

    public void setIdUserFk(Users idUserFk) {
        this.idUserFk = idUserFk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Requests requests = (Requests) o;

        if (idReq != null ? !idReq.equals(requests.idReq) : requests.idReq != null) return false;
        if (idUserFk != null ? !idUserFk.equals(requests.idUserFk) : requests.idUserFk != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idReq != null ? idReq.hashCode() : 0;
        result = 31 * result + (idUserFk != null ? idUserFk.hashCode() : 0);
        return result;
    }
}
