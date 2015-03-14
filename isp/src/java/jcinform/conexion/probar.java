/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jcinform.conexion;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.MessagingException;

/**
 *
 * @author Ismael Jadan
 */
public class probar {

    /**
     * @param args the command line arguments
     */
    public probar(){
        
    }
    public void llamar(){
        try {
            String FROM = "agendasoporte@csed.ne.ec";
          String TO = "geovanny.jadan@outlook.com"; 
          Mail mail = new Mail();
          mail.setTo(TO);
          mail.setHtml(true);
          mail.setSubject("Prueba");
          mail.setBody("HOLA BUBUSA"+new Date());
          mail.setFrom(FROM);
          
          
          NuevosCertificadosSMTP s = new NuevosCertificadosSMTP();
          MailConfiguration mc = new MailConfiguration();
           mc.setAuth("true");
           mc.setEnableStartTLS("true");
           mc.setHost("mail.csed.net.ec");
           mc.setPassword("csednet77");
           mc.setPort("587");
           mc.setUser("agendasoporte@csed.net.ec");
           s.send(mail,mc);
        } catch (MessagingException ex) {
            ex.printStackTrace();
            Logger.getLogger(probar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     

}
