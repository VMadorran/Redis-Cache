package ar.unrn.tp.modelo;

import java.util.ArrayList;
import java.util.List;

import ar.unrn.tp.exception.DatoVacioException;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String nombre;
	private String apellido;
	@Column(unique = true)
	private Dni dni;

	private String email;

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "id_cliente")
	private java.util.List<Tarjeta> mediosDePago = new ArrayList<>();

	public Cliente(String nombre, String apellido, Dni dni, String email) throws DatoVacioException, Exception {

		if (this.datoNulo(nombre))
			throw new DatoVacioException("El campo nombre no puede ser vacio");
		this.nombre = nombre;

		if (this.datoNulo(apellido))
			throw new DatoVacioException("El campo apellido no puede ser vacio");
		if (!this.checkEmail(email))
			throw new Exception("El campo email debe ser valido");
		this.apellido = apellido;

		this.dni = dni;

		this.email = email;
//		this.mediosDePago = null;
	}

	private boolean checkEmail(String email) {
		String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		return email.matches(regex);
	}

	public boolean perteneceAlCliente(Long nroTarjeta) {

		boolean existe = false;
		for (Tarjeta tarjeta : this.mediosDePago) {
			if (tarjeta.nroTarjeta().equals(nroTarjeta))
				return true;
		}
		return existe;

	}

	public String marcaTarjeta(Long nroTarjeta) {

		for (Tarjeta tarjeta : this.mediosDePago) {
			if (tarjeta.nroTarjeta().equals(nroTarjeta))
				return tarjeta.marcaTarjeta();
		}
		return null;
	}

	public boolean nombreYApellido(String nombre, String apellido) {
		if ((this.apellido.equals(apellido)) && (this.nombre.equals(nombre)))
			return true;
		return false;
	}

	public Long dniUsuario() {
		return this.dni.dni();
	}

	public void agregarMetodoDePago(Tarjeta tarjeta) {

		this.mediosDePago.add(tarjeta);

	}

	public void modificarCliente(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;

	}

	public Tarjeta tarjetaPorNro(Long nro) {
		for (Tarjeta tarjeta : this.mediosDePago) {
			if (tarjeta.nroTarjeta().equals(nro))
				return tarjeta;
		}
		return null;
	}

	public List<Tarjeta> mediosDePago() {
		return this.mediosDePago;
	}

	private boolean datoNulo(String dato) {
		return dato.equals("");
	}

	// Constructor vacio y getters y setters
	protected Cliente() {
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

	private String getApellido() {
		return apellido;
	}

	private void setApellido(String apellido) {
		this.apellido = apellido;
	}

	private Dni getDni() {
		return dni;
	}

	private void setDni(Dni dni) {
		this.dni = dni;
	}

	private String getEmail() {
		return email;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	private List<Tarjeta> getMediosDePago() {
		return mediosDePago;
	}

	private void setMediosDePago(ArrayList<Tarjeta> mediosDePago) {
		this.mediosDePago.addAll(mediosDePago);
	}

}
