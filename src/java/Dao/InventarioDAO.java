package Dao;

import conexion.Conexion;
import modelo.Inventario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {

    // =========================
    // LISTAR INVENTARIO
    // =========================

    public List<Inventario> listar() {

        List<Inventario> lista = new ArrayList<>();

        try {

            // Crear conexión
            Connection con = new Conexion().conectar();

            // SQL listar
            String sql = "SELECT * FROM inventario";

            // Preparar y ejecutar
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Recorrer resultados
            while (rs.next()) {

                // Crear objeto inventario
                Inventario i = new Inventario();

                // Mapear columnas
                i.setId(rs.getInt("id"));
                i.setNombreProducto(rs.getString("nombre_producto"));
                i.setCategoria(rs.getString("categoria"));
                i.setCantidad(rs.getInt("cantidad"));
                i.setUbicacion(rs.getString("ubicacion"));
                i.setEstadoProducto(rs.getString("estado_producto"));

                // Agregar a lista
                lista.add(i);
            }

        } catch (Exception e) {
            System.out.println("Error listar inventario: " + e);
        }

        return lista;
    }

    // =========================
    // INSERTAR EN INVENTARIO
    // =========================

    public boolean insertar(Inventario i) {

        try {

            // Crear conexión
            Connection con = new Conexion().conectar();

            // SQL insertar
            String sql = "INSERT INTO inventario(nombre_producto, categoria, cantidad, ubicacion, estado_producto) "
                    + "VALUES (?, ?, ?, ?, ?)";

            // Preparar consulta
            PreparedStatement ps = con.prepareStatement(sql);

            // Enviar datos
            ps.setString(1, i.getNombreProducto());
            ps.setString(2, i.getCategoria());
            ps.setInt(3, i.getCantidad());
            ps.setString(4, i.getUbicacion());
            ps.setString(5, i.getEstadoProducto());

            // Ejecutar
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error insertar inventario: " + e);
            return false;
        }
    }
}