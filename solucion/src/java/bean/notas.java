package bean;

import bsh.EvalError;
import bsh.Interpreter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import net.sf.jasperreports.engine.JRDataSource;
import org.zkforge.yuiext.grid.Label;
import org.zkforge.yuiext.grid.Row;
import org.zkforge.yuiext.grid.Rows;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Textbox;
import sources.Nota;
import sources.NotasClaseTemp;
import sources.NumerosaLetras;
import sources.ReporteCertificadoDataSource;
import sources.ReporteExamenesDataSource;
import sources.ReporteGradoDataSource;
import sources.ReporteNoasLibretaDataSource;
import sources.ReporteNotasDataSource;
import sources.ReporteProfesorDataSource;
import sources.ReportePromocionDataSource;

public class notas extends Rows {
//ArrayList listad = new ArrayList();

    private Double notaDisciplina = 0.0;

    public notas() {
    }

    public Boolean verificar(String formula, List<Notanotas> notas) {

        formula = formula.replace("()", "");
        String redon = "public Double redondear(Double numero, int decimales) {" +
                "" +
                "try{" +
                "               " +
                " java.math.BigDecimal d = new java.math.BigDecimal(numero);" +
                "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" +
                "        return d.doubleValue();" +
                "        }catch(Exception e){" +
                "            return 0.0;" +
                "        }" +
                "     }";

        Interpreter inter = new Interpreter();
        try {
            inter.eval(redon);
            for (Iterator<Notanotas> it = notas.iterator(); it.hasNext();) {
                Notanotas notanotas = it.next();
                inter.eval("" + notanotas.getNota() + "=1;");
            }
            inter.eval(formula + "*1");
        } catch (EvalError ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
        return false;
    }

    public void addRow(Cursos curso, MateriaProfesor materia) {
        int tamanio = 0;
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//     if(listad==null){
        Administrador adm = new Administrador();
        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
                "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' order by o.sistema.orden ");
        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }
        tamanio = sistemas.size();
        //List<Matriculas> matriculas = adm.query("Select o from Matriculas as o ");
        getChildren().clear();
        Label label = null;


        String q = "Select matriculas.codigomat,concat(estudiantes.apellido,' ',estudiantes.nombre), " + query + "  from matriculas " +
                "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest " +
                "left join notas on matriculas.codigomat = notas.matricula " +
                "and notas.materia = '" + materia.getMateria().getCodigo() + "' and notas.disciplina = false  " +
                "where matriculas.curso = '" + curso.getCodigocur() + "'  and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir' ) " +
                "order by estudiantes.apellido";
        ParametrosGlobales para = (ParametrosGlobales) adm.buscarClave(new Integer(1), ParametrosGlobales.class);
        if (para.getCvalor().equals("P")) {
            q = "Select matriculas.codigomat,(estudiantes.apellido,' ',estudiantes.nombre), " + query + "  from matriculas " +
                    "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "and notas.materia = '" + materia.getMateria().getCodigo() + "' and notas.disciplina = false  " +
                    "where matriculas.curso = '" + curso.getCodigocur() + "'  and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir' )" +
                    "order by estudiantes.apellido";
        }

