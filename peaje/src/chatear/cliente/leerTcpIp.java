package chatear.cliente;

import java.net.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import peaje.formas.frmPrincipal;

public class leerTcpIp {
public leerTcpIp(){
    
}
    private frmPrincipal principal;

    public void leerDatos(frmPrincipal pantalla,String ip, Integer puerto) {
        principal = pantalla;
        try {
            int c;
            Socket s = null;
            InputStream sIn;
            // Abrimos una conexión con breogan en el puerto 4321
            try {
                //s = new Socket("192.168.0.7", 1024);
                s = new Socket(ip, puerto);
            } catch (IOException e) {
                System.out.println(e);
            }
            // Obtenemos un controlador de fichero de entrada del socket y
            // leemos esa entrada
            sIn = s.getInputStream();
            String datos = "";
            while ((c = sIn.read()) != -1) {
                try {
                    //System.out.print((char) c);
                    datos += (char) c + "";
                    Thread.sleep(2);
                    datos = datos.replace(" ", "");
                    datos = datos.trim();
                    if (datos.length() == 8) {
                        abrirbarrera(datos,ip);
                        datos = "";
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(leerTcpIp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // Cuando se alcance el fin de fichero, cerramos la conexión y
            // abandonamos
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(leerTcpIp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void abrirbarrera(String tarjeta,String ip) {
        for (int i = tarjeta.length(); i < 10; i++) {
            tarjeta = "0" + tarjeta;
        }
        if (tarjeta.length() > 10) {
            tarjeta = tarjeta.substring(0, 10);
        }
        principal.buscarTarjetaValidarSalida2(ip, tarjeta);//ENVIO EL NUMERO DE TICKET

        tarjeta = "";

    }
}
