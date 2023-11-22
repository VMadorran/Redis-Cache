package ar.unrn.tp.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Dni {
	@Column(name = "dni")
	private Long dni;

	public Dni(Long dni) throws Exception {
		if (this.datoNulo(dni))
			throw new Exception("dni no puede ser vacio");
		this.dni = dni;
	}

	public Dni() {

	}

	public Long dni() {
		return this.dni;
	}

	private boolean datoNulo(Long dato) {
		return dato.equals(0);
	}

}