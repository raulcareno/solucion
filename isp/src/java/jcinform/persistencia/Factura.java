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
@Table(name = "factura")
@NamedQueries({
    @NamedQuery(name = "Factura.findAll", query = "SELECT f FROM Factura f"),
    @NamedQuery(name = "Factura.findByCodigo", query = "SELECT f FROM Factura f WHERE f.codigo = :codigo"),
    @NamedQuery(name = "Factura.findByNumero", query = "SELECT f FROM Factura f WHERE f.numero = :numero"),
    @NamedQuery(name = "Factura.findByFecha", query = "SELECT f FROM Factura f WHERE f.fecha = :fecha"),
    @NamedQuery(name = "Factura.findByTotal", query = "SELECT f FROM Factura f WHERE f.total = :total"),
    @NamedQuery(name = "Factura.findByEstado", query = "SELECT f FROM Factura f WHERE f.estado = :estado"),
    @NamedQuery(name = "Factura.findByObservacion", query = "SELECT f FROM Factura f WHERE f.observacion = :observacion"),
    @NamedQuery(name = "Factura.findByEfectivo", query = "SELECT f FROM Factura f WHERE f.efectivo = :efectivo"),
    @NamedQuery(name = "Factura.findByDeposito", query = "SELECT f FROM Factura f WHERE f.deposito = :deposito"),
    @NamedQuery(name = "Factura.findByCheque", query = "SELECT f FROM Factura f WHERE f.cheque = :cheque"),
    @NamedQuery(name = "Factura.findByBanco", query = "SELECT f FROM Factura f WHERE f.banco = :banco"),
    @NamedQuery(name = "Factura.findByNocuenta", query = "SELECT f FROM Factura f WHERE f.nocuenta = :nocuenta"),
    @NamedQuery(name = "Factura.findByNocheque", query = "SELECT f FROM Factura f WHERE f.nocheque = :nocheque"),
    @NamedQuery(name = "Factura.findByTarjeta", query = "SELECT f FROM Factura f WHERE f.tarjeta = :tarjeta")})
public class Factura implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "numero")
    private String numero;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "total")
    private Double total;
    @Column(name = "estado")
    private Boolean estado;
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "efectivo")
    private Double efectivo;
    @Column(name = "deposito")
    private Double deposito;
    @Column(name = "cheque")
    private Double cheque;
    @Column(name = "banco")
    private String banco;
    @Column(name = "nocuenta")
    private String nocuenta;
    @Column(name = "nocheque")
    private String nocheque;
    @Column(name = "tarjeta")
    private Double tarjeta;
//    @OneToMany(mappedBy = "factura")
//    private Collection<Cxcobrar> cxcobrarCollection;
    @OneToMany(mappedBy = "factura")
    private Collection<Detalle> detalleCollection;

    public Factura() {
    }

    public Factura(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Double getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(Double efectivo) {
        this.efectivo = efectivo;
    }

    public Double getDeposito() {
        return deposito;
    }

    public void setDeposito(Double deposito) {
        this.deposito = deposito;
    }

    public Double getCheque() {
        return cheque;
    }

    public void setCheque(Double cheque) {
        this.cheque = cheque;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getNocuenta() {
        return nocuenta;
    }

    public void setNocuenta(String nocuenta) {
        this.nocuenta = nocuenta;
    }

    public String getNocheque() {
        return nocheque;
    }

    public void setNocheque(String nocheque) {
        this.nocheque = nocheque;
    }

    public Double getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Double tarjeta) {
        this.tarjeta = tarjeta;
    }

//    public Collection<Cxcobrar> getCxcobrarCollection() {
//        return cxcobrarCollection;
//    }
//
//    public void setCxcobrarCollection(Collection<Cxcobrar> cxcobrarCollection) {
//        this.cxcobrarCollection = cxcobrarCollection;
//    }

    public Collection<Detalle> getDetalleCollection() {
        return detalleCollection;
    }

    public void setDetalleCollection(Collection<Detalle> detalleCollection) {
        this.detalleCollection = detalleCollection;
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
        if (!(object instanceof Factura)) {
            return false;
        }
        Factura other = (Factura) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Factura[codigo=" + codigo + "]";
    }

}
