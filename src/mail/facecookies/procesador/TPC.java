/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.procesador;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author mauriballes
 */
public class TPC {
    // Constantes
   private static final LinkedList<String> lexemas = new LinkedList<>(Arrays.asList(
            "HELP",
            "TRUE",
            "FALSE",   
            "CREARPERFIL",
            "MODIFICARPERFIL",
            "ELIMINARPERFIL",
            //////////////CU1/////////////////
            "VISUALIZARCONTACTO",
            "ELIMINARCONTACTO",
            ////////////CU2///////////////////
            "BUSCARAMIGOS",
            /////////////CU3/////////////////
            "BUSCARMENSAJES",
            /////////////CU4/////////////////
            "VISUALIZARSOLICITUD",
            "ENVIARSOLICITUD",
            "ACEPTARSOLICITUD",
            "RECHAZARSOLICITUD",
            /////////////CU5/////////////////
            "LEERMENSAJE",
            "ENVIARMENSAJE",
            "VISUALIZARNOTIFICACIONES",
            ////////////////CU6//////////////
            "REPORTENOTIFICACION",
            "REPORTEMENSAJES",
            "REPORTEAMIGOS",
            "REPORTEPERFILESMAYORES",
            "ESTADISTICAEDAD",
            "ESTADISTICASGENERO"
            
          //  "ESTADISTICASPERSONAS",
          //  
          //  "ESTADISTICAS"
            ////////////CU8//////////////////
    ));

    private static final LinkedList<Token> tokens = new LinkedList<>(Arrays.asList(
            new Token(Token.HELP, -1, "HELP"),
            new Token(Token.TRUE, -1, "TRUE"),
            new Token(Token.FALSE, -1, "FALSE"),
            new Token(Token.FUNC, Token.CREARPERFIL, "CREARPERFIL"),
            new Token(Token.FUNC, Token.MODIFICARPERFIL, "MODIFICARPERFIL"),
            new Token(Token.FUNC, Token.ELIMINARPERFIL, "ELIMINARPERFIL"),
            //
            new Token(Token.FUNC, Token.VISUALIZARCONTACTO, "VISUALIZARCONTACTO"),
            new Token(Token.FUNC, Token.ELIMINARCONTACTO, "ELIMINARCONTACTO"),
            //
            new Token(Token.FUNC, Token.BUSCARAMIGOS, "BUSCARAMIGOS"),
            //
            new Token(Token.FUNC, Token.BUSCARMENSAJES, "BUSCARMENSAJES"),
            //
            new Token(Token.FUNC, Token.VISUALIZARSOLICITUD, "VISUALIZARSOLICITUD"),
            new Token(Token.FUNC, Token.ENVIARSOLICITUD, "ENVIARSOLICITUD"),
            new Token(Token.FUNC, Token.ACEPTARSOLICITUD, "ACEPTARSOLICITUD"),
            new Token(Token.FUNC, Token.RECHAZARSOLICITUD, "RECHAZARSOLICITUD"),
            //
            new Token(Token.FUNC, Token.LEERMENSAJE, "LEERMENSAJE"),
            new Token(Token.FUNC, Token.ENVIARMENSAJE, "ENVIARMENSAJE"),
            new Token(Token.FUNC, Token.VISUALIZARNOTIFICACIONES, "VISUALIZARNOTIFICACIONES"),
            //
            new Token(Token.FUNC, Token.REPORTENOTIFICACION, "REPORTENOTIFICACION"),
            new Token(Token.FUNC, Token.REPORTEMENSAJES, "REPORTEMENSAJES"),
            new Token(Token.FUNC, Token.REPORTEAMIGOS, "REPORTEAMIGOS"),
            new Token(Token.FUNC, Token.REPORTEPERFILESMAYORES, "REPORTEPERFILESMAYORES"),
            //
            new Token(Token.FUNC, Token.ESTADISTICAEDAD, "ESTADISTICAEDAD"),
            new Token(Token.FUNC, Token.ESTADISTICASGENERO, "ESTADISTICASGENERO")
         //   new Token(Token.FUNC, Token.ESTADISTICASPERSONAS, "ESTADISTICASPERSONAS"),
         //   new Token(Token.FUNC, Token.ESTADISTICAEDAD, "ESTADISTICAEDAD"),
         //   new Token(Token.FUNC, Token.ESTADISTICAS, "ESTADISTICAS")

    ));

    public static Token estaEnTPC(String lexema) {
        lexema = lexema.toUpperCase();
        for (int i = 0; i < lexemas.size(); i++) {
            if (lexemas.get(i).toUpperCase().equals(lexema)) {
                Token token = new Token();
                token.setNombre(tokens.get(i).getNombre());
                token.setAtributo(tokens.get(i).getAtributo());
                token.setToStr(tokens.get(i).getToStr());
                return token;
            }
        }
        return null;
    }
}
