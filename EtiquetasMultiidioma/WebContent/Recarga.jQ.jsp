<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedHashMap, java.util.Map"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String idioma = (String) request.getAttribute("idioma");
	String[] palabras = (String[]) request.getAttribute("palabras");
	Map<String,String> arrayImagenesBanderas = (LinkedHashMap<String,String>) request.getAttribute("arrayImagenesBanderas");
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>Etiquetas multiidioma - Recarga</title>
	<script src="js/jquery-1.11.3.min.js"></script>
	<script>
		$(document).ready(function() {
			$('input[name="idioma"]').change(function() {
				$('#formIdioma').submit();
			});
		});	
	</script>
</head>
<body>
	<form name="formIdioma" id="formIdioma" method="get" action="Recarga">
		<%= generaRadiosIdioma(idioma, arrayImagenesBanderas) %>
	</form>
	<hr />
	<table>
	<tr>
		<td><span id="spanPalabra">${palabras[0]}</span></td>
		<td><input type="text" name="palabra" /></td>
	</tr>
	<tr>
		<td><span id="spanTraduccion">${palabras[1]}</span></td>
		<td><input type="text" id="traduccion" readonly="readonly" /></td>
	</tr>
	<tr>
		<td colspan="2"><input type="button" value="${palabras[2]}" id="enviar" /></td>
	</tr>
	</table>
</body>
</html>

<%!
// Crear esta función aquí hace que no sea reutuilizable => No es buena práctica
String generaRadiosIdioma(String idiomaSeleccionado, Map<String,String> arrayImagenesBanderas) {
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

%>