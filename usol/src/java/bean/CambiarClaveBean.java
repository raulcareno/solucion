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
import jcinform.persistencia.Canton;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.EmpleadosMaterias;
import jcinform.persistencia.Materias;
import jcinform.persistencia.Pais;
import jcinform.persistencia.Perfiles;
import jcinform.persistencia.Provincia;
import jcinform.persistencia.Titulos;
import jcinform.procesos.Administrador;
import jcinform.procesos.claves;
import miniaturas.ProcesadorImagenes;
import org.primefaces.model.DualListModel;
//import org.primefaces.event.TransferEvent;
import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
//@RequestScoped
public class CambiarClaveBean{

    /**
     * Creates a new instance of EmpleadosBean
     */
    Empleados object;
    Administrador adm;
    protected List<Empleados> model;
    protected List<EmpleadosMaterias> modelMaterias;
    public String textoBuscar;
    Permisos permisos;
    protected Titulos titulosSeleccionado = new Titulos();
    protected Perfiles perfilesSeleccioando = new Perfiles();
    public String foto1;
    protected String clave2;
    claves cl = new claves();
    List<SelectItem> provinciasEncontradas;
    List<SelectItem> cantonesEncontradas;
    public Pais paisSeleccionado = new Pais();
    public Provincia provinciaSeleccionado = new Provincia();
    public Canton cantonSeleccionado = new Canton();
    public Pais paisSeleccionado3 = new Pais();
    public Pais paisSeleccionado4 = new Pais();
    Auditar aud = new Auditar();
    ProcesadorImagenes p = new ProcesadorImagenes();
    
     private DualListModel<Materias> materias;  
     List<Materias> origen = new ArrayList<Materias>();  
        List<Materias> destino = new ArrayList<Materias>();  
     
     
    public CambiarClaveBean() {
        //super();
        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("Empleados", "ingresar_empleados.jspx", "ingresar", true, "ADMINISTRACION")) {
            try {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/noPuedeIngresar.jspx");
            } //selectedEmpleados = new Empleados();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(CambiarClaveBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(EmpleadosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
        inicializar();
        //selectedEmpleados = new Empleados();

    }
    public void buscarMateriasNoAsignadas(){
        origen =  adm.queryNativo(" Select o.* from Materias as o "
                + "where o.id_materias not in (Select m.id_Materias from Empleados_Materias as m where m.id_Empleados = '"+object.getIdEmpleados()+"') "
                + " order by o.nombre ",Materias.class);
        destino =  adm.query("Select m.idMaterias from EmpleadosMaterias as m where m.idEmpleados.idEmpleados = '"+object.getIdEmpleados()+"' "
                + " order by m.idMaterias.nombre ");
    }

    public String editarAction(Empleados obj) {
        inicializar();
        object = obj;
        obj.setClave(cl.desencriptar(obj.getClave()));
        clave2 = obj.getClave();
 
        System.out.println("" + object.getIdEmpleados());
        return null;
    }
    
    protected void inicializar() {
        claveActual = "";
        object = new Empleados(0);
        object.setSexo("M");
        object.setTipoIdentificacion("C");
        foto1 = null;
        clave2 = "";
        origen =  adm.query(" Select o from Materias as o order by o.nombre ");
        destino = new ArrayList<Materias>();
        //materias = new DualListModel<Materias>(origen, destino); 
        cargarDataModel();
    }

     public String anadir(Materias obj) {  
        destino.add(obj);  
        origen.remove(obj);  
        return null;
    }
     public String quitar(Materias obj) {  
        origen.add(obj);  
        destino.remove(obj);  
        return null;
    }
    
