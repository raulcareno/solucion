/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

/**
 *
 * @author inform
 */
public class borrar {
    public static void main(String[] args) {
     String val = "51-1";
        System.out.println(""+val.indexOf("-"));
        
        System.out.println(""+val.substring(val.indexOf("-")+1, val.length()));
     
    }
}
