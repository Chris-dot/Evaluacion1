package com.nttdata.models;

import org.springframework.stereotype.Controller;

@Controller
public class ProductoParaVenta extends Producto {
    private Float cantidad;

    public ProductoParaVenta(String nombre, String codigo, Float precio, Float existencia, Integer id, Float cantidad) {
        super(nombre, codigo, precio, existencia, id);
        this.cantidad = cantidad;
    }

    public ProductoParaVenta(String nombre, String codigo, Float precio, Float existencia, Float cantidad) {
        super(nombre, codigo, precio, existencia);
        this.cantidad = cantidad;
    }

    public void aumentarCantidad() {
        this.cantidad++;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public Float getTotal() {
        return this.getPrecio() * this.cantidad;
    }
}
