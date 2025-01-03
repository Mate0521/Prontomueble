package Modelo;

/**
 *
 * @author Mateo
 */
public class Proveedor extends Persona{
    private String empresa;

    public Proveedor(String empresa, String nombre, String direccion, String telefono, String email) {
        super(nombre, direccion, telefono, email);
        this.empresa = empresa;
    }

    public Proveedor() {
        super();
        this.empresa = "";
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return "Proveedor: \nempresa=" + empresa + super.toString();
    }
    
            
}
