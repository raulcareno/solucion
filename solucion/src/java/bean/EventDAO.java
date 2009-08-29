/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.ImageIcon;
import jcinform.persistencia.*;
import jcinform.persistencia.Global;
import jcinform.procesos.Administrador;
import org.zkoss.image.AImage;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zul.Vbox;

/**
 *
 * @author geovanny
 */
public class EventDAO {
Administrador adm;
private List<Global> listado;
 
 public EventDAO() {
  adm = new Administrador();
  listado = new  ArrayList<Global>();

 }
 
 //The implementation is ommited, please refer to the source code.
 public List buscarAccesos(String perfil){
     List<Accesos> listado = new ArrayList<Accesos>();
    
    try{
    Integer perfil2 = new Integer(perfil);
    listado = adm.query("Select o from Accesos as o where o.perfil.codigo = '"+perfil2+"'");
  
    Boolean bandera= false;
   if(listado.size()<=0){
        listado = adm.query("Select o from Accesos as o where o.perfil is null");
        bandera = true;
    }
     for (Iterator<Accesos> it = listado.iterator(); it.hasNext();) {
         Accesos accesos = it.next();

     }
    for (Accesos accesos : listado) {
        
            if(bandera){
                accesos.setCodigoacc(0);
            }
     }
    }catch(Exception e){}
    
     return listado;
 } 
 
 public List perfiles(){
     List<Global> listado2 = new ArrayList();
     Global g =  new Global(-1);
     g.setDescripcion("[Seleccione]");
     listado2.add(g);
     List<Global> listado = adm.query("Select o from Global as o where o.grupo = 'PER'");
     for (Iterator<Global> it = listado.iterator(); it.hasNext();) {
         Global global = it.next();
         listado2.add(global);

     }
     listado = null;
      return listado2;
    }
 public boolean delete(Event evt){ return true;} 
 public boolean guardar(Event evt){
 Object obj= evt.getData();    
 System.out.println("objecto"+obj);
 Empleados a = new Empleados();
 a.getClass();
 adm.eliminarObjeto(Empleados.class, obj);
     return true;
 }
  public boolean guardar(Global global){
         if(!global.getCodigo().equals(null) || !global.getCodigo().equals(0) ){
             adm.actualizar(global);
         }else{
             adm.guardar(global);
         }
     return true;
 }
 public boolean update(Event evt){return true;}
}
