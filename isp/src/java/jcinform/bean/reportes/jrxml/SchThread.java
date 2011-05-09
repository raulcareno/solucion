//package jcinform.bean.reportes.jrxml;
//
////  
////  SchThread.java
////  Copyright (c) 1996, Agustin Froufe
////  Todos los derechos reservados.
////  
////  No se asume ninguna  responsabilidad por el  uso o  alteracion  de este
////  software.  Este software se proporciona COMO ES, sin garantia de ningun
////  tipo de su funcionamiento y en ningun caso sera el autor responsable de
////  da�os o perjuicios que se deriven del mal uso del software,  aun cuando
////  este haya sido notificado de la posibilidad de dicho da�o.
//// 
////   Compilador: javac 1.0
////        Autor: Agustin Froufe
////     Creacion: 14-Sep-1996  18:34:35
//// 
////--------------------------------------------------------------------------
////  Esta informacion no es necesariamente definitiva y est� sujeta a cambios
////  que pueden ser incorporados en cualquier momento, sin avisar.
////--------------------------------------------------------------------------
//
//import java.awt.*;
//import java.applet.Applet;
//
//// En este applet se crean dos threads que incrementan un contador, se 
//// proporcionan distintas prioridades a cada uno y se para cuando los
//// dos coinciden
////
//public class SchThread extends Applet {
//    Contar alto,bajo;
// 
//    public void init() {
//        // Creamos un thread en 200, ya adelantado
//        bajo = new Contar( 200 );
//        // El otro comienza desde cero
//        alto = new Contar( 0 );
//        // Al que comienza en 200 le asignamos prioridad m�nima
//        bajo.setPriority( Thread.MIN_PRIORITY );
//        // Y al otro m�xima
//        alto.setPriority( Thread.MAX_PRIORITY );
//        System.out.println( "Prioridad alta es "+alto.getPriority() );
//        System.out.println( "Prioridad baja es "+bajo.getPriority() );
//        }
//
//    
//    // Arrancamos los dos threads, y vamos repintando hasta que el thread
//    // que tiene prioridad m�s alta alcanza o supera al que tiene prioridad
//    // m�s baja, pero empez� a contar m�s alto
//    public void start() {
//        bajo.start();
//        alto.start();
//        while( alto.getContar() < bajo.getContar() )
//            repaint();
//        repaint();
//        bajo.stop();
//        alto.stop();
//        }
//
//
//    // Vamos pintando los incrementos que realizan ambos threads
//    public void paint( Graphics g ) {
//        g.drawString( "bajo = "+bajo.getContar()+
//            " alto = "+alto.getContar(),10,10 );
//        System.out.println( "bajo = "+bajo.getContar()+ 
//            " alto = "+alto.getContar() );
//        }
//
//
//    // Para parar la ejecuci�n de los threads
//    public void stop() {
//        bajo.stop();
//        alto.stop();
//        }
//    }
//
////----------------------------------------- Final del fichero SchThread.java
