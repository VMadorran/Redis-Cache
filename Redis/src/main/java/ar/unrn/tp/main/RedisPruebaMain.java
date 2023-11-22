package ar.unrn.tp.main;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ar.unrn.tp.LocalDateTypeAdapter;
import ar.unrn.tp.modelo.Venta;
import ar.unrn.tp.servicios.VentaServiceImplementacion;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import redis.clients.jedis.Jedis;

public class RedisPruebaMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		JedisPool pool = new JedisPool("localhost", 6379);
//
//		try (Jedis jedis = pool.getResource()) {
//			// Store & Retrieve a simple string
//			jedis.set("foo", "bar");
//			System.out.println(jedis.get("foo")); // prints bar
//
//			// Store & Retrieve a HashMap
//			Map<String, String> hash = new HashMap<>();
//			;
//			hash.put("name", "John");
//			hash.put("surname", "Smith");
//			hash.put("company", "Redis");
//			hash.put("age", "29");
//			jedis.hset("Persona", hash);
//			System.out.println(jedis.hgetAll("Persona"));

//		}

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
//		HostAndPort config = new HostAndPort(Protocol.DEFAULT_HOST, 6379);
//		PooledConnectionProvider provider = new PooledConnectionProvider(config);
//		UnifiedJedis client = new UnifiedJedis(provider);
		Jedis jedis = new Jedis("localhost", 6379);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-mysql");

		var seVentas = new VentaServiceImplementacion();
		seVentas.VentaService(emf);

		List<Venta> ventas = seVentas.ventas();
		GsonBuilder gsonBuilder = new GsonBuilder();

		gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTypeAdapter()).create();

		Gson gson = gsonBuilder.setPrettyPrinting().create();
		String json = gson.toJson(ventas);
		jedis.set("idCliente:1", json);

		System.out.println("Esto esta en Redis:");

		String result = null;

		result = jedis.get("idCliente:1");
		jedis.close();
		System.out.println(result);
		String jsonVentas = gson.toJson(result);

		List<Venta> ventasJson = null;
		try {
			ventasJson = mapper.reader().forType(new TypeReference<List<Venta>>() {
			}).readValue(json);

		} catch (

		JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Primer elemento de la coleccion");
		System.out.println(ventasJson.get(0).toString());

	}

}
