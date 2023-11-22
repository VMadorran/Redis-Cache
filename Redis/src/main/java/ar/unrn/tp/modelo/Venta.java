package ar.unrn.tp.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Venta {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "fecha_y_hora")
	@JsonFormat(pattern = "d::MMM::uuuu HH::mm::ss")
	private LocalDateTime fechaYHora;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_venta")
	private List<ProductoVendido> productosVendidos = new ArrayList<>();
	private Long dniCliente;
	private double precioFinal;
	private double descuentoBancario;
	private String claveVenta;

	public Venta(LocalDateTime fechaYHora, List<ProductoVendido> productosVendidos, Long dniCliente, double precioFinal,
			double descuentoBancario) {

		this.fechaYHora = fechaYHora;
		this.productosVendidos = productosVendidos;
		this.dniCliente = dniCliente;
		this.precioFinal = precioFinal;
		this.descuentoBancario = descuentoBancario;
	}

	public Venta() {

	}

	public void agregarClaveVenta(String claveVenta) {
		this.claveVenta = claveVenta;

	}

	public Long clienteComprador() {
		return this.dniCliente;
	}

	public double precioFinal() {
		return this.precioFinal;
	}

	public List<ProductoVendido> productos() {
		return this.productosVendidos;
	}

	private Long getId() {
		return id;
	}

	@Override
	public String toString() {
		String productos = null;
		for (ProductoVendido producto : this.productosVendidos) {
			productos = productos + producto.toString() + "\n";

		}
		return "[id=" + id + ", fechaYHora=" + fechaYHora + ", productosVendidos=" +

				productos + "dniCliente=" + dniCliente + ", precioFinal=" + precioFinal + ", descuentoBancario="
				+ descuentoBancario + ", claveVenta=" + claveVenta + "]";
	}

	private void setId(Long id) {
		this.id = id;
	}

	private void setFechaYHora(LocalDateTime fechaYHora) {
		this.fechaYHora = fechaYHora;
	}

	private List<ProductoVendido> getProductosVendidos() {
		return productosVendidos;
	}

	private void setProductosVendidos(List<ProductoVendido> productosVendidos) {
		this.productosVendidos = productosVendidos;
	}

	private Long getDniCliente() {
		return dniCliente;
	}

	private void setDniCliente(Long dniCliente) {
		this.dniCliente = dniCliente;
	}

	private double getPrecioFinal() {
		return precioFinal;
	}

	private void setPrecioFinal(double precioFinal) {
		this.precioFinal = precioFinal;
	}

	private double getDescuentoBancario() {
		return descuentoBancario;
	}

	private void setDescuentoBancario(double descuentoBancario) {
		this.descuentoBancario = descuentoBancario;
	}

	private String getClaveVenta() {
		return claveVenta;
	}

	private void setClaveVenta(String claveVenta) {
		this.claveVenta = claveVenta;
	}

	public String detalles() {
		return "Fecha:" + this.fechaYHora + "/n" + "Monto" + this.precioFinal;
	}

}
