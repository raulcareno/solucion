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
//        try {
//            
//                 Thread cargar = new Thread(ip) {
//                public void run() {
//                        ping(this.getName()); 
//                }
//            };
//            cargar.start();
//            
//        } catch (Exception e) {
//        }
        try {
            System.out.println("ingreso a leertcpip");
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
            System.out.println("EMPEZO CONEXIÓN CON LECTORA DE BARRAS IP ");
            sIn = s.getInputStream();
            String datos = "";
            String tarjeta = "";
            byte[] readBuffer = new byte[10];
            while ((c = sIn.read()) != -1) {
                try {
                    
                    //System.out.print((char) c);
//                    Thread.sleep(2);
//                    datos += (char) c + "";
//                    datos = datos.replace(" ", "");
//                    datos = datos.trim();
//                    System.out.println(".."+datos);
//                    System.out.println("__"+sIn);
//                    int numBytes = s.getInputStream().read(readBuffer);
//                    Thread.sleep(30);
//                    tarjeta = tarjeta + new String(readBuffer).trim();
//                    System.out.println("#bytes:"+numBytes+" :__:" + tarjeta);
//                             
//                    if (datos.length() >6 ) {
//                        abrirbarrera(datos, ip);
//                        datos = "";
//                        System.gc();
//                    }
                    System.out.println("read");
                     while (sIn.available() > 0) {
                        System.out.println("Punto: "+datos);
                        int numBytes = sIn.read(readBuffer);
                        Thread.sleep(30);
                        datos = (char) c + "";
                        System.out.println("#bytes: " + numBytes);
                        tarjeta = tarjeta + new String(readBuffer).trim();
                        System.out.println("read: "+tarjeta);
                    }
                     if(tarjeta.toUpperCase().contains("A") || tarjeta.toUpperCase().contains("B") ||tarjeta.toUpperCase().contains("C") ||  
                             tarjeta.toUpperCase().contains("D") ){
                     
                     }else{
                                tarjeta = datos+tarjeta;
                     }
                     System.out.println("ticket:"+tarjeta);
                     abrirbarrera(tarjeta, ip);
                     System.out.println("fin read");
                     tarjeta = "";
                } catch (InterruptedException ex) {
                    Logger.getLogger(leerTcpIp.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            // Cuando se alcance el fin de fichero, cerramos la conexión y
            // abandonamos
            s.close();
            System.gc();
            lger.logger("leerTcpIP", "xxx CERRO CONEXION TCPIP \n "+"ABIERTA A LAS: "+fechaInicial.toLocaleString()+" CERRADO A LAS "+ (new Date()).toLocaleString());
            try {
                //VUELVO A RECONECTAR
                System.gc();
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
      
        principal.buscarTarjetaValidarSalida2(ip, tarjeta);//ENVIO EL NUMERO DE TICKET

        tarjeta = "";
        System.gc();
    }
}
