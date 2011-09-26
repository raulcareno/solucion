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
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public class notasGrado extends Rows {
//ArrayList listad = new ArrayList();

    public notasGrado() {
//        Cursos todos = new Cursos(-2);
//        todos.setDescripcion("[TODOS]");
    }

    public void addRow(Cursos curso) {
        Administrador adm = new Administrador();
        List<Materiasgrado> notas = adm.query("Select o from Materiasgrado as o"
                + " where o.curso.codigocur = '" + curso.getCodigocur() + "' order by o.codigo ");
        String query = "";
        ArrayList estados = new ArrayList();
        Session ses = Sessions.getCurrent();
          Empleados user = (Empleados) ses.getAttribute("user");
/*
            Cursos periodo = (Cursos) ses.getAttribute("curso");
                List sistemas = adm.query("Select o from Materiasgrado as o "+
                 " where o.curso.codigocur =  '"+periodo.getCodigocur()+"' order by o.codigo ");
              
                    else if(!((Materiasgrado)sistemas.get(i)).getProfesor().getCodigoemp().equals(user.getCodigoemp())){
                            values[i][1]= "readonly";
                     }*/
          estados.add(false);
          estados.add(false);
        for (Materiasgrado notass : notas) {
            query += notass.getColumna() + ",";
                    if(!notass.getProfesor().getCodigoemp().equals(user.getCodigoemp()) && !user.getTipo().equals("Interna")){
                        estados.add(true);
                     }else if(notass.getEspromedio()){
                        estados.add(true); 
                    }else if(user.getTipo().equals("Interna")){
                        estados.add(false);
                    }else{
                        estados.add(false);
                    }
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        getChildren().clear();
        Label label3 = null;
        Decimalbox label = null;
        String q = "Select matriculas.codigomat,concat(estudiantes.apellido,' ',estudiantes.nombre), " + query + "  from matriculas "
                + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                + "left join notasgrado on matriculas.codigomat = notasgrado.matricula "
                + "where matriculas.curso = '" + curso.getCodigocur() + "' and  matriculas.estado in ('Matriculado','Recibir Pase') "
                + "order by estudiantes.apellido";
        ParametrosGlobales para = (ParametrosGlobales) adm.buscarClave(new Integer(1), ParametrosGlobales.class);
        if (para.getCvalor().equals("P")) {
            q = "Select matriculas.codigomat,(estudiantes.apellido,' ',estudiantes.nombre), " + query + "  from matriculas "
                    + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                    + "left join notasgrado on matriculas.codigomat = notasgrado.matricula "
                    + "where matriculas.curso = '" + curso.getCodigocur() + "' "
                    + "order by estudiantes.apellido";
        }
        List nativo = adm.queryNativo(q);
        Row row = new Row();
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            row = new Row();
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                label = new Decimalbox();
                label3 = new Label();
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
                        label.setReadonly((Boolean)estados.get(j));
                        label.setValue(new BigDecimal(0));
                    } else {
                        label.setReadonly((Boolean)estados.get(j));
                        label.setValue(new BigDecimal(redondear((Double) dos, 3)));
                    }

                } else {
                    String valor = dos.toString().replace("(", "").replace(")", "").replace("\"", "").replace(",", "");
                    label3.setValue("" + valor);
                }
                if (j == 0) {
                    label3.setStyle("width:15px;font-size:11px;font:arial; ");
                    //label3.setReadonly(true);
                    row.appendChild(label3);
                } else if (j == 1) {
                    label3.setStyle("width:300px;font-size:11px;font:arial; ");
                    //label3.setReadonly(true);
                    row.appendChild(label3);
                } else if (j == (vec.size() - 1)) {
//                    label.setDisabled(true);
                    row.appendChild(label);
                } else {

                    row.appendChild(label);
                }
//                row.appendChild(label);
//                                 System.out.print(","+dos);
            }

            row.setParent(this);
        }
        nativo = null;



    }

    @SuppressWarnings("static-access")
    public String guardar(List col, Cursos curso) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        try {
            String redon = "public Double redondear(Double numero, int decimales) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" + "        return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
//            System.out.println("INICIO EN: " + new Date());
            Interpreter inter = new Interpreter();
            inter.eval(redon);
            Administrador adm = new Administrador();
            List<Materiasgrado> notas = adm.query("Select o from Materiasgrado as o "
                    + "where o.curso.codigocur = '" + curso.getCodigocur() + "' order by o.codigo ");
//            List<Sistemacalificacion> sisFormulas = adm.query("Select o from Sistemacalificacion as o " +

//            for (Iterator<Sistemacalificacion> it = sisFormulas.iterator(); it.hasNext();) {
//                Sistemacalificacion siCal = it.next();
//                        if(verificar(siCal.getFormula(), notas)){
//                              return "Revise la formula de ['"+siCal.getNombre()+"'] del Sistema de Calificacion " ;
//                        }
//            }
            secuencial sec = new secuencial();
            String del = "Delete from Notasgrado where matricula.curso.codigocur = '" + curso.getCodigocur() + "' ";
            adm.ejecutaSql(del);
            for (int i = 0; i < col.size(); i++) {
                try {
                    Row object = (Row) col.get(i);
                    Notasgrado nota = new Notasgrado();
                    nota.setCodigo(sec.generarClave());
                    List labels = object.getChildren();
                    nota.setMatricula(new Matriculas(new Integer(((Label) labels.get(0)).getValue())));
                    nota.setFecha(new Date());
                    inter.set("nota", nota);
                    for (int j = 2; j < labels.size(); j++) {
                        Decimalbox object1 = (Decimalbox) labels.get(j);
                        String formula = notas.get(j - 2).getFormula(); // EN CASO DE FORMULA
                        formula = formula.replace("no", "nota.getNo"); //EN CASO DE QUE HAYA FORMULA
                        String toda = notas.get(j - 2).getColumna() + "";
                        String uno = toda.substring(0, 1).toUpperCase();
                        toda = toda.substring(1, toda.length());
                        String vaNota = object1.getValue().toString();
                        Double aCargar = 0.0;
                        if (vaNota.equals("")) {
                            aCargar = 0.0;
                        } else {
                            aCargar = new Double(vaNota);
                        }
                        inter.eval("nota.set" + (uno + toda) + "(" + redondear(aCargar, 3) + ");");
                        if (!formula.isEmpty()) {
                            inter.eval("nota.set" + (uno + toda) + "(" + formula + ");");
                        }
                    }
                    nota = (Notasgrado) inter.get("nota");
                    adm.guardar(nota);

                } catch (EvalError ex) {
                    Logger.getLogger(notasGrado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            recalculoNotas(materia, curso);
            System.out.println("FINALIZO EN: " + new Date());
            return "ok";
        } catch (EvalError ex) {
            Logger.getLogger(notasGrado.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en:  " + ex;
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

    public Boolean verificar(String formula, List<Notanotas> notas) {

        formula = formula.replace("()", "");
        String redon = "public Double redondear(Double numero, int decimales) {"
                + ""
                + "try{"
                + "               "
                + " java.math.BigDecimal d = new java.math.BigDecimal(numero);"
                + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);"
                + "        return d.doubleValue();"
                + "        }catch(Exception e){"
                + "            return 0.0;"
                + "        }"
                + "     }";

        Interpreter inter = new Interpreter();
        try {
            inter.eval(redon);
            for (Iterator<Notanotas> it = notas.iterator(); it.hasNext();) {
                Notanotas notanotas = it.next();
                inter.eval("" + notanotas.getNota() + "=1;");
            }
            inter.eval(formula + "*1");
        } catch (EvalError ex) {
            Logger.getLogger(notasGrado.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
        return false;
    }
}
