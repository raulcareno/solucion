package chatear.cliente;

import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import peaje.formas.frmPrincipal;
import peaje.formas.logger;

public class leerTcpIp {

    public leerTcpIp() {
    }
    private frmPrincipal principal;
    logger lger = new logger();

    public void leerDatos(frmPrincipal pantalla, String ip, Integer puerto) {
        principal = pantalla;
        try {
            
                 Thread cargar = new Thread(ip) {
                public void run() {
                        ping(this.getName()); 
                }
            };
            cargar.start();
            
        } catch (Exception e) {
        }
        try {
            int c;
            Date fechaInicial = new Date();
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
                        abrirbarrera(datos, ip);
                        datos = "";
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(leerTcpIp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // Cuando se alcance el fin de fichero, cerramos la conexión y
            // abandonamos
            s.close();
            lger.logger("leerTcpIP", "xxx CERRO CONEXION TCPIP \n "+"ABIERTA A LAS: "+fechaInicial.toLocaleString()+" CERRADO A LAS "+ (new Date()).toLocaleString());
            try {
                //VUELVO A RECONECTAR
                System.out.println("VUELVO A RECONECTAR POR DESCONEXION O FALTA DE LECTURA EN EL SOCKET.....");
                leerDatos(pantalla, ip, puerto);
            } catch (Exception e) {
                lger.logger("leerTcpIP", "ERROR AL RECONECTAR TCPIP: \n " + e.getMessage());
                System.out.println("error al volver a reconectar");
            }

        } catch (IOException ex) {
            lger.logger("leerTcpIP", "PRUEBA"+ex.getMessage());
            Logger.getLogger(leerTcpIp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void ping(String ip) {
        try {
            InetAddress ping;
            //String ip = "192.168.10.1"; // Ip de la máquina remota 
            int sum = 0;
            ping = InetAddress.getByName(ip);
            while (sum >= 0) {


                if (ping.isReachable(5000)) {
                    System.out.print("\t"+ip + " - responde..!");
                } else {
                    lger.logger("leerTcpIP", "ERROR EN PING A: "+ip);
                    System.out.println(ip + " - error de conexion con ip "+ip);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(leerTcpIp.class.getName()).log(Level.SEVERE, null, ex);
                }
                sum += 1000;
                if (sum == 5000) {
                    try {
                        System.gc();
                        System.out.println("");
                        Thread.sleep(5000);
                        sum = 0;
                        System.gc();
                    } catch (InterruptedException ex) {
                        Logger.getLogger(leerTcpIp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                System.gc();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    void abrirbarrera(String tarjeta, String ip) {
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
