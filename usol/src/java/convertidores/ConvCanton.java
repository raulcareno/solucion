package convertidores;

import bean.CantonBean;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import jcinform.persistencia.Canton;
import jcinform.persistencia.Provincia;

/**
 * Clase Converter para la entidad SicCalle.
 * <p>
 * <H6>Soporte:APLINFO <I>mtupiza@gmail.com</I></H6>
 * @author Marco Tupiza mtupiza@gmail.com
 * @author APLINFO
 * @version 1.0 17/09/2010
 */
@FacesConverter(value = "ConvCanton", forClass = Canton.class)
public class ConvCanton  implements Converter {

  
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        try {
           Canton cat = new Canton(Integer.parseInt(value));
            return cat;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
//     public Object getAsObject(FacesContext context, UIComponent component, String value) {
//        AcaInstitucion cat = new AcaInstitucion(Integer.parseInt(value));
//        return cat;
//    }
// 
//    public String getAsString(FacesContext context, UIComponent component, Object value) {
//        return Integer.toString(((AcaInstitucion)value).getInsCodigo());
//    }

    /**
     * Convierte un Objeto java a String.
     * @param context el contexto de aplicación
     * @param component el componente asociado
     * @param value el objeto Java
     * @return el String
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        if(((Canton)value).getIdCanton() ==null){
            return null;
        }
        
            return Integer.toString(((Canton)value).getIdCanton());
        
    }
 
}
