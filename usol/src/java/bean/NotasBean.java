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
import jcinform.persistencia.Aulas;
import jcinform.persistencia.Carreras;
import jcinform.persistencia.Horarios;
import jcinform.persistencia.Materias;
import jcinform.persistencia.Niveles;
import jcinform.persistencia.Notas;
import jcinform.procesos.Administrador;

import utilerias.Permisos;
import utilerias.secuencial;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class NotasBean {

    /**
     * Creates a new instance of NotasBean
     */
    Notas object;
    Administrador adm;
    protected List<Notas> model;
    public String textoBuscar;
    Permisos permisos;
    Auditar aud = new Auditar();

    public NotasBean() {
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
        if (!permisos.verificarPermisoReporte("Notas", "ingresar_pais", "ingresar", true, "PARAMETROS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("noPuedeIngresar.jspx");
            } //selectedNotas = new Notas();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(NotasBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(NotasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //selectedNotas = new Notas();

    }

    public String editarAction(Notas obj) {
        inicializar();

        object = obj;
        System.out.println("" + object.getIdNotas());
        return null;
    }

    protected void inicializar() {
        object = new Notas("0");
        cargarDataModel();
    }

    /**
     * Graba el registro asociado al objeto que
     */
    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
// if (object.getNombre().isEmpty()) {
//            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el NOMBRE", ""));
//            return null;
//        }
//        if (object.getIdNotas() == 0) {
        if (!permisos.verificarPermisoReporte("Notas", "agregar_pais", "agregar", true, "PARAMETROS")) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));
            return null;
        }
        try {
            object.setIdNotas(secuencial.generarClave());
            adm.guardar(object);
            aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdNotas() + "");
            inicializar();
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
//        } else {
//            if (!permisos.verificarPermisoReporte("Notas", "actualizar_pais", "agregar", true, "PARAMETROS")) {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
//            }
//            try {
//                adm.actualizar(object);
//                aud.auditar(adm,this.getClass().getSimpleName().replace("Bean", ""), "actualizar", "", object.getIdNotas()+"");
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Actualizado Correctamente...!"));
//                inicializar();
//            } catch (Exception e) {
//                //log.error("grabarAction() {} ", e.getMessage());
//                java.util.logging.Logger.getLogger(NotasBean.class.getName()).log(Level.SEVERE, null, e);
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
//            }
//
//        }

        return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(Notas obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Notas", "eliminar_pais", "eliminar", true, "PARAMETROS")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));
                return null;
            }
            adm.eliminarObjeto(Notas.class, obj.getIdNotas());
            aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "eliminar", "", obj.getIdNotas() + "");
            inicializar();
            cargarDataModel();
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Eliminado...!"));
        } catch (Exception e) {
            //log.error("eliminarAction() {} ", e.getMessage());
            java.util.logging.Logger.getLogger(NotasBean.class.getName()).log(Level.SEVERE, null, e);
            context.addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }
        return null;
    }

    /**
     *
     * Obtiene el registro seleccionado
     */
    public List<SelectItem> getSelectedItem() {
//        try {
//            List<Notas> datos = adm.query("Select o from Notas as o");
//            List<SelectItem> items = new ArrayList<SelectItem>();
//            for (Notas obj : datos) {
//                items.add(new SelectItem(obj, obj.getNombre()));
//            }
//            return items;
//        } catch (Exception e) {
//            java.util.logging.Logger.getLogger(NotasBean.class.getName()).log(Level.SEVERE, null, e);
//            System.out.println(e.getMessage());
//        }
        return null;
    }
    
     public List<SelectItem> getSelectedItemNiveles() {
        try {
            List<Niveles> divisionPoliticas = new ArrayList<Niveles>();
            List<SelectItem> items = new ArrayList<SelectItem>();


            divisionPoliticas = adm.query("Select o from Niveles as o order by o.secuencia ");
            if (divisionPoliticas.size() > 0) {
                Niveles objSel = new Niveles(0);
                items.add(new SelectItem(objSel, "Seleccione..."));
                for (Niveles obj : divisionPoliticas) {
                    items.add(new SelectItem(obj, obj.getNombre()));
                }
            } else {
                Niveles obj = new Niveles(0);
                items.add(new SelectItem(obj, "NO EXISTEN NIVELES"));
            }


            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Horarios.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<SelectItem> getSelectedItemAulas() {
        try {
            List<Aulas> divisionPoliticas = new ArrayList<Aulas>();
            List<SelectItem> items = new ArrayList<SelectItem>();


            divisionPoliticas = adm.query("Select o from Aulas as o order by o.nombre ");
            if (divisionPoliticas.size() > 0) {
                Aulas objSel = new Aulas(0);
                items.add(new SelectItem(objSel, "Seleccione..."));
                for (Aulas obj : divisionPoliticas) {
                    items.add(new SelectItem(obj, obj.getNombre()));
                }
            } else {
                Aulas obj = new Aulas(0);
                items.add(new SelectItem(obj, "NO EXISTEN NIVELES"));
            }


            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Horarios.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
     public List<SelectItem> getSelectedItemMaterias() {
        try {
            List<Materias> divisionPoliticas = new ArrayList<Materias>();
            List<SelectItem> items = new ArrayList<SelectItem>();


            divisionPoliticas = adm.query("Select o from Materias as o order by o.nombre ");
            if (divisionPoliticas.size() > 0) {
                Materias objSel = new Materias(0);
                items.add(new SelectItem(objSel, "Seleccione..."));
                for (Materias obj : divisionPoliticas) {
                    items.add(new SelectItem(obj, obj.getNombre()));
                }
            } else {
                Materias obj = new Materias(0);
                items.add(new SelectItem(obj, "NO EXISTEN NIVELES"));
            }


            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Horarios.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
    
    public List<SelectItem> getSelectedItemCarreras() {
        try {
            List<Carreras> divisionPoliticas = new ArrayList<Carreras>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            divisionPoliticas = adm.query("Select o from Carreras as o order by o.nombre ");
            if (divisionPoliticas.size() > 0) {
                Carreras objSel = new Carreras(0);
                items.add(new SelectItem(objSel, "Seleccione..."));
                for (Carreras obj : divisionPoliticas) {
                    items.add(new SelectItem(obj, obj.getNombre()));
                }
            } else {
                Carreras obj = new Carreras(0);
                items.add(new SelectItem(obj, "NO EXISTEN CARRERAS"));
            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }


    /**
     * llena el listado con datos
     */
    public void cargarDataModel() {
        try {
            model = (adm.listar("Notas"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(NotasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    public void limpiar() {
        object = new Notas("0");
    }

    /**
     * busca según criterio textoBuscar
     */
    public void buscar() {
        try {
            //setModel(adm.listar("Notas"));
            model = (adm.query("Select o from Notas as o where o.nombre like '%" + textoBuscar + "%'"));
            setModel(model);

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(NotasBean.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("" + e);
        }
    }

    /**
     * propiedades
     *
     * @return
     */
    public List<Notas> getModel() {
        if (model == null) {
            cargarDataModel();
        }
        return model;
    }

    public void setModel(List<Notas> model) {
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

    public Notas getObject() {
        if (object == null) {
            object = new Notas("0");
        }
        return object;
    }

    public void setObject(Notas object) {
        this.object = object;
    }
}
