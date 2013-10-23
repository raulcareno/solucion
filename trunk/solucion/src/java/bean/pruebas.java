/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.zul.Decimalbox;

/**
 *
 * @author Ismael Jadan
 */
public class pruebas {

    /**
     * @param args the command line arguments
     */
    public Double redondear(Double numero, int decimales) {
        try {

            BigDecimal d = new BigDecimal(numero + "");
            d = d.setScale(decimales, RoundingMode.HALF_UP);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static void main(String[] args) {
        String punto = "8.5";
        String coma = "8,5";
        int tipo = 0;
        String separador = "";
           try {
            Decimalbox x = new Decimalbox();
               System.out.println(""+x.getAttribute("Locale"));
            x.setFormat("###,###.##");
            Double v = 99000/4.7d;
            x.setValue(new BigDecimal(v)); 
               System.out.println(""+x.getValue());
            //Decimalbox x = new Decimalbox(coma);
            tipo = 2;
            separador = ",";
        } catch (Exception e) {
            System.out.println("no es coma "+e);
        }
           try {
            Decimalbox x = new Decimalbox();
            x.setFormat("###,###.##");
            x.setValue(new BigDecimal("5.2")); 
            tipo = 1;
            separador = ".";
        } catch (Exception e) {
            System.out.println("no es punto "+e);
        }
    

        System.out.println("" + (tipo == 1 ? "es punto" : "es coma") + " " + separador);

        if (true) {
            return;
        }


        try {
            InetAddress ping;
            String ip = "192.168.10.1"; // Ip de la máquina remota 
            int sum = 0;
            ping = InetAddress.getByName(ip);
            while (sum >= 0) {


                if (ping.isReachable(5000)) {
                    System.out.println(ip + " - responde..!");
                } else {
                    System.out.println(ip + " - no responde!");
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(pruebas.class.getName()).log(Level.SEVERE, null, ex);
                }
                sum += 1000;
                if (sum == 5000) {
                    try {
                        System.gc();
                        System.out.println("");
                        Thread.sleep(5000);
                        sum = 0;
                        System.gc();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(pruebas.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.gc();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
//        Long i=Long.parseLong("001EEEA7",10);
//        System.out.println("DECIMALE: "+i);
//        pruebas p = new pruebas();
//        System.out.println(""+p.redondear(8.255, 2));
//          double siete = 7.44;
//          double ocho = 8.255;
//          System.out.println(""+siete*1);
//          System.out.println(""+ocho*1);
//          BigDecimal d = new BigDecimal(7.43+"");
//          System.out.println(""+d);
//            //BigDecimal d = new BigDecimal(resultado);
//            //d = d.setScale(2, RoundingMode.DOWN);
//            d= d.setScale(2, BigDecimal.ROUND_DOWN);
//          System.out.println(""+d);


        // TODO code application logic here
//        Double d = new Double("00");
//        System.out.println(""+d);
//        matriculasBean b = new matriculasBean();
//        Periodo per = new Periodo();
//        Institucion ins = new Institucion();
//        ins.setFotos("F:\\");
//        per.setInstitucion(ins);
//        per.setDescripcion("2010-2011");
//        
//        b.generar(per);

        /*
         * SELECT 10370,esp.codigo,esp.descripcion,1,52,numeroacta,e.cedula,
         * e.apellido, e.nombre,e.genero,n.nota7 FROM notasacta n, matriculas m,
         * estudiantes e, cursos c, GLOBAL esp WHERE m.estudiante = e.codigoest
         * AND m.codigomat = n.matricula AND m.curso = c.codigocur AND
         * c.especialidad = esp.codigo AND n.numeroacta >0 AND n.matricula IN (
         * SELECT codigomat FROM matriculas WHERE curso IN ( SELECT codigocur
         * FROM cursos WHERE periodo = 4 AND secuencia = 13) AND suspenso = TRUE
         * AND perdio = FALSE AND estado NOT IN
         * ('Inscrito','Retirado','Anulado') ) ORDER BY esp.codigo,
         * n.numeroactapeaje
         */
    }
}
