/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.Actagrado;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Detallepregunta;
import jcinform.persistencia.Equivalencias;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Global;
import jcinform.persistencia.Institucion;
import jcinform.persistencia.MateriaProfesor;
import jcinform.persistencia.Materiasgrado;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Notanotas;
import jcinform.persistencia.Notasacta;
import jcinform.persistencia.Notasrecord;
import jcinform.persistencia.ParametrosGlobales;
import jcinform.persistencia.Periodo;
import jcinform.persistencia.Pregunta;
import jcinform.persistencia.Respuestasencuesta;
import jcinform.persistencia.Sistemacalificacion;
import jcinform.persistencia.Textos;
import jcinform.procesos.Administrador;
import net.sf.jasperreports.engine.JRDataSource;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import sources.ActaGeneralDataSource;
import sources.DisciplinaDataSource;
import sources.Nota;
import sources.NotasClaseTemp;
import sources.NumerosaLetras;
import sources.ReporteCertificadoDataSource;
import sources.ReporteEvaluacionDataSource;
import sources.ReporteExamenesDataSource;
import sources.ReporteGradoDataSource;
import sources.ReporteNoasLibretaDataSource;
import sources.ReporteNotasDataSource;
import sources.ReporteProfesorDataSource;
import sources.ReportePromocionDataSource;
import sources.ReporteResumenDataSource;
import sources.ResumenClase;

/**
 *
 * @author Ismael Jadan
 */
public class reportesClase {

    public reportesClase() {
    }

    public JRDataSource evaluacion(Cursos curso, Matriculas matricula) {
        Administrador adm = new Administrador();
//        Session ses = Sessions.getCurrent();
//        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        ArrayList detalle = new ArrayList();
        List hoy = adm.query("SELECT mat FROM Respuestasencuesta AS mat "
                + "WHERE mat.matricula.curso.codigocur = '" + curso.getCodigocur() + "' "
                + "order by mat.matricula.estudiante.apellido, mat.detallepregunta.pregunta.orden ");

        for (Iterator it = hoy.iterator(); it.hasNext();) {
            Respuestasencuesta elem = (Respuestasencuesta) it.next();
            detalle.add(elem);
        }
        ReporteEvaluacionDataSource ds = new ReporteEvaluacionDataSource(detalle);


        return ds;
    }

    public JRDataSource distributivo() {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        ArrayList detalle = new ArrayList();
        List hoy = adm.query("SELECT mat FROM MateriaProfesor AS mat "
                + "WHERE mat.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "order by mat.curso.secuencia");

        for (Iterator it = hoy.iterator(); it.hasNext();) {
            MateriaProfesor elem = (MateriaProfesor) it.next();
            detalle.add(elem);
        }
        ReporteProfesorDataSource ds = new ReporteProfesorDataSource(detalle);


        return ds;
    }
//CUADRO DE NOTAS POR MATERIA

    public ArrayList notasd(Cursos curso, Global materia, Sistemacalificacion sistema) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//     int tamanio=0;
        Administrador adm = new Administrador();
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.seimprime = true  "
                + "and o.orden <= '" + sistema.getOrden() + "' order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true  and o.sistema.orden  <= '" + sistema.getOrden() + "' "
                + "order by o.sistema.orden ");
        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        Map parametros = new HashMap();
        Institucion insts = curso.getPeriodo().getInstitucion();
        parametros.put("denominacion", insts.getDenominacion());
        parametros.put("nombre", insts.getNombre());
        parametros.put("periodo", periodo.getDescripcion());
        parametros.put("slogan", insts.getSlogan());
        insts = null;
//PARA EL CUADRO DE PORCENTAJES, SIRVE PARA SACAR LOS PORCENTAJES
        try {
            int i = 0;
            String q2 = "Select  count(nota1) from matriculas "
                    + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "and notas.materia = '" + materia.getCodigo() + "' and notas.disciplina = false "
                    + "where matriculas.curso = '" + curso.getCodigocur() + "' and matriculas.estado in ('Matriculado','Recibir Pase')  "
                    + "order by estudiantes.apellido";
            List resulValor = adm.queryNativo(q2);
            Long totalEstudiantes = (Long) ((Vector) resulValor.get(0)).get(0);
            for (Iterator<Equivalencias> it = equivalencias.iterator(); it.hasNext();) {
                Equivalencias equivalencias1 = it.next();
                parametros.put("eq" + (i + 1), equivalencias1.getNombre());
                parametros.put("ran" + (i + 1), equivalencias1.getValorminimo() + " - " + equivalencias1.getValormaximo());

                String notaAseleccionar = notas.get(notas.size() - 1).getNota();
                String q = "Select  count(" + notaAseleccionar + ")  from matriculas "
                        + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                        + "left join notas on matriculas.codigomat = notas.matricula "
                        + "and notas.materia = '" + materia.getCodigo() + "' and notas.disciplina = false "
                        + "where matriculas.curso = '" + curso.getCodigocur() + "' and  " + notaAseleccionar + " "
                        + " BETWEEN " + equivalencias1.getValorminimo() + " AND " + equivalencias1.getValormaximo() + " "
                        + "and matriculas.estado in ('Matriculado','Recibir Pase')  "
                        + "order by estudiantes.apellido";
//                 System.out.println(""+q);
                List valor = adm.queryNativo(q);
                Long val = (Long) ((Vector) valor.get(0)).get(0);
                System.out.println("" + val);
                parametros.put("val" + (i + 1), val);
                Double porcentaje = (val.doubleValue() * 100) / totalEstudiantes;
                System.out.println("" + porcentaje);
                parametros.put("por" + (i + 1), porcentaje);
                i++;
            }
            equivalencias = null;
        } catch (Exception e) {
            System.out.println("ERROR EN CUADRO DE EQUIVALENCIAS EN REPORTE DE NOTAS POR MATERIA:_  " + e);
        }
//    tamanio = sistemas.size();
        //List<Matriculas> matriculas = adm.query("Select o from Matriculas as o ");
        String q = "Select matriculas.codigomat, " + query + "  from matriculas "
                + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                + "left join notas on matriculas.codigomat = notas.matricula "
                + "and notas.materia = '" + materia.getCodigo() + "' and notas.disciplina = false "
                + "where matriculas.curso = '" + curso.getCodigocur() + "' "
                + "and matriculas.estado in ('Matriculado','Recibir Pase','Retirado','Emitir Pase')  "
                + "order by estudiantes.apellido";
        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        int cont = 1;
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            //row = new Row();
            Matriculas matriculaNo = null;
//            MateriaProfesor mprofesor = null;
            int ksis = 0;

            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                Double val = 0.0;
                Nota nota = new Nota();
                try {
                    if (dos.equals(null)) {
                        dos = new Double(0.0);
                    }
                } catch (Exception e) {
                    dos = new Double(0.0);
                }
                if (j >= 1) {
                    val = redondear((Double) dos, 2);
                    nota.setMatricula(matriculaNo);
                    nota.setContador(cont);
                    nota.setNota(val);
                    nota.setMateria(materia);
                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    lisNotas.add(nota);
                    ksis++;
                } else {
                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                    //mprofesor = adm.query("Select o from ")
                }
            }
            cont++;
        }
        nativo = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        ArrayList arr = new ArrayList();
        arr.add(ds);
        arr.add(parametros);
        return arr;
    }

    public ArrayList notasdisciplina(Cursos curso, Global materia, Sistemacalificacion sistema) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//     int tamanio=0;
        Administrador adm = new Administrador();
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.seimprime = true  and o.orden <= '" + sistema.getOrden() + "' order by o.orden ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.orden  <= '" + sistema.getOrden() + "' and o.sistema.seimprime = true "
                + "order by o.sistema.orden ");
        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }
//    tamanio = sistemas.size();
        //List<Matriculas> matriculas = adm.query("Select o from Matriculas as o ");
        String q = "Select matriculas.codigomat, " + query + "  from matriculas "
                + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                + "left join notas on matriculas.codigomat = notas.matricula "
                + "and notas.materia = '" + materia.getCodigo() + "' and notas.disciplina = true "
                + "where matriculas.curso = '" + curso.getCodigocur() + "' and matriculas.estado in ('Matriculado','Recibir Pase','Emitir Pase')  "
                + "order by estudiantes.apellido";
//     System.out.println(""+q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        int cont = 1;
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            //row = new Row();
            Matriculas matriculaNo = null;
            MateriaProfesor mprofesor = null;
            int ksis = 0;
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                Double val = 0.0;
                Nota nota = new Nota();
                try {
                    if (dos.equals(null)) {
                        dos = new Double(0.0);
                    }
                } catch (Exception e) {
                    dos = new Double(0.0);
                }
                if (j >= 1) {
                    val = redondear((Double) dos, 2);
                    nota.setMatricula(matriculaNo);
                    nota.setNota(val);
                    nota.setMateria(materia);
                    nota.setContador(cont);
                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    lisNotas.add(nota);
                    ksis++;
                } else {
                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                    //mprofesor = adm.query("Select o from ")
                }
            }
            cont++;
        }
        nativo = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        ArrayList arr = new ArrayList();
        arr.add(ds);
        return arr;
        //return ds;

    }

    public JRDataSource cuadrocalificaciones(Cursos curso, Sistemacalificacion sistema, Double desde, Double hasta) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        Double numeroDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.orden <= '" + sistema.getOrden() + "' order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis  = '" + sistema.getCodigosis() + "' "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "  "
                + "order by o.sistema.orden ");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }
//    tamanio = sistemas.size();
        //List<Matriculas> matriculas = adm.query("Select o from Matriculas as o ");
