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
        Long i=Long.parseLong("001EEEA7",10);
        System.out.println("DECIMALE: "+i);
        // TODO code application logic here
//        Double d = new Double("00");
//        System.out.println(""+d);
//        matriculasBean b = new matriculasBean();
//        Periodo per = new Periodo();
//        Institucion ins = new Institucion();
//        ins.setFotos("F:\\");
//        per.setInstitucion(ins);
//        per.setDescripcion("2010-2011");
//        
//        b.generar(per);
        
        /*
         SELECT 10370,esp.codigo,esp.descripcion,1,52,numeroacta,e.cedula, e.apellido, e.nombre,e.genero,n.nota7 FROM notasacta n, matriculas m, estudiantes e, cursos c, GLOBAL esp
 WHERE m.estudiante = e.codigoest AND m.codigomat = n.matricula 
 AND m.curso = c.codigocur  AND c.especialidad = esp.codigo 
AND n.numeroacta >0 AND n.matricula IN (
SELECT codigomat FROM matriculas WHERE curso IN (
SELECT codigocur FROM cursos WHERE periodo = 4 AND secuencia = 6) AND estado NOT IN ('Inscrito','Retirado','Anulado') )
ORDER BY esp.codigo, n.numeroactapeaje
         */
    }

}
