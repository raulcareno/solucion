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
import jcinform.persistencia.Ejes;
import jcinform.procesos.Administrador;
 
import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class EjesBean {

    /**
     * Creates a new instance of EjesBean
     */
    Ejes object;
    Administrador adm;
    protected List<Ejes> model;
    public String textoBuscar;
    Permisos permisos;
   Auditar  aud = new Auditar();

    public EjesBean() {
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
        if (!permisos.verificarPermisoReporte("Ejes", "ingresar_ejes.jspx", "ingresar", true, "PARAMETROS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("noPuedeIngresar.jspx");
            } //selectedEjes = new Ejes();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(EjesBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(EjesBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //selectedEjes = new Ejes();

    }

    public String editarAction(Ejes obj) {
        inicializar();

        object = obj;
        System.out.println("" + object.getIdEjes());
        return null;
    }

    protected void inicializar() {
        object = new Ejes(0);
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
        if (object.getIdEjes() == 0) {
            if (!permisos.verificarPermisoReporte("Ejes", "agregar_ejes.jspx", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                if (adm.existe("Ejes", "nombre", object.getNombre()).size() <= 0) {
                    object.setIdEjes(adm.getNuevaClave("Ejes", "idEjes"));
                    adm.guardar(object);
                    aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdEjes()+"");
                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nombre ya existe...!","Nombre ya existe...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
            if (!permisos.verificarPermisoReporte("Ejes", "actualizar_ejes.jspx", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                adm.actualizar(object);
                aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdEjes()+"");
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(EjesBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }

        return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(Ejes obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Ejes", "eliminar_ejes.jspx", "eliminar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            adm.eliminarObjeto(Ejes.class, obj.getIdEjes());
            aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "eliminar", "", obj.getIdEjes()+"");
            inicializar();
            cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(EjesBean.class.getName()).log(Level.SEVERE, null, e);
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
            List<Ejes> datos = adm.query("Select o from Ejes as o");
            List<SelectItem> items = new ArrayList<SelectItem>();
            for (Ejes obj : datos) {
                items.add(new SelectItem(obj, obj.getNombre()));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EjesBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
            model = (adm.listar("Ejes"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EjesBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        object = new Ejes(0);
    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        try {
            //setModel(adm.listar("Ejes"));
            model = (adm.query("Select o from Ejes as o where o.nombre like '%" + textoBuscar + "%'"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(EjesBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    /**
     * propiedades
     *
     * @return
     */
    public List<Ejes> getModel() {
        if (model == null) {
            cargarDataModel();
        }
        return model;
    }

    public void setModel(List<Ejes> model) {
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

    public Ejes getObject() {
        if (object == null) {
            object = new Ejes(0);
        }
        return object;
    }

    public void setObject(Ejes object) {
        this.object = object;
    }
}
