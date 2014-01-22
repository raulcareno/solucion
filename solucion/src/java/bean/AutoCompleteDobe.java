package bean;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import jcinform.persistencia.DobeEstudiantes;
import jcinform.persistencia.Estudiantes;
import jcinform.procesos.Administrador;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.*;

public class AutoCompleteDobe extends Combobox {

    static {
//		Arrays.sort(arreglo);
    }
    Administrador adm;

    public AutoCompleteDobe() {
        //	refresh("");
        adm = new Administrador();
    }

    public void llenar() {
        List<Estudiantes> estu = adm.query("Select o from Estudiantes as o ");
        ArrayList arreglito = new ArrayList();
        for (Iterator<Estudiantes> it = estu.iterator(); it.hasNext();) {
            Estudiantes estudiantes = it.next();
            arreglito.add(estudiantes);
        }

    }

    public AutoCompleteDobe(String value) {
        super(value); //it invokes setValue
    }

    public void setValue(String value) {
        super.setValue(value);
        //	refresh(value);
    }

    public void onChanging(InputEvent evt) {
        if (!evt.isChangingBySelectBack()) {
            String abuscar = evt.getValue();
            List<DobeEstudiantes> estu = new ArrayList<DobeEstudiantes>();
            if (abuscar.length() > 1) {
                estu = adm.query("Select o from DobeEstudiantes as o where o.apellidos like '%" + abuscar + "%'",0,12);
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
            for (Iterator<DobeEstudiantes> itEst = estu.iterator(); itEst.hasNext();) {
                DobeEstudiantes estudiantes = itEst.next();
                it = null;
                Comboitem cm = new Comboitem(estudiantes.getApellidos() + " " + estudiantes.getNombres() + " [" + estudiantes.getCodigo() + "]");
                cm.setValue(estudiantes);
                cm.setParent(this);
            }



        }
    }
}
