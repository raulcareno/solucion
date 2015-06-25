/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.persistencia;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Geovanny
 */
@Entity
@Table(name = "depositos")
@NamedQueries({
    @NamedQuery(name = "Depositos.findAll", query = "SELECT d FROM Depositos d")})
public class Depositos implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codigo")
    private Integer codigo;
    @Column(name = "cxcobrar")
    private Integer cxcobrar;
    @Column(name = "nodeposito")
    private String nodeposito;
    @Column(name = "fechacxc")
    @Temporal(TemporalType.DATE)
    private Date fechacxc;
    @Column(name = "factura")
    private String factura;
    @Column(name = "tipo")
    private String tipo;
    @Column(name = "contable")
    private String contable;
    @Column(name = "detalle")
    private String detalle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pago")
    private BigDecimal pago;
    @Column(name = "contrato")
    private Integer contrato;
    @Column(name = "nopago")
    private Integer nopago;
    @Column(name = "cliente")
    private String cliente;
    @Column(name = "ruc")
    private String ruc;
    @Column(name = "monto")
    private BigDecimal monto;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Column(name = "emision")
    @Temporal(TemporalType.DATE)
    private Date emision;
    @Column(name = "vence")
    @Temporal(TemporalType.DATE)
    private Date vence;
    @Column(name = "comentario")
    private String comentario;
        @Column(name = "tipotransaccion")
    private String tipotransaccion;
           @Column(name = "banco")
    private Integer banco;
        @JoinColumn(name = "empleados", referencedColumnName = "codigo")
    @ManyToOne
    private Empleados empleados;

    public Depositos() {
    }

    public Depositos(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getCxcobrar() {
        return cxcobrar;
    }

    public void setCxcobrar(Integer cxcobrar) {
        this.cxcobrar = cxcobrar;
    }

    public String getNodeposito() {
        return nodeposito;
    }

    public void setNodeposito(String nodeposito) {
        this.nodeposito = nodeposito;
    }

    public Date getFechacxc() {
        return fechacxc;
    }

    public void setFechacxc(Date fechacxc) {
        this.fechacxc = fechacxc;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getContable() {
        return contable;
    }

    public void setContable(String contable) {
        this.contable = contable;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigDecimal getPago() {
        return pago;
    }

    public void setPago(BigDecimal pago) {
        this.pago = pago;
    }

    public Integer getContrato() {
        return contrato;
    }

    public void setContrato(Integer contrato) {
        this.contrato = contrato;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getEmision() {
        return emision;
    }

    public void setEmision(Date emision) {
        this.emision = emision;
    }

    public Date getVence() {
        return vence;
    }

    public void setVence(Date vence) {
        this.vence = vence;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Empleados getEmpleados() {
        return empleados;
    }

    public void setEmpleados(Empleados empleados) {
        this.empleados = empleados;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Integer getNopago() {
        return nopago;
    }

    public void setNopago(Integer nopago) {
        this.nopago = nopago;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getTipotransaccion() {
        return tipotransaccion;
    }

    public void setTipotransaccion(String tipotransaccion) {
        this.tipotransaccion = tipotransaccion;
    }

    public Integer getBanco() {
        return banco;
    }

    public void setBanco(Integer banco) {
        this.banco = banco;
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
        if (!(object instanceof Depositos)) {
            return false;
        }
        Depositos other = (Depositos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jcinform.persistencia.Depositos[ codigo=" + codigo + " ]";
    }
    
}
