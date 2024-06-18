package Servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        DAO dao = new DAO();
        int port = 12345;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Servidor iniciado en el puerto " + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                GesConect handler = new GesConect(dao, clientSocket);
                handler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
