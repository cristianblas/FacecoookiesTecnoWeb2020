/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.software.Negocio;

import javax.swing.table.DefaultTableModel;
import mail.facecookies.software.Datos.Amistad;

/**
 *
 * @author home
 */
public class AmistadNegocio {
    public Amistad m_Solicitud;

    public AmistadNegocio() {
        this.m_Solicitud = new Amistad();
    }
    
    //Funcionalidades del CU2
    public DefaultTableModel obtenerSolicitudes(int id) {
        return this.m_Solicitud.ListarSolicitud(id);
    }
    public int EnviarSolicitud(int id , String Nombre){  
        // No olvidar primero settear los datos
        this.m_Solicitud.setSolicitudAmistad(id , Nombre);
        return this.m_Solicitud.EnviarSolicitud() ;
    }
    public int AceptarSolicitud(int id , String Nombre){  
        // No olvidar primero settear los datos
        this.m_Solicitud.setSolicitudAmistad(id , Nombre);
        return this.m_Solicitud.AceptarSolicitud() ;
    }
    public int RechazarSolicitud(int id , String Nombre){  
        // No olvidar primero settear los datos
        this.m_Solicitud.setSolicitudAmistad(id , Nombre);
        return this.m_Solicitud.RechazarSolicitud() ;
    }
    
}
