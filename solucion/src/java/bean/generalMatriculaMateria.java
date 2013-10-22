/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import jcinform.persistencia.Sistemacalificacion;

/**
 *
 * @author JCINFORM
 */
public class generalMatriculaMateria {

    public generalMatriculaMateria() {
    }
    
    public Integer matricula;
    public Integer materia;

    public generalMatriculaMateria(Integer matricula, Integer materia) {
        this.matricula = matricula;
        this.materia = materia;
    }

    public Integer getMateria() {
        return materia;
    }

    public void setMateria(Integer materia) {
        this.materia = materia;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

  
    
     
    
    public String toString() {
        return " " + matricula+" "+materia;
    }

    
}
