package xml;

import hibernate.Empresa;
import hibernate.cargar.*;
import java.io.*;
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.apache.xml.serialize.XMLSerializer;
import org.apache.xml.serialize.OutputFormat;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import peaje.formas.EmpresaPuertos;
import peaje.formas.EmpresaPuertosStatic;

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
    private static final String TAG_PERSONAL = "JCINFORMEMPRESA";
 
    private static final String XML_VERSION = "1.0";
    private static final String XML_ENCODING = "ISO-8859-1";
    private static final String JAVA_ENCODING = "8859_1";
    private static final String NOMBRE_ARCHIVO_XML = "config.xml";
    private static final String PUERTOPRINCIPAL = "PUERTOPRINCIPAL";
    private static final String LED = "LED";
    private static final String BARRA1 = "BARRA1";
    private static final String BARRA2 = "BARRA2";
    private static final String SALE1 = "SALE1";
    private static final String SALE2 = "SALE2";
    private static final String ENTRADA1 = "ENTRADA1";
    private static final String ENTRADA2 = "ENTRADA2";
    private static final String ENTRADA3 = "ENTRADA3";
    private static final String ENTRADA4 = "ENTRADA4";
    private static final String ENTRADA5 = "ENTRADA5";
    private static final String ENTRADA6 = "ENTRADA6";
    private static final String ENTRADA7 = "ENTRADA7";
    
    private static final String SALIDA1 = "SALIDA1";
    private static final String SALIDA2 = "SALIDA2";
    private static final String SALIDA3 = "SALIDA3";
    private static final String SALIDA4 = "SALIDA4";
    private static final String SALIDA5 = "SALIDA5";
    private static final String SALIDA6 = "SALIDA6";
    private static final String SALIDA7 = "SALIDA7";

    private static final String ACTIVA1 = "ACTIVA1";
    private static final String ACTIVA2 = "ACTIVA2";
    private static final String ACTIVA3 = "ACTIVA3";
    private static final String ACTIVA4 = "ACTIVA4";
    private static final String ACTIVA5 = "ACTIVA5";
    private static final String ACTIVA6 = "ACTIVA6";
    private static final String ACTIVA7 = "ACTIVA7";

     private static final String PUERTA1 = "PUERTA1";
    private static final String PUERTA2 = "PUERTA2";
    private static final String PUERTA3 = "PUERTA3";
    private static final String PUERTA4 = "PUERTA4";
    private static final String PUERTA5 = "PUERTA5";
    private static final String PUERTA6 = "PUERTA6";
    private static final String PUERTA7 = "PUERTA7";
    private static final String WEBCAM = "WEBCAM";


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

        //agrega el elemento Fecha de Nacimiento dentro del elemento Empleado
        item = xmlDoc.createElement(LED);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getLed()));
        personal.appendChild(item);

        //agrega el elemento IP  dentro del elemento 
        item = xmlDoc.createElement(BARRA1);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getBarras()));
        personal.appendChild(item);

        //agrega el elemento Puerto dentro del elemento
        item = xmlDoc.createElement(BARRA2);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getBarras2()));
        personal.appendChild(item);

        //agrega el elemento Edad dentro del elemento Empleado
        item = xmlDoc.createElement(SALE1);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getSale()));
        personal.appendChild(item);

        item = xmlDoc.createElement(SALE2);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getSale2()));
        personal.appendChild(item);

        item = xmlDoc.createElement(ENTRADA1);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerto1()));
        personal.appendChild(item);
        item = xmlDoc.createElement(ENTRADA2);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerto2()));
        personal.appendChild(item);
        item = xmlDoc.createElement(ENTRADA3);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerto3()));
        personal.appendChild(item);
        item = xmlDoc.createElement(ENTRADA4);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerto4()));
        personal.appendChild(item);
        item = xmlDoc.createElement(ENTRADA5);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerto5()));
        personal.appendChild(item);
        item = xmlDoc.createElement(ENTRADA6);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerto6()));
        personal.appendChild(item);
        item = xmlDoc.createElement(ENTRADA7);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerto7()));
        personal.appendChild(item);

         item = xmlDoc.createElement(SALIDA1);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getSalida1()));
        personal.appendChild(item);
        item = xmlDoc.createElement(SALIDA2);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getSalida2()));
        personal.appendChild(item);
        item = xmlDoc.createElement(SALIDA3);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getSalida3()));
        personal.appendChild(item);
        item = xmlDoc.createElement(SALIDA4);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getSalida4()));
        personal.appendChild(item);
        item = xmlDoc.createElement(SALIDA5);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getSalida5()));
        personal.appendChild(item);
        item = xmlDoc.createElement(SALIDA6);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getSalida6()));
        personal.appendChild(item);
        item = xmlDoc.createElement(SALIDA7);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getSalida7()));
        personal.appendChild(item);

         item = xmlDoc.createElement(PUERTA1);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerta1()));
        personal.appendChild(item);
        item = xmlDoc.createElement(PUERTA2);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerta2()));
        personal.appendChild(item);
        item = xmlDoc.createElement(PUERTA3);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerta3()));
        personal.appendChild(item);
        item = xmlDoc.createElement(PUERTA4);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerta4()));
        personal.appendChild(item);
        item = xmlDoc.createElement(PUERTA5);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerta5()));
        personal.appendChild(item);
        item = xmlDoc.createElement(PUERTA6);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerta6()));
        personal.appendChild(item);
        item = xmlDoc.createElement(PUERTA7);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuerta7()));
        personal.appendChild(item);

         item = xmlDoc.createElement(ACTIVA1);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva1()+""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ACTIVA2);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva2()+""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ACTIVA3);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva3()+""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ACTIVA4);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva4()+""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ACTIVA5);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva5()+""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ACTIVA6);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva6()+""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ACTIVA7);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva7()+""));
        personal.appendChild(item);
        item = xmlDoc.createElement(WEBCAM);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getWebcam()+""));
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
        beanEmpleado.setLed(""+emp.getLed());
        beanEmpleado.setPuerto(emp.getPuerto());
        beanEmpleado.setBarras(emp.getBarras());
        beanEmpleado.setBarras2(emp.getBarras2());
        beanEmpleado.setSale(emp.getSale());
        beanEmpleado.setSale2(emp.getSale2());
        beanEmpleado.setSalida1(emp.getSalida1());
        beanEmpleado.setSalida2(emp.getSalida2());
        beanEmpleado.setSalida3(emp.getSalida3());
        beanEmpleado.setSalida4(emp.getSalida4());
        beanEmpleado.setSalida5(emp.getSalida5());
        beanEmpleado.setSalida6(emp.getSalida6());
        beanEmpleado.setSalida7(emp.getSalida7());
        beanEmpleado.setWebcam(emp.getWebcam());

        beanEmpleado.setActiva1(emp.getActiva1());
        beanEmpleado.setActiva2(emp.getActiva2());
        beanEmpleado.setActiva3(emp.getActiva3());
        beanEmpleado.setActiva4(emp.getActiva4());
        beanEmpleado.setActiva5(emp.getActiva5());
        beanEmpleado.setActiva6(emp.getActiva6());
        beanEmpleado.setActiva7(emp.getActiva7());
        
        beanEmpleado.setPuerto1(emp.getPuerto1());
        beanEmpleado.setPuerto2(emp.getPuerto2());
        beanEmpleado.setPuerto3(emp.getPuerto3());
        beanEmpleado.setPuerto4(emp.getPuerto4());
        beanEmpleado.setPuerto5(emp.getPuerto5());
        beanEmpleado.setPuerto6(emp.getPuerto6());
        beanEmpleado.setPuerto7(emp.getPuerto7());


   beanEmpleado.setPuerta1(emp.getPuerta1());
        beanEmpleado.setPuerta2(emp.getPuerta2());
        beanEmpleado.setPuerta3(emp.getPuerta3());
        beanEmpleado.setPuerta4(emp.getPuerta4());
        beanEmpleado.setPuerta5(emp.getPuerta5());
        beanEmpleado.setPuerta6(emp.getPuerta6());
        beanEmpleado.setPuerta7(emp.getPuerta7());
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
                        System.out.println("puerto principal 1");
                    }
                      //------
                    try {
                    NodeList lastNameList = firstPersonElement.getElementsByTagName("LED");
                    Element lastNameElement = (Element) lastNameList.item(0);
                    NodeList claveList = lastNameElement.getChildNodes();
                    user.setLed(((Node) claveList.item(0)).getNodeValue().trim());
                     } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList ageList = firstPersonElement.getElementsByTagName("BARRA1");
                    Element ageElement = (Element) ageList.item(0);
                    NodeList textAgeList = ageElement.getChildNodes();
                    user.setBarras(((Node) textAgeList.item(0)).getNodeValue().trim());
                     } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList puertoList = firstPersonElement.getElementsByTagName("BARRA2");
                    Element puertoElement = (Element) puertoList.item(0);
                    NodeList puertoAgeList = puertoElement.getChildNodes();
                    user.setBarras2(((Node) puertoAgeList.item(0)).getNodeValue().trim());
                       } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList inList = firstPersonElement.getElementsByTagName("SALE1");
                    Element inElement = (Element) inList.item(0);
                    NodeList inAgeList = inElement.getChildNodes();
                    user.setSale(((Node) inAgeList.item(0)).getNodeValue().trim());
  } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList sale2 = firstPersonElement.getElementsByTagName("SALE2");
                    Element sale2Elm = (Element) sale2.item(0);
                    NodeList saleinAgeList = sale2Elm.getChildNodes();
                    user.setSale2(((Node) saleinAgeList.item(0)).getNodeValue().trim());
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList ENTRADA1outList = firstPersonElement.getElementsByTagName("ENTRADA1");
                    Element ENTRADA1outElement = (Element) ENTRADA1outList.item(0);
                    NodeList ENTRADA1outAgeList = ENTRADA1outElement.getChildNodes();
                    user.setPuerto1(((Node) ENTRADA1outAgeList.item(0)).getNodeValue().trim());
                       } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList ENTRADA2outList = firstPersonElement.getElementsByTagName("ENTRADA2");
                    Element ENTRADA2outElement = (Element) ENTRADA2outList.item(0);
                    NodeList ENTRADA2outAgeList = ENTRADA2outElement.getChildNodes();
                    user.setPuerto2(((Node) ENTRADA2outAgeList.item(0)).getNodeValue().trim());
                     } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList ENTRADA3outList = firstPersonElement.getElementsByTagName("ENTRADA3");
                    Element ENTRADA3outElement = (Element) ENTRADA3outList.item(0);
                    NodeList ENTRADA3outAgeList = ENTRADA3outElement.getChildNodes();
                    user.setPuerto3(((Node) ENTRADA3outAgeList.item(0)).getNodeValue().trim());
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList ENTRADA4outList = firstPersonElement.getElementsByTagName("ENTRADA4");
                    Element ENTRADA4outElement = (Element) ENTRADA4outList.item(0);
                    NodeList ENTRADA4outAgeList = ENTRADA4outElement.getChildNodes();
                    user.setPuerto4(((Node) ENTRADA4outAgeList.item(0)).getNodeValue().trim());
                     } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList ENTRADA5outList = firstPersonElement.getElementsByTagName("ENTRADA5");
                    Element ENTRADA5outElement = (Element) ENTRADA5outList.item(0);
                    NodeList ENTRADA5outAgeList = ENTRADA5outElement.getChildNodes();
                    user.setPuerto5(((Node) ENTRADA5outAgeList.item(0)).getNodeValue().trim());
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList ENTRADA6outList = firstPersonElement.getElementsByTagName("ENTRADA6");
                    Element ENTRADA6outElement = (Element) ENTRADA6outList.item(0);
                    NodeList ENTRADA6outAgeList = ENTRADA6outElement.getChildNodes();
                    user.setPuerto6(((Node) ENTRADA6outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList ENTRADA7outList = firstPersonElement.getElementsByTagName("ENTRADA7");
                    Element ENTRADA7outElement = (Element) ENTRADA7outList.item(0);
                    NodeList ENTRADA7outAgeList = ENTRADA7outElement.getChildNodes();
                    user.setPuerto7(((Node) ENTRADA7outAgeList.item(0)).getNodeValue().trim());
 //------
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList SALIDA1outList = firstPersonElement.getElementsByTagName("SALIDA1");
                    Element SALIDA1outElement = (Element) SALIDA1outList.item(0);
                    NodeList SALIDA1outAgeList = SALIDA1outElement.getChildNodes();
                    user.setSalida1(((Node) SALIDA1outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                          NodeList SALIDA2outList = firstPersonElement.getElementsByTagName("SALIDA2");
                    Element SALIDA2outElement = (Element) SALIDA2outList.item(0);
                    NodeList SALIDA2outAgeList = SALIDA2outElement.getChildNodes();
                    user.setSalida2(((Node) SALIDA2outAgeList.item(0)).getNodeValue().trim());
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList SALIDA3outList = firstPersonElement.getElementsByTagName("SALIDA3");
                    Element SALIDA3outElement = (Element) SALIDA3outList.item(0);
                    NodeList SALIDA3outAgeList = SALIDA3outElement.getChildNodes();
                    user.setSalida3(((Node) SALIDA3outAgeList.item(0)).getNodeValue().trim());
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                      //------
                    NodeList SALIDA4outList = firstPersonElement.getElementsByTagName("SALIDA4");
                    Element SALIDA4outElement = (Element) SALIDA4outList.item(0);
                    NodeList SALIDA4outAgeList = SALIDA4outElement.getChildNodes();
                    user.setSalida4(((Node) SALIDA4outAgeList.item(0)).getNodeValue().trim());
                        } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList SALIDA5outList = firstPersonElement.getElementsByTagName("SALIDA5");
                    Element SALIDA5outElement = (Element) SALIDA5outList.item(0);
                    NodeList SALIDA5outAgeList = SALIDA5outElement.getChildNodes();
                    user.setSalida5(((Node) SALIDA5outAgeList.item(0)).getNodeValue().trim());
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                      //------
                    NodeList SALIDA6outList = firstPersonElement.getElementsByTagName("SALIDA6");
                    Element SALIDA6outElement = (Element) SALIDA6outList.item(0);
                    NodeList SALIDA6outAgeList = SALIDA6outElement.getChildNodes();
                    user.setSalida6(((Node) SALIDA6outAgeList.item(0)).getNodeValue().trim());
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                      //------
                    NodeList SALIDA7outList = firstPersonElement.getElementsByTagName("SALIDA7");
                    Element SALIDA7outElement = (Element) SALIDA7outList.item(0);
                    NodeList SALIDA7outAgeList = SALIDA7outElement.getChildNodes();
                    user.setSalida7(((Node) SALIDA7outAgeList.item(0)).getNodeValue().trim());
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {

                        NodeList ACTIVA1outList = firstPersonElement.getElementsByTagName("ACTIVA1");
                    Element ACTIVA1outElement = (Element) ACTIVA1outList.item(0);
                    NodeList ACTIVA1outAgeList = ACTIVA1outElement.getChildNodes();
                    user.setActiva1(new Boolean(((Node) ACTIVA1outAgeList.item(0)).getNodeValue().trim()));
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                      //------
                    NodeList ACTIVA2outList = firstPersonElement.getElementsByTagName("ACTIVA2");
                    Element ACTIVA2outElement = (Element) ACTIVA2outList.item(0);
                    NodeList ACTIVA2outAgeList = ACTIVA2outElement.getChildNodes();
                    user.setActiva2(new Boolean(((Node) ACTIVA2outAgeList.item(0)).getNodeValue().trim()));
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                      //------
                    NodeList ACTIVA3outList = firstPersonElement.getElementsByTagName("ACTIVA3");
                    Element ACTIVA3outElement = (Element) ACTIVA3outList.item(0);
                    NodeList ACTIVA3outAgeList = ACTIVA3outElement.getChildNodes();
                    user.setActiva3(new Boolean(((Node) ACTIVA3outAgeList.item(0)).getNodeValue().trim()));
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                      //------
                    NodeList ACTIVA4outList = firstPersonElement.getElementsByTagName("ACTIVA4");
                    Element ACTIVA4outElement = (Element) ACTIVA4outList.item(0);
                    NodeList ACTIVA4outAgeList = ACTIVA4outElement.getChildNodes();
                    user.setActiva4(new Boolean(((Node) ACTIVA4outAgeList.item(0)).getNodeValue().trim()));
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {

                    NodeList ACTIVA5outList = firstPersonElement.getElementsByTagName("ACTIVA5");
                    Element ACTIVA5outElement = (Element) ACTIVA5outList.item(0);
                    NodeList ACTIVA5outAgeList = ACTIVA5outElement.getChildNodes();
                    user.setActiva5(new Boolean(((Node) ACTIVA5outAgeList.item(0)).getNodeValue().trim()));
                     } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList ACTIVA6outList = firstPersonElement.getElementsByTagName("ACTIVA6");
                    Element ACTIVA6outElement = (Element) ACTIVA6outList.item(0);
                    NodeList ACTIVA6outAgeList = ACTIVA6outElement.getChildNodes();
                    user.setActiva6(new Boolean(((Node) ACTIVA6outAgeList.item(0)).getNodeValue().trim()));
                       } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList ACTIVA7outList = firstPersonElement.getElementsByTagName("ACTIVA7");
                    Element ACTIVA7outElement = (Element) ACTIVA7outList.item(0);
                    NodeList ACTIVA7outAgeList = ACTIVA7outElement.getChildNodes();
                    user.setActiva7(new Boolean(((Node) ACTIVA7outAgeList.item(0)).getNodeValue().trim()));
                    
                    NodeList WEBCAMoutList = firstPersonElement.getElementsByTagName("WEBCAM");
                    Element WEBCAMoutElement = (Element) WEBCAMoutList.item(0);
                    NodeList WEBCAMoutAgeList = WEBCAMoutElement.getChildNodes();
                    user.setWebcam(new Boolean(((Node) WEBCAMoutAgeList.item(0)).getNodeValue().trim()));
                    
  } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList PUERTA1outList = firstPersonElement.getElementsByTagName("PUERTA1");
                    Element PUERTA1outElement = (Element) PUERTA1outList.item(0);
                    NodeList PUERTA1outAgeList = PUERTA1outElement.getChildNodes();
                    user.setPuerta1(((Node) PUERTA1outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList PUERTA2outList = firstPersonElement.getElementsByTagName("PUERTA2");
                    Element PUERTA2outElement = (Element) PUERTA2outList.item(0);
                    NodeList PUERTA2outAgeList = PUERTA2outElement.getChildNodes();
                    user.setPuerta2(((Node) PUERTA2outAgeList.item(0)).getNodeValue().trim());
                      } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList PUERTA3outList = firstPersonElement.getElementsByTagName("PUERTA3");
                    Element PUERTA3outElement = (Element) PUERTA3outList.item(0);
                    NodeList PUERTA3outAgeList = PUERTA3outElement.getChildNodes();
                    user.setPuerta3(((Node) PUERTA3outAgeList.item(0)).getNodeValue().trim());
                     } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList PUERTA4outList = firstPersonElement.getElementsByTagName("PUERTA4");
                    Element PUERTA4outElement = (Element) PUERTA4outList.item(0);
                    NodeList PUERTA4outAgeList = PUERTA4outElement.getChildNodes();
                    user.setPuerta4(((Node) PUERTA4outAgeList.item(0)).getNodeValue().trim());
                       } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList PUERTA5outList = firstPersonElement.getElementsByTagName("PUERTA5");
                    Element PUERTA5outElement = (Element) PUERTA5outList.item(0);
                    NodeList PUERTA5outAgeList = PUERTA5outElement.getChildNodes();
                    user.setPuerta5(((Node) PUERTA5outAgeList.item(0)).getNodeValue().trim());
                     } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList PUERTA6outList = firstPersonElement.getElementsByTagName("PUERTA6");
                    Element PUERTA6outElement = (Element) PUERTA6outList.item(0);
                    NodeList PUERTA6outAgeList = PUERTA6outElement.getChildNodes();
                    user.setPuerta6(((Node) PUERTA6outAgeList.item(0)).getNodeValue().trim());
                   } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                      //------
                    try {
                    NodeList PUERTA7outList = firstPersonElement.getElementsByTagName("PUERTA7");
                    Element PUERTA7outElement = (Element) PUERTA7outList.item(0);
                    NodeList PUERTA7outAgeList = PUERTA7outElement.getChildNodes();
                    user.setPuerta7(((Node) PUERTA7outAgeList.item(0)).getNodeValue().trim());
  } catch (Exception parserConfigurationException) {
                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
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
        return user;
    }//end of main
}
