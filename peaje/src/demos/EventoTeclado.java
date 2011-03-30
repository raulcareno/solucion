/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package demos;

/**
 *
 * @author Familia Jadan Cahueñ
 */
import java.awt.event.*;
import javax.swing.*;

class EventoTeclado extends JFrame implements KeyListener{

    public EventoTeclado(){
        super("EventoTeclado");
        this.addKeyListener(this);
        this.setVisible(true);
    }

    public static void main(String[] arg){
        new EventoTeclado();
    }

    public void keyReleased(KeyEvent e){
    }

    public void keyPressed(KeyEvent e){
        int teclaPresionada=e.getKeyCode();
        System.out.println("Tecla Presionada: code: "+teclaPresionada+
         " char:"+ e.getKeyChar());
    }

    public void keyTyped(KeyEvent e){
    }
}  
