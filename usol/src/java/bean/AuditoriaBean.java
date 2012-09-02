
package bean;
 import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import jcinform.persistencia.Auditoria;
import jcinform.persistencia.Empleados;
import jcinform.procesos.Administrador;

import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class AuditoriaBean {

    /**
     * Creates a new instance of AuditoriaBean
     */
    Auditoria object;
    Administrador adm;
    protected List<Auditoria> model;
    public String textoBuscar;
    Permisos permisos;
    boolean esLogo;
    boolean verSubir;

    public AuditoriaBean() {
        //super();
        FacesContext context = FacesContext.getCurrentInstance();
 
        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("Auditoria", "ingresar_auditoria", "ingresar", true, "PARAMETROS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("noPuedeIngresar.jspx");
            } //selectedAuditoria = new Auditoria();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(AuditoriaBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(AuditoriaBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //selectedAuditoria = new Auditoria();

    }

    public String editarAction(Auditoria obj) {
        inicializar();

        object = obj;
        return null;
    }

    protected void inicializar() {
        object = new Auditoria(0);
 
        cargarDataModel();
    }
    
    /**
     * Graba el registro asociado al objeto que
     */
    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
 
        if (object.getIdAuditoria() == 0) {
            try {
 
                    object.setIdAuditoria(adm.getNuevaClave("Auditoria", "idAuditoria"));
                    adm.guardar(object);
                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
  
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
             
            try {
                adm.actualizar(object);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(AuditoriaBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }

        return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(Auditoria obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Auditoria", "eliminar_auditoria", "eliminar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para realizar ésta acción"));
            }
            adm.eliminarObjeto(Auditoria.class, obj.getIdAuditoria());
            inicializar();
            cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(AuditoriaBean.class.getName()).log(Level.SEVERE, null, e);
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return null;
    }

    /**
     *
     * Obtiene el registro seleccionado
     */
    public List<SelectItem> getSelectedItemEmpleados() {
        try {
            List<Empleados> datos = adm.query("Select o from Empleados as o");
            List<SelectItem> items = new ArrayList<SelectItem>();
            
            Empleados empTodos = new Empleados("-1");
            empTodos.setApellidoPaterno("Todos");
            empTodos.setNombre("");
            datos.add(empTodos); 
            for (Empleados obj : datos) {
                items.add(new SelectItem(obj, obj.getApellidoPaterno()+" "+obj.getNombre()));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AuditoriaBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    
    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
            model = (adm.listar("Auditoria"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AuditoriaBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        object = new Auditoria(0);
    }
    
    Date desde = new Date();
    Date hasta = new Date();
    Empleados empleado = new Empleados();

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        try {
            //setModel(adm.listar("Auditoria"));
            String complemento = " and o.idEmpleados.idEmpleados = '"+empleado.getIdEmpleados()+"' ";
            if(complemento.equals("-1")){
                complemento = "";
            }
            String fechaI = (desde.getYear()+1900)+"-"+(desde.getMonth()+1)+"-"+desde.getDate();
            String fechaF = (hasta.getYear()+1900)+"-"+(hasta.getMonth()+1)+"-"+hasta.getDate();
            
            model = (adm.query("Select o from Auditoria as o where o.fecha  between  '"+fechaI+" and '"+fechaF+"' " + complemento));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AuditoriaBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    /**
     * propiedades
     *
     * @return
     */
    public List<Auditoria> getModel() {
        if (model == null) {
            cargarDataModel();
        }
        return model;
    }

    public void setModel(List<Auditoria> model) {
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

    public Auditoria getObject() {
        if (object == null) {
            object = new Auditoria(0);
        }
        return object;
    }

    public void setObject(Auditoria object) {
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

    public Date getDesde() {
        return desde;
    }

    public void setDesde(Date desde) {
        this.desde = desde;
    }

    public Date getHasta() {
        return hasta;
    }

    public void setHasta(Date hasta) {
        this.hasta = hasta;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }
    
    
}