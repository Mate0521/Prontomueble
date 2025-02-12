/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 *
 * @author Mateo
 */
public class SaleControl {
    
    @FXML
    private TextField txtIdSale;
    @FXML
    private Button btn_search, btn_shopCart, btn_sale1, btn_sale2, btn_sale3, btn_sale4;
    @FXML
    private ImageView img_sale1, img_sale2, img_sale3, img_sale4;
    @FXML
    private TextField id_sale1, name_sale1, dim_sale1, color_sale1, mat_sale1, price_sale1;
    @FXML
    private TextField id_sale2, name_sale2, dim_sale2, color_sale2, mat_sale2, price_sale2;
    @FXML
    private TextField id_sale3, name_sale3, dim_sale3, color_sale3, mat_sale3, price_sale3;
    @FXML
    private TextField id_sale4, name_sale4, dim_sale4, color_sale4, mat_sale4, price_sale4;

        @FXML
    private void initialize() {
        // Inicializar acciones de los botones
        btn_search.setOnAction(event -> buscarVenta());
        btn_shopCart.setOnAction(event -> abrirCarrito());

        btn_sale1.setOnAction(event -> agregarAlCarrito(id_sale1.getText(), name_sale1.getText(), price_sale1.getText()));
        btn_sale2.setOnAction(event -> agregarAlCarrito(id_sale2.getText(), name_sale2.getText(), price_sale2.getText()));
        btn_sale3.setOnAction(event -> agregarAlCarrito(id_sale3.getText(), name_sale3.getText(), price_sale3.getText()));
        btn_sale4.setOnAction(event -> agregarAlCarrito(id_sale4.getText(), name_sale4.getText(), price_sale4.getText()));

        System.out.println("Botones inicializados correctamente.");
    }

    private void buscarVenta() {
        String id = txtIdSale.getText();
        
    }

    private void abrirCarrito() {
        System.out.println("Abriendo el carrito de compras...");
        // Aquí puedes agregar la lógica para abrir la ventana del carrito
    }

    private void agregarAlCarrito(String id, String nombre, String precio) {
        System.out.println("Agregando al carrito: " + nombre + " (ID: " + id + "), Precio: " + precio);
        // Aquí puedes agregar la lógica para almacenar la selección en el carrito
    }
}
