/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Geovanny Jadan
 */
public class PruebasTareas {
    
   static TimerTask timerTask = new TimerTask(){
         public void run() 
         {
             // Aquí el código que queremos ejecutar.
             System.out.println("SE EJECUTO");
         }
     };
    public static void main(String[] args) {
         
      // Aquí se pone en marcha el timer cada segundo.
     Timer timer = new Timer();
     // Dentro de 0 milisegundos avísame cada 1000 milisegundos
     Date fecha = new Date();
     //fecha.setDate(fecha.getDate()+1);
     timer.scheduleAtFixedRate(timerTask, fecha, 86400); 
    }
}
