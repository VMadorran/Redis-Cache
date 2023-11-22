package ar.unrn.tp.jpa.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.exception.CategoriaInvalidaException;
import ar.unrn.tp.exception.DatoVacioException;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.servicios.ProductoServiceImplementacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class ProductoServiceTest {

	private ProductoService implementacion = new ProductoServiceImplementacion();
	private List<Producto> productos = new ArrayList<>();
	private EntityManagerFactory emf;

	@BeforeEach
	public void setUp() {
		emf = Persistence.createEntityManagerFactory("jpa-derby-embedded");
	}

	@Test
	public void crearProductoTest() {

		implementacion.ProductoService(emf);

		inTransactionExecute((em) -> {

			Categoria categoria = new Categoria("Indumentaria", 1L);
			em.persist(categoria);
		});

		try {
			implementacion.crearProducto(1L, "Zapatillas", 10000, 1L, "Acme");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		inTransactionExecute((em) -> {

			var producto = em.find(Producto.class, 1L);

			assertEquals("Zapatillas", producto.descripcion());
			assertEquals(1L, producto.codigoProducto());
			assertEquals("Acme", producto.marcaProducto());
		});
	}

	@Test
	public void modificarProductoTest() {

		implementacion.ProductoService(emf);

		inTransactionExecute((em) -> {
			try {
				Categoria categoria = new Categoria("Indumentaria", 1L);

				em.persist(new Producto("Zapatillas", 1L, categoria, 1000, "Comarca"));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		try {
			implementacion.modificarProducto(1L, "Camiseta", 1500, "Nope", 1L);
		} catch (DatoVacioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CategoriaInvalidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		inTransactionExecute((em) -> {

			var producto = em.find(Producto.class, 1L);

			assertEquals("Camiseta", producto.descripcion());
			assertEquals(1L, producto.codigoProducto());
			assertEquals("Nope", producto.marcaProducto());

		});

	}

	@Test
	public void listarProductosTest() {

		implementacion.ProductoService(emf);

		inTransactionExecute((em) -> {
			try {
				Categoria categoria = new Categoria("Indumentaria", 1L);

				em.persist(new Producto("Zapatillas", 1L, categoria, 1000, "Comarca"));
				em.persist(new Producto("Remera", 2L, categoria, 1000, "Nope"));

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		productos = implementacion.listarProductos();

		assertEquals("Zapatillas", productos.get(0).descripcion());
		assertEquals(1L, productos.get(0).codigoProducto());
		assertEquals("Comarca", productos.get(0).marcaProducto());

		assertEquals("Remera", productos.get(1).descripcion());
		assertEquals(2L, productos.get(1).codigoProducto());
		assertEquals("Nope", productos.get(1).marcaProducto());

	}

	public void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		try {
			tx.begin();

			bloqueDeCodigo.accept(em);

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
	}

	@AfterEach
	public void tearDown() {
		emf.close();
	}

}
