package bean;

import bsh.EvalError;
import bsh.Interpreter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Disciplina;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Equivalencias;
import jcinform.persistencia.Global;
import jcinform.persistencia.MateriaProfesor;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Notanotas;
import jcinform.persistencia.Notas;
import jcinform.persistencia.ParametrosGlobales;
import jcinform.persistencia.Perfil;
import jcinform.persistencia.Periodo;
import jcinform.persistencia.Resultadoperfil;
import jcinform.persistencia.Sistemacalificacion;
import jcinform.procesos.Administrador;
import org.joda.time.DateMidnight;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import sources.DisciplinaDataSource;

public class disciplina extends Rows {
//ArrayList listad = new ArrayList();

    String redon = "public Double redondear(Double numero, int decimales) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" + "        return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
    String equival = "public Double equivalencia(Double numero) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "       return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
  String prom1 = "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23, Double va24){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++;  if(va23 >0) cont++; if(va24 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22+va23+va24)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++;  if(va23 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22+va23)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++;  if(va18 >0) cont++;  if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16){         int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;         if(cont==0) cont = 1;           return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16)/cont;     } "
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15){         int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;      if(cont==0) cont = 1;      return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14){          int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;      if(cont==0) cont = 1;      return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14)/cont;     } "
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13){         int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;            if(cont==0) cont = 1;       return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13)/cont;     } "
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12){         int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;     if(cont==0) cont = 1;      return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12)/cont;     } "
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11){         int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;          if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;      if(cont==0) cont = 1;       return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11)/cont;    } "
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10){         int cont = 0;          if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;          if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10)/cont;     } "
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9){         int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;          if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;    if(cont==0) cont = 1;        return (va1+va2+va3+va4+va5+va6+va7+va8+va9)/cont;     } "
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(cont==0) cont = 1;            return (va1+va2+va3+va4+va5+va6+va7+va8)/cont;     } "
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7){         int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(cont==0) cont = 1;            return (va1+va2+va3+va4+va5+va6+va7)/cont;    } "
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6){         int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;      if(cont==0) cont = 1;       return (va1+va2+va3+va4+va5+va6)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5){         int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;       if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5)/cont;     } "
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4){         int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;    if(va4 >0) cont++;      if(cont==0) cont = 1;      return (va1+va2+va3+va4)/cont;     }  "
            + "  Double promedio (Double va1, Double va2, Double va3){         int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;      if(cont==0) cont = 1;      return (va1+va2+va3)/cont;     } "
            + "  Double promedio (Double va1, Double va2){         int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;       if(cont==0) cont = 1;         return (va1+va2)/cont; } "
            + "  Double promedio (Double va1){         int cont = 0;         if(va1 >0) cont++;       if(cont==0) cont = 1;       return (va1)/cont; } ";


    public disciplina() {
    }

    public void addRow(Cursos curso, MateriaProfesor materia) {
        int tamanio = 0;
//     if(listad==null){
        Session ses = Sessions.getCurrent();
        Empleados empleado = (Empleados) ses.getAttribute("user");
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();
        List<Equivalencias> equ = null;
        List sistemas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.esdisciplina = true order by o.orden");
        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where o.sistema.esdisciplina = true "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "order by o.sistema.orden ");
        equ = adm.query("Select o from Equivalencias as o"
                    + " where o.periodo.codigoper  = '" + materia.getCurso().getPeriodo().getCodigoper() + "' "
                    + "and o.grupo = 'DR' ");
        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
//          String[] values = new String[sistemas.size()];

//          for (int i=0;i<sistemas.size();i++) {
//              values[i]= ((Sistemacalificacion)sistemas.get(i)).getAbreviatura();
//          }
        tamanio = sistemas.size();
        getChildren().clear();
        Decimalbox label = null;
        Label label3 = null;

        String vacio = "";
        String esDiscp = "true";
        if (materia.getMateria().getCodigo().equals(0)) {
            esDiscp = "false";
        }
        String q = "Select distinct matriculas.codigomat,concat(estudiantes.apellido,' ',estudiantes.nombre,'[',matriculas.estado,']'),"
                + " " + query + " from matriculas "
                + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                + "left join notas on matriculas.codigomat = notas.matricula "
                + "and notas.materia = '" + materia.getMateria().getCodigo() + "' and notas.disciplina = " + esDiscp + " "
                + "where matriculas.curso = '" + curso.getCodigocur() + "'  and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir Pase' or matriculas.estado  = 'Emitir Pase'  or matriculas.estado  = 'Retirado' )"
                + "order by estudiantes.apellido";
        ParametrosGlobales para = (ParametrosGlobales) adm.buscarClave(new Integer(1), ParametrosGlobales.class);
        if (para.getCvalor().equals("P")) {
            q = "Select matriculas.codigomat,(estudiantes.apellido,' ',estudiantes.nombre,'[',matriculas.estado,']'),"
                    + " " + query + " from matriculas "
                    + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                    + "left join notas on matriculas.codigomat = notas.matricula "
                    + "and notas.materia = '" + materia.getMateria().getCodigo() + "' and notas.disciplina = " + esDiscp + " "
                    + "where matriculas.curso = '" + curso.getCodigocur() + "'  and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir Pase'  or matriculas.estado  = 'Emitir Pase'  or matriculas.estado  = 'Retirado' ) "
                    + "order by estudiantes.apellido";
        }
        List nativo = adm.queryNativo(q);
        Row row = new Row();
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            row = new Row();
            int t = vec.size() - 1;
            Boolean deshabilitado = false;
            String color = "black";
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
//                                if(j==t)
//                                 dos = new String(" ");
                }
