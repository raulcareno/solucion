/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Cursos;
import jcinform.persistencia.Estudiantes;
import jcinform.persistencia.Inscripciones;
import jcinform.persistencia.Matriculas;
import jcinform.persistencia.Periodo;
import jcinform.persistencia.Representante;
import jcinform.procesos.Administrador;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericComposer;
//import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

/**
 *
 * @author geovanny
 */
public class Sorteo extends GenericComposer implements Serializable {
//	Window updateMyEntry;

    Listbox datos;
    Intbox estudiantes;
    Intbox total;
    Decimalbox base;
    Decimalbox desde;
    Decimalbox hasta;
    Intbox totalAbanderados;
    Intbox totalOtros;
    Listbox periodo;
    Listbox cursos;
    ArrayList favorecidos;
//    Progressmeter cargando;

    @Override
    public void doAfterCompose(Component win) throws Exception {
        super.doAfterCompose(win);
        datos = (Listbox) win.getFellow("datos");
        estudiantes = (Intbox) win.getFellow("estudiantes");
        total = (Intbox) win.getFellow("total");
        totalAbanderados = (Intbox) win.getFellow("totalAbanderados");
        totalOtros = (Intbox) win.getFellow("totalOtros");

        base = (Decimalbox) win.getFellow("base");
        desde = (Decimalbox) win.getFellow("desde");
        hasta = (Decimalbox) win.getFellow("hasta");
        periodo = (Listbox) win.getFellow("periodosListado");
        cursos = (Listbox) win.getFellow("cursosListado");

//        cargando = (Progressmeter)win.getFellow("cargando");
    }

    public void onSaveTask(Event evt) {

        Administrador adm = new Administrador();
        List abanderados = adm.queryNativo("Select a.* from Inscripciones as a where a.aprovechamiento >= " + base.getValue().doubleValue() + " and a.periodo = '" + ((Periodo) periodo.getSelectedItem().getValue()).getCodigoper() + "' order by a.aprovechamiento desc limit " + estudiantes.getValue() + " ", Inscripciones.class);

        List relacion = adm.queryNativo("Select a.* from Inscripciones as a where a.relacion = true  and a.aprovechamiento < " + hasta.getValue().doubleValue() + "  and a.periodo = '" + ((Periodo) periodo.getSelectedItem().getValue()).getCodigoper() + "'  ", Inscripciones.class);

        List otrosEstudiantes = adm.queryNativo("Select o.* from Inscripciones as o where (o.aprovechamiento >= " + desde.getValue().doubleValue() + " and o.aprovechamiento < " + hasta.getValue().doubleValue() + ")  and o.periodo = '" + ((Periodo) periodo.getSelectedItem().getValue()).getCodigoper() + "'  ", Inscripciones.class);
        ArrayList todos = new ArrayList();

        for (Iterator it = relacion.iterator(); it.hasNext();) {
            Inscripciones object = (Inscripciones) it.next();
            todos.add(object);
        }

        for (Iterator it = abanderados.iterator(); it.hasNext();) {
            Inscripciones object = (Inscripciones) it.next();
            todos.add(object);
        }
        ArrayList indiceSeleccionados = new ArrayList();
        int Noestudiantes = estudiantes.getValue() - abanderados.size() - relacion.size();
        int j = 0;
        if (Noestudiantes > otrosEstudiantes.size()) {
            Noestudiantes = otrosEstudiantes.size();
        }
        int incremento = (Noestudiantes) / 100;
        while (j < Noestudiantes) {
//    if((otrosEstudiantes.size()+abanderados.size())>estudiantes.getValue().intValue()){//si el número es mayor al ingresado
            int val = (int) (Math.random() * otrosEstudiantes.size());
            while (indiceSeleccionados.contains(val)) {
                val = (int) (Math.random() * otrosEstudiantes.size());
            }
            indiceSeleccionados.add(val);
//    }
            j++;
//    cargando.setValue(cargando.getValue() == 100 ? (0) : cargando.getValue()+1);
//    cargando.setValue(incremento);
//    incremento++;
//    System.out.println(""+incremento);
        }
        for (Iterator it = indiceSeleccionados.iterator(); it.hasNext();) {
            Integer indice = (Integer) it.next();
            todos.add(otrosEstudiantes.get(indice.intValue()));
        }
        total.setValue(todos.size());
        totalAbanderados.setValue(abanderados.size());
        totalOtros.setValue(indiceSeleccionados.size() + relacion.size());
//        datos = new Listbox();
        int a = 0;
        for (Iterator it = datos.getItems().iterator(); it.hasNext();) {
            datos.getItems().remove(a);
        }
        for (Iterator it = todos.iterator(); it.hasNext();) {
            Inscripciones acceIt = (Inscripciones) it.next();
            final Listitem li = new Listitem();
            li.setValue(acceIt);
            li.appendChild(new Listcell(acceIt.getCodigoest() + ""));
            li.appendChild(new Listcell(acceIt.getApellido() + " " + acceIt.getNombre()));
            li.appendChild(new Listcell(acceIt.getAprovechamiento() + ""));
            datos.appendChild(li);
        }
            favorecidos = todos;
 copiar();
    }

