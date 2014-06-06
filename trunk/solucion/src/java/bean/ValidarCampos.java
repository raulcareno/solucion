/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 *
 * @author jcinform
 */
public class ValidarCampos {
        public ValidarCampos() {
    }
    /** funcion para validar la cedula,nombres etc.*/
    public boolean validaCedula(java.lang.String cedula) {
        int d1= 0;
        int d2= 0;
        
        if(cedula == null || !(cedula.length() == 10) ) 
            return false;
        try {
            int auxpd = 0;
            for(int i=0; i< cedula.length(); i++){
                int codigo= cedula.charAt(i);
                
                if(!(codigo >= 48 && codigo <= 57))// si los numeros no estan en este rango (codigo ASCII)
                    return false;
                switch(i) {
                    case 0 : if(codigo > 50)
                                return false;
                             else
                                 auxpd = codigo;
                             break;
                    case 1 : switch(auxpd) {
                                case 48 : if(codigo < 49)
                                            return false;
                                          break;
                                case 50 : if(codigo > 50)
                                            return false;
                                          break;                   
                             }
                }
            }//fin for
        }catch(StringIndexOutOfBoundsException e) {
            
        }
        return true;
    }
    
    public int digitoVerificadorCedula(java.lang.String cedula) {
        int snp = 0;//almacena la suma de los numero parese
        int sni = 0;//almacena la suma de los numeros impares
        int dv = 0; //almacena el digito verificador
        
        for(int i=0; i<9; i++) {
            int dig = 0; //gigito =0;
            
            try {
                dig = Integer.parseInt(cedula.substring(i,i+1));
            }catch(NumberFormatException e) {}
            switch (i) {
                case 0:
                case 2:
                case 4:
                case 6:
                case 8: if(dig * 2 > 9) 
                            sni = sni+(dig*2)-9;
                        else
                            sni= sni+(dig*2);
                        break;
                case 1:
                case 3:
                case 5:
                case 7: snp = snp+dig;
            }
        }
        dv = sni+snp;
        if(dv % 10>0) {
            dv= 10-(dv % 10);
            System.out.println("Clave:" +dv);//imprime el digito verificador
        }else{
            dv= 0;
            System.out.println("Clave:" +dv);//imprime el digito verificador
        }
        return dv;
        
    }    
    
     //valida solo textos sin numeros
    public boolean validaTexto(String strText, int iLong) {
        for (int i=0; i<strText.length(); i++) {
            if ((strText.charAt(i) >= '0' && strText.charAt(i) <= '9')|| esCaracterEspecial(strText.charAt(i))) {
                System.out.println("ERROR: El texto no debe contener datos numericos No caracteres especiales ");
                //  strMsg = "ERROR: El texto no debe contener datos numericos\no caracteres especiales";
               // JOptionPane.showMessageDialog(new JFrame(), strMsg);                
                return false;
            }               
        }
        if (strText.length()>=iLong) {
            System.out.println(" ERROR: El texto excede la longitud determinada\nDebe reducir la longitud del campo");
           // strMsg = "ERROR: El texto excede la longitud determinada\nDebe reducir la longitud del campo" ;
           // JOptionPane.showMessageDialog(new JFrame(), strMsg);
            return false;            
        }
        if (strText.length()==0) {
           System.out.println(" ERROR: El texto no puede ser NULL");
            // strMsg = "ERROR: El texto no puede ser NULL" ;
           // JOptionPane.showMessageDialog(new JFrame(), strMsg);
            return false;
        }
        return true;
    }

    //valida texto de email
    private boolean validaEmail(String strText) {
        int iSw = 0;
        for (int i=0; i<strText.length(); i++) {
            if (strText.charAt(i) == ' ') {
             //   strMsg = "ERROR: El texto no debe contener espacios en blanco";
               // JOptionPane.showMessageDialog(new JFrame(), strMsg);                
                return false;
            }
            if (strText.charAt(i) == '@')
                iSw ++;
        }
        if (iSw != 1) {
          //  strMsg = "ERROR: La direccion email no es correcta" ;
          //  JOptionPane.showMessageDialog(new JFrame(), strMsg);
            return false;            
        }
        return true;
    }

