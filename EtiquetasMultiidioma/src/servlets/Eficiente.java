package servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Eficiente")
public class Eficiente extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// valores por defecto, para la primera carga, de las etiquetas en castellano
	private static String idiomaPorDefecto;
	private static String[] palabrasPorDefecto;
	private static Map<String,String[]> arrayTraducciones;
	private static Map<String,String> arrayImagenesBanderas;
	
	static  // inicialización de variables (atributos) de clase
    {
		idiomaPorDefecto = "ES";
		
		arrayTraducciones = new LinkedHashMap<String,String[]>();
		arrayTraducciones.put("ES", new String[] {"Palabra", "Traducción", "Enviar"}); 
		arrayTraducciones.put("EN", new String[] {"Word", "Translation", "Send"});
		arrayTraducciones.put("FR", new String[] {"Mot", "Traduction", "Envoyer"});
		
		palabrasPorDefecto = arrayTraducciones.get(idiomaPorDefecto);
				
		arrayImagenesBanderas = new LinkedHashMap<String,String>();
		arrayImagenesBanderas.put("ES","bandera-espania (18x26).gif");
		arrayImagenesBanderas.put("EN", "bandera-gran-bretania (18x27).gif");
		arrayImagenesBanderas.put("FR", "bandera-francia (18x27).gif");
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idioma = idiomaPorDefecto;
		String[] palabras = palabrasPorDefecto;
		
		// geberación de datos.js
		// URL url = Thread.currentThread().getContextClassLoader().getResource("com/youpackage/");
		String ruta = getServletContext().getRealPath("/js");
		// System.out.println(ruta);
		String nombreArchivo = File.separator + "datos.js";
		// System.out.println(nombreArchivo);

		synchronized(this) {  // se sincroniza el acceso al archivo para que sea thread-safe
			File archivo = new File(ruta + nombreArchivo);
			String datosJavaScript = datosJavaAJavaScript();
			if (archivo.createNewFile()) {  // si el archivo no existía, se crea  
			 	System.out.println("File created");
			 	PrintWriter writer = new PrintWriter(archivo, "UTF-8");
			 	writer.print(datosJavaScript);
			 	writer.close();
			} else {  // si el archivo existía y tenía una antigüedad mayor a un día, se reconstruye
			 	System.out.println("Failed to create file, it already existed");
			 	long fechaActual = System.currentTimeMillis();
			 	// long unDia = 1 * (24 * 60 * 60 * 1000);
			 	long diezMinutos = 10 * (60 * 1000);
			 	long vidaMaxima = diezMinutos;
			 	if (fechaActual - archivo.lastModified() > vidaMaxima) {  // el archivo es "viejo", se reconstruirá
			 		if (archivo.delete()) {  // se borra el archivo y se debe re-crear
			 			archivo.createNewFile();
			 			PrintWriter writer = new PrintWriter(archivo, "UTF-8");
			 			writer.print(datosJavaScript);
					 	writer.close();
			 		}
			 	}
			}
		}
		
		request.setAttribute("idioma", idioma);
		request.setAttribute("palabras", palabras);
		request.setAttribute("arrayImagenesBanderas", arrayImagenesBanderas);
		
		// se define la vista destino a usar en un archivo externo de propiedades
		String vistaDestino = "/Eficiente.jQ.jsp";
		Properties propiedades = new Properties();
		String rutaArchivoPropiedades = "/WEB-INF/propiedades/";
		String nombreArchivoPropiedades = "config.properties";
		try {
			propiedades.load(getServletContext().getResourceAsStream(rutaArchivoPropiedades + nombreArchivoPropiedades));
			vistaDestino = propiedades.getProperty("vista_destino_eficiente");
		} catch (NullPointerException npE) {
			System.out.println("NO SE ACCEDE AL ARCHIVO DE PROPIEDADES" + " <br />");
			npE.printStackTrace();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(vistaDestino);
		dispatcher.forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}
	
	/*
	  Función que convierte un Map Java en un array asociativo JavaScript
	  El Map de origen es arrayTraducciones
	  El array de destino es:

	    var palabras = new Array();
	    palabras['ES'] = new Array('Nombre', 'Dirección', 'Fecha de nacimiento');
	    palabras['EN'] = new Array('Name', 'Address', 'Date of birth');
	    palabras['FR'] = new Array('Nom', 'Adresse', 'Date de naissnace');
	*/
	private String datosJavaAJavaScript() {
		String texto = "";
		if (!arrayTraducciones.isEmpty()) {
		    texto += "// Este archivo JavaScript ha sido generado por un script Java" + System.lineSeparator();
			texto += "// Es la traducción de un Map Java a un array asociativo JavaScript" + System.lineSeparator();
			texto += "var palabras = new Array();" + System.lineSeparator();
			for (Map.Entry<String, String[]> elemento : arrayTraducciones.entrySet()) {
				String clave = elemento.getKey();
				texto += "palabras['" + clave + "'] = new Array(";
				String[] valor = elemento.getValue();
				for (String dato: valor) {
					texto += "'" + dato + "', ";
				}
				texto = texto.substring(0, texto.length()-2);  // al final del último elemento se eliminan el espacio y la coma 
				texto += ");" + System.lineSeparator();
			}
				
		}
		return texto;
	}

}
