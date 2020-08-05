/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.software.Datos;
import mail.facecookies.software.Negocio.*;
import mail.facecookies.utils.Mensaje;
import mail.facecookies.utils.Utils;
import com.idrsolutions.image.pdf.PdfEncoder;
//import correos.veterinaria.utils.Cadenas;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.mail.MessagingException;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.general.DefaultPieDataset;
/**
 *
 * @author home
 */
public class Estadisticas {
    private Conexion m_Conexion;

    public Estadisticas() {
        m_Conexion = Conexion.getInstancia();
    }
    
    
    public double getPorcentajeMenores() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
            String sql = "select CAST ( ((select count(*) from usuario "
          + "where ( (extract(year from current_date) - (extract(year from fecha)) < 18 )))  * 100) /\n" 
          +  "(select count(*) from usuario) as  DOUBLE PRECISION) as total";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                return rs.getDouble("total");
            }
            //Cierro la conexion
            this.m_Conexion.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }
    public double getPorcentajeMedio() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        String sql = "select CAST ( ((select count(*) from usuario "
          + "where ( (extract(year from current_date) - (extract(year from fecha)) between 18 and 25 )))  * 100) /\n" 
          +  "(select count(*) from usuario) as  DOUBLE PRECISION) as total";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                return rs.getDouble("total");
            }
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }
    public double getPorcentajeMayores() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        String sql = "select CAST ( ((select count(*) from usuario "
          + "where ( (extract(year from current_date) - (extract(year from fecha)) > 25 )))  * 100) /\n" 
          +  "(select count(*) from usuario) as  DOUBLE PRECISION) as total";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                return rs.getDouble("total");
            }
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }
    
    
    
    public double getPorcentajeHombres() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        String sql = "select CAST ( ((select count(*) from usuario "
          + "where ( (sexo = 'm')))  * 100) /\n" 
          +  "(select count(*) from usuario) as  DOUBLE PRECISION) as total";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                return rs.getDouble("total");
            }
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }
    public double getPorcentajeMujeres() {
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();
        String sql = "select CAST ( ((select count(*) from usuario "
          + "where ( (sexo = 'f')))  * 100) /\n" 
          +  "(select count(*) from usuario) as  DOUBLE PRECISION) as total";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            // Recorro el resultado
            while (rs.next()) {
                // Agrego las tuplas a mi tabla
                return rs.getDouble("total");
            }
            // Cierro la conexion
            this.m_Conexion.cerrarConexion();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return -1;
    }


    public DefaultTableModel EstadisticasEdad(int idAdmin) {
        // Tabla para mostrar lo obtenido de la consulta
        DefaultTableModel x = new DefaultTableModel();
        x.setColumnIdentifiers(new Object[]{
            "nombre","fecha","edad"
        });

        // Abro y obtengo la conexion
        this.m_Conexion.abrirConexion();
        Connection con = this.m_Conexion.getConexion();

        // Preparo la consulta
        String sql = "select nombre , fecha , ((extract(year from  current_date)) - \n" +
                                "(extract(year from fecha))) as edad\n" +
                                "from usuario \n" +
                                "group by nombre,edad,fecha ";
       
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
                    rs.getInt("edad"),
                    rs.getDate("fecha") 
                    
                }); 
            }           
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return x;
    }
}
