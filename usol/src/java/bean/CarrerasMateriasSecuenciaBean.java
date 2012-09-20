/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import jcinform.persistencia.Carreras;
import jcinform.persistencia.CarrerasMaterias;
import jcinform.persistencia.Ejes;
import jcinform.persistencia.Materias;
import jcinform.persistencia.Niveles;
import jcinform.procesos.Administrador;
import org.primefaces.event.DragDropEvent;

import utilerias.Permisos;
 
/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class CarrerasMateriasSecuenciaBean {

    /**
     * Creates a new instance of CarrerasMateriasBean
     */
    CarrerasMaterias object;
    Administrador adm;
    protected List<CarrerasMaterias> model;
    protected Carreras carreraSeleccionada;
    protected Niveles nivelesSeleccionada;
    protected Ejes ejesSeleccionada;
    protected Materias materiasSeleccionada;
    public String textoBuscar;
    Permisos permisos;
Auditar  aud = new Auditar();
    public CarrerasMateriasSecuenciaBean() {
        //super();
//        FacesContext context = FacesContext.getCurrentInstance();
//        String s = context.getExternalContext().getRequestParameterMap().get("skp");
//        if (s != null) {
//            System.out.println(s);
//        }
        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("CarrerasMaterias", "ingresar_carrerasMaterias", "ingresar", true, "PARAMETROS")) {
            try {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/noPuedeIngresar.jspx");
            } //selectedCarrerasMaterias = new CarrerasMaterias();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      
        //selectedCarrerasMaterias = new CarrerasMaterias();

    }
    public void onCarrerasInvDrop(CarrerasMaterias player) {  
        listaMaterias.add(player); 
        anadidas.remove(player);
    }
   public void onCarrerasDrop(DragDropEvent event) {  
        CarrerasMaterias player = (CarrerasMaterias) event.getData();  
        anadidas.add(player); 
        listaMaterias.remove(player);
         String filaColumna = event.getDropId();
         Integer fila = new Integer(filaColumna.substring(filaColumna.indexOf("f")+1, filaColumna.indexOf("c")));
         Integer columna = new Integer(filaColumna.substring(filaColumna.indexOf("c")+1, filaColumna.length()));
        anadidasArray[fila][columna] =  player;
        System.out.println("ID EN LA CUAL SE HA HAÑADIDO"+event.getDropId());
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(player.getIdCarreras().getNombre() + " added", "Position:" + event.getDropId()));  
    }   
    /**
     * busca según criterio textoBuscar
     */
    List<CarrerasMaterias> listaMaterias = new ArrayList<CarrerasMaterias>();
    List<CarrerasMaterias> anadidas  = new ArrayList<CarrerasMaterias>();
    //List<CarrerasMaterias> anadidas2[2][2]  = new ArrayList<>();
   CarrerasMaterias anadidasArray[][] = new CarrerasMaterias[30][12];
    public void llenarArreglo(){
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 12; j++) {
                CarrerasMaterias car = new CarrerasMaterias();
                Materias mat = new Materias();
                mat.setNombre("");
                car.setIdMaterias(mat);
                car.setIdNiveles(new Niveles());
                car.setIdCarreras(new Carreras());
                anadidasArray[i][j] = car;
            }
            
        }
        
    }
    public void buscarMateriasdeCarrera() {
        try {
        
              listaMaterias = adm.query("Select o from CarrerasMaterias as o "
                        + " where o.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "' ");
          llenarArreglo();

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public List<SelectItem> getSelectedItemCarreras() {
        try {
            List<Carreras> divisionPoliticas = new ArrayList<Carreras>();
            List<SelectItem> items = new ArrayList<SelectItem>();
                    divisionPoliticas = adm.query("Select o from Carreras as o order by o.nombre ");
                    if (divisionPoliticas.size() > 0) {
                        Carreras objSel = new Carreras(0);
                        items.add(new SelectItem(objSel, "Seleccione..."));
                        for (Carreras obj : divisionPoliticas) {
                            items.add(new SelectItem(obj, obj.getNombre()));
                        }
                    } else {
                        Carreras obj = new Carreras(0);
                        items.add(new SelectItem(obj, "NO EXISTEN CARRERAS"));
                    }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }
      

    /**
     * propiedades
     *
     * @return
     */
    public List<CarrerasMaterias> getModel() {
       
        return model;
    }

    public void setModel(List<CarrerasMaterias> model) {
        this.model = model;
    }

    /**
     * busca un componente dentro del form.jspx para enviar mensajes
     *
     * @param c
     * @param id
     * @return
     */
    protected UIComponent findComponent(UIComponent c, String id) {
        if (id.equals(c.getId())) {
            return c;
        }
        Iterator<UIComponent> kids = c.getFacetsAndChildren();
        while (kids.hasNext()) {
            UIComponent found = findComponent(kids.next(), id);
            if (found != null) {
                return found;
            }
        }
        return null;
    }

    public String getTextoBuscar() {
        return textoBuscar;
    }

    public void setTextoBuscar(String textoBuscar) {
        this.textoBuscar = textoBuscar;
    }
    private static final long serialVersionUID = 1L;

    public CarrerasMaterias getObject() {
        if (object == null) {
            object = new CarrerasMaterias(0);
        }
        return object;
    }

    public void setObject(CarrerasMaterias object) {
        this.object = object;
    }

    public Carreras getCarreraSeleccionada() {
        return carreraSeleccionada;
    }

    public void setCarreraSeleccionada(Carreras carreraSeleccionada) {
        this.carreraSeleccionada = carreraSeleccionada;
    }

    public Niveles getNivelesSeleccionada() {
        return nivelesSeleccionada;
    }

    public void setNivelesSeleccionada(Niveles nivelesSeleccionada) {
        this.nivelesSeleccionada = nivelesSeleccionada;
    }

    public Ejes getEjesSeleccionada() {
        return ejesSeleccionada;
    }

    public void setEjesSeleccionada(Ejes ejesSeleccionada) {
        this.ejesSeleccionada = ejesSeleccionada;
    }

    public Materias getMateriasSeleccionada() {
        return materiasSeleccionada;
    }

    public void setMateriasSeleccionada(Materias materiasSeleccionada) {
        this.materiasSeleccionada = materiasSeleccionada;
    }

    public List<CarrerasMaterias> getListaMaterias() {
        return listaMaterias;
    }

    public void setListaMaterias(List<CarrerasMaterias> listaMaterias) {
        this.listaMaterias = listaMaterias;
    }

    public List<CarrerasMaterias> getAnadidas() {
        return anadidas;
    }

    public void setAnadidas(List<CarrerasMaterias> anadidas) {
        this.anadidas = anadidas;
    }

    public CarrerasMaterias[][] getAnadidasArray() {
        return anadidasArray;
    }

    public void setAnadidasArray(CarrerasMaterias[][] anadidasArray) {
        this.anadidasArray = anadidasArray;
    }
    
    
    
}
