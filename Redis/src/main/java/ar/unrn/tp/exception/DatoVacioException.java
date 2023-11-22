package ar.unrn.tp.exception;

public class DatoVacioException extends Exception {
	// AGREGAR OTROS ATRIBUTOS EN CASO NECESARIO
	public DatoVacioException() {
	}

	public DatoVacioException(String message) {
		super(message);
	}
}