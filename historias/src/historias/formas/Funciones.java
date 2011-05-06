/*
 * Funciones.java
 *
 * Created on 18 de noviembre de 2007, 09:14 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package historias.formas;

/**
 *
 * @author Francisco
 */
public class Funciones {
    
    /** Creates a new instance of Funciones */
    public Funciones() {
    }
    
    public String meses(int i){
        switch(i){
            case 0: return "ENERO";
            case 1: return "FEBRERO";
            case 2: return "MARZO";
            case 3: return "ABRIL";
            case 4: return "MAYO";
            case 5: return "JUNIO";
            case 6: return "JULIO";
            case 7: return "AGOSTO";
            case 8: return "SEPTIEMBRE";
            case 9: return "OCTUBRE";
            case 10: return "NOVIEMBRE";
            case 11: return "DICIEMBRE";
   
            
        }
        return null;
    }
    

    
    public int ultimoDiaMes(int anio, int mes){
        int valor = anio%4;
        if(mes==1 || mes==3 || mes==5 || mes==7 || mes==8 || mes==10 || mes==12){
            return 31;
        }else if(mes==4 || mes==6 || mes==9 || mes==11){
            return 30;
        }else if(mes==2){
            if(valor==0)
                return 29;
            else
                return 28;
        }
        return 0;
    }
    
}