    //Valida solo numeros
    public boolean validaNumero(String strText,int longitud){// boolean bPlanCtas) {
        int iSw=0;        
        for (int i=0; i<strText.length(); i++) {
            if (strText.charAt(i)=='.')
                iSw++;
            if (!((strText.charAt(i) >= '0' && strText.charAt(i) <= '9') || strText.charAt(i) == '.')) {
               System.out.println( "ERROR: El numero no debe contener caracteres alfabeticos");
               // JOptionPane.showMessageDialog(new JFrame(), strMsg);                
                return false;
            }
        }
       /*
        if (!bPlanCtas){
            if (!(iSw == 0 || iSw == 1)){
               System.out.println("ERROR: El numero no es valido");
               // JOptionPane.showMessageDialog(new JFrame(), strMsg);
                return false;
            }
        }*/
        if (strText.length()==0) {
          System.out.print("ERROR: El numero no puede ser NULL");
         //JOptionPane.showMessageDialog(new JFrame(), strMsg);
            return false;
        }
        if (strText.length()>longitud) {
          System.out.print("ERROR: El campo exede la longitud");
         //JOptionPane.showMessageDialog(new JFrame(), strMsg);
            return false;
        }
        return true;
    }    
    //valida solo textos con numeros
    public boolean validaDir(String strText, int iLong) {
        for (int i=0; i<strText.length(); i++) {
            if (esCaracterEspecial(strText.charAt(i))) {
              //  strMsg = "ERROR: El texto no puede contener datos con\n caracteres especiales";
             //   JOptionPane.showMessageDialog(new JFrame(), strMsg);                
                return false;
            }               
        }
        if (strText.length()>=iLong) {
           // strMsg = "ERROR: El texto excede la longitud determinada\nDebe reducir la longitud del campo" ;
            //JOptionPane.showMessageDialog(new JFrame(), strMsg);
            return false;            
        }
        if (strText.length()==0) {
         //   strMsg = "ERROR: El texto no puede ser NULL" ;
        //    JOptionPane.showMessageDialog(new JFrame(), strMsg);
            return false;
        }
        return true;
    }
    
    private boolean esCaracterEspecial(char s) {
        switch (s) {
            case '+':
            case '-':
            case '/':
            case '*':
            case '<':
            case '>':
            case '=':
            case ';':
            case ':':
            case '(':
            case ')':
            case '[':
            case ']':
            case '{':
            case '}':
            case 'º':
            case '@':
            case '#':
            case '~':
            case '½':
            case '¬':
            case ',':		
            case '\'':
            case '\"':
            case 'ç':
			 return true;
        }
        return false;
    }
    
