/*
 * AccionEnviar.java
 *
 * Created on 30 de noviembre de 2007, 07:56 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package camaraIP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author olga
 */
class AccionEnviar{
    
    private char intruc;
    private DataOutputStream salida;
    private String login;
    
    public AccionEnviar(Socket s, char intrucs){
        intruc = intrucs;
        
        try {
            salida = new DataOutputStream(s.getOutputStream());
           
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    public void send() {    
        try {
            salida.writeChar( intruc);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}