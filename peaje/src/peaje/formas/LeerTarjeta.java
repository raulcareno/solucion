/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package peaje.formas;

import hibernate.Empresa;
import hibernate.Factura;
import hibernate.cargar.WorkingDirectory;
import java.awt.print.PrinterJob;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.comm.*;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import hibernate.cargar.Administrador;
import hibernate.cargar.GeneraXMLPersonal;
import javax.swing.JOptionPane;
import sources.FacturaSource;

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
                        System.out.println("#bytes: " + numBytes);
                        tarjeta = tarjeta + new String(readBuffer).trim();
                    }

                } catch (Exception e) {
                }
                break;
        }
        //System.out.println(""+tarjeta);
        System.out.println("LECTURA: " + tarjeta);

        if (tarjeta.length() >= 10) {
            if (tarjeta.length() > 10) {
                tarjeta = tarjeta.substring(0, 10);
            }
            //System.out.println("VAL: TARJETA: " + tarjeta);


            //System.out.println("" + tarjeta);
            if (tarjeta.contains("AEIOU")) {
//                            tarjeta = tarjeta.replace("00", "");
                System.out.println(tarjeta + " " + new Date());
                tarjeta = "";
                imprimir("");
                abrirPuerta(princip.empresaObj.getEntra1());
                System.out.println("ABRIO PUERTA: " + princip.empresaObj.getEntra1());

                return;

            }
            if (tarjeta.contains("AEIOU2")) {
                tarjeta = tarjeta.replace("00", "");
                System.out.println("AEIOUAEIOU2" + new Date());
                tarjeta = "";
                imprimir("2");
                abrirPuerta(princip.empresaObj.getEntra1());
                System.out.println("ABRIO PUERTA: " + princip.empresaObj.getEntra2());
                tarjeta = "";
                return;
            }
            if (puertoId.getName().equals(princip.empresaObj.getBarras())) { //VALIDO SALIDA DEL CARRO CON CODIGO DE BARRAS
                princip.buscarTarjetaValidarSalida(puertoId.getName(), tarjeta);//ENVIO EL NUMERO DE TICKET
                tarjeta = "";
                //                inputStream = null;
                return;
            }
            //BUSCAR TARJETA 
            princip.tarjetatxt.setText("");
            princip.tarjetatxt.setText(tarjeta);
            princip.buscarTarjeta(puertoId.getName());
            tarjeta = "";
            //peaje.formas.SimpleWrite.llamar("COM3");
            return;

        } else {
            System.out.println("< 10 : " + tarjeta);
            if (tarjeta.contains("AEI")) {
//             tarjeta = tarjeta.replace("00", "");
                System.out.println(tarjeta + " " + new Date());
                tarjeta = "";
                imprimir("");
                abrirPuerta(princip.empresaObj.getEntra1());
                System.out.println("ABRIO PUERTA aeiou1: " + princip.empresaObj.getEntra1());
                return;

            }
            Long valor = null;
            try {
                valor = new Long(tarjeta);
            } catch (Exception e) {
                return;

            }
            if (puertoId.getName().equals(princip.empresaObj.getBarras())) { //VALIDO SALIDA DEL CARRO CON CODIGO DE BARRAS
                    tarjeta = valor + "";
                    for (int i = tarjeta.length(); i < 10; i++) {
                        tarjeta = "0" + tarjeta;
                    }
                    if (tarjeta.length() > 10) {
                        tarjeta = tarjeta.substring(0, 10);
                    }
            }
            System.out.println("ABRIO BARRAS: " + tarjeta);
            if (puertoId.getName().equals(princip.empresaObj.getBarras())) { //VALIDO SALIDA DEL CARRO CON CODIGO DE BARRAS
                princip.buscarTarjetaValidarSalida(puertoId.getName(), tarjeta);//ENVIO EL NUMERO DE TICKET
                tarjeta = "";
                //                inputStream = null;
                return;
            }

        }



    }

    public void abrirPuerta(String puerta) {
        System.out.println("local puerta: " + puerta);
        try {
            if (puerta == null) {
                puerta = "1";
            }

            LeerTarjeta ta = (LeerTarjeta) princip.puertoListo.get(0);
            ta.outputSream.write(puerta.trim().getBytes());
        } catch (IOException ex) {
            Logger.getLogger(LeerTarjeta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void imprimir(String impresoraLlega) {
        try {
            tarjeta = "";
            WorkingDirectory w = new WorkingDirectory();
            String ubicacionDirectorio = w.get() + separador;
            if (ubicacionDirectorio.contains("build")) {
                ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
            }
//            Empresa emp = princip.empresaObj;
            JasperReport masterReport = (JasperReport) JRLoader.loadObject(ubicacionDirectorio + "reportes" + separador + "ticket2.jasper");
            Empresa emp = (Empresa) adm.querySimple("Select o from Empresa as o");
            Factura fac = new Factura();
            fac.setPlaca("CLIENTE BOTON");
            fac.setFechaini(new Date());
            fac.setFecha(new Date());
            Boolean pasar = true;
            Integer numero = new Integer(emp.getDocumentoticket()) + 1;
            while (pasar) {
                List sihay = adm.query("Select o from Factura as o where o.ticket = '" + numero + "'");
                if (sihay.size() <= 0) {
                    pasar = false;

                    fac.setTicket("" + numero);
                    emp.setDocumentoticket((numero) + "");
                    adm.actualizar(emp);//GUARDO EMPRESA
                    adm.guardar(fac); // GUARDO FACTURA

                } else {
                    numero++;
                }

            }


//            Factura fac = (Factura) adm.querySimple("Select o from Factura as o where o.codigo = " + cod + " ");
            ArrayList detalle = new ArrayList();
            detalle.add(fac);
            FacturaSource ds = new FacturaSource(detalle);
            Map parametros = new HashMap();

            parametros.put("empresa", emp.getRazon());
            parametros.put("direccion", emp.getDireccion());
            parametros.put("telefono", emp.getTelefonos());
            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
            PrinterJob job = PrinterJob.getPrinterJob();
            /* Create an array of PrintServices */
            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
            int selectedService = 0;
            /* Scan found services to see if anyone suits our needs */
            String impre = emp.getImpresora() + impresoraLlega;
            for (int i = 0; i < services.length; i++) {
                String nombre = services[i].getName();
                if (nombre.contains(impre)) {
                    selectedService = i;
                }
            }
            job.setPrintService(services[selectedService]);
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
            MediaSizeName mediaSizeName = MediaSize.findMedia(4, 3.8f, MediaPrintableArea.INCH);
            printRequestAttributeSet.add(mediaSizeName);
            printRequestAttributeSet.add(new Copies(1));
            JRPrintServiceExporter exporter;
            exporter = new JRPrintServiceExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, masterPrint);
            /* We set the selected service and pass it as a paramenter */
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
            exporter.exportReport();
            if (princip.empresaObj.getWebcam()) {
                if (ubicacionDirectorio.contains("build")) {
                    ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
                }
                int resultado = princip.ver.Fotografiar(ubicacionDirectorio + "\\fotos", false, fac.getCodigo() + "");
                if (resultado == 0) {
                    JOptionPane.showMessageDialog(null, "Error en la Fotografia");
                }

            } else if (princip.empresaObj.getIpcam()) {
                if (ubicacionDirectorio.contains("build")) {
                    ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
                }

                fotografiarIp("" + fac.getCodigo() + ".jpg", ubicacionDirectorio + "\\fotos");


            }


//            JasperViewer viewer = new JasperViewer(masterPrint, false); //PARA VER EL REPORTE ANTES DE IMPRIMIR
//            viewer.show();
//            try {
//                JasperPrintManager.printPage(masterPrint, 0, false);//LE ENVIO A IMPRIMIR false NO MUESTRA EL CUADRO DE DIALOGO
//            } catch (JRException ex) {
//                ex.printStackTrace();
//            }
        } catch (Exception ex) {
            Logger.getLogger(frmTicket.class.getName()).log(Level.SEVERE, null, ex);
        }

//        } catch (JRException ex) {
//            ex.printStackTrace();
//        }
    }

    public void fotografiarIp(String nombre, String direccion) {
        princip.verIp.tomarFotoIp(direccion + separador + nombre, princip);
//        if (resultado == 0) {
//            JOptionPane.showMessageDialog(null, "Error en la Fotografia");
//        }
    }
}
