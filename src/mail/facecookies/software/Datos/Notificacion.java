/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.software.Datos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author home
 */
public class Notificacion {
    private int id ;
    private String contenido;
    private String nombre ;
    
    public Conexion m_Conexion;
     
    public Notificacion() {
        // Instancia de Conexion
        this.m_Conexion = Conexion.getInstancia();
    }
    public void setNotificacion(String nombre, String contenido ) {
        this.nombre = nombre ;
        this.contenido = contenido;
    }
    public int EnviarNotificacion() {
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "INSERT INTO notificacion(contenido,idusu) VALUES \n"
                + "( ? ,(SELECT id FROM usuario WHERE nombre = ?))\n"
                ;
        
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // El segundo parametro de usa cuando se tienen tablas que generan llaves primarias
            // es bueno cuando nuestra bd tiene las primarias autoincrementables
            
            ps.setString(1, this.contenido ) ;
            ps.setString(2, this.nombre);
            int rows = ps.executeUpdate();
            // Cierro Conexion
            this.m_Conexion.cerrarConexion();

            // Obtengo el id generado pra devolverlo
            if (rows != 0) {
                ResultSet generateKeys = ps.getGeneratedKeys();
                if (generateKeys.next()) {
                    return generateKeys.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return 0;
    }
    public DefaultTableModel ListarNotificacion(int id) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel contactos = new DefaultTableModel();
        contactos.setColumnIdentifiers(new Object[]{
            "contenido" 
        });
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT \n"
                + "notificacion.contenido \n"
                + "FROM Notificacion \n "
                + "WHERE notificacion.idusu = ? \n "
               ;
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                contactos.addRow(new Object[]{
                    rs.getString("contenido")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return contactos;
        
    }
    public DefaultTableModel EstNotificacion(int id) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel contactos = new DefaultTableModel();
        contactos.setColumnIdentifiers(new Object[]{
            "id","contenido" 
        });
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT \n"
                + "notificacion.id,notificacion.contenido \n"
                + "FROM Notificacion \n "
                + "WHERE notificacion.idusu = ? \n "
               ;
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                contactos.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("contenido")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return contactos;
        
    }
    
    
    
    
}
