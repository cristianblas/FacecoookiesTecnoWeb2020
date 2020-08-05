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
public class Amistad {
    private int id_solicitante;
    private int id_solicitado;
    private int estado;
    private int id ;
    private String nombre;
    
    public Conexion m_Conexion;
    
    public Amistad() {
        // Instancia de Conexion
        this.m_Conexion = Conexion.getInstancia();
    }
    public void setSolicitudAmistad(int id_solicitante, String Nombre ) {
        this.id = id_solicitante ;
        this.nombre = Nombre;
    }
    
    
    public DefaultTableModel ListarSolicitud(int id) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel contactos = new DefaultTableModel();
        contactos.setColumnIdentifiers(new Object[]{
            "nombre","solicitudes" 
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT \n"
                + "us.nombre,\n"
                + "solicitud.estado as solicitudes \n "
                + "FROM solicitud, usuario u, usuario us \n "
                + "WHERE solicitud.idSolicitado = u.id  AND us.id = solicitud.idSolicitante \n "
                + "AND u.id = ? AND solicitud.estado = 0 AND u.estado=true and us.estado=true"
               ;
        /*
        SELECT us.id,us.nombre,solicitud.estado as solicitudes
FROM solicitud, usuario u, usuario us
WHERE 
(solicitud.idSolicitado = u.id  AND us.id = solicitud.idSolicitante
AND u.id = 16 AND solicitud.estado = 0 AND u.estado=true and us.estado=true)
        */
        // Los simbolos de interrogacion son para mandar parametros 
        // a la consulta al momento de ejecutalas

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
                    rs.getString("nombre"),
                    rs.getInt("solicitudes")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return contactos;
        
    }
    
    
    // modificar la consulta 
    public int EnviarSolicitud() {
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "INSERT INTO solicitud VALUES\n"
                + "(?,(SELECT id FROM usuario WHERE nombre = ?), 0)\n"
                ;
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // El segundo parametro de usa cuando se tienen tablas que generan llaves primarias
            // es bueno cuando nuestra bd tiene las primarias autoincrementables
              ps.setInt(1, this.id);
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
    public int AceptarSolicitud(){
       // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "UPDATE solicitud SET estado = 1 \n"
                + "WHERE solicitud.idsolicitante = \n"
                + "(SELECT id FROM usuario WHERE nombre = ?) \n"
                + "AND solicitud.idsolicitado = ? AND solicitud.estado = 0" ;
/*   
update solicitud set estado=1
where solicitud.idsolicitante= (select id
	  from usuario
	  where nombre='Debora Meltrozo') and solicitud.idsolicitado=14 and solicitud.estado is null
        */
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
              ps.setString(1, this.nombre);
              ps.setInt(2, this.id);
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
    public int RechazarSolicitud(){
       // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "UPDATE solicitud SET estado = 2 \n"
                + "WHERE solicitud.idsolicitante = (SELECT id FROM usuario \n"
                + "WHERE nombre = ? ) AND solicitud.idsolicitado = ? AND solicitud.estado = 0 " ;
/*
        update solicitud set estado=0
where solicitud.idsolicitante= (select id
	  from usuario
	  where nombre='Debora Meltrozo') and solicitud.idsolicitado=14 and solicitud.estado is null
        */
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // El segundo parametro de usa cuando se tienen tablas que generan llaves primarias
            // es bueno cuando nuestra bd tiene las primarias autoincrementables
            ps.setString(1, this.nombre);
            ps.setInt(2, this.id);
      //      ps.setDouble(3, this.precio);
      //      ps.setInt(4, this.id_producto);
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
    
}