//                             if(j==t)
//                                label.setValue(""+dos);
//                             else 
                if (j >= 2) {//si no es no matricl ani nombre del estudiante
                    Double valor = (Double) dos;
                    if (valor.equals(0.0)) {
                        label.setValue(new BigDecimal(0));
                    } else {
                        label.setValue(new BigDecimal(redondear((Double) dos, 2)));
                    }
                    //label.setValue(""+redondear((Double) dos,2));
                } else {
                    String valor = dos.toString().replace("(", "").replace(")", "").replace("\"", "").replace(",", "");
                    valor = valor.replace("[Emitir Pase]", "(PE)");
                    valor = valor.replace("[Retirado]", "(R)");
                    valor = valor.replace("[Recibir Pase]", "(PR)");
                    valor = valor.replace("[Matriculado]", "");
                    label3.setValue("" + valor);
                    //label.setValue(""+dos);
                }
                if (j == 0) {
                    label3.setStyle("width:15px;font-size:11px;font:arial; ");
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

                        if (actual.compareTo(finale) <= 0 && actual.compareTo(inicial) >= 0) {
                            label.setDisabled(false);
                            label.setStyle("color:black;font-weight:bold;width:30px;font:arial;font-size:12px;text-align:right;");
                        } else {
                            label.setDisabled(true);
                            label.setStyle("color: black !important; cursor: default !important; opacity: .6; -moz-opacity: .6; filter: alpha(opacity=60); width:30px;font:arial;font-size:12px;text-align:right;background:transparent;font-weigth:bold");

                        }

                        try {
                            Date fecha = ((Sistemacalificacion) sistemas.get(dat)).getFechainicial();
//                            System.out.println("FECHA INICIAL: "+fecha);
                            if (fecha.getDate() == 0) {
                                label.setDisabled(true);
                                label.setStyle("color: black !important; cursor: default !important; opacity: .6; -moz-opacity: .6; filter: alpha(opacity=60); width:30px;font:arial;font-size:12px;text-align:right;background:transparent;font-weigth:bold");
                            }
                        } catch (Exception z) {
                            label.setDisabled(true);
                            label.setStyle("color: black !important; cursor: default !important; opacity: .6; -moz-opacity: .6; filter: alpha(opacity=60); width:30px;font:arial;font-size:12px;text-align:right;background:transparent;font-weigth:bold");
                        }


                    } else {
                        label.setDisabled(true);
                        label.setStyle("color: " + color + " !important; cursor: default !important; opacity: .6; -moz-opacity: .6; filter: alpha(opacity=60); width:30px;font:arial;font-size:12px;text-align:right;background:transparent;font-weigth:bold");
                    }
