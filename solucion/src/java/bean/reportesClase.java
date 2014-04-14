/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import net.sf.jasperreports.engine.JRDataSource;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.Region;
import org.joda.time.DateMidnight;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.*;
import sources.*;

/**
 *
 * @author Ismael Jadan
 */
public class reportesClase {

    public reportesClase() {
    }
    public ConvertirNumeros c = new ConvertirNumeros();

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

    public JRDataSource evaluacionProfesores(Cursos curso, Empleados empleado, Encuestar enc) {
        Administrador adm = new Administrador();
        String query = "SELECT mat FROM Respuestasencuestar AS mat "
                + "WHERE mat.empleado.codigoemp = '" + empleado.getCodigoemp() + "' "
                + " and mat.detallepreguntar.preguntar.encuestar.codigo =  '" + enc.getCodigo() + "'  "
                + "order by mat.empleado.apellidos, mat.empleado.nombres,"
                + " mat.matricula.curso.secuencia, "
                + " mat.matricula.curso.descripcion,"
                + " mat.matricula.curso.paralelo.descripcion,  "
                + "mat.detallepreguntar.preguntar.orden,mat.detallepreguntar.secuencia  ";
        if (empleado.getCodigoemp().equals(-2) || empleado.getCodigoemp().equals(-1)) {
            query = "SELECT mat FROM Respuestasencuestar AS mat "
                    + " and mat.detallepreguntar.preguntar.encuestar.codigo =  '" + enc.getCodigo() + "'  "
                    + "order by mat.empleado.apellidos, mat.empleado.nombres,"
                    + " mat.matricula.curso.secuencia, "
                    + " mat.matricula.curso.descripcion,"
                    + " mat.matricula.curso.paralelo.descripcion,  "
                    + "ma.detallepreguntar.preguntar.orden,mat.detallepreguntar.secuencia  ";
        }
//        Session ses = Sessions.getCurrent();
//        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        ArrayList detalle = new ArrayList();
        List hoy = adm.query(query);

        for (Iterator it = hoy.iterator(); it.hasNext();) {
            Respuestasencuestar elem = (Respuestasencuestar) it.next();
            detalle.add(elem);
        }
        ReporteEvaluacionDataSourcer ds = new ReporteEvaluacionDataSourcer(detalle);


        return ds;
    }

    public JRDataSource evaluacionProfesoresResumido(Cursos curso, Empleados empleado, Encuestar enc) {
        Administrador adm = new Administrador();
        String query = "SELECT mat FROM Respuestasencuestar AS mat "
                + "WHERE mat.empleado.codigoemp = '" + empleado.getCodigoemp() + "' "
                + " and mat.detallepreguntar.preguntar.encuestar.codigo =  '" + enc.getCodigo() + "'  "
                + "order by mat.empleado.apellidos, mat.empleado.nombres,"
                + "mat.detallepreguntar.preguntar.orden,mat.detallepreguntar.secuencia  ";
        if (empleado.getCodigoemp().equals(-2) || empleado.getCodigoemp().equals(-1)) {
            query = "SELECT mat FROM Respuestasencuestar AS mat "
                    + " and mat.detallepreguntar.preguntar.encuestar.codigo =  '" + enc.getCodigo() + "'  "
                    + "order by mat.empleado.apellidos, mat.empleado.nombres,"
                    + "mat.detallepreguntar.preguntar.orden,mat.detallepreguntar.secuencia  ";
        }
//        Session ses = Sessions.getCurrent();
//        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        ArrayList detalle = new ArrayList();
        List hoy = adm.query(query);

        for (Iterator it = hoy.iterator(); it.hasNext();) {
            Respuestasencuestar elem = (Respuestasencuestar) it.next();
            detalle.add(elem);
        }
        ReporteEvaluacionDataSourcer ds = new ReporteEvaluacionDataSourcer(detalle);


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

    public ArrayList notasd(Cursos curso, MateriaProfesor materia, Sistemacalificacion sistema) {
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

        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);

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
        if (materia.getMateria().getCodigo().equals(new Integer(0))) {
            equivalencias = adm.query("Select o from Equivalencias as o "
                    + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        }
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
                    + "and notas.materia = '" + materia.getMateria().getCodigo() + "' and notas.disciplina = false "
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
                        + "and notas.materia = '" + materia.getMateria().getCodigo() + "' and notas.disciplina = false "
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
            //equivalencias = null;
        } catch (Exception e) {
            System.out.println("ERROR EN CUADRO DE EQUIVALENCIAS EN REPORTE DE NOTAS POR MATERIA:_  " + e);
        }
//    tamanio = sistemas.size();
        //List<Matriculas> matriculas = adm.query("Select o from Matriculas as o ");
        String q = "Select matriculas.codigomat, " + query + "  from matriculas "
                + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                + "left join notas on matriculas.codigomat = notas.matricula "
                + "and notas.materia = '" + materia.getMateria().getCodigo() + "' and notas.disciplina = false "
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
                    if (truncarNotas) {
                        val = truncar((Double) dos, 2);
                    } else {
                        val = redondear((Double) dos, 2);
                    }
                    nota.setMatricula(matriculaNo);
                    nota.setContador(cont);
                    nota.setNota(val);
                    if (!materia.getCuantitativa()) {
                        nota.setNota(equivalencia(dos, equivalencias));
                    }

                    nota.setMateria(materia.getMateria());
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

//CUADRO DE NOTAS POR MATERIA QUIMESTRAL
    public ArrayList notasq(Cursos curso, MateriaProfesor materia, Sistemacalificacion sistema) {

        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//     int tamanio=0;
        Administrador adm = new Administrador();
        Trimestres tri = sistema.getTrimestre();
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        if (materia.getMateria().getCodigo().equals(new Integer(0))) {
            equivalencias = adm.query("Select o from Equivalencias as o "
                    + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        }
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);

        List<Sistemacalificacion> sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + " and o.trimestre.codigotrim = '" + tri.getCodigotrim() + "' "
                + " order by o.orden ");
        List<Nota> lisNotas = new ArrayList();
        for (Iterator<Sistemacalificacion> itSis = sistemas.iterator(); itSis.hasNext();) {
            Sistemacalificacion sisCalifi = itSis.next();
            List<Sistemaevaluacion> notasEval = adm.query("SELECT o FROM Sistemaevaluacion as o "
                    + " WHERE o.sistemacalificacion.codigosis = " + sisCalifi.getCodigosis() + "  ORDER BY o.orden ");
            //+ " WHERE o.sistemacalificacion.codigosis = " + sisCalifi.getCodigosis() + "  AND o.espromedio = TRUE ORDER BY o.orden ");
            String queryEval = "";
            for (Iterator<Sistemaevaluacion> it = notasEval.iterator(); it.hasNext();) {
                Sistemaevaluacion siste = it.next();
                queryEval += siste.getNombre() + ",";
            }
            try {


                //lisNotas = new ArrayList();
                // AQUI APLICO SI ES QUE EXISTEN NOTAS
                if (notasEval.size() > 2) {
                    queryEval = queryEval.substring(0, queryEval.length() - 1).replace("'", "").replace("(", "").replace(")", "");
                    String q = "Select matriculas.codigomat, " + queryEval + "  from matriculas "
                            + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                            + "left join notasevaluacion on matriculas.codigomat = notasevaluacion.matricula "
                            + "and notasevaluacion.materia = '" + materia.getMateria().getCodigo() + "' and notasevaluacion.sistemacalificacion = '" + sisCalifi.getCodigosis() + "'   "
                            + "where matriculas.curso = '" + curso.getCodigocur() + "' "
                            + "and matriculas.estado in ('Matriculado','Recibir Pase','Retirado','Emitir Pase')  "
                            + "order by estudiantes.apellido";
                    System.out.println("" + q);
                    List nativo = adm.queryNativo(q);
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
                                if (truncarNotas) {
                                    val = truncar((Double) dos, 2);
                                } else {
                                    val = redondear((Double) dos, 2);
                                }
                                nota.setMatricula(matriculaNo);
                                nota.setContador(cont);
                                nota.setNota(val);
                                if (!materia.getCuantitativa()) {
                                    nota.setNota(equivalencia(dos, equivalencias));
                                }

                                nota.setMateria(materia.getMateria());
                                nota.setSistema(sisCalifi);
                                try {
                                    nota.setSistemaSi("" + ((Sistemaevaluacion) notasEval.get(ksis)).getOrden() + "*" + ((Sistemaevaluacion) notasEval.get(ksis)).getEvaluacion().getAbreviatura());
                                } catch (Exception e) {
                                    nota.setSistemaSi("8*P." + sisCalifi.getOrden());
                                }
//                            String a= null;
//                            a.substring(a.indexOf("*"));
                                nota.setTipoSi(sisCalifi.getNombre());
                                lisNotas.add(nota);
                                ksis++;
                            } else {
                                matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                                //mprofesor = adm.query("Select o from ")
                            }
                        }
                        cont++;
                    }


                } else {//CASO CONTRARIO SOLO AGREGO UNA SOLA NOTA

                    List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                            + "where  o.sistema.codigosis = '" + sisCalifi.getCodigosis() + "' "
                            + "  order by o.sistema.orden ");
                    String query = "";
                    for (Notanotas notass : notas) {
                        query += notass.getNota() + ",";
                    }
                    query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
                    String q = "Select matriculas.codigomat, " + query + "  from matriculas "
                            + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                            + "left join notas on matriculas.codigomat = notas.matricula "
                            + "and notas.materia = '" + materia.getMateria().getCodigo() + "'  "
                            + "where matriculas.curso = '" + curso.getCodigocur() + "' and notas.disciplina = false "
                            + "and matriculas.estado in ('Matriculado','Recibir Pase','Retirado','Emitir Pase')  "
                            + "order by estudiantes.apellido";
                    System.out.println("" + q);
                    List nativo = adm.queryNativo(q);

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
                                if (truncarNotas) {
                                    val = truncar((Double) dos, 2);
                                } else {
                                    val = redondear((Double) dos, 2);
                                }
                                nota.setMatricula(matriculaNo);
                                nota.setContador(cont);
                                nota.setNota(val);
                                if (!materia.getCuantitativa()) {
                                    nota.setNota(equivalencia(dos, equivalencias));
                                }

                                nota.setMateria(materia.getMateria());
                                nota.setSistema(sisCalifi);
                                nota.setSistemaSi((sisCalifi.getOrden() + 9) + "*" + sisCalifi.getNombre());
                                nota.setTipoSi("99.* ");
                                lisNotas.add(nota);
                                ksis++;
                            } else {
                                matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                                //mprofesor = adm.query("Select o from ")
                            }
                        }
                        cont++;
                    }


                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }




        Map parametros = new HashMap();
        Institucion insts = curso.getPeriodo().getInstitucion();
        parametros.put("denominacion", insts.getDenominacion());
        parametros.put("nombre", insts.getNombre());
        parametros.put("periodo", periodo.getDescripcion());
        parametros.put("slogan", insts.getSlogan());
        insts = null;
//    tamanio = sistemas.size();
        //List<Matriculas> matriculas = adm.query("Select o from Matriculas as o ");

//        nativo = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        ArrayList arr = new ArrayList();
        arr.add(ds);
        arr.add(parametros);
        return arr;
    }

    //CUADRO DE NOTAS POR MATERIA OTRO.
    public ArrayList notasdAportes(Cursos curso, MateriaProfesor materia, Sistemacalificacion sistema) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();
        String quu = "Select o from Sistemaevaluacion as o where o.sistemacalificacion.codigosis = '" + sistema.getCodigosis() + "' order by o.orden ";
        List<Sistemaevaluacion> notas = adm.query(quu);
        String query = "";
        for (Sistemaevaluacion notass : notas) {
            query += notass.getNombre() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        if (materia.getMateria().getCodigo().equals(new Integer(0))) {
            equivalencias = adm.query("Select o from Equivalencias as o "
                    + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        }
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);

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
            String q2 = "Select  count(nota1)  from matriculas "
                    + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                    + "left join notasevaluacion on matriculas.codigomat = notasevaluacion.matricula "
                    + "and notasevaluacion.materia = '" + materia.getMateria().getCodigo() + "'  "
                    + "where matriculas.curso = '" + curso.getCodigocur() + "' and matriculas.estado in ('Matriculado','Recibir Pase')  "
                    + " group by sistemacalificacion order by estudiantes.apellido";
            System.out.println("" + q2);
            List resulValor = adm.queryNativo(q2);
            Long totalEstudiantes = (Long) ((Vector) resulValor.get(0)).get(0);
            for (Iterator<Equivalencias> it = equivalencias.iterator(); it.hasNext();) {
                Equivalencias equivalencias1 = it.next();
                parametros.put("eq" + (i + 1), equivalencias1.getNombre());
                parametros.put("ran" + (i + 1), equivalencias1.getValorminimo() + " - " + equivalencias1.getValormaximo());

                String notaAseleccionar = notas.get(notas.size() - 1).getNombre();
                String q = "Select  count(" + notaAseleccionar + ") from matriculas "
                        + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                        + "left join notasevaluacion on matriculas.codigomat = notasevaluacion.matricula "
                        + "and notasevaluacion.materia = '" + materia.getMateria().getCodigo() + "' "
                        + "and notasevaluacion.sistemacalificacion = '" + sistema.getCodigosis() + "' "
                        + "where matriculas.curso = '" + curso.getCodigocur() + "' and  " + notaAseleccionar + " "
                        + " BETWEEN " + equivalencias1.getValorminimo() + " AND " + equivalencias1.getValormaximo() + " "
                        + "and matriculas.estado in ('Matriculado','Recibir Pase')  "
                        + " order by estudiantes.apellido";
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

        } catch (Exception e) {
            System.out.println("ERROR EN CUADRO DE EQUIVALENCIAS EN REPORTE DE NOTAS POR MATERIA:_  " + e);
        }
//    tamanio = sistemas.size();
        //List<Matriculas> matriculas = adm.query("Select o from Matriculas as o ");
        String q = "Select matriculas.codigomat, " + query + "  from matriculas "
                + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                + "left join notasevaluacion on matriculas.codigomat = notasevaluacion.matricula "
                + "and notasevaluacion.materia = '" + materia.getMateria().getCodigo() + "' and notasevaluacion.sistemacalificacion = '" + sistema.getCodigosis() + "'"
                + "where matriculas.curso = '" + curso.getCodigocur() + "' "
                + "and matriculas.estado in ('Matriculado','Recibir Pase','Retirado','Emitir Pase')  "
                + "order by estudiantes.apellido";
        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        int cont = 1;
        List<MateriaProfesor> map = adm.query("Select o from MateriaProfesor as o  "
                + "where o.curso.codigocur = '" + curso.getCodigocur() + "' "
                + " and o.materia.codigo = '" + materia.getMateria().getCodigo() + "'  ");
        MateriaProfesor map2 = new MateriaProfesor(0);
        if (map.size() > 0) {
            map2 = map.get(0);
        }
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
                    if (truncarNotas) {
                        val = truncar((Double) dos, 2);
                    } else {
                        val = redondear((Double) dos, 2);
                    }
                    nota.setMatricula(matriculaNo);
                    nota.setContador(cont);
                    if (notas.get(ksis).getEsdisciplina() || !materia.getCuantitativa()) {
                        try {
                            String valo = (String) equivalencia(val, equivalencias);
                            System.out.println("OTENIDO: " + valo);
                            nota.setNota(valo);
                        } catch (Exception e) {
                            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, e);
////                            System.out.println("ERROR AL TRAER EQUIVALENCIA"+e);
                        }


                    } else {
                        nota.setNota(val);
                    }

                    nota.setMateria(materia.getMateria());

                    nota.setSistemaSi(notas.get(ksis).getAbreviatura());
                    try {
                        nota.setTipoSi(notas.get(ksis).getEvaluacion().getDescripcion());
                    } catch (Exception e) {
                        nota.setTipoSi("Prom.");
                    }
                    if (!map2.getCodigomap().equals(new Integer(0))) {
                        if (map2.getIngcualitativo()) {
                            try {
                                nota.setTipoSi(notas.get(ksis).getEvaluacion().getDescripcion2());
                            } catch (Exception e) {
                                nota.setTipoSi("Prom");
//                                e.printStackTrace();
                            }

                        }
                    }


                    nota.setSistema(notas.get(ksis).getSistemacalificacion());
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
        equivalencias = null;
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
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
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
                    if (truncarNotas) {
                        val = truncar((Double) dos, 2);
                    } else {
                        val = redondear((Double) dos, 2);
                    }
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

    public JRDataSource cuadrocalificaciones(Cursos curso, Sistemacalificacion sistema, Double desde, Double hasta, Boolean incluyefaltas, Boolean incluyepromedio, Boolean incluyedias) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
         Integer noDecimalesProme = 3;
        try {
            noDecimalesProme = regresaVariableParametrosDecimal("DECIMALESPRO", parametrosGlobales).intValue();
        } catch (Exception a) {
            noDecimalesProme = 3;

        }
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
        List<Equivalencias> equivalencias2 = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");



//    tamanio = sistemas.size();
        //List<Matriculas> matriculas = adm.query("Select o from Matriculas as o ");
/*
         * String q = "Select matricula,materia, "+query+" from notas " + "where
         * matricula in (select codigomat from matriculas where curso =
         * '"+curso.getCodigocur()+"' ) " + " ";
         */
        //and matriculas.estado in ('Matriculado','Recibir Pase','Retirado')
        String q = "Select codigomap, mat.codigomat,notas.materia, " + query + "  "
                + "from notas, materia_profesor, matriculas mat, estudiantes est "
                + "where notas.materia =  materia_profesor.materia and materia_profesor.curso = '" + curso.getCodigocur() + "' "
                + " and notas.materia not in (Select codigo from Global where descripcion like '%DESGLOSE %' ) "
                + " AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "' "
                + " and estado in ('Matriculado','Recibir Pase','Emitir Pase','Retirado') )"
                + "and notas.disciplina = false and materia_profesor.seimprime = true  "
                + "order by CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";
        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        List<Equivalencias> equivalenciasFaltas = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DI' "
                + "and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalenciasFaltasSoloDias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DI' and  (o.esfj = TRUE OR o.esfi = TRUE)  "
                + "and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Dias> laborados = adm.query("Select o from Dias as o "
                + " where o.sistema.codigosis  = '" + sistema.getCodigosis() + "' ");
        Integer diasLaborados = 0;
        if (laborados.size() > 0) {
            diasLaborados = laborados.get(0).getDias();
        } else {
        }

        Integer cont = 0;
        String matriculaAct = "";
        int iVec = 0;
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            iVec++;
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
                    if (truncarNotas) {
                        val = truncar((Double) dos, 2);
                    } else {
                        val = redondear((Double) dos, 2);
                    }

                    if (materiaNo.getCodigo().equals(0)) {
                        val = redondear((Double) dos, numeroDecimalesDisc.intValue());
                    }
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    nota.setContador(cont);
                    matriculaAct = matriculaNo.getCodigomat() + "";
                    if (maprofesor.getCuantitativa() == false) {
                        if (materiaNo.getCodigo().equals(new Integer(0))) {
                            nota.setNota(equivalencia(dos, equivalencias2));
                        } else {
                            nota.setNota(equivalencia(dos, equivalencias));
                        }

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

                    if (j == (vec.size() - 1)) {
                        String matriNueva = matriculaAct;
                        try {
                            Vector vecSig = (Vector) nativo.get(iVec);
                            Object dosTmp = vecSig.get(1);
                            matriNueva = dosTmp + "";
                        } catch (Exception e) {
                            System.out.println("" + e);
                            if (iVec == nativo.size()) {
                                matriNueva = "";
                            }
                        }

                        if (true && !matriculaAct.equals(matriNueva)) {//PARA EL PROMEDIO

                            //IMPRIMO EL PROMEDIO ******************************* 
                            if (incluyepromedio) {
                                Object promedioFinal = null;
                                String query2 = "round(cast(avg(" + query + ") as decimal(9,"+noDecimalesProme+"))," + noDecimalesProme + ")";
                                if(truncarNotas){
                                        query2 = "truncate(cast(avg(" + query + ") as decimal(9,"+noDecimalesProme+"))," + noDecimalesProme + ")";
                                }
                                 //query2 = "round(cast(avg(" + query + ") as decimal(9,4))," + 2 + ")";
                                q = "Select matricula," + query2 + "  from notas "
                                        + "where notas.matricula = '" + matriculaNo.getCodigomat() + "' "
                                        + "and notas.seimprime = true "
                                        + "and notas.promedia = true "
                                        + "and notas.disciplina = false "
                                        + " and notas.materia != 0 "
                                        + " and " + query + " >0 "
                                        //+ "and notas.cuantitativa = true and notas.materia != 0 "
                                        + "group by matricula  ";
                                System.out.println("NOTAS DE promedio " + q);
                                List nativo2 = null;
                                nativo2 = adm.queryNativo(q);
                                if (nativo2.size() > 0) {
                                    for (Iterator itProm = nativo2.iterator(); itProm.hasNext();) {
                                        Vector vecProm = (Vector) itProm.next();

                                        Boolean cuantitativa = false;
                                        Global mate = new Global();
                                        mate.setCodigo(0);
                                        mate.setDescripcion("PROMEDIO");
                                        int ksisProm = 0;
                                        for (int jj = 0; jj < vecProm.size(); jj++) {
                                            Object dosProm = vecProm.get(jj);
                                            Double valProm = 0.0;

                                            Nota coll = new Nota();
                                            try {
                                                if (dosProm.equals(null)) {
                                                    dosProm = new Double(0.0);
                                                }
                                            } catch (Exception e) {
                                                dosProm = new BigDecimal(0.0);
                                                //                                        System.out.println(""+e);
                                            }
                                            System.out.println("PROMEDIOS: " + dosProm);
                                            if (jj > 0) {
                                                valProm = ((BigDecimal) dosProm).doubleValue();
                                                coll.setNota(dosProm);
//                                                if (maprofesor.getCuantitativa() == false || ((Sistemacalificacion) sistemas.get(ksisProm)).getEsequivalencia()) {
//                                                    coll.setNota(equivalencia(((BigDecimal) dosProm).doubleValue(), equivalencias));
//                                                } else {
                                                if (valProm.doubleValue() == 0.0) {
                                                    coll.setNota("");
                                                }
//                                                }
                                                promedioFinal = dosProm;
                                                Global promMate = new Global(1111);
                                                promMate.setDescripcion("PROMEDIO");

                                                coll.setContador(cont);
                                                coll.setMatricula(matriculaNo);
                                                //                                                       coll.setNota(valF);
                                                coll.setMateria(promMate);
                                                coll.setMprofesor(maprofesor);
                                                coll.setSistema(sistema);

                                                lisNotas.add(coll);
                                                ksisProm++;
                                            }

                                        }
                                        //row.setParent(this);
                                    }
                                } else {

                                    Boolean cuantitativa = false;
                                    Global mate = new Global();
                                    mate.setCodigo(0);
                                    mate.setDescripcion("PROMEDIO");
                                    int ksisProm = 0;
                                    Double valProm = 0.0;
                                    Nota coll = new Nota();
                                    coll.setNota("");
                                    Global promMate = new Global(1111);
                                    promMate.setDescripcion("PROMEDIO");
                                    coll.setContador(cont);
                                    coll.setMatricula(matriculaNo);
                                    coll.setMateria(promMate);
                                    coll.setMprofesor(maprofesor);
                                    coll.setSistema(sistema);
                                    lisNotas.add(coll);
                                    ksisProm++;


                                }

                            }




                            if (incluyefaltas) {

                                /**
                                 * AGREGRO LAS FALTAS AL REPORTE
                                 */
                                String query3 = "";
                                int w = 1;

                                for (int i = 0; i < equivalenciasFaltas.size(); i++) {
                                    query3 += "sum(nota" + w + "),";

                                    w++;
                                }
                                query3 = query3.substring(0, query3.length() - 1);
                                //IMPRIMO LAS FALTAS
                                String qf = "Select " + query3 + "  from disciplina "
                                        + "where matricula = '" + matriculaNo.getCodigomat() + "'  "
                                        + "and sistema = '" + sistema.getCodigosis() + "' "
                                        + " group by matricula ";
                                //System.out.println(""+q);
                                List nativoF = adm.queryNativo(qf);
                                for (Iterator itnaF = nativoF.iterator(); itnaF.hasNext();) {
                                    Vector vecF = (Vector) itnaF.next();
                                    for (int jF = 0; jF < vecF.size(); jF++) {
                                        Object dosF = vecF.get(jF);
                                        Integer valF = new Integer(dosF.toString());
                                        Nota notaF = new Nota();
                                        notaF.setContador(cont);
                                        notaF.setMatricula(matriculaNo);
                                        notaF.setNota(valF);
                                        Global matNueva = new Global((equivalenciasFaltas.get(jF)).getCodigoequi());
                                        matNueva.setDescripcion((equivalenciasFaltas.get(jF)).getNombre());
                                        notaF.setMateria(matNueva);
                                        notaF.setMprofesor(maprofesor);
                                        notaF.setSistema(sistema);
                                        lisNotas.add(notaF);
                                    }

                                }

                            }
                            if (incluyedias) {

                                /**
                                 * AGREGRO LAS FALTAS AL REPORTE
                                 */
                                String query3 = "";
                                int w = 1;

                                for (int i = 0; i < equivalenciasFaltasSoloDias.size(); i++) {
                                    query3 += "sum(nota" + w + "),";
                                    w++;
                                }
                                query3 = query3.substring(0, query3.length() - 1);
                                //IMPRIMO LAS FALTAS
                                String qf = "Select " + query3 + "  from disciplina "
                                        + "where matricula = '" + matriculaNo.getCodigomat() + "'  "
                                        + "and sistema = '" + sistema.getCodigosis() + "' "
                                        + " group by matricula ";
                                //System.out.println(""+q);
                                List nativoF = adm.queryNativo(qf);
                                Integer faltasFustificadas = 0;
                                Integer faltasInjustificadas = 0;
                                for (Iterator itnaF = nativoF.iterator(); itnaF.hasNext();) {
                                    Vector vecF = (Vector) itnaF.next();

                                    for (int jF = 0; jF < vecF.size(); jF++) {
                                        Object dosF = vecF.get(jF);
                                        Integer valF = new Integer(dosF.toString());
                                        Nota notaF = new Nota();
                                        notaF.setContador(cont);
                                        notaF.setMatricula(matriculaNo);
                                        notaF.setNota(valF);
                                        Global matNueva = new Global((equivalenciasFaltasSoloDias.get(jF)).getCodigoequi());
                                        matNueva.setDescripcion((equivalenciasFaltasSoloDias.get(jF)).getNombre());
                                        if ((equivalenciasFaltasSoloDias.get(jF)).getEsfj()) {
                                            faltasFustificadas = valF;
                                        } else if ((equivalenciasFaltasSoloDias.get(jF)).getEsfi()) {
                                            faltasInjustificadas = valF;
                                        }
                                        notaF.setMateria(matNueva);
                                        notaF.setMprofesor(maprofesor);
                                        notaF.setSistema(sistema);
                                        lisNotas.add(notaF);
                                    }

                                }

                                Nota notaF = new Nota();
                                notaF.setContador(cont);
                                notaF.setMatricula(matriculaNo);
                                notaF.setNota((diasLaborados - faltasInjustificadas));
                                Global matNueva = new Global(1000);
                                matNueva.setDescripcion("Asistencia");
                                notaF.setMateria(matNueva);
                                notaF.setMprofesor(maprofesor);
                                notaF.setSistema(sistema);
                                lisNotas.add(notaF);



                            }


                        }//FIN DE TRUE DE PROMEDIO


                    }

                } else if (j == 1) {
                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                } else if (j == 2) {
                    materiaNo = (Global) adm.buscarClave((Integer) dos, Global.class);
                } else if (j == 0) {
                    maprofesor = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                }

                if (matriculaNo != null && j > 1) {
                    if (!matriculaNo.getCodigomat().toString().equals(matriculaAct)) {

                        cont++;
                    }
                }
            }


        }
        nativo = null;
        equivalenciasFaltas = null;
        equivalencias = null;
        equivalenciasFaltasSoloDias = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        System.gc();
        lisNotas = null;
        return ds;

    }

    public JRDataSource cuadrocalificacionesPrimaria(Cursos curso, Sistemacalificacion sistema, Double desde, Double hasta, Boolean incluyefaltas, Boolean incluyepromedio, Boolean incluyedias) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
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

        String q = "Select codigomap, mat.codigomat,notas.materia, " + query + "  "
                + "from notas, materia_profesor, matriculas mat, estudiantes est "
                + "where notas.materia =  materia_profesor.materia and materia_profesor.curso = '" + curso.getCodigocur() + "' "
                + " AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                + " and notas.materia not in (Select codigo from Global where descripcion like '%DESGLOSE %' ) "
                + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "' "
                + " and estado in ('Matriculado','Recibir Pase','Emitir Pase','Retirado') )"
                + "and notas.disciplina = false and materia_profesor.seimprime = true  "
                + "order by CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";
        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        List<Equivalencias> equivalenciasFaltas = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DI' "
                + "and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalenciasFaltasSoloDias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DI' and  (o.esfj = TRUE OR o.esfi = TRUE)  "
                + "and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Dias> laborados = adm.query("Select o from Dias as o "
                + " where o.sistema.codigosis  = '" + sistema.getCodigosis() + "' ");
        Integer diasLaborados = 0;
        if (laborados.size() > 0) {
            diasLaborados = laborados.get(0).getDias();
        } else {
        }

        Integer cont = 0;
        String matriculaAct = "";
        int iVec = 0;
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            iVec++;
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

                    if (truncarNotas) {
                        val = truncar((Double) dos, 2);
                    } else {
                        val = redondear((Double) dos, 2);
                    }


                    if (materiaNo.getCodigo().equals(0)) {
                        val = redondear((Double) dos, numeroDecimalesDisc.intValue());
                    }
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    nota.setContador(cont);
                    matriculaAct = matriculaNo.getCodigomat() + "";
                    if (maprofesor.getCuantitativa() == false) {

                        nota.setNota(equivalencia(dos, equivalencias));
                        if ((Double) dos > 10.0 && !matriculaNo.getEstado().equals("Retirado")) {
                            nota.setNota("Aprobado");
                        } else if (matriculaNo.getEstado().equals("Retirado")) {
                            nota.setNota("");
                        } else {
                            nota.setNota(" ");
                        }
                    } else {
                        nota.setNota(val.toString());
                        if (val == 0.0) {
                            nota.setNota("");
                        }
                        if (matriculaNo.getEstado().equals("Retirado")) {
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

                    if (j == (vec.size() - 1)) {
                        String matriNueva = matriculaAct;
                        try {
                            Vector vecSig = (Vector) nativo.get(iVec);
                            Object dosTmp = vecSig.get(1);
                            matriNueva = dosTmp + "";
                        } catch (Exception e) {
                            System.out.println("" + e);
                            if (iVec == nativo.size()) {
                                matriNueva = "";
                            }
                        }

                        if (true && !matriculaAct.equals(matriNueva)) {//PARA EL PROMEDIO

                            //IMPRIMO EL PROMEDIO ******************************* 
                            if (incluyepromedio) {
                                Object promedioFinal = null;
                                String query2 = "round(cast(avg(" + query + ") as decimal(9,4))," + 3 + ")";
                                if (truncarNotas) {
                                    query2 = "truncate(cast(avg(" + query + ") as decimal(9,4))," + 3 + ")";
                                }
                                q = "Select matricula," + query2 + "  from notas "
                                        + "where notas.matricula = '" + matriculaNo.getCodigomat() + "' "
                                        + "and notas.seimprime = true "
                                        + "and notas.promedia = true "
                                        + "and notas.disciplina = false "
                                        + " and notas.materia != 0 "
                                        //+ "and notas.cuantitativa = true and notas.materia != 0 "
                                        + "group by matricula  ";
                                //                System.out.println("NOTAS DE promedio " + q);
                                List nativo2 = null;
                                nativo2 = adm.queryNativo(q);
                                for (Iterator itProm = nativo2.iterator(); itProm.hasNext();) {
                                    Vector vecProm = (Vector) itProm.next();

                                    Boolean cuantitativa = false;
                                    Global mate = new Global();
                                    mate.setCodigo(0);
                                    mate.setDescripcion("PROMEDIO");
                                    int ksisProm = 0;
                                    for (int jj = 0; jj < vecProm.size(); jj++) {
                                        Object dosProm = vecProm.get(jj);
                                        Double valProm = 0.0;

                                        Nota coll = new Nota();
                                        try {
                                            if (dosProm.equals(null)) {
                                                dosProm = new Double(0.0);
                                            }
                                        } catch (Exception e) {
                                            dosProm = new BigDecimal(0.0);
                                            //                                        System.out.println(""+e);
                                        }
                                        System.out.println("PROMEDIOS: " + dosProm);
                                        if (jj > 0) {
                                            valProm = ((BigDecimal) dosProm).doubleValue();
                                            coll.setNota(dosProm);
                                            if (maprofesor.getCuantitativa() == false || ((Sistemacalificacion) sistemas.get(ksisProm)).getEsequivalencia()) {
                                                coll.setNota(equivalencia(((BigDecimal) dosProm).doubleValue(), equivalencias));
                                            } else {
                                                if (valProm == 0.0) {
                                                    coll.setNota("");
                                                }
                                            }
                                            promedioFinal = dosProm;
                                            Global promMate = new Global(1000);
                                            promMate.setDescripcion("PROMEDIO");

                                            coll.setContador(cont);
                                            coll.setMatricula(matriculaNo);
                                            //                                                       coll.setNota(valF);
                                            coll.setMateria(promMate);
                                            coll.setMprofesor(maprofesor);
                                            coll.setSistema(sistema);
                                            try {
                                                coll.setAprovechamiento(new Double(coll.getNota().toString()));
                                            } catch (Exception a) {
                                                coll.setAprovechamiento(new Double("0"));
                                            }
                                            if (matriculaNo.getEstado().equals("Retirado")) {
                                                coll.setNota("");
                                                coll.setAprovechamiento(new Double("0"));
                                            }
                                            lisNotas.add(coll);
                                            ksisProm++;
                                        }

                                    }
                                    //row.setParent(this);
                                }
                            }




                            if (incluyefaltas) {

                                /**
                                 * AGREGRO LAS FALTAS AL REPORTE
                                 */
                                String query3 = "";
                                int w = 1;

                                for (int i = 0; i < equivalenciasFaltas.size(); i++) {
                                    query3 += "sum(nota" + w + "),";

                                    w++;
                                }
                                query3 = query3.substring(0, query3.length() - 1);
                                //IMPRIMO LAS FALTAS
                                String qf = "Select " + query3 + "  from disciplina "
                                        + "where matricula = '" + matriculaNo.getCodigomat() + "'  "
                                        + "and sistema = '" + sistema.getCodigosis() + "' "
                                        + " group by matricula ";
                                //System.out.println(""+q);
                                List nativoF = adm.queryNativo(qf);
                                for (Iterator itnaF = nativoF.iterator(); itnaF.hasNext();) {
                                    Vector vecF = (Vector) itnaF.next();
                                    for (int jF = 0; jF < vecF.size(); jF++) {
                                        Object dosF = vecF.get(jF);
                                        Integer valF = new Integer(dosF.toString());
                                        Nota notaF = new Nota();
                                        notaF.setContador(cont);
                                        notaF.setMatricula(matriculaNo);
                                        notaF.setNota(valF);
                                        Global matNueva = new Global((equivalenciasFaltas.get(jF)).getCodigoequi());
                                        matNueva.setDescripcion((equivalenciasFaltas.get(jF)).getNombre());
                                        notaF.setMateria(matNueva);
                                        notaF.setMprofesor(maprofesor);
                                        notaF.setSistema(sistema);
                                        lisNotas.add(notaF);
                                    }

                                }

                            }
                            if (incluyedias) {

                                /**
                                 * AGREGRO LAS FALTAS AL REPORTE
                                 */
                                String query3 = "";
                                int w = 1;

                                for (int i = 0; i < equivalenciasFaltasSoloDias.size(); i++) {
                                    query3 += "sum(nota" + w + "),";
                                    w++;
                                }
                                query3 = query3.substring(0, query3.length() - 1);
                                //IMPRIMO LAS FALTAS
                                String qf = "Select " + query3 + "  from disciplina "
                                        + "where matricula = '" + matriculaNo.getCodigomat() + "'  "
                                        + "and sistema = '" + sistema.getCodigosis() + "' "
                                        + " group by matricula ";
                                //System.out.println(""+q);
                                List nativoF = adm.queryNativo(qf);
                                Integer faltasFustificadas = 0;
                                Integer faltasInjustificadas = 0;
                                for (Iterator itnaF = nativoF.iterator(); itnaF.hasNext();) {
                                    Vector vecF = (Vector) itnaF.next();

                                    for (int jF = 0; jF < vecF.size(); jF++) {
                                        Object dosF = vecF.get(jF);
                                        Integer valF = new Integer(dosF.toString());
                                        Nota notaF = new Nota();
                                        notaF.setContador(cont);
                                        notaF.setMatricula(matriculaNo);
                                        notaF.setNota(valF);
                                        Global matNueva = new Global((equivalenciasFaltas.get(jF)).getCodigoequi());
                                        matNueva.setDescripcion((equivalenciasFaltas.get(jF)).getNombre());
                                        if ((equivalenciasFaltas.get(jF)).getEsfj()) {
                                            faltasFustificadas = valF;
                                        } else if ((equivalenciasFaltas.get(jF)).getEsfi()) {
                                            faltasInjustificadas = valF;
                                        }
                                        notaF.setMateria(matNueva);
                                        notaF.setMprofesor(maprofesor);
                                        notaF.setSistema(sistema);
                                        lisNotas.add(notaF);
                                    }

                                }

                                Nota notaF = new Nota();
                                notaF.setContador(cont);
                                notaF.setMatricula(matriculaNo);
                                notaF.setNota((diasLaborados - faltasInjustificadas));
                                Global matNueva = new Global(1000);
                                matNueva.setDescripcion("Asistencia");
                                notaF.setMateria(matNueva);
                                notaF.setMprofesor(maprofesor);
                                notaF.setSistema(sistema);
                                lisNotas.add(notaF);
                            }


                        }//FIN DE TRUE DE PROMEDIO


                    }

                } else if (j == 1) {
                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                } else if (j == 2) {
                    materiaNo = (Global) adm.buscarClave((Integer) dos, Global.class);
                } else if (j == 0) {
                    maprofesor = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                }

                if (matriculaNo != null && j > 1) {
                    if (!matriculaNo.getCodigomat().toString().equals(matriculaAct)) {

                        cont++;
                    }
                }
            }


        }
        nativo = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;

    }
    List<ParametrosGlobales> parametrosGlobales = null;
    public List<MateriaProfesor> materiasReprobadas = new ArrayList<MateriaProfesor>();

    public int quitarPaso(List<MateriaProfesor> maprof, Integer codigomatricula, Integer codigomateria) {

        int m = 0;
        for (int i = 0; i < maprof.size(); i++) {
            try {
                MateriaProfesor materiaProfesor = maprof.get(i);

                if (materiaProfesor.getCodigomap().equals(codigomatricula) && materiaProfesor.getOrden().equals(codigomateria)) {
                    maprof.remove(materiaProfesor);
                    i = 0;
                    m++;
                }
            } catch (Exception e) {
                //System.out.println("" + codigomatricula + " MATEIRA: " + codigomateria+""+e);
                System.out.println("" + e);
                break;

            }
        }
        return m;
//        for (Iterator<MateriaProfesor> it = maprof.iterator(); it.hasNext();) {
//              try {
//                  MateriaProfesor materiaProfesor = it.next();
//         
//                if (materiaProfesor.getCodigomap().equals(codigomatricula) && materiaProfesor.getOrden().equals(codigomateria)) {
//                    maprof.remove(materiaProfesor);
//                }
//            } catch (ConcurrentModificationException e) {
//                //System.out.println("" + codigomatricula + " MATEIRA: " + codigomateria+""+e);
//                System.out.println("" + e);
//                break;
//                
//            }
//
//        }
    }

