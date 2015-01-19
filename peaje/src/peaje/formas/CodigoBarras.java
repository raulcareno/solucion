///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package peaje.formas;
//
//import hibernate.Empresa;
//import hibernate.Factura;
//import hibernate.cargar.WorkingDirectory;
//import java.awt.print.PrinterJob;
//import java.io.*;
//import java.util.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.comm.*;
//import javax.print.PrintService;
//import javax.print.PrintServiceLookup;
//import javax.print.attribute.HashPrintRequestAttributeSet;
//import javax.print.attribute.PrintRequestAttributeSet;
//import javax.print.attribute.standard.Copies;
//import javax.print.attribute.standard.MediaPrintableArea;
//import javax.print.attribute.standard.MediaSize;
//import javax.print.attribute.standard.MediaSizeName;
//import net.sf.jasperreports.engine.JRExporterParameter;
//import net.sf.jasperreports.engine.JasperFillManager;
//import net.sf.jasperreports.engine.JasperPrint;
//import net.sf.jasperreports.engine.JasperReport;
//import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
//import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
//import net.sf.jasperreports.engine.util.JRLoader;
//import hibernate.cargar.Administrador;
//import hibernate.cargar.GeneraXMLPersonal;
//import sources.FacturaSource;
//
//public class CodigoBarras implements Runnable{
//
//    static CommPortIdentifier puertoId;
////  portList;
//    InputStream inputStream;
//     Thread readThread;
//    public String tarjeta;
//    frmPrincipal princip;
//    Administrador adm = new Administrador(GeneraXMLPersonal.user);
//
//    @SuppressWarnings("static-access")
//    public CodigoBarras() {
//
//        System.out.println("ACA VAN LOS PROCESOS");
//        readThread = new Thread(this);
//        readThread.start();
//    }
//
//    public void run() {
//        try {
//            Thread.sleep(20000);
//        } catch (InterruptedException e) {
//        }
//    }
//
//    public int inicio = 0;
//
//    public void serialEvent(SerialPortEvent event) {
////    if(tarjeta.length()>10){
////        tarjeta = "";
////    }
//
//        //System.out.println(""+tarjeta);
//        
//        if (tarjeta.length() >= 11) {
//
//            //System.out.println("" + tarjeta);
//            
//            if(puertoId.getName().equals(princip.empresaObj.getPuerto())){
//                imprimir(56);
//                return;
//            }
//            princip.tarjetatxt.setText("");
//            princip.tarjetatxt.setText(tarjeta);
//            princip.buscarTarjeta(puertoId.getName());
//            tarjeta = "";
//            //peaje.formas.SimpleWrite.llamar("COM3");
//            
//            return;
//        }
//
//
//    }
//
//        public void imprimir(int cod) {
//        try {
//             WorkingDirectory w = new WorkingDirectory();
//             String ubicacionDirectorio = w.get()+"\\";
//                if(ubicacionDirectorio.contains("build"))
//                    ubicacionDirectorio = ubicacionDirectorio.replace("\\build", "");
//            Empresa emp = princip.empresaObj;
//            JasperReport masterReport = (JasperReport) JRLoader.loadObject(ubicacionDirectorio+"reportes\\ticket2.jasper");
//
//            Factura fac = (Factura) adm.querySimple("Select o from Factura as o where o.codigo = "+cod+" ");
//            ArrayList detalle = new ArrayList();
//            detalle.add(fac);
//            FacturaSource ds = new FacturaSource(detalle);
//            Map parametros = new HashMap();
//
//            parametros.put("empresa", emp.getRazon());
//            parametros.put("direccion", emp.getDireccion());
//            parametros.put("telefono", emp.getTelefonos());
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
//            PrintRequestAttributeSet  printRequestAttributeSet = new HashPrintRequestAttributeSet();
//            MediaSizeName mediaSizeName = MediaSize.findMedia(4, 4, MediaPrintableArea.INCH);
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
//
//
////            JasperViewer viewer = new JasperViewer(masterPrint, false); //PARA VER EL REPORTE ANTES DE IMPRIMIR
////            viewer.show();
////            try {
////                JasperPrintManager.printPage(masterPrint, 0, false);//LE ENVIO A IMPRIMIR false NO MUESTRA EL CUADRO DE DIALOGO
////            } catch (JRException ex) {
////                ex.printStackTrace();
////            }
//        } catch (Exception ex) {
//            Logger.getLogger(frmTicket.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
////        } catch (JRException ex) {
////            ex.printStackTrace();
////        }
//    }
//}
