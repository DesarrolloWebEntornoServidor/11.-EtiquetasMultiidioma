package auxiliar;

import java.util.Map;

public class GeneradorControles {

	// Crear esta función aquí hace que no sea reutuilizable => No es buena práctica
	public static String generaRadiosIdioma(String idiomaSeleccionado, Map<String,String> arrayImagenesBanderas) {
		String salida = "";
		String chequeado = "";
		for (Map.Entry<String, String> elemento : arrayImagenesBanderas.entrySet()) {
			String clave = elemento.getKey();
			String valor = elemento.getValue();
			salida += "<img src=\"img/" + valor + "\" />";
			if (clave.equals(idiomaSeleccionado)) {
				chequeado = "checked=\"checked\"";
			}
			salida += "<input type=\"radio\" name=\"idioma\" value=\"" + clave + "\" " + chequeado + " />" + "\n";
			chequeado = "";
		}
		return salida;
	}
	
}