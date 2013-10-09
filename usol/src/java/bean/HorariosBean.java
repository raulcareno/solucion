/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import jcinform.persistencia.Aulas;
import jcinform.persistencia.Carreras;
import jcinform.persistencia.CarrerasMaterias;
import jcinform.persistencia.Ejes;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Horarios;
import jcinform.persistencia.Horas;
import jcinform.persistencia.Materias;
import jcinform.persistencia.Niveles;
import jcinform.persistencia.Periodos;
import jcinform.persistencia.SecuenciaDeMateriasAdicionales;
import jcinform.procesos.Administrador;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.ScheduleEntryMoveEvent;
import org.primefaces.event.ScheduleEntryResizeEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class HorariosBean {

    /**
     * Creates a new instance of HorariosBean
     */
    Horarios object;
    Administrador adm;
    protected List<Horarios> model;
    protected Carreras carreraSeleccionada;
    protected Niveles nivelesSeleccionada;
    protected Ejes ejesSeleccionada;
    protected Aulas aulasSeleccionada;
    protected Materias materiasSeleccionada;
    protected Empleados empleadoSeleccionado;
    protected CarrerasMaterias carreraMateriaSeleccionada;
    protected Horarios horariosSeleccionada;
    public String textoBuscar;
    protected Date fechaInicialS;
    Permisos permisos;
    Auditar aud = new Auditar();
    protected int totalHoras;
    protected List<Horas> horas;

    public HorariosBean() {
        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("Horarios", "ingresar_horarios.jspx", "ingresar", true, "HORARIOS")) {
            try {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/universidad/noPuedeIngresar.jspx");
            } //selectedHorarios = new Horarios();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        horas = adm.query("Select o from Horas as o ");
        totalHoras = horas.size();
        llenarArreglo();
        //selectedHorarios = new Horarios();

    }

    public Empleados buscarEmpleado(Materias mat) {
        for (Horarios obj : model) {
            if (obj.getIdMaterias().getIdMaterias().equals(mat.getIdMaterias())) {
                return obj.getIdEmpleados();

            }
        }
        return null;
    }

    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
        Periodos per = (Periodos) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodo");
        List lista = eventModel.getEvents();
        for (Iterator<DefaultScheduleEventLocal> it = lista.iterator(); it.hasNext();) {
            DefaultScheduleEventLocal dH = it.next();
            Horarios sec = dH.getIdHorarios();


            sec.setIdCarreras(dH.getIdHorarios().getIdCarreras());
            sec.setIdAulas(aulasSeleccionada);
            sec.setIdNiveles(nivelesSeleccionada);
            sec.setIdMaterias(dH.getIdHorarios().getIdMaterias());
            sec.setIdPeriodos(per);
            sec.setColor(dH.getStyleClass());
            sec.setIdEmpleados(dH.getIdHorarios().getIdEmpleados());
            Calendar calIni = Calendar.getInstance();
            calIni.setTime(dH.getStartDate());
            Calendar calFin = Calendar.getInstance();
            calFin.setTime(dH.getEndDate());

            sec.setFechainicial(dH.getStartDate());
            sec.setFechafinal(dH.getEndDate());
            sec.setDia(calIni.get(Calendar.DAY_OF_WEEK));
//                    if (sec.getIdMaterias().getIdMaterias() != null) {
            if (dH.getIdHorarios().getIdHorarios() == null) {
                sec.setIdHorarios(adm.getNuevaClave("Horarios", "idHorarios"));
                adm.guardar(sec);
            } else {
                adm.actualizar(sec);
            }

//                    }

//            System.out.println("");

        }
        aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", carreraSeleccionada.getNombre() + "");
        FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));

        if (true) {
            return "OK";
        }





