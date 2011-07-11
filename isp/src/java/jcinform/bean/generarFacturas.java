/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

 
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import jcinform.conexion.Administrador;
import jcinform.persistencia.Contratos;
import jcinform.persistencia.Cxcobrar;
import jcinform.persistencia.Detalle;
import jcinform.persistencia.Factura;
import jcinform.persistencia.Sucursal;
import org.zkoss.zul.Button;

/**
 *
 * @author Familia Jadan Cahueñ
 */
public class generarFacturas {

    public void generarFacturas() {
    }

    public String empezarGenerar(Date fecha, Integer numero, Sucursal suc) {
        //seleccionar todos los que no tenga deuda en éste més o periodo
        Button b = new Button();
       
        Administrador adm = new Administrador();
        Date fecha2 = fecha;
        fecha2.setDate(1);
        String mesActualIni = convertiraString(fecha2);
        String mesActualFin = convertiraString(ultimoDia(fecha));
        String nn = suc.getSerie1()+""+suc.getSerie2()+"FAC"+llenarCeros(""+numero);
        List existe = adm.query("Select o from Factura as o where o.numero = '"+ nn +"'");
                if(existe.size()>0){
                        return " "+ "EL NÚMERO DE FACTURA INICIAL YA EXISTE EN SUCURSAL: "+suc.getDescripcion();
                }
        
        List facturasHechas = adm.queryNativo("Select o.* from Contratos  as o "
                + "where o.clientes not in (Select f.clientes from Factura as f "
                + "where f.fecha between '" + mesActualIni + "' and '" + mesActualFin + "') "
                + "and  o.estado in ('Activo','Terminado')  and o.sucursal = '"+suc.getCodigo()+"' order by o.codigo ", Contratos.class);
        try {
            
        
                for (Iterator it = facturasHechas.iterator(); it.hasNext();) {
                    Contratos object = (Contratos) it.next();
                    Factura fac = new Factura(adm.getNuevaClave("Factura", "codigo"));
                    fac.setNumero(suc.getSerie1()+""+suc.getSerie2()+"FAC"+llenarCeros(""+numero)+"");
                    fac.setEfectivo(new BigDecimal(object.getPlan().getValor()));
                    fac.setEstado(true);
                    fac.setClientes(object.getClientes());
                    fac.setFecha(fecha);
                    fac.setSucursal(suc);
                    fac.setTotal(new BigDecimal(object.getPlan().getValor()));
                    adm.guardar(fac);
                    Detalle det = new Detalle(adm.getNuevaClave("Detalle", "codigo"));
                    det.setTotal(new BigDecimal(object.getPlan().getValor()));
                    det.setPlan(object.getPlan());
                    det.setCantidad(1);
                    det.setMes(fecha.getMonth()+1);
                    det.setAnio(fecha.getYear()+1900);
                    det.setDescripcion("generada");
                    det.setFactura(fac);
                    adm.guardar(det);
                    Cxcobrar cuenta = new Cxcobrar(adm.getNuevaClave("Cxcobrar", "codigo"));
                    cuenta.setDebe(fac.getTotal());
                    cuenta.setHaber(BigDecimal.ZERO);
                    cuenta.setDebito(BigDecimal.ZERO);
                    cuenta.setCheque(BigDecimal.ZERO);
                    cuenta.setEfectivo(BigDecimal.ZERO);
                    cuenta.setFactura(fac);
                    cuenta.setFecha(fecha);
                    cuenta.setNotarjeta("");
                    cuenta.setNocheque("");
                    cuenta.setTotal(fac.getTotal());
                    adm.guardar(cuenta);
                            
                    
                    
                    numero++;
                }
        } catch (Exception e) {
            System.out.println("DUPLICADO: "+e.hashCode());
            return e.hashCode()+"";
        }
        //seleccionar todos los clientes que tengan contrato activo o cortado (verificar si es )??

        //generar en facturas con el número y también en cxc.

        return "OK";
    }

    
     public String empezarGenerarIndividual(Date fecha, Integer numero, Sucursal suc) {
        //seleccionar todos los que no tenga deuda en éste més o periodo
        Button b = new Button();
       
        Administrador adm = new Administrador();
        Date fecha2 = fecha;
        fecha2.setDate(1);
        String mesActualIni = convertiraString(fecha2);
        String mesActualFin = convertiraString(ultimoDia(fecha));
        String nn = suc.getSerie1()+""+suc.getSerie2()+"FAC"+llenarCeros(""+numero);
        List existe = adm.query("Select o from Factura as o where o.numero = '"+ nn +"'");
                if(existe.size()>0){
                        return " "+ "EL NÚMERO DE FACTURA INICIAL YA EXISTE EN SUCURSAL: "+suc.getDescripcion();
                }
        
        List facturasHechas = adm.queryNativo("Select o.* from Contratos  as o "
                + "where o.clientes not in (Select f.clientes from Factura as f "
                + "where f.fecha between '" + mesActualIni + "' and '" + mesActualFin + "') "
                + "and  o.estado in ('Activo','Terminado')  and o.sucursal = '"+suc.getCodigo()+"' order by o.codigo ", Contratos.class);
        try {
            
        
                for (Iterator it = facturasHechas.iterator(); it.hasNext();) {
                    Contratos object = (Contratos) it.next();
                    Factura fac = new Factura(adm.getNuevaClave("Factura", "codigo"));
                    fac.setNumero(suc.getSerie1()+""+suc.getSerie2()+"FAC"+llenarCeros(""+numero)+"");
                    fac.setEfectivo(new BigDecimal(object.getPlan().getValor()));
                    fac.setEstado(true);
                    fac.setClientes(object.getClientes());
                    fac.setFecha(fecha);
                    fac.setSucursal(suc);
                    fac.setTotal(new BigDecimal(object.getPlan().getValor()));
                    adm.guardar(fac);
                    Detalle det = new Detalle(adm.getNuevaClave("Detalle", "codigo"));
                    det.setTotal(new BigDecimal(object.getPlan().getValor()));
                    det.setPlan(object.getPlan());
                    det.setCantidad(1);
                    det.setMes(fecha.getMonth()+1);
                    det.setAnio(fecha.getYear()+1900);
                    det.setDescripcion("generada");
                    det.setFactura(fac);
                    adm.guardar(det);
                    Cxcobrar cuenta = new Cxcobrar(adm.getNuevaClave("Cxcobrar", "codigo"));
                    cuenta.setDebe(fac.getTotal());
                    cuenta.setHaber(BigDecimal.ZERO);
                    cuenta.setDebito(BigDecimal.ZERO);
                    cuenta.setCheque(BigDecimal.ZERO);
                    cuenta.setEfectivo(BigDecimal.ZERO);
                    cuenta.setFactura(fac);
                    cuenta.setFecha(fecha);
                    cuenta.setNotarjeta("");
                    cuenta.setNocheque("");
                    cuenta.setTotal(fac.getTotal());
                    adm.guardar(cuenta);
                            
                    
                    
                    numero++;
                }
        } catch (Exception e) {
            System.out.println("DUPLICADO: "+e.hashCode());
            return e.hashCode()+"";
        }
        //seleccionar todos los clientes que tengan contrato activo o cortado (verificar si es )??

        //generar en facturas con el número y también en cxc.

        return "OK";
    }

    
    public static String convertiraString(Date fecha) {

        return (fecha.getYear() + 1900) + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();

    }
    
