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
public class BusquedaMensaje {
    private int id ;
    private String nombre;
    
    public Conexion m_Conexion;
    
    public BusquedaMensaje() {
        // Instancia de Conexion
        this.m_Conexion = Conexion.getInstancia();
    }
    public void setBuscarMensaje(int id ) {
        this.id = id ;
    }
    
    public DefaultTableModel getMensaje() {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel contactos = new DefaultTableModel();
        contactos.setColumnIdentifiers(new Object[]{
            "nombre" 
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT us.nombre\n"
                + "FROM mensaje,conversacion,solicitud,usuario u , usuario us \n"
                + "WHERE (solicitud.idsolicitado = u.id  AND us.id = solicitud.idsolicitante \n"
                             + "AND conversacion.idtado=solicitud.idsolicitado  and conversacion.idtante=solicitud.idsolicitante \n"
                             + "AND conversacion.id=mensaje.idcon AND u.id = ? AND solicitud.estado = 1 AND us.estado=true ) \n"
                + "OR (solicitud.idsolicitante = u.id  AND us.id = solicitud.idsolicitado \n"
                             + "AND conversacion.idtante=solicitud.idsolicitante  and conversacion.idtado=solicitud.idsolicitado \n" 
                             + "AND conversacion.id=mensaje.idcon AND u.id = ? AND solicitud.estado = 1 AND us.estado=true) \n"
                + "GROUP BY (us.nombre)"
               ;
       /*
        SELECT us.nombre
FROM mensaje,conversacion,solicitud,usuario u, usuario us
WHERE 
(solicitud.idsolicitado = u.id  AND us.id = solicitud.idsolicitante and 
 conversacion.idtado=solicitud.idsolicitado  and conversacion.idtante=solicitud.idsolicitante and
 conversacion.id=mensaje.idcon
AND u.id = 1 AND solicitud.estado = 1 AND us.estado=true )
OR (solicitud.idsolicitante = u.id  AND us.id = solicitud.idsolicitado and
	conversacion.idtante=solicitud.idsolicitante  and conversacion.idtado=solicitud.idsolicitado and 
	conversacion.id=mensaje.idcon
AND u.id = 1 AND solicitud.estado = 1 AND us.estado=true)
group by(us.nombre)
        
        
        
        
        
        */
        
        // Los simbolos de interrogacion son para mandar parametros 
        // a la consulta al momento de ejecutalas

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, this.id);
            ps.setInt(2, this.id);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                contactos.addRow(new Object[]{
                    rs.getString("nombre"),
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return contactos;
        }
}
