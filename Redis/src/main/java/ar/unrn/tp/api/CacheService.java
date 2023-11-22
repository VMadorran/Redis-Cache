package ar.unrn.tp.api;

import java.util.List;
import java.util.Optional;

import ar.unrn.tp.modelo.Venta;

public interface CacheService {
	public String comprasEnCache(String key);

	public void insertarCompras(List<Venta> ventasCliente, String key);

	public Optional<List<Venta>> jsonAVentas(String ventasJson);

}
