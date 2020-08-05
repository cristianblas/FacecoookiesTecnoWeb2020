/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.procesador;

/**
 *
 * @author mauriballes
 */
public class Token {
    // Constantes
    public static final int NUM = 0; // Numero Valor
    public static final int STRING = 1; // String Constante
    public static final int FUNC = 2; // Alguna Funcion
    public static final int GB = 3; //Guion Bajo
    public static final int CA = 4; // Corchete Abierto
    public static final int CC = 5; // Corchete Cerrado
    public static final int COMA = 6; // Coma ,
    public static final int FIN = 7;
    public static final int ERROR = 8;
    public static final int TRUE = 9;
    public static final int FALSE = 10;
    public static final int HELP = 11;
    
    //EVENTOS O ACCIONES
     public static final int CREARPERFIL = 100;
     public static final int MODIFICARPERFIL = 101;
     public static final int ELIMINARPERFIL = 102;
     
     public static final int VISUALIZARCONTACTO = 103;
     public static final int ELIMINARCONTACTO = 104;
     
     public static final int BUSCARAMIGOS = 105;
     public static final int BUSCARMENSAJES = 106;
     public static final int VISUALIZARSOLICITUD = 107;
     public static final int ENVIARSOLICITUD = 108;
     public static final int ACEPTARSOLICITUD = 109;
     public static final int RECHAZARSOLICITUD = 110;
     public static final int LEERMENSAJE = 111;
     public static final int ENVIARMENSAJE = 112;
     public static final int VISUALIZARNOTIFICACIONES = 113;
     public static final int REPORTENOTIFICACION = 114;
     public static final int REPORTEMENSAJES = 115;
     public static final int REPORTEAMIGOS = 116; 
     public static final int REPORTEPERFILESMAYORES = 117; 
     public static final int ESTADISTICAEDAD = 118;
     public static final int ESTADISTICASGENERO = 119;
     
    // public static final int ESTADISTICASPERSONAS = 117;
    // public static final int ESTADISTICAS = 119;


     
     
     
     
    private int nombre;
    private float atributo;
    private String toStr;

    public Token() {
    }

    public Token(int nombre, float atributo, String toStr) {
        this.nombre = nombre;
        this.atributo = atributo;
        this.toStr = toStr;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public float getAtributo() {
        return atributo;
    }

    public void setAtributo(float atributo) {
        this.atributo = atributo;
    }

    public String getToStr() {
        return toStr;
    }

    public void setToStr(String toStr) {
        this.toStr = toStr;
    }
}
