/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean.reportes.jrxml;
import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class FetchMail extends TimerTask {

  /**
  * Construct and use a TimerTask and Timer.
  */
  public static void main (String... arguments ) {
    TimerTask fetchMail  = new FetchMail();

    //perform the task once a day at 4 a.m., starting tomorrow morning
    //(other styles are possible as well)
    Timer timer = new Timer();
    Date fecha = getTomorrowMorning4am();
      System.out.println("INICIO"+fecha);
    timer.scheduleAtFixedRate(fetchMail,fecha , fONCE_PER_DAY);
  }

  /**
  * Implements TimerTask's abstract run method.
  */
  public void run(){
    //toy implementation
      for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
                System.out.println(""+(i+1));
            Toolkit.getDefaultToolkit().beep();    
                
            } catch (InterruptedException ex) {
                Logger.getLogger(FetchMail.class.getName()).log(Level.SEVERE, null, ex);
            }
      }
      
       
    System.out.println("Fetching mail...");
  }

  // PRIVATE ////

  //expressed in milliseconds
  private final static long fONCE_PER_DAY = 1000*60*60*24;

  private final static int fONE_DAY = 0;
  private final static int fFOUR_AM = 15;
  private final static int fZERO_MINUTES = 31;

  private static Date getTomorrowMorning4am(){
    Calendar tomorrow = new GregorianCalendar();
    tomorrow.add(Calendar.DATE, fONE_DAY);
    Calendar result = new GregorianCalendar(
      tomorrow.get(Calendar.YEAR),
      tomorrow.get(Calendar.MONTH),
      tomorrow.get(Calendar.DATE), fFOUR_AM,
      fZERO_MINUTES
    );
    return result.getTime();
  }
}
 