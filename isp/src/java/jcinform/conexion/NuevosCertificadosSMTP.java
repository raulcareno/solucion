/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.conexion;

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
import javax.activation.*;
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

public static void send(Mail datosMail, MailConfiguration config) throws MessagingException {
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

public static void main(String[] args) throws Exception {
  String FROM = "agendasoporte@csed.com.ec";
  String TO = "geovanny.jadan@outlook.com"; 
  Mail mail = new Mail();
  mail.setTo(TO);
  mail.setHtml(true);
  mail.setSubject("Prueba");
  mail.setBody("Espero que funcione el mensajillo");
  mail.setFrom(FROM);
  NuevosCertificadosSMTP.send(mail, new MailConfiguration());
}

private static void printCert(int i, X509Certificate cert) throws CertificateEncodingException, NoSuchAlgorithmException {
  MessageDigest sha1 = MessageDigest.getInstance("SHA1");
  MessageDigest md5 = MessageDigest.getInstance("MD5");
  System.out.println(" " + (i + 1) + " Subject " + cert.getSubjectDN());
  System.out.println(" Issuer " + cert.getIssuerDN());
  sha1.update(cert.getEncoded());
  System.out.println(" sha1 " + toHexString(sha1.digest()));
  md5.update(cert.getEncoded());
  System.out.println(" md5 " + toHexString(md5.digest()));
  System.out.println();
}

private static String toHexString(byte[] bytes) {
  char[] HEXDIGITS = "0123456789abcdef".toCharArray();
  StringBuilder sb = new StringBuilder(bytes.length * 3);
  for (int b : bytes) {
    b &= 0xff;
    sb.append(HEXDIGITS[b >> 4]);
    sb.append(HEXDIGITS[b & 15]);
    sb.append(' ');
  }
  return sb.toString();
}

// Este es el TrustManager que va a recoger y almacenar los certificasos nuevos
// enviados por el server de correo (ver metodo checkServerTrusted)
private static class SavingTrustManager implements X509TrustManager {
  private final X509TrustManager tm;
  private X509Certificate[] chain;
  SavingTrustManager(X509TrustManager tm) {
    this.tm = tm;
  }

  public X509Certificate[] getAcceptedIssuers() {
    return tm.getAcceptedIssuers();
  }

  public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    throw new UnsupportedOperationException();
  }

  public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    this.chain = chain;
    if (chain == null) {
      System.out.println("Could not obtain server certificate chain");
      return;
    }

    System.out.println();
    System.out.println("Server sent " + chain.length + " certificate(s):");
    System.out.println();
    for (int i = 0; i < chain.length; i++) {
      X509Certificate cert = chain[i];
      try {
        printCert(i, cert);
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
    }

    try {
      File file = new File(CERTS_FILE);
      System.out.println("Loading KeyStore " + file.getAbsolutePath() + "...");
      InputStream in = new FileInputStream(file);
      KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
      ks.load(in, CERTS_FILE_PASS);
      in.close();
      System.out.println("Enter certificate to add to trusted keystore or 'q' to quit: [1-" +
                         chain.length + "or (A)ll]");

      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      String line = reader.readLine().trim();
      boolean all = false;
      int k;
      try {
        k = (line.length() == 0) ? 0 : Integer.parseInt(line) - 1;
      } catch (NumberFormatException e) {
        if (line.equalsIgnoreCase("a")) {
          all = true;
          k = 0;
        } else {
          System.out.println("KeyStore not changed");
          return;
        }
      }

      if (all) {
        for (int i = 0; i < chain.length; i++) {
          X509Certificate cert = chain[i];           
          String alias = "el_host" + "-" + (i + 1);
          ks.setCertificateEntry(alias, cert);
          System.out.println();
          System.out.println(cert);
          System.out.println();
          System.out.println("Added certificate to keystore 'jssecacerts' using alias '" + alias + "'");
        }
      } else {
        X509Certificate cert = chain[k];
        String alias = "el_host" + "-" + (k + 1);
        ks.setCertificateEntry(alias, cert);
      }

      OutputStream out = new FileOutputStream(CERTS_FILE);
      ks.store(out, CERTS_FILE_PASS);
      out.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    tm.checkServerTrusted(chain, authType);
  }
}

// Esta factoría se encargará de coger los certificados que falten, ya que usa
// un TrustManager que lo hace
public static class DummySSLSocketFactory extends SSLSocketFactory {
  private SSLSocketFactory factory;

  public DummySSLSocketFactory() {
    System.out.println("DummySocketFactory instantiated");
    try {
      File file = new File(CERTS_FILE);
      System.out.println("Loading KeyStore " + file.getAbsolutePath() + "...");
      InputStream in = new FileInputStream(file);
      KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
      ks.load(in, CERTS_FILE_PASS);
      in.close();
      SSLContext context = SSLContext.getInstance("TLS");
      TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
      tmf.init(ks);
      X509TrustManager defaultTrustManager = (X509TrustManager) tmf.getTrustManagers()[0];

      // Asignamos un nuevo TrustManager que se encargará de coger los
      // certificados nuevos
      SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
      context.init(null, new TrustManager[] {tm}, null);
      factory = (SSLSocketFactory) context.getSocketFactory();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public static SocketFactory getDefault() {
    return new DummySSLSocketFactory();
  }

  public Socket createSocket(Socket socket, String s, int i, boolean flag)
      throws IOException {
    return factory.createSocket(socket, s, i, flag);
  }

  public Socket createSocket(InetAddress inaddr, int i, InetAddress inaddr1, int j) throws IOException {
    return factory.createSocket(inaddr, i, inaddr1, j);
  }

  public Socket createSocket(InetAddress inaddr, int i) throws IOException {
    return factory.createSocket(inaddr, i);
  }

  public Socket createSocket(String s, int i, InetAddress inaddr, int j) throws IOException {
    return factory.createSocket(s, i, inaddr, j);
  }

  public Socket createSocket(String s, int i) throws IOException {
    return factory.createSocket(s, i);
  }

  public String[] getDefaultCipherSuites() {
    return factory.getSupportedCipherSuites();
  }

  public String[] getSupportedCipherSuites() {
    return factory.getSupportedCipherSuites();
  }
}
} 