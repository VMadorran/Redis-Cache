package ar.unrn.tp.main;

import java.awt.EventQueue;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.servicios.CategoriaServiceImplementacion;
import ar.unrn.tp.servicios.ProductoServiceImplementacion;
import ar.unrn.tp.ui.ProductoUI;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ProductoUIMain {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-mysql");

					ProductoService producService = new ProductoServiceImplementacion();
					CategoriaService catService = new CategoriaServiceImplementacion();

					ProductoUI frame = new ProductoUI(emf, producService, catService);
					frame.setVisible(true);
					if (!frame.isDisplayable()) {
						emf.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
