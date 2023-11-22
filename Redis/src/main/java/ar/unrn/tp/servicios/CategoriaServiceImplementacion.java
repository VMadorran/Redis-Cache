package ar.unrn.tp.servicios;

import java.util.ArrayList;
import java.util.List;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.modelo.Categoria;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class CategoriaServiceImplementacion implements CategoriaService {

	private Categoria categoria;
	private EntityManagerFactory emf;

	@Override
	public void CategoriaService(EntityManagerFactory emf) {
		// TODO Auto-generated method stub
		this.emf = emf;
	}

	@Override
	public void crearCategoria(Long codCategoria, String marca) {
		// TODO Auto-generated method stub

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			Categoria categoria = new Categoria(marca, codCategoria);
			em.persist(categoria);

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	@Override
	public Categoria categoriaPorId(Long id) {
		// TODO Auto-generated method stub

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			categoria = em.find(Categoria.class, id);
		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
		return categoria;
	}

	@Override
	public List<Categoria> categorias() {
		// TODO Auto-generated method stub

		List<Categoria> categorias = new ArrayList<>();

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			TypedQuery<Categoria> categoriasQuery = em.createQuery("Select c from Categoria c", Categoria.class);
			categorias.addAll(categoriasQuery.getResultList());

		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
		return categorias;
	}

}
