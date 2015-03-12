/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package siscontrol;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import siscontrol.cnx.ReporteFacturaDataSource;
import siscontrol.mail.email;

/**
 *
 * @author Geovanny
 */
public class reporteEmail {

    public reporteEmail() {
    }

    public void abc(ArrayList detalles, Map map, String nombre, String destinatarios, String directorio,String mensaje, String tema) {
        try {
            ReporteFacturaDataSource ds = new ReporteFacturaDataSource(detalles);
            String archivoName = directorio+"/proforma" + nombre + ".pdf";
            InputStream input = new FileInputStream(new File(directorio+"/proforma.jasper"));
            JasperPrint print = JasperFillManager.fillReport(input, map, ds);
        //JasperPrint print = JasperFillManager.fillReport(input, map, jasperReports);
            //fillReport(input, map, jasperReports); 
//        ByteArrayOutputStream output = new ByteArrayOutputStream();
            OutputStream outputfile = new FileOutputStream(new File(archivoName));
            ByteArrayOutputStream output = new ByteArrayOutputStream();

//        
            JRExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output); // your output goes here
            exporter.exportReport();
//        //outputfile.write(b);
            outputfile.write(output.toByteArray());
            outputfile.close();

            detalles = null;
            map = null;

            email e = new email();
            StringTokenizer tokens = new StringTokenizer(destinatarios, ";");
            ArrayList correosAenviar = new ArrayList();
            int i = 0;
            while (tokens.hasMoreTokens()) {
                String str = tokens.nextToken();
                str = str.replace(" ", "");
                if (!str.equals("") && str.contains("@")) {
                    if (!correosAenviar.contains(str)) {
                        if (validaEmail(str.replace(" ", ""))) {
                            correosAenviar.add(str.replace(" ", ""));
                            System.out.println(str);
                        }
                    }
                }
                i++;
            }
            e.soporteTecnico("geova", archivoName, correosAenviar,mensaje,tema);
        } catch (Exception e) {
            System.out.println("" + e);
        }
    }

    private boolean validaEmail(String strText) {
        int iSw = 0;
        for (int i = 0; i < strText.length(); i++) {
            if (strText.charAt(i) == ' ') {
                //   strMsg = "ERROR: El texto no debe contener espacios en blanco";
                // JOptionPane.showMessageDialog(new JFrame(), strMsg);
                return false;
            }
            if (strText.charAt(i) == '@') {
                iSw++;
            }
        }
        if (iSw != 1) {
            //  strMsg = "ERROR: La direccion email no es correcta" ;
            //  JOptionPane.showMessageDialog(new JFrame(), strMsg);
            return false;
        }
        return true;
    }

}

        // coding For Excel:
//            JRPdfExporter exp = new JRPdfExporter();
//            exp.setParameter(JRExporterParameter.INPUT_URL, input);
//         JRXlsExporter exporterXLS = new JRXlsExporter();
//         exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
//         exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, output);
//         exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
//         exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
//         exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
//         exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
//         exporterXLS.exportReport();
//         outputfile.write(output.toByteArray()); 
////         
//         String reportName = "myreport";
//        Map<String, Object> parameters = new HashMap<String, Object>();
//        connection = new ConnectionFactory().getConnection(); // opens a jdbc connection
//        // compiles jrxml
//        JasperCompileManager.compileReportToFile(reportName + ".jrxml");
//        // fills compiled report with parameters and a connection
//        JasperPrint print = JasperFillManager.fillReport(reportName + ".jasper", parameters, connection);
// exports report to pdf

