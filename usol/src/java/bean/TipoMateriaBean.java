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
import jcinform.persistencia.TipoMateria;
import jcinform.procesos.Administrador;
 
import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class TipoMateriaBean {

    /**
     * Creates a new instance of TipoMateriaBean
     */
    TipoMateria object;
    Administrador adm;
    protected List<TipoMateria> model;
    public String textoBuscar;
    Permisos permisos;
   Auditar  aud = new Auditar();

    public TipoMateriaBean() {
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
        if (!permisos.verificarPermisoReporte("TipoMateria", "ingresar_tipomateria.jspx", "ingresar", true, "PARAMETROS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("noPuedeIngresar.jspx");
            } //selectedTipoMateria = new TipoMateria();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(TipoMateriaBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(TipoMateriaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //selectedTipoMateria = new TipoMateria();

    }

    public String editarAction(TipoMateria obj) {
//        inicializar();

        object = obj;
        System.out.println("" + object.getIdTipoMateria());
        return null;
    }

    protected void inicializar() {
        object = new TipoMateria(0);
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
        if (object.getIdTipoMateria() == 0) {
            if (!permisos.verificarPermisoReporte("TipoMateria", "agregar_tipomateria.jspx", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                if (adm.existe("TipoMateria", "nombre", object.getNombre()).size() <= 0) {
                    object.setIdTipoMateria(adm.getNuevaClave("TipoMateria", "idTipoMateria"));
                    adm.guardar(object);
                    aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdTipoMateria()+"");
                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Nombre ya existe...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
            if (!permisos.verificarPermisoReporte("TipoMateria", "actualizar_tipomateria.jspx", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                adm.actualizar(object);
                aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdTipoMateria()+"");
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(TipoMateriaBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }

        return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(TipoMateria obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("TipoMateria", "eliminar_tipomateria.jspx", "eliminar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            adm.eliminarObjeto(TipoMateria.class, obj.getIdTipoMateria());
            aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "eliminar", "", obj.getIdTipoMateria()+"");
            inicializar();
            cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(TipoMateriaBean.class.getName()).log(Level.SEVERE, null, e);
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
            List<TipoMateria> datos = adm.query("Select o from TipoMateria as o");
            List<SelectItem> items = new ArrayList<SelectItem>();
            for (TipoMateria obj : datos) {
                items.add(new SelectItem(obj, obj.getNombre()));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(TipoMateriaBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
            model = (adm.listar("TipoMateria"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(TipoMateriaBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        object = new TipoMateria(0);
    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        try {
            //setModel(adm.listar("TipoMateria"));
            model = (adm.query("Select o from TipoMateria as o where o.nombre like '%" + textoBuscar + "%'"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(TipoMateriaBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    /**
     * propiedades
     *
     * @return
     */
    public List<TipoMateria> getModel() {
        if (model == null) {
            cargarDataModel();
        }
        return model;
    }

    public void setModel(List<TipoMateria> model) {
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

    public TipoMateria getObject() {
        if (object == null) {
            object = new TipoMateria(0);
        }
        return object;
    }

    public void setObject(TipoMateria object) {
        this.object = object;
    }
}
