/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.conexion;

/**
 *
 * @author Geovanny
 */
public class MailConfiguration {

    public MailConfiguration() {
    }

    String getHost() {
        return "mail.csed.com.ec";
    }

    String getEnableStartTLS() {
        return "true";
    }

    String getPort() {
        return "587";
    }

    String getUser() {
     return "agendasoporte@csed.com.ec";
    }

    String getPassword() {
        return "csednet77";
    }

    String getAuth() {
        return "true";
    }
    
}
