/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.persistencia;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "perfiles")
@NamedQueries({
    @NamedQuery(name = "Perfiles.findAll", query = "SELECT p FROM Perfiles p")})
public class Perfiles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigoper")
    private Integer codigoper;
    @Column(name = "nombre")
    private String nombre;
    @OneToMany(mappedBy = "codigoper")
    private Collection<Accesos> accesosCollection;
    @OneToMany(mappedBy = "codigoper")
    private Collection<Empleados> empleadosCollection;

    public Perfiles() {
    }

    public Perfiles(Integer codigoper) {
        this.codigoper = codigoper;
    }

    public Integer getCodigoper() {
        return codigoper;
    }

    public void setCodigoper(Integer codigoper) {
        this.codigoper = codigoper;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<Accesos> getAccesosCollection() {
        return accesosCollection;
    }

    public void setAccesosCollection(Collection<Accesos> accesosCollection) {
        this.accesosCollection = accesosCollection;
    }

    public Collection<Empleados> getEmpleadosCollection() {
        return empleadosCollection;
    }

    public void setEmpleadosCollection(Collection<Empleados> empleadosCollection) {
        this.empleadosCollection = empleadosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoper != null ? codigoper.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Perfiles)) {
            return false;
        }
        Perfiles other = (Perfiles) object;
        if ((this.codigoper == null && other.codigoper != null) || (this.codigoper != null && !this.codigoper.equals(other.codigoper))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getNombre();
    }
    
}
