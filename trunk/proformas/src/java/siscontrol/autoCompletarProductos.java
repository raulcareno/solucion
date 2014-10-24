package siscontrol;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.*;
import siscontrol.cnx.Administrador;

public class autoCompletarProductos extends Combobox {

    static {
//		Arrays.sort(arreglo);
    }
    Administrador adm;
    Empresa empresa;

    public autoCompletarProductos() {
        //	refresh("");
        adm = new Administrador();
        Session a = Sessions.getCurrent();
        empresa = (Empresa) a.getAttribute("empresa");


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
    static {
//		Arrays.sort(arreglo);
    }

    public autoCompletarProductos(String value) {

        super(value); //it invokes setValue
        System.out.println("se ejecuto: " + value);
    }

    public void setValue(String value) {
        super.setValue(value);
        //	refresh(value);
    }

    public void onChanging(InputEvent evt) {
        if (!evt.isChangingBySelectBack()) {
            try {
                
            
            String abuscar = evt.getValue();
            String query = "";
            List<Productos> estu = new ArrayList<Productos>();


            if (abuscar.length() > 0) {
                query = "Select o from Productos as o "
                        + " where o.nombre like '%" + abuscar + "%' "
                        + "   "
                        + "order by o.nombre";

            }
            if (abuscar.toUpperCase().contains(empresa.getCode().toUpperCase())) {
                query = "Select o from Productos as o "
                        + " where o.code like '%" + abuscar + "%' "
                        + "   "
                        + "order by o.code";
            }
            estu = adm.query(query);

            for (int i = 0; i < getItems().size(); i++) {
                getItems().remove(i);

            }
            //getItems().size();
            Iterator it = getItems().iterator();
            while (it != null && it.hasNext()) {
                it.next();
                it.remove();
            }
            if (estu.size() > 0) {
                for (Iterator<Productos> itEst = estu.iterator(); itEst.hasNext();) {
                    Productos estudiantes = itEst.next();
                    it = null;
                    //new Comboitem(estudiantes.getApellidos() + " " + estudiantes.getNombres() + " [" + estudiantes.getCodigo() + "]").setParent(this);
                    Comboitem c = new Comboitem();
                    c.setValue(estudiantes);
                    c.setLabel("["+estudiantes.getCode() + "] " + (estudiantes.getNombre()) + "");


                    if (estudiantes.getDescripcion().length() > 100) {
                        c.setDescription(estudiantes.getDescripcion().substring(0, 90) + "...");
                    } else {
                        c.setDescription(estudiantes.getDescripcion());
                    }

                    c.setParent(this);

                }
            }


} catch (Exception e) {
                //System.out.println("");
            }
        } else {
        }
    }
    static String[] dictionary = {"contradiction", "zodiac"};

    public static String[] getDirectory() {
        return dictionary;
    }
    /**
     * Refresh comboitem based on the specified value.
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
