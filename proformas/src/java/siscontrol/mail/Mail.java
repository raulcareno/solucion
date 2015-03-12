/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siscontrol.mail;

import java.net.InetAddress;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;

/**
 *
 * @author Geovanny
 */
public class Mail {

   String to;
   Object subject,body;
   String from;
   Boolean html;

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Object getSubject() {
        return subject;
    }

    public void setSubject(Object subject) {
        this.subject = subject;
    }

 
    public Boolean getHtml() {
        return html;
    }

    public void setHtml(Boolean html) {
        this.html = html;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

     
   
 
   
    
}
