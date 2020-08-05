/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.software;

import mail.facecookies.correo.SMTP_Cliente;
import mail.facecookies.procesador.Analex;
import mail.facecookies.procesador.Cinta;
import mail.facecookies.procesador.Parser;
import mail.facecookies.procesador.Token;
import mail.facecookies.software.Negocio.PerfilNegocio;
import mail.facecookies.software.Negocio.ContactosNegocio;
import mail.facecookies.software.Negocio.AmigosNegocio;
import mail.facecookies.software.Negocio.BusquedaMensajeNegocio;
import mail.facecookies.software.Negocio.AmistadNegocio;
import mail.facecookies.software.Negocio.MensajeNegocio;
import mail.facecookies.software.Negocio.NotificacionNegocio;
import mail.facecookies.software.Negocio.EstadisticasNegocio;
import mail.facecookies.utils.Mensaje;
import mail.facecookies.utils.Utils;
import java.sql.Date;
import java.util.ArrayList;
import javax.mail.MessagingException;
import javax.swing.table.DefaultTableModel;
import javax.mail.MessagingException;
import mail.facecookies.utils.Help;

/**
 *
 * @author home
 */
public class FacecookiesMail {
    public void processMessage(String Message) throws MessagingException {
        // Setteando Variables
        System.out.println("Mensaje:"+Message);
        String destinatario = Utils.getDestinatario(Message);
        String content = Utils.getSubjectOrden(Message);
        System.out.println("Texto : \t"+content);
        System.out.println("Destinatario : \t"+destinatario);
        Cinta cinta = new Cinta(content);
        Analex analex = new Analex(cinta);
        Parser parser = new Parser(analex);

        // Verificar Orden
        parser.Expresion();
        if (parser.errorFlag) {
            // Enviar Correo de Error
            SMTP_Cliente.sendMail(destinatario, "Comando Mal Introducido", "Puede pedir Ayuda con el comando HELP"
            );
            return;
        }
        // Si todo va bien, procesar el Comando
        analex.Init();
        Token token = analex.Preanalisis();
        if (token.getNombre() == Token.HELP) {
            Mensaje message = Utils.TablaAyuda();
            message.setCorreo(destinatario);
            if(message.enviarCorreo()){
                System.out.println("Envio Correo");
            }else{
                System.out.println("No envio Correo");
            }
            return;
        }
        
        // Sino es HELP, es una funcionalidad
        switch ((int) token.getAtributo()) {
            //CU1
            case Token.CREARPERFIL:CrearPerfil(analex,destinatario);break;
            case Token.MODIFICARPERFIL:modificarPerfil(analex,destinatario);break;
            case Token.ELIMINARPERFIL:EliminarPerfil(analex,destinatario);break;
            case Token.VISUALIZARCONTACTO:VisualizarContacto(analex,destinatario);break;
            case Token.ELIMINARCONTACTO:EliminarContacto(analex,destinatario);break;
            case Token.BUSCARAMIGOS:BuscarAmigo(analex,destinatario);break;
            case Token.BUSCARMENSAJES:BuscarMensaje(analex,destinatario);break;
            case Token.VISUALIZARSOLICITUD:VisualizarSolicitudes(analex,destinatario);break;
            case Token.ENVIARSOLICITUD:EnviarSolicitud(analex,destinatario);break;
            case Token.ACEPTARSOLICITUD:AceptarSolicitud(analex,destinatario);break;
            case Token.RECHAZARSOLICITUD:RechazarSolicitud(analex,destinatario);break;
            case Token.LEERMENSAJE:LeerMensaje(analex,destinatario);break;
            case Token.ENVIARMENSAJE:EnviarMensaje(analex,destinatario);break;
            case Token.VISUALIZARNOTIFICACIONES:VisualizarNotificaciones(analex,destinatario);break;
                
                
            case Token.REPORTENOTIFICACION:ReporteNotificacion(analex,destinatario);break;
            case Token.REPORTEMENSAJES:ReporteMensajes(analex,destinatario);break;
            case Token.REPORTEAMIGOS:ReporteUsuariosAmigos(analex,destinatario);break;
            case Token.REPORTEPERFILESMAYORES:ReportePerfilesMayores(analex,destinatario);break;

                
            case Token.ESTADISTICAEDAD:EstadisticaEdad(analex,destinatario);break;
            case Token.ESTADISTICASGENERO:EstadisticaGenero(analex,destinatario);break;
          //  case Token.ESTADISTICASPERSONAS:EstadisticaPersonas(analex,destinatario);break;
          //  case Token.ESTADISTICAS:Estadisticas(analex,destinatario);break;    
        }
    }
    
