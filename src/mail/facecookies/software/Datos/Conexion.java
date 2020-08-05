/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.software.Datos;

import mail.facecookies.utils.BaseDatos;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author home
 */
public class Conexion {
     private Connection connection = null;
    private String host;
    private String user;
    private String password;
    private static Conexion m_Conexion = null;

    private Conexion() {
        this.host = BaseDatos.DB_HOST;
        this.user = BaseDatos.DB_USER;
        this.password = BaseDatos.DB_PASSWORD;
    }

    public static Conexion getInstancia() {
        if (m_Conexion == null) {
            m_Conexion = new Conexion();
        }
        return m_Conexion;
    }

    public Connection getConexion() {
        return this.connection;
    }

    public void abrirConexion() {
        String db = BaseDatos.DB_NAME;
        String url_db = "jdbc:postgresql://" + this.host + ":5432/" + db;
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(url_db, this.user, this.password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void cerrarConexion() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
