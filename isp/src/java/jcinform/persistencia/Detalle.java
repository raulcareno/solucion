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
 * @author Geovanny Jadan
 */
@Entity
@Table(name = "detalle")
@NamedQueries({
    @NamedQuery(name = "Detalle.findAll", query = "SELECT d FROM Detalle d"),
    @NamedQuery(name = "Detalle.findByCodigo", query = "SELECT d FROM Detalle d WHERE d.codigo = :codigo"),
    @NamedQuery(name = "Detalle.findByMes", query = "SELECT d FROM Detalle d WHERE d.mes = :mes"),
    @NamedQuery(name = "Detalle.findByCantidad", query = "SELECT d FROM Detalle d WHERE d.cantidad = :cantidad"),
    @NamedQuery(name = "Detalle.findByPrecio", query = "SELECT d FROM Detalle d WHERE d.precio = :precio"),
    @NamedQuery(name = "Detalle.findByDescuento", query = "SELECT d FROM Detalle d WHERE d.descuento = :descuento"),
    @NamedQuery(name = "Detalle.findByBeca", query = "SELECT d FROM Detalle d WHERE d.beca = :beca"),
    @NamedQuery(name = "Detalle.findByTotal", query = "SELECT d FROM Detalle d WHERE d.total = :total"),
    @NamedQuery(name = "Detalle.findByDescripcion", query = "SELECT d FROM Detalle d WHERE d.descripcion = :descripcion"),
    @NamedQuery(name = "Detalle.findByAnio", query = "SELECT d FROM Detalle d WHERE d.anio = :anio"),
    @NamedQuery(name = "Detalle.findByAsignado", query = "SELECT d FROM Detalle d WHERE d.asignado = :asignado")})
public class Detalle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "mes")
    private Integer mes;
    @Column(name = "cantidad")
    private Integer cantidad;
    @Column(name = "precio")
    private Double precio;
    @Column(name = "descuento")
    private Double descuento;
    @Column(name = "beca")
    private Double beca;
    @Column(name = "total")
    private Double total;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "anio")
    private Integer anio;
    @Column(name = "asignado")
    private Integer asignado;
    @JoinColumn(name = "factura", referencedColumnName = "codigo")
    @ManyToOne
    private Factura factura;

    public Detalle() {
    }

    public Detalle(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public Double getBeca() {
        return beca;
    }

    public void setBeca(Double beca) {
        this.beca = beca;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getAsignado() {
        return asignado;
    }

    public void setAsignado(Integer asignado) {
        this.asignado = asignado;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
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
        if (!(object instanceof Detalle)) {
            return false;
        }
        Detalle other = (Detalle) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Detalle[codigo=" + codigo + "]";
    }

}
