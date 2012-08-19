package jcinform.Administrador;

 
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import jcinform.persistencia.Empresa;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xml.serialize.OutputFormat;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
 

/** Clase abstracta para implementar un objeto Personal que se guarde en un archivo XML. */
public class XMLEmpresa {
public static UsuarioActivo user = new UsuarioActivo();
    public XMLEmpresa() {
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
        XMLEmpresa.user = user;
    }
    
    private static String separador = File.separator;
    private static String direccio = UsuarioActivo.getUbicacionDirectorio();

    public void inicio() {
        direccio = UsuarioActivo.getUbicacionDirectorio();
    }
    // Etiquetas XML
    private static final String TAG_PERSONAL = "JCINFORMEMPRESA";
 
    private static final String XML_VERSION = "1.0";
    private static final String XML_ENCODING = "ISO-8859-1";
    private static final String JAVA_ENCODING = "8859_1";
    private static final String NOMBRE_ARCHIVO_XML = "config.xml";
    private static final String PUERTOPRINCIPAL = "PUERTOPRINCIPAL";
    private static final String TIPOTERMINAL = "TIPOTERMINAL";
    private static final String VENTANILLA = "VENTANILLA";
    private static final String IP = "IP";
     
     
     


    // Variables
    private Document xmlDoc = null;
    private Element personal = null;

    /** M�todo para retornar el documento XML en cadena de texto */
    public String obtenerTextoXML() {

        return obtenerFuncionTextoXML();

    }

    /** M�todo para generar el elemento Personal del documento XML usando DOM */
    public void generaDocumentoXMLPersonal() {
        try {
            // Crea un documento XML
            DocumentBuilderFactory dbFactory = DocumentBuilderFactoryImpl.newInstance();
            DocumentBuilder docBuilder = dbFactory.newDocumentBuilder();
            xmlDoc = docBuilder.newDocument();
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        // crea un elemento personal
        personal = xmlDoc.createElement(TAG_PERSONAL);
        // agrega el elemento principal
        xmlDoc.appendChild(personal);
    }

    /** M�todo para generar documento XML usando DOM */
    public void llenarEstructuraDocumentoXMLEmpleado(EmpresaPuertos beanEmpresa) {
       
        Element item;
        //agrega el elemento Nombre dentro del elemento Empleado
        item = xmlDoc.createElement(PUERTOPRINCIPAL);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerto()));
        personal.appendChild(item);
        
