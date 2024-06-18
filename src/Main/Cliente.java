package Main;

import java.io.*;
import java.net.Socket;

public class Cliente {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 12345;

        try (
        	Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.print("Ingrese su nombre de usuario: ");
            String usuario = console.readLine();
            System.out.print("Ingrese su contraseña: ");
            String password = console.readLine();

            out.println(usuario);
            out.println(password);

            String respuesta = in.readLine();
            System.out.println(respuesta);

            if ("Inicio completado".equals(respuesta)) {
                boolean running = true;
                while (running) {
                    System.out.println("\nOpciones:");
                    System.out.println("1. Ver saldo");
                    System.out.println("2. Retirar dinero");
                    System.out.println("3. Ingresar dinero");
                    System.out.println("4. Salir");
                    System.out.print("Seleccione una opción: ");
                    String opcion = console.readLine();

                    switch (opcion) {
                        case "1":
                            out.println("saldo");
                            System.out.println(in.readLine());
                            break;
                        case "2":
                            out.println("retirar");
                            System.out.print("Cantidad a retirar: ");
                            String cantidadRetirar = console.readLine();
                            out.println(cantidadRetirar);
                            System.out.println(in.readLine());
                            break;
                        case "3":
                            out.println("ingresar");
                            System.out.print("Cantidad a ingresar: ");
                            String cantidadIngresar = console.readLine();
                            out.println(cantidadIngresar);
                            System.out.println(in.readLine());
                            break;
                        case "4":
                            out.println("salir");
                            running = false;
                            break;
                        default:
                            System.out.println("Opción no válida");
                            break;
                    }
                }
            } else {
                System.out.println("Autenticación fallida.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
