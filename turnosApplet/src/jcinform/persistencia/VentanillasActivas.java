/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.persistencia;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "ventanillas_activas")
@NamedQueries({
    @NamedQuery(name = "VentanillasActivas.findAll", query = "SELECT v FROM VentanillasActivas v")})
public class VentanillasActivas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigovenact")
    private Integer codigovenact;
    @Basic(optional = false)
    @Column(name = "numero")
    private String numero;
    @Column(name = "estado")
    private Boolean estado;
    @JoinColumn(name = "codigoven", referencedColumnName = "codigoven")
    @ManyToOne
    private Ventanillas codigoven;
    @JoinColumn(name = "codigoemp", referencedColumnName = "codigoemp")
    @ManyToOne
    private Empleados codigoemp;

    public VentanillasActivas() {
    }

    public VentanillasActivas(Integer codigovenact) {
        this.codigovenact = codigovenact;
    }

    public VentanillasActivas(Integer codigovenact, String numero) {
        this.codigovenact = codigovenact;
        this.numero = numero;
    }

    public Integer getCodigovenact() {
        return codigovenact;
    }

    public void setCodigovenact(Integer codigovenact) {
        this.codigovenact = codigovenact;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Ventanillas getCodigoven() {
        return codigoven;
    }

    public void setCodigoven(Ventanillas codigoven) {
        this.codigoven = codigoven;
    }

    public Empleados getCodigoemp() {
        return codigoemp;
    }

    public void setCodigoemp(Empleados codigoemp) {
        this.codigoemp = codigoemp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigovenact != null ? codigovenact.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentanillasActivas)) {
            return false;
        }
        VentanillasActivas other = (VentanillasActivas) object;
        if ((this.codigovenact == null && other.codigovenact != null) || (this.codigovenact != null && !this.codigovenact.equals(other.codigovenact))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.Persistencia.VentanillasActivas[ codigovenact=" + codigovenact + " ]";
    }
    
}
