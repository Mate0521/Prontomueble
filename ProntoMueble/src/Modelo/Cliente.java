package Modelo;

/**
 *
 * @author Mateo
 */
public class Cliente extends Persona{
    private int frecuencia; // desdes el fecha de inicio cada cuanto en cantidad de dias el cliente realiza una compra 

    public Cliente(String id, String nombre, String direccion, String telefono, String email) {
        super(id, nombre, direccion, telefono, email);
        this.frecuencia = frecuencia;
    }

    public Cliente() {
        super();
        this.frecuencia = 0;
    }

    

    @Override
    public String toString() {
        return "Cliente" + super.toString()+"\nfrecuencia=" + frecuencia ;
    }
    
    
}
