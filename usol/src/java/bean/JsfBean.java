// 
//package com.apli.jsf.managed;
//
//import com.apli.controladores.AplinfoFactoryBean;
//import com.apli.ejb.interfaces.Administrador;
//import java.io.Serializable;
//import java.util.Iterator;
//import java.util.List;
//import java.util.ResourceBundle;
//import javax.ejb.EJB;
//import javax.faces.application.FacesMessage;
//import javax.faces.component.UIComponent;
//import javax.faces.context.FacesContext;
//import javax.faces.event.AjaxBehaviorEvent;
//import javax.faces.model.DataModel;
//import javax.faces.model.ListDataModel;
//import javax.faces.model.SelectItem;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
///**
// * Clase base JsfBean que implementa los métodos genéricos para administrar una
// * página JSF. <P> <H6>Soporte:APLINFO <I>mtupiza@gmail.com,
// * mconlago@gmail.com</I></H6>
// *
// * @author Marco Tupiza mtupiza@gmail.com
// * @author Marcia Conlago mconlago@gmail.com
// * @author APLINFO
// * @version 1.0 17/09/2010
// */
//public abstract class JsfBean<T> implements Serializable {
//
//    final Logger log = LoggerFactory.getLogger(JsfBean.class);
//    protected DataModel<T> model;
//    protected T object;
//    @EJB
//    protected Administrador<T> admin;
//    @EJB
//    protected Administrador adminObject;
//    protected static ResourceBundle bundle;
//    private boolean swGrabar;
//
//    protected abstract ResourceBundle getProperties();
//    //sprotected abstract ResourceBundle getProperties();
//
//    /**
//     * Constructor por defecto.
//     */
//    public JsfBean() {
//        if (bundle == null) {
//            bundle = ResourceBundle.getBundle("com.apli.jsf.message");
//        }
//        cargarEjb();
//        inicializar();
//    }
//
//    /**
//     * Inicializa los datos miembros.
//     */
//    protected abstract void inicializar();
//
//    /**
//     * Obtiene el objeto remoto.
//     */
//    protected void cargarEjb() {
//        if (admin == null) {
//            String ejbPath = null;
//            try {
//                ResourceBundle property = getProperties();
//                String ORBContext = property.getString("ORBContext");
//                String ejbContextPath = property.getString("ejb.context.path.admin");
//                ejbPath = ORBContext + ejbContextPath + "/AdministradorBean";
//                admin = (Administrador<T>) AplinfoFactoryBean.lookupBean(ejbPath, false);
//            } catch (Exception e) {
//                log.error("cargarEjb() {} - {} ", ejbPath, e.getMessage());
//            }
//        }
//        if (adminObject == null) {
//            String ejbPath = null;
//            try {
//                ResourceBundle property = getProperties();
//                String ORBContext = property.getString("ORBContext");
//                String ejbContextPath = property.getString("ejb.context.path.admin");
//                ejbPath = ORBContext + ejbContextPath + "/AdministradorBean";
//                adminObject = (Administrador) AplinfoFactoryBean.lookupBean(ejbPath, false);
//            } catch (Exception e) {
//                log.error("cargarEjb() {} - {} ", ejbPath, e.getMessage());
//            }
//        }
//    }
//
//    /**
//     * Graba el registro asociado al objeto que se administra en la página.
//     *
//     * @return null, se mantiene en la página invocada.
//     */
//    public abstract String grabarAction();
//
//    /**
//     * Implementa el comportamiento para inicializar los datos del objeto a
//     * administrar.
//     *
//     * @return null, retorna a la página invocada.
//     */
//    public String nuevoAction() {
//        inicializar();
//        return null;
//    }
//
//    public String eliminarAction(T obj) {
//        FacesContext context = FacesContext.getCurrentInstance();
//        try {
//            admin.eliminar(obj, FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user"));
//            inicializar();
//            cargarDataModel();
//            context.addMessage(findComponent(context.getViewRoot(), "formDatos").getClientId(), new FacesMessage(bundle.getString("msg.eliminar")));
//        } catch (Exception e) {
//            log.error("eliminarAction() {} ", e.getMessage());
//            context.addMessage(findComponent(context.getViewRoot(), "formDatos").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
//        }
//        return null;
//    }
//
//    /**
//     * Implementa el comportamiento para seleccionar un elemento en una lista de
//     * la página.
//     *
//     * @return null, se mantiene en la página invocada.
//     */
//    public String seleccionarAction() {
//        T objectTmp = (T) model.getRowData();
//        //inicializar();
//        object = objectTmp;
//        return null;
//    }
//
//    public String editarAction(T obj) {
//        inicializar();
//        object = obj;
//        return null;
//    }
//
//    /**
//     * Carga la lista de objetos en una lista de la página.
//     */
//    public void cargarDataModel() {
//        if (admin == null) {
//            model = null;
//            return;
//        }
//        try {
//            model = new ListDataModel(admin.listarOrdenada(object.getClass(), "nombre", true));
//        } catch (Exception e) {
//            log.error("getDataModel() {} ", e.getMessage());
//        }
//    }
//
//    /**
//     * Obtiene la lista de objetos en un DataModel.
//     *
//     * @return el Modelo
//     */
//    public DataModel<T> getDataModel() {
//        if (model == null) {
//            cargarDataModel();
//        }
//
//        return model;
//    }
//
//    /**
//     * Obtiene una lista de objetos de tipo SelectedItem.
//     *
//     * @return la lista
//     */
//    public abstract List<SelectItem> getSelectedItem();
//
//    /**
//     * Busca un componente JSF
//     *
//     * @param c componente base
//     * @param id el id del componente
//     * @return el componente buscado
//     */
//    protected UIComponent findComponent(UIComponent c, String id) {
//        if (id.equals(c.getId())) {
//            return c;
//        }
//        Iterator<UIComponent> kids = c.getFacetsAndChildren();
//        while (kids.hasNext()) {
//            UIComponent found = findComponent(kids.next(), id);
//            if (found != null) {
//                return found;
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Obtiene el objeto que es administrado por la página JSF.
//     *
//     * @return el objeto
//     */
//    public T getObject() {
//        return object;
//    }
//
//    /**
//     * Pone el valor del objeto a ser administrado en la página JSF.
//     *
//     * @param object el objeto
//     */
//    public void setObject(T object) {
//        this.object = object;
//    }
//
//    public boolean isSwGrabar() {
//        return swGrabar;
//    }
//
//    public void setSwGrabar(boolean swGrabar) {
//        this.swGrabar = swGrabar;
//    }
//
//    public void valueChangeMethod(AjaxBehaviorEvent event) {
//        System.out.println(event.toString());
//        swGrabar = true;
//    }
//}
