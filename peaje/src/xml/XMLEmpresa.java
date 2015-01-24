package xml;

import hibernate.Empresa;
import hibernate.cargar.*;
import java.io.*;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;
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

/**
 * Clase abstracta para implementar un objeto Personal que se guarde en un
 * archivo XML.
 */
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
    private static final String SERIE = "SERIE";
    private static final String SUCURSAL = "SUCURSAL";
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
    private static final String BLOQUEAR = "BLOQUEAR";
    private static final String BLOQUEARSALIDA = "BLOQUEARSALIDA";
    private static final String BLOQUEARHORARIOSALIDA = "BLOQUEARHORARIOSALIDA";
    private static final String BLOQUEARENTRADA = "BLOQUEARENTRADA";
    private static final String PUERTA1 = "PUERTA1";
    private static final String PUERTA2 = "PUERTA2";
    private static final String PUERTA3 = "PUERTA3";
    private static final String PUERTA4 = "PUERTA4";
    private static final String PUERTA5 = "PUERTA5";
    private static final String PUERTA6 = "PUERTA6";
    private static final String PUERTA7 = "PUERTA7";
    private static final String WEBCAM = "WEBCAM";
    private static final String URL = "URL";
    private static final String ENTRA1 = "ENTRA1";
    private static final String ENTRA2 = "ENTRA2";
    private static final String IPCAM = "IPCAM";
    private static final String SEABRETIC = "SEABRETIC";
    private static final String SEABREFAC = "SEABREFAC";
    private static final String MULTA = "MULTA";
    private static final String PUERTATIC = "PUERTATIC";
    private static final String PUERTAFAC = "PUERTAFAC";
    private static final String BARRERAS = "BARRERAS";
    private static final String IPBARRAS1 = "IPBARRAS1";
    private static final String IPBARRAS2 = "IPBARRAS2";
    private static final String PUERTOBARRAS1 = "PUERTOBARRAS1";
    private static final String PUERTOBARRAS2 = "PUERTOBARRAS2";
    private static final String RETARDOENTRADA = "RETARDOENTRADA";
    private static final String RETARDOSALIDA = "RETARDOSALIDA";
    private static final String TRABAJANOTAVENTA = "TRABAJANOTAVENTA";
    private static final String IMPRIME2FACTURAS = "IMPRIME2FACTURAS";
    private static final String DESDE = "DESDE";
    private static final String HASTA = "HASTA";
    private static final String DESDEFIN = "DESDEFIN";
    private static final String HASTAFIN = "HASTAFIN";
    private static final String HORADESDECOBRO = "HORADESDECOBRO";
    private static final String  VALIDACEDULA ="VALIDACEDULA";
    
    
    private static final String PUNTO = "PUNTO";
    private static final String VALORMAXIMO = "VALORMAXIMO";
    private static final String NOMBRECAJA = "NOMBRECAJA";
    
    // Variables
    private Document xmlDoc = null;
    private Element personal = null;

    /**
     * M�todo para retornar el documento XML en cadena de texto
     */
    public String obtenerTextoXML() {

        return obtenerFuncionTextoXML();

    }

    /**
     * M�todo para generar el elemento Personal del documento XML usando DOM
     */
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

    /**
     * M�todo para generar documento XML usando DOM
     */
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
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva1() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ACTIVA2);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva2() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ACTIVA3);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva3() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ACTIVA4);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva4() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ACTIVA5);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva5() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ACTIVA6);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva6() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ACTIVA7);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getActiva7() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(WEBCAM);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getWebcam() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(SEABREFAC);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getSeabrefac() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(SEABRETIC);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getSeabretic() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(MULTA);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getMulta() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(IPCAM);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getIpcam() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(URL);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getUrl() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ENTRA1);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getEntra1() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(ENTRA2);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getEntra2() + ""));
        personal.appendChild(item);

        item = xmlDoc.createElement(PUERTATIC);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuertatic() + ""));
        personal.appendChild(item);

        item = xmlDoc.createElement(PUERTAFAC);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuertafac() + ""));
        personal.appendChild(item);

        item = xmlDoc.createElement(BLOQUEAR);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getBloquear() + ""));
        personal.appendChild(item);

        item = xmlDoc.createElement(BLOQUEARSALIDA);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getBloquearsalida() + ""));
        personal.appendChild(item);
        
        item = xmlDoc.createElement(BLOQUEARHORARIOSALIDA);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getBloquearhorariosalida() + ""));
        personal.appendChild(item);
        
        item = xmlDoc.createElement(BLOQUEARENTRADA);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getBloquearentrada()+ ""));
        personal.appendChild(item);

        item = xmlDoc.createElement(BARRERAS);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getBarreras() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(IPBARRAS1);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getIpBarras1() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(IPBARRAS2);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getIpBarras2() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(PUERTOBARRAS1);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuertoBarras1() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(PUERTOBARRAS2);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPuertoBarras2() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(RETARDOENTRADA);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getRetardoEntrada() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(RETARDOSALIDA);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getRetardoSalida() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(TRABAJANOTAVENTA);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getTrabajanotaventa() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(IMPRIME2FACTURAS);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getImprime2facturas() + ""));
        personal.appendChild(item);
        item = xmlDoc.createElement(DESDE);
        item.appendChild(xmlDoc.createTextNode("1900-01-01 "+beanEmpresa.getDesde().getHours()+":"+beanEmpresa.getDesde().getMinutes()+":"+beanEmpresa.getDesde().getSeconds()));
        personal.appendChild(item);
        item = xmlDoc.createElement(HASTA);
        item.appendChild(xmlDoc.createTextNode("1900-01-01 "+beanEmpresa.getHasta().getHours()+":"+beanEmpresa.getHasta().getMinutes()+":"+beanEmpresa.getHasta().getSeconds()));
        personal.appendChild(item);
        item = xmlDoc.createElement(VALORMAXIMO);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getValorMaximo() + ""));
        personal.appendChild(item);
        
        item = xmlDoc.createElement(DESDEFIN);
        item.appendChild(xmlDoc.createTextNode("1900-01-01 "+beanEmpresa.getDesdeFin().getHours()+":"+beanEmpresa.getDesdeFin().getMinutes()+":"+beanEmpresa.getDesdeFin().getSeconds()));
        personal.appendChild(item);
        item = xmlDoc.createElement(HASTAFIN);
        item.appendChild(xmlDoc.createTextNode("1900-01-01 "+beanEmpresa.getHastaFin().getHours()+":"+beanEmpresa.getHastaFin().getMinutes()+":"+beanEmpresa.getHastaFin().getSeconds()));
        personal.appendChild(item);
        
        item = xmlDoc.createElement(HORADESDECOBRO);
        item.appendChild(xmlDoc.createTextNode("1900-01-01 "+beanEmpresa.getHoraDesdeCobro().getHours()+":"+beanEmpresa.getHoraDesdeCobro().getMinutes()+":"+beanEmpresa.getHoraDesdeCobro().getSeconds()));
        personal.appendChild(item);
        
        item = xmlDoc.createElement(VALIDACEDULA);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getValidaCedula() + ""));
        personal.appendChild(item);
             
        
        
        item = xmlDoc.createElement(PUNTO);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getPunto()+ ""));
        personal.appendChild(item);
        
        item = xmlDoc.createElement(NOMBRECAJA);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getNombreCaja()));
        personal.appendChild(item);
        
        item = xmlDoc.createElement(SERIE);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getSerie()));
        personal.appendChild(item);
        
        item = xmlDoc.createElement(SUCURSAL);
        item.appendChild(xmlDoc.createTextNode(beanEmpresa.getSucursal()));
        personal.appendChild(item);
        
        //public Boolean imprime2facturas; IMPRIME2FACTURAS TRABAJANOTAVENTA IMPRIME2FACTURAS

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
        beanEmpleado.setLed("" + emp.getLed());
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
        beanEmpleado.setIpcam(emp.getIpcam());
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
        
        beanEmpleado.setPuertatic(emp.getPuertatic());
        beanEmpleado.setPuertafac(emp.getPuertafac());
        beanEmpleado.setIpBarras1(emp.getIpBarras1());
        beanEmpleado.setIpBarras2(emp.getIpBarras2());
        beanEmpleado.setPuertoBarras1(emp.getPuertoBarras1());
        beanEmpleado.setPuertoBarras2(emp.getPuertoBarras2());


        beanEmpleado.setPuerta1(emp.getPuerta1());
        beanEmpleado.setPuerta2(emp.getPuerta2());
        beanEmpleado.setPuerta3(emp.getPuerta3());
        beanEmpleado.setPuerta4(emp.getPuerta4());
        beanEmpleado.setPuerta5(emp.getPuerta5());
        beanEmpleado.setPuerta6(emp.getPuerta6());
        beanEmpleado.setPuerta7(emp.getPuerta7());
        beanEmpleado.setSeabretic(emp.getSeabretic());
        beanEmpleado.setSeabrefac(emp.getSeabrefac());
        beanEmpleado.setMulta(emp.getMulta());
        beanEmpleado.setUrl(emp.getUrl());
        beanEmpleado.setEntra1(emp.getEntra1());
        beanEmpleado.setEntra2(emp.getEntra2());
        beanEmpleado.setBloquear(emp.getBloquear());
        beanEmpleado.setBloquearsalida(emp.getBloquearsalida());
        beanEmpleado.setBloquearhorariosalida(emp.getBloquearhorariosalida());
        beanEmpleado.setBloquearentrada(emp.getBloquearentrada());
        beanEmpleado.setBarreras(emp.getBarreras());
        beanEmpleado.setRetardoEntrada(emp.getRetardoEntrada());
        beanEmpleado.setRetardoSalida(emp.getRetardoSalida());
        beanEmpleado.setDesde(emp.getDesde());
        beanEmpleado.setHasta(emp.getHasta());
        beanEmpleado.setValorMaximo(emp.getValorMaximo());
        beanEmpleado.setPunto(emp.getPunto());
        beanEmpleado.setTrabajanotaventa(emp.getTrabajanotaventa());
        beanEmpleado.setImprime2facturas(emp.getImprime2facturas());
        
        beanEmpleado.setDesdeFin(emp.getDesdeFin());
        beanEmpleado.setHastaFin(emp.getHastaFin());
        beanEmpleado.setHoraDesdeCobro(emp.getHoraDesdeCobro());
        beanEmpleado.setValidaCedula(emp.getValidaCedula());
        beanEmpleado.setNombreCaja(emp.getNombreCaja());
        beanEmpleado.setSerie(emp.getSerie());
        beanEmpleado.setSucursal(emp.getSucursal());
        
