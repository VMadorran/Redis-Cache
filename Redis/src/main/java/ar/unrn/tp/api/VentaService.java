package ar.unrn.tp.api;

import java.util.List;
import java.util.Optional;

import ar.unrn.tp.exception.CarritoVacioException;
import ar.unrn.tp.exception.DatoVacioException;
import ar.unrn.tp.exception.TarjetaInvalidaException;
import ar.unrn.tp.modelo.Venta;
import jakarta.persistence.EntityManagerFactory;

public interface VentaService {

	void VentaService(EntityManagerFactory emf);

	void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta)
			throws DatoVacioException, CarritoVacioException, TarjetaInvalidaException;

	float calcularMonto(List<Long> productos, Long idTarjeta, Long idCliente)
			throws DatoVacioException, TarjetaInvalidaException, CarritoVacioException;

	public List<Venta> ventas();

	public Optional<List<Venta>> comprasCliente(Long idCliente);

	void claveVenta(int anio);
}
