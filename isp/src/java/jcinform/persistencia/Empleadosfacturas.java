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
 * @author jcinform
 */
@Entity
@Table(name = "empleadosfacturas")
@NamedQueries({
    @NamedQuery(name = "Empleadosfacturas.findAll", query = "SELECT e FROM Empleadosfacturas e")})
public class Empleadosfacturas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "cobrado")
    private Boolean cobrado;
    @Column(name = "visitado")
    private Boolean visitado;
    @Column(name = "fecharegreso")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecharegreso;
        @JoinColumn(name = "empleados", referencedColumnName = "codigo")
    @ManyToOne
    private Empleados empleados;
       @JoinColumn(name = "factura", referencedColumnName = "codigo")
    @ManyToOne
    private Factura factura;

          @Column(name = "efectivo")
    private BigDecimal efectivo;
    @Column(name = "cheque")
    private BigDecimal cheque;
    @Column(name = "deposito")
    private BigDecimal deposito;
    @Column(name = "tarjeta")
    private BigDecimal tarjeta;
    @Column(name = "descuento")
    private BigDecimal descuento;
    @Column(name = "nocheque")
    private String nocheque;
    @Column(name = "notarjeta")
    private String notarjeta;
    @Column(name = "nocuenta")
    private String nocuenta;
  @Column(name = "saldo")
    private BigDecimal saldo;
@Column(name = "bancario")
    private BigDecimal bancario;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total")
    private BigDecimal total;

    public Empleadosfacturas() {
    }

    public Empleadosfacturas(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public Boolean getCobrado() {
        return cobrado;
    }

    public void setCobrado(Boolean cobrado) {
        this.cobrado = cobrado;
    }

    public Boolean getVisitado() {
        return visitado;
    }

    public void setVisitado(Boolean visitado) {
        this.visitado = visitado;
    }

    public Date getFecharegreso() {
        return fecharegreso;
    }

    public void setFecharegreso(Date fecharegreso) {
        this.fecharegreso = fecharegreso;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Empleados getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public BigDecimal getCheque() {
        return cheque;
    }

    public void setCheque(BigDecimal cheque) {
        this.cheque = cheque;
    }

    public BigDecimal getDeposito() {
        return deposito;
    }

    public void setDeposito(BigDecimal deposito) {
        this.deposito = deposito;
    }

    public BigDecimal getBancario() {
        return bancario;
    }

    public void setBancario(BigDecimal bancario) {
        this.bancario = bancario;
    }

  

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    public String getNocheque() {
        return nocheque;
    }

    public void setNocheque(String nocheque) {
        this.nocheque = nocheque;
    }

    public String getNocuenta() {
        return nocuenta;
    }

    public void setNocuenta(String nocuenta) {
        this.nocuenta = nocuenta;
    }

    public String getNotarjeta() {
        return notarjeta;
    }

    public void setNotarjeta(String notarjeta) {
        this.notarjeta = notarjeta;
    }

    public BigDecimal getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(BigDecimal tarjeta) {
        this.tarjeta = tarjeta;
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
        if (!(object instanceof Empleadosfacturas)) {
            return false;
        }
        Empleadosfacturas other = (Empleadosfacturas) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Empleadosfacturas[ codigo=" + codigo + " ]";
    }
    
}
