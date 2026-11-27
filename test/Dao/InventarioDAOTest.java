package Dao;

import modelo.Inventario;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

public class InventarioDAOTest {

    @Test
    public void testInsertar() {
        System.out.println("insertar");

        Inventario i = new Inventario();
        i.setNombreProducto("Arroz 500g - PRUEBA");
        i.setCategoria("Granos");
        i.setCantidad(50);
        i.setUbicacion("Bodega 1");
        i.setEstadoProducto("Disponible");

        InventarioDAO instance = new InventarioDAO();
        boolean result = instance.insertar(i);

        assertTrue("El producto de prueba debería insertarse correctamente en la base de datos", result);
    }

    @Test
    public void testListar() {
        System.out.println("listar");

        InventarioDAO instance = new InventarioDAO();
        List<Inventario> result = instance.listar();

        assertNotNull("La lista no debería ser nula", result);
        assertFalse("La lista de inventario debería contener al menos un producto", result.isEmpty());
    }
}