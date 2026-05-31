package servlets;

import modelo.EquipoDAO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/eliminarEquipo")
public class EliminarEquipoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Permitir conexión React
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Tipo respuesta
        response.setContentType("application/json;charset=UTF-8");

        // Obtener id
        int id = Integer.parseInt(request.getParameter("id"));

        // Crear DAO
        EquipoDAO dao = new EquipoDAO();

        // Eliminar
        boolean eliminado = dao.eliminarEquipo(id);

        // Respuesta
        PrintWriter out = response.getWriter();

        if (eliminado) {

            out.print("{\"mensaje\":\"Equipo eliminado correctamente\"}");

        } else {

            out.print("{\"mensaje\":\"Error al eliminar equipo\"}");

        }

    }
}
