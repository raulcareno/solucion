/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Date;
import jcinform.persistencia.Carreras;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Materias;
import jcinform.persistencia.Niveles;
import jcinform.persistencia.Periodos;
import org.primefaces.model.DefaultScheduleEvent;

/**
 *
 * @author geova
 */
public class DefaultScheduleEventLocal extends DefaultScheduleEvent {

    private Periodos idPeriodos;
    private Empleados idEmpleados;
    private Materias idMaterias;
    private Carreras idCarreras;
    private Niveles idNiveles;

    public DefaultScheduleEventLocal(String title, Date startDate, Date endDate, String styleClass, Periodos idPeriodos, Empleados idEmpleados, Materias idMaterias, Carreras idCarreras, Niveles idNiveles) {
        super(title, startDate, endDate, styleClass);
        this.idCarreras = idCarreras;
        this.idEmpleados = idEmpleados;
        this.idMaterias = idMaterias;
        this.idNiveles = idNiveles;
        this.idPeriodos = idPeriodos;
    }

    public Periodos getIdPeriodos() {
        return idPeriodos;
    }

    public void setIdPeriodos(Periodos idPeriodos) {
        this.idPeriodos = idPeriodos;
    }

    public Empleados getIdEmpleados() {
        return idEmpleados;
    }

    public void setIdEmpleados(Empleados idEmpleados) {
        this.idEmpleados = idEmpleados;
    }

    public Materias getIdMaterias() {
        return idMaterias;
    }

    public void setIdMaterias(Materias idMaterias) {
        this.idMaterias = idMaterias;
    }

    public Carreras getIdCarreras() {
        return idCarreras;
    }

    public void setIdCarreras(Carreras idCarreras) {
        this.idCarreras = idCarreras;
    }

    public Niveles getIdNiveles() {
        return idNiveles;
    }

    public void setIdNiveles(Niveles idNiveles) {
        this.idNiveles = idNiveles;
    }
}
