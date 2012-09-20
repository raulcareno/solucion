/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

     
    /**
     * busca según criterio textoBuscar
     */
    public void buscarMateriasdeCarrera() {
        try {
            //setModel(adm.listar("CarrerasMaterias"));
            model = (adm.query("Select o from CarrerasMaterias as o "
                    + " where o.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "' "
                    + "order by o.idNiveles.secuencia, o.idMaterias.nombre "));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public List<SelectItem> getSelectedItemCarreras() {
        try {
            List<Carreras> divisionPoliticas = new ArrayList<Carreras>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object != null) {
                 
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
                 
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }
      public List<SelectItem> getSelectedItemMaterias() {
        try {
            List<Materias> divisionPoliticas = new ArrayList<Materias>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object != null) {
                 
                    divisionPoliticas = adm.query("Select o from Materias as o order by o.nombre ");
                    if (divisionPoliticas.size() > 0) {
                        Materias objSel = new Materias(0);
                        items.add(new SelectItem(objSel, "Seleccione..."));
                        for (Materias obj : divisionPoliticas) {
                            items.add(new SelectItem(obj, obj.getNombre()));
                        }
                    } else {
                        Materias obj = new Materias(0);
                        items.add(new SelectItem(obj, "NO EXISTEN MATERIAS"));
                    }
                 
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMaterias.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }
      public List<SelectItem> getSelectedItemNiveles() {
        try {
            List<Niveles> divisionPoliticas = new ArrayList<Niveles>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object != null) {
                  
                    divisionPoliticas = adm.query("Select o from Niveles as o order by o.nombre ");
                    if (divisionPoliticas.size() > 0) {
                        Niveles objSel = new Niveles(0);
                        items.add(new SelectItem(objSel, "Seleccione..."));
                        for (Niveles obj : divisionPoliticas) {
                            items.add(new SelectItem(obj, obj.getNombre()));
                        }
                    } else {
                        Materias obj = new Materias(0);
                        items.add(new SelectItem(obj, "NO EXISTEN NIVELES"));
                    }
                 
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMaterias.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
   
      public List<SelectItem> getSelectedItemEjes() {
        try {
            List<Ejes> divisionPoliticas = new ArrayList<Ejes>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object != null) {
                 
                    divisionPoliticas = adm.query("Select o from Ejes as o order by o.nombre ");
                    if (divisionPoliticas.size() > 0) {
                        Niveles objSel = new Niveles(0);
                        items.add(new SelectItem(objSel, "Seleccione..."));
                        for (Ejes obj : divisionPoliticas) {
                            items.add(new SelectItem(obj, obj.getNombre()));
                        }
                    } else {
                        Ejes obj = new Ejes(0);
                        items.add(new SelectItem(obj, "NO EXISTEN EJES"));
                    }
                 
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMaterias.class.getName()).log(Level.SEVERE, null, e);
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
    
}
