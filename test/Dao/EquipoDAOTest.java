package Dao;

import java.util.ArrayList;
import modelo.Equipo;
import modelo.EquipoDAO;
import org.junit.Test;
import static org.junit.Assert.*;

public class EquipoDAOTest {

    // Serial único por ejecución, para evitar conflictos si el test se corre varias veces
    private String generarSerialUnico() {
        return "SN-TEST-" + System.currentTimeMillis();
    }

    @Test
    public void testGuardarEquipo() {
        System.out.println("guardarEquipo");

        Equipo equipo = new Equipo();
        equipo.setNombreEquipo("Computador de prueba");
        equipo.setMarca("HP");
        equipo.setSerialEquipo(generarSerialUnico());
        equipo.setEstadoEquipo("Disponible");

        EquipoDAO instance = new EquipoDAO();
        boolean result = instance.guardarEquipo(equipo);

        assertTrue("El equipo debería guardarse correctamente con un serial nuevo", result);
    }

    @Test
    public void testListarEquipos() {
        System.out.println("listarEquipos");

        EquipoDAO instance = new EquipoDAO();
        ArrayList<Equipo> result = instance.listarEquipos();

        assertNotNull("La lista no debería ser nula", result);
        assertFalse("La lista debería contener al menos un equipo", result.isEmpty());
    }

    @Test
    public void testActualizarYEliminarEquipo() {
        System.out.println("actualizarEquipo / eliminarEquipo");
        EquipoDAO instance = new EquipoDAO();

        // 1. Crear un equipo de prueba para poder actualizarlo y eliminarlo
        Equipo nuevo = new Equipo();
        nuevo.setNombreEquipo("Equipo temporal");
        nuevo.setMarca("Dell");
        nuevo.setSerialEquipo(generarSerialUnico());
        nuevo.setEstadoEquipo("Disponible");
        boolean guardado = instance.guardarEquipo(nuevo);
        assertTrue("El equipo temporal debería guardarse antes de poder actualizarlo", guardado);

        // 2. Buscar el equipo recién creado para obtener su id real
        ArrayList<Equipo> lista = instance.listarEquipos();
        Equipo creado = null;
        for (Equipo e : lista) {
            if (e.getSerialEquipo().equals(nuevo.getSerialEquipo())) {
                creado = e;
                break;
            }
        }
        assertNotNull("Debería encontrarse el equipo recién creado en el listado", creado);

        // 3. Actualizar el equipo encontrado
        creado.setEstadoEquipo("En mantenimiento");
        boolean actualizado = instance.actualizarEquipo(creado);
        assertTrue("El equipo debería actualizarse correctamente", actualizado);

        // 4. Eliminar el equipo de prueba para no dejar basura en la base de datos
        boolean eliminado = instance.eliminarEquipo(creado.getId());
        assertTrue("El equipo debería eliminarse correctamente", eliminado);
    }
}