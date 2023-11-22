package ar.unrn.tp.modelo;

import java.time.LocalDateTime;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue("MARCA")
@Entity
public class PromocionMarca extends Promocion {

	public PromocionMarca(LocalDateTime inicio, LocalDateTime fin, String nombre, float descuento) throws Exception {
		super(inicio, fin, nombre, descuento);
		this.tipo = "MARCA";
		// TODO Auto-generated constructor stub
	}

	public PromocionMarca() {

	}

	@Override
	public double aplicarDescuento(double monto) {
		// TODO Auto-generated method stub
		return monto - (monto * this.descuento);
	}

	@Override
	public boolean esBancaria() {
		// TODO Auto-generated method stub
		return false;
	}

}
