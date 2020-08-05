/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.software.Negocio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import mail.facecookies.software.Datos.BusquedaMensaje;

/**
 *
 * @author home
 */
public class BusquedaMensajeNegocio {
    public BusquedaMensaje m_BuscadorMensaje;

    public BusquedaMensajeNegocio() {
        this.m_BuscadorMensaje = new BusquedaMensaje();
    }
    
    public DefaultTableModel BuscarMensajes(int id ) {
        this.m_BuscadorMensaje.setBuscarMensaje(id);
        return this.m_BuscadorMensaje.getMensaje();
    }
    
    
    
}
