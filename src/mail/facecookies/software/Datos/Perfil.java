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
public class Perfil {
    private int id;
    private String nombre;
    private Date fecha;
    private String sexo;
    private int celular;
    private String correo;
    private String password;
    private boolean estado;
    
    public Conexion m_Conexion;
    
    public Perfil() {
        // Instancia de Conexion
        this.m_Conexion = Conexion.getInstancia();
    }

    /**
     *
     * @param nombre
     * @param fecha
     * @param sexo
     * @param celular
     * @param correo
     * @param password
     * @param estado
     */
    public void setUsuario(String nombre, Date fecha, String sexo, int celular,String correo,String password, boolean estado) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.sexo = sexo;
        this.celular = celular;
        this.correo = correo;
        this.password = password;
        this.estado = estado;
    }
    
    /**
     * @param id 
     * @param nombre
     * @param fecha
     * @param sexo
     * @param celular
     * @param correo
     * @param password
     * @param estado
     */
    
    public void setUsuario(int id, String nombre, Date fecha, String sexo, int celular,String correo,String password) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.sexo = sexo;
        this.celular = celular;
        this.correo = correo;
        this.password = password;
    }
    public void setUsuario(int id, String correo, boolean estado) {
        this.id = id;
        this.correo = correo;
        this.estado = estado;
    }
    
 /*   
     Metodos para las funcionalidades del CU1 Y CU3
     Usuario(id,nombre,fecha,sexo,celular,correo,passwd,estado)
 */
    
    /////////////////Crear Perfil//////////////////////////////
    public int registrarUsuario() {
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparando la consulta
        String sql = "INSERT INTO usuario(\n"
                + "nombre,fecha,sexo,celular,correo,password,estado)\n"
                + "VALUES(?,?,?,?,?,?,?)";

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // El segundo parametro de usa cuando se tienen tablas que generan llaves primarias
            // es bueno cuando nuestra bd tiene las primarias autoincrementables
            ps.setString(1, this.nombre);
            ps.setDate(2, this.fecha);
            ps.setString(3, this.sexo);
            ps.setInt(4, this.celular);
            ps.setString(5, this.correo);
            ps.setString(6, this.password);
            // estado al crear 1 activado
            ps.setBoolean(7, this.estado);
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
    
    public DefaultTableModel getUsuario(String correo) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel usuario = new DefaultTableModel();
        usuario.setColumnIdentifiers(new Object[]{
            "id", "nombre", "fecha", "sexo", "celular", "correo", "password" , "estado" 
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT\n"
                + "usuario.id,\n"
                + "usuario.nombre,\n"
                + "usuario.fecha,\n"
                + "usuario.sexo,\n"
                + "usuario.celular,\n"
                + "usuario.correo,\n"
                + "usuario.password,\n"
                + "usuario.estado\n"
                + "FROM usuario\n"
                + "WHERE usuario.correo=?";
        // Los simbolos de interrogacion son para mandar parametros 
        // a la consulta al momento de ejecutalas

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, correo);
            ResultSet rs = ps.executeQuery();

            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                usuario.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDate("fecha"),
                    rs.getString("sexo"),
                    rs.getInt("celular"),
                    rs.getString("correo"),
                    rs.getString("password"),
                    rs.getBoolean("estado")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return usuario;
        
    }
    
     
    ///////////////////Modificar Perfil///////////////////// 
    public void modificarUsuario() {
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "UPDATE usuario SET\n"
                + "nombre = ?,\n"
                + "fecha = ?,\n"
                + "sexo = ?,\n"
                + "celular = ?,\n"
                + "correo = ?,\n"
                + "password = ?\n"
                + "WHERE usuario.id = ?";
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, this.nombre);
            ps.setDate(2, this.fecha);
            ps.setString(3, this.sexo);
            ps.setInt(4, this.celular);
            ps.setString(5, this.correo);
            ps.setString(6, this.password);
            ps.setInt(7, this.id);
            int rows = ps.executeUpdate();
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
   
    public DefaultTableModel getUsuario(int id) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel usuario = new DefaultTableModel();
        usuario.setColumnIdentifiers(new Object[]{
            "id", "nombre", "fecha", "sexo", "celular", "correo", "password" , "estado" 
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT\n"
                + "usuario.id,\n"
                + "usuario.nombre,\n"
                + "usuario.fecha,\n"
                + "usuario.sexo,\n"
                + "usuario.celular,\n"
                + "usuario.correo,\n"
                + "usuario.password,\n"
                + "usuario.estado\n"
                + "FROM usuario\n"
                + "WHERE usuario.id=?";
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
                usuario.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getDate("fecha"),
                    rs.getString("sexo"),
                    rs.getInt("celular"),
                    rs.getString("correo"),
                    rs.getString("password"),
                    rs.getBoolean("estado")
                });
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return usuario;   
}
    
    
    
    ////////////////////Eliminar Perfil/////////////////////
    public void EliminarPerfil() {
        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "UPDATE usuario SET\n"
                + "correo = ?,\n"
                + "estado = ?\n"
                + "WHERE usuario.id = ?";
        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, this.correo);
            ps.setBoolean(2, this.estado);
            ps.setInt(3, this.id);
            int rows = ps.executeUpdate();
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /////////////////////Reportes Estqadisticas/////////////
    public DefaultTableModel ReporteG(int idAdmin) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel x = new DefaultTableModel();
        x.setColumnIdentifiers(new Object[]{
            "nombre","cantidad"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT usuario.nombre ,COUNT (*) AS cantidad FROM usuario, notificacion WHERE  \n" 
                    + "notificacion.idusu=usuario.id\n" 
                    + "group by nombre";
       
        //select COUNT(sexo)as hombres FROM usuario WHERE sexo='m' ;

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla3
                x.addRow(new Object[]{
                    rs.getString("nombre"),
                    rs.getInt("cantidad"),
                }); 
            }           
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return x;
    }
    public DefaultTableModel ReporteU(int idAdmin) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel x = new DefaultTableModel();
        x.setColumnIdentifiers(new Object[]{
            "Nombre","Edad","Celular","Cantidad"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT nombre, ((extract(year from  current_date)) - \n" +
                     "(extract(year from fecha))) as edad, Celular, \n" +
                     "count (*) amigos\n" +
                     "FROM usuario,solicitud\n" +
                     "WHERE (usuario.id = solicitud.idsolicitado OR \n" +
                     "usuario.id = solicitud.idsolicitante ) \n" +
                     "AND solicitud.estado= 1 \n" +
                     "GROUP BY nombre,edad,celular\n" +
                     "ORDER BY amigos DESC\n" +
                     "LIMIT 5";
       

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla3
                x.addRow(new Object[]{
                    rs.getString("nombre"),
                    rs.getInt("edad"),
                    rs.getInt("celular"),
                    rs.getInt("amigos")
                }); 
            }           
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return x;
    }
    public DefaultTableModel ReporteMayores(int idAdmin) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel x = new DefaultTableModel();
        x.setColumnIdentifiers(new Object[]{
            "nombre","edad","sexo","correo"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "SELECT nombre, ((extract(year from  current_date)) - \n" +
                    "(extract(year from fecha))) as edad, sexo, \n" +
                    "correo\n" +
                    "FROM usuario\n" +
                    "GROUP BY nombre,edad,sexo,correo\n" +
                    "ORDER BY edad DESC\n" +
                    "LIMIT 5";
       

        try {
            // La ejecuto
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();

            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla3
                x.addRow(new Object[]{
                    rs.getString("nombre"),
                    rs.getInt("edad"),
                    rs.getString("sexo"),
                    rs.getString("correo")
                }); 
            }           
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        return x;
    }
    
    
    
    
        

}