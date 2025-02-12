/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static javafx.application.Application.launch;

/**
 *
 * @author Mateo 57319
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
        } catch (IOException e) { // Imprime el error en consola para depuración
            // Imprime el error en consola para depuración
            System.out.println("Error al cargar la vista LongIn.fxml");
        }
    }
    
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
       launch(args); // Inicia la aplicación JavaFX
    }
}
