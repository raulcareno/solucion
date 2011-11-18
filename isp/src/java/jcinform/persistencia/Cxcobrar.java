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
    private Integer codigo;
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
    private BigDecimal efectivo;
    @Column(name = "cheque")
    private BigDecimal cheque;
    @Column(name = "debito")
    private BigDecimal debito;
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
    @Column(name = "transferencia")
    private BigDecimal transferencia;
    @Column(name = "riva")
    private BigDecimal riva;
    @Column(name = "rfuente")
    private BigDecimal rfuente;
    @Column(name = "rtotal")
    private BigDecimal rtotal;
    @Column(name = "numeroretencion")
    private String numeroretencion;
    
    
    @Column(name = "notransferencia")
    private String notransferencia;
    @Column(name = "fechatransferencia")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechatransferencia;
    @Column(name = "fechacheque")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date fechacheque;
    @JoinColumn(name = "factura", referencedColumnName = "codigo")
    @ManyToOne
    private Factura factura;
    @JoinColumn(name = "banco", referencedColumnName = "codigo")
    @ManyToOne
    private Bancos banco;
    @JoinColumn(name = "bancoche", referencedColumnName = "codigo")
    @ManyToOne
    private Bancos bancocheque;
    @JoinColumn(name = "empleados", referencedColumnName = "codigo")
    @ManyToOne
    private Empleados empleados;
    public Cxcobrar() {
    }

    public Cxcobrar(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
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

    public BigDecimal getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(BigDecimal efectivo) {
        this.efectivo = efectivo;
    }

    public String getNocuenta() {
        return nocuenta;
    }

    public void setNocuenta(String nocuenta) {
        this.nocuenta = nocuenta;
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

    public String getNocheque() {
        return nocheque;
    }

    public void setNocheque(String nocheque) {
        this.nocheque = nocheque;
    }

    public String getNotarjeta() {
        return notarjeta;
    }

    public void setNotarjeta(String notarjeta) {
        this.notarjeta = notarjeta;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public Bancos getBanco() {
        return banco;
    }

    public void setBanco(Bancos banco) {
        this.banco = banco;
    }

    public Date getFechatransferencia() {
        return fechatransferencia;
    }

    public void setFechatransferencia(Date fechatransferencia) {
        this.fechatransferencia = fechatransferencia;
    }

    public String getNotransferencia() {
        return notransferencia;
    }

    public void setNotransferencia(String notransferencia) {
        this.notransferencia = notransferencia;
    }

    public BigDecimal getTransferencia() {
        return transferencia;
    }

    public void setTransferencia(BigDecimal transferencia) {
        this.transferencia = transferencia;
    }

   
    public String getNumeroretencion() {
        return numeroretencion;
    }

    public void setNumeroretencion(String numeroretencion) {
        this.numeroretencion = numeroretencion;
    }

    public BigDecimal getRfuente() {
        return rfuente;
    }

    public void setRfuente(BigDecimal rfuente) {
        this.rfuente = rfuente;
    }

    public BigDecimal getRiva() {
        return riva;
    }

    public void setRiva(BigDecimal riva) {
        this.riva = riva;
    }

    public BigDecimal getRtotal() {
        return rtotal;
    }

    public void setRtotal(BigDecimal rtotal) {
        this.rtotal = rtotal;
    }

    public Empleados getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }

    public Bancos getBancocheque() {
        return bancocheque;
    }

    public void setBancocheque(Bancos bancocheque) {
        this.bancocheque = bancocheque;
    }

    public Date getFechacheque() {
        return fechacheque;
    }

    public void setFechacheque(Date fechacheque) {
        this.fechacheque = fechacheque;
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