    /*Método para validar la direccion de correo electronico*/
    public boolean ValidarEmail(String mail){
      //toma la variable arroba el indice en que se encuentra el @
      int arroba = mail.indexOf("@");
      //retorna false si antes del arroba hay dos caractes
      if(arroba<2){
          return false;
      }
      //retorna false si despues de la arroba hay - de 5 caracteres
      if(mail.length()- arroba < 5){
          return false;
      }
      //retorna false si despues del arroba no hay el punto 
      if(mail.indexOf(".",arroba)<0){
          return false;
      }
      return true;   
    }
    /*retorna la fecha
    public void retornafecha(){
       Date fecha = new Date();
       String dia = String.valueOf(fecha.getDay());
       String mes = String.valueOf(fecha.getMonth()+1);
       String anio =String.valueOf(fecha.getYear());
       /*
       String xxx = String.valueOf(fecha.DATE);
       String dia = String.valueOf(fecha.DAY_OF_WEEK);
       String mes = String.valueOf(fecha.MONTH+1);
       String anio = String.valueOf(fecha.YEAR);
       System.out.println(xxx); 
      // System.out.println("fecha actual : "+ dia+"/"+mes+"/"+anio);
    }
    */
    /* Metodo para validar la cedula
     * @param strCedula =  numero de cedula 
     * */
    public boolean ValidarNumeroCedula(String strCedula)
    {
        
        int intSnP =0;//almacena la suma de los numeros pares
        int intSnI=0; //almacena la suma de los numeros impares
        int intDigImp =0; //almacena los digitos impares pos 0,2,4,6,8
        int intSumParImp = 0;//almacena la suma total de la suma de pares y suma impares
        String strSumParImp; //convierte de int a string el valor del total suma impar + par 
        String strDigSpSi1; // alamacena el indice 1 de la suma de pares e impares
        String strDigSpSi2;//almacena el segundo indice de la suma de pares e impares
        int iDigVerificador; // almacena el digito verificador de la cadena strCedula posicion 9
        int iValorDigito; //almcena el digito calculado de la cadena strCedula
        int intAux,intDecenaSup;
        String strDecena;
        boolean validaCedula = true;//retorna true si la cedula es correcta caso contro retun false
        int valor=0;
        //calcula la suma de las posiciones pares 1,3,5,7 
        for(int j = 1; j < 8;j+=2)
        {
            //valor = Integer.parseInt(String.valueOf(strCedula.substring(j,j+1)));
            intSnP = intSnP + Integer.parseInt(String.valueOf(strCedula.substring(j,j+1)));
        }

        //calcula suma de las posiciones impares 0,2,4,6,8
        for(int i = 0; i <= 9 ;i+=2)
        {
                //almacenaen el intDigImp= carga las posiciones 
                intDigImp =Integer.parseInt(String.valueOf(strCedula.substring(i,i+1)));
                if((intDigImp*2)>8) //valida el digito impar
                        intDigImp = (intDigImp*2)-9;
                else
                        intDigImp = intDigImp * 2;
                intSnI =  intSnI + intDigImp;//toma la suma total de los digitos impares
        }
        //suma de pares e impares
        intSumParImp = intSnP + intSnI; 
        //convierte a string la variable intSumParImp
        strSumParImp = String.valueOf(intSumParImp);

        strDigSpSi1 = strSumParImp.substring(0,1); //toma el indice 0
        strDigSpSi2 = strSumParImp.substring(1); // toma el indice 1

        // toma el utimo digito de la cadena strCedula
        iDigVerificador = Integer.parseInt(String.valueOf(strCedula.substring(9,10).trim()));
        if (Integer.parseInt(String.valueOf(strDigSpSi2)) == 0) //si el utimo digito es igual al digito calculado
        {
                iValorDigito = 0;
        }
        else
        {
                intAux=Integer.parseInt(String.valueOf(strDigSpSi1));
                intAux = intAux+1; //para aumenta a la siguiente decena
                strDecena = String.valueOf(intAux)+"0";
                intDecenaSup = Integer.parseInt(String.valueOf(strDecena));
                iValorDigito = intDecenaSup - intSumParImp;//
        }

        if(iDigVerificador == iValorDigito)
            validaCedula = true;//cedula correcta   
        else
            validaCedula = false;//cedula incorrecta
        return validaCedula;
    }
    
    
    /** 
     * Encripta un String con el algoritmo MD5. 
     * @return String 
     * @throws Exception 
     */ 
    private static String hash(String clear) throws Exception { 
        MessageDigest md = MessageDigest.getInstance("MD5"); 
        byte[] b = md.digest(clear.getBytes()); 

        int size = b.length; 
        StringBuffer h = new StringBuffer(size); 
        for (int i = 0; i < size; i++) { 
            int u = b[i]&255; // unsigned conversion 
            if (u<16) { 
                h.append("0"+Integer.toHexString(u)); 
                //h.append("0"+Integer.toOctalString(u)); 
            } else { 
                h.append(Integer.toHexString(u)); 
               // h.append(Integer.toOctalString(u)); 
            } 
        } 
        return h.toString(); 
    } 

    /** 
     * Encripta un String con el algoritmo MD5. 
     * @return String 
     * @throws Exception 
     */ 

