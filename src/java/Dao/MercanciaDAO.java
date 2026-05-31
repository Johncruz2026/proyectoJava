package Dao;

import conexion.Conexion;
import modelo.Mercancia;
import modelo.MovimientoMercancia;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MercanciaDAO {

    // =========================
    // LISTAR MERCANCÍA
    // =========================

    public List<Mercancia> listar() {

        List<Mercancia> lista = new ArrayList<>();

        // SQL listar todos los productos
        String sql = "SELECT * FROM mercancia";

        try {

            // Crear conexión
            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            // Preparar consulta
            PreparedStatement ps = con.prepareStatement(sql);

            // Ejecutar
            ResultSet rs = ps.executeQuery();

            // Recorrer resultados
            while (rs.next()) {

                // Crear objeto mercancia
                Mercancia m = new Mercancia();

                // Mapear columnas de la base de datos al objeto
                m.setId(rs.getInt("id"));
                m.setNombreProducto(rs.getString("nombre_producto"));
                m.setCategoria(rs.getString("categoria"));
                m.setCantidad(rs.getInt("cantidad"));
                m.setUbicacion(rs.getString("ubicacion"));
                m.setEstadoProducto(rs.getString("estado_producto"));
                m.setLote(rs.getString("lote"));
                m.setFechaCaducidad(rs.getString("fecha_caducidad"));
                m.setPeso(rs.getDouble("peso"));

                // Agregar a la lista
                lista.add(m);
            }

        } catch (Exception e) {
            System.out.println("Error listar mercancia: " + e);
            e.printStackTrace();
        }

        return lista;
    }

    // =========================
    // INSERTAR MERCANCÍA
    // =========================

    public boolean insertar(Mercancia m) {

        try {

            // Crear conexión
            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            // =========================
            // VALIDAR NOMBRE DUPLICADO
            // Verifica si ya existe un producto con el mismo nombre
            // =========================

            String sqlVerificar = "SELECT COUNT(*) FROM mercancia WHERE nombre_producto = ?";

            PreparedStatement psVerificar = con.prepareStatement(sqlVerificar);

            // Enviar nombre a verificar
            psVerificar.setString(1, m.getNombreProducto());

            // Ejecutar verificación
            ResultSet rs = psVerificar.executeQuery();
            rs.next();
            int cantidad = rs.getInt(1);

            // Si ya existe un producto con ese nombre, no insertar
            if (cantidad > 0) {
                return false;
            }

            // =========================
            // INSERTAR PRODUCTO NUEVO
            // =========================

            // SQL insertar con nombres de columnas correctos
            String sql = "INSERT INTO mercancia "
                    + "(nombre_producto, categoria, cantidad, ubicacion, "
                    + "estado_producto, lote, fecha_caducidad, peso) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            // Preparar consulta
            PreparedStatement ps = con.prepareStatement(sql);

            // Enviar datos
            ps.setString(1, m.getNombreProducto());
            ps.setString(2, m.getCategoria());
            ps.setInt(3, m.getCantidad());
            ps.setString(4, m.getUbicacion());
            ps.setString(5, m.getEstadoProducto());
            ps.setString(6, m.getLote());
            ps.setString(7, m.getFechaCaducidad());
            ps.setDouble(8, m.getPeso());

            // Ejecutar y retornar resultado
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error insertar mercancia: " + e);
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // ELIMINAR MERCANCÍA
    // =========================

    public boolean eliminar(int id) {

        // SQL eliminar por id
        String sql = "DELETE FROM mercancia WHERE id = ?";

        try {

            // Crear conexión
            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            // Preparar consulta
            PreparedStatement ps = con.prepareStatement(sql);

            // Enviar id
            ps.setInt(1, id);

            // Ejecutar y retornar resultado
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error eliminar mercancia: " + e);
            e.printStackTrace();
            return false;
        }
    }

    // =========================================================================
    // MÉTODOS PARA LA ENTIDAD: MOVIMIENTO MERCANCÍA
    // =========================================================================

    // =========================
    // LISTAR MOVIMIENTOS
    // =========================

    public List<MovimientoMercancia> listarMovimientos() {

        List<MovimientoMercancia> lista = new ArrayList<>();

        // SQL listar todos los movimientos
        String sql = "SELECT * FROM movimientos_mercancia";

        try {

            // Crear conexión
            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            // Preparar consulta
            PreparedStatement ps = con.prepareStatement(sql);

            // Ejecutar
            ResultSet rs = ps.executeQuery();

            // Recorrer resultados
            while (rs.next()) {

                // Crear objeto movimiento
                MovimientoMercancia mov = new MovimientoMercancia();

                // Mapear columnas de la base de datos al objeto
                mov.setMercanciaId(rs.getInt("mercancia_id"));
                mov.setTipo(rs.getString("tipo"));
                mov.setCantidad(rs.getInt("cantidad"));
                mov.setObservacion(rs.getString("observacion"));

                // Agregar a la lista
                lista.add(mov);
            }

        } catch (Exception e) {
            System.out.println("Error listar movimientos: " + e);
            e.printStackTrace();
        }

        return lista;
    }

    // =========================
    // REGISTRAR MOVIMIENTO
    // =========================

    public boolean registrarMovimiento(MovimientoMercancia mov) {

        // SQL insertar movimiento con nombres de columnas correctos
        String sql = "INSERT INTO movimientos_mercancia "
                + "(mercancia_id, tipo, cantidad, observacion) "
                + "VALUES (?, ?, ?, ?)";

        try {

            // Crear conexión
            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            // Preparar consulta
            PreparedStatement ps = con.prepareStatement(sql);

            // Enviar datos
            ps.setInt(1, mov.getMercanciaId());
            ps.setString(2, mov.getTipo());
            ps.setInt(3, mov.getCantidad());
            ps.setString(4, mov.getObservacion());

            // Ejecutar y retornar resultado
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error registrar movimiento: " + e);
            e.printStackTrace();
            return false;
        }
    }
}