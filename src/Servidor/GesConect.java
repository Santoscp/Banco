package Servidor;

import java.io.*;
import java.net.Socket;

public class GesConect extends Thread {
    private DAO dao;
    private Socket socket;

    public GesConect(DAO dao, Socket socket) {
        this.dao = dao;
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            String usuario = in.readLine();
            String password = in.readLine();
            
            if(dao.getUsuario(usuario, password)) {
            	out.println("Admind completado");
            	  boolean running = true;
            	  while (running) {
                      String comando = in.readLine();
                      
                      switch (comando) {
                          case "crearusuario":
  
                        	  String usunuevo = in.readLine();
                        	  in.close();
                        	  String passnueva = in.readLine();
            
                        	  
                      
                        	  dao.crearUsuario(usunuevo, "hola");
                        	  
                        	  
                        	 
                        	  

                        
                              break;
                          case "retirar":
                              double cantidadRetirar = Double.parseDouble(in.readLine());
                              if (dao.retirarDinero(usuario, cantidadRetirar)) {
                                  out.println("Retiro exitoso");
                              } else {
                                  out.println("Saldo insuficiente");
                              }
                              break;
                          case "ingresar":
                              double cantidadIngresar = Double.parseDouble(in.readLine());
                              dao.ingresarDinero(usuario, cantidadIngresar);
                              out.println("Ingreso exitoso");
                              break;
                          case "salir":
                              running = false;
                              break;
                          default:
                              out.println("Comando deconocido");
                              break;
                      }
                  }
            	
            	
            	

            }else  if (dao.login(usuario, password)) {
                out.println("Inicio completado");
                boolean running = true;
                
                while (running) {
                    String comando = in.readLine();
                    
                    switch (comando) {
                        case "saldo":
                            double saldo = dao.getSaldo(usuario);
                            out.println("Saldo: " + saldo);
                            break;
                        case "retirar":
                            double cantidadRetirar = Double.parseDouble(in.readLine());
                            if (dao.retirarDinero(usuario, cantidadRetirar)) {
                                out.println("Retiro exitoso");
                            } else {
                                out.println("Saldo insuficiente");
                            }
                            break;
                        case "ingresar":
                            double cantidadIngresar = Double.parseDouble(in.readLine());
                            dao.ingresarDinero(usuario, cantidadIngresar);
                            out.println("Ingreso exitoso");
                            break;
                        case "salir":
                            running = false;
                            break;
                        default:
                            out.println("Comando deconocido");
                            break;
                    }
                }
            } else {
                out.println("Login fallido");
            }
            
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
