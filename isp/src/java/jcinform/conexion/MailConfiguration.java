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
String host,user,password,auth,enableStartTLS,port;

    public MailConfiguration() {
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public void setEnableStartTLS(String enableStartTLS) {
        this.enableStartTLS = enableStartTLS;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser(String user) {
        this.user = user;
    }

    String getHost() {
        return this.host;
        //return "mail.csed.net.ec";
    }

    String getEnableStartTLS() {
        return this.enableStartTLS;
        //return "true";
    }

    String getPort() {
        return this.port;
        //return "587";
    }

    String getUser() {
        return this.user;
     //return "agendasoporte@csed.net.ec";
    }

    String getPassword() {
        return this.password;
       // return "csednet77";
    }

    String getAuth() {
        return this.auth;
        //return "true";
    }

    public void setPort(String port) {
        this.port = port;
    }
    
    
}
