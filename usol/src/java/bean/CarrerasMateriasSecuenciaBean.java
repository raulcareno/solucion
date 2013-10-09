/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import jcinform.persistencia.Carreras;
import jcinform.persistencia.CarrerasMaterias;
import jcinform.persistencia.Ejes;
import jcinform.persistencia.Materias;
import jcinform.persistencia.Niveles;
import jcinform.persistencia.SecuenciaDeMaterias;
import jcinform.persistencia.SecuenciaDeMateriasAdicionales;
import jcinform.procesos.Administrador;
import org.primefaces.event.DragDropEvent;

import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class CarrerasMateriasSecuenciaBean {

    /**
     * Creates a new instance of CarrerasMateriasBean
     */
    CarrerasMaterias object;
    Administrador adm;
    protected List<CarrerasMaterias> model;
    protected Carreras carreraSeleccionada;
    protected Niveles nivelesSeleccionada;
    protected Ejes ejesSeleccionada;
    protected Materias materiasSeleccionada;
    protected CarrerasMaterias carreraMateriaSeleccionada;
    public String textoBuscar;
    Permisos permisos;
    Auditar aud = new Auditar();

    public CarrerasMateriasSecuenciaBean() {
        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("SecuenciaMaterias", "ingresar_carrerasMateriasSecuencia.jspx", "ingresar", true, "PARAMETROS")) {
            try {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("/universidad/noPuedeIngresar.jspx");
            } //selectedCarrerasMaterias = new CarrerasMaterias();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        llenarArreglo();
        //selectedCarrerasMaterias = new CarrerasMaterias();

    }

    public String guardar() {
        FacesContext context = FacesContext.getCurrentInstance();
//            if (!permisos.verificarPermisoReporte("Facultad", "agregar_facultad", "agregar", true, "PARAMETROS")) {
//                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));                return null;
//            }
        try {
            //borro primero los prerequisitos
            adm.ejecutaSql("Delete from SecuenciaDeMateriasAdicionales where idCarrerasMaterias.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "' ");
            //borro primero las anteriores
            adm.ejecutaSql("Delete from SecuenciaDeMaterias where idCarrerasMaterias.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "' ");
            for (int i = 0; i < 30; i++) {
                for (int j = 0; j < 12; j++) {
                    CarrerasMaterias carreraMateria = anadidasArray[i][j];

                    SecuenciaDeMaterias sec = new SecuenciaDeMaterias(adm.getNuevaClave("SecuenciaDeMaterias", "idSecuenciaDeMaterias"));
                    sec.setIdCarrerasMaterias(carreraMateria);
                    sec.setFila(i);
                    sec.setOrden(j);
                    sec.setSecuenciaDeMateriasAdicionalesList(carreraMateria.getSecuenciaDeMateriasAdicionalesList());
                    if (carreraMateria.getIdCarreras().getIdCarreras() != null) {
                        adm.guardar(sec);
                         
                          /**
                           * busco la coincidencia
                           */  
                        String filaColumna = "f"+i+"c"+j;
                                for (Iterator<SecuenciaDeMateriasAdicionales> it = adicionales.iterator(); it.hasNext();) {
                                    SecuenciaDeMateriasAdicionales secM = it.next();
                                    if (secM.getFilacolumna().equals(filaColumna)) {
                                        secM.setIdSecuenciaDeMateriasAdicionales(adm.getNuevaClave("SecuenciaDeMateriasAdicionales", "idSecuenciaDeMateriasAdicionales"));
                                        secM.setIdSecuenciaDeMaterias(sec);
                                        adm.guardar(secM); 
                                        
                                    }

                                }
                    }

                }
            }
            aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", carreraSeleccionada.getNombre() + "");
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));

        } catch (Exception e) {
            System.out.println("ERROR EN MALLA:");
            e.printStackTrace();
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
    public void onCarrerasInvDrop(CarrerasMaterias player) {


        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 12; j++) {
                CarrerasMaterias carA = anadidasArray[i][j];
                if (player.equals(carA)) {
                    CarrerasMaterias car = new CarrerasMaterias();
                    Materias mat = new Materias();
                    mat.setNombre("");
                    car.setIdMaterias(mat);
                    car.setIdNiveles(new Niveles());
                    car.setIdCarreras(new Carreras());
                    anadidasArray[i][j] = car;
                    listaMaterias.add(player);
                }

            }

        }




    }
    /**
     * VER ID QUE ESTÁ SELECCIONADO.
     */
    public String idSeleccionado = "";

    public String seleccionado(int i, int j) {
        idSeleccionado = "f"+i+"c"+j;

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
        try {
            for (int i = 0; i < adicionalesTmp.size(); i++) {
                SecuenciaDeMateriasAdicionales secM = adicionales.get(i);
                if (sec.getFilacolumna().equals(secM.getFilacolumna()) && sec.getIdCarrerasMaterias().equals(secM.getIdCarrerasMaterias())) {
                    adicionalesTmp.remove(i);
                    break;
                }
            }

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, e);
        }
        try {
            for (int i = 0; i < adicionales.size(); i++) {
                SecuenciaDeMateriasAdicionales secM = adicionales.get(i);
                if (sec.getFilacolumna().equals(secM.getFilacolumna()) && sec.getIdCarrerasMaterias().getIdCarrerasMaterias().equals(secM.getIdCarrerasMaterias().getIdCarrerasMaterias())) {
                    adicionales.remove(i);
                    break;
                }

            }
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * AÑADO A LA MALLA LAS MATERIAS
     *
     * @param event
     */
    public void onCarrerasDrop(DragDropEvent event) {
        CarrerasMaterias player = (CarrerasMaterias) event.getData();
        //anadidas.add(player); 
        listaMaterias.remove(player);
        String filaColumna = event.getDropId();
        Integer fila = new Integer(filaColumna.substring(filaColumna.indexOf("f") + 1, filaColumna.indexOf("c")));
        Integer columna = new Integer(filaColumna.substring(filaColumna.indexOf("c") + 1, filaColumna.length()));
        anadidasArray[fila][columna] = player;
    }
    List<SecuenciaDeMateriasAdicionales> adicionales = new ArrayList<SecuenciaDeMateriasAdicionales>();
    List<SecuenciaDeMateriasAdicionales> adicionalesTmp = new ArrayList<SecuenciaDeMateriasAdicionales>();

    /**
     * AÑADO LAS ADICIONALES
     *
     * @param event
     */

    public void anadirAdicional() {
        SecuenciaDeMateriasAdicionales sec = new SecuenciaDeMateriasAdicionales();

        carreraMateriaSeleccionada = (CarrerasMaterias) adm.buscarClave(carreraMateriaSeleccionada.getIdCarrerasMaterias(), CarrerasMaterias.class);
        sec.setIdCarrerasMaterias(carreraMateriaSeleccionada);
        sec.setFilacolumna(idSeleccionado);
        if (validarSiExiste(sec) == false) {
            adicionales.add(sec);
            adicionalesTmp = new ArrayList<SecuenciaDeMateriasAdicionales>();
            for (Iterator<SecuenciaDeMateriasAdicionales> it = adicionales.iterator(); it.hasNext();) {
                SecuenciaDeMateriasAdicionales secM = it.next();
                if (secM.getFilacolumna().equals(idSeleccionado)) {
                    adicionalesTmp.add(secM);
                }

            }
        }

    }

    public boolean validarSiExiste(SecuenciaDeMateriasAdicionales sec) {
        for (Iterator<SecuenciaDeMateriasAdicionales> it = adicionales.iterator(); it.hasNext();) {
            SecuenciaDeMateriasAdicionales secM = it.next();
            if (secM.getFilacolumna().equals(idSeleccionado) && sec.getIdCarrerasMaterias().equals(secM.getIdCarrerasMaterias())) {
                return true;
            }

        }
        return false;
    }
    /**
     * busca según criterio textoBuscar
     */
    List<CarrerasMaterias> listaMaterias = new ArrayList<CarrerasMaterias>();
    List<SelectItem> listaMateriasAdicionales = new ArrayList<SelectItem>();
    List<SelectItem> listaMateriasAdicionales2 = new ArrayList<SelectItem>();
    List<CarrerasMaterias> anadidas = new ArrayList<CarrerasMaterias>();
    //List<CarrerasMaterias> anadidas2[2][2]  = new ArrayList<>();
    CarrerasMaterias anadidasArray[][] = new CarrerasMaterias[30][12];

    public void llenarArreglo() {
        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 12; j++) {
                CarrerasMaterias car = new CarrerasMaterias();
                Materias mat = new Materias();
                mat.setNombre("");
                car.setIdMaterias(mat);
                car.setIdNiveles(new Niveles());
                car.setIdCarreras(new Carreras());
                car.setSecuenciaDeMateriasAdicionalesList(new ArrayList<SecuenciaDeMateriasAdicionales>());
                anadidasArray[i][j] = car;
            }
        }
    }

    public void filtroMaterias(int fila){
        listaMateriasAdicionales2 = new ArrayList<SelectItem>(); 
        for (Iterator<SelectItem> it = listaMateriasAdicionales.iterator(); it.hasNext();) {
            SelectItem selectItem = it.next();
            CarrerasMaterias carrerasMaterias = (CarrerasMaterias) selectItem.getValue();
            if(carrerasMaterias.getIdNiveles().getSecuencia()<= fila){
                 listaMateriasAdicionales2.add(selectItem);
            }
            
            
        }
        
    }
    public void buscarMateriasdeCarrera() {
        try {
            llenarArreglo();
            listaMaterias = adm.query("Select o from CarrerasMaterias as o "
                    + " where o.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "'  "
                    + "and o.idCarrerasMaterias NOT IN (Select c.idCarrerasMaterias.idCarrerasMaterias "
                    + "from SecuenciaDeMaterias as c "
                    + " WHERE c.idCarrerasMaterias.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "' ) "
                    + " order by o.idNiveles.secuencia ");
            List<CarrerasMaterias> adicionalesEncontradas = adm.query("Select o from CarrerasMaterias as o "
                    + " where o.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "'  "
                    + "   order by o.idNiveles.secuencia ");
            listaMateriasAdicionales = new ArrayList<SelectItem>();
            for (Iterator<CarrerasMaterias> it = adicionalesEncontradas.iterator(); it.hasNext();) {
                CarrerasMaterias carrerasMaterias = it.next();
                listaMateriasAdicionales.add(new SelectItem(carrerasMaterias, carrerasMaterias.getIdNiveles().getNombre() + " - " + carrerasMaterias.getIdMaterias().getNombre()));

            }
            adicionales = adm.query("SELECT o FROM SecuenciaDeMateriasAdicionales AS o "
                    + " WHERE o.idCarrerasMaterias.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "'  ");


            List<SecuenciaDeMaterias> materiasSecuenciales = adm.query("Select o from SecuenciaDeMaterias as o "
                    + "where o.idCarrerasMaterias.idCarreras.idCarreras = '" + carreraSeleccionada.getIdCarreras() + "' "
                    + "order by o.fila, o.orden ");
            if (materiasSecuenciales.size() > 0) {
                for (Iterator<SecuenciaDeMaterias> it = materiasSecuenciales.iterator(); it.hasNext();) {
                    SecuenciaDeMaterias cM = it.next();
                    cM.setSecuenciaDeMateriasAdicionalesList(new ArrayList<SecuenciaDeMateriasAdicionales>());
                    anadidasArray[cM.getFila()][cM.getOrden()] = cM.getIdCarrerasMaterias();
                }
                //LLENO LOS VACIOS
                for (int i = 0; i < 30; i++) {
                    for (int j = 0; j < 12; j++) {
                        try {
                            CarrerasMaterias tmp = anadidasArray[i][j];
                            if (tmp.getIdCarrerasMaterias().equals(null)) {
                                CarrerasMaterias car = new CarrerasMaterias();
                                Materias mat = new Materias();
                                mat.setNombre("");
                                car.setIdMaterias(mat);
                                car.setIdNiveles(new Niveles());
                                car.setIdCarreras(new Carreras());
                                car.setSecuenciaDeMateriasAdicionalesList(new ArrayList<SecuenciaDeMateriasAdicionales>());
                                anadidasArray[i][j] = car;
                            }


                        } catch (Exception e) {
                            CarrerasMaterias car = new CarrerasMaterias();
                            Materias mat = new Materias();
                            mat.setNombre("");
                            car.setIdMaterias(mat);
                            car.setIdNiveles(new Niveles());
                            car.setIdCarreras(new Carreras());
                            car.setSecuenciaDeMateriasAdicionalesList(new ArrayList<SecuenciaDeMateriasAdicionales>());
                            anadidasArray[i][j] = car;
                        }




                    }

                }

            } else {
                llenarArreglo();
            }

        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, e);
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
            java.util.logging.Logger.getLogger(CarrerasMateriasBean.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

    /**
     * propiedades
     *
     * @return
     */
    public List<CarrerasMaterias> getModel() {
        return model;
    }

    public void setModel(List<CarrerasMaterias> model) {
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

    public CarrerasMaterias getObject() {
        if (object == null) {
            object = new CarrerasMaterias(0);
        }
        return object;
    }

    public void setObject(CarrerasMaterias object) {
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

    public List<CarrerasMaterias> getAnadidas() {
        return anadidas;
    }

    public void setAnadidas(List<CarrerasMaterias> anadidas) {
        this.anadidas = anadidas;
    }

    public CarrerasMaterias[][] getAnadidasArray() {
        return anadidasArray;
    }

    public void setAnadidasArray(CarrerasMaterias[][] anadidasArray) {
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

    public Permisos getPermisos() {
        return permisos;
    }

    public void setPermisos(Permisos permisos) {
        this.permisos = permisos;
    }

    
}
