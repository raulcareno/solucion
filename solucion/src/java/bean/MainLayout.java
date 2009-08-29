package bean;


import java.math.BigDecimal;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
//import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkex.zul.Borderlayout;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Decimalbox;
//import org.zkoss.zul.Grid;
/**
 * The main window of userguide.
 * @author jumperchen
 * @since 3.0.0
 */
public class MainLayout extends Borderlayout {
    private EventListener listener;
	public MainLayout() {
		// We have to decide the key of Google Maps since we have a demo using
		// it.
		// This key is used by zkdemo/userguide/index.zul to generate a proper
		// script
		final Execution exec = Executions.getCurrent();
		final String sn = exec.getServerName();
		final int sp = exec.getServerPort();
        Decimalbox dec = new Decimalbox();
        Double val = 0.0;
        BigDecimal ddd = new BigDecimal(val);
       Comboitem a;
       Bandbox band;
       //band.setReadonly(_visible)
       //a.setValue(val);

//Grid g = new Grid();
//g.getModel().getSize();
//Grid d = new Grid();
// Bandbox b;
// Listbox l = null;
//        Listitem item = null;
//        item.app
// l.setSelectedItem(item);
 

		String gkey = null;
		if (sn.indexOf("www.potix.com") >= 0) { // http://www.potix.com/
			gkey = "ABQIAAAAmGxmYR57XDAbAumS9tV5fxRYCo_4ZGj_-54kHesWSk0nMkbs4xTpq0zo9O75_ZqvsSLGY2YkC7jjNg";
		} else if (sn.indexOf("www.zkoss.org") >= 0) { // http://www.zkoss.org/
			gkey = "ABQIAAAAmGxmYR57XDAbAumS9tV5fxQXyylOlR69a1vFTcUcpV6DXdesOBSMEHfkewcSzwEwBT7UzVx8ep8vjA";
		} else if (sn.indexOf("zkoss.org") >= 0) { // http://www.zkoss.org/
			gkey = "ABQIAAAAakIm31AXAvNGFHV8i1Tx8RSF4KLGEmvBsS1z1zAsQZvbQceuNRQBsm65qGaXpTWjZsc2bl-hm2Vyfw";
		} else if (sn.indexOf("localhost") >= 0) { // localhost
			if (sp == 80) // http://localhost/
				gkey = "ABQIAAAAmGxmYR57XDAbAumS9tV5fxT2yXp_ZAY8_ufC3CFXhHIE1NvwkxRUITTZ-rzsyEVih16Hn3ApyUpSkA";
			else if (sp == 8080) // http://localhost:8080
				gkey = "ABQIAAAAmGxmYR57XDAbAumS9tV5fxTwM0brOpm-All5BF6PoaKBxRWWERSynObNOWSyMNmLGAMZAO1WkDUubA";
			else if (sp == 7799)
				gkey = "ABQIAAAAmGxmYR57XDAbAumS9tV5fxTT6-Op-9nAQgn7qnDG0QjE8aldaBRU1BQK2ADNWCt1BR2yg4ghOM6YIA";
		}

		if (gkey != null)
			exec.getDesktop().getSession().setAttribute("gmapsKey", gkey);
	}
}
