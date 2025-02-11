package Modelo;

/**
 *
 * @author Mateo
 */
public class Devoluciones {
    private String id_venta;
    private String motivo;//lo tipificamos como un archivo? un txt
    private boolean validacion;

    public Devoluciones(String id_venta, String motivo, boolean validacion) {
        this.id_venta = id_venta;
        this.motivo = motivo;
        this.validacion = validacion;
    }
    public Devoluciones() {
        this.id_venta = "";
        this.motivo = "";
        this.validacion = false;
    }

    public String getId_venta() {
        return id_venta;
    }

    public void setId_venta(String id_venta) {
        this.id_venta = id_venta;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public boolean isValidacion() {
        return validacion;
    }

    public void setValidacion(boolean validacion) {
        this.validacion = validacion;
    }

    @Override
    public String toString() {
        return "Devoluciones \nID de la venta=" + id_venta + "\nMotivo=" + motivo + "\nValidacion=" + validacion + '}';
    }
    
    
}
