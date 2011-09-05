/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package demos;

import java.io.*;

import java.net.*;

class Servidor {

    static final int PUERTO = 12345;

    public Servidor() {

        try {

            ServerSocket skServidor = new ServerSocket(PUERTO);

            System.out.println("Escucho el puerto " + PUERTO);

            for (int numCli = 0; numCli < 5; numCli++) {
                Socket skCliente = skServidor.accept(); // Crea objeto
                System.out.println("Sirvo al cliente " + numCli);
                OutputStream aux = skCliente.getOutputStream();
                DataOutputStream flujo = new DataOutputStream(aux);
                flujo.writeUTF("Hola cliente " + numCli);
                skCliente.close();
            }

            System.out.println("Demasiados clientes por hoy");

        } catch (Exception e) {

            System.out.println(e.getMessage());

        }

    }

    public static void main(String[] arg) {

        new Servidor();

    }
}
