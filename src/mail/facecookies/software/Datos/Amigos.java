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
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author home
 */
public class Amigos {
    
    private int id ;
    private String nombre;
    
    public Conexion m_Conexion;
    
    public Amigos() {
        // Instancia de Conexion
        this.m_Conexion = Conexion.getInstancia();
    }
    public void setBuscar(String Nombre) {
        this.nombre = Nombre;
    }
    public DefaultTableModel getAmigos(String Nombre) {
        // Tabla para mostrar lo obtenido de la consulta
        
        DefaultTableModel contactos = new DefaultTableModel();
        contactos.setColumnIdentifiers(new Object[]{
            "nombre","fecha","sexo" 
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT\n"
                + "nombre,\n"
                + "fecha,"
                + "sexo\n"
                + "FROM usuario \n"
                + "WHERE nombre like '%"+ Nombre +"%' AND usuario.estado = true"
               ;
        // Los simbolos de interrogacion son para mandar parametros 
        // a la consulta al momento de ejecutalas

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
         //   ps.setString(1, this.nombre);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                contactos.addRow(new Object[]{
                    rs.getString("nombre"),
                    rs.getDate("fecha"),
                    rs.getString("sexo")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return contactos;
        }
}
