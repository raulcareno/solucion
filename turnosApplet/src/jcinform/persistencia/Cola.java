/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.persistencia;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "cola")
@NamedQueries({
    @NamedQuery(name = "Cola.findAll", query = "SELECT c FROM Cola c")})
public class Cola implements Serializable {
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    private Date hora;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigocol")
    private Integer codigocol;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "llamadopor")
    private String llamadopor;
    @JoinColumn(name = "codigotur", referencedColumnName = "codigotur")
    @ManyToOne
    private Turnos codigotur;

    public Cola() {
    }

    public Cola(Integer codigocol) {
        this.codigocol = codigocol;
    }

    public Integer getCodigocol() {
        return codigocol;
    }

    public void setCodigocol(Integer codigocol) {
        this.codigocol = codigocol;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getLlamadopor() {
        return llamadopor;
    }

    public void setLlamadopor(String llamadopor) {
        this.llamadopor = llamadopor;
    }

    public Turnos getCodigotur() {
        return codigotur;
    }

    public void setCodigotur(Turnos codigotur) {
        this.codigotur = codigotur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigocol != null ? codigocol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cola)) {
            return false;
        }
        Cola other = (Cola) object;
        if ((this.codigocol == null && other.codigocol != null) || (this.codigocol != null && !this.codigocol.equals(other.codigocol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.Persistencia.Cola[ codigocol=" + codigocol + " ]";
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }
    
}
