/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.Applet;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.*;

import jcinform.Administrador.Administrador;
import jcinform.Administrador.UsuarioActivo;
import jcinform.Administrador.logger;
import jcinform.persistencia.Calificacion;
import jcinform.persistencia.Tipos;

public class LeerTarjetaUsuario implements Runnable, SerialPortEventListener {

    CommPortIdentifier puertoId;
//  portList;
    InputStream inputStream;
    OutputStream outputSream;
    SerialPort serialPort;
    Thread readThread;
    public String tarjeta;
    VentanillaUsuario princip;
    UsuarioActivo s =new UsuarioActivo();
    Administrador adm = new Administrador(s);
    String separador = File.separatorChar + "";
logger lger = new logger();
    public LeerTarjetaUsuario() {
    }

    public void abrir(String puertodeTarjet, String LeerTarjetaUsuario) {

        try {
            String ubicacionDirectorio = UsuarioActivo.getUbicacionDirectorio();
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
//                                serialPort = LeerTarjetaUsuario.serialPort;
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
                            outputStream.write(LeerTarjetaUsuario.getBytes());
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
            Logger.getLogger(LeerTarjetaUsuario.class.getName()).log(Level.SEVERE, null, ex);
             lger.logger(LeerTarjetaUsuario.class.getName(), ex + "");
        } catch (InvocationTargetException ex) {
            Logger.getLogger(LeerTarjetaUsuario.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(LeerTarjetaUsuario.class.getName(), ex + "");
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(LeerTarjetaUsuario.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(LeerTarjetaUsuario.class.getName(), ex + "");
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(LeerTarjetaUsuario.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(LeerTarjetaUsuario.class.getName(), ex + "");
        } catch (SecurityException ex) {
            Logger.getLogger(LeerTarjetaUsuario.class.getName()).log(Level.SEVERE, null, ex);
            lger.logger(LeerTarjetaUsuario.class.getName(), ex + "");
        }
    }

    @SuppressWarnings("static-access")
    public LeerTarjetaUsuario(CommPortIdentifier portIde, VentanillaUsuario pantalla) {

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
                        System.out.println("#bytes: " + numBytes);
                        tarjeta = tarjeta + new String(readBuffer).trim();
                        Thread.sleep(200);
                    }

                } catch (Exception e) {
                    lger.logger(LeerTarjetaUsuario.class.getName(), e + "");
                }
                break;
        }



        Tipos tipo = buscar(tarjeta);
        if (tipo != null) {
            try {
                Integer codigotur = 0;
                if (princip.turnoSeleccionado != null) {
                    codigotur = princip.turnoSeleccionado.getCodigotur();
                }
                List<Calificacion> verificarSiExsiste = adm.query("Select o from Calificacion as o "
                        + "where o.turnos.codigotur = '" + codigotur + "'  ");
                if (verificarSiExsiste.size() > 0) {
                } else {
                    Calificacion cal = new Calificacion(adm.getNuevaClave("Calificacion", "secuencial"));
                    cal.setTipos(tipo);
                    cal.setFecha(adm.Date());
                    cal.setFechahora(adm.Date());
                    cal.setHora(adm.Date());
                    cal.setEmpleados(princip.usuario);
                    cal.setConsola(new Integer(princip.empresaObj.getVentanilla().trim()));
                    cal.setTurnos(princip.turnoSeleccionado);
                    adm.guardar(cal);
//                    List<Calificacion> siExiste = adm.query("Select o from Calificacion as o "
//                            + " where o.turnos.codigotur = '" + princip.turnoSeleccionado.getCodigotur() + "' "
//                            + "  ");
//                    while (siExiste.size() <= 0) {
//                        siExiste = adm.query("Select o from Calificacion as o "
//                                + " where o.turnos.codigotur = '" + princip.turnoSeleccionado.getCodigotur() + "' "
//                                + "  ");
//                        adm.guardar(cal);
//                    }

                    princip.PruebaPanelPersonalizado();
                    princip.mensajes.setText("Gracias...!");
                 

                    try {
                        if (princip.turnoSeleccionado != null) {
                            princip.turnoSeleccionado.setFechaatendido(adm.Date());
                            princip.turnoSeleccionado.setEstado(1); //atendido
                            adm.actualizar(princip.turnoSeleccionado);
                        }
                    } catch (Exception e) {
                        lger.logger(LeerTarjetaUsuario.class.getName(), e + "");
                        System.out.println("se califico pero hubo error en acutalizar turno seleccionado " + e);
                    }
                    tarjeta = "";

                }
            } catch (Exception ex) {
                lger.logger(LeerTarjetaUsuario.class.getName(), ex + "");
                ex.printStackTrace();
            }
        }
        tarjeta = "";

    }
    List<Tipos> tipos = null;

    Tipos buscar(String codificacion) {
        if (tipos == null) {
            try {
                tipos = adm.listar("Select o from Tipos as o ");
            } catch (Exception ex) {
                lger.logger(LeerTarjetaUsuario.class.getName(), ex + "");
                ex.printStackTrace();
            }
        }
        for (Iterator<Tipos> it = tipos.iterator(); it.hasNext();) {
            Tipos glbusuario = it.next();
            if (codificacion.equals(glbusuario.getCodificacion())) {
                return glbusuario;
            }

        }
        System.out.println("TIPO " + codificacion + " NO ENCONTRADO...! ");
        return null;
    }
}
