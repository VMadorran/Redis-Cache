package ar.unrn.tp.jpa.servicios;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.function.Consumer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.Fecha;
import ar.unrn.tp.modelo.PromocionBancaria;
import ar.unrn.tp.modelo.PromocionMarca;
import ar.unrn.tp.modelo.ProveedorDeFecha;
import ar.unrn.tp.servicios.DescuentoServiceImplementacion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DescuentoServiceTest {

	private DescuentoService descService = new DescuentoServiceImplementacion();
	private Fecha fecha = new ProveedorDeFecha();
	private EntityManagerFactory emf;

	private LocalDateTime desde = fecha.now().plusDays(5);
	private LocalDateTime hasta = fecha.now().plusWeeks(2);

	@BeforeEach
	public void setUp() {
		emf = Persistence.createEntityManagerFactory("jpa-derby-embedded");
	}

	@Test
	public void crearDescuentoTest() {// Funcionan estos test

		descService.DescuentoService(emf);
		descService.crearDescuento("Acme", desde, hasta, 0.05f);

		inTransactionExecute((em) -> {

			PromocionMarca promo = em.getReference(PromocionMarca.class, 1L);
			assertEquals(promo.marca(), "Acme");
			assertEquals(promo.descuento(), 0.05f);

		});

	}

	@Test
	public void creardescuentoSobreTotalTest() {

		try {

			descService.DescuentoService(emf);
			descService.crearDescuento("Acme", desde, hasta, 0.08f);

			inTransactionExecute((em) -> {

				PromocionBancaria promo = em.getReference(PromocionBancaria.class, 1L);
				assertEquals(promo.marca(), "Acme");
				assertEquals(promo.descuento(), 0.08f);
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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
