package bean;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import net.sf.jasperreports.engine.JRDataSource;
//import org.zkforge.yuiext.grid.Label;
//import org.zkforge.yuiext.grid.Row;
//import org.zkforge.yuiext.grid.Rows;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
 
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import sources.Nota;
import sources.ReporteNoasLibretaDataSource;

public class notasConsultas extends Rows {
 
 
public void addRow(Matriculas matriculas1) {
        int tamanio = 0;
         Session ses = Sessions.getCurrent();
     Periodo periodo=  null;
     try{
     periodo= matriculas1.getCurso().getPeriodo();
     }catch(Exception e){
         getChildren().clear();
     return;
     }
//     if(listad==null){
        Administrador adm = new Administrador();
        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
                "where o.periodo.codigoper = '"+periodo.getCodigoper()+"' ");
        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o " +
                "where o.grupo = 'AP' and o.periodo.codigoper = '"+periodo.getCodigoper()+"' ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o where o.sistema.periodo.codigoper = '"+periodo.getCodigoper()+"' order by o.sistema.orden ");
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
 
        String q = "Select global.descripcion, notas.cuantitativa,  " + query + "  from global, matriculas " +
                    "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "where matriculas.curso = '" + matriculas1.getCurso().getCodigocur() + "' " +
                    "and global.codigo = notas.materia  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    "and notas.seimprime = true " +
                    "and notas.disciplina = false " +
                    "order by estudiantes.apellido, notas.orden";
               System.out.println("NOTAS GENERALES"+q);
            //List nativo = adm.queryNativo(q);
//     System.out.println(""+q);
        List nativo = adm.queryNativo(q);
//        System.out.println(""+nativo);
        Row row = new Row();
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            row = new Row();
            Boolean cuantitativa = true;
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                
                label = new Label();
                try {
                    if (dos.equals(null)) {
                        dos = new Double(0.0);
                    }
                } catch (Exception e) {
                    dos = new Double(0.0);
                }
                if (j >= 2) {
                    Double valor = (Double) dos;
                    if(valor.equals(0.0)){
                        label.setValue("___");
                    }else{
                        if(cuantitativa){
                            label.setValue("" + redondear((Double) dos, 2));
                        }else{
//                            System.out.println(""+equivalencia(redondear((Double) dos, 2),equivalencias));
                            label.setValue("" + equivalencia(redondear((Double) dos, 2),equivalencias));
                        }
                        
                    }
                    
                }else if (j >= 1) {
                    Boolean valor = (Boolean) dos;
                    cuantitativa = valor;
                }  else {
                    label.setValue("" + dos);
                }
//                                 label.setAttribute(q, dos);
              if(j!=1)
                row.appendChild(label);
                             
            }

            row.setParent(this);
        }
        nativo = null;



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
  
    public static JRDataSource libretas(Cursos curso, Matriculas matri, Sistemacalificacion sistema) {
//     int tamanio=0; -2
        Administrador adm = new Administrador();
         Session ses = Sessions.getCurrent();
     Periodo periodo = (Periodo) ses.getAttribute("periodo");
      
     List<Equivalencias> equivalenciasFaltas = adm.query("Select o from Equivalencias as o " +
                "where o.grupo = 'DI' and o.periodo.codigoper = '"+periodo.getCodigoper()+"' ");

        List<Equivalencias> equivalencias = adm.query("Select o from Equivalencias as o " +
                "where o.grupo = 'AP' and o.periodo.codigoper = '"+periodo.getCodigoper()+"' ");
        List sistemas = adm.query("Select o from Sistemacalificacion as o " +
                "where o.periodo.codigoper = '"+periodo.getCodigoper()+"' and o.orden <= '"+sistema.getOrden()+"' order by o.orden ");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o " +
                " where o.sistema.periodo.codigoper = '"+periodo.getCodigoper()+"'  and o.sistema.orden <=  '"+sistema.getOrden()+"'  order by o.sistema.orden ");
        String query = "";
        String query2 = "";
