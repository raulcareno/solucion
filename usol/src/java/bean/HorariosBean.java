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
import jcinform.persistencia.CarrerasMaterias;
import jcinform.persistencia.Ejes;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Horarios;
import jcinform.persistencia.Horas;
import jcinform.persistencia.Materias;
import jcinform.persistencia.Niveles;
import jcinform.persistencia.SecuenciaDeMateriasAdicionales;
import jcinform.procesos.Administrador;
import org.primefaces.event.DragDropEvent;
import org.primefaces.event.RowEditEvent;
import utilerias.Car;

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
    protected Horarios carreraMateriaSeleccionada;
    public String textoBuscar;
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
        if (!permisos.verificarPermisoReporte("Horarios", "ingresar_carrerasMaterias", "ingresar", true, "PARAMETROS")) {
            try {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/noPuedeIngresar.jspx");
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

   

    public String guardar() {
        
        if(true){
             for (Horarios obj : model) {
                    System.out.println("EMPLEADO: "+obj.getIdEmpleados());
                }
            return "";
        }
        FacesContext context = FacesContext.getCurrentInstance();
//            if (!permisos.verificarPermisoReporte("Facultad", "agregar_facultad", "agregar", true, "PARAMETROS")) {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
//            }
        try {
            //borro primero los prerequisitos
            adm.ejecutaSql("Delete from Horarios where idHorarios.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "' ");
            //borro primero las anteriores
            adm.ejecutaSql("Delete from Horarios where idHorarios.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "' ");
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 12; j++) {
                    Horarios carreraMateria = anadidasArray[i][j];

                    Horarios sec = new Horarios(adm.getNuevaClave("SecuenciaDeMaterias", "idSecuenciaDeMaterias"));
//                    sec.setIdCarrerasMaterias(carreraMateria);
                    sec.setFila(i);
                    sec.setOrden(j);
//                    sec.setSecuenciaDeMateriasAdicionalesList(carreraMateria.getSecuenciaDeMateriasAdicionalesList());
                    if (carreraMateria.getIdCarreras().getIdCarreras() != null) {
                        adm.guardar(sec);

                        /**
                         * busco la coincidencia
                         */
                        String filaColumna = "f" + i + "c" + j;
                        for (Iterator<SecuenciaDeMateriasAdicionales> it = adicionales.iterator(); it.hasNext();) {
                            SecuenciaDeMateriasAdicionales secM = it.next();
                            if (secM.getFilacolumna().equals(filaColumna)) {
                                secM.setIdSecuenciaDeMateriasAdicionales(adm.getNuevaClave("SecuenciaDeMateriasAdicionales", "idSecuenciaDeMateriasAdicionales"));
//                                        secM.setIdSecuenciaDeMaterias(sec);
                                adm.guardar(secM);

                            }

                        }
                    }

                }
            }
            aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", carreraSeleccionada.getNombre() + "");
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
        }


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
         
             if(!verificarMateria2(player)){
                    for (Horarios obj : model) {
                        if(obj.getIdMaterias().getIdMaterias().equals(player.getIdMaterias().getIdMaterias()))
                            model.remove(obj);
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
        
        Empleados emp = (Empleados) adm.buscarClave(((Horarios) event.getObject()).getIdEmpleados().getIdEmpleados(),Empleados.class);
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
        idSeleccionado = "f" + i + "c" + j;

        adicionalesTmp = new ArrayList<SecuenciaDeMateriasAdicionales>();
        for (Iterator<SecuenciaDeMateriasAdicionales> it = adicionales.iterator(); it.hasNext();) {
            SecuenciaDeMateriasAdicionales secM = it.next();
            if (secM.getFilacolumna().equals(idSeleccionado)) {
                adicionalesTmp.add(secM);
            }

        }
        filtroMaterias(j);

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
       
      
         if(!verificarMateria(player)){
            if(model == null)
                model = new ArrayList<Horarios>();
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
//        SecuenciaDeMateriasAdicionales sec = new SecuenciaDeMateriasAdicionales();
//
//        carreraMateriaSeleccionada = (Horarios) adm.buscarClave(carreraMateriaSeleccionada.getIdHorarios(), Horarios.class);
//        sec.setIdHorarios(carreraMateriaSeleccionada);
//        sec.setFilacolumna(idSeleccionado);
//        if (validarSiExiste(sec) == false) {
//            adicionales.add(sec);
//            adicionalesTmp = new ArrayList<SecuenciaDeMateriasAdicionales>();
//            for (Iterator<SecuenciaDeMateriasAdicionales> it = adicionales.iterator(); it.hasNext();) {
//                SecuenciaDeMateriasAdicionales secM = it.next();
//                if (secM.getFilacolumna().equals(idSeleccionado)) {
//                    adicionalesTmp.add(secM);
//                }
//
//            }
//        }
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
    List<Horarios> listaMaterias = new ArrayList<Horarios>();
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
                    items.add(new SelectItem(obj, obj.getApellidoPaterno()+" "+ obj.getNombre()));
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
    public void buscarMateriasdeCarrera() {
        try {
            llenarArreglo();
            //SELECT * FROM carreras_materias WHERE id_carreras = 1 AND id_niveles = 1
            listaMaterias = adm.query("Select o from CarrerasMaterias as o "
                    + " where o.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "'  "
                    + " and o.idNiveles.idNiveles = '" + nivelesSeleccionada.getIdNiveles() + "'   "
                    + "  order by o.idMaterias.nombre ");


            List<Horarios> materiasSecuenciales = adm.query("Select o from Horarios as o "
                    + "where o.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "' "
                    + "  and o.idNiveles.idNiveles = '" + nivelesSeleccionada.getIdNiveles() + "' "
                    + " and o.idAulas.idAulas = '" + aulasSeleccionada.getIdAulas() + "'  order by o.fila, o.orden ");
            if (materiasSecuenciales.size() > 0) {

                //LLENO LOS VACIOS
                for (int i = 0; i < totalHoras; i++) {
                    for (int j = 0; j < 8; j++) {
                        try {
                            Horarios tmp = anadidasArray[i][j];
                            if (tmp.getIdHorarios().equals(null)) {
                                Horarios car = new Horarios();
                                Materias mat = new Materias();
                                mat.setNombre("");
                                car.setIdMaterias(mat);
                                car.setIdNiveles(new Niveles());
                                car.setIdCarreras(new Carreras());
                                anadidasArray[i][j] = car;
                            }
                        } catch (Exception e) {
                            Horarios car = new Horarios();
                            Materias mat = new Materias();
                            mat.setNombre("");
                            car.setIdMaterias(mat);
                            car.setIdNiveles(new Niveles());
                            car.setIdCarreras(new Carreras());
                            anadidasArray[i][j] = car;
                        }



                    }

                }

            } else {
                llenarArreglo();
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

    public List<Horarios> getListaMaterias() {
        return listaMaterias;
    }

    public void setListaMaterias(List<Horarios> listaMaterias) {
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

    public Horarios getCarreraMateriaSeleccionada() {
        return carreraMateriaSeleccionada;
    }

    public void setCarreraMateriaSeleccionada(Horarios carreraMateriaSeleccionada) {
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
}
