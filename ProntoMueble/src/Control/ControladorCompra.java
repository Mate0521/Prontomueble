/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

/**
 *
 * @author naran
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import Modelo.ConexionAzureSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ControladorCompra {

    
    @FXML private TextField txtName, txtHigh, txtLength, txtWidth, txtCost, txtSupplierName, txtSupplierAddress,txtSupplierPhone, txtSupplierLiable, txtColorName, txtMaterialName, txtTypeName;
    @FXML private Spinner<Integer> spnAmount, spnWarranty;
    @FXML private ComboBox<String> cbSupplier, cbMaterial, cbColor;
    @FXML
    private Button btnAddPurchase, btnAddSupplier, btnAddColor, btnAddMaterial, btnAddType;
    private Connection connection;
    public ControladorCompra(Connection connection) {
        this.connection = connection;
    }
    @FXML
    public void cagar() {
        // Se inicializa la vista, como llenar los ComboBox con datos de la BD
        try {
            cargaSuppliers();
            cargaMaterials();
            cargaColors();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void cargaSuppliers() throws SQLException {
        // Carga los proveedores desde la BD al ComboBox
        cbSupplier.getItems().clear();
        String query = "SELECT id FROM Suppliers";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cbSupplier.getItems().add(rs.getString("Name"));
            }
        }
    }

    private void cargaMaterials() throws SQLException {
        // Carga los materiales desde la BD al ComboBox
        cbMaterial.getItems().clear();
        String query = "SELECT Name FROM Materials";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cbMaterial.getItems().add(rs.getString("Name"));
            }
        }
    }
    private void cargaColors() throws SQLException {
        // Carga los colores desde la BD al ComboBox
        cbColor.getItems().clear();
        String sql = "SELECT Name FROM Colors";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                cbColor.getItems().add(rs.getString("Name"));
            }
        }
    }
    @FXML
    private void añadirPurchase(ActionEvent event) {
        String name = txtName.getText();
        int amount = spnAmount.getValue();
        String dimensions = txtHigh.getText() + "x" + txtLength.getText() + "x" + txtWidth.getText();
        String supplier = cbSupplier.getValue();
        String material = cbMaterial.getValue();
        String color = cbColor.getValue();
        double cost = Double.parseDouble(txtCost.getText());
        int warranty = spnWarranty.getValue();
        String sql = "INSERT INTO Purchases (Name, Amount, Dimensions, Supplier, Material, Color, Cost, Warranty) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, amount);
            stmt.setString(3, dimensions);
            stmt.setString(4, supplier);
            stmt.setString(5, material);
            stmt.setString(6, color);
            stmt.setDouble(7, cost);
            stmt.setInt(8, warranty);
            stmt.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Purchase added successfully!");
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to add purchase: " + e.getMessage());
            alert.show();
        }
    }
    @FXML
    private void añadirSupplier(ActionEvent event) {
        String name = txtSupplierName.getText();
        String address = txtSupplierAddress.getText();
        String phone = txtSupplierPhone.getText();
        String liable = txtSupplierLiable.getText();
        String sql = "INSERT INTO Suppliers (Name, Address, Phone, Liable) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, address);
            stmt.setString(3, phone);
            stmt.setString(4, liable);
            stmt.executeUpdate();
            cargaSuppliers();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al añadir: " + e.getMessage());
            alert.show();
        }
    }
    @FXML
    private void añadirColor(ActionEvent event) {
        String name = txtColorName.getText();
        String sql = "INSERT INTO Colors (Name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            cargaColors();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al añadir: " + e.getMessage());
            alert.show();
        }
    }
    @FXML
    private void añadirMaterial(ActionEvent event) {
        String name = txtMaterialName.getText();
        String sql = "INSERT INTO Materials (Name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            cargaMaterials();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al agrega: " + e.getMessage());
            alert.show();
        }
    }
    @FXML
    private void añadirType(ActionEvent event) {
        String name = txtTypeName.getText();
        String query = "INSERT INTO Types (Name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Type added successfully!");
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al agregar: " + e.getMessage());
            alert.show();
        }
    }
}

