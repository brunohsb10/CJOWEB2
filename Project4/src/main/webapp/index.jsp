<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang ="pt-br">
<head>
<meta charset="UTF-8">
<title>Cadastro de pessoas</title>
</head>
<body>
	<form action="PersonServlet" method="post">
		<label for="people">Dados das pessoas:</label>
		<textarea rows="10" cols="80" name="people" id="people">
		</textarea>
		<input type="submit" value="Enviar">
	</form>
</body>
</html>
