package com.formulario.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import com.formulario.models.Usuario;
import com.formulario.services.UsuarioService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	@Autowired
	UsuarioService usuarioService;

	// Desplegar JSP usuario
	@RequestMapping("")
	public String usuario(@ModelAttribute("usuario") Usuario usuario, Model model) {
		model.addAttribute("usuario", new Usuario());
		return "usuario.jsp";
	}

	@RequestMapping("/login")
	public String login(@Valid @ModelAttribute("usuario") Usuario usuario) {
		
		if(validacionTotal(
				usuario.getNombre(),
				usuario.getApellido(),
				usuario.getLimite(),
				usuario.getCodigoPostal())) {
			usuarioService.insertarUsuario(usuario);
		}
		return "redirect:/usuario";
	}

	//Valida que la palabra recibida  esta entre un rango de longitud mayor a 0(no es vacio) y menor que el largo entregado
	public Boolean validarLargo(String palabra, Integer largo) {
		if (palabra.isBlank() == false) { //Si la palabra entregada no es un conjunto de espacios
			if (0 < palabra.length() && palabra.length() < largo) { //Se verifica que el largo de la palabra sea > a 0 y menor que el largo entregado
				return true; //Si la palabra cumple con el largo retorna verdadero
			} else {
				return false; //Si la palabra esta vacia o su largo es mayor al valor limite se retorna falso
			}
		} else {
			return false; //Si la palabra entregada es un conjunto de espacios se retorna falso
		}
	}

	//Valida que la palabra recibida esta entre un rango de longitud mayor a 0(no es vacio) y es igual al largo entregado
	public Boolean validarLargoIgual(String palabra, Integer largo) {
		if (palabra.isBlank() == false) {//Si la palabra entregada no es un conjunto de espacios
			if (0 < palabra.length() && palabra.length() == largo) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}

	}

	//Función que verifica que el valor del limite sea un numero positivo
	public Boolean limitePositivo(String limite) {//Recibe el string del limite
		try {
			int i = Integer.parseInt(limite); //Se transforma a un entero
			if (i > 0) {
				return true; //Si el valor del limite es positivo
			}
		}
		catch(NumberFormatException e){
		}
		return false;
	}

	public Boolean validacionTotal(String nombre, String apellido, String limite, String codigoPostal) {
		if (validarLargo(nombre, 11) && validarLargo(apellido, 11) && validarLargo(limite, 6)
				&& validarLargoIgual(codigoPostal, 8) && limitePositivo(limite)) {
			return true;
		} else {
			return false;
		}

	}

}