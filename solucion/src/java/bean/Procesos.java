/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Global;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Notanotas;
import jcinform.persistencia.Notasrecord;
import jcinform.persistencia.Periodo;
import jcinform.procesos.Administrador;
import org.zkoss.zhtml.Messagebox;

/**
 *
 * @author geovanny
 */
public class Procesos {

    public Procesos(){
    }

     public  void aprovechamiento(Cursos cur) {
        Administrador adm = new Administrador();
        List<Cursos> cursosList = new ArrayList<Cursos>();
      Periodo periodo = cur.getPeriodo();
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
                if (matriculas.size() > 0) {
                Notanotas no = notas.get(0);
                 System.out.println("CURSO: "+cursos.getDescripcion()+" "+ cursos.getEspecialidad());
                for (Iterator<Matriculas> it = matriculas.iterator(); it.hasNext();) {
                    Matriculas matriculas1 = it.next();
                    String que = "select avg(" + no.getNota() + ")  from notas " +
                            "where matricula = '" + matriculas1.getCodigomat() + "' " +
                            " and promedia = true "
                            + "and seimprime = true "
                            + "and cuantitativa = true  "
                            + "and materia != 0 "
                            + "and disciplina = false  ";
                    //System.out.println(que);
                    List val = adm.queryNativo(que);

                    /*
                     "where notas.matricula = '" + matriculas1.getCodigomat() + "' "
                        + "and notas.seimprime = true "
                        + "and notas.promedia = true "
                        + "and notas.disciplina = false "
                        + "and notas.cuantitativa = true and notas.materia != 0 "
                        + "group by matricula  

                     */

                     //       System.out.println(matriculas1.getEstudiante().getApellido()+": " + ((Vector)val.get(0)).get(0));
                    Double  promedio = 0.0;
                    try{
                        promedio =  (Double) ((Vector)val.get(0)).get(0);
                    }catch(Exception e){
                            promedio = 0.0;
                    }
                    if(promedio == null){
                        promedio = 0.0;
                    }
                    List<Notasrecord> est = adm.query("Select o from Notasrecord as o " +
                            "where o.estudiante.codigoest = '"+matriculas1.getEstudiante().getCodigoest()+"' ");
                    if(est.size()>0){
                        Notasrecord actualizaR = est.get(0);
                        if(matriculas1.getCurso().getSecuencia().equals(1))
                            actualizaR.setPrimerob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(2))
                            actualizaR.setSegundob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(3))
                            actualizaR.setTercerob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(4))
                            actualizaR.setCuartob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(5))
                            actualizaR.setQuintob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(6))
                            actualizaR.setSextob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(7))
                            actualizaR.setSeptimob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(8))
                            actualizaR.setPrimero(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(9))
                            actualizaR.setSegundo(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(10))
                            actualizaR.setTercero(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(11))
                            actualizaR.setCuarto(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(12))
                            actualizaR.setQuinto(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(13))
                            actualizaR.setSexto(promedio);
                        adm.actualizar(actualizaR);
                    }else{
                        Notasrecord nuevoR = new Notasrecord();
                        nuevoR.setEstudiante(matriculas1.getEstudiante());
                        if(matriculas1.getCurso().getSecuencia().equals(1))
                            nuevoR.setPrimerob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(2))
                            nuevoR.setSegundob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(3))
                            nuevoR.setTercerob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(4))
                            nuevoR.setCuartob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(5))
                            nuevoR.setQuintob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(6))
                            nuevoR.setSextob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(7))
                            nuevoR.setSeptimob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(8))
                            nuevoR.setPrimero(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(9))
                            nuevoR.setSegundo(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(10))
                            nuevoR.setTercero(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(11))
                            nuevoR.setCuarto(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(12))
                            nuevoR.setQuinto(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(13))
                            nuevoR.setSexto(promedio);
                            adm.guardar(nuevoR);
                    }
                }
            }
                System.out.println("------------------------------------------------");
            } else {
                try {
                    Messagebox.show("Error no ha parametrizado el PF en la libreta");
                    return;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
     
     public  void aprovechamiento2(Cursos cur, Global materia) {
        Administrador adm = new Administrador();
        List<Cursos> cursosList = new ArrayList<Cursos>();
      Periodo periodo = cur.getPeriodo();
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
                if (matriculas.size() > 0) {
                Notanotas no = notas.get(0);
                 System.out.println("CURSO: "+cursos.getDescripcion()+" "+ cursos.getEspecialidad()+" "+cursos.getParalelo().getDescripcion());
                for (Iterator<Matriculas> it = matriculas.iterator(); it.hasNext();) {
                    Matriculas matriculas1 = it.next();
                    String que = "select " + no.getNota() + "  from notas " +
                            "where matricula = '" + matriculas1.getCodigomat() + "' " +
                            "and materia = '"+materia.getCodigo()+"' and disciplina = false ";
                    //System.out.println(que);
                    List val = adm.queryNativo(que);
                    Double  promedio = 0.0;
                    try{
                        promedio =  (Double) ((Vector)val.get(0)).get(0);
                    }catch(Exception e){
                            promedio = 0.0;
                    }
                    if(promedio == null){
                        promedio = 0.0;
                    }
                    List<Notasrecord> est = adm.query("Select o from Notasrecord as o " +
                            "where o.estudiante.codigoest = '"+matriculas1.getEstudiante().getCodigoest()+"' ");
                    if(est.size()>0){
                        Notasrecord actualizaR = est.get(0);
                        if(matriculas1.getCurso().getSecuencia().equals(1))
                            actualizaR.setPrimerob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(2))
                            actualizaR.setSegundob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(3))
                            actualizaR.setTercerob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(4))
                            actualizaR.setCuartob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(5))
                            actualizaR.setQuintob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(6))
                            actualizaR.setSextob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(6))
                            actualizaR.setSeptimob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(8))
                            actualizaR.setPrimero(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(9))
                            actualizaR.setSegundo(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(10))
                            actualizaR.setTercero(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(11))
                            actualizaR.setCuarto(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(12))
                            actualizaR.setQuinto(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(13))
                            actualizaR.setSexto(promedio);
                        adm.actualizar(actualizaR);
                    }else{
                        Notasrecord nuevoR = new Notasrecord();
                        nuevoR.setEstudiante(matriculas1.getEstudiante());
                            if(matriculas1.getCurso().getSecuencia().equals(1))
                            nuevoR.setPrimerob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(2))
                            nuevoR.setSegundob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(3))
                            nuevoR.setTercerob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(4))
                            nuevoR.setCuartob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(5))
                            nuevoR.setQuintob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(6))
                            nuevoR.setSextob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(7))
                            nuevoR.setSeptimob(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(8))
                            nuevoR.setPrimero(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(9))
                            nuevoR.setSegundo(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(10))
                            nuevoR.setTercero(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(11))
                            nuevoR.setCuarto(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(12))
                            nuevoR.setQuinto(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(13))
                            nuevoR.setSexto(promedio);
                       adm.guardar(nuevoR);
                    }
                }
                }
                System.out.println("------------------------------------------------");
            } else {
                try {
                    Messagebox.show("Error no ha parametrizado el PF en la libreta");
                    return;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }


      public void disciplina(Cursos cur) {
        Administrador adm = new Administrador();
        List<Cursos> cursosList = new ArrayList<Cursos>();
      Periodo periodo = cur.getPeriodo();
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
                            " and materia = 0   ");
                     //       System.out.println(matriculas1.getEstudiante().getApellido()+": " + ((Vector)val.get(0)).get(0));
                    Double  promedio =  (Double) ((Vector)val.get(0)).get(0);
                    if(promedio == null){
                        promedio = 0.0;
                    }
                    List<Notasrecord> est = adm.query("Select o from Notasrecord as o " +
                            "where o.estudiante.codigoest = '"+matriculas1.getEstudiante().getCodigoest()+"' ");
                    if(est.size()>0){
                        Notasrecord actualizaR = est.get(0);
                        if(matriculas1.getCurso().getSecuencia().equals(1))
                            actualizaR.setPrimerobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(2))
                            actualizaR.setSegundobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(3))
                            actualizaR.setTercerobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(4))
                            actualizaR.setCuartobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(5))
                            actualizaR.setQuintobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(6))
                            actualizaR.setSextobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(7))
                            actualizaR.setSeptimobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(8))
                            actualizaR.setPrimerod(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(9))
                            actualizaR.setSegundod(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(10))
                            actualizaR.setTercerd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(11))
                            actualizaR.setCuartod(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(12))
                            actualizaR.setQuintod(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(13))
                            actualizaR.setSextod(promedio);
                        adm.actualizar(actualizaR);
                    }else{
                        Notasrecord nuevoR = new Notasrecord();
                        nuevoR.setEstudiante(matriculas1.getEstudiante());
                            if(matriculas1.getCurso().getSecuencia().equals(1))
                            nuevoR.setPrimerobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(2))
                            nuevoR.setSegundobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(3))
                            nuevoR.setTercerobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(4))
                            nuevoR.setCuartobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(5))
                            nuevoR.setQuintobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(6))
                            nuevoR.setSextobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(7))
                            nuevoR.setSeptimobd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(8))
                            nuevoR.setPrimerod(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(9))
                            nuevoR.setSegundod(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(10))
                            nuevoR.setTercerd(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(11))
                            nuevoR.setCuartod(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(12))
                            nuevoR.setQuintod(promedio);
                        else if(matriculas1.getCurso().getSecuencia().equals(13))
                            nuevoR.setSextod(promedio); 
                            
                            adm.guardar(nuevoR);
                    }
                }
                System.out.println("------------------------------------------------");
            } else {
                try {
                    Messagebox.show("Error no ha parametrizado el PF en la libreta");
                    return;
                } catch (InterruptedException ex) {
                    Logger.getLogger(Procesos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }


}
