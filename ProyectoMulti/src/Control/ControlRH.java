package Control;
        /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
import Modelo.Empleado;
import Vista.Modificaciones;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ControlRH implements ActionListener{
    private Modificaciones ObModificaciones;
    private Connection conexion;
    Empleado ObEmpleado;
       
    public ControlRH( Connection conexion) {//recibimos la coneccion con el usuario
        this.conexion=conexion;
        ObModificaciones.getBtnActualizar().addActionListener(this);
        ObModificaciones.getBtnBuscar().addActionListener(this);
        cargarDatosEnTablas();
    }
    public void iniciar(){
        ObModificaciones.setTitle(" Registro Nueva Factura ");
        ObModificaciones.setLocation(200,30);
        ObModificaciones.setSize(1025, 690);
        ObModificaciones.setVisible(true);
    }

    private void cargarDatosEnTablas() {
        // Cargar datos en la tabla de productos
        ObModificaciones.getTblProducto().setModel(obtenerDatos("Mueble"));

        // Cargar datos en la tabla de clientes
        ObModificaciones.getTblCliente().setModel(obtenerDatos("Cliente"));

        // Cargar datos en la tabla de empleados
        ObModificaciones.getTbleEmplleados().setModel(obtenerDatos("Empleado"));

        // Cargar datos en la tabla de proveedores
        ObModificaciones.getTblProveedores().setModel(obtenerDatos("Proveedores"));
    }

    private void actualizarDatosDesdeTablas() {
        // Actualizar datos de productos
        actualizarDatosTabla(ObModificaciones.getTblProducto(), "Mueble");

        // Actualizar datos de clientes
        actualizarDatosTabla(ObModificaciones.getTblCliente(), "Cliente");

        // Actualizar datos de empleados
        actualizarDatosTabla(ObModificaciones.getTbleEmplleados(), "Empleado");

        // Actualizar datos de proveedores
        actualizarDatosTabla(ObModificaciones.getTblProveedores(), "Proveedores");

        JOptionPane.showMessageDialog(null, "Datos actualizados correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarDatosTabla(JTable tabla, String nombreTabla) {
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
        int filas = modelo.getRowCount();

        for (int i = 0; i < filas; i++) {
            int id = (int) modelo.getValueAt(i, 0); // Suponiendo que la primera columna es el ID
            String nombre = (String) modelo.getValueAt(i, 1); // Suponiendo que la segunda columna es el nombre

            // Verificar si el ID existe en la base de datos
            if (existeRegistro(nombreTabla, id)) {
                // Actualizar el registro existente
                switch (nombreTabla) {
                    case "Mueble":
                        actualizarProducto(
                            id,
                            (String) modelo.getValueAt(i, 1), // nombre
                            (int) modelo.getValueAt(i, 2), // cantidad
                            (double) modelo.getValueAt(i, 6), // precio
                            (int) modelo.getValueAt(i, 9), // id_material
                            (int) modelo.getValueAt(i, 10), // id_color
                            (int) modelo.getValueAt(i, 11) // id_tipoMueble
                        );
                        break;
                    case "Cliente":
                        actualizarCliente(
                            id,
                            (String) modelo.getValueAt(i, 1), // nombre
                            (String) modelo.getValueAt(i, 2), // direccion
                            (String) modelo.getValueAt(i, 3), // telefono
                            (String) modelo.getValueAt(i, 4), // email
                            (String) modelo.getValueAt(i, 5), // estado
                            (String) modelo.getValueAt(i, 6) // fecha_inicio
                        );
                        break;
                    case "Empleado":
                        actualizarEmpleado(
                            id,
                            (String) modelo.getValueAt(i, 1), // nombre
                            (String) modelo.getValueAt(i, 2), // telefono
                            (String) modelo.getValueAt(i, 3), // email
                            (double) modelo.getValueAt(i, 4), // sueldo
                            (String) modelo.getValueAt(i, 5), // fecha_nac
                            (String) modelo.getValueAt(i, 6), // fecha_inicio
                            (int) modelo.getValueAt(i, 7), // id_tipoContrato
                            (int) modelo.getValueAt(i, 8) // id_rol
                        );
                        break;
                    case "Proveedores":
                        actualizarProveedor(
                            id,
                            (String) modelo.getValueAt(i, 1), // nombre
                            (String) modelo.getValueAt(i, 2), // direccion
                            (String) modelo.getValueAt(i, 3), // responsable
                            (String) modelo.getValueAt(i, 4), // telefono
                            (String) modelo.getValueAt(i, 5), // fecha_inicio
                            (int) modelo.getValueAt(i, 6) // id_tipoMueble
                        );
                        break;
                }
            } else {
                // Insertar un nuevo registro
                switch (nombreTabla) {
                    case "Mueble":
                        insertarProducto(
                            (String) modelo.getValueAt(i, 1), // nombre
                            (int) modelo.getValueAt(i, 2), // cantidad
                            (double) modelo.getValueAt(i, 6), // precio
                            (int) modelo.getValueAt(i, 9), // id_material
                            (int) modelo.getValueAt(i, 10), // id_color
                            (int) modelo.getValueAt(i, 11) // id_tipoMueble
                        );
                        break;
                    case "Cliente":
                        insertarCliente(
                            (String) modelo.getValueAt(i, 1), // nombre
                            (String) modelo.getValueAt(i, 2), // direccion
                            (String) modelo.getValueAt(i, 3), // telefono
                            (String) modelo.getValueAt(i, 4), // email
                            (String) modelo.getValueAt(i, 5), // estado
                            (String) modelo.getValueAt(i, 6) // fecha_inicio
                        );
                        break;
                    case "Empleado":
                        insertarEmpleado(
                            (String) modelo.getValueAt(i, 1), // nombre
                            (String) modelo.getValueAt(i, 2), // telefono
                            (String) modelo.getValueAt(i, 3), // email
                            (double) modelo.getValueAt(i, 4), // sueldo
                            (String) modelo.getValueAt(i, 5), // fecha_nac
                            (String) modelo.getValueAt(i, 6), // fecha_inicio
                            (int) modelo.getValueAt(i, 7), // id_tipoContrato
                            (int) modelo.getValueAt(i, 8) // id_rol
                        );
                        break;
                    case "Proveedores":
                        insertarProveedor(
                            (String) modelo.getValueAt(i, 1), // nombre
                            (String) modelo.getValueAt(i, 2), // direccion
                            (String) modelo.getValueAt(i, 3), // responsable
                            (String) modelo.getValueAt(i, 4), // telefono
                            (String) modelo.getValueAt(i, 5), // fecha_inicio
                            (int) modelo.getValueAt(i, 6) // id_tipoMueble
                        );
                        break;
                }
            }
        }
    }

    private boolean existeRegistro(String tabla, int id) {
        try {
            String sql = "SELECT COUNT(*) FROM " + tabla + " WHERE id = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al verificar registro: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    // Métodos para obtener datos (ya implementados)
    public DefaultTableModel obtenerDatos(String tabla) {
        DefaultTableModel modelo = new DefaultTableModel();
        try {
            String consulta = "SELECT * FROM " + tabla;
            PreparedStatement ps = conexion.prepareStatement(consulta);
            ResultSet rs = ps.executeQuery();
            int columnas = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= columnas; i++) {
                modelo.addColumn(rs.getMetaData().getColumnName(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[columnas];
                for (int i = 0; i < columnas; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return modelo;
    }

    // Métodos para insertar y actualizar datos (ya implementados)
    public boolean insertarProducto(String nombre, int cantidad, double precio, int idMaterial, int idColor, int idTipoMueble) {
        try {
            String sql = "INSERT INTO Mueble (nombre, cantidad, precio, id_material, id_color, id_tipoMueble) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setInt(2, cantidad);
            ps.setDouble(3, precio);
            ps.setInt(4, idMaterial);
            ps.setInt(5, idColor);
            ps.setInt(6, idTipoMueble);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al insertar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean actualizarProducto(int id, String nombre, int cantidad, double precio, int idMaterial, int idColor, int idTipoMueble) {
        try {
            String sql = "UPDATE Mueble SET nombre = ?, cantidad = ?, precio = ?, id_material = ?, id_color = ?, id_tipoMueble = ? WHERE id_mueble = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setInt(2, cantidad);
            ps.setDouble(3, precio);
            ps.setInt(4, idMaterial);
            ps.setInt(5, idColor);
            ps.setInt(6, idTipoMueble);
            ps.setInt(7, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar producto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean insertarCliente(String nombre, String direccion, String telefono, String email, String estado, String fechaInicio) {
        try {
            String sql = "INSERT INTO Cliente (nombre, direccion, telefono, email, estado, fecha_inicio) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.setString(3, telefono);
            ps.setString(4, email);
            ps.setString(5, estado);
            ps.setString(6, fechaInicio);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al insertar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean actualizarCliente(int id, String nombre, String direccion, String telefono, String email, String estado, String fechaInicio) {
        try {
            String sql = "UPDATE Cliente SET nombre = ?, direccion = ?, telefono = ?, email = ?, estado = ?, fecha_inicio = ? WHERE id_cliente = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.setString(3, telefono);
            ps.setString(4, email);
            ps.setString(5, estado);
            ps.setString(6, fechaInicio);
            ps.setInt(7, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar cliente: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean insertarProveedor(String nombre, String direccion, String responsable, String telefono, String fechaInicio, int idTipoMueble) {
        try {
            String sql = "INSERT INTO Proveedores (nombre, direccion, responsable, telefono, fecha_inicio, id_tipoMueble) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.setString(3, responsable);
            ps.setString(4, telefono);
            ps.setString(5, fechaInicio);
            ps.setInt(6, idTipoMueble);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al insertar proveedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean actualizarProveedor(int id, String nombre, String direccion, String responsable, String telefono, String fechaInicio, int idTipoMueble) {
        try {
            String sql = "UPDATE Proveedores SET nombre = ?, direccion = ?, responsable = ?, telefono = ?, fecha_inicio = ?, id_tipoMueble = ? WHERE id_proveedores = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, direccion);
            ps.setString(3, responsable);
            ps.setString(4, telefono);
            ps.setString(5, fechaInicio);
            ps.setInt(6, idTipoMueble);
            ps.setInt(7, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar proveedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean insertarEmpleado(String nombre, String telefono, String email, double sueldo, String fechaNac, String fechaInicio, int idTipoContrato, int idRol) {
        try {
            String sql = "INSERT INTO Empleado (nombre, telefono, email, sueldo, fecha_nac, fecha_inicio, id_tipoContrato, id_rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setString(3, email);
            ps.setDouble(4, sueldo);
            ps.setString(5, fechaNac);
            ps.setString(6, fechaInicio);
            ps.setInt(7, idTipoContrato);
            ps.setInt(8, idRol);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al insertar empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean actualizarEmpleado(int id, String nombre, String telefono, String email, double sueldo, String fechaNac, String fechaInicio, int idTipoContrato, int idRol) {
        try {
            String sql = "UPDATE Empleado SET nombre = ?, telefono = ?, email = ?, sueldo = ?, fecha_nac = ?, fecha_inicio = ?, id_tipoContrato = ?, id_rol = ? WHERE id_empleado = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, telefono);
            ps.setString(3, email);
            ps.setDouble(4, sueldo);
            ps.setString(5, fechaNac);
            ps.setString(6, fechaInicio);
            ps.setInt(7, idTipoContrato);
            ps.setInt(8, idRol);
            ps.setInt(9, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar empleado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}