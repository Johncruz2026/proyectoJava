package dao;

import conexion.ConexionBd;
import modelo.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    // INSERTAR
    public void insertar(Usuario usuario) {
        String sql = "INSERT INTO usuarios(nombre, correo) VALUES(?, ?)";

        try (Connection con = ConexionBd.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Insertado correctamente");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }

    // LISTAR
    public List<Usuario> listar() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection con = ConexionBd.conectar();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Usuario u = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("correo")
                );
                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar: " + e.getMessage());
        }

        return lista;
    }

    // ACTUALIZAR
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre=?, correo=? WHERE id=?";

        try (Connection con = ConexionBd.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario.getNombre());
            ps.setString(2, usuario.getCorreo());
            ps.setInt(3, usuario.getId());

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Actualizado correctamente");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    // ELIMINAR
    public void eliminar(int id) {
        String sql = "DELETE FROM usuarios WHERE id=?";

        try (Connection con = ConexionBd.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Eliminado correctamente");
            } else {
                System.out.println("No se encontró el ID");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }
}