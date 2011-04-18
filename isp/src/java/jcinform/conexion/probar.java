/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jcinform.conexion;

import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Canton;

/**
 *
 * @author Ismael Jadan
 */
public class probar {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Administrador adm = new Administrador();
        List listado = adm.query("Select o from Canton as o ");
        for (Iterator it = listado.iterator(); it.hasNext();) {
            Canton object = (Canton) it.next();
            System.out.println(""+object);

        }
    }

}
