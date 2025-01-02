
package Modelo;

/**
 *
 * @author Mateo
 */
public class Usado extends Mueble{
    private String id_muebleUsado, id_devolucion;

    public Usado(String id_muebleUsado, String id_devolucion, String nombre, String dimenciones, String color, String material) {
        super(nombre, dimenciones, color, material);
        this.id_muebleUsado = id_muebleUsado;
        this.id_devolucion = id_devolucion;
    }
    
    public Usado() {
        super();
        this.id_muebleUsado = "";
        this.id_devolucion = "";
    }

    public String getId_muebleUsado() {
        return id_muebleUsado;
    }

    public void setId_muebleUsado(String id_muebleUsado) {
        this.id_muebleUsado = id_muebleUsado;
    }

    public String getId_devolucion() {
        return id_devolucion;
    }

    public void setId_devolucion(String id_devolucion) {
        this.id_devolucion = id_devolucion;
    }

    @Override
    public String toString() {
        return super.toString()+"\nId_muebleUsado=" + id_muebleUsado + "\nId_devolucion=" + id_devolucion ;
    }
    
    
}