     System.out.println(""+q);
        List nativo = adm.queryNativo(q);
        Row row = new Row();

        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            row = new Row();
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                label = new Label();
//                 label.setAttribute("onBlur", "alert(this)");
                try {
                    if (dos.equals(null)) {
                        dos = new Double(0.0);
                    }
                } catch (Exception e) {
                    dos = new Double(0.0);
                }
                if (j >= 2) {
                    Double valor = (Double) dos;
                    if (valor.equals(0.0)) {
                        label.setValue("");
                    } else {
                        label.setValue("" + redondear((Double) dos, 2));
                        
                    }

                } else {
                    String valor = dos.toString().replace("(", "").replace(")", "").replace("\"", "").replace(",", "");
                    label.setValue("" + valor);
                }
//                                 label.setAttribute(q, dos);
                  label.setStyle("font-size:11px;font:arial");
                row.appendChild(label);
//                                 System.out.print(","+dos);
            }

            row.setParent(this);
        }
        nativo = null;



    }

    public String validar(List col, List<Notanotas> notas) {
        Administrador adm = new Administrador();
        for (int i = 0; i < col.size(); i++) {
            Row object = (Row) col.get(i);
            List labels = object.getChildren();
            Matriculas ma = (Matriculas) adm.buscarClave(new Integer(((Label) labels.get(0)).getValue()), Matriculas.class);
            //nota.setMatricula(new Matriculas(new Integer(((Label) vecDato.get(0)).getValue())));
            for (int j = 2; j < labels.size(); j++) {
                Label object1 = (Label) labels.get(j);
                String formula = notas.get(j - 2).getSistema().getFormula(); // EN CASO DE FORMULA
                formula = formula.replace("no", "nota.getNo"); //EN CASO DE QUE HAYA FORMULA
                String toda = notas.get(j - 2).getNota() + "";

                toda = toda.substring(1, toda.length());
                String vaNota = object1.getValue().toString();
                Double aCargar = 0.0;
                if (vaNota.equals("")) {
                    aCargar = 0.0;
                } else {
                    aCargar = new Double(vaNota);
                }
                if ((aCargar > notas.get(j - 2).getSistema().getNotalimite() || aCargar < 0) && formula.isEmpty()) {
                    return "NOTA FUERA DE RANGO EN PERIODO: " + notas.get(j - 2).getSistema().getAbreviatura() + "  NOTA: " + aCargar + " " +
                            " ESTUDIANTE: " + ma.getEstudiante().getApellido() + " " + ma.getEstudiante().getNombre() + "  ";
                }
            }
        }
        return "";
    }

    @SuppressWarnings("static-access")
    public String guardar(List col, Cursos curso, MateriaProfesor materia) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        try {
            String redon = "public Double redondear(Double numero, int decimales) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" + "        return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
            System.out.println("INICIO EN: " + new Date());
            Interpreter inter = new Interpreter();
            inter.eval(redon);
            Administrador adm = new Administrador();
            List<Notanotas> notas = adm.query("Select o from Notanotas as o where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' order by o.sistema.orden ");
            List<Sistemacalificacion> sisFormulas = adm.query("Select o from Sistemacalificacion as o " +
                    "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.formula <> '' " +
                    "  order by o.orden ");
            for (Iterator<Sistemacalificacion> it = sisFormulas.iterator(); it.hasNext();) {
                Sistemacalificacion siCal = it.next();
                if (verificar(siCal.getFormula(), notas)) {
                    return "Revise la formula de ['" + siCal.getNombre() + "'] del Sistema de Calificacion ";
                }
            }
            String valida = validar(col, notas);
            if (!valida.isEmpty()) {
                return valida;
            }


            secuencial sec = new secuencial();

            String del = "Delete from Notas where matricula.curso.codigocur = '" + curso.getCodigocur() + "' " + "and materia.codigo = '" + materia.getMateria().getCodigo() + "'  and notas.disciplina = false ";
            adm.ejecutaSql(del);
            for (int i = 0; i < col.size(); i++) {
                try {
                    Row object = (Row) col.get(i);
                    Notas nota = new Notas();
                    nota.setCodigonot(sec.generarClave());
                    List labels = object.getChildren();
                    nota.setMatricula(new Matriculas(new Integer(((Label) labels.get(0)).getValue())));
                    nota.setMateria(materia.getMateria());
                    nota.setFecha(new Date());
                    nota.setOrden(materia.getOrden());
                    nota.setCuantitativa(materia.getCuantitativa());
                    nota.setPromedia(materia.getMinisterio());
                    nota.setSeimprime(materia.getSeimprime());
                    nota.setDisciplina(false);
                    inter.set("nota", nota);
                    for (int j = 2; j < labels.size(); j++) {
                        Label object1 = (Label) labels.get(j);
                        String formula = notas.get(j - 2).getSistema().getFormula(); // EN CASO DE FORMULA
                        formula = formula.replace("no", "nota.getNo"); //EN CASO DE QUE HAYA FORMULA
                        String toda = notas.get(j - 2).getNota() + "";
                        String uno = toda.substring(0, 1).toUpperCase();
                        toda = toda.substring(1, toda.length());
                        String vaNota = object1.getValue().toString();
                        Double aCargar = 0.0;
                        if (vaNota.equals("")) {
                            aCargar = 0.0;
                        } else {
                            aCargar = new Double(vaNota);
                        }
                        inter.eval("nota.set" + (uno + toda) + "(" + redondear(aCargar, 2) + ");");
                        if (!formula.isEmpty()) {
                            inter.eval("nota.set" + (uno + toda) + "(" + formula + ");");
                        }
                    }
                    nota = (Notas) inter.get("nota");
                    adm.guardar(nota);

                } catch (EvalError ex) {
                    Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            recalculoNotas(materia, curso);
            System.out.println("FINALIZO EN: " + new Date());
            return "ok";
        } catch (EvalError ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en:  " + ex;
        }


    }

    public void recalculoNotas(MateriaProfesor materia, Cursos curso) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();

        String redon = "public Double redondear(Double numero, int decimales) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" + "        return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
                "where o.periodo.codigoper =  '" + periodo.getCodigoper() + "'  ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o where  o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' order by o.sistema.orden ");
        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }

        List<MateriaProfesor> maprofes = adm.query("Select o from MateriaProfesor as o " +
                "where o.formula <> '' and o.formula like '%MA" + materia.getMateria().getCodigo() + "%' " +
                "and o.curso.codigocur = '" + curso.getCodigocur() + "' ");
        for (Iterator<MateriaProfesor> ita = maprofes.iterator(); ita.hasNext();) {
            try {
                MateriaProfesor map = ita.next();
                
                //   }
                List<Global> materias = adm.query("Select o from Global as o where o.grupo = 'MAT' " +
                        "and o.codigo in (Select ma.materia.codigo from  MateriaProfesor as ma " +
                        "where ma.curso.codigocur = '"+map.getCurso().getCodigocur()+"' ) ");
                String formula = map.getFormula();
                List<Global> Nmaterias = new ArrayList<Global>();
                ArrayList vectors = new ArrayList();
                String ma = "";
                for (Iterator<Global> it = materias.iterator(); it.hasNext();) {
                    Global global = it.next();
                    if (formula.contains("MA" + global.getCodigo())) {
                        Nmaterias.add(global);
                        ma += "VEC" + global.getCodigo() + ",";
                        vectors.add("VEC" + global.getCodigo());
                    }
                }
                if (ma.length() > 0) {
                    ma = ma.substring(0, ma.length() - 1);
                }
                Interpreter inter = new Interpreter();
                inter.eval(redon);
                try {
                    for (Iterator<Global> it = Nmaterias.iterator(); it.hasNext();) {
                        Global global = it.next();
                        String q = "Select matriculas.codigomat, " + query + "  from matriculas " +
                                "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest " +
                                "left join notas on matriculas.codigomat = notas.matricula " +
                                "and notas.materia = '" + global.getCodigo() + "' and notas.disciplina = false " +
                                "where matriculas.curso = '" + curso.getCodigocur() + "'  " +
                                " and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir') " +

                                "order by estudiantes.apellido";
//                        
                        List nativo = adm.queryNativo(q);
                        System.out.println("recalculo 1: "+q);
                        System.out.println("recalculo 2: "+nativo.size());
                        inter.set("VEC" + global.getCodigo(), nativo);
                    }
                    String vector1 = (String) vectors.get(0);
                    inter.eval("int tamanio1 =  " + vector1 + ".size(); " + "int tamanio2 = ((Vector)" + vector1 + ".get(0)).size(); " + "Vector calculado = new Vector(); ");
                    inter.eval("System.out.println(tamanio1);");
                    inter.eval("System.out.println(tamanio2);");
                    inter.eval("for (int k = 0; k < tamanio1; k++) {" + "                Vector resultado = new Vector();" + "                Vector object = (Vector) " + vector1 + ".get(k);" + "                for (int i = 0; i < tamanio2; i++) {" + "                    if (i == 0) {" + "                        Integer cod = (object.get(i) != null ? ((Integer) object.get(i)) : 0);" + "                        resultado.add(cod);" + "                    } else {" + "                        resultado.add(0.0);" + "                    }" + "                }" + "                calculado.add(resultado);" + "            }");
                    String asumar = "";
                    String aconvertir = "";
                    for (Iterator it = vectors.iterator(); it.hasNext();) {
                        String object = (String) it.next();
                        String codigo = object.replace("VEC", "");
                        asumar += "Vector object" + codigo + " = (Vector) " + object + ".get(k); ";
                        aconvertir += "Double MA" + codigo + " = (object" + codigo + ".get(i) != null ? ((Double) object" + codigo + ".get(i)) : 0.0);";
                    }
                    inter.eval(" for (int k = 0; k < " + vector1 + ".size(); k++) {" + asumar + "                Vector resultado = (Vector) calculado.get(k);" + "                for (int i = 1; i < resultado.size(); i++) {" + aconvertir + "                    resultado.set(i, " + formula + ");" + "                }" + "                calculado.set(k, resultado);" + "            }");
                    Vector aguardar = (Vector) inter.get("calculado");
                    //INICIO PROCESO DE GUARDAR LAS NOTAS
                    secuencial sec = new secuencial();
                    String del = "Delete from Notas where matricula.curso.codigocur = '" + curso.getCodigocur() + "' " + "and materia.codigo = '" + map.getMateria().getCodigo() + "' ";
                    adm.ejecutaSql(del);
                    for (int i = 0; i < aguardar.size(); i++) {
                        try {
                            //Row object = (Row) aguardar.get(i);
                            Vector labels = (Vector) aguardar.get(i);
                            Notas nota = new Notas();
                            nota.setCodigonot(sec.generarClave());
                            nota.setMatricula(new Matriculas(new Integer((Integer) labels.get(0))));
                            nota.setMateria(map.getMateria());
                            nota.setFecha(new Date());
                            nota.setOrden(map.getOrden());
                            nota.setCuantitativa(map.getCuantitativa());
                            nota.setPromedia(map.getMinisterio());
                            nota.setSeimprime(map.getSeimprime());
                            nota.setDisciplina(false);
                            inter.set("nota", nota);
                            for (int j = 1; j < labels.size(); j++) {
                                Double object1 = (Double) labels.get(j);
                                String toda = notas.get(j - 1).getNota() + "";
                                String uno = toda.substring(0, 1).toUpperCase();
                                toda = toda.substring(1, toda.length());
                                inter.eval("nota.set" + (uno + toda) + "(" + redondear(new Double(object1), 2) + ");");
                            }
                            nota = (Notas) inter.get("nota");
                            adm.guardar(nota);
                        } catch (EvalError ex) {
                            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
//            System.out.println(""+materia);
//            System.out.println(""+curso);
//            System.out.println(""+(Vector) inter.get("VEC17"));
//            System.out.println(""+(Vector) inter.get("VEC18"));
//          System.out.println(""+aguardar);
                } catch (EvalError ex) {
                    Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
                }

//            System.out.println(""+materia);
//            System.out.println(""+curso);
//            System.out.println(""+(Vector) inter.get("VEC17"));
//            System.out.println(""+(Vector) inter.get("VEC18"));
//          System.out.println(""+aguardar);

            } catch (EvalError ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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

    public JRDataSource certificados(Cursos curso) {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();

        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List lista = adm.listar("Institucion");
        Institucion insts = (Institucion) lista.get(0);
        ArrayList detalle = new ArrayList();
        String query = "SELECT mat FROM Matriculas AS mat " +
                "WHERE  mat.estado like '%Matriculado%' " +
                "and mat.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' " +
                " order by mat.curso.secuencia, mat.curso.codigocur, mat.estudiante.apellido";
        if (!curso.getCodigocur().equals(-2)) {
            query = "Select o from Matriculas as o " +
                    "where o.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' " +
                    "and o.curso.codigocur = '" + curso.getCodigocur() + "'";
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

    public JRDataSource distributivo() {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        ArrayList detalle = new ArrayList();
        List hoy = adm.query("SELECT mat FROM MateriaProfesor AS mat " +
                "WHERE mat.curso.periodo.codigoper = '" + periodo.getCodigoper() + "' " +
                "order by mat.curso.secuencia");

        for (Iterator it = hoy.iterator(); it.hasNext();) {
            MateriaProfesor elem = (MateriaProfesor) it.next();
            detalle.add(elem);
        }
        ReporteProfesorDataSource ds = new ReporteProfesorDataSource(detalle);


        return ds;
    }

    public JRDataSource notasd(Cursos curso, Global materia, Sistemacalificacion sistema) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//     int tamanio=0;
        Administrador adm = new Administrador();
        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
                "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.orden <= '" + sistema.getOrden() + "' order by o.orden ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
                "where  o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  and o.sistema.orden  <= '" + sistema.getOrden() + "' " +
                "order by o.sistema.orden ");
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
        String q = "Select matriculas.codigomat, " + query + "  from matriculas " +
                "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest " +
                "left join notas on matriculas.codigomat = notas.matricula " +
                "and notas.materia = '" + materia.getCodigo() + "' and notas.disciplina = false " +
                "where matriculas.curso = '" + curso.getCodigocur() + "' " +
                "order by estudiantes.apellido";
//     System.out.println(""+q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
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
                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    lisNotas.add(nota);
                    ksis++;
                } else {
                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                    //mprofesor = adm.query("Select o from ")
                }
            }
        }
        nativo = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;

    }

    public JRDataSource notasdisciplina(Cursos curso, Global materia, Sistemacalificacion sistema) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//     int tamanio=0;
        Administrador adm = new Administrador();
        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
                "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.orden <= '" + sistema.getOrden() + "' order by o.orden ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
                "where  o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  " +
                "and o.sistema.orden  <= '" + sistema.getOrden() + "' " +
                "order by o.sistema.orden ");
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
        String q = "Select matriculas.codigomat, " + query + "  from matriculas " +
                "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest " +
                "left join notas on matriculas.codigomat = notas.matricula " +
                "and notas.materia = '" + materia.getCodigo() + "' and notas.disciplina = true " +
                "where matriculas.curso = '" + curso.getCodigocur() + "' " +
                "order by estudiantes.apellido";
