package servlets;

import modelo.Equipo;
import modelo.EquipoDAO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/actualizarEquipo")
public class ActualizarEquipoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Permitir conexión React
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Tipo respuesta
        response.setContentType("application/json;charset=UTF-8");

        // Obtener datos
        int id = Integer.parseInt(request.getParameter("id"));

        String nombreEquipo = request.getParameter("nombreEquipo");
        String marca = request.getParameter("marca");
        String serialEquipo = request.getParameter("serialEquipo");
        String estadoEquipo = request.getParameter("estadoEquipo");

        // Crear objeto
        Equipo equipo = new Equipo();

        equipo.setId(id);
        equipo.setNombreEquipo(nombreEquipo);
        equipo.setMarca(marca);
        equipo.setSerialEquipo(serialEquipo);
        equipo.setEstadoEquipo(estadoEquipo);

        // Crear DAO
        EquipoDAO dao = new EquipoDAO();

        // Actualizar
        boolean actualizado = dao.actualizarEquipo(equipo);

        // Respuesta
        PrintWriter out = response.getWriter();

        if (actualizado) {

            out.print("{\"mensaje\":\"Equipo actualizado correctamente\"}");

        } else {

            out.print("{\"mensaje\":\"Error al actualizar equipo\"}");

        }

    }
}
