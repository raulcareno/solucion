/*
 * Numeros.java
 *
 * Created on 24 de septiembre de 2007, 09:44 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package sources;

/**
 *
 * @author Francisco
 */
public class NumerosaLetras {
    
    /** Creates a new instance of Numeros */
    public NumerosaLetras() {
    }
  
    public String numeros(Double numero){
                double dob = numero;
                String dd = String.valueOf(java.lang.Math.round(dob));
                int enteros = Integer.parseInt(dd);
//        String sobra = numero.substring(3,numero.length());
//        System.out.println(enteros);
        String valor = null;
        switch(enteros){
            case 0:
                valor = "Cero";
                break;
            case 1:
                valor = "Uno";
                break;
            case 2:
                valor = ("Dos");
                break;
            case 3:
                valor = ("Tres");
                break;
            case 4:
                valor = ("Cuatro");
                break;
            case 5:
                valor = ("Cinco");
                break;
            case 6:
                valor = ("Seis");
                break;
            case 7:
                valor = ("Siete");
                break;
            case 8:
                valor = ("Ocho");
                break;
            case 9:
                valor = ("Nueve");
                break;
            case 10:
                valor = ("Diez");
                break;
            case 11:
                valor = ("Once");
                break;
            case 12:
                valor = ("Doce");
                break;
            case 13:
                valor = ("Trece");
                break;
            case 14:
                valor = ("Catorce");
                break;
            case 15:
                valor = ("Quince");
                break;
            case 16:
                valor = ("Dieciséis");
                break;
            case 17:
                valor = ("Diecisiete");
                break;
            case 18:
                valor = ("Dieciocho");
                break;
            case 19:
                valor = ("Diecinueve");
                break;
            case 20:
                valor = ("Veinte");
                break;
                
                
        }
        //return valor + sobra +"/100";
        return valor;
        //+ sobra +"/100";
        
    }
    
     public String numerosDecimales(Double numero){
        Double ent  = java.lang.Math.floor(numero);
        Long aa = ent.longValue();
        int enteros = Integer.valueOf(aa.toString());
//        System.out.println(enteros);
        String valor = null;
        switch(enteros){
            case 0:
                valor = "Cero";
                break;
            case 1:
                valor = "Uno";
                break;
            case 2:
                valor = ("Dos");
                break;
            case 3:
                valor = ("Tres");
                break;
            case 4:
                valor = ("Cuatro");
                break;
            case 5:
                valor = ("Cinco");
                break;
            case 6:
                valor = ("Seis");
                break;
            case 7:
                valor = ("Siete");
                break;
            case 8:
                valor = ("Ocho");
                break;
            case 9:
                valor = ("Nueve");
                break;
            case 10:
                valor = ("Diez");
                break;
            case 11:
                valor = ("Once");
                break;
            case 12:
                valor = ("Doce");
                break;
            case 13:
                valor = ("Trece");
                break;
            case 14:
                valor = ("Catorce");
                break;
            case 15:
                valor = ("Quince");
                break;
            case 16:
                valor = ("Dieciseis");
                break;
            case 17:
                valor = ("Diecisiete");
                break;
            case 18:
                valor = ("Dieciocho");
                break;
            case 19:
                valor = ("Diecinueve");
                break;
            case 20:
                valor = ("Veinte");
                break;
                
                
        }
//        if(sobra.length() >= 2)
//            sobra =sobra.substring(0,2);
        
        return valor;
//        return valor;
//        //+ sobra +"/100";
        
    }
    
             public String GetDigito(int Digit){
           
        switch(Digit){
            case 1 : return "y Uno";
            case 2 : return "y Dos";
            case 3 : return "y Tres";
            case 4 : return "y Cuatro";
            case 5 : return "y Cinco";
            case 6 : return "y Seis";
            case 7 : return "y Siete";
            case 8 : return "y Ocho";
            case 9 : return "y Nueve";
        }
        return null;
       }   
       public String GetDigit(int Digit){
           
        switch(Digit){
            case 1 : return "Cero Uno";
            case 2 : return "Cero Dos";
            case 3 : return "Cero Tres";
            case 4 : return "Cero Cuatro";
            case 5 : return "Cero Cinco";
            case 6 : return "Cero Seis";
            case 7 : return "Cero Siete";
            case 8 : return "Cero Ocho";
            case 9 : return "Cero Nueve";
        }
        return null;
       }
    
