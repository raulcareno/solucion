package jcinform.Administrador;


import java.net.*;
import java.util.Date;
import jcinform.persistencia.Empleados;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
/**
 *
 * @author inform
 */
public class obtenerMac {

    public obtenerMac() {
    }

    /**
     * @param args the command line arguments
     */
    public String  devolverMac() {
        // TODO code application logic here
        Session a = Sessions.getCurrent();
             String abc = a.getRemoteAddr();
        InetAddress ip;
        
        try {
            ip = InetAddress.getLocalHost();
//            ip = InetAddress.getAllByName(abc)[0];
            System.out.println("Current IP address : " + abc);
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);

            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            System.out.println(sb.toString());
            return sb.toString();
        } catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (SocketException e) {

            e.printStackTrace();

        }
        return null;
    }
}
