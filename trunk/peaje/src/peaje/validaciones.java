/*
 * ValidarCedula.java
 *
 * Created on 27 de febrero de 2006, 11:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package peaje;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Shanto
 */
public class validaciones {
    
    /**
     * Creates a new instance of ValidarCedula
     */
    public validaciones() {
    }
   public boolean valida_cedula(String intcedula){
        int j;
        String val_cedula="False"; 
        int b,f,g,h;
        String a,x,w,z;
        a = intcedula.substring(0,2);
        b=Integer.parseInt(a);
        
        if(intcedula.length()!=10) 
            return false;
        if (b > 22 || b<1){ return false;}
        //x= intcedula.substring(9, 10);
  if (digito(intcedula)){ return true;}
        else{ return false;        }
        
   }
    
private boolean digito(String intcedula){
        int v_num=0;
        int v_acum=0;
        int aux=0;
        String v_char="";
        int i;
        int j=0;
 for(i=0;i<9;i++){
     j+=1;
   v_char= intcedula.substring(i,j);
   v_num=Integer.parseInt(v_char);
     if (i%2!=1){
       v_num = v_num*2;
            if (v_num > 9)
                v_num= v_num-9;
     }
   v_acum= v_acum + v_num;
   }
 v_acum=10-(v_acum%10);

   if(v_acum == 10){
      v_acum=0;}
    
 v_char= String.valueOf(v_acum);
 aux= Integer.parseInt(v_char); 
 String a=intcedula.substring(9,10);
 int aux1= Integer.parseInt(a) ;
 if (aux==aux1)
     return true;
 else
     return false;
    }


    
    /**
     * Valida el n�mero de RUC o c�dula de ciudadan�a
     * @param ruc El n�mero de RUC (13 digitos) o c�dula (10 d�gitos), esta no debe contener el gi�n
     * @return -1 si el RUC o c�dula es incorrecto
     * 1 si es RUC para Sociedades Privadas y Extranjeros sin c�dula
     * 2 si es RUC para Sociedades P�blicas
     * 3 si es RUC para Personas Naturales
     * 4 si es c�dula
     */

    public int validaRucCedula(String ruc){
        if(ruc.length()==13 || ruc.length()==10){
            try{ //comprobamos si ruc es num�rico
                long l = Long.parseLong(ruc);
            }catch(Exception e){
                return -1;
            }
            try{
                int provincia = Integer.parseInt(ruc.substring(0,2));
                if(provincia <0 || provincia >22) // recomendable utilizar archivo de propiedades
                    return -1;
                int tercerDigito = Integer.parseInt(ruc.substring(2,3));
                if(tercerDigito == 9){ // es RUC para Sociedades Privadas y Extranjeros sin c�dula
                    int[] modulo11 = {4,3,2,7,6,5,4,3,2};
                    int numeros[] = new int[9];
                    for(int i = 0; i < 9; i++){
                        numeros[i] = Integer.parseInt(ruc.substring(i,i+1));
                        modulo11[i] *= numeros[i];
                    }
                    int suma = 0;
                    for(int i = 0; i < 9; i++)
                        suma += modulo11[i];
                    int digitVerificador = 11 - (suma % 11);
                    if(suma % 11 == 0) digitVerificador = 0; // si el residuo es 0 digitoVerificador es 0
                    
                    int decimoDigito = Integer.parseInt(ruc.substring(9,10));
                    if(decimoDigito != digitVerificador)
                        return -1;
                    int numeroEstablecimientos = Integer.parseInt(ruc.substring(10,13));
                    if(numeroEstablecimientos > 9) return -1;
                    return 1;
                }else if(tercerDigito == 6){ // del RUC para Sociedades P�blicas
                    int[] modulo11 = {3,2,7,6,5,4,3,2};
                    int numeros[] = new int[8];
                    for(int i = 0; i < 8; i++){
                        numeros[i] = Integer.parseInt(ruc.substring(i,i+1));
                        modulo11[i] *= numeros[i];
                    }
                    int suma = 0;
                    for(int i = 0; i < 8; i++)
                        suma += modulo11[i];
                    int digitVerificador = 11 - (suma % 11);
                    if(suma % 11 == 0) digitVerificador = 0; // si el residuo es 0 digitoVerificador es 0
                    
                    int decimoDigito = Integer.parseInt(ruc.substring(8,9));
                    if(decimoDigito != digitVerificador)
                        return -1;
                    int numeroEstablecimientos = Integer.parseInt(ruc.substring(9,13));
                    if(numeroEstablecimientos > 9) return -1;
                    return 2;
                }else if(tercerDigito < 6){ // RUC para Personas Naturales o c�dula
                    int[] modulo11 = {2,1,2,1,2,1,2,1,2};
                    int numeros[] = new int[9];
                    for(int i = 0; i < 9; i++){
                        numeros[i] = Integer.parseInt(ruc.substring(i,i+1));
                        modulo11[i] *= numeros[i];
                        if(modulo11[i] >=10)  modulo11[i] -= 9;
                    }
                    int suma = 0;
                    for(int i = 0; i < 9; i++)
                        suma += modulo11[i];
                    int digitVerificador = 10 - (suma % 10);
                    if(suma % 10 == 0) digitVerificador = 0; // si el residuo es 0 digitoVerificador es 0
                    
                    int decimoDigito = Integer.parseInt(ruc.substring(9,10));
                    if(decimoDigito != digitVerificador)
                        return -1;
                    if(ruc.length()==13){
                        int numeroEstablecimientos = Integer.parseInt(ruc.substring(10,13));
                        if(numeroEstablecimientos > 9) return -1;
                        return 3; 
                    }
                    return 4; // es una cedula
                }
            }catch (Exception e){
                return -1;
            }
            
        }
        return -1; // ruc o c�dula incorrecta
    }   

  private static final char[] HEXADECIMAL = { '0', '1', '2', '3',
        '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

    public static String encriptar(String stringToHash)  {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(stringToHash.getBytes());
            StringBuilder sb = new StringBuilder(2 * bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                int low = (int)(bytes[i] & 0x0f);
                int high = (int)((bytes[i] & 0xf0) >> 4);
                sb.append(HEXADECIMAL[high]);
                sb.append(HEXADECIMAL[low]);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(validaciones.class.getName()).log(Level.SEVERE, null, ex);

        }
           return null;
    }




    
}
