package servlets;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Recarga")
public class Recarga extends HttpServlet {
	

	private static final long serialVersionUID = 1L;

	// valores por defecto, para la primera carga, de las etiquetas en castellano
	private static String idiomaPorDefecto;
	private static String[] palabrasPorDefecto;
	private static Map<String,String[]> arrayTraducciones;
	private static Map<String,String> arrayImagenesBanderas;
	
	private String vistaDestino;
	private final static String RUTA_ARCHIVO_PROPIEDADES = "/WEB-INF/propiedades/";
	private final static String NOMBRE_ARCHIVO_PROPIEDADES = "config.properties";
	private final static String VISTA_POR_DEFECTO = "/Recarga.jQ.jsp";
	private static final String VISTA_DESTINO = "vista_destino_recarga";

	static
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
		String idioma = request.getParameter("idioma");
		String[] palabras;
		if ( (idioma != null) && (!idioma.isEmpty()) ) {  // se recibe un parámetro idioma no vacío
			palabras = arrayTraducciones.get(idioma); 
		} else {
			idioma = idiomaPorDefecto;
			palabras = palabrasPorDefecto;
		}
		request.setAttribute("idioma", idioma);
		request.setAttribute("palabras", palabras);
		request.setAttribute("arrayImagenesBanderas", arrayImagenesBanderas);
		
		// se define la vista destino a usar en un archivo externo de propiedades
		 cargarArchivoPropiedades();
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(vistaDestino);
		dispatcher.forward(request,response);
	}

	private void cargarArchivoPropiedades() {
		vistaDestino = VISTA_POR_DEFECTO ;
		Properties propiedades = new Properties();

		try {
			propiedades.load(getServletContext().getResourceAsStream(RUTA_ARCHIVO_PROPIEDADES + NOMBRE_ARCHIVO_PROPIEDADES));
			vistaDestino = propiedades.getProperty(VISTA_DESTINO);
		} catch (NullPointerException npE) {
			System.out.println("NO SE ACCEDE AL ARCHIVO DE PROPIEDADES" + " <br />");
			npE.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
