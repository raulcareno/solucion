/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
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
import jcinform.persistencia.Periodos;
import jcinform.persistencia.SistemaNotas;
import jcinform.procesos.Administrador;
import org.joda.time.DateMidnight;
import utilerias.NotasIngresar;

import utilerias.Permisos;
import utilerias.secuencial;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@SessionScoped
public class NotasBean {

    /**
     * Creates a new instance of NotasBean
     */
    Notas object;
    Administrador adm;
    protected List<Notas> model;
    public String textoBuscar;
    protected Materias materiasSeleccionada;
    Permisos permisos;
    Auditar aud = new Auditar();
    Periodos per = null;

    public NotasBean() {
        //super();
        FacesContext context = FacesContext.getCurrentInstance();
        per = (Periodos) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodo");
        if (adm == null) {
            adm = new Administrador();
        }
        cabeceras = adm.query("Select o from SistemaNotas as o where o.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "' ");
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("Notas", "ingresar_notas", "ingresar", true, "PARAMETROS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("noPuedeIngresar.jspx");
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(NotasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
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
    List listaNotas = null;
    List cabeceras = null;

    public String buscarNotas() {
        boolean interno = true;
        listaNotas = new ArrayList();
        List<SistemaNotas> sistemas = adm.query("Select o from SistemaNotas as o "
                + "where o.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "' "
                + "order by o.idSistemaNotas ");
//        for (Iterator<SistemaNotas> its = sistemas0.iterator(); its.hasNext();) {
//            SistemaNotas acaNotanotas = its.next();
//            if (!acaNotanotas.getFormula().trim().equals("")) {
//                if (verificar(acaNotanotas.getFormula().trim(), false) == false) {
//                    mensajes = "NO SE HA PROCEDIDO A CARGAR LAS NOTAS, LAS FORMULAS INGRESADAS EN APROVECHAMIENTO ESTÀN INCORRECTAS ...!";
//                    return;
//                }
//            }
//        }

        String query = "";
        for (SistemaNotas notass : sistemas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        int tamanio = sistemas.size();
        String q = "SELECT matricula.estado_mat, matricula.id_matriculas, "
                + "CONCAT(estudiantes.apellido_paterno,' ',estudiantes.apellido_materno,' ',estudiantes.nombre),  " + query.toUpperCase() + "   "
                + " FROM Matriculas matricula  "
                + "LEFT JOIN  Estudiantes estudiantes ON matricula.id_estudiantes = estudiantes.id_estudiantes  "
                + "LEFT JOIN Materias_matricula mm ON matricula.id_matriculas = mm.id_matriculas AND mm.id_materias = '" + materiasSeleccionada.getIdMaterias() + "' "
                + "LEFT JOIN Notas notas ON mm.id_materias_matricula  = notas.id_materias_matricula   "
                + "   WHERE  matricula.estado_mat IN ('M','P','R') "
                + "    ORDER BY estudiantes.apellido_paterno, estudiantes.apellido_materno, estudiantes.nombre";
//        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        Date fechaActual = new Date();
        DateMidnight actual = new DateMidnight(fechaActual);
        int xy = 0;
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Object[] vec = (Object[]) itna.next();
            int a = 0;
            int x = 0;
            ArrayList notaAnadir = new ArrayList();
            Boolean estadoNota = false;
            for (a = 0; a < vec.length; a++) {
                SistemaNotas tnota = sistemas.get(x);
                NotasIngresar n = new NotasIngresar();
                if (a == 2) {
                    String object = (String) vec[a];
                    n.setNombre(object);
                    n.setTexto(true);
                    n.setNota(null);
                    if (estadoNota) {
                        n.setColorEstado("red");
                    }
                } else if (a == 1) {
                    Integer object = (Integer) vec[a];
                    n.setNombre(object + "");
                    n.setTexto(true);
                    n.setNota(null);
                } else if (a == 0) {
                    String object = (String) vec[a];

                    n.setTexto(true);
                    n.setNota(null);
                    if (object.equals("M") || object.equals("P")) {
                        n.setEstado(false);//DESHABILITO LA NOTA
                    } else {
                        estadoNota = true;
                        n.setEstado(true);//DESHABILITO LA NOTA
                        n.setNombre("");
                    }
                } else {

                    DateMidnight inicial = new DateMidnight(tnota.getFechai());
                    DateMidnight finale = new DateMidnight(tnota.getFechaf());
                    if (interno) {
                        inicial = new DateMidnight(tnota.getFechasi());
                        finale = new DateMidnight(tnota.getFechasf());
                    }

                    if (actual.compareTo(finale) <= 0 && actual.compareTo(inicial) >= 0) {
                        n.setEstado(false);//habilito la notaIngresada
                        n.setColor("white");
                    } else {
                        n.setEstado(true);// deshabilita la notaIngresada
                        n.setColor("#E9E7E2");
                    }
                    if (estadoNota) {
                        n.setEstado(estadoNota);
                        n.setColor("#E9E7E2");

                    }

                    Double object = (Double) vec[a];
                    if (object == null) {
                        object = 0.0;
                    }
                    n.setDesde(0.0);
                    n.setHasta(10d);
                    n.setNota(redondear(object, 2));
                    n.setNombre("");
                    n.setTexto(false);
                    
                    x++;
                }
                if(a>0){
                    n.setId(xy);
                    xy++;
                    notaAnadir.add(n);
                }
            }
            listaNotas.add(notaAnadir);
        }

        return null;
    }

    /**
     * Graba el registro asociado al objeto que
     */
    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
         
        for (Iterator it = listaNotas.iterator(); it.hasNext();) {
            ArrayList  notasArray = (ArrayList)it.next();
            for (Iterator it1 = notasArray.iterator(); it1.hasNext();) {
                NotasIngresar  object1 = (NotasIngresar)it1.next();
                System.out.println(".."+object1.getNota());
                
            }
            
            
        }
        if(true){
            listaNotas = null;
            materiasSeleccionada = new Materias(0);
            return "";
        }
// if (object.getNombre().isEmpty()) {
//            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el NOMBRE", ""));
//            return null;
//        }
//        if (object.getIdNotas() == 0) {
        if (!permisos.verificarPermisoReporte("Notas", "agregar_notas", "agregar", true, "PARAMETROS")) {
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
//            if (!permisos.verificarPermisoReporte("Notas", "actualizar_notas", "agregar", true, "PARAMETROS")) {
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
            if (!permisos.verificarPermisoReporte("Notas", "eliminar_notas", "eliminar", true, "PARAMETROS")) {
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

    public List<SelectItem> getSelectedItemMaterias() {
        try {
            Periodos per = (Periodos) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodo");
            List<Materias> divisionPoliticas = new ArrayList<Materias>();
            List<SelectItem> items = new ArrayList<SelectItem>();


            divisionPoliticas = adm.query("Select distinct o.idMaterias from Horarios as o "
                    + " where o.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "' "
                    + " order by o.idMaterias.nombre ");
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

    public Double redondear(Double numero, int decimales) {
        try {
            BigDecimal d = new BigDecimal(numero);
            d = d.setScale(decimales, RoundingMode.HALF_UP);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
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

    public List getListaNotas() {
        return listaNotas;
    }

    public void setListaNotas(List listaNotas) {
        this.listaNotas = listaNotas;
    }

    public List getCabeceras() {
        return cabeceras;
    }

    public void setCabeceras(List cabeceras) {
        this.cabeceras = cabeceras;
    }

    public Materias getMateriasSeleccionada() {
        return materiasSeleccionada;
    }

    public void setMateriasSeleccionada(Materias materiasSeleccionada) {
        this.materiasSeleccionada = materiasSeleccionada;
    }
}
