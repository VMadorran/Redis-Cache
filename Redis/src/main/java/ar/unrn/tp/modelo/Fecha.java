package ar.unrn.tp.modelo;

import java.time.LocalDateTime;

public interface Fecha {

	public LocalDateTime now(int dia, int mes, int anio, int hora, int minutos);

	public LocalDateTime now();
}

