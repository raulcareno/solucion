package bean;

import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabs;

/**
 *
 * @author jcinform
 */
public class DynamicTabs  extends Tabs {

    public DynamicTabs() {
        Tab t = new Tab("ad");
        t.setWidth("10%");
        t.setParent(this);
       }
}
