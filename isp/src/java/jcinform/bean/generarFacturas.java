/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.conexion.Administrador;
import jcinform.persistencia.Canton;
import jcinform.persistencia.Contratos;
import jcinform.persistencia.Cxcobrar;
import jcinform.persistencia.Detalle;
import jcinform.persistencia.Empleados;
import jcinform.persistencia.Factura;
import jcinform.persistencia.Procesos;
import jcinform.persistencia.Sector;
import jcinform.persistencia.Sucursal;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zul.Button;

/**
 *
 * @author Familia Jadan Cahueñ jcinform.bean.generarFacturas.convertiraString
 */
public class generarFacturas {

    Administrador adm;

    public generarFacturas() {
        adm = new Administrador();
    }

    public void generacionAutomatico(Sucursal suc) {
        if (adm == null) {
            adm = new Administrador();
        }
        Date fecha = adm.Date();
        String fechaSql = suc.getCodigo() + "" + (fecha.getYear() + 1900) + (fecha.getMonth() + 1) + "";
        List procesosList = adm.query("Select o from Procesos as o where o.fechastring = '" + fechaSql + "'  ");
        if (procesosList.size() > 0) {
            //Messagebox.show("Proceso ya realizado en éste mes", "Alerta", Messagebox.OK, Messagebox.INFORMATION);
            System.out.println("Proceso para éste mes ya realizado");
        } else {
            Procesos pro = new Procesos(fechaSql);
            pro.setFecha(fecha);
            pro.setFechaejecutado(fecha);
            pro.setEjecutado(true);
            //pro.setProblemas(fechaSql);
            adm.guardar(pro);
            empezarGenerar(fecha, 0, suc);
            System.out.println("SE EJECUTO PROCESO PARA FECHA" + fecha);
        }
    }

    public String empezarGenerar(Date fecha, Integer numero, Sucursal suc) {
        //seleccionar todos los que no tenga deuda en éste més o periodo
        Date fecha2 = fecha;
        fecha2.setDate(1);
        String mesActualIni = convertiraString(fecha2);
        String mesActualFin = convertiraString(ultimoDia(fecha));
        String nn = suc.getSerie1() + "" + suc.getSerie2() + "FAC" + llenarCeros("" + numero);
//        List existe = adm.query("Select o from Factura as o where o.numero = '"+ nn +"'");
//                if(existe.size()>0){
//                        return " "+ "EL NÚMERO DE FACTURA INICIAL YA EXISTE EN SUCURSAL: "+suc.getDescripcion();
//                }
        String query = "Select o.* from Contratos  as o "
                + "where o.codigo not in (Select f.contratos from Factura as f "
                + "where f.fecha between '" + mesActualIni + "' and '" + mesActualFin + "') "
                + "and  o.estado in ('Activo')  and o.sucursal = '" + suc.getCodigo() + "' order by o.codigo ";
        //+ "and  o.estado in ('Activo','Terminado')  and o.sucursal = '" + suc.getCodigo() + "' order by o.codigo ";
        System.out.println("" + query);
        List facturasHechas = adm.queryNativo(query, Contratos.class);
        try {


            for (Iterator it = facturasHechas.iterator(); it.hasNext();) {
                Contratos object = (Contratos) it.next();
                Factura fac = new Factura(adm.getNuevaClave("Factura", "codigo"));
                //fac.setNumero(suc.getSerie1()+""+suc.getSerie2()+"FAC"+llenarCeros(""+numero)+"");
                fac.setEstado(true);
                fac.setClientes(object.getClientes());
                fac.setFecha(fecha);
                fac.setSucursal(suc);
                fac.setContratos(object);
                if (object.getDescuento() == null) {
                    object.setDescuento(BigDecimal.ZERO);
                }
                BigDecimal valor = new BigDecimal(redondear(object.getPlan().getValor(), 2));
                if (object.getFormapago().equals(3)) {
                    valor = valor.add(suc.getEmpresa().getInstalacion());
                }
                fac.setSubtotal(valor.subtract(object.getDescuento()));
                fac.setDescuento(new BigDecimal(0));
                fac.setBaseiva(valor.subtract(object.getDescuento()));
                fac.setPorcentajeiva(suc.getEmpresa().getIva());
                fac.setValoriva(valor.subtract(object.getDescuento()).multiply(suc.getEmpresa().getIva().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)));
                fac.setTotal(fac.getValoriva().add(fac.getSubtotal()));
                adm.guardar(fac);
                Detalle det = new Detalle(adm.getNuevaClave("Detalle", "codigo"));
                det.setTotal(new BigDecimal(redondear(object.getPlan().getValor(), 2)).subtract(object.getDescuento()));
                det.setPlan(object.getPlan());
                det.setCantidad(1);
                det.setMes(fecha.getMonth() + 1);
                det.setAnio(fecha.getYear() + 1900);
                det.setDescripcion("generada");
                det.setFactura(fac);
                adm.guardar(det);
                Cxcobrar cuenta = new Cxcobrar(adm.getNuevaClave("Cxcobrar", "codigo"));
                cuenta.setDebe(fac.getTotal());
                cuenta.setHaber(BigDecimal.ZERO);
                cuenta.setDeposito(BigDecimal.ZERO);
                cuenta.setCheque(BigDecimal.ZERO);
                cuenta.setEfectivo(BigDecimal.ZERO);
                cuenta.setFactura(fac);
                cuenta.setFecha(fecha);
                cuenta.setNotarjeta("");
                cuenta.setNocheque("");
                cuenta.setDescuento(BigDecimal.ZERO);
                cuenta.setTotal(fac.getTotal());
                adm.guardar(cuenta);
//                numero++;
            }
        } catch (Exception e) {
            Logger.getLogger(generarFacturas.class.getName()).log(Level.SEVERE, null, e);
            System.out.println("DUPLICADO: " + e + " " + e.hashCode());
            return "" + e + " " + e.hashCode() + "";
        }
        //seleccionar todos los clientes que tengan contrato activo o cortado (verificar si es )??

