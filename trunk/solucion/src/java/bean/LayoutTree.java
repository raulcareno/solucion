package bean;

import java.util.Iterator;
import java.util.List;
import org.zkoss.zk.ui.*;
import org.zkoss.zk.ui.ext.AfterCompose;
import org.zkoss.zul.*;

/**
 * The Table-of-Content tree on the left.
 *
 * @author jumperchen
 */
public class LayoutTree extends Tree implements AfterCompose {
	public LayoutTree() {
	}
//	public void onSelect() {
//         Treeitem item = getSelectedItem();
//
//         String menu = item.getId();
//         if(menu.contains("ss")){
//             if(item != null) {
//                    Include inc = (Include)getSpaceOwner().getFellow("xcontents");
//                    inc.setSrc(null);
//                    inc.setSrc("/blanco.zul");
//
//
//                }
//             return;
//         }
//         Permisos per = new Permisos();
//         try{
//         if(per.verificarPermiso(menu, "Ingresar")){
//                if (item != null) {
//                    Include inc = (Include)getSpaceOwner().getFellow("xcontents");
//                    inc.setSrc(null);inc.setSrc((String)item.getValue());
//
//                    Label la = (Label) getSpaceOwner().getFellow("propiedad");
//                    la.setValue("El Menú Seleccionado actualmente es: "+ menu);
//                }
////Messagebox.show("Ingrese todos los campos con (*) ...!", "Administrador Educativo", Messagebox.OK, Messagebox.ERROR);
//         }else{
//                if(item != null) {
//                    Include inc = (Include)getSpaceOwner().getFellow("xcontents");
//                    inc.setSrc(null); inc.setSrc("/denegado.zul");
//                }
//         }
//         }catch(Exception e){
//                    Include inc = (Include)getSpaceOwner().getFellow("xcontents");
//                    inc.setSrc("/denegado.zul");
//         }
//
//	}
public Boolean buscar(String id,Tabs tabs){
    List tabes = tabs.getChildren();
    for (Iterator it = tabes.iterator(); it.hasNext();) {
        Tab object = (Tab) it.next();
        String ide = object.getId();
        if(ide.equals(id)){
            object.setSelected(true);
            return false;
        }
    }
return true;



}
    
	public void onSelect() {
         Treeitem item = getSelectedItem();

         String menu = item.getId();
         if(menu.contains("ss")){
             if(item != null) {
                    Tabs tbs = (Tabs)getSpaceOwner().getFellow("tbs");
                    if(buscar("blanco",tbs)){
                        Tabpanels tps = (Tabpanels)getSpaceOwner().getFellow("tps");
                            Tab newtb= new Tab("Inicio");
                            newtb.setClosable(true);
                            newtb.setSelected(true);
                            newtb.setImage(item.getImage());
                            newtb.setParent(tbs);
                            newtb.setId("blanco");
                            Tabpanel newtpl = new Tabpanel();
                            Iframe f = new Iframe("./blanco.zul");
                            f.setWidth("100%");
                            f.setHeight("100%");
                            newtpl.appendChild(f);
                            newtpl.setParent(tps);
                    }
                }
             return;
         }
         Permisos per = new Permisos();
         try{
         if(per.verificarPermiso(menu, "Ingresar")){
                if (item != null) {
                    Tabs tbs = (Tabs)getSpaceOwner().getFellow("tbs");
                    if(buscar((String)item.getValue(),tbs)){
                        Tabpanels tps = (Tabpanels)getSpaceOwner().getFellow("tps");
                            Tab newtb= new Tab(((String)item.getValue()).replace("/","").replace(".zul", ""));
                            newtb.setClosable(true);
                            newtb.setSelected(true);
                            newtb.setImage(item.getImage());
                            newtb.setParent(tbs);
                            newtb.setId((String)item.getValue());
                            Tabpanel newtpl = new Tabpanel();
                            Iframe f = new Iframe((String)item.getValue());
                            f.setWidth("100%");
                            f.setHeight("100%");
                            newtpl.appendChild(f);
                            newtpl.setParent(tps);
                    }


                    Label la = (Label) getSpaceOwner().getFellow("propiedad");
                    la.setValue("El Menú Seleccionado actualmente es: "+ menu);
                }

         }else{
                if(item != null) {
                      Tabs tbs = (Tabs)getSpaceOwner().getFellow("tbs");
                    if(buscar((String)item.getValue(),tbs)){
                        Tabpanels tps = (Tabpanels)getSpaceOwner().getFellow("tps");
                            Tab newtb= new Tab("denegado");
                            newtb.setClosable(true);
                            newtb.setSelected(true);
                            newtb.setParent(tbs);
                            newtb.setId((String)item.getValue());
                            Tabpanel newtpl = new Tabpanel();
                            Iframe f = new Iframe("/denegado.zul");
                            f.setWidth("100%");
                            f.setHeight("100%");
                            newtpl.appendChild(f);
                            newtpl.setParent(tps);
                    }

                }
         }
         }catch(Exception e){
                    Include inc = (Include)getSpaceOwner().getFellow("xcontents");
                    inc.setSrc("/denegado.zul");
         }

	}
//         void add(){
//   	         	Tab newtb= new Tab(txb.getValue().equals("")?"0":txb.getValue());
//   	 			newtb.setParent(tbs);
//   	 			Tabpanel newtpl = new Tabpanel();
//   	 			newtpl.setParent(tps);
//    	        }

	public void afterCompose() {
		final Execution exec = Executions.getCurrent();
		String id = exec.getParameter("id");

		Treeitem item = null;
		if (id != null) {
			try {
				item = (Treeitem)getSpaceOwner().getFellow(id);
			} catch (ComponentNotFoundException ex) { //ignore
			}
		}

		if (item == null)
			item = (Treeitem)getSpaceOwner().getFellow("ss1");
		exec.setAttribute("contentSrc", (String)item.getValue());

			//so index.zul know which page to load based on the id parameter
		selectItem(item);
	}
}