/*String q = "Select matricula,materia, "+query+" from notas " +
        "where matricula in (select codigomat from matriculas where  curso  =  '"+curso.getCodigocur()+"' ) " +
        " ";*/
        //and matriculas.estado in ('Matriculado','Recibir Pase','Retirado')
        String q = "Select codigomap, mat.codigomat,notas.materia, " + query + "  "
                + "from notas, materia_profesor, matriculas mat, estudiantes est "
                + "where notas.materia =  materia_profesor.materia and materia_profesor.curso = '" + curso.getCodigocur() + "' "
                + " AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "' "
                + " and estado in ('Matriculado','Recibir Pase','Emitir Pase','Retirado') )"
                + "and notas.disciplina = false and materia_profesor.seimprime = true  "
                + "order by CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";
        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        Integer cont = 0;
        String matricula = "";
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            //row = new Row();
            Matriculas matriculaNo = null;
            Global materiaNo = null;
            MateriaProfesor maprofesor = null;
            int ksis = 0;
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                Double val = 0.0;
                Nota nota = new Nota();
                try {
                    if (dos.equals(null)) {
                        dos = new Double(0.0);
                    }
                } catch (Exception e) {
                    dos = new Double(0.0);
                }
                if (j >= 3) {

                    val = redondear((Double) dos, 2);
                    if (materiaNo.getCodigo().equals(0)) {
                        val = redondear((Double) dos, numeroDecimalesDisc.intValue());
                    }
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    nota.setContador(cont);
                    matricula = matriculaNo.toString();
                    if (maprofesor.getCuantitativa() == false) {

                        nota.setNota(equivalencia(dos, equivalencias));
                    } else {
                        nota.setNota(val.toString());
                        if (val == 0.0) {
                            nota.setNota("");
                        }

                    }
                    if (val >= desde && val <= hasta) {
                    } else {
                        nota.setNota("");
                    }



                    nota.setMprofesor(maprofesor);
                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    lisNotas.add(nota);
                    ksis++;
                } else if (j == 1) {
                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                } else if (j == 2) {
                    materiaNo = (Global) adm.buscarClave((Integer) dos, Global.class);
                } else if (j == 0) {
                    maprofesor = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                }
                if (matriculaNo != null && j > 1) {
                    if (!matriculaNo.toString().equals(matricula)) {
                        cont++;
                    }
                }
            }


        }
        nativo = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;

    }

    public List<Matriculas> cuadroverificar(Cursos curso, Sistemacalificacion sistema) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "and o.orden <= '" + sistema.getOrden() + "' "
                + "and o.trimestre.codigotrim = '" + sistema.getTrimestre().getCodigotrim() + "' "
                + "and o.seimprime = true order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.orden  <= '" + sistema.getOrden() + "'  "
                + "and o.sistema.trimestre.codigotrim =  '" + sistema.getTrimestre().getCodigotrim() + "' "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true "
                + "order by o.sistema.orden ");

        List<Notanotas> notaFinal = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis = '" + sistema.getCodigosis() + "'  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true");
        if (notaFinal.size() <= 0) {
            try {
                Messagebox.show("No ha parametrizado el Promedio Final en Aportes...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (notas.size() <= 0) {
            try {
                Messagebox.show("No han nada que imprimir Aportes en 0 ...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalenciasSuple = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'SU' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String q = "Select codigomap, mat.codigomat,notas.materia, " + query + ", 'obs'  from notas, materia_profesor , matriculas mat, estudiantes est "
                + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                + "and materia_profesor.curso = '" + curso.getCodigocur() + "' "
                + "and notas.promedia = true and notas.disciplina = false and materia_profesor.seimprime = true  "
                + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "'"
                + "  AND NOTAS.MATERIA NOT IN (SELECT CODIGO FROM GLOBAL WHERE GRUPO = 'MAT' AND DESCRIPCION LIKE '%PROME%')  ) "
                + "order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";
        System.out.println("cuadro final: " + q);
        List nativo = adm.queryNativo(q);
        int cont = 0;
        String matricula = "";
        List<Matriculas> aprobadMatriculas = new ArrayList<Matriculas>();
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            Matriculas matriculaNo = null;
            MateriaProfesor mprofesor = null;
            Double sumatoria = 0.0;
            String obs = "";
            Integer obs1 = 0;
            int ksis = 0;

            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                Double val = 0.0;
                Nota nota = new Nota();
                try {
                    if (dos.equals(null)) {
                        dos = new Double(0.0);
                    }
                } catch (Exception e) {
                    dos = new Double(0.0);
                }
                if (j == (vec.size() - 1)) {
//                    val = redondear((Double) dos, 2);
//                    nota.setMatricula(matriculaNo);
                    matricula = matriculaNo.toString();
                } else if (j >= 3) {
                    val = redondear((Double) dos, 2);
//                    nota.setMatricula(matriculaNo);
                    matricula = matriculaNo.toString();
//                    if (mprofesor.getCuantitativa() == false) {
//                        nota.setNota(equivalencia(dos, equivalencias));
//                    } else {
//                        nota.setNota(val.toString());
//                        if (val == 0.0) {
//                            nota.setNota("");
//                        }
//                    }
                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    if (nota.getSistema().getPromediofinal().equals("SM")) {
                        if (val < 25) {
                            obs = "Pierde";
                            obs1++;
                        } else {
                            obs = "";
                        }
                        sumatoria = val;
                    } else if (nota.getSistema().getPromediofinal().equals("SU") && !obs.equals("Pierde")) {
                        if (sumatoria < 40) {
                            try {
                                Double valor = new Double(equivalenciaSupletorio(sumatoria, equivalenciasSuple) + "");
                                if (val < valor) {
                                    obs = "Pierde";
                                    obs1++;
                                } else {
                                    obs = "";
                                }
                            } catch (Exception e) {
                            }
                        } else {
                            obs = "";
                        }
                    }
                    ksis++;
                } else if (j == 1) {
                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                } else if (j == 0) {
//                    mprofesor = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                }
                if (matriculaNo != null && j > 1) {
                    if (!matriculaNo.toString().equals(matricula)) {
                        cont++;
                    }
                }
            }
            if(matricula.contains("HERMIDA")){
                    System.out.println("cambios ");
            }
            
            if (obs1 > 0) {
                if (!aprobadMatriculas.contains(matriculaNo)) {
                    aprobadMatriculas.add(matriculaNo);
                }
            } 
        }
        nativo = null;
        return aprobadMatriculas;

    }

    public JRDataSource cuadrofinalsupletorio(Cursos curso, Sistemacalificacion sistema, Double desde, Double hasta) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "and o.orden <= '" + sistema.getOrden() + "' "
                + "and o.trimestre.codigotrim = '" + sistema.getTrimestre().getCodigotrim() + "' "
                + "and o.seimprime = true order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.orden  <= '" + sistema.getOrden() + "'  "
                + "and o.sistema.trimestre.codigotrim =  '" + sistema.getTrimestre().getCodigotrim() + "' "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true "
                + "order by o.sistema.orden ");

        List<Notanotas> notaFinal = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis = '" + sistema.getCodigosis() + "'  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true");
        if (notaFinal.size() <= 0) {
            try {
                Messagebox.show("No ha parametrizado el Promedio Final en Aportes...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Notanotas nfinal = notaFinal.get(0);
        if (notas.size() <= 0) {
            try {
                Messagebox.show("No han nada que imprimir Aportes en 0 ...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalenciasSuple = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'SU' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

//Sistemacalificacion sd;
//sd.getTrimestre().getCodigotrim();


        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";

        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }

        String q = "Select codigomap, matricula,notas.materia, " + query + ", 'obs'  from notas, materia_profesor , matriculas mat, estudiantes est "
                + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                + "and materia_profesor.curso = '" + curso.getCodigocur() + "' "
                + "and notas.promedia = true and notas.disciplina = false and materia_profesor.seimprime = true  "
                + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "'  ) "
                + "order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";

        System.out.println("cuadro final: " + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        int cont = 0;
        String matricula = "";
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            //row = new Row();
            Matriculas matriculaNo = null;
            Global materiaNo = null;
            MateriaProfesor mprofesor = null;
            MateriaProfesor mprofesor1 = null;
            Double aprovecha = 0.0;
            Double disciplina = 0.0;
            Double sumatoria = 0.0;
            String obs = "";
            int ksis = 0;
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                Double val = 0.0;
                Nota nota = new Nota();
                try {
                    if (dos.equals(null)) {
                        dos = new Double(0.0);
                    }
                } catch (Exception e) {
                    dos = new Double(0.0);
                }
                if (j == (vec.size() - 1)) {
//                    val = redondear((Double) dos, 2);
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    nota.setContador(cont);
                    matricula = matriculaNo.toString();
                    Sistemacalificacion sistemp1 = new Sistemacalificacion();
                    sistemp1.setNombre("OBS");
                    sistemp1.setAbreviatura("OBS");
                    sistemp1.setTrimestre(((Sistemacalificacion) sistemas.get(ksis - 1)).getTrimestre());
                    nota.setNota("APRO");
                    nota.setNota("" + obs);
                    nota.setSistema(sistemp1);
                    nota.setAprovechamiento(aprovecha);
                    nota.setDisciplina(disciplina);
                    lisNotas.add(nota);
                } else if (j >= 3) {
                    val = redondear((Double) dos, 2);
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    nota.setContador(cont);
                    matricula = matriculaNo.toString();
                    //nota.setNota(val);

                    if (mprofesor.getCuantitativa() == false) {
                        nota.setNota(equivalencia(dos, equivalencias));
                    } else {
                        nota.setNota(val.toString());
//                        aprovecha+=val;
//                        System.out.println(matriculaNo+":::"+aprovecha);
                        if (val == 0.0) {
                            nota.setNota("");
                        }
                    }
//                    if (val >= desde && val <= hasta) {
//                    } else {
//                        nota.setNota("");
//                    }
                    int tamaSistema = sistemas.size() - 1;
//                    if (ksis == tamaSistema) {
//                        nota.setMprofesor(mprofesor);
//                    } else {
//                        mprofesor1.getEmpleado().setApellidos("");
//                        mprofesor1.getEmpleado().setNombres("");
//                        nota.setMprofesor(mprofesor1);
//
//                    }


                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    if (nota.getSistema().getPromediofinal().equals("SM")) {
                        if (val < 25) {
                            obs = "Pierde";
                        }
                        sumatoria = val;
                    } else if (nota.getSistema().getPromediofinal().equals("SU") && !obs.equals("Pierde")) {
                        if (sumatoria < 40) {
                            try {
                                Double valor = new Double(equivalenciaSupletorio(sumatoria, equivalenciasSuple) + "");
                                if (val < valor) {
                                    obs = "Pierde";
                                } else {
                                    obs = "";
                                }
                            } catch (Exception e) {
                                //System.out.println("" + e);
                            }
                        } else {
                            obs = "";
                        }
                    }
                    nota.setAprovechamiento(aprovecha);
                    nota.setDisciplina(disciplina);
                    lisNotas.add(nota);
                    ksis++;
                } else if (j == 1) {

                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);

                    List valor = adm.queryNativo("SELECT CAST(AVG(" + nfinal.getNota() + ")as decimal (9,3)) FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND cuantitativa = TRUE AND disciplina = FALSE AND  promedia = TRUE AND materia > 1 AND  seimprime = TRUE GROUP BY MATRICULA ");
                    if (valor.size() > 0) {
                        aprovecha = ((BigDecimal) (((Vector) valor.get(0)).get(0))).doubleValue();
                    }
//                    String querAprov = "SELECT (" + nfinal.getNota() + ")  FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND cuantitativa = TRUE AND disciplina = FALSE AND  promedia = TRUE AND materia > 1 AND  seimprime = TRUE ";
//                    List valor = adm.queryNativo(querAprov);
//                    System.out.println("APROVECHAMIENTO: "+querAprov);
//                    if (valor.size() > 0) {
//                        aprovecha = (Double) ((((Vector) valor.get(0)).get(0)));
//                    }
                    //System.out.println("SELECT CAST(("+nfinal.getNota()+")as decimal (9,0)) FROM notas WHERE matricula = '"+matriculaNo.getCodigomat()+"' AND materia = 0 ");
                    valor = adm.queryNativo("SELECT CAST(IF(" + nfinal.getNota() + " is null,0," + nfinal.getNota() + ")as decimal (9,0)) FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND materia = 0 ");
//                    System.out.println("" + valor);
                    if (valor.size() > 0) {
                        disciplina = ((BigDecimal) (((Vector) valor.get(0)).get(0))).doubleValue();
                    }
//                    disciplina = aprovecha;

                } else if (j == 2) {
                    materiaNo = (Global) adm.buscarClave((Integer) dos, Global.class);
                } else if (j == 0) {
                    mprofesor = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                    mprofesor1 = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                }
                if (matriculaNo != null && j > 1) {
                    if (!matriculaNo.toString().equals(matricula)) {
                        cont++;
                    }
                }


            }
        }
        nativo = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;

    }

    public JRDataSource cuadrocalificacionessupletorio(Cursos curso, Sistemacalificacion sistema, Double desde, Double hasta) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        Double numeroDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.orden <= '" + sistema.getOrden() + "' order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis  = '" + sistema.getCodigosis() + "' "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "  "
                + "order by o.sistema.orden ");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }
//    tamanio = sistemas.size();
        //List<Matriculas> matriculas = adm.query("Select o from Matriculas as o ");
/*String q = "Select matricula,materia, "+query+" from notas " +
        "where matricula in (select codigomat from matriculas where  curso  =  '"+curso.getCodigocur()+"' ) " +
        " ";*/
        //and matriculas.estado in ('Matriculado','Recibir Pase','Retirado')
        String q = "Select codigomap, mat.codigomat,notas.materia, " + query + "  "
                + "from notas, materia_profesor, matriculas mat, estudiantes est, GLOBAL glo "
                + "where notas.materia =  materia_profesor.materia and materia_profesor.curso = '" + curso.getCodigocur() + "'  AND materia_profesor.materia = glo.codigo  "
                + " AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "' "
                + " and estado in ('Matriculado','Recibir Pase','Emitir Pase','Retirado') )"
                + "and notas.disciplina = false and materia_profesor.seimprime = true   AND glo.descripcion NOT LIKE '%PROMED%'  "
                + "order by CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";
        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        Integer cont = 0;
        String matricula = "";
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            //row = new Row();
            Matriculas matriculaNo = null;
            Global materiaNo = null;
            MateriaProfesor maprofesor = null;
            int ksis = 0;
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                Double val = 0.0;
                Nota nota = new Nota();
                try {
                    if (dos.equals(null)) {
                        dos = new Double(0.0);
                    }
                } catch (Exception e) {
                    dos = new Double(0.0);
                }
                if (j >= 3) {

                    val = redondear((Double) dos, 2);
                    if (materiaNo.getCodigo().equals(0)) {
                        val = redondear((Double) dos, numeroDecimalesDisc.intValue());
                    }
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    nota.setContador(cont);
                    matricula = matriculaNo.toString();
                    if (maprofesor.getCuantitativa() == false) {

                        nota.setNota(equivalencia(dos, equivalencias));
                    } else {
                        nota.setNota(val.toString());
                        if (val == 0.0) {
                            nota.setNota("");
                        }

                    }
                    if (val >= desde && val < hasta) {
                    } else {
                        nota.setNota("");
                    }



                    nota.setMprofesor(maprofesor);
                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    if ((!nota.getMateria().getDescripcion().toUpperCase().contains("PROME") && !nota.getMateria().getDescripcion().toUpperCase().contains("DISCI")) && !(nota.getNota().toString().equals(""))) {
                        lisNotas.add(nota);
                    } else {
////                        matriculaNo.getEstudiante().setApellido("");
////                        matriculaNo.getEstudiante().setNombre("");
////                        lisNotas.add(nota);
                    }

                    ksis++;
                } else if (j == 1) {
                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                } else if (j == 2) {
                    materiaNo = (Global) adm.buscarClave((Integer) dos, Global.class);
                } else if (j == 0) {
                    maprofesor = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                }
                if (matriculaNo != null && j > 1) {
                    if (!matriculaNo.toString().equals(matricula)) {
                        cont++;
                    }
                }
            }


        }
        nativo = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;

    }

    //Promedio por curso
    public JRDataSource promediocurso(Sistemacalificacion sistema) {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        Double numeroDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.orden <= '" + sistema.getOrden() + "' order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis  = '" + sistema.getCodigosis() + "' "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "order by o.sistema.orden ");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }

        //2 666-882
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];
        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }
        List<Cursos> cursos = adm.query("Select o from Cursos as o where o.periodo.codigoper = '" + sistema.getPeriodo().getCodigoper() + "' ");
        List<Nota> lisNotas = new ArrayList();
        for (Iterator<Cursos> it = cursos.iterator(); it.hasNext();) {
            Cursos curso = it.next();
            String q = "Select round(avg (" + query + "),2)  from notas, materia_profesor, matriculas mat, estudiantes est "
                    + "where notas.materia =  materia_profesor.materia and materia_profesor.curso = '" + curso.getCodigocur() + "'  "
                    + "AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                    + "and notas.promedia = true  "
                    + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "'  )"
                    + "and notas.disciplina = false  "
                    + "order by est.apellido, materia_profesor.orden";
            System.out.println("" + q);
            List nativo = adm.queryNativo(q);

            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                Matriculas matriculaNo = new Matriculas(-1);
                matriculaNo.setCurso(curso);
                Nota n = new Nota();
                n.setMatricula(matriculaNo);
                n.setNota((vec.get(0)));
                lisNotas.add(n);
            }
            nativo = null;

        }
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;


    }

    public JRDataSource conteoMatriculas() {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Nota> lisNotas = new ArrayList();
        List total = adm.query("Select o.curso, count(o) from Matriculas as o"
                + " where o.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' and  "
                + " o.estado in ('Matriculado','Recibir Pase') "
                + "group by o.curso  order by o.curso.secuencia, o.curso.paralelo.descripcion ");
        for (Iterator it = total.iterator(); it.hasNext();) {
            Object object[] = (Object[]) it.next();
            Nota n = new Nota();
            Cursos cu = (Cursos) object[0];
            Long a = (Long) object[1];
            n.setCurso(cu);
            n.setNota(a);
            lisNotas.add(n);
        }


        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;


    }

    public JRDataSource conteoReligion() {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Nota> lisNotas = new ArrayList();
        List total = adm.query("Select o.curso, count(o), o.estudiante.religion from Matriculas as o"
                + " where o.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' and  "
                + " o.estado in ('Matriculado','Recibir Pase') "
                + "group by o.estudiante.religion,o.curso  order by o.curso.secuencia, o.curso.paralelo.descripcion ");
        for (Iterator it = total.iterator(); it.hasNext();) {
            Object object[] = (Object[]) it.next();
            Nota n = new Nota();
            Cursos cu = (Cursos) object[0];
            Long a = (Long) object[1];
            n.setCurso(cu);
            n.setNota(a);
            n.setCargo1(object[2].toString());
            lisNotas.add(n);
        }


        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;


    }

    //Promedio por curso
    public JRDataSource mejoresporcurso(Sistemacalificacion sistema) {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        Double numeroDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.orden <= '" + sistema.getOrden() + "' order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis  = '" + sistema.getCodigosis() + "' "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "order by o.sistema.orden ");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }

        //2 666-882
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];
        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }
        List<Cursos> cursos = adm.query("Select o from Cursos as o where o.periodo.codigoper = '" + sistema.getPeriodo().getCodigoper() + "' ");
        List<Nota> lisNotas = new ArrayList();
        for (Iterator<Cursos> it = cursos.iterator(); it.hasNext();) {
            Cursos curso = it.next();
            String q = "Select round(avg (" + query + "),3) valor, est.apellido ,est.nombre  "
                    + "from notas, materia_profesor, matriculas mat, estudiantes est "
                    + "where notas.materia =  materia_profesor.materia and materia_profesor.curso = '" + curso.getCodigocur() + "'  "
                    + "AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                    + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "'  )"
                    + "and notas.disciplina = false and notas.promedia = true  GROUP BY notas.matricula "
                    + "order by 1 desc limit 3";
            System.out.println("" + q);
            List nativo = adm.queryNativo(q);

            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                Matriculas matriculaNo = new Matriculas(-1);
                matriculaNo.setCurso(curso);
                Nota n = new Nota();
                Estudiantes estu = new Estudiantes(-2);
                estu.setApellido((vec.get(1) + ""));
                estu.setNombre((vec.get(2) + ""));
                matriculaNo.setEstudiante(estu);
                n.setMatricula(matriculaNo);
                n.setNota((vec.get(0)));
                lisNotas.add(n);
            }
            nativo = null;

        }
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;


    }

    public JRDataSource cuadroexamenes(Cursos curso) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
