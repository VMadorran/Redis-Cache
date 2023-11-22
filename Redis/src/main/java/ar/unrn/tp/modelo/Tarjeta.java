package ar.unrn.tp.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Tarjeta {

	@Id
	private Long nroTarjeta;
	private String marca;
	private boolean activa;

	public Tarjeta(Long nroTarjeta, String marca) {

		this.nroTarjeta = nroTarjeta;
		this.activa = true;
		this.marca = marca;
	}

	public Tarjeta() {

	}

	public Long nroTarjeta() {

		return this.nroTarjeta;
	}

	public String marcaTarjeta() {

		return this.marca;
	}

	public boolean estaActiva() {
		return this.activa;
	}

	public boolean saldoSuficiente(double monto) {
		return true;
	}

}
