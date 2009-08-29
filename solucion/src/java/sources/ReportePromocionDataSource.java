/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sources;

 
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 *
 * @author GEOVANNY
 */
public class ReportePromocionDataSource implements JRDataSource{
    private Iterator itrAlumnos;
     private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public ReportePromocionDataSource(List lista) {
        super();
        this.itrNodos = lista.iterator();
    }

    public boolean next() throws JRException {
        itrAlumnos = itrNodos;
        valorAtual = itrAlumnos.hasNext() ? itrAlumnos.next() : null;
        irParaProximoAlumno = (valorAtual != null);
        return irParaProximoAlumno;
    }
    /*
     * (non-Javadoc)
     *
     * @see net.sf.jasperreports.engine.JRDataSource#getFieldValue(net.sf.jasperreports.engine.JRField)
     */
    public Object getFieldValue(JRField campo) throws JRException {
        Object valor = null;
        NotasClaseTemp  nodo = (NotasClaseTemp) valorAtual;
        NumerosaLetras num = new NumerosaLetras();
        String fieldName = campo.getName();
try{

        if ("textoParametro".equals(fieldName)) {
            valor = nodo.getCabeceraTexto();
        }else if ("textPie".equals(fieldName)) {
            if(nodo.estado)
                valor = nodo.getPieTextoAprobado();
            else
                valor = nodo.getPieTextoReprobado();
        }else if ("asignatura".equals(fieldName)) {
            valor = nodo.getMateria();
        }else if ("letras".equals(fieldName)) {
            if(nodo.getMateriaProfesor().getCuantitativa()==true)
                valor = num.numeros((Double) nodo.getNota())+"";
            else
                valor = null;

        }else if ("matricula".equals(fieldName)) {
           valor = nodo.getMatricula().getCodigomat();
        }else if ("observacion".equals(fieldName)) {
           valor = nodo.getEstadoMateria();
        }else if ("promedio".equals(fieldName)) {
            if(nodo.getMateriaProfesor().getCuantitativa()==true)
                valor =  java.lang.Math.round((Double)nodo.getNota());
            else
                valor = null;
        }else if ("aprovechamiento".equals(fieldName)) {
                valor = nodo.getAprovechamiento();
        }else if ("disciplina".equals(fieldName)) {
                valor = nodo.getDisciplina();
        }else if ("letrasDisciplina".equals(fieldName)) {
                valor = num.numeros(nodo.getDisciplina());
        }else if ("letrasAprovechamiento".equals(fieldName)) {
            String formado = num.numerosDecimales(nodo.getAprovechamiento()).toUpperCase();
            try{
            String valor1 = nodo.getAprovechamiento().toString();
            int indice = valor1.indexOf(".");
            String cortado = valor1.substring(indice+1);
            String cientos = " "+num.GetHundreds(cortado).toUpperCase();
            if(!cientos.trim().equals(""))
            valor = formado+" PUNTOS CON "+cientos +" CENTESIMAS";
            else
                valor = formado+" PUNTOS ";
            }catch(Exception e){
                valor = formado;
                System.out.println("erroren"+e);
            }
        }else if ("sello".equals(fieldName)) {
            try{
                byte[] bImage = nodo.getMatricula().getCurso().getPeriodo().getInstitucion().getEscudo();
                if(bImage!=null){
                    InputStream is = new ByteArrayInputStream(bImage);
                    valor = is;
                }else{
                }
            }catch(Exception ex){
                System.out.println("Error en foto:"+ex);
            }
        }

}catch(Exception e){
    System.out.println("ERROR EN ITERAR REPORTE"+e);
}

        return valor;
    }
}
