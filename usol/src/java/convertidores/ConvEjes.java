package convertidores;

import java.util.logging.Level;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
 
import jcinform.persistencia.Ejes;


 
@FacesConverter(value = "ConvEjes", forClass = Ejes.class)
public class ConvEjes  implements Converter {

  
    
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        try {
           Ejes cat = new Ejes(Integer.parseInt(value));
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
        if(value instanceof Ejes){
           if (value == null) {
            return null;
        }
        if(((Ejes)value).getIdEjes() ==null){
            return null;
        }
        
            return Integer.toString(((Ejes)value).getIdEjes());  
        }else{
            return null;
        }
       
        
    }
 
}
