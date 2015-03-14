/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.conexion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.security.KeyStore;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Geovanny
 */

public class DummySSLSocketFactory extends SSLSocketFactory {
private SSLSocketFactory factory;
//private final String CERTS_FILE = "/usr/java/jdk1.6.0_11/jre/lib/security/cacerts";
private static final String CERTS_FILE = System.getProperty("java.home")+"/lib/security/cacerts";            
 private  final char[] CERTS_FILE_PASS = "changeit".toCharArray();
  

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
      SavingTrustManager tm = new SavingTrustManager(defaultTrustManager);
      context.init(null, new TrustManager[] {tm}, null);
      factory = (SSLSocketFactory) context.getSocketFactory();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
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
    

