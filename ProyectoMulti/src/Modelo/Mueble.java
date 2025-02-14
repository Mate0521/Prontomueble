package Modelo;

/**
 * @author Mateo
 */
public abstract class Mueble {
    private String nombre, dimenciones, color, material, id ;
    private int catntidad;

    public Mueble(String nombre, String dimenciones, String color, String material, String id,int cantidad) {
        this.nombre = nombre;
        this.dimenciones = dimenciones;
        this.color = color;
        this.material = material;
        this.id = id;
        this.catntidad=cantidad;
    }
    
    public Mueble() {
        this.nombre = "";
        this.dimenciones = "";
        this.color = "";
        this.material = "";
        this.id = "";
        this.catntidad=0;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public abstract String toStrinDimenciones(double alto, double largo, double ancho);

    public int getCatntidad() {
        return catntidad;
    }

    public void setCatntidad(int catntidad) {
        this.catntidad = catntidad;
    }
    
    @Override
    public String toString() {
        return "Mueble: \nID=" + this.id +"\nnombre=" + this.nombre + "\nDimenciones=" + this.dimenciones + "\nColor=" + this.color + "\nMaterial=" + this.material +"\nCantidad="+this.catntidad;
    }
            
        
}
