/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demos;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;

/**
 *
 * @author GEOVANNY
 */
public class Example1 {
	public static void main(String[] args)
	throws IOException {
 
		Runtime runtime = Runtime.getRuntime();
		Process proc = runtime.exec("java -jar javaanpr.jar -recognize -i f:/PROYECTOS/peaje/fotos/71.jpg");
		InputStream is = proc.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
                        break;
		}
                System.out.println("SU PLACA ES: "+line);
	}
}