//        notas.get(0).getSistema().getOrden()
        String numeroDecimales = "3";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        //round(avg(nota1),3),
        for (Notanotas notass : notas) {
            query2 += "round(avg("+notass.getNota() + "),"+numeroDecimales+"),";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        query2 = query2.substring(0, query2.length() - 1).replace("'", "");
        String[] values = new String[sistemas.size()];

        for (int i = 0; i < sistemas.size(); i++) {
            values[i] = ((Sistemacalificacion) sistemas.get(i)).getAbreviatura();
        }


        List<Nota> lisNotas = new ArrayList();
        ArrayList lisNotasC = new ArrayList();
        List<Matriculas> matriculas =   new ArrayList();
            if(matri.getCodigomat().equals(-2)){
                   matriculas = adm.query("Select o from Matriculas as o where o.curso.codigocur = '"+curso.getCodigocur()+"'");
            }else{
                matriculas.add(matri);
            }
        for (Matriculas matriculas1 : matriculas) {
            //Matriculas matriculas1 = itm.next();
            String q = "Select matriculas.codigomat,notas.materia,notas.cuantitativa, " + query + "  from matriculas " +
                    "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "where matriculas.curso = '" + curso.getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    "and notas.seimprime = true " +
                    "and notas.disciplina = false " +
                    "order by estudiantes.apellido, notas.orden";
//                 System.out.println("NOTAS GENERALES"+q);
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
                        if(cuantitativa==false){
                              coll.setNota(equivalencia(dos, equivalencias));
                        }
                        coll.setMateria(mate.getDescripcion());
                        coll.setMatricula("" + matriculaNo.getCodigomat());
                        coll.setEstudiante(matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre());
                        coll.setSistema(((Sistemacalificacion) sistemas.get(ksis)).getAbreviatura());
                        coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion());
                        lisNotasC.add(coll);
                          
                        ksis++;
                    }else if (j >= 2) {
                        //System.out.println(""+dos);
                        cuantitativa = (Boolean)dos;
                    }else if (j >= 1) {
                        mate = (Global) adm.buscarClave((Integer) dos, Global.class);
                    } else {
                        matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                    }
                }
            //row.setParent(this);
            }

//IMPRIMO EL PROMEDIO 
            q = "Select matriculas.codigomat,notas.materia,notas.cuantitativa, " + query2 + "  from matriculas " +
                    "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "where matriculas.curso = '" + curso.getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    "and notas.seimprime = true " +
                    "and notas.promedia = true " +
                    "and notas.disciplina = false " +
                    " and notas.cuantitativa = true " +
                    "group by codigomat  " +
                    "order by estudiantes.apellido, notas.orden";
                //System.out.println("NOTAS DE DISCIPLINA "+q);
nativo =null;
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
//                                        System.out.println(""+e);
                                    }
                                    if (j >= 3) {
                                        val = (Double) dos;
                                        coll.setNota(dos);
                                        if(cuantitativa==false){
                                              coll.setNota(equivalencia(dos, equivalencias));
                                        }
                                        coll.setMateria(mate.getDescripcion());
                                        coll.setMatricula("" + matriculaNo.getCodigomat());
                                        coll.setEstudiante(matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre());
                                        coll.setSistema(((Sistemacalificacion) sistemas.get(ksis)).getAbreviatura());
                                        coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion());
                                        lisNotasC.add(coll);
                                        ksis++;
                                    }else if (j >= 2) {
//                                        //System.out.println(""+dos);
                                        cuantitativa = true;
                                    }else if (j >= 1) {
                                        mate = new Global(-1);
                                        mate.setDescripcion("PROMEDIO");
                                    } else {
                                        matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                                    }
                                    
                                }
                            //row.setParent(this);
                            }


            //IMPRIMO DISCIPLINA
q = "Select matriculas.codigomat,notas.materia,notas.cuantitativa, " + query + "  from matriculas " +
                    "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "where matriculas.curso = '" + curso.getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    " and notas.materia = -1 " +
                    " group by codigomat  " +
                    "order by estudiantes.apellido, notas.orden";
