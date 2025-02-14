/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Modelo.*;
import Vista.Sale;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mateo
 */
public class SaleControl implements ActionListener{
    
    private Venta ObFactura;
    private Sale ObSale;
    private Empleado empleado;
    private Mueble ObMueble;
    private Cliente ObCliente;
    
    public SaleControl() {
        this.ObFactura = new Venta();
        this.ObSale = new Sale();
        ObSale.getBtnAgregar().addActionListener(this);
        ObSale.getCBoxMetPago().addActionListener(e -> metodoPagoSeleccionado());
        ObSale.getPnlServicio().setEnabled(false);
        ObSale.getPnlServicio().setVisible(false);
        ObSale.getTablaVentas().setEnabled(true);
    }
    
    public void iniciar() {
        ObSale.setTitle(" Registro Nueva Factura ");
        ObSale.setLocation(200,30);
        ObSale.setSize(1025, 690);
        ObSale.setVisible(true);
        ObSale.getTxtFechaF().setEnabled(false);
        ObSale.getjTabbedPane2().setEnabled(false);
        
    }
    
    public void agregarFilas(JTable tabla ,Factura objF){
        DefaultTableModel plantilla = (DefaultTableModel) tabla.getModel();
        plantilla.addRow(objF.registro());
    }
    
    private Cliente obtenerCliente() {
        Cliente cliente = null;
        String idCliente = ObSale.getTxtCliente().getText();
        ConexionAzureSQL conexionAzure = new ConexionAzureSQL();
        
        try (Connection connection = conexionAzure.conectarEmpleado(this.empleado.getId(), this.empleado.getContraseña());
            PreparedStatement stmtCliente = connection.prepareStatement("SELECT * FROM Cliente WHERE id_cliente = ?");
            PreparedStatement stmtCompras = connection.prepareStatement("SELECT total_compras FROM V_Contador_comp_Clientes WHERE id_cliente = ?")) {

           // Obtener datos del cliente
           stmtCliente.setString(1, idCliente);
           try (ResultSet rsCliente = stmtCliente.executeQuery()) {
               if (rsCliente.next()) {
                   this.ObCliente = new Cliente(
                       0, 
                       idCliente, 
                       rsCliente.getString("nombre"), 
                       rsCliente.getString("direccion"), 
                       rsCliente.getString("telefono"), 
                       rsCliente.getString("email")
                   );
               }
           } // `rsCliente` se cierra automáticamente aquí

           // Obtener total de compras
           stmtCompras.setString(1, idCliente);
           try (ResultSet rsCompras = stmtCompras.executeQuery()) {
               if (rsCompras.next()) {
                   ObCliente.setFrecuencia(rsCompras.getInt("total_compras"));
               }
           } // `rsCompras` se cierra automáticamente aquí

       } catch (SQLException e) {
           e.printStackTrace();
       }

        return cliente;
    }
    
    private void metodoPagoSeleccionado() {
        ConexionAzureSQL conexionAzure = new ConexionAzureSQL();
        JComboBox<String> comboBox = ObSale.getCBoxMetPago();
        comboBox.removeAllItems();
        
        try (Connection connection = conexionAzure.conectarEmpleado(this.empleado.getId(), this.empleado.getContraseña());
             PreparedStatement stmt = connection.prepareStatement("SELECT nombre FROM MetodoPago");
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                comboBox.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void agregarVenta() {
        ConexionAzureSQL conexionAzure = new ConexionAzureSQL();
        
        try (Connection connection = conexionAzure.conectarEmpleado(this.empleado.getId(), this.empleado.getContraseña());
             PreparedStatement stmt = connection.prepareStatement("INSERT INTO Venta (id_cliente, fecha, total, metodo_pago) VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, ObSale.getTxtCliente().getText());
            stmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            stmt.setDouble(3, obtenerTotalDeVenta(ObSale.getTablaVentas()));
            stmt.setString(4, ObFactura.obtenerIdMetodoPago((String)ObSale.getCBoxMetPago().getSelectedItem()));
            stmt.executeUpdate();
            
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int idVenta = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public double obtenerTotalDeVenta(JTable tablaVentas) {
        List<String> listaIds = new ArrayList<>();
        List<Integer> listaCantidades = new ArrayList<>();
        double total=0;

        DefaultTableModel modelo = (DefaultTableModel) tablaVentas.getModel();
        int filas = modelo.getRowCount();

        // Extraer datos de la tbla
        for (int i = 0; i < filas; i++) {
            String idMueble = modelo.getValueAt(i, 0).toString(); // Columna id_mueble
            int cantidad = Integer.parseInt(modelo.getValueAt(i, 1).toString()); // Columna cantidad

            listaIds.add(idMueble);
            listaCantidades.add(cantidad);
        }
        //Entramos a calcular el costo total de la fatura 
        for (int i = 0; i < listaIds.size(); i++) {
            total += ObFactura.obtenerCostoU(listaIds.get(i),i) * listaCantidades.get(i); // Multiplicar costo por cantidad y sumar al total
        }
        
        return total+(total*0.3)+(total*0.19)+(total*0.08);//valor total mas inpuestos y mantenimiento de software
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (ObSale.getTxtEmpleado().getText().trim().isEmpty() || ObSale.getTxtCliente().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if(e.getSource().equals(ObSale.getBtnOk())){
            ObSale.getPnlCliente().setVisible(false);
            ObSale.getPnlCliente().enable(false);
            ObSale.getPnlServicio().setVisible(true);
            ObSale.getPnlServicio().enable(true);
            obtenerCliente();
            
        }
        
        
        if (e.getSource().equals(ObSale.getBtnAgregar())) {
            if (ObSale.getTxtEmpleado().getText().trim().isEmpty() || ObSale.getTxtCliente().getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, llena todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                 agregarVenta();
                 agregarFilas(ObSale.getTblDatosVent(), ObFactura);
            }
            
        }
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }
    
    
}


