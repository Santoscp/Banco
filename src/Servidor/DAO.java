package Servidor;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
            String query = "SELECT * FROM usuarios WHERE username = ? AND password = ?";
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

    public synchronized double getSaldo(String usuario) {
        double saldo = 0.0;
        try {
            String query = "SELECT c.saldo "
                         + "FROM cuentas c "
                         + "JOIN usuarios u ON c.user_id = u.id "
                         + "WHERE u.username = ?";
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
            String getUserIdQuery = "SELECT id FROM usuarios WHERE username = ?";
            PreparedStatement getUserIdStmt = connection.prepareStatement(getUserIdQuery);
            getUserIdStmt.setString(1, usuario);
            ResultSet rs = getUserIdStmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                String query = "UPDATE cuentas SET saldo = saldo - ? WHERE user_id = ?";
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setDouble(1, cantidad);
                stmt.setInt(2, userId);
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
            String getUserIdQuery = "SELECT id FROM usuarios WHERE username = ?";
            PreparedStatement getUserIdStmt = connection.prepareStatement(getUserIdQuery);
            getUserIdStmt.setString(1, usuario);
            ResultSet rs = getUserIdStmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                String query = "UPDATE cuentas SET saldo = saldo + ? WHERE user_id = ?";
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

}
