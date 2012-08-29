package convertidores;


import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
  

 
 @FacesConverter(forClass=Integer.class,value="JsfConvInteger")
public class JsfConvInteger implements Converter {

     @Override
   public String getAsString(FacesContext context, UIComponent component, Object value) {
      return  value.toString();
   }
     @Override
   public Object getAsObject(FacesContext arg0, UIComponent arg1, String value) {
    if(value==null ){
        return 0;
    }
         try {
             if(value.toString().equals("")){
                    return 0;
                }
         } catch (Exception e) {
              return 0;
         }
      return Integer.parseInt(value);
   }
}
