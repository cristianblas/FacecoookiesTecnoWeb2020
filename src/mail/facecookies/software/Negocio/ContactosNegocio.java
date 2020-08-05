/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.software.Negocio;


import mail.facecookies.software.Datos.Contactos;
import java.sql.Date;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author home
 */
public class ContactosNegocio {
    public Contactos m_Solicitud;

    public ContactosNegocio() {
        this.m_Solicitud = new Contactos();
    }
    
    //Funcionalidades del CU2
    public DefaultTableModel obtenerContactos(int id) {
        return this.m_Solicitud.getContactos(id);
    }
    
    
    
    
    
    public DefaultTableModel obtenerContacto(String Nombre) {
        return this.m_Solicitud.getContacto(Nombre);
    }
    public void EliminarContacto(int id , String Nombre){  
        // No olvidar primero settear los datos
        this.m_Solicitud.setContacto(id , Nombre);
        this.m_Solicitud.deleteContacto();
    }
}
