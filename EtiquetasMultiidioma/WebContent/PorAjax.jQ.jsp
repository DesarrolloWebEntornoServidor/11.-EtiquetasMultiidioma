<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedHashMap, java.util.Map, auxiliar.GeneradorControles"%>
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
	<title>Etiquetas multiidioma - Por Ajax</title>
	<script src="js/jquery-1.11.3.min.js"></script>
	<script>
		$(document).ready(function() {

			// cambio de idioma de las etiquetas por Ajax
		  	$('input[name="idioma"]').change(function() {
				$.ajax({
		        	data: $('#formIdioma').serialize(),
		        	type: "POST",
		        	url: "PorAjax",
		        	timeout: 20000,
		        	contentType: "application/x-www-form-urlencoded;charset=UTF-8"
				})
				.done(function(respuesta){
			    	// se devuelve una cadena que representa las etiquetas separadas por #
					$('#palabra').html(respuesta);
		        	var arrayPalabras = respuesta.split("#");
					$('#spanPalabra').html(arrayPalabras[0]);
					$('#spanTraduccion').html(arrayPalabras[1]);
					$('#enviar').val(arrayPalabras[2]);
				});
			});
		});
	</script>
</head>
<body>
	<form name="formIdioma" id="formIdioma" method="get" action="Recarga">
		<%= GeneradorControles.generaRadiosIdioma(idioma, arrayImagenesBanderas) %>  <%-- Método ubicado en una clase externa => Reutilizable --%>
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