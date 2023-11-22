package ar.unrn.tp.servicios;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ar.unrn.tp.LocalDateTypeAdapter;
import ar.unrn.tp.api.CacheService;
import ar.unrn.tp.modelo.Venta;
import redis.clients.jedis.Jedis;

public class CacheRedis implements CacheService {
	Jedis client = new Jedis("localhost", 6379);

	public void insertarCompras(List<Venta> ventasCliente, String key) {

		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTypeAdapter()).create();

		Gson gson = gsonBuilder.setPrettyPrinting().create();
		String json = gson.toJson(ventasCliente);

		client.set(key, json);
		client.close();
	}

	public String comprasEnCache(String key) {

		String jsonConsulta = client.get(key);
		client.close();
		return jsonConsulta;
	}

	public Optional<List<Venta>> jsonAVentas(String ventasJson) {

		Optional<List<Venta>> ventas = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			ventas = mapper.reader().forType(new TypeReference<List<Venta>>() {
			}).readValue(ventasJson);

		} catch (

		JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
		return ventas;
	}
}
