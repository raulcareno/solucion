package jcinform.bean;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import jcinform.conexion.Administrador;
import jcinform.persistencia.Series;
import jcinform.persistencia.Series;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.*;

public class autoCompletarSeries extends Combobox {

    static {
//		Arrays.sort(arreglo);
    }
    Administrador adm;

    public autoCompletarSeries() {
        //	refresh("");
        adm = new Administrador();

    }

    public autoCompletarSeries(String value) {
        super(value); //it invokes setValue
    }

    public void setValue(String value) {
        super.setValue(value);
        //	refresh(value);
    }

    public void onChanging(InputEvent evt) {
        if (!evt.isChangingBySelectBack()) {
            String abuscar = evt.getValue();
            List<Series> estu = new ArrayList<Series>();
            if (abuscar.length() > 1) {
                estu = adm.query("Select o from Series as o " + 
                        " where o.estado = 'C' and o.serie like '%"+abuscar+"%' " + 
                        " and o.serie not in (Select a.serie from Series as a where a.estado IN ( 'A','P','V') ) "
                        + "and  o.codigo not in (Select e.series.codigo from Seriesempleados as e where e.estado = true )  ",0,10);
            }

            for (int i = 0; i < getItems().size(); i++) {
                getItems().remove(i);

            }
            //getItems().size();
            Iterator it = getItems().iterator();
            while (it != null && it.hasNext()) {
                it.next();
                it.remove();
            }
            for (Iterator<Series> itEst = estu.iterator(); itEst.hasNext();) {
                Series estudiantes = itEst.next();
                it = null;
                Comboitem c = new Comboitem();
                c.setValue(estudiantes);
                c.setLabel(estudiantes.getSerie() + " EQ: " + estudiantes.getDetallecompra().getEquipos());
                c.setParent(this);
                
            }



        }
    }
    
 
    static String[] dictionary = {  "contradiction" };
    public static String[] getDirectory() {
        return dictionary;
    }
    /** Refresh comboitem based on the specified value.
     */
//	private void refresh(String val) {
//		int j = Arrays.binarySearch(arreglo, val);
//		if (j < 0)
//            j = -j-1;
//
//		Iterator it = getItems().iterator();
//		for (int cnt = 10; --cnt >= 0 && j < arreglo.length && arreglo[j].startsWith(val); ++j) {
//			if (it != null && it.hasNext()) {
//				((Comboitem)it.next()).s.setLabel(arreglo[j]);
//			} else {
//				it = null;
//				new Comboitem(arreglo[j]).setParent(this);
//			}
//		}
//
//		while (it != null && it.hasNext()) {
//			it.next();
//			it.remove();
//		}
//	}
}
