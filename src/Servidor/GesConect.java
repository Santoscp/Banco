package Servidor;

import java.io.*;
import java.net.Socket;
import java.util.List;

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
                                
                           
                                String passnueva = in.readLine();
                                
                            
                                dao.crearUsuario(usunuevo, passnueva);
                        	  
                        	  
                        	 
                        	  

                        
                              break;
                          case "crearcuenta":
                        	  
                              int id_usuario = in.read();
                              int saldoInicial = in.read();
                              try {
                                  dao.crearCuentaBancaria(id_usuario ,saldoInicial);
                                  out.println("Cuenta bancaria creada con éxito.");
                              } catch (RuntimeException e) {
                                  out.println("Error al crear la cuenta bancaria: " + e.getMessage());
                              }
                              break;
                              
                          case "consultarcuenta":
                        	  String idCuentaStr = in.readLine(); 
                              int idCuenta = Integer.parseInt(idCuentaStr); 
                              System.out.println(idCuenta);

                              double datosCuenta = dao.getCuenta(idCuenta);
                              
                              out.println("Saldo: " + datosCuenta);
                              break;
                              
                          case "consultarcliente":
                        	  String idClienteStr = in.readLine();
                        	  int idCliente =Integer.parseInt(idClienteStr);
                        	  System.out.println(idCliente);
                              String datosCliente = dao.obtenerDatosCliente(idCliente);
                              out.println(datosCliente);
                           
                              break;
                          case "eliminarcuenta":
                        	  String idCuentaEliStr = in.readLine();
                        	  int idCuentaEli =Integer.parseInt(idCuentaEliStr);
              
                            dao.eliminarCuenta(idCuentaEli);
                              out.println("Cuenta eliminada");
                           
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
