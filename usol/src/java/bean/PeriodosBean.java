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
import jcinform.persistencia.Periodos;
import jcinform.procesos.Administrador;

import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class PeriodosBean {

    /**
     * Creates a new instance of PeriodosBean
     */
    Periodos object;
    Administrador adm;
    protected List<Periodos> model;
    public String textoBuscar;
    Permisos permisos;
    Auditar aud = new Auditar();

    public PeriodosBean() {
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
        if (!permisos.verificarPermisoReporte("Periodos", "ingresar_periodos.jspx", "ingresar", true, "PARAMETROS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("noPuedeIngresar.jspx");
            } //selectedPeriodos = new Periodos();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(PeriodosBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(PeriodosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //selectedPeriodos = new Periodos();

    }

    public String editarAction(Periodos obj) {
        inicializar();

        object = obj;
        System.out.println("" + object.getIdPeriodos());
        return null;
    }

    protected void inicializar() {
        object = new Periodos(0);
        cargarDataModel();
    }

    /**
     * Graba el registro asociado al objeto que
     */
    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
        if (object.getFechaInicio() == null) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la FECHA DE INICIO", ""));
            return null;
        }
        if (object.getFechaFin() == null) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la FECHA FINAL ", ""));
            return null;
        }
        System.out.println("fecha ini: " + object.getFechaInicio().getTime());
        System.out.println("fecha fin: " + object.getFechaFin().getTime());
        if (object.getFechaInicio().getTime() >= object.getFechaFin().getTime()) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "La FECHA DE INICIO no puede ser igual(=) o menor(<) a la FECHA FINAL", ""));
            return null;
        }
        if (object.getIdPeriodos() == 0) {
            if (!permisos.verificarPermisoReporte("Periodos", "agregar_periodos.jspx", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));
                return null;
            }
            try {
                if (adm.existe("Periodos", "fechaInicio", object.getFechaInicio(), "fechaInicio", object.getFechaFin(), "").size() <= 0) {
                    object.setIdPeriodos(adm.getNuevaClave("Periodos", "idPeriodos"));
                    adm.guardar(object);
                    if (object.getActivo()) {
                        estadoPeriodos(object.getIdPeriodos());
                    }
                    if (object.getProximo()) {
                        estadoPeriodosProximo(object.getIdPeriodos());
                    }
                    aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdPeriodos() + "");
                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Periodo ya existe...!", "Periodo ya existe...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
            if (!permisos.verificarPermisoReporte("Periodos", "actualizar_periodos.jspx", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));
                return null;
            }
            try {
                adm.actualizar(object);
                aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdPeriodos() + "");
                if (object.getActivo()) {
                    estadoPeriodos(object.getIdPeriodos());
                }
                if (object.getProximo()) {
                        estadoPeriodosProximo(object.getIdPeriodos());
                }
                
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(PeriodosBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }

        return null;
    }

    public void estadoPeriodos(Integer periodoActivo) {
        try {
            adm.ejecutaSql("Update Periodos set activo = 0 where idPeriodos != '" + periodoActivo + "' ");
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(PeriodosBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void estadoPeriodosProximo(Integer periodoActivo) {
        try {
            adm.ejecutaSql("Update Periodos set proximo = 0 where idPeriodos != '" + periodoActivo + "' ");
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(PeriodosBean.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(Periodos obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Periodos", "eliminar_periodos.jspx", "eliminar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));
                return null;
            }
            adm.eliminarObjeto(Periodos.class, obj.getIdPeriodos());
            aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "eliminar", "", obj.getIdPeriodos() + "");
            inicializar();
            cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(PeriodosBean.class.getName()).log(Level.SEVERE, null, e);
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
            List<Periodos> datos = adm.query("Select o from Periodos as o");
            List<SelectItem> items = new ArrayList<SelectItem>();
            for (Periodos obj : datos) {
                items.add(new SelectItem(obj, obj.getFechaInicio() + "-" + obj.getFechaFin()));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(PeriodosBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
            model = (adm.listar("Periodos"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(PeriodosBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        object = new Periodos(0);
    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        try {
            //setModel(adm.listar("Periodos"));
            model = (adm.query("Select o from Periodos as o  "));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(PeriodosBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    /**
     * propiedades
     *
     * @return
     */
    public List<Periodos> getModel() {
        if (model == null) {
            cargarDataModel();
        }
        return model;
    }

    public void setModel(List<Periodos> model) {
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

    public Periodos getObject() {
        if (object == null) {
            object = new Periodos(0);
        }
        return object;
    }

    public void setObject(Periodos object) {
        this.object = object;
    }
}
