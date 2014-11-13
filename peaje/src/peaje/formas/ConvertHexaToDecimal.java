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
        //String  hexadecimal = scanner.next();
          int decimal = Integer.parseInt(hexadecimal.substring(4), 16);
      
//        System.out.println("Converted Decimal number is : " + decimal);
            return ""+decimal;
      }

      
    public static void main (String args[]) {
        // Ask user to enter an Hexadecimal number in Console
        System.out.println("Please enter Hexadecimal number : ");
        Scanner scanner = new Scanner(System.in);
      
        String  hexadecimal = scanner.next();
        System.out.println(""+hexadecimal.substring(4));
        System.out.println(""+hexadecimal.substring(2));
        
          int decimal = Integer.parseInt(hexadecimal.substring(2), 16);
      
    System.out.println("Converted Decimal number is : " + decimal);
    decimal = Integer.parseInt(hexadecimal.substring(4), 16);
      
    System.out.println("Converted Decimal number is : " + decimal);
    
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
