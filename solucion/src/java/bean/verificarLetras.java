/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Letras;
import jcinform.persistencia.Periodo;
import jcinform.procesos.Administrador;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class verificarLetras {

    public void verificarLetras() {

    }
    public String verifica(String apellido,Administrador adm,Periodo per) {
        List<Letras> letr = adm.query("Select o from Letras as o "
                + " where o.periodo.codigoper = '"+per.getCodigoper()+"' ");
        Letras letraEncontrada = null;
        String primeraLetra ="";
        try {
                primeraLetra = apellido.replace(";","").replace(",", "").substring(0, 2).toUpperCase();
        } catch (Exception e) {
                primeraLetra = "XX";
        }
 
        Date fechaActual = new Date();
        Date actual = (fechaActual);

        if (primeraLetra.equals("CH")) {
            letraEncontrada = fechas(letr, primeraLetra);
            if (letraEncontrada != null) {
                Date inicial = (letraEncontrada.getDesde());
                Date finale = (letraEncontrada.getHasta());
                System.out.println("" + letraEncontrada.getLetras());
                if (actual.compareTo(finale) <= 0 && actual.compareTo(inicial) >= 0) {
                    System.out.println("HABILITADO");
                    return "OK..! DESDE: "+letraEncontrada.getDesde().toLocaleString()+" HASTA: "+letraEncontrada.getHasta().toLocaleString();
                } else {
                    System.out.println("DESHABILITADO");
                    return "ERROR..! APELLIDOS CON [ "+letraEncontrada.getLetras().replace(";"," o ")+" ] ESTÁ(N) \n  DESDE: "+letraEncontrada.getDesde().toLocaleString()+" \n  HASTA: "+letraEncontrada.getHasta().toLocaleString();
                }


            } else {
                return "ERROR...! \nLETRA INCIAL [ "+primeraLetra+" ] DE APELLIDO NO ENCONTRADA";
            }
        } else {
            primeraLetra = apellido.substring(0, 1).toUpperCase();
            letraEncontrada = fechas(letr, primeraLetra);
            if (letraEncontrada != null) {
                Date inicial = letraEncontrada.getDesde();
                Date finale = letraEncontrada.getHasta();
                if (actual.compareTo(finale) <= 0 && actual.compareTo(inicial) >= 0) {
                    System.out.println("HABILITADO");
                     return "OK..! DESDE: "+letraEncontrada.getDesde().toLocaleString()+" HASTA: "+letraEncontrada.getHasta().toLocaleString();
                } else {
                    System.out.println("DESHABILITADO");
                    return "ERROR..! APELLIDOS CON [ "+letraEncontrada.getLetras().replace(";"," o ")+" ] ESTÁ(N) \n  DESDE: "+letraEncontrada.getDesde().toLocaleString()+" \n  HASTA: "+letraEncontrada.getHasta().toLocaleString();
                }
            } else {
                return "ERROR...! \nLETRA INCIAL [ "+primeraLetra+" ] DE APELLIDO NO ENCONTRADA";
            }


        }
    }

    public Letras fechas(List<Letras> letr, String letraComprobar) {

        for (Iterator<Letras> it = letr.iterator(); it.hasNext();) {
            Letras letras = it.next();
            if(letraComprobar.length()>1){
                if (letras.getLetras().contains(letraComprobar)) {
                    return letras;
                }
            }else{
                if (letras.getLetras().contains(letraComprobar) && !letras.getLetras().equals("CH")) {
                    return letras;
                }
            }
        }
        return null;


    }


}
