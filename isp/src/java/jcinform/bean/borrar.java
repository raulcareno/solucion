/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author inform
 */
public class borrar {

    public static boolean esmultiplo(int n1, int n2) {
        if (n1 % n2 == 0) {
            return true;
        } else {
            return false;
        }

    }

    public static void main(String[] args) {
        //DEBE ESTAR MARCADA CON CERO EL DIAS GRACIA
      
   
//        BigDecimal resto;
//        BigDecimal numero1 = new BigDecimal(25.45);
//        BigDecimal numero2 = new BigDecimal(23.45);
//        System.out.println(""+numero1.remainder(BigDecimal.ONE));
//        System.out.println(""+numero2.remainder(BigDecimal.ONE));
//        resto = numero1.divide(numero2, 2, RoundingMode.HALF_UP);
//
//        if (resto.compareTo(new BigDecimal(0)) == 0) {
//            System.out.println(numero1 + " es múltiplo de " + numero2);
//        } else {
//            System.out.println(numero1 + " NO es múltiplo de " + numero2);
//        }

//
//        String val = "51-1";
//        System.out.println("" + val.indexOf("-"));
//
//        System.out.println("" + val.substring(val.indexOf("-") + 1, val.length()));
//        email e = new email();
//        e.soporteTecnico("1309700548",new Soporte(4));
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
