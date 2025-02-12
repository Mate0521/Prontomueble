/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Modelo.ConexionAzureSQL;
import Modelo.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.time.LocalDate;

public class ControlRH {

    @FXML private TableView<Empleado> empleadosExistentes;
    @FXML private TableColumn<Empleado, String> colId, colNombre, colEmail, colTelefono, colRol, colContrato;
    @FXML private TableColumn<Empleado, Double> colSueldo;
    @FXML private TableColumn<Empleado, LocalDate> colFechaNac;
    @FXML private TextField idField, nombreField, emailField, telefonoField, sueldoField;
    @FXML private ComboBox<String> rolComboBox, contratoComboBox;
    @FXML private Button okButton, despedirButton, contratarButton;
    @FXML private ImageView imagenView;
    @FXML private VBox root;
    
    private ConexionAzureSQL conexionAzureSQL;

    public ControlRH() {
        conexionAzureSQL = new ConexionAzureSQL();
    }

    @FXML
    public void initialize() {
        llenarComboBoxes();
        actualizarTablaEmpleados();
    }

    @FXML
    public void abrirVistaContratar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/VistaContratar.fxml"));
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(loader.load()));

            ControlContratar controlContratar = loader.getController();
            controlContratar.setControlRH(this);

            stage.showAndWait();
            actualizarTablaEmpleados();
        } catch (IOException e) {
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
        ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();
        
        try (Connection connection = conexionAzureSQL.conectar();
             PreparedStatement statement = connection.prepareStatement("SELECT id_empleado, nombre, email, telefono, sueldo, rol, tipo_contrato, fecha_nac FROM V_Empleados")) {
            ResultSet resultSet = statement.executeQuery();
            
            while (resultSet.next()) {
                Empleado empleado = new Empleado(
                        resultSet.getDouble("sueldo"),
                        resultSet.getString("rol"),
                        resultSet.getString("tipo_contrato"),
                        resultSet.getDate("fecha_nac").toLocalDate(),
                        resultSet.getString("id_empleado"),
                        resultSet.getString("nombre"),
                        "",  // Dirección no está en la consulta
                        resultSet.getString("telefono"),
                        resultSet.getString("email")
                );
                listaEmpleados.add(empleado);
            }
            empleadosExistentes.setItems(listaEmpleados);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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