    /**
     * Graba el registro asociado al objeto que
     */
    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
       if(!comparar(claveActual)){
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "La Clave Ingresada no Coincide con la clave actual...!", ""));
            return null;       
       }
        if (object.getClave().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese la Nueva Clave", ""));
            return null;
        }
        String nuevaClave = object.getClave();
        String nuevoUsuario = object.getUsuario();
        object = (Empleados) adm.buscarClave(object.getIdEmpleados(), object.getClass());
        object.setClave(cl.encriptar(nuevaClave));
        object.setUsuario(nuevoUsuario); 
        

 
            try {
                adm.actualizar(object);
                object.setClave(cl.desencriptar(object.getClave()));
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user",object);
                aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "cambioClave", "", object.getIdEmpleados() + "");
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(CambiarClaveBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
            
 
        
        return null;
    }
    public String claveActual;

    public String getClaveActual() {
        return claveActual;
    }

    public void setClaveActual(String claveActual) {
        this.claveActual = claveActual;
    }
    
    boolean comparar(String claveEnviada){
            if(object.getClave().equals(claveEnviada)){
                return true;
            }else{
                    return false;
            }
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(Empleados obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Empleados", "eliminar_empleados.jspx", "eliminar", true, "ADMINISTRACION")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            adm.eliminarObjeto(Empleados.class, obj.getIdEmpleados());
            aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "eliminar", "", obj.getIdEmpleados() + "");
            inicializar();
            cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(CambiarClaveBean.class.getName()).log(Level.SEVERE, null, e);
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
            List<Empleados> datos = adm.query("Select o from Empleados as o");
            List<SelectItem> items = new ArrayList<SelectItem>();
            for (Empleados obj : datos) {
                items.add(new SelectItem(obj, obj.getNombre()));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CambiarClaveBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
           object = (Empleados) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
 
           object.setClave(cl.desencriptar(object.getClave()));
          clave2 = object.getClave();
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CambiarClaveBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }
    
    public void limpiar() {
        //object = new Empleados(0);
        inicializar();
    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        try {
            //setModel(adm.listar("Empleados"));
            model = (adm.query("Select o from Empleados as o where o.nombre like '%" + textoBuscar + "%' order by o.nombre "));
           
            
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CambiarClaveBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
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
    
    public Empleados getObject() {
        if (object == null) {
            object = new Empleados(0);
        }
        return object;
    }
    
    public void setObject(Empleados object) {
        this.object = object;
    }
    
    public Titulos getTitulosSeleccionado() {
        return titulosSeleccionado;
    }
    
    public void setTitulosSeleccionado(Titulos titulosSeleccionado) {
        this.titulosSeleccionado = titulosSeleccionado;
    }
    
    public Perfiles getPerfilesSeleccioando() {
        return perfilesSeleccioando;
    }
    
    public void setPerfilesSeleccioando(Perfiles perfilesSeleccioando) {
        this.perfilesSeleccioando = perfilesSeleccioando;
    }
    
    public String getFoto1() {
        return foto1;
    }
    
    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }
    
    public String getClave2() {
        return clave2;
    }
    
    public void setClave2(String clave2) {
        this.clave2 = clave2;
    }
    
    public List<SelectItem> getProvinciasEncontradas() {
        return provinciasEncontradas;
    }
    
    public void setProvinciasEncontradas(List<SelectItem> provinciasEncontradas) {
        this.provinciasEncontradas = provinciasEncontradas;
    }
    
    public List<SelectItem> getCantonesEncontradas() {
        return cantonesEncontradas;
    }
    
    public void setCantonesEncontradas(List<SelectItem> cantonesEncontradas) {
        this.cantonesEncontradas = cantonesEncontradas;
    }
    
    public Pais getPaisSeleccionado() {
        return paisSeleccionado;
    }
    
    public void setPaisSeleccionado(Pais paisSeleccionado) {
        this.paisSeleccionado = paisSeleccionado;
    }
    
    public Provincia getProvinciaSeleccionado() {
        return provinciaSeleccionado;
    }
    
    public void setProvinciaSeleccionado(Provincia provinciaSeleccionado) {
        this.provinciaSeleccionado = provinciaSeleccionado;
    }
    
    public Canton getCantonSeleccionado() {
        return cantonSeleccionado;
    }
    
    public void setCantonSeleccionado(Canton cantonSeleccionado) {
        this.cantonSeleccionado = cantonSeleccionado;
    }

    public Pais getPaisSeleccionado3() {
        return paisSeleccionado3;
    }

    public void setPaisSeleccionado3(Pais paisSeleccionado3) {
        this.paisSeleccionado3 = paisSeleccionado3;
    }

    public Pais getPaisSeleccionado4() {
        return paisSeleccionado4;
    }

    public void setPaisSeleccionado4(Pais paisSeleccionado4) {
        this.paisSeleccionado4 = paisSeleccionado4;
    }

    public DualListModel<Materias> getMaterias() {
        return materias;
    }

    public void setMaterias(DualListModel<Materias> materias) {
        this.materias = materias;
    }

    public List<Materias> getOrigen() {
        return origen;
    }

    public void setOrigen(List<Materias> origen) {
        this.origen = origen;
    }

    public List<Materias> getDestino() {
        return destino;
    }

    public void setDestino(List<Materias> destino) {
        this.destino = destino;
    }
    
    
}
