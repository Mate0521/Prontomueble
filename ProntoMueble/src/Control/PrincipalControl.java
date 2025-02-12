/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Modelo.Empleado;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 *
 * @author Mateo
 */
class PrincipalControl {

    private Empleado usuario;
    @FXML private Menu P_Start, P_Sale, P_Inventory, P_HumanResources, P_Refund, P_Statistics, P_Boss, P_Help;
    @FXML private TabPane P_tabP;
    @FXML private Tab PL_Sales,Pl_Inventory,PL_rh, PL_Refund, PL_statistics;
            
            
        @FXML
    public void initialize() {
        // Configurar acciones para abrir ventanas y modificar los tabs
       P_Start.setOnAction(event -> abrirVentana("/Vista/Start.fxml", "Start", 1));
       P_Sale.setOnAction(event -> abrirVentana("/Vista/Sale.fxml", "Sales", 2));
       P_Inventory.setOnAction(event -> abrirVentana("/Vista/Inetory.fxml", "Inentory", 3));
       P_HumanResources.setOnAction(event -> abrirVentana("/Vista/HumanResources.fxml", "RRHH", 4));
       P_Refund.setOnAction(event -> abrirVentana("/Vista/Refund.fxml", "Refund", 5));
       P_Statistics.setOnAction(event -> abrirVentana("/Vista/Statistic.fxml", "Statistics", 6));
       P_Boss.setOnAction(event -> abrirVentana("/Vista/Boss.fxml", "Sales", 1));
       P_Help.setOnAction(event -> abrirVentana("/Vista/Help.fxml", "Help", 1));//añadir vista 
    }

    private void abrirVentana(String rutaFXML, String titulo, int papel) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(rutaFXML));
            Parent root = loader.load();
            
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(titulo);
            stage.setResizable(false); // Bloquea el tamaño de la ventana
            stage.show();

            // Controlar los tabs según la opción seleccionada
            configurarTabs(papel);

        } catch (IOException e) {
            System.out.println("Error al abrir la ventana: " + titulo);
        }
    }

    private void configurarTabs(int papel) {
        
        switch (papel) {
            case 1 -> {
                //no habilitado el tabPane
                P_tabP.setDisable(false);
            }
            case 2 -> {
                //solo havilitado sales 
                PL_Refund.setDisable(false);
                PL_rh.setDisable(false);
                PL_statistics.setDisable(false);
                Pl_Inventory.setDisable(false);
            }
            case 3 -> {
                //solo habilitado inventory
                PL_Refund.setDisable(false);
                PL_Sales.setDisable(false);
                PL_rh.setDisable(false);
                PL_statistics.setDisable(false);
            }
            case 4 -> {
                // solo habilitado RRHH
                PL_Refund.setDisable(false);
                PL_Sales.setDisable(false);
                PL_statistics.setDisable(false);
                Pl_Inventory.setDisable(false);
            }
            case 5 -> {
                //solo habilitado Refund
                PL_Sales.setDisable(false);
                PL_rh.setDisable(false);
                PL_statistics.setDisable(false);
                Pl_Inventory.setDisable(false);
            }
            case 6 -> {
                //solohabilitado statistics
                PL_Refund.setDisable(false);
                PL_Sales.setDisable(false);
                PL_rh.setDisable(false);
                Pl_Inventory.setDisable(false);
            }
            default -> throw new AssertionError();
        }

    }

    public Empleado getUsuario() {
        return usuario;
    }

    public void setUsuario(Empleado usuario) {
        this.usuario = usuario;
    }
    
    
}
            
   
