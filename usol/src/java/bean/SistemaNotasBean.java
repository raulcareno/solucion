/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.lang.reflect.Field;
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
import jcinform.persistencia.Notas;
import jcinform.persistencia.Periodos;
import jcinform.persistencia.SistemaNotas;
import jcinform.procesos.Administrador;
 
import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class SistemaNotasBean {

    /**
     * Creates a new instance of SistemaNotasBean
     */
    SistemaNotas object;
    Administrador adm;
    protected List<SistemaNotas> model;
    public String textoBuscar;
    Permisos permisos;
   Auditar  aud = new Auditar();
                Periodos per;
    public SistemaNotasBean() {
        //super();
        FacesContext context = FacesContext.getCurrentInstance();
//        String s = context.getExternalContext().getRequestParameterMap().get("skp");
//        if (s != null) {
//            System.out.println(s);
//        }
         per = (Periodos) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodo");
        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("SistemaNotas", "ingresar_sistemaNotas.jspx", "ingresar", true, "NOTAS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/universidad/noPuedeIngresar.jspx");
            } //selectedSistemaNotas = new SistemaNotas();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(SistemaNotasBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(SistemaNotasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        inicializar();
        //selectedSistemaNotas = new SistemaNotas();

    }

    public String editarAction(SistemaNotas obj) {
        inicializar();

        object = obj;
        System.out.println("" + object.getIdSistemaNotas());
        return null;
    }

    protected void inicializar() {
        object = new SistemaNotas(0);
        object.setNota(disponible());
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

        object.setIdPeriodos(per);
        if (object.getIdSistemaNotas() == 0) {
            
            if (!permisos.verificarPermisoReporte("SistemaNotas", "agregar_sistemaNotas.jspx", "agregar", true, "NOTAS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                if (adm.existe("SistemaNotas", "nombre", object.getNombre(),"idPeriodos",per,"").size() <= 0) {
                    object.setIdSistemaNotas(adm.getNuevaClave("SistemaNotas", "idSistemaNotas"));
                    adm.guardar(object);
                    aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdSistemaNotas()+"");
                    inicializar();
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
                } else {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nombre ya existe...!","Nombre ya existe...!"));
                }
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }
        } else {
            if (!permisos.verificarPermisoReporte("SistemaNotas", "actualizar_sistemaNotas.jspx", "agregar", true, "NOTAS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            try {
                adm.actualizar(object);
                aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdSistemaNotas()+"");
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
                inicializar();
            } catch (Exception e) {
                //log.error("grabarAction() {} ", e.getMessage());
                java.util.logging.Logger.getLogger(SistemaNotasBean.class.getName()).log(Level.SEVERE, null, e);
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
            }

        }

        return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(SistemaNotas obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("SistemaNotas", "eliminar_sistemaNotas.jspx", "eliminar", true, "NOTAS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
            }
            adm.eliminarObjeto(SistemaNotas.class, obj.getIdSistemaNotas());
            aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "eliminar", "", obj.getIdSistemaNotas()+"");
            inicializar();
            cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(SistemaNotasBean.class.getName()).log(Level.SEVERE, null, e);
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
            List<SistemaNotas> datos = adm.query("Select o from SistemaNotas as o ORDER BY o.idSistemaNotas");
            List<SelectItem> items = new ArrayList<SelectItem>();
            for (SistemaNotas obj : datos) {
                items.add(new SelectItem(obj, obj.getNombre()));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(SistemaNotasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
            //model = (adm.listar("SistemaNotas"));
            model = adm.query("Select o from SistemaNotas as o where o.idPeriodos.idPeriodos = '"+per.getIdPeriodos()+"' ");
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(SistemaNotasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        object = new SistemaNotas(0);
        object.setNota(disponible());
    }
public String disponible() {
        Field[] a = Notas.class.getDeclaredFields();
        ArrayList arregloTodos = new ArrayList();
 
        for (Field field : a) {
            if (field.getName().contains("nota") && !field.getName().equals("id_notas")) {
                arregloTodos.add(field.getName());
            }
        }
       List<SistemaNotas> notassistemas = adm.query("Select o from SistemaNotas as o where o.idPeriodos.idPeriodos = '" + per.getIdPeriodos()  + "'");
        
        ArrayList arregloAsignadas = new ArrayList();
        for (SistemaNotas notanotas : notassistemas) {
            arregloAsignadas.add(notanotas.getNota());
        }

        for (Iterator<SistemaNotas> it = notassistemas.iterator(); it.hasNext();) {
        SistemaNotas sistemaNotas = it.next();
         String aborrar = sistemaNotas.getNota();
            int i = arregloTodos.indexOf(aborrar);
            //alert(i);
            if (i != -1) {
                arregloTodos.remove(i);
            }
        }
 
        return arregloTodos.get(0).toString();

    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        try {
            //setModel(adm.listar("SistemaNotas"));
            model = (adm.query("Select o from SistemaNotas as o where o.nombre like '%" + textoBuscar + "%'"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(SistemaNotasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    /**
     * propiedades
     *
     * @return
     */
    public List<SistemaNotas> getModel() {
        if (model == null) {
            cargarDataModel();
        }
        return model;
    }

    public void setModel(List<SistemaNotas> model) {
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

    public SistemaNotas getObject() {
        if (object == null) {
            object = new SistemaNotas(0);
        }
        return object;
    }

    public void setObject(SistemaNotas object) {
        this.object = object;
    }
}
