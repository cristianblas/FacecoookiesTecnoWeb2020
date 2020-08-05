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
public class Mensaje {
    
    private int id ;
    private String nombre;
    private String contenido ;
    
    public Conexion m_Conexion;
    
    public Mensaje() {
        // Instancia de Conexion
        this.m_Conexion = Conexion.getInstancia();
    }
    public void setMensaje(int id_solicitante, String Nombre, String cont ) {
        this.id = id_solicitante ;
        this.nombre = Nombre;
        this.contenido = cont;
    }
    
    
    public DefaultTableModel LeerMensaje(int id,String nombre) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel contactos = new DefaultTableModel();
        contactos.setColumnIdentifiers(new Object[]{
            "nombre","contenido" 
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT b.nombre, mensaje.contenido \n"
                + "FROM mensaje, conversacion, solicitud, usuario u, usuario us, usuario b\n"
                + "WHERE (solicitud.idsolicitado = u.id  AND us.id = solicitud.idsolicitante AND \n"
                + "conversacion.idtado=solicitud.idsolicitado  AND conversacion.idtante=solicitud.idsolicitante AND\n"
                + "conversacion.id=mensaje.idcon and b.id=mensaje.idusu\n"
                + "AND u.id = ? AND solicitud.estado = 1 AND us.estado=true and us.nombre=?)\n"
                + "OR\n"
                + "(solicitud.idsolicitante = u.id  AND us.id = solicitud.idsolicitado AND\n"
                + "conversacion.idtante=solicitud.idsolicitante  AND conversacion.idtado=solicitud.idsolicitado AND\n"
                + "conversacion.id=mensaje.idcon AND b.id=mensaje.idusu\n"
                + "AND u.id = ? AND solicitud.estado = 1 AND us.estado=true and us.nombre=?)\n"
                ;

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.setString(2, nombre);
            ps.setInt(3, id);
            ps.setString(4, nombre);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                contactos.addRow(new Object[]{
                    rs.getString("nombre"),
                    rs.getString("contenido")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return contactos;
        
    }
// modificar la consulta   
     public int EnviarMensaje(int identificador , String Nombre , String Contenido) {
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        // solo modificar esta consulta
        
        String sql = "INSERT INTO mensaje(idcon,contenido,idusu) VALUES((SELECT conversacion.id FROM conversacion\n"
                + "WHERE\n"
                + "(conversacion.idtante ="+identificador+" AND conversacion.idtado =\n"
                + "(SELECT id FROM usuario WHERE usuario.nombre = '"+Nombre+"'))\n"
                + "OR\n"
                + "(conversacion.idtante = (SELECT id FROM usuario WHERE usuario.nombre = '"+Nombre+"')\n"
                + "AND conversacion.idtado = "+identificador+")) ,\n"
                + "'"+Contenido+"',\n"
                + ""+identificador+")\n"
                ;
 /**
  --INSERT INTO mensaje VALUES((SELECT conversacion.id FROM conversacion, solicitud
--					WHERE
--					(conversacion.idtante = idtante AND conversacion.idtado = 
--					(SELECT id FROM usuario WHERE usuario.nombre = nombre))
--					OR
--					(conversacion.idtante = (SELECT id FROM usuario WHERE usuario.nombre = nombre) 
--					AND conversacion.idtado = idtante)) ,
--					'Hola perro cmo estas?' ,
--					idtante)
  */
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // El segundo parametro de usa cuando se tienen tablas que generan llaves primarias
            // es bueno cuando nuestra bd tiene las primarias autoincrementables
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
     public int CrearConversacion(int id, String Nombre) {
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        // solo modificar esta consulta
        
        String sql = "INSERT INTO conversacion(idtante,idtado) VALUES \n"
                + "((SELECT solicitud.idsolicitante FROM solicitud WHERE idsolicitante = \n"
                + "(SELECT id FROM usuario WHERE nombre ='"+Nombre+"') )\n"
                + ",?)"
                ;

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // El segundo parametro de usa cuando se tienen tablas que generan llaves primarias
            // es bueno cuando nuestra bd tiene las primarias autoincrementables
                ps.setInt(1, id);
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
    
     
     
     public DefaultTableModel ReporteM(int idAdmin) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel x = new DefaultTableModel();
        x.setColumnIdentifiers(new Object[]{
            "nombre","celular","mensajes"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT nombre,celular ,COUNT (*) mensajes \n"
                + "FROM usuario \n" 
                + "INNER JOIN mensaje ON usuario.id = mensaje.idusu \n"
                + "GROUP BY nombre,celular \n"
                + "ORDER BY mensajes DESC \n"
                + "LIMIT 5 ";

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla5
                x.addRow(new Object[]{
                    rs.getString("nombre"),
                    rs.getInt("celular"),
                    rs.getInt("mensajes"),
                }); 
            }           
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return x;
    }
}
