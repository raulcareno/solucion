/*
 * crea_sock.java
 *
 * Created on 30 de noviembre de 2007, 11:37 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package camaraIP;

import java.io.IOException;
import java.net.Socket;
import java.lang.Integer;
import java.net.UnknownHostException;
/**
 *
 * @author olga
 */

/** Clase que crea el socket para establecer la comunicación TCP con la MSP*/
public class crea_sock {

    Integer sockete;
    int sokete;
    String so;
    String IP;

    private Socket soket;

    private Socket socket;
    
        
    public crea_sock() {
      
    }
    
    public Socket  crea(String ip, String so)  {
        
         IP = ip;
        sockete = Integer.parseInt( so);
        //sokete  = sockete.intValue();
        try {
            socket  = new Socket(IP, sockete);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return (socket);
        
    }
}
