package Dao;

import conexion.Conexion;
import modelo.Asistencia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO {

    private Connection obtenerConexion() throws SQLException {
        return new Conexion().conectar();
    }

    public boolean registrarEntrada(int usuarioId) {
        Connection con = null;
        try {
            con = obtenerConexion();
            
            // Verificar entrada activa
            String sqlCheck = "SELECT id FROM asistencia WHERE usuario_id=? AND fecha=CURDATE() AND hora_salida IS NULL";
            PreparedStatement psCheck = con.prepareStatement(sqlCheck);
            psCheck.setInt(1, usuarioId);
            ResultSet rs = psCheck.executeQuery();
            
            if (rs.next()) {
                System.out.println("Usuario ya tiene entrada activa");
                return false;
            }
            
            // Registrar entrada
            String sql = "INSERT INTO asistencia(usuario_id, fecha, hora_entrada) VALUES (?, CURDATE(), CURTIME())";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, usuarioId);
            int resultado = ps.executeUpdate();
            
            System.out.println("Entrada registrada: " + (resultado > 0));
            return resultado > 0;
            
        } catch (SQLException e) {
            System.out.println("Error SQL registrarEntrada: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
    }

    public boolean registrarSalida(int usuarioId) {
        Connection con = null;
        try {
            con = obtenerConexion();
            
            String sql = "UPDATE asistencia SET hora_salida=CURTIME() " +
                         "WHERE usuario_id=? AND fecha=CURDATE() AND hora_salida IS NULL";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, usuarioId);
            int resultado = ps.executeUpdate();
            
            System.out.println("Salida registrada: " + (resultado > 0));
            return resultado > 0;
            
        } catch (SQLException e) {
            System.out.println("Error SQL registrarSalida: " + e.getMessage());
            e.printStackTrace();
            return false;
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
    }

    public List<Asistencia> listar() {
        List<Asistencia> lista = new ArrayList<>();
        Connection con = null;
        try {
            con = obtenerConexion();
            
            String sql = "SELECT a.id, a.usuario_id, " +
                         "CONCAT(u.nombre,' ',u.apellido) AS nombreUsuario, " +
                         "a.fecha, a.hora_entrada, a.hora_salida " +
                         "FROM asistencia a " +
                         "INNER JOIN usuarios u ON a.usuario_id = u.id " +
                         "ORDER BY a.fecha DESC, a.hora_entrada DESC";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Asistencia a = new Asistencia();
                a.setId(rs.getInt("id"));
                a.setUsuarioId(rs.getInt("usuario_id"));
                a.setNombreUsuario(rs.getString("nombreUsuario"));
                a.setFecha(rs.getString("fecha"));
                a.setHoraEntrada(rs.getString("hora_entrada"));
                a.setHoraSalida(rs.getString("hora_salida"));
                lista.add(a);
            }
            
            System.out.println("Registros encontrados: " + lista.size());
            
        } catch (SQLException e) {
            System.out.println("Error SQL listar: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { if (con != null) con.close(); } catch (SQLException e) {}
        }
        return lista;
    }
}
