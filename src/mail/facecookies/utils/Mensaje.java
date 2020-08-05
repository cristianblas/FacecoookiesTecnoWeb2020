/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.utils;


import java.util.Date;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 *
 * @author home
 */
public class Mensaje {
    private String correo;
    private String subject;
    private String data;

    public Mensaje(String subject, String data) {
        this.subject = subject;
        this.data = data;
    }

    public Mensaje(String correo, String subject, String data) {
        this.correo = correo;
        this.subject = subject;
        this.data = data;
    }
    
    public Mensaje(String correo) {
        this.correo = correo;
    }

    public Mensaje() {
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    
    
    
    @Override
    public String toString() {
        return "Mensaje{" + "subject=" + subject + ", data=" + data + '}';
    }
    
    public boolean enviarCorreo() throws AddressException, MessagingException {

        // sets SMTP server properties
        
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "false");
        //properties.put("mail.smtp.starttls.enable", "false");
        properties.put("mail.smtp.host", "mail.tecnoweb.org.bo");
        properties.put("mail.smtp.port", 25);
        
        
     //   properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("grupo14sc@tecnoweb.org.bo", "grupo14grupo14");
            }
      
        };
        Session session = Session.getInstance(properties, auth);
        // creates a new e-mail message
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("grupo14sc@tecnoweb.org.bo"));
            InternetAddress[] toAddresses = {new InternetAddress(this.correo)};
            msg.setRecipients(Message.RecipientType.TO, toAddresses);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            // set plain text message
            msg.setContent(this.data, "text/html; charset=UTF-8");
            // sends the e-mail
            Transport.send(msg);
            System.out.println("Envie MAIL: to=" + this.correo + " subject=" + subject + " data:" + this.data);
            return true;
        } catch (MessagingException mex) {
            System.out.println("Error Al Enviar SMTP");
            mex.printStackTrace();
        }
        return false;
    }
    public boolean enviarCorreoAdjunto() throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "mail.tecnoweb.org.bo");
        properties.put("mail.smtp.port", 25);
        properties.put("mail.smtp.auth", "false");
       // properties.put("mail.smtp.starttls.enable", "true");

        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("grupo14sc", "grupo14grupo14");
            }
        };
        Session session = Session.getInstance(properties, auth);
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("grupo14sc@tecnoweb.org.bo"));
            message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(this.correo));
            message.setSubject(this.subject);
            String html = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "</head>\n"
                + "<body>\n"
                + "<font size=6 color=\"#483D8B\" face=\"Segoe Script\">\n"
                + "<h1 align=\"center\">Facecookies</h1>\n"
                + "</font>\n"
                + "</body>\n" 
                +"</html>\n";
           // message.setText(this.data);

            message.setSentDate(new Date());
            // set plain text message
            message.setContent(html, "text/html; charset=UTF-8");
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            String file = "Estadistica.pdf";
            String fileName = "Estadistica.pdf";
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            System.out.println("Sending");

            Transport.send(message);

            System.out.println("Done");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }
}
