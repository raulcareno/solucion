/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import jcinform.persistencia.*;
import jcinform.procesos.Administrador;

/**
 *
 * @author Ismael Jadan
 */
public class generaOptativaManuela {

    public static Estudiantes existe(List<Estudiantes> estu, String apellido, String nombre) {
        for (Iterator<Estudiantes> it = estu.iterator(); it.hasNext();) {
            Estudiantes estudiantes = it.next();
            if (estudiantes.getApellido().contains(apellido) && estudiantes.getNombre().contains(nombre)) {
                return estudiantes;
            }
        }
        return null;

    }
//
//    public static void copiarInscritosaEstudiantes() {
//        Administrador adm = new Administrador();
////        Permisos c = new Permisos();
//        List<Inscripciones> emp = adm.query("SELECT o FROM Inscripciones as o WHERE o.periodo = 8 AND o.preaprobado = TRUE  ");
//        List<Estudiantes> estu = adm.query("Select o from Estudiantes as o ");
//
//        int i = 1;
//String codigo ="";
//        for (Iterator<Inscripciones> it = emp.iterator(); it.hasNext();) {
//            Inscripciones in = it.next();
//            Estudiantes estudianteEncontrado = existe(estu, in.getApellido(), in.getNombre());
//            
//            if (estudianteEncontrado != null) {
//                System.out.println(i + " " + in.getApellido() + " " + in.getNombre());
//                Representante repreEst = estudianteEncontrado.getRepresentante();
//                codigo += "; update representante set usuario = '"+in.getUsuario()+"' "
//                        + ", clave =  '"+in.getClave()+"' where codigorep = "+repreEst.getCodigorep();
//                i++;
//            } else {
//                Representante repreEst = new Representante(adm.getNuevaClave("Representante", "codigorep"));
//
//                repreEst.setIdentificacionrepre(in.getCedular());
//                repreEst.setNombres(in.getNombrer());
//                repreEst.setApellidos(in.getApellidor());
//                repreEst.setDireccion(in.getDireccionr());
//                repreEst.setTelefono(in.getTelefonor());
//                repreEst.setEmail(in.getMailr());
//
//                repreEst.setTipoidentificacion("C");
//                repreEst.setIdentificacionfactura(in.getCedula());
//                repreEst.setNombrefactura("");
//                repreEst.setDirfactura("");
//                repreEst.setTelfactura("");
//
//                repreEst.setIdentificacionpadre(in.getCedulap());
//                repreEst.setApepadre(in.getApellidopa());
//                repreEst.setNompadre(in.getNombrepa());
//                repreEst.setTelpadre(in.getTelefonopa());
//                repreEst.setDirpadre(in.getDireccionpa());
//                repreEst.setMailpadre(in.getMail());
//                repreEst.setParentesco("");
//                repreEst.setProfesionpadre(in.getTrabajopa());
//                repreEst.setOcupacionpadre(in.getLugarpa());
//
//                repreEst.setIdentificacionmadre(in.getCedulam());
//                repreEst.setApemadre(in.getApellidma());
//                repreEst.setNommadre(in.getNombrema());
//                repreEst.setTelmadre(in.getTelefonoma());
//                repreEst.setDirmadre(in.getDireccionma());
//                repreEst.setMailmadre(in.getMail());
//                repreEst.setProfesionmadre(in.getTrabajoma());
//                repreEst.setOcupacionmadre(in.getLugarma());
//                repreEst.setUsuario(in.getUsuario());
//                repreEst.setClave(in.getClave());
//                repreEst.setEstado(false);
//                adm.guardar(repreEst);
//
//                Estudiantes nuevoEst = new Estudiantes(adm.getNuevaClave("Estudiantes", "codigoest"));
//                nuevoEst.setRepresentante(repreEst);
//                nuevoEst.setCedula(in.getCedula());
//                nuevoEst.setNombre(in.getNombre());
//                nuevoEst.setApellido(in.getApellido());
//                nuevoEst.setDireccion(in.getDireccion());
//                nuevoEst.setBarrio("");
//                nuevoEst.setTelefono(in.getTelefono());
//                nuevoEst.setMail(in.getMail());
//                nuevoEst.setFechanacimiento(in.getFechanacimiento());
//                nuevoEst.setEstado(true);
//                nuevoEst.setAdventista(false);
//                nuevoEst.setTransporte(false);
//                nuevoEst.setNobus("");
//                nuevoEst.setAseguradora("");
//                nuevoEst.setUsuario(null);
//                nuevoEst.setClave(null);
//                nuevoEst.setGenero(in.getGenero());
//
//                nuevoEst.setReligion("");
//
//
//                nuevoEst.setPendientes(false);
//                nuevoEst.setBloquear(false);
//
//                nuevoEst.setVivecon(in.getVivecon());
//                nuevoEst.setHermanos(0);
//                nuevoEst.setLugar(1);
//                nuevoEst.setLugarnacimiento(in.getLugarnacimiento());
//                nuevoEst.setProvincia("");
//                nuevoEst.setCanton("");
//                nuevoEst.setNacionalidad("Ecuatoriana");
//                nuevoEst.setDiscapacidad(in.getDiscapacidad());
//                nuevoEst.setTipodiscapacidad(in.getTdiscapacidad());
//                nuevoEst.setIngpadre(new Double(0));
//                nuevoEst.setIngmadre(new Double(0));
//                nuevoEst.setIngotros(new Double(0));
//                nuevoEst.setCasa("");
//                nuevoEst.setEstadocivil("");
//                nuevoEst.setAgua(true);
//                nuevoEst.setLuz(true);
//                nuevoEst.setSshh(true);
//                nuevoEst.setPozo(false);
//                nuevoEst.setTelefonob(false);
//                nuevoEst.setInternet(false);
//                nuevoEst.setMatriculado(false);
//                nuevoEst.setAplico(false);
//                adm.guardar(nuevoEst);
//
//
//            }
//
//        }
//        System.out.println("CODIGOS: "+codigo);
//    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        Clients.showBusy("Procediendo", true);
//        int val = Messagebox.show("¿Seguro de eliminar, puede causar la pérdida de notas, si ya tiene registrado?", "Seguridad", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION);
//         TODO code application logic here
        Administrador adm = new Administrador();
        //String q = "SELECT o FROM Cursos  as o WHERE o.codigocur IN(425) order by o.secuencia";
        String q = "SELECT o FROM Cursos  as o "
                + " WHERE o.codigocur IN(425,426,427,428,429,430,431,432,433,434,492,497,494, 448,449,450,451,452,435,436,437,438,439,493,419,477,478,479,480,481,482,483,484,485,486,487,420) "
                + "order by o.secuencia, o.paralelo.descripcion";


