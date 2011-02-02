/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hibernate;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author geovanny
 */

public class Comunicaciones implements Serializable {
    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @Column(name = "numero")
    private Integer numero;
    @Column(name = "fechai")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechai;
    @Column(name = "fechac")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechac;
    @Column(name = "dependencia")
    private String dependencia;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "departamento")
    private String departamento;
    @Column(name = "responsable")
    private String responsable;
    @Column(name = "estado")
    private String estado;

    public Comunicaciones() {
    }

    public Comunicaciones(Integer numero) {
        this.numero = numero;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Date getFechai() {
        return fechai;
    }

    public void setFechai(Date fechai) {
        this.fechai = fechai;
    }

    public Date getFechac() {
        return fechac;
    }

    public void setFechac(Date fechac) {
        this.fechac = fechac;
    }

    public String getDependencia() {
        return dependencia;
    }

    public void setDependencia(String dependencia) {
        this.dependencia = dependencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numero != null ? numero.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comunicaciones)) {
            return false;
        }
        Comunicaciones other = (Comunicaciones) object;
        if ((this.numero == null && other.numero != null) || (this.numero != null && !this.numero.equals(other.numero))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Comunicaciones[numero=" + numero + "]";
    }

}
