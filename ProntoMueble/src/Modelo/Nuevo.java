package Modelo;

/**
 *
 * @author Mateo
 */
public class Nuevo extends Mueble{
    private int garantia;

    public Nuevo(int garantia, String nombre, String dimenciones, String color, String material, String id) {
        super(nombre, dimenciones, color, material, id);
        this.garantia = garantia;
    }
    
    public Nuevo() {
        super();
        this.garantia = 0;
    }
    
    public int getGarantia() {
        return garantia;
    }

    public void setGarantia(int garantia) {
        this.garantia = garantia;
    }

    @Override
    public String toString() {
        return super.toString()+"\nGarantia=" + garantia ;
    }
    


    
    
}
