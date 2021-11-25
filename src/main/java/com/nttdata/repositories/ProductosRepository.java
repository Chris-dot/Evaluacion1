package com.nttdata.repositories;

import org.springframework.data.repository.CrudRepository;

import com.nttdata.models.Producto;

public interface ProductosRepository extends CrudRepository<Producto, Integer> {

	Producto findFirstBy(String codigo);

	Producto findFirstByCodigo(int id);

	Producto findFirstByCodigo(String codigo);


}
