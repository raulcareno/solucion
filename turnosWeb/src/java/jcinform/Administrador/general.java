/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jcinform.Administrador;

import java.util.Date;

/**
 *
 * @author geovanny
 */
public class general {
    
    public general() {
        
    }
    public String codigo;
    public String empleado;
    public String nombre;
    public String ventanilla;
    public String tipoVentanilla;
    public Integer ventanillacod;
    public Long cantidad;
    public String codigo2;
    private Date fechasolicitado;
    private Date fechaatendido;
    private Date fechallamada;
    private Date diferencia;
    private Date horas;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo2() {
        return codigo2;
    }

    public void setCodigo2(String codigo2) {
        this.codigo2 = codigo2;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString() {
        return codigo2 +"   | "+nombre;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }

    public Date getDiferencia() {
        return diferencia;
    }

    public void setDiferencia(Date diferencia) {
        this.diferencia = diferencia;
    }

    public Date getFechaatendido() {
        return fechaatendido;
    }

    public void setFechaatendido(Date fechaatendido) {
        this.fechaatendido = fechaatendido;
    }

    public Date getFechallamada() {
        return fechallamada;
    }

    public void setFechallamada(Date fechallamada) {
        this.fechallamada = fechallamada;
    }

    public Date getFechasolicitado() {
        return fechasolicitado;
    }

    public void setFechasolicitado(Date fechasolicitado) {
        this.fechasolicitado = fechasolicitado;
    }

    public Date getHoras() {
        return horas;
    }

    public void setHoras(Date horas) {
        this.horas = horas;
    }

    public String getVentanilla() {
        return ventanilla;
    }

    public void setVentanilla(String ventanilla) {
        this.ventanilla = ventanilla;
    }

    public Integer getVentanillacod() {
        return ventanillacod;
    }

    public void setVentanillacod(Integer ventanillacod) {
        this.ventanillacod = ventanillacod;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getTipoVentanilla() {
        return tipoVentanilla;
    }

    public void setTipoVentanilla(String tipoVentanilla) {
        this.tipoVentanilla = tipoVentanilla;
    }
    

}
