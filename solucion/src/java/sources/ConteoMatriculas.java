/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import java.math.BigDecimal;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class ConteoMatriculas {
    
    public Integer conteo;
    public String nombrecurso;
    public String paralelo;
    public String especialidad;
    public String genero;
    public Integer numero;
    public BigDecimal promedio;
    public String materia;
     
    
    public ConteoMatriculas(){
        
         }

    public Integer getConteo() {
        return conteo;
    }

    public void setConteo(Integer conteo) {
        this.conteo = conteo;
    }

  

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombrecurso() {
        return nombrecurso;
    }

    public void setNombrecurso(String nombrecurso) {
        this.nombrecurso = nombrecurso;
    }

    public String getParalelo() {
        return paralelo;
    }

    public void setParalelo(String paralelo) {
        this.paralelo = paralelo;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public BigDecimal getPromedio() {
        return promedio;
    }

    public void setPromedio(BigDecimal promedio) {
        this.promedio = promedio;
    }
  
    
}
