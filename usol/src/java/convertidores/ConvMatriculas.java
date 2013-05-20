package convertidores;

import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import jcinform.persistencia.Matriculas;


 
@FacesConverter(value = "ConvMatriculas", forClass = Matriculas.class)
public class ConvMatriculas  implements Converter {

  
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        try {
           Matriculas cat = new Matriculas(Integer.parseInt(value));
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
        if(value instanceof Matriculas){
           if (value == null) {
            return null;
        }
        if(((Matriculas)value).getIdMatriculas() ==null){
            return null;
        }
        
            return Integer.toString(((Matriculas)value).getIdMatriculas());  
        }else{
            return null;
        }
       
        
    }
 
}
