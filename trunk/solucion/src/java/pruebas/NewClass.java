/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pruebas;

import java.util.List;
import jcinform.persistencia.Notanotas;
import jcinform.persistencia.Periodo;
import jcinform.procesos.Administrador;

/**
 *
 * @author geovanny
 */
public class NewClass {
 
public void hola(){
    byte[] as;
    Administrador adm = new Administrador();
    Periodo periodo = null;
            List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
                " where o.sistema.periodo.codigoper = '"+periodo.getCodigoper()+"'  " +
                "and o.sistema.promediofinal =  'PF'");

}
}
