package Modelo;

import java.util.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mateo
 */
public abstract class Factura {
    protected double valor;
    protected int cantidad;
    protected String metodo_pago;
    protected Vector<Mueble> mueble;
    protected Persona persona;

    public Factura(double valor, int cantidad, String metodo_pago, Vector<Mueble> mueble) {
        this.valor = valor;
        this.cantidad = cantidad;
        this.metodo_pago = metodo_pago;
        this.mueble = mueble;
    }
    
    public Factura() {
        this.valor = 0.0;
        this.cantidad = 0;
        this.metodo_pago = "";
        this.mueble = new Vector<>();
        this.persona = new Persona();
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getMetodo_pago() {
        return metodo_pago;
    }

    public void setMetodo_pago(String metodo_pago) {
        this.metodo_pago = metodo_pago;
    }

    public Vector<Mueble> getMueble() {
        return mueble;
    }

    public void setMueble(Vector<Mueble> mueble) {
        this.mueble = mueble;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
    
    
    public abstract Object[] registro();
    
    public abstract String obtenerIdMetodoPago(String nombreMetodo);
    
    public abstract double obtenerCostoU(String id_mueble, int i) ;
    
    
    @Override
    public String toString() {// ver comomostrar los id de los muebles comprados - recorrer vector de objetos muebles y tomar id de cada uno de los objetos guardados 
        return "\nValor=" + valor + "\nCantidad=" + cantidad + "\nMetodo de pago=" + metodo_pago + "\nMueble=" + mueble ;
    }
    
}
