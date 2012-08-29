package convertidores;

import bean.CantonBean;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import jcinform.persistencia.TipoMateria;

/**
 * Clase Converter para la entidad SicCalle.
 * <p>
 * <H6>Soporte:APLINFO <I>mtupiza@gmail.com</I></H6>
 * @author Marco Tupiza mtupiza@gmail.com
 * @author APLINFO
 * @version 1.0 17/09/2010
 */
@FacesConverter(value = "ConvTipoMateria", forClass = TipoMateria.class)
public class ConvTipoMateria  implements Converter {

 
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        try {
           TipoMateria cat = new TipoMateria(Integer.parseInt(value));
            return cat;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CantonBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof TipoMateria) {
            if (((TipoMateria)value) == null) {
                return null;
            }
            if (((TipoMateria)value).getIdTipoMateria() == null) {
                return null;
            }
            return Integer.toString(((TipoMateria)value).getIdTipoMateria());
        }else{
            return null;
            
        }
        
         
        
            
        
    }
 
}
