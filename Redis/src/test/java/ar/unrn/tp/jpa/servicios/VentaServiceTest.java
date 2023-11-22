package ar.unrn.tp.jpa.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.exception.CarritoVacioException;
import ar.unrn.tp.exception.DatoVacioException;
import ar.unrn.tp.exception.TarjetaInvalidaException;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Dni;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.ProductoVendido;
import ar.unrn.tp.modelo.PromocionBancaria;
import ar.unrn.tp.modelo.PromocionMarca;
import ar.unrn.tp.modelo.ProveedorDeFecha;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.modelo.Venta;
import ar.unrn.tp.servicios.VentaServiceImplementacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class VentaServiceTest {

	private VentaService implementacion = new VentaServiceImplementacion();
	private EntityManagerFactory emf;

	@BeforeEach
	public void setUp() {
		emf = Persistence.createEntityManagerFactory("jpa-derby-embedded");
	}

	@Test
	public void calcularMontoTest() {

		implementacion.VentaService(emf);

		inTransactionExecute((em) -> {
			try {
				var categoria = new Categoria("Indumentaria", 1L);

				em.persist(new Producto("Remera corta", 1L, categoria, 1000, "Nope"));

				em.persist(new Producto("Medias", 2L, categoria, 500, "Acme"));

				var fecha = new ProveedorDeFecha();

				em.persist(new PromocionMarca(fecha.now().minusMonths(1), fecha.now().plusMonths(1), "Acme", 0.05f));
				em.persist(
						new PromocionBancaria(fecha.now().minusWeeks(1), fecha.now().plusMonths(2), "Memecard", 0.08f));

				var cliente = new Cliente("Jose", "Perez", new Dni(123123L), "jose@acdc.com");
				cliente.agregarMetodoDePago(new Tarjeta(123456L, "Visa"));
				em.persist(cliente);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		float monto;
		try {
			monto = implementacion.calcularMonto(List.of(1L, 2L), 123123L, 1L);
			assertEquals(1475.0f, monto);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void realizarVentaTest() {
		implementacion.VentaService(emf);

		inTransactionExecute((em) -> {
			try {
				var categoria = new Categoria("Indumentaria", 1L);

				em.persist(new Producto("Remera corta", 1L, categoria, 1000, "Nope"));

				em.persist(new Producto("Medias", 2L, categoria, 500, "Acme"));

				var fecha = new ProveedorDeFecha();

				em.persist(new PromocionMarca(fecha.now().minusMonths(1), fecha.now().plusMonths(1), "Acme", 0.05f));
				em.persist(
						new PromocionBancaria(fecha.now().minusWeeks(1), fecha.now().plusMonths(2), "Memecard", 0.08f));

				var cliente = new Cliente("Jose", "Perez", new Dni(123123L), "jose@acdc.com");
				cliente.agregarMetodoDePago(new Tarjeta(123456L, "Visa"));
				em.persist(cliente);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		try {
			implementacion.realizarVenta(1L, List.of(1L, 2L), 123456L);
		} catch (DatoVacioException | CarritoVacioException | TarjetaInvalidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		inTransactionExecute((em) -> {

			var venta = em.getReference(Venta.class, 1L);
			List<ProductoVendido> productos = venta.productos();
			DecimalFormat df = new DecimalFormat("#.00");
			String valor = df.format(venta.precioFinal());

			assertEquals("1475,00", valor);
			assertEquals(123123L, venta.clienteComprador());

			assertEquals(1L, productos.get(0).codProducto());
			assertEquals(2L, productos.get(1).codProducto());

		});

	}

	@Test
	public void ventasTest() {
		implementacion.VentaService(emf);

		inTransactionExecute((em) -> {
			try {
				var categoria = new Categoria("Indumentaria", 1L);

				em.persist(new Producto("Remera corta", 1L, categoria, 1000, "Nope"));

				em.persist(new Producto("Medias", 2L, categoria, 500, "Acme"));

				var fecha = new ProveedorDeFecha();

				em.persist(new PromocionMarca(fecha.now().minusMonths(1), fecha.now().plusMonths(1), "Acme", 0.05f));
				em.persist(
						new PromocionBancaria(fecha.now().minusWeeks(1), fecha.now().plusMonths(2), "Memecard", 0.08f));

				var cliente = new Cliente("Jose", "Perez", new Dni(12345678L), "jose@acdc.com");
				cliente.agregarMetodoDePago(new Tarjeta(123456L, "Visa"));
				em.persist(cliente);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		try {
			implementacion.realizarVenta(1L, List.of(1L, 2L), 123456L);
			implementacion.realizarVenta(1L, List.of(1L), 123456L);
		} catch (DatoVacioException | CarritoVacioException | TarjetaInvalidaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		inTransactionExecute((em) -> {

			var venta = em.getReference(Venta.class, 1L);
			var venta2 = em.getReference(Venta.class, 2L);

			List<ProductoVendido> productosVenta = venta.productos();
			DecimalFormat df = new DecimalFormat("#.00");
			String valor = df.format(venta.precioFinal());

			assertEquals("1475,00", valor);
			assertEquals(12345678L, venta.clienteComprador());

			assertEquals(1L, productosVenta.get(0).codProducto());
			assertEquals(2L, productosVenta.get(1).codProducto());

			productosVenta = venta2.productos();
			assertEquals(1000.00, venta2.precioFinal());
			assertEquals(12345678L, venta2.clienteComprador());
			assertEquals(1L, productosVenta.get(0).codProducto());

		});

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
