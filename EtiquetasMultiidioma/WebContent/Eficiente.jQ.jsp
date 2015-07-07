<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedHashMap, java.util.Map, auxiliar.GeneradorControles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String idioma = (String) request.getAttribute("idioma");
	String[] palabras = (String[]) request.getAttribute("palabras");  // lista de palabras del isioma por defecto
	Map<String,String> arrayImagenesBanderas = (LinkedHashMap<String,String>) request.getAttribute("arrayImagenesBanderas");
%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<title>Etiquetas multiidioma - Eficiente</title>
	<script src="js/datos.js"></script>
	<script src="js/jquery-1.11.3.min.js"></script>
	<script>
		$(document).ready(function() {

			// cambio de idioma de las etiquetas por Ajax$( document ).ready(function() {
  			$("input[name='idioma']").change(function(){
    			// alert($(this).val());
				$("#spanPalabra").html(palabras[$(this).val()][0]);
				$("#spanTraduccion").html(palabras[$(this).val()][1]);
				$("#enviar").val(palabras[$(this).val()][2]);
  			});
		});
	</script>
</head>
<body>
	<form name="formIdioma" id="formIdioma">
		<%= GeneradorControles.generaRadiosIdioma(idioma, arrayImagenesBanderas) %>
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