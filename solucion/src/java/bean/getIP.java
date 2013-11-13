/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;

/**
 *
 * @author JcinformLocal
 */
public class getIP {

    private String publicIP = null;

    public static void main(String args[]) {
        new getIP();
    }

    public String getPublicIP() {
        return publicIP;
    }

    public void setPublicIP(String publicIP) {
        this.publicIP = publicIP;
    }

    public String devolverIP() {
        try {
            URL tempURL = new URL("http://checkip.amazonaws.com/");
            HttpURLConnection tempConn = (HttpURLConnection) tempURL.openConnection();
            InputStream tempInStream = tempConn.getInputStream();
            InputStreamReader tempIsr = new InputStreamReader(tempInStream);
            BufferedReader tempBr = new BufferedReader(tempIsr);

            publicIP = tempBr.readLine();

            tempBr.close();
            tempInStream.close();

        } catch (Exception ex) {
            publicIP = "";
        }

        System.out.println("Mi IP Publica es " + publicIP);
        return publicIP;
    }
}
