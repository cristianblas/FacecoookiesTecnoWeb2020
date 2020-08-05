/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
    package mail.facecookies.utils;

  
    import mail.facecookies.presentador.Block;
    import mail.facecookies.presentador.Board;
    import mail.facecookies.presentador.Table;
    import java.sql.Date;
    import java.text.ParseException;
    import java.text.SimpleDateFormat;
    import java.util.ArrayList;
    import java.util.Arrays;
    import java.util.LinkedList;
    import java.util.List;
    import javax.swing.table.DefaultTableModel;
 



    /**
     *
     * @author home
     */
    public class Utils {
    public static String getDestinatario(String contenido) {
            String destinatario = "";
            // Dividir en lineas
            String[] lines = contenido.split("\n");
            if(lines[2].contains("Return-Path:")){
                int inicio=lines[2].indexOf("<");
                int fin=lines[2].indexOf(">");
                String s=lines[2].substring(inicio+1, fin);
                //System.out.println("Linea 2 : "+s);
                if(s.contains(".com"))return s;
                return s;
            }
            int index = -1;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].length() > 5
                        && lines[i].substring(0, 5).toUpperCase().equals("FROM:")) {
                    index = i;
                    break;
                }
            }
            if (index > -1) {
                // Quitar la palabra 'From: '
                destinatario = lines[index].substring(6);
                lines = destinatario.split(" ");
                if (lines.length == 1) { // Correo del Server
                    destinatario = lines[0];
                } else { // Desde otro Servidor de Correo
                    destinatario = lines[lines.length - 1];
                    destinatario = destinatario.split("<")[1].split(">")[0];
                }
            }
            return destinatario;
        }
    public static String getSubjectOrden(String contenido) {
            String orden = "";
            // Dividir en lineas
            String[] lines = contenido.split("\n");
            int index = -1;
            for (int i = 0; i < lines.length; i++) {
                if (lines[i].length() > 9
                        && lines[i].substring(0, 9).toUpperCase().equals("SUBJECT: ")) {
                    index = i;
                    break;
                }
                if (lines[i].length() > 14
                        && lines[i].substring(0, 14).toUpperCase().equals("SUBJECT: FWD: ")) {
                    index = i;
                    break;
                }
            }
            if (index > -1) {
                orden = lines[index].substring(9)+lines[index+1].substring(0)+lines[index+2].substring(0)+lines[index+3].substring(0);
                //System.out.println("Mensaje : "+orden);
                int i=orden.indexOf("Thread-Topic");
                if(i==-1) i=orden.indexOf("To");
                if(i==-1) i=orden.indexOf("MIME-Version:");
                if(i!=-1) orden=orden.substring(0,i);
            }
            return orden;

        }
    public static Date convertirFechas(String fecha) {
            // Formato de fecha a ingresar dd-MM-yyyy
            Date fechaNueva = null;
            SimpleDateFormat formato = new SimpleDateFormat("dd-MM-yyyy");
            try {
                java.util.Date fechaJava = formato.parse(fecha);
                fechaNueva = new Date(fechaJava.getTime());
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
            return fechaNueva;
        }
    public static String quitarComillas(String texto) {
            int len = texto.length() - 1;
            return texto.substring(1, len);
        }



        // a partir de aqui para las "vistas"    
        public static Mensaje TablaAyuda() {
            String Cabecera="BIENVENIDO A FACECOOKIES AYUDA\n"
                    ;
            LinkedList<String> Head = new LinkedList<>(Arrays.asList(
                    "Subject:",
                    "Ejemplo del Parametro",
                    "Recomendacion"
            ));
            LinkedList<String> Ejemplos = new LinkedList<>(Arrays.asList(

                "Repita El Email De Donde Envia La Orden",
                "(_) = No Modificar, para Modificar El Email Debe Enviar La Orden Desde El Nuevo Email",
                "",
                //////////////CU1/////////////////
                "",
                "",
                ////////////CU2///////////////////
                "",
                /////////////CU3/////////////////
                "",
                /////////////CU4/////////////////
                "",
                "",
                "",
                "",
                /////////////CU5/////////////////
                "",
                "",
                "",
                ////////////////CU6//////////////
                "",
                //////////CU7////////////////////
                ""
                ////////////CU8//////////////////
            ));
            LinkedList<String> Opciones = new LinkedList<>(Arrays.asList(
                "CREARPERFIL(\"Nombre Completo\")(\"Fecha de Nacimiento\")(\"Sexo\")(Celular)(\"Email\")(\"Password\")",
                "MODIFICARPERFIL(ID DE TU PERFIL)(\"Nombre Completo\")(\"Fecha de Nacimiento\")(\"Sexo\")(Celular)(\"Email\")(\"Password\")",
                "ELIMINARPERFIL(ID DE TU PERFIL)(\"Email Del ID\")",
                "VISUALIZARCONTACTO(ID DE TU PERFIL)",
                "ELIMINARCONTACTO(ID DE TU PERFIL)(\"Nombre del Contacto Bien Escrito\")",
                "BUSCARAMIGOS(\"Nombre A Buscar\")",
                "BUSCARMENSAJES(ID DE TU PERFIL)",
                "VISUALIZARSOLICITUD(ID DE TU PERFIL)",
                "ENVIARSOLICITUD(ID DE TU PERFIL)(\"Nombre Completo Del Perfil\")",
                "ACEPTARSOLICITUD(ID DE TU PERFIL)(\"Nombre Completo Del Perfil\")",
                "RECHAZARSOLICITUD(ID DE TU PERFIL)(\"Nombre Completo Del Perfil\")",
                "LEERMENSAJE(ID DE TU PERFIL)(\"Nombre Completo Del Perfil\")",
                "ENVIARMENSAJE(ID DE TU PERFIL)(\"Nombre Completo Del Perfil\")(\"Contenido Del Mensaje\")",
                "VISUALIZARNOTIFICACIONES(ID DE TU PERFIL)",
                "REPORTES(ID DE TU PERFIL)",
                "ESTADISTICAS(ID DE TU PERFIL)"
            ));        
            LinkedList<String> Detalles = new LinkedList<>(Arrays.asList(
                "(\"dtoke sheke sheka\")(\"13-10-1994\")(\"f\")(68821534)(\"rva@Gmail.com\")(\"12345\")",
                "(2)(\"mks basura basura\")(_)(_)(_)(_)(_)",
                "(2)(\"rva@Gmail.com\")",
                "(16)",
                "(16)(\"dtoke sheke sheka\")",
                "(\"dtoke\")",
                "(16)",  
                "(16)",  
                "(16)(\"dtoke sheke sheka\")",
                "(16)(\"dtoke sheke sheka\")",
                "(16)(\"daria lopez\")",
                "(16)(\"dtoke sheke sheka\")",
                "(16)(\"dtoke sheke sheka\")(\"hola dto vamos a la plaza ?\")",
                "(16)",
                "(16)",
                "(16)"
            ));
            String data = "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "<head>\n"  
                    + "<style>\n"
                    + "h1 {\n"
                    + "    color: black;\n"
                    + "}\n"
                    + "h2,img {\n"
                    + "    display: inline-block;\n"
                    + "}\n"
                    + "img{"
                    + "    float: right;\n"
                    + "    vertical-align: top;\n"
                    + "    margin-bottom: 0.45em;"
                    + "}\n"
                    + "table {\n"
                    + "    border-collapse: collapse;\n"
                    //+ "    border: green 5px solid;"
                    + "    width: 100%;\n"
                    + "}\n"
                    + "\n"
                    + "th, td {\n"
                    + "    border: 1px solid black;"
                    + "    text-align: center;\n"
                    + "    padding: 10px;\n"
                    + "}\n"
                    + "\n"
                    + "tr:nth-child(even){background-color: #f2f2f2}\n"
                    + "\n"
                    + "th {\n"
                    + "    background-color: #010721;\n"
                    + "    color: white;\n"
                    + "}\n"
                    + "</style>\n"
                    + "</head>\n"
                    + "<body>\n"
                    + "<font size=6 color=\"#483D8B\" face=\"Segoe Script\">\n" 
                    + "  <h1 align=\"center\">Facecookies</h1>\n"
                    + "</font>"
                    + " <br>"  
                    + "<h1 align=\"center\"><i>"+Cabecera+"</i></h1>\n" +
                    "  <font face=\"Comic Sans MS,arial\">\n" +
                    "  A continuacion Se Listaran Recomendaciones y Restricciones Para La Interacion Con El Sistema De Faceccokies\n" +
                    "  <br>\n" +
                    "  <br>\n" +
                    "  <b>1.-</b> Procure Recordar su ID  \n" +
                    "  <br>\n" +
                    "  <b>2.-</b> No Ingresar Caracteres Especiales, letra ñ(Ñ) ni caracteres con tildes \n" +
                    "  <br>\n" +
                    "  <b>3.-</b> Procure Escribir los parametros En Minuscula y Solo Ordenes En Mayuscula\n" +
                    "  <br> \n" +
                    "  <b>4.-</b> Para CREAR , MODIFICAR Y ELIMINAR debe Interactuar Con El Email De Su Perfil\n" +
                    "  <br> \n" +
                    "  </font>"
                    
                   
                    + "<table class=\"w3-table-all\">\n";
            data=data+"<tr>\n";
            for(int i=0;i<Head.size();i++){
                data=data+"<th>"+Head.get(i)+"</th>\n";
            }
            data=data+"</tr>\n";
            // Agregando Content
            for (int i = 0; i < Opciones.size(); i++) {
                data=data+"<tr>";
                data=data+"<td><strong>"+Opciones.get(i)+"</strong></td>";
                data=data+"<td><strong>"+Detalles.get(i)+"</strong></td>";
                data=data+"<td>"+Ejemplos.get(i)+"</td>";
                data=data+"</tr>\n";
            }
            data += "</table>\n"
                    + "</body>\n"
                    + "</html>\n";
            Mensaje mensaje = new Mensaje(Cabecera, data);
            return mensaje;
        }
        public static String dibujarTabla1(DefaultTableModel tabla, String Head[]) {
            String tableString = "";
            ArrayList<String> headers = new ArrayList<>();
            ArrayList<List<String>> rowList = new ArrayList<>();
            // Agregando Los Headers
            String Resultados="";
            //System.out.println("+-------------------------------------------------------------");
            Resultados=Resultados+"+-------------------------------------------------------------+\n";
            //System.out.println("|\tLISTADO DE REGISTROS : ");
            Resultados=Resultados+"\tDatos Del Perfil Facecookies : \n";
            // Agregando Los Headers
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                headers.add(tabla.getColumnName(i));
            }
            //System.out.println("|-------------------------------------------------------------");
            Resultados=Resultados+"+-------------------------------------------------------------+\n"
                    + "Muy Importante Recordar El ID De Su Perfil \n";

            // Agregando Content
            for (int i = 0; i < tabla.getRowCount(); i++) {
                ArrayList<String> row = new ArrayList<>();
                for (int j = 0; j < tabla.getColumnCount(); j++) {
                    row.add(String.valueOf(tabla.getValueAt(i, j)));
                    //System.out.println("|\t"+Aumentar(Head[j])+row.get(j));
                    if(Head[j].contains("Tipo")){
                        if(row.get(j).contains("1")){
                            row.set(j,"Administrador");
                        }else if(row.get(j).contains("2")){
                            row.set(j,"Instructor");
                        }else if(row.get(j).contains("3")){
                            row.set(j,"Socio");
                        }
                    }else if(Head[j].contains("Estado")){
                        if(row.get(j).contains("t")){
                            row.set(j,"Activo");
                        }else if(row.get(j).contains("f")){
                            row.set(j,"Inactivo");
                        }
                    }
                    Resultados=Resultados+"\t"+Head[j]+"  "+row.get(j)+'\n';
                }
                rowList.add(row.subList(0, row.size()));
                //System.out.println("+-------------------------------------------------------------");
                if(i!=tabla.getRowCount()-1)Resultados=Resultados+"-------------------------------------------------------------\n";
                else Resultados=Resultados+"+-------------------------------------------------------------+\n";
            }

            if (rowList.size() < 1) {
                return "(Tabla Vacia)";
            }

            // Creando Tabla para mostrar
    //        Board board = new Board(250);
    //        Table table = new Table(board, 250, headers, rowList);
    //        Block tableBlock = table.tableToBlocks();
    //        board.setInitialBlock(tableBlock);
    //        board.build();
    //        tableString = board.getPreview();
            System.out.println(Resultados);
            //return tableString;
            return Resultados;
        }
        public static String dibujarTabla2(DefaultTableModel tabla, String Head[]) {
            String tableString = "";
            ArrayList<String> headers = new ArrayList<>();
            ArrayList<List<String>> rowList = new ArrayList<>();
            // Agregando Los Headers
            String Resultados="";
            Resultados=Resultados+"+-------------------------------------------------------------+\n";
            Resultados=Resultados+"\tBienvenido a Facecookies :D \n\n"
                    + "\tPerfil Modificado Con Exito : \n";
            // Agregando Los Headers
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                headers.add(tabla.getColumnName(i));
            }
            Resultados=Resultados+"+-------------------------------------------------------------+\n";
            // Agregando Content
            for (int i = 0; i < tabla.getRowCount(); i++) {
                ArrayList<String> row = new ArrayList<>();
                for (int j = 0; j < tabla.getColumnCount(); j++) {
                    row.add(String.valueOf(tabla.getValueAt(i, j)));
                    Resultados=Resultados+"\t"+Head[j]+"  "+row.get(j)+'\n';
                }
                rowList.add(row.subList(0, row.size()));
                //System.out.println("+-------------------------------------------------------------");
                if(i!=tabla.getRowCount()-1)Resultados=Resultados+"-------------------------------------------------------------\n";
                else Resultados=Resultados+"+-------------------------------------------------------------+\n";
            }

            if (rowList.size() < 1) {
                return "(Tabla Vacia)";
            }
            return Resultados;
        }
        public static String dibujarTabla3(DefaultTableModel tabla, String Head[]) {
            String tableString = "";
            ArrayList<String> headers = new ArrayList<>();
            ArrayList<List<String>> rowList = new ArrayList<>();
            // Agregando Los Headers
            String Resultados="";
            Resultados=Resultados+"+-------------------------------------------------------------+\n";
            Resultados=Resultados+"\tBienvenido a Facecookies :D \n\n"
                    + "Tus Amigos Son : \n";
            // Agregando Los Headers
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                headers.add(tabla.getColumnName(i));
            }
            Resultados=Resultados+"+-------------------------------------------------------------+\n";
            // Agregando Content
            for (int i = 0; i < tabla.getRowCount(); i++) {
                ArrayList<String> row = new ArrayList<>();
                for (int j = 0; j < tabla.getColumnCount(); j++) {
                    row.add(String.valueOf(tabla.getValueAt(i, j)));
                    Resultados=Resultados+"\t"+Head[j]+"  "+row.get(j)+'\n';
                }
                rowList.add(row.subList(0, row.size()));
                //System.out.println("+-------------------------------------------------------------");
                if(i!=tabla.getRowCount()-1)Resultados=Resultados+"-------------------------------------------------------------\n";
                else Resultados=Resultados+"+-------------------------------------------------------------+\n";
            }

            if (rowList.size() < 1) {
                return "Agenda Vacia";
            }
            return Resultados;
        }
        /// para no crear hartos metodos esta tabla sera general a partir del caso de uso 3 BUSCAAMIGO
        public static String dibujarTablaGENERAL(DefaultTableModel tabla, String Head[],String Encabeezado) {
            String tableString = "";
            ArrayList<String> headers = new ArrayList<>();
            ArrayList<List<String>> rowList = new ArrayList<>();
            // Agregando Los Headers
            String Resultados="";
            Resultados=Resultados+"+-------------------------------------------------------------+\n";
            Resultados=Resultados+"\tBienvenido a Facecookies :D \n\n"
                    + Encabeezado 
                    + "\n" ; 
            // Agregando Los Headers
            for (int i = 0; i < tabla.getColumnCount(); i++) {
                headers.add(tabla.getColumnName(i));
            }
            Resultados=Resultados+"+-------------------------------------------------------------+\n";
            // Agregando Content
            for (int i = 0; i < tabla.getRowCount(); i++) {
                ArrayList<String> row = new ArrayList<>();
                for (int j = 0; j < tabla.getColumnCount(); j++) {
                    row.add(String.valueOf(tabla.getValueAt(i, j)));
                    Resultados=Resultados+"\t"+Head[j]+"  "+row.get(j)+'\n';
                }
                rowList.add(row.subList(0, row.size()));
                //System.out.println("+-------------------------------------------------------------");
                if(i!=tabla.getRowCount()-1)Resultados=Resultados+"-------------------------------------------------------------\n";
                else Resultados=Resultados+"+-------------------------------------------------------------+\n";
            }

            if (rowList.size() < 1) {
                return "(Tabla Vacia)";
            }
            return Resultados;
        }

        
        
        
        
        
    
    // reportes y estadisticas
        public static Mensaje GraficosReportesEstadisticas(DefaultTableModel tabla, String Head[],String Cabecera) {
      
        ArrayList<String> headers = new ArrayList<>();
        ArrayList<List<String>> rowList = new ArrayList<>();
        // Agregando Los Headers
        for (int i = 0; i <= tabla.getColumnCount(); i++) {
            headers.add(tabla.getColumnName(i));
        }
        //System.out.println("|-------------------------------------------------------------");       
        
        String data = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "h1 {\n"
                + "    color: black;\n"
                + "}\n"
                + "h2,img {\n"
                + "    display: inline-block;\n"
                + "}\n"
                + "img{"
                + "    float: right;\n"
                + "    vertical-align: top;\n"
                + "    margin-bottom: 0.45em;"
                + "}\n"
                + "table {\n"
                + "    border-collapse: collapse;\n"
                //+ "    border: green 5px solid;"
                + "    width: 100%;\n"
                + "}\n"
                + "\n"
                + "th, td {\n"
                + "    border: 1px solid black;"
                + "    text-align: center;\n"
                + "    padding: 10px;\n"
                + "}\n"
                + "\n"
                + "tr:nth-child(even){background-color: #f2f2f2}\n"
                + "\n"
                + "th {\n"
                + "    background-color: #010721;\n"
                + "    color: white;\n"
                + "}\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<font size=6 color=\"#483D8B\" face=\"Segoe Script\">\n"
                + "<h1 align=\"center\">Facecookies</h1>\n"
                + "</font>\n"
                + "<br>"
                + "<h1>"+Cabecera+"</h1> \n"
                + "<table class=\"w3-table-all\">\n"
                + "<tr>\n";
        for(int i=0;i<tabla.getColumnCount();i++){
            data=data+"<th>"+Head[i]+"</th>\n";
        }
        data=data+"</tr>\n";
        // Agregando Content        
        for (int i = 0; i < tabla.getRowCount() ; i++) {
            data=data+"<tr>";
                ArrayList<String> row = new ArrayList<>();
                for (int j = 0; j < tabla.getColumnCount(); j++) {
                    data=data+"<td><strong>";
                    row.add(String.valueOf(tabla.getValueAt(i, j)));
                    data=data+row.get(j) + '\n';
                    data=data+"</strong></td>";
                }
                rowList.add(row.subList(0, row.size()));
                if(i!=tabla.getRowCount()-1)data=data+" \n";
                else data=data+"\n";
            data=data+"</tr>\n";    
            }
        data += "</table>\n"
                + "</body>\n"
                + "</html>\n";
        
        if (rowList.size() < 1) {
            data= "(Tabla Vacia)";
        }
        Mensaje mensaje = new Mensaje(Cabecera, data);
        return mensaje;
            
        }

        
        public static Mensaje GraficosReportesEstadisticasEdad(DefaultTableModel tabla, String Head[],String Cabecera) {
        int p = 0 ;
        int o = 0 ;
        int resultado =0 ;
        ArrayList<String> headers = new ArrayList<>();
        ArrayList<List<String>> rowList = new ArrayList<>();
        // Agregando Los Headers
        for (int i = 0; i <= tabla.getColumnCount(); i++) {
            headers.add(tabla.getColumnName(i));
        }
        //System.out.println("|-------------------------------------------------------------");       
        
        String data = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "h1 {\n"
                + "    color: black;\n"
                + "}\n"
                + "h2,img {\n"
                + "    display: inline-block;\n"
                + "}\n"
                + "img{"
                + "    float: right;\n"
                + "    vertical-align: top;\n"
                + "    margin-bottom: 0.45em;"
                + "}\n"
                + "table {\n"
                + "    border-collapse: collapse;\n"
                //+ "    border: green 5px solid;"
                + "    width: 100%;\n"
                + "}\n"
                + "\n"
                + "th, td {\n"
                + "    border: 1px solid black;"
                + "    text-align: center;\n"
                + "    padding: 10px;\n"
                + "}\n"
                + "\n"
                + "tr:nth-child(even){background-color: #f2f2f2}\n"
                + "\n"
                + "th {\n"
                + "    background-color: #010721;\n"
                + "    color: white;\n"
                + "}\n"
                + "</style>\n"
                + "</head>\n"
                + "<body>\n"
                + "<font size=6 color=\"#483D8B\" face=\"Segoe Script\">\n"
                + "<h1 align=\"center\">Facecookies</h1>\n"
                + "</font>\n"
                + "<br>"
                + "<h1>"+Cabecera+"</h1> \n"
                + "<table class=\"w3-table-all\">\n"
                + "<tr>\n";
        for(int i=0;i<tabla.getColumnCount();i++){
            data=data+"<th>"+Head[i]+"</th>\n";
        }
        data=data+"</tr>\n";
        // Agregando Content        
        for (int i = 0; i < tabla.getRowCount() ; i++) {
            data=data+"<tr>";
                ArrayList<String> row = new ArrayList<>();
                for (int j = 0; j < tabla.getColumnCount(); j++) {
                    data=data+"<td><strong>";
                    row.add(String.valueOf(tabla.getValueAt(i, j)));
                    if(j % 2 != 0){
                          p = p + 1 ;
                          o = o + Integer.parseInt(row.get(j)) ;  
                    }
                    data=data+row.get(j) + '\n';
                    data=data+"</strong></td>";
                }
                rowList.add(row.subList(0, row.size()));
                if(i!=tabla.getRowCount()-1)data=data+" \n";
                else data=data+"\n";
            data=data+"</tr>\n";    
            }
        resultado = o / p ;
        data=data+"<tr>";
        data = data + "El Promedio Es:" + resultado ; 
        data=data+"</tr>\n"; 
        data += "</table>\n"
                + "</body>\n"
                + "</html>\n";
        
            System.out.println(p);
            System.out.println(o);
        if (rowList.size() < 1) {
            data= "(Tabla Vacia)";
        }
        Mensaje mensaje = new Mensaje(Cabecera, data);
        return mensaje;
            
        }
            
 }
      
