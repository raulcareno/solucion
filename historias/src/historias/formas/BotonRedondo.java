/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package historias.formas;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class BotonRedondo extends JButton {

    private Color colorFondo, colorPresionado;

    public BotonRedondo(String rotulo, Color fon, Color pre) {
        super(rotulo);
        colorFondo = fon;
        colorPresionado = pre;
        setContentAreaFilled(false);
    }

    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(colorPresionado);
        } else {
            g.setColor(colorFondo);
        }
        g.fillOval(0, 0, getSize().width - 1, getSize().height - 1);
        super.paintComponent(g);
    }

    protected void paintBorder(Graphics g) {
        g.setColor(getForeground());
        g.drawOval(0, 0, getSize().width - 1, getSize().height - 1);
    }
    Shape figura;

    public boolean contains(int x, int y) {
// En caso de que el botón cambie de tamaño, hay que conseguir una nueva
// figura que se adapte a ese nuevo tamaño
        if (figura == null || !figura.getBounds().equals(getBounds())) {
            figura = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        }
        return (figura.contains(x, y));
    }
//   public boolean contains(int x,int y){
//    if(figura==null!figura.getBounds().equals(getBounds())){
//        figura = new Ellipse2D.Float(0,0,getWidth(),getHeight());
//    }
//    return (figura.contains(x,y));
//    }

    public static void main(String args[]) {
        JFrame ventana = new JFrame("Tutorial de Java, Swing");
        ventana.getContentPane().setLayout(new FlowLayout());
        ventana.getContentPane().setBackground(Color.yellow);

        ventana.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        // Se crea el botón y se le pone fondo rojo
        JButton boton = new BotonRedondo("Boton",Color.red,Color.yellow);
        boton.setBackground(Color.red);
// Se incorpora a la ventana
        ventana.getContentPane().add(boton);
        ventana.setSize(300, 150);
        ventana.show();
    }
 
}