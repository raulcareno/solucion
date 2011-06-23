/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.MediaSizeName;
import jcinform.conexion.Administrador;
import jcinform.conexion.ubicacionDirectorio;
import jcinform.persistencia.Empresa;
import jcinform.persistencia.Factura;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class impresion {
    
//    public void imprimir(int cod, Empresa emp) {
//Administrador adm = new Administrador();
////                    viewer.show();
//        try {
//            
//            if (ubicacionDirectorio.contains("build")) {
//                ubicacionDirectorio = ubicacionDirectorio.replace(separador + "build", "");
//            }
//
//            JasperReport masterReport = (JasperReport) JRLoader.loadObject(ubicacionDirectorio + "reportes" + separador + "ticket.jasper");
//
//            Factura fac = (Factura) adm.querySimple("Select o from Factura as o where o.codigo = " + cod + " ");
//            ArrayList detalle = new ArrayList();
//            detalle.add(fac);
////            Factura ds = new Factura(detalle);
//            Map parametros = new HashMap();
//
//            parametros.put("empresa","empresacargada");
//            parametros.put("direccion","....");
//            parametros.put("telefono","..");
//            JasperPrint masterPrint = JasperFillManager.fillReport(masterReport, parametros, ds);
//            PrinterJob job = PrinterJob.getPrinterJob();
//            /* Create an array of PrintServices */
//            PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
//            int selectedService = 0;
//            /* Scan found services to see if anyone suits our needs */
//            for (int i = 0; i < services.length; i++) {
//                String nombre = services[i].getName();
//                if (nombre.contains(emp.getImpticket())) {
//                    selectedService = i;
//                }
//            }
//            job.setPrintService(services[selectedService]);
//            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
//            MediaSizeName mediaSizeName = MediaSize.findMedia(3.08F, 3.70F, MediaPrintableArea.INCH);
//            //MediaSizeName mediaSizeName = MediaSize.findMedia(3F,3F, MediaPrintableArea.INCH);
//            printRequestAttributeSet.add(mediaSizeName);
//            printRequestAttributeSet.add(new Copies(1));
//            JRPrintServiceExporter exporter;
//            exporter = new JRPrintServiceExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, masterPrint);
//            /* We set the selected service and pass it as a paramenter */
//            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, services[selectedService]);
//            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, services[selectedService].getAttributes());
//            exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
//            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
//            exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
//            exporter.exportReport();
// 
//        } catch (Exception ex) {
//            Logger.getLogger(impresion.class.getName()).log(Level.SEVERE, null, ex);
//        }
// 
//    }

}
