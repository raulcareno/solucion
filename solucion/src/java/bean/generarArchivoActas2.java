package bean;

import com.linuxense.javadbf.*;
import java.io.*;
import java.util.Date;

public class generarArchivoActas2 {

//    Administrador adm = new Administrador();
//    Periodo per = new Periodo();
//    CharField CODCOL = null;
//    CharField CODTIT = null;
//    CharField SECCION = null;
//    CharField LECTIVO = null;
//    NumField ACTA = null;
//    CharField CEDULA = null;
//    CharField APELLIDOS = null;
//    CharField NOMBRES = null;
//    CharField SEXO = null;
//    NumField CALIFICA = null;
//    DateField FECHAG = null;
//    CharField ACUERDO = null;
//    DateField FECHA = null;
//    DBF db;
    public static void main(String args[])
            throws DBFException, IOException {

        // let us create field definitions first
        // we will go for 3 fields
        //
        DBFField fields[] = new DBFField[13];

        fields[0] = new DBFField();
        fields[0].setName("CODCOL");
        fields[0].setDataType(DBFField.FIELD_TYPE_C);
        fields[0].setFieldLength(6);

        fields[1] = new DBFField();
        fields[1].setName("CODTIT");
        fields[1].setDataType(DBFField.FIELD_TYPE_C);
        fields[1].setFieldLength(8);

        fields[2] = new DBFField();
        fields[2].setName("SECCION");
        fields[2].setDataType(DBFField.FIELD_TYPE_C);
        fields[2].setFieldLength(1);


        fields[3] = new DBFField();
        fields[3].setName("LECTIVO");
        fields[3].setDataType(DBFField.FIELD_TYPE_C);
        fields[3].setFieldLength(12);

        fields[4] = new DBFField();
        fields[4].setName("ACTA");
        fields[4].setDataType(DBFField.FIELD_TYPE_N);
        fields[4].setFieldLength(3);
        fields[4].setDecimalCount(0);

        fields[5] = new DBFField();
        fields[5].setName("CEDULA");
        fields[5].setDataType(DBFField.FIELD_TYPE_C);
        fields[5].setFieldLength(15);

        fields[6] = new DBFField();
        fields[6].setName("APELLIDOS");
        fields[6].setDataType(DBFField.FIELD_TYPE_C);
        fields[6].setFieldLength(30);

        fields[7] = new DBFField();
        fields[7].setName("NOMBRES");
        fields[7].setDataType(DBFField.FIELD_TYPE_C);
        fields[7].setFieldLength(30);

        fields[8] = new DBFField();
        fields[8].setName("SEXO");
        fields[8].setDataType(DBFField.FIELD_TYPE_C);
        fields[8].setFieldLength(1);


        fields[9] = new DBFField();
        fields[9].setName("CALIFICA");
        fields[9].setDataType(DBFField.FIELD_TYPE_N);
        fields[9].setFieldLength(3);
        fields[9].setDecimalCount(0);

        fields[10] = new DBFField();
        fields[10].setName("FECHAG");
        fields[10].setDataType(DBFField.FIELD_TYPE_D);
//    fields[10].setFieldLength(8);

        fields[11] = new DBFField();
        fields[11].setName("ACUERDO");
        fields[11].setDataType(DBFField.FIELD_TYPE_C);
        fields[11].setFieldLength(10);

        fields[12] = new DBFField();
        fields[12].setName("FECHA");
        fields[12].setDataType(DBFField.FIELD_TYPE_D);
//    fields[12].setFieldLength(8);



        DBFWriter writer = new DBFWriter(new File("C:\\dpeptit\\bases\\alumnos.dbf"));
//    writer.setFields( fields);
//    CharField CODCOL = null;0
//    CharField CODTIT = null;1
//    CharField SECCION = null;2
//    CharField LECTIVO = null;3
//    NumField ACTA = null;4
//    CharField CEDULA = null;5
//    CharField APELLIDOS = null;6
//    CharField NOMBRES = null;7
//    CharField SEXO = null;8
//    NumField CALIFICA = null;9
//    DateField FECHAG = null;10
//    CharField ACUERDO = null;11
//    DateField FECHA = null;12
//    DBF db;
        // now populate DBFWriter
        //
        for (int i = 0; i < 9; i++) {
            Object rowData[] = new Object[13];
            rowData[0] = " 10370";
            rowData[1] = "1.2.01";
            rowData[2] = "1";
            rowData[3] = "50";
            rowData[4] = new Double(50 + i);
            rowData[5] = "130970054" + i;
            rowData[6] = "APELLIDO_" + i;
            rowData[7] = "NOMBRE_" + i;
            rowData[8] = "M";
            rowData[9] = new Double(20.00);
            rowData[10] = new Date("02/02/2011");
            rowData[11] = "50";
            rowData[12] = new Date("02/02/2011");
            //rowData[2] = new Double( 5000.00);
            writer.addRecord(rowData);
        }
//        writer.
//        DBFWriter writer2 = new DBFWriter(new File("f:\\alumnosc.dbf"));
//        for (int i = 0; i < 10; i++) {
//            //ORD NUMERIC
//            //ACTA NUMERIC
//            //CEDULA CHAR 15
//            //APELLIDOS CHAR
//            //NOMBRE CHAR 30
//            //NOMSEX CHAR 30
//            //CALIFICA NUMERIC
//            //FECHAG DATE
//            //ACUERDO CHAR
//            //FECHA DATE
//            Object rowData2[] = new Object[10];
//            rowData2[0] = new Double(i + 10);
//            rowData2[1] = new Double(i + 1);
//            rowData2[2] = "13097005" + i;
//            rowData2[3] = "APELLIDO_" + i;
//            rowData2[4] = "NOMBRE_" + i;
//            rowData2[5] = "Masc";
//            rowData2[6] = new Double(20.00);
//            rowData2[7] = new Date();
//            rowData2[8] = "50";
//            rowData2[9] = new Date();
//            //rowData[2] = new Double( 5000.00);
//            writer2.addRecord(rowData2);
//        }
//    rowData = new Object[3];
//    rowData[0] = "1001";
//    rowData[1] = "Lalit";
//    rowData[2] = new Double( 3400.00);
//
//    writer.addRecord( rowData);
//
//    rowData = new Object[3];
//    rowData[0] = "1002";
//    rowData[1] = "Rohit";
//    rowData[2] = new Double( 7350.00);
//    writer.addRecord( rowData);
        //FileOutputStream fos = new FileOutputStream();
        //writer.write( fos);
//    fos.close();
    }
}
