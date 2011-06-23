/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

import java.math.BigDecimal;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Tab;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class guardarCobros {
    Decimalbox valor = new Decimalbox();
    Decimalbox totalCobros = new Decimalbox();
    Decimalbox efectivo = new Decimalbox();
    Decimalbox cheque = new Decimalbox();
    Decimalbox deposito = new Decimalbox();
    Decimalbox tarjeta = new Decimalbox();
 
    public void sumarcobros(String tipo){
       BigDecimal  aPagar = valor.getValue();
       BigDecimal totalaCobrar = efectivo.getValue().add(cheque.getValue()).add(deposito.getValue());
       if(totalaCobrar.compareTo(aPagar)==1){ //>
                if(tipo.equals("efe")){
                    BigDecimal cobros =  totalCobros.getValue() ;
                    efectivo.setValue((aPagar.subtract(cobros).compareTo(new BigDecimal(0)) == -1?new BigDecimal(0):(aPagar.subtract(cobros))));
                }else if(tipo.equals("dep")){
                    BigDecimal cobros = (totalCobros.getValue());
                     //deposito.setValue(""+(redondear((aPagar -cobros),2)<0?0:redondear((aPagar -cobros),2)));
                     deposito.setValue((aPagar.subtract(cobros).compareTo(new BigDecimal(0)) == -1?new BigDecimal(0):(aPagar.subtract(cobros))));
                }else if(tipo.equals("che")){
                    BigDecimal cobros = (totalCobros.getValue());
                    //cheque.setValue(""+(redondear((aPagar -cobros),2)<0?0:redondear((aPagar -cobros),2)));
                    cheque.setValue((aPagar.subtract(cobros).compareTo(new BigDecimal(0)) == -1?new BigDecimal(0):(aPagar.subtract(cobros))));
                }else if(tipo.equals("tar")){
                    BigDecimal cobros = (totalCobros.getValue());
                    //cheque.setValue(""+(redondear((aPagar -cobros),2)<0?0:redondear((aPagar -cobros),2)));
                    tarjeta.setValue((aPagar.subtract(cobros).compareTo(new BigDecimal(0)) == -1?new BigDecimal(0):(aPagar.subtract(cobros))));
                 
                }
    }
     totalaCobrar = efectivo.getValue().add(cheque.getValue()).add(deposito.getValue());
    totalCobros.setValue(totalaCobrar);
}
    
}




















