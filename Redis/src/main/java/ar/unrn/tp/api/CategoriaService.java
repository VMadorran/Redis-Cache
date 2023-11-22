package ar.unrn.tp.api;

import java.util.List;

import ar.unrn.tp.modelo.Categoria;
import jakarta.persistence.EntityManagerFactory;

public interface CategoriaService {

	void CategoriaService(EntityManagerFactory emf);

	public void crearCategoria(Long codCategoria, String marca);

	public Categoria categoriaPorId(Long id);

	public List<Categoria> categorias();
}
