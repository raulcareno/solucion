/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package hibernate;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author geovanny
 */
public class Accesos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "pantalla")
    private String pantalla;
    @Column(name = "agregar")
    private Boolean agregar;
    @Column(name = "modificar")
    private Boolean modificar;
    @Column(name = "eliminar")
    private Boolean eliminar;
    @Column(name = "ingresar")
    private Boolean ingresar;
    @JoinColumn(name = "perfil", referencedColumnName = "codigo")
    @ManyToOne
    private Global perfil;

    public Accesos() {
    }

    public Accesos(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getPantalla() {
        return pantalla;
    }

    public void setPantalla(String pantalla) {
        this.pantalla = pantalla;
    }

    public Boolean getAgregar() {
        return agregar;
    }

    public void setAgregar(Boolean agregar) {
        this.agregar = agregar;
    }

    public Boolean getModificar() {
        return modificar;
    }

    public void setModificar(Boolean modificar) {
        this.modificar = modificar;
    }

    public Boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
    }

    public Global getPerfil() {
        return perfil;
    }

    public void setPerfil(Global perfil) {
        this.perfil = perfil;
    }

    public Boolean getIngresar() {
        return ingresar;
    }

    public void setIngresar(Boolean ingresar) {
        this.ingresar = ingresar;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Accesos)) {
            return false;
        }
        Accesos other = (Accesos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persisten.Accesos[codigo=" + codigo + "]";
    }

}