        //generar en facturas con el número y también en cxc.
        Date fechas = adm.Date();
        String fechaSql = suc.getCodigo() + "" + (fechas.getYear() + 1900) + (fechas.getMonth() + 1);
        try {

            Procesos pro = new Procesos(fechaSql);
            pro.setFecha(fecha);
            pro.setFechaejecutado(fecha);
            pro.setEjecutado(true);
            adm.guardar(pro);
        } catch (Exception er) {
            Procesos pro = new Procesos(fechaSql);
            pro.setFecha(fecha);
            pro.setFechaejecutado(fecha);
            pro.setEjecutado(true);
            pro.setProblemas("Re-ejecutado");
            adm.actualizar(pro);
            System.out.println("PROCESO YA EJECUTADO");
        }



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
        List facturasHechas = adm.queryNativo("Select o.* from Contratos  as o "
                + "where o.codigo not in (Select f.contratos from Factura as f "
                + "where f.fecha between '" + mesActualIni + "' and '" + mesActualFin + "') "
                + "and  o.estado in ('Activo')  and o.sucursal = '" + suc.getCodigo() + "' order by o.codigo ", Contratos.class);
        try {
            for (Iterator it = facturasHechas.iterator(); it.hasNext();) {
                Contratos object = (Contratos) it.next();
                Factura fac = new Factura(adm.getNuevaClave("Factura", "codigo"));
                //fac.setNumero(suc.getSerie1()+""+suc.getSerie2()+"FAC"+llenarCeros(""+numero)+"");
                fac.setNumero(null);
                fac.setEstado(true);
                fac.setClientes(object.getClientes());
                fac.setFecha(fecha);
                fac.setContratos(object);
                fac.setSucursal(suc);
                BigDecimal valor = new BigDecimal(redondear(object.getPlan().getValor(), 2));
                if (object.getFormapago().equals(3)) {
                    valor = valor.add(suc.getEmpresa().getInstalacion());
                }
                fac.setSubtotal(valor.subtract(object.getDescuento()));
                fac.setDescuento(new BigDecimal(0));
                fac.setBaseiva(valor.subtract(object.getDescuento()));
                fac.setPorcentajeiva(suc.getEmpresa().getIva());
                fac.setValoriva(valor.subtract(object.getDescuento()).multiply(suc.getEmpresa().getIva().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)));
                fac.setTotal(fac.getValoriva().add(fac.getSubtotal()));
                adm.guardar(fac);
                Detalle det = new Detalle(adm.getNuevaClave("Detalle", "codigo"));
                det.setTotal(valor.subtract(object.getDescuento()));
                det.setPlan(object.getPlan());
                det.setCantidad(1);
                det.setMes(fecha.getMonth() + 1);
                det.setAnio(fecha.getYear() + 1900);
                det.setDescripcion("generada");
                det.setFactura(fac);
                adm.guardar(det);
                Cxcobrar cuenta = new Cxcobrar(adm.getNuevaClave("Cxcobrar", "codigo"));
                cuenta.setDebe(fac.getTotal());
                cuenta.setDescuento(BigDecimal.ZERO);
                cuenta.setHaber(BigDecimal.ZERO);
                cuenta.setDeposito(BigDecimal.ZERO);
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
            System.out.println("DUPLICADO: " + e.hashCode());
            return e.hashCode() + "";
        }
        //seleccionar todos los clientes que tengan contrato activo o cortado (verificar si es )??

