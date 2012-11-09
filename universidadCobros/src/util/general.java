/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author inform
 */
public class general {
    Integer codigo;
    String codigoString="";
    String descripcion="";

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
    
    
    
}
