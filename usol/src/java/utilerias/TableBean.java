/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilerias;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import jcinform.persistencia.Materias;
import org.primefaces.event.DragDropEvent;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@RequestScoped
public class TableBean {

  
       private List<Materias> materiasSmall;  
    private List<Materias> droppedMaterias;  
    public TableBean() {  
        materiasSmall = new ArrayList<Materias>();  
        droppedMaterias = new ArrayList<Materias>();  
  
        populateRandomMateriass(materiasSmall, 9);  
    }  
  
    private void populateRandomMateriass(List<Materias> list, int size) {  
        for(int i = 0 ; i < size ; i++) { 
            Materias mat = new Materias(i);
            mat.setNombre("materia "+i);
            list.add(mat);  
        }
    }  
  
   
     
  
    public void onMateriasDrop(DragDropEvent ddEvent) {  
        Materias car = ((Materias) ddEvent.getData());  
        droppedMaterias.add(car);  
        materiasSmall.remove(car);  
    }  

    public List<Materias> getMateriasSmall() {
        return materiasSmall;
    }

    public void setMateriasSmall(List<Materias> materiasSmall) {
        this.materiasSmall = materiasSmall;
    }
  
    public List<Materias> getDroppedMaterias() {  
        return droppedMaterias;  
    }  
}
