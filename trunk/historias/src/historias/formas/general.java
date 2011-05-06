/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package historias.formas;

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
                String codig2 = getCodigo2();
                while(codig2.length()<3){
                    codig2 = "0"+codig2;
                }
        return codig2 +"   | "+nombre;
    }

}