//    public List<Matriculas> cuadroverificar(Cursos curso, Sistemacalificacion sistema, Matriculas matriculaActual) {
////     int tamanio=0;
//        Administrador adm = new Administrador();
////        Session ses = Sessions.getCurrent();
//        Periodo periodo = curso.getPeriodo();
//        materiasReprobadas = new ArrayList<MateriaProfesor>();
//        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
//                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
//        Double sumaPierde = regresaVariableParametrosDecimal("SUMATORIAPIERDE", parametrosGlobales);
//        Double sumaAprueba = regresaVariableParametrosDecimal("SUMATORIAAPRUEBA", parametrosGlobales);
//        Double valorPromedioGeneral = regresaVariableParametrosDecimal("VALORPROMEDIOGENERAL", parametrosGlobales);
//        Boolean validaConPromedioGeneral = regresaVariableParametrosLogico("PROMEDIOGENERAL", parametrosGlobales);
//
//        if (valorPromedioGeneral <= 0) {
//            valorPromedioGeneral = sumaAprueba;
//        }
//        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
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
//                Messagebox.show("No ha parametrizado el Promedio Final en Aportes...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
//                return null;
//            } catch (InterruptedException ex) {
//                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        if (notas.size() <= 0) {
//            try {
//                Messagebox.show("No han nada que imprimir Aportes en 0 ...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
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
//                Messagebox.show("No existen los rangos de Supletorio, ingrese a Equivalencias > Supletorios y verifique que esté lleno...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
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
//        String q = "";
//        if (matriculaActual.getCodigomat().equals(new Integer(-1)) || matriculaActual.getCodigomat().equals(new Integer(-2))) {
//            q = "Select codigomap, mat.codigomat,notas.materia, " + query + ", 'obs'  from notas, materia_profesor , matriculas mat, estudiantes est "
//                    + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
//                    + "and materia_profesor.curso = '" + curso.getCodigocur() + "' "
//                    + "and notas.promedia = true and notas.disciplina = false "
//                    + "and notas.materia != 0 and notas.materia != 1 and materia_profesor.seimprime = true  "
//                    + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "' and estado in  ('Matriculado','Recibir Pase')  ) "
//                    + "  AND NOTAS.MATERIA NOT IN (SELECT CODIGO FROM GLOBAL WHERE GRUPO = 'MAT' AND DESCRIPCION LIKE '%PROME%')   "
//                    + "order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";
//        } else {
//            q = "Select codigomap, mat.codigomat,notas.materia, " + query + ", 'obs'  from notas, materia_profesor , matriculas mat, estudiantes est "
//                    + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
//                    + "and materia_profesor.curso = '" + curso.getCodigocur() + "' "
//                    + "and notas.promedia = true and notas.disciplina = false "
//                    + "and notas.materia != 0 and notas.materia != 1 and materia_profesor.seimprime = true  "
//                    + "and matricula in (" + matriculaActual.getCodigomat() + ") "
//                    + "  AND NOTAS.MATERIA NOT IN (SELECT CODIGO FROM GLOBAL WHERE GRUPO = 'MAT' AND DESCRIPCION LIKE '%PROME%')   "
//                    + "order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";
//        }
//
//
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
//            Double pfinal = 0.0;
//            Double pgeneral = 0.0;
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
//                    if (truncarNotas) {
//                        val = truncar((Double) dos, 2);
//                    } else {
//                        val = redondear((Double) dos, 2);
//                    }
//                    matricula = matriculaNo.toString();
//                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
//                    if (nota.getSistema().getPromediofinal().equals("PG")) {
//                        if (val < valorPromedioGeneral && validaConPromedioGeneral) {
//                            obs = "Pierde";
//                            System.out.println("pierde PG (1):" + matricula + " mat:" + materia + " not:" + val + " FALTA VERIFICAR SUPLE");
//                            MateriaProfesor matR = new MateriaProfesor();
//                            matR.setCodigomap(matriculaNo.getCodigomat());
//                            matR.setOrden(materia.getCodigo());
//                            materiasReprobadas.add(matR);
//                            obs1++;
//                        } else {
//                            obs = "";
//                        }
//                        pgeneral = val;
//                    } else if (nota.getSistema().getPromediofinal().equals("SM")) {
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
//                        if (val < valorPromedioGeneral) {
//                            obs = "Pierde";
//                            System.out.println("pierde PF (1):" + matricula + " mat:" + materia + " not:" + val);
//                            MateriaProfesor matR = new MateriaProfesor();
//                            matR.setCodigomap(matriculaNo.getCodigomat());
//                            matR.setOrden(materia.getCodigo());
//                            materiasReprobadas.add(matR);
//                            obs1++;
//                        } else {
//                            obs = "";
//                            MateriaProfesor matR = new MateriaProfesor();
//                            matR.setCodigomap(matriculaNo.getCodigomat());
//                            matR.setOrden(materia.getCodigo());
//                            if (materiasReprobadas.contains(matR)) {
//                                //materiasReprobadas.remove(matR);
//                                System.out.println("ENVIO A QUITAR PF: " + materia);
//                                int Noquitadas = quitarPaso(materiasReprobadas, matriculaNo.getCodigomat(), materia.getCodigo());
//                                System.out.println("PF..he quitado la materia como reprobada: " + materia.getCodigo() + " " + materia + " NOTA: " + val);
//                                obs1 = obs1-Noquitadas;
//                            }
//                        }
//                        pfinal = val;
//                    } else if (nota.getSistema().getPromediofinal().equals("SU") && !obs.equals("Pierde")) {
//                        if (validaConPromedioGeneral) {
//                            try {
//                                Double valor = new Double(equivalenciaSupletorio(pgeneral, equivalenciasSuple) + "");
//                                if (val < valor && pgeneral < sumaPierde && val > 0) {
//                                    obs = "Pierde";
//                                    System.out.println("pierde SUPLE(1.0):" + matricula + " mat:" + materia + " not:" + val + " FALTA VER SUPLETORIO Y SUMATORIA");
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
//                            if (sumatoria < sumaAprueba && sumatoria > 0) {
//                                try {
//                                    Double valor = new Double(equivalenciaSupletorio(sumatoria, equivalenciasSuple) + "");
//                                    if (val < valor) {
//                                        obs = "Pierde";
//                                        System.out.println("pierde SUPLE(1.1):" + matricula + " mat:" + materia + " not:" + val);
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
//                    } else if (nota.getSistema().getPromediofinal().equals("SU")) {
//                        if (validaConPromedioGeneral && pgeneral < valorPromedioGeneral) {
//                            try {
//                                //si no llega a la nota minima
//                                if (val < valorPromedioGeneral) {
//                                    obs = "Pierde";
//                                    System.out.println("pierde SUPLE(2.0):" + matricula + " mat:" + materia + " not:" + val);
//                                    MateriaProfesor matR = new MateriaProfesor();
//                                    matR.setCodigomap(matriculaNo.getCodigomat());
//                                    matR.setOrden(materia.getCodigo());
//                                    materiasReprobadas.add(matR);
//                                    obs1++;
//                                } else {
//                                    Double valor = new Double(equivalenciaSupletorio(pgeneral, equivalenciasSuple) + "");
//                                    if (val < valor) {
//                                        obs = "Pierde";
//                                        System.out.println("pierde SUPLE(2.0):" + matricula + " mat:" + materia + " not:" + val);
//                                        MateriaProfesor matR = new MateriaProfesor();
//                                        matR.setCodigomap(matriculaNo.getCodigomat());
//                                        matR.setOrden(materia.getCodigo());
//                                        materiasReprobadas.add(matR);
//                                        obs1++;
//                                    } else {
//                                        obs = "";
//                                        MateriaProfesor matR = new MateriaProfesor();
//                                        matR.setCodigomap(matriculaNo.getCodigomat());
//                                        matR.setOrden(materia.getCodigo());
//                                        if (materiasReprobadas.contains(matR)) {
//                                            //materiasReprobadas.remove(matR);
//                                            System.out.println("ENVIO A QUITAR SU: " + materia);
//                                            quitarPaso(materiasReprobadas, matriculaNo.getCodigomat(), materia.getCodigo());
//                                            System.out.println("he quitado la materia como reprobada PASA SUPLE: " + materia.getCodigo() + " " + materia + " nota" + val);
//                                            obs1--;
//                                        }
//                                    }
//                                }
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            if (sumatoria < sumaAprueba && sumatoria > 0) {
//                                try {
//                                    Double valor = new Double(equivalenciaSupletorio(sumatoria, equivalenciasSuple) + "");
//                                    if (val < valor) {
//                                        obs = "Pierde";
//                                        System.out.println("pierde SUPLE(2.1):" + matricula + " mat:" + materia + " not:" + val);
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
//                    }else if (nota.getSistema().getPromediofinal().equals("RE")) {
//                        if (validaConPromedioGeneral && pgeneral < valorPromedioGeneral) {
//                            try {
//                           
//                                    Double valor = new Double(equivalenciaSupletorio(pgeneral, equivalenciasSuple) + "");
//                                    if (val < valor) {
//                                        obs = "Pierde";
//                                        System.out.println("pierde REMEDIAL(2.0):" + matricula + " mat:" + materia + " not:" + val);
//                                        MateriaProfesor matR = new MateriaProfesor();
//                                        matR.setCodigomap(matriculaNo.getCodigomat());
//                                        matR.setOrden(materia.getCodigo());
//                                        materiasReprobadas.add(matR);
//                                        obs1++;
//                                    } else {
//                                        obs = "";
//                                        MateriaProfesor matR = new MateriaProfesor();
//                                        matR.setCodigomap(matriculaNo.getCodigomat());
//                                        matR.setOrden(materia.getCodigo());
//                                        if (materiasReprobadas.contains(matR)) {
//                                            //materiasReprobadas.remove(matR);
//                                            System.out.println("ENVIO A QUITAR REM: " + materia);
//                                            quitarPaso(materiasReprobadas, matriculaNo.getCodigomat(), materia.getCodigo());
//                                            System.out.println("he quitado la materia como reprobada PASA REM: " + materia.getCodigo() + " " + materia + " nota" + val);
//                                            obs1--;
//                                        }
//                                    }
//                              
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            if (sumatoria < sumaAprueba && sumatoria > 0) {
//                                try {
//                                    Double valor = new Double(equivalenciaSupletorio(sumatoria, equivalenciasSuple) + "");
//                                    if (val < valor) {
//                                        obs = "Pierde";
//                                        System.out.println("pierde SUPLE(2.1):" + matricula + " mat:" + materia + " not:" + val);
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
//                    }else if (nota.getSistema().getPromediofinal().equals("GR")) { //fin remedial
//                        if (validaConPromedioGeneral && pgeneral < valorPromedioGeneral) {
//                            try {
//                           
//                                    Double valor = new Double(equivalenciaSupletorio(pgeneral, equivalenciasSuple) + "");
//                                    if (val> 0 && val < valor) {
//                                        obs = "Pierde";
//                                        System.out.println("pierde gracia(2.0):" + matricula + " mat:" + materia + " not:" + val);
//                                        MateriaProfesor matR = new MateriaProfesor();
//                                        matR.setCodigomap(matriculaNo.getCodigomat());
//                                        matR.setOrden(materia.getCodigo());
//                                        materiasReprobadas.add(matR);
//                                        obs1++;
//                                    } else {
//                                        obs = "";
//                                        MateriaProfesor matR = new MateriaProfesor();
//                                        matR.setCodigomap(matriculaNo.getCodigomat());
//                                        matR.setOrden(materia.getCodigo());
//                                        if (materiasReprobadas.contains(matR)) {
//                                            //materiasReprobadas.remove(matR);
//                                            System.out.println("ENVIO A QUITAR GRAC: " + materia);
////                                            quitarPaso(materiasReprobadas, matriculaNo.getCodigomat(), materia.getCodigo());
////                                            System.out.println("he quitado la materia como reprobada PASA GRAC: " + materia.getCodigo() + " " + materia + " nota" + val);
////                                            obs1--;
//                                            int Noquitadas = quitarPaso(materiasReprobadas, matriculaNo.getCodigomat(), materia.getCodigo());
//                                            System.out.println("PF..he quitado la materia como reprobada: " + materia.getCodigo() + " " + materia + " NOTA: " + val);
//                                            obs1 = obs1-Noquitadas;
//                                        }
//                                    }
//                              
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            if (sumatoria < sumaAprueba && sumatoria > 0) {
//                                try {
//                                    Double valor = new Double(equivalenciaSupletorio(sumatoria, equivalenciasSuple) + "");
//                                    if (val < valor) {
//                                        obs = "Pierde";
//                                        System.out.println("pierde SUPLE(2.1):" + matricula + " mat:" + materia + " not:" + val);
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
//                    }//fin gracia
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
//            if (matricula.contains("PARRA")) {
//                System.out.println("cambios ");
//            }
//
//            if (obs1 > 0) {
//                if (!aprobadMatriculas.contains(matriculaNo)) {
//                    aprobadMatriculas.add(matriculaNo);
//                }
//            }
//        }
//        nativo = null;
//        if (aprobadMatriculas.size() <= 0) {
//            materiasReprobadas = new ArrayList<MateriaProfesor>();
//          
//        }
//          for (Iterator<MateriaProfesor> it = materiasReprobadas.iterator(); it.hasNext();) {
//                MateriaProfesor mp = it.next();
//                Matriculas mat = (Matriculas)adm.buscarClave(mp.getCodigomap(),Matriculas.class);
//                Global gl = (Global)adm.buscarClave(mp.getOrden(),Global.class);
//                System.out.println("matricula"+mat.getEstudiante()+" "+gl.getDescripcion());
//                  //matR.setCodigomap(matriculaNo.getCodigomat());
//                    //matR.setOrden(materia.getCodigo());
//                    
//                
//            }
//         
//        return aprobadMatriculas;
//
//    }
    public List<Matriculas> cuadroverificar(Cursos curso, Sistemacalificacion sistema, Matriculas matriculaActual) {
//     int tamanio=0;
        Administrador adm = new Administrador();
//        Session ses = Sessions.getCurrent();
        Periodo periodo = curso.getPeriodo();
        materiasReprobadas = new ArrayList<MateriaProfesor>();
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        Double sumaPierde = regresaVariableParametrosDecimal("SUMATORIAPIERDE", parametrosGlobales);
        Double sumaAprueba = regresaVariableParametrosDecimal("SUMATORIAAPRUEBA", parametrosGlobales);
        Double valorPromedioGeneral = regresaVariableParametrosDecimal("VALORPROMEDIOGENERAL", parametrosGlobales);
        Boolean validaConPromedioGeneral = regresaVariableParametrosLogico("PROMEDIOGENERAL", parametrosGlobales);

        if (valorPromedioGeneral <= 0) {
            valorPromedioGeneral = sumaAprueba;
        }
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
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
        if (equivalenciasSuple.size() <= 0) {
            try {
                Messagebox.show("No existen los rangos de Supletorio, ingrese a Equivalencias > Supletorios y verifique que esté lleno...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String q = "";
        if (matriculaActual.getCodigomat().equals(new Integer(-1)) || matriculaActual.getCodigomat().equals(new Integer(-2))) {
            q = "Select codigomap, mat.codigomat,notas.materia, " + query + ", 'obs'  from notas, materia_profesor , matriculas mat, estudiantes est "
                    + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                    + "and materia_profesor.curso = '" + curso.getCodigocur() + "' "
                    + "and notas.promedia = true and notas.disciplina = false "
                    + "and notas.materia != 0 and notas.materia != 1 and materia_profesor.seimprime = true  "
                    + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "' and estado in  ('Matriculado','Recibir Pase')  ) "
                    + "  AND NOTAS.MATERIA NOT IN (SELECT CODIGO FROM GLOBAL WHERE GRUPO = 'MAT' AND DESCRIPCION LIKE '%PROME%')   "
                    + "order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";
        } else {
            q = "Select codigomap, mat.codigomat,notas.materia, " + query + ", 'obs'  from notas, materia_profesor , matriculas mat, estudiantes est "
                    + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                    + "and materia_profesor.curso = '" + curso.getCodigocur() + "' "
                    + "and notas.promedia = true and notas.disciplina = false "
                    + "and notas.materia != 0 and notas.materia != 1 and materia_profesor.seimprime = true  "
                    + "and matricula in (" + matriculaActual.getCodigomat() + ") "
                    + "  AND NOTAS.MATERIA NOT IN (SELECT CODIGO FROM GLOBAL WHERE GRUPO = 'MAT' AND DESCRIPCION LIKE '%PROME%')   "
                    + "order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";
        }


        System.out.println("cuadro final: " + q);
        List nativo = adm.queryNativo(q);
        int cont = 0;
        String matricula = "";
        List<Matriculas> aprobadMatriculas = new ArrayList<Matriculas>();
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            Matriculas matriculaNo = null;
            MateriaProfesor mprofesor = null;
            Global materia = null;
            Double sumatoria = 0.0;
            Double pfinal = 0.0;
            Double pgeneral = 0.0;
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

                    matricula = matriculaNo.toString();
                } else if (j >= 3) {
                    if (truncarNotas) {
                        val = truncar((Double) dos, 2);
                    } else {
                        val = redondear((Double) dos, 2);
                    }
                    matricula = matriculaNo.toString();
                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    if (nota.getSistema().getPromediofinal().equals("PG")) {
                        if (val < valorPromedioGeneral && validaConPromedioGeneral) {
                            obs = "Pierde";
                            System.out.println("pierde PG (1):" + matricula + " mat:" + materia + " not:" + val + " FALTA VERIFICAR SUPLE");
                            MateriaProfesor matR = new MateriaProfesor();
                            matR.setCodigomap(matriculaNo.getCodigomat());
                            matR.setOrden(materia.getCodigo());
                            materiasReprobadas.add(matR);
                            obs1++;
                        } else {
                            obs = "";
                        }
                        pgeneral = val;
                    } else if (nota.getSistema().getPromediofinal().equals("SM")) {
                        if (val < sumaPierde) {
                            obs = "Pierde";
                            System.out.println("pierde SM (1):" + matricula + " mat:" + materia + " not:" + val);
                            MateriaProfesor matR = new MateriaProfesor();
                            matR.setCodigomap(matriculaNo.getCodigomat());
                            matR.setOrden(materia.getCodigo());
                            materiasReprobadas.add(matR);
                            obs1++;
                        } else {
                            obs = "";
                        }
                        sumatoria = val;
                    } else if (nota.getSistema().getPromediofinal().equals("PF")) {
                        if (val < valorPromedioGeneral) {
                            obs = "Pierde";
                            System.out.println("pierde PF (1):" + matricula + " mat:" + materia + " not:" + val);
                            MateriaProfesor matR = new MateriaProfesor();
                            matR.setCodigomap(matriculaNo.getCodigomat());
                            matR.setOrden(materia.getCodigo());
                            materiasReprobadas.add(matR);
                            obs1++;
                        } else {
                            obs = "";
                            MateriaProfesor matR = new MateriaProfesor();
                            matR.setCodigomap(matriculaNo.getCodigomat());
                            matR.setOrden(materia.getCodigo());
                            if (materiasReprobadas.contains(matR)) {
                                //materiasReprobadas.remove(matR);
                                System.out.println("ENVIO A QUITAR PF: " + materia);
                                int Noquitadas = quitarPaso(materiasReprobadas, matriculaNo.getCodigomat(), materia.getCodigo());
                                System.out.println("PF..he quitado la materia como reprobada: " + materia.getCodigo() + " " + materia + " NOTA: " + val);
                                obs1 = obs1 - Noquitadas;
                            }
                        }
                        pfinal = val;
                    } else if (nota.getSistema().getPromediofinal().equals("SU") && !obs.equals("Pierde")) {
                        if (validaConPromedioGeneral) {
                            try {
                                Double valor = new Double(equivalenciaSupletorio(pgeneral, equivalenciasSuple) + "");
                                if (val < valor && pgeneral < sumaPierde && val > 0) {
                                    obs = "Pierde";
                                    System.out.println("pierde SUPLE(1.0):" + matricula + " mat:" + materia + " not:" + val + " FALTA VER SUPLETORIO Y SUMATORIA");
                                    MateriaProfesor matR = new MateriaProfesor();
                                    matR.setCodigomap(matriculaNo.getCodigomat());
                                    matR.setOrden(materia.getCodigo());
                                    materiasReprobadas.add(matR);
                                    obs1++;
                                } else {
                                    obs = "";
                                }
                            } catch (Exception e) {
                            }


                        } else {
                            if (sumatoria < sumaAprueba && sumatoria > 0) {
                                try {
                                    Double valor = new Double(equivalenciaSupletorio(sumatoria, equivalenciasSuple) + "");
                                    if (val < valor) {
                                        obs = "Pierde";
                                        System.out.println("pierde SUPLE(1.1):" + matricula + " mat:" + materia + " not:" + val);
                                        MateriaProfesor matR = new MateriaProfesor();
                                        matR.setCodigomap(matriculaNo.getCodigomat());
                                        matR.setOrden(materia.getCodigo());
                                        materiasReprobadas.add(matR);
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

                    } else if (nota.getSistema().getPromediofinal().equals("SU")) {
                        if (validaConPromedioGeneral && pgeneral < valorPromedioGeneral) {
                            try {
                                //si no llega a la nota minima
                                if (val < valorPromedioGeneral) {
                                    obs = "Pierde";
                                    System.out.println("pierde SUPLE(2.0):" + matricula + " mat:" + materia + " not:" + val);
                                    MateriaProfesor matR = new MateriaProfesor();
                                    matR.setCodigomap(matriculaNo.getCodigomat());
                                    matR.setOrden(materia.getCodigo());
                                    materiasReprobadas.add(matR);
                                    obs1++;
                                } else {
                                    Double valor = new Double(equivalenciaSupletorio(pgeneral, equivalenciasSuple) + "");
                                    if (val < valor) {
                                        obs = "Pierde";
                                        System.out.println("pierde SUPLE(2.0):" + matricula + " mat:" + materia + " not:" + val);
                                        MateriaProfesor matR = new MateriaProfesor();
                                        matR.setCodigomap(matriculaNo.getCodigomat());
                                        matR.setOrden(materia.getCodigo());
                                        materiasReprobadas.add(matR);
                                        obs1++;
                                    } else {
                                        obs = "";
                                        MateriaProfesor matR = new MateriaProfesor();
                                        matR.setCodigomap(matriculaNo.getCodigomat());
                                        matR.setOrden(materia.getCodigo());
                                        if (materiasReprobadas.contains(matR)) {
                                            //materiasReprobadas.remove(matR);
                                            System.out.println("ENVIO A QUITAR SU: " + materia);
                                            quitarPaso(materiasReprobadas, matriculaNo.getCodigomat(), materia.getCodigo());
                                            System.out.println("he quitado la materia como reprobada PASA SUPLE: " + materia.getCodigo() + " " + materia + " nota" + val);
                                            obs1--;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (sumatoria < sumaAprueba && sumatoria > 0) {
                                try {
                                    Double valor = new Double(equivalenciaSupletorio(sumatoria, equivalenciasSuple) + "");
                                    if (val < valor) {
                                        obs = "Pierde";
                                        System.out.println("pierde SUPLE(2.1):" + matricula + " mat:" + materia + " not:" + val);
                                        MateriaProfesor matR = new MateriaProfesor();
                                        matR.setCodigomap(matriculaNo.getCodigomat());
                                        matR.setOrden(materia.getCodigo());
                                        materiasReprobadas.add(matR);
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

                    } else if (nota.getSistema().getPromediofinal().equals("RE")) {
                        if (validaConPromedioGeneral && pgeneral < valorPromedioGeneral) {
                            try {

                                Double valor = new Double(equivalenciaSupletorio(pgeneral, equivalenciasSuple) + "");
                                if (val < valor) {
                                    obs = "Pierde";
                                    System.out.println("pierde REMEDIAL(2.0):" + matricula + " mat:" + materia + " not:" + val);
                                    MateriaProfesor matR = new MateriaProfesor();
                                    matR.setCodigomap(matriculaNo.getCodigomat());
                                    matR.setOrden(materia.getCodigo());
                                    materiasReprobadas.add(matR);
                                    obs1++;
                                } else {
                                    obs = "";
                                    MateriaProfesor matR = new MateriaProfesor();
                                    matR.setCodigomap(matriculaNo.getCodigomat());
                                    matR.setOrden(materia.getCodigo());
                                    if (materiasReprobadas.contains(matR)) {
                                        //materiasReprobadas.remove(matR);
                                        System.out.println("ENVIO A QUITAR REM: " + materia);
                                        quitarPaso(materiasReprobadas, matriculaNo.getCodigomat(), materia.getCodigo());
                                        System.out.println("he quitado la materia como reprobada PASA REM: " + materia.getCodigo() + " " + materia + " nota" + val);
                                        obs1--;
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (sumatoria < sumaAprueba && sumatoria > 0) {
                                try {
                                    Double valor = new Double(equivalenciaSupletorio(sumatoria, equivalenciasSuple) + "");
                                    if (val < valor) {
                                        obs = "Pierde";
                                        System.out.println("pierde SUPLE(2.1):" + matricula + " mat:" + materia + " not:" + val);
                                        MateriaProfesor matR = new MateriaProfesor();
                                        matR.setCodigomap(matriculaNo.getCodigomat());
                                        matR.setOrden(materia.getCodigo());
                                        materiasReprobadas.add(matR);
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

                    } else if (nota.getSistema().getPromediofinal().equals("GR")) { //fin remedial
                        if (validaConPromedioGeneral && pgeneral < valorPromedioGeneral) {
                            try {

                                Double valor = new Double(equivalenciaSupletorio(pgeneral, equivalenciasSuple) + "");
                                if (val > 0 && val < valor) {
                                    obs = "Pierde";
                                    System.out.println("pierde gracia(2.0):" + matricula + " mat:" + materia + " not:" + val);
                                    MateriaProfesor matR = new MateriaProfesor();
                                    matR.setCodigomap(matriculaNo.getCodigomat());
                                    matR.setOrden(materia.getCodigo());
                                    materiasReprobadas.add(matR);
                                    obs1++;
                                } else {
                                    obs = "";
                                    MateriaProfesor matR = new MateriaProfesor();
                                    matR.setCodigomap(matriculaNo.getCodigomat());
                                    matR.setOrden(materia.getCodigo());
                                    if (materiasReprobadas.contains(matR)) {
                                        //materiasReprobadas.remove(matR);
                                        System.out.println("ENVIO A QUITAR GRAC: " + materia);
//                                            quitarPaso(materiasReprobadas, matriculaNo.getCodigomat(), materia.getCodigo());
//                                            System.out.println("he quitado la materia como reprobada PASA GRAC: " + materia.getCodigo() + " " + materia + " nota" + val);
//                                            obs1--;
                                        int Noquitadas = quitarPaso(materiasReprobadas, matriculaNo.getCodigomat(), materia.getCodigo());
                                        System.out.println("PF..he quitado la materia como reprobada: " + materia.getCodigo() + " " + materia + " NOTA: " + val);
                                        obs1 = obs1 - Noquitadas;
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (sumatoria < sumaAprueba && sumatoria > 0) {
                                try {
                                    Double valor = new Double(equivalenciaSupletorio(sumatoria, equivalenciasSuple) + "");
                                    if (val < valor) {
                                        obs = "Pierde";
                                        System.out.println("pierde SUPLE(2.1):" + matricula + " mat:" + materia + " not:" + val);
                                        MateriaProfesor matR = new MateriaProfesor();
                                        matR.setCodigomap(matriculaNo.getCodigomat());
                                        matR.setOrden(materia.getCodigo());
                                        materiasReprobadas.add(matR);
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

                    }//fin gracia
                    ksis++;
                } else if (j == 1) {
                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
//                    if (matriculaNo.getCodigomat().equals(new Integer(1603))) {
//                        System.out.println("" + matriculaNo.getEstudiante());
                    obs = "";
                    obs1 = 0;
//                    }
                } else if (j == 0) {
//                    mprofesor = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                } else if (j == 2) {
                    materia = (Global) adm.buscarClave((Integer) dos, Global.class);
                }
                if (matriculaNo != null && j > 1) {
                    if (!matriculaNo.toString().equals(matricula)) {
                        cont++;
                        materiasReprobadas = new ArrayList<MateriaProfesor>();
                    }
                }
            }
            if (matricula.contains("PARRA")) {
                System.out.println("cambios ");
            }

            if (obs1 > 0) {
                if (!aprobadMatriculas.contains(matriculaNo)) {
                    aprobadMatriculas.add(matriculaNo);
                }
            }
        }
        nativo = null;
        if (aprobadMatriculas.size() <= 0) {
            materiasReprobadas = new ArrayList<MateriaProfesor>();

        }
        for (Iterator<MateriaProfesor> it = materiasReprobadas.iterator(); it.hasNext();) {
            MateriaProfesor mp = it.next();
            Matriculas mat = (Matriculas) adm.buscarClave(mp.getCodigomap(), Matriculas.class);
            Global gl = (Global) adm.buscarClave(mp.getOrden(), Global.class);
            System.out.println("matricula" + mat.getEstudiante() + " " + gl.getDescripcion());
            //matR.setCodigomap(matriculaNo.getCodigomat());
            //matR.setOrden(materia.getCodigo());


        }
        limpiarMemoria();
        return aprobadMatriculas;

    }

    public void limpiarMemoria() {
        System.gc();
        System.gc();
        System.gc();
        System.gc();
    }

    public JRDataSource cuadroresumen(Cursos curso, Sistemacalificacion sistema) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "and o.orden <= '" + sistema.getOrden() + "' "
                + "and o.seimprime = true and  o.ensupletorio = true  order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.orden  <= '" + sistema.getOrden() + "'  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true "
                + "and o.sistema.ensupletorio = true order by o.sistema.orden ");
        if (notas.size() <= 0) {
            try {
                Messagebox.show("No ha parametrizado el Impresiones para el Supletorio en Aportes (Impr.Supletorios)...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<Notanotas> notaFinal = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis = '" + sistema.getCodigosis() + "'  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true");
        if (notaFinal.size() <= 0) {
            try {
                Messagebox.show("No ha parametrizado el Promedio Final o General en Aportes...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
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
        if (equivalenciasSuple.size() <= 0) {
            try {
                Messagebox.show("No existen los rangos de Supletorio, ingrese a Equivalencias > Supletorios y verifique que esté lleno...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        Double sumaPierde = regresaVariableParametrosDecimal("SUMATORIAPIERDE", parametrosGlobales);
        Double sumaAprueba = regresaVariableParametrosDecimal("SUMATORIAAPRUEBA", parametrosGlobales);
        Boolean validaConPromedioGeneral = regresaVariableParametrosLogico("PROMEDIOGENERAL", parametrosGlobales);
        String query = "";
        for (Notanotas notass : notas) {

            if (truncarNotas) {
                query += "truncate(AVG(CAST(" + notass.getNota() + "  AS DECIMAL(9,2))),2),";
            } else {
                query += "round(AVG(CAST(" + notass.getNota() + "  AS DECIMAL(9,2))),2),";
            }


        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("()", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }

        String q = "Select codigomap, mat.codigomat,notas.materia, " + query + ", 'obs'  from notas, materia_profesor , matriculas mat, estudiantes est "
                + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                + "and materia_profesor.curso = '" + curso.getCodigocur() + "' "
                + " and notas.disciplina = false and materia_profesor.cuantitativa = true and materia_profesor.seimprime = true and notas.materia >1   AND materia_profesor.ministerio = TRUE   "
                + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "'  ) "
                + " GROUP BY matricula  order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";

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
            Boolean sn = false;
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
                    if (sn == true) {
                        nota.setNota("SN");
                    }
                    if (matriculaNo.getEstado().contains("Retir") || matriculaNo.getEstado().contains("Emitir")) {
                        nota.setNota("Ret.");
                    }

                    nota.setSistema(sistemp1);
                    nota.setAprovechamiento(aprovecha);
                    nota.setDisciplina(disciplina);
                    lisNotas.add(nota);
                } else if (j >= 3) {
                    if (truncarNotas) {
                        val = truncar(((BigDecimal) dos).doubleValue(), 2);
                    } else {
                        val = redondear(((BigDecimal) dos).doubleValue(), 2);
                    }

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
                    //int tamaSistema = sistemas.size() - 1;
                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    if (nota.getSistema().getPromediofinal().equals("SM")) {
                        if (val < sumaPierde && validaConPromedioGeneral == false) {
                            obs = "Pierde";
                        } else if (val < sumaAprueba) {
                            obs = "SP";
                        }
                        sumatoria = val;
                    } else if (nota.getSistema().getPromediofinal().equals("PG")) {
                        if (validaConPromedioGeneral) {
                            if (val < sumaAprueba && val < 5) {
                                obs = "RM";
                            } else if (val < sumaAprueba && val < 7) {
                                obs = "SP";
                            } else {
                                obs = "";
                            }
                            //sumatoria = val;
                        }
                    } else if (nota.getSistema().getPromediofinal().equals("SU") && !obs.equals("Pierde")) {
                        if (validaConPromedioGeneral == false) {
                            if (sumatoria < sumaAprueba) {
                                try {
                                    Double valor = new Double(equivalenciaSupletorio(sumatoria, equivalenciasSuple) + "");
                                    if (val < valor && val > 0.0) {
                                        obs = "Pierde";
                                    } else if (val == 0) {
                                        obs = "SP";
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
                    }
                    if (sn == false && val == 0 && nota.getSistema().getRequerida() == true) {
                        sn = true;
                    }
                    nota.setAprovechamiento(aprovecha);
                    nota.setDisciplina(disciplina);
                    lisNotas.add(nota);
                    ksis++;
                } else if (j == 1) {

                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                    if (matriculaNo.toString().contains("AYERVE MONTÚFAR")) {
                        System.out.println("..");
                    }
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

        ReporteNotasDataSource ds = null;
        ds = new ReporteNotasDataSource(lisNotas);
        limpiarMemoria();
        return ds;

    }

    public JRDataSource cuadrofinalsupletorio(Cursos curso, Sistemacalificacion sistema, Double desde, Double hasta, Boolean soloSupletoriados) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "and o.orden <= '" + sistema.getOrden() + "' "
                + "and o.seimprime = true and  o.ensupletorio = true  order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.orden  <= '" + sistema.getOrden() + "'  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true "
                + "and o.sistema.ensupletorio = true order by o.sistema.orden ");
        if (notas.size() <= 0) {
            try {
                Messagebox.show("No ha parametrizado el Impresiones para el Supletorio en Aportes (Impr.Supletorios)...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<Notanotas> notaFinal = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis = '" + sistema.getCodigosis() + "'  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true");
        if (notaFinal.size() <= 0) {
            try {
                Messagebox.show("No ha parametrizado el Promedio Final o General en Aportes...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
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
// List<Notanotas> notasRequeridas = adm.query("Select o from Notanotas as o "
//                    + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' "
//                    + " and o.sistema.requerida = true "
//                    + " order by o.sistema.orden ");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalenciasSuple = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'SU' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalenciasDisc = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        if (equivalenciasSuple.size() <= 0) {
            try {
                Messagebox.show("No existen los rangos de Supletorio, ingrese a Equivalencias > Supletorios y verifique que esté lleno...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        Double sumaPierde = regresaVariableParametrosDecimal("SUMATORIAPIERDE", parametrosGlobales);
        Double sumaAprueba = regresaVariableParametrosDecimal("SUMATORIAAPRUEBA", parametrosGlobales);
        Boolean validaConPromedioGeneral = regresaVariableParametrosLogico("PROMEDIOGENERAL", parametrosGlobales);
        Double valorPromedioGeneral = regresaVariableParametrosDecimal("VALORPROMEDIOGENERAL", parametrosGlobales);

        if (valorPromedioGeneral <= 0) {
            valorPromedioGeneral = sumaAprueba;
        }
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

        String q = "Select codigomap, mat.codigomat,notas.materia, " + query + ", 'obs'  from notas, materia_profesor , matriculas mat, estudiantes est "
                + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                + "and materia_profesor.curso = '" + curso.getCodigocur() + "' "
                + " and notas.disciplina = false and materia_profesor.seimprime = true  "
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
            Boolean sn = false;
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
                    if (sn == true) {
                        nota.setNota("SN");
                    }
                    if (matriculaNo.getEstado().contains("Retir") || matriculaNo.getEstado().contains("Emitir")) {
                        nota.setNota("Ret.");
                    }

                    nota.setSistema(sistemp1);
                    nota.setAprovechamiento(aprovecha);
                    nota.setDisciplina(disciplina);
                    lisNotas.add(nota);
                } else if (j >= 3) {
                    if (truncarNotas) {
                        val = truncar((Double) dos, 2);
                    } else {
                        val = redondear((Double) dos, 2);
                    }
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    nota.setContador(cont);
                    matricula = matriculaNo.toString();
                    //nota.setNota(val);

                    if (mprofesor.getCuantitativa() == false) {

                        //if(mprofesor.getMateria().getCodigo().equals(new Integer(0))){
                        if (materiaNo.getCodigo().intValue() == 0) {
                            nota.setNota(equivalencia(dos, equivalenciasDisc));
                        } else {
                            nota.setNota(equivalencia(dos, equivalencias));
                        }
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
                    //int tamaSistema = sistemas.size() - 1;
                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    if (nota.getSistema().getPromediofinal().equals("SM")) {
                        if (val < sumaPierde && validaConPromedioGeneral == false) {
                            obs = "Pierde";
                        } else if (val < sumaAprueba) {
                            obs = "SP";
                        }
                        sumatoria = val;
                    } else if (nota.getSistema().getPromediofinal().equals("PG")) {
                        if (validaConPromedioGeneral) {
                            if (val < valorPromedioGeneral && val < 5) {
                                obs = "RM";
                            } else if (val < valorPromedioGeneral && val < 7) {
                                obs = "SP";
                            } else {
                                obs = "";
                            }
                            //sumatoria = val;
                        }
                    } else if (nota.getSistema().getPromediofinal().equals("SU") && !obs.equals("Pierde")) {
                        if (validaConPromedioGeneral == false) {
                            if (sumatoria < sumaAprueba) {
                                try {
                                    Double valor = new Double(equivalenciaSupletorio(sumatoria, equivalenciasSuple) + "");
                                    if (val < valor && val > 0.0) {
                                        obs = "Pierde";
                                    } else if (val == 0) {
                                        obs = "SP";
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
                    }
                    if (sn == false && val == 0 && nota.getSistema().getRequerida() == true) {
                        sn = true;
                    }
                    nota.setAprovechamiento(aprovecha);
                    nota.setDisciplina(disciplina);
                    lisNotas.add(nota);
                    ksis++;
                } else if (j == 1) {

                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                    if (matriculaNo.toString().contains("AYERVE MONTÚFAR")) {
                        System.out.println("..");
                    }
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
        ArrayList<Matriculas> listaMatriculas = new ArrayList<Matriculas>();
        List<Nota> lisNotasArreglado = new ArrayList();
        if (soloSupletoriados) {
            for (Iterator<Nota> it = lisNotas.iterator(); it.hasNext();) {
                Nota nota = it.next();
                if (nota.getSistema().getNombre().equals("OBS")) {
                    if (nota.getNota().toString().contains("Pier") || nota.getNota().toString().contains("RM") || nota.getNota().toString().contains("SP") || nota.getNota().toString().contains("SN")) {
                        System.out.println("" + nota.getMatricula() + " " + nota.getMateria() + " " + nota.getNota());
                        listaMatriculas.add(nota.getMatricula());
                    }
                }
            }
            int contador = 1;
            int matriculaActual = 0;

            for (Iterator<Nota> it = lisNotas.iterator(); it.hasNext();) {
                Nota nota = it.next();
                if (listaMatriculas.contains(nota.getMatricula())) {
                    nota.setContador(contador);
                    int matriculaInt = nota.getMatricula().getCodigomat();
                    if (matriculaActual == 0) {
                        matriculaActual = matriculaInt;
                    } else if (matriculaActual == matriculaInt) {
                        nota.setContador(contador);
                    } else {
                        contador++;
                        matriculaActual = matriculaInt;
                        nota.setContador(contador);
                    }
                    lisNotasArreglado.add(nota);
                }

            }

        }

        ReporteNotasDataSource ds = null;
        if (soloSupletoriados) {
            ds = new ReporteNotasDataSource(lisNotasArreglado);
        } else {
            ds = new ReporteNotasDataSource(lisNotas);
        }
        return ds;

    }

    public JRDataSource cuadrocalificacionessupletorio(Cursos curso, Sistemacalificacion sistema, Double desde, Double hasta) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
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
/*
         * String q = "Select matricula,materia, "+query+" from notas " + "where
         * matricula in (select codigomat from matriculas where curso =
         * '"+curso.getCodigocur()+"' ) " + " ";
         */
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
                    if (truncarNotas) {
                        val = truncar((Double) dos, 2);
                    } else {
                        val = redondear((Double) dos, 2);
                    }

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
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
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
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }

        //2 666-882
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];
        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }
//        List<Cursos> cursos = adm.query("Select o from Cursos as o where o.periodo.codigoper = '" + sistema.getPeriodo().getCodigoper() + "' ");
        List<Nota> lisNotas = new ArrayList();
//        for (Iterator<Cursos> it = cursos.iterator(); it.hasNext();) {
//            Cursos curso = it.next();
        String q = "SELECT round(avg (" + query + "),2),mat.curso FROM notas, matriculas mat "
                + "WHERE  notas.matricula = mat.codigomat AND notas.promedia = TRUE   "
                + " AND notas.matricula IN (SELECT codigomat FROM matriculas "
                + "WHERE  curso  IN (SELECT codigocur FROM cursos WHERE periodo = '" + periodo.getCodigoper() + "' ) "
                + " AND  estado in ('Matriculado','Recibir Pase','Emitir Pase','Retirado')  )  "
                + " AND notas.disciplina = FALSE   "
                + " AND notas.promedia = TRUE  "
                + " AND notas.seimprime = TRUE  "
                + " GROUP BY mat.curso ORDER BY 1 DESC";
        if (truncarNotas) {
            q = "SELECT truncate(avg (" + query + "),2),mat.curso FROM notas, matriculas mat "
                    + "WHERE  notas.matricula = mat.codigomat AND notas.promedia = TRUE   "
                    + " AND notas.matricula IN (SELECT codigomat FROM matriculas "
                    + "WHERE  curso  IN (SELECT codigocur FROM cursos WHERE periodo = '" + periodo.getCodigoper() + "' ) "
                    + " AND  estado in ('Matriculado','Recibir Pase','Emitir Pase','Retirado')  )  "
                    + " AND notas.disciplina = FALSE   "
                    + " AND notas.promedia = TRUE  "
                    + " AND notas.seimprime = TRUE  "
                    + " GROUP BY mat.curso ORDER BY 1 DESC";
        }


        System.out.println("" + q);
        List nativo = adm.queryNativo(q);

        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            Matriculas matriculaNo = new Matriculas(-1);
            matriculaNo.setCurso((Cursos) adm.buscarClave(new Integer(vec.get(1) + ""), Cursos.class));
            Nota n = new Nota();
            n.setMatricula(matriculaNo);
            n.setNota((vec.get(0)));

            lisNotas.add(n);
        }
        nativo = null;

//        }
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
    public JRDataSource mejoresporcurso(Sistemacalificacion sistema, Integer numeroEstudiantes) {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
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

        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);

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
        List<Cursos> cursos = adm.query("Select o from Cursos as o "
                + " where o.periodo.codigoper = '" + sistema.getPeriodo().getCodigoper() + "'"
                + " order by o.secuencia, o.paralelo.descripcion  ");
        List<Nota> lisNotas = new ArrayList();
        for (Iterator<Cursos> it = cursos.iterator(); it.hasNext();) {
            Cursos curso = it.next();
            String comple = "round(avg (" + query + "),3) valor";
            if (truncarNotas) {
                comple = "truncate(avg (" + query + "),3) valor";
            }

            String q = "Select " + comple + ", est.apellido ,est.nombre  "
                    + "from notas, materia_profesor, matriculas mat, estudiantes est "
                    + "where notas.materia =  materia_profesor.materia and materia_profesor.curso = '" + curso.getCodigocur() + "'  "
                    + "  AND notas.materia > 0 AND  notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                    + "and matricula in (select codigomat from matriculas where   estado in ('Matriculado','Recibir Pase')  and    curso  =  '" + curso.getCodigocur() + "'  )"
                    + "and notas.disciplina = false and notas.promedia = true  GROUP BY notas.matricula "
                    + "order by 1 desc limit  " + numeroEstudiantes + " ";

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

    //Promedio por curso
    public JRDataSource mejoresporcurso2(Sistemacalificacion sistema) {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
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
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);

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
            String comple = "round(avg (" + query + "),3) valor";
            if (truncarNotas) {
                comple = "truncate(avg (" + query + "),3) valor";
            }

            String q = "Select " + comple + ", est.apellido ,est.nombre  "
                    + "from notas, materia_profesor, matriculas mat, estudiantes est "
                    + "where notas.materia =  materia_profesor.materia and materia_profesor.curso = '" + curso.getCodigocur() + "'  "
                    + "AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                    + "and matricula in (select codigomat from matriculas where  estado in ('Matriculado','Recibir Pase')  and   curso  =  '" + curso.getCodigocur() + "'  )"
                    + "and notas.disciplina = false and notas.promedia = true  GROUP BY notas.matricula "
                    + "order by 1 asc limit 5";
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

    public JRDataSource cuadroexamenes(Cursos curso, Boolean suspendidos) {
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
        String complemento2 = "";
        if (suspendidos != null) {
            complemento2 = " AND mat.suspenso = " + suspendidos + " ";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String q = "SELECT CONCAT(est.apellido,' ',est.nombre), " + query + "   FROM notasgrado notas,matriculas mat,estudiantes est "
                + "WHERE notas.matricula = mat.codigomat "
                + complemento2 + " AND mat.estudiante = est.codigoest AND mat.curso = '" + curso.getCodigocur() + "' order by 1";
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


                    if (((Materiasgrado) notas.get(ksis)).getEspromedio() || ((Materiasgrado) notas.get(ksis)).getEstrabajo()) {
                        String s = "##00.00";
                        DecimalFormat decimalFormat = new DecimalFormat(s);
                        //DecimalFormat formateador = new DecimalFormat("####.###");
                        // Esto sale en pantalla con cuatro decimales, es decir, 3,4324
//                        System.out.println("formato: " + decimalFormat.format(redondear((Double) dos, 3)));
                        nota.setNota(decimalFormat.format(redondear((Double) dos, 3)));
                    } else {
                        String s = "##00.00";
                        DecimalFormat decimalFormat = new DecimalFormat(s);
                        nota.setNota(decimalFormat.format(redondear((Double) dos, 2)));
                        //nota.setNota(redondear((Double) dos, 0));
                    }
                    nota.setProfesor(((Materiasgrado) notas.get(ksis)).getProfesor().getApellidos() + " " + ((Materiasgrado) notas.get(ksis)).getProfesor().getNombres() + " CC:" + ((Materiasgrado) notas.get(ksis)).getProfesor().getIdentificacion());
//                    nota.setCargo3(q);
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

    public JRDataSource cuadroexamenesEspecialidad(Global especialidad, Boolean suspendidos) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Cursos> listadoCursos = adm.query("Select o from Cursos as o "
                + " where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.secuencia = 13 "
                + " and o.especialidad.codigo = '" + especialidad.getCodigo() + "' ");
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        List<Nota> lisNotas = new ArrayList();
        int m = 0;
        for (Iterator<Cursos> it = listadoCursos.iterator(); it.hasNext();) {
            Cursos curso = it.next();
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
                    + "WHERE notas.matricula = mat.codigomat  "
                    + " AND mat.suspenso = " + suspendidos + " AND mat.estudiante = est.codigoest AND mat.curso = '" + curso.getCodigocur() + "' order by 1";
            System.out.println("" + q);
            List nativo = adm.queryNativo(q);

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
                        if (truncarNotas) {
                            nota.setNota(truncar((Double) dos, 2));
                        } else {
                            nota.setNota(redondear((Double) dos, 2));
                        }
                        if ((j + 1) == vec.size()) {
                            String s = "##00.00##";
                            DecimalFormat decimalFormat = new DecimalFormat(s);
                            //DecimalFormat formateador = new DecimalFormat("####.###");
                            // Esto sale en pantalla con cuatro decimales, es decir, 3,4324
//                        System.out.println("formato: " + decimalFormat.format(redondear((Double) dos, 3)));
                            if (truncarNotas) {
                                nota.setNota(decimalFormat.format(truncar((Double) dos, 3)));
                            } else {
                                nota.setNota(decimalFormat.format(redondear((Double) dos, 3)));
                            }
                        }
                        nota.setProfesor(((Materiasgrado) notas.get(ksis)).getProfesor().getApellidos() + " " + ((Materiasgrado) notas.get(0)).getProfesor().getNombres());
                        nota.setCurso(curso);
                        nota.setCargo1(estudiante);
                        nota.setCargo3("" + m);
                        lisNotas.add(nota);
                        ksis++;
                    } else if (j == 0) {
                        estudiante = dos.toString();//en seteo el nombre del estudiante
                        m++;
                    }
                }
                i++;
            }
            nativo = null;

        }
        Collections.sort(lisNotas);
        int num = 1;
        String anterior = "";
        for (Iterator<Nota> it = lisNotas.iterator(); it.hasNext();) {
            Nota nota = it.next();
            if (anterior.equals("")) {
                anterior = nota.getCargo3();
                nota.setCargo3(num + "");
            } else if (nota.getCargo3().equals(anterior)) {
                nota.setCargo3(num + "");
            } else {
                num++;
                anterior = nota.getCargo3();
                nota.setCargo3(num + "");
            }

        }
        ReporteExamenesDataSource ds = new ReporteExamenesDataSource(lisNotas);
        return ds;
        //return null;


    }

    public JRDataSource cuadrojuntasMinisterio(Cursos curso, Sistemacalificacion sistema, Double desde, Double hasta) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
//                "where o.periodo.codigoper = '"+periodo.getCodigoper()+"' and o.espromedio = true   order by o.orden ");
//        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
//                "where  o.sistema.periodo.codigoper = '"+periodo.getCodigoper()+"' and  o.sistema.espromedio = true  " +
//                "order by o.sistema.orden ");
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        Boolean esCualitativa = regresaVariableParametrosLogico("DISCCUALITATIVA", parametrosGlobales);
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "and o.promedioparcial = true  "
                + "and o.seimprime = true order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.promedioparcial = true  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true "
                + "order by o.sistema.orden ");

        List<Notanotas> notaFinal = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis = '" + sistema.getCodigosis() + "'  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "  and o.sistema.seimprime = true");
        if (notaFinal.size() <= 0) {
            try {
                Messagebox.show("No ha parametrizado el Promedio General en Aportes...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
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
        List<Equivalencias> equivalenciasDisc = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

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
                + "and notas.disciplina = false and notas.promedia = true  and materia_profesor.seimprime = true and materia_profesor.ministerio = true and notas.materia != 0   "
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
            String disciplinaS = "";
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
                    if (truncarNotas) {
                        val = truncar((Double) dos, 2);
                    } else {
                        val = redondear((Double) dos, 2);
                    }
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    nota.setContador(cont);
                    matricula = matriculaNo.toString();
                    //nota.setNota(val);
                    if (mprofesor.getCuantitativa() == false) {

                        try {
                            if (materiaNo.getCodigo().intValue() == 0) {
                                nota.setNota(equivalencia(dos, equivalenciasDisc));
                            } else {
                                nota.setNota(equivalencia(dos, equivalencias));
                            }
                        } catch (Exception e) {
                            System.out.println("NO HAY EQUIVALENCIAS DE DISCIPLINA..." + e);
                        }


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
                    nota.setDisciplinaS(disciplinaS);
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
                    valor = adm.queryNativo("SELECT CAST(IF(" + nfinal.getNota() + " is null,0," + nfinal.getNota() + ")as decimal (9,2)) FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND materia = 0 ");
//                    System.out.println("" + valor);
                    if (valor.size() > 0) {
                        disciplina = ((BigDecimal) (((Vector) valor.get(0)).get(0))).doubleValue();
                        disciplinaS = equivalencia(disciplina, equivalenciasDisc) + "";
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
        lisNotas = null;
        return ds;

    }

    public JRDataSource cuadrojuntas(Cursos curso, Sistemacalificacion sistema, Double desde, Double hasta) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
//                "where o.periodo.codigoper = '"+periodo.getCodigoper()+"' and o.espromedio = true   order by o.orden ");
//        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
//                "where  o.sistema.periodo.codigoper = '"+periodo.getCodigoper()+"' and  o.sistema.espromedio = true  " +
//                "order by o.sistema.orden ");
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
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
        List<Equivalencias> equivalenciasDisc = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

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
                + "and notas.disciplina = false and materia_profesor.seimprime = true  "
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
                    if (truncarNotas) {
                        val = truncar((Double) dos, 2);
                    } else {
                        val = redondear((Double) dos, 2);
                    }
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    nota.setContador(cont);
                    matricula = matriculaNo.toString();
                    //nota.setNota(val);
                    if (mprofesor.getCuantitativa() == false) {

                        try {
                            if (materiaNo.getCodigo().intValue() == 0) {
                                nota.setNota(equivalencia(dos, equivalenciasDisc));
                            } else {
                                nota.setNota(equivalencia(dos, equivalencias));
                            }
                        } catch (Exception e) {
                            System.out.println("NO HAY EQUIVALENCIAS DE DISCIPLINA..." + e);
                        }


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
        lisNotas = null;
        return ds;

    }

    public JRDataSource cuadrofinal(Cursos curso, Sistemacalificacion sistema, Double desde, Double hasta) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
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
        List<Equivalencias> equivalencias2 = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

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
                    if (truncarNotas) {
                        val = truncar((Double) dos, 2);
                    } else {
                        val = redondear((Double) dos, 2);
                    }
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    nota.setContador(cont);
                    matricula = matriculaNo.toString();
                    //nota.setNota(val);

                    if (mprofesor.getCuantitativa() == false) {

                        if (mprofesor.getMateria().getCodigo().equals(new Integer(0))) {
                            nota.setNota(equivalencia(dos, equivalencias2));
                        } else {
                            nota.setNota(equivalencia(dos, equivalencias));
                        }
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
        lisNotas = null;
        return ds;

    }

    public JRDataSource cuadrofinal3(Cursos curso, Sistemacalificacion sistema, Double desde, Double hasta) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        Boolean promCuantitativo = regresaVariableParametrosLogico("PROMCUAN", parametrosGlobales);
        Boolean discCuantitativo = regresaVariableParametrosLogico("DISCCUAN", parametrosGlobales);
        Boolean impPromedio = regresaVariableParametrosLogico("IMPPROM", parametrosGlobales);
        Boolean impDisciplina = regresaVariableParametrosLogico("IMPDISC", parametrosGlobales);

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
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'"
                + " and o.sistema.promediofinal = 'PF'   "
                + " ");
        if (notaFinal.size() <= 0) {
            try {
                Messagebox.show("No ha parametrizado el Promedio Final en Aportes...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<Notanotas> notaGeneral = adm.query("Select o from Notanotas as o "
                + "where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.promediofinal = 'PG' ");
        if (notaGeneral.size() <= 0) {
            try {
                Messagebox.show("No ha parametrizado el Promedio General en Aportes...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Notanotas nGeneral = notaGeneral.get(0);
        List<Matriculas> listaMatriculasPerdidos = cuadroverificar(curso, notaFinal.get(0).getSistema(), new Matriculas(-1));

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

//DIAS ASISTIDOS
//                List<Equivalencias> equivalenciasFaltas = adm.query("Select o from Equivalencias as o "
//                    + "where o.grupo = 'DI' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
//                List<Equivalencias> equivalenciasFaltasSoloDias = adm.query("Select o from Equivalencias as o "
//                                + "where o.grupo = 'DI' and  (o.esfj = TRUE OR o.esfi = TRUE)  "
//                                + "and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
//                if (equivalenciasFaltas.size() <= 0) {
//                    try {
//                        Messagebox.show("Ingrese primero a Adminsitración > Equivalencias y Seleccione cuales son FJ, FI...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
//                        return null;
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//                List<Dias> laborados = adm.query("Select o from Dias as o "
//                                + " where o.sistema.codigosis  = '" + sistema.getCodigosis() + "' ");

        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";

        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }

        List<Notanotas> notasRequeridas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + " and o.sistema.requerida = true "
                + " order by o.sistema.orden ");




        String q = "Select codigomap, mat.codigomat,notas.materia, " + query + "  from notas, materia_profesor , matriculas mat, estudiantes est "
                + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                + "and materia_profesor.curso = '" + curso.getCodigocur() + "' "
                + "and notas.promedia = true and notas.disciplina = false   and materia_profesor.seimprime = true  "
                + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "'  ) "
                + "order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";

//        System.out.println("cuadro final: " + q);
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
            Integer faltasFustificadas = 0;
            Integer faltasInjustificadas = 0;
            Integer diasLaborados = 0;
            String observacion = "";
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
                    if (truncarNotas) {
                        val = truncar((Double) dos, 2);
                    } else {
                        val = redondear((Double) dos, 2);
                    }
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    if (mprofesor.getCuantitativa() == false) {
                        nota.setNota(equivalencia(dos, equivalencias));
//                        nota.setNota("APROBADO");
                    } else {
                        nota.setNota(val.toString());
                        if (val == 0.0) {
                            nota.setNota("");
                        }
                    }
//                    if (val >= desde && val <= hasta) {
//                    } else {
//                        nota.setNota("");
//                    }
                    int tamaSistema = sistemas.size() - 1;
                    if (ksis == tamaSistema) {
                        nota.setMprofesor(mprofesor);
                    } else {
                        mprofesor1.getEmpleado().setApellidos("");
                        mprofesor1.getEmpleado().setNombres("");
                        mprofesor1.getEmpleado().setIdentificacion("");
                        nota.setMprofesor(mprofesor1);
                    }
                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    nota.setAprovechamiento(aprovecha);
                    nota.setContador(cont);
                    nota.setDisciplina(disciplina);
                    nota.setSumatoria(sumatoria);
                    nota.setDiasAsistidos(diasLaborados - faltasInjustificadas);
                    //verifico si es que tiene notas pendientes y las pongo en observación 
                    if (notaFinal.get(0).getSistema().getCodigosis().equals(nota.getSistema().getCodigosis())
                            && nota.getMatricula().getEstado().contains("Matriculado")) {
                        for (Iterator<Notanotas> it = notasRequeridas.iterator(); it.hasNext();) {
                            Notanotas notanotas = it.next();
                            List<Notas> notaBus = adm.query("Select o from Notas as o "
                                    + "where o." + notanotas.getNota() + " <=0  "
                                    + "and o.matricula.codigomat = '" + nota.getMatricula().getCodigomat() + "' "
                                    + "and o.materia.codigo = '" + nota.getMateria().getCodigo() + "' "
                                    + "and o.promedia = true and o.disciplina = false  "
                                    + " ");
                            if (notaBus.size() > 0) {
                                nota.setNota("SN");
                            }


                        }

                    }
                    if (notaGeneral.get(0).getSistema().getCodigosis().equals(nota.getSistema().getCodigosis())
                            && nota.getMatricula().getEstado().contains("Matriculado")) {
                        for (Iterator<Notanotas> it = notasRequeridas.iterator(); it.hasNext();) {
                            Notanotas notanotas = it.next();
                            List<Notas> notaBus = adm.query("Select o from Notas as o "
                                    + "where o." + notanotas.getNota() + " <=0  "
                                    + "and o.matricula.codigomat = '" + nota.getMatricula().getCodigomat() + "' "
                                    + "and o.materia.codigo = '" + nota.getMateria().getCodigo() + "' "
                                    + "and o.promedia = true and o.disciplina = false"
                                    + " ");
                            if (notaBus.size() > 0) {
                                nota.setNota("SN");
                            }


                        }

                    }
                    if (observacion.length() > 0) {
                        nota.setObservacion("SIN EXAMEN");
                    } else {
                        nota.setObservacion("APROBADO");
                        if (listaMatriculasPerdidos.contains(matriculaNo)) {
                            nota.setObservacion("REPROBADO");
                        }
                    }

                    matricula = matriculaNo.toString();
                    lisNotas.add(nota);
                    ksis++;
                } else if (j == 1) {
                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                    //System.out.println("SELECT CAST(AVG(" + nfinal.getNota() + ")as decimal (9,3)) FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND cuantitativa = TRUE AND disciplina = FALSE AND  promedia = TRUE AND materia > 1 AND  seimprime = TRUE GROUP BY MATRICULA ");
                    String queryProm = "SELECT CAST(AVG(" + nfinal.getNota() + ")as decimal (9,3)) FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND cuantitativa = TRUE AND disciplina = FALSE AND  promedia = TRUE AND materia > 1 AND  seimprime = TRUE GROUP BY MATRICULA ";

                    if (!promCuantitativo) {
                        queryProm = "SELECT CAST(AVG(" + nfinal.getNota() + ")as decimal (9)) FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND  disciplina = FALSE AND  promedia = TRUE AND materia > 1 AND  seimprime = TRUE GROUP BY MATRICULA ";
                    }
                    List valor = adm.queryNativo(queryProm);
                    if (valor.size() > 0) {
                        aprovecha = ((BigDecimal) (((Vector) valor.get(0)).get(0))).doubleValue();
                    }
                    valor = adm.queryNativo("SELECT CAST(IF(" + nfinal.getNota() + " is null,0," + nfinal.getNota() + ")as decimal (9,0)) FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND materia = 0 ");
                    if (valor.size() > 0) {
                        disciplina = ((BigDecimal) (((Vector) valor.get(0)).get(0))).doubleValue();
                    }
                    try {
//                        System.out.println("SELECT CAST(SUM(" + nfinal.getNota() + ")as decimal (9,3)) FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND cuantitativa = TRUE AND disciplina = FALSE AND  promedia = TRUE AND materia > 1 AND  seimprime = TRUE GROUP BY MATRICULA ");
                        valor = adm.queryNativo("SELECT CAST(SUM(" + nfinal.getNota() + ")as decimal (9,3)) FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND cuantitativa = TRUE AND disciplina = FALSE AND  promedia = TRUE AND materia > 1 AND  seimprime = TRUE GROUP BY MATRICULA ");
                        if (valor.size() > 0) {
                            sumatoria = ((BigDecimal) (((Vector) valor.get(0)).get(0))).doubleValue();
                        }
                    } catch (Exception e) {
                        System.out.println("ERRRO EN SUMATORIA: " + e);
                    }

                    if (matriculaNo.getEstado().contains("Matriculado")) {
                        for (Iterator<Notanotas> it = notasRequeridas.iterator(); it.hasNext();) {
                            Notanotas notanotas = it.next();
                            List<Notas> notaBus = adm.query("Select o from Notas as o "
                                    + "where o." + notanotas.getNota() + " <=0  "
                                    + "and o.matricula.codigomat = '" + matriculaNo.getCodigomat() + "' "
                                    + "and o.promedia = true and o.disciplina = false  "
                                    + " ");
                            if (notaBus.size() > 0) {
                                observacion = "SIN EXAMEN";
                            }
                        }
                    }

                    //aqui tengo que poner las faltas y los días laborados 
                    /**
                     * BUSCO LAS FLATA SI ASÍ LO REQUIEREN
                     */
//                       if (true) {
//                        //if (incluyedias) {
//                                /**
//                                 * AGREGRO LAS FALTAS AL REPORTE
//                                 */
//                                String query3 = "";
//                                int w = 1;
//
//                                for (int i = 0; i < equivalenciasFaltasSoloDias.size(); i++) {
//                                    query3 += "sum(nota" + w + "),";
//                                    w++;
//                                }
//                                query3 = query3.substring(0, query3.length() - 1);
//                                //IMPRIMO LAS FALTAS
//                                String qf = "Select " + query3 + "  from disciplina "
//                                        + "where matricula = '" + matriculaNo.getCodigomat() + "'  "
//                                        + "and sistema = '" + sistema.getCodigosis() + "' "
//                                        + " group by matricula ";
//                                //System.out.println(""+q);
//                                List nativoF = adm.queryNativo(qf);
//                                
//                                for (Iterator itnaF = nativoF.iterator(); itnaF.hasNext();) {
//                                    Vector vecF = (Vector) itnaF.next();
//
//                                    for (int jF = 0; jF < vecF.size(); jF++) {
//                                        Object dosF = vecF.get(jF);
//                                        Integer valF = new Integer(dosF.toString());
// 
//                                        if ((equivalenciasFaltasSoloDias.get(jF)).getEsfj()) {
//                                            faltasFustificadas = valF;
//                                        } else if ((equivalenciasFaltasSoloDias.get(jF)).getEsfi()) {
//                                            faltasInjustificadas = valF;
//                                        }
// 
//                                    }
//
//                                }
// 
//                                 //diasLaborados - faltasInjustificadas
//
//
//                            }
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
        lisNotas = null;
        return ds;

    }

    public JRDataSource libretas(Cursos curso, Matriculas matri, Sistemacalificacion sistema) throws InterruptedException {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        if (periodo == null) {
            periodo = matri.getCurso().getPeriodo();
        }
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        String firma1 = regresaVariableParametros("FIR1", parametrosGlobales);
        String cargo1 = regresaVariableParametros("CAR1", parametrosGlobales);
        String firma2 = regresaVariableParametros("FIR2", parametrosGlobales);
        String cargo2 = regresaVariableParametros("CAR2", parametrosGlobales);
        String firma3 = regresaVariableParametros("FIR3", parametrosGlobales);
        String cargo3 = regresaVariableParametros("CAR3", parametrosGlobales);
        Boolean validarPendientes = regresaVariableParametrosLogico("VALIDARPENDIENTES", parametrosGlobales);
        Boolean promCuantitativo = regresaVariableParametrosLogico("PROMCUAN", parametrosGlobales);
        Boolean discCuantitativo = regresaVariableParametrosLogico("DISCCUAN", parametrosGlobales);

        Boolean impPromedio = regresaVariableParametrosLogico("IMPPROM", parametrosGlobales);
        Boolean impDisciplina = regresaVariableParametrosLogico("IMPDISC", parametrosGlobales);
        Boolean impEquivalencias = regresaVariableParametrosLogico("IMPEQU", parametrosGlobales);


//DECIMALESDIS
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);


        List<Equivalencias> equivalenciasFaltas = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DI' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalenciasDisc = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
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
            if (truncarNotas) {
                query2 += "truncate(cast(avg(" + notass.getNota() + ") as decimal(9,4))," + numeroDecimales.intValue() + "),";
            } else {
                query2 += "round(cast(avg(" + notass.getNota() + ") as decimal(9,4))," + numeroDecimales.intValue() + "),";
            }
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
        if (validarPendientes) {
            buscarPendientes(curso.getCodigocur());
            //matriculadosListPendientes
        }

        List<Nota> lisNotas = new ArrayList();
        ArrayList lisNotasC = new ArrayList();
        List<Matriculas> matriculas = new ArrayList();
        if (matri.getCodigomat().equals(-2)) {
            matriculas = adm.query("Select o from Matriculas as o where  o.estado in ('Matriculado','Recibir Pase')  and o.curso.codigocur = '" + curso.getCodigocur() + "' order by o.estudiante.apellido ");

            for (Iterator<Matriculas> it1 = matriculadosListPendientes.iterator(); it1.hasNext();) {
                Matriculas matriculas2 = it1.next();
                matriculas.remove(matriculas2);//ELIMINO LOS ESTUDIANTES QUE ESTAN EN MATRICULADOSLISPENDIENTES DE MATRICULAS

            }

        } else {
            matriculas.add(matri);
            for (Iterator<Matriculas> it1 = matriculadosListPendientes.iterator(); it1.hasNext();) {
                Matriculas matriculas2 = it1.next();
                matriculas.remove(matriculas2);//ELIMINO LOS ESTUDIANTES QUE ESTAN EN MATRICULADOSLISPENDIENTES DE MATRICULAS

            }
            if (matriculas.size() <= 0) {
                Messagebox.show("El estudiante tiene rubros pendientes...! \n No se puede imprimir ", "[VALIDARPENDIENTES]Administrador Educativo..", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            }
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


                        if (cuantitativa == false || ((Sistemacalificacion) sistemas.get(ksis)).getEsequivalencia()) {
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
                if (!promCuantitativo) {
                    q = "Select matricula," + query2 + "  from notas "
                            + "where notas.matricula = '" + matriculas1.getCodigomat() + "' "
                            + "and notas.seimprime = true "
                            + "and notas.promedia = true "
                            + "and notas.disciplina = false "
                            + "and notas.cuantitativa = false and notas.materia != 0 "
                            + "group by matricula  ";
                }
                System.out.println("NOTAS DE promedio " + q);
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
                            if (!promCuantitativo || ((Sistemacalificacion) sistemas.get(ksis)).getEsequivalencia()) {

                                coll.setNota(equivalencia(((BigDecimal) dos).doubleValue(), equivalencias));
                                if (((Sistemacalificacion) sistemas.get(ksis)).getConsulta()) {
                                    coll.setNota("");
                                }

                            } else {
                                if (val == 0.0) {
                                    coll.setNota("");
                                }
                                if (((Sistemacalificacion) sistemas.get(ksis)).getConsulta()) {
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
                            if (!discCuantitativo || ((Sistemacalificacion) sistemas.get(ksis)).getEsequivalencia()) {
                                coll.setNota(equivalencia(((BigDecimal) dos).doubleValue(), equivalenciasDisc));
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
//                q = "Select " + query4 + ",sis.abreviatura from disciplina, sistemacalificacion  sis, trimestres tri "
//                        + "where matricula = '" + matriculas1.getCodigomat() + "' and sis.trimestre = tri.codigotrim   "
//                        + "and sis.orden <= '" + sistema.getOrden() + "'   AND sis.codigosis = disciplina.sistema  and sis.seimprime = true  "
//                        + "  group by sis.codigosis order by  tri.codigotrim, sis.orden "
//                        + " ";
//                
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


            //IMPRIMO EL CUADRO DE EQUIVALENCIAS DE FALTAS

            ArrayList lisAutoevaluacion = new ArrayList();

            try {
                //query = query3.substring(0, query3.length() - 1);
            } catch (Exception e) {
                //Messagebox.show("No existen EQUIVALENCIAS \n Revise ADMINISTRACION > PARAMETROS > EQUIVALENCIAS >> DISCIPLINA", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                //System.out.println("************** LINEA: 1276: REPORTECLASE ERROR NO HAY EQUIVALENCIAS PARAMETRIZADAS" + e);
                //return null;
            }

            //IMPRIMO LAS FALTAS
            q = "Select o from Resultadoperfil as o where  "
                    + "  o.matricula.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "and o.periodo.orden <= '" + sistema.getOrden() + "'   "
                    + " order by o.codigo "
                    + " ";
//                System.out.println(""+q);
            List qNormal = adm.query(q);
            for (Iterator it = qNormal.iterator(); it.hasNext();) {
                Resultadoperfil object = (Resultadoperfil) it.next();
                NotaCollection coll = new NotaCollection();
                coll.setNota(object.getValor());
                coll.setMateria(object.getPerfil().getNombre());
                coll.setMatricula("" + matriculas1.getCodigomat());
                coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                coll.setSistema("" + object.getPeriodo().getAbreviatura());
                coll.setTipo("" + object.getPeriodo().getTrimestre().getDescripcion());
                lisAutoevaluacion.add(coll);

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

            List recomenList = adm.query("Select o from Recomendaciones as o "
                    + " where o.sistema.orden <= " + sistema.getOrden() + "  "
                    + " and o.matricula.codigomat = '" + matriculas1.getCodigomat() + "' order by o.sistema.orden ");
            if (recomenList.size() > 0) {
                for (Iterator it = recomenList.iterator(); it.hasNext();) {
                    Recomendaciones object = (Recomendaciones) it.next();
                    nota.setObservacion(nota.getObservacion() + "; " + object.getSistema().getAbreviatura() + ": " + object.getRecomendacion());
                    nota.setObservacion(nota.getObservacion().replace("null", ""));
                }


            } else {
                nota.setObservacion("");
            }

            nota.setMatricula(matriculas1);
            nota.setNotas(lisNotasC);
            nota.setFaltas(lisFaltas);
            nota.setAutoevaluacion(lisAutoevaluacion);
            lisNotas.add(nota);


            lisNotasC = new ArrayList();
        }


        ReporteNoasLibretaDataSource ds = new ReporteNoasLibretaDataSource(lisNotas);
        lisNotas = null;
        return ds;

    }
    List matriculadosListPendientes = new ArrayList();

    void buscarPendientes(Integer curso) {
        Administrador adm = new Administrador();
        Date fechaActual = adm.Date();
        String fec = (fechaActual.getYear() + 1900) + "-" + (fechaActual.getMonth() + 1) + "-" + (fechaActual.getDate()) + "";
        List pendientesEncontrados = adm.query("Select o from Asignados  as o "
                + " where o.matricula.curso.codigocur = '" + curso + "'  "
                + " and o.fechai <= '" + fec + "' and o.estado = true order by o.fechai");
        matriculadosListPendientes = new ArrayList();
        for (int i = 0; i < pendientesEncontrados.size(); i++) {
            Asignados asi = (Asignados) pendientesEncontrados.get(i);
            DateMidnight inicial = new DateMidnight(asi.getFechai());
            inicial = inicial.plusDays(10);
            DateMidnight finale = new DateMidnight(fechaActual);
            if (inicial.compareTo(finale) > 0) {
            } else {
                matriculadosListPendientes.add(asi.getMatricula());
            }
        }

    }

    public JRDataSource libretasInforme(Cursos curso, Matriculas matri, Sistemacalificacion sistema) throws InterruptedException {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        if (periodo == null) {
            periodo = matri.getCurso().getPeriodo();
        }
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
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
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);

//DECIMALESDIS
        List<MateriaProfesor> materiaProfesores = adm.query("Select o from MateriaProfesor as o "
                + "where o.curso.codigocur = '" + curso.getCodigocur() + "'  ");

        List<Equivalencias> equivalenciasFaltas = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DI' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalenciasDisc = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + " and o.seimprime = true and o.orden = '" + sistema.getOrden() + "' "
                + " and o.trimestre.codigotrim = '" + sistema.getTrimestre().getCodigotrim() + "' order by o.orden ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.orden =  '" + sistema.getOrden() + "'"
                + " and o.sistema.seimprime = true  "
                + " and o.sistema.trimestre.codigotrim = '" + sistema.getTrimestre().getCodigotrim() + "'   order by o.sistema.orden ");
        if (notas.size() <= 0) {
            try {
                Messagebox.show("No hay nada que imprimir...! \n Revise en la pantalla Aportes si existen notas a imprimir", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
//        List<Sistemaevaluacion> sisEvaluaciones = adm.query("Select o from Sistemaevaluacion as o "
//                + " where o.sistemacalificacion.codigosis = '" + sistema.getCodigosis() + "' "
//                + " and o.espromedio = true ");
//        String notaQuery = "";
//        if (sisEvaluaciones.size() > 0) {
//            for (Sistemaevaluacion notass : sisEvaluaciones) {
//                notaQuery += notass.getNombre() + ",";
//            }
//            notaQuery = notaQuery.substring(0, notaQuery.length() - 1).replace("'", "").replace("(", "").replace(")", "");
//        } else {
//            Messagebox.show("No hay nada que imprimir...! \n Seleccion otro APORTE o BLOQUE", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
//            return null;
//        }
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
            if (truncarNotas) {
                query2 += "truncate(cast(avg(" + notass.getNota() + ") as decimal(9,4))," + numeroDecimales.intValue() + "),";
            } else {
                query2 += "round(cast(avg(" + notass.getNota() + ") as decimal(9,4))," + numeroDecimales.intValue() + "),";
            }
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
            String recomendacion = "";
            String plan = "";

            /*
             * aumentadio
             */

//            String que = "Select o.matricula,g.codigo,o.cuantitativa, " + notaQuery + " from Notasevaluacion as o, Global as g "
//                    + "where o.materia = g.codigo and o.sistemacalificacion = '" + sistema.getCodigosis() + "'  "
//                    + "and o.matricula = '" + matriculas1.getCodigomat() + "' "
//                    + " and o.materia in (Select x.materia from materia_profesor as x "
//                    + " where x.curso = '" + curso.getCodigocur() + "' and x.seimprime = true ) "
//                    + " order by g.descripcion ";
//            System.out.println("" + que);

//            List noEncontradas = adm.queryNativo(que);
            //sisEvaluaciones
            /*
             * if (noEncontradas.size() > 0) {
             *
             * for (Iterator it1 = noEncontradas.iterator(); it1.hasNext();) {
             * Vector vecti = (Vector) it1.next();
             *
             * Matriculas matriculaNo = null; Boolean cuantitativa = false;
             * Global mate = new Global(); int ksis = 0; int numerar = -20;
             *
             * for (int j = 0; j < vecti.size(); j++) { Object dos =
             * vecti.get(j); Double val = 0.0; NotaCollection coll2 = new
             * NotaCollection(); //val = (Double) dos; coll2.setNota(dos); try {
             * if (dos.equals(null)) { dos = new Double(0.0); } } catch
             * (Exception e) { dos = new Double(0.0); //
             * System.out.println(""+e); } if (j >= 3) { val = (Double) dos;
             * coll2.setNota(dos);
             *
             *
             * if (cuantitativa == false || ((Sistemaevaluacion)
             * sisEvaluaciones.get(ksis)).getEsequivalencia()) {
             * coll2.setNota(equivalencia(dos, equivalencias)); } else { // if
             * (val == 0.0) { // coll2.setNota(""); // } }
             * coll2.setOrden((buscarOrden(materiaProfesores, mate)));
             * coll2.setMateria(mate.getDescripcion()); coll2.setMatricula("" +
             * matriculaNo.getCodigomat()); //
             * coll2.setRecomendacion(recomendacion); // coll2.setPlan(plan); //
             * coll2.setEquivalencia(equivalencia2(dos, equivalencias));
             * coll2.setEstudiante(matriculaNo.getEstudiante().getApellido() + "
             * " + matriculaNo.getEstudiante().getNombre());
             * coll2.setSistema("-" + (ksis) + "" + ((Sistemaevaluacion)
             * sisEvaluaciones.get(ksis)).getEvaluacion().getAbreviatura());
             * coll2.setTipo(((Sistemaevaluacion)
             * sisEvaluaciones.get(ksis)).getSistemacalificacion().getTrimestre().getDescripcion()
             * + " - " + sistema.getNombre()); System.out.println("" +
             * coll2.getNota() + " s:" + coll2.getSistema());
             * lisNotasC.add(coll2);
             *
             * ksis++; numerar++; } else if (j >= 2) {
             * //System.out.println(""+dos); cuantitativa = (Boolean) dos; }
             * else if (j >= 1) { mate = (Global) adm.buscarClave((Integer) dos,
             * Global.class); // try { // List<Recomendaciones> rec =
             * adm.query(" Select o from Recomendaciones as o" // + " where
             * o.matricula.codigomat = '" + matriculaNo.getCodigomat() + "' " //
             * + " and o.sistema.codigosis = '" + sistema.getCodigosis() + "' "
             * // + " and o.materia.codigo = '" + mate.getCodigo() + "' "); //
             * if (rec.size() > 0) { // recomendacion =
             * rec.get(0).getRecomendacion(); // plan = rec.get(0).getPlan(); //
             * } else { // recomendacion = ""; // plan = ""; // } // } catch
             * (Exception e) { // System.out.println("recomendaciones error: " +
             * e); // }
             *
             * } else { matriculaNo = (Matriculas) adm.buscarClave((Integer)
             * dos, Matriculas.class);
             *
             * }
             * }
             *
             * }
             * }
             */
            /*
             * aumentadio
             */


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


                        if (cuantitativa == false || ((Sistemacalificacion) sistemas.get(ksis)).getEsequivalencia()) {
                            coll.setNota(equivalencia(dos, equivalencias));
                        } else {
                            if (val == 0.0) {
                                coll.setNota("");
                            }
                        }
                        coll.setEquivalencia(equivalencia2(dos, equivalencias));
                        coll.setOrden((buscarOrden(materiaProfesores, mate)));
                        coll.setMateria(mate.getDescripcion());
                        coll.setMatricula("" + matriculaNo.getCodigomat());
                        coll.setEstudiante(matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre());
                        coll.setSistema(((Sistemacalificacion) sistemas.get(ksis)).getAbreviatura());
                        coll.setRecomendacion(recomendacion);
                        coll.setPlan(plan);
                        coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion() + " - " + ((Sistemacalificacion) sistemas.get(ksis)).getNombre());
                        lisNotasC.add(coll);

                        ksis++;
                    } else if (j >= 2) {
                        //System.out.println(""+dos);
                        cuantitativa = (Boolean) dos;
                    } else if (j >= 1) {
                        mate = (Global) adm.buscarClave((Integer) dos, Global.class);
                        try {
                            List<Recomendaciones> rec = adm.query(" Select o from Recomendaciones as o"
                                    + " where o.matricula.codigomat = '" + matriculaNo.getCodigomat() + "' "
                                    + " and o.sistema.codigosis = '" + sistema.getCodigosis() + "' "
                                    + " and o.materia.codigo = '" + mate.getCodigo() + "' ");
                            if (rec.size() > 0) {
                                recomendacion = rec.get(0).getRecomendacion();
                                plan = rec.get(0).getPlan();
                            } else {
                                recomendacion = "";
                                plan = "";
                            }
                        } catch (Exception e) {
                            System.out.println("recomendaciones error: " + e);
                        }

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
                if (!promCuantitativo) {
                    q = "Select matricula," + query2 + "  from notas "
                            + "where notas.matricula = '" + matriculas1.getCodigomat() + "' "
                            + "and notas.seimprime = true "
                            + "and notas.promedia = true "
                            + "and notas.disciplina = false "
                            + "and notas.cuantitativa = false and notas.materia != 0 "
                            + "group by matricula  ";
                }
//                System.out.println("NOTAS DE promedio " + q);
                nativo = null;
                nativo = adm.queryNativo(q);
                for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();

                    Boolean cuantitativa = false;
                    Global mate = new Global();
                    mate.setCodigo(0);
                    mate.setDescripcion("98.PROMEDIO");
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
                            if (!promCuantitativo || ((Sistemacalificacion) sistemas.get(ksis)).getEsequivalencia()) {
                                coll.setNota(equivalencia(((BigDecimal) dos).doubleValue(), equivalencias));
                            } else {
                                if (val == 0.0) {
                                    coll.setNota("");
                                }
                            }
                            promedioFinal = dos;
                            coll.setOrden(100);
                            coll.setMateria(mate.getDescripcion());
                            coll.setMatricula("" + matriculas1.getCodigomat());
                            coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                            coll.setSistema(((Sistemacalificacion) sistemas.get(ksis)).getAbreviatura());
                            coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion() + " - " + ((Sistemacalificacion) sistemas.get(ksis)).getNombre());
                            coll.setPlan("");
                            coll.setRecomendacion("");
                            coll.setEquivalencia(equivalencia2(val, equivalencias));
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
                    mate.setDescripcion("99.DISCIPLINA");
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
                            if (!discCuantitativo || ((Sistemacalificacion) sistemas.get(ksis)).getEsequivalencia()) {
                                coll.setNota(equivalencia(((BigDecimal) dos).doubleValue(), equivalenciasDisc));
                            } else {
                                if (val == 0.0) {
                                    coll.setNota("");
                                }
                            }
                            disciplinaFinal = dos;
                            coll.setOrden(101);
                            coll.setMateria(mate.getDescripcion());
                            coll.setMatricula("" + matriculas1.getCodigomat());
                            coll.setPlan("");
                            coll.setRecomendacion("");
                            coll.setEquivalencia(equivalencia2(val, equivalenciasDisc));
                            coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                            coll.setSistema(((Sistemacalificacion) sistemas.get(ksis)).getAbreviatura());
                            coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion() + " - " + ((Sistemacalificacion) sistemas.get(ksis)).getNombre());
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
                q = "Select " + query4 + ",sis.abreviatura from disciplina, sistemacalificacion  sis, trimestres tri "
                        + "where matricula = '" + matriculas1.getCodigomat() + "' and sis.trimestre = tri.codigotrim   "
                        + "and sis.orden <= '" + sistema.getOrden() + "'   AND sis.codigosis = disciplina.sistema  and sis.seimprime = true  "
                        + "  group by sis.codigosis order by  tri.codigotrim, sis.orden "
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


            //IMPRIMO EL CUADRO DE EQUIVALENCIAS DE FALTAS

            ArrayList lisAutoevaluacion = new ArrayList();

            try {
                //query = query3.substring(0, query3.length() - 1);
            } catch (Exception e) {
                //Messagebox.show("No existen EQUIVALENCIAS \n Revise ADMINISTRACION > PARAMETROS > EQUIVALENCIAS >> DISCIPLINA", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                //System.out.println("************** LINEA: 1276: REPORTECLASE ERROR NO HAY EQUIVALENCIAS PARAMETRIZADAS" + e);
                //return null;
            }

            //IMPRIMO LAS FALTAS
            q = "Select o from Resultadoperfil as o where  "
                    + "  o.matricula.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "and o.periodo.codigosis <= '" + sistema.getOrden() + "'   "
                    + " order by o.codigo "
                    + " ";
//                System.out.println(""+q);
            List qNormal = adm.query(q);
            for (Iterator it = qNormal.iterator(); it.hasNext();) {
                Resultadoperfil object = (Resultadoperfil) it.next();
                NotaCollection coll = new NotaCollection();
                coll.setNota(object.getValor());
                coll.setMateria(object.getPerfil().getNombre());
                coll.setMatricula("" + matriculas1.getCodigomat());
                coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                coll.setSistema("" + object.getPeriodo().getAbreviatura());
                coll.setTipo("" + object.getPeriodo().getTrimestre().getDescripcion());
                lisAutoevaluacion.add(coll);

            }

            nota.setFirma1(firma1);
            nota.setFirma2(firma2);
            nota.setFirma3(firma3);
            nota.setCargo1(cargo1);
            nota.setCargo2(cargo2);
            nota.setCargo3(cargo3);
            List recomenList = adm.query("Select o from Recomendaciones as o "
                    + " where o.sistema.codigosis = '" + sistema.getCodigosis() + "'  "
                    + " and o.matricula.codigomat = '" + matriculas1.getCodigomat() + "' ");
            if (recomenList.size() > 0) {
                for (Iterator it = recomenList.iterator(); it.hasNext();) {
                    Recomendaciones object = (Recomendaciones) it.next();
                    nota.setObservacion(nota.getObservacion() + "; " + object.getRecomendacion());
                    nota.setObservacion(nota.getObservacion().replace("null", ""));
                }


            } else {
                nota.setObservacion("");
            }


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
            nota.setAutoevaluacion(lisAutoevaluacion);
            lisNotas.add(nota);


            lisNotasC = new ArrayList();
        }


        ReporteNoasLibretaDataSource ds = new ReporteNoasLibretaDataSource(lisNotas);

        return ds;

    }

    public JRDataSource faltas(Cursos curso, Matriculas matri, Sistemacalificacion sistema) throws InterruptedException {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        if (periodo == null) {
            periodo = matri.getCurso().getPeriodo();
        }
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
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
        List<Equivalencias> equivalenciasDisc = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
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
//        String query2 = "";
        String queryDisciplina = "";
        Double numeroDecimales = regresaVariableParametrosDecimal("DECIMALESPRO", parametrosGlobales);
        Double numeroDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
        //DECIMALESDIS
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
//        for (Notanotas notass : notas) {
//            query2 += "round(cast(avg(" + notass.getNota() + ") as decimal(9,4))," + numeroDecimales.intValue() + "),";
//        }
        for (Notanotas notass : notas) {
            queryDisciplina += "cast(round(cast(avg(" + notass.getNota() + ") as decimal(9,4))," + numeroDecimalesDisc.intValue() + ") as decimal(9,4)),";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
//        query2 = query2.substring(0, query2.length() - 1).replace("'", "");
        queryDisciplina = queryDisciplina.substring(0, queryDisciplina.length() - 1).replace("'", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }

        List<Matriculas> matriculas = new ArrayList();
        if (matri.getCodigomat().equals(-2)) {
            matriculas = adm.query("Select o from Matriculas as o where  o.estado in ('Matriculado','Recibir Pase')  and o.curso.codigocur = '" + curso.getCodigocur() + "' order by o.estudiante.apellido ");
        } else {
            matriculas.add(matri);
        }
        ArrayList lisFaltas = new ArrayList();
        for (Matriculas matriculas1 : matriculas) {
            //IMPRIMO EL CUADRO DE EQUIVALENCIAS DE FALTAS


            String query3 = "";
            String query4 = "";

            int w = 1;
            for (int i = 0; i < equivalenciasFaltas.size(); i++) {
                query3 += "sum(nota" + w + "),";
                query4 += "sum(nota" + w + "),";
                w++;
            }
            query3 = query3.substring(0, query3.length() - 1);
            query4 = query4.substring(0, query4.length() - 1);
            //IMPRIMO LAS FALTAS
//               String q = "Select " + query4 + ", tri.descripcion from disciplina, sistemacalificacion  sis, trimestres tri "
//                        + "where matricula = '" + matriculas1.getCodigomat() + "' and sis.trimestre = tri.codigotrim   "
//                        + "and sis.orden <= '" + sistema.getOrden() + "'   AND sis.codigosis = disciplina.sistema  and sis.seimprime = true  "
//                        + "  group by tri.codigotrim  order by  tri.codigotrim, sis.orden "
//                        + " ";
            String q = "Select " + query4 + ",sis.abreviatura from disciplina, sistemacalificacion  sis, trimestres tri "
                    + "where matricula = '" + matriculas1.getCodigomat() + "' and sis.trimestre = tri.codigotrim   "
                    + "and sis.orden <= '" + sistema.getOrden() + "'   AND sis.codigosis = disciplina.sistema  and sis.seimprime = true  "
                    + "  group by sis.codigosis order by  tri.codigotrim, sis.orden "
                    + " ";
//                
            System.out.println("" + q);
            List nativo = adm.queryNativo(q);
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                int ksis = 0;
                for (int j = 0; j < vec.size() - 1; j++) {
                    Object dos = vec.get(j);
                    NotaCollection coll = new NotaCollection();
                    coll.setNota(dos);
                    coll.setMateria(equivalenciasFaltas.get(ksis).getAbreviatura());
                    coll.setMatricula("" + matriculas1.getCodigomat());
                    coll.setMatriculas(matriculas1);
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
                    coll.setMateria(equivalenciasFaltas.get(ksis).getAbreviatura());
                    coll.setMatricula("" + matriculas1.getCodigomat());
                    coll.setMatriculas(matriculas1);
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
                    coll.setMateria(equivalenciasFaltas.get(i).getAbreviatura());
                    coll.setMatricula("" + matriculas1.getCodigomat());
                    coll.setMatriculas(matriculas1);
                    coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                    coll.setSistema("Totales");
                    lisFaltas.add(coll);

                }
                //row.setParent(this);

            }


        }


        ReporteFaltasDataSource ds = new ReporteFaltasDataSource(lisFaltas);
//        lisNotas = null;
        return ds;

    }

    public JRDataSource faltasM(Cursos curso, Matriculas matri, Sistemacalificacion sistema) throws InterruptedException {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        if (periodo == null) {
            periodo = matri.getCurso().getPeriodo();
        }
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
//        String firma1 = regresaVariableParametros("FIR1", parametrosGlobales);
//        String cargo1 = regresaVariableParametros("CAR1", parametrosGlobales);
//        String firma2 = regresaVariableParametros("FIR2", parametrosGlobales);
//        String cargo2 = regresaVariableParametros("CAR2", parametrosGlobales);
//        String firma3 = regresaVariableParametros("FIR3", parametrosGlobales);
//        String cargo3 = regresaVariableParametros("CAR3", parametrosGlobales);
//        Boolean promCuantitativo = regresaVariableParametrosLogico("PROMCUAN", parametrosGlobales);
//        Boolean discCuantitativo = regresaVariableParametrosLogico("DISCCUAN", parametrosGlobales);
//
//        Boolean impPromedio = regresaVariableParametrosLogico("IMPPROM", parametrosGlobales);
//        Boolean impDisciplina = regresaVariableParametrosLogico("IMPDISC", parametrosGlobales);
//        Boolean impEquivalencias = regresaVariableParametrosLogico("IMPEQU", parametrosGlobales);


//DECIMALESDIS


        List<Equivalencias> equivalenciasFaltas = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DI' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalenciasDisc = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
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
//        String query2 = "";
        String queryDisciplina = "";
        Double numeroDecimales = regresaVariableParametrosDecimal("DECIMALESPRO", parametrosGlobales);
        Double numeroDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
        //DECIMALESDIS
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }

//        for (Notanotas notass : notas) {
//            query2 += "round(cast(avg(" + notass.getNota() + ") as decimal(9,4))," + numeroDecimales.intValue() + "),";
//        }
        for (Notanotas notass : notas) {
            queryDisciplina += "cast(round(cast(avg(" + notass.getNota() + ") as decimal(9,4))," + numeroDecimalesDisc.intValue() + ") as decimal(9,4)),";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
//        query2 = query2.substring(0, query2.length() - 1).replace("'", "");
        queryDisciplina = queryDisciplina.substring(0, queryDisciplina.length() - 1).replace("'", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }

        List<Matriculas> matriculas = new ArrayList();
        if (matri.getCodigomat().equals(-2)) {
            matriculas = adm.query("Select o from Matriculas as o where  o.estado in ('Matriculado','Recibir Pase')  and o.curso.codigocur = '" + curso.getCodigocur() + "' order by o.estudiante.apellido ");
        } else {
            matriculas.add(matri);
        }
        ArrayList lisFaltas = new ArrayList();

        String q = "SELECT o.codigomat, e.apellido,e.nombre, m.descripcion, SUM(f.faltas), SUM(f.justificadas), SUM(f.total), m.codigo   "
                + " FROM faltas f, matriculas o, sistemacalificacion s, estudiantes e, GLOBAL m   "
                + "WHERE m.codigo = f.materia AND e.codigoest = o.estudiante AND s.codigosis = f.sistema "
                + " AND o.codigomat = f.matricula AND o.curso =  '" + curso.getCodigocur() + "'  "
                + " AND  s.orden   <= '" + sistema.getOrden() + "'   "
                + " GROUP BY matricula,m.descripcion ORDER BY e.apellido, e.nombre,m.descripcion ";
//                
        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            int ksis = 0;
//                for (int j = 0; j < vec.size() - 1; j++) {
//                    Object dos = vec.get(j);
            Matriculas matriculas1 = new Matriculas((Integer) vec.get(0));
            Estudiantes est = new Estudiantes();
            est.setApellido(vec.get(1) + "");
            est.setNombre(vec.get(2) + "");
            matriculas1.setCurso(curso);
            matriculas1.setEstudiante(est);
            List<MateriaProfesor> mp = adm.query("Select o from MateriaProfesor as o "
                    + "where o.materia.codigo =  '" + vec.get(7) + "' and o.curso.codigocur = '" + curso.getCodigocur() + "'  ");
            int NoHoras = 100;
            if (mp.size() > 0) {
                NoHoras = mp.get(0).getHoras();
                if (NoHoras <= 0) {
                    NoHoras = 100;
                }


            NotaCollection coll = new NotaCollection();
            coll.setNota(((BigDecimal) vec.get(4)));
            coll.setMateria("F.I.");
            coll.setMatricula("" + vec.get(0));
            coll.setMatriculas(matriculas1);
            coll.setEstudiante(vec.get(1) + " " + vec.get(2) + " ");
            coll.setSistema(vec.get(3) + " ");
            lisFaltas.add(coll);

            coll = new NotaCollection();
            coll.setNota(((BigDecimal) vec.get(5)));
            coll.setMateria("F.J.");
            coll.setMatricula("" + vec.get(0));
            coll.setMatriculas(matriculas1);
            coll.setEstudiante(vec.get(1) + " " + vec.get(2) + " ");
            coll.setSistema(vec.get(3) + " ");
            lisFaltas.add(coll);

            //int totalFaltas = (((BigDecimal)vec.get(5)).divide(new BigDecimal(2))).intValue()+ ((BigDecimal)vec.get(4)).intValue();
            BigDecimal totalFaltas = ((BigDecimal) vec.get(4));
            coll = new NotaCollection();
            coll.setNota((totalFaltas.multiply(new BigDecimal(100)).divide(new BigDecimal(NoHoras), 2, RoundingMode.HALF_UP)));
            coll.setMateria("%");
            coll.setMatricula("" + vec.get(0));
            coll.setMatriculas(matriculas1);
            coll.setEstudiante(vec.get(1) + " " + vec.get(2) + " ");
            coll.setSistema(vec.get(3) + " ");
            lisFaltas.add(coll);

//                }
            //row.setParent(this);
        }


        }

////        }


        ReporteFaltasDataSource ds = new ReporteFaltasDataSource(lisFaltas);
//        lisNotas = null;
        return ds;

    }

    public int buscarOrden(List<MateriaProfesor> materiaProfesores, Global materia) {
        for (Iterator<MateriaProfesor> it = materiaProfesores.iterator(); it.hasNext();) {

            MateriaProfesor materiaProfesor = it.next();
            if (materia.getCodigo().equals(materiaProfesor.getMateria().getCodigo())) {
                return materiaProfesor.getOrden();
            }

        }
        return 0;

    }

    public JRDataSource libretasNew(Cursos curso, Matriculas matri, Sistemacalificacion sistema) throws InterruptedException {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        if (periodo == null) {
            periodo = matri.getCurso().getPeriodo();
        }
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
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
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);

//DECIMALESDIS
        List<MateriaProfesor> materiaProfesores = adm.query("Select o from MateriaProfesor as o "
                + "where o.curso.codigocur = '" + curso.getCodigocur() + "'  ");

        List<Equivalencias> equivalenciasFaltas = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DI' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalenciasDisc = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + " and o.seimprime = true and o.codigosis = '" + sistema.getCodigosis() + "' "
                + " and o.trimestre.codigotrim = '" + sistema.getTrimestre().getCodigotrim() + "' order by o.orden ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.codigosis =  '" + sistema.getCodigosis() + "'"
                + " and o.sistema.seimprime = true  "
                + " and o.sistema.trimestre.codigotrim = '" + sistema.getTrimestre().getCodigotrim() + "'   order by o.sistema.orden ");
        if (notas.size() <= 0) {
            try {
                Messagebox.show("No hay nada que imprimir...! \n Revise en la pantalla Aportes si existen notas a imprimir", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        List<Sistemaevaluacion> sisEvaluaciones = adm.query("Select o from Sistemaevaluacion as o "
                + " where o.sistemacalificacion.codigosis = '" + sistema.getCodigosis() + "' "
                + " and o.espromedio = true ");
        String notaQuery = "";
        if (sisEvaluaciones.size() > 0) {
            for (Sistemaevaluacion notass : sisEvaluaciones) {
                notaQuery += notass.getNombre() + ",";
            }
            notaQuery = notaQuery.substring(0, notaQuery.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        } else {
            Messagebox.show("No hay nada que imprimir...! \n Seleccion otro APORTE o BLOQUE", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
            return null;
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
            if (truncarNotas) {
                query2 += "truncate(cast(avg(" + notass.getNota() + ") as decimal(9,4))," + numeroDecimales.intValue() + "),";
            } else {
                query2 += "round(cast(avg(" + notass.getNota() + ") as decimal(9,4))," + numeroDecimales.intValue() + "),";
            }
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
            String recomendacion = "";
            String plan = "";

            /*
             * aumentadio
             */

            String que = "Select o.matricula,g.codigo,o.cuantitativa, " + notaQuery + " from Notasevaluacion as o, Global as g "
                    + "where o.materia = g.codigo and o.sistemacalificacion = '" + sistema.getCodigosis() + "'  "
                    + "and o.matricula = '" + matriculas1.getCodigomat() + "' "
                    + " and o.materia in (Select x.materia from materia_profesor as x "
                    + " where x.curso = '" + curso.getCodigocur() + "' and x.seimprime = true ) "
                    + " order by g.descripcion ";
            System.out.println("" + que);

            List noEncontradas = adm.queryNativo(que);
            //sisEvaluaciones
            if (noEncontradas.size() > 0) {

                for (Iterator it1 = noEncontradas.iterator(); it1.hasNext();) {
                    Vector vecti = (Vector) it1.next();

                    Matriculas matriculaNo = null;
                    Boolean cuantitativa = false;
                    Global mate = new Global();
                    int ksis = 0;
                    int numerar = -20;

                    for (int j = 0; j < vecti.size(); j++) {
                        Object dos = vecti.get(j);
                        Double val = 0.0;
                        NotaCollection coll2 = new NotaCollection();
                        //val = (Double) dos;
                        coll2.setNota(dos);
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
                            coll2.setNota(dos);


                            if (cuantitativa == false || ((Sistemaevaluacion) sisEvaluaciones.get(ksis)).getEsequivalencia()) {
                                coll2.setNota(equivalencia(dos, equivalencias));
                            } else {
//                                if (val == 0.0) {
//                                    coll2.setNota("");
//                                }
                            }
                            coll2.setOrden((buscarOrden(materiaProfesores, mate)));
                            coll2.setMateria(mate.getDescripcion());
                            coll2.setMatricula("" + matriculaNo.getCodigomat());
//                            coll2.setRecomendacion(recomendacion);
//                            coll2.setPlan(plan);
//                            coll2.setEquivalencia(equivalencia2(dos, equivalencias)); 
                            coll2.setEstudiante(matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre());
                            coll2.setSistema("-" + (ksis) + "" + ((Sistemaevaluacion) sisEvaluaciones.get(ksis)).getEvaluacion().getAbreviatura());
                            coll2.setTipo(((Sistemaevaluacion) sisEvaluaciones.get(ksis)).getSistemacalificacion().getTrimestre().getDescripcion() + " - " + sistema.getNombre());
                            System.out.println("" + coll2.getNota() + " s:" + coll2.getSistema());
                            lisNotasC.add(coll2);

                            ksis++;
                            numerar++;
                        } else if (j >= 2) {
                            //System.out.println(""+dos);
                            cuantitativa = (Boolean) dos;
                        } else if (j >= 1) {
                            mate = (Global) adm.buscarClave((Integer) dos, Global.class);
//                            try {
//                                List<Recomendaciones> rec = adm.query(" Select o from Recomendaciones as o"
//                                        + " where o.matricula.codigomat = '" + matriculaNo.getCodigomat() + "' "
//                                        + " and o.sistema.codigosis = '" + sistema.getCodigosis() + "' "
//                                        + " and o.materia.codigo = '" + mate.getCodigo() + "' ");
//                                if (rec.size() > 0) {
//                                    recomendacion = rec.get(0).getRecomendacion();
//                                    plan = rec.get(0).getPlan();
//                                } else {
//                                    recomendacion = "";
//                                    plan = "";
//                                }
//                            } catch (Exception e) {
//                                System.out.println("recomendaciones error: " + e);
//                            }

                        } else {
                            matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);

                        }
                    }

                }
            }
            /*
             * aumentadio
             */


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


                        if (cuantitativa == false || ((Sistemacalificacion) sistemas.get(ksis)).getEsequivalencia()) {
                            coll.setNota(equivalencia(dos, equivalencias));
                        } else {
                            if (val == 0.0) {
                                coll.setNota("");
                            }
                        }
                        coll.setEquivalencia(equivalencia2(dos, equivalencias));
                        coll.setOrden((buscarOrden(materiaProfesores, mate)));
                        coll.setMateria(mate.getDescripcion());
                        coll.setMatricula("" + matriculaNo.getCodigomat());
                        coll.setEstudiante(matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre());
                        coll.setSistema(((Sistemacalificacion) sistemas.get(ksis)).getAbreviatura());
                        coll.setRecomendacion(recomendacion + "".replace("null", "").replace("[SELECCIONE]", ""));
                        coll.setPlan(plan + "".replace("null", "").replace("[SELECCIONE]", ""));

                        coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion() + " - " + ((Sistemacalificacion) sistemas.get(ksis)).getNombre());
                        lisNotasC.add(coll);

                        ksis++;
                    } else if (j >= 2) {
                        //System.out.println(""+dos);
                        cuantitativa = (Boolean) dos;
                    } else if (j >= 1) {
                        mate = (Global) adm.buscarClave((Integer) dos, Global.class);
                        try {
                            List<Recomendaciones> rec = adm.query(" Select o from Recomendaciones as o"
                                    + " where o.matricula.codigomat = '" + matriculaNo.getCodigomat() + "' "
                                    + " and o.sistema.codigosis = '" + sistema.getCodigosis() + "' "
                                    + " and o.materia.codigo = '" + mate.getCodigo() + "' ");
                            if (rec.size() > 0) {
                                try {
                                    if (rec.get(0).getItemRecomendacion() == null) {
                                        recomendacion = rec.get(0).getRecomendacion();
                                    } else {
                                        recomendacion = rec.get(0).getItemRecomendacion().getRecomendacion();
                                        if (rec.get(0).getItemRecomendacion().getCodigo().equals(new Integer(0))) {
                                            recomendacion = "";
                                        }
                                    }
                                } catch (Exception ar) {
                                }
                                try {
                                    if (rec.get(0).getItemPlan() == null) {
                                        plan = rec.get(0).getPlan();
                                    } else {
                                        plan = rec.get(0).getItemPlan().getRecomendacion();
                                        if (rec.get(0).getItemPlan().getCodigo().equals(new Integer(0))) {
                                            plan = "";
                                        }
                                    }

                                } catch (Exception ar) {
                                }

                            } else {
                                recomendacion = "";
                                plan = "";
                            }
                        } catch (Exception e) {
                            System.out.println("recomendaciones error: " + e);
                        }

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
                if (!promCuantitativo) {
                    q = "Select matricula," + query2 + "  from notas "
                            + "where notas.matricula = '" + matriculas1.getCodigomat() + "' "
                            + "and notas.seimprime = true "
                            + "and notas.promedia = true "
                            + "and notas.disciplina = false "
                            + "and notas.cuantitativa = false and notas.materia != 0 "
                            + "group by matricula  ";
                }
//                System.out.println("NOTAS DE promedio " + q);
                nativo = null;
                nativo = adm.queryNativo(q);
                for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();

                    Boolean cuantitativa = false;
                    Global mate = new Global();
                    mate.setCodigo(0);
                    mate.setDescripcion("98.PROMEDIO");
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
                            if (!promCuantitativo || ((Sistemacalificacion) sistemas.get(ksis)).getEsequivalencia()) {
                                coll.setNota(equivalencia(((BigDecimal) dos).doubleValue(), equivalencias));
                            } else {
                                if (val == 0.0) {
                                    coll.setNota("");
                                }
                            }
                            promedioFinal = dos;
                            coll.setOrden(100);
                            coll.setMateria(mate.getDescripcion());
                            coll.setMatricula("" + matriculas1.getCodigomat());
                            coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                            coll.setSistema(((Sistemacalificacion) sistemas.get(ksis)).getAbreviatura());
                            coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion() + " - " + ((Sistemacalificacion) sistemas.get(ksis)).getNombre());
                            coll.setPlan("");
                            coll.setRecomendacion("");
                            coll.setEquivalencia(equivalencia2(val, equivalencias));
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
                    mate.setDescripcion("99.DISCIPLINA");
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
                            if (!discCuantitativo || ((Sistemacalificacion) sistemas.get(ksis)).getEsequivalencia()) {
                                coll.setNota(equivalencia(((BigDecimal) dos).doubleValue(), equivalenciasDisc));
                            } else {
                                if (val == 0.0) {
                                    coll.setNota("");
                                }
                            }
                            disciplinaFinal = dos;
                            coll.setOrden(101);
                            coll.setMateria(mate.getDescripcion());
                            coll.setMatricula("" + matriculas1.getCodigomat());
                            coll.setPlan("");
                            coll.setRecomendacion("");
                            coll.setEquivalencia(equivalencia2(val, equivalenciasDisc));
                            coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                            coll.setSistema(((Sistemacalificacion) sistemas.get(ksis)).getAbreviatura());
                            coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion() + " - " + ((Sistemacalificacion) sistemas.get(ksis)).getNombre());
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
                q = "Select " + query4 + ",sis.abreviatura from disciplina, sistemacalificacion  sis, trimestres tri "
                        + "where matricula = '" + matriculas1.getCodigomat() + "' and sis.trimestre = tri.codigotrim   "
                        + "and sis.orden <= '" + sistema.getOrden() + "' AND sis.trimestre = '"+sistema.getTrimestre().getCodigotrim()+"'  AND sis.codigosis = disciplina.sistema  and sis.seimprime = true  "
                        + "  group by sis.codigosis order by  tri.codigotrim, sis.orden "
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


            //IMPRIMO EL CUADRO DE EQUIVALENCIAS DE FALTAS

            ArrayList lisAutoevaluacion = new ArrayList();

            try {
                //query = query3.substring(0, query3.length() - 1);
            } catch (Exception e) {
                //Messagebox.show("No existen EQUIVALENCIAS \n Revise ADMINISTRACION > PARAMETROS > EQUIVALENCIAS >> DISCIPLINA", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                //System.out.println("************** LINEA: 1276: REPORTECLASE ERROR NO HAY EQUIVALENCIAS PARAMETRIZADAS" + e);
                //return null;
            }

            //IMPRIMO LAS FALTAS
            q = "Select o from Resultadoperfil as o where  "
                    + "  o.matricula.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "and o.periodo.orden <= '" + sistema.getOrden() + "'   "
                    + " order by o.codigo "
                    + " ";
//                System.out.println(""+q);
            List qNormal = adm.query(q);
            for (Iterator it = qNormal.iterator(); it.hasNext();) {
                Resultadoperfil object = (Resultadoperfil) it.next();
                NotaCollection coll = new NotaCollection();
                coll.setNota(object.getValor());
                coll.setMateria(object.getPerfil().getNombre());
                coll.setMatricula("" + matriculas1.getCodigomat());
                coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                coll.setSistema("" + object.getPeriodo().getAbreviatura());
                coll.setTipo("" + object.getPeriodo().getTrimestre().getDescripcion());
                lisAutoevaluacion.add(coll);

            }

            nota.setFirma1(firma1);
            nota.setFirma2(firma2);
            nota.setFirma3(firma3);
            nota.setCargo1(cargo1);
            nota.setCargo2(cargo2);
            nota.setCargo3(cargo3);
            List recomenList = adm.query("Select o from Recomendaciones as o "
                    + " where o.sistema.codigosis = '" + sistema.getCodigosis() + "'  "
                    + " and o.matricula.codigomat = '" + matriculas1.getCodigomat() + "' ");
            if (recomenList.size() > 0) {
                for (Iterator it = recomenList.iterator(); it.hasNext();) {
                    Recomendaciones object = (Recomendaciones) it.next();
                    nota.setObservacion(nota.getObservacion() + "; " + object.getRecomendacion());
                    nota.setObservacion(nota.getObservacion().replace("null", ""));
                }


            } else {
                nota.setObservacion("");
            }


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
            nota.setAutoevaluacion(lisAutoevaluacion);
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

    public boolean buscarSiPierde(Integer matricula, Integer materia) {
        try {
            for (Iterator<MateriaProfesor> it = materiasReprobadas.iterator(); it.hasNext();) {
                MateriaProfesor reprobada = it.next();
                if (matricula.equals(reprobada.getCodigomap()) && materia.equals(reprobada.getOrden())) {
                    return true;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;


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



        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.promediofinal = 'PF' ");
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
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
        List<Matriculas> listaMatriculasPerdidos = cuadroverificar(curso, notas.get(0).getSistema(), matri);
        String codigosPerdidos = "";
        ArrayList perdidos = new ArrayList();
        for (Iterator<Matriculas> it = listaMatriculasPerdidos.iterator(); it.hasNext();) {
            Matriculas matriculas = it.next();
            codigosPerdidos = matriculas.getCodigomat() + "," + codigosPerdidos;
            perdidos.add(matriculas.getCodigomat());

        }
        String complemento = "";
        if (codigosPerdidos.length() > 0) {
            codigosPerdidos = codigosPerdidos.substring(0, codigosPerdidos.length() - 1);
            complemento = " and o.codigomat not in (" + codigosPerdidos + ") ";
        } else {
            complemento = "";
        }
        ArrayList listaMatriculados = new ArrayList();
//        List<Nota> lisNotas = new ArrayList();
        List<Matriculas> matriculas = new ArrayList();
        if (matri.getCodigomat().equals(-2)) {
            String codigoMatriculasPerdidos = "";
            for (Iterator<Matriculas> it = listaMatriculasPerdidos.iterator(); it.hasNext();) {
                Matriculas matriculaA = it.next();
                codigoMatriculasPerdidos += matriculaA.getCodigomat() + ",";
            }
            String complemPerdidos = "";
            if (codigoMatriculasPerdidos.length() > 0) {
                codigoMatriculasPerdidos = codigoMatriculasPerdidos.substring(0, codigoMatriculasPerdidos.length() - 1);
                complemPerdidos = " and o.codigomat not in (" + codigoMatriculasPerdidos + ")";
            }

            matriculas = adm.query("Select o from Matriculas as o "
                    + " where o.curso.codigocur = '" + curso.getCodigocur() + "' "
                    + " and o.estado in  ('Matriculado','Recibir Pase')  " + complemPerdidos
                    + " order by o.estudiante.apellido ");
        } else {
            if (matriculas.size() > 0) {
                for (Iterator<Matriculas> it = listaMatriculasPerdidos.iterator(); it.hasNext();) {
                    Matriculas matriculaA = it.next();
                    if (matri.getCodigomat().equals(matriculaA.getCodigomat())) {
                        try {
                            Messagebox.show("EL ESTUDIANTE " + matri.getEstudiante() + " ESTA REPROBADO, ESCOJA OTRO ESTUDIANTE", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                            return null;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        matriculas.add(matri);
                    }
                }
            } else {
                if (listaMatriculasPerdidos.size() > 0) {
                    if (matri.getCodigomat().equals(listaMatriculasPerdidos.get(0).getCodigomat())) {
                        try {
                            Messagebox.show("EL ESTUDIANTE " + matri.getEstudiante() + " ESTA REPROBADO, ESCOJA OTRO ESTUDIANTE", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                            return null;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        matriculas.add(matri);
                    }
                } else {
                    matriculas.add(matri);
                }


            }

        }
        String materiasDesglose = "";
        List<Global> materiasListado = adm.query("Select o from Global as o where o.descripcion like '%desglose%' ");
        if (materiasListado.size() > 0) {
            for (Iterator<Global> it = materiasListado.iterator(); it.hasNext();) {
                Global global = it.next();
                materiasDesglose = "" + global.getCodigo() + ",";
            }
            materiasDesglose = materiasDesglose.substring(0, materiasDesglose.length() - 1);
        }

        for (Matriculas matriculas1 : matriculas) {
            String cabe1 = cabecera;
            String piea = aprobado;
            String pier = reprobado;

            boolean estadoEstudiante = true;
            String valorAnadir = "round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal(8,3))," + 3 + ")";
            if (truncarNotas) {
                valorAnadir = "truncate(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal(8,3))," + 3 + ")";
            }
            String compleString = "";
            if (materiasDesglose.length() > 0) {
                compleString = " and notas.materia not in (" + materiasDesglose + ") ";
            }
            String q = "Select " + valorAnadir + "  from matriculas "
                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "and notas.seimprime = true "
                    + "and notas.promedia = true "
                    + "and notas.cuantitativa = true " + compleString
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
            String valorAnadir2 = " round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal(8,3))," + 3 + ") ";
            if (truncarNotas) {
                valorAnadir2 = " truncate(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal(8,3))," + 3 + ") ";
            }

            q = "Select " + valorAnadir2 + "   from matriculas "
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
                    + " " + compleString
                    + "and notas.disciplina = false   and notas.materia != 0  "
                    + "order by estudiantes.apellido, notas.orden";
            nativo = adm.queryNativo(q);
            //cabe1 = cabe1.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion() + " " + matriculas1.getCurso().getEspecialidad().getDescripcion() + " " + matriculas1.getCurso().getParalelo().getDescripcion());
            cabe1 = cabe1.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + ""
                    + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion()).replace("[especialidad]", matriculas1.getCurso().getEspecialidad().getDescripcion()).replace("[paralelo]", matriculas1.getCurso().getParalelo().getDescripcion()).replace("[siguientecurso]",matriculas1.getCurso().getSiguiente());
            piea = piea.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + ""
                    + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion()).replace("[especialidad]", matriculas1.getCurso().getEspecialidad().getDescripcion()).replace("[paralelo]", matriculas1.getCurso().getParalelo().getDescripcion()).replace("[siguientecurso]",matriculas1.getCurso().getSiguiente());
            pier = pier.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + ""
                    + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion()).replace("[especialidad]", matriculas1.getCurso().getEspecialidad().getDescripcion()).replace("[paralelo]", matriculas1.getCurso().getParalelo().getDescripcion()).replace("[siguientecurso]",matriculas1.getCurso().getSiguiente());
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
                        if (truncarNotas) {
                            not.setAprovechamiento(truncar(aprovechamiento, noDecimales));
                        } else {
                            not.setAprovechamiento(redondear(aprovechamiento, noDecimales));
                        }

                        not.setDisciplina(disciplina);
//                        if ((Double) dos >= matriculas1.getCurso().getAprobacion()) {

//                        } else {
//                            not.setEstadoMateria("REPROBADO");
//                            estadoEstudiante = false;
//                        }
                        if (buscarSiPierde(matriculas1.getCodigomat(), mate.getCodigo())) {
                            not.setEstadoMateria("REPROBADO");
                            estadoEstudiante = false;
                        } else {
                            not.setEstadoMateria("APROBADO");
                        }
                        not.setEstado(estadoEstudiante);
//                        if (perdidos.contains(matriculas1.getCodigomat())) {
//                            not.setEstadoMateria("REPROBADO");
//                        }
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
        adm = null;
        ses = null;
        periodo = null;
        textos = null;
        cabecera = null;
        aprobado = null;
        reprobado = null;
        parametrosGlobales = null;
        equivalencias = null;
        notas = null;
        noDecimales = null;
        listaMatriculasPerdidos = null;
        codigosPerdidos = null;
        listaMatriculados = null;
        codigosPerdidos = null;
        perdidos = null;
        complemento = null;
        matriculas = null;
        listaMatriculados = null;

        return ds;

    }

    public JRDataSource promocion2(Cursos curso, Matriculas matri, Boolean horizontal) { //CON DECIMALES
//     int tamanio=0; -2
        Administrador adm = new Administrador();


        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Textos> textos = adm.query("Select o from Textos as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        String cabecera = regresaTexto("CMAT", textos);
        String aprobado = regresaTexto("PACMAT", textos);
        String reprobado = regresaTexto("PRCMAT", textos);
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.promediofinal = 'PF' ");
        List<Notanotas> notasPG = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.promediofinal = 'PG' ");
        List<Notanotas> notash = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.promedioparcial = true and o.sistema.promediofinal = 'NI' ");
        List<Matriculas> listaMatriculasPerdidos = cuadroverificar(curso, notas.get(0).getSistema(), matri);
        String codigosPerdidos = "";
        for (Iterator<Matriculas> it = listaMatriculasPerdidos.iterator(); it.hasNext();) {
            Matriculas matriculas = it.next();
            codigosPerdidos = matriculas.getCodigomat() + "," + codigosPerdidos;

        }
        String complemento = "";
        if (codigosPerdidos.length() > 0) {
            codigosPerdidos = codigosPerdidos.substring(0, codigosPerdidos.length() - 1);
            complemento = " and o.codigomat not in (" + codigosPerdidos + ") ";
        } else {
            complemento = "";
        }
        Integer noDecimales = 3;
        try {
            noDecimales = regresaVariableParametrosDecimal("DECIPROMOCION", parametrosGlobales).intValue();
        } catch (Exception a) {
            noDecimales = 3;

        }
        Integer noDecimalesProme = 3;
        try {
            noDecimalesProme = regresaVariableParametrosDecimal("DECIMALESPRO", parametrosGlobales).intValue();
        } catch (Exception a) {
            noDecimalesProme = 3;

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
            String codigoMatriculasPerdidos = "";
            for (Iterator<Matriculas> it = listaMatriculasPerdidos.iterator(); it.hasNext();) {
                Matriculas matriculaA = it.next();
                codigoMatriculasPerdidos += matriculaA.getCodigomat() + ",";
            }
            String complemPerdidos = "";
            if (codigoMatriculasPerdidos.length() > 0) {
                codigoMatriculasPerdidos = codigoMatriculasPerdidos.substring(0, codigoMatriculasPerdidos.length() - 1);
                complemPerdidos = " and o.codigomat not in (" + codigoMatriculasPerdidos + ")";
            }

            matriculas = adm.query("Select o from Matriculas as o "
                    + " where o.curso.codigocur = '" + curso.getCodigocur() + "' "
                    + " and o.estado in  ('Matriculado','Recibir Pase')  " + complemPerdidos
                    + " order by o.estudiante.apellido ");
        } else {
            if (matriculas.size() > 0) {
                for (Iterator<Matriculas> it = listaMatriculasPerdidos.iterator(); it.hasNext();) {
                    Matriculas matriculaA = it.next();
                    if (matri.getCodigomat().equals(matriculaA.getCodigomat())) {
                        try {
                            Messagebox.show("EL ESTUDIANTE " + matri.getEstudiante() + " ESTA REPROBADO, ESCOJA OTRO ESTUDIANTE", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                            return null;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        matriculas.add(matri);
                    }
                }
            } else {
                if (listaMatriculasPerdidos.size() > 0) {
                    if (matri.getCodigomat().equals(listaMatriculasPerdidos.get(0).getCodigomat())) {
                        try {
                            Messagebox.show("EL ESTUDIANTE " + matri.getEstudiante() + " ESTA REPROBADO, ESCOJA OTRO ESTUDIANTE", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                            return null;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        matriculas.add(matri);
                    }
                } else {
                    matriculas.add(matri);
                }


            }

        }
        for (Matriculas matriculas1 : matriculas) {
            String cabe1 = cabecera;
            String piea = aprobado;
            String pier = reprobado;
            boolean estadoEstudiante = true;
            //aquí selecciono si es horizonal en el caso del 1q y el 2q o en vertical de todas las materias en su nota final
            
             String verificardorSupletorio = "Select notas." +  notasPG.get(0).getNota() + " from notas "
                    + " "
                    + "where   notas." +  notasPG.get(0).getNota() + " < 7   "
                    + " and notas.matricula = '" + matriculas1.getCodigomat() + "' "
                    + "and notas.seimprime = true "
                    + "and notas.promedia = true "
                    + "and notas.cuantitativa = true "
                    + "and notas.disciplina = false   and notas.materia != 0 ";
            System.out.println("" + verificardorSupletorio);
            List listMateriasSupletorio = adm.queryNativo(verificardorSupletorio);
            
            String valorAnadir2 = "";
            if(horizontal.booleanValue()==false){
                //PARA CARGAR EL APROVECHAMIENTO
                valorAnadir2 = " round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,"+noDecimalesProme+"))) as decimal(8,"+noDecimalesProme+"))," + noDecimales + ") ";
                if (truncarNotas) {
                    valorAnadir2 = " truncate(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,"+noDecimalesProme+"))) as decimal(8,"+noDecimalesProme+"))," + noDecimales+ ") ";
                }
            }else{
                //PARA CARGAR EL APROVECHAMIENTO en 1q y 2q
                String aPromediar = "";
                int contador=0;
                for (Iterator<Notanotas> it = notash.iterator(); it.hasNext();) {
                    Notanotas notah1 = it.next();
                        
                        if (truncarNotas) {
                            valorAnadir2 += " truncate(cast(avg(CAST(" + notah1.getNota() + "  AS DECIMAL(8,"+noDecimalesProme+"))) as decimal(8,"+noDecimalesProme+"))," + noDecimales + ") +";
                        }else{
                            valorAnadir2 += " round(cast(avg(CAST(" + notah1.getNota() + "  AS DECIMAL(8,"+noDecimalesProme+"))) as decimal(8,"+noDecimalesProme+"))," + noDecimales + ") +";        
                        }
                        contador++;
                    
                }
                valorAnadir2 = "truncate( CAST( ("+valorAnadir2.substring(0, valorAnadir2.length()-1)+")/"+contador+"  AS DECIMAL(8,3))," + noDecimales + ") ";
                    if(listMateriasSupletorio.size()>0){
                        valorAnadir2 = " round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,"+noDecimalesProme+"))) as decimal(8,"+noDecimalesProme+"))," + noDecimales + ") ";
                        if (truncarNotas) {
                            valorAnadir2 = " truncate(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,"+noDecimalesProme+"))) as decimal(8,"+noDecimalesProme+"))," + noDecimales + ") ";
                        }
                    }
            }
            

            String q = "Select " + valorAnadir2 + " from matriculas "
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
            String valorAnadir = " round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal(8,3))," + 3 + ") ";
            if (truncarNotas) {
                valorAnadir = " truncate(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal(8,3))," + 3 + ") ";
            }
            q = "Select " + valorAnadir + " from matriculas "
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
                    + "and notas.promedia = true  "
                    + "and notas.disciplina = false   and notas.materia != 0  "
                    + "order by estudiantes.apellido, notas.orden";
            nativo = adm.queryNativo(q);
            //cabe1 = cabe1.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion() + " " + matriculas1.getCurso().getEspecialidad().getDescripcion() + " " + matriculas1.getCurso().getParalelo().getDescripcion());
            cabe1 = cabe1.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + ""
                    + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion()).replace("[especialidad]", matriculas1.getCurso().getEspecialidad().getDescripcion()).replace("[paralelo]", matriculas1.getCurso().getParalelo().getDescripcion()).replace("[siguientecurso]",matriculas1.getCurso().getSiguiente());
            piea = piea.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + ""
                    + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion()).replace("[especialidad]", matriculas1.getCurso().getEspecialidad().getDescripcion()).replace("[paralelo]", matriculas1.getCurso().getParalelo().getDescripcion()).replace("[siguientecurso]",matriculas1.getCurso().getSiguiente());
            pier = pier.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + ""
                    + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion()).replace("[especialidad]", matriculas1.getCurso().getEspecialidad().getDescripcion()).replace("[paralelo]", matriculas1.getCurso().getParalelo().getDescripcion()).replace("[siguientecurso]",matriculas1.getCurso().getSiguiente());
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
                        if (truncarNotas) {
                            not.setAprovechamiento(truncar(aprovechamiento, noDecimales));
                        } else {
                            not.setAprovechamiento(redondear(aprovechamiento, noDecimales));
                        }
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
                        not.setNoDecimalesProme(noDecimales);
                        not.setNoDecimalesPromeParciales(noDecimalesProme);
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
        limpiarMemoria();
        return ds;

    }
    public int year = 0;
    public int month = 0;
    public int day = 0;

    public void calcularEdad(Date nacimiento, Date fecha) {
//Date yourDate = (Date) Calendar.getInstance().getTime();
        java.sql.Timestamp birth = new java.sql.Timestamp(nacimiento.getTime());
        Date d = fecha;


        SimpleDateFormat sdfDia = new SimpleDateFormat("dd");
        SimpleDateFormat sdfMes = new SimpleDateFormat("MM");
        SimpleDateFormat sdfAño = new SimpleDateFormat("yyyy");

        int a = Integer.parseInt(sdfAño.format(d)) - Integer.parseInt(sdfAño.format(birth));
        int b = Integer.parseInt(sdfMes.format(d)) - Integer.parseInt(sdfMes.format(birth));
        int c = Integer.parseInt(sdfDia.format(d)) - Integer.parseInt(sdfDia.format(birth));

        if (b < 0) {
            a = a - 1;
            b = 12 + b;
        }

        if (c < 0) {
            b = b - 1;
            switch (Integer.parseInt(sdfMes.format(d))) {
                case 2:
                    int año = Integer.parseInt(sdfAño.format(d));
                    if ((año % 4 == 0) && ((año % 100 != 0) || (año % 400 == 0))) {
                        c = 29 + c;
                    } else {
                        c = 28 + c;
                    }
                    break;
                case 4:
                case 6:
                case 9:
                case 10:
                case 11:
                    c = 30 + c;
                    break;
                case 1:
                case 3:
                case 5:
                case 7:
                case 8:
                case 12:
                    c = 31 + c;
                    break;
            }
        }


        this.day = c;
        this.month = (b < 0 ? 0 : b);
        this.year = a;

    }
    //REPORTE PARA PREESCOLAR

    public JRDataSource promocion3(Cursos curso, Matriculas matri, Date fecha) { //CON DECIMALES
//     int tamanio=0; -2
        Administrador adm = new Administrador();


        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Textos> textos = adm.query("Select o from Textos as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        String cabecera = regresaTexto("CMAT", textos);
        String aprobado = regresaTexto("PACMAT", textos);
        String reprobado = regresaTexto("PRCMAT", textos);
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        Boolean promCuantitativo = regresaVariableParametrosLogico("PROMCUAN", parametrosGlobales);
        Boolean discCuantitativo = regresaVariableParametrosLogico("DISCCUAN", parametrosGlobales);
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.promediofinal = 'PF' ");
        List<Matriculas> listaMatriculasPerdidos = cuadroverificar(curso, notas.get(0).getSistema(), matri);
        String codigosPerdidos = "";
        for (Iterator<Matriculas> it = listaMatriculasPerdidos.iterator(); it.hasNext();) {
            Matriculas matriculas = it.next();
            codigosPerdidos = matriculas.getCodigomat() + "," + codigosPerdidos;

        }
        String complemento = "";
        if (codigosPerdidos.length() > 0) {
            codigosPerdidos = codigosPerdidos.substring(0, codigosPerdidos.length() - 1);
            complemento = " and o.codigomat not in (" + codigosPerdidos + ") ";
        } else {
            complemento = "";
        }
        Integer noDecimales = 3;
        try {
            noDecimales = regresaVariableParametrosDecimal("DECIPROMOCION", parametrosGlobales).intValue();
        } catch (Exception a) {
            noDecimales = 3;

        }
        Integer noDecimalesProme = 3;
        try {
            noDecimalesProme = regresaVariableParametrosDecimal("DECIMALESPRO", parametrosGlobales).intValue();
        } catch (Exception a) {
            noDecimalesProme = 3;

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
            String codigoMatriculasPerdidos = "";
            for (Iterator<Matriculas> it = listaMatriculasPerdidos.iterator(); it.hasNext();) {
                Matriculas matriculaA = it.next();
                codigoMatriculasPerdidos += matriculaA.getCodigomat() + ",";
            }
            String complemPerdidos = "";
            if (codigoMatriculasPerdidos.length() > 0) {
                codigoMatriculasPerdidos = codigoMatriculasPerdidos.substring(0, codigoMatriculasPerdidos.length() - 1);
                complemPerdidos = " and o.codigomat not in (" + codigoMatriculasPerdidos + ")";
            }

            matriculas = adm.query("Select o from Matriculas as o "
                    + " where o.curso.codigocur = '" + curso.getCodigocur() + "' "
                    + " and o.estado in  ('Matriculado','Recibir Pase')  " + complemPerdidos
                    + " order by o.estudiante.apellido ");
        } else {
            if (matriculas.size() > 0) {
                for (Iterator<Matriculas> it = listaMatriculasPerdidos.iterator(); it.hasNext();) {
                    Matriculas matriculaA = it.next();
                    if (matri.getCodigomat().equals(matriculaA.getCodigomat())) {
                        try {
                            Messagebox.show("EL ESTUDIANTE " + matri.getEstudiante() + " ESTA REPROBADO, ESCOJA OTRO ESTUDIANTE", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                            return null;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        matriculas.add(matri);
                    }
                }
            } else {
                if (listaMatriculasPerdidos.size() > 0) {
                    if (matri.getCodigomat().equals(listaMatriculasPerdidos.get(0).getCodigomat())) {
                        try {
                            Messagebox.show("EL ESTUDIANTE " + matri.getEstudiante() + " ESTA REPROBADO, ESCOJA OTRO ESTUDIANTE", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                            return null;
                        } catch (InterruptedException ex) {
                            Logger.getLogger(reportesClase.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        matriculas.add(matri);
                    }
                } else {
                    matriculas.add(matri);
                }


            }

        }
        for (Matriculas matriculas1 : matriculas) {
            String cabe1 = cabecera;
            String piea = aprobado;
            String pier = reprobado;
            boolean estadoEstudiante = true;
            //PARA CARGAR EL APROVECHAMIENTO
            String valorAnadir2 = " round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal(8,3))," + 3 + ") ";
            if (truncarNotas) {
                valorAnadir2 = " truncate(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal(8,3))," + 3 + ") ";
            }
            String COMPLE = " and notas.cuantitativa = true ";
            if (promCuantitativo == false) {
                COMPLE = "";
            }
            String q = "Select " + valorAnadir2 + " from matriculas "
                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
                    + "and notas.seimprime = true "
                    + "and notas.promedia = true "
                    + COMPLE
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
            String valorAnadir = " round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal(8,3))," + 3 + ") ";
            if (truncarNotas) {
                valorAnadir = " truncate(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal(8,3))," + 3 + ") ";
            }
            q = "Select " + valorAnadir + " from matriculas "
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

//            q = "Select matriculas.codigomat,notas.materia,notas.cuantitativa, " + notas.get(0).getNota() + "  "
//                    + "from matriculas  "
//                    + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest "
//                    + "left join notas on matriculas.codigomat = notas.matricula "
//                    + "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  "
//                    + "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' "
//                    + "and notas.seimprime = true  "
//                    + "and notas.promedia = true  "
//                    + "and notas.disciplina = false   and notas.materia != 0  "
//                    + "order by estudiantes.apellido, notas.orden";
//            nativo = adm.queryNativo(q);
//            //cabe1 = cabe1.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion() + " " + matriculas1.getCurso().getEspecialidad().getDescripcion() + " " + matriculas1.getCurso().getParalelo().getDescripcion());
//            cabe1 = cabe1.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + ""
//                    + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion()).replace("[especialidad]", matriculas1.getCurso().getEspecialidad().getDescripcion()).replace("[paralelo]", matriculas1.getCurso().getParalelo().getDescripcion());
//            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
//                Vector vec = (Vector) itna.next();
//                Boolean cuantitativa = false;
//                Global mate = new Global();
//                int ksis = 0;
//                for (int j = 0; j < vec.size(); j++) {
//                    Object dos = vec.get(j);
////                    Double val = 0.0;
            NotaCollection coll = new NotaCollection();
//                    try {
//                        if (dos.equals(null)) {
//                            dos = new Double(0.0);
//                        }
//                    } catch (Exception e) {
//                        dos = new Double(0.0);
//                    }
//                    if (j >= 3) {
//
//                        ksis++;
            NotasClaseTemp not = new NotasClaseTemp();
//                        not.setNota(dos);
//                        not.setNotaCuali(equivalencia(dos, equivalencias) + "");
//                        not.setMateria(mate.getDescripcion());
//
//                        not.setCabeceraTexto(cabe1);
//                        not.setPieTextoAprobado(piea);
//                        not.setPieTextoReprobado(pier);
//
            MateriaProfesor matep = new MateriaProfesor();
            matep.setCuantitativa(true);
            matep.setMateria(new Global(-1));
            not.setMateriaProfesor(matep);
            not.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
            if (truncarNotas) {
                not.setAprovechamientos(truncar(aprovechamiento, noDecimales));
                if (promCuantitativo == false) {
                    not.setAprovechamientos(equivalencia(not.getAprovechamientos(), equivalencias));
                }
            } else {
                not.setAprovechamientos(redondear(aprovechamiento, noDecimales));
                if (promCuantitativo == false) {
                    not.setAprovechamientos(equivalencia(not.getAprovechamientos(), equivalencias));
                }
            }
            not.setDisciplina(disciplina);
//                        if ((Double) dos >= matriculas1.getCurso().getAprobacion()) {
//                            not.setEstadoMateria("APROBADO");
//                        } else {
//                            not.setEstadoMateria("REPROBADO");
//                            estadoEstudiante = false;
//                        }
//                        not.setEstado(estadoEstudiante);
//                        if (cuantitativa == false) {
//                            not.setNota(equivalencia(dos, equivalencias));
//                        }
            calcularEdad(matriculas1.getEstudiante().getFechanacimiento(), fecha);
            matriculas1.setFolio(this.year);
            not.setMatricula(matriculas1);
            not.setNoDecimalesProme(noDecimales);
            not.setNoDecimalesPromeParciales(noDecimalesProme);
            listaMatriculados.add(not);
//
//
//                    } else if (j >= 2) {
//                        //System.out.println(""+dos);
//                        cuantitativa = (Boolean) dos;
//                    } else if (j >= 1) {
//                        mate = (Global) adm.buscarClave((Integer) dos, Global.class);
//                    } else {
////                        matriculaNo = (AcaMatricula) adm.buscarAcaMatricula((Integer) dos);
//                    }
//                }
//                //row.setParent(this);
//            }
//
//
        }


        ReportePromocionDataSource ds = new ReportePromocionDataSource(listaMatriculados);

        return ds;

    }

    public JRDataSource certificadodisciplina(Cursos curso, List<Matriculas> matri, Sistemacalificacion sistema) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Textos> textos = adm.query("Select o from Textos as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.codigosis = '" + sistema.getCodigosis() + "' ");
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        try {
            if (notas.size() <= 0) {
                Messagebox.show("Seleccione el primero el APORTE \n Puede obtener resultados no esperados", "Administrador Educativo", Messagebox.OK, Messagebox.ERROR);
                return null;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList listaMatriculados = new ArrayList();
//        List<Nota> lisNotas = new ArrayList();
        List<Matriculas> matriculas = new ArrayList();
        matriculas = matri;
//        if (matri.getCodigomat().equals(-2)) {
//            matriculas = adm.query("Select o from Matriculas as o where o.curso.codigocur = '" + curso.getCodigocur() + "'");
//        } else {
//            matriculas.add(matri);
//        }
        for (Matriculas matriculas1 : matriculas) {
            boolean estadoEstudiante = true;

            String valorAnadir2 = " round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal)," + 3 + ")  ";
            if (truncarNotas) {
                valorAnadir2 = " truncate(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal)," + 3 + ")  ";
            }
            String q = "Select " + valorAnadir2 + " from matriculas "
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
            String valorAnadir = "  round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal)," + 3 + ")  ";
            if (truncarNotas) {
                valorAnadir = " truncate(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal)," + 3 + ")  ";
            }
            q = "Select " + valorAnadir + "  from matriculas "
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
            not.setDisciplinaAbreviatura(equivalencia(disciplina, equivalencias) + "");
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
        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
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

            String valorAnadir = "  round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal)," + 3 + ") ";
            if (truncarNotas) {
                valorAnadir = " truncate(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal)," + 3 + ")  ";
            }
            String q = "Select " + valorAnadir + "  from matriculas "
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
            String valorAnadir2 = " round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal)," + 3 + ")  ";
            if (truncarNotas) {
                valorAnadir2 = " truncate(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,3))) as decimal)," + 3 + ")  ";
            }
            q = "Select  " + valorAnadir2 + " from matriculas "
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

    public JRDataSource actaGrado(String tipo, Matriculas mat, Global esp, Boolean suspendidos) {
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
        if (mat.getCodigomat().equals(-2)) {

            String complem = " (o.suspenso = false or o.suspenso is null) ";
            if (suspendidos) {
                complem = " o.suspenso = true ";
            }
            matriculas = adm.query("Select o from Matriculas as o "
                    + "where  (o.perdio = false or o.perdio is null) and " + complem + " "
                    + " and o.curso.especialidad.codigo = '" + esp.getCodigo() + "'  "
                    + " and o.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                    + " and o.curso.secuencia = '13' "
                    + " order by o.estudiante.apellido,  o.estudiante.nombre  ");
        } else {
            matriculas.add(mat);
        }

        for (Matriculas matriculas1 : matriculas) {
            //Matriculas matriculas1 = itm.next();
            String q = "Select matriculas.codigomat,numeroacta,fecha, " + query + "   from matriculas "
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
                Date fecha = null;
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
                        String cabecera1tmp = cabecera1.replace("[estudiante]", matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre()).replace("[mes]", convertir(fecha)).replace("[dia]", fecha.getDate() + "").replace("[anio]", (fecha.getYear() + 1900) + "").replace("[anioletras]", c.convertNumberToLetter((fecha.getYear() + 1900)).toLowerCase() + "");
                        String cabecera2tmp = cabecera2.replace("[fecha]", convertir(new Date()) + "").replace("[estudiante]", matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre()).replace("[mes]", convertir(fecha)).replace("[dia]", fecha.getDate() + "").replace("[anio]", (fecha.getYear() + 1900) + "").replace("[anioletras]", c.convertNumberToLetter((fecha.getYear() + 1900)).toLowerCase() + "");
                        coll.setCabecera1(cabecera1tmp);
                        coll.setCabecera1(cabecera1tmp);
                        coll.setCabecera2(cabecera2tmp);
                        coll.setCabecera2(cabecera2tmp);
                        String pie1tmp = pi1.replace("[estudiante]", matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre()).replace("[titulo]", matriculaNo.getCurso().getActa()).replace("[mes]", convertir(fecha)).replace("[dia]", fecha.getDate() + "").replace("[anio]", (fecha.getYear() + 1900) + "").replace("[anioletras]", c.convertNumberToLetter((fecha.getYear() + 1900)).toLowerCase() + "");
                        coll.setPie1(pie1tmp);
                        String pie2tmp = pi2.replace("[estudiante]", matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre()).replace("[titulo]", matriculaNo.getCurso().getActa()).replace("[mes]", convertir(fecha)).replace("[dia]", fecha.getDate() + "").replace("[anio]", (fecha.getYear() + 1900) + "").replace("[anioletras]", c.convertNumberToLetter((fecha.getYear() + 1900)).toLowerCase() + "");
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
                        coll.setFecha(fecha);

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
                        }
                    } else if (j == 2) {
                        try {
                            fecha = ((Date) dos);
                        } catch (Exception e) {
                            fecha = new Date();
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
                + " where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "and o.esfinal = true  order by o.codigo ");
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
            query2 += "round(cast(avg(" + notass.getColumna() + ") as decimal(9,3))," + numeroDecimales + "),";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        query2 = query2.substring(0, query2.length() - 1).replace("'", "");
        Actagrado acColumna = notas.get(0);
        String columna = acColumna.getColumna();
        //String columna  = "get"+acColumna.getColumna().substring(0,1).toUpperCase()+acColumna.getColumna().substring(1,acColumna.getColumna().length());
        List<Notasacta> notasA = adm.query(" SELECT o FROM Notasacta as o where o.matricula.curso.secuencia = 13 "
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
                + "where o.estado in ('Matriculado','Recibir Pase') and  o.curso.secuencia = 13 and o.curso.periodo.codigoper = '" + curso.getPeriodo().getCodigoper() + "' order by o.estudiante.apellido,o.estudiante.nombre");
        parametros.put("n1", "OCTAVO");
        parametros.put("n2", "NOVENO");
        parametros.put("n3", "DECIMO");
        parametros.put("n4", "1ER.AÑO");
        parametros.put("n5", "2DO.AÑO");
        parametros.put("n6", "PROM 8 a 2");
        parametros.put("n7", "3ER.AÑO");
        parametros.put("jornada", periodo.getSeccion().getDescripcion() );
        parametros.put("regimen", periodo.getRegimen());
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
                Double sumaPromedio = 0d;
                try {
                    sumaPromedio = (encontrados.getPrimero() + encontrados.getSegundo() + encontrados.getTercero() + encontrados.getCuarto() + encontrados.getQuinto()) / 5;
                } catch (Exception em) {
                    sumaPromedio = 0d;
                }


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
   
    public ArrayList actaGradoTodosPorCurso(Cursos curso) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Map parametros = new HashMap();
        Institucion insts = curso.getPeriodo().getInstitucion();
        parametros.put("denominacion", insts.getDenominacion());
        parametros.put("nombre", insts.getNombre());
        parametros.put("periodo", periodo.getDescripcion());
        parametros.put("jornada", periodo.getSeccion().getDescripcion() );
        parametros.put("regimen", periodo.getRegimen());
        
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
                + "where o.estado in ('Matriculado','Recibir Pase') and  o.curso.secuencia = 13 and o.curso.codigocur = '" + curso.getCodigocur() + "' "
                + " order by o.estudiante.apellido,o.estudiante.nombre");
        parametros.put("n1", "OCTAVO");
        parametros.put("n2", "NOVENO");
        parametros.put("n3", "DECIMO");
        parametros.put("n4", "1ER.AÑO");
        parametros.put("n5", "2DO.AÑO");
        parametros.put("n6", "PROM 8 a 2");
        parametros.put("n7", "3ER.AÑO");
        parametros.put("curso", curso+"");
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
                Double sumaPromedio = 0d;
                try {
                    sumaPromedio = (encontrados.getPrimero() + encontrados.getSegundo() + encontrados.getTercero() + encontrados.getCuarto() + encontrados.getQuinto()) / 5;
                } catch (Exception em) {
                    sumaPromedio = 0d;
                }


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
    //nomina oficial de graduados

    public ArrayList actaGradoTodos(Global especialidad, Boolean suspendidos) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Map parametros = new HashMap();
        Institucion insts = periodo.getInstitucion();
        parametros.put("denominacion", insts.getDenominacion());
        parametros.put("nombre", insts.getNombre());
        parametros.put("periodo", periodo.getDescripcion());
        parametros.put("slogan", insts.getSlogan());
        parametros.put("secretaria", insts.getSecretaria());
        parametros.put("rectora", insts.getRector());
        parametros.put("jornada", periodo.getSeccion().getDescripcion() );
        parametros.put("regimen", periodo.getRegimen());

        List<Actagrado> notas = adm.query("Select o from Actagrado as o "
                + " where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "   and o.esfinal = true order by o.codigo ");

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        String query2 = "";
        String numeroDecimales = "3";
        for (Actagrado notass : notas) {
            query2 += "round(cast(avg(" + notass.getColumna() + ") as decimal(9,3))," + numeroDecimales + "),";
        }
        query2 = query2.substring(0, query2.length() - 1).replace("'", "");
        ArrayList lisNotasC = new ArrayList();
        List<Matriculas> matriculas = new ArrayList();
        matriculas = adm.query("Select o from Matriculas as o "
                + "where o.estado in ('Matriculado','Recibir Pase') and  o.curso.secuencia = 13 "
                + " and o.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + " and o.curso.titulo.codigo = '" + especialidad.getCodigo() + "'  and o.suspenso = '" + suspendidos + "'  "
                + "order by o.estudiante.apellido,o.estudiante.nombre");
        parametros.put("n1", "Numero");

        for (Matriculas matriculas1 : matriculas) {
            //Matriculas matriculas1 = itm.next();
            NotaActaGeneral acta = new NotaActaGeneral();
            acta.setMatricula(matriculas1);
            List nativo = adm.queryNativo("Select numeroacta,fecha, " + query2 + " from notasacta where matricula = '" + matriculas1.getCodigomat() + "' ");
            for (Iterator it = nativo.iterator(); it.hasNext();) {
                Vector object = (Vector) it.next();
                if (notas.size() >= 1) {
//                        parametros.put("n1", notas.get(1).getAbreviatura());
                    acta.setNumeroacta(((Integer) object.get(0)));
                    acta.setFecha(((Date) object.get(1)));
                    acta.setN1(redondear(((BigDecimal) object.get(2)).doubleValue(), 0));
                    if (notas.get(0).getEsfinal()) {
                        acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(2)).doubleValue(), 0), equivalencias));
                    }

                }


            }
//
//        
            lisNotasC.add(acta);

        }


        ActaGeneralDataSource ds = new ActaGeneralDataSource(lisNotasC);
        lisNotasC = new ArrayList();
        ArrayList otro = new ArrayList();
        otro.add(ds);
        otro.add(parametros);
        return otro;

    }

    //resumen de calificacioens de constan en el acta de grado
    public ArrayList actaGradoTodosConstan(Global especialidad, Boolean suspendidos) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Map parametros = new HashMap();
        Institucion insts = periodo.getInstitucion();
        parametros.put("denominacion", insts.getDenominacion());
        parametros.put("nombre", insts.getNombre());
        parametros.put("periodo", periodo.getDescripcion());
        parametros.put("slogan", insts.getSlogan());
        parametros.put("secretaria", insts.getSecretaria());
        parametros.put("rectora", insts.getRector());
        parametros.put("jornada", periodo.getSeccion().getDescripcion());
        parametros.put("regimen", periodo.getRegimen());

        List<Actagrado> notas = adm.query("Select o from Actagrado as o "
                + " where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "    order by o.codigo ");

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        String query2 = "";
        String numeroDecimales = "3";
        for (Actagrado notass : notas) {
            query2 += "round(cast(avg(" + notass.getColumna() + ") as decimal(9,3))," + numeroDecimales + "),";
        }
        String complemento2 = "";
        if (suspendidos != null) {
            complemento2 = " and o.suspenso = '" + suspendidos + "' ";
        }
        query2 = query2.substring(0, query2.length() - 1).replace("'", "");
        ArrayList lisNotasC = new ArrayList();
        List<Matriculas> matriculas = new ArrayList();
        matriculas = adm.query("Select o from Matriculas as o "
                + "where o.estado in ('Matriculado','Recibir Pase') and  o.curso.secuencia = 13 "
                + " and o.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + " and o.curso.especialidad.codigo = '" + especialidad.getCodigo() + "'  "
                + " " + complemento2
                + "order by o.estudiante.apellido,o.estudiante.nombre");
        parametros.put("n1", "Numero");

        for (Matriculas matriculas1 : matriculas) {
            //Matriculas matriculas1 = itm.next();
            NotaActaGeneral acta = new NotaActaGeneral();
            acta.setMatricula(matriculas1);
            List nativo = adm.queryNativo("Select numeroacta,fecha, " + query2 + " from notasacta where matricula = '" + matriculas1.getCodigomat() + "' ");
            for (Iterator it = nativo.iterator(); it.hasNext();) {
                Vector object = (Vector) it.next();
                try {

                    if (notas.size() >= 1) {

                        acta.setNumeroacta(((Integer) object.get(0)));
                        acta.setFecha(((Date) object.get(1)));
                        parametros.put("n1", notas.get(0).getAbreviatura());
                        acta.setN1(redondear(((BigDecimal) object.get(2)).doubleValue(), 3));
                        if (notas.get(0).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(2)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 2) {

                        acta.setNumeroacta(((Integer) object.get(0)));
                        acta.setFecha(((Date) object.get(1)));
                        acta.setN2(redondear(((BigDecimal) object.get(3)).doubleValue(), 3));
                        parametros.put("n2", notas.get(1).getAbreviatura());
                        if (notas.get(1).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(3)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 3) {
                        acta.setNumeroacta(((Integer) object.get(0)));
                        acta.setFecha(((Date) object.get(1)));
                        acta.setN3(redondear(((BigDecimal) object.get(4)).doubleValue(), 3));
                        parametros.put("n3", notas.get(2).getAbreviatura());
                        if (notas.get(2).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(4)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 4) {
                        acta.setNumeroacta(((Integer) object.get(0)));
                        acta.setFecha(((Date) object.get(1)));
                        acta.setN4(redondear(((BigDecimal) object.get(5)).doubleValue(), 3));
                        parametros.put("n4", notas.get(3).getAbreviatura());
                        if (notas.get(3).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(4)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 5) {
                        acta.setNumeroacta(((Integer) object.get(0)));
                        acta.setFecha(((Date) object.get(1)));
                        acta.setN5(redondear(((BigDecimal) object.get(6)).doubleValue(), 3));
                        parametros.put("n5", notas.get(4).getAbreviatura());
                        if (notas.get(4).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(6)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 6) {
                        acta.setNumeroacta(((Integer) object.get(0)));
                        acta.setFecha(((Date) object.get(1)));
                        acta.setN6(redondear(((BigDecimal) object.get(7)).doubleValue(), 3));
                        parametros.put("n6", notas.get(5).getAbreviatura());
                        if (notas.get(5).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(7)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 7) {
                        acta.setNumeroacta(((Integer) object.get(0)));
                        acta.setFecha(((Date) object.get(1)));
                        acta.setN7(redondear(((BigDecimal) object.get(8)).doubleValue(), 3));
                        parametros.put("n7", notas.get(6).getAbreviatura());
                        if (notas.get(6).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(8)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 8) {
                        acta.setNumeroacta(((Integer) object.get(0)));
                        acta.setFecha(((Date) object.get(1)));
                        acta.setN8(redondear(((BigDecimal) object.get(9)).doubleValue(), 3));
                        parametros.put("n8", notas.get(7).getAbreviatura());
                        if (notas.get(7).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(9)).doubleValue(), 0), equivalencias));
                        }
                    }
                    if (notas.size() >= 9) {
                        acta.setNumeroacta(((Integer) object.get(0)));
                        acta.setFecha(((Date) object.get(1)));
                        acta.setN9(redondear(((BigDecimal) object.get(10)).doubleValue(), 3));
                        parametros.put("n9", notas.get(8).getAbreviatura());
                        if (notas.get(8).getEsfinal()) {
                            acta.setEquivalencia("" + equivalencia2(redondear(((BigDecimal) object.get(10)).doubleValue(), 0), equivalencias));
                        }
                    }

                } catch (Exception ex) {
                    System.out.println(matriculas1 + "obj: " + object.get(2) + "");
                    Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
//
//        
            lisNotasC.add(acta);

        }


        ActaGeneralDataSource ds = new ActaGeneralDataSource(lisNotasC);
        lisNotasC = new ArrayList();
        ArrayList otro = new ArrayList();
        otro.add(ds);
        otro.add(parametros);
        return otro;

    }

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

    public JRDataSource resumenGrupalProfesores(Encuestar enc) {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Cursos> lisCursos = new ArrayList<Cursos>();
        lisCursos = adm.queryNativo("Select o.* from Cursos as o where o.periodo = '" + periodo.getCodigoper() + "'  "
                + "group by o.secuencia order by o.secuencia ", Cursos.class);
        List<Preguntar> preg = adm.query("Select o from Preguntar as o  "
                + " where o.encuestar.codigo =  '" + enc.getCodigo() + "'  "
                + "order by o.orden ");
        ArrayList listaResultados = new ArrayList();
        for (Iterator<Cursos> itCursos = lisCursos.iterator(); itCursos.hasNext();) {
            Cursos curso = itCursos.next();
            int i = 1;
            for (Iterator<Preguntar> it = preg.iterator(); it.hasNext();) {
                Preguntar pregunta = it.next();
                List<Detallepreguntar> detall = adm.query("Select o from Detallepreguntar as o "
                        + "where o.preguntar.codigo = '" + pregunta.getCodigo() + "' order by o.secuencia ");
                int a = 1;
                for (Iterator<Detallepreguntar> it1 = detall.iterator(); it1.hasNext();) {
                    Detallepreguntar detallepregunta = it1.next();
//                rep.setId(i);
                    ResumenClase rep = new ResumenClase();
                    rep.setCurso(curso.getDescripcion() + " ");
                    rep.setPregunta(i + ".- " + pregunta.getPregunta());
                    rep.setRespuesta(detallepregunta.getOpcion());
                    Object respuestas = adm.querySimple("Select count(o) from Respuestasencuestar as o "
                            + "where o.detallepreguntar.codigo = '" + detallepregunta.getCodigo() + "' "
                            + "and o.matricula.curso.secuencia  = '" + curso.getSecuencia() + "' ");
                    Long valor = (Long) respuestas;
                    int val = valor.intValue();
                    rep.setValor(val);
                    listaResultados.add(rep);
                    a++;
                }
                i++;
            }
        }


        ReporteResumenDataSource ds = new ReporteResumenDataSource(listaResultados);
        return ds;


    }

    public JRDataSource recordporcurso(Cursos cursoLlega) {
        Administrador adm = new Administrador();
//        Session ses = Sessions.getCurrent();
//        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        ArrayList listaResultados = new ArrayList();
        List<Notasrecord> notas = adm.query("Select o from Notasrecord as o"
                + " where o.estudiante.codigoest "
                + " in (Select m.estudiante.codigoest from Matriculas as m where m.curso.codigocur = '" + cursoLlega.getCodigocur() + "' ) order by o.estudiante.apellido");
        for (Iterator<Notasrecord> it = notas.iterator(); it.hasNext();) {
            Notasrecord n = it.next();
            Nota n1 = new Nota();
            n1.setCurso(cursoLlega);
            n1.setEstudiante(n.getEstudiante());
            n1.setP1(new BigDecimal(n.getPrimero() == null ? 0 : n.getPrimero()));
            n1.setP2(new BigDecimal(n.getSegundo() == null ? 0 : n.getSegundo()));
            n1.setP3(new BigDecimal(n.getTercero() == null ? 0 : n.getTercero()));
            n1.setP4(new BigDecimal(n.getCuarto() == null ? 0 : n.getCuarto()));
            n1.setP5(new BigDecimal(n.getQuinto() == null ? 0 : n.getQuinto()));
            n1.setP6(new BigDecimal(n.getSexto() == null ? 0 : n.getSexto()));
            try {
                n1.setPromedio2((promedio(n1.getP1(), n1.getP2(), n1.getP3(), n1.getP4(), n1.getP5(), n1.getP6())));
            } catch (Exception e) {
            }
            n1.setD1(new BigDecimal(n.getPrimerod() == null ? 0 : n.getPrimerod()));
            n1.setD2(new BigDecimal(n.getSegundod() == null ? 0 : n.getSegundod()));
            n1.setD3(new BigDecimal(n.getTercerd() == null ? 0 : n.getTercerd()));
            n1.setD4(new BigDecimal(n.getCuartod() == null ? 0 : n.getCuartod()));
            n1.setD5(new BigDecimal(n.getQuintod() == null ? 0 : n.getQuintod()));
            n1.setD6(new BigDecimal(n.getSextod() == null ? 0 : n.getSextod()));
            try {
                n1.setDisciplina2((promedio(n1.getD1(), n1.getD2(), n1.getD3(), n1.getD4(), n1.getD5(), n1.getD6())));
            } catch (Exception e) {
            }
            listaResultados.add(n1);
        }
        ReporteRecordDataSource ds = new ReporteRecordDataSource(listaResultados);
        return ds;
    }

    public JRDataSource recordporcurso1(Integer secuencia) {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        ArrayList listaResultados = new ArrayList();
        List<Notasrecord> notas = adm.query("Select o from Notasrecord as o"
                + " where o.estudiante.codigoest "
                + " in (Select m.estudiante.codigoest from Matriculas as m where m.curso.secuencia= '" + secuencia + "' "
                + "and m.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' ) order by o.estudiante.apellido");
        for (Iterator<Notasrecord> it = notas.iterator(); it.hasNext();) {
            Notasrecord n = it.next();
            Nota n1 = new Nota();
            //n1.setCurso("");

            n1.setEstudiante(n.getEstudiante());
            n1.setP1(new BigDecimal(n.getPrimero() == null ? 0 : n.getPrimero()));
            n1.setP2(new BigDecimal(n.getSegundo() == null ? 0 : n.getSegundo()));
            n1.setP3(new BigDecimal(n.getTercero() == null ? 0 : n.getTercero()));
            n1.setP4(new BigDecimal(n.getCuarto() == null ? 0 : n.getCuarto()));
            n1.setP5(new BigDecimal(n.getQuinto() == null ? 0 : n.getQuinto()));
            n1.setP6(new BigDecimal(n.getSexto() == null ? 0 : n.getSexto()));
            try {
                n1.setPromedio2((promedio(n1.getP1(), n1.getP2(), n1.getP3(), n1.getP4(), n1.getP5(), n1.getP6())));
            } catch (Exception e) {
                System.out.println("ERRO FORMUAL" + e);
            }
            n1.setD1(new BigDecimal(n.getPrimerod() == null ? 0 : n.getPrimerod()));
            n1.setD2(new BigDecimal(n.getSegundod() == null ? 0 : n.getSegundod()));
            n1.setD3(new BigDecimal(n.getTercerd() == null ? 0 : n.getTercerd()));
            n1.setD4(new BigDecimal(n.getCuartod() == null ? 0 : n.getCuartod()));
            n1.setD5(new BigDecimal(n.getQuintod() == null ? 0 : n.getQuintod()));
            n1.setD6(new BigDecimal(n.getSextod() == null ? 0 : n.getSextod()));
            try {
                n1.setDisciplina2((promedio(n1.getD1(), n1.getD2(), n1.getD3(), n1.getD4(), n1.getD5(), n1.getD6())));
            } catch (Exception e) {
            }
            listaResultados.add(n1);
        }
        ReporteRecordDataSource ds = new ReporteRecordDataSource(listaResultados);
        return ds;
    }

    public JRDataSource recordporcurso2(Cursos cursoLlega) {
        Administrador adm = new Administrador();
//        Session ses = Sessions.getCurrent();
//        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        ArrayList listaResultados = new ArrayList();
        int secuencia = cursoLlega.getSecuencia();
        String complementoPromedio = " (o.primero + o.segundo + o.tercero + o.cuarto + o.quinto + o.sexto) /6";
        if (secuencia == 1) {
            complementoPromedio = " (o.primero) ";
        } else if (secuencia == 2) {
            complementoPromedio = " (o.primero + o.segundo)/2";
        } else if (secuencia == 3) {
            complementoPromedio = " (o.primero + o.segundo + o.tercero)/3";
        } else if (secuencia == 4) {
            complementoPromedio = " (o.primero + o.segundo + o.tercero + o.cuarto)/4";
        } else if (secuencia == 5) {
            complementoPromedio = " (o.primero + o.segundo + o.tercero + o.cuarto + o.quinto)/5";
        } else if (secuencia == 6) {
            complementoPromedio = " (o.primero + o.segundo + o.tercero + o.cuarto + o.quinto + o.sexto)/6 ";
        }

        List notas = adm.queryNativo("Select " + complementoPromedio + ", o.estudiante, o.primero, o.segundo, o.tercero, o.cuarto, o.quinto, o.sexto, "
                + "o.primerod, o.segundod, o.tercerd, o.cuartod, o.quintod, o.sextod  from Notasrecord as o"
                + " where o.estudiante "
                + " in (Select m.estudiante from Matriculas as m "
                + "where m.curso = '" + cursoLlega.getCodigocur() + "' ) "
                + "order by  " + complementoPromedio + " DESC  LIMIT 7  ");

        for (Iterator itna = notas.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            Nota n1 = new Nota();
            n1.setCurso(cursoLlega);
            n1.setEstudiante((Estudiantes) adm.buscarClave(vec.get(1), Estudiantes.class));
            n1.setP1(new BigDecimal(vec.get(2) == null ? "0" : vec.get(2) + ""));
            n1.setP2(new BigDecimal(vec.get(3) == null ? "0" : vec.get(3) + ""));
            n1.setP3(new BigDecimal(vec.get(4) == null ? "0" : vec.get(4) + ""));
            n1.setP4(new BigDecimal(vec.get(5) == null ? "0" : vec.get(5) + ""));
            n1.setP5(new BigDecimal(vec.get(6) == null ? "0" : vec.get(6) + ""));
            n1.setP6(new BigDecimal(vec.get(7) == null ? "0" : vec.get(7) + ""));
            try {
                n1.setPromedio2((promedio(n1.getP1(), n1.getP2(), n1.getP3(), n1.getP4(), n1.getP5(), n1.getP6())));
            } catch (Exception e) {
            }

            n1.setD1(new BigDecimal(vec.get(8) + ""));
            n1.setD2(new BigDecimal(vec.get(9) + ""));
            n1.setD3(new BigDecimal(vec.get(10) + ""));
            n1.setD4(new BigDecimal(vec.get(11) + ""));
            n1.setD5(new BigDecimal(vec.get(12) + ""));
            n1.setD6(new BigDecimal(vec.get(13) + ""));
            try {
                n1.setDisciplina2((promedio(n1.getD1(), n1.getD2(), n1.getD3(), n1.getD4(), n1.getD5(), n1.getD6())));
            } catch (Exception e) {
            }
            listaResultados.add(n1);

        }


        ReporteRecordDataSource ds = new ReporteRecordDataSource(listaResultados);
        return ds;
    }

    /**
     * if (secuencia == 1) { complementoPromedio = " CAST((o.primerob) AS
     * DECIMAL(5,3)) AS 2do10mo, "; complementoDisciplina = " CAST((o.primerobd
     * ) AS DECIMAL(5,3)) AS 2do10moDISC "; } else if (secuencia == 2) {
     * complementoPromedio = " CAST((o.segundob) AS DECIMAL(5,3)) AS 2do10mo, ";
     * complementoDisciplina = " CAST((o.segundobd) AS DECIMAL(5,3)) AS
     * 2do10moDISC "; } else if (secuencia == 3) { complementoPromedio = "
     * CAST((o.segundob + o.tercerob ) / 2 AS DECIMAL(5,3)) AS 2do10mo, ";
     * complementoDisciplina = " CAST((o.segundobd + o.tercerobd ) / 2 AS
     * DECIMAL(5,3)) AS 2do10moDISC "; } else if (secuencia == 4) {
     * complementoPromedio = " CAST((o.segundob + o.tercerob + o.cuartob) / 3 AS
     * DECIMAL(5,3)) AS 2do10mo, "; complementoDisciplina = " CAST((o.segundobd
     * + o.tercerobd + o.cuartobd ) / 3 AS DECIMAL(5,3)) AS 2do10moDISC "; }
     * else if (secuencia == 5) { complementoPromedio = " CAST((o.segundob +
     * o.tercerob + o.cuartob + o.quintob ) / 4 AS DECIMAL(5,3)) AS 2do10mo, ";
     * complementoDisciplina = " CAST((o.segundobd + o.tercerobd + o.cuartobd +
     * o.quintobd ) / 4 AS DECIMAL(5,3)) AS 2do10moDISC "; } else if (secuencia
     * == 6) { complementoPromedio = " CAST((o.segundob + o.tercerob + o.cuartob
     * + o.quintob + o.sextob) / 5 AS DECIMAL(5,3)) AS 2do10mo, ";
     * complementoDisciplina = " CAST((o.segundobd + o.tercerobd + o.cuartobd +
     * o.quintobd + o.sextobd ) / 5 AS DECIMAL(5,3)) AS 2do10moDISC "; } else if
     * (secuencia == 7) { complementoPromedio = " CAST((o.segundob + o.tercerob
     * + o.cuartob + o.quintob + o.sextob + o.septimob) / 6 AS DECIMAL(5,3)) AS
     * 2do10mo, "; complementoDisciplina = " CAST((o.segundobd + o.tercerobd +
     * o.cuartobd + o.quintobd + o.sextobd + o.septimobd ) / 6 AS DECIMAL(5,3))
     * AS 2do10moDISC "; } else if (secuencia == 8) { complementoPromedio = "
     * CAST((o.segundob + o.tercerob + o.cuartob + o.quintob + o.sextob +
     * o.septimob + o.primero) / 7 AS DECIMAL(5,3)) AS 2do10mo, ";
     * complementoDisciplina = " CAST((o.segundobd + o.tercerobd + o.cuartobd +
     * o.quintobd + o.sextobd + o.septimobd + o.primerod ) / 7 AS DECIMAL(5,3))
     * AS 2do10moDISC "; } else if (secuencia == 9) { complementoPromedio = "
     * CAST((o.segundob + o.tercerob + o.cuartob + o.quintob + o.sextob +
     * o.septimob + o.primero + o.segundo) / 8 AS DECIMAL(5,3)) AS 2do10mo, ";
     * complementoDisciplina = " CAST((o.segundobd + o.tercerobd + o.cuartobd +
     * o.quintobd + o.sextobd + o.septimobd + o.primerod + o.segundod ) / 8 AS
     * DECIMAL(5,3)) AS 2do10moDISC "; } else if (secuencia == 10) {
     * complementoPromedio = " CAST((o.segundob + o.tercerob + o.cuartob +
     * o.quintob + o.sextob + o.septimob + o.primero + o.segundo+ o.tercero) / 9
     * AS DECIMAL(5,3)) AS 2do10mo, "; complementoDisciplina = "
     * CAST((o.segundobd + o.tercerobd + o.cuartobd + o.quintobd + o.sextobd +
     * o.septimobd + o.primerod + o.segundod+ o.tercerd) / 9 AS DECIMAL(5,3)) AS
     * 2do10moDISC "; }
     *
     * @param cursoLlega
     * @return
     */
    public JRDataSource recordporcursoMejorCurso(Cursos cursoLlega) {
        Administrador adm = new Administrador();
//        Session ses = Sessions.getCurrent();
//        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        ArrayList listaResultados = new ArrayList();
        int secuencia = cursoLlega.getSecuencia();
        String complementoPromedio = " CAST((o.segundob + o.tercerob + o.cuartob + o.quintob + o.sextob + o.septimob + o.primero + o.segundo+ o.tercero) / 9  AS DECIMAL(5,3))  ";
        String complementoDisciplina = " CAST((o.segundobd + o.tercerobd + o.cuartobd + o.quintobd + o.sextobd + o.septimobd + o.primerod + o.segundod+ o.tercerd) / 9 AS DECIMAL(5,3)) ";
        String complementoPromedioBac = "";
        String complementoDisciplinaBac = "";
        if (secuencia == 11) {
            complementoPromedioBac = "  CAST((o.cuarto )   AS DECIMAL(5,3)) ";
            complementoDisciplinaBac = " CAST((o.cuartod ) AS DECIMAL(5,3))";
        } else if (secuencia == 12) {
            complementoPromedioBac = "  CAST((o.cuarto + o.quinto) / 2 AS DECIMAL(5,3)) ";
            complementoDisciplinaBac = "  CAST((o.cuartod + o.quintod) / 2 AS DECIMAL(5,3))";
        } else if (secuencia == 13) {
            complementoPromedioBac = "  CAST((o.cuarto + o.quinto+ o.sexto ) / 3 AS DECIMAL(5,3)) ";
            complementoDisciplinaBac = "  CAST((o.cuartod + o.quintod+ o.sextod ) / 3 AS DECIMAL(5,3))";
        }
        List notas = adm.queryNativo(" Select " + complementoPromedio + ", " + complementoDisciplina + " "
                + ", " + complementoPromedioBac + ", " + complementoDisciplinaBac + ", "
                + " o.estudiante,  "
                + " CAST( (" + complementoPromedio + "  "
                + "   + " + complementoPromedioBac + ") /2 AS DECIMAL(5,3)) AS PROM_APROV  "
                + " from Notasrecord as o where o.estudiante  "
                + "  in (Select m.estudiante from Matriculas as m, Cursos as cu "
                + "where cu.codigocur = m.curso and cu.secuencia = '" + cursoLlega.getSecuencia() + "'  and cu.periodo = '" + cursoLlega.getPeriodo().getCodigoper() + "' ) "
                + " order by  6 DESC ");
//        System.out.println(" Select " + complementoPromedio + ", " + complementoDisciplina + " "
//                + ", " + complementoPromedioBac + ", " + complementoDisciplinaBac + ", "
//                + " o.estudiante,  "
//                + " CAST( (" + complementoPromedio + "  "
//                + "   + " + complementoPromedioBac + ") /2 AS DECIMAL(5,3)) AS PROM_APROV  "
//                + " from Notasrecord as o where o.estudiante  "
//                + "  in (Select m.estudiante from Matriculas as m, Cursos as cu "
//                + "where cu.codigocur = m.curso and cu.secuencia = '"+cursoLlega.getSecuencia()+"'  and cu.periodo = '"+cursoLlega.getPeriodo().getCodigoper()+"' ) "
//                + " order by  6 DESC ");
        for (Iterator itna = notas.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            Nota n1 = new Nota();
            //n1.setCurso(cursoLlega);
            n1.setP1(new BigDecimal(vec.get(0) == null ? "0" : vec.get(0) + ""));
            n1.setD1(new BigDecimal(vec.get(1) == null ? "0" : vec.get(1) + ""));
            n1.setP2(new BigDecimal(vec.get(2) == null ? "0" : vec.get(2) + ""));
            n1.setD2(new BigDecimal(vec.get(3) == null ? "0" : vec.get(3) + ""));
            n1.setEstudiante((Estudiantes) adm.buscarClave(vec.get(4), Estudiantes.class));
            try {
                List<Cursos> matri = adm.query("Select o.curso from Matriculas as o  "
                        + "where o.estudiante.codigoest = '" + n1.getEstudiante().getCodigoest() + "' "
                        + "and o.curso.periodo.codigoper = '" + cursoLlega.getPeriodo().getCodigoper() + "' ");
                n1.setCurso(matri.get(0));
            } catch (Exception e) {
            }
            n1.setP3(new BigDecimal(vec.get(5) == null ? "0" : vec.get(5) + ""));
            listaResultados.add(n1);

        }
        ReporteRecordDataSource ds = new ReporteRecordDataSource(listaResultados);
        return ds;
    }

    public JRDataSource recordporcursoMejorCursoFormula(Cursos cursoLlega, String formula, String formulaDisc) {
        Administrador adm = new Administrador();
//        Session ses = Sessions.getCurrent();
//        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        ArrayList listaResultados = new ArrayList();
        int secuencia = cursoLlega.getSecuencia();
        String complementoPromedio = " CAST((o.segundob + o.tercerob + o.cuartob + o.quintob + o.sextob + o.septimob + o.primero + o.segundo+ o.tercero) / 9  AS DECIMAL(5,3))  ";
        String complementoDisciplina = " CAST((o.segundobd + o.tercerobd + o.cuartobd + o.quintobd + o.sextobd + o.septimobd + o.primerod + o.segundod+ o.tercerd) / 9 AS DECIMAL(5,3)) ";
        String complementoPromedioBac = "" + formula;
        String complementoDisciplinaBac = "" + formulaDisc;
//        if (secuencia == 11) {
//            complementoPromedioBac = "  CAST((o.primero + o.segundo+ o.tercero+ o.cuarto )/4   AS DECIMAL(5,3)) ";
//            complementoDisciplinaBac = " CAST((o.primerod + o.segundod+ o.tercerd+o.cuartod )/4 AS DECIMAL(5,3))";
//        } else if (secuencia == 12) {
//            complementoPromedioBac = "  CAST((o.primero/2 + o.segundo/2 + o.tercero/2 + o.cuarto/2 + o.quinto) / 5 AS DECIMAL(5,3)) ";
//            complementoDisciplinaBac = "  CAST((o.primerod + o.segundod+ o.tercerd+o.cuartod + o.quintod) / 5 AS DECIMAL(5,3))";
//        } else if (secuencia == 13) {
//            complementoPromedioBac = "  CAST((o.primero + o.segundo+ o.tercero+ o.cuarto + o.quinto+ o.sexto ) / 6 AS DECIMAL(5,3)) ";
        //complementoDisciplinaBac = "  CAST((o.primerod + o.segundod+ o.tercerd+o.cuartod + o.quintod+ o.sextod ) / 6 AS DECIMAL(5,3))";
//        }
        String querString = " Select " + complementoPromedio + ", " + complementoDisciplina + " "
                + ", " + complementoPromedioBac + ", " + complementoDisciplinaBac + ", "
                + " o.estudiante,  "
                + " CAST( (" + complementoPromedio + "  )  AS DECIMAL(5,3)) AS PROM_APROV  "
                + " from Notasrecord as o where o.estudiante  "
                + "  in (Select m.estudiante from Matriculas as m, Cursos as cu "
                + "where cu.codigocur = m.curso and cu.secuencia = '" + cursoLlega.getSecuencia() + "'  and cu.periodo = '" + cursoLlega.getPeriodo().getCodigoper() + "' ) "
                + " order by  3 DESC ";
        List notas = adm.queryNativo(querString);
        System.out.println("Q82: " + querString);

        for (Iterator itna = notas.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            Nota n1 = new Nota();
            //n1.setCurso(cursoLlega);
            n1.setP1(new BigDecimal(vec.get(0) == null ? "0" : vec.get(0) + ""));
            n1.setD1(new BigDecimal(vec.get(1) == null ? "0" : vec.get(1) + ""));
            n1.setP2(new BigDecimal(vec.get(2) == null ? "0" : vec.get(2) + ""));
            n1.setD2(new BigDecimal(vec.get(3) == null ? "0" : vec.get(3) + ""));
            n1.setEstudiante((Estudiantes) adm.buscarClave(vec.get(4), Estudiantes.class));
            try {
                List<Cursos> matri = adm.query("Select o.curso from Matriculas as o  "
                        + "where o.estudiante.codigoest = '" + n1.getEstudiante().getCodigoest() + "' "
                        + "and o.curso.periodo.codigoper = '" + cursoLlega.getPeriodo().getCodigoper() + "' ");
                n1.setCurso(matri.get(0));
            } catch (Exception e) {
            }
            n1.setP3(new BigDecimal(vec.get(5) == null ? "0" : vec.get(5) + ""));
            listaResultados.add(n1);

        }
        ReporteRecordDataSource ds = new ReporteRecordDataSource(listaResultados);
        return ds;
    }

    public JRDataSource recordporcursoMejorCurso8a2(Cursos cursoLlega) {
        Administrador adm = new Administrador();
//        Session ses = Sessions.getCurrent();
//        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        ArrayList listaResultados = new ArrayList();
        int secuencia = cursoLlega.getSecuencia();
        String complementoPromedio = " CAST((o.segundob + o.tercerob + o.cuartob + o.quintob + o.sextob + o.septimob + o.primero + o.segundo+ o.tercero) / 9  AS DECIMAL(5,3))  ";
        String complementoDisciplina = " CAST((o.segundobd + o.tercerobd + o.cuartobd + o.quintobd + o.sextobd + o.septimobd + o.primerod + o.segundod+ o.tercerd) / 9 AS DECIMAL(5,3)) ";
        String complementoPromedioBac = "";
        String complementoDisciplinaBac = "";
        if (secuencia == 11) {
            complementoPromedioBac = "  CAST((o.primero + o.segundo+ o.tercero+ o.cuarto )/4   AS DECIMAL(5,3)) ";
            complementoDisciplinaBac = " CAST((o.primerod + o.segundod+ o.tercerd+o.cuartod )/4 AS DECIMAL(5,3))";
        } else if (secuencia == 12) {
            complementoPromedioBac = "  CAST((o.primero/2 + o.segundo/2 + o.tercero/2 + o.cuarto/2 + o.quinto) / 5 AS DECIMAL(5,3)) ";
            complementoDisciplinaBac = "  CAST((o.primerod + o.segundod+ o.tercerd+o.cuartod + o.quintod) / 5 AS DECIMAL(5,3))";
        } else if (secuencia == 13) {
            complementoPromedioBac = "  CAST((o.primero + o.segundo+ o.tercero+ o.cuarto + o.quinto+ o.sexto ) / 6 AS DECIMAL(5,3)) ";
            complementoDisciplinaBac = "  CAST((o.primerod + o.segundod+ o.tercerd+o.cuartod + o.quintod+ o.sextod ) / 6 AS DECIMAL(5,3))";
        }
        String querString = " Select " + complementoPromedio + ", " + complementoDisciplina + " "
                + ", " + complementoPromedioBac + ", " + complementoDisciplinaBac + ", "
                + " o.estudiante,  "
                + " CAST( (" + complementoPromedio + "  )  AS DECIMAL(5,3)) AS PROM_APROV  "
                + " from Notasrecord as o where o.estudiante  "
                + "  in (Select m.estudiante from Matriculas as m, Cursos as cu "
                + "where cu.codigocur = m.curso and cu.secuencia = '" + cursoLlega.getSecuencia() + "'  and cu.periodo = '" + cursoLlega.getPeriodo().getCodigoper() + "' ) "
                + " order by  3 DESC ";
        List notas = adm.queryNativo(querString);
        System.out.println("Q82: " + querString);

        for (Iterator itna = notas.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            Nota n1 = new Nota();
            //n1.setCurso(cursoLlega);
            n1.setP1(new BigDecimal(vec.get(0) == null ? "0" : vec.get(0) + ""));
            n1.setD1(new BigDecimal(vec.get(1) == null ? "0" : vec.get(1) + ""));
            n1.setP2(new BigDecimal(vec.get(2) == null ? "0" : vec.get(2) + ""));
            n1.setD2(new BigDecimal(vec.get(3) == null ? "0" : vec.get(3) + ""));
            n1.setEstudiante((Estudiantes) adm.buscarClave(vec.get(4), Estudiantes.class));
            try {
                List<Cursos> matri = adm.query("Select o.curso from Matriculas as o  "
                        + "where o.estudiante.codigoest = '" + n1.getEstudiante().getCodigoest() + "' "
                        + "and o.curso.periodo.codigoper = '" + cursoLlega.getPeriodo().getCodigoper() + "' ");
                n1.setCurso(matri.get(0));
            } catch (Exception e) {
            }
            n1.setP3(new BigDecimal(vec.get(5) == null ? "0" : vec.get(5) + ""));
            listaResultados.add(n1);

        }
        ReporteRecordDataSource ds = new ReporteRecordDataSource(listaResultados);
        return ds;
    }

    public JRDataSource recordporcursoMejorCurso8a3(Cursos cursoLlega) {
        Administrador adm = new Administrador();
//        Session ses = Sessions.getCurrent();
//        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        ArrayList listaResultados = new ArrayList();
        int secuencia = cursoLlega.getSecuencia();
        String complementoPromedio = " CAST((o.segundob + o.tercerob + o.cuartob + o.quintob + o.sextob + o.septimob + o.primero + o.segundo+ o.tercero) / 9  AS DECIMAL(5,3))  ";
        String complementoDisciplina = " CAST((o.segundobd + o.tercerobd + o.cuartobd + o.quintobd + o.sextobd + o.septimobd + o.primerod + o.segundod+ o.tercerd) / 9 AS DECIMAL(5,3)) ";
        String complementoPromedioBac = "";
        String complementoDisciplinaBac = "";
        if (secuencia == 11) {
            complementoPromedioBac = "  CAST((o.primero + o.segundo+ o.tercero+ o.cuarto )/4   AS DECIMAL(5,3)) ";
            complementoDisciplinaBac = " CAST((o.primerod + o.segundod+ o.tercerd+o.cuartod )/4 AS DECIMAL(5,3))";
        } else if (secuencia == 12) {
            complementoPromedioBac = "  CAST((o.primero + o.segundo+ o.tercero + o.cuarto + o.quinto) / 5 AS DECIMAL(5,3)) ";
            complementoDisciplinaBac = "  CAST((o.primerod + o.segundod+ o.tercerd+o.cuartod + o.quintod) / 5 AS DECIMAL(5,3))";
        } else if (secuencia == 13) {
            //complementoPromedioBac = " CAST((o.primero + o.segundo+ o.tercero+ o.cuarto + o.quinto) / 5 AS DECIMAL(5,3))  + CAST((CAST((o.primero + o.segundo+ o.tercero+ o.cuarto + o.quinto) / 5 AS DECIMAL(5,3)) + o.sexto ) / 6 AS DECIMAL(5,3)) ";
            complementoPromedioBac = "  CAST((o.primero/2 + o.segundo/2+ o.tercero/2+ o.cuarto/2 + o.quinto/2+ o.sexto ) / 6 AS DECIMAL(5,3)) ";
            complementoDisciplinaBac = "  CAST((o.primerod/2 + o.segundod/2+ o.tercerd/2+o.cuartod/2 + o.quintod/2+ o.sextod ) / 6 AS DECIMAL(5,3))";
//            complementoPromedioBac = "  CAST((o.primero + o.segundo+ o.tercero+ o.cuarto + o.quinto+ o.sexto ) / 6 AS DECIMAL(5,3)) ";
//            complementoDisciplinaBac = "  CAST((o.primerod + o.segundod+ o.tercerd+o.cuartod + o.quintod+ o.sextod ) / 6 AS DECIMAL(5,3))";
        }
        String querString = " Select " + complementoPromedio + ", " + complementoDisciplina + " "
                + ", " + complementoPromedioBac + ", " + complementoDisciplinaBac + ", "
                + " o.estudiante,  "
                + " CAST( (" + complementoPromedio + "  )  AS DECIMAL(5,3)) AS PROM_APROV  "
                + " from Notasrecord as o where o.estudiante  "
                + "  in (Select m.estudiante from Matriculas as m, Cursos as cu "
                + "where cu.codigocur = m.curso and cu.secuencia = '" + cursoLlega.getSecuencia() + "'  and cu.periodo = '" + cursoLlega.getPeriodo().getCodigoper() + "' ) "
                + " order by  3 DESC ";
        List notas = adm.queryNativo(querString);
        System.out.println("Q82: " + querString);

        for (Iterator itna = notas.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            Nota n1 = new Nota();
            //n1.setCurso(cursoLlega);
            n1.setP1(new BigDecimal(vec.get(0) == null ? "0" : vec.get(0) + ""));
            n1.setD1(new BigDecimal(vec.get(1) == null ? "0" : vec.get(1) + ""));
            n1.setP2(new BigDecimal(vec.get(2) == null ? "0" : vec.get(2) + ""));
            n1.setD2(new BigDecimal(vec.get(3) == null ? "0" : vec.get(3) + ""));
            n1.setEstudiante((Estudiantes) adm.buscarClave(vec.get(4), Estudiantes.class));
            try {
                List<Cursos> matri = adm.query("Select o.curso from Matriculas as o  "
                        + "where o.estudiante.codigoest = '" + n1.getEstudiante().getCodigoest() + "' "
                        + "and o.curso.periodo.codigoper = '" + cursoLlega.getPeriodo().getCodigoper() + "' ");
                n1.setCurso(matri.get(0));
            } catch (Exception e) {
            }
            n1.setP3(new BigDecimal(vec.get(5) == null ? "0" : vec.get(5) + ""));
            listaResultados.add(n1);

        }
        ReporteRecordDataSource ds = new ReporteRecordDataSource(listaResultados);
        return ds;
    }

    public JRDataSource recordporespecialidad(Global especialidad, Integer secuencia) {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        ArrayList listaResultados = new ArrayList();
        List<Notasrecord> notas = adm.query("Select o from Notasrecord as o"
                + " where o.estudiante.codigoest "
                + " in (Select  m.estudiante.codigoest from Matriculas as m where m.curso.especialidad.codigo = '" + especialidad.getCodigo() + "' "
                + "and m.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' and m.curso.secuencia = '" + secuencia + "' ) "
                + "order by o.estudiante.apellido ");
        for (Iterator<Notasrecord> it = notas.iterator(); it.hasNext();) {
            Notasrecord n = it.next();
            Nota n1 = new Nota();

            n1.setEstudiante(n.getEstudiante());
            n1.setP1(new BigDecimal(n.getPrimero() == null ? 0 : n.getPrimero()));
            n1.setP2(new BigDecimal(n.getSegundo() == null ? 0 : n.getSegundo()));
            n1.setP3(new BigDecimal(n.getTercero() == null ? 0 : n.getTercero()));
            n1.setP4(new BigDecimal(n.getCuarto() == null ? 0 : n.getCuarto()));
            n1.setP5(new BigDecimal(n.getQuinto() == null ? 0 : n.getQuinto()));
            n1.setP6(new BigDecimal(n.getSexto() == null ? 0 : n.getSexto()));
            try {
                n1.setPromedio2((promedio(n1.getP1(), n1.getP2(), n1.getP3(), n1.getP4(), n1.getP5(), n1.getP6())));
            } catch (Exception e) {
            }
            n1.setD1(new BigDecimal(n.getPrimerod() == null ? 0 : n.getPrimerod()));
            n1.setD2(new BigDecimal(n.getSegundod() == null ? 0 : n.getSegundod()));
            n1.setD3(new BigDecimal(n.getTercerd() == null ? 0 : n.getTercerd()));
            n1.setD4(new BigDecimal(n.getCuartod() == null ? 0 : n.getCuartod()));
            n1.setD5(new BigDecimal(n.getQuintod() == null ? 0 : n.getQuintod()));
            n1.setD6(new BigDecimal(n.getSextod() == null ? 0 : n.getSextod()));
            try {
                n1.setDisciplina2((promedio(n1.getD1(), n1.getD2(), n1.getD3(), n1.getD4(), n1.getD5(), n1.getD6())));

            } catch (Exception e) {
            }
            listaResultados.add(n1);
        }
        ReporteRecordDataSource ds = new ReporteRecordDataSource(listaResultados);
        return ds;
    }

    public BigDecimal promedio(BigDecimal n1, BigDecimal n2, BigDecimal n3, BigDecimal n4, BigDecimal n5, BigDecimal n6) {
        Integer dividendo = 0;
        try {
            if (n1.doubleValue() > 0) {
                dividendo += 1;
            }
            if (n2.doubleValue() > 0) {
                dividendo += 1;
            }
            if (n3.doubleValue() > 0) {
                dividendo += 1;
            }
            if (n4.doubleValue() > 0) {
                dividendo += 1;
            }
            if (n5.doubleValue() > 0) {
                dividendo += 1;
            }
            if (n6.doubleValue() > 0) {
                dividendo += 1;
            }

            BigDecimal total = n1.add(n2).add(n3).add(n4).add(n5).add(n6);
            total = total.divide(new BigDecimal(dividendo), 3, RoundingMode.HALF_UP);
            return total;
        } catch (Exception e) {
            System.out.println("" + e);
            return new BigDecimal(0.0);
        }
    }
    String truncar = "public Double truncar(Double numero, int decimales) {         try {             java.math.BigDecimal d = new java.math.BigDecimal(numero+\");             d = d.setScale(decimales, java.math.BigDecimal.ROUND_DOWN);             return d.doubleValue();         } catch (Exception e) {             return 0.0;         }     }";

    public Double truncar(Double numero, int decimales) {
        try {
            BigDecimal d = new BigDecimal(numero + "");
            d = d.setScale(decimales, java.math.BigDecimal.ROUND_DOWN);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }

    public Double redondear(Double numero, int decimales) {
        try {

            BigDecimal d = new BigDecimal(numero + "");
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

        return mes;
    }

    public JRDataSource matriculadosxgenero() {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");

        List nativo = adm.queryNativo(" SELECT cur.descripcion, esp.descripcion, par.descripcion, est.genero, "
                + " COUNT(est.genero) FROM matriculas mat, estudiantes est, cursos cur, GLOBAL par, GLOBAL esp "
                + " WHERE mat.estudiante = est.codigoest  AND cur.codigocur = mat.curso"
                + " AND cur.paralelo = par.codigo AND cur.especialidad = esp.codigo "
                + "  AND cur.periodo = '" + periodo.getCodigoper() + "'   "
                + " AND mat.estado ='Matriculado'"
                + " GROUP BY genero, cur.descripcion, par.descripcion "
                + " ORDER BY cur.secuencia, cur.descripcion,esp.descripcion, par.descripcion, est.genero DESC   ");


        List<ConteoMatriculas> lisNotas = new ArrayList();
        int i = 1;
        String cursoAnte = "";
        String paraleAnte = "";
        String espeAnte = "";
        Integer masculino = 0;
        Integer femenino = 0;
        ConteoMatriculas cTotal = new ConteoMatriculas();
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            ConteoMatriculas c = new ConteoMatriculas();
            c.setNumero(i);
            c.setNombrecurso(vec.get(0).toString());
            c.setEspecialidad(vec.get(1).toString());
            c.setParalelo(vec.get(2).toString());
            c.setGenero(vec.get(3).toString());
            c.setConteo(new Integer(vec.get(4).toString()));
            if (c.getGenero().contains("Masc")) {
                masculino += c.getConteo();
            } else {
                femenino += c.getConteo();
            }
            if (cursoAnte.equals("")) {
                cursoAnte = c.getNombrecurso();
            }
            if (!cursoAnte.equals(c.getNombrecurso())) {


                cursoAnte = c.getNombrecurso();
                i++;
                c.setNumero(i);
            }
            lisNotas.add(c);

        }

        ReporteMatriculadosDataSource ds = new ReporteMatriculadosDataSource(lisNotas);
        return ds;

    }

    public JRDataSource promediosxcursoymateria(Sistemacalificacion sistema, Cursos curso) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis  = '" + sistema.getCodigosis() + "' "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "  "
                + "order by o.sistema.orden ");
        List<Cursos> cursos = new ArrayList<Cursos>();
        List<Global> map = new ArrayList<Global>();
        if (curso.getCodigocur().equals(-2)) {
            cursos = adm.query("Select o from Cursos as o where o.periodo.codigoper = '" + periodo.getCodigoper() + "' order by o.secuencia, o.paralelo.descripcion ");
            map = adm.query("Select DISTINCT o.materia from MateriaProfesor as o "
                    + " where o.curso.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                    + " and o.ministerio = true order by o.orden");
        } else {
            cursos = adm.query("Select o from Cursos as o where o.secuencia = '" + curso.getSecuencia() + "' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' order by o.secuencia, o.paralelo.descripcion ");
            map = adm.query("Select DISTINCT o.materia from MateriaProfesor as o "
                    + " where o.curso.codigocur = '" + curso.getCodigocur() + "'  "
                    + " and o.ministerio = true order by o.orden");
        }




        List<ConteoMatriculas> lisNotas = new ArrayList();
        int i = 1;
        for (Iterator<Cursos> it = cursos.iterator(); it.hasNext();) {
            Cursos cursos1 = it.next();
//               List<MateriaProfesor> map = adm.query("Select o from MateriaProfesor as o "
//                                    + " where o.curso.codigocur = '"+cursos1.getCodigocur()+"'  and o.ministerio = true order by o.orden");
            for (Iterator<Global> itMap = map.iterator(); itMap.hasNext();) {
                Global mapProfesor = itMap.next();
                List nativo = adm.queryNativo(" Select avg(" + notas.get(0).getNota() + ") from notas "
                        + " where materia = '" + mapProfesor.getCodigo() + "' "
                        + "and  matricula in (Select codigomat from matriculas where curso = '" + cursos1.getCodigocur() + "' )  ");
                for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();
                    ConteoMatriculas c = new ConteoMatriculas();
                    c.setNumero(i);
                    c.setNombrecurso(cursos1.getSecuencia() + ".-" + cursos1.getDescripcion());
                    c.setEspecialidad(cursos1.getEspecialidad().getDescripcion());
                    c.setParalelo(cursos1.getParalelo().getDescripcion());
                    c.setGenero("");
                    c.setConteo(1);
                    if (vec.get(0) != null) {
                        c.setPromedio(new BigDecimal(vec.get(0) + ""));
                    } else {
                        c.setPromedio(null);
                    }
                    c.setMateria(mapProfesor.getDescripcion());
                    lisNotas.add(c);
                }


            }
        }



        ReporteMatriculadosDataSource ds = new ReporteMatriculadosDataSource(lisNotas);
        return ds;

    }
    //REPORTE DE RENDIMIENTO POR CURSO

    public JRDataSource cuadrocalificacionesestadistico(Cursos curso, Sistemacalificacion sistema) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
//                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
//        Double numeroDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
//        List sistemas = adm.query("Select o from Sistemacalificacion as o "
//                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.orden <= '" + sistema.getOrden() + "' order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis  = '" + sistema.getCodigosis() + "' "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "  "
                + "order by o.sistema.orden ");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");


        String query = "";
        int numeroEquivalencias = 0;
        for (Notanotas notass : notas) {
            //query += notass.getNota() + ",";

            for (Iterator<Equivalencias> itEquiva = equivalencias.iterator(); itEquiva.hasNext();) {
                Equivalencias eqIt = itEquiva.next();
                query += "COUNT(IF(" + notass.getNota() + " >= (" + eqIt.getValorminimo() + ") AND " + notass.getNota() + " <= (" + eqIt.getValormaximo() + "),1,NULL)), cast(COUNT(IF(" + notass.getNota() + ">= (" + eqIt.getValorminimo() + ") AND " + notass.getNota() + " <= (" + eqIt.getValormaximo() + "),1,NULL)) * 100 / COUNT(" + notass.getNota() + ") as decimal(9,2) ),";
                numeroEquivalencias++;
            }
            query += " COUNT(IF(mat.estado = 'Matriculado',1,NULL)),COUNT(IF(mat.estado != 'Matriculado',1,NULL)), AVG(" + notass.getNota() + ") ";
        }

        //query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String q = "Select  mate.descripcion, " + query + "  "
                + "from notas, materia_profesor, matriculas mat, global mate  "
                + "where  materia_profesor.materia = mate.codigo "
                + "AND  notas.materia =  materia_profesor.materia and materia_profesor.curso = '" + curso.getCodigocur() + "' "
                + " AND notas.matricula = mat.codigomat  "
                + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "' "
                + " and estado in ('Matriculado','Recibir Pase','Emitir Pase','Retirado') )"
                + "and notas.disciplina = false and materia_profesor.seimprime = true  "
                + "GROUP BY notas.materia "
                + "order by materia_profesor.orden ";
        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        List<EstadisticoPorcentajes> lisNotas = new ArrayList();

        int iVec = 0;
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            iVec++;
            EstadisticoPorcentajes est = new EstadisticoPorcentajes();
            try {


                int em = 1;
                for (Iterator<Equivalencias> itEquiva = equivalencias.iterator(); itEquiva.hasNext();) {
                    Equivalencias eqIt = itEquiva.next();
                    est = new EstadisticoPorcentajes();
                    est.setEquivalencia(eqIt.getNombre());
                    est.setTipo("No.");
                    est.setValor(new Integer(vec.get(em).toString()));
                    est.setNumero(iVec);
                    est.setContador(iVec);
                    est.setMateria(vec.get(0).toString());
                    est.setPromedio(new Double(vec.get((numeroEquivalencias * 2) + 3).toString()));
                    est.setMatriculados(new Integer(vec.get((numeroEquivalencias * 2) + 1).toString()));
                    est.setNomatriculados(new Integer(vec.get((numeroEquivalencias * 2) + 2).toString()));
                    lisNotas.add(est);
                    em++;
                    est = new EstadisticoPorcentajes();
                    est.setEquivalencia(eqIt.getNombre());
                    est.setTipo("%");
                    est.setValor(new Double(vec.get(em).toString()));
                    est.setNumero(iVec);
                    est.setContador(iVec);
                    est.setMateria(vec.get(0).toString());
                    est.setPromedio(new Double(vec.get((numeroEquivalencias * 2) + 3).toString()));
                    est.setMatriculados(new Integer(vec.get((numeroEquivalencias * 2) + 1).toString()));
                    est.setNomatriculados(new Integer(vec.get((numeroEquivalencias * 2) + 2).toString()));
                    lisNotas.add(est);
                    em++;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
//                est = new  EstadisticoPorcentajes();
//                est.setEquivalencia("Matriculados");
//                est.setTipo("No.");
//                est.setMatriculados(new Integer(vec.get((numeroEquivalencias*2)+1).toString()));  
//                est.setNomatriculados(new Integer(vec.get((numeroEquivalencias*2)+2).toString())); 
//                est.setPromedio(new Double(vec.get((numeroEquivalencias*2)+3).toString()));
//                est.setNumero(iVec); est.setContador(iVec);
//                est.setMateria(vec.get(0).toString()); 
//                est.setPromedio(new Double(vec.get((numeroEquivalencias*2)+3).toString()));
//                lisNotas.add(est); 
            //numeroEquivalencias

        }


        ReporteEstadisticoDataSource ds = new ReporteEstadisticoDataSource(lisNotas);
        return ds;

    }

    public JRDataSource cuadrocalificacionesestadisticodistributivo(Sistemacalificacion sistema, Double desde, Double hasta) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        Double numeroDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.orden <= '" + sistema.getOrden() + "' order by o.orden ");

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis  = '" + sistema.getCodigosis() + "' "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "  "
                + "order by o.sistema.orden ");

        String query = "";
        int numeroEquivalencias = 0;
        for (Notanotas notass : notas) {
//                for (Iterator<Equivalencias> itEquiva = equivalencias.iterator(); itEquiva.hasNext();) {
//                    Equivalencias eqIt = itEquiva.next();
//                        query += "COUNT(IF("+notass.getNota() + " > ("+eqIt.getValorminimo()+") AND "+notass.getNota() + " <= ("+eqIt.getValormaximo()+"),1,NULL)), cast(COUNT(IF("+notass.getNota() + "> ("+eqIt.getValorminimo()+") AND "+notass.getNota() + " <= ("+eqIt.getValormaximo()+"),1,NULL)) * 100 / COUNT("+notass.getNota() + ") as decimal(9,2) ),";
//                         numeroEquivalencias++;
//                }
            query += " AVG(" + notass.getNota() + "), COUNT(" + notass.getNota() + ") TOTAL, "
                    + "COUNT(IF((" + notass.getNota() + ") >= " + desde + " and (" + notass.getNota() + ") <= " + hasta + "   ,1,NULL)) CONPROBLEMAS, "
                    + "COUNT(IF((" + notass.getNota() + ") >= " + desde + " and (" + notass.getNota() + ") <= " + hasta + "   ,1,NULL)) * 100 / COUNT(" + notass.getNota() + ")  "
                    + " ";
        }
        List<MateriaProfesor> materProfesores = adm.query("Select o from MateriaProfesor as o "
                + "where o.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "order by o.empleado.apellidos, o.curso.secuencia ");
        List<EstadisticoPorcentajes> lisNotas = new ArrayList();
        for (Iterator<MateriaProfesor> itMapProfe = materProfesores.iterator(); itMapProfe.hasNext();) {
            MateriaProfesor mateProfesor = itMapProfe.next();
            //query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
            String q = "Select  " + query + "  "
                    + "from notas, materia_profesor, matriculas mat, global mate  "
                    + "where  materia_profesor.materia = mate.codigo "
                    + "AND  notas.materia =  materia_profesor.materia and materia_profesor.curso = '" + mateProfesor.getCurso().getCodigocur() + "' "
                    + " AND notas.matricula = mat.codigomat  "
                    + "and matricula in (select codigomat from matriculas where  curso  =  '" + mateProfesor.getCurso().getCodigocur() + "' "
                    + " and estado in ('Matriculado','Recibir Pase','Emitir Pase','Retirado') )"
                    + " and notas.disciplina = false and materia_profesor.seimprime = true  "
                    + " and notas.materia = '" + mateProfesor.getMateria().getCodigo() + "' "
                    + " GROUP BY notas.materia "
                    + " order by materia_profesor.orden ";
            System.out.println("" + q);
            List nativo = adm.queryNativo(q);

            int iVec = 0;
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                iVec++;
                EstadisticoPorcentajes est = new EstadisticoPorcentajes();

                est = new EstadisticoPorcentajes();
                est.setEmpleado(mateProfesor.getEmpleado());
                est.setCurso(mateProfesor.getCurso());
                est.setPromedio(new Double(vec.get(0).toString()));
                est.setTotal(new Integer(vec.get(1).toString()));
                est.setContador(new Integer(vec.get(2).toString()));
                est.setPorcentaje(new Double(vec.get(3).toString()));


                est.setMateria(mateProfesor.getMateria().getDescripcion());
                lisNotas.add(est);

            }

        }


        ReporteEstadisticoDataSource ds = new ReporteEstadisticoDataSource(lisNotas);
        return ds;

    }

    public JRDataSource cuadrocalificacionesestadisticodistributivo(Sistemacalificacion sistema, List cursos, List rangos) {
//  RANGOS


        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        ArrayList equiva = new ArrayList<Equivalencias>();
        for (int i = 0; i < rangos.size(); i++) {
            Row object = (Row) rangos.get(i);
            List labels = object.getChildren();
            Equivalencias eq = new Equivalencias();
            eq.setValorminimo((((Doublebox) labels.get(0)).getValue()));
            eq.setValormaximo((((Doublebox) labels.get(1)).getValue()));
            eq.setNombre((((Textbox) labels.get(2)).getValue()));
            equiva.add(eq);
        }
        Periodo periodo = (Periodo) ses.getAttribute("periodo");


        List<Periodo> periodos = adm.query("Select o from Periodo as o "
                + " where o.institucion.codigoinst = '" + periodo.getInstitucion().getCodigoinst() + "' "
                + "and o.cerrado = true "
                + "order by o.codigoper ");
        List<NotasPromedios> arregloPromedios = new ArrayList<NotasPromedios>();
        String s = "###0.00##";
        DecimalFormat decimalFormat = new DecimalFormat(s);
        for (Iterator<Periodo> itPer = periodos.iterator(); itPer.hasNext();) {
            Periodo periodoIt = itPer.next();
            List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                    + " where o.sistema.periodo.codigoper = '" + periodoIt.getCodigoper() + "'  "
                    + "and o.sistema.promediofinal = 'PF' ");
            for (int i = 1; i < 10; i++) {
                List<Matriculas> matri = adm.query("Select o from Matriculas as o "
                        + "where o.curso.periodo.codigoper = '" + periodoIt.getCodigoper() + "' "
                        + "and o.curso.secuencia = '" + i + "'  and o.estado in ('Matriculado','Recibir Pase') "
                        + "order by o.codigomat ");
                List<Global> globalMat = adm.query("Select o.materia from MateriaProfesor as o "
                        + " where o.curso.secuencia = '" + i + "' "
                        + " and o.curso.periodo.codigoper = '" + periodoIt.getCodigoper() + "'  "
                        + " and o.materia.descripcion like '%PROMEDIO DE APROVECHAMIENTO%' ");
                Global materiaA = new Global(-1);
                if (globalMat.size() > 0) {
                    materiaA = globalMat.get(0);
                }
                Integer totalMatriculados = matri.size();
                Integer rango0a10 = 0;
                Integer rango11a15 = 0;
                Integer rango16a18 = 0;
                Integer rango19a20 = 0;
                Cursos curso = new Cursos();
                if (matri.size() > 0) {
                    curso = matri.get(0).getCurso(); //validar
                    for (Iterator<Matriculas> itMatri = matri.iterator(); itMatri.hasNext();) {
                        Matriculas matriculas = itMatri.next();
                        String q = "Select o." + notas.get(0).getNota() + " from Notas as o "
                                + " where o.matricula.codigomat = '" + matriculas.getCodigomat() + "' "
                                + " and o.materia.codigo = '" + materiaA.getCodigo() + "' and o.disciplina = false";
                        List notasList = adm.query(q);
                        //System.out.println(""+q+" UNION "); 
                        Double valorActual = 0.0;
                        if (notasList != null) {
                            try {
                                valorActual = (Double) notasList.get(0);
                            } catch (Exception e) {
                                System.out.println("" + e);
                                valorActual = 0.0;
                            }
                        } else {
                            valorActual = 0.0;
                        }


                        if (valorActual >= ((Equivalencias) equiva.get(0)).getValorminimo() && valorActual < ((Equivalencias) equiva.get(0)).getValormaximo()) {
                            rango0a10 += 1;
                        } else if (valorActual >= ((Equivalencias) equiva.get(1)).getValorminimo() && valorActual < ((Equivalencias) equiva.get(1)).getValormaximo()) {
                            rango11a15 += 1;
                        } else if (valorActual >= ((Equivalencias) equiva.get(2)).getValorminimo() && valorActual < ((Equivalencias) equiva.get(2)).getValormaximo()) {
                            rango16a18 += 1;
                        } else if (valorActual >= ((Equivalencias) equiva.get(3)).getValorminimo() && valorActual <= ((Equivalencias) equiva.get(3)).getValormaximo()) {
                            rango19a20 += 1;
                        }
                    }
                    String nombreCurso = i + ".-" + curso.getDescripcion() + "\n Estudiantes en el rango";
//                        if(i==1){
//                            nombreCurso = "8vo.Año Básico\n Estudiantes en el rango";
//                        }else if(i==2){
//                            nombreCurso = "9no.Año Básico\n Estudiantes en el rango";
//                        }else if(i==3){
//                            nombreCurso = "10mo.Año Básico\n Estudiantes en el rango";
//                        }else if(i==4){
//                            nombreCurso = "Primero\n Estudiantes en el rango";
//                        }else if(i==5){
//                            nombreCurso = "Segundo\n Estudiantes en el rango";
//                        }else if(i==6){
//                            nombreCurso = "Tercero\n Estudiantes en el rango";
//                        }
                    //0 A 10 
                    NotasPromedios notaP = new NotasPromedios();
                    notaP.setAnioLectivo(periodoIt.getDescripcion().substring(0, 4) + "\nValores Absolutos");
                    notaP.setTitulo("Resultados de aprendizajes de los estudiantes de Educación Básica");
                    notaP.setAnioBasica(nombreCurso);
                    notaP.setSubtitulo("Estudiantes en el rango");
                    notaP.setCantidad(rango0a10 + "");
                    notaP.setRango("0 - 10");
                    notaP.setRango("" + ((Equivalencias) equiva.get(0)).getNombre());
                    arregloPromedios.add(notaP);

                    notaP = new NotasPromedios();
                    notaP.setAnioLectivo(periodoIt.getDescripcion().substring(0, 4) + " \nPorcentajes");
                    notaP.setTitulo("Resultados de aprendizajes de los estudiantes de Educación Básica");
                    notaP.setAnioBasica(nombreCurso);
                    notaP.setSubtitulo("Estudiantes en el rango");
                    Double perc = (rango0a10 * 100) / new Double(totalMatriculados);
                    notaP.setCantidad(decimalFormat.format(redondear(perc, 2)) + "");
                    notaP.setRango("0 - 10");
                    notaP.setRango("" + ((Equivalencias) equiva.get(0)).getNombre());
                    arregloPromedios.add(notaP);
                    //11 A 15 
                    notaP = new NotasPromedios();
                    notaP.setAnioLectivo(periodoIt.getDescripcion().substring(0, 4) + "\nValores Absolutos");
                    notaP.setTitulo("Resultados de aprendizajes de los estudiantes de Educación Básica");
                    notaP.setAnioBasica(nombreCurso);
                    notaP.setSubtitulo("Estudiantes en el rango");
                    notaP.setCantidad(rango11a15 + "");
                    notaP.setRango("11 - 15");
                    notaP.setRango("" + ((Equivalencias) equiva.get(1)).getNombre());
                    arregloPromedios.add(notaP);

                    notaP = new NotasPromedios();
                    notaP.setAnioLectivo(periodoIt.getDescripcion().substring(0, 4) + " \nPorcentajes");
                    notaP.setTitulo("Resultados de aprendizajes de los estudiantes de Educación Básica");
                    notaP.setAnioBasica(nombreCurso);
                    notaP.setSubtitulo("Estudiantes en el rango");
                    perc = (rango11a15 * 100) / new Double(totalMatriculados);
                    notaP.setCantidad(decimalFormat.format(redondear(perc, 2)) + "");
                    notaP.setRango("11 - 15");
                    notaP.setRango("" + ((Equivalencias) equiva.get(1)).getNombre());
                    arregloPromedios.add(notaP);

                    //16 - 18 
                    notaP = new NotasPromedios();
                    notaP.setAnioLectivo(periodoIt.getDescripcion().substring(0, 4) + "\nValores Absolutos");
                    notaP.setTitulo("Resultados de aprendizajes de los estudiantes de Educación Básica");
                    notaP.setAnioBasica(nombreCurso);
                    notaP.setSubtitulo("Estudiantes en el rango");
                    notaP.setCantidad(rango16a18 + "");
                    notaP.setRango("16 - 18");
                    notaP.setRango("" + ((Equivalencias) equiva.get(2)).getNombre());
                    arregloPromedios.add(notaP);

                    notaP = new NotasPromedios();
                    notaP.setAnioLectivo(periodoIt.getDescripcion().substring(0, 4) + " \nPorcentajes");
                    notaP.setTitulo("Resultados de aprendizajes de los estudiantes de Educación Básica");
                    notaP.setAnioBasica(nombreCurso);
                    notaP.setSubtitulo("Estudiantes en el rango");
                    perc = (rango16a18 * 100) / new Double(totalMatriculados);
                    notaP.setCantidad(decimalFormat.format(redondear(perc, 2)) + "");
                    notaP.setRango("16 - 18");
                    notaP.setRango("" + ((Equivalencias) equiva.get(2)).getNombre());
                    arregloPromedios.add(notaP);

                    //19 - 20 
                    notaP = new NotasPromedios();
                    notaP.setAnioLectivo(periodoIt.getDescripcion().substring(0, 4) + "\nValores Absolutos");
                    notaP.setTitulo("Resultados de aprendizajes de los estudiantes de Educación Básica");
                    notaP.setAnioBasica(nombreCurso);
                    notaP.setSubtitulo("Estudiantes en el rango");
                    notaP.setCantidad(rango19a20 + "");
                    notaP.setRango("19 - 20");
                    notaP.setRango("" + ((Equivalencias) equiva.get(3)).getNombre());
                    arregloPromedios.add(notaP);

                    notaP = new NotasPromedios();
                    notaP.setAnioLectivo(periodoIt.getDescripcion().substring(0, 4) + " \nPorcentajes");
                    notaP.setTitulo("Resultados de aprendizajes de los estudiantes de Educación Básica");
                    notaP.setAnioBasica(nombreCurso);
                    notaP.setSubtitulo("Estudiantes en el rango");
                    perc = (rango19a20 * 100) / new Double(totalMatriculados);
                    notaP.setCantidad(decimalFormat.format(redondear(perc, 2)) + "");
                    notaP.setRango("19 - 20");
                    notaP.setRango("" + ((Equivalencias) equiva.get(3)).getNombre());
                    arregloPromedios.add(notaP);

                }
            }
        }

        PromediosDataSource ds = new PromediosDataSource(arregloPromedios);
        return ds;

    }

    public void aprobadosPerdidos() {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  "
                + "and o.sistema.promediofinal = 'PF' ");
        try {
            if (notas.size() <= 0) {
                Messagebox.show("No se ha parametrizado el PROMEDIO FINAL en los APORTES \n Puede obtener resultados no esperados", "Administrador Educativo", Messagebox.CANCEL, Messagebox.ERROR);
                return;
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
        }
        Integer totalPerdidos = 0;
        List<Cursos> cursos = adm.query("Select o from Cursos as o where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        for (Iterator<Cursos> it = cursos.iterator(); it.hasNext();) {
            Cursos curso = it.next();
            List<Matriculas> listaMatriculasPerdidos = cuadroverificar(curso, notas.get(0).getSistema(), new Matriculas(-1));
            totalPerdidos += listaMatriculasPerdidos.size();

        }
        System.out.println("TOTAL PERDIDOS: " + totalPerdidos);


    }

    public JRDataSource planificaciones(jcinform.persistencia.Planificacion plan) {
//  RANGOS
        //plan.setCodigo(1);
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        ArrayList equiva = new ArrayList<Equivalencias>();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List planificaciones = new ArrayList<PlanificacionSource>();
        List<jcinform.persistencia.Planificacion> datos = adm.query("Select o from Planificacion as o where o.codigo = '" + plan.getCodigo() + "' ");
        for (Iterator<jcinform.persistencia.Planificacion> it = datos.iterator(); it.hasNext();) {
            jcinform.persistencia.Planificacion pla = it.next();
            PlanificacionSource p = new PlanificacionSource();
            p.setBloques(pla.getBloque());
            p.setCurso(pla.getCurso() + "");
            p.setDuracion(pla.getDuracion());
            p.setEaprendizaje(pla.getEjeaprendizaje());
            p.setEintegrador(pla.getEjeintegrador());
            p.setEtransversal(pla.getEjetransversal());
            p.setFfin(pla.getFechafin());
            p.setFinicio(pla.getFechainicio());
            p.setMateria(pla.getMateria().getDescripcion());
            p.setObjetivos(pla.getObjetivos());
            p.setPlanificacion(pla.getCodigo());
            ArrayList<PlanificacionCollection> detaAna = new ArrayList<PlanificacionCollection>();
            List<PlanificacionDetalle> detalles = adm.query("Select o from PlanificacionDetalle as o where o.planificacion.codigo = '" + plan.getCodigo() + "' ");
            int m = 1;
            for (Iterator<PlanificacionDetalle> it1 = detalles.iterator(); it1.hasNext();) {
                PlanificacionDetalle pD = it1.next();
                if (m == 1) {
                    p.setDetalle1(pD.getDescripcion());
                    p.setTipo1(pD.getEvaluador().getNombre());
                } else if (m == 2) {
                    p.setDetalle2(pD.getDescripcion());
                    p.setTipo2(pD.getEvaluador().getNombre());
                } else if (m == 3) {
                    p.setDetalle3(pD.getDescripcion());
                    p.setTipo3(pD.getEvaluador().getNombre());
                } else if (m == 4) {
                    p.setDetalle4(pD.getDescripcion());
                    p.setTipo4(pD.getEvaluador().getNombre());
                } else if (m == 5) {
                    p.setDetalle5(pD.getDescripcion());
                    p.setTipo5(pD.getEvaluador().getNombre());
                }
                m++;
            }
            p.setListaDetalles(detaAna);
            planificaciones.add(p);
        }
        ReportePlanificacionDataSource ds = new ReportePlanificacionDataSource(planificaciones);
        return ds;

    }
    public int contador1 = 0, contador2 = 0, contador3 = 0, contador4 = 0, contador5 = 0;
    public int contador6 = 0, contador7 = 0, contador8 = 0, contador9 = 0, contador10 = 0, k = 0;

    public Equivalencias devolverNombre(List<Equivalencias> equiva, Double codigo) {

        for (Iterator<Equivalencias> it = equiva.iterator(); it.hasNext();) {
            Equivalencias equivalencias = it.next();
            if (equivalencias.getValorminimo() <= codigo && codigo <= equivalencias.getValormaximo()) {
                return equivalencias;
            }
        }
        return equiva.get(0);


    }

    public Equivalencias devolverNombreDisciplina(List<Equivalencias> equiva, Double codigo) {

        for (Iterator<Equivalencias> it = equiva.iterator(); it.hasNext();) {
            Equivalencias equivalencias = it.next();
            if (equivalencias.getValorminimo() <= codigo && codigo <= equivalencias.getValormaximo()) {
                return equivalencias;
            }
        }
        return equiva.get(0);


    }

    public void devolverCuadro(Cursos curso, Sistemacalificacion sistema, String tipo,boolean incluyeEquivalencias) {
        contador1 = 0;
        contador2 = 0;
        contador3 = 0;
        contador4 = 0;
        contador5 = 0;
        contador6 = 0;
        contador7 = 0;
        contador8 = 0;
        contador9 = 0;
        contador10 = 0;
        k = 0;
         
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");

//        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
//                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.grupo = 'AP'");

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalencias2 = adm.query("Select o from Equivalencias as o "
                + "where o.grupo = 'DR' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        List<Sistemacalificacion> sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "and o.orden <= '" + sistema.getOrden() + "' "
                //+ "and o.trimestre.codigotrim = '" + sistema.getTrimestre().getCodigotrim() + "' "
                + "and o.seimprime = true and o.espromedio =true order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.orden  <= '" + sistema.getOrden() + "'  "
                //+ "and o.sistema.trimestre.codigotrim =  '" + sistema.getTrimestre().getCodigotrim() + "' "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true "
                + "and o.sistema.espromedio = true "
                + "order by o.sistema.orden ");

        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        Integer noDecimales = 3;
        try {
            noDecimales = regresaVariableParametrosDecimal("DECIPROMOCION", parametrosGlobales).intValue();
        } catch (Exception a) {
            noDecimales = 3;

        }

        Integer remedial = 5;
        try {
            remedial = regresaVariableParametrosDecimal("REMEDIAL", parametrosGlobales).intValue();
        } catch (Exception a) {
            remedial = 5;

        }

        Integer noDecimalesDisc = 2;
        try {
            noDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales).intValue();
        } catch (Exception a) {
            noDecimalesDisc = 2;

        }

        Integer noDecimalesPromediosParciales = 2;
        try {
            noDecimalesPromediosParciales = regresaVariableParametrosDecimal("DECIMALESPRO", parametrosGlobales).intValue();
        } catch (Exception a) {
            noDecimalesPromediosParciales = 2;

        }
        List<Notanotas> notaFinal = adm.query("Select o from Notanotas as o "
                + "where  o.sistema.codigosis = '" + sistema.getCodigosis() + "'  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.seimprime = true");
        Double desde = 0d;
        Double hasta = 0d;

        if (notaFinal.size() <= 0) {
            try {
                Messagebox.show("No ha parametrizado el Promedio Final en Aportes...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);
                // return null;
            } catch (InterruptedException ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        Notanotas nfinal = notaFinal.get(0);



        int eqTamano = equivalencias.size();

        sistema = (Sistemacalificacion) adm.buscarClave(sistema.getCodigosis(), Sistemacalificacion.class);

        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("cuadro");
        HSSFPrintSetup ps = sheet.getPrintSetup();
        sheet.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
        sheet.getPrintSetup().setLandscape(true);
        sheet.setAutobreaks(true);
        ps.setFitHeight((short) 1);
        ps.setFitWidth((short) 1);
        HSSFCellStyle stiloCabeceras = wb.createCellStyle();
        HSSFCellStyle stiloContenido = wb.createCellStyle();
        HSSFCellStyle stiloTitulo = wb.createCellStyle();
        HSSFCellStyle stiloSubTitulo = wb.createCellStyle();

        sheet.setFitToPage(true);

        //style.setFillForegroundColor(HSSFColor.LIME.index);
        //style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        stiloCabeceras.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
        stiloCabeceras.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
        stiloCabeceras.setBorderRight((short) 1);
        stiloCabeceras.setBorderBottom((short) 1);
        stiloCabeceras.setBorderLeft((short) 1);
        stiloCabeceras.setBorderTop((short) 1);


        stiloContenido.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
        stiloContenido.setVerticalAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
        stiloContenido.setBorderRight((short) 1);
        stiloContenido.setBorderBottom((short) 1);
        stiloContenido.setBorderLeft((short) 1);
        stiloContenido.setBorderTop((short) 1);

        HSSFFont fuenteTitulo = wb.createFont();
        fuenteTitulo.setFontHeightInPoints((short) 15);
        fuenteTitulo.setFontName("Arial");
        fuenteTitulo.setColor(HSSFFont.COLOR_NORMAL);
        fuenteTitulo.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fuenteTitulo.setItalic(false);

        HSSFFont fuenteSubTitulo = wb.createFont();
        fuenteSubTitulo.setFontHeightInPoints((short) 11);
        fuenteSubTitulo.setFontName("Arial");
        fuenteSubTitulo.setColor(HSSFFont.COLOR_NORMAL);
        fuenteSubTitulo.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fuenteSubTitulo.setItalic(false);

        HSSFFont defaultFont = wb.createFont();
        defaultFont.setFontHeightInPoints((short) 7);
        defaultFont.setFontName("Arial");
        defaultFont.setColor(HSSFFont.COLOR_NORMAL);
        defaultFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        defaultFont.setItalic(false);

        HSSFFont defaultFont2 = wb.createFont();
        defaultFont2.setFontHeightInPoints((short) 8);
        defaultFont2.setFontName("Arial");
        defaultFont2.setColor(HSSFFont.COLOR_NORMAL);
        defaultFont2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        defaultFont2.setItalic(false);

        stiloCabeceras.setFont(defaultFont);
        stiloContenido.setFont(defaultFont2);
        stiloTitulo.setFont(fuenteTitulo);

        HSSFRow row = sheet.createRow((short) 0);//fila

        HSSFCell cellInst = row.createCell((short) 1);
        cellInst.setCellValue("" + periodo.getInstitucion().getDenominacion() + " " + periodo.getInstitucion().getNombre());
        cellInst.setCellStyle(stiloTitulo);

        HSSFCell cellCuadroCal = row.createCell((short) 18);
        cellCuadroCal.setCellValue("CUADRO FINAL DE CALIFICACIONES");
        cellCuadroCal.setCellStyle(stiloTitulo);


        row = sheet.createRow((short) 1);//fila
        row.setHeight((short) 100);
        row.setRowStyle(stiloTitulo);
        row = sheet.createRow((short) 2);//fila
        row.setHeight((short) 100);
        row.setRowStyle(stiloTitulo);
        row = sheet.createRow((short) 3);//fila
        row.setHeight((short) 100);
        row.setRowStyle(stiloTitulo);

        row = sheet.createRow((short) 6);//fila
        row.setHeight((short) 100);
        row.setRowStyle(stiloTitulo);

        row = sheet.createRow((short) 7);//fila
        row.setHeight((short) 100);
        row.setRowStyle(stiloTitulo);


        row = sheet.createRow((short) 4);//fila

        stiloSubTitulo.setFont(fuenteSubTitulo);
        HSSFCell cellCurso = row.createCell((short) 1);
        cellCurso.setCellValue("" + curso);
        cellCurso.setCellStyle(stiloSubTitulo);

        HSSFCell cellAnio = row.createCell((short) 18);
        cellAnio.setCellValue("AÑO LECTIVO: " + periodo.getDescripcion());
        cellAnio.setCellStyle(stiloSubTitulo);



        row = sheet.createRow((short) 5);//fila

        HSSFCell cellJornada = row.createCell((short) 18);
        cellJornada.setCellValue("JORNADA: " + periodo.getSeccion().getDescripcion());
        cellJornada.setCellStyle(stiloSubTitulo);



        List<MateriaProfesor> materiaProfesor = adm.query("Select o from MateriaProfesor as o "
                + " where o.curso.codigocur = '" + curso.getCodigocur() + "' "
                + " and o.seimprime = true and o.ministerio = true "
                + " order by o.orden");

        String matriculasSupletoriadas = "";
        if (tipo.equals("SUP")) {
            List<Notanotas> notaGeneral = adm.query("Select o from Notanotas as o "
                    + "where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.promediofinal = 'PG' ");
            if (notaGeneral.size() <= 0) {
                try {
                    Messagebox.show("No ha parametrizado el Promedio General en Aportes...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);

                } catch (InterruptedException ex) {
                    Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String nGeneral = notaGeneral.get(0).getNota();
            String codigoMapProfesor = "";
            for (Iterator<MateriaProfesor> it = materiaProfesor.iterator(); it.hasNext();) {
                MateriaProfesor mp = it.next();
                codigoMapProfesor += mp.getMateria().getCodigo() + ",";
            }
            if (codigoMapProfesor.length() > 0) {
                codigoMapProfesor = codigoMapProfesor.substring(0, codigoMapProfesor.length() - 1);
            }
            List matriculasList = adm.query("Select DISTINCT o.matricula.codigomat from Notas as o"
                    + " where o." + nGeneral + " < 7 and o." + nGeneral + " >= " + remedial + "  and o.materia.codigo in (" + codigoMapProfesor + ") "
                    + " and o.matricula.curso.codigocur = '" + curso.getCodigocur() + "' ");
            for (Object object : matriculasList) {
                matriculasSupletoriadas += object + ",";
            }
            if (matriculasSupletoriadas.length() > 0) {
                matriculasSupletoriadas = matriculasSupletoriadas.substring(0, matriculasSupletoriadas.length() - 1);
            }


        } else if (tipo.equals("REM")) {
            List<Notanotas> notaGeneral = adm.query("Select o from Notanotas as o "
                    + "where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.promediofinal = 'PG' ");
            if (notaGeneral.size() <= 0) {
                try {
                    Messagebox.show("No ha parametrizado el Promedio General en Aportes...!", "Administrador Educativo", Messagebox.CANCEL, Messagebox.EXCLAMATION);

                } catch (InterruptedException ex) {
                    Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            String nGeneral = notaGeneral.get(0).getNota();
            String codigoMapProfesor = "";
            for (Iterator<MateriaProfesor> it = materiaProfesor.iterator(); it.hasNext();) {
                MateriaProfesor mp = it.next();
                codigoMapProfesor += mp.getMateria().getCodigo() + ",";
            }
            if (codigoMapProfesor.length() > 0) {
                codigoMapProfesor = codigoMapProfesor.substring(0, codigoMapProfesor.length() - 1);
            }
            List matriculasList = adm.query("Select DISTINCT o.matricula.codigomat from Notas as o"
                    + " where o." + nGeneral + " < " + remedial + "  and o.materia.codigo in (" + codigoMapProfesor + ") "
                    + " and o.matricula.curso.codigocur = '" + curso.getCodigocur() + "' ");
            for (Object object : matriculasList) {
                matriculasSupletoriadas += object + ",";
            }
            if (matriculasSupletoriadas.length() > 0) {
                matriculasSupletoriadas = matriculasSupletoriadas.substring(0, matriculasSupletoriadas.length() - 1);
            }


        }

        List<Matriculas> listaMatriculasPerdidos = cuadroverificar(curso, notaFinal.get(0).getSistema(), new Matriculas(-1));
        String codigosPerdidos = "";
        ArrayList perdidos = new ArrayList();
        for (Iterator<Matriculas> it = listaMatriculasPerdidos.iterator(); it.hasNext();) {
            Matriculas matriculas = it.next();
            codigosPerdidos = matriculas.getCodigomat() + "," + codigosPerdidos;
            perdidos.add(matriculas.getCodigomat());

        }

//        List<Matriculas> matriculados = adm.query("Select o from Matriculas as o "
//                + " where o.curso.codigocur = " + curso.getCodigocur() + "");
        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";

        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");

        row = sheet.createRow((short) 7);//fila
        sheet.autoSizeColumn((short) 0);
        //row.createCell((short) 0).setCellValue("Orden");//columna

        HSSFCell cellOrd = row.createCell((short) 0);
        cellOrd.setCellValue("Ord.");
        cellOrd.setCellStyle(stiloCabeceras);
        sheet.autoSizeColumn((short) 0);

        // row = sheet.createRow((short) 7);//fila
        //      sheet.autoSizeColumn((short) 1);

        sheet.addMergedRegion(new Region(7, (short) 0, 8, (short) 0));
        sheet.addMergedRegion(new Region(7, (short) 1, 8, (short) 1));

        HSSFCell cellApe = row.createCell((short) 1, 1);
        cellApe.setCellValue("APELLIDOS Y NOMBRES");
        cellApe.setCellStyle(stiloCabeceras);
        sheet.autoSizeColumn((short) 1);

        int materiasCabecera = 2;

        for (Iterator<MateriaProfesor> ImapTitulos = materiaProfesor.iterator(); ImapTitulos.hasNext();) {
            MateriaProfesor acaMateriaProfesor = ImapTitulos.next();
            // row = sheet.createRow((short) 7);
            HSSFCell cell = row.createCell((short) materiasCabecera);
            cell.setCellValue("" + acaMateriaProfesor.getMateria().getDescripcion());
            cell.setCellStyle(stiloCabeceras);
            //anado celdas segun el tamaño de sub sistemas
            for (int sis = 0; sis < sistemas.size() - 1; sis++) {
                HSSFCell cellVacio = row.createCell((short) (materiasCabecera + sis + 1));
                cellVacio.setCellValue("");
                cellVacio.setCellStyle(stiloCabeceras);
            }
            sheet.addMergedRegion(new Region(7, (short) materiasCabecera, 7, (short) (materiasCabecera + sistemas.size() - 1)));
            materiasCabecera++;
            //le añado 2 celdas mas ya que voy a combinar 
            //materiasCabecera++;
            materiasCabecera = materiasCabecera + sistemas.size() - 1;
            //sistemas.size()
            sheet.autoSizeColumn((short) materiasCabecera);
        }
        //row = sheet.createRow((short) 7);
        sheet.autoSizeColumn((short) materiasCabecera);
        HSSFCell cellComp = row.createCell((short) materiasCabecera);
        cellComp.setCellValue("COMPORTAMIENTO");
        cellComp.setCellStyle(stiloCabeceras);
        materiasCabecera++;

        if (incluyeEquivalencias) {
            sheet.autoSizeColumn((short) materiasCabecera);
            HSSFCell cellCompEq = row.createCell((short) materiasCabecera);
            cellCompEq.setCellValue("EQ.");
            cellCompEq.setCellStyle(stiloCabeceras);
            materiasCabecera++;
        }
//
//        for (Iterator<MateriaProfesor> ImapTitulos = materiaProfesorOpcionales.iterator(); ImapTitulos.hasNext();) {
//            MateriaProfesor acaMateriaProfesor = ImapTitulos.next();
//            row = sheet.createRow((short) 7);
//            sheet.autoSizeColumn((short) materiasCabecera);
//            row.createCell((short) materiasCabecera).setCellValue("" + acaMateriaProfesor.getMateria().getDescripcion());
//
//            materiasCabecera++;
//        }
        // row = sheet.createRow((short) 7);
        sheet.autoSizeColumn((short) materiasCabecera);
        HSSFCell celdaDisciplina = row.createCell((short) materiasCabecera);
        celdaDisciplina.setCellValue("PROM.");
        celdaDisciplina.setCellStyle(stiloCabeceras);
        // row = sheet.createRow((short) 7);
        materiasCabecera++;
        
         if (incluyeEquivalencias) {
            sheet.autoSizeColumn((short) materiasCabecera);
            HSSFCell cellCompEq = row.createCell((short) materiasCabecera);
            cellCompEq.setCellValue("EQ.");
            cellCompEq.setCellStyle(stiloCabeceras);
            materiasCabecera++;
        }
//        sheet.autoSizeColumn((short) materiasCabecera);
        HSSFCell cellObs = row.createCell((short) materiasCabecera);
        cellObs.setCellValue("OBSERVACION");
        cellObs.setCellStyle(stiloCabeceras);

        row = sheet.createRow((short) 8);//fila
        HSSFCell cellOrd2 = row.createCell((short) 0);
        cellOrd2.setCellValue("");//
        cellOrd2.setCellStyle(stiloCabeceras);
        HSSFCell cellOrd3 = row.createCell((short) 1);
        cellOrd3.setCellValue("");//
        cellOrd3.setCellStyle(stiloCabeceras);

        materiasCabecera = 2;
        //PONGO LOS SISTEMAS DE CALIFICAICONES PG, PF, SUM, GRACIA, ETC.
        for (Iterator<MateriaProfesor> ImapTitulos = materiaProfesor.iterator(); ImapTitulos.hasNext();) {
            MateriaProfesor acaMateriaProfesor = ImapTitulos.next();
            for (Iterator<Sistemacalificacion> it = sistemas.iterator(); it.hasNext();) {
                Sistemacalificacion sistemacalificacion = it.next();
                // row = sheet.createRow((short) 8);
                HSSFCell cell = row.createCell((short) materiasCabecera);
                cell.setCellValue("" + sistemacalificacion.getAbreviatura());
                cell.setCellStyle(stiloCabeceras);
                sheet.autoSizeColumn((short) materiasCabecera);
                materiasCabecera++;
            }
        }

        //HSSFCellStyle.ALIGN_CENTER)
        int x = 8;
        int y = 1;
        Double sumaPromedio = 0.0;
//        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
        //              + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        System.out.println("TRUNCAR NOTAS: " + truncarNotas);
        String q = "Select codigomap, matricula,notas.materia, " + query + "  from notas, materia_profesor , matriculas mat, estudiantes est "
                + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                + "and materia_profesor.curso = '" + curso.getCodigocur() + "' and notas.materia > 0 "
                + "and notas.promedia = true and notas.disciplina = false and materia_profesor.seimprime = true  "
                + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "'  ) "
                + "order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";

        if (tipo.equals("SUP")) {
            q = "Select codigomap, matricula,notas.materia, " + query + "  from notas, materia_profesor , matriculas mat, estudiantes est "
                    + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                    + "and materia_profesor.curso = '" + curso.getCodigocur() + "' and notas.materia > 0 "
                    + "and notas.promedia = true and notas.disciplina = false and materia_profesor.seimprime = true  "
                    + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "'  ) "
                    + " and mat.codigomat in (" + matriculasSupletoriadas + ") "
                    + "order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";
        } else if (tipo.equals("REM")) {
            q = "Select codigomap, matricula,notas.materia, " + query + "  from notas, materia_profesor , matriculas mat, estudiantes est "
                    + "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante "
                    + "and materia_profesor.curso = '" + curso.getCodigocur() + "' and notas.materia > 0 "
                    + "and notas.promedia = true and notas.disciplina = false and materia_profesor.seimprime = true  "
                    + "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "'  ) "
                    + " and mat.codigomat in (" + matriculasSupletoriadas + ") "
                    + "order by  CONCAT(est.apellido,' ',est.nombre), materia_profesor.orden";
        }
        System.out.println("cuadro final: " + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        int cont = 0;
        int conteoMateriasBloque = 0;
        String matricula = "";
        int columna = 0;
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
                    if (truncarNotas) {
                        val = truncar((Double) dos, 2);
                    } else {
                        val = redondear((Double) dos, 2);
                    }
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    nota.setContador(cont);
                    matricula = matriculaNo.toString();
                    //nota.setNota(val);

                    if (mprofesor.getCuantitativa() == false) {

                        if (mprofesor.getMateria().getCodigo().equals(new Integer(0))) {
                            nota.setNota(equivalencia(dos, equivalencias2));
                        } else {
                            nota.setNota(equivalencia(dos, equivalencias));
                        }
                    } else {
                        nota.setNota(val.toString());
//                        aprovecha+=val;
//                        System.out.println(matriculaNo+":::"+aprovecha);
                        if (val.equals(new Double(0.0))) {
                            nota.setNota("");
                        }
                    }
                    if (val >= desde && val <= hasta) {
                    } else {
                        //nota.setNota("");
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
//                    row = sheet.createRow((short) x); //fila
                    HSSFCell cellNota = row.createCell((short) (columna + 2));

                    String s = "###0.00";
                    if (noDecimalesPromediosParciales > 2) {
                        s = "###0.000";
                    }
                    DecimalFormat decimalFormat = new DecimalFormat(s);
                    nota.setNota(decimalFormat.format(redondear((Double) dos, noDecimalesPromediosParciales)));
                    if (dos.equals(new Double(0.0))) {
                        nota.setNota("");
                    }
                    try {
                        cellNota.setCellValue("" + decimalFormat.format(redondear(new Double(nota.getNota() + ""), noDecimalesPromediosParciales)));
                    } catch (Exception e) {
                        if (mprofesor.getCuantitativa() == false) {
                            cellNota.setCellValue("APROBADO");
                            if (ksis == 0) {
                                sheet.addMergedRegion(new Region(x, (short) (columna + 2), x, (short) ((columna + 2) + sistemas.size() - 1)));
                            }
                        } else {
                            cellNota.setCellValue("" + nota.getNota());
                        }
                    }
//                    System.out.println("VALORCELDA:"+cellNota.getStringCellValue()+"");
                    cellNota.setCellStyle(stiloContenido);
                    sheet.setColumnWidth((short) (columna + 2), (short) 1250);
                    ksis++;
                    columna++;
                } else if (j == 1) {

                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);

                    //AÑADO LA MATRICULA COMO CELDA AL EXCELS
                    if (!matriculaNo.toString().equals(matricula)) {
                        row = sheet.createRow((short) (x + 1));
                    }

                    HSSFCell cellOrden = row.createCell((short) 0);
                    cellOrden.setCellValue("" + (cont));
                    cellOrden.setCellStyle(stiloContenido);

                    HSSFCell cell = row.createCell((short) 1);
                    cell.setCellValue("" + matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre());
                    cell.setCellStyle(stiloContenido);




                    List valor = adm.queryNativo("SELECT CAST(IF(" + nfinal.getNota() + " is null,0," + nfinal.getNota() + ")as decimal (9," + noDecimalesDisc + ")) "
                            + " FROM notas WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND materia = 0 ");
                    if (valor.size() > 0) {
                        disciplina = ((BigDecimal) (((Vector) valor.get(0)).get(0))).doubleValue();
                    }
                    HSSFCell cellDisc = row.createCell((short) (materiaProfesor.size() * sistemas.size() + 2));
                    cellDisc.setCellValue("" + equivalencia(disciplina, equivalencias2));
                    cellDisc.setCellStyle(stiloContenido);
                    int m=0;
                     if (incluyeEquivalencias) {
                         HSSFCell cellDiscE = row.createCell((short) (materiaProfesor.size() * sistemas.size() + 3));
                        cellDiscE.setCellValue("" + devolverNombreDisciplina(equivalencias2,disciplina.doubleValue()).getNombre());
                        cellDiscE.setCellStyle(stiloContenido);
                        sheet.setColumnWidth((short) (materiaProfesor.size() * sistemas.size() + 3), (short) 5000);
                        m=1;
                    }
                    
                    String query2 = "round(cast(avg(" + nfinal.getNota() + ") as decimal(9,4))," + noDecimales + ")";
                    if (truncarNotas) {
                        query2 = "truncate(cast(avg(" + nfinal.getNota() + ") as decimal(9,4))," + noDecimales + ")";
                    }
                    //CAST(AVG(" + nfinal.getNota() + ")as decimal (9,"+noDecimales+"))
                    valor = adm.queryNativo("SELECT " + query2 + " FROM notas "
                            + " WHERE matricula = '" + matriculaNo.getCodigomat() + "' AND cuantitativa = TRUE "
                            + "AND disciplina = FALSE AND  promedia = TRUE "
                            + "AND materia > 1 AND  seimprime = TRUE GROUP BY MATRICULA ");
                    if (valor.size() > 0) {
                        if (truncarNotas) {
                            aprovecha = truncar(((BigDecimal) (((Vector) valor.get(0)).get(0))).doubleValue(), noDecimales);
                        } else {
                            aprovecha = redondear(((BigDecimal) (((Vector) valor.get(0)).get(0))).doubleValue(), noDecimales);
                        }

                    }

                    String s = "###0.00";
                    if (noDecimales > 2) {
                        s = "###0.000";
                    }
                    DecimalFormat decimalFormat = new DecimalFormat(s);
                    //nota.setNota(decimalFormat.format(redondear((Double) dos, noDecimalesPromediosParciales)));

                    HSSFCell cellApr = row.createCell((short) (materiaProfesor.size() * sistemas.size() + 3+m));
                    cellApr.setCellValue("" + decimalFormat.format(aprovecha));
                    cellApr.setCellStyle(stiloContenido);
                    
                     if (incluyeEquivalencias) {
                         HSSFCell cellDiscE = row.createCell((short) (materiaProfesor.size() * sistemas.size() + 4+m));
                        cellDiscE.setCellValue("" + devolverNombre(equivalencias,aprovecha.doubleValue()).getAbreviatura());
                        cellDiscE.setCellStyle(stiloContenido);
                        m++;
                    }

                    HSSFCell cellObss = row.createCell((short) (materiaProfesor.size() * sistemas.size() + 4+m));
                    if (perdidos.contains(matriculaNo.getCodigomat())) {
                        cellObss.setCellValue("PIERDE EL AÑO");
                    } else {
                        cellObss.setCellValue("");
                    }
                    sheet.setColumnWidth((short) (materiaProfesor.size() * sistemas.size() + 4+m), (short) 5000);
                    cellObss.setCellStyle(stiloContenido);


                } else if (j == 2) {
                    materiaNo = (Global) adm.buscarClave((Integer) dos, Global.class);
                } else if (j == 0) {
                    mprofesor = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                    mprofesor1 = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                }
                if (matriculaNo != null && j > 1) {
                    if (!matriculaNo.toString().equals(matricula)) {
                        cont++;
                        x++;
                        columna = 0;
//                        if(x!=9)

                    }
                }


            }
        }

        sheet.setColumnWidth((short) (1), (short) 10000);

        parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        String firma1 = regresaVariableParametros("FIR1", parametrosGlobales);
        String cargo1 = regresaVariableParametros("CAR1", parametrosGlobales);
//                String firma2 = regresaVariableParametros("FIR2", parametrosGlobales);
//                String cargo2 = regresaVariableParametros("CAR2", parametrosGlobales);
        String firma3 = regresaVariableParametros("FIR3", parametrosGlobales);
        String cargo3 = regresaVariableParametros("CAR3", parametrosGlobales);


        try {
            firma1 = firma1.replace("[RECTOR]", periodo.getInstitucion().getRector()).replace("[INSPECTOR]", curso.getInspector()).replace("[TUTOR]", curso.getTutor()).replace("[SECRETARIA]", periodo.getInstitucion().getSecretaria());

            firma3 = firma3.replace("[RECTOR]", periodo.getInstitucion().getRector()).replace("[INSPECTOR]", curso.getInspector()).replace("[TUTOR]", curso.getTutor()).replace("[SECRETARIA]", periodo.getInstitucion().getSecretaria());


        } catch (Exception e) {
            System.out.println("FALTA PARAMETROS DE FIRMAS: DE LIBRETA" + e);
        }

        row = sheet.createRow((short) (x + 7));
        row.createCell((short) 1).setCellValue("" + firma1);//;
        row.createCell((short) 14).setCellValue("" + firma3);//;

        row = sheet.createRow((short) (x + 8));
        row.createCell((short) 1).setCellValue("" + cargo1);//;
        row.createCell((short) 14).setCellValue("" + cargo3);//;





        try {
//            response.setContentType("application/vnd.ms-excel");
//            ServletOutputStream outputStream = response.getOutputStream();
//            wb.write(outputStream);
//            outputStream.flush();
//            outputStream.close();
            try {
                File outFile = File.createTempFile("cuadro_basica", ".xls");
                FileOutputStream elFichero = new FileOutputStream(outFile);
                wb.write(elFichero);
                elFichero.close();

                Filedownload.save(outFile, "application/vnd.ms-excel");

            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception ex) {
        }

    }
}