package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mateo
 */
public class ConexionAzureSQL {
    
    private Connection conexion;
    private String mensaje;

    private static final String URL = "jdbc:sqlserver://bd-principal.database.windows.net:1433;"
                                    + "database=ProntoMueble;"
                                    + "user=PepitoPerez;"
                                    + "password=Qwert102938;"
                                    + "encrypt=true;trustServerCertificate=false;loginTimeout=30;";

    public Connection conectarRoot() {
        try {
            this.conexion = DriverManager.getConnection(URL);
            mensaje = "Conexión exitosa a Azure SQL Database.";
        } catch (SQLException ex) {
            mensaje = "No se puede conectar con SQL Server: " + ex.getMessage();
            this.conexion = null;
        }
        return this.conexion;
    }

    public Connection conectarEmpleado(String usuario, String clave) {// a tener en cuenta que el usuario es el nombre mas el id
        String url = "jdbc:sqlserver://bd-principal.database.windows.net:1433;"
                   + "database=ProntoMueble;"
                   + "encrypt=true;trustServerCertificate=false;loginTimeout=30;";
        
        try {
            conexion = DriverManager.getConnection(url, usuario, clave);
            mensaje = "Conexión exitosa con el usuario: " + usuario;
            System.out.println(mensaje);
            return conexion;
        } catch (SQLException ex) {
            mensaje = "Error de autenticación: " + ex.getMessage();
            System.out.println(mensaje);
            return null;
        }
    }
   
    public Connection getConexion() {
        return conexion;
    }

    public String getMensaje() {
        return mensaje;
    }

    
    
}

