/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.software.Negocio;

import mail.facecookies.software.Datos.Perfil;
import java.sql.Date;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author home
 */
public class PerfilNegocio {
    
    public Perfil m_Usuario;

    public PerfilNegocio() {
        this.m_Usuario = new Perfil();
    }
    
    
    /*
        Perfil(id,nombre,fecha,sexo,celular,correo,passwd,estado)
    */
    
    // Funcionalidades del CU1
     public DefaultTableModel obtenerUsuario(String correo) {
        return this.m_Usuario.getUsuario(correo);
    }
    public int registrarUsuario(String nombre, Date fecha, String sexo, int celular
                                    ,String correo,String password, boolean estado) { 
        // No olvidar primero settear los datos
        this.m_Usuario.setUsuario(nombre, fecha, sexo, celular, correo, password,estado);
        return this.m_Usuario.registrarUsuario();
    }
    
    
   
    public DefaultTableModel obtenerUsuario(int id) {
        return this.m_Usuario.getUsuario(id);
    }
    public void modificarPerfil(int id, String nombre, Date fecha, String sexo, int celular
                                    ,String correo,String password){  
        // No olvidar primero settear los datos
        this.m_Usuario.setUsuario(id, nombre, fecha, sexo, celular, correo, password);
        this.m_Usuario.modificarUsuario();
    }
    
    
    public void EliminarPerfil(int id ,String correo, boolean estado){  
        // No olvidar primero settear los datos
        this.m_Usuario.setUsuario(id,  correo, estado);
        this.m_Usuario.EliminarPerfil();
    }


    public DefaultTableModel ReporteG(int idAdmin) {
        return this.m_Usuario.ReporteG(idAdmin);
    }
    public DefaultTableModel ReporteU(int idAdmin) {
        return this.m_Usuario.ReporteU(idAdmin);
    }
    public DefaultTableModel ReporteMayores(int idAdmin) {
        return this.m_Usuario.ReporteMayores(idAdmin);
    }


}