     String llenarCeros(String numero){
        
     while(numero.length()<7){
        numero = "0"+numero;
     }  
     return numero;
    
    }

    public static Date ultimoDia(Date fecha) {
        //Calendar calInicio = Calendar.getInstance();
        Calendar calFin = Calendar.getInstance();
        String anioFin = (fecha.getYear() + 1900) + "";
        String mesFin = fecha.getMonth() + "";
        calFin.set(Integer.parseInt(anioFin), Integer.parseInt(mesFin), 1);
        calFin.set(Integer.parseInt(anioFin), Integer.parseInt(mesFin), calFin.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date fechaFin = calFin.getTime();
        System.out.println("ULTIMO: " + fechaFin.toLocaleString());
        return fechaFin;

    }

    public static Date primerDia(Date fecha) {
        //Calendar calInicio = Calendar.getInstance();
        Calendar calFin = Calendar.getInstance();
        String anioFin = (fecha.getYear() + 1900) + "";
        String mesFin = fecha.getMonth() + "";
        calFin.set(Integer.parseInt(anioFin), Integer.parseInt(mesFin), 1);
        calFin.set(Integer.parseInt(anioFin), Integer.parseInt(mesFin), calFin.getActualMinimum(Calendar.DAY_OF_MONTH));
        Date fechaFin = calFin.getTime();
        System.out.println("PRIMER: " + fechaFin.toLocaleString());
        return fechaFin;

    }

    public static void main(String arg[]) {

        ultimoDia(new Date());
        primerDia(new Date());
        System.out.println("" + convertiraString(new Date()));
    }
}
