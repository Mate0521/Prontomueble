/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Modelo.ConexionAzureSQL;
import Modelo.Empleado;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControlContratar {
    
    @FXML private TextField idField, nombreField, direccionField, telefonoField, emailField, sueldoField, rolField, contratoField, fechaNacField;
    @FXML private Button contratarButton;
    
    private ConexionAzureSQL conexionAzureSQL;
    private ControlRH controlRH;
    
    public ControlContratar() {
        conexionAzureSQL = new ConexionAzureSQL();
    }
    
    public void setControlRH(ControlRH controlRH) {
        this.controlRH = controlRH;
    }
    
    @FXML
    public void contratarEmpleado() {
        try (Connection connection = conexionAzureSQL.conectarRoot();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO Empleado (id_empleado, nombre, direccion, telefono, email, sueldo, rol, contrato, fecha_nac) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")
        ) {
            statement.setString(1, idField.getText());
            statement.setString(2, nombreField.getText());
            statement.setString(3, direccionField.getText());
            statement.setString(4, telefonoField.getText());
            statement.setString(5, emailField.getText());
            statement.setDouble(6, Double.parseDouble(sueldoField.getText()));
            statement.setString(7, rolField.getText());
            statement.setString(8, contratoField.getText());
            statement.setString(9, fechaNacField.getText());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0 && controlRH != null) {
                controlRH.actualizarTablaEmpleados();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

