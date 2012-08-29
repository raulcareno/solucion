/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

 
  
import javax.faces.application.FacesMessage;  
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;  
  
import org.primefaces.event.FileUploadEvent;  
  
/**
 *
 * @author inform
 */
@ManagedBean
@RequestScoped
public class FileUploadController {  
  
    public void handleFileUpload(FileUploadEvent event) {  
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");  
        FacesContext.getCurrentInstance().addMessage("sdfsad", msg);  
    }  
}  
  