    public void copiar(){
        Administrador adm = new Administrador();
        int i=1;
        for (Iterator it = favorecidos.iterator(); it.hasNext();) {
            Inscripciones o = (Inscripciones) it.next();
            Representante r = new Representante(adm.getNuevaClave("Representante", "codigorep"));
            r.setApellidos(o.getApellidor());
            r.setApemadre(o.getApellidma());
            r.setApepadre(o.getApellidopa());
            r.setDireccion(o.getDireccion());
            r.setDirfactura("");
            r.setDirmadre(o.getDireccionma());
            r.setDirpadre(o.getDireccionpa());
            r.setEmail(o.getMailr());
            r.setEstado(true);
            r.setIdentificacionfactura("");
            r.setIdentificacionmadre(o.getCedulam());
            r.setIdentificacionpadre(o.getCedulap());
            r.setIdentificacionrepre(o.getCedular());
            r.setMailmadre(o.getMail());
            r.setMailpadre(o.getMail());
            r.setNombrefactura("");
            r.setNombres(o.getNombrer());
            r.setNommadre(o.getNombrema());
            r.setNompadre(o.getNombrepa());
            r.setOcupacionmadre(o.getLugarma());
            r.setProfesionmadre(o.getTrabajoma());//profecion es trabajo
            r.setOcupacionpadre(o.getLugarpa());
            r.setProfesionpadre(o.getTrabajopa());
            r.setParentesco(o.getVivecon());
            r.setTelfactura("");
            r.setTelmadre(o.getTelefonoma());
            r.setTelpadre(o.getTelefonopa());
            r.setTipoidentificacion("C");
            r.setUsuario(generarClave(o.getApellidor(), o.getNombrer()));
            r.setClave(generarClave(o.getApellidor(), o.getNombrer()));
adm.guardar(r);
            Estudiantes n = new Estudiantes();
            n.setCodigoest(adm.getNuevaClave("Estudiantes", "codigoest"));
            n.setApellido(o.getApellido());
            n.setNombre(o.getNombre());
            n.setCedula(o.getCedula());
            n.setAdventista(false);
            n.setDireccion(o.getDireccion());
            n.setEstado(true);
            n.setFechanacimiento(o.getFechanacimiento());
            n.setGenero(o.getGenero());
            n.setMail(o.getMail());
            n.setNobus("");
            n.setPendientes(false);
            n.setTelefono(o.getTelefono());
            n.setRepresentante(r);
            n.setUsuario(generarClave(o.getApellido(), o.getNombre()));
            n.setClave(generarClave(o.getApellido(), o.getNombre()));
adm.guardar(n);

        Matriculas m = new Matriculas(adm.getNuevaClave("Matriculas", "codigomat"));
        m.setBeca(0.0);
        m.setCurso((Cursos) cursos.getSelectedItem().getValue());
        m.setEstado("Matriculado");
        m.setEstudiante(n);
        m.setFechains(new Date());
        m.setFechamat(new Date());
        m.setFolio(i);
        m.setInstitucion(o.getInstanterior());
        m.setNuevo(true);
        m.setNumero(i);
        m.setObservacion("");
        m.setOtros(0.0);
        m.setRepite(false);
        adm.guardar(m);
 i++;
        }
    }
        String generarClave(String apellidos, String nombres){
     
         
        String caracter="JCINFRM";
        caracter+="JCQWERTYUIO"+apellidos+nombres+"PASDFGHJKLZXCVBNM";
        int numero_caracteres=5;
        int total=caracter.length();
                   String  clave2="";
                        for(int a=0;a<numero_caracteres;a++){
                            clave2+=caracter.charAt(((Double)(total*Math.random())).intValue());
                        }
                   return clave2.toLowerCase();
    }



    private int numeroAzar(int minimo, int maximo) {

        // intervalo del rango de números (incluidos)
        int intervalo = maximo - minimo + 1;

        // selección del número al azar dentro del rango
        int azarEnRango = (int) (Math.random() * intervalo);

        // devolver el selecciona en el rango para el intervalo
        return (azarEnRango + minimo);
    }
}
