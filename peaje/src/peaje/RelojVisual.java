/*
 * Javier Abell�n, 10 de octubre de 2004
 * reloj.java
 */

package peaje;

import javax.swing.JLabel;
import java.util.Observer;
import java.util.Observable;
import java.awt.Dimension;
import javax.swing.SwingUtilities;
import javax.swing.SwingConstants;
import java.text.SimpleDateFormat;

/**
 * Visual para mostrar el reloj.
 * Es un JLabel que recibe un Observable de cambio de fecha.
 */
public class RelojVisual extends JLabel
 {
     /**
      * Se pasa un observable de fecha/hora. El Observable debe pasar un
      * Date a esta visual para que la presente.
      */
     public RelojVisual(Observable modelo)
     {
         this.setHorizontalAlignment((SwingConstants.CENTER));
         modelo.addObserver (new Observer ()
         {
             public void update(java.util.Observable o, Object arg) 
             {
                 final Object fecha = arg;
                  SwingUtilities.invokeLater (new Runnable()
                 {
                     public void run()
                     {

                          //setFont(new java.awt.Font("Agency FB", 1, 40)); // NOI18N
                          setFont(new java.awt.Font("DS-Digital", 1, 40)); // NOI18N
                          //setBounds(190, 100, 210, 500);
                          //#003366
                        setForeground(new java.awt.Color(255,51,51));
                        setVerticalAlignment(javax.swing.SwingConstants.CENTER);
                        setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                         setText (format.format(fecha));
                     }
                 });
             }
         });
         
         // Se da una dimension al JLabel.
           
         this.setPreferredSize(new Dimension (222, 40));

     }
     
     /**
      * Cambia el formato de presentacion de la fecha/hora en pantalla.
      */
     public void setFormat (SimpleDateFormat unFormato)
     {
         format = unFormato;
     }
     
     /**
      * Clase para mostrar una fecha/hora en formato texto.
      */
    SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
}
