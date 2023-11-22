package ar.unrn.tp.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("BANCARIA")
public class PromocionBancaria extends Promocion {

	public PromocionBancaria(LocalDateTime inicio, LocalDateTime fin, String nombre, float descuento) throws Exception {
		super(inicio, fin, nombre, descuento);
		// TODO Auto-generated constructor stub
	}

	private PromocionBancaria() {

	}

	@Override
	public double aplicarDescuento(double monto) {
		// TODO Auto-generated method stub

		return monto * this.descuento;
	}

	@Override
	public boolean esBancaria() {
		// TODO Auto-generated method stub
		return true;
	}

}
