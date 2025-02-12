package Control;

import Modelo.*;
import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Mateo 
 */

public class LongInControl {

    @FXML private Button BtnSiguiente;
    @FXML private TextField IdIniSecion;
    @FXML private PasswordField PassIniSecion;
    private ConexionAzureSQL obAzureSQL = new ConexionAzureSQL(); // Inicializar la conexión

    @FXML
    public void initialize() {
        // Verificar acción del botón
        BtnSiguiente.setOnAction(event -> {
            try {
                login();
            } catch (SQLException e) {
                mostrarAlerta("Error de base de datos", "No se pudo conectar a la base de datos.");
                e.printStackTrace();
            }
        });
    }

    private void login() throws SQLException {
        String idText = IdIniSecion.getText();
        String password = PassIniSecion.getText();

        // Validar campos vacíos
        if (idText.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor, ingrese su usuario y contraseña.");
            return;
        }

        // Intentar conectar a la base de datos
        Connection conexion = obAzureSQL.conectarEmpleado(idText, password);

        if (conexion != null) {
            mostrarAlerta("Inicio de sesión exitoso", "Bienvenido " + idText);

            // Cerrar la ventana de login
            Stage stage = (Stage) BtnSiguiente.getScene().getWindow();
            stage.close();

            // Abrir la ventana principal
            Empleado empleado = obtenerEmpleado(idText, password);
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Principal.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana principal
            PrincipalControl principalController = loader.getController();
            principalController.setUsuario(usuario); // Enviar el usuario autenticado

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Ventana Principal");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error", "No se pudo cargar la ventana principal.");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public Empleado obtenerEmpleado(String idEmpleado, String password) {
        Empleado ObEmpleado = null;

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
                ObEmpleado = new Empleado();
                ObEmpleado.setId(rs.getString("id"));
                ObEmpleado.setNombre(rs.getString("nombre"));
                ObEmpleado.setDireccion(rs.getString("direccion"));
                ObEmpleado.setTelefono(rs.getString("telefono"));
                ObEmpleado.setEmail(rs.getString("email"));
                ObEmpleado.setSueldo(rs.getDouble("sueldo"));
                ObEmpleado.setRol(rs.getString("rol"));
                ObEmpleado.setContrato(rs.getString("contrato"));
                ObEmpleado.setFecha_nac(rs.getDate("fecha_nac"));
                ObEmpleado.setContraseña(password);
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

        return ObEmpleado; // Devolver el objeto empleado, o null si no se encontró
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

