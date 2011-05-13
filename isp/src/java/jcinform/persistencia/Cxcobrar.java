/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jcinform.persistencia;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Familia Jadan Cahueñ
 */
@Entity
@Table(name = "cxcobrar")
@NamedQueries({
    @NamedQuery(name = "Cxcobrar.findAll", query = "SELECT c FROM Cxcobrar c")})
public class Cxcobrar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private String codigo;
    @Column(name = "debe")
    private BigDecimal debe;
    @Column(name = "haber")
    private BigDecimal haber;
    @Column(name = "total")
    private BigDecimal total;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "efectivo")
    private Double efectivo;
    @Column(name = "cheque")
    private BigDecimal cheque;
    @Column(name = "debito")
    private BigDecimal debito;
    @Column(name = "tarjeta")
    private BigDecimal tarjeta;
    @Column(name = "nocheque")
    private BigDecimal nocheque;
    @Column(name = "notarjeta")
    private BigDecimal notarjeta;
    @JoinColumn(name = "factura", referencedColumnName = "codigo")
    @ManyToOne
    private Factura factura;

    public Cxcobrar() {
    }

    public Cxcobrar(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getDebe() {
        return debe;
    }

    public void setDebe(BigDecimal debe) {
        this.debe = debe;
    }

    public BigDecimal getHaber() {
        return haber;
    }

    public void setHaber(BigDecimal haber) {
        this.haber = haber;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(Double efectivo) {
        this.efectivo = efectivo;
    }

    public BigDecimal getCheque() {
        return cheque;
    }

    public void setCheque(BigDecimal cheque) {
        this.cheque = cheque;
    }

    public BigDecimal getDebito() {
        return debito;
    }

    public void setDebito(BigDecimal debito) {
        this.debito = debito;
    }

    public BigDecimal getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(BigDecimal tarjeta) {
        this.tarjeta = tarjeta;
    }

    public BigDecimal getNocheque() {
        return nocheque;
    }

    public void setNocheque(BigDecimal nocheque) {
        this.nocheque = nocheque;
    }

    public BigDecimal getNotarjeta() {
        return notarjeta;
    }

    public void setNotarjeta(BigDecimal notarjeta) {
        this.notarjeta = notarjeta;
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
        if (!(object instanceof Cxcobrar)) {
            return false;
        }
        Cxcobrar other = (Cxcobrar) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Cxcobrar[codigo=" + codigo + "]";
    }

}
