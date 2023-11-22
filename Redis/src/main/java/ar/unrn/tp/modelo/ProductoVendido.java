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
public class ProductoVendido {
	@Override
	public String toString() {
		return "ProductoVendido [id=" + id + ", codProducto=" + codProducto + ", precio=" + precio + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private Long codProducto;
	private double precio;

	public ProductoVendido(Long codProducto, double precio) {
		this.codProducto = codProducto;
		this.precio = precio;
	}

	public ProductoVendido() {

	}

	public Long codProducto() {
		return this.codProducto;
	}

	private Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	private Long getCodProducto() {
		return codProducto;
	}

	private void setCodProducto(Long codProducto) {
		this.codProducto = codProducto;
	}

	private double getPrecio() {
		return precio;
	}

	private void setPrecio(double precio) {
		this.precio = precio;
	}

}
