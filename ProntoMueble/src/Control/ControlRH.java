/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Modelo.ConexionAzureSQL;
import Modelo.Empleado;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControlRH {

    @FXML private TableView<Empleado> empleadosExistentes;
    @FXML private TextField idField, nombreField, emailField, telefonoField, sueldoField;
    @FXML private ComboBox<String> rolComboBox, contratoComboBox;
    @FXML private Button okButton, despedirButton;
    @FXML private ImageView imagenView;
    @FXML private VBox root;
    
    private ConexionAzureSQL conexionAzureSQL;

    public ControlRH() {
        conexionAzureSQL = new ConexionAzureSQL();
    }

    @FXML
    public void initialize() {
        llenarComboBoxes();
    }

    @FXML
    public void buscarEmpleado(KeyEvent event) {
        String idEmpleado = idField.getText();
        if (idEmpleado.isEmpty()) {
            clearFields();
            return;
        }
        try (Connection connection = conexionAzureSQL.conectar();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM V_Empleados WHERE id_empleado = ?")) {
            statement.setString(1, idEmpleado);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                nombreField.setText(resultSet.getString("nombre"));
                emailField.setText(resultSet.getString("email"));
                telefonoField.setText(resultSet.getString("telefono"));
                sueldoField.setText(resultSet.getString("sueldo"));
                rolComboBox.setValue(resultSet.getString("rol"));
                contratoComboBox.setValue(resultSet.getString("tipo_contrato"));
                String imagePath = resultSet.getString("imagen");
                if (imagePath != null && !imagePath.isEmpty()) {
                    imagenView.setImage(new Image(imagePath));
                }
            } else {
                clearFields();
                showAlert("Empleado no encontrado", "No se encontró un empleado con el ID especificado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void actualizarEmpleado() {
        String idEmpleado = idField.getText();
        if (idEmpleado.isEmpty()) {
            showAlert("Error", "Por favor, ingrese un ID de empleado válido.");
            return;
        }
        try (Connection connection = conexionAzureSQL.conectar();
             PreparedStatement statement = connection.prepareStatement(
                     "UPDATE Empleado SET nombre = ?, email = ?, telefono = ?, sueldo = ?, id_rol = ?, id_tipoContrato = ? WHERE id_empleado = ?")) {
            statement.setString(1, nombreField.getText());
            statement.setString(2, emailField.getText());
            statement.setString(3, telefonoField.getText());
            statement.setBigDecimal(4, new BigDecimal(sueldoField.getText()));
            statement.setInt(5, obtenerIdRol(rolComboBox.getValue()));
            statement.setInt(6, obtenerIdTipoContrato(contratoComboBox.getValue()));
            statement.setString(7, idEmpleado);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert("Éxito", "Empleado actualizado exitosamente.");
            } else {
                showAlert("Error", "No se pudo actualizar el empleado. Verifique los datos ingresados.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void abrirVistaDespedir() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/VistaDespedir.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(loader.load()));
            
            ControlDespedir controlDespedir = loader.getController();
            controlDespedir.setControlRH(this);
            controlDespedir.setEmpleadoId(idField.getText());
            
            stage.showAndWait();
            actualizarTablaEmpleados();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void actualizarTablaEmpleados() {
        // Código para refrescar la tabla empleadosExistentes.
    }

    private void llenarComboBoxes() {
        try (Connection connection = conexionAzureSQL.conectar();
             PreparedStatement rolStatement = connection.prepareStatement("SELECT nombre FROM Rol");
             PreparedStatement contratoStatement = connection.prepareStatement("SELECT nombre FROM Tipo_Contrato")) {
            ResultSet rolResultSet = rolStatement.executeQuery();
            while (rolResultSet.next()) {
                rolComboBox.getItems().add(rolResultSet.getString("nombre"));
            }
            ResultSet contratoResultSet = contratoStatement.executeQuery();
            while (contratoResultSet.next()) {
                contratoComboBox.getItems().add(contratoResultSet.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int obtenerIdRol(String nombreRol) {
        try (Connection connection = conexionAzureSQL.conectar();
             PreparedStatement statement = connection.prepareStatement("SELECT id_rol FROM Rol WHERE nombre = ?")) {
            statement.setString(1, nombreRol);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id_rol");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private int obtenerIdTipoContrato(String nombreTipoContrato) {
        try (Connection connection = conexionAzureSQL.conectar();
             PreparedStatement statement = connection.prepareStatement("SELECT id_tipoContrato FROM Tipo_Contrato WHERE nombre = ?")) {
            statement.setString(1, nombreTipoContrato);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id_tipoContrato");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private void clearFields() {
        nombreField.clear();
        emailField.clear();
        telefonoField.clear();
        sueldoField.clear();
        rolComboBox.setValue(null);
        contratoComboBox.setValue(null);
        imagenView.setImage(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