//        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        //Double numeroDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
        List<Materiasgrado> notas = adm.query("Select o from Materiasgrado as o "
                + "where  o.curso.codigocur = '" + curso.getCodigocur() + "' "
                + " order by o.codigo ");
        String query = "";
        for (Materiasgrado notass : notas) {
            query += notass.getColumna() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String q = "SELECT CONCAT(est.apellido,' ',est.nombre), " + query + "   FROM notasgrado notas,matriculas mat,estudiantes est "
                + "WHERE notas.matricula = mat.codigomat AND mat.estudiante = est.codigoest AND mat.curso = '" + curso.getCodigocur() + "' order by 1";
        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        int i = 1;
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            int ksis = 0;
            String estudiante = "";
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                Double val = 0.0;
                Nota nota = new Nota();
                if (j >= 1) {
                    //                   val = redondear((Double) dos, 2);
                    nota.setCargo2(((Materiasgrado) notas.get(ksis)).getNombre());

                    nota.setNota(redondear((Double) dos, 0).intValue());
                    if ((j + 1) == vec.size()) {
                        String s = "##00.00##";
                        DecimalFormat decimalFormat = new DecimalFormat(s);
                        //DecimalFormat formateador = new DecimalFormat("####.###");
                        // Esto sale en pantalla con cuatro decimales, es decir, 3,4324
//                        System.out.println("formato: " + decimalFormat.format(redondear((Double) dos, 3)));
                        nota.setNota(decimalFormat.format(redondear((Double) dos, 3)));
                    }
                    nota.setProfesor(((Materiasgrado) notas.get(ksis)).getProfesor().getApellidos() + " " + ((Materiasgrado) notas.get(0)).getProfesor().getNombres());
                    nota.setCurso(curso);
                    nota.setCargo1(estudiante);
                    nota.setCargo3("" + i);
                    lisNotas.add(nota);
                    ksis++;
                } else if (j == 0) {
                    estudiante = dos.toString();//en seteo el nombre del estudiante
                }
            }
            i++;
        }
        nativo = null;
        ReporteExamenesDataSource ds = new ReporteExamenesDataSource(lisNotas);
        return ds;

    }

    public JRDataSource cuadrofinal(Cursos curso, Sistemacalificacion sistema, Double desde, Double hasta) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