//        empresaObj.setDesdeFin((Date)horaDesde3.getValue()); 
//                    empresaObj.setHastaFin((Date)horaHasta3.getValue());
//                    empresaObj.setHoraDesdeCobro((Date)horaDesdeCobrar.getValue());
//                    empresaObj.setValidaCedula(chkvalidacedula.isSelected());
        
        
        

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
                        NodeList lastNameList = firstPersonElement.getElementsByTagName("LED");
                        Element lastNameElement = (Element) lastNameList.item(0);
                        NodeList claveList = lastNameElement.getChildNodes();
                        user.setLed(((Node) claveList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
//                        System.out.println("ERROR XML LED"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList ageList = firstPersonElement.getElementsByTagName("BARRA1");
                        Element ageElement = (Element) ageList.item(0);
                        NodeList textAgeList = ageElement.getChildNodes();
                        user.setBarras(((Node) textAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
//                        System.out.println("ERROR XML BARRA1"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList puertoList = firstPersonElement.getElementsByTagName("BARRA2");
                        Element puertoElement = (Element) puertoList.item(0);
                        NodeList puertoAgeList = puertoElement.getChildNodes();
                        user.setBarras2(((Node) puertoAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
//                        System.out.println("ERROR XML BARRA 2"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList inList = firstPersonElement.getElementsByTagName("SALE1");
                        Element inElement = (Element) inList.item(0);
                        NodeList inAgeList = inElement.getChildNodes();
                        user.setSale(((Node) inAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //    System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList sale2 = firstPersonElement.getElementsByTagName("SALE2");
                        Element sale2Elm = (Element) sale2.item(0);
                        NodeList saleinAgeList = sale2Elm.getChildNodes();
                        user.setSale2(((Node) saleinAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList ENTRADA1outList = firstPersonElement.getElementsByTagName("ENTRADA1");
                        Element ENTRADA1outElement = (Element) ENTRADA1outList.item(0);
                        NodeList ENTRADA1outAgeList = ENTRADA1outElement.getChildNodes();
                        user.setPuerto1(((Node) ENTRADA1outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList ENTRADA2outList = firstPersonElement.getElementsByTagName("ENTRADA2");
                        Element ENTRADA2outElement = (Element) ENTRADA2outList.item(0);
                        NodeList ENTRADA2outAgeList = ENTRADA2outElement.getChildNodes();
                        user.setPuerto2(((Node) ENTRADA2outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList ENTRADA3outList = firstPersonElement.getElementsByTagName("ENTRADA3");
                        Element ENTRADA3outElement = (Element) ENTRADA3outList.item(0);
                        NodeList ENTRADA3outAgeList = ENTRADA3outElement.getChildNodes();
                        user.setPuerto3(((Node) ENTRADA3outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList ENTRADA4outList = firstPersonElement.getElementsByTagName("ENTRADA4");
                        Element ENTRADA4outElement = (Element) ENTRADA4outList.item(0);
                        NodeList ENTRADA4outAgeList = ENTRADA4outElement.getChildNodes();
                        user.setPuerto4(((Node) ENTRADA4outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList ENTRADA5outList = firstPersonElement.getElementsByTagName("ENTRADA5");
                        Element ENTRADA5outElement = (Element) ENTRADA5outList.item(0);
                        NodeList ENTRADA5outAgeList = ENTRADA5outElement.getChildNodes();
                        user.setPuerto5(((Node) ENTRADA5outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList ENTRADA6outList = firstPersonElement.getElementsByTagName("ENTRADA6");
                        Element ENTRADA6outElement = (Element) ENTRADA6outList.item(0);
                        NodeList ENTRADA6outAgeList = ENTRADA6outElement.getChildNodes();
                        user.setPuerto6(((Node) ENTRADA6outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList ENTRADA7outList = firstPersonElement.getElementsByTagName("ENTRADA7");
                        Element ENTRADA7outElement = (Element) ENTRADA7outList.item(0);
                        NodeList ENTRADA7outAgeList = ENTRADA7outElement.getChildNodes();
                        user.setPuerto7(((Node) ENTRADA7outAgeList.item(0)).getNodeValue().trim());
                        //------
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList SALIDA1outList = firstPersonElement.getElementsByTagName("SALIDA1");
                        Element SALIDA1outElement = (Element) SALIDA1outList.item(0);
                        NodeList SALIDA1outAgeList = SALIDA1outElement.getChildNodes();
                        user.setSalida1(((Node) SALIDA1outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList SALIDA2outList = firstPersonElement.getElementsByTagName("SALIDA2");
                        Element SALIDA2outElement = (Element) SALIDA2outList.item(0);
                        NodeList SALIDA2outAgeList = SALIDA2outElement.getChildNodes();
                        user.setSalida2(((Node) SALIDA2outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList SALIDA3outList = firstPersonElement.getElementsByTagName("SALIDA3");
                        Element SALIDA3outElement = (Element) SALIDA3outList.item(0);
                        NodeList SALIDA3outAgeList = SALIDA3outElement.getChildNodes();
                        user.setSalida3(((Node) SALIDA3outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        //------
                        NodeList SALIDA4outList = firstPersonElement.getElementsByTagName("SALIDA4");
                        Element SALIDA4outElement = (Element) SALIDA4outList.item(0);
                        NodeList SALIDA4outAgeList = SALIDA4outElement.getChildNodes();
                        user.setSalida4(((Node) SALIDA4outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList SALIDA5outList = firstPersonElement.getElementsByTagName("SALIDA5");
                        Element SALIDA5outElement = (Element) SALIDA5outList.item(0);
                        NodeList SALIDA5outAgeList = SALIDA5outElement.getChildNodes();
                        user.setSalida5(((Node) SALIDA5outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        //------
                        NodeList SALIDA6outList = firstPersonElement.getElementsByTagName("SALIDA6");
                        Element SALIDA6outElement = (Element) SALIDA6outList.item(0);
                        NodeList SALIDA6outAgeList = SALIDA6outElement.getChildNodes();
                        user.setSalida6(((Node) SALIDA6outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        //------
                        NodeList SALIDA7outList = firstPersonElement.getElementsByTagName("SALIDA7");
                        Element SALIDA7outElement = (Element) SALIDA7outList.item(0);
                        NodeList SALIDA7outAgeList = SALIDA7outElement.getChildNodes();
                        user.setSalida7(((Node) SALIDA7outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        user.setSalida7("");
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }

                    try {
                        //------
                        NodeList BARRERASoutList = firstPersonElement.getElementsByTagName("BARRERAS");
                        Element BARRERASoutElement = (Element) BARRERASoutList.item(0);
                        NodeList BARRERASoutAgeList = BARRERASoutElement.getChildNodes();
                        user.setBarreras(((Node) BARRERASoutAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        user.setBarreras("");
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        //------
                        NodeList BLOQUEAR2outList = firstPersonElement.getElementsByTagName("BLOQUEAR");
                        Element BLOQUEAR2outElement = (Element) BLOQUEAR2outList.item(0);
                        NodeList BLOQUEAR2outAgeList = BLOQUEAR2outElement.getChildNodes();
                        user.setBloquear(new Boolean(((Node) BLOQUEAR2outAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        user.setBloquear(false);
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        //------
                        NodeList BLOQUEARSALIDA2outList = firstPersonElement.getElementsByTagName("BLOQUEARSALIDA");
                        Element BLOQUEARSALIDA2outElement = (Element) BLOQUEARSALIDA2outList.item(0);
                        NodeList BLOQUEARSALIDA2outAgeList = BLOQUEARSALIDA2outElement.getChildNodes();
                        user.setBloquearsalida(new Boolean(((Node) BLOQUEARSALIDA2outAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setBloquearsalida(false);
                    }
                    
                    
                         //------
                    try {
                        //------
                        NodeList BLOQUEARHORARIOSALIDA2outList = firstPersonElement.getElementsByTagName("BLOQUEARHORARIOSALIDA");
                        Element BLOQUEARHORARIOSALIDA2outElement = (Element) BLOQUEARHORARIOSALIDA2outList.item(0);
                        NodeList BLOQUEARHORARIOSALIDA2outAgeList = BLOQUEARHORARIOSALIDA2outElement.getChildNodes();
                        user.setBloquearhorariosalida(new Boolean(((Node) BLOQUEARHORARIOSALIDA2outAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setBloquearhorariosalida(false);
                    }
                    
                      //------
                    try {
                        //------
                        NodeList BLOQUEARENTRADAoutList = firstPersonElement.getElementsByTagName("BLOQUEARENTRADA");
                        Element BLOQUEARENTRADAoutElement = (Element) BLOQUEARENTRADAoutList.item(0);
                        NodeList BLOQUEARENTRADAoutAgeList = BLOQUEARENTRADAoutElement.getChildNodes();
                        user.setBloquearentrada(new Boolean(((Node) BLOQUEARENTRADAoutAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setBloquearentrada(true);
                    }
                    //------
                    try {

                        NodeList ACTIVA1outList = firstPersonElement.getElementsByTagName("ACTIVA1");
                        Element ACTIVA1outElement = (Element) ACTIVA1outList.item(0);
                        NodeList ACTIVA1outAgeList = ACTIVA1outElement.getChildNodes();
                        user.setActiva1(new Boolean(((Node) ACTIVA1outAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setActiva1(false);
                    }
                    //------
                    try {
                        //------
                        NodeList ACTIVA2outList = firstPersonElement.getElementsByTagName("ACTIVA2");
                        Element ACTIVA2outElement = (Element) ACTIVA2outList.item(0);
                        NodeList ACTIVA2outAgeList = ACTIVA2outElement.getChildNodes();
                        user.setActiva2(new Boolean(((Node) ACTIVA2outAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setActiva2(false);
                    }
                    //------
                    try {
                        //------
                        NodeList ACTIVA3outList = firstPersonElement.getElementsByTagName("ACTIVA3");
                        Element ACTIVA3outElement = (Element) ACTIVA3outList.item(0);
                        NodeList ACTIVA3outAgeList = ACTIVA3outElement.getChildNodes();
                        user.setActiva3(new Boolean(((Node) ACTIVA3outAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setActiva3(false);
                    }
                    //------
                    try {
                        //------
                        NodeList ACTIVA4outList = firstPersonElement.getElementsByTagName("ACTIVA4");
                        Element ACTIVA4outElement = (Element) ACTIVA4outList.item(0);
                        NodeList ACTIVA4outAgeList = ACTIVA4outElement.getChildNodes();
                        user.setActiva4(new Boolean(((Node) ACTIVA4outAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setActiva4(false);
                    }
                    //------
                    try {

                        NodeList ACTIVA5outList = firstPersonElement.getElementsByTagName("ACTIVA5");
                        Element ACTIVA5outElement = (Element) ACTIVA5outList.item(0);
                        NodeList ACTIVA5outAgeList = ACTIVA5outElement.getChildNodes();
                        user.setActiva5(new Boolean(((Node) ACTIVA5outAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setActiva5(false);
                    }
                    //------
                    try {
                        NodeList ACTIVA6outList = firstPersonElement.getElementsByTagName("ACTIVA6");
                        Element ACTIVA6outElement = (Element) ACTIVA6outList.item(0);
                        NodeList ACTIVA6outAgeList = ACTIVA6outElement.getChildNodes();
                        user.setActiva6(new Boolean(((Node) ACTIVA6outAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setActiva6(false);
                    }
                    //------
                    try {
                        NodeList ACTIVA7outList = firstPersonElement.getElementsByTagName("ACTIVA7");
                        Element ACTIVA7outElement = (Element) ACTIVA7outList.item(0);
                        NodeList ACTIVA7outAgeList = ACTIVA7outElement.getChildNodes();
                        user.setActiva7(new Boolean(((Node) ACTIVA7outAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setActiva7(false);
                    }
                    try {
                        NodeList WEBCAMoutList = firstPersonElement.getElementsByTagName("WEBCAM");
                        Element WEBCAMoutElement = (Element) WEBCAMoutList.item(0);
                        NodeList WEBCAMoutAgeList = WEBCAMoutElement.getChildNodes();
                        user.setWebcam(new Boolean(((Node) WEBCAMoutAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setWebcam(false);
                    }
                    try {
                        NodeList SEABRETICoutList = firstPersonElement.getElementsByTagName("SEABRETIC");
                        Element SEABRETICoutElement = (Element) SEABRETICoutList.item(0);
                        NodeList SEABRETICoutAgeList = SEABRETICoutElement.getChildNodes();
                        user.setSeabretic(new Boolean(((Node) SEABRETICoutAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setSeabretic(false);
                    }
                    try {
                        NodeList SEABREFACoutList = firstPersonElement.getElementsByTagName("SEABREFAC");
                        Element SEABREFACoutElement = (Element) SEABREFACoutList.item(0);
                        NodeList SEABREFACoutAgeList = SEABREFACoutElement.getChildNodes();
                        user.setSeabrefac(new Boolean(((Node) SEABREFACoutAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setSeabrefac(false);
                    }
                    try {
                        NodeList MULTAoutList = firstPersonElement.getElementsByTagName("MULTA");
                        Element MULTAoutElement = (Element) MULTAoutList.item(0);
                        NodeList MULTAoutAgeList = MULTAoutElement.getChildNodes();
                        user.setMulta(new Double(((Node) MULTAoutAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setMulta(0d);
                    }
                    try {
                        NodeList IPCAMoutList = firstPersonElement.getElementsByTagName("IPCAM");
                        Element IPCAMoutElement = (Element) IPCAMoutList.item(0);
                        NodeList IPCAMoutAgeList = IPCAMoutElement.getChildNodes();
                        user.setIpcam(new Boolean(((Node) IPCAMoutAgeList.item(0)).getNodeValue().trim()));

                    } catch (Exception parserConfigurationException) {
//                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setIpcam(false);
                    }

                    try {
                        //------
                        NodeList URLoutList = firstPersonElement.getElementsByTagName("URL");
                        Element URLoutElement = (Element) URLoutList.item(0);
                        NodeList URLoutAgeList = URLoutElement.getChildNodes();
                        user.setUrl(((Node) URLoutAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setUrl("");
                    }
                    try {
                        //------
                        NodeList ENTRA1outList = firstPersonElement.getElementsByTagName("ENTRA1");
                        Element ENTRA1outElement = (Element) ENTRA1outList.item(0);
                        NodeList ENTRA1outAgeList = ENTRA1outElement.getChildNodes();
                        user.setEntra1(((Node) ENTRA1outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setEntra1("");
                    }

                    try {
                        //------
                        NodeList ENTRA2outList = firstPersonElement.getElementsByTagName("ENTRA2");
                        Element ENTRA2outElement = (Element) ENTRA2outList.item(0);
                        NodeList ENTRA2outAgeList = ENTRA2outElement.getChildNodes();
                        user.setEntra2(((Node) ENTRA2outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setEntra2("");
                    }




                    try {
                        //------
                        NodeList PUERTATICoutList = firstPersonElement.getElementsByTagName("PUERTATIC");
                        Element PUERTATICoutElement = (Element) PUERTATICoutList.item(0);
                        NodeList PUERTATICoutAgeList = PUERTATICoutElement.getChildNodes();
                        user.setPuertatic(((Node) PUERTATICoutAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }

                    try {
                        //------
                        NodeList PUERTAFACoutList = firstPersonElement.getElementsByTagName("PUERTAFAC");
                        Element PUERTAFACoutElement = (Element) PUERTAFACoutList.item(0);
                        NodeList PUERTAFACoutAgeList = PUERTAFACoutElement.getChildNodes();
                        user.setPuertafac(((Node) PUERTAFACoutAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setPuertafac("");
                    }

                    //------
                    try {
                        NodeList PUERTA1outList = firstPersonElement.getElementsByTagName("PUERTA1");
                        Element PUERTA1outElement = (Element) PUERTA1outList.item(0);
                        NodeList PUERTA1outAgeList = PUERTA1outElement.getChildNodes();
                        user.setPuerta1(((Node) PUERTA1outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
//                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList PUERTA2outList = firstPersonElement.getElementsByTagName("PUERTA2");
                        Element PUERTA2outElement = (Element) PUERTA2outList.item(0);
                        NodeList PUERTA2outAgeList = PUERTA2outElement.getChildNodes();
                        user.setPuerta2(((Node) PUERTA2outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
//                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList PUERTA3outList = firstPersonElement.getElementsByTagName("PUERTA3");
                        Element PUERTA3outElement = (Element) PUERTA3outList.item(0);
                        NodeList PUERTA3outAgeList = PUERTA3outElement.getChildNodes();
                        user.setPuerta3(((Node) PUERTA3outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
//                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList PUERTA4outList = firstPersonElement.getElementsByTagName("PUERTA4");
                        Element PUERTA4outElement = (Element) PUERTA4outList.item(0);
                        NodeList PUERTA4outAgeList = PUERTA4outElement.getChildNodes();
                        user.setPuerta4(((Node) PUERTA4outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
//                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList PUERTA5outList = firstPersonElement.getElementsByTagName("PUERTA5");
                        Element PUERTA5outElement = (Element) PUERTA5outList.item(0);
                        NodeList PUERTA5outAgeList = PUERTA5outElement.getChildNodes();
                        user.setPuerta5(((Node) PUERTA5outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
//                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList PUERTA6outList = firstPersonElement.getElementsByTagName("PUERTA6");
                        Element PUERTA6outElement = (Element) PUERTA6outList.item(0);
                        NodeList PUERTA6outAgeList = PUERTA6outElement.getChildNodes();
                        user.setPuerta6(((Node) PUERTA6outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
//                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    //------
                    try {
                        NodeList PUERTA7outList = firstPersonElement.getElementsByTagName("PUERTA7");
                        Element PUERTA7outElement = (Element) PUERTA7outList.item(0);
                        NodeList PUERTA7outAgeList = PUERTA7outElement.getChildNodes();
                        user.setPuerta7(((Node) PUERTA7outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }

                    try {
                        NodeList IPBARRAS1outList = firstPersonElement.getElementsByTagName("IPBARRAS1");
                        Element IPBARRAS1outElement = (Element) IPBARRAS1outList.item(0);
                        NodeList IPBARRAS1outAgeList = IPBARRAS1outElement.getChildNodes();
                        user.setIpBarras1(((Node) IPBARRAS1outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    try {
                        NodeList IPBARRAS2outList = firstPersonElement.getElementsByTagName("IPBARRAS2");
                        Element IPBARRAS2outElement = (Element) IPBARRAS2outList.item(0);
                        NodeList IPBARRAS2outAgeList = IPBARRAS2outElement.getChildNodes();
                        user.setIpBarras2(((Node) IPBARRAS2outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }

                    try {
                        NodeList PUERTOBARRAS1outList = firstPersonElement.getElementsByTagName("PUERTOBARRAS1");
                        Element PUERTOBARRAS1outElement = (Element) PUERTOBARRAS1outList.item(0);
                        NodeList PUERTOBARRAS1outAgeList = PUERTOBARRAS1outElement.getChildNodes();
                        user.setPuertoBarras1(((Node) PUERTOBARRAS1outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }


                    try {
                        NodeList PUERTOBARRAS2outList = firstPersonElement.getElementsByTagName("PUERTOBARRAS2");
                        Element PUERTOBARRAS2outElement = (Element) PUERTOBARRAS2outList.item(0);
                        NodeList PUERTOBARRAS2outAgeList = PUERTOBARRAS2outElement.getChildNodes();
                        user.setPuertoBarras2(((Node) PUERTOBARRAS2outAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }


                    try {
                        NodeList RETARDOENTRADAoutList = firstPersonElement.getElementsByTagName("RETARDOENTRADA");
                        Element RETARDOENTRADAoutElement = (Element) RETARDOENTRADAoutList.item(0);
                        NodeList RETARDOENTRADAoutAgeList = RETARDOENTRADAoutElement.getChildNodes();
                        user.setRetardoEntrada(((Node) RETARDOENTRADAoutAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }

                    try {
                        NodeList RETARDOSALIDAoutList = firstPersonElement.getElementsByTagName("RETARDOSALIDA");
                        Element RETARDOSALIDAoutElement = (Element) RETARDOSALIDAoutList.item(0);
                        NodeList RETARDOSALIDAoutAgeList = RETARDOSALIDAoutElement.getChildNodes();
                        user.setRetardoSalida(((Node) RETARDOSALIDAoutAgeList.item(0)).getNodeValue().trim());
                    } catch (Exception parserConfigurationException) {
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }


                    //public Boolean imprime2facturas; IMPRIME2FACTURAS TRABAJANOTAVENTA IMPRIME2FACTURAS
                    try {
                        NodeList TRABAJANOTAVENTAoutList = firstPersonElement.getElementsByTagName("TRABAJANOTAVENTA");
                        Element TRABAJANOTAVENTAoutElement = (Element) TRABAJANOTAVENTAoutList.item(0);
                        NodeList TRABAJANOTAVENTAoutAgeList = TRABAJANOTAVENTAoutElement.getChildNodes();
                        //user.setTrabajanotaventa(((Node) TRABAJANOTAVENTAoutAgeList.item(0)).getNodeValue().trim());
                        user.setTrabajanotaventa(new Boolean(((Node) TRABAJANOTAVENTAoutAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        // System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }

                    try {
                        NodeList IMPRIME2FACTURASoutList = firstPersonElement.getElementsByTagName("IMPRIME2FACTURAS");
                        Element IMPRIME2FACTURASoutElement = (Element) IMPRIME2FACTURASoutList.item(0);
                        NodeList IMPRIME2FACTURASoutAgeList = IMPRIME2FACTURASoutElement.getChildNodes();
                        user.setImprime2facturas(new Boolean(((Node) IMPRIME2FACTURASoutAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    
                    try {
                        NodeList DESDEoutList = firstPersonElement.getElementsByTagName("DESDE");
                        Element DESDEoutElement = (Element) DESDEoutList.item(0);
                        NodeList DESDEoutAgeList = DESDEoutElement.getChildNodes();
                        Date de = new Date();
                        String tiempo  = (((Node) DESDEoutAgeList.item(0)).getNodeValue().trim().substring(10));
                         StringTokenizer t = new StringTokenizer(tiempo, ":");
                         int a = 0;
                            de.setHours(0);
                            de.setMinutes(0);
                            de.setSeconds(0);
                         for (StringTokenizer stringTokenizer = new StringTokenizer(tiempo,":"); stringTokenizer.hasMoreTokens();) {
                            String token = stringTokenizer.nextToken();
                            if(a == 0){
                                de.setHours(new Integer(token.trim()));
                            }else if(a == 1){
                                de.setMinutes(new Integer(token.trim()));
                            }else if(a == 2){
                                    de.setSeconds(new Integer(token.trim()));
                            }
                            a++;
                             //System.out.println(""+token);
                             
                        }
                        user.setDesde(de);
                    } catch (Exception parserConfigurationException) {
                        System.out.println(""+parserConfigurationException);
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }

                    try {
                        NodeList HastaoutList = firstPersonElement.getElementsByTagName("HASTA");
                        Element HastaoutElement = (Element) HastaoutList.item(0);
                        NodeList HastaoutAgeList = HastaoutElement.getChildNodes();
                        Date de = new Date();
                        String tiempo  = (((Node) HastaoutAgeList.item(0)).getNodeValue().trim().substring(10));
                         int a = 0;
                            de.setHours(23);
                            de.setMinutes(59);
                            de.setSeconds(59);
                         for (StringTokenizer stringTokenizer = new StringTokenizer(tiempo,":"); stringTokenizer.hasMoreTokens();) {
                            String token = stringTokenizer.nextToken();
                            if(a == 0){
                                de.setHours(new Integer(token.trim()));
                            }else if(a == 1){
                                de.setMinutes(new Integer(token.trim()));
                            }else if(a == 2){
                                    de.setSeconds(new Integer(token.trim()));
                            }
                            a++;
                          //  System.out.println(""+token);
                             
                        }
                        user.setHasta(de);
                        
                    } catch (Exception parserConfigurationException) {
                        System.out.println(""+parserConfigurationException);
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    
                                        
                    try {
                        NodeList VALORMAXIMOSoutList = firstPersonElement.getElementsByTagName("VALORMAXIMO");
                        Element VALORMAXIMOSoutElement = (Element) VALORMAXIMOSoutList.item(0);
                        NodeList VALORMAXIMOSoutAgeList = VALORMAXIMOSoutElement.getChildNodes();
                        user.setValorMaximo(new Double(((Node) VALORMAXIMOSoutAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }

                    try {
                        //------
                        NodeList PUNTOoutList = firstPersonElement.getElementsByTagName("PUNTO");
                        Element PUNTOoutElement = (Element) PUNTOoutList.item(0);
                        NodeList PUNTOoutAgeList = PUNTOoutElement.getChildNodes();
                        user.setPunto(new Boolean(((Node) PUNTOoutAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        //  System.out.println("ERROR LECTURA"+parserConfigurationException);
                        user.setPunto(false);
                    }
                    
                    
                   try {
                        NodeList DESDEFINoutList = firstPersonElement.getElementsByTagName("DESDEFIN");
                        Element DESDEFINoutElement = (Element) DESDEFINoutList.item(0);
                        NodeList DESDEFINoutAgeList = DESDEFINoutElement.getChildNodes();
                        Date de = new Date();
                        String tiempo  = (((Node) DESDEFINoutAgeList.item(0)).getNodeValue().trim().substring(10));
                         StringTokenizer t = new StringTokenizer(tiempo, ":");
                         int a = 0;
                            de.setHours(0);
                            de.setMinutes(0);
                            de.setSeconds(0);
                         for (StringTokenizer stringTokenizer = new StringTokenizer(tiempo,":"); stringTokenizer.hasMoreTokens();) {
                            String token = stringTokenizer.nextToken();
                            if(a == 0){
                                de.setHours(new Integer(token.trim()));
                            }else if(a == 1){
                                de.setMinutes(new Integer(token.trim()));
                            }else if(a == 2){
                                    de.setSeconds(new Integer(token.trim()));
                            }
                            a++;
                            // System.out.println(""+token);
                             
                        }
                        user.setDesdeFin(de);
                    } catch (Exception parserConfigurationException) {
                        System.out.println(""+parserConfigurationException);
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }

                    try {
                        NodeList HastaFinoutList = firstPersonElement.getElementsByTagName("HASTAFIN");
                        Element HastaFinoutElement = (Element) HastaFinoutList.item(0);
                        NodeList HastaFinoutAgeList = HastaFinoutElement.getChildNodes();
                        Date de = new Date();
                        String tiempo  = (((Node) HastaFinoutAgeList.item(0)).getNodeValue().trim().substring(10));
                         int a = 0;
                            de.setHours(23);
                            de.setMinutes(59);
                            de.setSeconds(59);
                         for (StringTokenizer stringTokenizer = new StringTokenizer(tiempo,":"); stringTokenizer.hasMoreTokens();) {
                            String token = stringTokenizer.nextToken();
                            if(a == 0){
                                de.setHours(new Integer(token.trim()));
                            }else if(a == 1){
                                de.setMinutes(new Integer(token.trim()));
                            }else if(a == 2){
                                    de.setSeconds(new Integer(token.trim()));
                            }
                            a++;
                           //  System.out.println(""+token);
                             
                        }
                        user.setHastaFin(de);
                        
                    } catch (Exception parserConfigurationException) {
                        System.out.println(""+parserConfigurationException);
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    
                    try {
                        NodeList HORADESDECOBROoutList = firstPersonElement.getElementsByTagName("HORADESDECOBRO");
                        Element HORADESDECOBROoutElement = (Element) HORADESDECOBROoutList.item(0);
                        NodeList HORADESDECOBROoutAgeList = HORADESDECOBROoutElement.getChildNodes();
                        Date de = new Date();
                        String tiempo  = (((Node) HORADESDECOBROoutAgeList.item(0)).getNodeValue().trim().substring(10));
                         StringTokenizer t = new StringTokenizer(tiempo, ":");
                         int a = 0;
                            de.setHours(0);
                            de.setMinutes(0);
                            de.setSeconds(0);
                         for (StringTokenizer stringTokenizer = new StringTokenizer(tiempo,":"); stringTokenizer.hasMoreTokens();) {
                            String token = stringTokenizer.nextToken();
                            if(a == 0){
                                de.setHours(new Integer(token.trim()));
                            }else if(a == 1){
                                de.setMinutes(new Integer(token.trim()));
                            }else if(a == 2){
                                    de.setSeconds(new Integer(token.trim()));
                            }
                            a++;
                           //  System.out.println(""+token);
                             
                        }
                        user.setHoraDesdeCobro(de);
                    } catch (Exception parserConfigurationException) {
                        System.out.println(""+parserConfigurationException);
                        //                        System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    
                    //public Boolean imprime2facturas; IMPRIME2FACTURAS TRABAJANOTAVENTA IMPRIME2FACTURAS
                    try {
                        NodeList VALIDACEDULAoutList = firstPersonElement.getElementsByTagName("VALIDACEDULA");
                        Element VALIDACEDULAoutElement = (Element) VALIDACEDULAoutList.item(0);
                        NodeList VALIDACEDULAoutAgeList = VALIDACEDULAoutElement.getChildNodes();
                        //user.setTrabajanotaventa(((Node) VALIDACEDULAoutAgeList.item(0)).getNodeValue().trim());
                        user.setValidaCedula(new Boolean(((Node) VALIDACEDULAoutAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        // System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    
                     
                    try {
                        NodeList NOMBRECAJAoutList = firstPersonElement.getElementsByTagName("NOMBRECAJA");
                        Element NOMBRECAJAoutElement = (Element) NOMBRECAJAoutList.item(0);
                        NodeList NOMBRECAJAoutAgeList = NOMBRECAJAoutElement.getChildNodes();
                        user.setNombreCaja((((Node) NOMBRECAJAoutAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        // System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                    
                    try {
                        NodeList SERIEoutList = firstPersonElement.getElementsByTagName("SERIE");
                        Element SERIEoutElement = (Element) SERIEoutList.item(0);
                        NodeList SERIEoutAgeList = SERIEoutElement.getChildNodes();
                        user.setSerie((((Node) SERIEoutAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        // System.out.println("ERROR LECTURA"+parserConfigurationException);
                    }
                       try {
                        NodeList SUCURSALoutList = firstPersonElement.getElementsByTagName("SUCURSAL");
                        Element SUCURSALoutElement = (Element) SUCURSALoutList.item(0);
                        NodeList SUCURSALoutAgeList = SUCURSALoutElement.getChildNodes();
                        user.setSucursal((((Node) SUCURSALoutAgeList.item(0)).getNodeValue().trim()));
                    } catch (Exception parserConfigurationException) {
                        // System.out.println("ERROR LECTURA"+parserConfigurationException);
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
