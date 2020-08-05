/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.correo;
import mail.facecookies.utils.Constantes;
import java.io.*;
import java.net.*;
/**
 *
 * @author Blas
 */
public class SMTP_Cliente {
    private static final int PORT = 25; // SMTP

    public static void sendMail(String toMail, String subject, String content) {
        // Estableciendo variables
        BufferedReader reader;
        DataOutputStream writer;
        String command;

        try {
            // Estableciendo Conexion Socket de lectura y escritura
            Socket socket = new Socket(Constantes.MAIL_SERVER_HOST, PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new DataOutputStream(socket.getOutputStream());

            if (socket != null && reader != null && writer != null) {
                reader.readLine();
                // mensaje para iniciar sesion saludando al server
                command = "EHLO " + Constantes.MAIL_SERVER_HOST + "\r\n";
                writer.writeBytes(command);
                System.out.println(command);
                getMultiline(reader);

                command = "MAIL FROM : " + Constantes.MAIL_USERMAIL + "\r\n";
                writer.writeBytes(command);
                System.out.println(command);
                reader.readLine();

                command = "RCPT TO : " + toMail + "\r\n";
                writer.writeBytes(command);
                System.out.println(command);
                reader.readLine();

                // Escribir Mensaje
                command = "DATA\n";
                writer.writeBytes(command);
                System.out.println(command);
                getMultiline(reader);

                command = "SUBJECT: " + subject + "\r\n" + content + "\n.\r\n";
                writer.writeBytes(command);
                System.out.println(command);
                reader.readLine();

                command = "QUIT\r\n";
                writer.writeBytes(command);
                System.out.println(command);
                reader.readLine();
            }

            // Cerrar Conexion
            writer.close();
            reader.close();
            socket.close();
            System.out.println("Cierra Conexión");
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
// para salto de linea
    static protected String getMultiline(BufferedReader in) throws IOException {
        String lines = "";
        while (true) {
            String line = in.readLine();
            if (line == null) {
                // Server closed connection
                throw new IOException(" S : Server unawares closed the connection.");
            }
            if (line.charAt(3) == ' ') {
                lines = lines + "\n" + line;
                // No more lines in the server response
                break;
            }
            // Add read line to the list of lines
            lines = lines + "\n" + line;
        }
        return lines;
    }
}
