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
@Table(name = "accesos")
@NamedQueries({
    @NamedQuery(name = "Accesos.findAll", query = "SELECT a FROM Accesos a")})
public class Accesos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigoacc")
    private Integer codigoacc;
    @Column(name = "pantalla")
    private String pantalla;
    @Column(name = "ingresar")
    private Boolean ingresar;
    @Column(name = "agregar")
    private Boolean agregar;
    @Column(name = "eliminar")
    private Boolean eliminar;
    @Column(name = "modificar")
    private Boolean modificar;
    @JoinColumn(name = "codigoper", referencedColumnName = "codigoper")
    @ManyToOne
    private Perfiles codigoper;

    public Accesos() {
    }

    public Accesos(Integer codigoacc) {
        this.codigoacc = codigoacc;
    }

    public Integer getCodigoacc() {
        return codigoacc;
    }

    public void setCodigoacc(Integer codigoacc) {
        this.codigoacc = codigoacc;
    }

    public String getPantalla() {
        return pantalla;
    }

    public void setPantalla(String pantalla) {
        this.pantalla = pantalla;
    }

    public Boolean getIngresar() {
        return ingresar;
    }

    public void setIngresar(Boolean ingresar) {
        this.ingresar = ingresar;
    }

    public Boolean getAgregar() {
        return agregar;
    }

    public void setAgregar(Boolean agregar) {
        this.agregar = agregar;
    }

    public Boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
    }

    public Boolean getModificar() {
        return modificar;
    }

    public void setModificar(Boolean modificar) {
        this.modificar = modificar;
    }

    public Perfiles getCodigoper() {
        return codigoper;
    }

    public void setCodigoper(Perfiles codigoper) {
        this.codigoper = codigoper;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoacc != null ? codigoacc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accesos)) {
            return false;
        }
        Accesos other = (Accesos) object;
        if ((this.codigoacc == null && other.codigoacc != null) || (this.codigoacc != null && !this.codigoacc.equals(other.codigoacc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.Persistencia.Accesos[ codigoacc=" + codigoacc + " ]";
    }
    
}
