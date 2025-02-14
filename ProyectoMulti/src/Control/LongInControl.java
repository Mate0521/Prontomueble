package Control;

import Modelo.*;
import Vista.login; // Cambié el nombre a Login (por convención de nombres)
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

public class LongInControl implements ActionListener {

    private ConexionAzureSQL obAzureSQL = new ConexionAzureSQL(); // Inicializar la conexión
    login obLogin = new login(); 

    public LongInControl() {
        obLogin.getBtnlogin().addActionListener(this);
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(obLogin.getBtnlogin())) {
            try {
                // Intento de conexión a la BD
                login();
                PrincipalControl obControl = new PrincipalControl();
                obControl.iniciar();
                this.obLogin.dispose();  // Cierra la ventana de login
            } catch (SQLException ex) {
                Logger.getLogger(LongInControl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void iniciar(){
        obLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obLogin.setSize(780, 440); // Tamaño de la ventana
        obLogin.setLocationRelativeTo(null); // Centrar la ventana
        obLogin.setVisible(true);
    }
    
    private void login() throws SQLException {

        // Validar campos vacíos
        if (obLogin.getTxtusuario().getText().trim().isEmpty() || obLogin.getTxtcontraseña().getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese su usuario y contraseña.", 
                                          "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Intentar conectar a la base de datos
        Connection conexion = obAzureSQL.conectarEmpleado(obLogin.getTxtusuario().getText(),obLogin.getTxtcontraseña().getText());

        if (conexion != null) {
            mostrarAlerta("Inicio de sesión exitoso", "Bienvenido " + obLogin.getTxtusuario().getText());

            // Cerrar la ventana de login
            obLogin.dispose();

            // Abrir la ventana principal
            Empleado empleado = obtenerEmpleado(obLogin.getTxtusuario().getText(), obLogin.getTxtcontraseña().getText());
            if (empleado != null) {
                abrirVentanaPrincipal(empleado);
            } else {
                mostrarAlerta("Error", "No se pudo obtener la información del empleado.");
            }
        } else {
            mostrarAlerta("Error de autenticación", "Usuario o contraseña incorrectos.");
        }
    }

    private void abrirVentanaPrincipal(Empleado usuario) {
        // Crear y mostrar la ventana principal
        JFrame ventanaPrincipal = new JFrame("Ventana Principal");
        ventanaPrincipal.setSize(600, 400); // Tamaño de la ventana
        ventanaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Puedes agregar contenido a la ventana principal aquí
        ventanaPrincipal.setVisible(true); // Mostrar la ventana
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(obLogin, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }

    public Empleado obtenerEmpleado(String idEmpleado, String password) {
        Empleado obEmpleado = null;

        // Crear una instancia de ConexionAzureSQL para obtener la conexión
        ConexionAzureSQL conexionAzure = new ConexionAzureSQL();

        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión
            connection = conexionAzure.conectarEmpleado(idEmpleado, password);

            if (connection == null) {
                System.out.println("No se pudo establecer la conexión.");
                return null; // Salir si no se pudo conectar
            }

            // Preparar la consulta
            stmt = connection.prepareStatement(generarConsultaEmpleado(idEmpleado));

            // Establecer el valor del parámetro en la consulta
            stmt.setString(1, idEmpleado);

            // Ejecutar la consulta
            rs = stmt.executeQuery();

            // Procesar los resultados
            if (rs.next()) {
                obEmpleado = new Empleado();
                obEmpleado.setId(rs.getString("id"));
                obEmpleado.setNombre(rs.getString("nombre"));
                obEmpleado.setDireccion(rs.getString("direccion"));
                obEmpleado.setTelefono(rs.getString("telefono"));
                obEmpleado.setEmail(rs.getString("email"));
                obEmpleado.setSueldo(rs.getDouble("sueldo"));
                obEmpleado.setRol(rs.getString("rol"));
                obEmpleado.setContrato(rs.getString("contrato"));
                obEmpleado.setFecha_nac(rs.getDate("fecha_nac"));
                obEmpleado.setContraseña(password);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return obEmpleado; // Devolver el objeto empleado, o null si no se encontró
    }

    public String generarConsultaEmpleado(String idEmpleado) {
        // Generar la consulta SQL para obtener los datos del empleado
        String consultaSQL = "SELECT e.id, e.nombre, e.direccion, e.telefono, e.email, e.sueldo, r.rol, c.contrato, e.fecha_nac "
                           + "FROM empleado e "
                           + "INNER JOIN rol r ON e.rol_id = r.id "
                           + "INNER JOIN contrato c ON e.contrato_id = c.id "
                           + "WHERE e.id = ?"; // Aquí el "?" es el parámetro que se usará para el id del empleado

        return consultaSQL;
    }
}

