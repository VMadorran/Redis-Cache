package ar.unrn.tp.servicios;

import java.util.ArrayList;
import java.util.List;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.exception.DatoVacioException;
import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Dni;
import ar.unrn.tp.modelo.Tarjeta;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

public class ClienteServiceImplementacion implements ClienteService {

	private Cliente cliente;
	private EntityManagerFactory emf;
	List<Tarjeta> tarjetas = new ArrayList<>();

	@Override
	public void ClienteService(EntityManagerFactory emf) {
		// TODO Auto-generated method stub
		this.emf = emf;
	}

	@Override
	public void crearCliente(String nombre, String apellido, String dni, String email)
			throws DatoVacioException, Exception {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			Long numero = Long.parseLong(dni);
			Cliente cliente = new Cliente(nombre, apellido, new Dni(numero), email);

			em.persist(cliente);

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
	public void modificarCliente(Long idCliente, String nombre, String apellido) throws DatoVacioException, Exception {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Cliente cliente = em.getReference(Cliente.class, idCliente);

			cliente.modificarCliente(nombre, apellido);

			em.persist(cliente);
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
	public void agregarTarjeta(Long idCliente, String nro, String marca) {

		Long nroTarjeta = Long.valueOf(nro);

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();

			Cliente cliente = em.getReference(Cliente.class, idCliente);

			TypedQuery<Cliente> clientesQuery = em.createQuery("select c from Cliente c", Cliente.class);

			Tarjeta tarjeta = new Tarjeta(nroTarjeta, marca);
			cliente.agregarMetodoDePago(tarjeta);
			em.persist(cliente);

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
	public List<Tarjeta> listarTarjetas(Long idCliente) {

		EntityManager em = this.emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Cliente cliente = em.getReference(Cliente.class, idCliente);
			tarjetas.addAll(cliente.mediosDePago());

			tx.commit();

		} catch (Exception e) {
			tx.rollback();
			throw e;
		} finally {
			if (em != null && em.isOpen())
				em.close();
		}

		return tarjetas;
	}

}
