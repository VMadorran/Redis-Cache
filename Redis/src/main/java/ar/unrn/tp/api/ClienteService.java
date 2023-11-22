package ar.unrn.tp.api;

import java.util.List;

import ar.unrn.tp.exception.DatoVacioException;
import ar.unrn.tp.modelo.Tarjeta;
import jakarta.persistence.EntityManagerFactory;

public interface ClienteService {

	void ClienteService(EntityManagerFactory emf);

	void crearCliente(String nombre, String apellido, String dni, String email) throws DatoVacioException, Exception;

	void modificarCliente(Long idCliente, String nombre, String apellido) throws DatoVacioException, Exception;

	void agregarTarjeta(Long idCliente, String nro, String marca);

	List<Tarjeta> listarTarjetas(Long idCliente);

}
