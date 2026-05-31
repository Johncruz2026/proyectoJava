package servlets;

import Dao.AsistenciaDAO;
import modelo.Asistencia;
import modelo.Usuario;
import modelo.UsuarioDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

@WebServlet("/AsistenciaServlet")
public class AsistenciaServlet extends HttpServlet {

    AsistenciaDAO dao = new AsistenciaDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
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
        resp.setStatus(200);
    }

    // =========================
    // GET - Listar asistencia o usuarios
    // =========================
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        String tipo = req.getParameter("tipo");
        PrintWriter out = resp.getWriter();

        if ("usuarios".equals(tipo)) {
            // Devolver lista de usuarios
            ArrayList<Usuario> usuarios = usuarioDAO.listarUsuarios();
            out.write(gson.toJson(usuarios));
        } else {
            // Devolver historial de asistencia
            List<Asistencia> lista = dao.listar();
            out.write(gson.toJson(lista));
        }
    }

    // =========================
    // POST - Registrar entrada o salida
    // =========================
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setContentType("application/json;charset=UTF-8");

        String accion = req.getParameter("accion");
        int usuarioId = Integer.parseInt(req.getParameter("usuarioId"));

        boolean resultado = false;
        String mensaje = "";

        if ("entrada".equals(accion)) {
            resultado = dao.registrarEntrada(usuarioId);
            mensaje = resultado
                ? "Entrada registrada correctamente"
                : "El usuario ya tiene una entrada activa hoy";
        } else if ("salida".equals(accion)) {
            resultado = dao.registrarSalida(usuarioId);
            mensaje = resultado
                ? "Salida registrada correctamente"
                : "El usuario no tiene una entrada activa hoy";
        }

        PrintWriter out = resp.getWriter();
        out.write("{\"success\":" + resultado + ",\"mensaje\":\"" + mensaje + "\"}");
    }
}
