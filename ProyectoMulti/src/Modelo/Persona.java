package Modelo;

/**
 *
 * @author Mateo
 */
public class Persona {
    private String id, nombre, direccion, telefono, email;

    public Persona(String id, String nombre, String direccion, String telefono, String email) {
        this.id=id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }
    public Persona() {
        this.id="";
        this.nombre = "";
        this.direccion = "";
        this.telefono = "";
        this.email = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "\nNombre=" + nombre + "\nDireccion=" + direccion + "\nTelefono=" + telefono + "\nEmail=" + email ;
    }
    
    
}
