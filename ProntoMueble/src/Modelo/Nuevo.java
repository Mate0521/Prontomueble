package Modelo;

/**
 *
 * @author Mateo
 */
public class Nuevo extends Mueble{
    private int garantia;
    private String id_mueble;

    public Nuevo(int garantia, String id_mueble, String nombre, String dimenciones, String color, String material) {
        super(nombre, dimenciones, color, material);
        this.garantia = garantia;
        this.id_mueble = id_mueble;
    }
    public Nuevo() {
        super();
        this.garantia = 0;
        this.id_mueble = "";
    }

    public int getGarantia() {
        return garantia;
    }

    public void setGarantia(int garantia) {
        this.garantia = garantia;
    }

    public String getId_mueble() {
        return id_mueble;
    }

    public void setId_mueble(String id_mueble) {
        this.id_mueble = id_mueble;
    }

    @Override
    public String toString() {
        return super.toString()+"\nGarantia=" + garantia + "\nId_mueble=" + id_mueble ;
    }
    


    
    
}
