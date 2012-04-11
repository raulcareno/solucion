/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import java.util.Collection;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Empleados;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class EstadisticoPorcentajes {
    
    public Integer contador;
    public Integer total;
    public String equivalencia;
    public String tipo;
    public Object valor;
    public Double promedio;
    public Double porcentaje;
    public String materia;
    public Integer numero;
    public Integer matriculados;
    public Integer nomatriculados;
    public Empleados empleado;
    public Cursos curso;
     
    
    public EstadisticoPorcentajes(){
        
         }

    public Integer getContador() {
        return contador;
    }

    public void setContador(Integer contador) {
        this.contador = contador;
    }

    public String getEquivalencia() {
        return equivalencia;
    }

    public void setEquivalencia(String equivalencia) {
        this.equivalencia = equivalencia;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }

    
     public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

     

    public Integer getMatriculados() {
        return matriculados;
    }

    public void setMatriculados(Integer matriculados) {
        this.matriculados = matriculados;
    }

    public Integer getNomatriculados() {
        return nomatriculados;
    }

    public void setNomatriculados(Integer nomatriculados) {
        this.nomatriculados = nomatriculados;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public Cursos getCurso() {
        return curso;
    }

    public void setCurso(Cursos curso) {
        this.curso = curso;
    }

    public Empleados getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleados empleado) {
        this.empleado = empleado;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    
     
}
