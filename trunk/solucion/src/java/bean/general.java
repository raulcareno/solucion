/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.math.BigDecimal;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Sistemacalificacion;

/**
 *
 * @author Geovanny
 */
public class general {

    public general() {
    }
    
public Matriculas matricula;
public BigDecimal nota;
public Sistemacalificacion sistema;

    public Matriculas getMatricula() {
        return matricula;
    }

    public void setMatricula(Matriculas matricula) {
        this.matricula = matricula;
    }

    public BigDecimal getNota() {
        return nota;
    }

    public void setNota(BigDecimal nota) {
        this.nota = nota;
    }

    public Sistemacalificacion getSistema() {
        return sistema;
    }

    public void setSistema(Sistemacalificacion sistema) {
        this.sistema = sistema;
    }


    
}
