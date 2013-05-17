package convertidores;

import bean.CantonBean;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import jcinform.persistencia.OtrosIngresos;

 
@FacesConverter(value = "ConvOtrosIngresos", forClass = OtrosIngresos.class)
public class ConvOtrosIngresos  implements Converter {

 
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        try {
           OtrosIngresos cat = new OtrosIngresos(Integer.parseInt(value));
            return cat;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CantonBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (((OtrosIngresos)value) == null) {
            return null;
        }
         if (((OtrosIngresos)value).getIdOtrosIngresos() == null) {
            return null;
        }
        
            return Integer.toString(((OtrosIngresos)value).getIdOtrosIngresos());
        
    }
 
}
