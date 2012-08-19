/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.persistencia;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "empleados")
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e")})
public class Empleados implements Serializable {
    @Column(name = "ultimoingreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimoingreso;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigoemp")
    private Integer codigoemp;
    @Basic(optional = false)
    @Column(name = "apellidos")
    private String apellidos;
    @Basic(optional = false)
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Column(name = "usuario")
    private String usuario;
    @Basic(optional = false)
    @Column(name = "clave")
    private String clave;
    @Column(name = "tipousuario")
    private Integer tipousuario;
    @OneToMany(mappedBy = "codigoemp")
    private Collection<Turnos> turnosCollection;
    @OneToMany(mappedBy = "codigoemp")
    private Collection<VentanillasActivas> ventanillasActivasCollection;
    @OneToMany(mappedBy = "empleados")
    private Collection<Calificacion> calificacionCollection;
    @OneToMany(mappedBy = "codigoemp")
    private Collection<VentanillasEmpleados> ventanillasEmpleadosCollection;
    @JoinColumn(name = "codigoper", referencedColumnName = "codigoper")
    @ManyToOne
    private Perfiles codigoper;

    public Empleados() {
    }

    public Empleados(Integer codigoemp) {
        this.codigoemp = codigoemp;
    }

    public Empleados(Integer codigoemp, String apellidos, String nombres, String usuario, String clave) {
        this.codigoemp = codigoemp;
        this.apellidos = apellidos;
        this.nombres = nombres;
        this.usuario = usuario;
        this.clave = clave;
    }

    public Integer getCodigoemp() {
        return codigoemp;
    }

    public void setCodigoemp(Integer codigoemp) {
        this.codigoemp = codigoemp;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getTipousuario() {
        return tipousuario;
    }

    public void setTipousuario(Integer tipousuario) {
        this.tipousuario = tipousuario;
    }

    public Collection<Turnos> getTurnosCollection() {
        return turnosCollection;
    }

    public void setTurnosCollection(Collection<Turnos> turnosCollection) {
        this.turnosCollection = turnosCollection;
    }

    public Collection<VentanillasActivas> getVentanillasActivasCollection() {
        return ventanillasActivasCollection;
    }

    public void setVentanillasActivasCollection(Collection<VentanillasActivas> ventanillasActivasCollection) {
        this.ventanillasActivasCollection = ventanillasActivasCollection;
    }

    public Collection<Calificacion> getCalificacionCollection() {
        return calificacionCollection;
    }

    public void setCalificacionCollection(Collection<Calificacion> calificacionCollection) {
        this.calificacionCollection = calificacionCollection;
    }

    public Collection<VentanillasEmpleados> getVentanillasEmpleadosCollection() {
        return ventanillasEmpleadosCollection;
    }

    public void setVentanillasEmpleadosCollection(Collection<VentanillasEmpleados> ventanillasEmpleadosCollection) {
        this.ventanillasEmpleadosCollection = ventanillasEmpleadosCollection;
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
        hash += (codigoemp != null ? codigoemp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.codigoemp == null && other.codigoemp != null) || (this.codigoemp != null && !this.codigoemp.equals(other.codigoemp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return apellidos+" "+nombres;
    }

    public Date getUltimoingreso() {
        return ultimoingreso;
    }

    public void setUltimoingreso(Date ultimoingreso) {
        this.ultimoingreso = ultimoingreso;
    }
    
}
