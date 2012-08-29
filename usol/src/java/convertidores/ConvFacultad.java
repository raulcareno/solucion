package convertidores;

import bean.CantonBean;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import jcinform.persistencia.Facultad;
 
@FacesConverter(value = "ConvFacultad", forClass = Facultad.class)
public class ConvFacultad  implements Converter {

 
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        try {
           Facultad cat = new Facultad(Integer.parseInt(value));
            return cat;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CantonBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Facultad) {
            if (((Facultad)value) == null) {
                return null;
            }
            if (((Facultad)value).getIdFacultad() == null) {
                return null;
            }
            return Integer.toString(((Facultad)value).getIdFacultad());
        }else{
            return null;
            
        }
        
         
        
            
        
    }
 
}
