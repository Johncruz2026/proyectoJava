package servlets;

import modelo.UsuarioDAO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    // Método POST para recibir datos del login
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
  // Permitir conexión desde React
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS");
    response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        // Obtener datos enviados desde formulario
        String correo = request.getParameter("correo");
        String contrasena = request.getParameter("contrasena");

        // Crear objeto DAO
        UsuarioDAO dao = new UsuarioDAO();

        // Validar acceso
        boolean acceso = dao.validarLogin(correo, contrasena);

        // Respuesta del servidor
response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (acceso) {

    out.println("{\"mensaje\":\"Login exitoso\"}");

} else {

    out.println("{\"mensaje\":\"Correo o contraseña incorrectos\"}");

}
    }
}
