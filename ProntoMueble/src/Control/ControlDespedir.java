/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Modelo.ConexionAzureSQL;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ControlDespedir {

    @FXML private TextField idField, nombreField, emailField, telefonoField, sueldoField;
    @FXML private Button despedirButton, cancelarButton;
    @FXML private Label mensajeLabel;

    private ConexionAzureSQL conexionAzureSQL;
    private ControlRH controlRH;

    public ControlDespedir() {
        conexionAzureSQL = new ConexionAzureSQL();
    }

    @FXML
    public void initialize() {
        idField.setEditable(false);
        nombreField.setEditable(false);
        emailField.setEditable(false);
        telefonoField.setEditable(false);
        sueldoField.setEditable(false);
    }

    public void setControlRH(ControlRH controlRH) {
        this.controlRH = controlRH;
    }

    public void setEmpleadoId(String idEmpleado) {
        idField.setText(idEmpleado);
        cargarDatosEmpleado(idEmpleado);
    }

    private void cargarDatosEmpleado(String idEmpleado) {
        try (Connection connection = conexionAzureSQL.conectarRoot();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM V_Empleados WHERE id_empleado = ?")) {
            statement.setString(1, idEmpleado);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                nombreField.setText(resultSet.getString("nombre"));
                emailField.setText(resultSet.getString("email"));
                telefonoField.setText(resultSet.getString("telefono"));
                sueldoField.setText(resultSet.getString("sueldo"));
            } else {
                mensajeLabel.setText("Empleado no encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void despedirEmpleado() {
        String idEmpleado = idField.getText();
        if (idEmpleado.isEmpty()) {
            mensajeLabel.setText("Debe ingresar un ID de empleado.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmación");
        confirmacion.setHeaderText("¿Está seguro de que desea despedir a este empleado?");
        confirmacion.setContentText("Esta acción actualizará la fecha de terminación.");

        if (confirmacion.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {
            try (Connection connection = conexionAzureSQL.conectarRoot();
                 PreparedStatement statement = connection.prepareStatement(
                         "UPDATE Empleado SET fecha_terminacion = ? WHERE id_empleado = ?")) {
                statement.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                statement.setString(2, idEmpleado);
                int filasActualizadas = statement.executeUpdate();

                if (filasActualizadas > 0) {
                    mensajeLabel.setText("Empleado despedido con éxito.");
                    if (controlRH != null) {
                        controlRH.actualizarTablaEmpleados();
                    }
                    cerrarVentana();
                } else {
                    mensajeLabel.setText("No se pudo despedir al empleado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void cancelar() {
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) cancelarButton.getScene().getWindow();
        stage.close();
    }
}


