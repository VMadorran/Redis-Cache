package ar.unrn.tp.ui;

import java.awt.Font;
import java.awt.Label;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.factories.DefaultComponentFactory;

import ar.unrn.tp.api.ClienteService;
import ar.unrn.tp.api.DescuentoService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.api.VentaService;
import ar.unrn.tp.exception.CarritoVacioException;
import ar.unrn.tp.exception.DatoVacioException;
import ar.unrn.tp.exception.TarjetaInvalidaException;
import ar.unrn.tp.modelo.Producto;
import ar.unrn.tp.modelo.Promocion;
import ar.unrn.tp.modelo.Tarjeta;
import ar.unrn.tp.modelo.Venta;
import ar.unrn.tp.servicios.ClienteServiceImplementacion;
import ar.unrn.tp.servicios.DescuentoServiceImplementacion;
import ar.unrn.tp.servicios.ProductoServiceImplementacion;
import ar.unrn.tp.servicios.VentaServiceImplementacion;
import jakarta.persistence.EntityManagerFactory;

public class VentaUI extends JFrame {

	private JPanel contentPane;
	private JButton consultarPrecioButton;
	private JButton comprarButton;
	private ClienteService clientService = new ClienteServiceImplementacion();
	private VentaService ventaService = new VentaServiceImplementacion();
	private ProductoService producService = new ProductoServiceImplementacion();
	private DescuentoService descService = new DescuentoServiceImplementacion();
	private EntityManagerFactory emf;
	private JTable table;
	private JTable productosTable;
	private JTable tarjetasTable;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;

	public VentaUI(EntityManagerFactory emf, ClienteService clientService, VentaService ventaService,
			ProductoService producService, DescuentoService descService) {

		this.clientService = clientService;
		this.ventaService = ventaService;
		this.producService = producService;
		this.descService = descService;
		this.emf = emf;

		setTitle("Venta o Consulta");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(100, 100, 641, 490);
		contentPane = new JPanel();

		setContentPane(contentPane);

		clientService.ClienteService(emf);

		producService.ProductoService(emf);
		descService.DescuentoService(emf);

		DefaultTableModel modeloProductos;

		String[] titulosProductos = { "Descripción", "Precio", "Id" };

		modeloProductos = new DefaultTableModel(new Object[][] {}, titulosProductos);

		producService.ProductoService(emf);
		List<Producto> productosDeBase = producService.listarProductos();

		clientService.ClienteService(emf);
		List<Tarjeta> tarjetasBase = clientService.listarTarjetas(1L);

		descService.DescuentoService(emf);
		List<Promocion> promociones = descService.listarDescuentos();

		for (Producto producto : productosDeBase) {
			modeloProductos
					.addRow(new Object[] { producto.descripcion(), producto.precioProducto(), producto.idProducto() });
		}

		DefaultTableModel modeloTarjetas;
		String[] titulosTajetas = { "Marca", "Digito" };
		modeloTarjetas = new DefaultTableModel(new Object[][] {}, titulosTajetas);

		for (Tarjeta tarjeta : tarjetasBase) {

			modeloTarjetas.addRow(new Object[] { tarjeta.marcaTarjeta(), tarjeta.nroTarjeta() });
		}

		consultarPrecioButton = new JButton("Costo");
		consultarPrecioButton.setBounds(352, 402, 89, 23);
		consultarPrecioButton.setFont(new Font("Arial", Font.PLAIN, 12));
		consultarPrecioButton.addActionListener(new ActionListener() {
			float monto;
			Long nroTarjeta = 0L;

			public void actionPerformed(ActionEvent e) {

				try {
					List<Long> productos = new ArrayList<>();

					int[] seleccionados = productosTable.getSelectedRows();
					for (int i : seleccionados) {
						productos.add(productosDeBase.get(i).idProducto());
					}

					int[] seleccionadas = tarjetasTable.getSelectedRows();
					for (int i : seleccionadas) {
						nroTarjeta = tarjetasBase.get(i).nroTarjeta();
					}

					ventaService.VentaService(emf);

					monto = ventaService.calcularMonto(productos, nroTarjeta, 1L);
					JOptionPane.showMessageDialog(null, "El precio de la compra es de: $" + monto);
				} catch (DatoVacioException | CarritoVacioException | TarjetaInvalidaException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Upps!", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}

			}

		});
		contentPane.setLayout(null);
		contentPane.add(consultarPrecioButton);

		comprarButton = new JButton("Comprar");
		comprarButton.setBounds(489, 402, 89, 23);
		comprarButton.setFont(new Font("Arial", Font.PLAIN, 12));
		comprarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Long nroTarjeta = 0L;

				try {

					List<Long> productos = new ArrayList<>();

					int[] seleccionados = productosTable.getSelectedRows();
					for (int i : seleccionados) {
						productos.add(productosDeBase.get(i).idProducto());
					}

					int[] seleccionadas = tarjetasTable.getSelectedRows();
					for (int i : seleccionadas) {
						nroTarjeta = tarjetasBase.get(i).nroTarjeta();

					}

					ventaService.VentaService(emf);

					ventaService.realizarVenta(1L, productos, nroTarjeta);
					JOptionPane.showMessageDialog(null, "Usted ha realizado una compra!");

				} catch (DatoVacioException | CarritoVacioException | TarjetaInvalidaException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Upps!", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}

			}
		});