        List<Cursos> cursosList = adm.query(q);
        secuencial sec = new secuencial();


        Empleados emp = (Empleados) adm.buscarClave(new Integer(44), Empleados.class);
        Global materia = (Global) adm.buscarClave(new Integer(194), Global.class);
        Date fecha = new Date();
        for (Iterator<Cursos> it = cursosList.iterator(); it.hasNext();) {
            Cursos cursos = it.next();
            System.out.println("" + cursos);
            MateriaProfesor mp = new MateriaProfesor();
            mp.setMateria(materia);
            mp.setEmpleado(emp);
            mp.setCantidad(0);
            mp.setCodigomap(adm.getNuevaClave("MateriaProfesor", "codigomap"));
            mp.setCurso(cursos);
            mp.setEscala("AP");
            mp.setFormula("");
            mp.setHoras(200);
            mp.setIngcualitativo(false);
            mp.setMinisterio(true);
            mp.setOpcional(false);
            mp.setOrden(11);
            mp.setSeimprime(true);
            //adm.guardar(mp);
        }

        //GENERO LAS NOTAS
        for (Iterator<Cursos> it = cursosList.iterator(); it.hasNext();) {
            Cursos cursos = it.next();
            System.out.println("********************** " + cursos+ " -------------------------------------------------------------------------------------- ");
            List<Matriculas> matri = adm.query("Select o from Matriculas as o "
                    + " where o.curso.codigocur = '" + cursos.getCodigocur() + "' "
                    + "and o.estado in ('Matriculado') order by o.estudiante.apellido, o.estudiante.nombre ");
            for (Iterator<Matriculas> it1 = matri.iterator(); it1.hasNext();) {
                Matriculas matric = it1.next();
                Notas nota = new Notas(sec.generarClave());
                nota.setMatricula(matric);
                nota.setMateria(materia);
                nota.setFecha(fecha);
                nota.setCuantitativa(false);
                nota.setDisciplina(false);
                nota.setFecha(fecha);
                //busco la nota

                String valorAnadir2  = " round(cast(avg(CAST( nota15 AS DECIMAL(8," + 2 + "))) as decimal(8," + 2 + "))," + 2 + ") ";

                String quNota = "Select " + valorAnadir2 + " from matriculas "
                        + "left join estudiantes on matriculas.estudiante = estudiantes.codigoest   "
                        + "left join notas on matriculas.codigomat = notas.matricula "
                        + "where matriculas.curso = '" + matric.getCurso().getCodigocur() + "'  "
                        + "and matriculas.codigomat = '" + matric.getCodigomat() + "' "
                        + "and notas.seimprime = true "
                        + "and notas.promedia = true "
                        + "and notas.cuantitativa = true "
                        + "and notas.disciplina = false and notas.materia <> 194   and notas.materia != 0  "
                        + "group by notas.matricula  ";
                System.out.println("" + quNota);
                List nativo = adm.queryNativo(quNota);

                Double aprovechamiento = 0.0;
                for (Iterator itna = nativo.iterator(); itna.hasNext();) {
                    Vector vec = (Vector) itna.next();
                    for (int j = 0; j < vec.size(); j++) {
                        Object dos = vec.get(j);
                        try {
                            if (dos.equals(null)) {
                                dos = new BigDecimal(0.0);
                            }
                        } catch (Exception e) {
                            dos = new BigDecimal(0.0);
                        }

                        BigDecimal b = (BigDecimal) dos;
                        aprovechamiento = b.doubleValue();
                    }
                }
                nota.setNota1(aprovechamiento);
                nota.setNota2(aprovechamiento);
                nota.setNota3(aprovechamiento);
                nota.setNota4(aprovechamiento);
                nota.setNota5(aprovechamiento);
                nota.setNota16(aprovechamiento);
                nota.setNota6(aprovechamiento);
                nota.setNota7(aprovechamiento);
                nota.setNota8(aprovechamiento);
                nota.setNota9(aprovechamiento);
                nota.setNota10(aprovechamiento);
                nota.setNota17(aprovechamiento);
                nota.setNota11(aprovechamiento);
                nota.setNota12(0d);
                nota.setNota13(0d);
                nota.setNota14(0d);
                nota.setNota15(aprovechamiento);
                nota.setObservacion("SISCONTROL");
                nota.setOrden(11);
                nota.setPromedia(true);
                nota.setSeimprime(true);
                System.out.println("matricula " + matric+ " aprovechamiento " + aprovechamiento);
                adm.guardar(nota); 
            }
            System.out.println("********** FIN ************ " + cursos+ " -------------------------------------------------------------------------------------- ");
        }

    }
}
