package bean;

import bsh.EvalError;
import bsh.Interpreter;

import java.math.BigDecimal;
import java.math.BigInteger;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public class notasActa extends Rows {
//ArrayList listad = new ArrayList();
    public notasActa() {
//        Cursos todos = new Cursos(-2);
//        todos.setDescripcion("[TODOS]");
    }

    public void addRow(Cursos curso) {
        Administrador adm = new Administrador();
          Session ses = Sessions.getCurrent();
     Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Actagrado> notas = adm.query("Select o from Actagrado as o where o.periodo.codigoper = '"+periodo.getCodigoper()+"' order by o.codigo ");
        List<Materiasgrado> mgrado = adm.query("select o from Materiasgrado as o where o.espromedio = true and o.curso.codigocur = '"+curso.getCodigocur()+"' ");
String columnaExamen ="";
        if(mgrado.size()>0){
            columnaExamen = mgrado.get(0).getColumna();
        }else{

        }
        List<Materiasgrado> mtrabajo = adm.query("select o from Materiasgrado as o "
                + " where o.estrabajo = true "
                + " and o.curso.codigocur = '"+curso.getCodigocur()+"' ");
        String columnaTrabajo ="";
        if(mtrabajo.size()>0){
            columnaTrabajo = mtrabajo.get(0).getColumna();
        }else{

        }
        String query = "";
        for (Actagrado notass : notas) {
            query += notass.getColumna() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        getChildren().clear();
     Decimalbox txtNota = null;
     Intbox txtNumero  = null;
     String estilonotas = "width:55px;text-align:center";
     Datebox txtFecha = null;
        Label label3 = null;
        String q = "Select matriculas.codigomat,concat(estudiantes.apellido,' ',estudiantes.nombre), " + query + ", numeroacta, fecha  from matriculas " +
                "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest " +
                "left join notasacta on matriculas.codigomat = notasacta.matricula " +
                 "where matriculas.curso = '" + curso.getCodigocur() + "' and matriculas.estado in ('Matriculado','Recibir Pase') " +
                "order by estudiantes.apellido";
        ParametrosGlobales para = (ParametrosGlobales) adm.buscarClave(new Integer(1),ParametrosGlobales.class);
        if(para.getCvalor().equals("P")){
             q = "Select matriculas.codigomat,(estudiantes.apellido,' ',estudiantes.nombre), " + query + "  from matriculas " +
                "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest " +
                "left join notasacta on matriculas.codigomat = notasacta.matricula " +
                 "where matriculas.curso = '" + curso.getCodigocur() + "' " +
                "order by estudiantes.apellido";
        }
        
        List nativo = adm.queryNativo(q);
        Row row = new Row();
        System.out.println(" ....... ");
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            row = new Row();
            Matriculas matricula = null;
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                txtNota = new Decimalbox();
                txtNumero = new Intbox();
                label3 = new Label();
                try {
                    if (dos.equals(null)) {
                        dos = new Double(0.0);
                    }
                } catch (Exception e) {
                    dos = new Double(0.0);
                }
                
                 if(j == (vec.size()-2)) {
                     System.out.println("NUMERO ACTA:"+dos);
                     try{
                         if( dos!= null){
                        txtNumero.setValue((Integer)dos);
                     }else{
                         txtNumero.setValue(null);
                     }
                     }catch(Exception ex){
                     txtNumero.setValue(null);
                     }
                     txtNumero.setReadonly(false);
                     txtNumero.setStyle("float:right; text-align:right;");
                     txtNumero.setCols(1);
                     
                    //label3.setValue("" + dos);
                    
                }else if(j == (vec.size()-1)) {
                     System.out.println("FECHA ACTA:"+dos);
                     txtFecha = new Datebox();
                     try{
                   if( dos!= null){
                        txtFecha.setValue((Date)dos);
                     }else{
                         txtFecha.setValue(null);
                     }
                     }catch(Exception ex){
                         txtFecha.setValue(null);
                     }
//                     txtFecha.setReadonly(true);
                     txtFecha.setStyle("float:right; text-align:right;");
                     txtFecha.setCols(7);
                     
                    //label3.setValue("" + dos);
                    
                }else if (j >= 2) {
                    Double valor = (Double) dos;
                    if (valor.equals(0.0)) {
                        txtNota.setValue(new BigDecimal(0));
                    } else {
                        txtNota.setValue(new BigDecimal(redondear((Double) dos, 3)));
                    }
                    String formula = notas.get(j - 2).getFormula(); // EN CASO DE FORMULA
                    if(formula.contains("primero") || formula.contains("segundo") || formula.contains("tercero")
                            || formula.contains("cuarto") || formula.contains("sexto")){
                         
                        List rec =  adm.queryNativo("Select ("+formula+") from Notasrecord " +
                                 "where estudiante = '"+matricula.getEstudiante().getCodigoest()+"' ");
                              if(rec==null || rec.size()<=0){
                                  txtNota.setValue(new BigDecimal(0));
                              }else{
                                 txtNota.setValue(new BigDecimal(redondear((Double) ((Vector)rec.get(0)).get(0) ,3)));

                              }
                        
                    }else if(formula.contains("examenes")){
                        try{
                         List rec =  adm.queryNativo("Select ("+columnaExamen+") from Notasgrado " +
                                 "where matricula = '"+matricula.getCodigomat()+"' ");
                              if(rec==null){
                                  txtNota.setValue(new BigDecimal(0));
                              }else{
                                 txtNota.setValue(new BigDecimal(redondear((Double) ((Vector)rec.get(0)).get(0) , 3)));

                              }
                        }catch(Exception e){
                            System.out.println(""+e);
                        }

                    }else if(formula.contains("trabajo")){
                        try{
                         List rec =  adm.queryNativo("Select ("+columnaTrabajo+") from Notasgrado " +
                                 "where matricula = '"+matricula.getCodigomat()+"' ");
                              if(rec==null){
                                  txtNota.setValue(new BigDecimal(0));
                              }else{
                                 txtNota.setValue(new BigDecimal(redondear((Double) ((Vector)rec.get(0)).get(0) , 3)));

                              }
                        }catch(Exception e){
                            System.out.println(""+e);
                        }

                    }
                    
                    
                } else if(j==0) {
                    matricula = (Matriculas) adm.buscarClave(new Integer(dos.toString()),Matriculas.class);
                    label3.setValue("" + dos);
                    
                } else {
                    String valor = dos.toString().replace("(","").replace(")","").replace("\"","").replace(",","");
                    label3.setValue("" + valor);
 
                    //label.setValue("" + dos);
                }
                  if(j==0){
                    label3.setStyle("width:15px;font-size:11px;font:arial; ");
                    row.appendChild(label3);
                }else if(j==1){
                    label3.setStyle("width:300px;font-size:11px;font:arial; ");
                    row.appendChild(label3);
                }else if(j == (vec.size()-1)) {
                    row.appendChild(txtFecha);
                }else if(j == (vec.size()-2)) {
                    txtNumero.setStyle("float:right;text-align:right");
                    row.appendChild(txtNumero);
                }else{
                    txtNota.setStyle(estilonotas);
                     row.appendChild(txtNota);
                }

                //row.appendChild(label);
//                                 System.out.print(","+dos);
            }

//                                 label.setAttribute(q, dos);
                //row.appendChild(label);
//                                 System.out.print(","+dos);
//            }

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
            String truncar = "public Double truncar(Double numero, int decimales) {         try {             java.math.BigDecimal d = new java.math.BigDecimal(numero);             d = d.setScale(decimales, java.math.BigDecimal.ROUND_DOWN);             return d.doubleValue();         } catch (Exception e) {             return 0.0;         }     }";
 
//            System.out.println("INICIO EN: " + new Date());
            Interpreter inter = new Interpreter();
            inter.eval(redon);
            inter.eval(truncar);
            Administrador adm = new Administrador();
//            List<Materiasgrado> notas = adm.query("Select o from Materiasgrado as o " +
//                    "where o.curso.codigocur = '"+curso.getCodigocur() +"' order by o.codigo ");
           List<Actagrado> notas = adm.query("Select o from Actagrado as o where o.periodo.codigoper = '"+periodo.getCodigoper()+"' order by o.codigo ");
//            List<Sistemacalificacion> sisFormulas = adm.query("Select o from Sistemacalificacion as o " +

//            for (Iterator<Sistemacalificacion> it = sisFormulas.iterator(); it.hasNext();) {
//                Sistemacalificacion siCal = it.next();
//                        if(verificar(siCal.getFormula(), notas)){
//                              return "Revise la formula de ['"+siCal.getNombre()+"'] del Sistema de Calificacion " ;
//                        }
//            }
            secuencial sec = new secuencial();
            String del = "Delete from Notasacta where matricula.curso.codigocur = '" + curso.getCodigocur() + "' ";
            adm.ejecutaSql(del);

            for (int i = 0; i < col.size(); i++) {
                try {
                    Row object = (Row) col.get(i);
                    Notasacta nota = new Notasacta();
                    nota.setCodigo(sec.generarClave());
                    List labels = object.getChildren();
                    nota.setMatricula(new Matriculas(new Integer(((Label) labels.get(0)).getValue())));
//                    nota.setFecha(new Date());
                    nota.setNumeroacta((((Intbox) labels.get(labels.size()-2)).getValue()));
                    nota.setFecha((((Datebox) labels.get(labels.size()-1)).getValue()));
                    inter.set("nota", nota);
                     inter.eval(" public Double equivalencia(Double n){" +
                                "return n;" +
                                "}");
                   
                    for (int j = 2; j < labels.size()-2; j++) {
                        Decimalbox object1 = (Decimalbox) labels.get(j);
                        String formula = notas.get(j - 2).getFormula(); // EN CASO DE FORMULA
                        String formula0 = notas.get(j - 2).getFormula(); // EN CASO DE FORMULA
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
                        if (!formula.isEmpty() && !formula0.contains("primero")
                            && !formula0.contains("segundo")
                            && !formula0.contains("tercero")
                            && !formula0.contains("cuarto")
                            && !formula0.contains("sexto")  && !formula0.contains("examenes")   && !formula0.contains("trabajo")   ){
                            
                            inter.eval("nota.set" + (uno + toda) + "(" + formula + ");");
                        }
                    }
                    nota = (Notasacta) inter.get("nota");
                    adm.guardar(nota);
                } catch (EvalError ex) {
                    Logger.getLogger(notasActa.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            recalculoNotas(materia, curso);
            System.out.println("FINALIZO EN: " + new Date());
            return "ok";
        } catch (EvalError ex) {
            Logger.getLogger(notasActa.class.getName()).log(Level.SEVERE, null, ex);
             return "Error en:  "+ex;
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

            BigDecimal d = new BigDecimal(numero);
            d = d.setScale(decimales, RoundingMode.HALF_UP);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }
    
    public static Object equivalencia(Object no,List<Equivalencias> equivalencias){
        Double nota = (Double) no;
            ArrayList listado  = new ArrayList();     
        for (Equivalencias acaEquivalencias : equivalencias) {
                Object obj[] = new Object[3];
                obj[0] = acaEquivalencias.getValorminimo();
                obj[1] = acaEquivalencias.getValormaximo();
                obj[2] = acaEquivalencias.getAbreviatura();
                listado.add(obj);
          }

            for (Iterator it = listado.iterator(); it.hasNext();) {
                Object object[] = (Object[]) it.next();
                Double mini  = (Double) object[0];
                Double maxi  = (Double) object[1];
                if(nota>= mini && nota<= maxi){
                    //System.out.println(""+);
                        return object[2];
                }
            }    
        
        return "";
    }
    

public Boolean verificar(String formula,List<Notanotas> notas){

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
                    inter.eval(""+notanotas.getNota()+"=1;");
        }
              inter.eval(formula+"*1");
      } catch (EvalError ex) {
           Logger.getLogger(notasActa.class.getName()).log(Level.SEVERE, null, ex);
          return true;
      }
        return false;
}

}
