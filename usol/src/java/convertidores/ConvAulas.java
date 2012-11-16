package convertidores;

import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import jcinform.persistencia.Aulas;


 
@FacesConverter(value = "ConvAulas", forClass = Aulas.class)
public class ConvAulas  implements Converter {

  
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        try {
           Aulas cat = new Aulas(Integer.parseInt(value));
            return cat;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
 

    /**
     * Convierte un Objeto java a String.
     * @param context el contexto de aplicación
     * @param component el componente asociado
     * @param value el objeto Java
     * @return el String
     */
    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if(value instanceof Aulas){
           if (value == null) {
            return null;
        }
        if(((Aulas)value).getIdAulas() ==null){
            return null;
        }
        
            return Integer.toString(((Aulas)value).getIdAulas());  
        }else{
            return null;
        }
       
        
    }
 
}
