package bean;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Periodo;
import jcinform.procesos.Administrador;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.*;

/**
 * An example of auto-complete with combobox.
 *
 * @author tomyeh
 */
public class AutoCompleteMatriculados extends Combobox {
  //  static int n =0;
//	private static Object[] arreglo = new Object[n];
    /*{ //alphabetic order
		"abacus", "accuracy", "acuity", "adage", "afar", "after", "apple",
		"bible", "bird", "bingle", "blog",  "cabane", "cape", "cease", "cedar",
		"dacron", "defacto", "definable", "deluxe", 		"each", "eager", "effect", "efficacy",
		"far", "far from",		"girl", "gigantean", "giant",		"home", "honest", "huge",
		"information", "inner",		"jump", "jungle", "jungle fever",		"kaka", "kale", "kame",		"lamella", "lane", "lemma",
		"master", "maxima", "music",		"nerve", "new", "number",
		"omega", "opera", 		"pea", "peace", "peaceful",		"rock", "sound", "spread", "student", "super", "tea", "teacher",
		"unit", "universe", 	"vector", "victory",		"wake", "wee", "weak",  "xeme", 		"yea", "yellow",
		"zebra", "zk",
	};*/
	static {
//		Arrays.sort(arreglo);
	}
Administrador adm;
Session ses = Sessions.getCurrent();
Periodo periodo = (Periodo) ses.getAttribute("periodo");
	public AutoCompleteMatriculados() {
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
	public AutoCompleteMatriculados(String value) {
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
        
           estu = adm.query("Select o.estudiante from Matriculas as o where o.estudiante.apellido like '%"+abuscar+"%' "
                   + " and o.curso.periodo.codigoper = '"+periodo.getCodigoper()+"'  ");
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
            new Comboitem(estudiantes.getApellido()+" "+ estudiantes.getNombre() +" ["+estudiantes.getCodigoest()+"]" ).setParent(this);
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
