/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peaje.formas;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author JcinformLocal
 */
public class pruebasMemoria {

    private static void insert() {
        List<String> v1 = new ArrayList();
        List<String> v2 = new ArrayList();
        List<String> v3 = new ArrayList();
        String str = "Hello";
        for (long i = 0L; i <= 999999; i++) {
            v1.add(str + i);
            v2.add(str + i);
            v3.add(str + i);
        }
    }

    private static void startGC() {
        System.gc();
    }

    private static long mbUsed() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024 / 1024;
    }

    public static void main(String... args) {
        startGC();
        System.out.printf("Memory used before insert %,d MB%n", mbUsed());
        insert();
        System.out.printf("Memory used after insert %,d MB%n", mbUsed());
        startGC();
        startGC();
        startGC();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(pruebasMemoria.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.printf("Memory used after GC %,d MB%n", mbUsed());


//        startGC();
//        startGC();
//        System.out.printf("Memory used after GC %,d MB%n", mbUsed());
    }
    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        // TODO code application logic here
//    }
}
