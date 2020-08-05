/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.software.Negocio;

import javax.swing.table.DefaultTableModel;
import mail.facecookies.software.Datos.Amigos;

/**
 *
 * @author home
 */
public class AmigosNegocio {
    public Amigos m_Buscador;

    public AmigosNegocio() {
        this.m_Buscador = new Amigos();
    }
    
    public DefaultTableModel BuscarAmigo(String Nombre) {
        this.m_Buscador.setBuscar(Nombre);
        return this.m_Buscador.getAmigos(Nombre);
    }
}
