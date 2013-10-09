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
public class CarrerasMateriasBean {

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
    public CarrerasMateriasBean() {
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
        if (!permisos.verificarPermisoReporte("Malla", "ingresar_carrerasMaterias.jspx", "ingresar", true, "PARAMETROS")) {
            try {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/universidad/noPuedeIngresar.jspx");
            } //selectedCarrerasMaterias = new CarrerasMaterias();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        inicializar();
        //selectedCarrerasMaterias = new CarrerasMaterias();

    }

    public String editarAction(CarrerasMaterias obj) {
        inicializar();
        object = obj;
        carreraSeleccionada = obj.getIdCarreras();
        ejesSeleccionada = obj.getIdEjes();
        nivelesSeleccionada = obj.getIdNiveles();
        materiasSeleccionada = obj.getIdMaterias();
         
        return null;
    }

    protected void inicializar() {
        object = new CarrerasMaterias(0);
        object.setIdCarreras(new Carreras(0));
        object.setIdEjes(new Ejes(0));
        object.setIdNiveles(new Niveles(0));
        object.setIdMaterias(new Materias(0));
        carreraSeleccionada = object.getIdCarreras();
        ejesSeleccionada = object.getIdEjes();
        nivelesSeleccionada = object.getIdNiveles();
        materiasSeleccionada = object.getIdMaterias();
        cargarDataModel();
    }

    /**
     * Graba el registro asociado al objeto que
     */
    public String guardar() {
        Carreras carreraSeleccionada2 = carreraSeleccionada;
        FacesContext context = FacesContext.getCurrentInstance();
        if(carreraSeleccionada.getIdCarreras().equals(new Integer(0))){
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione una CARRERA", "Seleccione una CARRERA"));
            return null;
        }

        if(nivelesSeleccionada.getIdNiveles().equals(new Integer(0))){
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione un NIVEL", "Seleccione un NIVEL"));
            return null;
        }
        if(materiasSeleccionada.getIdMaterias().equals(new Integer(0))){
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione una MATERIA", "Seleccione una MATERIA"));
            return null;
        }
        if(ejesSeleccionada.getIdEjes().equals(new Integer(0))){
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione el EJE", "Seleccione el EJE"));
            return null;
        }
        
                    object.setIdEjes(ejesSeleccionada);
                    object.setIdCarreras(carreraSeleccionada);
                    object.setIdNiveles(nivelesSeleccionada);
                    object.setIdMaterias(materiasSeleccionada); 
        if (object.getIdCarrerasMaterias() == 0) {
            if (!permisos.verificarPermisoReporte("Malla", "agregar_carrerasMaterias.jspx", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                if (adm.existe("CarrerasMaterias", "idMaterias", object.getIdMaterias(),"idCarreras",object.getIdCarreras(),"").size() <= 0) {
                    object.setIdCarrerasMaterias(adm.getNuevaClave("CarrerasMaterias", "idCarrerasMaterias"));
                    
                    adm.guardar(object);
                    aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdCarrerasMaterias()+"");
                    inicializar();
                    object.setIdCarreras(carreraSeleccionada2);
                    carreraSeleccionada = carreraSeleccionada2;
                    buscar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ya existe una materia con ésos parametros...!","Ya existe una materia con ésos parametros...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
            if (!permisos.verificarPermisoReporte("Malla", "actualizar_carrerasMaterias.jspx", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                adm.actualizar(object);
                aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdCarrerasMaterias()+"");
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                inicializar();
                object.setIdCarreras(carreraSeleccionada2);
                carreraSeleccionada = carreraSeleccionada2;
                buscar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }

        return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(CarrerasMaterias obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Malla", "eliminar_carrerasMaterias.jspx", "eliminar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            adm.eliminarObjeto(CarrerasMaterias.class, obj.getIdCarrerasMaterias());
            aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "eliminar", "", obj.getIdCarrerasMaterias()+"");
            inicializar();
            carreraSeleccionada = obj.getIdCarreras();
            buscar();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, e);
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return null;
    }

    /**
     *
     * Obtiene el registro seleccionado
     */
    public List<SelectItem> getSelectedItem() {
        try {
            List<CarrerasMaterias> datos = adm.query("Select o from CarrerasMaterias as o");
            List<SelectItem> items = new ArrayList<SelectItem>();
            for (CarrerasMaterias obj : datos) {
                items.add(new SelectItem(obj, obj.getIdMaterias().getNombre()));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
//            model = (adm.listar("CarrerasMaterias"));
//            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        object = new CarrerasMaterias(0);
        
    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
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
                  
                    divisionPoliticas = adm.query("Select o from Niveles as o  order by o.secuencia ");
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
        if (model == null) {
            cargarDataModel();
        }
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
