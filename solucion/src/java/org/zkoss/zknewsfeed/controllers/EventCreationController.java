package org.zkoss.zknewsfeed.controllers;

import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zknewsfeed.data.NewsColors;
import org.zkoss.zknewsfeed.models.DatabaseCalendarModel;
import org.zkoss.zknewsfeed.models.NewsItem;

import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

public class EventCreationController extends GenericForwardComposer {

    Window createMyEntry;
    Combobox cmbType;
    Textbox tbText;
    Datebox inicio;
    Datebox fina;
    Timebox tinicio;
    Timebox tfina;

    public void prepareWindow(int left, int top, NewsItem ni) {
        cmbType.setSelectedIndex(0);
        createMyEntry.setLeft(left + "px");
        createMyEntry.setTop(top + "px");
        inicio.setValue(ni.getBeginDate());
        fina.setValue(ni.getEndDate());
        tinicio.setValue(ni.getBeginDate());
        tfina.setValue(ni.getEndDate());

    }

    public void onClick$btnAddNews() {
        CalendarsEvent evt = ((org.zkoss.calendar.event.CalendarsEvent) createMyEntry.getAttribute("calevent"));

        Window win = (Window) createMyEntry.getParent();
        org.zkoss.calendar.Calendars cals = (org.zkoss.calendar.Calendars) win.getVariable("cal", false);
//		Chart piechart = (Chart)win.getVariable("piechart", false);

        NewsItem ni = new NewsItem();

        int selectedColor = cmbType.getSelectedIndex();

        if (selectedColor == -1) {
            selectedColor = 0;
        }


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
        //ni.setBeginDate(evt.getBeginDate());
        //ni.setEndDate(evt.getEndDate());

        ni.setHeaderColor(hcolor);
        ni.setContentColor(color);

        ni.setContent(tbText.getValue());
        ni.setLocked(false);

        DatabaseCalendarModel.dao.insertNewsItem(ni);
        DatabaseCalendarModel dcm = new DatabaseCalendarModel();

        cals.setModel(dcm.getSimpleCalendarModel());
//		piechart.setModel(dcm.getSimplePieModel());

        evt.clearGhost();
        createMyEntry.setVisible(false);
    }

    public void onClick$btnCancel() {
        CalendarsEvent evt = ((org.zkoss.calendar.event.CalendarsEvent) createMyEntry.getAttribute("calevent"));
        createMyEntry.setVisible(false);
        evt.clearGhost();
    }
}
