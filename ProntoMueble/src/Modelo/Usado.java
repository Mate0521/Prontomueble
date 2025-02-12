
package Modelo;

/**
 *
 * @author Mateo
 */
public class Usado extends Mueble{
    private String id_devolucion;

    public Usado(String id_devolucion, String nombre, String dimenciones, String color, String material,int cantidad) {
        super(nombre, dimenciones, color, material, color,cantidad);
        this.id_devolucion = id_devolucion;
    }
    
    public Usado() {
        super();
        this.id_devolucion = "";
    }

    public String getId_devolucion() {
        return id_devolucion;
    }

    public void setId_devolucion(String id_devolucion) {
        this.id_devolucion = id_devolucion;
    }

    @Override
    public String toString() {
        return super.toString()+ "\nId_devolucion=" + id_devolucion ;
    }
    
    
}
