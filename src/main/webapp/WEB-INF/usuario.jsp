<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Usuario</title>
</head>

<body>
Formulario Usuario: <br>

	<div class="container">
		<form:form action="/usuario/login" method="POST" modelAttribute="usuario">
			<form:label path="nombre">Nombre: </form:label>
			<form:input class="form-control" type="text" path="nombre" /> <br>
			
			<form:label path="apellido">Apellido: </form:label>
			<form:input class="form-control" type="text" path="apellido" /> <br>
			
			<form:label path="email">Email: </form:label>
			<form:input class="form-control" type="text" path="email" /> <br>
			
			<form:label path="codigoPostal">Codigo Postal: </form:label>
			<form:input class="form-control" type="text" path="codigoPostal" /> <br>
			
			<button  class="btn btn-warning mb-3" value="limpiar"> Limpiar </button>
			<button  class="btn btn-primary mb-3" type="submit" value="enviar"> Enviar </button>
		</form:form>
	</div>

    
</body>

</html>