		contentPane.add(comprarButton);

		String[] promo = { "Descripción" };

		DefaultListModel descuentosListModel = new DefaultListModel();

		for (Promocion promocion : promociones) {
			descuentosListModel.addElement(promocion.marca());

		}

		JLabel lblNewLabel = new JLabel("Descuentos\r\n Disponibles");
		lblNewLabel.setBounds(304, 198, 143, 38);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		contentPane.add(lblNewLabel);

		JLabel lblTarjetas = new JLabel("Tarjetas");
		lblTarjetas.setBounds(348, 40, 46, 14);
		lblTarjetas.setFont(new Font("Arial", Font.PLAIN, 12));
		contentPane.add(lblTarjetas);

		JLabel lblProductos = new JLabel("Productos");
		lblProductos.setBounds(113, 57, 71, 14);
		lblProductos.setFont(new Font("Arial", Font.PLAIN, 12));
		contentPane.add(lblProductos);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(21, 102, 261, 245);
		contentPane.add(scrollPane);

		productosTable = new JTable();
		scrollPane.setViewportView(productosTable);
		productosTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		productosTable.setModel(modeloProductos);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(301, 79, 146, 100);
		contentPane.add(scrollPane_1);

		tarjetasTable = new JTable();
		scrollPane_1.setViewportView(tarjetasTable);
		tarjetasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tarjetasTable.setModel(modeloTarjetas);

		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(305, 247, 136, 111);
		contentPane.add(scrollPane_2);
		JList descuentosList = new JList();
		scrollPane_2.setViewportView(descuentosList);
		descuentosList.setFont(new Font("Arial", Font.PLAIN, 12));

		descuentosList.setModel(descuentosListModel);

		JLabel lblNewJgoodiesTitle = DefaultComponentFactory.getInstance().createTitle("Últimas compras");
		lblNewJgoodiesTitle.setBounds(489, 86, 89, 14);
		contentPane.add(lblNewJgoodiesTitle);

		Optional<List<Venta>> ultimasTresVentas = ventaService.comprasCliente(1L);
		Label ultimaUnoLabel = new Label(ultimasTresVentas.get().get(0).detalles());
		ultimaUnoLabel.setBounds(489, 132, 108, 65);
		contentPane.add(ultimaUnoLabel);

		Label ultimaDosLabel = new Label(ultimasTresVentas.get().get(1).detalles());
		ultimaDosLabel.setBounds(489, 209, 108, 55);
		contentPane.add(ultimaDosLabel);

		Label ultimaTresLabel = new Label(ultimasTresVentas.get().get(2).detalles());
		ultimaTresLabel.setBounds(489, 292, 108, 55);
		contentPane.add(ultimaTresLabel);

		ScrollPane scrollPane_3 = new ScrollPane();
		scrollPane_3.setBounds(470, 119, 136, 245);
		contentPane.add(scrollPane_3);

	}
}