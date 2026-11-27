package Dao;

import conexion.Conexion;
import modelo.Inventario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioDAO {

    public List<Inventario> listar() {
        List<Inventario> lista = new ArrayList<>();
        try {
            Connection con = new Conexion().conectar();
            String sql = "SELECT * FROM inventario";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Inventario i = new Inventario();
                i.setId(rs.getInt("id"));
                i.setNombreProducto(rs.getString("nombre_producto"));
                i.setCategoria(rs.getString("categoria"));
                i.setCantidad(rs.getInt("cantidad"));
                i.setUbicacion(rs.getString("ubicacion"));
                i.setEstadoProducto(rs.getString("estado_producto"));
                lista.add(i);
            }
        } catch (Exception e) {
            System.out.println("Error listar inventario: " + e);
        }
        return lista;
    }

    public boolean insertar(Inventario i) {
        try {
            Connection con = new Conexion().conectar();
            String sql = "INSERT INTO inventario(nombre_producto, categoria, cantidad, ubicacion, estado_producto) "
                    + "VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, i.getNombreProducto());
            ps.setString(2, i.getCategoria());
            ps.setInt(3, i.getCantidad());
            ps.setString(4, i.getUbicacion());
            ps.setString(5, i.getEstadoProducto());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.out.println("Error insertar inventario: " + e);
            return false;
        }
    }
}