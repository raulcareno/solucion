/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siscontrol.mail;

/**
 *
 * @author Geovanny
 */
public class MailConfiguration2 {

    public MailConfiguration2() {
    }
String host;
String enableStartTLS;
String port;
String auth, user, password;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getEnableStartTLS() {
        return enableStartTLS;
    }

    public void setEnableStartTLS(String enableStartTLS) {
        this.enableStartTLS = enableStartTLS;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    
}
