package modelo;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class UsuarioDAO {

    // Método para validar login
    public boolean validarLogin(String correo, String contrasena) {

        boolean acceso = false;

        try {

            // Crear conexión
            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            // Consulta SQL
            String sql = "SELECT * FROM usuarios WHERE correo=? AND contrasena=?";

            // Preparar consulta
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, correo);
            ps.setString(2, contrasena);

            // Ejecutar consulta
            ResultSet rs = ps.executeQuery();

            // Validar si encontró usuario
            if (rs.next()) {

                acceso = true;

            }

        } catch (Exception e) {

            System.out.println("Error login: " + e);

        }

        return acceso;
    }
  // Listar usuarios para asistencia
    public ArrayList<Usuario> listarUsuarios() {
        ArrayList<Usuario> lista = new ArrayList<>();
        try {
            Conexion cn = new Conexion();
            Connection con = cn.conectar();
            String sql = "SELECT id, nombre, apellido FROM usuarios ORDER BY nombre";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setNombre(rs.getString("nombre"));
                u.setApellido(rs.getString("apellido"));
                lista.add(u);
            }
        } catch (Exception e) {
            System.out.println("Error listar usuarios: " + e);
        }
        return lista;
    }  
}
