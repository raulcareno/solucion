/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Date;
import jcinform.persistencia.Horarios;
import org.primefaces.model.ScheduleEvent;

/**
 *
 * @author geova
 */
public class ScheduleEventLocal implements ScheduleEvent {

    public ScheduleEventLocal() {
    }
    String id;
    String title;
    Object data;
    Date startDate;
    Date endDate;
    boolean allDay;
    boolean editable;
    String styleClass;
    private Horarios idHorarios;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean isAllDay() {
        return allDay;
    }

    public void setAllDay(boolean allDay) {
        this.allDay = allDay;
    }

    @Override
    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    @Override
    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public Horarios getIdHorarios() {
        return idHorarios;
    }

    public void setIdHorarios(Horarios idHorarios) {
        this.idHorarios = idHorarios;
    }
    
    
}
