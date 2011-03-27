package peaje.formas;

import hibernate.cargar.WorkingDirectory;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.*;

public class AbrirPuerta {
    static OutputStream outputStream;
    static SerialPort serialPort = null;
    static SerialPort serieI;
static String separador = File.separatorChar+"";
    public static void abrir(String puertodeTarjet, String abrirPuerta) {
        
        try {
            WorkingDirectory w = new WorkingDirectory();
            String ubicacionDirectorio = w.get() + separador;
            if (ubicacionDirectorio.contains("build")) {
                ubicacionDirectorio = ubicacionDirectorio.replace(separador+"build", "");
            }
            String temp_string = ubicacionDirectorio + "lib"+separador+"javax.comm.properties";
            Method loadDriver_Method = CommPortIdentifier.class.getDeclaredMethod("loadDriver", new Class[]{String.class});
            loadDriver_Method.setAccessible(true);
            loadDriver_Method.invoke("loadDriver", new Object[]{temp_string});
            CommPortIdentifier portId;
            Enumeration portList = CommPortIdentifier.getPortIdentifiers();

            OutputStream outputStream = null;
//            SimpleRead reader;
            portList = CommPortIdentifier.getPortIdentifiers();
            while (portList.hasMoreElements()) {
                portId = (CommPortIdentifier) portList.nextElement();
                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    if (portId.getName().equals(puertodeTarjet)) {
//                if (portId.getName().equals("/dev/term/a")) {
                        try {
//                            if(portId.isCurrentlyOwned()){
//                                serialPort = LeerTarjeta.serialPort;
//                            }else{
                                serialPort = (SerialPort) portId.open("COMPUERTA", 2001);
//                            }
                            
                        } catch (PortInUseException e) {
                            System.out.println("" + e);
                        }
                        try {
                            outputStream = serialPort.getOutputStream();
                        } catch (IOException e) {
                            System.out.println("" + e);
                        }
                        try {
                            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                        } catch (UnsupportedCommOperationException e) {
                            System.out.println("" + e);
                        }
                        try {
                            outputStream.write(abrirPuerta.getBytes());
                        } catch (IOException e) {
                            System.out.println("" + e);
                        }
                        try {
                            Thread.sleep(1000);  // Me aseguro que es transmitido correctamente antes de cerrar
                        } catch (Exception e) {
                        } //ESPERO UN POCO
                        serialPort.close();
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AbrirPuerta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(AbrirPuerta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(AbrirPuerta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(AbrirPuerta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(AbrirPuerta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
