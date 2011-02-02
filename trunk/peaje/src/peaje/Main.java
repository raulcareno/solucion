/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peaje;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.CommDriver;
import javax.comm.CommPort;
import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;

/**
 *
 * @author geovanny
 */
public class Main {
 
    public static  void main(String[] args) {
        try {
            abrir("COM10", 9600, 1020202000);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    static InputStream entrada = null;
static OutputStream salida;
static SerialPort puertoSerie = null;

public static void abrir(String dispositivo,int baudios,int timeout ) throws Exception {
  String temp_string = "D:\\PROYECTOS\\peaje\\lib\\javax.comm.properties";
    Method loadDriver_Method = CommPortIdentifier.class.getDeclaredMethod("loadDriver", new Class[]{String.class});
    loadDriver_Method.setAccessible(true);
    loadDriver_Method.invoke("loadDriver", new Object[]{temp_string});
  CommPortIdentifier idPuerto =    CommPortIdentifier.getPortIdentifier("COM10");
  puertoSerie = (SerialPort)idPuerto.open("PuertoSerie",timeout );
  puertoSerie.setSerialPortParams( 9600,SerialPort.DATABITS_8,
    SerialPort.STOPBITS_1,SerialPort.PARITY_NONE );
  puertoSerie.setFlowControlMode( SerialPort.FLOWCONTROL_NONE);
  puertoSerie.enableReceiveThreshold( 1 );
  puertoSerie.enableReceiveTimeout( timeout );
  System.out.println( "Dispositivo Serie abierto" );

  salida = puertoSerie.getOutputStream();
  entrada = puertoSerie.getInputStream();
    System.out.println(""+salida);
    System.out.println(""+entrada);
}

}
