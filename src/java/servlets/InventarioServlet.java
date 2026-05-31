package servlets;

import Dao.InventarioDAO;
import modelo.Inventario;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/InventarioServlet")
public class InventarioServlet extends HttpServlet {

    InventarioDAO dao = new InventarioDAO();
    Gson gson = new Gson();

    // =========================
    // CORS - Permitir React
    // =========================

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    // =========================
    // GET - Listar inventario
    // =========================

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        // Obtener lista de inventario
        List<Inventario> lista = dao.listar();

        // Retornar lista en JSON
        resp.getWriter().write(gson.toJson(lista));
    }

    // =========================
    // POST - Insertar en inventario
    // =========================

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        // Obtener parámetros del formulario
        Inventario i = new Inventario();
        i.setNombreProducto(req.getParameter("nombreProducto"));
        i.setCategoria(req.getParameter("categoria"));
        i.setCantidad(Integer.parseInt(req.getParameter("cantidad")));
        i.setUbicacion(req.getParameter("ubicacion"));
        i.setEstadoProducto(req.getParameter("estadoProducto"));

        // Insertar en base de datos
        boolean resultado = dao.insertar(i);

        // Retornar respuesta
        PrintWriter out = resp.getWriter();
        if (resultado) {
            out.write("{\"mensaje\":\"Inventario registrado correctamente\"}");
        } else {
            out.write("{\"mensaje\":\"Error al registrar en inventario\"}");
        }
    }
}