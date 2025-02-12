/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Modelo.ConexionAzureSQL;
import Modelo.Mueble;
import Modelo.Nuevo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        btn_search.setOnAction(event -> SearchStok());
        btn_shopCart.setOnAction(event -> abrirCarrito());

        btn_sale1.setOnAction(event -> agregarAlCarrito(id_sale1.getText(), name_sale1.getText(), price_sale1.getText()));
        btn_sale2.setOnAction(event -> agregarAlCarrito(id_sale2.getText(), name_sale2.getText(), price_sale2.getText()));
        btn_sale3.setOnAction(event -> agregarAlCarrito(id_sale3.getText(), name_sale3.getText(), price_sale3.getText()));
        btn_sale4.setOnAction(event -> agregarAlCarrito(id_sale4.getText(), name_sale4.getText(), price_sale4.getText()));

    }

    private Mueble SearchStok() {
        
        Nuevo ObMueble = null;

        // Crear una instancia de ConexionAzureSQL para obtener la conexión
        ConexionAzureSQL conexionAzure = new ConexionAzureSQL();
        
        //Crear una instancia de la venta principal 
        PrincipalControl ObPrincipalControl= new PrincipalControl();
        
        
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Obtener la conexión
            connection = conexionAzure.conectarEmpleado(ObPrincipalControl.getUsuario().getId(), ObPrincipalControl.getUsuario().getContraseña()); 

            if (connection == null) {
                System.out.println("No se pudo establecer la conexión.");
                return null; // Salir si no se pudo conectar
            }

            // Preparar la consulta
            stmt = connection.prepareStatement(DataMueble(txtIdSale.getText()));

            // Establecer el valor del parámetro en la consulta
            stmt.setString(1, txtIdSale.getText());

            // Ejecutar la consulta
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                ObMueble.setNombre(rs.getString("nombre"));
                ObMueble.setCatntidad(rs.getInt("cantidad"));
                ObMueble.toStrinDimenciones(rs.getDouble("alto"),rs.getDouble("largo"),rs.getDouble("ancho"));
                ObMueble.setGarantia(rs.getInt("garantia"));
                
            }
            
            } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return ObMueble; // Devolver el objeto mueble, o null si no se encontró
    }


    private void abrirCarrito() {
        System.out.println("Abriendo el carrito de compras...");
        // Aquí puedes agregar la lógica para abrir la ventana del carrito
    }

    private void agregarAlCarrito(String id, String nombre, String precio) {
        System.out.println("Agregando al carrito: " + nombre + " (ID: " + id + "), Precio: " + precio);
        // Aquí puedes agregar la lógica para almacenar la selección en el carrito
    }
    public String DataMueble(String id_mueble){
        String sentenciaSQL = "SELECT  m.nombre, m.cantidad, m.alto, m.largo, m.ancho, m.imagen, m.precio, m.garantia, m.fecha_terminacion, p.nombre, ma.nombre, co.nombre "
                       + "FROM Mueble m "
                       + "INNER JOIN Proveedores p ON c.id_proveedores = p.id_proveedores"
                       + "INNER JOIN Color co ON co.id_color = m.id_color"
                       + "INNER JOIN Material ma ON ma.id_material = m.id_material" 
                       + "WHERE m.id_mueble = ?"; // El "?" es el parámetro que se usará para el id del mueble
        return sentenciaSQL;
    }
}
