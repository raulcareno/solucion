package bean;
import bsh.EvalError;
import bsh.Interpreter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Disciplina;
import jcinform.persistencia.MateriaProfesor;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Notanotas;
import jcinform.persistencia.Notas;
import jcinform.persistencia.ParametrosGlobales;
import jcinform.persistencia.Periodo;
import jcinform.persistencia.Sistemacalificacion;
import jcinform.procesos.Administrador;
import org.zkforge.yuiext.grid.Grid;
import org.zkforge.yuiext.grid.Label;
import org.zkforge.yuiext.grid.Row;
import org.zkforge.yuiext.grid.Rows;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;


public class disciplina extends Rows {
//ArrayList listad = new ArrayList();
public disciplina(){
  
}

 public void addRow(Cursos curso, MateriaProfesor materia){
     int tamanio=0;
//     if(listad==null){
             Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
          Administrador adm = new Administrador();
          List sistemas = adm.query("Select o from Sistemacalificacion as o " +
                  "where o.periodo.codigoper = '"+periodo.getCodigoper()+"' and o.esdisciplina = true");
          List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
                  "where o.sistema.esdisciplina = true " +
                  "and o.sistema.periodo.codigoper = '"+periodo.getCodigoper()+"' " +
                  "order by o.sistema.orden ");
          String query ="";
          for (Notanotas notass : notas) {
                query += notass.getNota()+",";
           }
          query = query.substring(0, query.length()-1).replace("'","").replace("(", "").replace(")", "");
//          String[] values = new String[sistemas.size()];

//          for (int i=0;i<sistemas.size();i++) {
//              values[i]= ((Sistemacalificacion)sistemas.get(i)).getAbreviatura();
//          }
    tamanio = sistemas.size();
    getChildren().clear();
    Label label =null;
    String vacio ="";
    String esDiscp = "true";
    if(materia.getMateria().getCodigo().equals(0)){
        esDiscp = "false";
    }
    String q = "Select matriculas.codigomat,concat(estudiantes.apellido,' ',estudiantes.nombre)," +
            " "+query+" from matriculas " +
            "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest " +
            "left join notas on matriculas.codigomat = notas.matricula " +
            "and notas.materia = '"+materia.getMateria().getCodigo()+"' and notas.disciplina = "+esDiscp+" " +
            "where matriculas.curso = '"+curso.getCodigocur()+"'  and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir' )" +
            "order by estudiantes.apellido";
    ParametrosGlobales para = (ParametrosGlobales) adm.buscarClave(new Integer(1),ParametrosGlobales.class);
        if(para.getCvalor().equals("P")){
            q = "Select matriculas.codigomat,(estudiantes.apellido,' ',estudiantes.nombre)," +
            " "+query+" from matriculas " +
            "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest " +
            "left join notas on matriculas.codigomat = notas.matricula " +
            "and notas.materia = '"+materia.getMateria().getCodigo()+"' and notas.disciplina = "+esDiscp+" " +
            "where matriculas.curso = '"+curso.getCodigocur()+"'  and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir' ) " +
            "order by estudiantes.apellido";
        }
    List nativo = adm.queryNativo(q);
    Row row = new Row();
     for (Iterator itna = nativo.iterator(); itna.hasNext();){
         Vector vec = (Vector) itna.next();
           row = new Row();
           int t = vec.size()-1;
                         for (int j = 0; j < vec.size(); j++) {
                             Object dos =  vec.get(j);
                             label = new Label();
                             try{ if(dos.equals(null)){dos = new Double(0.0); }
                             }catch(Exception e){ 
                                 dos = new Double(0.0);
//                                if(j==t)
//                                 dos = new String(" ");
                             }
//                             if(j==t)
//                                label.setValue(""+dos);
//                             else 
                             if(j>=2){//si no es no matricl ani nombre del estudiante
                                 Double valor = (Double) dos;
                                   if (valor.equals(0.0)) {
                                        label.setValue("");
                                    } else {
                                        label.setValue(""+redondear((Double) dos,2));
                                    }
                                //label.setValue(""+redondear((Double) dos,2));
                             }else {
                                 String valor = dos.toString().replace("(","").replace(")","").replace("\"","").replace(",","");
                                label.setValue("" + valor);
                                //label.setValue(""+dos);
                             }
                             label.setStyle("font-size:11px;font:arial");
                                row.appendChild(label);
                             
                         }

          row.setParent(this);
     }
    nativo = null;
 }

     public Boolean verificar(String formula, List<Notanotas> notas) {

        formula = formula.replace("()", "");
        if(formula.equals(""))
             return false;
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

      public String validar(List col, List<Notanotas> notas) {
        Administrador adm = new Administrador();
        for (int i = 0; i < col.size(); i++) {
            Row object = (Row) col.get(i);
            List labels = object.getChildren();
            Matriculas ma = (Matriculas) adm.buscarClave(new Integer(((Label) labels.get(0)).getValue()), Matriculas.class);
            //nota.setMatricula(new Matriculas(new Integer(((Label) vecDato.get(0)).getValue())));
            for (int j = 2; j < labels.size(); j++) {
                Label object1 = (Label) labels.get(j);
                String formula = notas.get(j - 2).getSistema().getFormuladisciplina(); // EN CASO DE FORMULA
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
                if((aCargar > notas.get(j - 2).getSistema().getConduca() || aCargar < 0) && formula.isEmpty()) {
                    return "NOTA FUERA DE RANGO EN PERIODO: " + notas.get(j - 2).getSistema().getAbreviatura() + "  NOTA: " + aCargar + " " +
                            " ESTUDIANTE: " + ma.getEstudiante().getApellido() + " " + ma.getEstudiante().getNombre() + "  ";
                }
            }
        }
        return "";
    }



 public String guardar(List col,Cursos curso,MateriaProfesor materia){
     System.out.println("INICIO EN: "+new Date());
     Interpreter inter = new Interpreter();

      String redon = "public Double redondear(Double numero, int decimales) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" + "        return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";

      
      try {
        inter.eval(redon);
     } catch (Exception e) {
         System.out.println(""+e);
         return "false";
     }
            
     Administrador adm = new Administrador();
     secuencial sec = new secuencial();
     Session ses = Sessions.getCurrent();
     Periodo periodo = (Periodo) ses.getAttribute("periodo");
     
     List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
               "where o.sistema.esdisciplina = true  " +
               "and o.sistema.periodo.codigoper = '"+periodo.getCodigoper()+"'order by o.sistema.orden ");

     
        List<Sistemacalificacion> sisFormulas = adm.query("Select o from Sistemacalificacion as o " +
                    "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.formula <> '' " +
                    " and o.esdisciplina = true " +
                    "  order by o.orden ");

     for (Iterator<Sistemacalificacion> it = sisFormulas.iterator(); it.hasNext();) {
                Sistemacalificacion siCal = it.next();
                if (verificar(siCal.getFormuladisciplina(), notas)) {
                    return "Revise la formula de ['" + siCal.getNombre() + "'] del Sistema de Calificacion ";
                }
            }

           String valida = validar(col, notas);
            if (!valida.isEmpty()) {
                return valida;
            }
String del = "Delete from Notas where matricula.curso.codigocur = '"+curso.getCodigocur()+"' " +
               "and materia.codigo = '"+materia.getMateria().getCodigo()+"'  and notas.disciplina = true ";
     if(materia.getMateria().getCodigo().equals(0)){
            del = "Delete from Notas where matricula.curso.codigocur = '"+curso.getCodigocur()+"' " +
                "and materia.codigo = '"+materia.getMateria().getCodigo()+"'  ";
     }
       adm.ejecutaSql(del);
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Notas nota = new Notas();
                nota.setCodigonot(sec.generarClave());
                List labels = object.getChildren();
                nota.setMatricula(new Matriculas(new Integer(((Label)labels.get(0)).getValue())));
                nota.setMateria(materia.getMateria());
                nota.setOrden(materia.getOrden());
                nota.setCuantitativa(materia.getCuantitativa());
                nota.setPromedia(materia.getMinisterio());
                nota.setSeimprime(materia.getSeimprime());
                nota.setFecha(new Date());
                nota.setDisciplina(true);
                if(nota.getMateria().getCodigo().equals(0)){
                        nota.setDisciplina(false);
                }

                inter.set("nota", nota);
                int ta = labels.size()-1;
                for (int j = 2; j < labels.size()-1; j++) {
                    Label object1 = (Label) labels.get(j);
                    String formula = notas.get(j-2).getSistema().getFormuladisciplina();// EN CASO DE FORMULA
                    formula = formula.replace("no","nota.getNo");//EN CASO DE QUE HAYA FORMULA
                    String toda = notas.get(j-2).getNota()+"";
                    String uno = toda.substring(0,1).toUpperCase();
                                        toda = toda.substring(1, toda.length());
                                        String vaNota = object1.getValue().toString();
                    Double aCargar = 0.0;
                        if (vaNota.equals("")) {
                            aCargar = 0.0;
                        } else {
                            aCargar = new Double(vaNota);
                        }
                    inter.eval("nota.set"+(uno+toda)+"("+redondear(aCargar,2)+");");
                        if(!formula.isEmpty()){
                                inter.eval("nota.set"+(uno+toda)+"("+formula+");");
                        }
                }
                nota = (Notas)inter.get("nota");
//              Label object1 = (Label) labels.get(ta);
//              nota.setObservacion(object1.getValue()+"");
                adm.guardar(nota);
                //System.out.println("FINALIZO EN: "+new Date());
                //return "ok";
            }catch (EvalError ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         return "ok";



 }
  public Double redondear(Double numero, int decimales) {
        try{
                BigDecimal d = new BigDecimal(numero);
        d = d.setScale(decimales, RoundingMode.HALF_UP);
        return d.doubleValue();
        }catch(Exception e){
            return 0.0;
        }
     }

  public void addFaltas(Cursos curso,Sistemacalificacion sistema){
     int tamanio=0;
//     if(listad==null){
     Session ses = Sessions.getCurrent();
     Periodo periodo = (Periodo) ses.getAttribute("periodo");
          Administrador adm = new Administrador();
          List sistemas = adm.query("Select o from Equivalencias as o " +
                  "where o.periodo.codigoper = '"+periodo.getCodigoper()+"' and o.grupo = 'DI'");
          String query ="";
          for (int a = 1; a< sistemas.size()+1; a++) {
                query += "nota"+a+",";
           }
          query = query.substring(0, query.length()-1).replace("'","").replace("(", "").replace(")", "");
    tamanio = sistemas.size();
    getChildren().clear();
    Label label =null;
    String vacio ="";
    String q = "Select matriculas.codigomat,concat(estudiantes.apellido,' ',estudiantes.nombre)," +
            " "+query+" from matriculas " +
            "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest " +
            "left join disciplina on matriculas.codigomat = disciplina.matricula and disciplina.sistema = '"+sistema.getCodigosis()+"'  " +
            "where matriculas.curso = '"+curso.getCodigocur()+"' " +
            "order by estudiantes.apellido";
    ParametrosGlobales para = (ParametrosGlobales) adm.buscarClave(new Integer(1),ParametrosGlobales.class);
        if(para.getCvalor().equals("P")){
             q = "Select matriculas.codigomat,(estudiantes.apellido,' ',estudiantes.nombre)," +
            " "+query+" from matriculas " +
            "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest " +
            "left join disciplina on matriculas.codigomat = disciplina.matricula and disciplina.sistema = '"+sistema.getCodigosis()+"'  " +
            "where matriculas.curso = '"+curso.getCodigocur()+"' " +
            "order by estudiantes.apellido";
        }

    List nativo = adm.queryNativo(q);
    Row row = new Row();
     for (Iterator itna = nativo.iterator(); itna.hasNext();){
         Vector vec = (Vector) itna.next();
           row = new Row();
           int t = vec.size()-1;
                         for (int j = 0; j < vec.size(); j++) {
                             Object dos =  vec.get(j);
                             label = new Label();
                             try{ if(dos.equals(null)){dos = new Integer(0); }
                             }catch(Exception e){
                                 dos = new Integer(0);
                             }

                                 if(j>=2){//si no es no matricl ani nombre del estudiante
                                    Integer valor = (Integer) dos;
                                    if(valor.equals(0)){
                                        label.setZIndex(j);
                                        label.setAttribute("ini", 0);
                                        label.setAttribute("fin", 20);
                                        label.setValue("");
                                    }else{
                                        label.setAttribute("ini", 0);
                                        label.setAttribute("fin", 20);
                                        label.setZIndex(j);
                                        label.setValue("" + ((Integer) dos));
                                    }
                                        //label.setValue(""+((Integer) dos));
                                 }else{
                                        String valor = dos.toString().replace("(","").replace(")","").replace("\"","").replace(",","");
                                        label.setValue("" + valor);
                                        //label.setValue(""+dos);
                                 }


                                row.appendChild(label);
                         }

          row.setParent(this);
     }
    nativo = null;



 }
