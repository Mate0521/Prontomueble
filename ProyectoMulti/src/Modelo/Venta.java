package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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

    public Venta() {
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

    @Override
    public Object[] registro() {
        return new Object[]{new Date().toString(), super.getValor(), 
            super.getMetodo_pago(), this.empleado.getNombre() ,this.cliente.getId(), super.getMueble().toString()};
        
    }



    @Override
    public String obtenerIdMetodoPago(String nombreMetodo) {
        String idMetodoPago = "0";
        ConexionAzureSQL conexionAzure = new ConexionAzureSQL();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = conexionAzure.conectarEmpleado(this.empleado.getId(), this.empleado.getContraseña());
            
            if (connection == null) return "1";

            String sql = "SELECT id_metodo FROM MetodoPago WHERE nombre = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, nombreMetodo);
            rs = stmt.executeQuery();

            if (rs.next()) {
                idMetodoPago = rs.getString("id_metodo");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return idMetodoPago;
    }
    

    @Override
    public double obtenerCostoU(String id_mueble, int i) {
        ConexionAzureSQL conexionAzure = new ConexionAzureSQL();
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = conexionAzure.conectarEmpleado(this.empleado.getId(), this.empleado.getContraseña());
            String sql = "SELECT costo FROM compra WHERE id_mueble = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, id_mueble);
            rs = stmt.executeQuery();
            if (rs.next()) {
                 this.valor= rs.getDouble("costo");
            }
            sql="SELEC cantidad FROM Mueble WHERE id_mueble = ?";
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, id_mueble);
            rs = stmt.executeQuery();
            if(rs.next()){
                if(rs.getInt("cantidad")<i){
                    this.valor=0;
                }
            }
        
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this.valor;
    }
    
    
}
