/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.*;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class validarPalabras {

    private static BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String args[]) throws IOException {

        boolean estado = true;
        while (estado) {
            System.out.println("Escribe la palabra a validar: ");
            String lectura = stdIn.readLine();
           validar(lectura);
        }
    }
  public static Boolean validar(String lectura){

            lectura = lectura.replace("á", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("ú", "u").replace("Á", "A").replace("É", "E").replace("Í", "I").replace("Ó", "O").replace("Ú", "U").replace(" ","");
            System.out.println("" + lectura);
            if (esPalabraCaracteres(lectura)) {
                System.out.println("OK PALABRA");
                return true;
            } else {
               
                System.out.println("ERROR EN PALABRA");
                return false;
            }
            
         
    }
    public static boolean esPalabraCaracteres(String palabra) {
         if(palabra.toLowerCase().contains("aa") || palabra.toLowerCase().contains("bb") || palabra.toLowerCase().contains("cc") || 
                        palabra.toLowerCase().contains("dd") ||  palabra.toLowerCase().contains("ee") || palabra.toLowerCase().contains("ff") || 
                        palabra.toLowerCase().contains("gg") || palabra.toLowerCase().contains("hh") || palabra.toLowerCase().contains("ii") || 
                        palabra.toLowerCase().contains("jj") || palabra.toLowerCase().contains("kk") || palabra.toLowerCase().contains("mm") ||
                        palabra.toLowerCase().contains("nnn") || palabra.toLowerCase().contains("ññ") || palabra.toLowerCase().contains("oo") ||
                        palabra.toLowerCase().contains("pp") || palabra.toLowerCase().contains("qq") || palabra.toLowerCase().contains("rrr") ||
                        palabra.toLowerCase().contains("sss") || palabra.toLowerCase().contains("tt") || palabra.toLowerCase().contains("uu") ||
                        palabra.toLowerCase().contains("vv") || palabra.toLowerCase().contains("ww") || palabra.toLowerCase().contains("xx") ||
                        palabra.toLowerCase().contains("yy") || palabra.toLowerCase().contains("zz")){
             return false;
             
         }
        for (int i = 0; i < palabra.length(); i++) {
            if (!((palabra.charAt(i) > 64 && palabra.charAt(i) < 91)
                    || (palabra.charAt(i) > 96 && palabra.charAt(i) < 123))) {
                return false;
            }
        }
        return true;
    }
}
