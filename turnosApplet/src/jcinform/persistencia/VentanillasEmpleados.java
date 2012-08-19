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
@Table(name = "ventanillas_empleados")
@NamedQueries({
    @NamedQuery(name = "VentanillasEmpleados.findAll", query = "SELECT v FROM VentanillasEmpleados v")})
public class VentanillasEmpleados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigovenemp")
    private Integer codigovenemp;
    @JoinColumn(name = "codigoven", referencedColumnName = "codigoven")
    @ManyToOne
    private Ventanillas codigoven;
    @JoinColumn(name = "codigoemp", referencedColumnName = "codigoemp")
    @ManyToOne
    private Empleados codigoemp;
    @Column(name = "estado")
    private Boolean estado = false;
    @Column(name = "opcional")
    private Boolean opcional = false;

    public VentanillasEmpleados() {
    }

    public VentanillasEmpleados(Integer codigovenemp) {
        this.codigovenemp = codigovenemp;
    }

    public Integer getCodigovenemp() {
        return codigovenemp;
    }

    public void setCodigovenemp(Integer codigovenemp) {
        this.codigovenemp = codigovenemp;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Boolean getOpcional() {
        return opcional;
    }

    public void setOpcional(Boolean opcional) {
        this.opcional = opcional;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigovenemp != null ? codigovenemp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentanillasEmpleados)) {
            return false;
        }
        VentanillasEmpleados other = (VentanillasEmpleados) object;
        if ((this.codigovenemp == null && other.codigovenemp != null) || (this.codigovenemp != null && !this.codigovenemp.equals(other.codigovenemp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return codigoven.getCodificacion()+" "+codigoven.getNombre();
    }
    
}
