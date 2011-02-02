package peaje;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.*;

public class java1901 {
  static Enumeration listaPuertos;
  static CommPortIdentifier idPuerto;
  static String mensaje = "Tutorial de Java, Comunicaciones Serie\n";
  static SerialPort puertoSerie;
  static OutputStream salida;

  public static void main( String[] args ) {
        try {
            listaPuertos = CommPortIdentifier.getPortIdentifiers();
            String temp_string = "D:\\PROYECTOS\\peaje\\lib\\javax.comm.properties";
            Method loadDriver_Method = CommPortIdentifier.class.getDeclaredMethod("loadDriver", new Class[]{String.class});
            loadDriver_Method.setAccessible(true);
            loadDriver_Method.invoke("loadDriver", new Object[]{temp_string});
            while (listaPuertos.hasMoreElements()) {
                idPuerto = (CommPortIdentifier) listaPuertos.nextElement();
                if (idPuerto.getPortType() == CommPortIdentifier.PORT_SERIAL) {
//        if( idPuerto.getName().equals("/dev/term/a") ) {
                    if (idPuerto.getName().equals("COM10")) {
                        // Si el puerto no est� en uso, se intenta abrir
                        try {
                            puertoSerie = (SerialPort) idPuerto.open("AplEscritura",2000);
                        } catch (PortInUseException e) {
                        }
                        // Se obtiene un canal de salida
                        try {
                            salida = puertoSerie.getOutputStream();
                        } catch (IOException e) {
                        }
                        // Se fijan los par�metros de comunicaci�n del puerto
                        try {
                            puertoSerie.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                        } catch (UnsupportedCommOperationException e) {
                        }
                        // Se env�a el mensaje
                        try {
                            salida.write(mensaje.getBytes());
                        } catch (IOException e) {
                        }
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(java1901.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(java1901.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(java1901.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(java1901.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(java1901.class.getName()).log(Level.SEVERE, null, ex);
        }
  }
}
  
//------------------------------------------ Final del fichero java1901.java  