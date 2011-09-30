package jcinform.conexion;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import jcinform.persistencia.Sucursal;
import org.xml.sax.SAXException;

/** Clase abstracta para implementar un objeto Personal que se guarde en un archivo XML. */
public class leerXml {

    public static UsuarioActivo user = new UsuarioActivo();
    public static List sucursales = new ArrayList<Sucursal>();
    public leerXml() {
    }

    public void borrar() {
        inicio();
        File fichero = new File(direccio + NOMBRE_ARCHIVO_XML);
        if (fichero.exists()) {
            fichero.delete();
        }

    }

    public static UsuarioActivo getUser() {
        return user;
    }

    public static void setUser(UsuarioActivo user) {
        leerXml.user = user;
    }
    private static String separador = File.separator;
    private static String direccio = null;

    public static void inicio() {
        ubicacionDirectorio w = new ubicacionDirectorio();

        direccio = w.get() + separador;
        System.out.println("DIRECTORIO UBICACION DEL : " + direccio);
        if (direccio.contains("build")) {
            direccio = direccio.replace(separador + "build", "");
        }
        if (direccio.contains("build")) {
            direccio = direccio.replace("\\build", "");
            direccio = direccio.replace("/build", "");
        }
        direccio = direccio + separador;
    }
    private static final String NOMBRE_ARCHIVO_XML = "empresas.xml";
    public static List leer() {
        inicio();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            System.out.println(""+direccio);
            Document doc = docBuilder.parse(new File(NOMBRE_ARCHIVO_XML));
            doc.getDocumentElement().normalize();
            System.out.println("El elemento raíz es " + doc.getDocumentElement().getNodeName());
            NodeList listaPersonas = doc.getElementsByTagName("empresa");
            int totalPersonas = listaPersonas.getLength();
            System.out.println("Número total de personas : " + totalPersonas);
            
            for (int i = 0; i < listaPersonas.getLength(); i++) {
                Node persona = listaPersonas.item(i);
                if (persona.getNodeType() == Node.ELEMENT_NODE) {
                    Element firstPersonElement = (Element) persona;
                    //----------------------------------------------------------
                    Sucursal s = new Sucursal();
                    
                    NodeList idList = firstPersonElement.getElementsByTagName("id");
                    Element idElement = (Element) idList.item(0);
                    NodeList textIdList = idElement.getChildNodes();
                    System.out.println("" + ((Node) textIdList.item(0)).getNodeValue().trim());
                    s.setCodigo(new Integer(((Node) textIdList.item(0)).getNodeValue().trim()));

                    NodeList baseList = firstPersonElement.getElementsByTagName("base");
                    Element baseElement = (Element) baseList.item(0);
                    NodeList textBaseList = baseElement.getChildNodes();
                    System.out.println("" + ((Node) textBaseList.item(0)).getNodeValue().trim());
                    s.setSerie1((((Node) textBaseList.item(0)).getNodeValue().trim()));

                    NodeList nombreList = firstPersonElement.getElementsByTagName("nombre");
                    Element nombreElement = (Element) nombreList.item(0);
                    NodeList textNombreList = nombreElement.getChildNodes();
                    System.out.println("" + ((Node) textNombreList.item(0)).getNodeValue().trim());
                    s.setDescripcion((((Node) textNombreList.item(0)).getNodeValue().trim()));

                    sucursales.add(s);                    
                }
            }


        } catch (ParserConfigurationException ex) {
            Logger.getLogger(leerXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException ex) {
            Logger.getLogger(leerXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(leerXml.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sucursales;
    }

      
    public static void main(String[] args) {
        // TODO code application logic here
        leer();
        for (Iterator it = sucursales.iterator(); it.hasNext();) {
            Sucursal  object = (Sucursal) it.next();
            System.out.println("SUCURSALES: "+object.getDescripcion());
            
        }
    }
}
