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
    <title>Productos</title>
</head>

<body>

	<div class="col-12">
        <h2>Editar producto </h2>
        <form th:object="${producto}" th:action="@{/productos/editar/} + ${producto.id}" method="post">
            <input type="hidden" th:field="*{id}">
            <div class="form-group">
                <label for="nombre">Nombre del producto</label>
                <input autocomplete="off" autofocus th:field="*{nombre}" id="nombre"
                       placeholder="Escribe el nombre del producto" type="text"
                       class="form-control" th:classappend="${#fields.hasErrors('nombre')} ? 'is-invalid' : ''">
                <div class="invalid-feedback" th:errors="*{nombre}"></div>
            </div>
            <div class="form-group">
                <label for="codigo">Código de barras</label>
                <input autocomplete="off" th:field="*{codigo}" id="codigo" placeholder="Escribe el código del producto"
                       type="text"
                       class="form-control" th:classappend="${#fields.hasErrors('codigo')} ? 'is-invalid' : ''">
                <div class="invalid-feedback" th:errors="*{codigo}"></div>
            </div>
            <div class="form-group">
                <label for="existencia">Existencia actual</label>
                <input autocomplete="off" th:field="*{existencia}" id="existencia"
                       placeholder="Cantidad actual del producto" type="number"
                       class="form-control" th:classappend="${#fields.hasErrors('existencia')} ? 'is-invalid' : ''">
                <div class="invalid-feedback" th:errors="*{existencia}"></div>
            </div>
            <div class="form-group">
                <label for="existencia">Precio</label>
                <input autocomplete="off" th:field="*{precio}" id="precio" placeholder="Precio del producto"
                       type="number"
                       class="form-control" th:classappend="${#fields.hasErrors('precio')} ? 'is-invalid' : ''">
                <div class="invalid-feedback" th:errors="*{precio}"></div>
            </div>
            <button class="btn btn-success" type="submit">Guardar</button>
            &nbsp;<a class="btn btn-warning" th:href="@{/productos/mostrar}">Ver todos</a>
        </form>
    </div>
	
</body>

</html>
