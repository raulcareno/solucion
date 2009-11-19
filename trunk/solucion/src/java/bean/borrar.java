/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.util.List;
import jcinform.persistencia.Correos;
import jcinform.persistencia.Estudiantes;
import jcinform.procesos.Administrador;

/**
 *
 * @author geovanny
 */
public class borrar {

        public static void bandejaEstudiante(Estudiantes est){
        Administrador adm = new Administrador();
        List<Correos> a = adm.query("Select o from Correos as o where o.destinatario = '"+est.getCodigoest()+"' " +
        "and  o.tiporemitente = 'P' and o.eliminado = false and o.archivado = false ");
            //System.out.println(""+a.get(0).getTema() + " "+ a.get(0).getRemitenten());
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        bandejaEstudiante(new Estudiantes(1));
    }

}
