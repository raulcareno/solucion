/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siscontrol.mail;

/**
 *
 * @author Geovanny
 */
public class MailConfiguration {

    public MailConfiguration() {
    }

    String getHost() {
        return "mail.siscontrol.com.ec";
    }

    String getEnableStartTLS() {
        return "true";
    }

    String getPort() {
        return "587";
    }

    String getUser() {
     return "gjadan@siscontrol.com.ec";
    }

    String getPassword() {
        return "ismael2020";
    }

    String getAuth() {
        return "true";
    }
    
}
