package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mateo
 */
public class ConexionAzureSQL {
    private static final String URL = "jdbc:sqlserver://bd-principal.database.windows.net:1433;"
                                    + "database=ProntoMueble;"
                                    + "user=PepitoPerez;"
                                    + "password=Qwert102938;"
                                    + "encrypt=true;trustServerCertificate=false;loginTimeout=30;";

    public static Connection conectar() {
        try {
            Connection connection = DriverManager.getConnection(URL);
            System.out.println("Conexión exitosa a Azure SQL Database.");
            return connection;
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
            return null;
        }
    }
}