    //// CU1 y sus funcionalidades
    public void CrearPerfil(Analex analex, String destinatario) throws MessagingException {
        analex.Avanzar();
        Token token = analex.Preanalisis();
        //reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        PerfilNegocio usuarioNegocio = new PerfilNegocio();
        analex.Avanzar();
        String nombre = Utils.quitarComillas(analex.Preanalisis().getToStr()).toLowerCase();
        System.out.println("NombreCompleto :"+nombre);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        Date fecha = Utils.convertirFechas(Utils.quitarComillas(analex.Preanalisis().getToStr()));
        System.out.println("Fecha :"+fecha);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String sexo = Utils.quitarComillas(analex.Preanalisis().getToStr()).toLowerCase();
        System.out.println("Sexo :"+sexo);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        int celular = (int) analex.Preanalisis().getAtributo();
        System.out.println("Celular :"+celular);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String correo = Utils.quitarComillas(analex.Preanalisis().getToStr()).toLowerCase();
        String mail = correo;
        System.out.println("Correo :"+correo);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String password = Utils.quitarComillas(analex.Preanalisis().getToStr());
        System.out.println("Password :"+password);
        boolean  estado = true ;  
        if (destinatario.equals(mail)){
            usuarioNegocio.registrarUsuario(nombre, fecha, sexo, celular, correo, password,estado);
            String Head[]={"id","Nombre","Fecha","Sexo","Celular","Correo","Password","Estado"};
            String RegitroPerfil = Utils.dibujarTabla1(usuarioNegocio.obtenerUsuario(destinatario),Head);
            SMTP_Cliente.sendMail(destinatario, "Bienvenido a Facecookies Perfil Creado Correctamente", RegitroPerfil); 
        }else {
            SMTP_Cliente.sendMail(destinatario, "Crear Perfil", 
                    "Email No Coinciden Con el de la Informacion Proporcionada\n"
                            + "Debe Colocar El Email Con El Que Estas Creando El Perfil, Esta Es Como Su Confirmacion Del Mismo");  
        }
    }
    public void modificarPerfil(Analex analex, String destinatario) {
        //Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        PerfilNegocio usuarioNegocio = new PerfilNegocio();
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        int identificador = id ;
        System.out.println("id :"+id);
        DefaultTableModel usuario = usuarioNegocio.obtenerUsuario(id);
        System.out.println(usuario.getDataVector());

        // Revisar los GuionBajo
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String nombre = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr().toLowerCase())
                : String.valueOf(usuario.getValueAt(0, 1));
        System.out.println("NombreCompleto :"+nombre);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        Date fecha = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.convertirFechas(Utils.quitarComillas(analex.Preanalisis().getToStr()))
                : ((Date) usuario.getValueAt(0, 2));
        System.out.println("Fecha Nacimiento :"+fecha);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String sexo = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr().toLowerCase())
                : String.valueOf(usuario.getValueAt(0, 3));
        System.out.println("Sexo :"+sexo);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        int celular = (analex.Preanalisis().getNombre() != Token.GB)
                ? (int) analex.Preanalisis().getAtributo()
                : Integer.parseInt(String.valueOf(usuario.getValueAt(0, 4)));
        System.out.println("Celular :"+celular);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String correo = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr().toLowerCase())
                : String.valueOf(usuario.getValueAt(0, 5));
        System.out.println("Correo :"+correo);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String password = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr())
                : String.valueOf(usuario.getValueAt(0, 6));
        System.out.println("Password :"+password);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
   
        // no puede modificar el correo 
        if(destinatario.equals(correo)){
            usuarioNegocio.modificarPerfil(id,nombre, fecha, sexo,celular
                                    ,correo,password );
            String Head[]={"id","Nombre","Fecha","Sexo","Celular","Correo","Password","Estado"};
            String RegitroPerfil = Utils.dibujarTabla2(usuarioNegocio.obtenerUsuario(identificador),Head);
            SMTP_Cliente.sendMail(destinatario, "Modificacion de Perfil Exitosa ", RegitroPerfil); 
        }else{
            SMTP_Cliente.sendMail(destinatario, "Modificacion de Perfil Fallida", 
                    "Usted No Esta Autorizado a Realizar Esta Modificacion \n"
                            + "Acceda Desde El Correo Orinal del ID o del correo que intenta intenta reemplazar");  
        }
            
    }
    public void EliminarPerfil(Analex analex, String destinatario) {
        //Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        PerfilNegocio usuarioNegocio = new PerfilNegocio();
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        System.out.println("id :"+id);
        DefaultTableModel usuario = usuarioNegocio.obtenerUsuario(id);
        System.out.println(usuario.getDataVector());

        // Revisar los GuionBajo
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String correo = (analex.Preanalisis().getNombre() != Token.GB)
                ? Utils.quitarComillas(analex.Preanalisis().getToStr())
                : String.valueOf(usuario.getValueAt(0, 5));
        System.out.println("Correo :"+correo);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        boolean estado = false ;
        // no puede modificar el correo 
        if(destinatario.equals(correo)){
            usuarioNegocio.EliminarPerfil(id ,correo, estado);
            SMTP_Cliente.sendMail(destinatario, "Baja de Perfil", "Eliminaste Tu Perfil :( , Esperamos Que Vuelvas Pronto...!!"); 
        }else{
            SMTP_Cliente.sendMail(destinatario, "Baja de Perfil", 
                    "Usted No Esta Autorizado a Dar De Baja Este Perfil \n"
                            + "Acceda Desde El Correo Orinal del ID");  
        }
            
    }
    /// CU2 y sus funcionalidades
    public void VisualizarContacto(Analex analex, String destinatario){
    //Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        ContactosNegocio solicitudNegocio = new ContactosNegocio(); 

        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        int identificador = id ;
        System.out.println("id :"+id);
      //  DefaultTableModel usuario = solicitudNegocio.obtenerContactos(id);
        // no puede modificar el correo 
            String Head[]={"Nombre","Fecha","Sexo","Celular","Estado"};
            String Cabecera="Perfil Modificado De Usuario";
            String RegitroPerfil = Utils.dibujarTabla3(solicitudNegocio.obtenerContactos(identificador),Head);
        if(!RegitroPerfil.equals("")){
            SMTP_Cliente.sendMail(destinatario, "Facecookies Agenda Estos Son Tus Contactos :D", RegitroPerfil);
            
        }else{
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Agenda de tus Contactos :( ", 
                    "Usted No Esta Autorizado a Listar los Contactos de Este Perfil O \n"
                            + "Coloque ID Correcto Del Perfil");  
            
        }
            
}
    public void EliminarContacto(Analex analex, String destinatario){ 
        //Obtengo el Suiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        ContactosNegocio solicitudNegocio = new ContactosNegocio();
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        System.out.println("id :" +id);
        // Revisar los GuionBajo
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String Nombre = Utils.quitarComillas(analex.Preanalisis().getToStr());
        System.out.println("Nombre :"+ Nombre);
        
        if(id > 0 && !Nombre.equals("")){
            solicitudNegocio.EliminarContacto(id,Nombre);
            SMTP_Cliente.sendMail(destinatario, "Eliminar Contacto", "Contacto Eliminado, Espero Puedan Volver Amistarse :( "); 
        }else{
            SMTP_Cliente.sendMail(destinatario, "Eliminar Contacto", 
                  "Usted No Esta Autorizado a Eliminar Contactos de Este Perfil  o \n"
                            + "El Perfil No Existe");  
        }
    }
    /// CU3 y sus funcionalidades
    public void BuscarAmigo(Analex analex, String destinatario){
    //Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        AmigosNegocio buscarAmigo = new AmigosNegocio(); 
        analex.Avanzar();
        String Nombre = Utils.quitarComillas(analex.Preanalisis().getToStr()).toLowerCase();
        System.out.println("Nombre :"+ Nombre);
            
            String Encabezado = " Estas Son Las Coincidencias" ;
            String Head[]={"Nombre","Fecha","Sexo"};
            String BuscarAmigo = Utils.dibujarTablaGENERAL(buscarAmigo.BuscarAmigo(Nombre),Head,Encabezado);

        if(!BuscarAmigo.equals("")){
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Buscando Coincidencias  :D", BuscarAmigo);
            
        }else{
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Amigos No Se Encontro Resultados :( ", 
                    "Corrobora Que El Nombre Este Bien Escrito\n"
                         );  
            
        }
            
}
    /// CU4 y sus funcionalidades 
    public void BuscarMensaje(Analex analex, String destinatario){
    //Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        BusquedaMensajeNegocio buscarMensaje = new BusquedaMensajeNegocio(); 
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        int identificador = id ;
        System.out.println("id :"+id);
            
            String Encabezado = " \t Estos Son Tus Chats" ;
            String Head[]={"Nombre"};
            String BuscarAmigo = Utils.dibujarTablaGENERAL(buscarMensaje.BuscarMensajes(id),Head,Encabezado);

        if(!BuscarAmigo.equals("")){
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Lista de Mensajes :D", BuscarAmigo);        
        }else{
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Lista de Mensajes no tienes chats :( ", 
                    "Corrobora Que tu ID Este Bien Escrito \n"
                         );  
            
        }
        }
    /// CU5 y sus funcionalidades
    public void VisualizarSolicitudes(Analex analex, String destinatario){
    //Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        AmistadNegocio amistad = new AmistadNegocio(); 
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        int identificador = id ;
        System.out.println("id :"+id);
            
            String Encabezado = " \t Estas Son Tus Solicitudes" ;
            String Head[]={"Nombre","Estado"};
            String BuscarAmigo = Utils.dibujarTablaGENERAL(amistad.obtenerSolicitudes(identificador),Head,Encabezado);

        if(!BuscarAmigo.equals("")){
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Lista de Solicitudes :D", BuscarAmigo);        
        }else{
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Lista de Solicitudes No Tienes Solicitudes :( ", 
                    "Corrobora Que tu ID Este Bien Escrito \n"
                         );  
            
        }
        }    
    public void EnviarSolicitud(Analex analex, String destinatario){
    //Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        AmistadNegocio amistad = new AmistadNegocio(); 
        NotificacionNegocio notificacion = new NotificacionNegocio();
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        int identificador = id ;
        System.out.println("id :"+id);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String Nombre = Utils.quitarComillas(analex.Preanalisis().getToStr());
        System.out.println("Nombre :"+ Nombre);
        
        String contenido = "Has Recibido Una Solicitud De Amistad " ;
        int i = amistad.EnviarSolicitud(id, Nombre); 
        System.out.println(i);
        int j = notificacion.EnviarNotificacion(Nombre, contenido);
        System.out.println(j);
        if(i > 0 ){
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Solicitud Enviada :D", "");        
        }else{
            SMTP_Cliente.sendMail(destinatario, "Faceccokies No Se Encontro El Perfil :( ", 
                    "Corrobora Que tu ID Este Bien Escrito y El Nombre Del Contacto \n"
                         );  
            
        }
        }
    public void AceptarSolicitud(Analex analex, String destinatario){
    //Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        AmistadNegocio amistad = new AmistadNegocio(); 
        MensajeNegocio mensaje = new MensajeNegocio();
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        int identificador = id ;
        System.out.println("id :"+id);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String Nombre = Utils.quitarComillas(analex.Preanalisis().getToStr());
        System.out.println("Nombre :"+ Nombre);
        int i = amistad.AceptarSolicitud(id, Nombre);  
        int j = mensaje.CrearConversacion(id,Nombre);
        System.out.println(j);
        if(i > 0){
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Has Aceptado La Solicitud :D", "");        
        }else{
            SMTP_Cliente.sendMail(destinatario, "Faceccokies No Se Encontro La Solicitud :( ", 
                    "Corrobora Que tu ID Este Bien Escrito y El Nombre Del Contacto \n"
                         );  
            
        }
    }
    public void RechazarSolicitud (Analex analex, String destinatario){
        //Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        AmistadNegocio amistad = new AmistadNegocio(); 
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        int identificador = id ;
        System.out.println("id :"+id);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String Nombre = Utils.quitarComillas(analex.Preanalisis().getToStr());
        System.out.println("Nombre :"+ Nombre);
        int i = amistad.RechazarSolicitud(id, Nombre);    
        if(i > 0){
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Has Rechazado La Solicitud :( ", "");        
        }else{
            SMTP_Cliente.sendMail(destinatario, "Faceccokies No Se Encontro La Solicitud :( ", 
                    "Corrobora Que tu ID Este Bien Escrito y El Nombre Del Contacto \n"
                         );  
            
        }
    }
    // CU6 y sus funcionalidades
    public void LeerMensaje(Analex analex, String destinatario){
    //Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        MensajeNegocio mensaje = new MensajeNegocio(); 
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        int identificador = id ;
        System.out.println("id :"+id);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String Nombre = Utils.quitarComillas(analex.Preanalisis().getToStr());
        System.out.println("Nombre :"+ Nombre);
            
            String Encabezado = " \t Estos Son Tus Mensajes Con" + Nombre ;
            String Head[]={"Nombre","Contenido"};
            String Mensajes = Utils.dibujarTablaGENERAL(mensaje.ObtenerMensajes(id,Nombre),Head,Encabezado);

        if(!Mensajes.equals("(Tabla Vacia)")){
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Mensajes :D", Mensajes);        
        }else{
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Mensajes No Tienes mensajes :( ", 
                    "Corrobora Que tu ID Este Bien Escrito o Nombre de la persona \n"
                         );  
            
        }
        } 
    public void EnviarMensaje(Analex analex, String destinatario){
    //Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        MensajeNegocio mensaje = new MensajeNegocio(); 
        NotificacionNegocio notificacion = new NotificacionNegocio();
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        int identificador = id ;
        System.out.println("id :"+id);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String Nombre = Utils.quitarComillas(analex.Preanalisis().getToStr());
        System.out.println("Nombre :"+ Nombre);
        analex.Avanzar();
        analex.Avanzar();
        analex.Avanzar();
        String Contenido = Utils.quitarComillas(analex.Preanalisis().getToStr());
        System.out.println("Contenido :"+ Contenido);
        String Cont = "Te Han Enviado Un Mensaje ";
        int i = mensaje.EnviarMensaje(id, Nombre,Contenido);  
        if(i > 0){
            int j = notificacion.EnviarNotificacion(Nombre, Cont);
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Mensaje Enviado :D", "");        
        }else{
            SMTP_Cliente.sendMail(destinatario, "Faceccokies No Se Encontro El Perfil :( ", 
                    "Corrobora Que tu ID Este Bien Escrito y El Nombre Del Contacto \n"
                         );  
            
        }
        }
    public void VisualizarNotificaciones(Analex analex, String destinatario){
    //Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();
        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            SMTP_Cliente.sendMail(destinatario, "Facecookies Support", "Sigua Las Instrucciones del Mensaje HELP");
            return;
        }
        // Sino, ejecutar el comando
        NotificacionNegocio notificacion = new NotificacionNegocio(); 
        analex.Avanzar();
        int id = (int) analex.Preanalisis().getAtributo();
        int identificador = id ;
        System.out.println("id :"+id);
            String Encabezado = " \t Estas Son Tus Notificaciones" ;
            String Head[]={"Contenido"};
            String Notificacion = Utils.dibujarTablaGENERAL(notificacion.obtenerNotificaciones(identificador),Head,Encabezado);

        if(!Notificacion.equals("")){
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Lista de Solicitudes :D", Notificacion);        
        }else{
            SMTP_Cliente.sendMail(destinatario, "Faceccokies Lista de Solicitudes No Tienes Solicitudes :( ", 
                    "Corrobora Que tu ID Este Bien Escrito \n"
                         );  
            
        }
        }  
    
    
    
    // CU7  Reportes
    public void ReporteNotificacion(Analex analex, String destinatario) throws MessagingException{
        // Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();

        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            // Mostrar ayuda de esa funcionalidad
            // Enviar correo con la ayuda
            SMTP_Cliente.sendMail(destinatario, "Ayudas Facecookies", "Sigua las Instrucciones Detalladas en HELP");
            return;
        }

        // Sino, ejecutar el comando
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        analex.Avanzar();
        int idAdmin = (int) analex.Preanalisis().getAtributo();
        System.out.println("id_administrador : "+idAdmin);
        if(idAdmin == 2141){
            String Head[]={"Nombre","cantidad"};
            String Cabecera="Cantidad Notificaciones Por Usuario";
            Mensaje message = Utils.GraficosReportesEstadisticas(perfilNegocio.ReporteG(idAdmin),Head,Cabecera);
            message.setCorreo(destinatario);
            if(message.enviarCorreo()){
                System.out.println("Envio Correo");
            }else{
                System.out.println("No envio Correo");
        }    
        }
        
    }   
    public void ReporteMensajes(Analex analex, String destinatario) throws MessagingException{
        // Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();

        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            // Mostrar ayuda de esa funcionalidad
            // Enviar correo con la ayuda
            SMTP_Cliente.sendMail(destinatario, "Ayudas Facecookies", "Sigua las Instrucciones Detalladas en HELP");
            return;
        }

        // Sino, ejecutar el comando
        MensajeNegocio mensajeNegocio = new MensajeNegocio();
        analex.Avanzar();
        int idAdmin;
        idAdmin = (int) analex.Preanalisis().getAtributo();
        System.out.println("id_administrador : "+idAdmin);
        if(idAdmin == 2141){
            String Head[]={"Nombre","celular","Cantidad Mensajes"};
            String Cabecera="Los 5 Perfiles Con Mas Mensajes Enviados";
            Mensaje message = Utils.GraficosReportesEstadisticas(mensajeNegocio.ReporteM(idAdmin),Head,Cabecera);
            message.setCorreo(destinatario);
            if(message.enviarCorreo()){
                System.out.println("Envio Correo");
            }else{
                System.out.println("No envio Correo");
        }    
        }
    }
    public void ReporteUsuariosAmigos(Analex analex, String destinatario) throws MessagingException{
        // Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();

        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            // Mostrar ayuda de esa funcionalidad
            // Enviar correo con la ayuda
            SMTP_Cliente.sendMail(destinatario, "Ayudas Facecookies", "Sigua las Instrucciones Detalladas en HELP");
            return;
        }

        // Sino, ejecutar el comando
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        analex.Avanzar();
        int idAdmin;
        idAdmin = (int) analex.Preanalisis().getAtributo();
        System.out.println("id_administrador : "+idAdmin);
        if(idAdmin == 2141){
            String Head[]={"Nombre","Edad","Celular","Cantidad Amigos"};
            String Cabecera="5 Usuario con mas Amigos ";
            Mensaje message = Utils.GraficosReportesEstadisticas(perfilNegocio.ReporteU(idAdmin),Head,Cabecera);
            message.setCorreo(destinatario);
            if(message.enviarCorreo()){
                System.out.println("Envio Correo");
            }else{
                System.out.println("No envio Correo");
        }    
        }
    }
    public void ReportePerfilesMayores(Analex analex, String destinatario) throws MessagingException{
        // Obtengo el Siguiente token
        analex.Avanzar();
        Token token = analex.Preanalisis();

        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            // Mostrar ayuda de esa funcionalidad
            // Enviar correo con la ayuda
            SMTP_Cliente.sendMail(destinatario, "Ayudas Facecookies", "Sigua las Instrucciones Detalladas en HELP");
            return;
        }

        // Sino, ejecutar el comando
        PerfilNegocio perfilNegocio = new PerfilNegocio();
        analex.Avanzar();
        int idAdmin;
        idAdmin = (int) analex.Preanalisis().getAtributo();
        System.out.println("id_administrador : "+idAdmin);
        if(idAdmin == 2141){
            String Head[]={"Nombre","Edad","Sexo","Correo"};
            String Cabecera="5 Perfiles Con Mas Edad ";
            Mensaje message = Utils.GraficosReportesEstadisticas(perfilNegocio.ReporteMayores(idAdmin),Head,Cabecera);
            message.setCorreo(destinatario);
            if(message.enviarCorreo()){
                System.out.println("Envio Correo");
            }else{
                System.out.println("No envio Correo");
        }    
        }
    }
    
    
    
    //CU8 Estadisticas 
    public void EstadisticaEdad(Analex analex, String destinatario) throws MessagingException{
        EstadisticasNegocio estadisticaNegocio = new EstadisticasNegocio();
        analex.Avanzar();
        Token token = analex.Preanalisis();

        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            // Mostrar ayuda de esa funcionalidad
            // Enviar correo con la ayuda
            SMTP_Cliente.sendMail(destinatario, "Ayudas Facecookies", "Sigua las Instrucciones Detalladas en HELP");
            return;
        }

        // Sino, ejecutar el comando
        analex.Avanzar();
        int idAdmin;
        idAdmin = (int) analex.Preanalisis().getAtributo();
        System.out.println("id_administrador : "+idAdmin);
        if(idAdmin == 2141){
           estadisticaNegocio.tortaPorcentajeEdad();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Mensaje message = new Mensaje();
        message.setCorreo(destinatario);
        message.setSubject("Estadistica por Porcentaje Edades de Facecookies");
        message.setCorreo(destinatario);
        if (message.enviarCorreoAdjunto()) {
            System.out.println("Correo enviado");
        } else {
            System.out.println("Correo no enviado");
        }
        }
    }
    public void EstadisticaGenero(Analex analex, String destinatario) throws MessagingException{
        // Obtengo el Siguiente token     
        EstadisticasNegocio estadisticaNegocio = new EstadisticasNegocio();
        analex.Avanzar();
        Token token = analex.Preanalisis();

        // Reviso si no es ayuda
        if (token.getNombre() == Token.HELP) {
            // Mostrar ayuda de esa funcionalidad
            // Enviar correo con la ayuda
            SMTP_Cliente.sendMail(destinatario, "Ayudas Facecookies", "Sigua las Instrucciones Detalladas en HELP");
            return;
        }

        // Sino, ejecutar el comando
        analex.Avanzar();
        int idAdmin;
        idAdmin = (int) analex.Preanalisis().getAtributo();
        System.out.println("id_administrador : "+idAdmin);
        if(idAdmin == 2141){
            estadisticaNegocio.tortaPorcentajeEdad();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Mensaje message = new Mensaje();
        message.setCorreo(destinatario);
        message.setSubject("Estadistica Porcentajes Hombres y Mujeres");
        message.setCorreo(destinatario);
        if (message.enviarCorreoAdjunto()) {
            System.out.println("Correo enviado");
        } else {
            System.out.println("Correo no enviado");
        }
        }
    }   
    
    
    public void EstadisticaPersonas(Analex analex, String destinatario){
        
    }
    public void Estadisticas(Analex analex, String destinatario){
        
    }  
}


