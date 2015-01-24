/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peaje.formas;

import java.util.Scanner;

/**
 *
 * @author Geovanny
 */
public class ConvertHexaToDecimal {
      
    public static String convertir(String hexadecimal){
          //int decimal = 
//        System.out.println("Convertir...: " + hexadecimal.substring(4));
            return ""+Integer.parseInt(hexadecimal.substring(4), 16);
      }

       public static String convertirComplemento(String hexadecimal){
          //int decimal = Integer.parseInt(hexadecimal.substring(0,4), 16);
//        System.out.println("Convertir..: " + hexadecimal.substring(0,4));
            return Integer.parseInt(hexadecimal.substring(0,4), 16)+"";
      }

       public static String convertirHexadecimal(int num){
              return Integer.toHexString(num);
       }
    public static void main (String args[]) {
        
       
      System.out.println("Method 1: Decimal to hexadecimal: "+convertirHexadecimal(4352));
        // Ask user to enter an Hexadecimal number in Console
//        System.out.println("ingrese un numero exadecimal: ");
//        Scanner scanner = new Scanner(System.in);
//        String  hexadecimal = scanner.next();
//         
//        
//        System.out.println(""+convertir(hexadecimal));
//        System.out.println(""+convertirComplemento(hexadecimal));
        
//          int decimal = Integer.parseInt(hexadecimal.substring(2), 16);
      
//    System.out.println("Converted Decimal number is : " + decimal);
//    decimal = Integer.parseInt(hexadecimal.substring(4), 16);
//      
//    System.out.println("Converted Decimal number is : " + decimal);
//    
//    System.out.println("2 Converted Decimal number is : " + decimal);
//    decimal = Integer.parseInt(hexadecimal.substring(0,4), 16);
//      
//    System.out.println("2 Converted Decimal number is : " + decimal);
    
//        //Converting hexa decimal number to binary in Java       
//        String binary = Integer.toBinaryString(decimal);
//        System.out.printf("Hexadecimal to Binary conversion of %s is %s %n", hexadecimal, binary );
//      
//        //Converting Hexa decimal number to Decimal in Java
//        int decimal = Integer.parseInt(hexadecimal, 16);
//      
//        System.out.println("Converted Decimal number is : " + decimal);
//    
//        //Converting hexa decimal number to binary in Java       
//        String binary = Integer.toBinaryString(decimal);
//        System.out.printf("Hexadecimal to Binary conversion of %s is %s %n", hexadecimal, binary );
//      
//        // Converting Hex String to Octal in Java
//        String octal = Integer.toOctalString(decimal);
//        System.out.printf("Hexadecimal to Octal conversion of %s is %s %n", hexadecimal, octal );
    }
}
