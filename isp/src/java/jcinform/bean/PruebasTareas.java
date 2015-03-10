/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

import java.util.TimerTask;
import org.zkoss.zhtml.Iframe;
import org.zkoss.zk.ui.Executions;

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
//     Timer timer = new Timer();
//     // Dentro de 0 milisegundos avísame cada 1000 milisegundos
//     Date fecha = new Date();
//     //fecha.setDate(fecha.getDate()+1);
//     timer.scheduleAtFixedRate(timerTask, fecha, 86400); 
//     
//     String url = "http://www.bing.com/maps/?v=2&cp=0.131410~-79.056080&lvl=14&sty=r&form=LMLTCC";
     Iframe f = new Iframe();
     //Executions.getCurrent().getNativeRequest().toString();
//     f.getPage().GET
//     f.getPage().getRequestPath()
////        f.getChildren().get(0)
    }
}
