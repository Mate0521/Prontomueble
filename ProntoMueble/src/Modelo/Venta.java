package Modelo;

import java.util.Vector;

/**
 *
 * @author Mateo
 */
public class Venta extends Factura{
    
    private Cliente cliente;
    private Empleado empleado;

    public Venta(Cliente cliente, Empleado empleado, double valor, int cantidad, String metodo_pago, Vector<Mueble> mueble) {
        super(valor, cantidad, metodo_pago, mueble);
        this.cliente = cliente;
        this.empleado = empleado;
    }

    public Venta(Cliente cliente, Empleado empleado) {
        super();
        this.cliente = new Cliente();
        this.empleado = new Empleado();
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public String toString() {
        return "Factura de Venta \nCliente=" + cliente + "\nEmpleado=" + empleado + super.toString();
    }
    
    
}
