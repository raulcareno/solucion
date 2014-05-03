/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peaje.formas;

   import java.util.logging.Level;
    import java.util.logging.Logger;
    public class pruebasBorrar {
        public static void main(String args[]) {
//            Monitor monitor = new Monitor();
//            Escribir hola = new Escribir("hola", 0, monitor);
//            hola.start();
//            Escribir adios = new Escribir("adiós", 1, monitor);
//           adios.start();
            claves c = new claves();
            System.out.println(""+c.encriptar("peaje@2011"));
            System.out.println(""+c.desencriptar("O1ZzXv0Dhac="));
       }
   }
   class Escribir extends Thread {
       private Monitor monitor;
       private String texto;
       private int orden;
       public Escribir(String texto, int orden, Monitor monitor) {
           this.texto = texto;
           this.monitor = monitor;
           this.orden = orden;
       }
       @Override
       public void run() {
           for (int i = 0; i < 10; i++) {
               try {
                   monitor.escribir(texto + " ", orden);
                   sleep((int) (Math.random() * 1000));
               } catch (InterruptedException ex) {
                   Logger.getLogger(pruebasBorrar.class.getName()).log(Level.SEVERE, null, ex);
               }
           }
      }
  }
   class Monitor {
     private int actual = 0;
      public synchronized void escribir(String texto, int orden) {
        while (orden != actual) {
               try {
                wait();
             } catch (InterruptedException ex) {
                  Logger.getLogger(pruebasBorrar.class.getName()).log(Level.SEVERE, null, ex);
              }
           }
           System.out.print(texto);
           actual = (actual + 1) % 2;
           notifyAll();
       }
   }