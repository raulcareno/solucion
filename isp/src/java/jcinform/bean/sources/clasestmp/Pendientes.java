/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean.sources.clasestmp;

import java.math.BigDecimal;
import java.util.Date;
import jcinform.persistencia.Clientes;

/**
 *
 * @author jcinform
 */
public class Pendientes {
    
    public void Pendientes(){
        
    }
    
    String factura;
    Date fecha,fechapago;
    BigDecimal total,valorabonoefe,valorabonoche,valorabonotar,valorabonodeb;
    BigDecimal saldo;
    Clientes cliente;
    Integer noabono;
    String plan;
    String formapago,notarjeta,nocheque,nocuenta;
    String direccion;

    public Clientes getCliente() {
        return cliente;
    }

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
            
    //fa.codigo, fa.fecha, p.nombre, fa.total,  (SUM(cx.debe) - SUM(cx.haber))

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
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

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getFechapago() {
        return fechapago;
    }

    public void setFechapago(Date fechapago) {
        this.fechapago = fechapago;
    }

    public String getFormapago() {
        return formapago;
    }

    public void setFormapago(String formapago) {
        this.formapago = formapago;
    }

    public Integer getNoabono() {
        return noabono;
    }

    public void setNoabono(Integer noabono) {
        this.noabono = noabono;
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

    public BigDecimal getValorabonoche() {
        return valorabonoche;
    }

    public void setValorabonoche(BigDecimal valorabonoche) {
        this.valorabonoche = valorabonoche;
    }

    public BigDecimal getValorabonodeb() {
        return valorabonodeb;
    }

    public void setValorabonodeb(BigDecimal valorabonodeb) {
        this.valorabonodeb = valorabonodeb;
    }

    public BigDecimal getValorabonoefe() {
        return valorabonoefe;
    }

    public void setValorabonoefe(BigDecimal valorabonoefe) {
        this.valorabonoefe = valorabonoefe;
    }

    public BigDecimal getValorabonotar() {
        return valorabonotar;
    }

    public void setValorabonotar(BigDecimal valorabonotar) {
        this.valorabonotar = valorabonotar;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

  
    
    
    
}
