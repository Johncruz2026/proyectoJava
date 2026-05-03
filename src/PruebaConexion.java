import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PruebaConexion {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/mi_base";
        String user = "root";
        String password = "11501Ka.";

        try {
            Connection conexion = DriverManager.getConnection(url, user, password);

            String sql = "DELETE FROM usuarios WHERE id=?";
            PreparedStatement ps = conexion.prepareStatement(sql);

            ps.setInt(1, 1); // elimina el usuario con id 1

            ps.executeUpdate();

            System.out.println("✅ Usuario eliminado correctamente");

        } catch (SQLException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
