package org.zkoss.zknewsfeed.controllers;


import bean.Permisos;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.calendar.impl.SimpleCalendarModel;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zknewsfeed.models.DatabaseCalendarModel;
import org.zkoss.zknewsfeed.models.NewsItem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class NewsController extends GenericForwardComposer {
	
	Window win;
	Calendars cal;
//	Chart piechart;
	
	Window creationDialog = null;
	Window updateDialog = null;
    Permisos permiso = new Permisos();
	
	public SimpleCalendarModel getCalendarModel() {
		DatabaseCalendarModel dcm = new DatabaseCalendarModel();
//		piechart.setModel(dcm.getSimplePieModel());
		return dcm.getSimpleCalendarModel();
	}
	
	public void onEventCreate$cal(ForwardEvent event) {
          if(!permiso.verificarPermiso("Calendario", "Agregar")){
            try {
                Messagebox.show("No tiene permisos para realizar esta acción...!", "Administrador Educativo", Messagebox.OK, Messagebox.ERROR);
                return;
            } catch (InterruptedException ex) {
                Logger.getLogger(NewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
		CalendarsEvent evt = (CalendarsEvent) event.getOrigin();
		
		int left = evt.getX();
		int top = evt.getY();
		
		
		if (top + 245 > evt.getDesktopHeight())
			top = evt.getDesktopHeight() - 245;
		if (left + 410 > evt.getDesktopWidth())
			left = evt.getDesktopWidth() - 410;
		
		if(creationDialog == null)
		{		
			creationDialog = (Window)Executions.createComponents("createEntry.zul", win, null);
		}
		
		EventCreationController c = (EventCreationController)creationDialog.getVariable("createMyEntry$composer", false);
        NewsItem ni = new NewsItem();
        ni.setBeginDate(evt.getBeginDate());
        ni.setEndDate(evt.getEndDate());
		c.prepareWindow(left, top, ni);
		//c.inicio.setValue(evt.getBeginDate());
		creationDialog.setAttribute("calevent", evt);
		creationDialog.setVisible(true);
		
		evt.stopClearGhost();
	}
	
	public void onEventEdit$cal(ForwardEvent event) {
            
         if(!permiso.verificarPermiso("Calendario", "Modificar")){
            try {
                Messagebox.show("No tiene permisos para realizar esta acción...!", "Administrador Educativo", Messagebox.OK, Messagebox.ERROR);
            return;
            } catch (InterruptedException ex) {
                Logger.getLogger(NewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
         
		CalendarsEvent evt = (CalendarsEvent) event.getOrigin();
		
		int left = evt.getX();
		int top = evt.getY();
		
		
		if (top + 245 > evt.getDesktopHeight())
			top = evt.getDesktopHeight() - 245;
		if (left + 410 > evt.getDesktopWidth())
			left = evt.getDesktopWidth() - 410;
		
		NewsItem ni = (NewsItem)evt.getCalendarEvent();
		
		if(updateDialog == null)
		{		
			updateDialog = (Window)Executions.createComponents("updateEntry.zul", win, null);
		}
		
		EventUpdateController c = (EventUpdateController)updateDialog.getVariable("updateMyEntry$composer", false);
		c.prepareWindow(left, top, ni);
				
		
		updateDialog.setAttribute("calevent", evt);
		updateDialog.setAttribute("ni", ni);
		updateDialog.setVisible(true);
		evt.stopClearGhost();
	}
	
	public void onEventUpdate$cal(ForwardEvent event) {

         if(!permiso.verificarPermiso("Calendario", "Modificar")){
            try {
                Messagebox.show("No tiene permisos para realizar esta acción...!", "Administrador Educativo", Messagebox.OK, Messagebox.ERROR);
            return;
            } catch (InterruptedException ex) {
                Logger.getLogger(NewsController.class.getName()).log(Level.SEVERE, null, ex);
            }
         }
		CalendarsEvent evt = (CalendarsEvent)event.getOrigin();
		NewsItem ni = (NewsItem)evt.getCalendarEvent();
		
		ni.setBeginDate(evt.getBeginDate());
		ni.setEndDate(evt.getEndDate());
		
		DatabaseCalendarModel.dao.updateNewsItem(ni);
		DatabaseCalendarModel dm = new DatabaseCalendarModel();
		cal.setModel(dm.getSimpleCalendarModel());
	}
}
