/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Mateo
 */
public class ProntoMueble extends Application{
    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar la vista correctamente
            Parent root = FXMLLoader.load(getClass().getResource("/Vista/LongIn.fxml"));

            // Configurar la escena
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Inicio de Sesión");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error en consola para depuración
            System.out.println("Error al cargar la vista LongIn.fxml");
        }
    }
}
