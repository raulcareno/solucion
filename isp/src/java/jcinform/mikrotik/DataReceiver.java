/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.mikrotik;

/**
 *
 * @author inform
 */
 
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 /**
  *
  * @author janisk
  */
 public class DataReceiver extends Thread {
 
   private ApiConn aConn = null;
   NewJFrame t3A = null;
 
   public DataReceiver(ApiConn  aConn, NewJFrame t3A) {
       this.aConn = aConn;
       this.t3A = t3A;
   }
 
   @Override
   public void run() {
       String s = "";
       while (true) {
           try {
               s = aConn.getData();
               if (s != null) {
                   t3A.salida(s);
                   if (s.contains("!done")) {
                   }
               }
           } catch (InterruptedException ex) {
               Logger.getLogger(DataReceiver.class.getName()).log(Level.SEVERE, null, ex);
           }
       }
   }
 }
