package Control;

import Modelo.*;
import com.sun.jdi.connect.spi.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.fxml.FXML;
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
    
    public void abrirVentanaPrincipal(String id){
        
    }
        
   /* public DefaultTableModel consultLongIn() {
        DefaultTableModel plantilla = new DefaultTableModel();
        ConexionAzureSQL con = new ConexionAzureSQL(); // Asegúrate de que tu clase Conexion maneje Azure SQL correctamente

        try {
            con.conectar(); // Método para establecer la conexión
            JOptionPane.showMessageDialog(null, con.getMensaje());
            
            Statement consulta = con.getConexion().createStatement();
            String sql = "SELECT nombre r  FROM Empleado WHERE id="+id_empleado; // Ajusta según tu estructura
            
            ResultSet datos = consulta.executeQuery(sql);
            ResultSetMetaData campos = datos.getMetaData();
            
            // Agregar nombres de columnas al modelo
            for (int i = 1; i <= campos.getColumnCount(); i++) {
                plantilla.addColumn(campos.getColumnName(i));
            }
            
            // Llenar filas con los datos
            while (datos.next()) {
                Object fila[] = new Object[campos.getColumnCount()];
                for (int i = 0; i < campos.getColumnCount(); i++) {
                    fila[i] = datos.getObject(i + 1);
                }
                plantilla.addRow(fila);
            }
            
            // Cerrar recursos
            datos.close();
            consulta.close();
            con.getConexion().close();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error SQL: " + ex.getMessage());
        }
        
        return plantilla;
    }
}
*/
    
    
    
}
