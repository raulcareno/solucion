/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author inform
 */
public class pruebaGenerar {
public Double truncar(Double numero, int decimales) {
        try {
            BigDecimal d = new BigDecimal(numero);
            d = d.setScale(decimales,java.math.BigDecimal.ROUND_DOWN );
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //generarArchivoActas gen = new generarArchivoActas();
        //gen.todos();
        pruebaGenerar p = new pruebaGenerar();
        System.out.println(" "+p.truncar(3.456, 2));
        System.out.println(" "+p.truncar(3.9999999, 2));
        System.out.println(" "+p.truncar(3.43, 2));
        
    }
}
