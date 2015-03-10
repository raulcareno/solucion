/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

import java.util.Date;
import jcinform.persistencia.Soporte;
import org.joda.time.DateMidnight;

/**
 *
 * @author inform
 */
public class borrar {

    public static void main(String[] args) {
        String val = "51-1";
        System.out.println("" + val.indexOf("-"));

        System.out.println("" + val.substring(val.indexOf("-") + 1, val.length()));
        email e = new email();
        e.soporteTecnico("1714846043",new Soporte(4));
        //GregorianCalendar now = new GregorianCalendar();
//        Date desde = new Date();
//        desde.setYear(desde.getYear()-1);
//        Date fe = new Date();
//        DateMidnight jodafec1 = new DateMidnight(fe.getYear()+1900, fe.getMonth()+1, fe.getDate());
//        DateMidnight f =  jodafec1.minusMonths(3);
//        Date fee = f.toDate();
//         if(desde.compareTo(fee)==0){
//             System.out.println("igual");
//             return true;
//         }else if(desde.compareTo(fee)>0){
//             System.out.println("mayor");
//             return true;
//         }else if(desde.compareTo(fee)<0){
//             System.out.println("menor");
//             return false;
//         }
 

    }   
}
