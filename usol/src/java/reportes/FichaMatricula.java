/**
 * Copyright (C) 2010, 2011 by Marco Tupiza.
 *
 * Original Author: Marco V. Tupiza A. (Aplinfo), mtupiza@gmail.com
 * Contributor(s):
 *
 */
package reportes;
  
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.JRException;
import org.apache.log4j.spi.LoggerFactory;
 
import sun.java2d.loops.DrawGlyphListAA.General;

/**
  * @author geovanny
 */
@ManagedBean
@ViewScoped
public class FichaMatricula {
 
    Date desde;
    Date hasta;
    List<General> resultadosTotales;
    List<General> resultadosIndividual;
Integer totalZona;
Integer totalIndividual;
    public FichaMatricula() {
        super();
    }
     
 
   public void individual(String tipo) throws JRException{
        try {
            Map map = new HashMap();
            map.put("tituloReporte", "FICHA DE MATRICULA");
            map.put("titulo1", "USUARIO");
            map.put("titulo2", "# DE INFORMES");
            ExportarReportes ex = new ExportarReportes(resultadosIndividual, "reporte",map);
            if(tipo.equals("PDF")){
                ex.PDF();
            }else if(tipo.equals("DOCX")){
                ex.DOCX();
            }else if(tipo.equals("XLS")){
                ex.XLSX();
            }
        } catch (Exception ex1) {
            java.util.logging.Logger.getLogger(FichaMatricula.class.getName()).log(Level.SEVERE, null, ex1);
        }
   }

     

    public String devolverFecha(Date fecha, Boolean tipo) {//SI ES TRUE AÑO MES DIA, CASO CONTRARIO DIA MES AÑO
        String fecha2 = (fecha.getYear() + 1900) + "";
        String m = (1+fecha.getMonth()) + "";
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
