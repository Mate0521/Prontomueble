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
 * @author Mateo
 */
public class ContolStart {
    @FXML private TextField idField, nombreField, direccionField, rolField, telefonoField, emailField, fechaNacField, contratoField, sueldoField;
    
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
            
            idField.setText(resultSet.getString("id_empleado"));
            nombreField.setText(resultSet.getString("nombre"));
            direccionField.setText(resultSet.getString("direccion"));
            rolField.setText(resultSet.getString("rol"));
            telefonoField.setText(resultSet.getString("telefono"));
            emailField.setText(resultSet.getString("email"));
            fechaNacField.setText(resultSet.getString("edad"));
            contratoField.setText(resultSet.getString("contrato"));
            sueldoField.setText(resultSet.getString("sueldo"));

        }catch (SQLException e){
            e.printStackTrace();
        }
        
    }
    
}