      public String GetHundreds(String MyNumber){
        
          
        
        String Result ="";
        String Quinien ="";
        String Quinien2= "";
        
        if(MyNumber.equals("0")) return "";
        //'Converte el lugar de las centenas
        Quinien2 = "Cientos "; 
//          System.out.println("NUMERO ORIGINAL--------------------------------"+MyNumber);
        if(MyNumber.length()==1)
              MyNumber = MyNumber+"00";
          if(MyNumber.length()==2)
              MyNumber = MyNumber+"0";
          if(MyNumber.length()>3)
              MyNumber = MyNumber.substring(0,3);
//        System.out.println("NUMERO RESULTADO--------------------------------"+MyNumber);
        
        if(!MyNumber.substring(0,1).equals("0")){
            Quinien = GetDigit(Integer.parseInt(String.valueOf(MyNumber).substring(0,1)));
            if(Quinien.equals("Cinco")){
                Quinien = "Quinientos ";
                Quinien2 = "";
            }
            if(Quinien.equals("Uno")){
                Quinien = "";
                Quinien2 = "Ciento ";
            }
            if(Quinien.equals("Nueve")){
                Quinien = "Nove";
                Quinien2 = "cientos ";
            }
            if(Quinien.equals("Siete")){
                Quinien = "Sete";
                Quinien2 = "cientos ";
            }
            
            if(!MyNumber.substring(1,2).equals("0"))
                Result = Quinien + Quinien2 + GetTens(Integer.parseInt(String.valueOf(MyNumber).substring(1,3)));
            else if(!MyNumber.substring(2,3).equals("0"))
                Result = Quinien + Quinien2 + "CERO CERO "+GetDigit(Integer.parseInt(String.valueOf(MyNumber).substring(2,3)));
            else if(MyNumber.substring(1,3).equals("00"))
            Result = Quinien + Quinien2;
            
                //' aca le agrega al numero la palabra
        }else{
            if(!MyNumber.substring(1,2).equals("0"))
                Result = GetTens(Integer.parseInt(String.valueOf(MyNumber).substring(1,3)));
            else if(!MyNumber.substring(2,3).equals("0"))
                Result = "CERO CERO "+ GetDigit(Integer.parseInt(String.valueOf(MyNumber).substring(2,3)));
            else if(!MyNumber.substring(1,3).equals("000"))
                Result ="CERO CERO CERO";
        }

       return Result;
}
      
       public String GetTens(int TensText){
        String Result ="";
        String digito="";
        Result = ""; //'anula el valor temporal de la funcion
        if(String.valueOf(TensText).substring(0,1).equals("1")){// si el valor esta entre 10-19
            switch (TensText){
                
                
                case 10 : Result = "Diez"; break;
                case 11 : Result = "Once"; break;
                case 12 : Result = "Doce"; break;
                case 13 : Result = "Trece"; break;
                case 14 : Result = "Catorce"; break;
                case 15 : Result = "Quince"; break;
                case 16 : Result = "Dieciseis"; break;
                case 17 : Result = "Diecisiete"; break;
                case 18 : Result = "Dieciocho"; break;
                case 19 : Result = "Diecinueve"; break;
             
            }
            
        }else{
        //' Si el valor esta entre 20-99
            switch(Integer.parseInt(String.valueOf(TensText).substring(0,1))){
                case 2 : Result = "Veinte "; break;
                case 3 : Result = "Treinta "; break;
                case 4 : Result = "Cuarenta "; break;
                case 5 : Result = "Cincuenta "; break;
                case 6 : Result = "Sesenta "; break;
                case 7 : Result = "Setenta "; break;
                case 8 : Result = "Ochenta "; break;
                case 9 : Result = "Noventa "; break;
                
            }
            if(Integer.parseInt(String.valueOf(TensText).substring(1,2))!=0)
                digito = GetDigito(Integer.parseInt(String.valueOf(TensText).substring(1,2)));
            Result = Result + digito;
        }
      
       return Result;
       }
       
       
  public String fecha(int fec){
       switch(fec){
           case 1: return "enero";
           case 2: return "febrero";
           case 3: return "marzo";
           case 4: return "abril";
           case 5: return "mayo";
           case 6: return "junio";
           case 7: return "julio";
           case 8: return "agosto";
           case 9: return "septiembre";
           case 10: return "octubre";
           case 11: return "noviembre";
           case 12: return "diciembre";
           
           
       }
       return null; 
    }
}
