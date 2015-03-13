/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscontrol.mail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Geovanny
 */
class SavingTrustManager implements X509TrustManager {
    private static final String CERTS_FILE = "C:/Program Files/Java/jdk1.7.0_71/jre/lib/security/cacerts";            
//private static final String CERTS_FILE = "C:/Program Files/Java/jre7/lib/security/cacerts";
private static final char[] CERTS_FILE_PASS = "changeit".toCharArray();
    
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
//      System.out.println("Enter certificate to add to trusted keystore or 'q' to quit: [1-" +
//                         chain.length + "or (A)ll]");
//
//      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//      String line = reader.readLine().trim();
      String line ="A";
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

  
}
