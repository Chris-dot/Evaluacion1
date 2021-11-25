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
    <title>Ver Ventas</title>
</head>

<body>

	<div class="col-12">
        <h2>Ventas</h2>
        <div th:classappend="'alert-' + (${clase != null} ? ${clase} : info)" th:if="${mensaje != null}"
             th:text="${mensaje}"
             class="alert">
        </div>
        <a class="btn btn-success mb-2" th:href="@{/vender/}">Agregar</a>
        <div class="table-responsive">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Fecha</th>
                    <th>Total</th>
                    <th>Productos</th>
                </tr>
                </thead>
                <tbody>
                <tr th:each="venta : ${ventas}">
                    <td th:text="${venta.fechaYHora}"></td>
                    <td th:text="${venta.total}"></td>
                    <td>
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th>Nombre</th>
                                <th>Código de barras</th>
                                <th>Cantidad vendida</th>
                                <th>Precio</th>
                                <th>Total</th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr th:each="producto : ${venta.productos}">
                                <td th:text="${producto.nombre}"></td>
                                <td th:text="${producto.codigo}"></td>
                                <td th:text="${producto.cantidad}"></td>
                                <td th:text="${producto.precio}"></td>
                                <td th:text="${producto.total}"></td>
                            </tr>
                            </tbody>
                        </table>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
	
</body>

</html>