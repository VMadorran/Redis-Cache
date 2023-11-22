package ar.unrn.tp.api;

import java.util.List;

import ar.unrn.tp.exception.CategoriaInvalidaException;
import ar.unrn.tp.exception.DatoVacioException;
import ar.unrn.tp.modelo.Producto;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;

public interface ProductoService {

	void ProductoService(EntityManagerFactory emf);

	// validar que sea una categoría existente y que codigo no se repita
	void crearProducto(Long codigo, String descripcion, double precio, Long idCategoria, String marca) throws Exception;

	// validar que sea un producto existente
	void modificarProducto(Long idProducto, String descripcion, double precio, String marca, Long idCategoria)
			throws DatoVacioException, CategoriaInvalidaException, OptimisticLockException;

	// Devuelve todos los productos
	List<Producto> listarProductos();
}
