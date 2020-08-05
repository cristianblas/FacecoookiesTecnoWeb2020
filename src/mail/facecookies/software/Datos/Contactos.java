/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.software.Datos;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author home
 */
public class Contactos {
    
    private int id_solicitante;
    private int id_solicitado;
    private int estado;
    private int id ;
    private String nombre;
    
    public Conexion m_Conexion;
    
    public Contactos() {
        // Instancia de Conexion
        this.m_Conexion = Conexion.getInstancia();
    }
    
    public void setSolicitud(int id_solicitante, int id_solicitado, int estado ) {
        this.id_solicitante = id_solicitante;
        this.id_solicitado = id_solicitado;
        this.estado = estado;
    }
    public void setContacto(int id, String Nombre) {
        this.id = id;
        this.nombre = Nombre;
    }
    public DefaultTableModel getContactos(int id) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel contactos = new DefaultTableModel();
        contactos.setColumnIdentifiers(new Object[]{
            "nombre","fecha","sexo","celular","estado" 
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT\n"
                + "us.nombre,\n"
                + "us.fecha,\n"
                + "us.sexo,\n"
                + "us.celular,\n"
                + "us.estado\n"
                + "FROM solicitud, usuario u, usuario us \n"
                + "WHERE (solicitud.idsolicitado = u.id AND us.id = solicitud.idsolicitante AND \n"
                + " u.id= ? AND solicitud.estado = 1 ) OR \n"
                + " (solicitud.idsolicitante = u.id AND us.id = solicitud.idsolicitado AND \n"
                + " u.id = ? AND solicitud.estado = 1 ) "
               ;
        /*
        SELECT us.nombre, us.fecha, us.sexo, us.celular, US.ESTADO
FROM solicitud, usuario u, usuario us
WHERE 
(solicitud.idSolicitado = u.id  AND us.id = solicitud.idSolicitante
AND u.id = 1 AND solicitud.estado = 1 AND us.estado=true)
OR (solicitud.idSolicitante = u.id  AND us.id = solicitud.idSolicitado 
AND u.id = 1 AND solicitud.estado = 1 AND us.estado=true)
        
        
        
        */


        // Los simbolos de interrogacion son para mandar parametros 
        // a la consulta al momento de ejecutalas

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setInt(2, id);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                contactos.addRow(new Object[]{
                    rs.getString("nombre"),
                    rs.getDate("fecha"),
                    rs.getString("sexo"),
                    rs.getInt("celular"),
                    rs.getBoolean("estado")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return contactos;
        
    }
    
    
    
    
    
    
    
    
    public DefaultTableModel getContacto(String Nombre) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel usuario = new DefaultTableModel();
        usuario.setColumnIdentifiers(new Object[]{
            "id" 
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT\n"
                + "usuario.id \n"
                + "FROM usuario\n"
                + "WHERE usuario.nombre=?";
        // Los simbolos de interrogacion son para mandar parametros 
        // a la consulta al momento de ejecutalas

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, Nombre);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                usuario.addRow(new Object[]{
                    rs.getInt("id"),
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return usuario;   
}
             /*
               select us.id, us.nombre
FROM solicitud, usuario u, usuario us
WHERE 
(solicitud.idSolicitado = u.id  AND us.id = solicitud.idSolicitante
AND u.id = 1 AND solicitud.estado = 1 AND us.estado=true and us.nombre='Maria Quispe')
OR (solicitud.idSolicitante = u.id  AND us.id = solicitud.idSolicitado 
AND u.id = 1 AND solicitud.estado = 1 AND us.estado=true and us.nombre='Maria Quispe') 
                
                */ 
    public DefaultTableModel deleteContacto() {
        // Tabla para mostrar lo obtenido de la consulta
        int id_contacto ;
        DefaultTableModel contactos = new DefaultTableModel();
        contactos.setColumnIdentifiers(new Object[]{
            "id , nombre  " 
        });
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
   
        // Preparo la consulta
        String sql = "UPDATE solicitud SET estado= 0 \n"
                + "WHERE (solicitud.idsolicitante = (SELECT usuario.id FROM usuario \n"
                + "Where (usuario.nombre = ? )) \n"
                + "AND solicitud.idsolicitante = ? ) OR \n"
                + "(solicitud.idsolicitado= (SELECT usuario.id FROM usuario\n "
                + "Where (usuario.nombre = ? )) \n "
                + "AND solicitud.idsolicitante = ?  ) "
                ;
        /**
         update solicitud set estado= 0
where solicitud.idsolicitante = (select usuario.id From  usuario 
								WHERE (usuario.nombre = 'chumacero jupanqui'))
AND solicitud.idsolicitado = 1  OR 
	   solicitud.idsolicitado =(select usuario.id From  usuario 
								WHERE (usuario.nombre = 'chumacero jupanqui')) 
AND solicitud.idsolicitante = 1 
         
         
         
         */
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, this.nombre);
            ps.setInt(2,this.id);
            ps.setString(3, this.nombre);
            ps.setInt(4,this.id);
            ResultSet rs = ps.executeQuery();
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                contactos.addRow(new Object[]{
                    rs.getString("nombre"),
                    rs.getDate("fecha"),
                    rs.getInt("celular"),
                    rs.getInt("estado")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return contactos;
        
    }
}