//                    label.setReadonly(true);
                    row.appendChild(label);
                }

            }

            row.setParent(this);
        }
        nativo = null;
    }

    public Boolean verificar(String formula, List<Notanotas> notas) {

        formula = formula.replace("()", "");
        if (formula.equals("")) {
            return false;
        }


        Interpreter inter = new Interpreter();
        try {
            inter.eval(redon);
            inter.eval(prom1);
            inter.eval(equival);
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
                Decimalbox object1 = (Decimalbox) labels.get(j);
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
                if ((aCargar > notas.get(j - 2).getSistema().getConduca() || aCargar < 0) && formula.isEmpty()) {
                    return "NOTA FUERA DE RANGO EN PERIODO: " + notas.get(j - 2).getSistema().getAbreviatura() + "  NOTA: " + aCargar + " "
                            + " ESTUDIANTE: " + ma.getEstudiante().getApellido() + " " + ma.getEstudiante().getNombre() + "  ";
                }
            }
        }
        return "";
    }

    public String guardar(List col, Cursos curso, MateriaProfesor materia) {
        System.out.println("INICIO EN: " + new Date());
        Interpreter inter = new Interpreter();

        //  String redon = "public Double redondear(Double numero, int decimales) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" + "        return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";


        try {
            inter.eval(redon);
            inter.eval(prom1);
            inter.eval(equival);
        } catch (Exception e) {
            System.out.println("" + e);
            return "false";
        }

        Administrador adm = new Administrador();
        secuencial sec = new secuencial();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where o.sistema.esdisciplina = true  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'order by o.sistema.orden ");


        List<Sistemacalificacion> sisFormulas = adm.query("Select o from Sistemacalificacion as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.formula <> '' "
                + " and o.esdisciplina = true "
                + "  order by o.orden ");

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
        String del = "Delete from Notas where matricula.curso.codigocur = '" + curso.getCodigocur() + "' "
                + "and materia.codigo = '" + materia.getMateria().getCodigo() + "'  and notas.disciplina = true ";
        if (materia.getMateria().getCodigo().equals(0)) {
            del = "Delete from Notas where matricula.curso.codigocur = '" + curso.getCodigocur() + "' "
                    + "and materia.codigo = '" + materia.getMateria().getCodigo() + "'  ";
        }
        adm.ejecutaSql(del);
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Notas nota = new Notas();
                nota.setCodigonot(sec.generarClave());
                List labels = object.getChildren();
                nota.setMatricula(new Matriculas(new Integer(((Label) labels.get(0)).getValue())));
                nota.setMateria(materia.getMateria());
                nota.setOrden(materia.getOrden());
                nota.setCuantitativa(materia.getCuantitativa());
                nota.setPromedia(materia.getMinisterio());
                nota.setSeimprime(materia.getSeimprime());
                nota.setFecha(new Date());
                nota.setDisciplina(true);
                if (nota.getMateria().getCodigo().equals(0)) {
                    nota.setDisciplina(false);
                }

                inter.set("nota", nota);
                int ta = labels.size() - 1;
                for (int j = 2; j < labels.size(); j++) {
                    Decimalbox object1 = (Decimalbox) labels.get(j);
                    String formula = notas.get(j - 2).getSistema().getFormuladisciplina();// EN CASO DE FORMULA
                    formula = formula.replace("no", "nota.getNo");//EN CASO DE QUE HAYA FORMULA
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
//              Label object1 = (Label) labels.get(ta);
//              nota.setObservacion(object1.getValue()+"");
                adm.guardar(nota);
               
                //System.out.println("FINALIZO EN: "+new Date());
                //return "ok";
            } catch (EvalError ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
        }
        recalculoNotas(materia, curso);
          System.out.println("FINZALI EN: " + new Date());
        return "ok";



    }

    public void recalculoNotas(MateriaProfesor materia, Cursos curso) {
             Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where o.sistema.esdisciplina = true  "
                + "and o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "'order by o.sistema.orden ");
        String query = "";
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
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
                Interpreter inter = new Interpreter();
                inter.eval(redon);
                inter.eval(prom1);
                inter.eval(equival);
                try {
                    for (Iterator<Global> it = Nmaterias.iterator(); it.hasNext();) {
                        Global global = it.next();
                        String q = "Select matriculas.codigomat, " + query + "  from matriculas "
                                + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                                + "left join notas on matriculas.codigomat = notas.matricula "
                                + "and notas.materia = '" + global.getCodigo() + "' and notas.disciplina = true "
                                + "where matriculas.curso = '" + curso.getCodigocur() + "'  "
                                + " and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir Pase'  or matriculas.estado  = 'Emitir Pase'  or matriculas.estado  = 'Retirado' ) "
                                + "order by estudiantes.apellido";
System.out.println("EL QUERY DE REALCULO: "+q);
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
                            + "and materia.codigo = '" + map.getMateria().getCodigo() + "' and disciplina = true ";
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
                            nota.setDisciplina(true);
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

    public void addPerfil(Cursos curso, Sistemacalificacion sistema) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();

        getChildren().clear();
        Label label = null;
        Label label3 = null;

        Listbox combo = new Listbox();
        Listitem item = new Listitem("USUALMENTE");


        List<Matriculas> matriculas = adm.query("Select o from Matriculas as o where o.curso.codigocur = '" + curso.getCodigocur() + "' order by o.estudiante.apellido, o.estudiante.nombre ");
        Row row = new Row();
        try {
            for (Iterator<Matriculas> it = matriculas.iterator(); it.hasNext();) {
                Matriculas matriculas1 = it.next();
                row = new Row();
                String q = "SELECT valor FROM perfil LEFT JOIN resultadoperfil ON resultadoperfil.perfil = perfil.codigo and matricula = '" + matriculas1.getCodigomat() + "' "
                        + " AND resultadoperfil.periodo = '" + sistema.getCodigosis() + "' "
                        + "where perfil.periodo = '" + periodo.getCodigoper() + "' ";
                System.out.println("QUERY;   " + q);
                List nativo = adm.queryNativo(q);
                label3 = new Label("" + matriculas1.getCodigomat());
                row.appendChild(label3);
                label3 = new Label("" + matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                row.appendChild(label3);

                for (Iterator it1 = nativo.iterator(); it1.hasNext();) {

                    Vector vec = (Vector) it1.next();
                    Object dos = vec.get(0);
                    if (dos == null) {
                        dos = "SIEMPRE";
                    }
                    combo = new Listbox();
                    combo.setMold("select");
                    combo.setRows(1);
                    combo.setStyle("font-size:9px;");

                    item = new Listitem("SIEMPRE");
                    item.setValue("SIEMPRE");
                    combo.appendChild(item);

                    item = new Listitem("CASI SIEMPRE");
                    item.setValue("CASI SIEMPRE");
                    combo.appendChild(item);

                    item = new Listitem("USUALMENTE");
                    item.setValue("USUALMENTE");
                    combo.appendChild(item);

                    item = new Listitem("A VECES");
                    item.setValue("A VECES");
                    combo.appendChild(item);

                    item = new Listitem("RARA VEZ");
                    item.setValue("RARA VEZ");
                    combo.appendChild(item);

                    item = new Listitem("NUNCA");
                    item.setValue("NUNCA");
                    combo.appendChild(item);

                    item = new Listitem(dos + "");
                    item.setValue(dos + "");
                    combo.appendChild(item);
                    combo.setSelectedItem(item);

                    row.appendChild(combo);


                }
//                    row = new Row();

                row.setParent(this);

                nativo = null;

            }


        } catch (Exception ex) {
            Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
        }



    }

    public void guardarPerfil(List col, Cursos curso, Sistemacalificacion sistema) {
        System.out.println("INICIO EN: " + new Date());
        Interpreter inter = new Interpreter();
        Administrador adm = new Administrador();
        secuencial sec = new secuencial();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List perfiles = adm.query("Select o from Perfil as o where o.periodo.codigoper =  '" + periodo.getCodigoper() + "' ");

//        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");

        String del = "Delete from Resultadoperfil "
                + "where matricula.curso.codigocur = '" + curso.getCodigocur() + "' "
                + "and periodo.codigosis = '" + sistema.getCodigosis() + "' ";
        adm.ejecutaSql(del);
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Resultadoperfil nota = new Resultadoperfil();

                List labels = object.getChildren();
                nota.setMatricula(new Matriculas(new Integer(((Label) labels.get(0)).getValue())));
                nota.setPeriodo(sistema);

                inter.set("nota", nota);

                for (int j = 2; j < labels.size(); j++) {
                    Listbox object1 = (Listbox) labels.get(j);
                    String vaNota = object1.getSelectedItem().getValue().toString();
                    nota.setValor(vaNota);
                    nota.setPerfil((Perfil) perfiles.get(j - 2));
                    adm.guardar(nota);

                }
//                nota = (Resultadoperfil) inter.get("nota");


//                adm.guardar(nota);
            } catch (EvalError ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("FINALIZO EN: " + new Date());


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

    public void addFaltas(Cursos curso, Sistemacalificacion sistema) {
        int tamanio = 0;
//     if(listad==null){
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();
        List sistemas = adm.query("Select o from Equivalencias as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.grupo = 'DI'");
        String query = "";
        for (int a = 1; a < sistemas.size() + 1; a++) {
            query += "nota" + a + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        tamanio = sistemas.size();
        getChildren().clear();
        Decimalbox label = null;
        Label label3 = null;
        String vacio = "";
        String q = "Select distinct matriculas.codigomat,concat(estudiantes.apellido,' ',estudiantes.nombre),"
                + " " + query + " from matriculas "
                + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                + "left join disciplina on matriculas.codigomat = disciplina.matricula and disciplina.sistema = '" + sistema.getCodigosis() + "'  "
                + "where matriculas.curso = '" + curso.getCodigocur() + "' "
                + "order by estudiantes.apellido";
        ParametrosGlobales para = (ParametrosGlobales) adm.buscarClave(new Integer(1), ParametrosGlobales.class);
        if (para.getCvalor().equals("P")) {
            q = "Select matriculas.codigomat,(estudiantes.apellido,' ',estudiantes.nombre),"
                    + " " + query + " from matriculas "
                    + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                    + "left join disciplina on matriculas.codigomat = disciplina.matricula and disciplina.sistema = '" + sistema.getCodigosis() + "'  "
                    + "where matriculas.curso = '" + curso.getCodigocur() + "' "
                    + "order by estudiantes.apellido";
        }

        List nativo = adm.queryNativo(q);
        Row row = new Row();
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            row = new Row();
            int t = vec.size() - 1;
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                label = new Decimalbox();
                label3 = new Label();
                try {
                    if (dos.equals(null)) {
                        dos = new Integer(0);
                    }
                } catch (Exception e) {
                    dos = new Integer(0);
                }

                if (j >= 2) {//si no es no matricl ani nombre del estudiante
                    Integer valor = (Integer) dos;
                    if (valor.equals(0)) {
                        label.setZIndex(j);
                        label.setAttribute("ini", 0);
                        label.setAttribute("fin", 20);
                        label.setValue(new BigDecimal(0));
                    } else {
                        label.setAttribute("ini", 0);
                        label.setAttribute("fin", 20);
                        label.setZIndex(j);
                        label.setValue(new BigDecimal((Integer) dos));
                    }
                    //label.setValue(""+((Integer) dos));
                } else {
                    String valor = dos.toString().replace("(", "").replace(")", "").replace("\"", "").replace(",", "");
                    label3.setValue(valor);
                    //label.setValue(""+dos);
                }


                if (j == 0) {
                    label3.setStyle("width:15px;font-size:11px;font:arial; ");
                    row.appendChild(label3);
                } else if (j == 1) {
                    label3.setStyle("width:300px;font-size:11px;font:arial; ");
                    row.appendChild(label3);
                } else {
                    label.setDisabled(false);
                    label.setStyle("width:30px;font:arial;font-size:12px;text-align:right;");
                    row.appendChild(label);
                }
            }

            row.setParent(this);
        }
        nativo = null;



    }

    public void guardar(List col, Cursos curso, Sistemacalificacion sistema) {
        System.out.println("INICIO EN: " + new Date());
        Interpreter inter = new Interpreter();
        Administrador adm = new Administrador();
        secuencial sec = new secuencial();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List sistemas = adm.query("Select o from Equivalencias as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.grupo = 'DI'");
        String query = "";
        for (int a = 1; a < sistemas.size() + 1; a++) {
            query += "nota" + a + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
//       List<Notanotas> notas = adm.query("Select o from Notanotas as o  " +
//               "where o.sistema.faltas = true " +
//               " and o.sistema.periodo.codigoper = '"+periodo.getCodigoper()+"'  " +
//               "order by o.sistema.orden ");
        String del = "Delete from Disciplina where matricula.curso.codigocur = '" + curso.getCodigocur() + "' "
                + "and sistema.codigosis = '" + sistema.getCodigosis() + "' "
                + "   ";
        adm.ejecutaSql(del);
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Disciplina nota = new Disciplina();
                nota.setCodigodisc(sec.generarClave());
                List labels = object.getChildren();
                nota.setMatricula(new Matriculas(new Integer(((Label) labels.get(0)).getValue())));
                nota.setSistema(sistema);
                //nota.setMateria(materia.getMateria());
                //nota.setOrden(materia.getOrden());
//                nota.setCuantitativa(materia.getCuantitativa());
//                nota.setPromedia(materia.getMinisterio());
//                nota.setSeimprime(materia.getSeimprime());
                nota.setFecha(new Date());
//                nota.setDisciplina(true);
                inter.set("nota", nota);
                int ta = labels.size() - 1;
                int m = 1;
                for (int j = 2; j < labels.size(); j++) {

                    Decimalbox object1 = (Decimalbox) labels.get(j);
//                  String formula = notas.get(j-2).getSistema().getFormulafaltas();// EN CASO DE FORMULA
//                  formula = formula.replace("no","nota.getNo");//EN CASO DE QUE HAYA FORMULA
                    String vaNota = object1.getValue().toString();
                    Integer aCargar = 0;
                    if (vaNota.equals("")) {
                        aCargar = 0;
                    } else {
                        aCargar = new Integer(vaNota);
                    }

                    inter.eval("nota.setNota" + (m) + "(" + aCargar + ");");
                    m++;
//                        if(!formula.isEmpty()){
//                                System.out.println(""+formula);
//                                inter.eval("nota.set"+(uno+toda)+"("+formula+");");
//                        }
                }
                nota = (Disciplina) inter.get("nota");
//                    Label object1 = (Label) labels.get(ta);
//                nota.setObservacion(object1.getValue()+"");

                adm.guardar(nota);
            } catch (EvalError ex) {
                Logger.getLogger(notas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("FINALIZO EN: " + new Date());


    }

    public ArrayList reporteDisciplina(Cursos curso, Sistemacalificacion sistema) {
        Administrador adm = new Administrador();
        byte[] bytes = null;
        try {
            Session ses = Sessions.getCurrent();
            Periodo periodo = (Periodo) ses.getAttribute("periodo");
            Map parameters = new HashMap();
            parameters.put("denominacion", periodo.getInstitucion().getDenominacion());
            parameters.put("nombre", "" + periodo.getInstitucion().getNombre());
            parameters.put("periodo", "" + periodo.getDescripcion());
            parameters.put("slogan", "" + periodo.getInstitucion().getSlogan());


            //parameters.put("titulo", "" + titulo);

            String tipo = "INGRESADA";
            List<ParametrosGlobales> para = adm.query("Select o from ParametrosGlobales as o "
                    + "where o.variable = 'TIPODISCIPLINA' "
                    + "and o.periodo.codigoper = '" + periodo.getCodigoper() + "'");
            if (para.size() > 0) {
                ParametrosGlobales param = para.get(0);
                tipo = param.getCvalor();
            }

//            String tipo = var.busquedaC("Z_FORMULADISCIPLINA", parametros);
            //Double notaBase = var.busquedaD("Z_NOTADISC",parametros);
            Double notaBase = 0.0;
            List<Equivalencias> equivalenciasFaltas = adm.query("Select o from Equivalencias as o "
                    + "where o.grupo = 'DI' "
                    + "and o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");

            List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                    + " where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' and o.sistema.codigosis = '" + sistema.getCodigosis() + "' "
                    + "order by o.sistema.orden");
            List<MateriaProfesor> lista = adm.query("Select o from MateriaProfesor as o "
                    + "where o.curso.codigocur = '" + curso.getCodigocur() + "'"
                    + " and o.materia.codigo > 2 and o.opcional = true and o.ministerio = true order by o.materia.descripcion");
            String query = "";
            String query2 = "";
            String numeroDecimales = "3";
            for (Notanotas notass : notas) {
                query += notass.getNota() + ",";
            }
            for (Notanotas notass : notas) {
                query2 += "round(avg(" + notass.getNota() + ")," + numeroDecimales + "),";
            }
            query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
            query2 = query2.substring(0, query2.length() - 1).replace("'", "");
            List<Notas> lisNotas = new ArrayList();
            ArrayList lisNotasC = new ArrayList();

            List<Matriculas> matriculas = new ArrayList();
            matriculas = adm.query("Select o from Matriculas as o "
                    + "where o.curso.codigocur = '" + curso.getCodigocur() + "' "
                    + "and  o.estado IN ('Matriculado','Retirado') "
                    + "order by o.estudiante.apellido ");
            Interpreter inter = new Interpreter();
            inter.eval(prom1);
            inter.eval(redon);
            inter.eval(equival);
            for (Iterator<Matriculas> itm = matriculas.iterator(); itm.hasNext();) {
                Matriculas matriculas1 = itm.next();
                String query3 = "";
                int w = 1;
                
                for (int i = 0; i < equivalenciasFaltas.size(); i++) {
                    query3 += "sum(nota" + w + "),";
                    parameters.put("f" + (i + 1), equivalenciasFaltas.get(i).getAbreviatura());
                    w++;
                }
                query3 = query3.substring(0, query3.length() - 1);
                //IMPRIMO LAS FALTAS
                String q = "Select " + query3 + "  from disciplina "
                        + "where matricula = '" + matriculas1.getCodigomat() + "'  "
                        + "and sistema = '" + sistema.getCodigosis() + "' "
                        + " group by matricula ";
                //System.out.println(""+q);
                List nativo = adm.queryNativo(q);
                NotaDisciplina coll = new NotaDisciplina();
                coll.setEstado(matriculas1.getEstado());
                coll.setNombres(matriculas1.getEstudiante().getApellido() + " " + matriculas1.getEstudiante().getNombre());
                coll.setCurso(curso + "");

                for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();
                    inter.set("nota", coll);
                    for (int j = 0; j < vec.size(); j++) {
                        Object dos = vec.get(j);
                        Integer val = new Integer(dos.toString());

                        inter.eval("nota.setJ" + (j + 1) + "(" + val + ")");
                    }
                    coll = (NotaDisciplina) inter.get("nota");

                }
                int i = 0;
                Double promProfesor = 0.0;
                String formu="";
                for (Iterator<MateriaProfesor> it = lista.iterator(); it.hasNext();) {
                    MateriaProfesor MateriaProfesor = it.next();
                    parameters.put("n" + (i + 1), MateriaProfesor.getMateria().getDescripcion());
                    q = "Select " + query + "  from Notas notas "
                            + "where notas.matricula = '" + matriculas1.getCodigomat() + "' "
                            + "and notas.materia = '" + MateriaProfesor.getMateria().getCodigo() + "'"
                            + " and notas.disciplina = true and notas.promedia = true  "
                            + " ";
                    List nativo2 = adm.queryNativo(q);
//                    System.out.println("query: " + q);
                    if (nativo2.size() > 0) {

                        Vector vec = (Vector) nativo2.get(0);
                        inter.set("nota", coll);
                        Object dos = vec.get(0);
                        if (dos == null) {
                            dos = new Double(0);
                        }
                        Double val = new Double(dos.toString());
                        inter.eval("nota.setN" + (i + 1) + "(" + val + ")");
                        coll = (NotaDisciplina) inter.get("nota");
                        promProfesor += val;
                        formu +=  val+",";

                    } else {
//                                        inter.eval("nota.setN" + (i + 1) + "(0.0)");
//                                        coll = (NotaDisciplina) inter.get("nota");
                    }

                    i++;

                }
                if(formu.length()>0)
                    formu = formu.substring(0, formu.length() - 1);
                else
                    formu ="0";
                inter.eval("Double valCal =  promedio("+formu+")  ");
                Double valorF = (Double) inter.get("valCal");
                //coll.setProfesores(promProfesor / lista.size());
                coll.setProfesores(valorF);
                 

                q = "Select " + query + "  from Notas notas " + //SACO LA DISCIPLINA DEL INSPECTOR CODIGO = 1
                        "where notas.matricula = '" + matriculas1.getCodigomat() + "' "
                        + "and notas.materia = '1' "
                        + " and notas.disciplina = true  "
                        + " ";
                nativo = adm.queryNativo(q);
                
                if (nativo.size() > 0) {
                    Vector vec = (Vector) nativo.get(0);
                    inter.set("nota", coll);
                    Object dos = vec.get(0);
                    Double val = new Double(dos.toString());

                    coll.setInspector(val);
                    if (val.equals(0.0)) {
                        coll.setInspector(notaBase);
                    }
                } else {
                    coll.setInspector(notaBase);
                }
                Double notaInspector = coll.getInspector();
                
                if (tipo.contains("MITAD")) {//SUMA ENTRE PROMEDIOS DIVIDIDO PARA 2
                    //coll.setFinali( (promProfesor / lista.size() + notaInspector) / 2);
                    inter.eval("Double prom1 = promedio(promedio("+formu+") , "+notaInspector+" ) ");
                    Double valorMit = (Double) inter.get("prom1");
                    //coll.setProfesores(promProfesor / lista.size());
                    coll.setFinali(valorMit);
                } else if (tipo.contains("PROMEDIO")) {//
                     //coll.setFinali((promProfesor + notaInspector) / (lista.size() + 1));
                        inter.eval("Double prom2 =  promedio("+formu+" , "+notaInspector+" ) ");
                        Double valorMit = (Double) inter.get("prom2");
                        coll.setFinali(valorMit);
                     
                } else if (tipo.contains("SUMATORIA")) {//PROMEDIO DE PROFESORES + PROMEDIO DE INSPECCION
                    //coll.setFinali(promProfesor / lista.size() + notaInspector);
                      inter.eval("Double prom3 =  promedio("+formu+") + "+notaInspector+" ");
                        Double valorMit = (Double) inter.get("prom3");
                        coll.setFinali(valorMit);
                } else if (tipo.contains("PROMEDIADA")) {//SUMA SOLO LAS MATERIAS Y LAS DIVIDE PARA LOS QUE INGRESARON
                    //coll.setFinali((promProfesor) / lista.size());
                        inter.eval("Double prom4 =  promedio("+formu+")");
                        Double valorMit = (Double) inter.get("prom4");
                        coll.setFinali(valorMit);
                } else if (tipo.contains("INGRESADA")) {
                    q = "Select " + query + "  from Notas notas "
                            + "where notas.matricula = '" + matriculas1.getCodigomat() + "' "
                            + "and notas.materia = '0'"
                            + "   "
                            + " ";
                    nativo = adm.queryNativo(q);
                    if (nativo.size() > 0) {
                        Vector vec = (Vector) nativo.get(0);
                        inter.set("nota", coll);
                        Object dos = vec.get(0);
                        Double val = new Double(dos.toString());
                        coll.setFinali(val);
                    } else {
                        coll.setFinali(20.0);
                    }
                }
                lisNotasC.add(coll);
                nativo = null;
            }
            DisciplinaDataSource ds = new DisciplinaDataSource(lisNotasC);
            lisNotas = null;
            //ReporteBecadosDataSource ds = new ReporteBecadosDataSource(listaMatriculados);
            ArrayList arr = new ArrayList();
            arr.add(ds);
            arr.add(parameters);
//            DisciplinaDataSource ds = (DisciplinaDataSource) arr.get(0);
            return arr;
        } catch (Exception e) {
            Logger.getLogger(disciplina.class.getName()).log(Level.SEVERE, null, e);
//            Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, "exception caught", e);
        }

        return null;
    }
}