        item = xmlDoc.createElement(TIPOTERMINAL);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getTipoterminal()+""));
        personal.appendChild(item);
        
        item = xmlDoc.createElement(VENTANILLA);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getVentanilla()+""));
        personal.appendChild(item);
        
        
        item = xmlDoc.createElement(IP);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getIp()+""));
        personal.appendChild(item);

        //agrega el elemento Fecha de Nacimiento dentro del elemento Empleado
        
    }

    // genera el objeto de documento XML en una cadena de texto
    private String obtenerFuncionTextoXML() {
        StringWriter strWriter = null;
        XMLSerializer xmlSerializer = null;
        OutputFormat outFormat = null;
        try {
            xmlSerializer = new XMLSerializer();
            strWriter = new StringWriter();
            outFormat = new OutputFormat();

            // estableciendo el formato
            outFormat.setEncoding(XML_ENCODING);
            outFormat.setVersion(XML_VERSION);
            outFormat.setIndenting(true);
            outFormat.setIndent(4);

            // Define una escritura
            xmlSerializer.setOutputCharStream(strWriter);

            // Aplicando el formato establecido
            xmlSerializer.setOutputFormat(outFormat);
            // Serializando el documento XML
            xmlSerializer.serialize(xmlDoc);
            strWriter.close();
        } catch (IOException ioEx) {
            System.out.println("Error : " + ioEx);
        }
        return strWriter.toString();
    }

    public void grabaDocumentoXML(String textoXML) {
        try {
            OutputStream fout = new FileOutputStream(direccio + NOMBRE_ARCHIVO_XML);
            OutputStream bout = new BufferedOutputStream(fout);
            OutputStreamWriter out = new OutputStreamWriter(bout, JAVA_ENCODING);
            out.write(textoXML);
            out.flush();
            out.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("La Maquina Virtual no soporta la codificacion Latin-1.");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
    }

    public void comprobar() {
//        XMLEmpresa pXml = new XMLEmpresa();
//        pXml.inicio();
//        String textoXML = pXml.leerXML();
//        if (textoXML == null) {
//            pXml.generaDocumentoXMLPersonal();
//            EmpresaPuertos beanEmpleado = new EmpresaPuertos();
//            //Establecemos los valores de atributos de Empleado
//            beanEmpleado.setDNI("80114918");
//            beanEmpleado.setUsuario("root");
//            beanEmpleado.setClave("clavesss");
//            beanEmpleado.setIp("localhost");
//            beanEmpleado.setPuerto("puerto");
//            beanEmpleado.setIn("in");
//            beanEmpleado.setOut("out");

            //Generamos documento XML para los valores anteriores
//            pXml.llenarEstructuraDocumentoXMLEmpleado(beanEmpleado);
//            //obtenemos el documento XML en cadena de texto
//            textoXML = pXml.obtenerTextoXML();
//            //grabamos en archivo el documento XML
//            pXml.grabaDocumentoXML(textoXML);
//        }
////
//        // mostramos en pantalla el documento XML grabado
//        System.out.println(textoXML);
    }

//    public static void main(String argv[]) {
//        GeneraXMLPersonal pXml = new GeneraXMLPersonal();
//            pXml.inicio();
//        String textoXML = pXml.leerXML();
//        if (textoXML == null) {
//
//        }
////
//        // mostramos en pantalla el documento XML grabado
//        System.out.println(textoXML);
//    }
    public void escribir(Empresa emp) {
        XMLEmpresa pXml = new XMLEmpresa();
        pXml.generaDocumentoXMLPersonal();
        EmpresaPuertos beanEmpleado = new EmpresaPuertos();
        //Establecemos los valores de atributos de Empleado
        beanEmpleado.setDNI("80114918");
         
//        beanEmpleado.setPuerto(emp.getPuerto());
//        beanEmpleado.setTipoterminal(emp.getTipoterminal());
//        beanEmpleado.setVentanilla(emp.getVentanilla());
//        beanEmpleado.setIp(emp.getIp());
           
        //Generamos documento XML para los valores anteriores
        pXml.llenarEstructuraDocumentoXMLEmpleado(beanEmpleado);
        //obtenemos el documento XML en cadena de texto
        String textoXML = pXml.obtenerTextoXML();
        //grabamos en archivo el documento XML
        pXml.grabaDocumentoXML(textoXML);
//        leerXML();
        
    }

    public EmpresaPuertosStatic leerXML() {
        EmpresaPuertosStatic user = new EmpresaPuertosStatic();
//        public void leerDocumento(){
//        System.out.println("" + direccio + NOMBRE_ARCHIVO_XML);
        try {
            File fichero = new File(direccio + NOMBRE_ARCHIVO_XML);
            if (!fichero.exists()) {
                return null;
            }

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(new File(direccio + NOMBRE_ARCHIVO_XML));

            // normalize text representation
            doc.getDocumentElement().normalize();
//            System.out.println("Root element of the doc is "
//                    + doc.getDocumentElement().getNodeName());


            NodeList listOfPersons = doc.getElementsByTagName("JCINFORMEMPRESA");
//            int totalPersons = listOfPersons.getLength();
//            System.out.println("Total no of people : " + totalPersons);

            for (int s = 0; s < listOfPersons.getLength(); s++) {


                Node firstPersonNode = listOfPersons.item(s);
                if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element firstPersonElement = (Element) firstPersonNode;

 
                   
                      //------
                    try {
                    NodeList usuarioList = firstPersonElement.getElementsByTagName("PUERTOPRINCIPAL");
                    Element usuarioElement = (Element) usuarioList.item(0);
                    NodeList textFNList = usuarioElement.getChildNodes();
                    user.setPuerto(((Node) textFNList.item(0)).getNodeValue().trim());
                     } catch (Exception parserConfigurationException) {
//                        System.out.println("puerto principal 1");
                    }
                    
                      //------
                    try {
                        NodeList usuarioList = firstPersonElement.getElementsByTagName("TIPOTERMINAL");
                        Element usuarioElement = (Element) usuarioList.item(0);
                        NodeList textFNList = usuarioElement.getChildNodes();
                        user.setTipoterminal(new Integer(((Node) textFNList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
//                        System.out.println("puerto principal 1");
                        user.setTipoterminal(1);
                    }
                    
                    //------
                    try {
                        NodeList usuarioList = firstPersonElement.getElementsByTagName("VENTANILLA");
                        Element usuarioElement = (Element) usuarioList.item(0);
                        NodeList textFNList = usuarioElement.getChildNodes();
                        user.setVentanilla(((Node) textFNList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
//                        System.out.println("puerto principal 1");
                        user.setVentanilla("1");
                    }
                    
                    //------
                    try {
                        NodeList usuarioList = firstPersonElement.getElementsByTagName("IP");
                        Element usuarioElement = (Element) usuarioList.item(0);
                        NodeList textFNList = usuarioElement.getChildNodes();
                        user.setIp(((Node) textFNList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
//                        System.out.println("puerto principal 1");
                        user.setIp("localhost");
                    }
                    
                }//end of if clause


            }//end of for loop with s var


        } catch (SAXParseException err) {
            System.out.println("** Parsing error" + ", line "
                    + err.getLineNumber() + ", uri " + err.getSystemId());
            System.out.println(" " + err.getMessage());

        } catch (SAXException e) {
            Exception x = e.getException();
            ((x == null) ? e : x).printStackTrace();

        } catch (Throwable t) {
            t.printStackTrace();
        }
        //System.exit (0);
        return user;
    }//end of main
}
