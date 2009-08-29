package bean;

import java.util.logging.Level;
import java.util.logging.Logger;
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
 
	public void onSelect() {
         Treeitem item = getSelectedItem();
          
         String menu = item.getId();
         if(menu.contains("ss")){
             if(item != null) {
                    Include inc = (Include)getSpaceOwner().getFellow("xcontents");
                    inc.setSrc("/blanco.zul");
                   
                    
                }
             return;
         }
         Permisos per = new Permisos();
         try{
         if(per.verificarPermiso(menu, "Ingresar")){
                if (item != null) {
                    Include inc = (Include)getSpaceOwner().getFellow("xcontents");
                    inc.setSrc((String)item.getValue());
                    
                    Label la = (Label) getSpaceOwner().getFellow("propiedad");
                    la.setValue("El Menú Seleccionado actualmente es: "+ menu);
                }
                 
         }else{
                if(item != null) {
                    Include inc = (Include)getSpaceOwner().getFellow("xcontents");
                    inc.setSrc("/denegado.zul");
                }
         }
         }catch(Exception e){
                    Include inc = (Include)getSpaceOwner().getFellow("xcontents");
                    inc.setSrc("/denegado.zul");
         }
		
	}
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
