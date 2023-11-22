package ar.unrn.tp.main;

import java.awt.EventQueue;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.servicios.ClienteServiceImplementacion;
import ar.unrn.tp.servicios.DescuentoServiceImplementacion;
import ar.unrn.tp.servicios.ProductoServiceImplementacion;
import ar.unrn.tp.servicios.VentaServiceImplementacion;
import ar.unrn.tp.ui.VentaUI;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class VentaUIMain {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa-mysql");

					ClienteService clientService = new ClienteServiceImplementacion();
					VentaService ventaService = new VentaServiceImplementacion();
					ProductoService producService = new ProductoServiceImplementacion();
					DescuentoService descService = new DescuentoServiceImplementacion();

					VentaUI frame = new VentaUI(emf, clientService, ventaService, producService, descService);
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
