/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import sources.Nota;

/**
 *
 * @author geovanny
 */
public class Procesos {

    public Procesos() {
    }

    public void aprovechamiento(Cursos cur) {
        Administrador adm = new Administrador();
        List<Cursos> cursosList = new ArrayList<Cursos>();
        Periodo periodo = cur.getPeriodo();
        if (cur.getCodigocur().equals(-1)) {
            cursosList = adm.query("Select o from Cursos as o where o.periodo.codigoper =  '" + periodo.getCodigoper() + "' ");
        } else {
            cur = (Cursos) adm.buscarClave(cur.getCodigocur(), Cursos.class);
            cursosList.add(cur);
        }

        //Cursos cur = new Cursos(1);
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.promediofinal =  'PF'");
reportesClase rp = new reportesClase();
        for (Iterator<Cursos> ist = cursosList.iterator(); ist.hasNext();) {
            Cursos cursos = ist.next();
            List<Matriculas> listaMatriculasPerdidos = rp.cuadroverificar(cursos, notas.get(0).getSistema(), new Matriculas(-1));

            adm.ejecutaSql("Update Matriculas set perdio = false where curso.codigocur = '"+cursos.getCodigocur()+"' "); 
            List<Matriculas> matriculas = adm.query("Select o from Matriculas as o "
                    + "where o.curso.codigocur = '" + cursos.getCodigocur() + "' and  o.estado in ('Matriculado','Recibir Pase') ");
            if (notas.size() > 0) {
                if (matriculas.size() > 0) {
                    Notanotas no = notas.get(0);
                    System.out.println("CURSO: " + cursos.getDescripcion() + " " + cursos.getEspecialidad());
                    for (Iterator<Matriculas> it = matriculas.iterator(); it.hasNext();) {
                        Matriculas matriculas1 = it.next();
                        if (!listaMatriculasPerdidos.contains(matriculas1)) {//reviso si no ha perdido
                            String que = "select avg(" + no.getNota() + ")  from notas "
                                    + "where matricula = '" + matriculas1.getCodigomat() + "' "
                                    + " and promedia = true "
                                    + "and seimprime = true "
                                    + "and cuantitativa = true  "
                                    + "and materia != 0 "
                                    + "and disciplina = false  ";
                            //System.out.println(que);
                            List val = adm.queryNativo(que);


                            Double promedio = 0.0;
                            try {
                                promedio = (Double) ((Vector) val.get(0)).get(0);
                            } catch (Exception e) {
                                promedio = 0.0;
                            }
                            if (promedio == null) {
                                promedio = 0.0;
                            }
                            List<Notasrecord> est = adm.query("Select o from Notasrecord as o "
                                    + "where o.estudiante.codigoest = '" + matriculas1.getEstudiante().getCodigoest() + "' ");
                            if (est.size() > 0) {
                                Notasrecord actualizaR = est.get(0);
                                if (matriculas1.getCurso().getSecuencia().equals(1)) {
                                    actualizaR.setPrimerob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(2)) {
                                    actualizaR.setSegundob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(3)) {
                                    actualizaR.setTercerob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(4)) {
                                    actualizaR.setCuartob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(5)) {
                                    actualizaR.setQuintob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(6)) {
                                    actualizaR.setSextob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(7)) {
                                    actualizaR.setSeptimob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(8)) {
                                    actualizaR.setPrimero(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(9)) {
                                    actualizaR.setSegundo(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(10)) {
                                    actualizaR.setTercero(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(11)) {
                                    actualizaR.setCuarto(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(12)) {
                                    actualizaR.setQuinto(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(13)) {
                                    actualizaR.setSexto(promedio);
                                }
                                adm.actualizar(actualizaR);
                            } else {
                                Notasrecord nuevoR = new Notasrecord();
                                nuevoR.setEstudiante(matriculas1.getEstudiante());
                                if (matriculas1.getCurso().getSecuencia().equals(1)) {
                                    nuevoR.setPrimerob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(2)) {
                                    nuevoR.setSegundob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(3)) {
                                    nuevoR.setTercerob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(4)) {
                                    nuevoR.setCuartob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(5)) {
                                    nuevoR.setQuintob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(6)) {
                                    nuevoR.setSextob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(7)) {
                                    nuevoR.setSeptimob(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(8)) {
                                    nuevoR.setPrimero(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(9)) {
                                    nuevoR.setSegundo(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(10)) {
                                    nuevoR.setTercero(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(11)) {
                                    nuevoR.setCuarto(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(12)) {
                                    nuevoR.setQuinto(promedio);
                                } else if (matriculas1.getCurso().getSecuencia().equals(13)) {
                                    nuevoR.setSexto(promedio);
                                }
                                adm.guardar(nuevoR);
                            }
                        } else {
                            matriculas1.setPerdio(true);
                            adm.actualizar(matriculas1);
                            System.out.println("PERDIDO: " + matriculas1.getEstudiante());
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

//    public List<Matriculas> cuadroverificar(Cursos curso, Sistemacalificacion sistema) {
////     int tamanio=0;
//        Administrador adm = new Administrador();
//        Session ses = Sessions.getCurrent();
//        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//        materiasReprobadas = new ArrayList<MateriaProfesor>();
//        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
//                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
//
//        Double sumaPierde = regresaVariableParametrosDecimal("SUMATORIAPIERDE", parametrosGlobales);
//        Double sumaAprueba = regresaVariableParametrosDecimal("SUMATORIAAPRUEBA", parametrosGlobales);
//        Boolean validaConPromedioGeneral = regresaVariableParametrosLogico("PROMEDIOGENERAL", parametrosGlobales);
//
//        List sistemas = adm.query("Select o from Sistemacalificacion as o "
//                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
//                + "and o.orden <= '" + sistema.getOrden() + "' "
//                + "and o.trimestre.codigotrim = '" + sistema.getTrimestre().getCodigotrim() + "' "
//                + "and o.seimprime = true order by o.orden ");
//
//        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
//                + "where  o.sistema.orden  <= '" + sistema.getOrden() + "'  "
//                + "and o.sistema.trimestre.codigotrim =  '" + sistema.getTrimestre().getCodigotrim() + "' "
//                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true "
//                + "order by o.sistema.orden ");
//
//        List<Notanotas> notaFinal = adm.query("Select o from Notanotas as o "
//                + "where  o.sistema.codigosis = '" + sistema.getCodigosis() + "'  "
//                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true");
//        if (notaFinal.size() <= 0) {
//            try {
//                org.zkoss.zul.Messagebox.show("No ha parametrizado el Promedio Final en Aportes...!", "Administrador Educativo", org.zkoss.zul.Messagebox.CANCEL, org.zkoss.zul.Messagebox.EXCLAMATION);
//                return null;
//            } catch (InterruptedException ex) {
//                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        if (notas.size() <= 0) {
//            try {
//                org.zkoss.zul.Messagebox.show("No han nada que imprimir Aportes en 0 ...!", "Administrador Educativo", org.zkoss.zul.Messagebox.CANCEL, org.zkoss.zul.Messagebox.EXCLAMATION);
//                return null;
//            } catch (InterruptedException ex) {
//                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
//                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
//        List<Equivalencias> equivalenciasSuple = adm.query("Select o from Equivalencias as o "
//                + "where o.grupo = 'SU' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
//        if (equivalenciasSuple.size() <= 0) {
//            try {
//                org.zkoss.zul.Messagebox.show("No existen los rangos de Supletorio, ingrese a Equivalencias > Supletorios y verifique que esté lleno...!", "Administrador Educativo", org.zkoss.zul.Messagebox.CANCEL, org.zkoss.zul.Messagebox.ERROR);
//                return null;
//            } catch (InterruptedException ex) {
//                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        String query = "";
//        for (Notanotas notass : notas) {
//            query += notass.getNota() + ",";
//        }
//        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
//        String q = "Select codigomap, mat.codigomat,notas.materia, " + query + ", 'obs'  from notas, materia_profesor , matriculas mat, estudiantes est "
//                + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
//                + "and materia_profesor.curso = '" + curso.getCodigocur() + "' "
//                + "and notas.promedia = true and notas.disciplina = false and notas.materia != 0 and notas.materia != 1 and materia_profesor.seimprime = true  "
//                + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "' and estado in  ('Matriculado','Recibir Pase')  ) "
//                + "  AND NOTAS.MATERIA NOT IN (SELECT CODIGO FROM GLOBAL WHERE GRUPO = 'MAT' AND DESCRIPCION LIKE '%PROME%')   "
//                + "order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";
//        System.out.println("cuadro final: " + q);
//        List nativo = adm.queryNativo(q);
//        int cont = 0;
//        String matricula = "";
//        List<Matriculas> aprobadMatriculas = new ArrayList<Matriculas>();
//        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
//            Vector vec = (Vector) itna.next();
//            Matriculas matriculaNo = null;
//            MateriaProfesor mprofesor = null;
//            Global materia = null;
//            Double sumatoria = 0.0;
//            Double pg = 0.0;
//            String obs = "";
//            Integer obs1 = 0;
//            int ksis = 0;
//
//            for (int j = 0; j < vec.size(); j++) {
//                Object dos = vec.get(j);
//                Double val = 0.0;
//                Nota nota = new Nota();
//                try {
//                    if (dos.equals(null)) {
//                        dos = new Double(0.0);
//                    }
//                } catch (Exception e) {
//                    dos = new Double(0.0);
//                }
//                if (j == (vec.size() - 1)) {
//
//                    matricula = matriculaNo.toString();
//                } else if (j >= 3) {
//                    val = redondear((Double) dos, 2);
//                    matricula = matriculaNo.toString();
//                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
//                    if (nota.getSistema().getPromediofinal().equals("SM")) {
//                        if (val < sumaPierde) {
//                            obs = "Pierde";
//                            System.out.println("pierde SM (1):" + matricula + " mat:" + materia + " not:" + val);
//                            MateriaProfesor matR = new MateriaProfesor();
//                            matR.setCodigomap(matriculaNo.getCodigomat());
//                            matR.setOrden(materia.getCodigo());
//                            materiasReprobadas.add(matR);
//                            obs1++;
//                        } else {
//                            obs = "";
//                        }
//                        sumatoria = val;
//                    } else if (nota.getSistema().getPromediofinal().equals("PF")) {
//                        if (val < sumaPierde && validaConPromedioGeneral) {
//                            obs = "Pierde";
//                            System.out.println("pierde PG (1):" + matricula + " mat:" + materia + " not:" + val);
//                            MateriaProfesor matR = new MateriaProfesor();
//                            matR.setCodigomap(matriculaNo.getCodigomat());
//                            matR.setOrden(materia.getCodigo());
//                            materiasReprobadas.add(matR);
//                            obs1++;
//                        } else {
//                            obs = "";
//                        }
//                        pg = val;
//                    } else if (nota.getSistema().getPromediofinal().equals("SU") && !obs.equals("Pierde")) {
//                        if (validaConPromedioGeneral) {
//                            try {
//                                Double valor = new Double(equivalenciaSupletorio(pg, equivalenciasSuple) + "");
//                                if (val < valor) {
//                                    obs = "Pierde";
//                                    System.out.println("pierde PG:" + matricula + " mat:" + materia + " not:" + val);
//                                    MateriaProfesor matR = new MateriaProfesor();
//                                    matR.setCodigomap(matriculaNo.getCodigomat());
//                                    matR.setOrden(materia.getCodigo());
//                                    materiasReprobadas.add(matR);
//                                    obs1++;
//                                } else {
//                                    obs = "";
//                                }
//                            } catch (Exception e) {
//                            }
//
//
//                        } else {
//                            if (sumatoria < sumaAprueba) {
//                                try {
//                                    Double valor = new Double(equivalenciaSupletorio(sumatoria, equivalenciasSuple) + "");
//                                    if (val < valor) {
//                                        obs = "Pierde";
//                                        System.out.println("pierde SU:" + matricula + " mat:" + materia + " not:" + val);
//                                        MateriaProfesor matR = new MateriaProfesor();
//                                        matR.setCodigomap(matriculaNo.getCodigomat());
//                                        matR.setOrden(materia.getCodigo());
//                                        materiasReprobadas.add(matR);
//                                        obs1++;
//                                    } else {
//                                        obs = "";
//                                    }
//                                } catch (Exception e) {
//                                }
//
//                            } else {
//                                obs = "";
//                            }
//                        }
//
//                    }
//                    ksis++;
//                } else if (j == 1) {
//                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
////                    if (matriculaNo.getCodigomat().equals(new Integer(1603))) {
////                        System.out.println("" + matriculaNo.getEstudiante());
////                    }
//                } else if (j == 0) {
////                    mprofesor = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
//                } else if (j == 2) {
//                    materia = (Global) adm.buscarClave((Integer) dos, Global.class);
//                }
//                if (matriculaNo != null && j > 1) {
//                    if (!matriculaNo.toString().equals(matricula)) {
//                        cont++;
//                    }
//                }
//            }
////            if (matricula.contains("HERMIDA")) {
////                System.out.println("cambios ");
////            }
//
//            if (obs1 > 0) {
//                if (!aprobadMatriculas.contains(matriculaNo)) {
//                    aprobadMatriculas.add(matriculaNo);
//                }
//            }
//        }
//        nativo = null;
//        return aprobadMatriculas;
//
//    }
    List<ParametrosGlobales> parametrosGlobales = null;
    public List<MateriaProfesor> materiasReprobadas = new ArrayList<MateriaProfesor>();

        String truncar = "public Double truncar(Double numero, int decimales) {         try {             java.math.BigDecimal d = new java.math.BigDecimal(numero);             d = d.setScale(decimales, java.math.BigDecimal.ROUND_DOWN);             return d.doubleValue();         } catch (Exception e) {             return 0.0;         }     }";
    public Double truncar(Double numero, int decimales) {
        try {
            BigDecimal d = new BigDecimal(numero);
            d = d.setScale(decimales, java.math.BigDecimal.ROUND_DOWN);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }


    public Double redondear(Double numero, int decimales) {
        try {

            BigDecimal d = new BigDecimal(numero+"");
            d = d.setScale(decimales, RoundingMode.HALF_UP);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    public Boolean regresaVariableParametrosLogico(String variable, List<ParametrosGlobales> textos) {
        for (Iterator<ParametrosGlobales> it = textos.iterator(); it.hasNext();) {
            ParametrosGlobales textos1 = it.next();
            if (textos1.getVariable().equals(variable)) {
                return textos1.getBvalor();
            }
        }

        return false;
    }

    public Double regresaVariableParametrosDecimal(String variable, List<ParametrosGlobales> textos) {
        for (Iterator<ParametrosGlobales> it = textos.iterator(); it.hasNext();) {
            ParametrosGlobales textos1 = it.next();
            if (textos1.getVariable().equals(variable)) {
                return textos1.getNvalor();
            }
        }

        return 0.0;
    }

    public Object equivalenciaSupletorio(Object no, List<Equivalencias> equivalencias) {
        Double nota = (Double) no;
        ArrayList listado = new ArrayList();
        for (Equivalencias acaEquivalencias : equivalencias) {
            Object obj[] = new Object[3];
            obj[0] = acaEquivalencias.getValorminimo();
            obj[1] = acaEquivalencias.getValormaximo();
            obj[2] = acaEquivalencias.getNombre();
            listado.add(obj);
        }

        for (Iterator it = listado.iterator(); it.hasNext();) {
            Object object[] = (Object[]) it.next();
            Double mini = (Double) object[0];
            Double maxi = (Double) object[1];
            if (nota >= mini && nota <= maxi) {
                return object[2];
            }
        }
        //Double sumaAprueba = regresaVariableParametrosDecimal("SUMATORIAAPRUEBA", parametrosGlobales);
        //System.out.println("FALTA PARAMETRIZAR EL CUADRO DE EQUIVALENCIAS EL VALOR CERO DE SUMATORIA");
        return 0;

    }

    public void aprovechamiento2(Cursos cur, Global materia) {
        Administrador adm = new Administrador();
        List<Cursos> cursosList = new ArrayList<Cursos>();
        Periodo periodo = cur.getPeriodo();
        if (cur.getCodigocur().equals(-1)) {
            cursosList = adm.query("Select o from Cursos as o where o.periodo.codigoper =  '" + periodo.getCodigoper() + "' ");
        } else {
            cur = (Cursos) adm.buscarClave(cur.getCodigocur(), Cursos.class);
            cursosList.add(cur);
        }

        //Cursos cur = new Cursos(1);
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.promediofinal =  'PF'");

        for (Iterator<Cursos> ist = cursosList.iterator(); ist.hasNext();) {
            Cursos cursos = ist.next();

            List<Matriculas> matriculas = adm.query("Select o from Matriculas as o "
                    + "where o.curso.codigocur = '" + cursos.getCodigocur() + "' and o.estado in ('Matriculado','Recibir Pase')  ");
            if (notas.size() > 0) {
                if (matriculas.size() > 0) {
                    Notanotas no = notas.get(0);
                    System.out.println("CURSO: " + cursos.getDescripcion() + " " + cursos.getEspecialidad() + " " + cursos.getParalelo().getDescripcion());
                    for (Iterator<Matriculas> it = matriculas.iterator(); it.hasNext();) {
                        Matriculas matriculas1 = it.next();
                        String que = "select " + no.getNota() + "  from notas "
                                + "where matricula = '" + matriculas1.getCodigomat() + "' "
                                + "and materia = '" + materia.getCodigo() + "' and disciplina = false ";
                        System.out.println(que);
                        List val = adm.queryNativo(que);
                        Double promedio = 0.0;
                        try {
                            promedio = (Double) ((Vector) val.get(0)).get(0);
                        } catch (Exception e) {
                            promedio = 0.0;
                        }
                        if (promedio == null) {
                            promedio = 0.0;
                        }
                        List<Notasrecord> est = adm.query("Select o from Notasrecord as o "
                                + "where o.estudiante.codigoest = '" + matriculas1.getEstudiante().getCodigoest() + "' ");
                        if (est.size() > 0) {
                            Notasrecord actualizaR = est.get(0);
                            if (matriculas1.getCurso().getSecuencia().equals(1)) {
                                actualizaR.setPrimerob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(2)) {
                                actualizaR.setSegundob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(3)) {
                                actualizaR.setTercerob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(4)) {
                                actualizaR.setCuartob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(5)) {
                                actualizaR.setQuintob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(6)) {
                                actualizaR.setSextob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(7)) {
                                actualizaR.setSeptimob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(8)) {
                                actualizaR.setPrimero(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(9)) {
                                actualizaR.setSegundo(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(10)) {
                                actualizaR.setTercero(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(11)) {
                                actualizaR.setCuarto(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(12)) {
                                actualizaR.setQuinto(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(13)) {
                                actualizaR.setSexto(promedio);
                            }
                            adm.actualizar(actualizaR);
                        } else {
                            Notasrecord nuevoR = new Notasrecord();
                            nuevoR.setEstudiante(matriculas1.getEstudiante());
                            if (matriculas1.getCurso().getSecuencia().equals(1)) {
                                nuevoR.setPrimerob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(2)) {
                                nuevoR.setSegundob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(3)) {
                                nuevoR.setTercerob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(4)) {
                                nuevoR.setCuartob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(5)) {
                                nuevoR.setQuintob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(6)) {
                                nuevoR.setSextob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(7)) {
                                nuevoR.setSeptimob(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(8)) {
                                nuevoR.setPrimero(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(9)) {
                                nuevoR.setSegundo(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(10)) {
                                nuevoR.setTercero(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(11)) {
                                nuevoR.setCuarto(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(12)) {
                                nuevoR.setQuinto(promedio);
                            } else if (matriculas1.getCurso().getSecuencia().equals(13)) {
                                nuevoR.setSexto(promedio);
                            }
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
            cursosList = adm.query("Select o from Cursos as o where o.periodo.codigoper =  '" + periodo.getCodigoper() + "' ");
        } else {
            cur = (Cursos) adm.buscarClave(cur.getCodigocur(), Cursos.class);
            cursosList.add(cur);
        }

        //Cursos cur = new Cursos(1);
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.promediofinal =  'PF'");

        for (Iterator<Cursos> ist = cursosList.iterator(); ist.hasNext();) {
            Cursos cursos = ist.next();

            List<Matriculas> matriculas = adm.query("Select o from Matriculas as o "
                    + "where o.curso.codigocur = '" + cursos.getCodigocur() + "'  and  o.estado in ('Matriculado','Recibir Pase')  ");
            if (notas.size() > 0) {
                Notanotas no = notas.get(0);
                System.out.println("CURSO: " + cursos.getDescripcion() + " " + cursos.getEspecialidad());
                for (Iterator<Matriculas> it = matriculas.iterator(); it.hasNext();) {
                    Matriculas matriculas1 = it.next();
                    List val = adm.queryNativo("select avg(" + no.getNota() + ")  from notas "
                            + "where matricula = '" + matriculas1.getCodigomat() + "' "
                            + " and materia = 0   ");
                    //       System.out.println(matriculas1.getEstudiante().getApellido()+": " + ((Vector)val.get(0)).get(0));
                    Double promedio = (Double) ((Vector) val.get(0)).get(0);
                    if (promedio == null) {
                        promedio = 0.0;
                    }
                    List<Notasrecord> est = adm.query("Select o from Notasrecord as o "
                            + "where o.estudiante.codigoest = '" + matriculas1.getEstudiante().getCodigoest() + "' ");
                    if (est.size() > 0) {
                        Notasrecord actualizaR = est.get(0);
                        if (matriculas1.getCurso().getSecuencia().equals(1)) {
                            actualizaR.setPrimerobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(2)) {
                            actualizaR.setSegundobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(3)) {
                            actualizaR.setTercerobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(4)) {
                            actualizaR.setCuartobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(5)) {
                            actualizaR.setQuintobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(6)) {
                            actualizaR.setSextobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(7)) {
                            actualizaR.setSeptimobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(8)) {
                            actualizaR.setPrimerod(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(9)) {
                            actualizaR.setSegundod(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(10)) {
                            actualizaR.setTercerd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(11)) {
                            actualizaR.setCuartod(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(12)) {
                            actualizaR.setQuintod(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(13)) {
                            actualizaR.setSextod(promedio);
                        }
                        adm.actualizar(actualizaR);
                    } else {
                        Notasrecord nuevoR = new Notasrecord();
                        nuevoR.setEstudiante(matriculas1.getEstudiante());
                        if (matriculas1.getCurso().getSecuencia().equals(1)) {
                            nuevoR.setPrimerobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(2)) {
                            nuevoR.setSegundobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(3)) {
                            nuevoR.setTercerobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(4)) {
                            nuevoR.setCuartobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(5)) {
                            nuevoR.setQuintobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(6)) {
                            nuevoR.setSextobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(7)) {
                            nuevoR.setSeptimobd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(8)) {
                            nuevoR.setPrimerod(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(9)) {
                            nuevoR.setSegundod(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(10)) {
                            nuevoR.setTercerd(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(11)) {
                            nuevoR.setCuartod(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(12)) {
                            nuevoR.setQuintod(promedio);
                        } else if (matriculas1.getCurso().getSecuencia().equals(13)) {
                            nuevoR.setSextod(promedio);
                        }

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
