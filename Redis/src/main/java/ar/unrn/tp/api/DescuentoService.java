package ar.unrn.tp.api;

import java.time.LocalDateTime;
import java.util.List;

import ar.unrn.tp.modelo.Promocion;
import jakarta.persistence.EntityManagerFactory;

public interface DescuentoService {

	// validar que las fechas no se superpongan
	// Promocion bancaria

	void DescuentoService(EntityManagerFactory emf);

	void crearDescuentoSobreTotal(String marcaTarjeta, LocalDateTime fechaDesde, LocalDateTime fechaHasta,
			float porcentaje);

	// validar que las fechas no se superpongan
	// Promocion de marca
	void crearDescuento(String marcaProducto, LocalDateTime fechaDesde, LocalDateTime fechaHasta, float porcentaje);

	public List<Promocion> listarDescuentos();

}
