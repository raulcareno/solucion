/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Notanotas;
import jcinform.persistencia.Notasrecord;
import jcinform.persistencia.Periodo;
import jcinform.procesos.Administrador;
import org.zkoss.zul.Messagebox;

/**
 *
 * @author geovanny
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void prueba(Cursos cur) {
        Administrador adm = new Administrador();
        List<Cursos> cursosList = new ArrayList<Cursos>();
      Periodo periodo = new Periodo(1);
        if (cur.getCodigocur().equals(-1)) {
            cursosList = adm.query("Select o from Cursos as o where o.periodo.codigoper =  '"+periodo.getCodigoper()+"' ");
        }else{
          cur = (Cursos) adm.buscarClave(cur.getCodigocur(), Cursos.class);
          cursosList.add(cur);
        }
        
        //Cursos cur = new Cursos(1);
        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
                " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  " +
                "and o.sistema.promediofinal =  'PF'");

        for (Iterator<Cursos> ist = cursosList.iterator(); ist.hasNext();) {
            Cursos cursos = ist.next();

            List<Matriculas> matriculas = adm.query("Select o from Matriculas as o " +
                    "where o.curso.codigocur = '" + cursos.getCodigocur() + "' ");
            if (notas.size() > 0) {
                Notanotas no = notas.get(0);
                 System.out.println("CURSO: "+cursos.getDescripcion()+" "+ cursos.getEspecialidad());
                for (Iterator<Matriculas> it = matriculas.iterator(); it.hasNext();) {
                    Matriculas matriculas1 = it.next();
                    List val = adm.queryNativo("select avg(" + no.getNota() + ")  from notas " +
                            "where matricula = '" + matriculas1.getCodigomat() + "' " +
                            "and promedia = true and disciplina = false  ");
                     //       System.out.println(matriculas1.getEstudiante().getApellido()+": " + ((Vector)val.get(0)).get(0));
                    Double  promedio =  (Double) ((Vector)val.get(0)).get(0);
                    List<Notasrecord> est = adm.query("Select o from Notasrecord as o " +
                            "where o.estudiante.codigoest = '"+matriculas1.getEstudiante().getCodigoest()+"' ");
                    if(est.size()>0){
                        Notasrecord actualizaR = est.get(0);
                        if(matriculas1.getCurso().getSecuencia().equals(1))
                            actualizaR.setPrimero(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(2))
                            actualizaR.setSegundo(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(3))
                            actualizaR.setTercero(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(4))
                            actualizaR.setCuarto(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(5))
                            actualizaR.setQuinto(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(6))
                            actualizaR.setSexto(promedio);
                        adm.actualizar(actualizaR);
                    }else{
                        Notasrecord nuevoR = new Notasrecord();
                        nuevoR.setEstudiante(matriculas1.getEstudiante());
                            if(matriculas1.getCurso().getSecuencia().equals(1))
                            nuevoR.setPrimero(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(2))
                            nuevoR.setSegundo(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(3))
                            nuevoR.setTercero(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(4))
                            nuevoR.setCuarto(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(5))
                            nuevoR.setQuinto(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(6))
                            nuevoR.setSexto(promedio);
                            adm.guardar(nuevoR); 
                    }
                }
                System.out.println("------------------------------------------------");
            } else {
                try {
                    Messagebox.show("Error no ha parametrizado el PF en la libreta");
                    return;
                } catch (InterruptedException ex) {
                    Logger.getLogger(NewMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    public static void main(String[] args) {
        // TODO code application logic here
        Cursos cur = new Cursos(1);
        prueba(cur);
    }
}
