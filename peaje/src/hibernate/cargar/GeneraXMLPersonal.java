package hibernate.cargar;

import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xml.serialize.OutputFormat;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/** Clase abstracta para implementar un objeto Personal que se guarde en un archivo XML. */
public class GeneraXMLPersonal {

    public GeneraXMLPersonal() {
    }

    public void borrar() {
        inicio();
        File fichero = new File(direccio + NOMBRE_ARCHIVO_XML);
        if (fichero.exists()) {
            fichero.delete();
        }

    }
    private static String separador = File.separator;
    private static String direccio = null;

    public void inicio() {
        WorkingDirectory w = new WorkingDirectory();

        direccio = w.get() + separador;
        if (direccio.contains("build")) {
            direccio = direccio.replace(separador + "build", "");
        }
        if (direccio.contains("build")) {
            direccio = direccio.replace("\\build", "");
            direccio = direccio.replace("/build", "");
        }
        direccio = direccio + separador;
    }
    // Etiquetas XML
    private static final String TAG_PERSONAL = "JCINFORM";
    private static final String USUARIO = "USUARIO";
    private static final String CLAVE = "CLAVE";
    private static final String IP = "IP";
    private static final String PUERTO = "PUERTO";
    private static final String XML_VERSION = "1.0";
    private static final String XML_ENCODING = "ISO-8859-1";
    private static final String JAVA_ENCODING = "8859_1";
    private static final String NOMBRE_ARCHIVO_XML = "KDJFASD5F4AS5D2.xml";
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
//            xmlDoc = docBuilder.parse(NOMBRE_ARCHIVO_XML);
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }

        // crea un elemento personal
        personal = xmlDoc.createElement(TAG_PERSONAL);

        // agrega el elemento principal
        xmlDoc.appendChild(personal);
    }

    /** M�todo para generar documento XML usando DOM */
    public void llenarEstructuraDocumentoXMLEmpleado(BeanUsuario beanEmpleado) {
        Element empleado;
        Element item;

        // crea un elemento empleado y agrega al elemento personal
//        empleado = xmlDoc.createElement(TAG_EMPLEADO);
//        empleado.setAttribute(IDPRINCIPAL, beanEmpleado.getDNI());
//        personal.appendChild(empleado);

        //agrega el elemento Nombre dentro del elemento Empleado
        item = xmlDoc.createElement(USUARIO);
        item.appendChild(xmlDoc.createTextNode(beanEmpleado.getUsuario()));
        personal.appendChild(item);

        //agrega el elemento Fecha de Nacimiento dentro del elemento Empleado
        item = xmlDoc.createElement(CLAVE);
        item.appendChild(xmlDoc.createTextNode(beanEmpleado.getClave()));
        personal.appendChild(item);

        //agrega el elemento Salario dentro del elemento Empleado
        item = xmlDoc.createElement(IP);
        item.appendChild(xmlDoc.createTextNode(beanEmpleado.getIp()));
        personal.appendChild(item);

        //agrega el elemento Edad dentro del elemento Empleado
        item = xmlDoc.createElement(PUERTO);
        item.appendChild(xmlDoc.createTextNode(beanEmpleado.getPuerto()));
        personal.appendChild(item);
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
        GeneraXMLPersonal pXml = new GeneraXMLPersonal();
        pXml.inicio();
        String textoXML = pXml.leerXML();
        if (textoXML == null) {
            pXml.generaDocumentoXMLPersonal();
            BeanUsuario beanEmpleado = new BeanUsuario();
            //Establecemos los valores de atributos de Empleado
            beanEmpleado.setDNI("80114918");
            beanEmpleado.setUsuario("root");
            beanEmpleado.setClave("clavesss");
            beanEmpleado.setIp("localhost");
            beanEmpleado.setPuerto("puerto");

            //Generamos documento XML para los valores anteriores
            pXml.llenarEstructuraDocumentoXMLEmpleado(beanEmpleado);
            //obtenemos el documento XML en cadena de texto
            textoXML = pXml.obtenerTextoXML();
            //grabamos en archivo el documento XML
            pXml.grabaDocumentoXML(textoXML);
        }
//
        // mostramos en pantalla el documento XML grabado
        System.out.println(textoXML);
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
    public void escribir() {
        GeneraXMLPersonal pXml = new GeneraXMLPersonal();
        pXml.generaDocumentoXMLPersonal();
        BeanUsuario beanEmpleado = new BeanUsuario();
        //Establecemos los valores de atributos de Empleado
        beanEmpleado.setDNI("80114918");
        beanEmpleado.setUsuario("root");
        beanEmpleado.setClave("clavesss");
        beanEmpleado.setIp("localhost");
        beanEmpleado.setPuerto("puerto");

        //Generamos documento XML para los valores anteriores
        pXml.llenarEstructuraDocumentoXMLEmpleado(beanEmpleado);
        //obtenemos el documento XML en cadena de texto
        String textoXML = pXml.obtenerTextoXML();
        //grabamos en archivo el documento XML
        pXml.grabaDocumentoXML(textoXML);
        leerXML();
    }

    public String leerXML() {
        UsuarioActivo user = new UsuarioActivo();
//        public void leerDocumento(){
        System.out.println("" + direccio + NOMBRE_ARCHIVO_XML);
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
            System.out.println("Root element of the doc is "
                    + doc.getDocumentElement().getNodeName());


            NodeList listOfPersons = doc.getElementsByTagName("JCINFORM");
            int totalPersons = listOfPersons.getLength();
            System.out.println("Total no of people : " + totalPersons);

            for (int s = 0; s < listOfPersons.getLength(); s++) {


                Node firstPersonNode = listOfPersons.item(s);
                if (firstPersonNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element firstPersonElement = (Element) firstPersonNode;

                    //----------------------------------------------------------
                    NodeList usuarioList = firstPersonElement.getElementsByTagName("USUARIO");
                    Element usuarioElement = (Element) usuarioList.item(0);
                    NodeList textFNList = usuarioElement.getChildNodes();
                    user.setNombre(((Node) textFNList.item(0)).getNodeValue().trim());
                    System.out.println("USUARIO: " + ((Node) textFNList.item(0)).getNodeValue().trim());
                    //----------------------------------------------------------
                    NodeList lastNameList = firstPersonElement.getElementsByTagName("CLAVE");
                    Element lastNameElement = (Element) lastNameList.item(0);
                    NodeList claveList = lastNameElement.getChildNodes();
                    System.out.println("CLAVE: " + ((Node) claveList.item(0)).getNodeValue().trim());
                    user.setContrasenia(((Node) claveList.item(0)).getNodeValue().trim());
                    //----------------------------------------------------------
                    NodeList ageList = firstPersonElement.getElementsByTagName("IP");
                    Element ageElement = (Element) ageList.item(0);

                    NodeList textAgeList = ageElement.getChildNodes();
                    System.out.println("IP: " + ((Node) textAgeList.item(0)).getNodeValue().trim());
                    user.setIp(((Node) textAgeList.item(0)).getNodeValue().trim());
                    //----------------------------------------------------------
                    //----
                    NodeList puertoList = firstPersonElement.getElementsByTagName("PUERTO");
                    Element puertoElement = (Element) puertoList.item(0);

                    NodeList puertoAgeList = puertoElement.getChildNodes();
                    System.out.println("PUERTO : " + ((Node) puertoAgeList.item(0)).getNodeValue().trim());
                    user.setPuerto(((Node) puertoAgeList.item(0)).getNodeValue().trim());

                    //------


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
        return "listo";
    }//end of main
}
