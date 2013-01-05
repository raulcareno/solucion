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
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.Textbox;

public class Planificacion extends Rows {
//ArrayList listad = new ArrayList();

    private Double notaDisciplina = 0.0;
    String redon = "public Double redondear(Double numero, int decimales) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" + "        return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
    String equival = "public Double equivalencia(Double numero) {" + "" + "try{" + "                java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "       return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        }" + "     }";
    String prom1 = " "
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23, Double va24, Double va25, Double va26, Double va27, Double va28, Double va29, Double va30, Double va31, Double va32){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++;  if(va23 >0) cont++; if(va24 >0) cont++; if(va25 >0) cont++; if(va26 >0) cont++;  if(va27 >0) cont++;  if(va28 >0) cont++;   if(va29 >0) cont++;   if(va30 >0) cont++;  if(va31 >0) cont++; if(va32 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22+va23+va24+va25+va26+va27+va28+va29+va30+va31+va32)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23, Double va24, Double va25, Double va26, Double va27, Double va28, Double va29, Double va30, Double va31){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++;  if(va23 >0) cont++; if(va24 >0) cont++; if(va25 >0) cont++; if(va26 >0) cont++;  if(va27 >0) cont++;  if(va28 >0) cont++;   if(va29 >0) cont++;   if(va30 >0) cont++; if(va31 >0) cont++;   if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22+va23+va24+va25+va26+va27+va28+va29+va30+va31)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23, Double va24, Double va25, Double va26, Double va27, Double va28, Double va29, Double va30){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++;  if(va23 >0) cont++; if(va24 >0) cont++; if(va25 >0) cont++; if(va26 >0) cont++;  if(va27 >0) cont++;  if(va28 >0) cont++;   if(va29 >0) cont++;   if(va30 >0) cont++;  if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22+va23+va24+va25+va26+va27+va28+va29+va30)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23, Double va24, Double va25, Double va26, Double va27, Double va28, Double va29){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++;  if(va23 >0) cont++; if(va24 >0) cont++; if(va25 >0) cont++; if(va26 >0) cont++;  if(va27 >0) cont++;  if(va28 >0) cont++;   if(va29 >0) cont++;   if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22+va23+va24+va25+va26+va27+va28+va29)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23, Double va24, Double va25, Double va26, Double va27, Double va28){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++;  if(va23 >0) cont++; if(va24 >0) cont++; if(va25 >0) cont++; if(va26 >0) cont++;  if(va27 >0) cont++;  if(va28 >0) cont++;   if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22+va23+va24+va25+va26+va27+va28)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23, Double va24, Double va25, Double va26, Double va27){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++;  if(va23 >0) cont++; if(va24 >0) cont++; if(va25 >0) cont++;  if(va26 >0) cont++;  if(va27 >0) cont++;   if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22+va23+va24+va25+va26+va27)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23, Double va24, Double va25, Double va26){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++;  if(va23 >0) cont++; if(va24 >0) cont++;  if(va25 >0) cont++;  if(va26 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22+va23+va24+va25+va26)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23, Double va24, Double va25){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++;  if(va23 >0) cont++; if(va24 >0) cont++;  if(va25 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22+va23+va24+va25)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23, Double va24){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++;  if(va23 >0) cont++; if(va24 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22+va23+va24)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22, Double va23){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++;  if(va23 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22+va23)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21, Double va22){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++;  if(va22 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21+va22)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20, Double va21){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++;  if(va21 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20+va21)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19, Double va20){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++;  if(va20 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19+va20)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18, Double va19){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++; if(va18 >0) cont++; if(va19 >0) cont++; if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18+va19)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17, Double va18){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++;  if(va18 >0) cont++;  if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17+va18)/cont;     }"
            + "  Double promedio (Double va1, Double va2, Double va3, Double va4, Double va5, Double va6, Double va7, Double va8, Double va9, Double va10, Double va11, Double va12, Double va13, Double va14, Double va15, Double va16, Double va17){        int cont = 0;         if(va1 >0) cont++;  if(va2 >0) cont++;        if(va3 >0) cont++;         if(va4 >0) cont++;        if(va5 >0) cont++;        if(va6 >0) cont++;         if(va7 >0) cont++;        if(va8 >0) cont++;        if(va9 >0) cont++;         if(va10 >0) cont++;        if(va11 >0) cont++;        if(va12 >0) cont++;         if(va13 >0) cont++;        if(va14 >0) cont++;        if(va15 >0) cont++;         if(va16 >0) cont++;        if(va17 >0) cont++;  if(cont==0) cont = 1;         return (va1+va2+va3+va4+va5+va6+va7+va8+va9+va10+va11+va12+va13+va14+va15+va16+va17)/cont;     }"
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

    public Planificacion() {
        //         Grid g;
//         Label l;
//         Row row;
//         row.getZIndex()
    }

    public Boolean verificar(String formula, List<Notanotas> notas) {

        formula = formula.replace("()", "");


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
            Logger.getLogger(Planificacion.class.getName()).log(Level.SEVERE, null, ex);
            return true;
        }
        return false;
    }
    
    

    public void addRow(String curso, Global materia) {
        System.out.println("TOP INI; " + new Date());
//        int tamanio = 0;
        System.setProperty("java.awt.headless", "true");
        Session ses = Sessions.getCurrent();
        Empleados empleado = (Empleados) ses.getAttribute("user");
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
//     if(listad==null){
        Administrador adm = new Administrador();
        Row row = new Row();
        getChildren().clear();
        Textbox notaTexto = null;
        List<PlanificacionDetalle> detalle = adm.query("Select o from PlanificacionDetalle as o  "
                + " where o.evaluador.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + " order by o.evaluador.orden  ");
        if (detalle.size()> 0) {
            row = new Row();
            for (Iterator<PlanificacionDetalle> it = detalle.iterator(); it.hasNext();) {
                PlanificacionDetalle planificacionDetalle = it.next();
                
                notaTexto = new Textbox();
                notaTexto.setCols(30);
                notaTexto.setValue(planificacionDetalle.getDescripcion()); 
                notaTexto.setRows(2);
                row.appendChild(notaTexto);
            }
            row.setParent(this);
        } else {
            row = new Row();
            for (int i = 0; i < 5; i++) {
                
                notaTexto = new Textbox();
                notaTexto.setCols(30);
                notaTexto.setRows(30);
                row.appendChild(notaTexto);
            }
            row.setParent(this);
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

    public String validar(List col, List<Notanotas> notas) {
//        Administrador adm = new Administrador();
//        for (int i = 0; i < col.size(); i++) {
//            Row object = (Row) col.get(i);
//            List labels = object.getChildren();
//            Matriculas ma = (Matriculas) adm.buscarClave(new Integer(((Label) labels.get(0)).getValue()), Matriculas.class);
//            //nota.setMatricula(new Matriculas(new Integer(((Label) vecDato.get(0)).getValue())));
//            for (int j = 2; j < labels.size(); j++) {
//                Decimalbox object1 = (Decimalbox) labels.get(j);
//                String formula = notas.get(j - 2).getSistema().getFormula(); // EN CASO DE FORMULA
//                formula = formula.replace("no", "nota.getNo"); //EN CASO DE QUE HAYA FORMULA
//                String toda = notas.get(j - 2).getNota() + "";
//
//                toda = toda.substring(1, toda.length());
//                String vaNota = object1.getValue().toString();
//                Double aCargar = 0.0;
//                if (vaNota.equals("")) {
//                    aCargar = 0.0;
//                } else {
//                    aCargar = new Double(vaNota);
//                }
//                if ((aCargar > notas.get(j - 2).getSistema().getNotalimite() || aCargar < 0) && formula.isEmpty()) {
//                    return "NOTA FUERA DE RANGO EN PERIODO: " + notas.get(j - 2).getSistema().getAbreviatura() + "  NOTA: " + aCargar + " "
//                            + " ESTUDIANTE: " + ma.getEstudiante().getApellido() + " " + ma.getEstudiante().getNombre() + "  ";
//                }
//            }
//        }
        return "";
    }

    @SuppressWarnings("static-access")
    public String guardar(List col, Cursos curso, MateriaProfesor materia, Sistemacalificacion sistema) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<general> listadoEnviar = new ArrayList();
        try {
            System.out.println("INICIO EN: " + new Date());
            Interpreter inter = new Interpreter();
            inter.eval(redon);
            inter.eval(prom1);
            inter.eval(equival);
            Administrador adm = new Administrador();
            List<Sistemaevaluacion> notas = adm.query("Select o from Sistemaevaluacion as o  "
                    + "where o.sistemacalificacion.codigosis = '" + sistema.getCodigosis() + "' order by o.orden ");
//            String valida = validar(col, notas);
//            if (!valida.isEmpty()) {
//                return valida;
//            }


            secuencial sec = new secuencial();

            List codigosNotas = adm.query("Select o.codigonot from Notasevaluacion as o "
                    + " where o.matricula.curso.codigocur = '" + curso.getCodigocur() + "' "
                    + " and o.materia.codigo = '" + materia.getMateria().getCodigo() + "'  "
                    + " and o.sistemacalificacion.codigosis = '" + sistema.getCodigosis() + "'  ");
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
            for (int i = 0; i < col.size(); i++) {
                try {
                    Row object = (Row) col.get(i);
                    Notasevaluacion nota = new Notasevaluacion();
                    nota.setCodigonot(sec.generarClave());
                    List labels = object.getChildren();
                    nota.setMatricula(new Matriculas(new Integer(((Label) labels.get(0)).getValue())));
                    nota.setMateria(materia.getMateria());
                    nota.setFecha(new Date());
                    nota.setSistemacalificacion(sistema);
                    nota.setOrden(materia.getOrden());
                    nota.setCuantitativa(materia.getCuantitativa());
                    nota.setSeimprime(materia.getSeimprime());
                    inter.set("nota", nota);
                    BigDecimal notaFinal = new BigDecimal("0");
                    String ultimaNota = "";
                    for (int j = 2; j < labels.size(); j++) {
                        //Decimalbox object1
                        BigDecimal object1 = new BigDecimal("0");
                        if (labels.get(j) instanceof Decimalbox) {
                            object1 = ((Decimalbox) labels.get(j)).getValue();
                        } else if (labels.get(j) instanceof Listbox) {
                            object1 = new BigDecimal(((Equivalencias) ((Listbox) labels.get(j)).getSelectedItem().getValue()).getNota());
                        }
                        if (object1 == null) {
                            object1 = new BigDecimal(0);
                        }
                        //Decimalbox object1 = (Decimalbox) labels.get(j);
                        String formula = notas.get(j - 2).getFormula(); // EN CASO DE FORMULA
                        formula = formula.replace("no", "nota.getNo"); //EN CASO DE QUE HAYA FORMULA
                        String toda = notas.get(j - 2).getNombre() + "";
                        String uno = toda.substring(0, 1).toUpperCase();
                        toda = toda.substring(1, toda.length());
                        String vaNota = object1.toString();
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
                        ultimaNota = (uno + toda) + "";
                    }

                    try {
                        notaFinal = new BigDecimal((Double) inter.eval("nota.get" + ultimaNota + "()"));
                        general gen = new general();
                        gen.setMatricula(nota.getMatricula());
                        gen.setSistema(sistema);
                        gen.setNota(notaFinal);
                        listadoEnviar.add(gen);
                    } catch (Exception nn) {
                        System.out.println("error en nn: " + nn);
                        Logger.getLogger(Planificacion.class.getName()).log(Level.SEVERE, null, nn);
                    }
                    nota = (Notasevaluacion) inter.get("nota");
                    adm.guardar(nota);

                } catch (EvalError ex) {
                    Logger.getLogger(Planificacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if (codigosNotasString.length() > 0) {
                String del = "Delete from Notasevaluacion where codigonot in  (" + codigosNotasString + ")  ";
                adm.ejecutaSql(del);
            }
            //recalculoNotas(materia, curso);
            System.out.println("FINALIZO EN: " + new Date());

            System.out.println("GUARDO EN NOTAS ");
            //TENGO QUE SETEAR LAS NOTAS ANTES DE GUARDAR
            Rows fil = buscarFilas(curso, materia, sistema, listadoEnviar);


            guardarActualizar(fil.getChildren(), curso, materia);
            listadoEnviar = null;
            fil = null;
            col = null;
            curso = null;
            materia = null;
            sistema = null;
            System.gc();

            return "ok";
        } catch (EvalError ex) {
            Logger.getLogger(Planificacion.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en:  " + ex;
        }


    }

    public BigDecimal buscarCoincidencia(List<general> general, Matriculas mat, Sistemacalificacion sis) {
        for (Iterator<general> it = general.iterator(); it.hasNext();) {
            general object = it.next();
            if (object.getMatricula().getCodigomat().equals(mat.getCodigomat()) && object.getSistema().getCodigosis().equals(sis.getCodigosis())) {
                return object.getNota();
            }
        }
        return null;
    }

    /**
     * BUSCO LAS NOTAS PARA VOLVERLAS A GUARDAR
     *
     * @param curso
     * @param materia
     */
    public Rows buscarFilas(Cursos curso, MateriaProfesor materia, Sistemacalificacion sistemaLlega, List<general> notasLlega) {
        System.out.println("TOP INI; " + new Date());
        int tamanio = 0;

        Rows filas = new Rows();

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
        getChildren().clear();
        Decimalbox notaTexto = null;
        Label label3 = null;

        String q = "Select distinct matriculas.codigomat,concat(estudiantes.apellido,' ',estudiantes.nombre,'[',matriculas.estado,']'), " + query + "  from matriculas "
                + "left join  estudiantes on matriculas.estudiante = estudiantes.codigoest "
                + "left join notas on matriculas.codigomat = notas.matricula "
                + "and notas.materia = '" + materia.getMateria().getCodigo() + "' and notas.disciplina = false  "
                + "where matriculas.curso = '" + curso.getCodigocur() + "'  and (matriculas.estado = 'Matriculado' or matriculas.estado  = 'Recibir Pase'  or matriculas.estado  = 'Emitir Pase'  or matriculas.estado  = 'Retirado' ) "
                + "order by estudiantes.apellido";
        System.out.println("" + q);
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
        String codigomat = "";
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Vector vec = (Vector) itna.next();
            row = new Row();
            Boolean deshabilitado = false;
            String color = "black";

            //if (materia.getCuantitativa()) {
            for (int j = 0; j < vec.size(); j++) {
                Object dos = vec.get(j);
                notaTexto = new Decimalbox();
                notaTexto.setConstraint("no negative: No se permiten datos en NEGATIVO");
//                    notaTexto.setTabindex(j);
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
                    codigomat = label3.getValue();
                    row.appendChild(label3);
                } else if (j == 1) {
                    row.appendChild(label3);
                } else {
                    int dat = j - 2;
                    BigDecimal notaNueva = buscarCoincidencia(notasLlega, new Matriculas(new Integer(codigomat)), ((Sistemacalificacion) sistemas.get(dat)));
                    if (notaNueva != null) {
                        notaTexto.setValue(notaNueva);
                    }
                    row.appendChild(notaTexto);

                }

                //row.appendChild(label);
//                                 System.out.print(","+dos);
            }


            row.setParent(filas);
        }
        nativo = null;
        System.out.println("TOP FIN; " + new Date());
        return filas;
    }

    public String guardarActualizar(List col, Cursos curso, MateriaProfesor materia) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        try {
            System.out.println("INICIO EN: " + new Date());
            Interpreter inter = new Interpreter();
            inter.eval(redon);
            inter.eval(prom1);
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

    /**
     * GUARDAR EN CASO DE QUE SEA LA MATERIA CUALITATIVA
     *
     * @param col
     * @param curso
     * @param materia
     * @return
     */
    public String guardarCualitativas(List col, Cursos curso, MateriaProfesor materia) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        try {
            System.out.println("INICIO EN: " + new Date());
            Interpreter inter = new Interpreter();
            inter.eval(redon);
            inter.eval(prom1);
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
                    Logger.getLogger(Planificacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            recalculoNotas(materia, curso);
            System.out.println("FINALIZO EN: " + new Date());
            return "ok";
        } catch (EvalError ex) {
            Logger.getLogger(Planificacion.class.getName()).log(Level.SEVERE, null, ex);
            return "Error en:  " + ex;
        }


    }

    public void recalculoNotas(MateriaProfesor materia, Cursos curso) {
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        Administrador adm = new Administrador();

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
                            Logger.getLogger(Planificacion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
//            System.out.println(""+materia);
//            System.out.println(""+curso);
//            System.out.println(""+(Vector) inter.get("VEC17"));
//            System.out.println(""+(Vector) inter.get("VEC18"));
//          System.out.println(""+aguardar);
                } catch (EvalError ex) {
                    Logger.getLogger(Planificacion.class.getName()).log(Level.SEVERE, null, ex);
                }

//            System.out.println(""+materia);
//            System.out.println(""+curso);
//            System.out.println(""+(Vector) inter.get("VEC17"));
//            System.out.println(""+(Vector) inter.get("VEC18"));
//          System.out.println(""+aguardar);

            } catch (EvalError ex) {
                Logger.getLogger(Planificacion.class.getName()).log(Level.SEVERE, null, ex);
            }
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

    public void calcularDisciplina(Cursos curso0) {
        String tipo = "INGRESADA";
        Administrador adm = new Administrador();
        Session ses = Sessions.getCurrent();
        Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<ParametrosGlobales> para = adm.query("Select o from ParametrosGlobales as o "
                + "where o.variable = 'TIPODISCIPLINA' "
                + "and o.periodo.codigoper = '" + curso0.getPeriodo().getCodigoper() + "'");
        List<ParametrosGlobales> parametrosGlobales = adm.query("Select o from ParametrosGlobales as o "
                + "where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        Double decimales = regresaVariableParametrosDecimal("DECIMALESDIS", parametrosGlobales);
        if (para.size() > 0) {
            ParametrosGlobales param = para.get(0);
            tipo = param.getCvalor();
        }

        List<Notanotas> notas = adm.query("Select o from Notanotas as o "
                + "where o.sistema.periodo.codigoper = '" + periodo.getCodigoper() + "' "
                + "and o.sistema.esdisciplina = true "
                + "order by o.sistema.orden ");
        String query = "";
        if (tipo.contains("INGRESADA")) {
            return;
        }
        //CARGO EL QUERY PARA SELECCIONAR
        for (Notanotas notass : notas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        List<Cursos> listaLlega = new ArrayList<Cursos>();

        if (curso0.getCodigocur().equals(-2)) {
            listaLlega = adm.query("Select o from Cursos as o where o.periodo.codigoper = '" + periodo.getCodigoper() + "' ");
        } else {
            listaLlega.add(curso0);
        }
        for (Iterator<Cursos> ita = listaLlega.iterator(); ita.hasNext();) {
            Cursos ActualCurso = ita.next();
            List<DisciplinaModificada> existeModificaciones = adm.query("Select o from DisciplinaModificada as o "
                    + "where o.matricula.curso.codigocur = '" + ActualCurso.getCodigocur() + "' ");

            List<MateriaProfesor> maprofes = adm.query("Select o from MateriaProfesor as o "
                    + "where o.curso.codigocur = '" + ActualCurso.getCodigocur() + "' "
                    + "and o.materia.codigo > 1 and o.ministerio = true and o.opcional = true ");
            if (maprofes.size() > 0) {

                String formu = "";

                for (Iterator<MateriaProfesor> it = maprofes.iterator(); it.hasNext();) {
                    MateriaProfesor acaMater = it.next();
                    formu += "N" + acaMater.getMateria().getCodigo() + ",";
                }

                formu = formu.substring(0, formu.length() - 1);
                if (tipo.contains("MITAD")) { // EL INSPECTOR MEDIA NOTA Y EL PROMEDIO DE LOS PROFES LA OTRA MITDA
                    formu = "promedio(promedio(" + formu + ") , " + "(N1==null || N1==0 ?" + notaDisciplina + ":N1))";
                } else if (tipo.contains("PROMEDIO")) {//PROMEDIO DE TODAS LAS NOTAS INCLUIDA LA NOTA DEL INSPECTOR
                    formu = " promedio(" + formu + " , (N1==null || N1==0 ?" + notaDisciplina + ":N1)" + ") ";
                    //formu = "(((" + formu + ")/" + maprofes.size() + ") +" + "(N1==null || N1==0 ?" + notaDisciplina + ":N1))/2";
                    //formu = "(((" + formu + ")+" + "(N1==null || N1==0 ?" + notaDisciplina + ":N1))/(" + maprofes.size() + "))";
                } else if (tipo.contains("PROMEDIADA")) {// PROMEDIA SOLO LAS NTOAS DE LOS PROFESORES
                    formu = "promedio(" + formu + ")";
                    //formu = "(((" + formu + ")/" + maprofes.size() + ") +" + "(N1==null || N1==0 ?" + notaDisciplina + ":N1))/2";
                    //formu = "(((" + formu + ")+" + "(N1==null || N1==0 ?" + notaDisciplina + ":N1))/(" + maprofes.size() + "))";
                } else if (tipo.contains("SUMATORIA")) {//PROMEDIO DE PROFESORES + PROMEDIO DE DE LA NOTA INGRESADA INSPECCION
                    formu = " promedio(" + formu + ") +  (N1==null || N1==0 ?" + notaDisciplina + ":N1)";
                }
//                 else if (tipo.contains("PROMEDIADA")) {//SUMA SOLO LAS MATERIAS Y LAS DIVIDE PARA LOS QUE INGRESARON
//                    coll.setFinali((promProfesor) / lista.size());
//                } 
                List<Global> materias = adm.query("Select o from Global as o "
                        + "where o.codigo in (Select t.materia.codigo from MateriaProfesor as t"
                        + " where t.curso.codigocur = '" + ActualCurso.getCodigocur() + "' and t.opcional = true  ) "
                        + " ");
//                boolean siHayInspector = false;
//                for (Iterator<Global> it = materias.iterator(); it.hasNext();) {
//                    Global global = it.next();
//                    if(global.getCodigo().equals(new Integer(1))){
//                        siHayInspector = true;
//                    }
//                }
//                if(siHayInspector==false){
//                    Global glb = new Global(1);
//                    glb.setDescripcion("DISCIPLINA INSPECTOR");
//                    glb.setGrupo("MAT");
//                    materias.add(glb); 
//                }
                String formula = formu;

                List<Global> Nmaterias = new ArrayList<Global>();
                ArrayList vectors = new ArrayList();
                Global global2 = new Global(1);
                Nmaterias.add(global2);
                vectors.add("VEC1");
                String ma = "";
                for (Iterator<Global> it = materias.iterator(); it.hasNext();) {
                    Global global = it.next();
                    if (formula.contains("N" + global.getCodigo())) {
                        Nmaterias.add(global);
                        ma += "VEC" + global.getCodigo() + ",";
                        vectors.add("VEC" + global.getCodigo());
                    }
                }



                if (ma.length() > 0) {
                    ma = ma.substring(0, ma.length() - 1);
                }
                Interpreter inter = new Interpreter();
                try {
                    //1 DISCIPLINA INSPECTOR
                    //0 disciplina

                    for (Iterator<Global> it = Nmaterias.iterator(); it.hasNext();) {
                        Global global = it.next();
                        String q = "Select matriculas.codigomat, " + query + "  from matriculas  "
                                + "left join  estudiantes  on matriculas.estudiante = estudiantes.codigoest "
                                + "left join notas on matriculas.codigomat = notas.matricula "
                                + "and notas.materia = '" + global.getCodigo() + "' "
                                + "and notas.disciplina = true and notas.promedia = true "
                                + "where matriculas.curso = '" + ActualCurso.getCodigocur() + "' "
                                + "and matriculas.estado in ('Matriculado','Recibir Pase','Retirado') "
                                + "order by estudiantes.apellido";
//                        System.out.println("" + q);
                        List nativo = adm.queryNativo(q);
//                        System.out.println(""+nativo);
//                        System.out.println("RESULTADO VEC:" + global.getCodigo() + "=" + nativo);
                        inter.set("VEC" + global.getCodigo(), nativo);
                    }
                    String vector1 = (String) vectors.get(0);
//                    System.out.println("VECTOR 1: "+vector1);
                    inter.eval(redon);
                    inter.eval(prom1);
                    inter.eval(equival);
                    inter.eval("int tamanio1 =  " + vector1 + ".size(); " +//OJOSSS
                            "int tamanio2 = ((Vector)" + vector1 + ".get(0)).size(); " +//OJOSSS
                            "Vector calculado = new Vector(); ");
                    String hola = ":::::::::::::::::::::::::::::::::.:";
                    inter.eval("System.out.print(tamanio1);");
                    inter.eval("System.out.print(tamanio2);");
                    inter.eval("for (int k = 0; k < tamanio1; k++) {"
                            + "                Vector resultado = new Vector();"
                            + "                Vector object = (Vector) " + vector1 + ".get(k);" +//OJOSSS
                            "                for (int i = 0; i < tamanio2; i++) {"
                            + "                    if (i == 0) {"
                            + "                        Integer cod = (object.get(i) != null ? ((Integer) object.get(i)) : 0);"
                            + "                        resultado.add(cod);"
                            + "                    } else {"
                            + "                        resultado.add(0.0);"
                            + "                    }"
                            + "                }"
                            + "                calculado.add(resultado); "
                            + "                "
                            + "                      "
                            + "            }");
                    String asumar = "";
                    String aconvertir = "";
                    for (Iterator it = vectors.iterator(); it.hasNext();) {
                        String object = (String) it.next();
                        String codigo = object.replace("VEC", "");
                        asumar += "Vector object" + codigo + " = (Vector) " + object + ".get(k); ";
                        aconvertir += "Double N" + codigo + " = (object" + codigo + ".get(i) != null ? ((Double) object" + codigo + ".get(i)) : 0.0);";
                    }
//                    System.out.println("-------------" + aconvertir);
//                    System.out.println("-------------" + formula);
                    try {
                        Object obtenido = inter.eval("calculado.get(0);");
//                        N3+N4+N6+N7+N8+N12+N14+N15+N16+N18+N19+N20+N36+N32+N17)/15+(N1==NULL || N1==0 ?5.0:N1))
//                        System.out.println("N1:          " + obtenido);
                    } catch (Exception e) {
                        System.out.println("" + e);
                    }


                    inter.eval(" for (int k = 0; k < " + vector1 + ".size(); k++) {"
                            + "                     " + asumar
                            + "                Vector resultado = (Vector) calculado.get(k);"
                            + "                "
                            + "                for (int i = 1; i < resultado.size(); i++) { "
                            + "                 " + aconvertir + " "
                            + "                        resultado.set(i, " + formula + ");"
                            + "                   "
                            + "                }"
                            + "                calculado.set(k, resultado);   "
                            + "             "
                            + "            }    ");
                    Vector aguardar = (Vector) inter.get("calculado");

                    //INICIO PROCESO DE GUARDAR LAS NOTAS
                    secuencial sec = new secuencial();
//
                    for (int i = 0; i < aguardar.size(); i++) {
                        try {
                            //Row object = (Row) aguardar.get(i);
                            Vector vecDato = (Vector) aguardar.get(i);
                            Notas nota = new Notas();
                            nota.setCodigonot(sec.generarClave());
                            nota.setMatricula(new Matriculas(new Integer(((Integer) vecDato.get(0)))));
                            nota.setMateria(new Global(0));

                            nota.setFecha(new Date());
                            inter.set("nota", nota);
                            for (int j = 1; j < vecDato.size(); j++) {
                                Double object1 = (Double) vecDato.get(j);
                                String toda = notas.get(j - 1).getNota() + "";
                                Sistemacalificacion sisPregunta = notas.get(j - 1).getSistema();

                                String uno = toda.substring(0, 1).toUpperCase();
                                toda = toda.substring(1, toda.length());

                                inter.eval("nota.set" + (uno + toda) + "(" + redondear(new Double(object1), decimales.intValue()) + ");");
                                if (existeModificaciones.size() > 0) {
                                    //DisciplinaModificada disBuscada = buscarModificada(existeModificaciones, sisPregunta, new Integer(((Integer) vecDato.get(0))));
//                                    if (disBuscada != null) {
//                                        Double valor = disBuscada.getNota();
//                                        inter.eval("nota.set" + (uno + toda) + "(" + redondear(valor, decimales.intValue()) + ");");
//                                    }
                                }
                            }
                            nota = (Notas) inter.get("nota");
                            nota.setCuantitativa(true);
                            nota.setOrden(100);
                            nota.setSeimprime(true);
                            nota.setPromedia(false);
                            nota.setDisciplina(true);
                            if (nota.getMateria().getCodigo().equals(0)) {
                                nota.setDisciplina(false);
                            }
                            String del = "Delete from Notas where  materia.codigo = '0' "
                                    + " and  matricula.codigomat = '" + nota.getMatricula().getCodigomat() + "'";
//                            System.out.println("" + del);
                            adm.ejecutaSql(del);

                            adm.guardar(nota);

                        } catch (EvalError ex) {

                            Logger.getLogger(Planificacion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                } catch (EvalError ex) {

                    Logger.getLogger(Planificacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void nuevaClave() {
        try {
            Administrador adm = new Administrador();
            String apellidos = "jadan";
            String nombres = "geovanny";
            String user = apellidos.substring(0, 2) + "JC" + nombres.substring(0, 2) + "";

//                System.out.println(user);
            List rs2 = adm.queryNativo("SELECT IF(max(usuario)is null,'" + user + "000', MAX(usuario)) FROM estudiantes "
                    + "where usuario like '%" + user + "%'  ");
            if (rs2.size() > 0) {
                String user2 = rs2.get(0).toString();
                Integer valor = new Integer(user2.substring(6)) + 1;
                String nuevo = String.format("%03d", valor);
                user = user2.substring(0, 4) + nuevo;
//                         stmt2.executeUpdate("Update aca_estudiantes " +
//                                "set est_usuario = '"+user+"', est_clave = '"+user+"' where est_codigo = '"+codigo+"' ");
            } else {
                user = user + "001";

            }

        } catch (Exception ex) {
            Logger.getLogger(Planificacion.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
