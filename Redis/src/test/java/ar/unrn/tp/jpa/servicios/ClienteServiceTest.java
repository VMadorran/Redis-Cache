package ar.unrn.tp.jpa.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Dni;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.servicios.ClienteServiceImplementacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class ClienteServiceTest {

	private ClienteServiceImplementacion implementacion = new ClienteServiceImplementacion();
	private EntityManagerFactory emf;

	@BeforeEach
	public void setUp() {
		emf = Persistence.createEntityManagerFactory("jpa-derby-embedded");
	}

	@Test
	public void crearCliente() {
		implementacion.ClienteService(emf);
		try {
			implementacion.crearCliente("Jose", "Perez", "123123", "angus@gmail.com");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		inTransactionExecute((em) -> {

			var cliente = em.find(Cliente.class, 1L);

			assertEquals(cliente.dniUsuario(), 123123L);
			assertEquals(cliente.nombreYApellido("Jose", "Perez"), true);
		});

	}

	@Test
	public void agregarTarjetaTest() {

		implementacion.ClienteService(emf);
		inTransactionExecute((em) -> {

			try {
				em.persist(new Cliente("Jose", "Perez", new Dni(123123L), "angus@gmail.com"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		implementacion.agregarTarjeta(1L, "234234", "MemeCard");

		inTransactionExecute((em) -> {

			var cliente = em.getReference(Cliente.class, 1L);

			assertEquals(true, cliente.perteneceAlCliente(234234L));
		});
	}

	@Test
	public void modificarCliente() {
		implementacion.ClienteService(emf);

		inTransactionExecute((em) -> {
			try {
				em.persist(new Cliente("Jose", "Perez", new Dni(123123L), "angus@gmail.com"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

		try {
			implementacion.modificarCliente(1L, "Pedro", "Juarez");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		inTransactionExecute((em) -> {
			var cliente = em.find(Cliente.class, 1L);

			assertEquals(cliente.nombreYApellido("Pedro", "Juarez"), true);
			assertEquals(cliente.dniUsuario(), 123123L);
		});

	}

	@Test
	public void listarTarjeta() {

		implementacion.ClienteService(emf);

		inTransactionExecute((em) -> {
			try {

				var cliente = new Cliente("Jose", "Perez", new Dni(123123L), "angus@gmail.com");
				cliente.agregarMetodoDePago(new Tarjeta(123456L, "MemeCard"));
				cliente.agregarMetodoDePago(new Tarjeta(234234L, "MemeCard"));

				em.persist(cliente);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		List<Tarjeta> tarjetas = implementacion.listarTarjetas(1L);

		assertEquals(123456L, tarjetas.get(0).nroTarjeta());
		assertEquals(234234L, tarjetas.get(1).nroTarjeta());
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
