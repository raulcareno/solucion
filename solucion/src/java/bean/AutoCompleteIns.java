package bean;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import jcinform.persistencia.Inscripciones;
import jcinform.persistencia.Periodo;
import jcinform.persistencia.Inscripciones;
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
public class AutoCompleteIns extends Combobox {
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
	public AutoCompleteIns() {
	//	refresh("");
        adm = new Administrador();
	}
    public void llenar(){
         Session ses = Sessions.getCurrent();
         Periodo periodo = (Periodo) ses.getAttribute("periodo");
        List<Inscripciones> estu = adm.query("Select o from Inscripciones as o where o.periodo = '"+ periodo.getCodigoper() +"'",0,20);
        ArrayList arreglito = new ArrayList();
        for (Iterator<Inscripciones> it = estu.iterator(); it.hasNext();) {
            Inscripciones estudiantes = it.next();
            arreglito.add(estudiantes);
        }
 
    }
	public AutoCompleteIns(String value) {
		super(value); //it invokes setValue
	}

	public void setValue(String value) {
		super.setValue(value);
	//	refresh(value);
	}
	public void onChanging(InputEvent evt) {
if (!evt.isChangingBySelectBack()){
    String abuscar = evt.getValue();
    Session ses = Sessions.getCurrent();
         Periodo periodo = (Periodo) ses.getAttribute("periodo");
    List<Inscripciones> estu = new ArrayList<Inscripciones>();
    if(abuscar.length()>1){
           estu = adm.query("Select o from Inscripciones as o where o.apellido like '%"+abuscar+"%' and o.periodo = '"+ periodo.getCodigoper() +"' ");
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
        for (Iterator<Inscripciones> itEst = estu.iterator(); itEst.hasNext();) {
            Inscripciones estudiantes = itEst.next();
				it = null;
            new Comboitem(estudiantes.getApellido()+" "+ estudiantes.getNombre() +" ["+estudiantes.getCodigoest()+"]" ).setParent(this);
        }
      
    
         
                }
	}
 
}
