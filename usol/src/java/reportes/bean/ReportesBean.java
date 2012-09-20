/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package reportes.bean;

import bean.*;
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
import jcinform.persistencia.Canton;
import jcinform.persistencia.Carreras;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Pais;
import jcinform.persistencia.Provincia;
import jcinform.procesos.Administrador;

import utilerias.Permisos;

/**
 *
 * @author Geovanny
 */
@ManagedBean
@ViewScoped
public class ReportesBean {

    Administrador adm;
    Permisos permisos;
    Auditar aud = new Auditar();
 private Matriculas selectedMatricula;  
  
    private Matriculas[] selectedMatriculas;  
  
    private MatriculasDataModel mediumMatriculasModel;  
  
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
    public void buscarMatriculas(){
        
        try {
            List<Matriculas> matriculasEncontradas = new ArrayList<Matriculas>();
            

//                if (!object.getIdCanton().equals("")) {
                try {
                    matriculasEncontradas = adm.query("Select o from Matriculas as o "
                            + "where o.idCarreras.idCarreras = '" + carreraSeleccionado.getIdCarreras() + "' order by o.idEstudiantes.apellidoPaterno ");
                    if(matriculasEncontradas.size()>0){
//                        Matriculas mat = new Matriculas(-1);
//                        mat.setIdEstudiantes(new Estudiantes());
//                        mat.getIdEstudiantes().setApellidoPaterno("[ T O D O S ]");
//                        mat.getIdEstudiantes().setApellidoMaterno(" ");
//                        mat.getIdEstudiantes().setNombre(" ");
//                        matriculasEncontradas.add(mat);
                    }
                    mediumMatriculasModel = new MatriculasDataModel(matriculasEncontradas);    
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

    public MatriculasDataModel getMediumMatriculasModel() {
     
        return mediumMatriculasModel;
    }

    public void setMediumMatriculasModel(MatriculasDataModel mediumMatriculasModel) {
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

}