public void guardar(List col,Cursos curso,Sistemacalificacion sistema){
     System.out.println("INICIO EN: "+new Date());
     Interpreter inter = new Interpreter();
     Administrador adm = new Administrador();
     secuencial sec = new secuencial();
     Session ses = Sessions.getCurrent();
     Periodo periodo = (Periodo) ses.getAttribute("periodo");
               List sistemas = adm.query("Select o from Equivalencias as o " +
                  "where o.periodo.codigoper = '"+periodo.getCodigoper()+"' and o.grupo = 'DI'");
          String query ="";
          for (int a = 1; a< sistemas.size()+1; a++) {
                query += "nota"+a+",";
           }
          query = query.substring(0, query.length()-1).replace("'","").replace("(", "").replace(")", "");
//       List<Notanotas> notas = adm.query("Select o from Notanotas as o  " +
//               "where o.sistema.faltas = true " +
//               " and o.sistema.periodo.codigoper = '"+periodo.getCodigoper()+"'  " +
//               "order by o.sistema.orden ");
       String del = "Delete from Disciplina where matricula.curso.codigocur = '"+curso.getCodigocur()+"' " +
               "   ";
       adm.ejecutaSql(del);
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Disciplina nota = new Disciplina();
                nota.setCodigodisc(sec.generarClave());
                List labels = object.getChildren();
                nota.setMatricula(new Matriculas(new Integer(((Label)labels.get(0)).getValue())));
                nota.setSistema(sistema);
                //nota.setMateria(materia.getMateria());
                //nota.setOrden(materia.getOrden());
//                nota.setCuantitativa(materia.getCuantitativa());
//                nota.setPromedia(materia.getMinisterio());
//                nota.setSeimprime(materia.getSeimprime());
                nota.setFecha(new Date());
//                nota.setDisciplina(true);
                inter.set("nota", nota);
                int ta = labels.size()-1;
                int m=1;
                for (int j = 2; j < labels.size(); j++) {

                    Label object1 = (Label) labels.get(j);
//                  String formula = notas.get(j-2).getSistema().getFormulafaltas();// EN CASO DE FORMULA
//                  formula = formula.replace("no","nota.getNo");//EN CASO DE QUE HAYA FORMULA
                    String vaNota = object1.getValue().toString();
                    Integer aCargar = 0;
                        if (vaNota.equals("")) {
                            aCargar = 0;
                        } else {
                            aCargar = new Integer(vaNota);
                        }
                    
                    inter.eval("nota.setNota"+(m)+"("+aCargar+");");
                    m++;
//                        if(!formula.isEmpty()){
//                                System.out.println(""+formula);
//                                inter.eval("nota.set"+(uno+toda)+"("+formula+");");
//                        }
                }
                nota = (Disciplina)inter.get("nota");
//                    Label object1 = (Label) labels.get(ta);
//                nota.setObservacion(object1.getValue()+"");
                
                adm.guardar(nota);
            } catch (EvalError ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
System.out.println("FINALIZO EN: "+new Date());


 }
 

}
