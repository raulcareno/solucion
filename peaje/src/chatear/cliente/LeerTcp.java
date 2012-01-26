/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package chatear.cliente;

import peaje.formas.*;
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
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.JOptionPane;
import sources.FacturaSource;

public class LeerTcp implements Runnable {

    CommPortIdentifier puertoId;
//  portList;
    InputStream inputStream;
    OutputStream outputSream;
    SerialPort serialPort;
    Thread readThread;
    public String tarjeta;
    private Socket socket;
    frmPrincipal princip;
    Administrador adm = new Administrador(GeneraXMLPersonal.user);
    String separador = File.separatorChar + "";

    public LeerTcp() {
    }

    public void abrir() {
        try {
            socket = new Socket("192.168.0.7", 1024);
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(LeerTcp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LeerTcp.class.getName()).log(Level.SEVERE, null, ex);
        }

            
    }

     
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
        }
    }
  
}