//            if (!permisos.verificarPermisoReporte("Facultad", "agregar_facultad", "agregar", true, "HORARIOS")) {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
//            }
//        try {
//            //borro primero los prerequisitos
//            adm.ejecutaSql("Delete from Horarios where idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "'  "
//                    + "and idNiveles.idNiveles = '" + nivelesSeleccionada.getIdNiveles() + "' and idAulas.idAulas = '" + aulasSeleccionada.getIdAulas() + "' "
//                    + "and idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "' ");
//
//            for (int i = 0; i < totalHoras; i++) {
//                for (int j = 0; j < 8; j++) {
//                    Horarios sec = anadidasArray[i][j];
//
//                    sec.setIdHorarios(adm.getNuevaClave("Horarios", "idHorarios"));
//                    sec.setIdCarreras(carreraSeleccionada);
//                    sec.setIdAulas(aulasSeleccionada);
//                    sec.setIdNiveles(nivelesSeleccionada);
//                    sec.setIdPeriodos(per);
//                    sec.setIdEmpleados(anadidasArray[i][j].getIdEmpleados());
//                    sec.setDia(j);
//                    sec.setFila(i);
//                    sec.setOrden(j);
//                    sec.setIdHoras(horas.get(i));
//
//                    if (sec.getIdMaterias().getIdMaterias() != null) {
//                        //           sec.setIdEmpleados(buscarEmpleado(sec.getIdMaterias()));
//                        adm.guardar(sec);
//                    }
//
//                }
//            }
//            aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", carreraSeleccionada.getNombre() + "");
//            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));

//        } catch (Exception e) {
//            e.printStackTrace();
//            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
//        }


        return null;
    }

    /**
     * ELIMINO EL ELEMENTO SELECCIONADO EVENTO SE REALIZADA EN EL DRAG DROP
     * AÑADO
     *
     * @param player
     */
    public void onCarrerasInvDrop(Horarios player, int fila, int columna) {
        Horarios car = new Horarios();
        Materias mat = new Materias();
        mat.setNombre("");
        car.setIdMaterias(mat);
        car.setIdNiveles(new Niveles());
        car.setIdCarreras(new Carreras());
        anadidasArray[fila][columna] = car;

        if (!verificarMateria2(player)) {
            for (Horarios obj : model) {
                if (obj.getIdMaterias().getIdMaterias().equals(player.getIdMaterias().getIdMaterias())) {
                    model.remove(obj);
                }
            }

        }




    }

    public Boolean verificarMateria2(Horarios player) {
        for (int i = 0; i < totalHoras; i++) {
            for (int j = 0; j < 8; j++) {
                Horarios carA = anadidasArray[i][j];
                if (player.getIdMaterias().getIdMaterias().equals(carA.getIdMaterias().getIdMaterias())) {
                    return true;
                }

            }

        }
        return false;
    }

    public void onEdit(RowEditEvent event) {

        Empleados emp = (Empleados) adm.buscarClave(((Horarios) event.getObject()).getIdEmpleados().getIdEmpleados(), Empleados.class);
        ((Horarios) event.getObject()).setIdEmpleados(emp);
//        FacesMessage msg = new FacesMessage("Listo", ((Horarios) event.getObject()).getIdEmpleados().getApellidoPaterno()+"");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCancel(RowEditEvent event) {
//        FacesMessage msg = new FacesMessage("Cancelado", ((Horarios) event.getObject())+"");
//
//        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    /**
     * VER ID QUE ESTÁ SELECCIONADO.
     */
    public String idSeleccionado = "";

    public String seleccionado(int i, int j) {
        try {
            filaActual = i;
            columnaActual = j;
            Horarios car = anadidasArray[i][j];
            empleadoSeleccionado = car.getIdEmpleados();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("" + e);
        }

        return "";
    }

    public String getIdSeleccionado() {
        return idSeleccionado;
    }

    public void setIdSeleccionado(String idSeleccionado) {
        this.idSeleccionado = idSeleccionado;
    }

    /**
     * elimino una secuancia de materias adicionales
     */
    public void eliminar(SecuenciaDeMateriasAdicionales sec) {
//        try {
//            for (int i = 0; i < adicionalesTmp.size(); i++) {
//                SecuenciaDeMateriasAdicionales secM = adicionales.get(i);
////                if (sec.getFilacolumna().equals(secM.getFilacolumna()) && sec.getIdHorarios().equals(secM.getIdHorarios())) {
////                    adicionalesTmp.remove(i);
////                    break;
////                }
//            }
//
//        } catch (Exception e) {
//            java.util.logging.Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, e);
//        }
//        try {
//            for (int i = 0; i < adicionales.size(); i++) {
//                SecuenciaDeMateriasAdicionales secM = adicionales.get(i);
//                if (sec.getFilacolumna().equals(secM.getFilacolumna()) && sec.getIdHorarios().getIdHorarios().equals(secM.getIdHorarios().getIdHorarios())) {
//                    adicionales.remove(i);
//                    break;
//                }
//
//            }
//        } catch (Exception e) {
//            java.util.logging.Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, e);
//        }
    }

    /**
     * CONTAR EL NUMERO DE MATERIAS AÑADIDAS
     */
    public int contarMaterias(CarrerasMaterias player) {
        int total = 0;
        for (int i = 0; i < totalHoras; i++) {
            for (int j = 0; j < 8; j++) {
                Horarios carA = anadidasArray[i][j];
                if (player.getIdMaterias().getIdMaterias().equals(carA.getIdMaterias().getIdMaterias())) {
                    total++;
                }

            }

        }
        return total;




    }

    /**
     * AÑADO A LA MALLA LAS MATERIAS
     *
     * @param event
     */
    public void onCarrerasDrop(DragDropEvent event) {

        CarrerasMaterias player = (CarrerasMaterias) event.getData();
        int maximoHorasSemana = player.getNumeroCreditos();
        int totalAgregadas = contarMaterias(player);
        if (maximoHorasSemana <= totalAgregadas) {
            FacesContext context = FacesContext.getCurrentInstance();
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "EXECIDO EN HORAS", "EXECIDO EN HORAS"));
            return;
        }
        Horarios hora = new Horarios();
        hora.setIdCarreras(carreraSeleccionada);
        hora.setIdMaterias(player.getIdMaterias());
