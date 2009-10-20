/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import bsh.Interpreter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Equivalencias;
import jcinform.persistencia.MateriaProfesor;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Notanotas;
import jcinform.persistencia.Notas;
import jcinform.persistencia.ParametrosGlobales;
import jcinform.persistencia.Periodo;
import jcinform.persistencia.Sistemacalificacion;
import jcinform.procesos.Administrador;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import sources.DisciplinaDataSource;
/**
 *
 * @author geovanny
 */
public class reporteDisciplina {

    
public void reporteDisciplina(Cursos curso,Sistemacalificacion sistema){
        Administrador adm = new Administrador();
        byte[] bytes = null;
        try {
            Session ses = Sessions.getCurrent();
            Periodo periodo = (Periodo) ses.getAttribute("periodo");
            Map parameters = new HashMap();
            parameters.put("denominacion", periodo.getInstitucion().getDenominacion());
            parameters.put("nombre", "" + periodo.getInstitucion().getNombre());
            parameters.put("periodo", "" + periodo.getDescripcion());
            //parameters.put("titulo", "" + titulo);

        String tipo = "INGRESADA";
        List<ParametrosGlobales> para = adm.query("Select o from ParametrosGlobales as o " +
                "where o.variable = 'TIPODISCIPLINA' " +
                "and o.periodo.codigoper = '"+periodo.getCodigoper()+"'");
        if(para.size()>0){
            ParametrosGlobales param = para.get(0);
            tipo = param.getCvalor();
        }

//            String tipo = var.busquedaC("Z_FORMULADISCIPLINA", parametros);
            //Double notaBase = var.busquedaD("Z_NOTADISC",parametros);
            Double notaBase = 0.0;
            List<Equivalencias> equivalenciasFaltas = adm.query("Select o from Equivalencias as o " +
                    "where o.grupo = 'DI' " +
                    "and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

            List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
                    " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.codigosis = '"+sistema.getCodigosis()+"' " +
                    "order by o.sistema.orden");
            List<MateriaProfesor> lista = adm.query("Select o from MateriaProfesor as o " +
                "where o.curso.codigocur = '" + curso.getCodigocur() + "'" +
                " and o.materia.codigo > 2  order by o.materia.descripcion");
            String query = "";
            String query2 = "";
            String numeroDecimales = "3";
            for (Notanotas notass : notas) {
                query += notass.getNota() + ",";
            }
            for (Notanotas notass : notas) {
                query2 += "round(avg(" + notass.getNota() + ")," + numeroDecimales + "),";
            }
            query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
            query2 = query2.substring(0, query2.length() - 1).replace("'", "");
            List<Notas> lisNotas = new ArrayList();
            ArrayList lisNotasC = new ArrayList();

            List<Matriculas> matriculas = new ArrayList();
            matriculas = adm.query("Select o from Matriculas as o " +
                    "where o.curso.codigocur = '" + curso.getCodigocur() + "' " +
                    "and  o.estado IN ('Matriculado','Retirado') " +
                    "order by o.estudiante.apellido ");
            for (Iterator<Matriculas> itm = matriculas.iterator(); itm.hasNext();) {
                Matriculas matriculas1 = itm.next();
                String query3 = "";
                int w = 1;
                Interpreter inter = new Interpreter();

                for (int i = 0; i < equivalenciasFaltas.size(); i++) {
                    query3 += "sum(nota" + w + "),";
                    parameters.put("f" + (i + 1), equivalenciasFaltas.get(i).getAbreviatura());
                    w++;
                }
                query3 = query3.substring(0, query3.length() - 1);
                //IMPRIMO LAS FALTAS
                String q = "Select " + query3 + "  from disciplina " +
                        "where matricula = '" + matriculas1.getCodigomat() + "'  " +
                        "and sistema = '" + sistema.getCodigosis() + "' " +
                        " group by matricula ";
                //System.out.println(""+q);
                List nativo = adm.queryNativo(q);
                NotaDisciplina coll = new NotaDisciplina();
                coll.setEstado(matriculas1.getEstado());
                coll.setNombres(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                coll.setCurso(curso+"");

                for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();
                    inter.set("nota", coll);
                    for (int j = 0; j < vec.size(); j++) {
                        Object dos = vec.get(j);
                        Integer val = new Integer(dos.toString());

                        inter.eval("nota.setJ" + (j + 1) + "(" + val + ")");
                    }
                    coll = (NotaDisciplina) inter.get("nota");

                }
                 int i=0;
                 Double promProfesor=0.0;
                for (Iterator<MateriaProfesor> it = lista.iterator(); it.hasNext();) {
                    MateriaProfesor MateriaProfesor = it.next();
                    parameters.put("n" + (i + 1), MateriaProfesor.getMateria().getDescripcion());
                    q = "Select " + query + "  from Notas notas " +
                                "where notas.matricula = '" + matriculas1.getCodigomat() + "' " +
                                "and notas.materia = '"+MateriaProfesor.getMateria().getCodigo()+"'" +
                                " and notas.disciplina = true  " +
                                " ";
                           List nativo2 = adm.queryNativo(q);
                            if(nativo2.size()>0){

                                        Vector vec = (Vector) nativo2.get(0);
                                        inter.set("nota", coll);
                                        Object dos = vec.get(0);
                                        Double val = new Double(dos.toString());
                                        inter.eval("nota.setN" + (i + 1) + "(" + val + ")");
                                        coll = (NotaDisciplina) inter.get("nota");
                                        promProfesor+=val;

                            }else{
//                                        inter.eval("nota.setN" + (i + 1) + "(0.0)");
//                                        coll = (NotaDisciplina) inter.get("nota");
                            }

                    i++;

                 }


                 coll.setProfesores(promProfesor/lista.size());

                 q = "Select " + query + "  from Notas notas " +
                                "where notas.mat_codigo = '" + matriculas1.getCodigomat() + "' " +
                                "and notas.materia = '1'" +
                                " and notas.disciplina = true  " +
                                " ";
                         nativo = adm.queryNativo(q);
                                    if(nativo.size()>0){
                                       Vector vec = (Vector) nativo.get(0);
                                        inter.set("nota", coll);
                                        Object dos = vec.get(0);
                                        Double val = new Double(dos.toString());

                                        coll.setInspector(val);
                                        if(val.equals(0.0)){
                                            coll.setInspector(notaBase);
                                        }
                                    }else{
                                            coll.setInspector(notaBase);
                                    }

                if (tipo.equals("MITAD")) {//SUMA ENTRE PROMEDIOS DIVIDIDO PARA 2
                    coll.setFinali((promProfesor/lista.size()+coll.getInspector())/2);
                } else if (tipo.equals("PROMEDIO")) {//
                    coll.setFinali(promProfesor+coll.getInspector()/(lista.size())+1);
                } else if (tipo.equals("SUMATORIA")) {//PROMEDIO DE PROFESORES + PROMEDIO DE INSPECCION
                    coll.setFinali(promProfesor/lista.size()+coll.getInspector());
                }else if(tipo.equals("INGRESADA")){
                                    q = "Select " + query + "  from Notas notas " +
                                                "where notas.matricula = '" + matriculas1.getCodigomat() + "' " +
                                                "and notas.materia = '0'" +
                                                " and notas.disciplina = true  " +
                                                " ";
                                         nativo = adm.queryNativo(q);
                                                    if(nativo.size()>0){
                                                       Vector vec = (Vector) nativo.get(0);
                                                        inter.set("nota", coll);
                                                        Object dos = vec.get(0);
                                                        Double val = new Double(dos.toString());
                                                        coll.setFinali(val);
                                                    }else{
                                                            coll.setFinali(20.0);
                                                    }
                }
            lisNotasC.add(coll);
                nativo = null;
            }
            DisciplinaDataSource ds = new DisciplinaDataSource(lisNotasC);
            lisNotas = null;
            //ReporteBecadosDataSource ds = new ReporteBecadosDataSource(listaMatriculados);
//return ds;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, "exception caught", e);
      }


    }

//    public static Object equivalencia(Object no, List<Equivalencias> equivalencias) {
//        Double nota = (Double) no;
//        ArrayList listado = new ArrayList();
//        for (Equivalencias acaEquivalencias : equivalencias) {
//            Object obj[] = new Object[3];
//            obj[0] = acaEquivalencias.getValorminimo();
//            obj[1] = acaEquivalencias.getValormaximo();
//            obj[2] = acaEquivalencias.getAbreviatura();
//            listado.add(obj);
//        }
//
//        for (Iterator it = listado.iterator(); it.hasNext();) {
//            Object object[] = (Object[]) it.next();
//            Double mini = (Double) object[0];
//            Double maxi = (Double) object[1];
//            if (nota >= mini && nota <= maxi) {
//                //System.out.println(""+);
//                equivalencias = null;
//                return object[2];
//            }
//        }
//        equivalencias = null;
//        return "";
//    }
}