//                 System.out.println(""+q);
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
//                                        System.out.println(""+e);
                                    }
                                    if (j >= 3) {
                                        val = (Double) dos;
                                        coll.setNota(dos);
                                        if(cuantitativa==false){
                                              coll.setNota(equivalencia(dos, equivalencias));
                                        }
                                        coll.setMateria(mate.getDescripcion());
                                        coll.setMatricula("" + matriculaNo.getCodigomat());
                                        coll.setEstudiante(matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre());
                                        coll.setSistema(((Sistemacalificacion) sistemas.get(ksis)).getAbreviatura());
                                        coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion());
                                        lisNotasC.add(coll);
                                        ksis++;
                                    }else if (j >= 2) {
                                        //System.out.println(""+dos);
                                        cuantitativa = true;
                                    }else if (j >= 1) {
                                        mate = new Global(-2);
                                        mate.setDescripcion("DISCIPLINA");
                                    } else {
                                        matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                                    }
                                }
                            //row.setParent(this);
                            }

//IMPRIMO MATERIAS NO INCLUIDAS EN EL MINISTERIO

            q = "Select matriculas.codigomat,notas.materia,notas.cuantitativa, " + query + "  from matriculas " +
                    "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest  " +
                    "left join notas on matriculas.codigomat = notas.matricula " +
                    "where matriculas.curso = '" + curso.getCodigocur() + "'  " +
                    "and matriculas.codigomat = '" + matriculas1.getCodigomat() + "' " +
                    "and notas.seimprime = true " +
                    "and notas.disciplina = false " +
                    "and notas.promedia = false " +
                    "and notas.cuantitativa = true " +
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
                        System.out.println(""+e);
                    }
                    if (j >= 3) {
                        val = (Double) dos;
                        coll.setNota(dos);
                        if(cuantitativa==false){
                              coll.setNota(equivalencia(dos, equivalencias));
                        }
                        coll.setMateria(mate.getDescripcion());
                        coll.setMatricula("" + matriculaNo.getCodigomat());
                        coll.setEstudiante(matriculaNo.getEstudiante().getApellido() + " " + matriculaNo.getEstudiante().getNombre());
                        coll.setSistema(((Sistemacalificacion) sistemas.get(ksis)).getAbreviatura());
                        coll.setTipo(((Sistemacalificacion) sistemas.get(ksis)).getTrimestre().getDescripcion());
                        lisNotasC.add(coll);
                        ksis++;
                    }else if (j >= 2) {
                        //System.out.println(""+dos);
                        cuantitativa = (Boolean)dos;
                    }else if (j >= 1) {
                        mate = (Global) adm.buscarClave((Integer) dos, Global.class);
                    } else {
                        matriculaNo = (Matriculas) adm.buscarClave((Integer) dos, Matriculas.class);
                    }
                }
            //row.setParent(this);
            }

        //IMPRIMO EL CUADRO DE EQUIVALENCIAS

        ArrayList lisFaltas = new ArrayList();
            String query3 ="";
            int w =1;
            for (int i = 0; i < equivalenciasFaltas.size(); i++) {
                        query3 += "sum(nota"+w+"),";
                        w++;

            }
            query3 = query3.substring(0, query3.length() - 1);
        //IMPRIMO LAS FALTAS
            q = "Select " + query3 + "  from disciplina " +
                    "where matricula = '" + matriculas1.getCodigomat() + "'  " +
                    "and sistema = '" + sistema.getCodigosis() + "' " +
                    " group by matricula ";
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
                if(nativo.size()<=0){

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

 
            nota.setMatricula(matriculas1);
            nota.setNotas(lisNotasC);
            nota.setFaltas(lisFaltas);
            lisNotas.add(nota);
            

            lisNotasC = new ArrayList();
        }

 
        ReporteNoasLibretaDataSource  ds = new ReporteNoasLibretaDataSource(lisNotas);

        return ds;

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
