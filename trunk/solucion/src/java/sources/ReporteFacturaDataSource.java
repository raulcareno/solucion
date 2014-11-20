/*ura
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package sources;
 
 
import java.util.Iterator;
import java.util.List;
import jcinform.persistencia.Detallefactura;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
//import persistencia.Detallefactura;

/**
 *
 * @author Francisco
 */
public class ReporteFacturaDataSource implements JRDataSource{

   private Iterator itrAlumnos;
   private Iterator itrNodos;
   private Object valorAtual;
   private boolean irParaProximoAlumno = true;

    public ReporteFacturaDataSource(List lista) {
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
        Detallefactura nodo = (Detallefactura) valorAtual;

        if ("item".equals(campo.getName())) {
            valor = nodo.getDetCantidad();
        }else if ("estudiante".equals(campo.getName())) {
            valor = nodo.getFacNumero().getMatriCod().getEstudiante().getApellido()+" "+nodo.getFacNumero().getMatriCod().getEstudiante().getNombre();
        }else if ("rubro".equals(campo.getName())) {

             if(nodo.getDetMes().equals(1)){valor ="ENERO";}
            else if(nodo.getDetMes().equals(2)){valor ="FEBRERO";}
            else if(nodo.getDetMes().equals(3)){valor ="MARZO";}
            else if(nodo.getDetMes().equals(4)){valor ="ABRIL";
            }else if(nodo.getDetMes().equals(5)){valor ="MAYO";}
            else if(nodo.getDetMes().equals(6)){valor ="JUNIO";
            }else if(nodo.getDetMes().equals(7)){valor ="JULIO";
            }else if(nodo.getDetMes().equals(8)){valor ="AGOSTO";
            }else if(nodo.getDetMes().equals(9)){valor ="SEPTIEMBRE";
            }else if(nodo.getDetMes().equals(10)){valor ="OCTUBRE";
            }else if(nodo.getDetMes().equals(11)){valor ="NOVIEMBRE";
            }else if(nodo.getDetMes().equals(12)){valor ="DICIEMBRE";
            }else{valor = "";    }
            valor = nodo.getProducto().getDescripcion()+ " - "+ valor;
        }else if ("valor".equals(campo.getName())) {
            valor = nodo.getDetTotal();
        }else if ("descuento".equals(campo.getName())) {
            valor = nodo.getDescuento();
        }else if ("numero".equals(campo.getName())) {
            valor = nodo.getFacNumero().getFacNumero().substring(3);
        }
        else if ("mes".equals(campo.getName())) {
            if(nodo.getDetMes().equals(1)){valor ="ENERO";}
            else if(nodo.getDetMes().equals(2)){valor ="FEBRERO";}
            else if(nodo.getDetMes().equals(3)){valor ="MARZO";}
            else if(nodo.getDetMes().equals(4)){valor ="ABRIL";
            }else if(nodo.getDetMes().equals(5)){valor ="MAYO";}
            else if(nodo.getDetMes().equals(6)){valor ="JUNIO";
            }else if(nodo.getDetMes().equals(7)){valor ="JULIO";
            }else if(nodo.getDetMes().equals(8)){valor ="AGOSTO";
            }else if(nodo.getDetMes().equals(9)){valor ="SEPTIEMBRE";
            }else if(nodo.getDetMes().equals(10)){valor ="OCTUBRE";
            }else if(nodo.getDetMes().equals(11)){valor ="NOVIEMBRE";
            }else if(nodo.getDetMes().equals(12)){valor ="DICIEMBRE";
            }else{valor = "";    }

        }

 /* Fijar que el arraylist de disciplinas es enbuelto en un JRBeanCollectionDataSource */
//   else if ("ListaDisciplinas".equals(campo.getName())) {
//   valor = new JRBeanCollectionDataSource(
//   			aluno.getDisciplinas());
//   }
        return valor;
    }
}

