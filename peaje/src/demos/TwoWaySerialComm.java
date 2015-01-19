///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package demos;
//
// 
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.lang.reflect.Method;
//import java.util.Enumeration;
//import javax.comm.CommPort;
//import javax.comm.CommPortIdentifier;
//import javax.comm.SerialPort;
//import hibernate.cargar.WorkingDirectory;
//
//public class TwoWaySerialComm {
//
//    String separador = File.separatorChar + "";
//
//    public TwoWaySerialComm() {
//        super();
//    }
//
//    void connect(String portName) throws Exception {
//        CommPortIdentifier portIdentifier = null;
//        WorkingDirectory w = new WorkingDirectory();
//        String ubicacionDirectorio = w.get() + separador;
//        if (ubicacionDirectorio.contains("build")) {
//            ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
//        }
//        String temp_string = ubicacionDirectorio + "lib" + separador + "javax.comm.properties";
//        Method loadDriver_Method = CommPortIdentifier.class.getDeclaredMethod("loadDriver", new Class[]{String.class});
//        loadDriver_Method.setAccessible(true);
//        loadDriver_Method.invoke("loadDriver", new Object[]{temp_string});
////            CommPortIdentifier portId;
//        Enumeration portList = CommPortIdentifier.getPortIdentifiers();
//
//        OutputStream outputStream = null;
////            SimpleRead reader;
//        portList = CommPortIdentifier.getPortIdentifiers();
//        while (portList.hasMoreElements()) {
//            portIdentifier = (CommPortIdentifier) portList.nextElement();
//            if (portIdentifier.getPortType() == CommPortIdentifier.PORT_SERIAL) {
//                if (portIdentifier.getName().equals(portName)) {
//                    if (portIdentifier.isCurrentlyOwned()) {
//                        System.out.println("Error: Port is currently in use");
//                    } else {
//                        CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);
//
//                        if (commPort instanceof SerialPort) {
//                            SerialPort serialPort = (SerialPort) commPort;
//                            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
//
//                            InputStream in = serialPort.getInputStream();
//                            OutputStream out = serialPort.getOutputStream();
//
//                            (new Thread(new SerialReader(in))).start();
//                            (new Thread(new SerialWriter(out))).start();
//
//                        } else {
//                            System.out.println("Error: Only serial ports are handled by this example.");
//                        }
//                    }
//                }
//            }
//        }
//
//
//    }
//
//    /** */
//    public static class SerialReader implements Runnable {
//
//        InputStream in;
//
//        public SerialReader(InputStream in) {
//            this.in = in;
//        }
//
//        public void run() {
//            byte[] readBuffer = new byte[8];
//                try {
////                    while (inputStream.available() > 0) {
//                    int numBytes = in.read(readBuffer);
//                    System.out.println(""+new String(readBuffer).trim());
////                    }
//                } catch (Exception e) {
//                }
//        }
//    }
//
//    /** */
//    public static class SerialWriter implements Runnable {
//
//        OutputStream out;
//
//        public SerialWriter(OutputStream out) {
//            this.out = out;
//        }
//
//        public void run() {
//            try {
//                int c = 0;
//                while ((c = System.in.read()) > -1) {
//                    this.out.write(c);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        try {
//            (new TwoWaySerialComm()).connect("COM5");
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//}
