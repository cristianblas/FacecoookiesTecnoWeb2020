/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.software.Negocio;

import javax.swing.table.DefaultTableModel;
import mail.facecookies.software.Datos.Notificacion;

/**
 *
 * @author home
 */
public class NotificacionNegocio {
     public Notificacion m_Solicitud;
     public NotificacionNegocio() {
        this.m_Solicitud = new Notificacion();
    }
     public int EnviarNotificacion(String Nombre , String Cont){  
        // No olvidar primero settear los datos
        this.m_Solicitud.setNotificacion(Nombre , Cont);
        return this.m_Solicitud.EnviarNotificacion() ;
    }
    public DefaultTableModel obtenerNotificaciones(int id) {
        return this.m_Solicitud.ListarNotificacion(id);
    }
    public DefaultTableModel EstNotificacion(int id) {
        return this.m_Solicitud.EstNotificacion(id);
    }
    
        
        
}
