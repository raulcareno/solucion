/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.math.BigDecimal;

/**
 *
 * @author inform
 */
public class general {
    Integer codigo;
    String codigoString="";
    String descripcion="";
    BigDecimal valor=new BigDecimal("0");

    public general() {
    }

    public general(String codigoString, String descripcion) {
        this.codigoString = codigoString;
        this.descripcion = descripcion;
    }
    

    public general(Integer codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }
     public general(Integer codigo, String descripcion,BigDecimal valor) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.valor = valor;
    }


    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigoString() {
        return codigoString;
    }

    public void setCodigoString(String codigoString) {
        this.codigoString = codigoString;
    }
    

    @Override
    public String toString() {
        return ""+descripcion ;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
    
    
}
