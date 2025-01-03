
package Modelo;

import java.util.Vector;

/**
 *
 * @author Mateo
 */
public class Compra extends Factura{
    private Proveedor proveedor;

    public Compra(Proveedor proveedor, double valor, int cantidad, String metodo_pago, Vector<Mueble> mueble) {
        super(valor, cantidad, metodo_pago, mueble);
        this.proveedor = proveedor;
    }

    public Compra(Proveedor proveedor) {
        super();
        this.proveedor = new Proveedor();
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public String toString() {
        return "Factura de Compra \nProveedor=" + proveedor + super.toString();
    }
    
    
}
