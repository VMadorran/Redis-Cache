package ar.unrn.tp.servicios;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.modelo.Promocion;
import ar.unrn.tp.modelo.PromocionBancaria;
import ar.unrn.tp.modelo.PromocionMarca;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class DescuentoServiceImplementacion implements DescuentoService {

	private EntityManagerFactory emf;

	@Override
	public void DescuentoService(EntityManagerFactory emf) {
		// TODO Auto-generated method stub

		this.emf = emf;

	}

	@Override
	public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDateTime fechaDesde, LocalDateTime fechaHasta,
			float porcentaje) {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Promocion promo;

			promo = new PromocionBancaria(fechaDesde, fechaHasta, marcaTarjeta, porcentaje);
			em.persist(promo);
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
//			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}

	}

	@Override

	public void crearDescuento(String marcaProducto, LocalDateTime fechaDesde, LocalDateTime fechaHasta,
			float porcentaje) {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Promocion promo;

			promo = new PromocionMarca(fechaDesde, fechaHasta, marcaProducto, porcentaje);
			em.persist(promo);
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
//			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}

	}

	public List<Promocion> listarDescuentos() {

		List<Promocion> descuentos = new ArrayList<>();

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			TypedQuery<Promocion> promocionesTypedQuery = em.createQuery("select p from Promocion p", Promocion.class);
			descuentos.addAll(promocionesTypedQuery.getResultList());
			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}
		return descuentos;

	}

}
