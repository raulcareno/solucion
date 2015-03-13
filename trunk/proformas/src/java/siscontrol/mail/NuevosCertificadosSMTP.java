/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package siscontrol.mail;

/**
 *
 * @author Geovanny
 */
 

import javax.net.SocketFactory;

 

import java.io.*;
import java.net.*;
import java.security.*;
import java.security.cert.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.net.ssl.*;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
 

/**
* Usar esta clase para añadir certificados para correo al almacen de
* certificados. Trata de hacer un envío y mira a ver que certificados faltan.
* Permite añadirlos al almacen de certificados
*
* @author fgarcia
*
*/

public class NuevosCertificadosSMTP extends Object {
  
private static final String CERTS_FILE = "C:/Program Files/Java/jdk1.7.0_71/jre/lib/security/cacerts";            
//private static final String CERTS_FILE = "C:/Program Files/Java/jre7/lib/security/cacerts";
private static final char[] CERTS_FILE_PASS = "changeit".toCharArray();

private static Message createMessage(Session session, Mail datosMail) throws AddressException, MessagingException {
  Message msg;
  msg = new MimeMessage(session);
  if (datosMail.getFrom() != null) {
    msg.setFrom(new InternetAddress(datosMail.getFrom()));
  }
     InternetAddress[] emailsA = new InternetAddress[1];
     emailsA[0] = new InternetAddress(datosMail.getTo()+"");
  msg.setRecipients(Message.RecipientType.TO, emailsA);
  if (datosMail.getSubject() != null) {
    msg.setSubject(datosMail.getSubject()+"");
  }
  msg.setSentDate(new Date());

  Multipart mp = new MimeMultipart();
  if (datosMail.getBody() != null) {
    MimeBodyPart mbp1 = new MimeBodyPart();
    if (datosMail.getHtml()) {
      mbp1.setContent(datosMail.getBody(), "text/html"); // Esto si el contenido es html
    } else {
      mbp1.setText(datosMail.getBody()+"");
    }
    mp.addBodyPart(mbp1);
  }
  msg.setContent(mp);

  return msg;
}

public  void send(Mail datosMail, MailConfiguration config) throws MessagingException {
  boolean debug = true;

  System.setProperty("javax.net.ssl.trustStore", CERTS_FILE);
  Properties props = new Properties();
  props.setProperty("mail.smtp.host", config.getHost());
  props.setProperty("mail.smtp.starttls.enable", config.getEnableStartTLS()); // TLS si está disponible
  props.setProperty("mail.smtp.port", config.getPort());
  props.setProperty("mail.smtp.user", config.getUser()); // Nombre del usuario
  props.setProperty("mail.smtp.password", config.getPassword());
  props.setProperty("mail.smtp.auth", config.getAuth()); // Si requiere o no usuario y password para conectarse.
// Esta es la parte importante
// Se trata de escribir una factoría de sockets que fabrique sockets que  autentiquen con un TrustManager
// especial, que será el que almacene los certs nuevos recibidos del  en el almacen de certificados
  DummySSLSocketFactory sf = new DummySSLSocketFactory();
  Security.setProperty("ssl.SocketFactory.provider", sf.getClass().getName());
  Security.setProperty("ssl.SocketFactory.provider", "DummySSLSocketFactory");
  //props.setProperty("SSLServerAuthRequired","true");
    //Security.setProperty("ssl.SocketFactory.provider", "com.ibm.jsse2.SSLSocketFactoryImpl");
    //Security.setProperty("ssl.ServerSocketFactory.provider", sf.getClass().getName());
  //Security.setProperty("ssl.SocketFactory.provider","siscontrol.mail.DummySSLSocketFactory");
  //javax.net.ssl.SSLSocketFactory
// FIN PARTE NUEVA (ver clase DummySSLSocketFactory más adelante)

  Session session = Session.getDefaultInstance(props, null);
  session.setDebug(debug);
  Message msg = createMessage(session, datosMail);
  Transport t = session.getTransport("smtp");
  t.connect(config.getHost(), config.getUser(), config.getPassword());
  InternetAddress[] emailsA = new InternetAddress[1];
    emailsA[0] = new InternetAddress(datosMail.getTo() + "");
  t.sendMessage(msg, emailsA);
  t.close();
}
public  static void llamar(){
    try {
        String FROM = "gjadan@siscontrol.com.ec";
        String TO = "geovanny.jadan@outlook.com";
        Mail mail = new Mail();
        mail.setTo(TO);
        mail.setHtml(true);
        mail.setSubject("Prueba");
        mail.setBody("HOLA BUBUSA");
        mail.setFrom(FROM);
        MailConfiguration mc = new MailConfiguration();
        mc.setAuth("true");
        mc.setEnableStartTLS("true");
        mc.setHost("faster.myhostingdomain.net");
        mc.setPassword("ismael2020");
        mc.setPort("587");
        mc.setUser("gjadan@siscontrol.com.ec");
        

    NuevosCertificadosSMTP n =  new NuevosCertificadosSMTP();
    n.send(mail, mc);
    } catch (MessagingException ex) {
        Logger.getLogger(NuevosCertificadosSMTP.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public static void main(String[] args) throws Exception {
  String FROM = "gjadan@siscontrol.com.ec";
  String TO = "geovanny.jadan@outlook.com"; 
  Mail mail = new Mail();
  mail.setTo(TO);
  mail.setHtml(true);
  mail.setSubject("Prueba");
  mail.setBody("HOLA BUBUSA");
  mail.setFrom(FROM);
  MailConfiguration mc = new MailConfiguration();
  mc.setAuth("true");
  mc.setEnableStartTLS("true");
  mc.setHost("faster.myhostingdomain.net");
  mc.setPassword("ismael2020");
  mc.setPort("587");
  mc.setUser("gjadan@siscontrol.com.ec");

    NuevosCertificadosSMTP n =  new NuevosCertificadosSMTP();
    n.send(mail, mc);
}


// Este es el TrustManager que va a recoger y almacenar los certificasos nuevos
// enviados por el server de correo (ver metodo checkServerTrusted)

// Esta factoría se encargará de coger los certificados que falten, ya que usa
// un TrustManager que lo hace

} 