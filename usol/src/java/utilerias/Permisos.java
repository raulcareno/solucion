/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import jcinform.persistencia.Accesos;
import jcinform.persistencia.Empleados;
import jcinform.procesos.Administrador;

/**
 *
 * @author inform
 */
public class Permisos {

    public Permisos() {
    }
    
    
    public boolean verificarPermisoReporte(String idVariable, String descripcion, String accion, Boolean pantalla, String modulo) {

       
        if (idVariable == null) {
            return true;
        }
        idVariable = idVariable.replace("_", " ");
        List<Accesos> accesosList =  (List<Accesos>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("accesos");
        Empleados empleadoAc = (Empleados) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
            if(empleadoAc == null){
                return false;
            }
        if (idVariable.equals("-1")) {
            return true;
        }
        if (idVariable.toUpperCase().contains("LINEA")) {
            return true;
        }
         if(accesosList ==null){
             accesosList = new ArrayList<Accesos>();
             
         }
        for (Iterator<Accesos> it = accesosList.iterator(); it.hasNext();) {
            Accesos accesos = it.next();
//            int inicio = accesos.getModulo().indexOf("[");
//            int finales = accesos.getModulo().indexOf("]");
            String elmodulo = accesos.getVariable();
            try {
//                elmodulo = accesos.getModulo().substring(inicio + 1, finales);

            } catch (Exception e) {
                //System.out.println("error leve"+e);
                elmodulo = accesos.getModulo();
            }


            if (elmodulo.equals(idVariable)) {
//                 System.out.println("MODULO: "+elmodulo);
                if (accion.equals("ingresar")) {
                    return accesos.getIngresar();
                } else if (accion.equals("agregar")) {
                    return accesos.getAgregar();
                } else if (accion.equals("modificar")) {
                    return accesos.getModificar();
                } else if (accion.equals("eliminar")) {
                    return accesos.getEliminar();
                }
            }
        }
         
        if (empleadoAc.getIdPerfiles().getNombre().contains("ADMINIS")) {
            Accesos ac = new Accesos();
            Administrador adm = new Administrador();
            ac.setIdAccesos(adm.getNuevaClave("Accesos", "idAccesos"));
            if (pantalla) {
               
                ac.setModulo(""+modulo);
            } else {
                ac.setModulo("REPORTES");

            }
             ac.setVariable(idVariable);;

            ac.setAgregar(true);
            ac.setNombre(descripcion);
            ac.setIngresar(true);
            ac.setModificar(true);
            ac.setEliminar(Boolean.TRUE);
            ac.setIdPerfiles(empleadoAc.getIdPerfiles());
            if(adm.existe("Accesos","variable", idVariable,"nombre", descripcion," ").size()<=0) {
                adm.guardar(ac);
            }

            accesosList.add(ac);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("accesos");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("accesos",accesosList);
            
            //CAMBIO LOS ****************************
            ac = new Accesos();
            ac.setIdAccesos(adm.getNuevaClave("Accesos", "idAccesos"));
            if (pantalla) {
                ac.setModulo(""+modulo);
            } else {
                ac.setModulo("REPORTES");

            }
            ac.setVariable(idVariable);
            ac.setNombre(descripcion);
            ac.setAgregar(true);
            ac.setIngresar(true);
            ac.setModificar(true);
            ac.setEliminar(Boolean.TRUE);
            ac.setIdPerfiles(null);
            if(adm.existe("Accesos","variable", idVariable,"nombre", descripcion," and o.idPerfiles is null").size()<=0) {
                adm.guardar(ac);
            }
            //**********************
            
            

            return true;
        } else {
            Accesos ac = new Accesos();
            Administrador adm = new Administrador();
            ac.setIdAccesos(adm.getNuevaClave("Accesos", "idAccesos"));
            if (pantalla) {
                ac.setModulo(idVariable);
                ac.setModulo(""+modulo);
            } else {
                ac.setModulo("REPORTES");
            }
           ac.setVariable(idVariable);
           ac.setNombre(descripcion);
            ac.setAgregar(false);
            ac.setIngresar(false);
            ac.setModificar(false);
            ac.setEliminar(false);
            ac.setIdPerfiles(empleadoAc.getIdPerfiles());
//            if(adm.query("Select o from Accesos as o where o.modulo = '"+ac.getModulo()+"' and perfil is null ").size()>0)
            if(adm.existe("Accesos","variable", idVariable,"nombre", descripcion," ").size()<=0) {
                adm.guardar(ac);
            }
            accesosList.add(ac);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("accesos");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("accesos",accesosList);
            
            return false;
        }

        //return false;

    }

}