        //generar en facturas con el número y también en cxc.

        return "OK";
    }

    public String anadirCobros(Sucursal suc, Contratos con) { //ESTO ES PARA AÑADIR COBROS PENDIENTES individual
        //seleccionar todos los que no tenga deuda en éste més o periodo
        Administrador adm = new Administrador();
        Date fecha2 = con.getFecha();
        fecha2.setDate(1);
        con = (Contratos) adm.buscarClave(con.getCodigo(), Contratos.class);

        try {

            Contratos object = con;
            Factura fac = new Factura(adm.getNuevaClave("Factura", "codigo"));
            //fac.setNumero(suc.getSerie1()+""+suc.getSerie2()+"FAC"+llenarCeros(""+numero)+"");
            fac.setNumero(null);
            fac.setEstado(true);
            fac.setContratos(con);
            fac.setClientes(object.getClientes());
            fac.setFecha(fecha2);
            fac.setSucursal(suc);
            if (object.getDescuento() == null) {
                object.setDescuento(BigDecimal.ZERO);
            }

            BigDecimal valor = new BigDecimal(redondear(object.getPlan().getValor(), 2));
            if (object.getFormapago().equals(3)) {
                valor = valor.add(suc.getEmpresa().getInstalacion());
            }
            fac.setDescuento(BigDecimal.ZERO);
            fac.setSubtotal(valor.subtract(object.getDescuento()));
            fac.setDescuento(new BigDecimal(0));
            fac.setBaseiva(valor.subtract(object.getDescuento()));
            fac.setPorcentajeiva(suc.getEmpresa().getIva());
            fac.setValoriva((valor).subtract(object.getDescuento()).multiply(suc.getEmpresa().getIva().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)));
            fac.setTotal(fac.getValoriva().add(fac.getSubtotal()));
            adm.guardar(fac);
            Detalle det = new Detalle(adm.getNuevaClave("Detalle", "codigo"));
            det.setTotal(valor.subtract(object.getDescuento()));
            det.setPlan(object.getPlan());
            det.setCantidad(1);
            det.setMes(fecha2.getMonth() + 1);
            det.setAnio(fecha2.getYear() + 1900);
            det.setDescripcion("generada");
            det.setFactura(fac);
            adm.guardar(det);
            Cxcobrar cuenta = new Cxcobrar(adm.getNuevaClave("Cxcobrar", "codigo"));
            cuenta.setDebe(fac.getTotal());
            cuenta.setHaber(BigDecimal.ZERO);
            cuenta.setDeposito(BigDecimal.ZERO);
            cuenta.setCheque(BigDecimal.ZERO);
            cuenta.setEfectivo(BigDecimal.ZERO);
            cuenta.setDescuento(BigDecimal.ZERO);
            cuenta.setTransferencia(BigDecimal.ZERO);
            cuenta.setFactura(fac);
            cuenta.setFecha(fecha2);
            cuenta.setNotarjeta("");
            cuenta.setNocheque("");
            cuenta.setTotal(fac.getTotal());
            adm.guardar(cuenta);

        } catch (Exception e) {
            System.out.println("" + e);
            System.out.println("DUPLICADO: " + e.hashCode());
            Logger.getLogger(generarFacturas.class.getName()).log(Level.SEVERE, null, e);
            return e.hashCode() + "";
        }
        //seleccionar todos los clientes que tengan contrato activo o cortado (verificar si es )??

        //generar en facturas con el número y también en cxc.

        return "OK";
    }

    //ESTO ES PARA AÑADIR EL COBRO CUANDO GUARDA EL CONTRATO 
    public String generarCobros(Sucursal suc, Contratos con, Date fechaInstalacion) {
        //seleccionar todos los que no tenga deuda en éste més o periodo
        int dia = fechaInstalacion.getDate();
        Date fec = fechaInstalacion;
        fec.setDate(1);
        Administrador adm = new Administrador();
        String mesActualIni = convertiraString(fec);
        String mesActualFin = convertiraString(ultimoDia(fec));
        List facturaExistente = adm.query("Select f from Factura as f "
                + "where f.contratos.codigo = '" + con.getCodigo() + "' "
                + "and f.fecha between '" + mesActualIni + "' and '" + mesActualFin + "' "
                + "and f.clientes.codigo = '" + con.getClientes().getCodigo() + "' ");
        if (facturaExistente.size() > 0) {
            try {
                int valor0 = Messagebox.show("Ya se ha añadido una Deuda para éste mes, desea continuar añadiendolo?", "JCINFORM-Seguridad", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
                if (valor0 == 16) {
                } else {
                    return "";
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(generarFacturas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {

            Contratos object = con;
            Factura fac = new Factura(adm.getNuevaClave("Factura", "codigo"));
            fac.setNumero(null);
            fac.setEstado(true);
            fac.setContratos(con);
            fac.setClientes(object.getClientes());
            fechaInstalacion.setDate(dia);
            fac.setFecha(fechaInstalacion);
            fac.setSucursal(suc);
            fac.setDescuento(BigDecimal.ZERO);
            BigDecimal valor = new BigDecimal(redondear(con.getPlan().getValor(), 2)).subtract(object.getDescuento());
//            if(object.getFormapago().equals(3)){
//                valor = valor.add(suc.getEmpresa().getInstalacion());
//            }
            if (fechaInstalacion.getDate() > 1) {
                int noDias = object.getPlan().getDias() - fechaInstalacion.getDate();
                if (noDias > 0) {
                    if ((fechaInstalacion.getMonth() == adm.Date().getMonth())) {
                        valor = new BigDecimal(noDias).multiply(valor).divide(new BigDecimal(object.getPlan().getDias()), 2, RoundingMode.HALF_UP);
                    } else {
                        valor = new BigDecimal(con.getPlan().getValor()).subtract(object.getDescuento());
                    }
                } else {
                    return "";
                }

            } else {
                valor = new BigDecimal(redondear(con.getPlan().getValor(), 2)).subtract(object.getDescuento());
            }
            fac.setSubtotal(valor);
            fac.setDescuento(new BigDecimal(0));
            fac.setBaseiva(fac.getSubtotal());
            fac.setPorcentajeiva(suc.getEmpresa().getIva());
            fac.setValoriva((valor).multiply(suc.getEmpresa().getIva().divide(new BigDecimal(100), 2, RoundingMode.HALF_UP)));
            fac.setTotal(fac.getValoriva().add(valor));
            adm.guardar(fac);
            Detalle det = new Detalle(adm.getNuevaClave("Detalle", "codigo"));
            det.setTotal(new BigDecimal(redondear(object.getPlan().getValor(), 2)).subtract(object.getDescuento()));
            det.setPlan(object.getPlan());
            det.setCantidad(1);
            det.setMes(fechaInstalacion.getMonth() + 1);
            det.setAnio(fechaInstalacion.getYear() + 1900);
            det.setDescripcion("generada");
            det.setFactura(fac);
            adm.guardar(det);
            Cxcobrar cuenta = new Cxcobrar(adm.getNuevaClave("Cxcobrar", "codigo"));
            cuenta.setDebe(fac.getTotal());
            cuenta.setHaber(BigDecimal.ZERO);
            cuenta.setDeposito(BigDecimal.ZERO);
            cuenta.setCheque(BigDecimal.ZERO);
            cuenta.setEfectivo(BigDecimal.ZERO);
            cuenta.setDescuento(BigDecimal.ZERO);
            cuenta.setFactura(fac);
            cuenta.setFecha(fechaInstalacion);
            cuenta.setNotarjeta("");
            cuenta.setNocheque("");
            cuenta.setTotal(fac.getTotal());
            adm.guardar(cuenta);

        } catch (Exception e) {
            System.out.println("DUPLICADO: " + e.hashCode() + " .." + e);
            return e.hashCode() + "";
        }
        //seleccionar todos los clientes que tengan contrato activo o cortado (verificar si es )??

        //generar en facturas con el número y también en cxc.

        return "OK";
    }

    public static String convertiraString(Date fecha) {

        return (fecha.getYear() + 1900) + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();

    }

    String llenarCeros(String numero) {

        while (numero.length() < 7) {
            numero = "0" + numero;
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

    //BUSCAR PARA ASIGNAR FACTURA 
    public List buscar(Sucursal suc, Sector uno, Sector dos, String formapago) {
        //seleccionar todos los que no tenga deuda en éste més o periodo
        String complemento = " and o.formapago = '" + formapago + "' ";
        if (formapago.equals("0")) {
            complemento = "";
        }
        List<Contratos> contratos = adm.query("Select o from Contratos as o "
                + "where o.sector.numero between  " + uno.getNumero() + "  and  " + dos.getNumero() + " "
                + "and  o.sucursal.codigo =  '" + suc.getCodigo() + "'  " + complemento + " ");
        String contraString = "";
        for (Iterator<Contratos> itContratos = contratos.iterator(); itContratos.hasNext();) {
            Contratos contratos1 = itContratos.next();
            contraString = "" + contratos1.getCodigo() + "," + contraString + "";
        }
        if (contraString.length() > 0) {
            contraString = contraString.substring(0, contraString.length() - 1);
        }
        String quer = "SELECT fa.codigo, fa.numero, fa.fecha, CONCAT(cli.apellidos,' ',cli.nombres), c.direccion, fa.total, (SUM(cx.debe) - SUM(cx.haber)) saldo  "
                + "FROM cxcobrar cx, factura  fa, contratos c, clientes cli "
                + " WHERE fa.contratos in (" + contraString + ")  and c.codigo = fa.contratos  "
                + "  AND cx.factura = fa.codigo  AND cli.codigo = fa.clientes and fa.sucursal = '" + suc.getCodigo() + "'  GROUP BY fa.codigo "
                + " HAVING  (SUM(cx.debe) - SUM(cx.haber)) > 0 order by substring(fa.numero,9),  fa.contratos, fa.fecha ";
        List deudas = adm.queryNativo(quer);

        return deudas;
    }

    //BUSCAR PARA ASIGNAR FACTURA 
    public List buscar(Sucursal suc, Canton canton, String formapago, String diapago,Boolean anteriores) {
        //seleccionar todos los que no tenga deuda en éste més o periodo
        String complemento = " and o.formapago = '" + formapago + "' ";
        if (formapago.equals("0")) {
            complemento = "";
        }

        String complementoCanton = "  o.sector.canton.codigo  = '" + canton.getCodigo() + "'  ";
        if (canton.getCodigo().equals("-1")) {
            complementoCanton = "";
        }
        String complementoDia = " and o.diapago = "+diapago+"";
        if(anteriores){
            complementoDia = " and o.diapago <= "+diapago+" ";
        }
        List<Contratos> contratos = adm.query("Select o from Contratos as o "
                + "where "+complementoCanton
                + "and  o.sucursal.codigo =  '" + suc.getCodigo() + "'  "
                + " " + complemento + " "
                + " " + complementoDia);
        String contraString = "";
        for (Iterator<Contratos> itContratos = contratos.iterator(); itContratos.hasNext();) {
            Contratos contratos1 = itContratos.next();
            contraString = "" + contratos1.getCodigo() + "," + contraString + "";
        }
        if (contraString.length() > 0) {
            contraString = contraString.substring(0, contraString.length() - 1);
        }
        String quer = "SELECT fa.codigo, fa.numero, fa.fecha, CONCAT(cli.apellidos,' ',cli.nombres), c.direccion, fa.total, (SUM(cx.debe) - SUM(cx.haber)) saldo  "
                + "FROM cxcobrar cx, factura  fa, contratos c, clientes cli "
                + " WHERE fa.contratos in (" + contraString + ")  and c.codigo = fa.contratos  "
                + "  AND cx.factura = fa.codigo  AND cli.codigo = fa.clientes and fa.sucursal = '" + suc.getCodigo() + "'  GROUP BY fa.codigo "
                + " HAVING  (SUM(cx.debe) - SUM(cx.haber)) > 0 order by substring(fa.numero,9),  fa.contratos, fa.fecha ";
        List deudas = adm.queryNativo(quer);

        return deudas;
    }

    public List buscar(Sucursal suc, String tipoPlan) {
        //seleccionar todos los que no tenga deuda en éste més o periodo
        List<Contratos> contratos = adm.query("Select o from Contratos as o "
                + "where o.plan.tipo  like '%" + tipoPlan + "%'  order by o.clientes.apellidos ");
        String contraString = "";
        for (Iterator<Contratos> itContratos = contratos.iterator(); itContratos.hasNext();) {
            Contratos contratos1 = itContratos.next();
            contraString = "" + contratos1.getCodigo() + "," + contraString + "";
        }
        if (contraString.length() > 0) {
            contraString = contraString.substring(0, contraString.length() - 1);
        }
        String quer = "SELECT fa.codigo, fa.numero, fa.fecha, CONCAT(cli.apellidos,' ',cli.nombres),  fa.total, (SUM(cx.debe) - SUM(cx.haber)) saldo  "
                + "FROM cxcobrar cx, factura  fa, contratos c, clientes cli "
                + " WHERE fa.contratos in (" + contraString + ")  and c.codigo = fa.contratos  "
                + "  AND cx.factura = fa.codigo  AND cli.codigo = fa.clientes "
                + "and fa.sucursal = '" + suc.getCodigo() + "' and (fa.numero = '' or fa.numero is null) "
                + " GROUP BY fa.codigo "
                + " HAVING  (SUM(cx.debe) - SUM(cx.haber)) > 0 "
                + "order by cli.apellidos, fa.fecha ";
        List deudas = null;
        try {
            deudas = adm.queryNativo(quer);
        } catch (Exception e) {
            System.out.println("ERRROR: " + e);
            return null;
        }

        return deudas;
    }

    public List buscar(Sucursal suc, Empleados emp, Date fecha) {
        //seleccionar todos los que no tenga deuda en éste més o periodo
        String fec = convertiraString(fecha);
//         List<Factura> facturasLista = adm.queryNativo("Select o.factura from Empleadosfacturas as o "
//                 + "where o.empleados.codigo = "+emp.getCodigo()+"  "
//                 + "and  o.fecha = '"+fec+"'  ",Factura.class);
//         String facturaString = "";
//         for (Iterator<Factura> itfacturas = facturasLista.iterator(); itfacturas.hasNext();) {
//            Factura facturas1 = itfacturas.next();
//            facturaString = ""+facturas1.getCodigo()+","+facturaString+"";
//
//        }
//         if(facturaString.length()>0){
//             facturaString = facturaString.substring(0,facturaString.length()-1);
//         }
        String quer = "SELECT fa.codigo, fa.numero, fa.fecha,  CONCAT(cli.apellidos,' ',cli.nombres), c.direccion, fa.total, (SUM(cx.debe) - SUM(cx.haber)) saldo  "
                + "FROM cxcobrar cx, factura  fa, contratos c, clientes cli  "
                + " WHERE fa.codigo in ( Select x.factura from Empleadosfacturas as x where x.empleados = '" + emp.getCodigo() + "' and Date(x.fecha) = '" + fec + "' )  and c.codigo = fa.contratos  "
                + "  AND cx.factura = fa.codigo "
                + "   AND cli.codigo = fa.clientes AND fa.sucursal = '" + suc.getCodigo() + "'  GROUP BY fa.codigo  "
                + " HAVING  (SUM(cx.debe) - SUM(cx.haber)) > 0 order by substring(fa.numero,9),  fa.contratos, fa.fecha ";
        List deudas = adm.queryNativo(quer);

        return deudas;
    }

    public Double redondear(Double numero, int decimales) {
        try {
            BigDecimal d = new BigDecimal(numero);
            d = d.setScale(decimales, RoundingMode.HALF_UP);
            return d.doubleValue();
        } catch (Exception e) {
            return 0.0;
        }
    }
}
