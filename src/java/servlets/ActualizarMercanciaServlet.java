package servlets;

import Dao.MercanciaDAO;
import modelo.Mercancia;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/actualizarMercancia")
public class ActualizarMercanciaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Permitir conexión React
        response.setHeader("Access-Control-Allow-Origin", "*");

        // Tipo respuesta
        response.setContentType("application/json;charset=UTF-8");

        // Obtener datos
        int id = Integer.parseInt(request.getParameter("id"));

        String nombreProducto = request.getParameter("nombreProducto");
        String categoria = request.getParameter("categoria");
        String ubicacion = request.getParameter("ubicacion");
        String estadoProducto = request.getParameter("estadoProducto");
        String lote = request.getParameter("lote");
        String fechaCaducidad = request.getParameter("fechaCaducidad");

        double peso = parseDoubleSeguro(request.getParameter("peso"));
        int cantidad = parseIntSeguro(request.getParameter("cantidad"));

        // Crear objeto
        Mercancia m = new Mercancia();

        m.setId(id);
        m.setNombreProducto(nombreProducto);
        m.setCategoria(categoria);
        m.setUbicacion(ubicacion);
        m.setEstadoProducto(estadoProducto);
        m.setLote(lote);
        m.setFechaCaducidad(fechaCaducidad);
        m.setPeso(peso);
        m.setCantidad(cantidad);

        // Crear DAO
        MercanciaDAO dao = new MercanciaDAO();

        // Actualizar
        boolean actualizado = dao.actualizar(m);

        // Respuesta
        PrintWriter out = response.getWriter();

        if (actualizado) {

            out.print("{\"mensaje\":\"Mercancia actualizada correctamente\"}");

        } else {

            out.print("{\"mensaje\":\"Error al actualizar mercancia\"}");

        }
    }

    // Evita que un campo numérico vacío tumbe el servlet con una excepción.
    private double parseDoubleSeguro(String valor) {
        try {
            return (valor == null || valor.isBlank()) ? 0 : Double.parseDouble(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private int parseIntSeguro(String valor) {
        try {
            return (valor == null || valor.isBlank()) ? 0 : Integer.parseInt(valor);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}