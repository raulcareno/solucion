/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peaje.formas;

import hibernate.Calificaciones;
import hibernate.Tipos;
import hibernate.cargar.WorkingDirectory;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.*;
import hibernate.cargar.Administrador;
import hibernate.cargar.GeneraXMLPersonal;

public class LeerTarjeta implements Runnable, SerialPortEventListener {

    CommPortIdentifier puertoId;
//  portList;
    InputStream inputStream;
    OutputStream outputSream;
    SerialPort serialPort;
    Thread readThread;
    public String tarjeta;
    frmPrincipal princip;
    Administrador adm = new Administrador(GeneraXMLPersonal.user);
    String separador = File.separatorChar + "";

    public LeerTarjeta() {
    }

    public void abrir(String puertodeTarjet, String abrirPuerta) {

        try {
            WorkingDirectory w = new WorkingDirectory();
            String ubicacionDirectorio = w.get() + separador;
            if (ubicacionDirectorio.contains("build")) {
                ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
            }
            String temp_string = ubicacionDirectorio + "lib" + separador + "javax.comm.properties";
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
                            if (portId.isCurrentlyOwned()) {
                                portId.removePortOwnershipListener(null);
                            }

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
                            Thread.sleep(500);  // Me aseguro que es transmitido correctamente antes de cerrar
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

    @SuppressWarnings("static-access")
    public LeerTarjeta(CommPortIdentifier portIde, frmPrincipal pantalla) {

        try {
            this.tarjeta = "";
            princip = pantalla;
            this.puertoId = portIde;
            //SimpleReadApp
            serialPort = (SerialPort) puertoId.open("LECTORA", 2000);
        } catch (PortInUseException e) {
            System.out.println("" + e);
        }
        try {
            inputStream = null;
            inputStream = serialPort.getInputStream();
        } catch (IOException e) {
            System.out.println("LEER TARJETA.CLASS " + e);
        }
        try {
            outputSream = serialPort.getOutputStream();
        } catch (IOException e) {
            System.out.println("" + e);
        }
        try {
            serialPort.addEventListener(this);
        } catch (TooManyListenersException e) {
            System.out.println("" + e);
        }
        serialPort.notifyOnDataAvailable(true);
        try {
            serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
        } catch (UnsupportedCommOperationException e) {
        }
        readThread = new Thread(this);
        readThread.start();
    }

    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }
    public int inicio = 0;

    @Override
    public void serialEvent(SerialPortEvent event) {

        //        System.out.println("" + tarjeta);
        switch (event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:
            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                byte[] readBuffer = new byte[10];
                try {
//                                tarjeta = "";
                    while (inputStream.available() > 0) {
                        int numBytes = inputStream.read(readBuffer);
                             Thread.sleep(30);
                        System.out.println("#bytes: " + numBytes);
                        tarjeta = tarjeta + new String(readBuffer).trim();
                    }

                } catch (Exception e) {
                }
                break;
        }



        Tipos tipo = buscar(tarjeta);
        if (tipo != null) {
            Calificaciones cal = new Calificaciones();
            cal.setTipos(tipo);
            cal.setFecha(new Date());
            cal.setFechahora(new Date());
            cal.setConsola(1);
            cal.setHora(new Date());
            cal.setUsuarios(princip.usuarioActual);
            tarjeta = "";
        }


    }

    Tipos buscar(String codificacion) {
        for (Iterator<Tipos> it = princip.tipos.iterator(); it.hasNext();) {
            Tipos glbusuario = it.next();
            if (codificacion.equals(glbusuario.getCodificacion())) {
                return glbusuario;
            }

        }
        return null;
    }
}
