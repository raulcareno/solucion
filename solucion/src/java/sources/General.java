/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;

import java.util.Collection;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class General implements Comparable{
    
    public Integer orden;
    public Object valor1;
    public Object valor2;
    public Object valor3;
    public Object valor4;
    public Object valor5;
    public Object valor6;
    public Object valor7;
    
    public General(){
        
         }

    public Object getValor1() {
        return valor1;
    }

    public void setValor1(Object valor1) {
        this.valor1 = valor1;
    }

    public Object getValor2() {
        return valor2;
    }

    public void setValor2(Object valor2) {
        this.valor2 = valor2;
    }

    public Object getValor3() {
        return valor3;
    }

    public void setValor3(Object valor3) {
        this.valor3 = valor3;
    }

    public Object getValor4() {
        return valor4;
    }

    public void setValor4(Object valor4) {
        this.valor4 = valor4;
    }

    public Object getValor5() {
        return valor5;
    }

    public void setValor5(Object valor5) {
        this.valor5 = valor5;
    }

    public Object getValor6() {
        return valor6;
    }

    public void setValor6(Object valor6) {
        this.valor6 = valor6;
    }

    public Object getValor7() {
        return valor7;
    }

    public void setValor7(Object valor7) {
        this.valor7 = valor7;
    }

    public Integer getOrden() {
        return orden;
    }

    public void setOrden(Integer orden) {
        this.orden = orden;
    }

 

     
    public int compareTo(Object o) {
    General otroUsuario = (General) o;
    //podemos hacer esto porque String implementa Comparable
        return orden.compareTo(otroUsuario.getOrden());
  }
    
    
}
