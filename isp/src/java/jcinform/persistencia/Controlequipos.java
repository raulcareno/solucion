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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
 * @author Geovanny Jadan
 */
@Entity
@Table(name = "controlequipos")
@NamedQueries({
    @NamedQuery(name = "Controlequipos.findAll", query = "SELECT c FROM Controlequipos c")})
public class Controlequipos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "fechacompra")
    @Temporal(TemporalType.DATE)
    private Date fechacompra =  new Date();
    @Column(name = "garantia")
    private Integer garantia;
    @Column(name = "precio")
    private Double precio;
    @Column(name = "asignado")
    private Boolean asignado;
    @Column(name = "mac")
    private String mac;
    @JoinColumn(name = "proveedores", referencedColumnName = "codigo")
    @ManyToOne
    private Proveedores proveedores;
    @JoinColumn(name = "equipos", referencedColumnName = "codigo")
    @ManyToOne
    private Equipos equipos;
//    @OneToMany(mappedBy = "controlequipos")
//    private Collection<Contratos> contratosCollection;
//    @OneToMany(mappedBy = "controlequipos1")
//    private Collection<Contratos> contratosCollection1;
//    @OneToMany(mappedBy = "controlequipos2")
//    private Collection<Contratos> contratosCollection2;

    public Controlequipos() {
    }

    public Controlequipos(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFechacompra() {
        return fechacompra;
    }

    public void setFechacompra(Date fechacompra) {
        this.fechacompra = fechacompra;
    }

    public Integer getGarantia() {
        return garantia;
    }

    public void setGarantia(Integer garantia) {
        this.garantia = garantia;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Boolean getAsignado() {
        return asignado;
    }

    public void setAsignado(Boolean asignado) {
        this.asignado = asignado;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Proveedores getProveedores() {
        return proveedores;
    }

    public void setProveedores(Proveedores proveedores) {
        this.proveedores = proveedores;
    }

    public Equipos getEquipos() {
        return equipos;
    }

    public void setEquipos(Equipos equipos) {
        this.equipos = equipos;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    

//    public Collection<Contratos> getContratosCollection() {
//        return contratosCollection;
//    }
//
//    public void setContratosCollection(Collection<Contratos> contratosCollection) {
//        this.contratosCollection = contratosCollection;
//    }
//
//    public Collection<Contratos> getContratosCollection1() {
//        return contratosCollection1;
//    }
//
//    public void setContratosCollection1(Collection<Contratos> contratosCollection1) {
//        this.contratosCollection1 = contratosCollection1;
//    }
//
//    public Collection<Contratos> getContratosCollection2() {
//        return contratosCollection2;
//    }
//
//    public void setContratosCollection2(Collection<Contratos> contratosCollection2) {
//        this.contratosCollection2 = contratosCollection2;
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
        if (!(object instanceof Controlequipos)) {
            return false;
        }
        Controlequipos other = (Controlequipos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "persistencia.Controlequipos[codigo=" + codigo + "]";
    }

}
