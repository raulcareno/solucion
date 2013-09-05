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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import jcinform.persistencia.Facultad;
import jcinform.persistencia.Escuela;
import jcinform.procesos.Administrador;

import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class EscuelaBean {

    /**
     * Creates a new instance of EscuelaBean
     */
    Escuela object;
    Administrador adm;
    protected List<Escuela> model;
    public String textoBuscar;
    Permisos permisos;
Auditar  aud = new Auditar();
    public EscuelaBean() {
        //super();
        FacesContext context = FacesContext.getCurrentInstance();
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
        if (!permisos.verificarPermisoReporte("Escuela", "ingresar_escuela.jspx", "ingresar", true, "PARAMETROS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("noPuedeIngresar.jspx");
            } //selectedEscuela = new Escuela();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(EscuelaBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(EscuelaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        inicializar();
        //selectedEscuela = new Escuela();

    }

    public String editarAction(Escuela obj) {
        //inicializar();
        object = obj;
        System.out.println("" + object.getIdEscuela());
        return null;
    }

    protected void inicializar() {
        object = new Escuela(0);
        object.setIdFacultad(new Facultad());
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
        if (object.getIdEscuela() == 0) {
            if (!permisos.verificarPermisoReporte("Escuela", "agregar_escuela.jspx", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                if (adm.existe("Escuela", "nombre", object.getNombre()).size() <= 0) {
                    object.setIdEscuela(adm.getNuevaClave("Escuela", "idEscuela"));
                    adm.guardar(object);
                    aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdEscuela()+"");
                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nombre ya existe...!","Nombre ya existe...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
            if (!permisos.verificarPermisoReporte("Escuela", "actualizar_escuela.jspx", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                adm.actualizar(object);
                aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdEscuela()+"");
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(EscuelaBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }

        return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(Escuela obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Escuela", "eliminar_escuela.jspx", "eliminar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            adm.eliminarObjeto(Escuela.class, obj.getIdEscuela());
            aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""),  "eliminar", "", object.getIdEscuela()+"");
            inicializar();
            cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(EscuelaBean.class.getName()).log(Level.SEVERE, null, e);
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
            List<Escuela> datos = adm.query("Select o from Escuela as o");
            List<SelectItem> items = new ArrayList<SelectItem>();
            for (Escuela obj : datos) {
                items.add(new SelectItem(obj, obj.getNombre()));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EscuelaBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
            model = (adm.listar("Escuela"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EscuelaBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        object = new Escuela(0);
    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        try {
            //setModel(adm.listar("Escuela"));
            model = (adm.query("Select o from Escuela as o where o.nombre like '%" + textoBuscar + "%' order by o.nombre "));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EscuelaBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public List<SelectItem> getSelectedItemFacultad() {
        try {
            List<Facultad> divisionPoliticas = new ArrayList<Facultad>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object != null) {
                if (!object.getIdEscuela().equals("")) {
                    divisionPoliticas = adm.query("Select o from Facultad as o order by o.nombre ");
                    if (divisionPoliticas.size() > 0) {
                        Facultad objSel = new Facultad(0);
                        items.add(new SelectItem(objSel, "Seleccione..."));
                        for (Facultad obj : divisionPoliticas) {
                            items.add(new SelectItem(obj, obj.getNombre()));
                        }
                    } else {
                        Facultad obj = new Facultad(0);
                        items.add(new SelectItem(obj, "NO EXISTEN PAISES"));
                    }
                }
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EscuelaBean.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

    /**
     * propiedades
     *
     * @return
     */
    public List<Escuela> getModel() {
        if (model == null) {
            cargarDataModel();
        }
        return model;
    }

    public void setModel(List<Escuela> model) {
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

    public Escuela getObject() {
        if (object == null) {
            object = new Escuela(0);
        }
        return object;
    }

    public void setObject(Escuela object) {
        this.object = object;
    }
}
