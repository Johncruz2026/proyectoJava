package servlets;

import Dao.AsistenciaDAO;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cerrarAsistencia")
public class CerrarAsistenciaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Permitir conexión React
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Tipo respuesta
        response.setContentType("application/json;charset=UTF-8");

        int id = Integer.parseInt(request.getParameter("id"));
        String horaSalida = request.getParameter("horaSalida"); // formato HH:mm o HH:mm:ss

        AsistenciaDAO dao = new AsistenciaDAO();
        boolean cerrado = dao.cerrarManualmente(id, horaSalida);

        PrintWriter out = response.getWriter();

        if (cerrado) {
            out.print("{\"mensaje\":\"Turno cerrado correctamente\"}");
        } else {
            out.print("{\"mensaje\":\"No se pudo cerrar el turno. Puede que ya estuviera cerrado.\"}");
        }
    }
}