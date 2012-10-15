/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes.bean;

import bean.*;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import jcinform.persistencia.Canton;
import jcinform.persistencia.Carreras;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Pais;
import jcinform.persistencia.Provincia;
import jcinform.procesos.Administrador;
import reportes.ExportarReportes;
import reportes.FichaMatricula;

import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class ReportesBean implements Serializable {

    Administrador adm;
    Permisos permisos;
    Auditar aud = new Auditar();
    private Matriculas selectedMatricula;
    private Matriculas[] selectedMatriculas;
    private List<Matriculas> mediumMatriculasModel;

    public ReportesBean() {
        //super();
        FacesContext context = FacesContext.getCurrentInstance();

        if (adm == null) {
            adm = new Administrador();
        }
        if (permisos == null) {
            permisos = new Permisos();
        }
        if (!permisos.verificarPermisoReporte("Institucion", "ingresar_institucion", "ingresar", true, "PARAMETROS")) {
            try {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "No tiene permisos para ingresar"));
                FacesContext.getCurrentInstance().getExternalContext().redirect("noPuedeIngresar.jspx");
            } //selectedInstitucion = new Institucion();
            catch (IOException ex) {
                java.util.logging.Logger.getLogger(InstitucionBean.class.getName()).log(Level.SEVERE, null, ex);
//                Logger.getLogger(InstitucionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //selectedMatricula = new Matriculas();
        //selectedInstitucion = new Institucion();

    }

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
    public Boolean todos;

    public void seleccionarTodos() {
        for (Iterator<Matriculas> it = mediumMatriculasModel.iterator(); it.hasNext();) {
            Matriculas matriculas = it.next();
            matriculas.setConfirmada(todos);
        }
    }

    public void buscarMatriculas() {

        try {
            try {
                mediumMatriculasModel = adm.query("Select o from Matriculas as o "
                        + "where o.idCarreras.idCarreras = '" + carreraSeleccionado.getIdCarreras() + "' order by o.idEstudiantes.apellidoPaterno ");
                for (Iterator<Matriculas> it = mediumMatriculasModel.iterator(); it.hasNext();) {
                    Matriculas matriculas = it.next();
                    matriculas.setConfirmada(false);
                }
            } catch (Exception e) {
                System.out.println("NO SE HAN ENCONTRADO MATRICULADOS");
            }
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(CantonBean.class.getName()).log(Level.SEVERE, null, e);
        }


    }

    public List<SelectItem> getSelectedItemCarreras() {
        try {
            List<Carreras> carrerasListado = new ArrayList<Carreras>();
            List<SelectItem> items = new ArrayList<SelectItem>();

            carrerasListado = adm.query("Select o from Carreras as o "
                    + " order by o.nombre ");
            if (carrerasListado.size() > 0) {
                Carreras objSel = new Carreras(0);
                items.add(new SelectItem(objSel, "Seleccione..."));
                for (Carreras obj : carrerasListado) {
                    items.add(new SelectItem(obj, obj.getNombre()));
                }
            } else {
                Carreras obj = new Carreras(0);
                items.add(new SelectItem(obj, "NO EXISTEN CARRERAS"));
            }

            return items;
        } catch (Exception e) {
            java.util.logging.Logger.getLogger(MatriculasBean.class.getName()).log(Level.SEVERE, null, e);

        }
        return null;
    }

    public String verReporte(String tipo) {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            List<Estudiantes> estu = new ArrayList<Estudiantes>();
            Map map = new HashMap();
            if (tipoReporte.equals("")) {
                FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione un Reporte...!", "Seleccione un Reporte...!"));
                return null;
            } else if (tipoReporte.equals("FM")) { //Ficha de Matricula" itemValue="FM" />
                if (mediumMatriculasModel.size()  <= 0) {
                    FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, "Seleccione un Estudiante...!", "Seleccione un Estudiante...!"));
                    return null;
                }
                for (int i = 0; i < mediumMatriculasModel.size() ; i++) {
                    if(mediumMatriculasModel.get(i).getConfirmada())
                        estu.add(mediumMatriculasModel.get(i).getIdEstudiantes());
                }
                map.put("tituloReporte", "FICHA DE MATRICULA");
                map.put("titulo1", "USUARIO");
                map.put("titulo2", "# DE INFORMES");
            }




            ExportarReportes ex = new ExportarReportes(estu, "fichaMatricula", map);
            if (tipo.equals("PDF")) {
                ex.PDF();
            } else if (tipo.equals("DOCX")) {
                ex.DOCX();
            } else if (tipo.equals("XLS")) {
                ex.XLSX();
            } else if (tipo.equals("PRINT")) {
                ex.PRINT();
            }
        } catch (Exception ex1) {
            FacesContext.getCurrentInstance().addMessage(findComponent(context.getViewRoot(), "form").getClientId(), new FacesMessage(FacesMessage.SEVERITY_ERROR, ex1.getMessage(), ex1.getMessage()));
            java.util.logging.Logger.getLogger(FichaMatricula.class.getName()).log(Level.SEVERE, null, ex1);
        }
        return null;

    }
    protected String tipoReporte;

    /**
     * Get the value of tipoReporte
     *
     * @return the value of tipoReporte
     */
    public String getTipoReporte() {
        return tipoReporte;
    }

    /**
     * Set the value of tipoReporte
     *
     * @param tipoReporte new value of tipoReporte
     */
    public void setTipoReporte(String tipoReporte) {
        this.tipoReporte = tipoReporte;
    }

    public Matriculas getSelectedMatricula() {
        return selectedMatricula;
    }

    public void setSelectedMatricula(Matriculas selectedMatricula) {
        this.selectedMatricula = selectedMatricula;
    }

    public Matriculas[] getSelectedMatriculas() {
        return selectedMatriculas;
    }

    public void setSelectedMatriculas(Matriculas[] selectedMatriculas) {
        this.selectedMatriculas = selectedMatriculas;
    }

    public List<Matriculas> getMediumMatriculasModel() {
        return mediumMatriculasModel;
    }

    public void setMediumMatriculasModel(List<Matriculas> mediumMatriculasModel) {
        this.mediumMatriculasModel = mediumMatriculasModel;
    }
    private Carreras carreraSeleccionado;

    /**
     * Get the value of carreraSeleccionado
     *
     * @return the value of carreraSeleccionado
     */
    public Carreras getCarreraSeleccionado() {
        return carreraSeleccionado;
    }

    /**
     * Set the value of carreraSeleccionado
     *
     * @param carreraSeleccionado new value of carreraSeleccionado
     */
    public void setCarreraSeleccionado(Carreras carreraSeleccionado) {
        this.carreraSeleccionado = carreraSeleccionado;
    }

    public Boolean getTodos() {
        return todos;
    }

    public void setTodos(Boolean todos) {
        this.todos = todos;
    }
}
