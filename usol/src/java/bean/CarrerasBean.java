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
import jcinform.persistencia.Escuela;
import jcinform.persistencia.Jornada;
import jcinform.persistencia.Modalidad;
import jcinform.procesos.Administrador;

import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class CarrerasBean {

    /**
     * Creates a new instance of CarrerasBean
     */
    Carreras object;
    Administrador adm;
    protected List<Carreras> model;
    public String textoBuscar;
    Permisos permisos;
    boolean esLogo;
    boolean verSubir;
Auditar  aud = new Auditar();
    public CarrerasBean() {
        //super();
        FacesContext context = FacesContext.getCurrentInstance();
 
        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("Carreras", "ingresar_carreras", "ingresar", true, "PARAMETROS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("noPuedeIngresar.jspx");
            } //selectedCarreras = new Carreras();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(CarrerasBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(CarrerasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //selectedCarreras = new Carreras();

    }

    public String editarAction(Carreras obj) {
        inicializar();

        object = obj;
        return null;
    }

    protected void inicializar() {
        object = new Carreras(0);
        object.setIdEscuela(new Escuela());
         object.setIdJornada(new Jornada());
         object.setIdModalidad(new Modalidad());
        cargarDataModel();
    }
    
    /**
     * Graba el registro asociado al objeto que
     */
    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
 
        if (object.getNombre().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el NOMBRE", ""));
            return null;
        }
        if (object.getTitulo().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el TITULO", ""));
            return null;
        }
        if (object.getTitulo().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el TITULO", ""));
            return null;
        }
        if (object.getIdCarreras() == 0) {
            if (!permisos.verificarPermisoReporte("Carreras", "agregar_carreras", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                if (adm.existe("Carreras", "nombre", object.getNombre()).size() <= 0) {
                    object.setIdCarreras(adm.getNuevaClave("Carreras", "idCarreras"));
                    adm.guardar(object);
                    aud.auditar(adm,"Carreras", "guardar", "", object.getIdCarreras()+"");
                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nombre ya existe...!", "Nombre ya existe...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
            if (!permisos.verificarPermisoReporte("Carreras", "actualizar_carreras", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                adm.actualizar(object);
                aud.auditar(adm,"Carreras", "actualizar", "", object.getIdCarreras()+"");
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(CarrerasBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }

        return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(Carreras obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Carreras", "eliminar_carreras", "eliminar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            adm.eliminarObjeto(Carreras.class, obj.getIdCarreras());
            aud.auditar(adm,"Carreras", "eliminar", "", obj.getIdCarreras()+"");
            inicializar();
            cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(CarrerasBean.class.getName()).log(Level.SEVERE, null, e);
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
            List<Carreras> datos = adm.query("Select o from Carreras as o");
            List<SelectItem> items = new ArrayList<SelectItem>();
            for (Carreras obj : datos) {
                items.add(new SelectItem(obj, obj.getNombre()));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * Obtiene el el listado de escuelas
     *
     * @return
     */
    public List<SelectItem> getSelectedItemEscuela() {
        try {
            List<Escuela> divisionPoliticas = new ArrayList<Escuela>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object == null) {
                object = new Carreras();
                object.setIdEscuela(new Escuela());
            }
            if (object != null) {

                divisionPoliticas = adm.query("Select o from Escuela as o order by o.nombre ");
                if (divisionPoliticas.size() > 0) {
                    Escuela objSel = new Escuela(0);
                    items.add(new SelectItem(objSel, "Seleccione..."));
                    for (Escuela obj : divisionPoliticas) {
                        items.add(new SelectItem(obj, obj.getNombre()));
                    }
                } else {
                    Escuela obj = new Escuela(0);
                    items.add(new SelectItem(obj, "NO EXISTEN ESCUELAS"));
                }

            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CantonBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

       public List<SelectItem> getSelectedItemModalidad() {
        try {
            List<Modalidad> divisionPoliticas = new ArrayList<Modalidad>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object == null) {
                //object = new Carreras();
                object.setIdEscuela(new Escuela());
            }
            if (object != null) {

                divisionPoliticas = adm.query("Select o from Modalidad as o order by o.nombre ");
                if (divisionPoliticas.size() > 0) {
                    Modalidad objSel = new Modalidad(0);
                    items.add(new SelectItem(objSel, "Seleccione..."));
                    for (Modalidad obj : divisionPoliticas) {
                        items.add(new SelectItem(obj, obj.getNombre()));
                    }
                } else {
                    Modalidad obj = new Modalidad(0);
                    items.add(new SelectItem(obj, "NO EXISTEN MODALIDADES"));
                }

            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CantonBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

          public List<SelectItem> getSelectedItemJornada() {
        try {
            List<Jornada> divisionPoliticas = new ArrayList<Jornada>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object == null) {
                //object = new Carreras();
                object.setIdEscuela(new Escuela());
            }
            if (object != null) {

                divisionPoliticas = adm.query("Select o from Jornada as o order by o.nombre ");
                if (divisionPoliticas.size() > 0) {
                    Jornada objSel = new Jornada(0);
                    items.add(new SelectItem(objSel, "Seleccione..."));
                    for (Jornada obj : divisionPoliticas) {
                        items.add(new SelectItem(obj, obj.getNombre()));
                    }
                } else {
                    Jornada obj = new Jornada(0);
                    items.add(new SelectItem(obj, "NO EXISTEN JORNADAS"));
                }

            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CantonBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
            model = (adm.listar("Carreras"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        object = new Carreras(0);
    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        try {
            //setModel(adm.listar("Carreras"));
            model = (adm.query("Select o from Carreras as o where o.nombre like '%" + textoBuscar + "%'"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    /**
     * propiedades
     *
     * @return
     */
    public List<Carreras> getModel() {
        if (model == null) {
            cargarDataModel();
        }
        return model;
    }

    public void setModel(List<Carreras> model) {
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

    public Carreras getObject() {
        if (object == null) {
            object = new Carreras(0);
        }
        return object;
    }

    public void setObject(Carreras object) {
        this.object = object;
    }

    public boolean getVerSubir() {
        return verSubir;
    }

    public void setVerSubir(boolean verSubir) {
        this.verSubir = verSubir;
    }

    public boolean getEsLogo() {
        return esLogo;
    }

    public void setEsLogo(boolean esLogo) {
        verSubir = true;
        this.esLogo = esLogo;
    }
}
