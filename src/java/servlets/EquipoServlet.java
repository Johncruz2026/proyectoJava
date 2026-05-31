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

@WebServlet("/guardarEquipo")
public class EquipoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Permitir conexión React
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Tipo respuesta
        response.setContentType("application/json;charset=UTF-8");

        // Obtener datos
        String nombreEquipo = request.getParameter("nombreEquipo");
        String marca = request.getParameter("marca");
        String serialEquipo = request.getParameter("serialEquipo");
        String estadoEquipo = request.getParameter("estadoEquipo");

        // Crear objeto
        Equipo equipo = new Equipo();

        equipo.setNombreEquipo(nombreEquipo.trim());
        equipo.setMarca(marca.trim());
        equipo.setSerialEquipo(serialEquipo.trim());
        equipo.setEstadoEquipo(estadoEquipo.trim());

        // Crear DAO
        EquipoDAO dao = new EquipoDAO();

        // Guardar
        boolean registro = dao.guardarEquipo(equipo);

        // Respuesta
        PrintWriter out = response.getWriter();

        // Validar
        if (registro) {

            out.print("{\"mensaje\":\"Equipo guardado correctamente\"}");

        } else {

            out.print("{\"mensaje\":\"Error al registrar equipo\"}");

        }

    }
}
