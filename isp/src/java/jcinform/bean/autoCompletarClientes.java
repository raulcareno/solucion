package jcinform.bean;

import java.util.ArrayList;
import java.util.Iterator;

import java.util.List;
import jcinform.conexion.Administrador;
import jcinform.persistencia.Clientes;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.*;

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
                        + "where o.apellidos like '%" + abuscar + "%' order by o.apellidos ",0,10);
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
                c.setLabel(estudiantes.getApellidos() + " " + estudiantes.getNombres());
                c.setParent(this);
                
            }



        }
    }
    
 
    static String[] dictionary = {  "contradiction", "contradictory", "contraposition", "contravene", "contribution", "contributor", "contrite", "contrivance",
            "contrive", "control", "controller", "contumacious", "contumacy", "contumely", "contuse", "contusion", "conundrum", "convalesce", "convalescence", "convalescent", "convene",
            "convenience", "conventional", "converge", "convergent", "conversant", "conversion", "convertible", "convex", "conveyance", "convivial", "convoluted", "convolution", "convolve", "convoy",
            "convulse", "convulsion", "copious", "coquette", "cornice", "cornucopia", "corollary", "coronation", "coronet", "corporal", "corporate", "corporeal", "corps", "corpse", "corpulent",
            "corpuscle", "correlate", "correlation", "correlative", "corrigible", "corroborate", "corroboration", "corrode", "corrosion", "corrosive", "corrugated", "corruptible", "corruption",
            "cosmetic", "cosmic", "cosmogony", "cosmography", "cosmology", "cosmopolitan", "cosmopolitanism", "cosmos", "counseling", "counselor", "countenance", "counteract", "counterbalance",
            "countercharge", "counter-claim", "counterfeit", "counterpart", "countervail", "counting-house", "countless", "countryman", "courageous", "course", "courser", "courtesy", "covenant",
            "covert", "covey", "cower", "coxswain", "crag", "cranium", "crass", "craven", "craving", "creak", "creamery", "creamy", "creature", "credence", "credible", "credulous", "creed",
            "crematory", "crevasse", "crevice", "criterion", "critique", "crockery", "crucible", "crusade", "crustacean", "crustaceous", "cryptic", "cryptogram", "crystallize", "cudgel", "culinary",
            "cull", "culminate", "culpable", "culprit", "culvert", "cupidity", "curable", "curative", "curator", "curio", "curl", "cursive", "cursory", "curt", "curtail", "curtsy", "cycloid",
            "zenith", "zephyr", "zero", "zk", "zodiac" };
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
