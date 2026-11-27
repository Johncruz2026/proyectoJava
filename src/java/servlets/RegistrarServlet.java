package servlets;
import conexion.Conexion;
import util.PasswordUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegistrarServlet")
public class RegistrarServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();

        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String tipoDocumento = request.getParameter("tipo_documento");
        String numeroDocumento = request.getParameter("numero_documento");
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");
        String confirmar = request.getParameter("confirmar");

        try {
            Conexion cn = new Conexion();
            Connection con = cn.conectar();

            if (!contrasena.equals(confirmar)) {
                out.print("{\"success\":false,\"mensaje\":\"Las contrasenas no coinciden\"}");
                return;
            }

            String validar = "SELECT * FROM usuarios WHERE correo=? OR numero_documento=?";
            PreparedStatement psValidar = con.prepareStatement(validar);
            psValidar.setString(1, correo);
            psValidar.setString(2, numeroDocumento);
            ResultSet rs = psValidar.executeQuery();

            if (rs.next()) {
                out.print("{\"success\":false,\"mensaje\":\"El usuario ya esta registrado\"}");
            } else {
                String sql = "INSERT INTO usuarios (nombre, apellido, tipo_documento, numero_documento, correo, contrasena) VALUES (?,?,?,?,?,?)";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, nombre);
                ps.setString(2, apellido);
                ps.setString(3, tipoDocumento);
                ps.setString(4, numeroDocumento);
                ps.setString(5, correo);
                ps.setString(6, PasswordUtil.hash(contrasena));
                ps.executeUpdate();
                out.print("{\"success\":true,\"mensaje\":\"Usuario registrado correctamente\"}");
            }

            con.close();

        } catch (Exception e) {
            e.printStackTrace();
            out.print("{\"success\":false,\"mensaje\":\"Error en el servidor\"}");
        }
    }
}