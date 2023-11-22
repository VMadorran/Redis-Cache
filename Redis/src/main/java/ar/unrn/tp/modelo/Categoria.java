package ar.unrn.tp.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombre;
	private Long codigoCategoria;

	public Categoria(String nombre, Long codigoCategoria) {
		this.nombre = nombre;
		this.codigoCategoria = codigoCategoria;
	}

	public Categoria() {

	}

	public Long idCategoria() {
		return this.id;
	}

	public String infoCategoria() {
		return this.nombre + this.codigoCategoria;
	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	private String getNombre() {
		return nombre;
	}

	private void setNombre(String nombre) {
		this.nombre = nombre;
	}

	private Long getCodigoCategoria() {
		return codigoCategoria;
	}

	private void setCodigoCategoria(Long codigoCategoria) {
		this.codigoCategoria = codigoCategoria;
	}

}
