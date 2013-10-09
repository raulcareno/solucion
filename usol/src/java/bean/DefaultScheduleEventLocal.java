/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Date;
import jcinform.persistencia.Horarios;
import org.primefaces.model.DefaultScheduleEvent;

/**
 *
 * @author geova
 */
public class DefaultScheduleEventLocal extends DefaultScheduleEvent {

    public DefaultScheduleEventLocal() {
    }

    private Horarios idHorarios;
    
     private String title;

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }
    
    
    
    public DefaultScheduleEventLocal(String title, Date startDate, Date endDate, String styleClass, Horarios idHorarios) {
        super(title, startDate, endDate, styleClass);
        this.idHorarios = idHorarios;
    }

 

    public Horarios getIdHorarios() {
        return idHorarios;
    }

    public void setIdHorarios(Horarios idHorarios) {
        this.idHorarios = idHorarios;
    }
    
    @Override
    public String toString(){
        return getTitle()+" "+getStartDate().toLocaleString()+" "+getEndDate().toLocaleString();
    }
    
}