//                "where o.periodo.codigoper = '"+periodo.getCodigoper()+"' and o.espromedio = true   order by o.orden ");
//        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
//                "where  o.sistema.periodo.codigoper = '"+periodo.getCodigoper()+"' and  o.sistema.espromedio = true  " +
//                "order by o.sistema.orden ");
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "and o.orden <= '" + sistema.getOrden() + "' "
                + "and o.trimestre.codigotrim = '" + sistema.getTrimestre().getCodigotrim() + "' "
                + "and o.seimprime = true order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.orden  <= '" + sistema.getOrden() + "'  "
                + "and o.sistema.trimestre.codigotrim =  '" + sistema.getTrimestre().getCodigotrim() + "' "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true "
                + "order by o.sistema.orden ");

        List<Notanotas> notaFinal = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis = '" + sistema.getCodigosis() + "'  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true");
        if (notaFinal.size() <= 0) {
            try {
                Messagebox.show("No ha parametrizado el Promedio Final en Aportes...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Notanotas nfinal = notaFinal.get(0);
        if (notas.size() <= 0) {
            try {
                Messagebox.show("No han nada que imprimir Aportes en 0 ...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

//Sistemacalificacion sd;
//sd.getTrimestre().getCodigotrim();


        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";

        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }

        String q = "Select codigomap, matricula,notas.materia, " + query + "  from notas, materia_profesor , matriculas mat, estudiantes est "
                + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                + "and materia_profesor.curso = '" + curso.getCodigocur() + "' "
                + "and notas.promedia = true and notas.disciplina = false and materia_profesor.seimprime = true  "
                + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "'  ) "
                + "order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";

        System.out.println("cuadro final: " + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        int cont = 0;
        String matricula = "";
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            //row = new Row();
            Matriculas matriculaNo = null;
            Global materiaNo = null;
            MateriaProfesor mprofesor = null;
            MateriaProfesor mprofesor1 = null;
            Double aprovecha = 0.0;
            Double disciplina = 0.0;
            int ksis = 0;
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                Double val = 0.0;
                Nota nota = new Nota();
                try {
                    if (dos.equals(null)) {
                        dos = new Double(0.0);
                    }
                } catch (Exception e) {
                    dos = new Double(0.0);
                }
                if (j >= 3) {
                    val = redondear((Double) dos, 2);
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    nota.setContador(cont);
                    matricula = matriculaNo.toString();
                    //nota.setNota(val);

                    if (mprofesor.getCuantitativa() == false) {
                        nota.setNota(equivalencia(dos, equivalencias));
                    } else {
                        nota.setNota(val.toString());
//                        aprovecha+=val;
//                        System.out.println(matriculaNo+":::"+aprovecha);
                        if (val == 0.0) {
                            nota.setNota("");
                        }
                    }
                    if (val >= desde && val <= hasta) {
                    } else {
                        nota.setNota("");
                    }
                    int tamaSistema = sistemas.size() - 1;
                    if (ksis == tamaSistema) {
                        nota.setMprofesor(mprofesor);
                    } else {
                        mprofesor1.getEmpleado().setApellidos("");
                        mprofesor1.getEmpleado().setNombres("");
                        nota.setMprofesor(mprofesor1);

                    }


                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    nota.setAprovechamiento(aprovecha);
                    nota.setDisciplina(disciplina);
                    lisNotas.add(nota);
                    ksis++;
                } else if (j == 1) {

                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);

                    List valor = adm.queryNativo("SELECT CAST(AVG(" + nfinal.getNota() + ")as decimal (9,3)) FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND cuantitativa = TRUE AND disciplina = FALSE AND  promedia = TRUE AND materia > 1 AND  seimprime = TRUE GROUP BY MATRICULA ");
                    if (valor.size() > 0) {
                        aprovecha = ((BigDecimal) (((Vector) valor.get(0)).get(0))).doubleValue();
                    }
//                    String querAprov = "SELECT (" + nfinal.getNota() + ")  FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND cuantitativa = TRUE AND disciplina = FALSE AND  promedia = TRUE AND materia > 1 AND  seimprime = TRUE ";
//                    List valor = adm.queryNativo(querAprov);
//                    System.out.println("APROVECHAMIENTO: "+querAprov);
//                    if (valor.size() > 0) {
//                        aprovecha = (Double) ((((Vector) valor.get(0)).get(0)));
//                    }
                    //System.out.println("SELECT CAST(("+nfinal.getNota()+")as decimal (9,0)) FROM notas WHERE matricula = '"+matriculaNo.getCodigomat()+"' AND materia = 0 ");
                    valor = adm.queryNativo("SELECT CAST(IF(" + nfinal.getNota() + " is null,0," + nfinal.getNota() + ")as decimal (9,0)) FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND materia = 0 ");
//                    System.out.println("" + valor);
                    if (valor.size() > 0) {
                        disciplina = ((BigDecimal) (((Vector) valor.get(0)).get(0))).doubleValue();
                    }
//                    disciplina = aprovecha;

                } else if (j == 2) {
                    materiaNo = (Global) adm.buscarClave((Integer) dos, Global.class);
                } else if (j == 0) {
                    mprofesor = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                    mprofesor1 = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                }
                if (matriculaNo != null && j > 1) {
                    if (!matriculaNo.toString().equals(matricula)) {
                        cont++;
                    }
                }


            }
        }
        nativo = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;

    }

    public JRDataSource cuadrofinal3(Cursos curso, Sistemacalificacion sistema, Double desde, Double hasta) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
//                "where o.periodo.codigoper = '"+periodo.getCodigoper()+"' and o.espromedio = true   order by o.orden ");
//        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
//                "where  o.sistema.periodo.codigoper = '"+periodo.getCodigoper()+"' and  o.sistema.espromedio = true  " +
//                "order by o.sistema.orden ");
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + " and o.orden <= '" + sistema.getOrden() + "' "
                + "and o.seimprime = true and o.espromedio = true order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.orden  <= '" + sistema.getOrden() + "'  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true "
                + "and o.sistema.espromedio = true order by o.sistema.orden ");

        List<Notanotas> notaFinal = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis = '" + sistema.getCodigosis() + "'  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'    and o.sistema.espromedio = true");
        if (notaFinal.size() <= 0) {
            try {
                Messagebox.show("No ha parametrizado el Promedio Final en Aportes...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Notanotas nfinal = notaFinal.get(0);
        if (notas.size() <= 0) {
            try {
                Messagebox.show("No han nada que imprimir Aportes en 0 ...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

//Sistemacalificacion sd;
//sd.getTrimestre().getCodigotrim();


        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";

        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }

        String q = "Select codigomap, matricula,notas.materia, " + query + "  from notas, materia_profesor , matriculas mat, estudiantes est "
                + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                + "and materia_profesor.curso = '" + curso.getCodigocur() + "' "
                + "and notas.promedia = true and notas.disciplina = false and materia_profesor.seimprime = true  "
                + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "'  ) "
                + "order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";

        System.out.println("cuadro final: " + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        int cont = 0;
        String matricula = "";
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            //row = new Row();
            Matriculas matriculaNo = null;
            Global materiaNo = null;
            MateriaProfesor mprofesor = null;
            MateriaProfesor mprofesor1 = null;
            Double aprovecha = 0.0;
            Double disciplina = 0.0;
            int ksis = 0;
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                Double val = 0.0;
                Nota nota = new Nota();
                try {
                    if (dos.equals(null)) {
                        dos = new Double(0.0);
                    }
                } catch (Exception e) {
                    dos = new Double(0.0);
                }
                if (j >= 3) {
                    val = redondear((Double) dos, 2);
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);


                    //nota.setNota(val);

                    if (mprofesor.getCuantitativa() == false) {
//                        if(val < 14){
//                            nota.setNota("REPROBADO");
//                        }else{
//                            nota.setNota("APROBADO");
//
//                        }
                        nota.setNota(equivalencia(dos, equivalencias));
                    } else {
                        nota.setNota(val.toString());
//                        aprovecha+=val;
//                        System.out.println(matriculaNo+":::"+aprovecha);
                        if (val == 0.0) {
                            nota.setNota("");
                        }
                    }
                    if (val >= desde && val <= hasta) {
                    } else {
                        nota.setNota("");
                    }


                    int tamaSistema = sistemas.size() - 1;
                    if (ksis == tamaSistema) {
                        nota.setMprofesor(mprofesor);
                    } else {
                        mprofesor1.getEmpleado().setApellidos("");
                        mprofesor1.getEmpleado().setNombres("");
                        nota.setMprofesor(mprofesor1);

                    }

//                    if(mprofesor.getCuantitativa()){
//                            nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
//                    }else{
//                          Sistemacalificacion sis2 = (Sistemacalificacion) sistemas.get(ksis);
//                          Sistemacalificacion sistemaN = new Sistemacalificacion();
//                          sistemaN.setAbreviatura("PROMEDIO");
//                          sistemaN.setTrimestre(sis2.getTrimestre());
//                          nota.setSistema(sistemaN);
//
//                    }
                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    nota.setAprovechamiento(aprovecha);
                    nota.setContador(cont);
                    nota.setDisciplina(disciplina);
                    matricula = matriculaNo.toString();
                    lisNotas.add(nota);
                    ksis++;
                } else if (j == 1) {

//                    try{
//                        Integer va = (Integer) dos;
//                    }catch(Exception e){
//                            dos = new Integer(0);
//                    }

                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
//                    System.out.println("SELECT DE PROMEDIO:  SELECT CAST(AVG("+nfinal.getNota()+")as decimal (9,3)) FROM notas WHERE matricula = '"+matriculaNo.getCodigomat()+"' AND cuantitativa = TRUE AND disciplina = FALSE AND  promedia = TRUE AND materia > 1 AND  seimprime = TRUE ");
                    List valor = adm.queryNativo("SELECT CAST(AVG(" + nfinal.getNota() + ")as decimal (9,3)) FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND cuantitativa = TRUE AND disciplina = FALSE AND  promedia = TRUE AND materia > 1 AND  seimprime = TRUE GROUP BY MATRICULA ");
                    if (valor.size() > 0) {
                        aprovecha = ((BigDecimal) (((Vector) valor.get(0)).get(0))).doubleValue();
                    }
                    //System.out.println("SELECT CAST(("+nfinal.getNota()+")as decimal (9,0)) FROM notas WHERE matricula = '"+matriculaNo.getCodigomat()+"' AND materia = 0 ");
                    valor = adm.queryNativo("SELECT CAST(IF(" + nfinal.getNota() + " is null,0," + nfinal.getNota() + ")as decimal (9,0)) FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND materia = 0 ");
//                    System.out.println(""+valor);
                    if (valor.size() > 0) {
                        disciplina = ((BigDecimal) (((Vector) valor.get(0)).get(0))).doubleValue();
                    }
//                    disciplina = aprovecha;

                } else if (j == 2) {
                    materiaNo = (Global) adm.buscarClave((Integer) dos, Global.class);
                } else if (j == 0) {
                    mprofesor = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                    mprofesor1 = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                }
                if (matriculaNo != null && j > 1) {
                    if (!matriculaNo.toString().equals(matricula)) {
                        cont++;
                    }
                }



            }
        }
        nativo = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;

    }

    public JRDataSource libretas(Cursos curso, Matriculas matri, Sistemacalificacion sistema) throws InterruptedException {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        String firma1 = regresaVariableParametros("FIR1", parametrosGlobales);
        String cargo1 = regresaVariableParametros("CAR1", parametrosGlobales);
        String firma2 = regresaVariableParametros("FIR2", parametrosGlobales);
        String cargo2 = regresaVariableParametros("CAR2", parametrosGlobales);
        String firma3 = regresaVariableParametros("FIR3", parametrosGlobales);
        String cargo3 = regresaVariableParametros("CAR3", parametrosGlobales);
        Boolean promCuantitativo = regresaVariableParametrosLogico("PROMCUAN", parametrosGlobales);
        Boolean discCuantitativo = regresaVariableParametrosLogico("DISCCUAN", parametrosGlobales);

        Boolean impPromedio = regresaVariableParametrosLogico("IMPPROM", parametrosGlobales);
        Boolean impDisciplina = regresaVariableParametrosLogico("IMPDISC", parametrosGlobales);
        Boolean impEquivalencias = regresaVariableParametrosLogico("IMPEQU", parametrosGlobales);


//DECIMALESDIS


        List<Equivalencias> equivalenciasFaltas = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DI' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + " and o.seimprime = true and o.orden <= '" + sistema.getOrden() + "' order by o.orden ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.orden <=  '" + sistema.getOrden() + "'"
                + " and o.sistema.seimprime = true  order by o.sistema.orden ");
        if (notas.size() <= 0) {
            try {
                Messagebox.show("No hay nada que imprimir...! \n Revise en la pantalla Aportes si existen notas a imprimir", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String query = "";
        String query2 = "";
        String queryDisciplina = "";
        Double numeroDecimales = regresaVariableParametrosDecimal("DECIMALESPRO", parametrosGlobales);
        Double numeroDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
        //DECIMALESDIS
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        for (Notanotas notass : notas) {
            query2 += "round(cast(avg(" + notass.getNota() + ") as decimal(9,4))," + numeroDecimales.intValue() + "),";
        }
        for (Notanotas notass : notas) {
            queryDisciplina += "cast(round(cast(avg(" + notass.getNota() + ") as decimal(9,4))," + numeroDecimalesDisc.intValue() + ") as decimal(9,4)),";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        query2 = query2.substring(0, query2.length() - 1).replace("'", "");
        queryDisciplina = queryDisciplina.substring(0, queryDisciplina.length() - 1).replace("'", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }


        List<Nota> lisNotas = new ArrayList();
        ArrayList lisNotasC = new ArrayList();
        List<Matriculas> matriculas = new ArrayList();
        if (matri.getCodigomat().equals(-2)) {
            matriculas = adm.query("Select o from Matriculas as o where  o.estado in ('Matriculado','Recibir Pase')  and o.curso.codigocur = '" + curso.getCodigocur() + "' order by o.estudiante.apellido ");
        } else {
            matriculas.add(matri);
        }
        for (Matriculas matriculas1 : matriculas) {
            //Matriculas matriculas1 = itm.next();
            String q = "Select matriculas.codigomat,notas.materia,notas.cuantitativa, " + query + "  from matriculas "
                    + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  "
                    + "left join notas on matriculas.codigomat = notas.matricula  "
                    + "and notas.materia != 0  "
                    + "where matriculas.curso = '" + curso.getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "and notas.seimprime = true "
                    + //"and notas.promedia = true " +
                    "and notas.disciplina = false  "
                    + "and notas.materia != 0 "
                    + "order by estudiantes.apellido, notas.orden";
            System.out.println("NOTAS GENERALES" + q);
            List nativo = adm.queryNativo(q);
            Nota nota = new Nota();
            Object promedioFinal = null, disciplinaFinal = null;
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                Matriculas matriculaNo = null;
                Boolean cuantitativa = false;
                Global mate = new Global();
                int ksis = 0;
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
                    Double val = 0.0;
                    NotaCollection coll = new NotaCollection();
                    try {
                        if (dos.equals(null)) {
                            dos = new Double(0.0);
                        }
                    } catch (Exception e) {
                        dos = new Double(0.0);
//                        System.out.println(""+e);
                    }
                    if (j >= 3) {
                        val = (Double) dos;
                        coll.setNota(dos);


                        if (cuantitativa == false) {
                            coll.setNota(equivalencia(dos, equivalencias));
                        } else {
                            if (val == 0.0) {
                                coll.setNota("");
                            }
                        }
                        coll.setMateria(mate.getDescripcion());
                        coll.setMatricula("" + matriculaNo.getCodigomat());
                        coll.setEstudiante(matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre());
                        coll.setSistema(((Sistemacalificacion) sistemas.get(ksis)).getAbreviatura());
                        coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion());
                        lisNotasC.add(coll);

                        ksis++;
                    } else if (j >= 2) {
                        //System.out.println(""+dos);
                        cuantitativa = (Boolean) dos;
                    } else if (j >= 1) {
                        mate = (Global) adm.buscarClave((Integer) dos, Global.class);
                    } else {
                        matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                    }
                }
                //row.setParent(this);
            }

            if (impPromedio) {
//IMPRIMO EL PROMEDIO
                q = "Select matricula," + query2 + "  from notas "
                        + "where notas.matricula = '" + matriculas1.getCodigomat() + "' "
                        + "and notas.seimprime = true "
                        + "and notas.promedia = true "
                        + "and notas.disciplina = false "
                        + "and notas.cuantitativa = true and notas.materia != 0 "
                        + "group by matricula  ";
//                System.out.println("NOTAS DE promedio " + q);
                nativo = null;
                nativo = adm.queryNativo(q);
                for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();

                    Boolean cuantitativa = false;
                    Global mate = new Global();
                    mate.setCodigo(0);
                    mate.setDescripcion("PROMEDIO");
                    int ksis = 0;
                    for (int j = 0; j < vec.size(); j++) {
                        Object dos = vec.get(j);
                        Double val = 0.0;
                        NotaCollection coll = new NotaCollection();
                        try {
                            if (dos.equals(null)) {
                                dos = new Double(0.0);
                            }
                        } catch (Exception e) {
                            dos = new BigDecimal(0.0);
//                                        System.out.println(""+e);
                        }
                        if (j > 0) {
                            val = ((BigDecimal) dos).doubleValue();
                            coll.setNota(dos);
                            if (promCuantitativo == false) {
                                coll.setNota(equivalencia(((BigDecimal) dos).doubleValue(), equivalencias));
                            } else {
                                if (val == 0.0) {
                                    coll.setNota("");
                                }
                            }
                            promedioFinal = dos;
                            coll.setMateria(mate.getDescripcion());
                            coll.setMatricula("" + matriculas1.getCodigomat());
                            coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                            coll.setSistema(((Sistemacalificacion) sistemas.get(ksis)).getAbreviatura());
                            coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion());
                            lisNotasC.add(coll);
                            ksis++;
                        }

                    }
                    //row.setParent(this);
                }

            }
            //IMPRIMO DISCIPLINA
            if (impDisciplina) {
                q = "Select matriculas.codigomat," + queryDisciplina + "  from matriculas "
                        + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  "
                        + "left join notas on matriculas.codigomat = notas.matricula "
                        + "where matriculas.curso = '" + curso.getCodigocur() + "'  "
                        + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                        + " and notas.materia = 0  "
                        + "group by codigomat  ";
//                System.out.println("DISCIPLINA " + q);
                nativo = adm.queryNativo(q);
                for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();
//                                Matriculas matriculaNo = null;
                    Boolean cuantitativa = false;
                    Global mate = new Global();
                    mate = new Global(-2);
                    mate.setDescripcion("DISCIPLINA");
                    int ksis = 0;
                    for (int j = 0; j < vec.size(); j++) {
                        Object dos = vec.get(j);
                        Double val = 0.0;
                        NotaCollection coll = new NotaCollection();
                        try {
                            if (dos.equals(null)) {
                                dos = new Double(0.0);
                            }
                        } catch (Exception e) {
                            dos = new BigDecimal(0.0);
//                                        System.out.println(""+e);
                        }
                        if (j > 0) {
                            val = ((BigDecimal) dos).doubleValue();
                            coll.setNota(dos);
                            if (discCuantitativo == false) {
                                coll.setNota(equivalencia(((BigDecimal) dos).doubleValue(), equivalencias));
                            } else {
                                if (val == 0.0) {
                                    coll.setNota("");
                                }
                            }
                            disciplinaFinal = dos;
                            coll.setMateria(mate.getDescripcion());
                            coll.setMatricula("" + matriculas1.getCodigomat());
                            coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                            coll.setSistema(((Sistemacalificacion) sistemas.get(ksis)).getAbreviatura());
                            coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion());
                            lisNotasC.add(coll);
                            ksis++;
                        }
                    }
                    //row.setParent(this);
                }
            }

            //IMPRIMO EL CUADRO DE EQUIVALENCIAS DE FALTAS

            ArrayList lisFaltas = new ArrayList();
            String query3 = "";
            String query4 = "";
            if (impEquivalencias) {
                int w = 1;
                for (int i = 0; i < equivalenciasFaltas.size(); i++) {
                    query3 += "sum(nota" + w + "),";
                    query4 += "sum(nota" + w + "),";
                    w++;
                }
                try {
                    query3 = query3.substring(0, query3.length() - 1);
                } catch (Exception e) {
                    Messagebox.show("No existen EQUIVALENCIAS \n Revise ADMINISTRACION > PARAMETROS > EQUIVALENCIAS >> DISCIPLINA", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                    System.out.println("************** LINEA: 1276: REPORTECLASE ERROR NO HAY EQUIVALENCIAS PARAMETRIZADAS" + e);
                    return null;
                }
                try {
                    query4 = query4.substring(0, query4.length() - 1);
                } catch (Exception e) {
                    Messagebox.show("No existen EQUIVALENCIAS \n Revise ADMINISTRACION > PARAMETROS >> DISCIPLINA", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                    System.out.println("************** LINEA: 1276: REPORTECLASE ERROR NO HAY EQUIVALENCIAS PARAMETRIZADAS" + e);
                    return null;

                }
                //IMPRIMO LAS FALTAS
                q = "Select " + query4 + ", tri.descripcion from disciplina, sistemacalificacion  sis, trimestres tri "
                        + "where matricula = '" + matriculas1.getCodigomat() + "' and sis.trimestre = tri.codigotrim   "
                        + "and sis.orden <= '" + sistema.getOrden() + "'   AND sis.codigosis = disciplina.sistema  and sis.seimprime = true  "
                        + "  group by tri.codigotrim  order by  tri.codigotrim, sis.orden "
                        + " ";
//                System.out.println(""+q);
                nativo = adm.queryNativo(q);
                for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();
                    int ksis = 0;
                    for (int j = 0; j < vec.size() - 1; j++) {
                        Object dos = vec.get(j);
                        NotaCollection coll = new NotaCollection();
                        coll.setNota(dos);
                        coll.setMateria(equivalenciasFaltas.get(ksis).getNombre());
                        coll.setMatricula("" + matriculas1.getCodigomat());
                        coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                        coll.setSistema("" + vec.get(vec.size() - 1));
                        lisFaltas.add(coll);
                        ksis++;
                    }
                    //row.setParent(this);
                }


                q = "Select " + query3 + "  from disciplina, sistemacalificacion "
                        + "where matricula = '" + matriculas1.getCodigomat() + "'  "
                        + "and sistemacalificacion.orden <= '" + sistema.getOrden() + "' "
                        + "and sistemacalificacion.codigosis =  sistema  and sistemacalificacion.seimprime = true  "
                        + " group by matricula ";
                //SELECT * FROM disciplina, sistemacalificacion WHERE sistemacalificacion.codigosis =  sistema
////                 System.out.println(""+q);
                nativo = adm.queryNativo(q);
                for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();
                    int ksis = 0;
                    for (int j = 0; j < vec.size(); j++) {
                        Object dos = vec.get(j);
                        NotaCollection coll = new NotaCollection();
                        coll.setNota(dos);
                        coll.setMateria(equivalenciasFaltas.get(ksis).getNombre());
                        coll.setMatricula("" + matriculas1.getCodigomat());
                        coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                        coll.setSistema("Totales");
                        lisFaltas.add(coll);
                        ksis++;
                    }
                    //row.setParent(this);
                }
                if (nativo.size() <= 0) {

                    for (int i = 0; i < equivalenciasFaltas.size(); i++) {
                        NotaCollection coll = new NotaCollection();
                        coll.setNota(0);
                        coll.setMateria(equivalenciasFaltas.get(i).getNombre());
                        coll.setMatricula("" + matriculas1.getCodigomat());
                        coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                        coll.setSistema("Totales");
                        lisFaltas.add(coll);

                    }
                    //row.setParent(this);

                }

            }

            nota.setFirma1(firma1);
            nota.setFirma2(firma2);
            nota.setFirma3(firma3);
            nota.setCargo1(cargo1);
            nota.setCargo2(cargo2);
            nota.setCargo3(cargo3);

            nota.setPromedioFinal(promedioFinal);
            nota.setDisciplinaFinal(disciplinaFinal);

            try {
                if (firma1.trim().equals("[RECTOR]")) {
                    nota.setFirma1(periodo.getInstitucion().getRector());
                } else if (firma1.trim().equals("[INSPECTOR]")) {
                    nota.setFirma1(matriculas1.getCurso().getInspector());
                } else if (firma1.trim().equals("[TUTOR]")) {
                    nota.setFirma1(matriculas1.getCurso().getTutor());
                } else if (firma1.trim().equals("[SECRETARIA]")) {
                    nota.setFirma1(periodo.getInstitucion().getSecretaria());
                } else if (firma1.trim().equals("[REPRESENTANTE]")) {
                    nota.setFirma1(matriculas1.getEstudiante().getRepresentante().getApellidos() + " " + matriculas1.getEstudiante().getRepresentante().getNombres());
                }

                if (firma2.trim().equals("[RECTOR]")) {
                    nota.setFirma2(periodo.getInstitucion().getRector());
                } else if (firma2.trim().equals("[INSPECTOR]")) {
                    nota.setFirma2(matriculas1.getCurso().getInspector());
                } else if (firma2.trim().equals("[TUTOR]")) {
                    nota.setFirma2(matriculas1.getCurso().getTutor());
                } else if (firma2.trim().equals("[SECRETARIA]")) {
                    nota.setFirma2(periodo.getInstitucion().getSecretaria());
                } else if (firma2.trim().equals("[REPRESENTANTE]")) {
                    nota.setFirma2(matriculas1.getEstudiante().getRepresentante().getApellidos() + " " + matriculas1.getEstudiante().getRepresentante().getNombres());
                }
                if (firma3.trim().equals("[RECTOR]")) {
                    nota.setFirma3(periodo.getInstitucion().getRector());
                } else if (firma3.trim().equals("[INSPECTOR]")) {
                    nota.setFirma3(matriculas1.getCurso().getInspector());
                } else if (firma3.trim().equals("[TUTOR]")) {
                    nota.setFirma3(matriculas1.getCurso().getTutor());
                } else if (firma3.trim().equals("[SECRETARIA]")) {
                    nota.setFirma3(periodo.getInstitucion().getSecretaria());
                } else if (firma3.trim().equals("[REPRESENTANTE]")) {
                    nota.setFirma3(matriculas1.getEstudiante().getRepresentante().getApellidos() + " " + matriculas1.getEstudiante().getRepresentante().getNombres());
                }

            } catch (Exception e) {
                System.out.println("FALTA PARAMETROS DE FIRMAS: DE LIBRETA" + e);
            }


            nota.setMatricula(matriculas1);
            nota.setNotas(lisNotasC);
            nota.setFaltas(lisFaltas);
            lisNotas.add(nota);


            lisNotasC = new ArrayList();
        }


        ReporteNoasLibretaDataSource ds = new ReporteNoasLibretaDataSource(lisNotas);

        return ds;

    }

    public JRDataSource certificados(Cursos curso) {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();

        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List lista = adm.listar("Institucion");
        Institucion insts = (Institucion) lista.get(0);
        ArrayList detalle = new ArrayList();
        String query = "SELECT mat FROM Matriculas AS mat "
                + "WHERE  mat.estado like '%Matriculado%' "
                + "and mat.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + " order by mat.curso.secuencia, mat.curso.codigocur, mat.estudiante.apellido";
        if (!curso.getCodigocur().equals(-2)) {
            query = "Select o from Matriculas as o "
                    + "where o.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                    + "and o.curso.codigocur = '" + curso.getCodigocur() + "'";
        }

        List hoy = adm.query(query);
        for (Iterator it = hoy.iterator(); it.hasNext();) {
            Matriculas elem = (Matriculas) it.next();
            detalle.add(elem);
        }


        ReporteCertificadoDataSource ds = new ReporteCertificadoDataSource(detalle);
        return ds;
//                    Map parametros = new HashMap();
//                    parametros.put("denominacion", insts.getDenominacion());
//                    parametros.put("nombre", insts.getNombre());
//                    parametros.put("periodo", insts.getDireccion());
    }

    public JRDataSource promocion(Cursos curso, Matriculas matri) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Textos> textos = adm.query("Select o from Textos as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        String cabecera = regresaTexto("CMAT", textos);
        String aprobado = regresaTexto("PACMAT", textos);
        String reprobado = regresaTexto("PRCMAT", textos);
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.promediofinal = 'PF' ");

        Integer noDecimales = 3;
        try {
            noDecimales = regresaVariableParametrosDecimal("DECIPROMOCION", parametrosGlobales).intValue();
        } catch (Exception a) {
            noDecimales = 3;

        }


        try {
            if (notas.size() <= 0) {
                Messagebox.show("No se ha parametrizado el PROMEDIO FINAL en los APORTES \n Puede obtener resultados no esperados", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                return null;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
        }
     List<Matriculas> listaMatriculasPerdidos = cuadroverificar(curso, notas.get(0).getSistema());
        String codigosPerdidos ="";
        ArrayList perdidos = new ArrayList();
        for (Iterator<Matriculas> it = listaMatriculasPerdidos.iterator(); it.hasNext();) {
            Matriculas matriculas = it.next();
            codigosPerdidos = matriculas.getCodigomat()+","+codigosPerdidos;
            perdidos.add(matriculas.getCodigomat());
            
        }
        String complemento ="";
        if(codigosPerdidos.length()>0){
                codigosPerdidos = codigosPerdidos.substring(0,codigosPerdidos.length()-1);
                complemento = " and o.codigomat not in ("+codigosPerdidos+") ";
        }else{
                complemento = "";
        }
        ArrayList listaMatriculados = new ArrayList();
//        List<Nota> lisNotas = new ArrayList();
        List<Matriculas> matriculas = new ArrayList();
        if (matri.getCodigomat().equals(-2)) {
            matriculas = adm.query("Select o from Matriculas as o where o.curso.codigocur = '" + curso.getCodigocur() + "'");
        } else {
            matriculas.add(matri);
        }
        for (Matriculas matriculas1 : matriculas) {
            String cabe1 = cabecera;
            String piea = aprobado;
            String pier = reprobado;

            boolean estadoEstudiante = true;
            String q = "Select round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,4))) as decimal(8,4))," + 3 + ") from matriculas "
                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "and notas.seimprime = true "
                    + "and notas.promedia = true "
                    + "and notas.cuantitativa = true "
                    + "and notas.disciplina = false   and notas.materia != 0  "
                    + "group by notas.matricula  ";
            System.out.println("" + q);
            List nativo = adm.queryNativo(q);
            Double aprovechamiento = 0.0;
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
                    try {
                        if (dos.equals(null)) {
                            dos = new BigDecimal(0.0);
                        }
                    } catch (Exception e) {
                        dos = new BigDecimal(0.0);
                    }

                    BigDecimal b = (BigDecimal) dos;
                    aprovechamiento = b.doubleValue();
                }
            }

            q = "Select  round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,4))) as decimal(8,4))," + 3 + ")  from matriculas "
                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + " and notas.materia = 0 "
                    + "group by notas.matricula    ";
//        System.out.println(""+q);
            nativo = adm.queryNativo(q);
            Double disciplina = 0.0;
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
                    try {
                        if (dos.equals(null)) {
                            dos = new BigDecimal(0.0);
                        }
                    } catch (Exception e) {
                        dos = new BigDecimal(0.0);
                    }

                    BigDecimal b = (BigDecimal) dos;
                    disciplina = b.doubleValue();
                }
            }

            q = "Select matriculas.codigomat,notas.materia,notas.cuantitativa, " + notas.get(0).getNota() + "  "
                    + "from matriculas  "
                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "and notas.seimprime = true  "
                    + " "
                    + "and notas.disciplina = false   and notas.materia != 0  "
                    + "order by estudiantes.apellido, notas.orden";
            nativo = adm.queryNativo(q);
            //cabe1 = cabe1.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion() + " " + matriculas1.getCurso().getEspecialidad().getDescripcion() + " " + matriculas1.getCurso().getParalelo().getDescripcion());
            cabe1 = cabe1.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + ""
                    + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion()).replace("[especialidad]", matriculas1.getCurso().getEspecialidad().getDescripcion()).replace("[paralelo]", matriculas1.getCurso().getParalelo().getDescripcion());
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                Boolean cuantitativa = false;
                Global mate = new Global();
                int ksis = 0;
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
//                    Double val = 0.0;
//                    NotaCollection coll = new NotaCollection();
                    try {
                        if (dos.equals(null)) {
                            dos = new Double(0.0);
                        }
                    } catch (Exception e) {
                        dos = new Double(0.0);
                    }
                    if (j >= 3) {
//                        val = (Double) dos;
//                        coll.setNota(dos);

//                        coll.setMatricula("" + acaMatricula.getMatCodigo());
//                        coll.setEstudiante(acaMatricula.getEstCodigo().getEstApellido() + " " + matriculaNo.getEstCodigo().getEstApellido());
                        ksis++;
                        NotasClaseTemp not = new NotasClaseTemp();
                        not.setNota(dos);
                        not.setNotaCuali(equivalencia(dos, equivalencias) + "");
                        not.setMateria(mate.getDescripcion());

                        not.setCabeceraTexto(cabe1);
                        not.setPieTextoAprobado(piea);
                        not.setPieTextoReprobado(pier);

                        MateriaProfesor matep = new MateriaProfesor();
                        matep.setCuantitativa(cuantitativa);
                        matep.setMateria(mate);
                        not.setMateriaProfesor(matep);
                        not.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());

                        not.setAprovechamiento(redondear(aprovechamiento, noDecimales));
                        not.setDisciplina(disciplina);
                        if ((Double) dos >= matriculas1.getCurso().getAprobacion()) {
                            not.setEstadoMateria("APROBADO");
                        } else {
                            not.setEstadoMateria("REPROBADO");
                            estadoEstudiante = false;
                        }
                        not.setEstado(estadoEstudiante);
                        if(perdidos.contains(matriculas1.getCodigomat())){
                            not.setEstadoMateria("REPROBADO");
                        }
                        if (cuantitativa == false) {
                            not.setNota(equivalencia(dos, equivalencias));
                        }
                        not.setMatricula(matriculas1);
                        listaMatriculados.add(not);


                    } else if (j >= 2) {
                        //System.out.println(""+dos);
                        cuantitativa = (Boolean) dos;
                    } else if (j >= 1) {
                        mate = (Global) adm.buscarClave((Integer) dos, Global.class);
                    } else {
//                        matriculaNo = (AcaMatricula) adm.buscarAcaMatricula((Integer) dos);
                    }
                }
                //row.setParent(this);
            }


        }


        ReportePromocionDataSource ds = new ReportePromocionDataSource(listaMatriculados);

        return ds;

    }

    public JRDataSource promocion2(Cursos curso, Matriculas matri) { //CON DECIMALES
//     int tamanio=0; -2
        Administrador adm = new Administrador();


        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Textos> textos = adm.query("Select o from Textos as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        String cabecera = regresaTexto("CMAT", textos);
        String aprobado = regresaTexto("PACMAT", textos);
        String reprobado = regresaTexto("PRCMAT", textos);
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.promediofinal = 'PF' ");
        List<Matriculas> listaMatriculasPerdidos = cuadroverificar(curso, notas.get(0).getSistema());
        String codigosPerdidos ="";
        for (Iterator<Matriculas> it = listaMatriculasPerdidos.iterator(); it.hasNext();) {
            Matriculas matriculas = it.next();
            codigosPerdidos = matriculas.getCodigomat()+","+codigosPerdidos;
            
        }
        String complemento ="";
        if(codigosPerdidos.length()>0){
                codigosPerdidos = codigosPerdidos.substring(0,codigosPerdidos.length()-1);
                complemento = " and o.codigomat not in ("+codigosPerdidos+") ";
        }else{
                complemento = "";
        }
        Integer noDecimales = 3;
        try {
            noDecimales = regresaVariableParametrosDecimal("DECIPROMOCION", parametrosGlobales).intValue();
        } catch (Exception a) {
            noDecimales = 3;

        }
        try {
            if (notas.size() <= 0) {
                Messagebox.show("No se ha parametrizado el PROMEDIO FINAL en los APORTES \n Puede obtener resultados no esperados", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                return null;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList listaMatriculados = new ArrayList();
//        List<Nota> lisNotas = new ArrayList();
        List<Matriculas> matriculas = new ArrayList();
        if (matri.getCodigomat().equals(-2)) {
            matriculas = adm.query("Select o from Matriculas as o where o.curso.codigocur = '" + curso.getCodigocur() + "'"
                    + complemento +"  order by o.estudiante.apellido, o.estudiante.nombre ");
        } else {
            for (Iterator<Matriculas> it = listaMatriculasPerdidos.iterator(); it.hasNext();) {
                    Matriculas matriculaA = it.next();
                    if(matri.getCodigomat().equals(matriculaA.getCodigomat())){
                    try {
                        Messagebox.show("EL ESTUDIANTE "+ matri.getEstudiante() +" ESTA REPROBADO, ESCOJA OTRO ESTUDIANTE", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                        return null;
                    } catch (InterruptedException ex) {
                        Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    }
              }
            
            matriculas.add(matri);
        }
        for (Matriculas matriculas1 : matriculas) {
            String cabe1 = cabecera;
            String piea = aprobado;
            String pier = reprobado;
            boolean estadoEstudiante = true;
            //PARA CARGAR EL APROVECHAMIENTO
            String q = "Select round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,4))) as decimal(8,4))," + 3 + ") from matriculas "
                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "and notas.seimprime = true "
                    + "and notas.promedia = true "
                    + "and notas.cuantitativa = true "
                    + "and notas.disciplina = false   and notas.materia != 0  "
                    + "group by notas.matricula  ";
            System.out.println("" + q);
            List nativo = adm.queryNativo(q);
            Double aprovechamiento = 0.0;
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
                    try {
                        if (dos.equals(null)) {
                            dos = new BigDecimal(0.0);
                        }
                    } catch (Exception e) {
                        dos = new BigDecimal(0.0);
                    }

                    BigDecimal b = (BigDecimal) dos;
                    aprovechamiento = b.doubleValue();
                }
            }
//PARA CARGAR LA DISCIPLINA
            q = "Select  round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,4))) as decimal(8,4))," + 3 + ")  from matriculas "
                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + " and notas.materia = 0 "
                    + "group by notas.matricula    ";
//        System.out.println(""+q);
            nativo = adm.queryNativo(q);
            Double disciplina = 0.0;
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
                    try {
                        if (dos.equals(null)) {
                            dos = new BigDecimal(0.0);
                        }
                    } catch (Exception e) {
                        dos = new BigDecimal(0.0);
                    }

                    BigDecimal b = (BigDecimal) dos;
                    disciplina = b.doubleValue();
                }
            }

            q = "Select matriculas.codigomat,notas.materia,notas.cuantitativa, " + notas.get(0).getNota() + "  "
                    + "from matriculas  "
                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "and notas.seimprime = true  "
                    + " "
                    + "and notas.disciplina = false   and notas.materia != 0  "
                    + "order by estudiantes.apellido, notas.orden";
            nativo = adm.queryNativo(q);
            //cabe1 = cabe1.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion() + " " + matriculas1.getCurso().getEspecialidad().getDescripcion() + " " + matriculas1.getCurso().getParalelo().getDescripcion());
            cabe1 = cabe1.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + ""
                    + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion()).replace("[especialidad]", matriculas1.getCurso().getEspecialidad().getDescripcion()).replace("[paralelo]", matriculas1.getCurso().getParalelo().getDescripcion());
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                Boolean cuantitativa = false;
                Global mate = new Global();
                int ksis = 0;
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
//                    Double val = 0.0;
//                    NotaCollection coll = new NotaCollection();
                    try {
                        if (dos.equals(null)) {
                            dos = new Double(0.0);
                        }
                    } catch (Exception e) {
                        dos = new Double(0.0);
                    }
                    if (j >= 3) {

                        ksis++;
                        NotasClaseTemp not = new NotasClaseTemp();
                        not.setNota(dos);
                        not.setNotaCuali(equivalencia(dos, equivalencias) + "");
                        not.setMateria(mate.getDescripcion());

                        not.setCabeceraTexto(cabe1);
                        not.setPieTextoAprobado(piea);
                        not.setPieTextoReprobado(pier);

                        MateriaProfesor matep = new MateriaProfesor();
                        matep.setCuantitativa(cuantitativa);
                        matep.setMateria(mate);
                        not.setMateriaProfesor(matep);
                        not.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());

                        not.setAprovechamiento(redondear(aprovechamiento, noDecimales));
                        not.setDisciplina(disciplina);
                        if ((Double) dos >= matriculas1.getCurso().getAprobacion()) {
                            not.setEstadoMateria("APROBADO");
                        } else {
                            not.setEstadoMateria("REPROBADO");
                            estadoEstudiante = false;
                        }
                        not.setEstado(estadoEstudiante);
                        if (cuantitativa == false) {
                            not.setNota(equivalencia(dos, equivalencias));
                        }
                        not.setMatricula(matriculas1);
                        listaMatriculados.add(not);


                    } else if (j >= 2) {
                        //System.out.println(""+dos);
                        cuantitativa = (Boolean) dos;
                    } else if (j >= 1) {
                        mate = (Global) adm.buscarClave((Integer) dos, Global.class);
                    } else {
//                        matriculaNo = (AcaMatricula) adm.buscarAcaMatricula((Integer) dos);
                    }
                }
                //row.setParent(this);
            }


        }


        ReportePromocionDataSource ds = new ReportePromocionDataSource(listaMatriculados);

        return ds;

    }

    public JRDataSource certificadodisciplina(Cursos curso, Matriculas matri) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Textos> textos = adm.query("Select o from Textos as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.promediofinal = 'PF' ");
        try {
            if (notas.size() <= 0) {
                Messagebox.show("No se ha parametrizado el PROMEDIO FINAL en los APORTES \n Puede obtener resultados no esperados", "Administrador Educativo", Messagebox.OK, Messagebox.ERROR);
                return null;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList listaMatriculados = new ArrayList();
//        List<Nota> lisNotas = new ArrayList();
        List<Matriculas> matriculas = new ArrayList();
        if (matri.getCodigomat().equals(-2)) {
            matriculas = adm.query("Select o from Matriculas as o where o.curso.codigocur = '" + curso.getCodigocur() + "'");
        } else {
            matriculas.add(matri);
        }
        for (Matriculas matriculas1 : matriculas) {
            boolean estadoEstudiante = true;
            String q = "Select round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,4))) as decimal)," + 3 + ") from matriculas "
                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "and notas.seimprime = true "
                    + "and notas.promedia = true "
                    + "and notas.disciplina = false   and notas.materia != 0  "
                    + "group by notas.matricula  ";
            System.out.println("" + q);
            List nativo = adm.queryNativo(q);
            Double aprovechamiento = 0.0;
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
                    try {
                        if (dos.equals(null)) {
                            dos = new BigDecimal(0.0);
                        }
                    } catch (Exception e) {
                        dos = new BigDecimal(0.0);
                    }

                    BigDecimal b = (BigDecimal) dos;
                    aprovechamiento = b.doubleValue();
                }
            }

            q = "Select  round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,4))) as decimal)," + 3 + ")  from matriculas "
                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + " and notas.materia = 0 "
                    + "group by notas.matricula    ";
//        System.out.println(""+q);
            nativo = adm.queryNativo(q);
            Double disciplina = 0.0;
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
                    try {
                        if (dos.equals(null)) {
                            dos = new BigDecimal(0.0);
                        }
                    } catch (Exception e) {
                        dos = new BigDecimal(0.0);
                    }

                    BigDecimal b = (BigDecimal) dos;
                    disciplina = b.doubleValue();
                }
            }

            NotasClaseTemp not = new NotasClaseTemp();
            not.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());

            not.setAprovechamiento(aprovechamiento);
            not.setDisciplina(disciplina);
            not.setEstado(estadoEstudiante);
            not.setCabeceraTexto(equivalencia2(disciplina, equivalencias) + "");
            not.setMatricula(matriculas1);
            listaMatriculados.add(not);

        }


        ReportePromocionDataSource ds = new ReportePromocionDataSource(listaMatriculados);

        return ds;

    }

    public JRDataSource mejorestudiante(Cursos curso, Matriculas matri) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Textos> textos = adm.query("Select o from Textos as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        String cabecera = regresaTexto("CMAT", textos);
        String aprobado = regresaTexto("PACMAT", textos);
        String reprobado = regresaTexto("PRCMAT", textos);
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.promediofinal = 'PF' ");
        ArrayList listaMatriculados = new ArrayList();
//        List<Nota> lisNotas = new ArrayList();
        List<Matriculas> matriculas = new ArrayList();
        if (matri.getCodigomat().equals(-2)) {
            matriculas = adm.query("Select o from Matriculas as o where o.curso.codigocur = '" + curso.getCodigocur() + "'");
        } else {
            matriculas.add(matri);
        }
        for (Matriculas matriculas1 : matriculas) {
            String cabe1 = cabecera;
            String piea = aprobado;
            String pier = reprobado;

            boolean estadoEstudiante = true;
            String q = "Select round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,4))) as decimal)," + 3 + ") from matriculas "
                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "and notas.seimprime = true "
                    + "and notas.promedia = true "
                    + "and notas.disciplina = false "
                    + "group by notas.matricula  ";
//        System.out.println(""+q);
            List nativo = adm.queryNativo(q);
            Double aprovechamiento = 0.0;
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
                    try {
                        if (dos.equals(null)) {
                            dos = new Double(0.0);
                        }
                    } catch (Exception e) {
                        dos = new Double(0.0);
                    }

                    BigDecimal b = (BigDecimal) dos;
                    aprovechamiento = b.doubleValue();
                }
            }

            q = "Select  round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,4))) as decimal)," + 3 + ")  from matriculas "
                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + " and notas.materia = 0 "
                    + "group by notas.matricula    ";
