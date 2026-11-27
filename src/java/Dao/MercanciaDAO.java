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

        String sql = "SELECT * FROM mercancia";

        try {

            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Mercancia m = new Mercancia();

                m.setId(rs.getInt("id"));
                m.setNombreProducto(rs.getString("nombre_producto"));
                m.setCategoria(rs.getString("categoria"));
                m.setCantidad(rs.getInt("cantidad"));
                m.setUbicacion(rs.getString("ubicacion"));
                m.setEstadoProducto(rs.getString("estado_producto"));
                m.setLote(rs.getString("lote"));
                m.setFechaCaducidad(rs.getString("fecha_caducidad"));
                m.setPeso(rs.getDouble("peso"));

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

            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            String sqlVerificar = "SELECT COUNT(*) FROM mercancia WHERE nombre_producto = ?";

            PreparedStatement psVerificar = con.prepareStatement(sqlVerificar);
            psVerificar.setString(1, m.getNombreProducto());

            ResultSet rs = psVerificar.executeQuery();
            rs.next();
            int cantidad = rs.getInt(1);

            if (cantidad > 0) {
                return false;
            }

            String sql = "INSERT INTO mercancia "
                    + "(nombre_producto, categoria, cantidad, ubicacion, "
                    + "estado_producto, lote, fecha_caducidad, peso) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, m.getNombreProducto());
            ps.setString(2, m.getCategoria());
            ps.setInt(3, m.getCantidad());
            ps.setString(4, m.getUbicacion());
            ps.setString(5, m.getEstadoProducto());
            ps.setString(6, m.getLote());
            ps.setString(7, m.getFechaCaducidad());
            ps.setDouble(8, m.getPeso());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error insertar mercancia: " + e);
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // ACTUALIZAR MERCANCÍA (nuevo)
    // Usado por "Renovar lote" y "Dar de baja" en el frontend:
    // ambos reemplazan los datos del producto sin borrar el registro.
    // =========================

    public boolean actualizar(Mercancia m) {

        String sql = "UPDATE mercancia SET "
                + "nombre_producto = ?, categoria = ?, ubicacion = ?, "
                + "estado_producto = ?, lote = ?, fecha_caducidad = ?, "
                + "peso = ?, cantidad = ? "
                + "WHERE id = ?";

        try {

            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, m.getNombreProducto());
            ps.setString(2, m.getCategoria());
            ps.setString(3, m.getUbicacion());
            ps.setString(4, m.getEstadoProducto());
            ps.setString(5, m.getLote());
            ps.setString(6, m.getFechaCaducidad());
            ps.setDouble(7, m.getPeso());
            ps.setInt(8, m.getCantidad());
            ps.setInt(9, m.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error actualizar mercancia: " + e);
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // ELIMINAR MERCANCÍA
    // =========================

    public boolean eliminar(int id) {

        String sql = "DELETE FROM mercancia WHERE id = ?";

        try {

            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

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

        String sql = "SELECT * FROM movimientos_mercancia ORDER BY id DESC";

        try {

            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                MovimientoMercancia mov = new MovimientoMercancia();

                mov.setMercanciaId(rs.getInt("mercancia_id"));
                mov.setTipo(rs.getString("tipo"));
                mov.setCantidad(rs.getInt("cantidad"));
                mov.setObservacion(rs.getString("observacion"));

                lista.add(mov);
            }

        } catch (Exception e) {
            System.out.println("Error listar movimientos: " + e);
            e.printStackTrace();
        }

        return lista;
    }

    // =========================
    // REGISTRAR MOVIMIENTO (corregido)
    //
    // Antes: solo insertaba en movimientos_mercancia y nunca tocaba
    // mercancia.cantidad. Por eso el stock quedaba desincronizado del
    // historial (así llegó a quedar el Queso Gouda en -10).
    //
    // Ahora: valida el stock disponible para una salida, inserta el
    // movimiento Y ajusta mercancia.cantidad, todo en una transacción —
    // si algo falla a mitad de camino, no queda ninguna de las dos
    // escrituras a medias.
    //
    // Cambia de boolean a String: devuelve null si todo salió bien,
    // o el mensaje de error si algo falló (por ejemplo, stock
    // insuficiente). MercanciaServlet debe leer este valor.
    // =========================

    public String registrarMovimiento(MovimientoMercancia mov) {

        Connection con = null;

        try {

            Conexion cn = new Conexion();
            con = cn.conectar();
            con.setAutoCommit(false);

            // 1. Si es salida, comprobar que haya stock suficiente.
            if ("salida".equals(mov.getTipo())) {

                String sqlStock = "SELECT cantidad FROM mercancia WHERE id = ?";
                PreparedStatement psStock = con.prepareStatement(sqlStock);
                psStock.setInt(1, mov.getMercanciaId());

                ResultSet rs = psStock.executeQuery();

                if (!rs.next()) {
                    con.rollback();
                    return "No existe un producto con ese id";
                }

                int stockActual = rs.getInt("cantidad");

                if (stockActual < mov.getCantidad()) {
                    con.rollback();
                    return "Stock insuficiente: quedan " + stockActual + " unidades";
                }
            }

            // 2. Insertar el movimiento en el historial.
            String sqlInsertar = "INSERT INTO movimientos_mercancia "
                    + "(mercancia_id, tipo, cantidad, observacion) "
                    + "VALUES (?, ?, ?, ?)";

            PreparedStatement psInsertar = con.prepareStatement(sqlInsertar);
            psInsertar.setInt(1, mov.getMercanciaId());
            psInsertar.setString(2, mov.getTipo());
            psInsertar.setInt(3, mov.getCantidad());
            psInsertar.setString(4, mov.getObservacion());
            psInsertar.executeUpdate();

            // 3. Ajustar el stock real del producto.
            String sqlAjustar = "entrada".equals(mov.getTipo())
                    ? "UPDATE mercancia SET cantidad = cantidad + ? WHERE id = ?"
                    : "UPDATE mercancia SET cantidad = cantidad - ? WHERE id = ?";

            PreparedStatement psAjustar = con.prepareStatement(sqlAjustar);
            psAjustar.setInt(1, mov.getCantidad());
            psAjustar.setInt(2, mov.getMercanciaId());
            psAjustar.executeUpdate();

            con.commit();
            return null; // sin error

        } catch (Exception e) {

            try {
                if (con != null) con.rollback();
            } catch (Exception ignorado) { }

            System.out.println("Error registrar movimiento: " + e);
            e.printStackTrace();
            return "Error de base de datos al registrar el movimiento";

        } finally {

            try {
                if (con != null) con.setAutoCommit(true);
            } catch (Exception ignorado) { }
        }
    }
}