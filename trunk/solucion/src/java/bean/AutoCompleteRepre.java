package bean;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Representante;
import jcinform.procesos.Administrador;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.*;

/**
 * An example of auto-complete with combobox.
 *
 * @author tomyeh
 */
public class AutoCompleteRepre extends Combobox {
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
	public AutoCompleteRepre() {
	//	refresh("");
        adm = new Administrador();
	}
    public void llenar(){
        List<Representante> estu = adm.query("Select o from Representante as o ");
        ArrayList arreglito = new ArrayList();
        for (Iterator<Representante> it = estu.iterator(); it.hasNext();) {
            Representante estudiantes = it.next();
            arreglito.add(estudiantes);
        }
 
    }
	public AutoCompleteRepre(String value) {
		super(value); //it invokes setValue
	}

	public void setValue(String value) {
		super.setValue(value);
	//	refresh(value);
	}
	public void onChanging(InputEvent evt) {
if (!evt.isChangingBySelectBack()){
    String abuscar = evt.getValue();
    List<Representante> estu = new ArrayList<Representante>();
    if(abuscar.length()>1){
           estu = adm.query("Select o from Representante as o where o.apellidos like '%"+abuscar+"%'");
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
        for (Iterator<Representante> itEst = estu.iterator(); itEst.hasNext();) {
            Representante estudiantes = itEst.next();
				it = null;
            new Comboitem(estudiantes.getApellidos()+" "+ estudiantes.getNombres() +" ["+estudiantes.getCodigorep()+"]" ).setParent(this);
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
