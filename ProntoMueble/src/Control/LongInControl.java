package Control;

import Modelo.*;
import com.sun.jdi.connect.spi.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    ConexionAzureSQL ObAzureSQL;
    @FXML private Button BtnSiguiente;
    @FXML private TextField IdIniSecion;
    @FXML private PasswordField PassIniSecion;
    
    public void initialize() {
        
        // Verificar acción del botón
        BtnSiguiente.setOnAction(event -> LongIn());
    }
    
    public void LongIn(){
        
        String id = IdIniSecion.getText();
        String contrasena = PassIniSecion.getText();
        
          // Intentar conectar a la base de datos
        Connection conexion = (Connection) ObAzureSQL.conectarEmpleado(id, contrasena);
        
        if (conexion != null) {
            System.out.println("Inicio de sesión exitoso.");

            // Cerrar la ventana de login
            Stage stage = (Stage) BtnSiguiente.getScene().getWindow();
            stage.close();

            // Abrir la ventana principal y pasarle el usuario
            abrirVentanaPrincipal(id);
        } else {
            System.out.println("Error de autenticación. Verifique sus credenciales.");
        }
    }
    
    private void abrirVentanaPrincipal(String usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Principal.fxml"));
            Parent root = loader.load();  // Primero se carga la vista

            // Obtener el controlador correctamente
            PrincipalControl principalController = loader.getController();

            principalController.setUsuario(usuario); // Enviar el usuario autenticado

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Main Window");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al cargar Principal.fxml");
        }
    }
         
}
