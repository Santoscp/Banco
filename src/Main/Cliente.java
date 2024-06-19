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
             BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.print("Ingrese su nombre de usuario: ");
            String usuario = teclado.readLine();
            System.out.print("Ingrese su contrasena: ");
            String password = teclado.readLine();

            out.println(usuario);
            out.println(password);

            String op1 = in.readLine();
 
            System.out.println(op1);
            
            
            
            if("Admind completado".equals(op1)) {
           
            	  boolean inicio = true;
                  while (inicio) {
                      System.out.println("\nOpciones:");
                      System.out.println("1. Crear usuario ");
                      System.out.println("2. Crear Cuenta");
                      System.out.println("3. Consultar datos de cuenta");
                      System.out.println("4. Consultar datos de cliente");
                      System.out.println("5. Eliminar cuenta");
                      System.out.println("6. Salir");
                      System.out.print("Seleccione una opcion: ");
                      String op2 = teclado.readLine();

                      switch (op2) {
                          case "1":
                              out.println("crearusuario");
                              System.out.println("Nombre del usuario:");
                              String nombreUsu = teclado.readLine();
                              out.println(nombreUsu);

                   
                              System.out.println("Contrasena del usuario:");
                              String contraUsu = teclado.readLine();
                              out.println(contraUsu);

                              
                              
                              
                          
                              break;
                          case "2":
                              out.println("crearcuenta");
                           
                              
                              
                              System.out.println("ID del cliente:");
                              String idCliente = teclado.readLine();
                              System.out.println("Saldo inicial:");
                              String saldoInicial = teclado.readLine();
                              out.println(idCliente);
                              out.println(saldoInicial);
                              System.out.println(in.readLine());
                              break;
                          case "3":
                              out.println("consultarcuenta");
                              
                              System.out.println("ID de la cuenta bancaria:");
                              
                              String idCuentaStr = teclado.readLine(); 
                              int idCuenta = Integer.parseInt(idCuentaStr); 
                              System.out.println(idCuenta);
                              out.println(idCuenta);
                              System.out.println(in.readLine());
                              break;
                              
                          case "4":
                        	  out.println("consultarcliente");
                        	  
                              System.out.println("ID del cliente:");
                              
                              String idClienteStr = teclado.readLine();
                              int idcli = Integer.parseInt(idClienteStr);
                              System.out.println(idcli);
                              out.println(idcli);
                              System.out.println(in.readLine());
                              break;
                              
                              
                          case "5":
                        	  out.println("eliminarcuenta");
                              System.out.println("ID de la cuenta bancaria a eliminar:");
                              String idCuentaEliStr = teclado.readLine();
                              int idCuentaEli = Integer.parseInt(idCuentaEliStr);
                              System.out.println(idCuentaEli);
                              out.println(idCuentaEli);
                              System.out.println(in.readLine());
                              break;
                          case "6":
                          	
                          	System.out.println("Adioss");
                              out.println("salir");
                              inicio = false;
                              break;
                          default:
                              System.out.println("Opcion no valida");
                              break;
                      }
                  }
            	
            	
            	
            	
            	
            }else  if ("Inicio completado".equals(op1)) {
                boolean inicio = true;
                while (inicio) {
                    System.out.println("\nOpciones:");
                    System.out.println("1. Ver saldo");
                    System.out.println("2. Retirar dinero");
                    System.out.println("3. Ingresar dinero");
                    System.out.println("4. Salir");
                    System.out.print("Seleccione una opcion: ");
                    String op2 = teclado.readLine();

                    switch (op2) {
                        case "1":
                            out.println("saldo");
                            System.out.println(in.readLine());
                            break;
                        case "2":
                            out.println("retirar");
                            System.out.print("Cantidad a retirar: ");
                            String cantidadRetirar = teclado.readLine();
                            out.println(cantidadRetirar);
                            System.out.println(in.readLine());
                            break;
                        case "3":
                            out.println("ingresar");
                            System.out.print("Cantidad a ingresar: ");
                            String cantidadIngresar = teclado.readLine();
                            out.println(cantidadIngresar);
                            System.out.println(in.readLine());
                            break;
                        case "4":
                        	
                        	System.out.println("Adioss");
                            out.println("salir");
                            inicio = false;
                            break;
                        default:
                            System.out.println("Opcion no valida");
                            break;
                    }
                }
            } else {
                System.out.println("Autenticacion fallida.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
