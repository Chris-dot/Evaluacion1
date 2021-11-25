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
    <title>Vender</title>
</head>

<body>

	<div class="col-12">
        <h2>Vender</h2>
        <div th:classappend="'alert-' + (${clase != null} ? ${clase} : info)" th:if="${mensaje != null}"
             th:text="${mensaje}"
             class="alert">
        </div>
        <form th:object="${producto}" th:action="@{/vender/agregar}" method="post">
            <div class="form-group">
                <label for="codigo">Código de barras</label>
                <input autofocus autocomplete="off" th:field="*{codigo}" id="codigo"
                       placeholder="Escanea el código o escríbelo y presiona Enter"
                       type="text"
                       class="form-control" th:classappend="${#fields.hasErrors('codigo')} ? 'is-invalid' : ''">
                <div class="invalid-feedback" th:errors="*{codigo}"></div>

            </div>
        </form>
        <form class="mb-2" th:action="@{/vender/terminar}" method="post">
            <button type="submit" class="btn btn-success">Terminar&nbsp;<i class="fa fa-check"></i>
            </button>
            <a th:href="@{/vender/limpiar}" class="btn btn-danger">Cancelar venta&nbsp;<i
                    class="fa fa-trash"></i>
            </a>
        </form>
        <h1 th:text="${'Total: ' + total}"></h1>
        <div class="table-responsive">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Nombre</th>
                    <th>Código</th>
                    <th>Precio</th>
                    <th>Cantidad</th>
                    <th>Total</th>
                    <th>Quitar de lista</th>
                </tr>
                </thead>
                <tbody>
                <tr th:each="producto, iterador : ${session.carrito}">
                    <td th:text="${producto.nombre}"></td>
                    <td th:text="${producto.codigo}"></td>
                    <td th:text="${producto.precio}"></td>
                    <td th:text="${producto.cantidad}"></td>
                    <td th:text="${producto.total}"></td>
                    <td>
                        <form th:action="@{/vender/quitar/} + ${iterador.index}" method="post">
                            <button type="submit" class="btn btn-danger"><i class="fa fa-trash"></i>
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