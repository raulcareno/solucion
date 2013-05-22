/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import bsh.EvalError;
import bsh.Interpreter;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import jcinform.persistencia.Aulas;
import jcinform.persistencia.Carreras;
import jcinform.persistencia.Horarios;
import jcinform.persistencia.Materias;
import jcinform.persistencia.MateriasMatricula;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Niveles;
import jcinform.persistencia.Notas;
import jcinform.persistencia.Periodos;
import jcinform.persistencia.RangosGpa;
import jcinform.persistencia.SistemaNotas;
import jcinform.procesos.Administrador;
import jcinform.procesos.SequenceUtil;
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
public class NotasBeanConvalidaciones implements Serializable {

    /**
     * Creates a new instance of NotasBean
     */
    Notas object;
    Administrador adm;
    protected List<Notas> model;
    public String textoBuscar;
    protected Materias materiasSeleccionada;
    protected Matriculas matriculasSeleccionada;
    protected Carreras carrerasSeleccionada = new Carreras(0);
    Permisos permisos;
    Auditar aud = new Auditar();
    Periodos per = null;

    public NotasBeanConvalidaciones() {
//        super();
        FacesContext context = FacesContext.getCurrentInstance();
        per = (Periodos) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodo");
        if (adm == null) {
            adm = new Administrador();
        }
        cabeceras = adm.query("Select o from SistemaNotas as o where o.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "' ");
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("Registro de Notas", "ingresar_notas.jspx", "ingresar", true, "NOTAS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("noPuedeIngresar.jspx");
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(NotasBeanConvalidaciones.class.getName()).log(Level.SEVERE, null, ex);
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
    List listaNotas;
    List cabeceras = null;

    public String limpiarNotas() {
        listaNotas = new ArrayList();
        materiasSeleccionada   = new Materias(0);
        return null;
    }

    public String buscarNotas() {
        boolean interno = true;
        listaNotas = new ArrayList();
        List<RangosGpa> rangos = adm.query("Select o from RangosGpa as o ");
        List<SistemaNotas> sistemas = adm.query("Select o from SistemaNotas as o "
                + "where o.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "' "
                + "order by o.idSistemaNotas ");
        SistemaNotas sisEstado = new SistemaNotas(-1);
        sisEstado.setAbreviatura("ESTADO");
        sisEstado.setEsgpa(false);
        sisEstado.setEsexamen(false);
        sisEstado.setEsnota(false);
        sisEstado.setEsasistencia(false);
        sisEstado.setSeimprime(false);
        sisEstado.setNota("");
        sistemas.add(sisEstado);
        String query = "";
        for (SistemaNotas notass : sistemas) {
            query += notass.getNota() + ",";
        }
        query = query.substring(0, query.length() - 1).replace("'", "").replace("(", "").replace(")", "");
        int tamanio = sistemas.size();
        String q = "SELECT matricula.estado_mat, matricula.id_matriculas, CONCAT(estudiantes.apellido_paterno,' ',estudiantes.apellido_materno,'  ',estudiantes.nombre), "
                + " nota1,nota2,nota3,nota4,  notas.estado    FROM   Matriculas matricula   "
                + " LEFT JOIN  Estudiantes estudiantes  ON matricula.id_estudiantes = estudiantes.id_estudiantes  "
                + " LEFT JOIN Notas notas ON   notas.id_matriculas = matricula.id_matriculas    AND notas.id_materias = '" + materiasSeleccionada.getIdMaterias() + "' "
                + " WHERE   matricula.id_periodos = '" + per.getIdPeriodos() + "'   AND (notas.convalidad = true or notas.convalidad is null) "
                + " AND matricula.id_carreras =  '" + carrerasSeleccionada.getIdCarreras() + "' "
                + "AND matricula.id_matriculas = '" + matriculasSeleccionada.getIdMatriculas() + "'  "
                + "ORDER BY estudiantes.apellido_paterno, estudiantes.apellido_materno, estudiantes.nombre";
        System.out.println("" + q);
        List nativo = adm.queryNativo(q);
        Date fechaActual = adm.Date();
        DateMidnight actual = new DateMidnight(fechaActual);
        int xy = 0;
        for (Iterator itna = nativo.iterator(); itna.hasNext();) {
            Object[] vec = (Object[]) itna.next();
            int a = 0;
            int x = 0;
            ArrayList notaAnadir = new ArrayList();
            Boolean estadoNota = false;
            Double notaVerifica = 0.0;
            for (a = 0; a < vec.length; a++) {
                SistemaNotas tnota = sistemas.get(x);
                NotasIngresar n = new NotasIngresar();
                if (a == vec.length - 1) {
                    String object = (String) vec[a];
                    n.setNombre(aprobado(rangos, notaVerifica));
                    n.setTexto(true);
                    n.setNota(null);
                    if (n.getNombre().contains("A")) {
                        n.setNombre("APROBADO");
                        n.setColorEstado("blue");
                    } else {
                        n.setNombre("REPROBADO");
                        n.setColorEstado("red");
                    }
                    System.out.println("NOTAveri: " + notaVerifica);
                    n.setAncho(12);
                } else if (a == 2) {
                    String object = (String) vec[a];
                    n.setNombre(object);
                    n.setTexto(true);
                    n.setNota(null);
                    n.setColorEstado("black");
                    if (estadoNota) {
                        n.setColorEstado("red");
                    }
                    n.setAncho(45);
                } else if (a == 1) {
                    Integer object = (Integer) vec[a];
                    n.setNombre(object + "");
                    n.setTexto(true);
                    n.setColorEstado("black");
                    n.setNota(null);
                    n.setAncho(2);
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
                    n.setAncho(5);
                } else {

                    DateMidnight inicial = new DateMidnight(tnota.getFechai());
                    DateMidnight finale = new DateMidnight(tnota.getFechaf());
//                    if (interno) {
//                        inicial = new DateMidnight(tnota.getFechasi());
//                        finale = new DateMidnight(tnota.getFechasf());
//                    }
//
//                    if (actual.compareTo(finale) <= 0 && actual.compareTo(inicial) >= 0) {
//                        n.setEstado(true);//habilito la notaIngresada AQUI CAMBIE DE FALSE A TRUE
//                        n.setColor("white");
//                    } else {
//                        n.setEstado(true);// deshabilita la notaIngresada
//                        n.setColor("#E9E7E2");
//                    }
//                    if (estadoNota) {
//                        n.setEstado(estadoNota);
//                        n.setColor("#E9E7E2");
//
//                    }

                    Double object = (Double) vec[a];
                    if (object == null) {
                        object = new Double(0.0);
                    }
                    n.setDesde(0.0);
                    n.setHasta(100d);
                    n.setNota(redondear(object.doubleValue(), 2));
                    n.setNombre("");
                    n.setAncho(10);
                    n.setTexto(false);
                    if (tnota.getEsnota()) {
                        notaVerifica = n.getNota();
                    }
                    if (tnota.getEsgpa()) {
                        n.setNombre(gpa(rangos, n.getNota()) + "");
                        n.setTexto(true);
                        n.setAncho(10);
                        n.setColorEstado("black");
                        n.setNota(null);
                    }
                    if (tnota.getEsexamen()) {
                        n.setNombre(equivalencia(rangos, n.getNota()) + "");
                        n.setTexto(true);
                        n.setNota(null);
                        n.setColorEstado("black");
                        n.setAncho(10);
                    }

                    x++;
                }
                if (a > 0) {
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
     * REGRESAR GPA
     */
    public Double gpa(List<RangosGpa> ra, Double valor) {
        for (Iterator<RangosGpa> it = ra.iterator(); it.hasNext();) {
            RangosGpa rangosGpa = it.next();
            if (valor.doubleValue() >= rangosGpa.getDesde() && valor.doubleValue() <= rangosGpa.getHasta()) {
                return rangosGpa.getGpa().doubleValue();
            }
        }
        return 0.0;
    }

    /**
     * REGRESAR EQUIVALENCIA
     */
    public String equivalencia(List<RangosGpa> ra, Double valor) {
        for (Iterator<RangosGpa> it = ra.iterator(); it.hasNext();) {
            RangosGpa rangosGpa = it.next();
            if (valor.doubleValue() >= rangosGpa.getDesde() && valor.doubleValue() <= rangosGpa.getHasta()) {
                return rangosGpa.getEquivalencia();
            }
        }
        return "";
    }

    public String aprobado(List<RangosGpa> ra, Double valor) {
        for (Iterator<RangosGpa> it = ra.iterator(); it.hasNext();) {
            RangosGpa rangosGpa = it.next();
            if (valor.doubleValue() >= rangosGpa.getDesde() && valor.doubleValue() <= rangosGpa.getHasta()) {
                return rangosGpa.getResultado();
            }
        }
        return "";
    }

    /**
     * Graba el registro asociado al objeto que
     */
    public void guardar() {
        List<RangosGpa> rangos = adm.query("Select o from RangosGpa as o ");
        FacesContext context = FacesContext.getCurrentInstance();
        if (!permisos.verificarPermisoReporte("Registro de Notas", "agregar_notas.jspx", "agregar", true, "NOTAS")) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "No tiene permisos para realizar ésta acción", "No tiene permisos para realizar ésta acción"));
            return;
        }

        try {
            System.out.println("INICIO GUARDAR NOTAS : " + new Date());
            String redondear = "public Double redondear(Double numero, int decimales) {"
                    + "    try{" + "        java.math.BigDecimal d = new java.math.BigDecimal(numero);" + "        d = d.setScale(decimales, java.math.RoundingMode.HALF_UP);" + "        return d.doubleValue();" + "        }catch(Exception e){" + "            return 0.0;" + "        } " + "     } ";

            Interpreter inter = new Interpreter();
            secuencial sec = new secuencial();
            //List<SistemaNotas> notas = adm.query("Select o from SistemaNotas as o order by o.sistema.orden ");
            List<SistemaNotas> sistemas0 = adm.query("Select o from SistemaNotas as o "
                    + "where o.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "' "
                    + "order by o.idSistemaNotas");
            for (Iterator<SistemaNotas> its = sistemas0.iterator(); its.hasNext();) {
                SistemaNotas acaNotanotas = its.next();
                if (!acaNotanotas.getFormula().trim().equals("")) {
//                    if (verificar(acaNotanotas.getFormula().trim(), false) == false) {
//                        mensajes = "NO SE HA PROCEDIDO A GUARDAR REVISE LAS FORMULAS DE DISCIPLINA";
//                        return;
//                    }
                }


            }
            List<SistemaNotas> notas = adm.query("Select o from SistemaNotas as o "
                    + "where o.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "' order by o.idSistemaNotas");
//            String del = "Delete from AcaNotas where matCodigo.curCodigo.curCodigo = '" + this.asiprofesor.getCurCodigo().getCurCodigo() + "' " + "and asiCodigo.asiCodigo = '" + asiprofesor.getAsiCodigo().getAsiCodigo() + "' ";
//            adm.ejecutaSql(del);
            inter.eval(redondear);
            for (int i = 0; i < listaNotas.size(); i++) {
                try {
                    ArrayList labels = (ArrayList) listaNotas.get(i);
                    Notas notaIngresada = new Notas();
                    notaIngresada.setIdNotas(sec.generarClave());
                    System.out.println("" + ((NotasIngresar) labels.get(0)).getNombre());
//                    MateriasMatricula matMat = (MateriasMatricula) adm.querySimple("Select o from MateriasMatricula as o "
//                            + "where o.idMatriculas.idMatriculas = '" + new Matriculas(new Integer(((NotasIngresar) labels.get(0)).getNombre())).getIdMatriculas() + "' "
//                            + " and o.idMaterias.idMaterias = '" + materiasSeleccionada.getIdMaterias() + "' "
//                            + " and o.idMatriculas.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "' ");
                    notaIngresada.setIdMatriculas(new Matriculas(new Integer(((NotasIngresar) labels.get(0)).getNombre())));
                    notaIngresada.setIdMaterias(materiasSeleccionada);
                    notaIngresada.setConvalidad(false);
                    notaIngresada.setEstado("");
                    notaIngresada.setEstadoNot(true);
                    notaIngresada.setEquivalencia("");
                    notaIngresada.setConvalidad(true);
                    //notaIngresada.setSistemaNotas(asiprofesor.getOrden());
                    //notaIngresada.setNotFecha(new Date());
                    inter.set("nota", notaIngresada);
                    for (int j = 2; j < labels.size() - 1; j++) {
                        NotasIngresar object1 = (NotasIngresar) labels.get(j);
                        String formula = notas.get(j - 2).getFormula(); // EN CASO DE FORMULA
                        formula = formula.replace("no", "nota.getNo");//EN CASO DE QUE HAYA FORMULA
                        String toda = notas.get(j - 2).getNota() + "";
                        String uno = toda.substring(0, 1).toUpperCase();
                        toda = toda.substring(1, toda.length());
                        inter.eval("nota.set" + (uno + toda) + "(" + redondear(object1.getNota(), 2) + ");");
                        inter.eval("Double N" + notas.get(j - 2).getIdSistemaNotas() + " = " + redondear(object1.getNota(), 2) + ";");
                        if (!formula.isEmpty()) {
                            inter.eval("nota.set" + (uno + toda) + "((" + formula + "));");
                            inter.eval("Double N" + notas.get(j - 2).getIdSistemaNotas() + " = " + formula + ";");
                        }
                        Double valor = (Double) inter.get("nota." + (uno + toda) + "");
//                    System.out.println("NOTA: "+valor);
                        object1.setNota(redondear(valor.doubleValue(), 2));
                        if (notas.get(j - 2).getEsnota()) {
                            inter.eval("nota.setEstado(\"" + aprobado(rangos, valor) + " \");");
                        }
                    }
                    notaIngresada = (Notas) inter.get("nota");
//                    String del = "Delete from AcaNotas where matCodigo.curCodigo.curCodigo = '" + this.asiprofesor.getCurCodigo().getCurCodigo() + "' " +
//                            "and asiCodigo.asiCodigo = '" + asiprofesor.getAsiCodigo().getAsiCodigo() + "' and disciplina = false " +
//                            "and matCodigo.matCodigo = '" + notaIngresada.getMatCodigo().getMatCodigo() + "' ";
                    String del = "Delete from Notas "
                            + " where idMaterias.idMaterias = '" + materiasSeleccionada.getIdMaterias() + "' "
                            + " and idMatriculas.idMatriculas = '" + notaIngresada.getIdMatriculas().getIdMatriculas() + "'   ";
                    adm.ejecutaSql(del);

                    adm.guardar(notaIngresada);

                    //adm.crearAcaNotas(acaNotas);
                } catch (EvalError ex) {

                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "NO SE HA GRABADO LOS REGISTROS\\n ERROR: " + ex));
                    Logger.getLogger(NotasBeanConvalidaciones.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }

            }
            matriculasSeleccionada = (Matriculas) adm.buscarClave(matriculasSeleccionada.getIdMatriculas(), Matriculas.class);
            materiasSeleccionada = (Materias) adm.buscarClave(materiasSeleccionada.getIdMaterias(), Materias.class);
            aud.auditar(adm, "Convalidaciones", "guardar", "" + materiasSeleccionada.getNombre(), matriculasSeleccionada.getNumero() + " " + matriculasSeleccionada.getIdEstudiantes().getApellidoPaterno() + " " + matriculasSeleccionada.getIdEstudiantes().getNombre());
            buscarNotas();
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_INFO, "Información", "Registro Almacenado con éxito...!"));
            //            aud.auditar(adm, this.getClass().getSimpleName().replace("Bean", ""), "guardar", "", object.getIdNotas() + "");
        } catch (EvalError ex) {
            Logger.getLogger(NotasBeanConvalidaciones.class.getName()).log(Level.SEVERE, null, ex);
        }
//        try {
//            object.setIdNotas(secuencial.generarClave());
//            adm.guardar(object);

//            inicializar();
//            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage("Guardado...!"));
//
//        } catch (Exception e) {
//            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, e.getMessage(), e.getMessage()));
//        }

        //return null;
    }

    /**
     * Elimina un registro asociado a la página
     */
    public String eliminar(Notas obj) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            if (!permisos.verificarPermisoReporte("Registro de Notas", "eliminar_notas.jspx", "eliminar", true, "NOTAS")) {
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
            java.util.logging.Logger.getLogger(NotasBeanConvalidaciones.class.getName()).log(Level.SEVERE, null, e);
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
            //Periodos per = (Periodos) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodo");
            List<Horarios> materiasListado = new ArrayList<Horarios>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            materiasListado = adm.query("Select o from Horarios as o "
                    + " where o.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "' "
                    + " and o.idCarreras.idCarreras = '" + carrerasSeleccionada.getIdCarreras() + "'  "
                    + " order by o.idNiveles.secuencia asc, o.idMaterias.nombre ");
            ArrayList agregados = new ArrayList();
            if (materiasListado.size() > 0) {
                Materias objSel = new Materias(0);
                items.add(new SelectItem(objSel, "Seleccione..."));
                for (Horarios obj : materiasListado) {
                    if (!agregados.contains(obj.getIdMaterias().getIdMaterias())) {
                        items.add(new SelectItem(obj.getIdMaterias(), obj.getIdNiveles().getNombre() + " | " + obj.getIdMaterias().getNombre() + " "));
                    }
                    agregados.add(obj.getIdMaterias().getIdMaterias());
                }
            } else {
                Materias obj = new Materias(0);
                items.add(new SelectItem(obj, "SELECCIONE UNA CARRERA"));
            }


            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(Horarios.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    public List<SelectItem> getSelectedItemMatriculas() {
        try {
            //Periodos per = (Periodos) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("periodo");
            List<Matriculas> materiasListado = new ArrayList<Matriculas>();
            List<SelectItem> items = new ArrayList<SelectItem>();
            materiasListado = adm.query("Select o from Matriculas as o "
                    + " where o.idPeriodos.idPeriodos = '" + per.getIdPeriodos() + "' "
                    + " and o.idCarreras.idCarreras = '" + carrerasSeleccionada.getIdCarreras() + "' and o.estadoMat = 'M'  "
                    + " order by o.idEstudiantes.apellidoPaterno ");
            ArrayList agregados = new ArrayList();
            if (materiasListado.size() > 0) {
                Matriculas objSel = new Matriculas(0);
                items.add(new SelectItem(objSel, "Seleccione..."));
                for (Matriculas obj : materiasListado) {
                    items.add(new SelectItem(obj, obj.getIdEstudiantes().getApellidoPaterno() + " " + obj.getIdEstudiantes().getApellidoMaterno() + " " + obj.getIdEstudiantes().getNombre()));
                }
            } else {
                Matriculas obj = new Matriculas(0);
                items.add(new SelectItem(obj, "SELECCIONE UNA CARRERA"));
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
            java.util.logging.Logger.getLogger(NotasBeanConvalidaciones.class.getName()).log(Level.SEVERE, null, e);
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
            java.util.logging.Logger.getLogger(NotasBeanConvalidaciones.class.getName()).log(Level.SEVERE, null, e);
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

    public Carreras getCarrerasSeleccionada() {
        return carrerasSeleccionada;
    }

    public void setCarrerasSeleccionada(Carreras carrerasSeleccionada) {
        this.carrerasSeleccionada = carrerasSeleccionada;
    }

    public Matriculas getMatriculasSeleccionada() {
        return matriculasSeleccionada;
    }

    public void setMatriculasSeleccionada(Matriculas matriculasSeleccionada) {
        this.matriculasSeleccionada = matriculasSeleccionada;
    }
}
