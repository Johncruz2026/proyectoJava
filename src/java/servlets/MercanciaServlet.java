package servlets;

import Dao.MercanciaDAO;
import modelo.Mercancia;
import modelo.MovimientoMercancia;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;

@WebServlet("/mercanciaServlet")
public class MercanciaServlet extends HttpServlet {

    MercanciaDAO dao = new MercanciaDAO();
    Gson gson = new Gson();

    // =========================
    // CORS - Permitir React
    // =========================

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, OPTIONS");
        resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    // =========================
    // GET - Listar mercancia o movimientos
    // =========================

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        String tipo = req.getParameter("tipo");

        if ("movimientos".equals(tipo)) {
            // Listar historial de movimientos
            List<MovimientoMercancia> lista = dao.listarMovimientos();
            resp.getWriter().write(gson.toJson(lista));
        } else {
            // Listar todos los productos de mercancia
            List<Mercancia> lista = dao.listar();
            resp.getWriter().write(gson.toJson(lista));
        }
    }

    // =========================
    // POST - Insertar mercancia o registrar movimiento
    // =========================

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        String accion = req.getParameter("accion");

        if ("movimiento".equals(accion)) {

            // Registrar entrada o salida de mercancia
            MovimientoMercancia mov = new MovimientoMercancia();

            mov.setMercanciaId(Integer.parseInt(req.getParameter("mercanciaId")));
            mov.setTipo(req.getParameter("tipo"));
            mov.setCantidad(Integer.parseInt(req.getParameter("cantidad")));
            mov.setObservacion(req.getParameter("observacion"));

            boolean resultado = dao.registrarMovimiento(mov);

            if (resultado) {
                resp.getWriter().write("{\"mensaje\":\"Movimiento registrado correctamente\"}");
            } else {
                resp.getWriter().write("{\"mensaje\":\"Error al registrar movimiento\"}");
            }

        } else {

            // Insertar nuevo producto de mercancia
            Mercancia m = new Mercancia();

            m.setNombreProducto(req.getParameter("nombreProducto"));
            m.setCategoria(req.getParameter("categoria"));
            m.setCantidad(Integer.parseInt(req.getParameter("cantidad")));
            m.setUbicacion(req.getParameter("ubicacion"));
            m.setEstadoProducto(req.getParameter("estadoProducto"));
            m.setLote(req.getParameter("lote"));
            m.setFechaCaducidad(req.getParameter("fechaCaducidad"));
            m.setPeso(Double.parseDouble(req.getParameter("peso")));

            boolean resultado = dao.insertar(m);

            if (resultado) {
                resp.getWriter().write("{\"mensaje\":\"Mercancia registrada correctamente\"}");
            } else {
                resp.getWriter().write("{\"mensaje\":\"Error al registrar mercancia\"}");
            }
        }
    }

    // =========================
    // DELETE - Eliminar mercancia
    // =========================

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        int id = Integer.parseInt(req.getParameter("id"));

        boolean resultado = dao.eliminar(id);

        if (resultado) {
            resp.getWriter().write("{\"mensaje\":\"Mercancia eliminada correctamente\"}");
        } else {
            resp.getWriter().write("{\"mensaje\":\"Error al eliminar mercancia\"}");
        }
    }
}