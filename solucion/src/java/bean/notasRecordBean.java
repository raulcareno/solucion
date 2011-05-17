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
 
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;

public class notasRecordBean extends Rows {
//ArrayList listad = new ArrayList();
    public notasRecordBean() {
        Cursos todos = new Cursos(-2);
//        todos.setDescripcion("[TODOS]");
//        List accesos = new ArrayList();
//        for (Iterator it = accesos.iterator(); it.hasNext();) {
//            Object object = it.next();
//        }
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
           Logger.getLogger(notasRecordBean.class.getName()).log(Level.SEVERE, null, ex);
          return true;
      }
        return false;
}
    public void addRow(Cursos curso) {
        
       // int tamanio = 0;
         //Session ses = Sessions.getCurrent();
     //Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();
        String query = "";
        query = "primero,segundo,tercero,cuarto,quinto,sexto,((primero+segundo+tercero+cuarto+quinto+sexto)/5),primerod,segundod,tercerd,cuartod,quintod,sextod,((primerod+segundod+tercerd+cuartod+quintod+sextod)/5)";
        getChildren().clear();
             Decimalbox label = null;
        Label label3 = null;

        String q = "Select estudiantes.codigoest,concat(estudiantes.apellido,' ',estudiantes.nombre), "+query+" from matriculas  left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  left join notasrecord on estudiantes.codigoest = notasrecord.estudiante where matriculas.curso = '"+curso.getCodigocur()+"'  order by estudiantes.apellido ";
        ParametrosGlobales para = (ParametrosGlobales) adm.buscarClave(new Integer(1),ParametrosGlobales.class);
        if(para.getCvalor().equals("P")){
           q = "Select estudiantes.codigoest,(estudiantes.apellido,' ',estudiantes.nombre), "+query+" from matriculas  left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  left join notasrecord on estudiantes.codigoest = notasrecord.estudiante where matriculas.curso = '"+curso.getCodigocur()+"'  order by estudiantes.apellido ";
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
                        label.setValue(new BigDecimal(0));
                    } else {
                        label.setValue(new BigDecimal(redondear((Double) dos, 3)));
                    }
                    
                } else {
                       String valor = dos.toString().replace("(", "").replace(")", "").replace("\"", "").replace(",", "");
                    label3.setValue("" + valor);
                    //label.setValue("" + dos);
                }

                   if(j==0){
                    label3.setStyle("width:15px;font-size:11px;font:arial; ");
//                    label3.setReadonly(true);
                    row.appendChild(label3);
                }else if(j==1){
                    label3.setStyle("width:300px;font-size:11px;font:arial; ");
//                    label3.setReadonly(true);
                    row.appendChild(label3);
                }else{


                                label.setDisabled(false);
                                label.setStyle("width:40px;font:arial;font-size:11px;text-align:right;");
                    
                     row.appendChild(label);
                }

