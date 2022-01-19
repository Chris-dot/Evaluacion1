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
        <h2>Productos</h2>
        <div th:classappend="'alert-' + (${clase != null} ? ${clase} : info)" th:if="${mensaje != null}"
             th:text="${mensaje}"
             class="alert">
        </div>
        <a class="btn btn-success mb-2" th:href="@{/productos/agregar}">Agregar</a>
        <div class="table-responsive">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Código</th>
                    <th>Precio</th>
                    <th>Existencia</th>
                    <th>Editar</th>
                    <th>Eliminar</th>
                </tr>
                </thead>
                <tbody>
                <tr th:each="producto : ${productos}">
                    <td th:text="${producto.nombre}"></td>
                    <td th:text="${producto.codigo}"></td>
                    <td th:text="${producto.precio}"></td>
                    <td th:text="${producto.existencia}"></td>
                    <td>
                        <a class="btn btn-warning" th:href="@{/productos/editar/} + ${producto.id}">Editar <i
                                class="fa fa-edit"></i></a>
                    </td>
                    <td>
                        <form th:action="@{/productos/eliminar}" method="post">
                            <input type="hidden" name="id" th:value="${producto.id}"/>
                            <button type="submit" class="btn btn-danger">Eliminar <i class="fa fa-trash"></i>
                            </button>
                        </form>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
	
</body>

</html>