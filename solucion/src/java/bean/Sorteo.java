/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

  
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Inscripciones;
import jcinform.procesos.Administrador;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericComposer;
//import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Progressmeter;


/**
 *
 * @author geovanny
 */

public class Sorteo  extends GenericComposer  implements Serializable  {
//	Window updateMyEntry;
    Listbox datos;
    Intbox estudiantes;
    Intbox total;
    Decimalbox base;
    Decimalbox desde;
    Decimalbox hasta;
    Intbox totalAbanderados;
    Intbox totalOtros;
//    Progressmeter cargando;
    @Override
  public void doAfterCompose(Component win) throws Exception {
        super.doAfterCompose(win);
        datos = (Listbox) win.getFellow("datos");
        estudiantes = (Intbox) win.getFellow("estudiantes");
        total = (Intbox) win.getFellow("total");
        totalAbanderados = (Intbox) win.getFellow("totalAbanderados");
        totalOtros = (Intbox) win.getFellow("totalOtros");

        base = (Decimalbox) win.getFellow("base");
        desde = (Decimalbox) win.getFellow("desde");
        hasta = (Decimalbox) win.getFellow("hasta");
//        cargando = (Progressmeter)win.getFellow("cargando");
    }


   public void onSaveTask(Event evt) {

   
    Administrador adm = new Administrador();
    List abanderados = adm.queryNativo("Select a.* from Inscripciones as a where a.aprovechamiento >= "+base.getValue().doubleValue()+" order by a.aprovechamiento desc limit "+estudiantes.getValue()+" ",Inscripciones.class);

    List otrosEstudiantes = adm.queryNativo("Select o.* from Inscripciones as o where (o.aprovechamiento >= "+desde.getValue().doubleValue()+" and o.aprovechamiento < "+hasta.getValue().doubleValue()+") ",Inscripciones.class);
    ArrayList todos = new ArrayList();
    for (Iterator it = abanderados.iterator(); it.hasNext();) {
           Inscripciones object = (Inscripciones)it.next();
           todos.add(object);
    }
ArrayList indiceSeleccionados = new ArrayList();
int Noestudiantes = estudiantes.getValue()-abanderados.size();
int j = 0;
if(Noestudiantes > otrosEstudiantes.size())
    Noestudiantes = otrosEstudiantes.size();
int incremento = (Noestudiantes)/100;
while(j < Noestudiantes){
//    if((otrosEstudiantes.size()+abanderados.size())>estudiantes.getValue().intValue()){//si el número es mayor al ingresado
        int val = (int) (Math.random()*otrosEstudiantes.size());
         while(indiceSeleccionados.contains(val)){
              val = (int) (Math.random()*otrosEstudiantes.size());
         }
             indiceSeleccionados.add(val);
//    }
    j++;
//    cargando.setValue(cargando.getValue() == 100 ? (0) : cargando.getValue()+1);
//    cargando.setValue(incremento);
//    incremento++;
//    System.out.println(""+incremento);
}
  for (Iterator it = indiceSeleccionados.iterator(); it.hasNext();) {
            Integer indice = (Integer)it.next();
           todos.add(otrosEstudiantes.get(indice.intValue()));
    }
    total.setValue(todos.size());
    totalAbanderados.setValue(abanderados.size());
    totalOtros.setValue(indiceSeleccionados.size());
//        datos = new Listbox();
        int a = 0;
        for (Iterator it = datos.getItems().iterator(); it.hasNext();) {
            datos.getItems().remove(a);
        }
        for (Iterator it = todos.iterator(); it.hasNext();) {
            Inscripciones acceIt = (Inscripciones) it.next();
            final Listitem li = new Listitem();
            li.setValue(acceIt);
            li.appendChild(new Listcell(acceIt.getCodigoest() + ""));
            li.appendChild(new Listcell(acceIt.getApellido()+" "+acceIt.getNombre()));
            li.appendChild(new Listcell(acceIt.getAprovechamiento()+""));
            datos.appendChild(li);
        }
       

    }

       private int numeroAzar(int minimo, int maximo) {

        // intervalo del rango de números (incluidos)
        int intervalo = maximo - minimo + 1;

        // selección del número al azar dentro del rango
        int azarEnRango = (int)(Math.random() * intervalo);

        // devolver el selecciona en el rango para el intervalo
        return (azarEnRango + minimo);
    }

   

}
