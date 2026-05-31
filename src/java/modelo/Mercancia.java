package modelo;

public class Mercancia {
    private int id;
    private String nombreProducto;
    private String categoria;
    private int cantidad;
    private String ubicacion;
    private String estadoProducto;
    private String lote;
    private String fechaCaducidad;
    private double peso;

    public Mercancia() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }

    public String getEstadoProducto() { return estadoProducto; }
    public void setEstadoProducto(String estadoProducto) { this.estadoProducto = estadoProducto; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public String getFechaCaducidad() { return fechaCaducidad; }
    public void setFechaCaducidad(String fechaCaducidad) { this.fechaCaducidad = fechaCaducidad; }

    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
}
