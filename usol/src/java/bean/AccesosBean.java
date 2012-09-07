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
import jcinform.persistencia.Accesos;
import jcinform.persistencia.Pais;
import jcinform.persistencia.Perfiles;
import jcinform.persistencia.Provincia;
import jcinform.procesos.Administrador;
 
import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
//@RequestScoped
public class AccesosBean {

    /**
     * Creates a new instance of AccesosBean
     */
    Accesos object;
    Administrador adm;
    protected List<Accesos> model;
    public String textoBuscar;
    Permisos permisos;
    Perfiles perfil;
    String modulo;
 Auditar  aud = new Auditar();

    public AccesosBean() {
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
        if (!permisos.verificarPermisoReporte("Accesos", "ingresar_accesos", "ingresar", true, "PARAMETROS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("noPuedeIngresar.jspx");
            } //selectedAccesos = new Accesos();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     //   inicializar();
        //selectedAccesos = new Accesos();
          ingresar = true;
        modificar = true;
        eliminar = true;
        agregar = true;
perfil = new Perfiles();
modulo = "";
    }

    public String editarAction(Accesos obj) {
        inicializar();
        object = obj;
        ingresar = true;
        modificar = true;
        eliminar = true;
        agregar = true;
         
        return null;
    }

    protected void inicializar() {
        object = new Accesos(0);
         
        cargarDataModel();
    }

    /**
     * Graba el registro asociado al objeto que
     */
    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
 
            if (!permisos.verificarPermisoReporte("Accesos", "actualizar_accesos", "agregar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                
                for (Iterator<Accesos> it = model.iterator(); it.hasNext();) {
                    Accesos accesos = it.next();
                    accesos.setIngresar(ingresar);
                    adm.actualizar(accesos);
                }
                
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                aud.auditar(adm,"Accesos", "actualizar", "", object.getIdAccesos()+"");
                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

  

        return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(Accesos obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Accesos", "eliminar_accesos", "eliminar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            adm.eliminarObjeto(Accesos.class, obj.getIdAccesos());
            aud.auditar(adm,"Accesos", "eliminar", "", obj.getIdAccesos()+"");
            inicializar();
            cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, e);
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return null;
    }

    /**
     *
     * Obtiene el registro seleccionado
     */
    public List<SelectItem> getSelectedItemPerfiles() {
        try {
            List<Perfiles> datos = adm.query("Select o from Perfiles as o");
            List<SelectItem> items = new ArrayList<SelectItem>();
            for (Perfiles obj : datos) {
                items.add(new SelectItem(obj, obj.getNombre()));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }
    List<SelectItem> provinciasEncontradas ;

    public List<SelectItem> getProvinciasEncontradas() {
        return provinciasEncontradas;
    }

    public void setProvinciasEncontradas(List<SelectItem> provinciasEncontradas) {
        this.provinciasEncontradas = provinciasEncontradas;
    }
    
    /**
     * Obtiene el el listado de provincias
     * @return 
     */
     public void buscarProvincia() {
        try {
            List<Provincia> divisionPoliticas = new ArrayList<Provincia>();
            provinciasEncontradas = new ArrayList<SelectItem>();
            
            if (object == null) {
                object = new Accesos();
             }
            if (object != null) {
//                if (!object.getIdAccesos().equals("")) {
                    try {
                       divisionPoliticas = adm.query("Select o from Provincia as o where o.idPais.idPais= '"+paisSeleccionado.getIdPais()+"' order by o.nombre ");
                        if(divisionPoliticas.size()>0){
                            Provincia objSel = new Provincia(0);
                            provinciasEncontradas.add(new SelectItem(objSel, "Seleccione..."));
                        for (Provincia obj : divisionPoliticas) {
                            provinciasEncontradas.add(new SelectItem(obj, obj.getNombre()));
                        }
                    }else{
                        Provincia obj = new Provincia(0);
                        provinciasEncontradas.add(new SelectItem(obj, "No ha seleccionado el País"));
                    } 
                    } catch (Exception e) {
                        Provincia obj = new Provincia(0);
                        provinciasEncontradas.add(new SelectItem(obj, "No ha seleccionado el País"));
                    }
                    
//                }
            }
//            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, e);
        }
//        return null;
    }
     /**
     * Obtiene el el listado de paises
     * @return 
     */
     public Boolean ingresar;
     public void seleccionarIngresar(){
         for (Iterator<Accesos> it = model.iterator(); it.hasNext();) {
             Accesos accesos = it.next();
             accesos.setIngresar(ingresar);
         }
     }
     
     public Boolean agregar;
     public void seleccionarAgregar(){
         for (Iterator<Accesos> it = model.iterator(); it.hasNext();) {
             Accesos accesos = it.next();
             accesos.setAgregar(agregar);
         }
     }
     
     public Boolean eliminar;
     public void seleccionarEliminar(){
         for (Iterator<Accesos> it = model.iterator(); it.hasNext();) {
             Accesos accesos = it.next();
             accesos.setEliminar(eliminar);
         }
     }
     
     public Boolean modificar;
     public void seleccionarModificar(){
         for (Iterator<Accesos> it = model.iterator(); it.hasNext();) {
             Accesos accesos = it.next();
             accesos.setModificar(modificar);
         }
     }
     
       
 public List<SelectItem> getSelectedItemPais() {
        try {
            List<Pais> divisionPoliticas = new ArrayList<Pais>();
            List<SelectItem> items = new ArrayList<SelectItem>();
              if (object == null) {
                object = new Accesos();
            }
            if (object != null) {
               
                    divisionPoliticas = adm.query("Select o from Pais as o order by o.nombre ");
                    if(divisionPoliticas.size()>0){
                        Pais objSel = new Pais(0);
                        items.add(new SelectItem(objSel, "Seleccione..."));
                    for (Pais obj : divisionPoliticas) {
                        items.add(new SelectItem(obj, obj.getNombre()));
                    }
                    }else{
                        Pais obj = new Pais(0);
                        items.add(new SelectItem(obj, "NO EXISTEN PAISES"));
                    }
                
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
            String query = "Select o from Accesos as o  "
                    + " where o.idPerfiles.idPerfiles = '"+perfil.getIdPerfiles()+"' "
                    + "and o.modulo = '"+modulo+"'  ";
            if(modulo.toLowerCase().equals("todos")){
             query = "Select o from Accesos as o  "
                    + " where o.idPerfiles.idPerfiles = '"+perfil.getIdPerfiles()+"' ";
            }
            model = (adm.query(query));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        object = new Accesos(0);
    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        try {
            //setModel(adm.listar("Accesos"));
            model = (adm.query("Select o from Accesos as o where o.nombre like '%" + textoBuscar + "%'"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(AccesosBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }
public Pais paisSeleccionado= new Pais();
public Provincia provinciaSeleccionado= new Provincia();

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

    

    /**
     * propiedades
     *
     * @return
     */
    public List<Accesos> getModel() {
//        if (model == null) {
//            cargarDataModel();
//        }
        return model;
    }

    public void setModel(List<Accesos> model) {
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

    public Accesos getObject() {
        if (object == null) {
            object = new Accesos(0);
        }
        return object;
    }

    public void setObject(Accesos object) {
        this.object = object;
    }

    public Boolean getIngresar() {
        
        return ingresar;
    }

    public void setIngresar(Boolean ingresar) {
        
        this.ingresar = ingresar;
//        seleccionarIngresar();
    }

    public Boolean getAgregar() {
        return agregar;
    }

    public void setAgregar(Boolean agregar) {
        this.agregar = agregar;
    }

    public Boolean getEliminar() {
        return eliminar;
    }

    public void setEliminar(Boolean eliminar) {
        this.eliminar = eliminar;
    }

    public Boolean getModificar() {
        return modificar;
    }

    public void setModificar(Boolean modificar) {
        this.modificar = modificar;
    }

    public Perfiles getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfiles perfil) {
        this.perfil = perfil;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
    
    
}
