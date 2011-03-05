package peaje.formas;
 
import hibernate.cargar.WorkingDirectory;
import java.io.*;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.*;

public class SimpleWrite2 {
//    static Enumeration portList;
//    static CommPortIdentifier portId;
//    static String messageString = "Hello, world!\n";
//    static SerialPort serialPort;
//    static OutputStream outputStream;
   static SerialPort serialPort = null;
    public static void llamar(String elpuerto) {
//        try {
//             WorkingDirectory w = new WorkingDirectory();
////            String query = "";
//          String ubicacionDirectorio = w.get()+"\\";
//                if(ubicacionDirectorio.contains("build"))
//                    ubicacionDirectorio = ubicacionDirectorio.replace("\\build", "");
//
//            String temp_string = ubicacionDirectorio+"lib\\javax.comm.properties";
//            Method loadDriver_Method = CommPortIdentifier.class.getDeclaredMethod("loadDriver", new Class[]{String.class});
//            loadDriver_Method.setAccessible(true);
//            loadDriver_Method.invoke("loadDriver", new Object[]{temp_string});
//            CommPortIdentifier portId;
//            Enumeration portList = CommPortIdentifier.getPortIdentifiers();
//
//            OutputStream outputStream = null;
////            SimpleRead reader;
//            portList = CommPortIdentifier.getPortIdentifiers();
//            while (portList.hasMoreElements()) {
//                portId = (CommPortIdentifier) portList.nextElement();
//                if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
//                    if (portId.getName().equals(elpuerto)) {
////                if (portId.getName().equals("/dev/term/a")) {
//                        try {
//                            serialPort = (SerialPort) portId.open("SimpleWriteApp", 2000);
//                        } catch (PortInUseException e) {
//                            System.out.println("" + e);
//                        }
//                        try {
//                            outputStream = serialPort.getOutputStream();
//                        } catch (IOException e) {
//                            System.out.println("" + e);
//                        }
//                        try {
//                            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//                        } catch (UnsupportedCommOperationException e) {
//                            System.out.println("" + e);
//                        }
//                        try {
//                            //ABRIR
////                            byte[] a = new byte[3];
////                            a[0] = (byte) 254;
////                            a[1] = 108;
////                            a[2] = 1;
////                        //CERRAR
////                        byte[] data = new byte[3];
////                        data[0] =  (byte) 254;
////                        data[1] = 100;
////                        data[2] = 1;
//                            //            serialPort1.Write(data, 0, 3);
////                        outputStream.write(messageString.getBytes());
//                            outputStream.write(1);
//
////                            outputStream.write(2);
//                            //outputStream.w
//
//                        } catch (IOException e) {
//                            System.out.println("" + e);
//                        }
////                          try {
////			outputStream.write(1);
////		    } catch (IOException e) {}
//
//		    try {
//		       Thread.sleep(10000);  // Be sure data is xferred before closing
//		    } catch (Exception e) {}
//		    serialPort.close();
//                    }
//                }
//            }
//        } catch (IllegalArgumentException ex) {
//            Logger.getLogger(SimpleWrite.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (NoSuchMethodException ex) {
//            Logger.getLogger(SimpleWrite.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SecurityException ex) {
//            Logger.getLogger(SimpleWrite.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
