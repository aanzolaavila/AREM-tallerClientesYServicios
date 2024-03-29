package edu.eci.arem.tallerclientesyservicios.ejercicio451;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * El ćodigo 4 presenta un servidor web que atiende una solicitud. Implemente
 * el servidor e intente conectarse desde el browser.
 *
 * @author Alejandro Anzola email: alejandro.anzola@mail.escuelaing.edu.co
 */
public class HttpServer2 {

    public static final int SERVER_PORT = 35000;

    public static void main(String[] args) throws IOException {

        generateResponders();

        ServerSocket serverSocket = null;
        serverSocket = new ServerSocket(SERVER_PORT);

        Socket clientSocket = null;
        boolean finished = false;
        while (!finished) {
            clientSocket = serverSocket.accept();

            OutputStream out = clientSocket.getOutputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                processPetition(inputLine, out);
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }

            out.close();
            in.close();
            clientSocket.close();
        }

        serverSocket.close();
    }

    public static Map<String, Responder> respondersMap;

    public static void generateResponders() {
        respondersMap = new HashMap<>();
        respondersMap.put("/", (OutputStream out) -> {
            PrintWriter o = new PrintWriter(out, true);
            o.println("HTTP/1.1 200 OK\n"
                    + "Content-Type: text/html; charset=utf-8\n"
                    + "Content-Encoding: raw\n"
                    + "\n"
                    + "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "  <head>\n"
                    + "    <meta charset=\"utf-8\" />\n"
                    + "    <title>BPMN Documentation</title>\n"
                    + "    <link rel=\"stylesheet\" href=\"css/styles.css\">\n"
                    + " </link>\n"
                    + "  </head>\n"
                    + "  <body>\n"
                    + "    <img src=\"images/invie2x.png\">"
                    + "    <div class=\"contenedor\">\n"
                    + "      <h1 class=\"title\">BPMN Documentation</h1>\n"
                    + "      <h1>Titulo 1</h1>\n"
                    + "      <h2>Titulo 2</h2>\n"
                    + "      <h3>Titulo 3</h3>\n"
                    + "      <h4>Titulo 4</h4>\n"
                    + "      <h5>Titulo 5</h5>\n"
                    + "      <h6>Titulo 6</h6>\n"
                    + "\n"
                    + "      <ol>\n"
                    + "	<h1><li>Item 1</li></h1>\n"
                    + "	<h1><li>Item 2</li></h1>\n"
                    + "	<h1><li>Item 3</li></h1>\n"
                    + "	<h1><li>Item 4</li></h1>\n"
                    + "	<h1><li>Item 5</li></h1>\n"
                    + "	  <ol>\n"
                    + "	    <h2><li>SumItem 1</li></h2>\n"
                    + "	    <h2><li>SubItem 2</li></h2>\n"
                    + "	    <h2><li>SubItem 3</li></h2>\n"
                    + "	    <h2><li>SubItem 4</li></h2>\n"
                    + "	    <h2><li>SubItem 5<br />\n"
                    + "		<p>Blabla de definición</p>\n"
                    + "		<ol>\n"
                    + "		  <h3><li>Subsubitem 1</li></h3>\n"
                    + "		  <h3><li>Subsubitem 2</li></h3>\n"
                    + "		  <h3><li>Subsubitem 3</li></h3>\n"
                    + "		</ol>\n"
                    + "	    </li></h2>\n"
                    + "	  </ol>\n"
                    + "	</li>\n"
                    + "      </ol>\n"
                    + "    </div>\n"
                    + "  </body>\n"
                    + "</html>");
            o.close();
        });

        respondersMap.put("/images/invie2x.png", (OutputStream out) -> {
            File f = new File("invie2x.png");
            PrintStream o = new PrintStream(new BufferedOutputStream(out));
            o.print("HTTP/1.1 200 OK\r\n"
                    + "content-type: image/png\r\n"
                    + "accept-ranges: bytes\r\n"
                    + "content-length: " + f.length() + "\r\n"
                    + "\r\n\r\n");

            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(f);
                byte[] a = new byte[4096];
                int c;
                while ((c = inputStream.read(a)) > 0) {
                    System.out.println(c);
                    o.write(a, 0, c);
                }

                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                System.err.println("Error: " + ex);
            }

        });

        respondersMap.put("/css/styles.css", (OutputStream out) -> {
            PrintWriter o = new PrintWriter(out, true);
            o.println("HTTP/1.1 200 OK\n"
                    + "Content-Type: text/css; charset=utf-8\n"
                    + "Content-Encoding: raw\n"
                    + "\n"
                    + "body {\n"
                    + "    background-color: #990000;\n"
                    + "    font-family: arial, helvetica, sans-serif;\n"
                    + "    font-size: 15px;\n"
                    + "}\n"
                    + "\n"
                    + "h1, h2, h3, h4, h5, h6 {\n"
                    + "    color: #990000;\n"
                    + "    font-size: 18px;\n"
                    + "}\n"
                    + "\n"
                    + ".title {\n"
                    + "    font-size: 25px\n"
                    + "}\n"
                    + "\n"
                    + ".contenedor {\n"
                    + "    width: 1500px;\n"
                    + "    margin: auto;\n"
                    + "    position: relative;\n"
                    + "    height: 40%;\n"
                    + "    background-color: white;\n"
                    + "    padding: 30px;\n"
                    + "}\n"
                    + "\n"
                    + "h2 {\n"
                    + "    font-size: 17px;\n"
                    + "}\n"
                    + "\n"
                    + "h3 {\n"
                    + "    font-size: 16px;\n"
                    + "}\n"
                    + "\n"
                    + "h4 {\n"
                    + "    font-size: 15px;\n"
                    + "}\n"
                    + "\n"
                    + "h4, h5, h6 {\n"
                    + "    font-size: 14px;\n"
                    + "}\n"
                    + "\n"
                    + "p {\n"
                    + "    font-family: arial, helvetica, sans-serif;\n"
                    + "    font-size: 14px;\n"
                    + "    color: black;\n"
                    + "}\n"
                    + "\n"
                    + "ol {\n"
                    + "    list-style: none;\n"
                    + "    padding-left: 0;\n"
                    + "}\n");
            o.close();
        });
    }

    public static void processPetition(String petition, OutputStream out) {
        StringTokenizer strtok = new StringTokenizer(petition, " ");
        if (strtok.hasMoreElements() && ((String) strtok.nextElement()).equals("GET")) {
            System.out.println("GET petition recognized: " + petition);

            String resourceName = "";
            if (strtok.hasMoreElements()) {
                resourceName = ((String) strtok.nextElement());
                if (respondersMap.containsKey(resourceName)) {
                    System.out.println("GET petition is valid");
                    respondersMap.get(resourceName).respond(out); // responds petition
                }
            }
        }
    }

    private interface Responder {

        public void respond(OutputStream out);
    }

}
