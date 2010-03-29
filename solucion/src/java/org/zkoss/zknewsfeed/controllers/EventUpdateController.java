package org.zkoss.zknewsfeed.controllers;

import bean.Permisos;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zknewsfeed.data.NewsColors;
import org.zkoss.zknewsfeed.models.DatabaseCalendarModel;
import org.zkoss.zknewsfeed.models.NewsItem;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

public class EventUpdateController extends GenericForwardComposer {
	Window updateMyEntry;
	Combobox cmbType;
	Textbox tbText;
    Datebox inicio;
    Datebox fina;
    Timebox tinicio;
    Timebox tfina;
	 Permisos permiso = new Permisos();
	String[] _colors = {"#FF6F6F", "#6D8EFE", "#00E874","#FF9900",};
	
	public void prepareWindow(int left, int top, NewsItem ni) {
		updateMyEntry.setLeft(left + "px");
		updateMyEntry.setTop(top + "px");
		
		int colorPosition = NewsColors.getColorPosition(ni.getContentColor());
		
		if(colorPosition == -1)
			colorPosition = 0;
		inicio.setValue(ni.getBeginDate());
        fina.setValue(ni.getEndDate());
        tinicio.setValue(ni.getBeginDate());
        tfina.setValue(ni.getEndDate());
		cmbType.setSelectedIndex(colorPosition);
//        cmbType.setStyle("color:"+_colors[colorPosition]);
		tbText.setValue(ni.getContent());
	}
	
	public void onClick$btnUpdateNews() {
        
        if(!permiso.verificarPermiso("Calendario", "Modificar")){
            try {
                Messagebox.show("No tiene permisos para realizar esta acción...!", "Administrador Educativo", Messagebox.OK, Messagebox.ERROR);
                return;
            } catch (InterruptedException ex) {
                Logger.getLogger(NewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
		CalendarsEvent evt = ((org.zkoss.calendar.event.CalendarsEvent)updateMyEntry.getAttribute("calevent"));
		
		Window win = (Window)updateMyEntry.getParent();
		org.zkoss.calendar.Calendars cals = (org.zkoss.calendar.Calendars)win.getVariable("cal", false);
//		Chart piechart = (Chart)win.getVariable("piechart", false);
		
		NewsItem ni = ((NewsItem)updateMyEntry.getAttribute("ni"));
        int ihora = tinicio.getValue().getHours();
        int imin = tinicio.getValue().getMinutes();
        int iseg = tinicio.getValue().getSeconds();
        inicio.getValue().setHours(ihora);
        inicio.getValue().setMinutes(imin);
        inicio.getValue().setSeconds(iseg);
        
        int fhora = tfina.getValue().getHours();
        int fmin = tfina.getValue().getMinutes();
        int fseg = tfina.getValue().getSeconds();
        fina.getValue().setHours(fhora);
        fina.getValue().setMinutes(fmin);
        fina.getValue().setSeconds(fseg);

        
		ni.setBeginDate(inicio.getValue());
        ni.setEndDate(fina.getValue());
        ni.setContent(tbText.getValue());
		int selectedColor = cmbType.getSelectedIndex();
		
		if(selectedColor == -1)
			selectedColor = 0;
		
		ni.setContentColor(NewsColors._colors[selectedColor]);

       String color = NewsColors._colors[selectedColor];
        String hcolor = "";

        if (color.equals("#FF6F6F")) {
            hcolor = "red";
        } else if (color.equals("#6D8EFE")) {
            hcolor = "blue";
        } else if (color.equals("#00E874")) {
            hcolor = "green";
        } else if (color.equals("#FF9900")) {
            hcolor = "tomato";
        }
            ni.setHeaderColor(hcolor);

		DatabaseCalendarModel.dao.updateNewsItem(ni);
		DatabaseCalendarModel dcm = new DatabaseCalendarModel();
		
		cals.setModel(dcm.getSimpleCalendarModel());
//		piechart.setModel(dcm.getSimplePieModel());
		
		evt.clearGhost();
		
		updateMyEntry.setVisible(false);
	}
	
	public void onClick$btnDeleteNews() {
          if(!permiso.verificarPermiso("Calendario", "Eliminar")){
            try {
                Messagebox.show("No tiene permisos para realizar esta acción...!", "Administrador Educativo", Messagebox.OK, Messagebox.ERROR);
            return;
            } catch (InterruptedException ex) {
                Logger.getLogger(NewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
		NewsItem ni = ((NewsItem)updateMyEntry.getAttribute("ni"));
		
		Window win = (Window)updateMyEntry.getParent();
		org.zkoss.calendar.Calendars cals = (org.zkoss.calendar.Calendars)win.getVariable("cal", false);
//		Chart piechart = (Chart)win.getVariable("piechart", false);
		
		DatabaseCalendarModel.dao.deleteNewsItem(ni);
		DatabaseCalendarModel dcm = new DatabaseCalendarModel();
		
		cals.setModel(dcm.getSimpleCalendarModel());
//		piechart.setModel(dcm.getSimplePieModel());
		
		updateMyEntry.setVisible(false);
	}
	
	public void onClick$btnCancel() {
		CalendarsEvent evt = ((org.zkoss.calendar.event.CalendarsEvent)updateMyEntry.getAttribute("calevent"));
		updateMyEntry.setVisible(false);
		evt.clearGhost();
	}
}
