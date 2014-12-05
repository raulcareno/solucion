/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bsh.EvalError;
import bsh.Interpreter;
import java.awt.Robot;
import java.math.BigDecimal;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import org.joda.time.DateMidnight;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.*;

/**
 *
 * @author GEOVANNY
 */
public class notasComplemento {
    private Double notaDisciplina = 0.0;
    private static Boolean malFormulas = false;
    String redon = "public Double redondear(Double numero, int decimales) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero+\"\");" + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" + "        return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
    String truncar = "public Double truncar(Double numero, int decimales) {         try {             java.math.BigDecimal d = new java.math.BigDecimal(numero+\"\");             d = d.setScale(decimales, java.math.BigDecimal.ROUND_DOWN);             return d.doubleValue();         } catch (Exception e) {             return 0.0;         }     }";
    String equival = "public Double equivalencia(Double numero) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero+\"\");" + "       return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
    String prom1 = metodos.prom1;
    String prom2 = metodos.prom2;
    public notasComplemento() {
    }
    
        @SuppressWarnings("static-access")
    public String guardar(List col, Cursos curso, MateriaProfesor materia) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();


        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean noformulasencualitativas = metodos.regresaVariableParametrosLogico("NOAPLICAFORMULAS", parametrosGlobales);
        boolean truncarNotas = metodos.regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        try {
            System.out.println("INICIO EN: " + new Date());
            Interpreter inter = new Interpreter();
            inter.eval(redon);
            inter.eval(truncar);
            inter.eval(prom1); inter.eval(prom2);
            inter.eval(equival);

            List<Notanotas> notas = adm.query("Select o from Notanotas as o where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' order by o.sistema.orden ");
//            List<Sistemacalificacion> sisFormulas = adm.query("Select o from Sistemacalificacion as o "
//                    + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.formula <> '' "
//                    + "  order by o.orden ");
//            System.out.println("1.-"+(new Date()));
//            for (Iterator<Sistemacalificacion> it = sisFormulas.iterator(); it.hasNext();) {
//                Sistemacalificacion siCal = it.next();
//               if (verificar(siCal.getFormula(), notas)) {
//                    return "Revise la formula de ['" + siCal.getNombre() + "'] del Sistema de Calificacion ";
//                }
//            }
//            System.out.println("2.-"+(new Date()));
            String valida = validar(col, notas);
            if (!valida.isEmpty()) {
                return valida;
            }


            secuencial sec = new secuencial();

//            String del = "Delete from Notas where matricula.curso.codigocur = '" + curso.getCodigocur() + "' " + "and materia.codigo = '" + materia.getMateria().getCodigo() + "'  and notas.disciplina = false ";
//            adm.ejecutaSql(del);

            List codigosNotas = adm.query("Select o.codigonot from Notas as o "
                    + " where o.matricula.curso.codigocur = '" + curso.getCodigocur() + "' "
                    + " and o.materia.codigo = '" + materia.getMateria().getCodigo() + "'  "
                    + " and o.disciplina = false ");
            String codigosNotasString = "";
            if (codigosNotas.size() > 0) {
                for (Iterator it = codigosNotas.iterator(); it.hasNext();) {
                    Object object = it.next();
                    codigosNotasString += "'" + object.toString() + "',";
                }
                System.out.println("" + codigosNotasString);
                codigosNotasString = codigosNotasString.substring(0, codigosNotasString.length() - 1);
//                return "FALLO";

            }
//            adm.ejecutaSql(del);
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
                        Decimalbox object1 = (Decimalbox) labels.get(j);
                        if (object1.getValue() == null) {
                            object1.setValue(new BigDecimal(0));
                        }

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
                        if (truncarNotas) {
                            inter.eval("nota.set" + (uno + toda) + "(" + metodos.truncar(aCargar, 2) + ");");
                        } else {
                            inter.eval("nota.set" + (uno + toda) + "(" + metodos.redondear(aCargar, 2) + ");");
                        }

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
            if (codigosNotasString.length() > 0) {
                String del = "Delete from Notas where codigonot in  (" + codigosNotasString + ")  ";
                adm.ejecutaSql(del);
            }

            Thread cargar = new Thread("m" + materia.getCodigomap() + "c" + curso.getCodigocur()) {

                public void run() {
                    String matcod = this.getName().substring(this.getName().indexOf("m") + 1, this.getName().indexOf("c"));
                    String curcod = this.getName().substring(this.getName().indexOf("c") + 1, this.getName().length());
                    recalculoNotas(new MateriaProfesor(new Integer(matcod)), new Cursos(new Integer(curcod)));
                }
            };
            cargar.start();

            System.out.println("FINALIZO EN: " + new Date());
            return "ok";
        } catch (EvalError ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en:  " + ex;
        }


    }

    public String guardarCualitativas(List col, Cursos curso, MateriaProfesor materia) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = metodos.regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        boolean noformulasencualitativas = metodos.regresaVariableParametrosLogico("NOAPLICAFORMULAS", parametrosGlobales);
        try {
            System.out.println("INICIO EN: " + new Date());
            Interpreter inter = new Interpreter();
            inter.eval(redon);
            inter.eval(truncar);
            inter.eval(prom1); inter.eval(prom2);
            inter.eval(equival);
//        
            List<Notanotas> notas = adm.query("Select o from Notanotas as o where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' order by o.sistema.orden ");
//            List<Sistemacalificacion> sisFormulas = adm.query("Select o from Sistemacalificacion as o "
//                    + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.formula <> '' "
//                    + "  order by o.orden ");
//            for (Iterator<Sistemacalificacion> it = sisFormulas.iterator(); it.hasNext();) {
//                Sistemacalificacion siCal = it.next();
//                if (verificar(siCal.getFormula(), notas)) {
//                    return "Revise la formula de ['" + siCal.getNombre() + "'] del Sistema de Calificacion ";
//                }
//            }
//            String valida = validar(col, notas);
//            if (!valida.isEmpty()) {
//                return valida;
//            }


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
                        Listbox object1 = (Listbox) labels.get(j);
                        String formula = notas.get(j - 2).getSistema().getFormula(); // EN CASO DE FORMULA
                        formula = formula.replace("no", "nota.getNo"); //EN CASO DE QUE HAYA FORMULA
                        String toda = notas.get(j - 2).getNota() + "";
                        String uno = toda.substring(0, 1).toUpperCase();
                        toda = toda.substring(1, toda.length());
                        String vaNota = ((Equivalencias) object1.getSelectedItem().getValue()).getNota().toString();
                        Double aCargar = 0.0;
                        if (vaNota.equals("")) {
                            aCargar = 0.0;
                        } else {
                            aCargar = new Double(vaNota);
                        }
                        if (truncarNotas) {
                            inter.eval("nota.set" + (uno + toda) + "(" + metodos.truncar(aCargar, 2) + ");");
                        } else {
                            inter.eval("nota.set" + (uno + toda) + "(" + metodos.redondear(aCargar, 2) + ");");
                        }
                        if (!formula.isEmpty() && noformulasencualitativas == false) {
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

      public void reguardar(Cursos curso, MateriaProfesor materia, String separador) {

        Rows fil = devolverRow(curso, materia, separador);
        guardar(fil.getChildren(), curso, materia);
        fil = null;
        //guardarActualizar(fil.getChildren(), curso, materia);

        //new notasEvaluacion.guardarYactualizarHilo(fil.getChildren(), curso, materia).start();
    }
    public Rows devolverRow(Cursos curso, MateriaProfesor materia, String separador) {
        Rows filas = new Rows();
        separador = separador.substring(6, 7);
        Interpreter inter = new Interpreter();
        System.out.println("TOP INI; " + new Date());
        int tamanio = 0;
        Session ses = Sessions.getCurrent();
        Empleados empleado = (Empleados) ses.getAttribute("user");
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//     if(listad==null){
        Administrador adm = new Administrador();
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = metodos.regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' order by o.orden");
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
        filas.getChildren().clear();
        Decimalbox notaTexto = null;
        Label label3 = null;

        String q = "Select distinct matriculas.codigomat,concat(estudiantes.apellido,' ',estudiantes.nombre,'[',matriculas.estado,']'), " + query + "  from matriculas "
                + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                + "left join notas on matriculas.codigomat = notas.matricula "
                + "and notas.materia = '" + materia.getMateria().getCodigo() + "' and notas.disciplina = false  "
                + "where matriculas.curso = '" + curso.getCodigocur() + "'  and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir Pase'  or matriculas.estado  = 'Emitir Pase'  or matriculas.estado  = 'Retirado' ) "
                + "order by estudiantes.apellido";

        List<Equivalencias> equ = null;
//        System.out.println("" + q);
        if (materia.getCuantitativa() == false) {

            equ = adm.query("Select o from Equivalencias as o"
                    + " where o.periodo.codigoper  = '" + materia.getCurso().getPeriodo().getCodigoper() + "' "
                    + "and o.grupo = 'AP' ");
        }

        List nativo = adm.queryNativo(q);
        Row row = new Row();
        String Shabilitado = "color:black;font-weight:bold;width:27px;font:arial;font-size:11px;text-align:right;";
        String Sdeshabilitado = "color: black !important; cursor: default !important; opacity: .6; -moz-opacity: .6; filter: alpha(opacity=60); width:27px;font:arial;font-size:11px;text-align:right;background:transparent;font-weigth:bold";
//        String Shabilitado = "color:black;font-weight:bold;width:37px;font:arial;font-size:12px;text-align:right;";
        //      String Sdeshabilitado = "color: black !important; cursor: default !important; opacity: .6; -moz-opacity: .6; filter: alpha(opacity=60); width:37px;font:arial;font-size:12px;text-align:right;background:transparent;font-weigth:bold";
        String Sdeshabilitadorojo = "color: red !important; cursor: default !important; opacity: .6; -moz-opacity: .6; filter: alpha(opacity=60); width:30px;font:arial;font-size:12px;text-align:right;background:transparent;font-weigth:bold";
//        System.out.println("antes del select"+(new Date()));
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            row = new Row();
            Boolean deshabilitado = false;
            String color = "black";
            Double pg = 0.0;
            Double sup = 0.0;
            Double rem = 0.0;
            if (materia.getCuantitativa()) {
                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
                    notaTexto = new Decimalbox();
                    notaTexto.setConstraint("no negative: No se permiten datos en NEGATIVO");
                    notaTexto.setTabindex(j);
                    label3 = new Label();
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
                            notaTexto.setValue(null);
                        } else {
                            if (truncarNotas) {
                                notaTexto.setValue(new BigDecimal(metodos.truncar((Double) dos, 2)));
                            } else {
                                notaTexto.setValue(new BigDecimal(metodos.redondear((Double) dos, 2)));
                            }

                        }
                        int dat = j - 2;
                        if (((Sistemacalificacion) sistemas.get(dat)).getPromediofinal().equals("PG")) {
                            pg = valor;
                        } else if (((Sistemacalificacion) sistemas.get(dat)).getPromediofinal().equals("SU")) {
                            sup = valor;
                        } else if (((Sistemacalificacion) sistemas.get(dat)).getPromediofinal().equals("RE")) {
                            rem = valor;
                        }

                    } else {
                        String valor = dos.toString().replace("(", "").replace(")", "").replace("\"", "").replace(",", "");
                        valor = valor.replace("[Emitir Pase]", "(PE)");
                        valor = valor.replace("[Retirado]", "(R)");
                        valor = valor.replace("[Recibir Pase]", "(PR)");
                        valor = valor.replace("[Matriculado]", "");
                        label3.setValue("" + valor);
                    }
//                                 label.setAttribute(q, dos);

                    if (j == 0) {
                        label3.setStyle(" ");
//                    label3.setReadonly(true);
                        row.appendChild(label3);
                    } else if (j == 1) {
                        label3.setStyle("width:300px;font-size:11px;font:arial; ");
//                    label3.setReadonly(true);
                        if (label3.getValue().contains("(PE)")) {
                            label3.setStyle("color:red;width:300px;font-size:11px;font:arial; ");
                            color = "red";
                            deshabilitado = true;
                        } else if (label3.getValue().contains("(R)")) {
                            label3.setStyle("color:blue;width:300px;font-size:11px;font:arial; ");
                            color = "blue";
                            deshabilitado = true;
                        }


                        row.appendChild(label3);
                    } else {
                        if (!deshabilitado) {

                            Date fechaActual = new Date();
                            DateMidnight actual = new DateMidnight(fechaActual);
                            int dat = j - 2;
                            DateMidnight inicial = new DateMidnight(((Sistemacalificacion) sistemas.get(dat)).getFechainicial());
                            DateMidnight finale = new DateMidnight(((Sistemacalificacion) sistemas.get(dat)).getFechafinal());
                            if (empleado.getTipo().equals("Interna")) {
                                inicial = new DateMidnight(((Sistemacalificacion) sistemas.get(dat)).getFechainti());
                                finale = new DateMidnight(((Sistemacalificacion) sistemas.get(dat)).getFechaintf());

                            }
                            final double limite = ((Sistemacalificacion) sistemas.get(dat)).getNotalimite();
                            notaTexto.addEventListener("onBlur", new org.zkoss.zk.ui.event.EventListener() {

                                public void onEvent(org.zkoss.zk.ui.event.Event event) throws Exception {
                                    //int show = Messagebox.show("Seguro que deséa Concertar una cita?" + ((Decimalbox)event.getTarget()).etValue(), "Alerta", Messagebox.OK, Messagebox.ERROR);
                                    try {
                                        Double valor = ((Decimalbox) event.getTarget()).getValue().doubleValue();
                                        if (valor > limite) {

                                            ((Decimalbox) event.getTarget()).setFocus(true);
                                            ((Decimalbox) event.getTarget()).focus();
                                            ((Decimalbox) event.getTarget()).setValue(null);
                                            Messagebox.show("ERROR 0001: Nota MAYOR a [" + limite + "] \n Fuera del rango establecido", "ERROR DE VALIDACION", Messagebox.CANCEL, Messagebox.ERROR);
//                                             Robot b = new Robot();
//                                                b.keyPress(java.awt.event.KeyEvent.VK_SHIFT);
//                                                b.keyPress(java.awt.event.KeyEvent.VK_TAB);
//                                                b.keyRelease(java.awt.event.KeyEvent.VK_SHIFT);

                                        }
                                    } catch (Exception e) {
                                        ((Decimalbox) event.getTarget()).setValue(null);
                                    }

                                }
                            });

                            //notaTexto.setAction("onkeyup:#{self}.value = #{self}.value.replace('.',',');");
                            notaTexto.setAction("onkeyup:#{self}.value = #{self}.value.replace('.','" + separador + "');");

                            notaTexto.addEventListener("onOK", new org.zkoss.zk.ui.event.EventListener() {

                                public void onEvent(org.zkoss.zk.ui.event.Event event) throws Exception {
                                    Robot b = new Robot();
                                    b.keyPress(java.awt.event.KeyEvent.VK_TAB);
                                    b.keyRelease(java.awt.event.KeyEvent.VK_TAB);
                                }
                            });
                            if (actual.compareTo(finale) <= 0 && actual.compareTo(inicial) >= 0) {
                                notaTexto.setDisabled(false);
                                notaTexto.setStyle(Shabilitado);
                            } else {
                                notaTexto.setDisabled(true);
                                notaTexto.setStyle(Sdeshabilitado);

                            }

                            try {
                                Date fecha = ((Sistemacalificacion) sistemas.get(dat)).getFechainicial();
                                if (empleado.getTipo().equals("Interna")) {
                                    fecha = ((Sistemacalificacion) sistemas.get(dat)).getFechainti();
                                }
//                            System.out.println("FECHA INICIAL: "+fecha);
                                if (fecha.getDate() == 0) {
                                    notaTexto.setDisabled(true);
                                    notaTexto.setStyle(Sdeshabilitado);
                                }
                            } catch (Exception z) {
                                notaTexto.setDisabled(true);
                                notaTexto.setStyle(Sdeshabilitado);
                            }


                            try {//valido si es que es una nota de supletorio o remedial 
                                inter.eval("Double pg = " + pg);
                                inter.eval("Double sup = " + sup);
                                inter.eval("Double rem = " + rem);
                                if ((((Sistemacalificacion) sistemas.get(dat)).getPromediofinal()).equals("SU")) {
                                    String formulaValidacion = (((Sistemacalificacion) sistemas.get(dat)).getValidacion());
//                                    formulaValidacion = " (pg >= 5 && pg<7?false:true)";
                                    if (!formulaValidacion.trim().equals("")) {

                                        Boolean valorObtenido = (Boolean) inter.eval(formulaValidacion);
                                        notaTexto.setDisabled(valorObtenido);
                                    } else {
                                    }
                                } else if ((((Sistemacalificacion) sistemas.get(dat)).getPromediofinal()).equals("RE")) {
                                    String formulaValidacion = (((Sistemacalificacion) sistemas.get(dat)).getValidacion());
                                    //                                  formulaValidacion = "( pg >=4 && pg <5) || (pg >=5 && sup <7)?false:true)";
                                    if (!formulaValidacion.trim().equals("")) {
                                        Boolean valorObtenido = (Boolean) inter.eval(formulaValidacion);
                                        notaTexto.setDisabled(valorObtenido);
                                    } else {
                                    }
                                } else if ((((Sistemacalificacion) sistemas.get(dat)).getPromediofinal()).equals("GR")) {
                                    String formulaValidacion = (((Sistemacalificacion) sistemas.get(dat)).getValidacion());
                                    //formulaValidacion = " (pg < 7 && (sup > 0 && sup<7) && (rem >0 && rem<7)?false:true)";
                                    if (!formulaValidacion.trim().equals("")) {
                                        Boolean valorObtenido = (Boolean) inter.eval(formulaValidacion);
                                        notaTexto.setDisabled(valorObtenido);
                                    } else {
                                    }
                                }
                            } catch (Exception e) {
                            }

                        } else {
                            notaTexto.setDisabled(true);
                            notaTexto.setStyle("color: " + color + " !important; cursor: default !important; opacity: .6; -moz-opacity: .6; filter: alpha(opacity=60); width:30px;font:arial;font-size:12px;text-align:right;background:transparent;font-weigth:bold");

                        }

                        row.appendChild(notaTexto);

                    }

                    //row.appendChild(label);
//                                 System.out.print(","+dos);
                }
            } else { // SI LA MATERIA ES CUALITATIVA APLICO UN COMBOBOX
                Shabilitado = "color:black;font-weight:bold;width:45px;font:arial;font-size:12px;";
                Sdeshabilitado = "color: black !important; cursor: default !important; opacity: .6; -moz-opacity: .6; filter: alpha(opacity=60); width:45px;font:arial;font-size:11px;background:transparent;font-weigth:bold";
                Listbox combo = new Listbox();
                Listitem item = new Listitem("");

                for (int j = 0; j < vec.size(); j++) {
                    Object dos = vec.get(j);
                    notaTexto = new Decimalbox();
                    combo.setTabindex(j);
                    label3 = new Label();
//                 label.setAttribute("onBlur", "alert(this)");
                    try {
                        if (dos.equals(null)) {
                            dos = new Double(0.0);
                        }
                    } catch (Exception e) {
                        dos = new Double(0.0);
                    }
                    if (j >= 2) {

                        if (dos == null) {
                            dos = equ.get(0);
                        }
                        combo = new Listbox();
                        combo.setMold("select");
                        combo.setWidth("50px");
                        combo.setRows(1);
                        combo.setStyle("font-size:9px;width:30px");
                        for (Iterator<Equivalencias> it2 = equ.iterator(); it2.hasNext();) {
                            Equivalencias equivalencias = it2.next();
                            item = new Listitem("" + equivalencias.getAbreviatura());
                            item.setValue(equivalencias);
                            combo.appendChild(item);

                        }
                        if (dos instanceof Double) {
                            dos = devolverNombre(equ, (((Double) dos).intValue()));
                        }
                        item = new Listitem(((Equivalencias) dos).getAbreviatura() + "");
                        item.setValue(dos);
                        combo.appendChild(item);
                        combo.setSelectedItem(item);
//                        Double valor = (Double) dos;
//                        if (valor.equals(0.0)) {
//                            notaTexto.setValue(new BigDecimal(0));
//                        } else {
//                            notaTexto.setValue(new BigDecimal(metodos.redondear((Double) dos, 2)));
//                        }

                    } else {
                        String valor = dos.toString().replace("(", "").replace(")", "").replace("\"", "").replace(",", "");
                        valor = valor.replace("[Emitir Pase]", "(PE)");
                        valor = valor.replace("[Retirado]", "(R)");
                        valor = valor.replace("[Recibir Pase]", "(PR)");
                        valor = valor.replace("[Matriculado]", "");
                        label3.setValue("" + valor);
                    }
//                                 label.setAttribute(q, dos);

                    if (j == 0) {
                        label3.setStyle(" ");
//                    label3.setReadonly(true);
                        row.appendChild(label3);
                    } else if (j == 1) {
                        label3.setStyle("width:300px;font-size:11px;font:arial; ");
//                    label3.setReadonly(true);
                        if (label3.getValue().contains("(PE)")) {
                            label3.setStyle("color:red;width:300px;font-size:11px;font:arial; ");
                            color = "red";
                            deshabilitado = true;
                        } else if (label3.getValue().contains("(R)")) {
                            label3.setStyle("color:blue;width:300px;font-size:11px;font:arial; ");
                            color = "blue";
                            deshabilitado = true;
                        }

                        row.appendChild(label3);
                    } else {
                        if (!deshabilitado) {
                            Date fechaActual = new Date();
                            DateMidnight actual = new DateMidnight(fechaActual);
                            int dat = j - 2;
                            DateMidnight inicial = new DateMidnight(((Sistemacalificacion) sistemas.get(dat)).getFechainicial());
                            DateMidnight finale = new DateMidnight(((Sistemacalificacion) sistemas.get(dat)).getFechafinal());
                            if (empleado.getTipo().equals("Interna")) {
                                inicial = new DateMidnight(((Sistemacalificacion) sistemas.get(dat)).getFechainti());
                                finale = new DateMidnight(((Sistemacalificacion) sistemas.get(dat)).getFechaintf());

                            }
                            final double limite = ((Sistemacalificacion) sistemas.get(dat)).getNotalimite();

                            if (actual.compareTo(finale) <= 0 && actual.compareTo(inicial) >= 0) {
                                combo.setDisabled(false);
                                combo.setStyle(Shabilitado);
                            } else {
                                combo.setDisabled(true);
                                combo.setStyle(Sdeshabilitado);

                            }

                            try {
                                Date fecha = ((Sistemacalificacion) sistemas.get(dat)).getFechainicial();
                                if (empleado.getTipo().equals("Interna")) {
                                    fecha = ((Sistemacalificacion) sistemas.get(dat)).getFechainti();


                                }
//                            System.out.println("FECHA INICIAL: "+fecha);
                                if (fecha.getDate() == 0) {
                                    combo.setDisabled(true);
                                    combo.setStyle(Sdeshabilitado);
                                }
                            } catch (Exception z) {
                                combo.setDisabled(true);
                                combo.setStyle(Sdeshabilitado);
                            }

                        } else {
                            combo.setDisabled(true);
                            combo.setStyle("color: " + color + " !important; cursor: default !important; opacity: .6; -moz-opacity: .6; filter: alpha(opacity=60); width:30px;font:arial;font-size:12px;text-align:right;background:transparent;font-weigth:bold");

                        }

                        row.appendChild(combo);

                    }

                    //row.appendChild(label);
//                                 System.out.print(","+dos);
                }


            }

            row.setParent(filas);
        }
        sistemas = null;
        parametrosGlobales = null;
        notas = null;
        nativo = null;
        System.out.println("TOP FIN; " + new Date());
        limpiarMemoria();
        return filas;
    }
    
         public String validar(List col, List<Notanotas> notas) {
        Administrador adm = new Administrador();
        for (int i = 0; i < col.size(); i++) {
            Row object = (Row) col.get(i);
            List labels = object.getChildren();
            Matriculas ma = (Matriculas) adm.buscarClave(new Integer(((Label) labels.get(0)).getValue()), Matriculas.class);
            //nota.setMatricula(new Matriculas(new Integer(((Label) vecDato.get(0)).getValue())));
            for (int j = 2; j < labels.size(); j++) {
                Decimalbox object1 = (Decimalbox) labels.get(j);
                if (object1.getValue() == null) {
                    object1.setValue(new BigDecimal(0));
                }
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
                    return "NOTA FUERA DE RANGO EN PERIODO: " + notas.get(j - 2).getSistema().getAbreviatura() + "  NOTA: " + aCargar + " "
                            + " ESTUDIANTE: " + ma.getEstudiante().getApellido() + " " + ma.getEstudiante().getNombre() + "  ";
                }
            }
        }
        return "";
    }


    public void recalculoNotas(MateriaProfesor materia, Cursos curso) {
        Administrador adm = new Administrador();
        materia = (MateriaProfesor) adm.buscarClave(materia.getCodigomap(), materia.getClass());
        curso = (Cursos) adm.buscarClave(curso.getCodigocur(), curso.getClass());
        Periodo periodo = curso.getPeriodo();
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper =  '" + periodo.getCodigoper() + "'  ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o where  o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' order by o.sistema.orden ");

        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        boolean truncarNotas = metodos.regresaVariableParametrosLogico("TRUNCARNOTAS", parametrosGlobales);
        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }

        List<MateriaProfesor> maprofes = adm.query("Select o from MateriaProfesor as o "
                + "where o.formula <> '' and o.formula like '%MA" + materia.getMateria().getCodigo() + "%' "
                + "and o.curso.codigocur = '" + curso.getCodigocur() + "' ");
        Interpreter inter = new Interpreter();
        try {
            inter.eval(redon);
            inter.eval(truncar);
            inter.eval(prom1); inter.eval(prom2);
            inter.eval(equival);
        } catch (Exception e) {
            System.out.println("error en evaluar: funciones previas redondeo, prom1" + e);
        }


        for (Iterator<MateriaProfesor> ita = maprofes.iterator(); ita.hasNext();) {
            try {
                MateriaProfesor map = ita.next();

                //   }
                List<Global> materias = adm.query("Select o from Global as o where o.grupo = 'MAT' "
                        + "and o.codigo in (Select ma.materia.codigo from  MateriaProfesor as ma "
                        + "where ma.curso.codigocur = '" + map.getCurso().getCodigocur() + "' ) ");
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

                try {
                    for (Iterator<Global> it = Nmaterias.iterator(); it.hasNext();) {
                        Global global = it.next();
                        String q = "Select matriculas.codigomat, " + query + "  from matriculas "
                                + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                                + "left join notas on matriculas.codigomat = notas.matricula "
                                + "and notas.materia = '" + global.getCodigo() + "' and notas.disciplina = false "
                                + "where matriculas.curso = '" + curso.getCodigocur() + "'  "
                                + " and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir Pase'  or matriculas.estado  = 'Emitir Pase'  or matriculas.estado  = 'Retirado' ) "
                                + "order by estudiantes.apellido";
//
                        List nativo = adm.queryNativo(q);
//                        System.out.println("recalculo 1: " + q);
//                        System.out.println("recalculo 2: " + nativo.size());
                        inter.set("VEC" + global.getCodigo(), nativo);
                    }
                    String vector1 = (String) vectors.get(0);
                    inter.eval("int tamanio1 =  " + vector1 + ".size(); " + "int tamanio2 = ((Vector)" + vector1 + ".get(0)).size(); " + "Vector calculado = new Vector(); ");
//                    inter.eval("System.out.println(tamanio1);");
//                    inter.eval("System.out.println(tamanio2);");
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
                    String del = "Delete from Notas where matricula.curso.codigocur = '" + curso.getCodigocur() + "' " + " "
                            + "and materia.codigo = '" + map.getMateria().getCodigo() + "' and disciplina = false ";
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
                                if (truncarNotas) {
                                    inter.eval("nota.set" + (uno + toda) + "(" + metodos.truncar(new Double(object1), 2) + ");");
                                } else {
                                    inter.eval("nota.set" + (uno + toda) + "(" + metodos.redondear(new Double(object1), 2) + ");");
                                }
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

            } catch (Exception ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public Equivalencias devolverNombre(List<Equivalencias> equiva, Integer codigo) {

        for (Iterator<Equivalencias> it = equiva.iterator(); it.hasNext();) {
            Equivalencias equivalencias = it.next();
            if (equivalencias.getValorminimo() <= codigo && codigo <= equivalencias.getValormaximo()) {
                return equivalencias;
            }
        }
        return equiva.get(0);


    }
   void limpiarMemoria() {
        System.gc();
        System.gc();
        System.gc();
        System.gc();
    }

  
}
