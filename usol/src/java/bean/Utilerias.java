/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Iterator;
import javax.faces.component.UIComponent;

/**
 *
 * @author Geovanny
 */
public class Utilerias {

    public Utilerias() {
    }
    
       protected UIComponent findComponent(UIComponent c, String id) {
        if (id.equals(c.getId())) {
            return c;
        }
        Iterator<UIComponent> kids = c.getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent found = findComponent(kids.next(), id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