//        listaMaterias.remove(player);


        if (!verificarMateria(player)) {
            if (model == null) {
                model = new ArrayList<Horarios>();
            }
            Empleados empVacio = new Empleados(0);
            empVacio.setApellidoPaterno("SIN PROFESOR");
            hora.setIdEmpleados(empVacio);
            hora.setIdHorarios(hora.getIdMaterias().getIdMaterias());
            model.add(hora);
        }

        String filaColumna = event.getDropId();
        Integer fila = new Integer(filaColumna.substring(filaColumna.indexOf("f") + 1, filaColumna.indexOf("c")));
        Integer columna = new Integer(filaColumna.substring(filaColumna.indexOf("c") + 1, filaColumna.length()));
        anadidasArray[fila][columna] = hora;
    }

    public Boolean verificarMateria(CarrerasMaterias player) {
        for (int i = 0; i < totalHoras; i++) {
            for (int j = 0; j < 8; j++) {
                Horarios carA = anadidasArray[i][j];
                if (player.getIdMaterias().getIdMaterias().equals(carA.getIdMaterias().getIdMaterias())) {

                    return true;
                }

            }

        }
        return false;
    }
    List<SecuenciaDeMateriasAdicionales> adicionales = new ArrayList<SecuenciaDeMateriasAdicionales>();
    List<SecuenciaDeMateriasAdicionales> adicionalesTmp = new ArrayList<SecuenciaDeMateriasAdicionales>();

    /**
     * AÑADO LAS ADICIONALES
     *
     * @param event
     */
    public void anadirAdicional() {
        try {
            anadidasArray[filaActual][columnaActual].setIdEmpleados(empleadoSeleccionado);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("" + e);
        }

    }

    public boolean validarSiExiste(SecuenciaDeMateriasAdicionales sec) {
        for (Iterator<SecuenciaDeMateriasAdicionales> it = adicionales.iterator(); it.hasNext();) {
            SecuenciaDeMateriasAdicionales secM = it.next();
//            if (secM.getFilacolumna().equals(idSeleccionado) && sec.getIdHorarios().equals(secM.getIdHorarios())) {
//                return true;
//            }

        }
        return false;
    }
    /**
     * busca según criterio textoBuscar
     */
    List<CarrerasMaterias> listaMaterias = new ArrayList<CarrerasMaterias>();
    List<SelectItem> listaMateriasAdicionales = new ArrayList<SelectItem>();
    List<SelectItem> listaMateriasAdicionales2 = new ArrayList<SelectItem>();
    List<Horarios> anadidas = new ArrayList<Horarios>();
    //List<Horarios> anadidas2[2][2]  = new ArrayList<>();
    Horarios anadidasArray[][] = new Horarios[30][12];

    public void llenarArreglo() {


        for (int i = 0; i < horas.size(); i++) {
            for (int j = 0; j < 8; j++) {
                Horarios car = new Horarios();
                Materias mat = new Materias();
                mat.setNombre("");
                car.setIdMaterias(mat);
                car.setIdNiveles(new Niveles());
                car.setIdCarreras(new Carreras());
                car.setIdEmpleados(null);
                car.setIdAulas(new Aulas());
                car.setIdHoras(new Horas());
//                car.setIdPeriodos(null);
//                car.setSecuenciaDeMateriasAdicionalesList(new ArrayList<SecuenciaDeMateriasAdicionales>());
                anadidasArray[i][j] = car;
            }
        }
    }

    public void filtroMaterias(int fila) {
        listaMateriasAdicionales2 = new ArrayList<SelectItem>();
        for (Iterator<SelectItem> it = listaMateriasAdicionales.iterator(); it.hasNext();) {
            SelectItem selectItem = it.next();
            Horarios carrerasMaterias = (Horarios) selectItem.getValue();
            if (carrerasMaterias.getIdNiveles().getSecuencia() <= fila) {
                listaMateriasAdicionales2.add(selectItem);
            }


        }

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

    public List<SelectItem> getSelectedItemEmpleados() {
        try {
            List<Empleados> divisionPoliticas = new ArrayList<Empleados>();
            List<SelectItem> items = new ArrayList<SelectItem>();


            divisionPoliticas = adm.query("Select o from Empleados as o order by o.apellidoPaterno");
            if (divisionPoliticas.size() > 0) {
                Empleados objSel = new Empleados(0);
                items.add(new SelectItem(objSel, "SIN PROFESOR..."));
                for (Empleados obj : divisionPoliticas) {
                    items.add(new SelectItem(obj, obj.getApellidoPaterno() + " " + obj.getNombre()));
                }
            } else {
                Empleados obj = new Empleados(0);
                items.add(new SelectItem(obj, "NO EXISTEN NIVELES"));
            }


            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Horarios.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public void encerar0() {

        Niveles objSel = new Niveles(0);
        nivelesSeleccionada = objSel;
        Aulas objSelA = new Aulas();
        aulasSeleccionada = objSelA;

    }

    public void encerar1() {
        Aulas objSelA = new Aulas();
        aulasSeleccionada = objSelA;

    }

    public void buscarMateriasdeCarrera() {
        eventModel = new DefaultScheduleModel();
        Date feca = new Date();
        Periodos per = (Periodos) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodo");
        fechaInicialS = per.getFechaInicio();

        feca.setDate(20);

        eventModel = new DefaultScheduleModel();
//        eventModel.addEvent(new DefaultScheduleEventLocal("Mi evento", new Date(), feca, "uno", new Horarios()));
//        lazyEventModel = new LazyScheduleModel() {
////            @Override  
//            public void fetchEvents(Date start, Date end) {
//                clear();
//                Date random = new Date();
////                addEvent(new DefaultScheduleEvent("Lazy Event 1", random, random));
//                //random = getRandomDate(start);
////                addEvent(new DefaultScheduleEvent("Lazy Event 2", random, random));
//            }
//        };
        try {
            //Periodos per = (Periodos) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodo");
            llenarArreglo();
            if (carreraSeleccionada.getIdCarreras() == null) {
                return;
            }
            if (nivelesSeleccionada.getIdNiveles() == null) {
                return;
            }
            //SELECT * FROM carreras_materias WHERE id_carreras = 1 AND id_niveles = 1
            listaMaterias = adm.query("Select o from CarrerasMaterias as o "
                    + " where o.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "'  "
                    + " and o.idNiveles.idNiveles = '" + nivelesSeleccionada.getIdNiveles() + "'   "
                    + "  order by o.idMaterias.nombre ");


            List<Horarios> materiasSecuenciales = adm.query("Select o from Horarios as o "
                    + "where o.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "' "
                    + "  and o.idNiveles.idNiveles = '" + nivelesSeleccionada.getIdNiveles() + "' "
                    + " and o.idAulas.idAulas = '" + aulasSeleccionada.getIdAulas() + "' "
                    + " and o.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "'  ");
            model = new ArrayList<Horarios>();
            if (materiasSecuenciales.size() > 0) {
                int i = 0;
                for (Iterator<Horarios> it = materiasSecuenciales.iterator(); it.hasNext();) {
                    Horarios cM = it.next();
                    if (i == 0) {
                        fechaInicialS = cM.getFechainicial();
                    }
                    carreraSeleccionada = (Carreras) adm.buscarClave(carreraSeleccionada.getIdCarreras(), Carreras.class);
     
  //CALULO EL TIEMPO
                DateTime start = new DateTime(cM.getFechainicial()); //Devuelve la fecha actual al estilo Date
                DateTime end = new DateTime(cM.getFechafinal()); //Devuelve la fecha actual al estilo Date
                int minutos = Minutes.minutesBetween(start, end).getMinutes();
            
            
            
                    DefaultScheduleEventLocal eve = new DefaultScheduleEventLocal(cM.getIdMaterias().getNombre()+" "+minutos+" min.", cM.getFechainicial(), cM.getFechafinal(), cM.getColor(), cM);
                    Materias m = (Materias) adm.buscarClave(cM.getIdMaterias().getIdMaterias(), Materias.class);
                    eve.setTitle(m.getNombre()+" "+minutos+" min.");
                    eventModel.addEvent(eve);
                    m = null;
                    i++;
//                    }
//                    anadidasArray[cM.getFila()][cM.getOrden()] = cM;

                }

              

            } else {
//                llenarArreglo();
            }

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(HorariosBean.class.getName()).log(Level.SEVERE, null, e);
        }
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
     * propiedades
     *
     * @return
     */
    public List<Horarios> getModel() {
        return model;
    }

    public void setModel(List<Horarios> model) {
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

    public Horarios getObject() {
        if (object == null) {
            object = new Horarios(0);
        }
        return object;
    }

    public void setObject(Horarios object) {
        this.object = object;
    }

    public Carreras getCarreraSeleccionada() {
        return carreraSeleccionada;
    }

    public void setCarreraSeleccionada(Carreras carreraSeleccionada) {
        this.carreraSeleccionada = carreraSeleccionada;
    }

    public Niveles getNivelesSeleccionada() {
        return nivelesSeleccionada;
    }

    public void setNivelesSeleccionada(Niveles nivelesSeleccionada) {
        this.nivelesSeleccionada = nivelesSeleccionada;
    }

    public Ejes getEjesSeleccionada() {
        return ejesSeleccionada;
    }

    public void setEjesSeleccionada(Ejes ejesSeleccionada) {
        this.ejesSeleccionada = ejesSeleccionada;
    }

    public Materias getMateriasSeleccionada() {
        return materiasSeleccionada;
    }

    public void setMateriasSeleccionada(Materias materiasSeleccionada) {
        this.materiasSeleccionada = materiasSeleccionada;
    }

    public List<CarrerasMaterias> getListaMaterias() {
        return listaMaterias;
    }

    public void setListaMaterias(List<CarrerasMaterias> listaMaterias) {
        this.listaMaterias = listaMaterias;
    }

    public List<Horarios> getAnadidas() {
        return anadidas;
    }

    public void setAnadidas(List<Horarios> anadidas) {
        this.anadidas = anadidas;
    }

    public Horarios[][] getAnadidasArray() {
        return anadidasArray;
    }

    public void setAnadidasArray(Horarios[][] anadidasArray) {
        this.anadidasArray = anadidasArray;
    }

    public List<SelectItem> getListaMateriasAdicionales() {
        return listaMateriasAdicionales;
    }

    public void setListaMateriasAdicionales(List<SelectItem> listaMateriasAdicionales) {
        this.listaMateriasAdicionales = listaMateriasAdicionales;
    }

    public CarrerasMaterias getCarreraMateriaSeleccionada() {
        return carreraMateriaSeleccionada;
    }

    public void setCarreraMateriaSeleccionada(CarrerasMaterias carreraMateriaSeleccionada) {
        this.carreraMateriaSeleccionada = carreraMateriaSeleccionada;
    }

    public List<SecuenciaDeMateriasAdicionales> getAdicionales() {
        return adicionales;
    }

    public void setAdicionales(ArrayList<SecuenciaDeMateriasAdicionales> adicionales) {
        this.adicionales = adicionales;
    }

    public List<SecuenciaDeMateriasAdicionales> getAdicionalesTmp() {
        return adicionalesTmp;
    }

    public void setAdicionalesTmp(ArrayList<SecuenciaDeMateriasAdicionales> adicionalesTmp) {
        this.adicionalesTmp = adicionalesTmp;
    }

    public List<SelectItem> getListaMateriasAdicionales2() {
        return listaMateriasAdicionales2;
    }

    public void setListaMateriasAdicionales2(List<SelectItem> listaMateriasAdicionales2) {
        this.listaMateriasAdicionales2 = listaMateriasAdicionales2;
    }

    public int getTotalHoras() {
        return totalHoras;
    }

    public void setTotalHoras(int totalHoras) {
        this.totalHoras = totalHoras;
    }

    public List<Horas> getHoras() {
        return horas;
    }

    public void setHoras(List<Horas> horas) {
        this.horas = horas;
    }

    public Aulas getAulasSeleccionada() {
        return aulasSeleccionada;
    }

    public void setAulasSeleccionada(Aulas aulasSeleccionada) {
        this.aulasSeleccionada = aulasSeleccionada;
    }

    public Empleados getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleados empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }
    int filaActual = 0;
    int columnaActual = 0;

    public int getFilaActual() {
        return filaActual;
    }

    public void setFilaActual(int filaActual) {
        this.filaActual = filaActual;
    }
    /*HORARIOS CON CALENDAR*/
    private ScheduleModel eventModel;
    private ScheduleModel lazyEventModel;
    private DefaultScheduleEventLocal event = new DefaultScheduleEventLocal();
    private String theme;

    public int contarMateriasNuevo(CarrerasMaterias player) {
        List lista = eventModel.getEvents();
        int total = 0;
        for (Iterator<DefaultScheduleEventLocal> it = lista.iterator(); it.hasNext();) {
            DefaultScheduleEventLocal dH = it.next();
            Horarios carA = dH.getIdHorarios();
            if (player.getIdMaterias().getIdMaterias().equals(carA.getIdMaterias().getIdMaterias())) {
                DateTime start = new DateTime(dH.getStartDate()); //Devuelve la fecha actual al estilo Date
                DateTime end = new DateTime(dH.getEndDate()); //Devuelve la fecha actual al estilo Date
                int minutos = Minutes.minutesBetween(start, end).getMinutes();
                total+=minutos;
            }
        }
        return total;




    }

    public void addEvent(ActionEvent actionEvent) {




        carreraMateriaSeleccionada = (CarrerasMaterias) adm.buscarClave(carreraMateriaSeleccionada.getIdCarrerasMaterias(), CarrerasMaterias.class);


        if (event.getId() == null) {

//        CarrerasMaterias player = (CarrerasMaterias) event.getData();
            int maximoHorasSemana = carreraMateriaSeleccionada.getNumeroCreditos()*60;
            int totalAgregadas = contarMateriasNuevo(carreraMateriaSeleccionada);
            if (maximoHorasSemana <= totalAgregadas) {
//            FacesContext context = FacesContext.getCurrentInstance();
//            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "EXECIDO EN HORAS", "EXECIDO EN HORAS"));
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "EXECIDO EN HORAS", "EXECIDO EN HORAS:");
                addMessage(message);
                return;
            }


            Horarios idHo = new Horarios();

            idHo.setIdMaterias(carreraMateriaSeleccionada.getIdMaterias());
            idHo.setIdEmpleados(empleadoSeleccionado);
            idHo.setIdNiveles(nivelesSeleccionada);
            idHo.setIdAulas(aulasSeleccionada);
            idHo.setIdCarreras(carreraSeleccionada);
            //CALULO EL TIEMPO
                DateTime start = new DateTime(event.getStartDate()); //Devuelve la fecha actual al estilo Date
                DateTime end = new DateTime(event.getEndDate()); //Devuelve la fecha actual al estilo Date
                int minutos = Minutes.minutesBetween(start, end).getMinutes();
            
            
            event.setTitle(carreraMateriaSeleccionada.getIdMaterias().getNombre()+" "+minutos+" min.");
            event.setIdHorarios(idHo);

            eventModel.addEvent(event);
        } else {
            Horarios idHo = new Horarios();
            idHo.setIdMaterias(carreraMateriaSeleccionada.getIdMaterias());
            idHo.setIdEmpleados(empleadoSeleccionado);
            idHo.setIdNiveles(nivelesSeleccionada);
            idHo.setIdAulas(aulasSeleccionada);
            idHo.setIdCarreras(carreraSeleccionada);
            //CALULO EL TIEMPO
                DateTime start = new DateTime(event.getStartDate()); //Devuelve la fecha actual al estilo Date
                DateTime end = new DateTime(event.getEndDate()); //Devuelve la fecha actual al estilo Date
                int minutos = Minutes.minutesBetween(start, end).getMinutes();
            
            
            event.setTitle(carreraMateriaSeleccionada.getIdMaterias().getNombre()+" "+minutos+" min.");
            event.setIdHorarios(idHo);
            eventModel.updateEvent(event);
        }
        event = new DefaultScheduleEventLocal();
    }

    public void deleteEvent(ActionEvent actionEvent) {
        //carreraMateriaSeleccionada = (CarrerasMaterias) adm.buscarClave(carreraMateriaSeleccionada.getIdCarrerasMaterias(), CarrerasMaterias.class);
        try {
            DefaultScheduleEventLocal dH = event;
            adm.eliminarObjeto(Horarios.class, dH.getIdHorarios().getIdHorarios());
            dH = null;
        } catch (Exception e) {
        }


        if (event.getId() == null) {


            eventModel.deleteEvent(event);
        } else {
            eventModel.deleteEvent(event);
        }
        event = new DefaultScheduleEventLocal();
    }

    public void onEventSelect(SelectEvent selectEvent) {
        nivelesSeleccionada = (Niveles) adm.buscarClave(nivelesSeleccionada.getIdNiveles(), Niveles.class);
        String estilo = estiloColor(nivelesSeleccionada.getSecuencia().intValue());
//       ScheduleEvent abc;
//        abc = (ScheduleEvent)selectEvent.getObject();
        event = ((DefaultScheduleEventLocal) selectEvent.getObject());
        horariosSeleccionada = event.getIdHorarios();
        List<CarrerasMaterias> ca = adm.query("Select o from CarrerasMaterias as o "
                + "where  o.idCarreras.idCarreras = '" + horariosSeleccionada.getIdCarreras().getIdCarreras() + "'"
                + " and o.idNiveles.idNiveles = '" + horariosSeleccionada.getIdNiveles().getIdNiveles() + "'"
                + " and o.idMaterias.idMaterias = '" + horariosSeleccionada.getIdMaterias().getIdMaterias() + "'");
        if (ca.size() > 0) {
            carreraMateriaSeleccionada = ca.get(0);
        }
        empleadoSeleccionado = event.getIdHorarios().getIdEmpleados();
        System.out.println("");
    }

    public void onDateSelect(SelectEvent selectEvent) {
        System.out.println("");
        nivelesSeleccionada = (Niveles) adm.buscarClave(nivelesSeleccionada.getIdNiveles(), Niveles.class);
        String estilo = estiloColor(nivelesSeleccionada.getSecuencia().intValue());
        //event = new DefaultScheduleEvent("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject(),estilo);
        //event = new DefaultScheduleEventLocal("", (Date) selectEvent.getObject(), (Date) selectEvent.getObject(), estilo, new Horarios());
        Horarios idHo = new Horarios();
        try {
            idHo.setIdMaterias(carreraMateriaSeleccionada.getIdMaterias());
        } catch (Exception e) {
            idHo.setIdMaterias(new Materias());
        }

        idHo.setIdEmpleados(empleadoSeleccionado);
        idHo.setIdNiveles(nivelesSeleccionada);
        idHo.setIdAulas(aulasSeleccionada);
        idHo.setIdCarreras(carreraSeleccionada);

        Date fechaF = ((Date) selectEvent.getObject());


        Date fechaIni = new Date(fechaF.getYear(), fechaF.getMonth(), fechaF.getDate(), fechaF.getHours(), fechaF.getMinutes());

        int ho = fechaF.getHours() + 1;
        fechaF.setHours(ho);
        event = new DefaultScheduleEventLocal("", fechaIni, fechaF, estilo, idHo);
        System.out.println("" + event.getStartDate().toLocaleString());
    }

    public void onEventMove(ScheduleEntryMoveEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event moved", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    public void onEventResize(ScheduleEntryResizeEvent event) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Event resized", "Day delta:" + event.getDayDelta() + ", Minute delta:" + event.getMinuteDelta());

        addMessage(message);
    }

    private void addMessage(FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(null, message);
    }

    private Calendar today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 0, 0, 0);

        return calendar;
    }

    public String estiloColor(int nivel) {
        String valor = "blue";
        switch (nivel) {
            case 1:
                valor = "uno";
                break;
            case 2:
                valor = "dos";
                break;
            case 3:
                valor = "tres";
                break;
            case 4:
                valor = "cuatro";
                break;
            case 5:
                valor = "cinco";
                break;
            case 6:
                valor = "seis";
                break;
            case 7:
                valor = "siete";
                break;
            case 8:
                valor = "ocho";
                break;
            case 9:
                valor = "nueve";
                break;
            case 10:
                valor = "diez";
                break;

        }
        return valor;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public DefaultScheduleEventLocal getEvent() {
        return event;
    }

    public void setEvent(DefaultScheduleEventLocal event) {
        this.event = event;
    }

    public ScheduleModel getLazyEventModel() {
        return lazyEventModel;
    }

    public void setLazyEventModel(ScheduleModel lazyEventModel) {
        this.lazyEventModel = lazyEventModel;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public List<SelectItem> getSelectedItemMaterias() {
        try {
            List<Materias> divisionPoliticas = new ArrayList<Materias>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            if (object != null) {

                divisionPoliticas = adm.query("Select o from Materias as o order by o.nombre ");
                if (divisionPoliticas.size() > 0) {
                    Materias objSel = new Materias(0);
                    items.add(new SelectItem(objSel, "Seleccione..."));
                    for (Materias obj : divisionPoliticas) {
                        items.add(new SelectItem(obj, obj.getNombre()));
                    }
                } else {
                    Materias obj = new Materias(0);
                    items.add(new SelectItem(obj, "NO EXISTEN MATERIAS"));
                }

            }
            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMaterias.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

    public Date getFechaInicialS() {
        return fechaInicialS;
    }

    public void setFechaInicialS(Date fechaInicialS) {
        this.fechaInicialS = fechaInicialS;
    }
}
