/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import Modelo.Devoluciones;
import Modelo.ConexionAzureSQL;
import java.io.IOException;
import java.sql.*;

public class ControlDevolucion {
    @FXML private VBox vboxPrincipal;
    @FXML private Button btnMakeRefund;
    @FXML private TextField txtSaleID;
    @FXML private ImageView imgProduct;
    @FXML private Button btnConfirmProduct;
    @FXML private Spinner<Integer> spnQuantity;
    @FXML private Button btnReason;
    @FXML private TextArea txtSpecifications;
    @FXML private Button btnCreateReport;
    @FXML private Pane panelDetails;

    private Connection conexion;

    public void initialize() {
        ocultarElementos();
        establecerConexion();
    }

    private void ocultarElementos() {
        panelDetails.setVisible(false);
        btnConfirmProduct.setVisible(false);
        spnQuantity.setVisible(false);
        btnReason.setVisible(false);
        txtSpecifications.setVisible(false);
        btnCreateReport.setVisible(false);
    }

    private void mostrarElementos() {
        panelDetails.setVisible(true);
        btnConfirmProduct.setVisible(true);
        spnQuantity.setVisible(true);
        btnReason.setVisible(true);
        txtSpecifications.setVisible(true);
        btnCreateReport.setVisible(true);
    }

    @FXML
    private void manejarClickRefund() {
        mostrarElementos();
    }

    @FXML
    private void confirmarProducto() {
        String idVenta = txtSaleID.getText();
        if (idVenta.isEmpty()) {
            mostrarAlerta("Error", "Por favor ingrese un ID de venta.");
            return;
        }

        String consulta = "SELECT * FROM V_Producto WHERE id_mueble = ?";
        try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
            stmt.setString(1, idVenta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String garantia = rs.getString("garantia");
                if (garantia == null || garantia.isEmpty()) {
                    mostrarAlerta("Sin Garantía", "El producto no tiene garantía.");
                } else {
                    String detalles = "Nombre: " + rs.getString("nombre") + "\n" +
                                      "Cantidad: " + rs.getInt("cantidad") + "\n" +
                                      "Dimensiones: " + rs.getDouble("alto") + "x" + rs.getDouble("largo") + "x" + rs.getDouble("ancho") + "\n" +
                                      "Precio: $" + rs.getDouble("precio") + "\n" +
                                      "Garantía: " + garantia;
                    txtSpecifications.setText(detalles);
                }
            } else {
                mostrarAlerta("Error", "No se encontró un producto con ese ID.");
            }
        } catch (SQLException e) {
            mostrarAlerta("Error", "Error en la consulta: " + e.getMessage());
        }
    }

    @FXML
    private void crearReporte() {
        String idVenta = txtSaleID.getText();
        int cantidad = spnQuantity.getValue();
        String especificaciones = txtSpecifications.getText();

        if (idVenta.isEmpty() || cantidad <= 0 || especificaciones.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos deben estar completos y la cantidad debe ser mayor a 0.");
            return;
        }

        String consulta = "INSERT INTO Devoluciones (id_mueble, cantidad, detalles) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conexion.prepareStatement(consulta)) {
            stmt.setString(1, idVenta);
            stmt.setInt(2, cantidad);
            stmt.setString(3, especificaciones);
            stmt.executeUpdate();
            mostrarAlerta("Éxito", "Reporte de devolución creado exitosamente.");
        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudo registrar la devolución: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void establecerConexion() {
        conexion = ConexionAzureSQL.conectar();
        if (conexion == null) {
            mostrarAlerta("Error", "No se pudo conectar a la base de datos.");
        }
    }

    @FXML
    private void mostrarVista2() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/Archivo.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Archivo");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            mostrarAlerta("Error", "No se pudo cargar la vista Achivo: " + e.getMessage());
        }
    }
}





