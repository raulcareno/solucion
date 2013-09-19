/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import jcinform.persistencia.Institucion;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRPptxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@SessionScoped
public class ExportarReportesSource {

     JRDataSource datosReporte = null;
    JasperPrint jasperPrint;
    String ubicacionReportes = "";
    String path="";
    Map parametros = null;
    Institucion inst;
    String nombreReporte;
    JasperPrint masterPrint;
    public ExportarReportesSource() {
        inst = (Institucion) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("institucion");
    }
    public ExportarReportesSource(JRDataSource datos, String reporte,Map parametros) {
        inst = (Institucion) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("institucion");
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        //ubicacionReportes = ctx.getRealPath("/WEB-INF/reporte/" + reporte + ".jasper");
        ubicacionReportes = (inst.getReportes() +File.separator +reporte + ".jasper");
        nombreReporte = reporte;
        path= ctx.getRealPath(inst.getReportes());
        this.datosReporte = datos;
        parametros.put("subReporte",path);
        this.parametros = parametros;
    }
    public void init() throws JRException {
        //JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(datosReporte);
        jasperPrint = JasperFillManager.fillReport(ubicacionReportes, parametros, datosReporte);        
    }

    public void PRINT() throws JRException, IOException {
             init();
            //HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            //httpServletResponse.addHeader("Content-disposition", "attachment; filename="+nombreReporte+".pdf");
            //ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            //JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
            JasperPrintManager.printPage(jasperPrint, 0, true);    
    }
    
    public void PDF() throws JRException, IOException {
        try {

             init();
            HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
            httpServletResponse.addHeader("Content-disposition", "inline; filename="+nombreReporte+".pdf");
            ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
            //httpServletResponse.addHeader("Content-disposition", "inline; filename="+nombreReporte+".pdf");
            httpServletResponse.setContentType("application/pdf");
//            servletOutputStream.print("<script language=\"javascript\"> window.print();</script>");
            JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);
                        
        } catch (Exception e) {
        }
            
    }

    public void DOCX() throws JRException, IOException {
        init();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename="+nombreReporte+".docx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRDocxExporter docxExporter = new JRDocxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
    }

    public void XLSX() throws JRException, IOException {
        init();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename="+nombreReporte+".xlsx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRXlsxExporter docxExporter = new JRXlsxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
    }

    public void ODT(ActionEvent actionEvent) throws JRException, IOException {
        init();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename="+nombreReporte+".odt");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JROdtExporter docxExporter = new JROdtExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
    }

    public void PPT(ActionEvent actionEvent) throws JRException, IOException {
        init();
        HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        httpServletResponse.addHeader("Content-disposition", "attachment; filename="+nombreReporte+".pptx");
        ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
        JRPptxExporter docxExporter = new JRPptxExporter();
        docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
        docxExporter.exportReport();
    }
}