//     System.out.println(""+q); 
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
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
                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    lisNotas.add(nota);
                    ksis++;
                } else {
                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                    //mprofesor = adm.query("Select o from ")
                }
            }
        }
        nativo = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;

    }

    public JRDataSource cuadrocalificaciones(Cursos curso, Sistemacalificacion sistema) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o " +
                "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        Double numeroDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
                "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.orden <= '" + sistema.getOrden() + "' order by o.orden ");
        
        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
                "where  o.sistema.codigosis  = '" + sistema.getCodigosis() + "' " +
                "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' " +
                "order by o.sistema.orden ");
         List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o " +
                "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        
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
        //and matriculas.estado in ('Matriculado','Recibir','Retirado') 
        String q = "Select codigomap, matricula,notas.materia, " + query + "  from notas, materia_profesor, matriculas mat, estudiantes est " +
                "where notas.materia =  materia_profesor.materia and materia_profesor.curso = '" + curso.getCodigocur() + "'  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante " +
                "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "'  )" +
                "and notas.disciplina = false  " +
                "order by est.apellido, materia_profesor.orden";

        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
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
                    
                        if (maprofesor.getCuantitativa() == false) {
                            nota.setNota(equivalencia(dos, equivalencias));
                        } else {
                             nota.setNota(val.toString());
                            if (val == 0.0) {
                                nota.setNota("");
                            }

                        }
                    //nota.setNota(val);


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
            }
        }
        nativo = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;

    }


    public JRDataSource cuadroexamenes(Cursos curso) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
