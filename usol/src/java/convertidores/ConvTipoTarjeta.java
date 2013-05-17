package convertidores;

import bean.CantonBean;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import jcinform.persistencia.TipoTarjeta;

 
@FacesConverter(value = "ConvTipoTarjeta", forClass = TipoTarjeta.class)
public class ConvTipoTarjeta  implements Converter {

 
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        try {
           TipoTarjeta cat = new TipoTarjeta(Integer.parseInt(value));
            return cat;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CantonBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (((TipoTarjeta)value) == null) {
            return null;
        }
         if (((TipoTarjeta)value).getIdTipoTarjeta() == null) {
            return null;
        }
        
            return Integer.toString(((TipoTarjeta)value).getIdTipoTarjeta());
        
    }
 
}
