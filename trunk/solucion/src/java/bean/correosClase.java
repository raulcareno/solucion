/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.util.Date;
import java.util.List;
import jcinform.persistencia.Correos;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Estudiantes;
import jcinform.procesos.Administrador;
import org.zkoss.zul.Listitem;

/**
 *
 * @author geovanny
 */
public class correosClase {

    public correosClase(){
    }
    
    public void bandejaEstudiante(Estudiantes est){
        Administrador adm = new Administrador();
        List<Correos> a = adm.query("Select o from Correos as o where o.destinatario = '"+est.getCodigoest()+"' " +
        "and  o.tiporemitente = 'P' and o.eliminado = false and o.archivado = false ");
    }

    
    public void bandejaProfesor(Empleados est){
        Administrador adm = new Administrador();
        List<Correos> a = adm.query("Select o from Correos as o where o.destinatario = '"+est.getCodigoemp()+"' " +
        "and  o.tiporemitente = 'D' and o.eliminado = false and o.archivado = false  ");
    }

    public void enviar(){
        Correos cor = new Correos(1);
        cor.setFecha(new Date());
        cor.setTema("Temita");
        cor.setLeido(false);
        cor.setEliminado(false);
        cor.setArchivado(false);
    }

}