//        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        //Double numeroDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
        List<Materiasgrado> notas = adm.query("Select o from Materiasgrado as o " +
                "where  o.curso.codigocur = '" + curso.getCodigocur() + "' " +
                " order by o.codigo ");
        String query = "";
        for (Materiasgrado notass : notas) {
            query += notass.getColumna() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String q = "SELECT CONCAT(est.apellido,' ',est.nombre), " + query + "   FROM notasgrado notas,matriculas mat,estudiantes est " +
                "WHERE notas.matricula = mat.codigomat AND mat.estudiante = est.codigoest AND mat.curso = '"+curso.getCodigocur()+"'";
        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        int i = 1;
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            int ksis = 0;
            String estudiante ="";
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                Double val = 0.0;
            Nota nota = new Nota();
                 if (j >= 1) {
 //                   val = redondear((Double) dos, 2);
                    nota.setCargo2(((Materiasgrado)notas.get(ksis)).getNombre());
                    
                    nota.setNota(redondear((Double)dos, 0).intValue());
                        if((j+1) == vec.size()){
                              String s = "##00.00##";
                            DecimalFormat decimalFormat = new DecimalFormat(s);
                            //DecimalFormat formateador = new DecimalFormat("####.###");
                            // Esto sale en pantalla con cuatro decimales, es decir, 3,4324
                            System.out.println ("formato: "+decimalFormat.format(redondear((Double)dos, 3)));
                            nota.setNota(decimalFormat.format (redondear((Double)dos, 3)));
                        }
                    nota.setProfesor(((Materiasgrado)notas.get(ksis)).getProfesor().getApellidos() +" "+((Materiasgrado)notas.get(0)).getProfesor().getNombres() );
                    nota.setCurso(curso);
                    nota.setCargo1(estudiante);
                    nota.setCargo3(""+i);
                    lisNotas.add(nota);
                    ksis++;
                }  else if (j == 0) {
                    estudiante = dos.toString();//en seteo el nombre del estudiante
                }
            }
            i++;
        }
        nativo = null;
        ReporteExamenesDataSource ds = new ReporteExamenesDataSource(lisNotas);
        return ds;

    }


    public JRDataSource cuadrofinal(Cursos curso, Sistemacalificacion sistema) {
//     int tamanio=0;
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
//                "where o.periodo.codigoper = '"+periodo.getCodigoper()+"' and o.espromedio = true   order by o.orden ");
//        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
//                "where  o.sistema.periodo.codigoper = '"+periodo.getCodigoper()+"' and  o.sistema.espromedio = true  " +
//                "order by o.sistema.orden ");
        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
                "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' " +
                "and o.orden <= '" + sistema.getOrden() + "' " +
                "and o.trimestre.codigotrim = '" + sistema.getTrimestre().getCodigotrim() + "' order by o.orden ");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
                "where  o.sistema.orden  <= '" + sistema.getOrden() + "'  " +
                "and o.sistema.trimestre.codigotrim =  '" + sistema.getTrimestre().getCodigotrim() + "' " +
                "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' " +
                "order by o.sistema.orden ");

           List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o " +
                "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        
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

        String q = "Select codigomap, matricula,notas.materia, " + query + "  from notas, materia_profesor , matriculas mat, estudiantes est " +
                "where notas.materia =  materia_profesor.materia  AND notas.matricula = mat.codigomat AND est.codigoest = mat.estudiante " +
                "and materia_profesor.curso = '" + curso.getCodigocur() + "' " +
                "and notas.promedia = true and notas.disciplina = false   " +
                "and matricula in (select codigomat from matriculas where  curso  =  '" + curso.getCodigocur() + "'  ) " +
                "order by  est.apellido, materia_profesor.orden";

        System.out.println("cuadro final: " + q);
        List nativo = adm.queryNativo(q);
        List<Nota> lisNotas = new ArrayList();
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            //row = new Row();
            Matriculas matriculaNo = null;
            Global materiaNo = null;
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
                if (j >= 3) {
                    val = redondear((Double) dos, 2);
                    nota.setMatricula(matriculaNo);
                    nota.setMateria(materiaNo);
                    //nota.setNota(val);

                        if (mprofesor.getCuantitativa() == false) {
                            nota.setNota(equivalencia(dos, equivalencias));
                        } else {
                             nota.setNota(val.toString());
                            if (val == 0.0) {
                                nota.setNota("");
                            }

                        }

                    nota.setMprofesor(mprofesor);
                    nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                    lisNotas.add(nota);
                    ksis++;
                } else if (j == 1) {
                    matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                } else if (j == 2) {
                    materiaNo = (Global) adm.buscarClave((Integer) dos, Global.class);
                } else if (j == 0) {
                    mprofesor = (MateriaProfesor) adm.buscarClave((Integer) dos, MateriaProfesor.class);
                }
            }
        }
        nativo = null;
        ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
        return ds;

    }

    public JRDataSource libretas(Cursos curso, Matriculas matri, Sistemacalificacion sistema) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o " +
                "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
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


        List<Equivalencias> equivalenciasFaltas = adm.query("Select o from Equivalencias as o " +
                "where o.grupo = 'DI' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o " +
                "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
                "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.orden <= '" + sistema.getOrden() + "' order by o.orden ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
                " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  and o.sistema.orden <=  '" + sistema.getOrden() + "'  order by o.sistema.orden ");
        String query = "";
        String query2 = "";
        String queryDisciplina = "";
        String numeroDecimales = "3";
        Double numeroDecimalesDisc = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
        //DECIMALESDIS
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        for (Notanotas notass : notas) {
            query2 += "round(cast(avg(" + notass.getNota() + ") as decimal)," + numeroDecimales + "),";
        }
        for (Notanotas notass : notas) {
            queryDisciplina += "cast(round(cast(avg(" + notass.getNota() + ") as decimal)," + numeroDecimalesDisc.intValue() + ") as decimal),";
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
            matriculas = adm.query("Select o from Matriculas as o where o.curso.codigocur = '" + curso.getCodigocur() + "'");
        } else {
            matriculas.add(matri);
        }
        for (Matriculas matriculas1 : matriculas) {
            //Matriculas matriculas1 = itm.next();
            String q = "Select matriculas.codigomat,notas.materia,notas.cuantitativa, " + query + "  from matriculas " +
                    "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  " +
                    "left join notas on matriculas.codigomat = notas.matricula  " +
                    "and notas.materia != 0  " +
                    "where matriculas.curso = '" + curso.getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    "and notas.seimprime = true " +
                    //"and notas.promedia = true " +
                    "and notas.disciplina = false  " +
                    "and notas.materia != 0 " +
                    "order by estudiantes.apellido, notas.orden";
                 System.out.println("NOTAS GENERALES"+q);
            List nativo = adm.queryNativo(q);
            Nota nota = new Nota();
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
if(impPromedio){
//IMPRIMO EL PROMEDIO 
            q = "Select matricula," + query2 + "  from notas " +
                    "where notas.matricula = '" + matriculas1.getCodigomat() + "' " +
                    "and notas.seimprime = true " +
                    "and notas.promedia = true " +
                    "and notas.disciplina = false " +
                    "and notas.cuantitativa = true and notas.materia != 0 " +
                    "group by matricula  ";
//            q = "Select matriculas.codigomat," + query2 + "  from matriculas " +
//                    "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  " +
//                    "left join notas on matriculas.codigomat = notas.matricula " +
//                    "where matriculas.curso = '" + curso.getCodigocur() + "'  " +
//                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
//                    "and notas.seimprime = true " +
//                    "and notas.promedia = true " +
//                    "and notas.disciplina = false " +
//                    "and notas.cuantitativa = true and notas.materia != 0 " +
//                    "group by codigomat  ";
            System.out.println("NOTAS DE promedio "+q);
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
if(impDisciplina){
            q = "Select matriculas.codigomat," + queryDisciplina + "  from matriculas " +
                    "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "where matriculas.curso = '" + curso.getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    " and notas.materia = 0  " +
                    "group by codigomat  ";
            System.out.println("DISCIPLINA "+q);
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
//IMPRIMO MATERIAS NO INCLUIDAS EN EL MINISTERIO
/*
            q = "Select matriculas.codigomat,notas.materia,notas.cuantitativa, " + query + "  from matriculas " +
                    "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "where matriculas.curso = '" + curso.getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    "and notas.seimprime = true " +
                    "and notas.disciplina = false " +
                    "and notas.promedia = false " +
                    "and notas.cuantitativa = true " +
                    "and notas.materia != 0 " +
                    "order by estudiantes.apellido, notas.orden";
            //System.out.println(""+q);
            nativo = adm.queryNativo(q);
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
                        System.out.println("" + e);
                    }
                    if (j >= 3) {
                        val = (Double) dos;
                        coll.setNota(dos);
                        if (cuantitativa == false) {
                            coll.setNota(equivalencia(dos, equivalencias));
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
*/
            //IMPRIMO EL CUADRO DE EQUIVALENCIAS

            ArrayList lisFaltas = new ArrayList();
            String query3 = "";
if(impEquivalencias){
            int w = 1;
            for (int i = 0; i < equivalenciasFaltas.size(); i++) {
                query3 += "sum(nota" + w + "),";
                w++;

            }
            query3 = query3.substring(0, query3.length() - 1);
            //IMPRIMO LAS FALTAS
            q = "Select " + query3 + "  from disciplina, sistemacalificacion " +
                    "where matricula = '" + matriculas1.getCodigomat() + "'  " +
                    "and sistemacalificacion.orden <= '" + sistema.getOrden() + "' " +
                    "and sistemacalificacion.codigosis =  sistema  " +

                    " group by matricula ";
            //SELECT * FROM disciplina, sistemacalificacion WHERE sistemacalificacion.codigosis =  sistema
//                 System.out.println(""+q);
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
                    coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
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
                    coll.setEstudiante(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
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

    public JRDataSource actaGrado(Cursos curso, String tipo) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");

        NumerosaLetras num = new NumerosaLetras();
        List<Actagrado> notas = adm.query("Select o from Actagrado as o " +
                " where o.periodo.codigoper = '" + periodo.getCodigoper() + "'  order by o.codigo ");
        List<Textos> variablesVarias = adm.query("Select o from Textos as o " +
                " where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o " +
                "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
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
            query2 += "round(cast(avg(" + notass.getColumna() + ") as decimal)," + numeroDecimales + "),";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        query2 = query2.substring(0, query2.length() - 1).replace("'", "");

        ArrayList lisNotasC = new ArrayList();
        List<Matriculas> matriculas = new ArrayList();

        matriculas = adm.query("Select o from Matriculas as o where o.curso.codigocur = '" + curso.getCodigocur() + "'");

        for (Matriculas matriculas1 : matriculas) {
            //Matriculas matriculas1 = itm.next();
            String q = "Select matriculas.codigomat, " + query + "  from matriculas " +
                    "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  " +
                    "left join notasacta on matriculas.codigomat = notasacta.matricula " +
                    "where matriculas.curso = '" + curso.getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    "order by estudiantes.apellido ";
            System.out.println("NOTAS GENERALES" + q);
            List nativo = adm.queryNativo(q);
//            Nota nota = new Nota();
            String s = "#,#00.000";
            DecimalFormat decimalFormat = new DecimalFormat(s);
            for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                Vector vec = (Vector) itna.next();
                Matriculas matriculaNo = null;
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
                    if (j >= 1) {
                        val = (Double) dos;
                        String s1 = decimalFormat.format(val);
                        coll.setNota(s1);
                        Actagrado ac = ((Actagrado) notas.get(ksis));
//                         coll.setNota(dos);
                        if (ac.getFormula().toUpperCase().contains("EQUIVAL")) {
                                  coll.setNota(equivalencia2(redondear(val, 0), equivalencias));
                        }
                        coll.setMatricula("" + matriculaNo.getCodigomat());
                        coll.setEstudiante(matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre());
                        coll.setMatriculas(matriculas1);
                        
                        coll.setMateria(ac.getNombre());


                       
                        coll.setCabecera1(cabecera1.replace("[estudiante]", matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre()));
                        coll.setCabecera1(cabecera1.replace("[fecha]", new Date().toLocaleString()));
                        coll.setCabecera2(cabecera2.replace("[fecha]", new Date().toLocaleString()));
                        coll.setCabecera2(cabecera2.replace("[estudiante]", matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre()));
                        coll.setPie1(pi1.replace("[estudiante]", matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre()));
                        coll.setPie2(pi2.replace("[estudiante]", matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre()));
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
                    } else {
                        matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                    }
                }
                //row.setParent(this);
            }





        }


        ReporteGradoDataSource ds = new ReporteGradoDataSource(lisNotasC);
        lisNotasC = new ArrayList();
        return ds;

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

    public JRDataSource promocion(Cursos curso, Matriculas matri) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Textos> textos = adm.query("Select o from Textos as o " +
                "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        String cabecera = regresaTexto("CMAT", textos);
        String aprobado = regresaTexto("PACMAT", textos);
        String reprobado = regresaTexto("PRCMAT", textos);
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o " +
                "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
                " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  " +
                "and o.sistema.promediofinal = 'PF' ");
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
            String q = "Select round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,4))) as decimal)," + 3 + ") from matriculas " +
                    "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    "and notas.seimprime = true " +
                    "and notas.promedia = true " +
                    "and notas.disciplina = false " +
                    "group by notas.matricula  ";
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

            q = "Select  round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,4))) as decimal)," + 3 + ")  from matriculas " +
                    "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    " and notas.materia = 0 " +
                    "group by notas.matricula    ";
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

            q = "Select matriculas.codigomat,notas.materia,notas.cuantitativa, " + notas.get(0).getNota() + "  " +
                    "from matriculas  " +
                    "left join estudiantes on matriculas.estudiante = estudiantes.codigoest " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    "and notas.seimprime = true  " +
                    "and notas.promedia = true " +
                    "and notas.disciplina = false " +
                    "order by estudiantes.apellido, notas.orden";
            nativo = adm.queryNativo(q);
            cabe1 = cabe1.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion() + " " + matriculas1.getCurso().getEspecialidad().getDescripcion() + " " + matriculas1.getCurso().getParalelo().getDescripcion());
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

    public JRDataSource mejorestudiante(Cursos curso, Matriculas matri) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Textos> textos = adm.query("Select o from Textos as o " +
                "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        String cabecera = regresaTexto("CMAT", textos);
        String aprobado = regresaTexto("PACMAT", textos);
        String reprobado = regresaTexto("PRCMAT", textos);
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o " +
                "where o.grupo = 'AP' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
                " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'  " +
                "and o.sistema.promediofinal = 'PF' ");
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
            String q = "Select round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,4))) as decimal)," + 3 + ") from matriculas " +
                    "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    "and notas.seimprime = true " +
                    "and notas.promedia = true " +
                    "and notas.disciplina = false " +
                    "group by notas.matricula  ";
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

            q = "Select  round(cast(avg(CAST(" + notas.get(0).getNota() + "  AS DECIMAL(8,4))) as decimal)," + 3 + ")  from matriculas " +
                    "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    " and notas.materia = 0 " +
                    "group by notas.matricula    ";
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

            q = "Select matriculas.codigomat,notas.materia,notas.cuantitativa, " + notas.get(0).getNota() + "  " +
                    "from matriculas  " +
                    "left join estudiantes on matriculas.estudiante = estudiantes.codigoest " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    "and notas.seimprime = true  " +
                    "and notas.promedia = true " +
                    "and notas.disciplina = false " +
                    "order by estudiantes.apellido, notas.orden";
            nativo = adm.queryNativo(q);
            cabe1 = cabe1.replace("[estudiante]", matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre()).replace("[curso]", matriculas1.getCurso().getDescripcion() + " " + matriculas1.getCurso().getEspecialidad().getDescripcion() + " " + matriculas1.getCurso().getParalelo().getDescripcion());
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

    public void calcularDisciplina(Cursos curso0) {
        String tipo = "INGRESADA";
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<ParametrosGlobales> para = adm.query("Select o from ParametrosGlobales as o " +
                "where o.variable = 'TIPODISCIPLINA' " +
                "and o.periodo.codigoper = '"+curso0.getPeriodo().getCodigoper()+"'");
        if(para.size()>0){
            ParametrosGlobales param = para.get(0);
            tipo = param.getCvalor();
        }

//        List<ParametrosGlobales> para = adm.query("Select o from ParametrosGlobales as o " +
//                "where o.variable = 'TIPODISCIPLINA' " +
//                "and o.periodo.codigoper = '"+curso0.getPeriodo().getCodigoper()+"'");
//        if(para.size()>0){
//            ParametrosGlobales param = para.get(0);
//            tipo = param.getCvalor();
//        }

        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
                "where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' " +
                "and o.sistema.esdisciplina = true " +
                "order by o.sistema.orden ");
        String query = "";
        if (tipo.equals("INGRESADA")) {
            return;
        }
        //CARGO EL QUERY PARA SELECCIONAR 
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        List<Cursos> listaLlega = new ArrayList<Cursos>();

        if (curso0.getCodigocur().equals(-2)) {
            listaLlega = adm.query("Select o from Cursos as o where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        } else {
            listaLlega.add(curso0);
        }
        for (Iterator<Cursos> ita = listaLlega.iterator(); ita.hasNext();) {
            Cursos ActualCurso = ita.next();

            List<MateriaProfesor> maprofes = adm.query("Select o from MateriaProfesor as o " +
                    "where o.curso.codigocur = '" + ActualCurso.getCodigocur() + "' " +
                    "and o.materia.codigo > 1 and o.ministerio = true ");
            if (maprofes.size() > 0) {

                String formu = "";

                for (Iterator<MateriaProfesor> it = maprofes.iterator(); it.hasNext();) {
                    MateriaProfesor acaMater = it.next();
                    formu += "N" + acaMater.getMateria().getCodigo() + "+";
                }

                formu = formu.substring(0, formu.length() - 1);
                if (tipo.contains("MITAD")){
                    formu = "(((" + formu + ")/" + maprofes.size() + ")+" + "(N1==null || N1==0 ?" + notaDisciplina + ":N1))/2";
                } else if (tipo.contains("PROMEDIO")){//
                    formu = "(((" + formu + ")+" + "(N1==null || N1==0 ?" + notaDisciplina + ":N1))/(" + maprofes.size() + "+1))";
                } else if (tipo.contains("SUMATORIA")){//PROMEDIO DE PROFESORES + PROMEDIO DE INSPECCION
                    formu = "((" + formu + ")/" + maprofes.size() + "+" + "(N1==null || N1==0 ?" + notaDisciplina + ":N1))";
                }
                List<Global> materias = adm.query("Select o from Global as o " +
                        "where o.codigo in (Select t.materia.codigo from MateriaProfesor as t" +
                        " where t.curso.codigocur = '" + ActualCurso.getCodigocur() + "' and t.ministerio = true ) " +
                        " ");
                String formula = formu;

                List<Global> Nmaterias = new ArrayList<Global>();
                ArrayList vectors = new ArrayList();
                Global global2 = new Global(1);
                Nmaterias.add(global2);
                vectors.add("VEC1");
                String ma = "";
                for (Iterator<Global> it = materias.iterator(); it.hasNext();) {
                    Global global = it.next();
                    if (formula.contains("N" + global.getCodigo())) {
                        Nmaterias.add(global);
                        ma += "VEC" + global.getCodigo() + ",";
                        vectors.add("VEC" + global.getCodigo());
                    }
                }



                if (ma.length() > 0) {
                    ma = ma.substring(0, ma.length() - 1);
                }
                Interpreter inter = new Interpreter();
                try {
                    //1 DISCIPLINA INSPECTOR
                    //0 disciplina 

                    for (Iterator<Global> it = Nmaterias.iterator(); it.hasNext();) {
                        Global global = it.next();
                        String q = "Select matriculas.codigomat, " + query + "  from matriculas  " +
                                "left join  estudiantes  on matriculas.estudiante = estudiantes.codigoest " +
                                "left join notas on matriculas.codigomat = notas.matricula " +
                                "and notas.materia = '" + global.getCodigo() + "' and notas.disciplina = true " +
                                "where matriculas.curso = '" + ActualCurso.getCodigocur() + "' " +
                                "and matriculas.estado in ('Matriculado','Recibir','Retirado') " +
                                "order by estudiantes.apellido";
                        System.out.println("" + q);
                        List nativo = adm.queryNativo(q);
//                        System.out.println(""+nativo);
                        System.out.println("RESULTADO VEC:" + global.getCodigo() + "=" + nativo);
                        inter.set("VEC" + global.getCodigo(), nativo);
                    }
                    String vector1 = (String) vectors.get(0);
//                    System.out.println("VECTOR 1: "+vector1);
                    inter.eval("int tamanio1 =  " + vector1 + ".size(); " +//OJOSSS
                            "int tamanio2 = ((Vector)" + vector1 + ".get(0)).size(); " +//OJOSSS
                            "Vector calculado = new Vector(); ");

                    String hola = ":::::::::::::::::::::::::::::::::.:";
                    inter.eval("System.out.print(tamanio1);");
                    inter.eval("System.out.print(tamanio2);");
                    inter.eval("for (int k = 0; k < tamanio1; k++) {" +
                            "                Vector resultado = new Vector();" +
                            "                Vector object = (Vector) " + vector1 + ".get(k);" +//OJOSSS
                            "                for (int i = 0; i < tamanio2; i++) {" +
                            "                    if (i == 0) {" +
                            "                        Integer cod = (object.get(i) != null ? ((Integer) object.get(i)) : 0);" +
                            "                        resultado.add(cod);" +
                            "                    } else {" +
                            "                        resultado.add(0.0);" +
                            "                    }" +
                            "                }" +
                            "                calculado.add(resultado); " +
                            "                " +
                            "                      " +
                            "            }");
                    String asumar = "";
                    String aconvertir = "";
                    for (Iterator it = vectors.iterator(); it.hasNext();) {
                        String object = (String) it.next();
                        String codigo = object.replace("VEC", "");
                        asumar += "Vector object" + codigo + " = (Vector) " + object + ".get(k); ";
                        aconvertir += "Double N" + codigo + " = (object" + codigo + ".get(i) != null ? ((Double) object" + codigo + ".get(i)) : 0.0);";
                    }
                    System.out.println("-------------" + aconvertir);
                    System.out.println("-------------" + formula);

                    try {

                        Object obtenido = inter.eval("calculado.get(0);");
//                        N3+N4+N6+N7+N8+N12+N14+N15+N16+N18+N19+N20+N36+N32+N17)/15+(N1==NULL || N1==0 ?5.0:N1))
                        System.out.println("N1:          " + obtenido);
                    } catch (Exception e) {
                        System.out.println("" + e);
                    }


                    inter.eval(" for (int k = 0; k < " + vector1 + ".size(); k++) {" +
                            "                     " + asumar +
                            "                Vector resultado = (Vector) calculado.get(k);" +
                            "                " +
                            "                for (int i = 1; i < resultado.size(); i++) { " +
                            "                 " + aconvertir + " " +
                            "                        resultado.set(i, " + formula + ");" +
                            "                   " +
                            "                }" +
                            "                calculado.set(k, resultado);   " +
                            "             " +
                            "            }    ");
                    Vector aguardar = (Vector) inter.get("calculado");

                    //INICIO PROCESO DE GUARDAR LAS NOTAS
                    secuencial sec = new secuencial();

                    for (int i = 0; i < aguardar.size(); i++) {
                        try {
                            //Row object = (Row) aguardar.get(i);
                            Vector vecDato = (Vector) aguardar.get(i);
                            Notas nota = new Notas();
                            nota.setCodigonot(sec.generarClave());
                            nota.setMatricula(new Matriculas(new Integer(((Integer) vecDato.get(0)))));
                            nota.setMateria(new Global(0));

                            nota.setFecha(new Date());
                            inter.set("nota", nota);
                            for (int j = 1; j < vecDato.size(); j++) {
                                Double object1 = (Double) vecDato.get(j);
                                String toda = notas.get(j - 1).getNota() + "";
                                String uno = toda.substring(0, 1).toUpperCase();
                                toda = toda.substring(1, toda.length());
                                inter.eval("nota.set" + (uno + toda) + "(" + redondear(new Double(object1), 2) + ");");
                            }
                            nota = (Notas) inter.get("nota");
                            nota.setCuantitativa(true);
                            nota.setOrden(100);
                            nota.setSeimprime(true);
                            nota.setPromedia(false);
                            nota.setDisciplina(true);
                            if (nota.getMateria().getCodigo().equals(0)) {
                                nota.setDisciplina(false);
                            }
                            String del = "Delete from Notas where matricula.curso.codigocur = '" + ActualCurso.getCodigocur() + "' " +
                                    " and materia.codigo = '0' " +
                                    " and  matricula.codigomat = '" + nota.getMatricula().getCodigomat() + "'";
                            adm.ejecutaSql(del);

                            adm.guardar(nota);

                        } catch (EvalError ex) {
                            System.out.println("" + ex.getErrorSourceFile());
                            System.out.println("1: " + ex.getMessage());
                            System.out.println("2: " + ex.getErrorText());
                            System.out.println("3: " + ex.getScriptStackTrace());
                            System.out.println("4: " + ex.getCause());
                            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                } catch (EvalError ex) {
                    System.out.println("7: " + ex.getErrorSourceFile());
                    System.out.println("8: " + ex.getMessage());
                    System.out.println("9: " + ex.getErrorText());
                    System.out.println("10: " + ex.getScriptStackTrace());
                    System.out.println("11: " + ex.getCause());
                    Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void nuevaClave() {
   try {
Administrador adm = new Administrador();
String apellidos = "jadan";
        String nombres = "geovanny";
                String user = apellidos.substring(0,2)+"JC"+nombres.substring(0,2)+"" ;

//                System.out.println(user);
                    List rs2 = adm.queryNativo("SELECT IF(max(usuario)is null,'"+user+"000', MAX(usuario)) FROM estudiantes " +
                            "where usuario like '%"+user+"%'  ");
                     if(rs2.size()>0){
                        String user2 = rs2.get(0).toString();
                        Integer valor = new Integer(user2.substring(6))+1;
                         String nuevo =  String.format("%03d",valor);
                         user = user2.substring(0, 4) +nuevo;
//                         stmt2.executeUpdate("Update aca_estudiantes " +
//                                "set est_usuario = '"+user+"', est_clave = '"+user+"' where est_codigo = '"+codigo+"' ");
                     }else{
                        user = user+"001";

                     }

        } catch (Exception ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
