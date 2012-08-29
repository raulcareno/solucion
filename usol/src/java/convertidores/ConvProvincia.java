package convertidores;

import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import jcinform.persistencia.Provincia;

/**
 * Clase Converter para la entidad SicCalle.
 * <p>
 * <H6>Soporte:APLINFO <I>mtupiza@gmail.com</I></H6>
 * @author Marco Tupiza mtupiza@gmail.com
 * @author APLINFO
 * @version 1.0 17/09/2010
 */
@FacesConverter(value = "ConvProvincia", forClass = Provincia.class)
public class ConvProvincia  implements Converter {

 

    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        try {
           Provincia cat = new Provincia(Integer.parseInt(value));
            return cat;
        } catch (Exception e) {
           java.util.logging.Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
 
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value == null) {
            return null;
        }
        if(((Provincia)value).getIdProvincia() ==null){
            return null;
        }
        
            return Integer.toString(((Provincia)value).getIdProvincia());
        
    }
 
}
