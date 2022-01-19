package com.nttdata.controllers;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nttdata.models.Producto;
import com.nttdata.models.ProductoParaVenta;
import com.nttdata.repositories.VentasRepository;

@Controller
@RequestMapping(path = "/ventas")
public class VentasController {
	@Autowired
	VentasRepository ventasRepository;
	
	@GetMapping(value="/")
	public String mostrarVentas(Model model) {
		model.addAttribute("ventas", ventasRepository.findAll());
		return "ventas/ver-ventas";
	}
	
	private ArrayList<ProductoParaVenta> obtenerCarrito(HttpServletRequest request) {
	    ArrayList<ProductoParaVenta> carrito = (ArrayList<ProductoParaVenta>) request.getSession().getAttribute("carrito");
	    if (carrito == null) {
	        carrito = new ArrayList<>();
	    }
	    return carrito;
	}

	private void guardarCarrito(ArrayList<ProductoParaVenta> carrito, HttpServletRequest request) {
	    request.getSession().setAttribute("carrito", carrito);
	}
	
	@GetMapping(value = "/")
	public String interfazVender(Model model, HttpServletRequest request) {
	    model.addAttribute("producto", new Producto());
	    float total = 0;
	    ArrayList<ProductoParaVenta> carrito = this.obtenerCarrito(request);
	    for (ProductoParaVenta p: carrito) total += p.getTotal();
	    model.addAttribute("total", total);
	    return "vender/vender";
	}
	
	
	
}
