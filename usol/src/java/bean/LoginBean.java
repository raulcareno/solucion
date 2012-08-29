package bean;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Iterator;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import jcinform.persistencia.Empleados;
import jcinform.procesos.Administrador;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@RequestScoped
public class LoginBean {
public  String usuario;
public String clave;
Administrador adm;
    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        usuario = "";
        clave = "";
        adm = new Administrador();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("accesos");
       FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
 public String loginAction() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) context.getExternalContext().getSession(true);
        String remoteAddr = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteAddr();
        String host = ((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest()).getRemoteHost();
        try {
            Empleados user = (Empleados) adm.ingresoSistema(usuario, clave);
            
            if(user == null){
                //FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage("El nombre de Usuario o Contraseña están incorrectas...!"));
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "login").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"El nombre de Usuario o Contraseña están incorrectas...!","El nombre de Usuario o Contraseña están incorrectas...!"));
                    return null;
            }else{
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("menu");
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.jspx");
            }
            
            
 
//            inicializar();
        } catch (Exception e) {
            //FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "datosform").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.toString(), bundle.getString("msg.iniciarSesion.error")));
            //log.error("loginAction() ERROR " + e);
            return null;
        }
        usuario = "";
        clave = "";
        try {
   
 
            String url = "index";
//            System.out.println(url);
            //context.getExternalContext().dispatch(url);
            //String encodeURL = context.getExternalContext().encodeResourceURL(url);
            //context.getExternalContext().redirect(encodeURL);
        } catch (Exception e) {
            //log.error("loginAction() ERROR " + e);
        } finally {
            context.responseComplete();
        }
        return "index";
    }
    
      protected UIComponent findComponent(UIComponent c, String id) {
        if (id.equals(c.getId())) {
            return c;
        }
        Iterator<UIComponent> kids = c.getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent found = findComponent(kids.next(), id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
