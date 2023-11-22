package ar.unrn.tp.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ClaveVenta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private int numero;
	private int anio;

	public ClaveVenta(int numero, int anio) {
		this.numero = numero;
		this.anio = anio;
	}

	public String recuperarSiguiente() {

		int anioActual = new ProveedorDeFecha().now().getYear();

		if (anio != anioActual) {
			this.anio = anioActual;
			this.numero = 1;
		} else
			this.numero += 1;

		return this.numero + "-" + this.anio;
	}

	private int getNumero() {
		return numero;
	}

	private void setNumero(int numero) {
		this.numero = numero;
	}

	private int getAnio() {
		return anio;
	}

	private void setAnio(int anio) {
		this.anio = anio;
	}

	private ClaveVenta() {

	}

}