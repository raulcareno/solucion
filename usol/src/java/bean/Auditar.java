/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import jcinform.persistencia.Auditoria;
import jcinform.persistencia.Empleados;
import jcinform.procesos.Administrador;

/**
 *
 * @author Geovanny
 */
public class Auditar {

    public Auditar() {
    }
    
      public String auditar(Administrador adm,String pantalla, String accion,String registros,String codigosmodificados) {
        FacesContext context = FacesContext.getCurrentInstance();
           Auditoria object = new Auditoria();
            try {
            String ipRemota  = ((HttpServletRequest) context.getExternalContext().getRequest()).getRemoteAddr();
            String ipHost = ((HttpServletRequest) context.getExternalContext().getRequest()).getRemoteHost();
            String ipUsuario = ((HttpServletRequest) context.getExternalContext().getRequest()).getRemoteUser();
 
            object.setAccion(accion);
            object.setDescripcion("");
            object.setFecha(adm.Date());
            Empleados empleadoAc = (Empleados) context.getExternalContext().getSessionMap().get("user");
            object.setIdEmpleados(empleadoAc);
            object.setIp(ipRemota);
//            object.setPc(ipHost);
            object.setTabla(pantalla);
            object.setRegistro(registros);
            object.setCodigosmodificados(codigosmodificados); 
            
            
                    object.setIdAuditoria(adm.getNuevaClave("Auditoria", "idAuditoria"));
                    adm.guardar(object);
                     
                     
  
            } catch (Exception e) {
                System.out.println("no se audito.."+e);
            }
        return null;
    }
    
}