//        System.out.println(""+q);
            nativo = adm.queryNativo(q);
            Double disciplina = 0.0;
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
                    try {
                        if (dos.equals(null)) {
                            dos = new Double(0.0);
                        }
                    } catch (Exception e) {
                        dos = new Double(0.0);
                    }

                    BigDecimal b = (BigDecimal) dos;
                    disciplina = b.doubleValue();
                }
            }

            q = "Select matriculas.codigomat,notas.materia,notas.cuantitativa, " + notas.get(0).getNota() + "  "
                    + "from matriculas  "
                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "and notas.seimprime = true  "
                    + "and notas.promedia = true "
                    + "and notas.disciplina = false "
                    + "order by estudiantes.apellido, notas.orden";
            nativo = adm.queryNativo(q);
            cabe1 = cabe1.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + ""
                    + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion()).replace("[especialidad]", matriculas1.getCurso().getEspecialidad().getDescripcion()).replace("[paralelo]", matriculas1.getCurso().getParalelo().getDescripcion());
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                Boolean cuantitativa = false;
                Global mate = new Global();
                int ksis = 0;
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
//                    Double val = 0.0;
//                    NotaCollection coll = new NotaCollection();
                    try {
                        if (dos.equals(null)) {
                            dos = new Double(0.0);
                        }
                    } catch (Exception e) {
                        dos = new Double(0.0);
                    }
                    if (j >= 3) {
//                        val = (Double) dos;
//                        coll.setNota(dos);

//                        coll.setMatricula("" + acaMatricula.getMatCodigo());
//                        coll.setEstudiante(acaMatricula.getEstCodigo().getEstApellido() + " " + matriculaNo.getEstCodigo().getEstApellido());
                        ksis++;
                        NotasClaseTemp not = new NotasClaseTemp();
                        not.setNota(dos);
                        not.setMateria(mate.getDescripcion());

                        not.setCabeceraTexto(cabe1);
                        not.setPieTextoAprobado(piea);
                        not.setPieTextoReprobado(pier);

                        MateriaProfesor matep = new MateriaProfesor();
                        matep.setCuantitativa(cuantitativa);
                        matep.setMateria(mate);
                        not.setMateriaProfesor(matep);
                        not.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());

                        not.setAprovechamiento(aprovechamiento);
                        not.setDisciplina(disciplina);
                        if ((Double) dos >= matriculas1.getCurso().getAprobacion()) {
                            not.setEstadoMateria("APROBADO");
                        } else {
                            not.setEstadoMateria("REPROBADO");
                            estadoEstudiante = false;
                        }
                        not.setEstado(estadoEstudiante);
                        if (cuantitativa == false) {
                            not.setNota(equivalencia(dos, equivalencias));
                        }
                        not.setMatricula(matriculas1);
                        listaMatriculados.add(not);


                    } else if (j >= 2) {
                        //System.out.println(""+dos);
                        cuantitativa = (Boolean) dos;
                    } else if (j >= 1) {
                        mate = (Global) adm.buscarClave((Integer) dos, Global.class);
                    } else {
//                        matriculaNo = (AcaMatricula) adm.buscarAcaMatricula((Integer) dos);
                    }
                }
                //row.setParent(this);
            }


        }


        ReportePromocionDataSource ds = new ReportePromocionDataSource(listaMatriculados);

        return ds;

    }

    public JRDataSource actaGrado(String tipo,Matriculas mat,Global esp) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");

        NumerosaLetras num = new NumerosaLetras();
        List<Actagrado> notas = adm.query("Select o from Actagrado as o "
                + " where o.periodo.codigoper = '" + periodo.getCodigoper() + "'  order by o.codigo ");
        List<Textos> variablesVarias = adm.query("Select o from Textos as o "
                + " where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        String cabecera1 = regresaVariable("CABEACT1", variablesVarias);
        String cabecera2 = regresaVariable("CABEACT2", variablesVarias);
        String pi1 = regresaVariable("PIEACT1", variablesVarias);
        String pi2 = regresaVariable("PIEACT2", variablesVarias);
        if (tipo.equals("copia")) {
            cabecera1 = regresaVariable("CABEACT1C", variablesVarias);
            cabecera2 = regresaVariable("CABEACT2C", variablesVarias);
            pi1 = regresaVariable("PIEACT1C", variablesVarias);
            pi2 = regresaVariable("PIEACT2C", variablesVarias);
        }

        String car1 = regresaVariable("CAR1", variablesVarias);
        String car2 = regresaVariable("CAR2", variablesVarias);
        String car3 = regresaVariable("CAR3", variablesVarias);
        String car4 = regresaVariable("CAR4", variablesVarias);
        String car5 = regresaVariable("CAR5", variablesVarias);
        String car6 = regresaVariable("CAR6", variablesVarias);
        String car7 = regresaVariable("CAR7", variablesVarias);
        String car8 = regresaVariable("CAR8", variablesVarias);
        String nom1 = regresaVariable("NOM1", variablesVarias);
        String nom2 = regresaVariable("NOM2", variablesVarias);
        String nom3 = regresaVariable("NOM3", variablesVarias);
        String nom4 = regresaVariable("NOM4", variablesVarias);
        String nom5 = regresaVariable("NOM5", variablesVarias);
        String nom6 = regresaVariable("NOM6", variablesVarias);
        String nom7 = regresaVariable("NOM7", variablesVarias);
        String nom8 = regresaVariable("NOM8", variablesVarias);
        String query = "";
        String query2 = "";
//        notas.get(0).getSistema().getOrden()
        String numeroDecimales = "3";
        for (Actagrado notass : notas) {
            query += notass.getColumna() + ",";
        }
        //round(avg(nota1),3),
        for (Actagrado notass : notas) {
            query2 += "round(cast(avg(" + notass.getColumna() + ") as decimal(9,2))," + numeroDecimales + "),";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        query2 = query2.substring(0, query2.length() - 1).replace("'", "");

        ArrayList lisNotasC = new ArrayList();
        List<Matriculas> matriculas = new ArrayList();
        if(mat.getCodigomat().equals(-2)){
            matriculas = adm.query("Select o from Matriculas as o "
                        + "where  o.perdio = false and o.suspenso = false "
                        + "and o.curso.especialidad.codigo = '"+esp.getCodigo()+"' and o.curso.periodo.codigoper = '"+periodo.getCodigoper()+"' and o.curso.secuencia = '6' "
                        + " order by o.estudiante.apellido,  o.estudiante.nombre  ");
        }else{
            matriculas.add(mat); 
        }

        for (Matriculas matriculas1 : matriculas) {
            //Matriculas matriculas1 = itm.next();
            String q = "Select matriculas.codigomat,numeroacta, " + query + "   from matriculas "
                    + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  "
                    + "left join notasacta on matriculas.codigomat = notasacta.matricula "
                    + "where  matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "order by estudiantes.apellido ";
//            System.out.println("NOTAS GENERALES" + q);
            List nativo = adm.queryNativo(q);
//            Nota nota = new Nota();
            String s = "#,#00.000";
            DecimalFormat decimalFormat = new DecimalFormat(s);
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                Matriculas matriculaNo = null;
                Integer noActa = null;
                Boolean cuantitativa = false;
                int ksis = 0;
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
                    Double val = 0.0;
                    NotaCollection coll = new NotaCollection();
                    try {
                        if (dos.equals(null)) {
                            dos = new Double(0.0);
                        }
                    } catch (Exception e) {
                        dos = new Double(0.0);
//                        System.out.println(""+e);
                    }
                    if (j >= 2) {
                        val = (Double) dos;
                        String s1 = decimalFormat.format(val);
                        coll.setNota(s1);
                        Actagrado ac = ((Actagrado) notas.get(ksis));
//                         coll.setNota(dos);
                        if (ac.getFormula().toUpperCase().contains("EQUIVAL") || ac.getFormula().toUpperCase().contains("equival")) {
                            coll.setNota(equivalencia2(redondear(val, 0), equivalencias));
                        }
                        coll.setTitulo(matriculaNo.getCurso().getActa());
                        coll.setMatricula("" + matriculaNo.getCodigomat());
                        coll.setEstudiante(matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre());
                        coll.setMatriculas(matriculas1);
                        coll.setMateria(ac.getNombre());
                        coll.setNoActa(noActa + "");
                        String cabecera1tmp = cabecera1.replace("[estudiante]", matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre()).replace("[fecha]", convertir(new Date()) + "");
                        String cabecera2tmp = cabecera2.replace("[fecha]", convertir(new Date()) + "").replace("[estudiante]", matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre());
                        coll.setCabecera1(cabecera1tmp);
                        coll.setCabecera1(cabecera1tmp);
                        coll.setCabecera2(cabecera2tmp);
                        coll.setCabecera2(cabecera2tmp);
                        String pie1tmp = pi1.replace("[estudiante]", matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre()).replace("[titulo]", matriculaNo.getCurso().getActa());
                        coll.setPie1(pie1tmp);
                        String pie2tmp = pi2.replace("[estudiante]", matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre()).replace("[titulo]", matriculaNo.getCurso().getActa());
                        coll.setPie2(pie2tmp);
                        coll.setCar1(car1);
                        coll.setCar2(car2);
                        coll.setCar3(car3);
                        coll.setCar4(car4);
                        coll.setCar5(car5);
                        coll.setCar6(car6);
                        coll.setCar7(car7);
                        coll.setCar8(car8);
                        coll.setNom1(nom1);
                        coll.setNom2(nom2);
                        coll.setNom3(nom3);
                        coll.setNom4(nom4);
                        coll.setNom5(nom5);
                        coll.setNom6(nom6);
                        coll.setNom7(nom7);
                        coll.setNom8(nom8);

//                        coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion());
                        lisNotasC.add(coll);

                        ksis++;
                    } else if (j == 0) {
                        matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                    } else if (j == 1) {
                        try {
                            noActa = ((Integer) dos);
                        } catch (Exception e) {
                            noActa = 0;
//                            try {
//                                Messagebox.show("No ha GENERADO LOS NÚMEROS DE ACTAS EN: Grados > Calculo de Promedios > ACTAS DE GRADO \n NO SE MOSTRARA LAS ACTAS DE GRADO", "Administrador Educativo", Messagebox.OK, Messagebox.ERROR);
//                                return null;
//                            } catch (InterruptedException ex) {
//                                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
//                            }
                        }

                    }

                }
                //row.setParent(this);
            }





        }


        ReporteGradoDataSource ds = new ReporteGradoDataSource(lisNotasC);
        lisNotasC = new ArrayList();
        return ds;

    }

    public JRDataSource reporteMejor(Cursos curso) {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");

        List<Actagrado> notas = adm.query("Select o from Actagrado as o "
                + " where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.esfinal = true  order by o.codigo ");
        if (notas.size() <= 0) {
            try {
                Messagebox.show("No se ha parametrizado el PROMEDIO en el Acta de Grado \n Puede obtener resultados no esperados", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String query = "";
        String query2 = "";
//        notas.get(0).getSistema().getOrden()
        String numeroDecimales = "3";
        for (Actagrado notass : notas) {
            query += notass.getColumna() + ",";
        }
        //round(avg(nota1),3),
        for (Actagrado notass : notas) {
            query2 += "round(cast(avg(" + notass.getColumna() + ") as decimal(9,2))," + numeroDecimales + "),";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        query2 = query2.substring(0, query2.length() - 1).replace("'", "");
        Actagrado acColumna = notas.get(0);
        String columna = acColumna.getColumna();
        //String columna  = "get"+acColumna.getColumna().substring(0,1).toUpperCase()+acColumna.getColumna().substring(1,acColumna.getColumna().length());
        List<Notasacta> notasA = adm.query(" SELECT o FROM Notasacta as o where o.matricula.curso.secuencia = 6 "
                + "and o.matricula.curso.periodo.codigoper = '" + curso.getPeriodo().getCodigoper() + "' "
                + "and  o.matricula.estado NOT in ('Retirado','Anulado','anulado','Eliminado','Inscrito') ORDER BY o." + columna + " DESC");
        ArrayList lisNotasC = new ArrayList();
        for (Iterator<Notasacta> it = notasA.iterator(); it.hasNext();) {
            Notasacta notasacta = it.next();
            NotaDisciplina aCargar = new NotaDisciplina();
            if (columna.equals("nota1")) {
                aCargar.setN1(notasacta.getNota1());
            }
            if (columna.equals("nota2")) {
                aCargar.setN1(notasacta.getNota2());
            }
            if (columna.equals("nota3")) {
                aCargar.setN1(notasacta.getNota3());
            }
            if (columna.equals("nota4")) {
                aCargar.setN1(notasacta.getNota4());
            }
            if (columna.equals("nota5")) {
                aCargar.setN1(notasacta.getNota5());
            }
            if (columna.equals("nota6")) {
                aCargar.setN1(notasacta.getNota6());
            }
            if (columna.equals("nota7")) {
                aCargar.setN1(notasacta.getNota7());
            }
            if (columna.equals("nota8")) {
                aCargar.setN1(notasacta.getNota8());
            }
            if (columna.equals("nota9")) {
                aCargar.setN1(notasacta.getNota9());
            }
            if (columna.equals("nota10")) {
                aCargar.setN1(notasacta.getNota10());
            }
            if (columna.equals("nota11")) {
                aCargar.setN1(notasacta.getNota12());
            }
            if (columna.equals("nota12")) {
                aCargar.setN1(notasacta.getNota12());
            }
            if (columna.equals("nota13")) {
                aCargar.setN1(notasacta.getNota13());
            }
            aCargar.setNombres(notasacta.getMatricula().getEstudiante().getApellido() + " " + notasacta.getMatricula().getEstudiante().getNombre());

            lisNotasC.add(aCargar);
        }

        DisciplinaDataSource ds = new DisciplinaDataSource(lisNotasC);
        return ds;

    }

    public ArrayList actaGradoTodos(Cursos curso) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Map parametros = new HashMap();
        Institucion insts = curso.getPeriodo().getInstitucion();
        parametros.put("denominacion", insts.getDenominacion());
        parametros.put("nombre", insts.getNombre());
        parametros.put("periodo", periodo.getDescripcion());
        parametros.put("slogan", insts.getSlogan());

        NumerosaLetras num = new NumerosaLetras();
        List<Actagrado> notas = adm.query("Select o from Actagrado as o "
                + " where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and  o.imprimecuadro = true order by o.codigo ");
        List<Materiasgrado> cabeMateGrado = adm.query("Select o from Materiasgrado as o "
                + "where o.curso.periodo.codigoper = '" + curso.getPeriodo().getCodigoper() + "' "
                + "and o.curso.codigocur = '" + curso.getCodigocur() + "'");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");


        String query5 = "";
        String query = "";
        String query2 = "";
//        notas.get(0).getSistema().getOrden()
        String numeroDecimales = "3";
//        for (Actagrado notass : notas) {
//            query += notass.getColumna() + ",";
//        }
        for (Materiasgrado notass : cabeMateGrado) {
            query5 += notass.getColumna() + ",";
        }
        //round(avg(nota1),3),
        for (Actagrado notass : notas) {
            query2 += "round(cast(avg(" + notass.getColumna() + ") as decimal(9,3))," + numeroDecimales + "),";
        }
//        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        query2 = query2.substring(0, query2.length() - 1).replace("'", "");
        query5 = query5.substring(0, query5.length() - 1).replace("'", "");

        ArrayList lisNotasC = new ArrayList();
        List<Matriculas> matriculas = new ArrayList();

        matriculas = adm.query("Select o from Matriculas as o "
                + "where o.estado in ('Matriculado','Recibir Pase') and  o.curso.secuencia = 6 and o.curso.periodo.codigoper = '" + curso.getPeriodo().getCodigoper() + "' order by o.estudiante.apellido,o.estudiante.nombre");
        parametros.put("n1", "OCTAVO");
        parametros.put("n2", "NOVENO");
        parametros.put("n3", "DECIMO");
        parametros.put("n4", "1ER.AÑO");
        parametros.put("n5", "2DO.AÑO");
        parametros.put("n6", "PROM 8 a 2");
        parametros.put("n7", "3ER.AÑO");
        for (Matriculas matriculas1 : matriculas) {
            //Matriculas matriculas1 = itm.next();
            List primeroQuinto = adm.query("Select o from Notasrecord as o "
                    + "where o.estudiante.codigoest = '" + matriculas1.getEstudiante().getCodigoest() + "' ");
            NotaActaGeneral acta = new NotaActaGeneral();
            acta.setMatricula(matriculas1);
            if (primeroQuinto.size() > 0) {

                Notasrecord encontrados = (Notasrecord) primeroQuinto.get(0);
                acta.setN1(encontrados.getPrimero());
                acta.setN2(encontrados.getSegundo());
                acta.setN3(encontrados.getTercero());
                acta.setN4(encontrados.getCuarto());
                acta.setN5(encontrados.getQuinto());
                Double sumaPromedio = (encontrados.getPrimero() + encontrados.getSegundo() + encontrados.getTercero() + encontrados.getCuarto() + encontrados.getQuinto()) / 5;
                acta.setN6(sumaPromedio);
                acta.setN7(encontrados.getSexto());
            } else {
                acta.setN1(0.0);
                acta.setN2(0.0);
                acta.setN3(0d);
                acta.setN4(0d);
                acta.setN5(0d);
                acta.setN6(0d);
                acta.setN7(0d);
            }
            String q = "SELECT " + query5 + " FROM  Notasgrado  WHERE matricula = " + matriculas1.getCodigomat() + "";
//            System.out.println("MATERIAGRADO NOTAS: "+q);
            List nativo = adm.queryNativo(q);
            for (Iterator it = nativo.iterator(); it.hasNext();) {
                Vector object = (Vector) it.next();
                if (cabeMateGrado.size() >= 1) {
                    parametros.put("n8", cabeMateGrado.get(0).getAbreviatura());
                    acta.setN8((Double) object.get(0));
                }
                if (cabeMateGrado.size() >= 2) {
                    parametros.put("n9", cabeMateGrado.get(1).getAbreviatura());
                    acta.setN9((Double) object.get(1));
                }
                if (cabeMateGrado.size() >= 3) {
                    parametros.put("n10", cabeMateGrado.get(2).getAbreviatura());
                    acta.setN10((Double) object.get(2));
                }

                if (cabeMateGrado.size() >= 4) {
                    parametros.put("n11", cabeMateGrado.get(3).getAbreviatura());
                    acta.setN11((Double) object.get(3));
                }
                if (cabeMateGrado.size() >= 5) {
                    parametros.put("n12", cabeMateGrado.get(4).getAbreviatura());
                    acta.setN12((Double) object.get(4));

                }
                if (cabeMateGrado.size() >= 6) {
                    parametros.put("n13", cabeMateGrado.get(5).getAbreviatura());
                    acta.setN13((Double) object.get(5));

                }


            }
            nativo = adm.queryNativo("Select " + query2 + " from notasacta where matricula = '" + matriculas1.getCodigomat() + "' ");
            for (Iterator it = nativo.iterator(); it.hasNext();) {
                Vector object = (Vector) it.next();
                if (cabeMateGrado.size() >= 5) {
                    if (notas.size() >= 1) {
                        parametros.put("n13", notas.get(0).getAbreviatura());
                        acta.setN13(((BigDecimal) object.get(0)).doubleValue());
                        if (notas.size() == 1) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(0)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 2) {
                        parametros.put("n14", notas.get(1).getAbreviatura());
                        acta.setN14(((BigDecimal) object.get(1)).doubleValue());
                        if (notas.size() == 2) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(1)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 3) {
                        parametros.put("n15", notas.get(2).getAbreviatura());
                        acta.setN15(((BigDecimal) object.get(2)).doubleValue());
                        if (notas.size() == 3) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(2)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 4) {
                        parametros.put("n16", notas.get(3).getAbreviatura());
                        acta.setN16(((BigDecimal) object.get(3)).doubleValue());
                        if (notas.size() == 4) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(3)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 5) {
                        parametros.put("n17", notas.get(4).getAbreviatura());
                        acta.setN17(((BigDecimal) object.get(4)).doubleValue());
                        if (notas.size() == 5) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(4)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 6) {
                        parametros.put("n18", notas.get(5).getAbreviatura());
                        acta.setN18(((BigDecimal) object.get(5)).doubleValue());
                        if (notas.size() == 6) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(5)).doubleValue(), 0), equivalencias));
                        }
                    }

                } else if (cabeMateGrado.size() >= 6) {
                    if (notas.size() >= 1) {
                        parametros.put("n14", notas.get(0).getAbreviatura());
                        acta.setN14(((BigDecimal) object.get(0)).doubleValue());
                        if (notas.get(0).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(0)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 2) {
                        parametros.put("n15", notas.get(1).getAbreviatura());
                        acta.setN15(((BigDecimal) object.get(1)).doubleValue());
                        if (notas.get(1).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(1)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 3) {
                        parametros.put("n16", notas.get(2).getAbreviatura());
                        acta.setN16(((BigDecimal) object.get(2)).doubleValue());
                        if (notas.get(2).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(2)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 4) {
                        parametros.put("n17", notas.get(3).getAbreviatura());
                        acta.setN17(((BigDecimal) object.get(3)).doubleValue());
                        if (notas.get(3).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(3)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 5) {
                        parametros.put("n18", notas.get(4).getAbreviatura());
                        acta.setN18(((BigDecimal) object.get(4)).doubleValue());
                        if (notas.get(4).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(4)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 6) {
                        parametros.put("n19", notas.get(5).getAbreviatura());
                        acta.setN18(((BigDecimal) object.get(5)).doubleValue());
                        if (notas.get(5).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(5)).doubleValue(), 0), equivalencias));
                        }
                    }
                }
            }
//
//            String q = "Select matriculas.codigomat, " + query + "  from matriculas "
//                    + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  "
//                    + "left join notasacta on matriculas.codigomat = notasacta.matricula "
//                    + "where matriculas.curso = '" + curso.getCodigocur() + "'  "
//                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
//                    + "order by estudiantes.apellido ";
//            List nativo = adm.queryNativo(q);
//            String s = "#,#00.000";
//            DecimalFormat decimalFormat = new DecimalFormat(s);
            lisNotasC.add(acta);

        }


        ActaGeneralDataSource ds = new ActaGeneralDataSource(lisNotasC);
        lisNotasC = new ArrayList();
        ArrayList otro = new ArrayList();
        otro.add(ds);
        otro.add(parametros);
        return otro;

    }
    //resumen encuesta

    public JRDataSource resumen(Cursos cursoLlega) {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");

        List<Cursos> lisCursos = new ArrayList<Cursos>();

        if (cursoLlega.getCodigocur().equals(-2)) {
            lisCursos = adm.query("Select o from Cursos as o where o.periodo.codigoper = '" + periodo.getCodigoper() + "'  order by o.secuencia ");
        } else {
            lisCursos.add(cursoLlega);
        }

        List<Pregunta> preg = adm.query("Select o from Pregunta as o  order by o.orden ");

        ArrayList listaResultados = new ArrayList();
        for (Iterator<Cursos> itCursos = lisCursos.iterator(); itCursos.hasNext();) {
            Cursos curso = itCursos.next();
            int i = 1;
            for (Iterator<Pregunta> it = preg.iterator(); it.hasNext();) {
                Pregunta pregunta = it.next();
//                System.out.println(i + ") " + pregunta.getPregunta());
                List<Detallepregunta> detall = adm.query("Select o from Detallepregunta as o where o.pregunta.codigo = '" + pregunta.getCodigo() + "' order by o.secuencia ");
                int a = 1;

                for (Iterator<Detallepregunta> it1 = detall.iterator(); it1.hasNext();) {
                    Detallepregunta detallepregunta = it1.next();
//                rep.setId(i);
                    ResumenClase rep = new ResumenClase();
                    rep.setCurso(curso + "");
                    rep.setPregunta(i + ".- " + pregunta.getPregunta());
                    rep.setRespuesta(detallepregunta.getOpcion());
                    Object respuestas = adm.querySimple("Select count(o) from Respuestasencuesta as o "
                            + "where o.detallepregunta.codigo = '" + detallepregunta.getCodigo() + "' and o.matricula.curso.codigocur  = '" + curso.getCodigocur() + "' ");
                    Long valor = (Long) respuestas;
                    int val = valor.intValue();
//                    System.out.println("\t *  " + detallepregunta.getOpcion() + "\t" + respuestas);
                    rep.setValor(val);
                    listaResultados.add(rep);
                    a++;
                }
//                System.out.println("");
//                System.out.println("-----------------------------------------------------------------------");
                i++;
            }

        }


        ReporteResumenDataSource ds = new ReporteResumenDataSource(listaResultados);
        return ds;


    }

    public JRDataSource resumenGrupal() {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");

        List<Cursos> lisCursos = new ArrayList<Cursos>();


        lisCursos = adm.queryNativo("Select o.* from Cursos as o where o.periodo = '" + periodo.getCodigoper() + "'  group by o.secuencia order by o.secuencia ", Cursos.class);


        List<Pregunta> preg = adm.query("Select o from Pregunta as o  order by o.orden ");

        ArrayList listaResultados = new ArrayList();
        for (Iterator<Cursos> itCursos = lisCursos.iterator(); itCursos.hasNext();) {
            Cursos curso = itCursos.next();
            int i = 1;
            for (Iterator<Pregunta> it = preg.iterator(); it.hasNext();) {
                Pregunta pregunta = it.next();
//                System.out.println(i + ") " + pregunta.getPregunta());
                List<Detallepregunta> detall = adm.query("Select o from Detallepregunta as o where o.pregunta.codigo = '" + pregunta.getCodigo() + "' order by o.secuencia ");
                int a = 1;

                for (Iterator<Detallepregunta> it1 = detall.iterator(); it1.hasNext();) {
                    Detallepregunta detallepregunta = it1.next();
//                rep.setId(i);
                    ResumenClase rep = new ResumenClase();
                    rep.setCurso(curso.getDescripcion() + " ");
                    rep.setPregunta(i + ".- " + pregunta.getPregunta());
                    rep.setRespuesta(detallepregunta.getOpcion());
                    Object respuestas = adm.querySimple("Select count(o) from Respuestasencuesta as o "
                            + "where o.detallepregunta.codigo = '" + detallepregunta.getCodigo() + "' and o.matricula.curso.secuencia  = '" + curso.getSecuencia() + "' ");
                    Long valor = (Long) respuestas;
                    int val = valor.intValue();
//                    System.out.println("\t *  " + detallepregunta.getOpcion() + "\t" + respuestas);
                    rep.setValor(val);
                    listaResultados.add(rep);
                    a++;
                }
//                System.out.println("");
//                System.out.println("-----------------------------------------------------------------------");
                i++;
            }

        }


        ReporteResumenDataSource ds = new ReporteResumenDataSource(listaResultados);
        return ds;


    }

    public Double redondear(Double numero, int decimales) {
        try {

            BigDecimal d = new BigDecimal(numero);
            d = d.setScale(decimales, RoundingMode.HALF_UP);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static Object equivalencia(Object no, List<Equivalencias> equivalencias) {
        Double nota = (Double) no;
        ArrayList listado = new ArrayList();
        for (Equivalencias acaEquivalencias : equivalencias) {
            Object obj[] = new Object[3];
            obj[0] = acaEquivalencias.getValorminimo();
            obj[1] = acaEquivalencias.getValormaximo();
            obj[2] = acaEquivalencias.getAbreviatura();
            listado.add(obj);
        }

        for (Iterator it = listado.iterator(); it.hasNext();) {
            Object object[] = (Object[]) it.next();
            Double mini = (Double) object[0];
            Double maxi = (Double) object[1];
            if (nota >= mini && nota <= maxi) {
                //System.out.println(""+);
                return object[2];
            }
        }

        return "";
    }

    public static Object equivalenciaSupletorio(Object no, List<Equivalencias> equivalencias) {
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

        return "40";
    }

    public static Object equivalencia2(Object no, List<Equivalencias> equivalencias) {
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
                //System.out.println(""+);
                return object[2];
            }
        }

        return "";
    }

    public String regresaVariableParametros(String variable, List<ParametrosGlobales> textos) {
        String dato = "";
        for (Iterator<ParametrosGlobales> it = textos.iterator(); it.hasNext();) {
            ParametrosGlobales textos1 = it.next();
            if (textos1.getVariable().equals(variable)) {
                return textos1.getCvalor();
            }
        }
        return dato;
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

    public Boolean regresaVariableParametrosLogico(String variable, List<ParametrosGlobales> textos) {
        for (Iterator<ParametrosGlobales> it = textos.iterator(); it.hasNext();) {
            ParametrosGlobales textos1 = it.next();
            if (textos1.getVariable().equals(variable)) {
                return textos1.getBvalor();
            }
        }

        return false;
    }

    public String regresaTexto(String variable, List<Textos> textos) {
        for (Iterator<Textos> it = textos.iterator(); it.hasNext();) {
            Textos textos1 = it.next();
            if (textos1.getVariable().equals(variable)) {
                return textos1.getMensaje();
            }
        }
        return "NO EXISTE VARIABLE";
    }

    public String regresaVariable(String variable, List<Textos> textos) {
        String dato = "";
        for (Iterator<Textos> it = textos.iterator(); it.hasNext();) {
            Textos textos1 = it.next();
            if (textos1.getVariable().equals(variable)) {
                return textos1.getMensaje();
            }
        }
        return dato;
    }

    public String convertir(Date fecha) {
        String mes = "";
        switch (fecha.getMonth()) {
            case 0:
                mes = "Enero";
                break;
            case 1:
                mes = "Febrero";
                break;
            case 2:
                mes = "Marzo";
                break;
            case 3:
                mes = "Abril";
                break;
            case 4:
                mes = "Mayo";
                break;
            case 5:
                mes = "Junio";
                break;
            case 6:
                mes = "Julio";
                break;
            case 7:
                mes = "Agosto";
                break;
            case 8:
                mes = "Septiembre";
                break;
            case 9:
                mes = "Octubre";
                break;
            case 10:
                mes = "Noviembre";
                break;
            case 11:
                mes = "Diciembre";
                break;
        }

        return fecha.getDate() + " de " + mes + " del " + (fecha.getYear() + 1900);
    }
}
