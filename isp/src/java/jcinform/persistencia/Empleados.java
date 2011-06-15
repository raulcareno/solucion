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
@Table(name = "empleados")
@NamedQueries({
    @NamedQuery(name = "Empleados.findAll", query = "SELECT e FROM Empleados e")})
public class Empleados implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "identificacion")
    private String identificacion;
    @Column(name = "apellidos")
    private String apellidos;
    @Column(name = "nombres")
    private String nombres;
    @Column(name = "direccion")
    private String direccion;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "email")
    private String email;
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "clave")
    private String clave;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "tipo")
    private String tipo;
    @OneToMany(mappedBy = "empleados")
    private Collection<Comisiones> comisionesCollection;
    @OneToMany(mappedBy = "empleados")
    private Collection<Empleadossucursal> empleadossucursalCollection;
    @JoinColumn(name = "perfil", referencedColumnName = "codigo")
    @ManyToOne
    private Perfil perfil;
    @OneToMany(mappedBy = "empleados")
    private Collection<Auditoria> auditoriaCollection;
    @OneToMany(mappedBy = "empleados")
    private Collection<Contratos> contratosCollection;
    @OneToMany(mappedBy = "empleados1")
    private Collection<Contratos> contratosCollection1;
    @OneToMany(mappedBy = "empleados2")
    private Collection<Contratos> contratosCollection2;

    public Empleados() {
    }

    public Empleados(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Collection<Comisiones> getComisionesCollection() {
        return comisionesCollection;
    }

    public void setComisionesCollection(Collection<Comisiones> comisionesCollection) {
        this.comisionesCollection = comisionesCollection;
    }

    public Collection<Empleadossucursal> getEmpleadossucursalCollection() {
        return empleadossucursalCollection;
    }

    public void setEmpleadossucursalCollection(Collection<Empleadossucursal> empleadossucursalCollection) {
        this.empleadossucursalCollection = empleadossucursalCollection;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Collection<Auditoria> getAuditoriaCollection() {
        return auditoriaCollection;
    }

    public void setAuditoriaCollection(Collection<Auditoria> auditoriaCollection) {
        this.auditoriaCollection = auditoriaCollection;
    }

    public Collection<Contratos> getContratosCollection() {
        return contratosCollection;
    }

    public void setContratosCollection(Collection<Contratos> contratosCollection) {
        this.contratosCollection = contratosCollection;
    }

    public Collection<Contratos> getContratosCollection1() {
        return contratosCollection1;
    }

    public void setContratosCollection1(Collection<Contratos> contratosCollection1) {
        this.contratosCollection1 = contratosCollection1;
    }

    public Collection<Contratos> getContratosCollection2() {
        return contratosCollection2;
    }

    public void setContratosCollection2(Collection<Contratos> contratosCollection2) {
        this.contratosCollection2 = contratosCollection2;
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
        if (!(object instanceof Empleados)) {
            return false;
        }
        Empleados other = (Empleados) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getApellidos()+" "+getNombres();
    }

}
