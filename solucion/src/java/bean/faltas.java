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
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import sources.DisciplinaDataSource;

public class faltas extends Rows {
//ArrayList listad = new ArrayList();

    String redon = "public Double redondear(Double numero, int decimales) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" + "        return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
    String truncar = "public Double truncar(Double numero, int decimales) {         try {             java.math.BigDecimal d = new java.math.BigDecimal(numero);             d = d.setScale(decimales, java.math.BigDecimal.ROUND_DOWN);             return d.doubleValue();         } catch (Exception e) {             return 0.0;         }     }";
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

    public faltas() {
    }

    public void addFaltas(Cursos curso, Sistemacalificacion sistema,MateriaProfesor mp) {
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
        Intbox label = null;
        Label label3 = null;
        String vacio = "";
        String q = "Select distinct matriculas.codigomat,concat(estudiantes.apellido,' ',estudiantes.nombre),"
                + " faltas, justificadas, total from matriculas "
                + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                + "left join faltas on matriculas.codigomat = faltas.matricula"
                + "  and faltas.sistema = '" + sistema.getCodigosis() + "' "
                + " and faltas.materia = '" + mp.getMateria().getCodigo() + "'  "
                + "where matriculas.curso = '" + curso.getCodigocur() + "' "
                + "   and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir Pase'  or matriculas.estado  = 'Emitir Pase'  or matriculas.estado  = 'Retirado' )  "
                + "order by estudiantes.apellido";
    

        List nativo = adm.queryNativo(q);
        Row row = new Row();
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            row = new Row();
            int t = vec.size() - 1;
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                label = new Intbox();
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
                        label.setValue((0));
                    } else {
                        label.setAttribute("ini", 0);
                        label.setAttribute("fin", 20);
                        label.setZIndex(j);
                        label.setValue(((Integer) dos));
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
                } else if (j == 4) {
                    label.setStyle("color: black !important; cursor: default !important; opacity: .6; -moz-opacity: .6; filter: alpha(opacity=60); width:30px;font:arial;font-size:12px;text-align:right;background:transparent;font-weigth:bold");
                    label.setDisabled(true);
                    //label.setReadonly(true);
                    row.appendChild(label);
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

    public Boolean verificar(String formula, List<Notanotas> notas) {

        formula = formula.replace("()", "");
        if (formula.equals("")) {
            return false;
        }


        Interpreter inter = new Interpreter();
        try {
            inter.eval(redon);
            inter.eval(truncar);
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
                //Decimalbox object1 = (Decimalbox) labels.get(j);
                String vaNota = "";//object1.getValue().toString();
                Listbox objectListBox = null;
                Decimalbox objectDecimalBox = null;
//                   String vaNota="";
                if (labels.get(j) instanceof Listbox) {
                    objectListBox = (Listbox) labels.get(j);
                    vaNota = ((Equivalencias) objectListBox.getSelectedItem().getValue()).getNota().toString();
                } else {
                    objectDecimalBox = (Decimalbox) labels.get(j);
                    vaNota = objectDecimalBox.getValue().toString();
                }

                String formula = notas.get(j - 2).getSistema().getFormuladisciplina(); // EN CASO DE FORMULA
                formula = formula.replace("no", "nota.getNo"); //EN CASO DE QUE HAYA FORMULA
                String toda = notas.get(j - 2).getNota() + "";

                toda = toda.substring(1, toda.length());

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


    public void guardar(List col, Cursos curso, Sistemacalificacion sistema, MateriaProfesor mp) {
        System.out.println("INICIO EN: " + new Date());
        //Interpreter inter = new Interpreter();
        Administrador adm = new Administrador();
        secuencial sec = new secuencial();
        //Session ses = Sessions.getCurrent();
        //Periodo periodo = (Periodo) ses.getAttribute("periodo");
        String del = "Delete from Faltas "
                + " where matricula.curso.codigocur = '" + curso.getCodigocur() + "' "
                + " and sistema.codigosis = '" + sistema.getCodigosis() + "' "
                + " and materia.codigo = '"+mp.getMateria().getCodigo()+"' "
                + "   ";
        adm.ejecutaSql(del);
        for (int i = 0; i < col.size(); i++) {
            try {
                Row object = (Row) col.get(i);
                Faltas nota = new Faltas();
                nota.setCodigo(sec.generarClave());
                List labels = object.getChildren();
                nota.setMatricula(new Matriculas(new Integer(((Label) labels.get(0)).getValue())));
                nota.setFaltas(new Integer(((Intbox) labels.get(2)).getValue()));
                nota.setJustificadas(new Integer(((Intbox) labels.get(3)).getValue()));
                if(nota.getFaltas()<nota.getJustificadas()&&nota.getFaltas()>0){
                    nota.setJustificadas(nota.getFaltas()); 
                }
                
                nota.setTotal(nota.getFaltas()-nota.getJustificadas());
                if(nota.getTotal()<0){
                    nota.setTotal(0); 
                }
                
                nota.setMateria(mp.getMateria());
                nota.setSistema(sistema);
//                 String vaNota = object1.getValue().toString();
//                    Integer aCargar = 0;
//                    if (vaNota.equals("")) {
//                        aCargar = 0;
                
//                    } else {
//                        aCargar = new Integer(vaNota);
//                    }
                //nota.setMateria(materia.getMateria());
                //nota.setOrden(materia.getOrden());
//                nota.setCuantitativa(materia.getCuantitativa());
//                nota.setPromedia(materia.getMinisterio());
//                nota.setSeimprime(materia.getSeimprime());
//                nota.setFecha(new Date());
//                nota.setDisciplina(true);

//                    Label object1 = (Label) labels.get(ta);
//                nota.setObservacion(object1.getValue()+"");

                adm.guardar(nota);
            } catch (Exception ex) {
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
            inter.eval(truncar);
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
                String formu = "";
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
                        formu += val + ",";

                    } else {
//                                        inter.eval("nota.setN" + (i + 1) + "(0.0)");
//                                        coll = (NotaDisciplina) inter.get("nota");
                    }

                    i++;

                }
                if (formu.length() > 0) {
                    formu = formu.substring(0, formu.length() - 1);
                } else {
                    formu = "0";
                }
                inter.eval("Double valCal =  promedio(" + formu + ")  ");
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
                    inter.eval("Double prom1 = promedio(promedio(" + formu + ") , " + notaInspector + " ) ");
                    Double valorMit = (Double) inter.get("prom1");
                    //coll.setProfesores(promProfesor / lista.size());
                    coll.setFinali(valorMit);
                } else if (tipo.contains("PROMEDIO")) {//
                    //coll.setFinali((promProfesor + notaInspector) / (lista.size() + 1));
                    inter.eval("Double prom2 =  promedio(" + formu + " , " + notaInspector + " ) ");
                    Double valorMit = (Double) inter.get("prom2");
                    coll.setFinali(valorMit);

                } else if (tipo.contains("SUMATORIA")) {//PROMEDIO DE PROFESORES + PROMEDIO DE INSPECCION
                    //coll.setFinali(promProfesor / lista.size() + notaInspector);
                    inter.eval("Double prom3 =  promedio(" + formu + ") + " + notaInspector + " ");
                    Double valorMit = (Double) inter.get("prom3");
                    coll.setFinali(valorMit);
                } else if (tipo.contains("PROMEDIADA")) {//SUMA SOLO LAS MATERIAS Y LAS DIVIDE PARA LOS QUE INGRESARON
                    //coll.setFinali((promProfesor) / lista.size());
                    inter.eval("Double prom4 =  promedio(" + formu + ")");
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
            Logger.getLogger(faltas.class.getName()).log(Level.SEVERE, null, e);
//            Logger.getLogger(getClass().getName()).log(java.util.logging.Level.SEVERE, "exception caught", e);
        }

        return null;
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

    public Equivalencias devolverNombre(List<Equivalencias> equiva, Integer codigo) {

        for (Iterator<Equivalencias> it = equiva.iterator(); it.hasNext();) {
            Equivalencias equivalencias = it.next();
            if (equivalencias.getValorminimo() <= codigo && codigo <= equivalencias.getValormaximo()) {
                return equivalencias;
            }
        }
        return equiva.get(0);


    }
}
