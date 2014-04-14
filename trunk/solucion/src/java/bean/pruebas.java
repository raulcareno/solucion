/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public Double redondear2(Double numero, int decimales) {
        try {


            BigDecimal d = new BigDecimal(numero);
            d = d.setScale(decimales, RoundingMode.HALF_UP);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

//    Double promedio(Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23, Double va24, Double va25, Double va26, Double va27, Double va28, Double va29, Double va30, Double va31, Double va32) {
//        int cont = 0;
//        if (va1 > 0) {
//            cont++;
//        }
//        if (va2 > 0) {
//            cont++;
//        }
//        if (va3 > 0) {
//            cont++;
//        }
//        if (va4 > 0) {
//            cont++;
//        }
//        if (va5 > 0) {
//            cont++;
//        }
//        if (va6 > 0) {
//            cont++;
//        }
//        if (va7 > 0) {
//            cont++;
//        }
//        if (va8 > 0) {
//            cont++;
//        }
//        if (va9 > 0) {
//            cont++;
//        }
//        if (va10 > 0) {
//            cont++;
//        }
//        if (va11 > 0) {
//            cont++;
//        }
//        if (va12 > 0) {
//            cont++;
//        }
//        if (va13 > 0) {
//            cont++;
//        }
//        if (va14 > 0) {
//            cont++;
//        }
//        if (va15 > 0) {
//            cont++;
//        }
//        if (va16 > 0) {
//            cont++;
//        }
//        if (va17 > 0) {
//            cont++;
//        }
//        if (va18 > 0) {
//            cont++;
//        }
//        if (va19 > 0) {
//            cont++;
//        }
//        if (va20 > 0) {
//            cont++;
//        }
//        if (va21 > 0) {
//            cont++;
//        }
//        if (va22 > 0) {
//            cont++;
//        }
//        if (va23 > 0) {
//            cont++;
//        }
//        if (va24 > 0) {
//            cont++;
//        }
//        if (va25 > 0) {
//            cont++;
//        }
//        if (va26 > 0) {
//            cont++;
//        }
//        if (va27 > 0) {
//            cont++;
//        }
//        if (va28 > 0) {
//            cont++;
//        }
//        if (va29 > 0) {
//            cont++;
//        }
//        if (va30 > 0) {
//            cont++;
//        }
//        if (va31 > 0) {
//            cont++;
//        }
//        if (va32 > 0) {
//            cont++;
//        }
//        if (cont == 0) {
//            cont = 1;
//        }
//        return (va1 + va2 + va3 + va4 + va5 + va6 + va7 + va8 + va9 + va10 + va11 + va12 + va13 + va14 + va15 + va16 + va17 + va18 + va19 + va20 + va21 + va22 + va23 + va24 + va25 + va26 + va27 + va28 + va29 + va30 + va31 + va32) / cont;
//    }
//
//    BigDecimal promedio2(Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23, Double va24, Double va25, Double va26, Double va27, Double va28, Double va29, Double va30, Double va31, Double va32) {
//        int cont = 0;
//        if (va1 > 0) {
//            cont++;
//        }
//        if (va2 > 0) {
//            cont++;
//        }
//        if (va3 > 0) {
//            cont++;
//        }
//        if (va4 > 0) {
//            cont++;
//        }
//        if (va5 > 0) {
//            cont++;
//        }
//        if (va6 > 0) {
//            cont++;
//        }
//        if (va7 > 0) {
//            cont++;
//        }
//        if (va8 > 0) {
//            cont++;
//        }
//        if (va9 > 0) {
//            cont++;
//        }
//        if (va10 > 0) {
//            cont++;
//        }
//        if (va11 > 0) {
//            cont++;
//        }
//        if (va12 > 0) {
//            cont++;
//        }
//        if (va13 > 0) {
//            cont++;
//        }
//        if (va14 > 0) {
//            cont++;
//        }
//        if (va15 > 0) {
//            cont++;
//        }
//        if (va16 > 0) {
//            cont++;
//        }
//        if (va17 > 0) {
//            cont++;
//        }
//        if (va18 > 0) {
//            cont++;
//        }
//        if (va19 > 0) {
//            cont++;
//        }
//        if (va20 > 0) {
//            cont++;
//        }
//        if (va21 > 0) {
//            cont++;
//        }
//        if (va22 > 0) {
//            cont++;
//        }
//        if (va23 > 0) {
//            cont++;
//        }
//        if (va24 > 0) {
//            cont++;
//        }
//        if (va25 > 0) {
//            cont++;
//        }
//        if (va26 > 0) {
//            cont++;
//        }
//        if (va27 > 0) {
//            cont++;
//        }
//        if (va28 > 0) {
//            cont++;
//        }
//        if (va29 > 0) {
//            cont++;
//        }
//        if (va30 > 0) {
//            cont++;
//        }
//        if (va31 > 0) {
//            cont++;
//        }
//        if (va32 > 0) {
//            cont++;
//        }
//        if (cont == 0) {
//            cont = 1;
//        }
//        return (new BigDecimal(va1 + "")).add(new BigDecimal(va2 + "")).add(new BigDecimal(va3 + "")).add(new BigDecimal(va4 + "")).add(new BigDecimal(va5 + "")).add(new BigDecimal(va6 + "")).add(new BigDecimal(va7 + "")).add(new BigDecimal(va8 + "")).add(new BigDecimal(va9 + "")).add(new BigDecimal(va10 + "")).add(new BigDecimal(va11 + "")).add(new BigDecimal(va12 + "")).add(new BigDecimal(va13 + "")).add(new BigDecimal(va14 + "")).add(new BigDecimal(va15 + "")).add(new BigDecimal(va16 + "")).add(new BigDecimal(va17 + "")).add(new BigDecimal(va18 + "")).add(new BigDecimal(va19 + "")).add(new BigDecimal(va20 + "")).add(new BigDecimal(va21 + "")).add(new BigDecimal(va22 + "")).add(new BigDecimal(va23 + "")).add(new BigDecimal(va24 + "")).add(new BigDecimal(va25 + "")).add(new BigDecimal(va26 + "")).add(new BigDecimal(va27 + "")).add(new BigDecimal(va28 + "")).add(new BigDecimal(va29 + "")).add(new BigDecimal(va30 + "")).add(new BigDecimal(va31 + "")).add(new BigDecimal(va32)).divide(new BigDecimal(cont), 5, RoundingMode.HALF_UP);
//    }
    public static Double truncar(Double numero, int decimales) {
        try {
            java.math.BigDecimal d = new java.math.BigDecimal(numero + "");
            d = d.setScale(decimales, java.math.BigDecimal.ROUND_DOWN);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }
//    public static Double promedio(Double... bar) {
//        int cont = 0;
//        BigDecimal total = new BigDecimal("0");
//        for (Double valor : bar) {
//            if (valor > 0) {
//                cont++;
//            }
//            total = total.add(new BigDecimal(valor + ""));
//        }
//        total = total.divide(new BigDecimal(cont), 5, RoundingMode.HALF_UP);
//        return total.doubleValue();
//    }
    String prom1 = " public static Double promedio(Double... bar) {         int cont = 0;"
            + "        BigDecimal total = new BigDecimal(\"0\");         for (Double valor : bar) {            if (valor > 0) {"
            + "                cont++;             }             total = total.add(new BigDecimal(valor + \"\"));         } "
            + "        total = total.divide(new BigDecimal(cont), 5, RoundingMode.HALF_UP);         return total.doubleValue();    }";
    String prom2 = " public static Double promedioSimple(Double... bar) {         int cont = 0;"
            + "        BigDecimal total = new BigDecimal(\"0\");         for (Double valor : bar) {         "
            + "        total = total.add(new BigDecimal(valor + \"\"));         } "
            + "        total = total.divide(new BigDecimal(cont), 5, RoundingMode.HALF_UP);         return total.doubleValue();    }";

    public static Double promedioSimple(Double... bar) {
        int cont = 0;
        BigDecimal total = new BigDecimal("0");
        for (Double valor : bar) {
            cont++;
            total = total.add(new BigDecimal(valor + ""));
        }
        total = total.divide(new BigDecimal(cont), 5, RoundingMode.HALF_UP);
        return total.doubleValue();
    }
//  
    static String eva = "public double promedio(Object... bar) {"
            + "         int cont = 0; "
            + "        BigDecimal total = new BigDecimal(\"0\"); "
            + "         for (Double valor : bar) { "
            + "            if (valor > 0) {"
            + "                cont++; "
            + "             } "
            + "            total = total.add(new BigDecimal(valor + \"\")); "
            + "         } "
            + "        total = total.divide(new BigDecimal(cont), 5, RoundingMode.HALF_UP); "
            + "         return total.doubleValue();  "
            + "    } ";
//    int cont = 0;

private static BigDecimal truncate(final String text) {
    BigDecimal bigDecimal = new BigDecimal(text);
    if (bigDecimal.scale() > 2)
        bigDecimal = new BigDecimal(text).setScale(2, RoundingMode.HALF_UP);
    return bigDecimal.stripTrailingZeros();
}

private static void check(final BigDecimal bigDecimal, final String string) {
    if (!bigDecimal.toString().equals(string))
        throw new IllegalStateException("not equal: " + bigDecimal + " and " + string);

}
    public static void main(String[] args) {
        try {

            metodos met = new metodos();
            Double a1 = 8.7;
            Double b1 = 6.9;
            Double c1 = 7.02;
            pruebas p = new pruebas();
            
            System.out.println(""+pruebas.truncar(7.8599999999999999999999999999999999999, 2));
            System.out.println(""+pruebas.truncar(7.8589999999999999999999999999999999999, 2));
            System.out.println(""+pruebas.truncar(7.8579999999999999999999999999999999999, 2));
            System.out.println(""+pruebas.truncar(7.8569999999999999999999999999999999999, 2));
            System.out.println(""+pruebas.truncar(7.8559999999999999999999999999999999999, 2));
            System.out.println(""+pruebas.truncar(7.8549999999999999999999999999999999999, 2));
            System.out.println(""+pruebas.truncar(7.8539999999999999999999999999999999999, 2));
             
            BigDecimal bb = new BigDecimal("7.8579999999999999999999999999999999999");
            DecimalFormat df = new DecimalFormat("#.######");
            System.out.println("a. "+(df.format(bb)));

 check(truncate("12.000000"), "12");
    check(truncate("12.0001"), "12");
    check(truncate("12.0051"), "12.01");
    check(truncate("12.99"), "12.99");
    check(truncate("12.999"), "13");
    check(truncate("12.3456"), "12.35");
    System.out.println("if we see this message without exceptions, everything is ok");
//            Interpreter inter = new Interpreter();
//
////            inter.eval(eva);
////            inter.eval("bean.metodos met = new bean.metodos();");
////            inter.eval("met.promedio(new Double(2), new Double(4))");
//            int variables = 35;
//
//
//            for (int k = 1; k <= variables; k++) {
//                String codigos0 = " public Double promedio(";
//                String codigo1 = " int cont =0 ;";
//                String codigos2 = "";
//                String codigos3 = " new java.math.BigDecimal(0)";
//                for (int i = 1; i <= k; i++) {
//                    codigos0 += "Double va" + i + ",";
//                    codigos2 += " if(va" + i + ">0){cont++;}";
//                    codigos3 += ".add(new java.math.BigDecimal(va" + i + "+\"\"))";
//                }
//                codigos0 = codigos0.substring(0, codigos0.length() - 1) + "){";
//                codigos3 = "return (" + codigos3 + ").divide(new BigDecimal(cont), 5, RoundingMode.HALF_UP).doubleValue(); }";
//
//                System.out.println("" + codigos0 + codigo1 + codigos2 + codigos3);
//                //System.out.println("" + codigos0.replace("promedio", "promedioSimple") + codigo1 + codigos2 + codigos3);
//            }

//               Double promedio(Double va1, Double va2) {
//                    int cont = 0;
//                    if (va1 > 0) {
//                        cont++;
//                    }
//                   
//                   
//                    return (new BigDecimal(va1).add(new BigDecimal(va2))).divide(new BigDecimal(cont), 5, RoundingMode.HALF_UP).doubleValue();
//                }

//            System.out.println("" + promedio(a1, b1, c1));
//            System.out.println("" + promedioSimple(a1, b1, c1, 0d));
        } catch (Exception ex) {
            Logger.getLogger(pruebas.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
