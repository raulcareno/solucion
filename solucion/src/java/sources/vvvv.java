/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sources;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import net.sf.jasperreports.engine.JRDataSource;

/**
 *
 * @author geovanny
 */
public class vvvv {

    /**
     * @param args the command line arguments
     */
    public static JRDataSource notasd(Cursos curso, Global materia){
//     int tamanio=0;
           Administrador adm = new Administrador();
          List sistemas = adm.query("Select o from Sistemacalificacion as o " +
                  "where o.periodo.codigoper = 1 order by o.orden ");
          List<Notanotas> notas = adm.query("Select o from Notanotas as o order by o.sistema.orden ");
          String query ="";
          for (Notanotas notass : notas) {
                query += notass.getNota()+",";
           }
          query = query.substring(0, query.length()-1).replace("'","").replace("(", "").replace(")", "");
          String[] values = new String[sistemas.size()];

          for (int i=0;i<sistemas.size();i++) {
              values[i]= ((Sistemacalificacion)sistemas.get(i)).getAbreviatura();
          }
//    tamanio = sistemas.size();
    //List<Matriculas> matriculas = adm.query("Select o from Matriculas as o ");
    String q = "Select matriculas.codigomat, "+query+"  from matriculas " +
            "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest " +
            "left join notas on matriculas.codigomat = notas.matricula " +
            "and notas.materia = '"+materia.getCodigo()+"' " +
            "where matriculas.curso = '"+curso.getCodigocur()+"' " +
            "order by estudiantes.apellido";
//     System.out.println(""+q);
    List nativo = adm.queryNativo(q);
    ArrayList lisNotas = new ArrayList();
     for (Iterator itna = nativo.iterator(); itna.hasNext();){
         Vector vec = (Vector) itna.next();
           //row = new Row();
         Matriculas matriculaNo = null;
         int ksis=0;
                         for (int j = 0; j < vec.size(); j++) {
                             Object dos =  vec.get(j);
                             Double val = 0.0;
                             Nota nota = new Nota();
                             try{
                              if(dos.equals(null)){dos = new Double(0.0); }
                             }catch(Exception e){dos = new Double(0.0); }
                             if(j>=1){
                                  val = redondear((Double)dos,2);
                                  nota.setMatricula(matriculaNo);
                                  nota.setNota(val);
                                  nota.setSistema((Sistemacalificacion) sistemas.get(ksis));
                                  lisNotas.add(nota);
                                  ksis++;
                             }else{
                                 matriculaNo = (Matriculas) adm.buscarClave((Integer)dos, Matriculas.class);
                             }
                             

                         }
                  
          //row.setParent(this);
     }
    nativo = null;
    ReporteNotasDataSource ds = new ReporteNotasDataSource(lisNotas);
    return ds;
    
 }

      public static Double redondear(Double numero, int decimales) {
        try{
                BigDecimal d = new BigDecimal(numero);
        d = d.setScale(decimales, RoundingMode.HALF_UP);
        return d.doubleValue();
        }catch(Exception e){
            return 0.0;
        }
     }
    public static void main(String[] args) {
                    String user ="";
    String caracter="1234567890";
    caracter+="JCQWERTYUIOPASDFGHJKLÑZXCVÇBNM";
    caracter+="jcqwertyuiopñlkjhçgfdsazxcvbnm";
    int numero_caracteres=10;
    int total=caracter.length();
    String  clave="";
    for(int a=0;a<numero_caracteres;a++){
        clave+=caracter.charAt(((Double)(total*Math.random())).intValue());
    }
    System.out.println(""+clave);

     caracter="CORREA";
    caracter+="ANA MARIA";
    numero_caracteres=8;
    total=caracter.length();
    user ="";
    for(int a=0;a<numero_caracteres;a++){
        user+=caracter.charAt(((Double)(total*Math.random())).intValue());
    }
    System.out.println("USUARIO: "+user);

    
    }
}
