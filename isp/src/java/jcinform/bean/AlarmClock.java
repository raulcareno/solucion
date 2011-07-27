package jcinform.bean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Familia Jadan Cahueñ
 */
import java.awt.Toolkit;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.tiling.scheduling.Scheduler;
import org.tiling.scheduling.SchedulerTask;
import org.tiling.scheduling.examples.iterators.DailyIterator;

public class AlarmClock {

    private final Scheduler scheduler = new Scheduler();
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm:ss.SSS");
    private final int hourOfDay, minute, second;

    public AlarmClock(int hourOfDay, int minute, int second) {
        this.hourOfDay = hourOfDay;
        this.minute = minute;
        this.second = second;
    }

    public void start() {
        scheduler.schedule(new SchedulerTask() {
            public void run() {
                try {
                    soundAlarm();
                } catch (InterruptedException ex) {
                    Logger.getLogger(AlarmClock.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            private void soundAlarm() throws InterruptedException {
                for (int i = 0; i < 10; i++) {
                    System.out.println("Wake up! " +   "It's " + dateFormat.format(new Date()));
                    Thread.sleep(1000);
                    Toolkit.getDefaultToolkit().beep();    
                }
                for (int i = 0; i < 10; i++) {
                    System.out.println("Wake up! " +   "It's " + dateFormat.format(new Date()));
                    Thread.sleep(1000);
                    Toolkit.getDefaultToolkit().beep();    
                }
                 
                
                // Start a new thread to sound an alarm...
            }
        },  new DailyIterator(hourOfDay, minute, second));
         
    }

    public static void main(String[] args) {
        
        AlarmClock alarmClock = new AlarmClock(22, 04, 30);
        alarmClock.start();
    }
}