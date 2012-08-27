/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author Geovanny
 */
public class pruebasBorrar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         HashSet correosH = new HashSet();
          
         
        correosH.add("a");
        correosH.add("b");
        correosH.add("c");
        correosH.add("a");
        correosH.add("b");
        correosH.add("b");
        correosH.add("b");
        correosH.add("b");
        correosH.add("b");
 
        System.out.println(" - Lista de mandado con " + correosH.size() + " elementos");

        for( Iterator it = correosH.iterator(); it.hasNext();) { 
	    String x = (String)it.next();
	    System.out.println(" : " + x);

	}
    }
}