    public boolean ValidarFecha(String strFecha, String strFormato)
    {
        boolean validar = true;
        SimpleDateFormat sdf = new SimpleDateFormat(strFormato);
        try{
            Date d = sdf.parse(strFecha);
        }catch(Exception e){
            return false;
        }
        return true;
        
        /*
        String sAnio=""; 
        String sMes=""; 
        String sDia="";
        
        int iAnio =0;int iMes =0; int iDia=0;
        int iSlash1 = strFecha.indexOf("/"); //almacena el primer slash
        int iSlash2 = strFecha.lastIndexOf("/"); //almacena el segundo slash
        int iCont = 0;
        for(int i=0; i<= strFecha.length()-1;i++)
        {
            int iCodAsc = strFecha.charAt(i);
            if(iCodAsc == 47)//47= codigo ascii del slash (/)
                iCont = iCont + 1;
        }
        if(iCont!= 2)// si el campo fecha no tiene dos slash
            return false;
        if(iSlash1== -1) return false;
        if(iSlash2== -1)return false;
            
            
        if(!strFormato.equals("yyyy/mm/dd") && !strFormato.equals("dd/mm/yyyy"))
            return false;
        else if(strFormato.equals("yyyy/mm/dd"))
        {
            sAnio = strFecha.substring(0,iSlash1);
            sMes = strFecha.substring(iSlash1+1,iSlash2);
            sDia = strFecha.substring(iSlash2+1,strFecha.length());
        }else if(strFormato.equals("dd/mm/yyyy")){
            sAnio = strFecha.substring(iSlash2+1,strFecha.length());
            sMes = strFecha.substring(iSlash1+1,iSlash2);
            sDia = strFecha.substring(0,iSlash1);
        }
        //verfico que el año tenga 4 dígitos
        if(sAnio.length()!=4)
            return false;
        //verifica que el mes tenga 1 0 2 dígitos
        if(sMes.length()<1 ||sMes.length()>2)
            return false;
        //verifica que el día tenga 1 o dígitos
        if(sDia.length()<1 ||sDia.length()>2)
            return false;
        
        iAnio = Integer.parseInt(String.valueOf(sAnio));
        iMes = Integer.parseInt(String.valueOf(sMes));
        iDia = Integer.parseInt(String.valueOf(sDia));
        //verifica que los meses 3,6, 9 y 11 no esten fuera del rango de dias 0 -30
        if (((iMes == 4) || (iMes == 6) || (iMes == 9) || (iMes == 11)) && (iDia < 0 || iDia > 30))
            return false;
        else if(((iMes == 1) || (iMes == 3) || (iMes == 5) || (iMes == 7) || (iMes == 8) || (iMes == 10) || (iMes == 12)) && (iDia < 0 || iDia > 31))
               return false;
        else if (iMes == 2){// si el mes es febrero validamos si es año viciesto o no
            // verifica si el año es viciesto febrero toma 29 dias
            if ((iAnio % 4 == 0) && (iAnio % 100 != 0) || (iAnio % 100 == 0)){
                if (iMes == 2 && (iDia < 0 || iDia > 29))
                    return false;
                // si el año no es viciesto febreo toma 28 días
            } else{
                if (iMes == 2 && (iDia <0 || iDia > 28))
                    return false;                
            }
        }
      
        return true;*/
    }
        public int validaRucCedula(String ruc){
        if(ruc.length()==13 || ruc.length()==10){
            try{ //comprobamos si ruc es numérico
                long l = Long.parseLong(ruc);
            }catch(Exception e){
                return -1;
            }
            try{
                int provincia = Integer.parseInt(ruc.substring(0,2));
                if(provincia <0 || provincia >24) // recomendable utilizar archivo de propiedades
                    return -1;
                int tercerDigito = Integer.parseInt(ruc.substring(2,3));
                if(tercerDigito == 9){ // es RUC para Sociedades Privadas y Extranjeros sin cédula
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
                }else if(tercerDigito == 6){ // del RUC para Sociedades Públicas
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
                }else if(tercerDigito < 6){ // RUC para Personas Naturales o cédula
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
        return -1; // ruc o cédula incorrecta
    }  
    
    
//    public boolean ValidaFechaDesdeHasta(String strFecDes, String strFecHas,String strFormato)
//    {
//        String strFormatoAux="";
//        if(!strFormato.equals("yyyy-mm-dd") && !strFormato.equals("dd-mm-yyyy"))
//            return false;
//        else 
//            strFormatoAux = strFormato;
//        if(!this.ValidarFecha(strFecDes,strFormatoAux) && !this.ValidarFecha(strFecDes,strFormatoAux))
//            return false;
//        Timestamp dtFecDes = null;
//        Timestamp dtFecHas = null;
//        java.text.SimpleDateFormat  dateFormat = new java.text.SimpleDateFormat("dd/MM/yyyy");//("dd/MM/yyyy");
//        java.util.Calendar fecha = java.util.Calendar.getInstance();
//        
//        String fechaActual = dateFormat.format(fecha.getTime());
//        fecha.toString().
//        String strFecAuxD = strFecDes +" 00:00:00.000000000";
//        String strFecAuxH = strFecHas +" 23:59:59.000000000";
//        dtFecDes.valueOf(strFecDes + " 00:00:00.000000000");
//        dtFecHas.toString().format().valueOf(strFecHas + " 23:59:59.000000000");
//        if(dtFecDes== null && dtFecHas == null)
//            return false;
//        if(dtFecDes.after(dtFecHas))
//            return false;
//        return true;    
//    }
 private static Cipher enryptCipher;
    private static Cipher decriptCipher;

    // 8-byte Salt
    private static final byte[] SALT = {
            (byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
            (byte) 0x56, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };

    // Iteration count
    private static final int iterationCount = 19;

    // passphrase initialization
    private static final String PASSPHRASE = "ICEsoft Rocks!";
    private static ValidarCampos encrypter = new ValidarCampos(PASSPHRASE);



    /**
     * Encrypts the given string using symmetric encryption algorithm.
     *
     * @see #decrypt(String)
     *
     * @param plainText String to encrypt.
     * @return encrypted representation of attribute value.
     */
    public static String encriptar(String plainText){
        if (encrypter == null){
            encrypter = new ValidarCampos(PASSPHRASE);
        }
        return encrypter.encrypter(plainText);
    }

    /**
     * Encrypts the given string using symmetric encryption algorithm.
     *
     * @see #decrypt(String)
     *
     * @param encryptedText String to encrypt.
     * @return decrypted representation of attribute value.
     */
    public static String desencriptar(String encryptedText){
        if (encrypter == null){
            encrypter = new ValidarCampos(PASSPHRASE);
        }
        return encrypter.decrypter(encryptedText);
    }

    /**
     *  Defautl constructor for creating a DES encrypt/decryption mechanism.
     * @param passPhrase
     */
    private ValidarCampos(String passPhrase) {
        try {
            // Create the key
            KeySpec keySpec = new PBEKeySpec(passPhrase.toCharArray(), SALT, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance(
                    "PBEWithMD5AndDES").generateSecret(keySpec);

            enryptCipher = Cipher.getInstance(key.getAlgorithm());
            decriptCipher = Cipher.getInstance(key.getAlgorithm());

            // Prepare the parameter to the ciphers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, iterationCount);

            // Create the ciphers
            enryptCipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            decriptCipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        } catch (java.security.InvalidAlgorithmParameterException e) {
            encrypter = null;
        } catch (java.security.spec.InvalidKeySpecException e) {
            encrypter = null;
        } catch (javax.crypto.NoSuchPaddingException e) {
            encrypter = null;
        } catch (java.security.NoSuchAlgorithmException e) {
            encrypter = null;
        } catch (java.security.InvalidKeyException e) {
            encrypter = null;
        }
    }

    /**
     * Utility method for encrypting plain text using the DES algorithm
     * and a specified passPhrase.
     * @param plaintext String to encrypt
     * @return encrypted String
     */
    private String encrypter(String plaintext) {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = plaintext.getBytes("UTF8");

            // Encrypt
            byte[] enc = enryptCipher.doFinal(utf8);

            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);

        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (java.io.IOException e) {
        }
        return null;
    }

    /**
     * Utility method for decrypting text that was encrypted with the DES algorithm.
     * @param plaintext String to encrypt
     * @return encrypted String
     */
    private String decrypter(String plaintext) {
        try {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(plaintext);

            // Decrypt
            byte[] utf8 = decriptCipher.doFinal(dec);

            // Decode using utf-8
            return new String(utf8, "UTF8");

        } catch (javax.crypto.BadPaddingException e) {
        } catch (IllegalBlockSizeException e) {
        } catch (UnsupportedEncodingException e) {
        } catch (java.io.IOException e) {
        }
        return null;
    }

    public static void main(String [] args){
     
        ValidarCampos obj = new ValidarCampos();
        System.out.println("VALIDAR CEDULA: "+obj.ValidarNumeroCedula("1309700549"));
        System.out.println("VALIDAR CEDULA 2: "+obj.validaRucCedula("1309700548001"));
        System.out.println("VALIDAR CEDULA 3: "+obj.validaCedula("1309700549"));
       /*******************************
       *Prueba de fechas
       ***********************************/
//       if(obj.ValidarFecha("2006/8/211","yyyy/mm/dd"))
//           System.out.println("Fecha correcta");
//       else
//           System.out.println("Fecha errada");
       
   }
}
