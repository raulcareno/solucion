
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
import javax.servlet.http.HttpServletRequest;
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
    public String auditar(String pantalla, String accion,String registros,String codigosmodificados) {
        FacesContext context = FacesContext.getCurrentInstance();
            object = new Auditoria();
            try {
            String ipRemota  = ((HttpServletRequest) context.getExternalContext().getRequest()).getRemoteAddr();
            String ipHost = ((HttpServletRequest) context.getExternalContext().getRequest()).getRemoteHost();
            String ipUsuario = ((HttpServletRequest) context.getExternalContext().getRequest()).getRemoteUser();
            
                System.out.println("ipusuario"+ipUsuario);
                System.out.println("ipremota;: "+ipRemota);
                System.out.println("host:"+ipHost);
            object.setAccion(accion);
            object.setDescripcion("");
            object.setFecha(adm.Date());
            Empleados empleadoAc = (Empleados) context.getExternalContext().getSessionMap().get("user");
            object.setIdEmpleados(empleadoAc);
            object.setIp(ipRemota);
            object.setPc(ipHost);
            object.setTabla(pantalla);
            object.setRegistro(registros);
            object.setCodigosmodificados(codigosmodificados); 
            
            
                    object.setIdAuditoria(adm.getNuevaClave("Auditoria", "idAuditoria"));
                    adm.guardar(object);
                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
  
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
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
            
            Empleados empTodos = new Empleados(-1);
            empTodos.setApellidoPaterno("Todos");
            empTodos.setNombre("");
            
            items.add(new SelectItem(empTodos, "Todos.."));
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
            model = (adm.query("Select o from Auditoria as o where o.idAuditoria = 0 "));
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
            if(empleado.getIdEmpleados().equals(new Integer("-1"))){
                complemento = "";
            }
            String fechaI = (desde.getYear()+1900)+"-"+(desde.getMonth()+1)+"-"+desde.getDate() +" 00:00:01";
            String fechaF = (hasta.getYear()+1900)+"-"+(hasta.getMonth()+1)+"-"+hasta.getDate() +" 23:59:59";
            
            model = (adm.query("Select o from Auditoria as o where o.fecha  between  '"+fechaI+"' and '"+fechaF+"' " + complemento));
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
//        if (model == null) {
//            cargarDataModel();
//        }
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