package Dao;

import java.util.List;
import modelo.Mercancia;
import modelo.MovimientoMercancia;
import org.junit.Test;
import static org.junit.Assert.*;

public class MercanciaDAOTest {

    // Nombre único por ejecución, ya que insertar() rechaza nombres duplicados
    private String generarNombreUnico() {
        return "Producto Prueba " + System.currentTimeMillis();
    }

    @Test
    public void testInsertarYListar() {
        System.out.println("insertar / listar");

        Mercancia m = new Mercancia();
        m.setNombreProducto(generarNombreUnico());
        m.setCategoria("Granos");
        m.setCantidad(30);
        m.setUbicacion("Bodega 2");
        m.setEstadoProducto("Disponible");
        m.setLote("L-001");
    }
}