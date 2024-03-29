package bean;

import bsh.EvalError;
import bsh.Interpreter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;

import org.joda.time.DateMidnight;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.*;

public class notasRecalcular  {
//ArrayList listad = new ArrayList();
ArrayList grid = null;
    private Double notaDisciplina = 0.0;
    String redon = "public Double redondear(Double numero, int decimales) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" + "        return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
   String truncar = "public Double truncar(Double numero, int decimales) {         try {             java.math.BigDecimal d = new java.math.BigDecimal(numero);             d = d.setScale(decimales, java.math.BigDecimal.ROUND_DOWN);             return d.doubleValue();         } catch (Exception e) {             return 0.0;         }     }";
    String equival = "public Double equivalencia(Double numero) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "       return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
       String prom1 = metodos.prom1;
    String prom2 = metodos.prom2;
 

    public notasRecalcular() {
        //         Grid g;
//         Label l;
//         Row row;
//         row.getZIndex()
    }
    public void calcularSistema(Boolean preguntar){
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();
        List<MateriaProfesor> mprofe = adm.query("Select o from MateriaProfesor as o "
                + " where o.curso.periodo.codigoper = '"+periodo.getCodigoper()+"' and o.materia.codigo > 1 "
                + "order by o.curso.secuencia, o.curso.paralelo.descripcion, o.formula ASC, o.orden ");
        System.out.println("INICIO DEL PROCESO: "+new Date());
        int cursoActual = 0;
        Boolean mostrarPregunta = false;
        String materiasCurso = "";
        for (Iterator<MateriaProfesor> it = mprofe.iterator(); it.hasNext();) {
            MateriaProfesor materiaProfesor = it.next();
            grid = new ArrayList();
            int actual = materiaProfesor.getCurso().getCodigocur();
            if(cursoActual ==0){
                cursoActual = materiaProfesor.getCurso().getCodigocur();
                mostrarPregunta = false;
                materiasCurso = ""+materiaProfesor.getCurso()+":";
            }else if(cursoActual == actual){
                mostrarPregunta = false;
            }else{
                mostrarPregunta = true;
            }
            addRow(materiaProfesor.getCurso(), materiaProfesor);
            if(materiaProfesor.getCuantitativa()){
                guardar(grid, materiaProfesor.getCurso(), materiaProfesor);
            }else{
                guardarCualitativas(grid, materiaProfesor.getCurso(), materiaProfesor);
            }
            if(preguntar && mostrarPregunta){
                try {
                    int val = Messagebox.show("RESUMEN: "+materiasCurso+" ______________________________________   \n  ¿Desea Continuar con el Siguiente Curso?", "Seguridad",  Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
                        if(val == 16){
                            //Messagebox.show("Desea Continuar con el Siguiente Curso:?", "Administrador Educativo", Messagebox.OK, Messagebox.INFORMATION);
                            mostrarPregunta = false;
                        }else{
                        //break;
                            return;
                        }
                } catch (InterruptedException ex) {
                    Logger.getLogger(notasRecalcular.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            materiasCurso +=" " +materiaProfesor.getMateria().getDescripcion()+",";
            System.out.println("RECALCULADO EN: "+materiaProfesor.getCurso() +" " +materiaProfesor.getMateria().getDescripcion()+" - "+ new Date());
        }
    }

    public Boolean verificar(String formula, List<Notanotas> notas) {

        formula = formula.replace("()", "");


        Interpreter inter = new Interpreter();
        try {
            inter.eval(redon);
            inter.eval(truncar);
            inter.eval(prom1); inter.eval(prom2);
            inter.eval(equival);
            for (Iterator<Notanotas> it = notas.iterator(); it.hasNext();) {
                Notanotas notanotas = it.next();
                inter.eval("" + notanotas.getNota() + "=1;");
            }
            inter.eval(formula + "*1");
        } catch (EvalError ex) {
            Logger.getLogger(notasRecalcular.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
        return false;
    }

    public void addRow(Cursos curso, MateriaProfesor materia) {
//        System.out.println("TOP INI; " + new Date());
        int tamanio = 0;
        Session ses = Sessions.getCurrent();
        Empleados empleado = (Empleados) ses.getAttribute("user");
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//     if(listad==null){
        Administrador adm = new Administrador();
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
//        grid.getChildren().clear();
        Decimalbox notaTexto = null;
        Label label3 = null;

        String q = "Select distinct matriculas.codigomat,concat(estudiantes.apellido,' ',estudiantes.nombre,'[',matriculas.estado,']'), " + query + "  from matriculas "
                + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                + "left join notas on matriculas.codigomat = notas.matricula "
                + "and notas.materia = '" + materia.getMateria().getCodigo() + "' and notas.disciplina = false  "
                + "where matriculas.curso = '" + curso.getCodigocur() + "'  and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir Pase'  or matriculas.estado  = 'Emitir Pase'  or matriculas.estado  = 'Retirado' ) "
                + "order by estudiantes.apellido";
        //System.out.println("" + q);
        ParametrosGlobales para = (ParametrosGlobales) adm.buscarClave(new Integer(1), ParametrosGlobales.class);
        if (para.getCvalor().equals("P")) {
            q = "Select matriculas.codigomat,(estudiantes.apellido,' ',estudiantes.nombre,'(',matriculas.estado,')'), " + query + "  from matriculas "
                    + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "and notas.materia = '" + materia.getMateria().getCodigo() + "' and notas.disciplina = false  "
                    + "where matriculas.curso = '" + curso.getCodigocur() + "'  and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir Pase' or matriculas.estado  = 'Emitir Pase'  or matriculas.estado  = 'Retirado' )"
                    + "order by estudiantes.apellido";
        }

        List<Equivalencias> equ = null;
//        System.out.println("" + q);
        if (materia.getCuantitativa() == false) {

            equ = adm.query("Select o from Equivalencias as o"
                    + " where o.periodo.codigoper  = '" + materia.getCurso().getPeriodo().getCodigoper() + "' "
                    + "and o.grupo = 'AP' ");
        }

        List nativo = adm.queryNativo(q);
        Row row = new Row();
        String Shabilitado = "color:black;font-weight:bold;width:37px;font:arial;font-size:12px;text-align:right;";
        String Sdeshabilitado = "color: black !important; cursor: default !important; opacity: .6; -moz-opacity: .6; filter: alpha(opacity=60); width:37px;font:arial;font-size:12px;text-align:right;background:transparent;font-weigth:bold";
        String Sdeshabilitadorojo = "color: red !important; cursor: default !important; opacity: .6; -moz-opacity: .6; filter: alpha(opacity=60); width:30px;font:arial;font-size:12px;text-align:right;background:transparent;font-weigth:bold";
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            row = new Row();
            Boolean deshabilitado = false;
            String color = "black";

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
                            notaTexto.setValue(new BigDecimal(0));
                        } else {
                            notaTexto.setValue(new BigDecimal(redondear((Double) dos, 2)));
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
                            notaTexto.addEventListener("onBlur", new EventListener() {

                                public void onEvent(org.zkoss.zk.ui.event.Event event) throws Exception {
                                    //int show = Messagebox.show("Seguro que deséa Concertar una cita?" + ((Decimalbox)event.getTarget()).etValue(), "Alerta", Messagebox.OK, Messagebox.ERROR);
                                    try {
                                        Double valor = ((Decimalbox) event.getTarget()).getValue().doubleValue();
                                        if (valor > limite) {

                                            ((Decimalbox) event.getTarget()).setFocus(true);
                                            ((Decimalbox) event.getTarget()).focus();
                                            ((Decimalbox) event.getTarget()).setValue(new BigDecimal(0));
                                            Messagebox.show("ERROR 0001: Nota MAYOR a [" + limite + "] \n Fuera del rango establecido", "ERROR DE VALIDACION", Messagebox.CANCEL, Messagebox.ERROR);
                                        }
                                    } catch (Exception e) {
                                        ((Decimalbox) event.getTarget()).setValue(new BigDecimal(0));
                                    }

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
//                            notaTexto.setValue(new BigDecimal(redondear((Double) dos, 2)));
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
            grid.add(row); 
            //row.setParent(grid);
        }
        nativo = null;
//        System.out.println("TOP FIN; " + new Date());
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

    public String validar(List col, List<Notanotas> notas) {
        Administrador adm = new Administrador();
        for (int i = 0; i < col.size(); i++) {
            Row object = (Row) col.get(i);
            List labels = object.getChildren();
            Matriculas ma = (Matriculas) adm.buscarClave(new Integer(((Label) labels.get(0)).getValue()), Matriculas.class);
            //nota.setMatricula(new Matriculas(new Integer(((Label) vecDato.get(0)).getValue())));
            for (int j = 2; j < labels.size(); j++) {
                Decimalbox object1 = (Decimalbox) labels.get(j);
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

    @SuppressWarnings("static-access")
    public String guardar(ArrayList col, Cursos curso, MateriaProfesor materia) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        try {
//            System.out.println("INICIO EN: " + new Date());
            Interpreter inter = new Interpreter();
            inter.eval(redon);
            inter.eval(truncar);
            inter.eval(prom1); inter.eval(prom2);
            inter.eval(equival);
            Administrador adm = new Administrador();
            List<Notanotas> notas = adm.query("Select o from Notanotas as o where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' order by o.sistema.orden ");
            List<Sistemacalificacion> sisFormulas = adm.query("Select o from Sistemacalificacion as o "
                    + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.formula <> '' "
                    + "  order by o.orden ");
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
                        Decimalbox object1 = (Decimalbox) labels.get(j);
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
                    Logger.getLogger(notasRecalcular.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            recalculoNotas(materia, curso);
//            System.out.println("FINALIZO EN: " + new Date());
            return "ok";
        } catch (EvalError ex) {
            Logger.getLogger(notasRecalcular.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en:  " + ex;
        }


    }

    public String guardarCualitativas(ArrayList col, Cursos curso, MateriaProfesor materia) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        try {
//            System.out.println("INICIO EN: " + new Date());
            Interpreter inter = new Interpreter();
            inter.eval(redon);
            inter.eval(truncar);
            inter.eval(prom1); inter.eval(prom2);
            inter.eval(equival);
            Administrador adm = new Administrador();
            List<Notanotas> notas = adm.query("Select o from Notanotas as o where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' order by o.sistema.orden ");
            List<Sistemacalificacion> sisFormulas = adm.query("Select o from Sistemacalificacion as o "
                    + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.formula <> '' "
                    + "  order by o.orden ");
            for (Iterator<Sistemacalificacion> it = sisFormulas.iterator(); it.hasNext();) {
                Sistemacalificacion siCal = it.next();
                if (verificar(siCal.getFormula(), notas)) {
                    return "Revise la formula de ['" + siCal.getNombre() + "'] del Sistema de Calificacion ";
                }
            }
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
                        inter.eval("nota.set" + (uno + toda) + "(" + redondear(aCargar, 2) + ");");
                        if (!formula.isEmpty()) {
                            inter.eval("nota.set" + (uno + toda) + "(" + formula + ");");
                        }
                    }
                    nota = (Notas) inter.get("nota");
                    adm.guardar(nota);

                } catch (EvalError ex) {
                    Logger.getLogger(notasRecalcular.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            recalculoNotas(materia, curso);
//            System.out.println("FINALIZO EN: " + new Date());
            return "ok";
        } catch (EvalError ex) {
            Logger.getLogger(notasRecalcular.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en:  " + ex;
        }


    }

    public void recalculoNotas(MateriaProfesor materia, Cursos curso) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();
Interpreter inter = new Interpreter();
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper =  '" + periodo.getCodigoper() + "'  ");
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

        List<MateriaProfesor> maprofes = adm.query("Select o from MateriaProfesor as o "
                + "where o.formula <> '' and o.formula like '%MA" + materia.getMateria().getCodigo() + "%' "
                + "and o.curso.codigocur = '" + curso.getCodigocur() + "' ");
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
                
                inter.eval(redon);
                inter.eval(truncar);
                inter.eval(prom1); inter.eval(prom2);
                inter.eval(equival);
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
                                inter.eval("nota.set" + (uno + toda) + "(" + redondear(new Double(object1), 2) + ");");
                            }
                            nota = (Notas) inter.get("nota");
                            adm.guardar(nota);
                        } catch (EvalError ex) {
                            Logger.getLogger(notasRecalcular.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
//            System.out.println(""+materia);
//            System.out.println(""+curso);
//            System.out.println(""+(Vector) inter.get("VEC17"));
//            System.out.println(""+(Vector) inter.get("VEC18"));
//          System.out.println(""+aguardar);
                } catch (EvalError ex) {
                    Logger.getLogger(notasRecalcular.class.getName()).log(Level.SEVERE, null, ex);
                }

//            System.out.println(""+materia);
//            System.out.println(""+curso);
//            System.out.println(""+(Vector) inter.get("VEC17"));
//            System.out.println(""+(Vector) inter.get("VEC18"));
//          System.out.println(""+aguardar);

            } catch (EvalError ex) {
                Logger.getLogger(notasRecalcular.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
 
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

    public void generarNumeros(String tipo, Date fecha, Boolean empezar) {
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Global> especial = adm.query("Select o from Global as o where o.grupo = 'ESP' ");
        for (Iterator<Global> it = especial.iterator(); it.hasNext();) {
            Global especialidad = it.next();
            List<Cursos> curs = adm.query("Select o from Cursos as o where o.secuencia  = 13  "
                    + "and o.titulo.codigo = '" + especialidad.getCodigo() + "' and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
            String codigosCursos = "(";
            for (Iterator<Cursos> itCursos = curs.iterator(); itCursos.hasNext();) {
                Cursos cursos = itCursos.next();
                codigosCursos += cursos.getCodigocur() + ",";
            }
            if (codigosCursos.length() > 1) {
                codigosCursos = codigosCursos.substring(0, codigosCursos.length() - 1);
            }
            if (tipo.contains("all")) {

                try {
                    if (codigosCursos.length() > 1) {
                        
                        List<Notasacta> matriculas = adm.query("Select o from Notasacta as o "
                                + "where o.matricula.curso.secuencia = 13 and o.matricula.perdio = false "
                                + "and o.matricula.estado in ('Matriculado','Recibir Pase') and o.matricula.suspenso = false "
                                + "and  o.matricula.curso.codigocur  in " + codigosCursos + ") "
                                + " order by o.matricula.estudiante.apellido,  o.matricula.estudiante.nombre  ");
                        System.out.println("GENERADOS CURSOS: " + especialidad+" NO GRADUADOS:"+matriculas.size());
                        int i = 1;
                        System.out.println("__________________________________________________ ESPECIALIDAD: " + especialidad);
                        for (Notasacta acta : matriculas) {
                            System.out.println("" + acta.getMatricula().getEstudiante() + " : " + i);
                            acta.setFecha(fecha);
                            acta.setNumeroacta(i);
                            i++;
                            adm.actualizar(acta);
                        }
                    } else {
                        System.out.println("NO HAY CODIGOSCURSOS: "+especialidad);

                    }
                } catch (Exception e) {
                    System.out.println("NO EXISTEN CURSOS CON CODIGOS: " + codigosCursos);
                }

            } else {
                try {
                    if (codigosCursos.length() > 1) {
                          

//                        String q = "Select o from Notasacta as o "
//                                + "where o.matricula.curso.secuencia = 13 and o.matricula.suspenso = true "
//                                + "and  o.matricula.curso.codigocur  in " + codigosCursos + ")  "
//                                + " order by o.matricula.estudiante.apellido,  o.matricula.estudiante.nombre  ";
//                        System.out.println("" + q);
//                        List<Notasacta> matriculas = adm.query(q);
                         List<Notasacta> matriculas = adm.query("Select o from Notasacta as o "
                                + "where o.matricula.curso.secuencia = 13 and o.matricula.perdio = false "
                                + "and o.matricula.estado in ('Matriculado','Recibir Pase') and o.matricula.suspenso = true "
                                + "and  o.matricula.curso.codigocur  in " + codigosCursos + ") "
                                + " order by o.matricula.estudiante.apellido,  o.matricula.estudiante.nombre  ");
                        System.out.println("GENERADOS CURSOS: " + especialidad+" # SUSPENDIDOS:"+matriculas.size());
                        if (matriculas.size() > 0) {
                            Integer ultimo = 0;
                            if(empezar){
                                ultimo = 0;
                            }else{
                                List numeroFinal = adm.query("Select max(o.numeroacta) from Notasacta as o "
                                    + "where o.matricula.suspenso = false  "
                                    + "and  o.matricula.curso.codigocur  in " + codigosCursos + ")  ");
                                ultimo = (Integer) numeroFinal.get(0);
                            
                            }
                            
                            int i = ultimo + 1;
                            for (Notasacta acta : matriculas) {
                                acta.setFecha(fecha);
                                acta.setNumeroacta(i);
                                i++;
                                adm.actualizar(acta);
                            }

                        }

                    } else {
                      System.out.println("NO HAY CODIGOSCURSOS: "+especialidad);

                    }
                } catch (Exception e) {
                    System.out.println("NO EXISTEN CURSOS CON CODIGOS: " + codigosCursos);
                }

            }


        }






    }
  
}
