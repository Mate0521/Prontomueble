package Control;

import Modelo.Cliente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.*;
import javafx.beans.property.SimpleStringProperty;

public class ControlCliente {
    @FXML private TableView<ObservableList<String>> tablaClientes;
    @FXML private TableColumn<Cliente, Integer> colId;
    @FXML private TableColumn<Cliente, String> colNombre;
    @FXML private TableColumn<Cliente, Integer> colCompras;
    @FXML private TextField txtBuscarId, txtNombre, txtTelefono, txtEmail, txtDireccion;
    @FXML private Button btnBuscar, btnActualizar, btnNuevoCliente;
    private Connection conexion;
    private ObservableList<Cliente> listaClientes = FXCollections.observableArrayList();
    public void initialize() {
        conectarDB();
        configurarTabla();
        cargarDatosTabla();
    }
    private void conectarDB() {
        try {
            conexion = DriverManager.getConnection("jdbc:sqlserver://tu-servidor.database.windows.net;database=TuBase;user=Usuario;password=Clave;");
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de conexión", "No se pudo conectar a la base de datos.");
        }
    }

    private void configurarTabla() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCompras.setCellValueFactory(new PropertyValueFactory<>("totalCompras"));
    }

    private void cargarDatosTabla() {
    tablaClientes.getItems().clear();
    tablaClientes.getColumns().clear();
    
    try {
        Statement stmt = conexion.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM V_Contador_Comp_Clientes ORDER BY total_compras DESC");
        for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
            final int colIndex = i;
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(rs.getMetaData().getColumnName(i + 1));
            col.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(colIndex)));
            tablaClientes.getColumns().add(col);
            
        }
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        while (rs.next()) {
            ObservableList<String> row = FXCollections.observableArrayList();
            for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                row.add(rs.getString(i)); // Añade cada valor al registro
            }
            data.add(row);
        }
        tablaClientes.setItems(data);
    } catch (SQLException e) {
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los datos: " + e.getMessage());
    }
}
    @FXML
    private void buscarCliente() {
        try {
            PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM V_Clientes WHERE id = ?");
            stmt.setInt(1, Integer.parseInt(txtBuscarId.getText()));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                txtNombre.setText(rs.getString("nombre"));
                txtTelefono.setText(rs.getString("telefono"));
                txtEmail.setText(rs.getString("email"));
                txtDireccion.setText(rs.getString("direccion"));
            } else {
                mostrarAlerta(Alert.AlertType.INFORMATION, "Cliente no encontrado", "No hay registros con ese ID.");
            }
        } catch (SQLException | NumberFormatException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Verifique el ID ingresado.");
        }
    }

    @FXML
    private void actualizarCliente() {
        try {
            PreparedStatement stmt = conexion.prepareStatement("UPDATE Cliente SET nombre=?, telefono=?, email=?, direccion=? WHERE id=?");
            stmt.setString(1, txtNombre.getText());
            stmt.setString(2, txtTelefono.getText());
            stmt.setString(3, txtEmail.getText());
            stmt.setString(4, txtDireccion.getText());
            stmt.setInt(5, Integer.parseInt(txtBuscarId.getText()));
            stmt.executeUpdate();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cliente actualizado correctamente.");
            cargarDatosTabla();
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo actualizar el cliente.");
        }
    }

    @FXML
    private void abrirVentanaNuevoCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/NewCustomer.fxml"));
            AnchorPane root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Agregar Cliente");
            stage.show();
        } catch (Exception e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de nuevo cliente.");
        }
    }

    @FXML
    private void crearNuevoCliente() {
        if (txtNombre.getText().isEmpty() || txtTelefono.getText().isEmpty() || txtEmail.getText().isEmpty() || txtDireccion.getText().isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos vacíos", "Todos los campos son obligatorios.");
            return;
        }
        try {
            PreparedStatement stmt = conexion.prepareStatement("INSERT INTO Cliente (nombre, telefono, email, direccion) VALUES (?, ?, ?, ?)");
            stmt.setString(1, txtNombre.getText());
            stmt.setString(2, txtTelefono.getText());
            stmt.setString(3, txtEmail.getText());
            stmt.setString(4, txtDireccion.getText());
            stmt.executeUpdate();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Cliente agregado correctamente.");
            cargarDatosTabla();
            ((Stage) txtNombre.getScene().getWindow()).close();
        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo agregar el cliente.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}