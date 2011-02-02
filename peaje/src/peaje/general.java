/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peaje;

/**
 *
 * @author geovanny
 */
public class general {
    
    public general() {
        
    }
    public String codigo;
    public String nombre;
    public String codigo2;

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

}
