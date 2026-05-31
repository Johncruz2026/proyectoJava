package modelo;

public class MovimientoMercancia {
    private int id;
    private int mercanciaId;
    private String tipo; // "entrada" o "salida"
    private int cantidad;
    private String fecha;
    private String observacion;

    public MovimientoMercancia() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMercanciaId() { return mercanciaId; }
    public void setMercanciaId(int mercanciaId) { this.mercanciaId = mercanciaId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }

    public String getObservacion() { return observacion; }
    public void setObservacion(String observacion) { this.observacion = observacion; }
}