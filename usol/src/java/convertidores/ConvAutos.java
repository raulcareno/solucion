package convertidores;

import bean.CantonBean;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import jcinform.persistencia.Autos;

 
@FacesConverter(value = "ConvAutos", forClass = Autos.class)
public class ConvAutos  implements Converter {

 
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        try {
           Autos cat = new Autos(Integer.parseInt(value));
            return cat;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CantonBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (((Autos)value) == null) {
            return null;
        }
         if (((Autos)value).getIdAutos() == null) {
            return null;
        }
        
            return Integer.toString(((Autos)value).getIdAutos());
        
    }
 
}
