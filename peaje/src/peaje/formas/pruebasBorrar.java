/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peaje.formas;

import java.util.Date;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.Minutes;

/**
 *
 * @author geovanny
 */
public class pruebasBorrar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        claves c = new claves();
        System.out.println(""+c.encriptar("root"));
        System.out.println(""+c.desencriptar(c.encriptar("root")));
//        Date fecIn = new Date();
//        Date fecIn3 = new Date();
//        Date fechaO = new Date();
//
//        LocalTime horaIni = new LocalTime(new DateTime(fecIn));
//        System.out.println("INI "+horaIni);
//        LocalTime horaFin = new LocalTime(new DateTime(fecIn3));
//        System.out.println("FIN "+horaFin);
//        LocalTime ahora = new LocalTime(new DateTime(fechaO));
//        System.out.println("AHORA "+ahora);
//
//        if((ahora.compareTo(horaIni) > 0 || ahora.compareTo(horaIni)== 0 ) && (ahora.compareTo(horaFin) < 0 || ahora.compareTo(horaFin) == 0  )){
//            System.out.println("ESTA EN EL RANGO");
//        }
//
//
        
        
        
    }

}
