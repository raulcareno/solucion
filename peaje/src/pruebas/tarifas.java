/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebas;

import hibernate.Factura;
import hibernate.Tarifas;
import hibernate.Tipotarifa;
import hibernate.cargar.Administrador;
import hibernate.cargar.UsuarioActivo;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

/**
 *
 * @author geovanny
 */
public class tarifas {

    static ArrayList<List<Tarifas>> listadoTarifas = new ArrayList();
    static List<Tipotarifa> tipostarifas;
    static List<Tarifas> tarifario;
    UsuarioActivo datosConecta = new UsuarioActivo();
    Administrador adm;

    public tarifas() {
        datosConecta.setNombre("root");
        datosConecta.setIp("localhost");
        datosConecta.setPuerto("3306");
        datosConecta.setContrasenia("j/eDF6Vyqmgzcb6udqpFMA==");
        adm = new Administrador(datosConecta);
    }

    public void funcionPrincipal() {
        try {
            // TODO code application logic here

            tipostarifas = adm.query("Select o from Tipotarifa as o  ");

            if (tipostarifas.size() > 0) {
                for (Iterator<Tipotarifa> iterator = tipostarifas.iterator(); iterator.hasNext();) {
                    Tipotarifa next = iterator.next();
                    tarifario = adm.query("Select o from Tarifas as o where o.hasta > 0  "
                            + " and o.tipotarifa.codigo = '" + next.getCodigo() + "' order by o.codigo ");
                    listadoTarifas.add(tarifario);
                }

            } else {
                tarifario = adm.query("Select o from Tarifas as o where  o.tipotarifa is null and  o.hasta > 0 order by o.codigo ");
                listadoTarifas.add(tarifario);
            }
            String ticket = "A102788";
//            Scanner sca = new Scanner(System.in);
// 
//            System.out.print("Introduzca un número ticket: ");
//            ticket = sca.nextLine();

            //List<Factura> fac = adm.query("Select o from Factura as o where o.fecha > '2015-04-30' and o.ticket = !=null");
            List<Factura> fac = adm.query("Select o from Factura as o where  o.ticket = 'a102783' ");
            for (Iterator<Factura> iterator = fac.iterator(); iterator.hasNext();) {
                Factura next = iterator.next();
                Long minutos0 = diferenciaFechas(next.getFechaini(), new Date());
                Integer minutos = minutos0.intValue();
                Integer horas = minutos / 60;
                buscarQueTarifaAplicar(next.getTicket(), next.getFechaini(), horas);
            }
            //empresaObj.getGracia()

        } catch (Exception ex) {
            Logger.getLogger(tarifas.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        tarifas t = new tarifas();
        t.funcionPrincipal();
    }

    public static long diferenciaFechas(Date fechai, Date fechaf) {
        //fechaf = new Date();
        java.util.GregorianCalendar date1 = new java.util.GregorianCalendar(fechai.getYear(), fechai.getMonth(), fechai.getDate(), fechai.getHours(), fechai.getMinutes(), fechai.getSeconds());
        java.util.GregorianCalendar date2 = new java.util.GregorianCalendar(fechaf.getYear(), fechaf.getMonth(), fechaf.getDate(), fechaf.getHours(), fechaf.getMinutes(), fechaf.getSeconds());
        long difms = date2.getTimeInMillis() - date1.getTimeInMillis();
        long difd = difms / (1000 * 60);
        return difd;
    }

    private static Tipotarifa buscarQueTarifaAplicar(String tck, Date fechaIngresoLlega, Integer horas) {
       

        for (Iterator iterator = tipostarifas.iterator(); iterator.hasNext();) {
            Tipotarifa itTt = (Tipotarifa) iterator.next();
            
             Date fechaActual = new Date();
        int diaActual = fechaIngresoLlega.getDay(); //1=Domingo, 2=Lunes 3=Martes,4=Miercoles,5=Jueves,6=Viernes
            boolean continua = false;
            if (diaActual == 0) {
                if (itTt.getDomingo()) {
                    continua = true;
                }
            } else if (diaActual == 1) {
                if (itTt.getLunes()) {
                    continua = true;
                }
            } else if (diaActual == 2) {
                if (itTt.getMartes()) {
                    continua = true;
                }
            } else if (diaActual == 3) {
                if (itTt.getMiercoles()) {
                    continua = true;
                }
            } else if (diaActual == 4) {
                if (itTt.getJueves()) {
                    continua = true;
                }
            } else if (diaActual == 5) {
                if (itTt.getViernes()) {
                    continua = true;
                }
            } else if (diaActual == 6) {
                if (itTt.getSabado()) {
                    continua = true;
                }
            }

            Date fecIn = itTt.getDesde();
            Date fecSal = itTt.getHasta();

            fecIn.setYear(fechaActual.getYear());
            fecIn.setDate(fechaActual.getDate());
            fecIn.setMonth(fechaActual.getMonth());

            fecSal.setYear(fechaActual.getYear());
            fecSal.setDate(fechaActual.getDate());
            fecSal.setMonth(fechaActual.getMonth());

            fechaIngresoLlega.setYear(fechaActual.getYear());
            fechaIngresoLlega.setDate(fechaActual.getDate());
            fechaIngresoLlega.setMonth(fechaActual.getMonth());

            DateTime fechaIngresoTarifa = new DateTime(fecIn);
            DateTime fechaSalidaTarifa = new DateTime(fecSal);
            DateTime fechaSalidaSumadoUnDiaTarifario = new DateTime(fecSal).plusDays(1);

            LocalTime horaIniTarifa = new LocalTime(fechaIngresoTarifa);
//            LocalTime horaSalidaCarro = new LocalTime(fechaActual);
            LocalTime horaFinTarifa = new LocalTime(fechaSalidaSumadoUnDiaTarifario); //fecha del tipo de Tarifa

            LocalTime ahoraLlegaIngresaCarro = new LocalTime(new DateTime(fechaIngresoLlega));
            System.out.println(" ");

            DateTime fechaLlegaCarro = new DateTime(fechaIngresoLlega);

            DateTime fechaSalidaCarro = new DateTime(fechaActual);

            Date fechaActual2 = new Date();
            fechaActual2.setDate(fechaIngresoLlega.getDate());
            fechaActual2.setMonth(fechaIngresoLlega.getMonth());
            fechaActual2.setYear(fechaIngresoLlega.getYear());
            DateTime fechaSalidaCarro2 = new DateTime(fechaActual2);

            //VERIFICO SI LA HORA DE INGRESO ESTÁ EN EL TIPO DE TARIFA
            //FALTA VERIFICAR SI EESTÁ EN EL RANGO FINAL DE LA HORA PERO LA HORA FINAL DE LLEGADA
            if (horaFinTarifa.compareTo(horaIniTarifa) >= 0) {
                if ((fechaLlegaCarro.compareTo(fechaIngresoTarifa) >= 0) && fechaLlegaCarro.compareTo(fechaSalidaTarifa) <= 0
                        && (fechaSalidaCarro.compareTo(fechaIngresoTarifa) >= 0) && (fechaSalidaCarro.compareTo(fechaSalidaTarifa) <= 0)
                        && continua && horas >= itTt.getNohoras()) {
                    System.out.println("EN EL RANGO DE HORA de tarifario");
                    for (int i = 0; i <= listadoTarifas.size(); i++) {
                        List<Tarifas> next = listadoTarifas.get(i);
                        if (next.get(0).getTipotarifa().getCodigo().equals(itTt.getCodigo())) {
                            tarifario = next;
                            System.out.println(tck + " FECHA INGRESA: " + fechaIngresoLlega.toLocaleString() + " tarifa: " + tarifario.get(0).getTipotarifa().getNombre() + " " + " Horas: " + tarifario.get(0).getTipotarifa().getDesde().toLocaleString() + " " + " Hasta: " + tarifario.get(0).getTipotarifa().getHasta().toLocaleString() + " " + " Horas: " + tarifario.get(0).getTipotarifa().getNohoras());
                            next = null;
                            break;
                        }

                    }
                    return null;

                }
            } else if (horaFinTarifa.compareTo(horaIniTarifa) < 0) {
                
                if((fechaLlegaCarro.compareTo(fechaIngresoTarifa) <= 0)){
                
                }else{
                
                }
                if ((fechaLlegaCarro.compareTo(fechaIngresoTarifa) >= 0)
                        && fechaLlegaCarro.compareTo(fechaSalidaSumadoUnDiaTarifario) <= 0
                        && (fechaSalidaCarro2.compareTo(fechaIngresoTarifa) >= 0)
                        && (fechaSalidaCarro2.compareTo(fechaSalidaSumadoUnDiaTarifario) <= 0)
                        && continua && horas >= itTt.getNohoras()) {
                    System.out.println("EN EL RANGO DE HORA 2(fecha 1 mayor a fecha 2) de tarifario");
                    for (int i = 0; i <= listadoTarifas.size(); i++) {
                        List<Tarifas> next = listadoTarifas.get(i);
                        if (next.get(0).getTipotarifa().getCodigo().equals(itTt.getCodigo())) {
                            tarifario = next;
                            System.out.println(tck + " FECHA INGRESA: " + fechaIngresoLlega.toLocaleString() + " tarifa: " + tarifario.get(0).getTipotarifa().getNombre() + " " + " Horas: " + tarifario.get(0).getTipotarifa().getDesde().toLocaleString() + " " + " Hasta: " + tarifario.get(0).getTipotarifa().getHasta().toLocaleString() + " " + " Horas: " + tarifario.get(0).getTipotarifa().getNohoras());
                            next = null;
                            break;
                        }

                    }
                    return null;
                } else if ((fechaLlegaCarro.compareTo(fechaIngresoTarifa) >= 0) ? true : true
                        && fechaLlegaCarro.compareTo(fechaSalidaSumadoUnDiaTarifario) <= 0
                        && (fechaSalidaCarro2.compareTo(fechaIngresoTarifa) >= 0)
                        && (fechaSalidaCarro2.compareTo(fechaSalidaSumadoUnDiaTarifario) <= 0)
                        && continua && horas >= itTt.getNohoras()) {
                    System.out.println("EN EL RANGO DE HORA 2(fecha 1 mayor a fecha 2) de tarifario");
                    for (int i = 0; i <= listadoTarifas.size(); i++) {
                        List<Tarifas> next = listadoTarifas.get(i);
                        if (next.get(0).getTipotarifa().getCodigo().equals(itTt.getCodigo())) {
                            tarifario = next;
                            System.out.println(tck + "_____ FECHA INGRESA: " + fechaIngresoLlega.toLocaleString() + " tarifa: " + tarifario.get(0).getTipotarifa().getNombre() + " " + " Horas: " + tarifario.get(0).getTipotarifa().getDesde().toLocaleString() + " " + " Hasta: " + tarifario.get(0).getTipotarifa().getHasta().toLocaleString() + " " + " Horas: " + tarifario.get(0).getTipotarifa().getNohoras());
                            next = null;
                            break;
                        }

                    }
                    return null;
                }

            }
        }
//        JOptionPane.showMessageDialog(this, "CREE ALGUNA TARIFA QUE SE ENCUENTRE EN ESTE HORARIO Y DIA");
        return null;

    }

}
