package Control;

import Modelo.*;
import com.sun.jdi.connect.spi.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

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
        BtnSiguiente.setOnAction(event -> login());
    }

    

    private void login() throws SQLException {
        IdIniSecion.getText();
        PassIniSecion.getText();
        
        
        // Validar campos vacíos
        if (IdIniSecion.getText().isEmpty() || PassIniSecion.getText().isEmpty()) {
            mostrarAlerta("Campos vacíos", "Por favor, ingrese su usuario y contraseña.");
            return;
        }

        // Intentar conectar a la base de datos
        Connection conexion = (Connection) obAzureSQL.conectarEmpleado(IdIniSecion.getText(), PassIniSecion.getText());

        if (conexion != null) {
            mostrarAlerta("Inicio de sesión exitoso", "Bienvenido " );
            
            // Cerrar la ventana de login
            Stage stage = (Stage) BtnSiguiente.getScene().getWindow();
            stage.close();
            
            
            
            // Abrir la ventana principal
            abrirVentanaPrincipal(obtenerEmpleado(Integer.parseInt(IdIniSecion.getText())), PassIniSecion.getText());//enviarle el usuario
            
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
    
    public Empleado obtenerEmpleado(int idEmpleado, String Password) {
        Empleado ObEmpleado = null; // Inicializar como null
    
        // Crear una instancia de ConexionAzureSQL para obtener la conexión
        ConexionAzureSQL conexionAzure = new ConexionAzureSQL();

        // Usamos el método conectarEmpleado para asegurar que siempre usamos el mismo usuario
        try (Connection connection = (Connection) conexionAzure.conectarEmpleado(Integer.toString(idEmpleado), Password) // Usa el usuario y la contraseña correctos
             PreparedStatement stmt = connection.prepareStatement(consulta)) {

            if (connection == null) {
                System.out.println("No se pudo establecer la conexión.");
                return null; // Salir si no se pudo conectar
            }

            // Establecer el valor del parámetro en la consulta
            stmt.setInt(1, idEmpleado);

            // Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();

            // Procesar los resultados
            if (rs.next()) {
                ObEmpleado.setId(rs.getString("id"));
                ObEmpleado.setNombre(rs.getString("nombre")); 
                ObEmpleado.setDireccion(rs.getString("direccion"));
                ObEmpleado.setTelefono(rs.getString("telefono"));
                ObEmpleado.setEmail(rs.getString("email"));
                ObEmpleado.setSueldo(rs.getDouble("sueldo"));
                ObEmpleado.setRol(rs.getString("rol"));
                ObEmpleado.setContrato(rs.getString("contrato"));
                ObEmpleado.setFecha_nac(rs.getDate("fecha_nac"));
                

            }

        } catch (Exception e) {
            e.printStackTrace(); // Manejo de excepciones
        }

        return ObEmpleado; // Devolver el objeto empleado, o null si no se encontró
    }
         
}
