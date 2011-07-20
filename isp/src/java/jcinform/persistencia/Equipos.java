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
@Table(name = "equipos")
@NamedQueries({})
public class Equipos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "modelo")
    private String modelo;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "caracteristica")
    private String caracteristica;
    @Column(name = "costo")
    private Double costo;
    @Column(name = "pvp1")
    private Double pvp1;
    @Column(name = "pvp2")
    private Double pvp2;
    @Column(name = "pvp3")
    private Double pvp3;
    @Column(name = "pvp4")
    private Double pvp4;
    @Column(name = "bien")
    private Boolean bien;
    @OneToMany(mappedBy = "equipos")
    private Collection<Detallecompra> detallecompraCollection;
    @JoinColumn(name = "marcas", referencedColumnName = "codigo")
    @ManyToOne
    private Marcas marcas;
//    @OneToMany(mappedBy = "equipos")
//    private Collection<Detalle> detalleCollection;
//    @OneToMany(mappedBy = "equipos")
//    private Collection<Contratos> contratosCollection;
//    @OneToMany(mappedBy = "equipos1")
//    private Collection<Contratos> contratosCollection1;
//    @OneToMany(mappedBy = "equipos2")
//    private Collection<Contratos> contratosCollection2;

    public Equipos() {
    }

    public Equipos(Integer codigo) {
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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(String caracteristica) {
        this.caracteristica = caracteristica;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }

    public Double getPvp1() {
        return pvp1;
    }

    public void setPvp1(Double pvp1) {
        this.pvp1 = pvp1;
    }

    public Double getPvp2() {
        return pvp2;
    }

    public void setPvp2(Double pvp2) {
        this.pvp2 = pvp2;
    }

    public Double getPvp3() {
        return pvp3;
    }

    public void setPvp3(Double pvp3) {
        this.pvp3 = pvp3;
    }

    public Double getPvp4() {
        return pvp4;
    }

    public void setPvp4(Double pvp4) {
        this.pvp4 = pvp4;
    }

    public Boolean getBien() {
        return bien;
    }

    public void setBien(Boolean bien) {
        this.bien = bien;
    }

    public Collection<Detallecompra> getDetallecompraCollection() {
        return detallecompraCollection;
    }

    public void setDetallecompraCollection(Collection<Detallecompra> detallecompraCollection) {
        this.detallecompraCollection = detallecompraCollection;
    }

    public Marcas getMarcas() {
        return marcas;
    }

    public void setMarcas(Marcas marcas) {
        this.marcas = marcas;
    }

//    public Collection<Detalle> getDetalleCollection() {
//        return detalleCollection;
//    }
//
//    public void setDetalleCollection(Collection<Detalle> detalleCollection) {
//        this.detalleCollection = detalleCollection;
//    }
//
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
        if (!(object instanceof Equipos)) {
            return false;
        }
        Equipos other = (Equipos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre+" "+ getMarcas()+" "+getModelo();
    }

}
