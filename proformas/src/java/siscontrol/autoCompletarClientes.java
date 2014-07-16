package siscontrol;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
 
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.*;
import siscontrol.cnx.Administrador;

public class autoCompletarClientes extends Combobox {

    static {
//		Arrays.sort(arreglo);
    }
    Administrador adm;

    public autoCompletarClientes() {
        //	refresh("");
        adm = new Administrador();

    }

//    public void llenar() {
//        List<Clientes> estu = adm.query("Select o from Clientes as o ");
//        ArrayList arreglito = new ArrayList();
//        for (Iterator<Clientes> it = estu.iterator(); it.hasNext();) {
//            Clientes estudiantes = it.next();
//            arreglito.add(estudiantes);
//        }
//
//    }
    public autoCompletarClientes(String value) {
        super(value); //it invokes setValue
    }

    public void setValue(String value) {
        super.setValue(value);
        //	refresh(value);
    }

    public void onChanging(InputEvent evt) {
        if (!evt.isChangingBySelectBack()) {
            String abuscar = evt.getValue();
            List<Clientes> estu = new ArrayList<Clientes>();
            if (abuscar.length() > 1) {
                estu = adm.query("Select o from Clientes as o "
                        + "where o.nombres like '%" + abuscar + "%' "
                        + "OR "
                        + "o.razonsocial like '%" + abuscar + "%' "
                        + "order by o.nombres, o.razonsocial ",0,10);
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
            for (Iterator<Clientes> itEst = estu.iterator(); itEst.hasNext();) {
                Clientes estudiantes = itEst.next();
                it = null;
                //new Comboitem(estudiantes.getApellidos() + " " + estudiantes.getNombres() + " [" + estudiantes.getCodigo() + "]").setParent(this);
                Comboitem c = new Comboitem();
                c.setValue(estudiantes);
                c.setLabel(estudiantes.getNombres());
                if(estudiantes.getRazonsocial() != null && !estudiantes.getRazonsocial().isEmpty()){
                    c.setLabel(estudiantes.getRazonsocial() + " | ["+estudiantes.getNombres()+"]");
                }
                    
                c.setParent(this);
                
            }



        }
    }
    
 
    static String[] dictionary = {  "contradiction",   "zodiac" };
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
