/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.software.Negocio;

import javax.swing.table.DefaultTableModel;
import mail.facecookies.software.Datos.Mensaje;

/**
 *
 * @author home
 */
public class MensajeNegocio {
    public Mensaje m_Mensaje;

    public MensajeNegocio() {
        this.m_Mensaje = new Mensaje();
    }
    public DefaultTableModel ObtenerMensajes(int id, String nombre) {
        return this.m_Mensaje.LeerMensaje(id,nombre);
    }
    public int EnviarMensaje(int id , String Nombre , String contenido){  
        // No olvidar primero settear los datos
        return this.m_Mensaje.EnviarMensaje(id,Nombre,contenido) ;
    }
    public int CrearConversacion(int id , String Nombre){  
        // No olvidar primero settear los datos
        return this.m_Mensaje.CrearConversacion(id,Nombre) ;
    }
   
    public DefaultTableModel ReporteM(int idAdmin) {
        return this.m_Mensaje.ReporteM(idAdmin);
    }
}
