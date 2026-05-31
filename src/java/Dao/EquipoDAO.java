package modelo;

import conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;

public class EquipoDAO {

    // =========================
    // GUARDAR EQUIPO
    // =========================

    public boolean guardarEquipo(Equipo equipo) {

        boolean registro = false;

        try {

            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            String sqlVerificar = "SELECT COUNT(*) FROM equipos WHERE serial_equipo = ?";

            PreparedStatement psVerificar = con.prepareStatement(sqlVerificar);

            psVerificar.setString(1, equipo.getSerialEquipo());

            ResultSet rs = psVerificar.executeQuery();

            rs.next();

            int cantidad = rs.getInt(1);

            if (cantidad > 0) {
                return false;
            }

            String sql = "INSERT INTO equipos(nombre_equipo, marca, serial_equipo, estado_equipo) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, equipo.getNombreEquipo());
            ps.setString(2, equipo.getMarca());
            ps.setString(3, equipo.getSerialEquipo());
            ps.setString(4, equipo.getEstadoEquipo());

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                registro = true;
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println("ERROR GUARDAR EQUIPO:");
            e.printStackTrace();

        }

        return registro;
    }

    // =========================
    // LISTAR EQUIPOS
    // =========================

    public ArrayList<Equipo> listarEquipos() {

        ArrayList<Equipo> lista = new ArrayList<>();

        try {

            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            String sql = "SELECT * FROM equipos";

            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Equipo equipo = new Equipo();

                equipo.setId(rs.getInt("id"));
                equipo.setNombreEquipo(rs.getString("nombre_equipo"));
                equipo.setMarca(rs.getString("marca"));
                equipo.setSerialEquipo(rs.getString("serial_equipo"));
                equipo.setEstadoEquipo(rs.getString("estado_equipo"));

                lista.add(equipo);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println("ERROR LISTAR EQUIPOS:");
            e.printStackTrace();

        }

        return lista;
    }

    // =========================
    // ELIMINAR EQUIPO
    // =========================

    public boolean eliminarEquipo(int id) {

        boolean eliminado = false;

        try {

            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            String sql = "DELETE FROM equipos WHERE id = ?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, id);

            int resultado = ps.executeUpdate();

            if (resultado > 0) {
                eliminado = true;
            }

            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println("ERROR ELIMINAR EQUIPO:");
            e.printStackTrace();

        }

        return eliminado;
    }

    // =========================
    // ACTUALIZAR EQUIPO
    // =========================

    public boolean actualizarEquipo(Equipo equipo) {

        boolean actualizado = false;

        try {

            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            // Validar serial duplicado
            String sqlVerificar =
                    "SELECT COUNT(*) FROM equipos WHERE serial_equipo = ? AND id != ?";

            PreparedStatement psVerificar =
                    con.prepareStatement(sqlVerificar);

            psVerificar.setString(1, equipo.getSerialEquipo());
            psVerificar.setInt(2, equipo.getId());

            ResultSet rs = psVerificar.executeQuery();

            rs.next();

            int cantidad = rs.getInt(1);

            if (cantidad > 0) {

                System.out.println("Serial duplicado: "
                        + equipo.getSerialEquipo());

                return false;
            }

            String sql =
                    "UPDATE equipos "
                    + "SET nombre_equipo=?, "
                    + "marca=?, "
                    + "serial_equipo=?, "
                    + "estado_equipo=? "
                    + "WHERE id=?";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, equipo.getNombreEquipo());
            ps.setString(2, equipo.getMarca());
            ps.setString(3, equipo.getSerialEquipo());
            ps.setString(4, equipo.getEstadoEquipo());
            ps.setInt(5, equipo.getId());

            System.out.println("========== ACTUALIZANDO ==========");
            System.out.println("ID: " + equipo.getId());
            System.out.println("Nombre: " + equipo.getNombreEquipo());
            System.out.println("Marca: " + equipo.getMarca());
            System.out.println("Serial: " + equipo.getSerialEquipo());
            System.out.println("Estado: " + equipo.getEstadoEquipo());

            int resultado = ps.executeUpdate();

            System.out.println("Filas actualizadas: " + resultado);

            if (resultado > 0) {

                actualizado = true;

            } else {

                System.out.println("No se actualizó ninguna fila.");

            }

            rs.close();
            psVerificar.close();
            ps.close();
            con.close();

        } catch (Exception e) {

            System.out.println("ERROR ACTUALIZAR EQUIPO:");
            e.printStackTrace();

        }

        return actualizado;
    }
}