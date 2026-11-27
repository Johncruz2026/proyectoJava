package Dao;

import java.util.ArrayList;
import modelo.Usuario;
import modelo.UsuarioDAO;
import org.junit.Test;
import static org.junit.Assert.*;

public class UsuarioDAOTest {

    @Test
    public void testListarUsuarios() {
        System.out.println("listarUsuarios");

        UsuarioDAO instance = new UsuarioDAO();
        ArrayList<Usuario> result = instance.listarUsuarios();

        assertNotNull("La lista no debería ser nula", result);
        assertFalse("La lista debería contener al menos un usuario registrado", result.isEmpty());
    }

    @Test
    public void testValidarLoginConCredencialesInvalidas() {
        System.out.println("validarLoginConCredencialesInvalidas");

        UsuarioDAO instance = new UsuarioDAO();
        boolean acceso = instance.validarLogin("correo_que_no_existe@test.com", "contrasenaIncorrecta123");

        assertFalse("El login no debería ser exitoso con un correo y contraseña inexistentes", acceso);
    }

    // TODO: agregar testValidarLoginConCredencialesValidas() una vez se tenga
    // un correo y contraseña reales existentes en la base de datos de pruebas.
}