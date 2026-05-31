package servlets;

import modelo.Equipo;
import modelo.EquipoDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/listarEquipos")
public class ListarEquiposServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Permitir conexión React
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Tipo respuesta
        response.setContentType("application/json;charset=UTF-8");

        // Crear DAO
        EquipoDAO dao = new EquipoDAO();

        // Obtener lista
        ArrayList<Equipo> lista = dao.listarEquipos();

        // Crear respuesta JSON
        PrintWriter out = response.getWriter();

        // Abrir arreglo JSON
        out.print("[");

        // Recorrer lista
        for (int i = 0; i < lista.size(); i++) {

            Equipo equipo = lista.get(i);

            out.print("{");

            out.print("\"id\":\"" + equipo.getId() + "\",");
            out.print("\"nombreEquipo\":\"" + equipo.getNombreEquipo().trim() + "\",");
            out.print("\"marca\":\"" + equipo.getMarca().trim() + "\",");
            out.print("\"serialEquipo\":\"" + equipo.getSerialEquipo().trim() + "\",");
            out.print("\"estadoEquipo\":\"" + equipo.getEstadoEquipo().trim() + "\"");

            out.print("}");

            // Agregar coma excepto último registro
            if (i < lista.size() - 1) {

                out.print(",");

            }

        }

        // Cerrar arreglo JSON
        out.print("]");

    }
}
