package servlets;

import conexion.Conexion;
import java.sql.SQLException;

public class PruebaConexion {
    public static void main(String[] args) {
        try {
            Conexion con = new Conexion();
            con.conectar();
            System.out.println("Conexión exitosa");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}