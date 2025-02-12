/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import Modelo.ConexionAzureSQL;
import Modelo.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

/**
 *
 * @author ASUS
 */
public class ContolStart {
    @FXML private TextField TxtId, TxtNombre, TxtDireccion, TxtRol, TxtTelefono, TxtEmail, TxtEdad, TxtContrato, TxtSueldo;
    
    private ConexionAzureSQL conexionAzureSQL;
    private String idLogueado;
    public ContolStart() {
        conexionAzureSQL = new ConexionAzureSQL();
    }
    
    public void setIdEmpleadoLogueado(String idEmpleado){
        this.idLogueado = idEmpleado;
    }
    @FXML
    public void mostrarDatos(String idEmpleado){
        try(Connection connection = conexionAzureSQL.conectarRoot()){
            PreparedStatement statement = connection.prepareStatement("SELECT id_empleado, nombre, direccion, rol, telefono, email, edad, contrato, sueldo " + "FROM Empleado WHERE id_empleado = ?");
            statement.setString(1, idEmpleado);
            ResultSet resultSet = statement.executeQuery();
            
            TxtId.setText(resultSet.getString("id_empleado"));
            TxtNombre.setText(resultSet.getString("nombre"));
            TxtDireccion.setText(resultSet.getString("direccion"));
            TxtRol.setText(resultSet.getString("rol"));
            TxtTelefono.setText(resultSet.getString("telefono"));
            TxtEmail.setText(resultSet.getString("email"));
            TxtEdad.setText(resultSet.getString("edad"));
            TxtContrato.setText(resultSet.getString("contrato"));
            TxtSueldo.setText(resultSet.getString("sueldo"));

        }catch (SQLException e){
            e.printStackTrace();
        }
        
    }
    
}
