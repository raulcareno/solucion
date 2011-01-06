/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import jcinform.persistencia.Institucion;
import jcinform.persistencia.Periodo;

/**
 *
 * @author Ismael Jadan
 */
public class pruebas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
//        Double d = new Double("00");
//        System.out.println(""+d);
        matriculasBean b = new matriculasBean();
        Periodo per = new Periodo();
        Institucion ins = new Institucion();
        ins.setFotos("F:\\");
        per.setInstitucion(ins);
        per.setDescripcion("2010-2011");
        
        b.generar(per);
    }

}
