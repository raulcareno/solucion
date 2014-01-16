/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean.sources.clasestmp;

import java.math.BigDecimal;
import java.util.Date;
import jcinform.persistencia.Clientes;
import jcinform.persistencia.Contratos;

/**
 *
 * @author jcinform
 */
public class Pendientes implements Comparable {

    public void Pendientes() {
    }
    String factura;
    String telefono;
    Date fecha, fechapago,emision;
    BigDecimal total, valorabonoefe, valorabonoche, valorabonotar, valorabonodep, valorabonodes, valorabonoban;
    BigDecimal enplanes,enequipos,eninstalaciones;
    BigDecimal valorabonoret, subtotal,iva;
    BigDecimal valorabonotra, valorabonoriva, valorabonorfue, valorabonotot;
    String numerotransferencia, numeroretencion;
    BigDecimal saldo;
    Clientes cliente;
    Contratos contratos;
    Integer noabono;
    String plan;
    String contrato;
    String formapago, notarjeta, nocheque, nocuenta;
    String direccion;
    String empleado;

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

    public BigDecimal getValorabonodep() {
        return valorabonodep;
    }

    public void setValorabonodep(BigDecimal valorabonodep) {
        this.valorabonodep = valorabonodep;
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

    public BigDecimal getValorabonodes() {
        return valorabonodes;
    }

    public void setValorabonodes(BigDecimal valorabonodes) {
        this.valorabonodes = valorabonodes;
    }

    public BigDecimal getValorabonorfue() {
        return valorabonorfue;
    }

    public void setValorabonorfue(BigDecimal valorabonorfue) {
        this.valorabonorfue = valorabonorfue;
    }

    public BigDecimal getValorabonoriva() {
        return valorabonoriva;
    }

    public void setValorabonoriva(BigDecimal valorabonoriva) {
        this.valorabonoriva = valorabonoriva;
    }

    public BigDecimal getValorabonotot() {
        return valorabonotot;
    }

    public void setValorabonotot(BigDecimal valorabonotot) {
        this.valorabonotot = valorabonotot;
    }

    public BigDecimal getValorabonotra() {
        return valorabonotra;
    }

    public void setValorabonotra(BigDecimal valorabonotra) {
        this.valorabonotra = valorabonotra;
    }

    public String getNumeroretencion() {
        return numeroretencion;
    }

    public void setNumeroretencion(String numeroretencion) {
        this.numeroretencion = numeroretencion;
    }

    public String getNumerotransferencia() {
        return numerotransferencia;
    }

    public void setNumerotransferencia(String numerotransferencia) {
        this.numerotransferencia = numerotransferencia;
    }

    public BigDecimal getValorabonoban() {
        return valorabonoban;
    }

    public void setValorabonoban(BigDecimal valorabonoban) {
        this.valorabonoban = valorabonoban;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public BigDecimal getValorabonoret() {
        return valorabonoret;
    }

    public void setValorabonoret(BigDecimal valorabonoret) {
        this.valorabonoret = valorabonoret;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public Contratos getContratos() {
        return contratos;
    }

    public void setContratos(Contratos contratos) {
        this.contratos = contratos;
    }

    public BigDecimal getEnequipos() {
        return enequipos;
    }

    public void setEnequipos(BigDecimal enequipos) {
        this.enequipos = enequipos;
    }

    public BigDecimal getEninstalaciones() {
        return eninstalaciones;
    }

    public void setEninstalaciones(BigDecimal eninstalaciones) {
        this.eninstalaciones = eninstalaciones;
    }

    public BigDecimal getEnplanes() {
        return enplanes;
    }

    public void setEnplanes(BigDecimal enplanes) {
        this.enplanes = enplanes;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Date getEmision() {
        return emision;
    }

    public void setEmision(Date emision) {
        this.emision = emision;
    }

   

    @Override
    public int compareTo(Object o) {
        Pendientes persona = (Pendientes) o;
        if (this.factura.compareToIgnoreCase(persona.factura) == 0) {
            return 0;
        } else {
             return this.factura.compareToIgnoreCase(persona.factura);
        }
        

    }
}
