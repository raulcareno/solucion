/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Matriculas;
import jcinform.procesos.Administrador;

/**
 *
 * @author GEOVANNY
 */
public class copiarInformacion {

    /**
     * @param args the command line arguments
     */
    public static Integer estudiantesActuales(ArrayList arreglo, String nombreLlega){
        for (Iterator it = arreglo.iterator(); it.hasNext();) {
            Vector object = (Vector) it.next();
             String nombres = (String)object.get(1);
             nombreLlega = nombreLlega.replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U");
             nombres = nombres.replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U");
             if(nombreLlega.contains(nombres)){
                return Integer.parseInt(object.get(0)+"");
             }
        }
        return null;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Administrador adm = new Administrador();

        ArrayList arreglo = new ArrayList();
        List estudiantes = adm.queryNativo("select codigoest, concat(apellido,' ',nombre) from estudiantes ");
        for (int i = 0; i < estudiantes.size(); i++) {
            //System.out.println(""+estudiantes.get(i));
            arreglo.add(estudiantes.get(i)); 
        }
        
        List abc = adm.queryNativo("select * from estudiantes2 ");
        int encontrados =0;
        for (int i = 0; i < abc.size(); i++) {
            Vector obj = (Vector)abc.get(i);
             Integer valor = estudiantesActuales(arreglo, obj.get(1)+"");
             if(valor != null){
                System.out.println(""+abc.get(i)+" \t "+valor+" \t "+" "+encontrados);
                Matriculas mat = new Matriculas(adm.getNuevaClave("Matriculas", "codigomat"));
                mat.setEstudiante(new Estudiantes(valor));
                mat.setCurso(new Cursos(new Integer(obj.get(0)+"")));
                mat.setFechamat(new Date()); 
                mat.setEstado("Matriculado");
                adm.guardar(mat);
                encontrados++;     
             }else{
                Estudiantes est = new Estudiantes();
                est.setCodigoest(adm.getNuevaClave("Estudiantes", "codigoest"));
                est.setApellido(obj.get(1)+"");
                est.setNombre(obj.get(1)+"");
                adm.guardar(est);
                Matriculas mat = new Matriculas(adm.getNuevaClave("Matriculas", "codigomat"));
                mat.setEstudiante(est);
                mat.setFechamat(new Date()); 
                mat.setEstado("Matriculado");
                mat.setObservacion(""+obj.get(1)); 
                mat.setCurso(new Cursos(new Integer(obj.get(0)+"")));
                adm.guardar(mat);
             }
            
        }
        System.out.println("total encontrados: "+encontrados);
    }
}   
