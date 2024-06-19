package Servidor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO {
    private Connection connection;
    
     DAO() {
    	 try {
             Class.forName("com.mysql.cj.jdbc.Driver");
             connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/banco", "root", "");
         } catch (Exception e) {
        	 System.out.println(e);
         }
             
         }

    public synchronized boolean login(String usuario, String password) {
        try {
            String query = "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
           
            
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public synchronized boolean getUsuario(String usuario, String password) {
    	boolean isAdmind=false;
        try {
            String query = "SELECT * FROM usuarios WHERE nombre = ? AND password = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, password);
            
            ResultSet rs = stmt.executeQuery();
           if(rs.next()) {
        	   int count =rs.getInt(1);
        	   isAdmind=count>0 && "admin".equals(usuario) && "admin".equals(password);
           }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isAdmind;
        
    }

    public synchronized double getSaldo(String usuario) {
        double saldo = 0.0;
        try {
            String query = "SELECT c.saldo "
                         + "FROM cuentas c "
                         + "JOIN usuarios u ON c.id_usuario = u.id "
                         + "WHERE u.nombre = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, usuario);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                saldo = rs.getDouble("saldo");
            } else {
                System.out.println("Usuario no encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saldo;
    }

    public synchronized boolean retirarDinero(String usuario, double cantidad) {
    	boolean retirar=true;
        try {
            String idQuery = "SELECT id FROM usuarios WHERE nombre = ?";
            PreparedStatement getUserId = connection.prepareStatement(idQuery);
            getUserId.setString(1, usuario);
            ResultSet rs = getUserId.executeQuery();
            if (rs.next()) {
                int idUsuario = rs.getInt("id");
                String query = "UPDATE cuentas SET saldo = saldo - ? WHERE id_usuario = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setDouble(1, cantidad);
                stmt.setInt(2, idUsuario);
                stmt.executeUpdate();
              retirar = true;
               
            } else {
                System.out.println("Usuario no encontrado");
                retirar =false;
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return retirar;
    }


    public synchronized void ingresarDinero(String usuario, double cantidad) {
        try {
            String idQuery = "SELECT id FROM usuarios WHERE nombre = ?";
            PreparedStatement getUserId = connection.prepareStatement(idQuery);
            getUserId.setString(1, usuario);
            ResultSet rs = getUserId.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                String query = "UPDATE cuentas SET saldo = saldo + ? WHERE id_usuario = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setDouble(1, cantidad);
                stmt.setInt(2, userId);
                stmt.executeUpdate();
            } else {
                System.out.println("Usuario no encontrado");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public synchronized void crearUsuario(String nombre, String password) {
    	
    	  String query = "INSERT INTO usuarios (nombre, password) VALUES (?, ?)";

          try (PreparedStatement stmt = connection.prepareStatement(query)) {
              stmt.setString(1, nombre);
              stmt.setString(2, password);

              int rowsAffected = stmt.executeUpdate();
             
          } catch (SQLException e) {
              if (e.getErrorCode() == 1062) { // Error de clave duplicada
                  System.out.println("El nombre de usuario ya existe.");
              } else {
                  e.printStackTrace();
              }
          }
       
    	 
    	
    	
    }
    
    public void crearCuentaBancaria(int id_usuario, int saldoInicial) {
        try {
    
            String queryVerificacionCliente = "SELECT COUNT(*) FROM usuarios WHERE id = ?";
            PreparedStatement stmtVerificacionCliente = connection.prepareStatement(queryVerificacionCliente);
            stmtVerificacionCliente.setInt(1, id_usuario);
            ResultSet rsVerificacionCliente = stmtVerificacionCliente.executeQuery();
            if (!rsVerificacionCliente.next() || rsVerificacionCliente.getInt(1) == 0) {
                throw new SQLException("El cliente no existe.");
            }

          
            String queryVerificacionCuenta = "SELECT COUNT(*) FROM cuentas WHERE id_usuario = ?";
            PreparedStatement stmtVerificacionCuenta = connection.prepareStatement(queryVerificacionCuenta);
            stmtVerificacionCuenta.setInt(1, id_usuario);
            ResultSet rsVerificacionCuenta = stmtVerificacionCuenta.executeQuery();
            if (rsVerificacionCuenta.next() && rsVerificacionCuenta.getInt(1) > 0) {
                throw new SQLException("El cliente ya tiene una cuenta.");
            }

           
            String query = "INSERT INTO cuentas (id_usuario, saldo) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id_usuario);
            stmt.setInt(2, saldoInicial);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    
    
    public synchronized double getCuenta(int id) {
        double saldo = 0.0;
        try {
            String query = "SELECT saldo "
                         + "FROM cuentas  "
                         + "WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                saldo = rs.getDouble("saldo");
            } else {
                System.out.println("Usuario no encontrado");
            }
          
        } catch (SQLException e) {
            e.printStackTrace();
        }
       
        return saldo;
    }
    
    public String obtenerDatosCliente(int idCliente) {
  
    	
        try {
            String query = "SELECT * FROM usuarios WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            System.out.println(idCliente);
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return "ID: " + rs.getString("nombre") + " , contrasena :" + rs.getString("password");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Cliente no encontrado.";
    }
    public void eliminarCuenta(int idCuenta) {
        try {
            String query = "DELETE FROM cuentas WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, idCuenta);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    
    
    
    
    

}