//                                 label.setAttribute(q, dos);
//                row.appendChild(label);
//                                 System.out.print(","+dos);
            }

            row.setParent(this);
        }
        nativo = null;



    }

    @SuppressWarnings("static-access")
    public String guardar(List col, Cursos curso) {
//         Session ses = Sessions.getCurrent();
        try {
            String redon = "public Double redondear(Double numero, int decimales) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" + "        return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
            System.out.println("INICIO EN: " + new Date());
            Interpreter inter = new Interpreter();
            inter.eval(redon);
            Administrador adm = new Administrador();
            secuencial sec = new secuencial();

            for (int i = 0; i < col.size(); i++) {
                try {
                    Row object = (Row) col.get(i);
                    Notasrecord nota = new Notasrecord();
                    List labels = object.getChildren();
                    nota.setEstudiante(new Estudiantes(new Integer(((Label) labels.get(0)).getValue())));
                        Decimalbox object1 = (Decimalbox) labels.get(2);
                        String vaNota = object1.getValue().toString();
                        Double aCargar = 0.0;
                        if (vaNota.equals(""))
                            aCargar = 0.0;
                        else
                            aCargar = new Double(vaNota);
                          nota.setPrimero(aCargar);

                            //2
                        object1 = (Decimalbox) labels.get(3);
                         vaNota = object1.getValue().toString();
                         aCargar = 0.0;
                        if (vaNota.equals(""))
                            aCargar = 0.0;
                        else
                            aCargar = new Double(vaNota);
                          nota.setSegundo(aCargar);

                         //3
                        object1 = (Decimalbox) labels.get(4);
                         vaNota = object1.getValue().toString();
                         aCargar = 0.0;
                        if (vaNota.equals(""))
                            aCargar = 0.0;
                        else
                            aCargar = new Double(vaNota);
                          nota.setTercero(aCargar);
                         //4
                        object1 = (Decimalbox) labels.get(5);
                         vaNota = object1.getValue().toString();
                         aCargar = 0.0;
                        if (vaNota.equals(""))
                            aCargar = 0.0;
                        else
                            aCargar = new Double(vaNota);
                          nota.setCuarto(aCargar);
                        //5
                        object1 = (Decimalbox) labels.get(6);
                         vaNota = object1.getValue().toString();
                         aCargar = 0.0;
                        if (vaNota.equals(""))
                            aCargar = 0.0;
                        else
                            aCargar = new Double(vaNota);
                          nota.setQuinto(aCargar);
                         //6
                        object1 = (Decimalbox) labels.get(7);
                         vaNota = object1.getValue().toString();
                         aCargar = 0.0;
                        if (vaNota.equals(""))
                            aCargar = 0.0;
                        else
                            aCargar = new Double(vaNota);
                          nota.setSexto(aCargar);

                        //7DISCIPLIINA
                        object1 = (Decimalbox) labels.get(9);
                         vaNota = object1.getValue().toString();
                         aCargar = 0.0;
                        if (vaNota.equals(""))
                            aCargar = 0.0;
                        else
                            aCargar = new Double(vaNota);

                          nota.setPrimerod(aCargar);
                         //8
                        object1 = (Decimalbox) labels.get(10);
                         vaNota = object1.getValue().toString();
                         aCargar = 0.0;
                        if (vaNota.equals(""))
                            aCargar = 0.0;
                        else
                            aCargar = new Double(vaNota);
                          nota.setSegundod(aCargar);
                         //9
                        object1 = (Decimalbox) labels.get(11);
                         vaNota = object1.getValue().toString();
                         aCargar = 0.0;
                        if (vaNota.equals(""))
                            aCargar = 0.0;
                        else
                            aCargar = new Double(vaNota);
                          nota.setTercerd(aCargar);

                          //10
                        object1 = (Decimalbox) labels.get(12);
                         vaNota = object1.getValue().toString();
                         aCargar = 0.0;
                        if (vaNota.equals(""))
                            aCargar = 0.0;
                        else
                            aCargar = new Double(vaNota);
 
                          nota.setCuartod(aCargar);
                        //11
                        object1 = (Decimalbox) labels.get(13);
                         vaNota = object1.getValue().toString();
                         aCargar = 0.0;
                        if (vaNota.equals(""))
                            aCargar = 0.0;
                        else
                            aCargar = new Double(vaNota);
 
                          nota.setQuintod(aCargar);
                         //12
                        object1 = (Decimalbox) labels.get(14);
                         vaNota = object1.getValue().toString();
                         aCargar = 0.0;
                        if (vaNota.equals(""))
                            aCargar = 0.0;
                        else
                            aCargar = new Double(vaNota);
                          nota.setSextod(aCargar);

            String del = "Delete from Notasrecord " +
                    "where estudiante.codigoest = '"+nota.getEstudiante().getCodigoest()+"' ";
            adm.ejecutaSql(del);
            nota.getEstudiante();
                    adm.guardar(nota);
                    
                } catch (Exception ex) {
                    Logger.getLogger(notasRecordBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            System.out.println("FINALIZO EN: " + new Date());
            return "ok";
        } catch (EvalError ex) {
            Logger.getLogger(notasRecordBean.class.getName()).log(Level.SEVERE, null, ex);
             return "Error en:  "+ex;
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
    
    
}