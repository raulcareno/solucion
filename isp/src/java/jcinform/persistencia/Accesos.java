/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jcinform.persistencia;

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
 * @author Geovanny Jadan
 */
@Entity
@Table(name = "accesos")
@NamedQueries({
    @NamedQuery(name = "Accesos.findAll", query = "SELECT a FROM Accesos a")})
public class Accesos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigoacc")
    private Integer codigoacc;
    @Column(name = "modulo")
    private String modulo;
    @Column(name = "guardar")
    private Boolean guardar;
    @Column(name = "eliminar")
    private Boolean eliminar;
    @Column(name = "actualizar")
    private Boolean actualizar;
    @Column(name = "ingresar")
    private Boolean ingresar;
    @Column(name = "grupo")
    private String grupo;
    @JoinColumn(name = "perfil", referencedColumnName = "codigo")
    @ManyToOne
    private Perfil perfil;

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

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public Boolean getGuardar() {
        return guardar;
    }

    public void setGuardar(Boolean guardar) {
        this.guardar = guardar;
    }

    public Boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
    }

    public Boolean getActualizar() {
        return actualizar;
    }

    public void setActualizar(Boolean actualizar) {
        this.actualizar = actualizar;
    }

    public Boolean getIngresar() {
        return ingresar;
    }

    public void setIngresar(Boolean ingresar) {
        this.ingresar = ingresar;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
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
        return "persistencia.Accesos[codigoacc=" + codigoacc + "]";
    }

}
