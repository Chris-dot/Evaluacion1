package com.nttdata.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ventas {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String detalle;
	
	public Ventas() {
		super();
	}

	public Ventas(Integer id, String detalle) {
		super();
		this.id = id;
		this.detalle = detalle;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	
	
	
}