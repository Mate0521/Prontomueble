/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Modelo.ArchPdf;


public class ControlArchPdf {

    @FXML private TextField filePathField;
    @FXML private Button insertFilePathButton;
    @FXML private TextArea fileContentTextArea;

    private ArchPdf archPdf;

    public void initialize() {
        archPdf = new ArchPdf();
    }

    @FXML
    private void insertarDireccionArchivo() {
        String rutaArchivo = filePathField.getText();
        if (rutaArchivo.isEmpty()) {
            mostrarAlerta("Error", "Por favor ingrese la dirección del archivo.");
            return;
        }

        File archivo = new File(rutaArchivo);
        if (!archivo.exists()) {
            mostrarAlerta("Error", "No se encontró el archivo especificado.");
            return;
        }

        String contenido = archPdf.leerArchivo(rutaArchivo);
        fileContentTextArea.setText(contenido);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}

