package jcinform.bean;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import jcinform.conexion.Administrador;
import jcinform.persistencia.Empleados;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.*;

public class autoCompletarEmpleados extends Combobox {

    static {
//		Arrays.sort(arreglo);
    }
    Administrador adm;

    public autoCompletarEmpleados() {
        //	refresh("");
        adm = new Administrador();

    }

//    public void llenar() {
//        List<Empleados> estu = adm.query("Select o from Empleados as o ");
//        ArrayList arreglito = new ArrayList();
//        for (Iterator<Empleados> it = estu.iterator(); it.hasNext();) {
//            Empleados estudiantes = it.next();
//            arreglito.add(estudiantes);
//        }
//
//    }

    public autoCompletarEmpleados(String value) {
        super(value); //it invokes setValue
    }

    public void setValue(String value) {
        super.setValue(value);
        //	refresh(value);
    }

    public void onChanging(InputEvent evt) {
        Combobox combo = new Combobox();
        combo.getSelectedItemApi().getAttribute("id");
        if (!evt.isChangingBySelectBack()) {
            String abuscar = evt.getValue();
            List<Empleados> estu = new ArrayList<Empleados>();
            if (abuscar.length() > 1) {
                estu = adm.query("Select o from Empleados as o where o.apellidos like '%" + abuscar + "%'");
            }
            Object obj = new Integer(PAGE_SCOPE);
            
            for (int i = 0; i < getItems().size(); i++) {
                getItems().remove(i);

            }
            //getItems().size();
            Iterator it = getItems().iterator();
            while (it != null && it.hasNext()) {
                it.next();
                it.remove();
            }
            for (Iterator<Empleados> itEst = estu.iterator(); itEst.hasNext();) {
                Empleados estudiantes = itEst.next();
                it = null;
                new Comboitem(estudiantes.getApellidos() + " " + estudiantes.getNombres() + " [" + estudiantes.getCodigo() + "]").setParent(this);
                Comboitem c = new Comboitem();
                
                c.setValue(obj); 
                c.setValue(estu);
            }



        }
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
