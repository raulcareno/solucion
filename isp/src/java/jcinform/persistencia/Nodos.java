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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Familia Jadan Cahueñ
 */
@Entity
@Table(name = "nodos")
@NamedQueries({
    @NamedQuery(name = "Nodos.findAll", query = "SELECT n FROM Nodos n")})
public class Nodos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "torre")
    private String torre;
    @Column(name = "observaciones")
    private String observaciones;
    @OneToMany(mappedBy = "nodos")
    private Collection<Radios> radiosCollection;
    @JoinColumn(name = "sector", referencedColumnName = "codigo")
    @ManyToOne
    private Sector sector;
//    @OneToMany(mappedBy = "nodos")
//    private Collection<Contratos> contratosCollection;

    public Nodos() {
    }

    public Nodos(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTorre() {
        return torre;
    }

    public void setTorre(String torre) {
        this.torre = torre;
    }

    public Collection<Radios> getRadiosCollection() {
        return radiosCollection;
    }

    public void setRadiosCollection(Collection<Radios> radiosCollection) {
        this.radiosCollection = radiosCollection;
    }

    public Sector getSector() {
        return sector;
    }

    public void setSector(Sector sector) {
        this.sector = sector;
    }

//    public Collection<Contratos> getContratosCollection() {
//        return contratosCollection;
//    }
//
//    public void setContratosCollection(Collection<Contratos> contratosCollection) {
//        this.contratosCollection = contratosCollection;
//    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nodos)) {
            return false;
        }
        Nodos other = (Nodos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Nodos[codigo=" + codigo + "]";
    }
}
