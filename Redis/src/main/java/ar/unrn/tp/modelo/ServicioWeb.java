package ar.unrn.tp.modelo;

import ar.unrn.tp.exception.TarjetaInvalidaException;

public interface ServicioWeb {

	public boolean fondosSuficientes(Long nroTarjeta) throws TarjetaInvalidaException;
}
