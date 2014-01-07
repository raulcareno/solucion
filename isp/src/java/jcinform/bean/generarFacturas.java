/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jcinform.bean;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import jcinform.conexion.Administrador;
import jcinform.persistencia.*;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;

/**
 *
 * @author Familia Jadan Cahueñ jcinform.bean.generarFacturas.convertiraString
 */
public class generarFacturas {

    Administrador adm;

    public generarFacturas() {
        adm = new Administrador();
    }

    public void generacionAutomatico(final Sucursal suc) {

        Thread cargar = new Thread() {

            @Override
            public void run() {
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
                    System.out.println("INICIO EJECUTAR PROCESO: " + adm.Date());
                    Procesos pro = new Procesos(fechaSql);
                    pro.setFecha(fecha);
                    pro.setFechaejecutado(fecha);
                    pro.setEjecutado(true);
                    //pro.setProblemas(fechaSql);

                    empezarGenerar(fecha, 0, suc);
                    System.out.println("FIN DEL PROCESO: " + adm.Date());
                    try {
                        adm.guardar(pro);
                    } catch (Exception e) {
                        System.out.println("" + e);
                    }


                }
            }
        };
        cargar.start();

    }

    public String empezarGenerar(Date fecha, Integer numero, Sucursal suc) { //GENERACION AUTOMATICA
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

        /*
         *
         * List facEncontradas = adm.queryNativo("SELECT fa.codigo, fa.fecha,
         * fa.total, ((SUM(cx.debe) + SUM(cx.rtotal)) - SUM(cx.haber))
         * saldo,fa.contratos, fa.numero,fa.emision FROM cxcobrar cx, factura fa
         * " + " WHERE fa.clientes = "+codigocli.value+" " + " AND cx.factura =
         * fa.codigo GROUP BY fa.codigo HAVING (SUM(cx.debe) - SUM(cx.haber)) >
         * 0 order by fa.contratos, fa.fecha "); *
         */

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
                //ESTO LE VA A AÑADIR UN VALOR POR RECONEXION
                Equipos equipoMora = null;
                if (suc.getEmpresa().getAplicamora()) {
                    try {
                        equipoMora = ((Equipos) adm.buscarClave(suc.getEmpresa().getMora(), Equipos.class));
                        List facEncontradas = adm.queryNativo("SELECT fa.codigo, fa.fecha, fa.total, "
                                + " ((SUM(cx.debe) + SUM(cx.rtotal)) - SUM(cx.haber)) saldo,fa.contratos, fa.numero,fa.emision "
                                + " FROM cxcobrar cx, factura  fa  "
                                + " WHERE fa.contratos =  " + object.getCodigo() + "   "
                                + " AND cx.factura = fa.codigo "
                                + " GROUP BY fa.codigo   "
                                + " HAVING  (SUM(cx.debe) - SUM(cx.haber)) > 0 ");
                        if (facEncontradas.size() > 0) {
                        }
                    } catch (Exception e) {
                        System.out.println("ERROR ASIGNADO VALOR DE MORA");
                    }

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
                cuenta.setRtotal(BigDecimal.ZERO);
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
                cuenta.setRtotal(BigDecimal.ZERO);
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

    public String anadirMora(Sucursal suc, Contratos con) { //ESTO ES PARA AÑADIR COBROS PENDIENTES individual
        //seleccionar todos los que no tenga deuda en éste més o periodo
        if (!suc.getEmpresa().getAplicamora()) {
            return "";
        }
        Administrador adm = new Administrador();
        Date fecha2 = adm.Date();
        //fecha2.setDate(1);
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

            Equipos eqValorMora = (Equipos) adm.querySimple("Select o from Equipos as o where o.codigo = '" + suc.getEmpresa().getMora() + "' ");

            BigDecimal valor = new BigDecimal(redondear(eqValorMora.getPvp1().doubleValue(), 2));
//            if (object.getFormapago().equals(3)) {
//                valor = valor.add(suc.getEmpresa().getInstalacion());
//            }
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
            //det.setPlan(object.getPlan());
            det.setEquipos(eqValorMora);
            det.setCantidad(1);
            det.setMes(fecha2.getMonth() + 1);
            det.setAnio(fecha2.getYear() + 1900);
            det.setDescripcion("generadamora");
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
            cuenta.setRtotal(BigDecimal.ZERO);
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
            cuenta.setRtotal(BigDecimal.ZERO);
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
            det.setTotal(valor.subtract(object.getDescuento()));
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
            cuenta.setRtotal(BigDecimal.ZERO);
            adm.guardar(cuenta);

        } catch (Exception e) {
            System.out.println("DUPLICADO: " + e.hashCode() + " .." + e);
            return e.hashCode() + "";
        }
        //seleccionar todos los clientes que tengan contrato activo o cortado (verificar si es )??

        //generar en facturas con el número y también en cxc.

        return "OK";
    }
    
     public String generarCobrosProrrateado(Sucursal suc, Contratos con, Date fechaInstalacion) {
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
                System.out.println("NO SE HA AÑADIDO POR QUE YA TIENE DEDUA PARA EL MES ");
//                int valor0 = Messagebox.show("Ya se ha añadido una Deuda para éste mes, desea continuar añadiendolo?", "JCINFORM-Seguridad", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
//                if (valor0 == 16) {
//                } else {
                    return "";
//                }
            } catch (Exception ex) {
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
            if (fechaInstalacion.getDate() > 1 && fechaInstalacion.getDate() > suc.getEmpresa().getDiasminima()) {
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
            det.setTotal(valor.subtract(object.getDescuento()));
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
            cuenta.setRtotal(BigDecimal.ZERO);
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

    //BUSCAR FACTURAS EN CUENTAS POR COBRAR //cerrar día
    public List buscar(Sucursal suc, Date desde) {
        //seleccionar todos los que no tenga deuda en éste més o periodo

        String desdestr = convertiraString(desde) + "";
        List<Integer> contratos = adm.query("Select o.codigo from Contratos as o "
                + "where  o.sucursal.codigo =  '" + suc.getCodigo() + "'  ");
        String contraString = "";
        for (Iterator<Integer> itContratos = contratos.iterator(); itContratos.hasNext();) {
            Integer contratos1 = itContratos.next();
            contraString = "" + contratos1 + "," + contraString + "";
        }
        if (contraString.length() > 0) {
            contraString = contraString.substring(0, contraString.length() - 1);
        }
        String quer = "SELECT fa.codigo, fa.numero, fa.fecha, "
                + "IF(cli.razonsocial IS NULL OR cli.razonsocial ='' ,CONCAT(cli.apellidos,' ',cli.nombres),cli.razonsocial),  fa.total, cx.haber, "
                + "IF(fa.total = cx.haber, 'CANCELADO', 'ABONO'), "
                + " cx.efectivo, cx.cheque, cx.codigo,  c.contrato, fa.fecha, fa.vencimiento, cli.identificacion , cx.rtotal "
                + " FROM cxcobrar cx, factura  fa, contratos c, clientes cli "
                + " WHERE (cx.efectivo > 0 OR cx.cheque >0) "
                + "AND  fa.contratos in (" + contraString + ")  "
                + "and c.codigo = fa.contratos  "
                + "  AND cx.factura = fa.codigo  "
                + "AND cli.codigo = fa.clientes and fa.sucursal = '" + suc.getCodigo() + "'  "
                + " AND cx.fecha between '" + desdestr + "' and '" + desdestr + "' AND cx.haber > 0 "
                + " AND cx.codigo not in (Select cxcobrar from Depositos) GROUP BY cx.codigo "
                + "   "
                + " order by substring(fa.numero,9),  fa.contratos, fa.fecha ";
        System.out.println("" + quer);
        List deudas = adm.queryNativo(quer);

        return deudas;
    }

    //BUSCAR PARA ASIGNAR FACTURA 
    public List buscar(Sucursal suc, Sector uno, Sector dos, String formapago, String estado) {
        //seleccionar todos los que no tenga deuda en éste més o periodo
        String complemento = " and o.formapago = '" + formapago + "' ";
        String complementoSectores = " and o.sector.numero between  " + uno.getNumero() + "  and  " + dos.getNumero() + " ";
        if (uno.getCodigo().equals(new Integer(-1))) {
            complementoSectores = "";
        }
        if (formapago.equals("0")) {
            complemento = "";
        }
        String compEstado = " and o.estado = '" + estado + "' ";
        if (estado.equals("Todos")) {
            compEstado = "";
        }
        List<Contratos> contratos = adm.query("Select o from Contratos as o "
                + "where o.sucursal.codigo =  '" + suc.getCodigo() + "'  " + complementoSectores + complemento + " " + compEstado);
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

    public List buscarPagos(Sucursal suc, Canton canton, Date desde, Date hasta) {
        //seleccionar todos los que no tenga deuda en éste més o periodo
        String desdestr = convertiraString(desde) + "";
        String hastastr = convertiraString(hasta) + "";

        String complementoCanton = "  o.sector.canton.codigo  = '" + canton.getCodigo() + "'  ";
        if (canton.getCodigo().equals("-1")) {
            complementoCanton = "";
        }
        List<Contratos> contratos = adm.query("Select o from Contratos as o "
                + "where " + complementoCanton
                + " and o.formapago = 2 and  o.sucursal.codigo =  '" + suc.getCodigo() + "'  "
                + " ");
        String contraString = "";
        for (Iterator<Contratos> itContratos = contratos.iterator(); itContratos.hasNext();) {
            Contratos contratos1 = itContratos.next();
            contraString = "" + contratos1.getCodigo() + "," + contraString + "";
        }
        if (contraString.length() > 0) {
            contraString = contraString.substring(0, contraString.length() - 1);
        }
        String query = "Select o from Cxcobrar as o "
                + " where o.factura.contratos.codigo in (" + contraString + ") "
                + "and o.fecha between '" + desdestr + "'  and '" + hastastr + "' and o.haber > 0 order by o.fecha  ";
        List<Cxcobrar> cuentas = adm.query(query);
//        for (Iterator<Cxcobrar> it = cuentas.iterator(); it.hasNext();) {
//                Cxcobrar cxcobrar = it.next(); 
//                
//        }
        return cuentas;
    }
    //BUSCAR PARA ASIGNAR FACTURA 

    public List buscar(Sucursal suc, Canton canton, String formapago, String diapago, String diapago2, Bancos bancoSel) {
        //seleccionar todos los que no tenga deuda en éste més o periodo
        String complemento = " and o.formapago = '" + formapago + "' ";
        if (formapago.equals("0")) {
            complemento = "";
        }

        String complementoCanton = "  o.sector.canton.codigo  = '" + canton.getCodigo() + "'  ";
        if (canton.getCodigo().equals("-1")) {
            complementoCanton = "";
        }

        String complementoBanco = "  and o.bancos.codigo = '" + bancoSel.getCodigo() + "'  ";
        if (bancoSel.getCodigo().equals(-1)) {
            complementoBanco = "";
        }

        String complementoDia = " and o.diapago >= " + diapago + " and o.diapago <= " + diapago2 + " ";
//        if(anteriores){
//            complementoDia = " and o.diapago <= "+diapago+" ";
//        }
        List<Contratos> contratos = adm.query("Select o from Contratos as o "
                + "where " + complementoCanton
                + " and o.formapago = 2 and  o.sucursal.codigo =  '" + suc.getCodigo() + "'  "
                + " " + complemento + ""
                + " " + complementoBanco
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
        String quer = "SELECT fa.codigo, fa.numero, fa.fecha, CONCAT(cli.apellidos,' ',cli.nombres),  fa.total, (SUM(cx.debe) - SUM(cx.haber)) saldo, c.serie3  "
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
//PARA LA PANTALLA GENERAR FACTURAS

    public List buscar(Sucursal suc, String tipoPlan, String dondepaga, Bancos banco) {
        /*
         * if(equi.getFormapago().equals(1)){//SE COBRA EN OFICIONA efe.checked
         * = true; }else if(equi.getFormapago().equals(2)){//POR DÉBITO BANCARIO
         * deb.checked = true; }else if(equi.getFormapago().equals(3)){ //COBRA
         * A DOMICIOLIO cbr.checked = true; } else { efe.checked = true; }
         */


        //seleccionar todos los que no tenga deuda en éste més o periodo
        String compleDondePaga = " and o.formapago  = '" + dondepaga + "' ";
        if (dondepaga.contains("0")) {
            compleDondePaga = " and o.formapago is not null ";
        } else if (dondepaga.contains("2")) {//ES POR DÉBITO
            compleDondePaga = " and o.formapago   = '" + dondepaga + "'   and o.bancos.codigo = '" + banco.getCodigo() + "' ";
            if (banco.getCodigo().equals(new Integer(0))) {
                compleDondePaga = " and o.formapago   = '" + dondepaga + "'  ";
            }
        }
        List contratos = adm.query("Select o.codigo from Contratos as o "
                + "where o.plan.tipo = '" + tipoPlan + "' " + compleDondePaga + "  order by o.clientes.apellidos ");
        String contraString = "";
        contraString = contratos.toString().replace("[", "").replace("]", "");
//        for (Iterator itContratos = contratos.iterator(); itContratos.hasNext();) {
//            Integer contratos1 = (Integer)itContratos.next();
//            //contratos1.getBancos().getCodigo();
//            contraString = "" + contratos1 + "," + contraString + "";
//        }
//        if (contraString.length() > 0) {
//            contraString = contraString.substring(0, contraString.length() - 1);
//        }

        String quer = "SELECT fa.codigo, fa.numero, fa.fecha, CONCAT(cli.apellidos,' ',cli.nombres),  fa.total, (SUM(cx.debe) - SUM(cx.haber)) saldo, c.serie3  "
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
        contratos = null;

        return deudas;
    }

    //PARA LA PANTALLA GENERAR FACTURAS 2 Y RECIBOS
    public List buscar(Sucursal suc, String tipoPlan, String dondepaga, Bancos banco, String seFactura, Sector sect) {

        String complementotipoPlan = "o.plan.tipo = '" + tipoPlan + "' ";
        if (tipoPlan.equals("TODOS")) {
            complementotipoPlan = "o.plan.tipo like '%%' ";
        }

        String complementoSector = " and  o.sector.codigo  = '" + sect.getCodigo() + "'  ";
        if (sect.getCodigo().equals(new Integer("-1"))) {
            complementoSector = "";
        }
        String complementoFacturar = "";
        if (seFactura.equals(new Integer("-1"))) {
            complementoFacturar = "";
        } else if (seFactura.equals("SI")) {
            complementoFacturar = " and  o.serie3 = '" + seFactura + "'  ";
        } else if (seFactura.equals("NO")) {
            complementoFacturar = " and  o.serie3 = 'NO'  ";
        }
        //seleccionar todos los que no tenga deuda en éste més o periodo
        String compleDondePaga = " and o.formapago  = '" + dondepaga + "' ";
        if (dondepaga.contains("0")) {
            compleDondePaga = " and o.formapago is not null ";
        } else if (dondepaga.contains("2")) {//ES POR DÉBITO
            compleDondePaga = " and o.formapago   = '" + dondepaga + "'   and o.bancos.codigo = '" + banco.getCodigo() + "' ";
            if (banco.getCodigo().equals(new Integer(0))) {
                compleDondePaga = " and o.formapago   = '" + dondepaga + "'  ";
            }
        }
        String query = "Select o.codigo from Contratos as o "
                + "where " + complementotipoPlan + " " + compleDondePaga + "" + complementoSector + "  " + complementoFacturar
                + "order by o.clientes.apellidos ";
        List contratos = adm.query(query);
        String contraString = "";
        contraString = contratos.toString().replace("[", "").replace("]", "");
//        for (Iterator itContratos = contratos.iterator(); itContratos.hasNext();) {
//            Integer contratos1 = (Integer)itContratos.next();
//            //contratos1.getBancos().getCodigo();
//            contraString = "" + contratos1 + "," + contraString + "";
//        }
//        if (contraString.length() > 0) {
//            contraString = contraString.substring(0, contraString.length() - 1);
//        }

        String quer = "SELECT fa.codigo, fa.numero, fa.fecha, CONCAT(cli.apellidos,' ',cli.nombres),  fa.total, (SUM(cx.debe) - SUM(cx.haber)) saldo, c.serie3  "
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
        contratos = null;

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

    //BUSCA POR NUMERO DE INFORME BUSCO LAS FACTURAS ENVIADAS.
    public List buscar(Sucursal suc, Integer numero) {
        //seleccionar todos los que no tenga deuda en éste més o periodo

        String quer = "SELECT fa.codigo, fa.numero, fa.fecha,  CONCAT(cli.apellidos,' ',cli.nombres), c.direccion, fa.total, (SUM(cx.debe) - SUM(cx.haber)) saldo  "
                + "FROM cxcobrar cx, factura  fa, contratos c, clientes cli  "
                + " WHERE fa.codigo in ( Select x.factura from Facturasenviadas as x where x.numero= '" + numero + "' "
                + " )  and c.codigo = fa.contratos  "
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

    public byte[] comprobarEstados(Sucursal suc) { //cambio de estados a clientes por deuda

        String quer = "SELECT fa.contratos FROM cxcobrar cx, factura  fa, contratos c, clientes cli,plan p  "
                + " WHERE c.codigo = fa.contratos   AND p.codigo = c.plan   "
                + "  AND cx.factura = fa.codigo AND fa.fecha "
                + " AND c.automatico = TRUE  "
                + " AND CURDATE() > CONCAT(YEAR(fa.fecha),'-',MONTH(fa.fecha),'-',p.fechavencimiento) "
                + "   AND cli.codigo = fa.clientes AND fa.sucursal = '" + suc.getCodigo() + "'  GROUP BY fa.codigo  "
                + " HAVING  (SUM(cx.debe) - SUM(cx.haber)) > 0 order by substring(fa.numero,9),  fa.contratos, fa.fecha ";
        List deudas = adm.queryNativo(quer);
        String contratosList = "";
        for (int i = 0; i < deudas.size(); i++) {
            contratosList += deudas.get(i) + ",";

        }
        //TENGO QUE ACTUALIZAR A TODOS LOS CONTRATOS QUE SE ENCUENTREN CORTADOS
        adm.ejecutaSqlNativo("UPDATE contratos SET estado = 'Activo' WHERE estado = 'Cortado' and automatico = true ");
        if (contratosList.length() > 0) {
            contratosList = contratosList.substring(0, contratosList.length() - 1);
            contratosList = contratosList.replace("[", "").replace("]", "");
            adm.ejecutaSqlNativo("UPDATE contratos SET estado = 'Cortado' WHERE automatico = true  and estado = 'Activo' and codigo in (" + contratosList + ") ");
        }

        try {
            byte[] data = null;

            File outFile = File.createTempFile("secureisp", ".csv");
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
            List<Contratos> listado = adm.query("Select o from Contratos as o where o.estado in ('Activo','Cortado','Suspendido') ");
            for (Iterator<Contratos> it = listado.iterator(); it.hasNext();) {
                Contratos contratos = it.next();
                writer.write("" + contratos.getContrato() + ";" + (contratos.getEstado().equals("Activo") ? "habilitado" : "deshabilitado")); // numero de autorización
                writer.write("\r\n");
            }
            listado = null;
            FileInputStream input;
            writer.close();
            try {
                input = new FileInputStream(outFile);
                data = new byte[input.available()];
                input.read(data);
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return data;
        } catch (Exception ex) {
            Logger.getLogger(generarFacturas.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

    public byte[] comprobarEstadosAviso(Sucursal suc) { //aviso

        String quer = "SELECT fa.contratos FROM cxcobrar cx, factura  fa, contratos c, clientes cli,plan p  "
                + " WHERE c.codigo = fa.contratos   AND p.codigo = c.plan   "
                + "  AND cx.factura = fa.codigo AND fa.fecha "
                + " AND c.automatico = TRUE AND c.estado in ('Activo','Cortado','Suspendido')  "
                + " AND CURDATE() > CONCAT(YEAR(fa.fecha),'-',MONTH(fa.fecha),'-',p.fechaaviso)  "
                + " AND CURDATE() <= CONCAT(YEAR(fa.fecha),'-',MONTH(fa.fecha),'-',p.fechavencimiento)   "
                + "   AND cli.codigo = fa.clientes AND fa.sucursal = '" + suc.getCodigo() + "'  GROUP BY fa.codigo  "
                + " HAVING  (SUM(cx.debe) - SUM(cx.haber)) > 0 order by substring(fa.numero,9),  fa.contratos, fa.fecha ";
        List deudas = adm.queryNativo(quer);
        String contratosList = "";
        for (int i = 0; i < deudas.size(); i++) {
            contratosList += deudas.get(i) + ",";
        }

        if (contratosList.length() > 0) {
            contratosList = contratosList.substring(0, contratosList.length() - 1);
            contratosList = contratosList.replace("[", "").replace("]", "");
            try {
                byte[] data = null;

                File outFile = File.createTempFile("secureisp", ".csv");
                BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
                List<Contratos> listado = adm.query("Select o from Contratos as o where o.codigo in (" + contratosList + ") ");
                for (Iterator<Contratos> it = listado.iterator(); it.hasNext();) {
                    Contratos contratos = it.next();
                    writer.write("" + contratos.getContrato() + ";" + "avisado"); // numero de autorización
                    writer.write("\r\n");
                }
                FileInputStream input;
                writer.close();
                try {
                    input = new FileInputStream(outFile);
                    data = new byte[input.available()];
                    input.read(data);
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                listado = null;

                return data;
            } catch (Exception ex) {
                Logger.getLogger(generarFacturas.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return null;
    }

    public byte[] comprobarEstados(Sucursal suc, Contratos contrato) { //GENERACION AUTOMATICA

        String quer = "SELECT fa.contratos FROM cxcobrar cx, factura  fa, contratos c, clientes cli, plan p  "
                + " WHERE c.codigo = fa.contratos  AND p.codigo = c.plan   "
                + "  AND cx.factura = fa.codigo "
                + " AND c.automatico = TRUE  "
                + " AND CURDATE() > CONCAT(YEAR(fa.fecha),'-',MONTH(fa.fecha),'-',p.fechavencimiento) "
                + "   AND cli.codigo = fa.clientes AND fa.sucursal = '" + suc.getCodigo() + "' "
                + " and fa.contratos = '" + contrato.getCodigo() + "'  GROUP BY fa.codigo  "
                + " HAVING  (SUM(cx.debe) - SUM(cx.haber)) > 0 order by substring(fa.numero,9),  fa.contratos, fa.fecha ";
        List deudas = adm.queryNativo(quer);
        String contratosList = "";
        for (int i = 0; i < deudas.size(); i++) {
            contratosList += deudas.get(i) + ",";

        }

        if (contratosList.length() > 0) {
            contratosList = contratosList.substring(0, contratosList.length() - 1);
            contratosList = contratosList.replace("[", "").replace("]", "");
            adm.ejecutaSqlNativo("UPDATE contratos SET estado = 'Cortado' WHERE automatico = true  and codigo in (" + contratosList + ") ");
        } else {
            //TENGO QUE ACTUALIZAR A TODOS LOS CONTRATOS QUE SE ENCUENTREN CORTADOS
            if(contrato.getEstado().equals("Suspendido") || contrato.getEstado().equals("Terminado")){
                return null;
            }else{
                adm.ejecutaSqlNativo("UPDATE contratos SET estado = 'Activo' WHERE estado = 'Cortado' and codigo = '" + contrato.getCodigo() + "' and automatico = true ");
                generarCobrosProrrateado(suc, contrato, new Date());
            }
             //TOMAR EN CUENTA SI NO ESTA EN FIN DE MES 31
             //TOMAR EN CUENTA QUE SI ES QUE ESTA ENTRE EL 1 Y EL 5
             //TOMAR EN CUENTA SI ES QUE NO HA PAGADO EL MES Y QUE NO SE GENERE DOS VECES EL MISMO MES
             //
        }




        return null;
    }
//    public void comprobarEstados() {
//
//        Thread cargar = new Thread() {
//            public void run() {
//                while (true) {
//
//                    try {
//                         
//
//
//                    } catch (Exception ex) {
//                        Logger.getLogger(generarFacturas.class.getName()).log(Level.SEVERE, null, ex);
//                    }  
//                }
//            }
//        };
//        cargar.start();
//
//
//    }
}
