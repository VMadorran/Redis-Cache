package ar.unrn.tp.main;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.modelo.ProveedorDeFecha;
import ar.unrn.tp.servicios.CategoriaServiceImplementacion;
import ar.unrn.tp.servicios.ClienteServiceImplementacion;
import ar.unrn.tp.servicios.DescuentoServiceImplementacion;
import ar.unrn.tp.servicios.ProductoServiceImplementacion;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class MainSistemaService {

	public static void main(String[] args) {
//
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-mysql");
		// TODO Auto-generated method stub

		CategoriaService catService = new CategoriaServiceImplementacion();
		catService.CategoriaService(emf);
		catService.crearCategoria(1L, "Indumentaria");

		ProductoService productService = new ProductoServiceImplementacion();
		productService.ProductoService(emf);

		try {
			productService.crearProducto(1L, "Remera corta", 1000, 1L, "Nope");
			productService.crearProducto(3L, "Medias", 500, 1L, "Acme");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DescuentoService descService = new DescuentoServiceImplementacion();
		descService.DescuentoService(emf);

		var fecha = new ProveedorDeFecha();

		descService.crearDescuento("Acme", fecha.now().minusMonths(1), fecha.now().plusMonths(1), 0.05f);
		descService.crearDescuentoSobreTotal("Memecard", fecha.now().minusWeeks(1), fecha.now().plusMonths(2), 0.08f);

		ClienteService clientService = new ClienteServiceImplementacion();
		clientService.ClienteService(emf);

		try {
			clientService.crearCliente("Jose", "Perez", "12345678", "jose@acdc.com");
			clientService.agregarTarjeta(1L, "123123", "Visa");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		emf.close();

	}

}
