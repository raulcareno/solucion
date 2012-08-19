/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.Applet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Geovanny
 */
public class Dibuja {
 
    public static void rectangulo(Graphics g,int x, int y, int a, int l,Color color,String texto,String porcentaje){
        
        Graphics2D g2d = ( Graphics2D ) g;  // convertir g a Graphics2D
          g2d.setPaint(Color.white); 
          g2d.fill( new Rectangle2D.Double( x, y-16, 172, 295 ));
//          g2d.setPaint(Color.GRAY);  
//          g2d.setStroke( new BasicStroke( 1.0f ) ); 
//          g2d.draw( new Rectangle2D.Double( x, y-16, 172, 296*4) );
//          g2d.setPaint(Color.BLACK);
        // dibujar elipse 2D rellena con un gradiente azul-amarillo
        g2d.setPaint( new GradientPaint( x+5, y, Color.white, 35, l, color, true ) );  
        g2d.fill( new Rectangle2D.Double( x+5, y, 65, l ));
        g2d.setPaint(Color.lightGray);  
        g2d.setStroke( new BasicStroke( 1.0f ) ); 
        g2d.draw( new Rectangle2D.Double( x+5, y, 66, l+1 ) );
        g2d.setPaint(Color.BLACK);
        
        g2d.setStroke( new BasicStroke( 2.0f ) ); 
        g2d.drawString(texto, x+8, y+l+15);
        
        g2d.setStroke( new BasicStroke( 2.0f ) ); 
        g2d.drawString(porcentaje, x+8, y+l);
 
           
          
    }
}
