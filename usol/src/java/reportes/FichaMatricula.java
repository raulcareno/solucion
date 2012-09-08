/**
 * Copyright (C) 2010, 2011 by Marco Tupiza.
 *
 * Original Author: Marco V. Tupiza A. (Aplinfo), mtupiza@gmail.com
 * Contributor(s):
 *
 */
package reportes;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import jcinform.persistencia.Estudiantes;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.primefaces.model.StreamedContent;

/**
 * @author geovanny
 */
@ManagedBean
@ViewScoped
public class FichaMatricula {

    Date desde;
    Date hasta;
    Integer totalZona;
    Integer totalIndividual;

    public FichaMatricula() {
        super();
    }

    public String getArchivoPDF() throws JRException {
         List datosReporte = new ArrayList();
        Estudiantes est = new Estudiantes();
        for (int i = 0; i < 10; i++) {
            est.setApellidoPaterno("jadan" + i);
            est.setApellidoMaterno("ortega " + i);
            est.setNombre("geovanny " + i);
            datosReporte.add(est);
        }


        Map parameters = new HashMap();
        parameters.put("ALGUN_PARAMETRO", "");
        JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(datosReporte);

        InputStream inputStream = null;
        try {
            ByteArrayOutputStream Teste = new ByteArrayOutputStream();
//            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(getClass().getClassLoader().getResourceAsStream(path.trim()));
            JasperPrint print = JasperFillManager.fillReport("c:/reportes/fichaMatricula.jasper", parameters, beanCollectionDataSource);
            JRExporter exporter = new net.sf.jasperreports.engine.export.JRPdfExporter();
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, Teste);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, print);
            exporter.exportReport();
            inputStream = new ByteArrayInputStream(Teste.toByteArray());

            ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();

            String fileFoto = servletContext.getRealPath("") + File.separator + "fotosMatriculas" + File.separator + "miArchivo.pdf";
            try {
                File f = new File(fileFoto);
                OutputStream out = new FileOutputStream(f);
                byte buf[] = new byte[1024];
                int len;
                while ((len = inputStream.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                inputStream.close();
                System.out.println("\nFile is created...................................");
            } catch (IOException e) {
            }
        }catch(Exception ab){
            System.out.println(""+ab);
        }
        return  "miArchivo.pdf";
    }

    //return new DefaultStreamedContent(inputStream,"application/pdf");
 
    public String devolverFecha(Date fecha, Boolean tipo) {//SI ES TRUE AÑO MES DIA, CASO CONTRARIO DIA MES AÑO
        String fecha2 = (fecha.getYear() + 1900) + "";
        String m = (1 + fecha.getMonth()) + "";
        while (m.length() < 2) {
            m = "0" + m;
        }
        String d = fecha.getDate() + "";
        while (d.length() < 2) {
            d = "0" + d;
        }
        if (tipo) { //AÑO MES DÍA
            fecha2 = fecha2 + m + d;
            return fecha2;
        } else { // DIA MES AÑO
            fecha2 = d + m + fecha2;
            return fecha2;
        }

    }
}
