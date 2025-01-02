package Modelo;

/**
 * @author Mateo
 */
public class Mueble {
    private String nombre, dimenciones, color, material ;

    public Mueble(String nombre, String dimenciones, String color, String material) {
        this.nombre = nombre;
        this.dimenciones = dimenciones;
        this.color = color;
        this.material = material;
    }
        public Mueble() {
        this.nombre = "";
        this.dimenciones = "";
        this.color = "";
        this.material = "";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDimenciones() {
        return dimenciones;
    }

    public void setDimenciones(String dimenciones) {
        this.dimenciones = dimenciones;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "Mueble: \nnombre=" + nombre + "\nDimenciones=" + dimenciones + "\nColor=" + color + "\nMaterial=" + material ;
    }
            
        
}
