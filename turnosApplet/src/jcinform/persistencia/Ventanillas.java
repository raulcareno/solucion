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
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "ventanillas")
@NamedQueries({
    @NamedQuery(name = "Ventanillas.findAll", query = "SELECT v FROM Ventanillas v")})
public class Ventanillas implements Serializable {

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigoven")
    private Integer codigoven;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "codificacion")
    private String codificacion;
    @Column(name = "escorporativa")
    private int escorporativa =0;
    @Column(name = "estado")
    private Boolean estado =false;
    @OneToMany(mappedBy = "codigoven")
    private Collection<VentanillasActivas> ventanillasActivasCollection;
    @OneToMany(mappedBy = "codigoven")
    private Collection<VentanillasEmpleados> ventanillasEmpleadosCollection;

    public Ventanillas() {
    }

    public Ventanillas(Integer codigoven) {
        this.codigoven = codigoven;
    }

    public Integer getCodigoven() {
        return codigoven;
    }

    public void setCodigoven(Integer codigoven) {
        this.codigoven = codigoven;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodificacion() {
        return codificacion;
    }

    public void setCodificacion(String codificacion) {
        this.codificacion = codificacion;
    }

    public Collection<VentanillasActivas> getVentanillasActivasCollection() {
        return ventanillasActivasCollection;
    }

    public void setVentanillasActivasCollection(Collection<VentanillasActivas> ventanillasActivasCollection) {
        this.ventanillasActivasCollection = ventanillasActivasCollection;
    }

    public Collection<VentanillasEmpleados> getVentanillasEmpleadosCollection() {
        return ventanillasEmpleadosCollection;
    }

    public void setVentanillasEmpleadosCollection(Collection<VentanillasEmpleados> ventanillasEmpleadosCollection) {
        this.ventanillasEmpleadosCollection = ventanillasEmpleadosCollection;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

 

    public int getEscorporativa() {
        return escorporativa;
    }

    public void setEscorporativa(int escorporativa) {
        this.escorporativa = escorporativa;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigoven != null ? codigoven.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ventanillas)) {
            return false;
        }
        Ventanillas other = (Ventanillas) object;
        if ((this.codigoven == null && other.codigoven != null) || (this.codigoven != null && !this.codigoven.equals(other.codigoven))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }
}
