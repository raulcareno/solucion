package bean;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import jcinform.persistencia.Estudiantes;
import jcinform.procesos.Administrador;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.*;

/**
 * An example of auto-complete with combobox.
 *
 * @author tomyeh
 */
public class AutoCompleteEstudiante extends Combobox {
   
	static {
 
	}
Administrador adm;
	public AutoCompleteEstudiante() {
	//	refresh("");
        adm = new Administrador();
	}
    public void llenar(){
        List<Estudiantes> estu = adm.query("Select o from Estudiantes as o ");
        ArrayList arreglito = new ArrayList();
        for (Iterator<Estudiantes> it = estu.iterator(); it.hasNext();) {
            Estudiantes estudiantes = it.next();
            arreglito.add(estudiantes);
        }
 
    }
	public AutoCompleteEstudiante(String value) {
		super(value); //it invokes setValue
	}

	public void setValue(String value) {
		super.setValue(value);
	//	refresh(value);
	}
	public void onChanging(InputEvent evt) {
if (!evt.isChangingBySelectBack()){
    String abuscar = evt.getValue();
    List<Estudiantes> estu = new ArrayList<Estudiantes>();
    if(abuscar.length()>1){
           estu = adm.query("Select o from Estudiantes as o where o.apellido like '"+abuscar+"%'",0,10);
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
        for (Iterator<Estudiantes> itEst = estu.iterator(); itEst.hasNext();) {
            Estudiantes estudiantes = itEst.next();
				it = null;
            Comboitem combo = new Comboitem(estudiantes.getApellido()+" "+ estudiantes.getNombre());
            combo.setDescription("Representante: "+estudiantes.getRepresentante().getApellidos()+" "+estudiantes.getRepresentante().getNombres()); 
            combo.setValue(estudiantes);
            combo.setParent(this);
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
