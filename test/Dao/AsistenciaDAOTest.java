package Dao;

import modelo.UsuarioDAO;
import modelo.Asistencia;
import modelo.Usuario;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class AsistenciaDAOTest {

    /**
     * Obtiene un usuario real ya existente en la base de datos,
     * para evitar el error de llave foránea (usuario_id inexistente).
     */
    private int obtenerUsuarioIdValido() {
        UsuarioDAO usuarioDao = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDao.listarUsuarios();
        assertFalse("Debe existir al menos un usuario registrado para poder probar asistencia", usuarios.isEmpty());
        return usuarios.get(0).getId();
    }

    @Test
    public void testRegistrarEntradaYSalida() {
        System.out.println("registrarEntrada / registrarSalida");
        int usuarioId = obtenerUsuarioIdValido();
        AsistenciaDAO dao = new AsistenciaDAO();

        // Por si quedó una entrada abierta de una ejecución anterior, se cierra primero
        dao.registrarSalida(usuarioId);

        boolean entrada = dao.registrarEntrada(usuarioId);
        assertTrue("La entrada debería registrarse correctamente para un usuario válido", entrada);

        boolean salida = dao.registrarSalida(usuarioId);
        assertTrue("La salida debería registrarse correctamente tras una entrada activa", salida);
    }

    @Test
    public void testNoPermiteEntradaDuplicadaMismoDia() {
        System.out.println("noPermiteEntradaDuplicada");
        int usuarioId = obtenerUsuarioIdValido();
        AsistenciaDAO dao = new AsistenciaDAO();

        dao.registrarSalida(usuarioId); // asegurar que no haya entrada abierta previa
        boolean primeraEntrada = dao.registrarEntrada(usuarioId);
        boolean segundaEntrada = dao.registrarEntrada(usuarioId); // sin registrar salida entre medio

        assertTrue("La primera entrada del día debería registrarse", primeraEntrada);
        assertFalse("No debería permitir una segunda entrada activa el mismo día sin salida", segundaEntrada);

        dao.registrarSalida(usuarioId); // dejar el estado limpio para la próxima ejecución
    }

    @Test
    public void testListar() {
        System.out.println("listar");
        List<Asistencia> result = new AsistenciaDAO().listar();
        assertNotNull("La lista no debería ser nula", result);
    }
}