package ar.unrn.tp.ui;

import java.awt.Button;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ar.unrn.tp.api.CategoriaService;
import ar.unrn.tp.api.ProductoService;
import ar.unrn.tp.exception.CategoriaInvalidaException;
import ar.unrn.tp.exception.DatoVacioException;
import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.servicios.CategoriaServiceImplementacion;
import ar.unrn.tp.servicios.ProductoServiceImplementacion;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.OptimisticLockException;

public class ProductoUI extends JFrame {

	private JPanel contentPane;
	private JTextField nombreTextField;
	private JTextField precioTextField;
	private JTextField marcaTextField;
	private ProductoService producService = new ProductoServiceImplementacion();
	private EntityManagerFactory emf;
	private CategoriaService catService = new CategoriaServiceImplementacion();

	public ProductoUI(EntityManagerFactory emf, ProductoService producService, CategoriaService catService) {
		setFont(new Font("Arial", Font.PLAIN, 12));

		this.emf = emf;
		this.producService = producService;
		this.catService = catService;

		setTitle("Modificar Producto");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 352, 395);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JComboBox<String> categoriaComboBox = new JComboBox();
		categoriaComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
		categoriaComboBox.setBounds(141, 235, 148, 22);
		categoriaComboBox.setSelectedIndex(-1);
		contentPane.add(categoriaComboBox);

		catService.CategoriaService(emf);
		List<Categoria> categorias = catService.categorias();
		for (Categoria categoria : categorias) {
			categoriaComboBox.addItem(categoria.infoCategoria());
		}

		nombreTextField = new JTextField();
		nombreTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		nombreTextField.setBounds(164, 89, 86, 20);
		contentPane.add(nombreTextField);
		nombreTextField.setColumns(10);

		precioTextField = new JTextField();
		precioTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		precioTextField.setBounds(164, 133, 86, 20);
		contentPane.add(precioTextField);
		precioTextField.setColumns(10);

		marcaTextField = new JTextField();
		marcaTextField.setFont(new Font("Arial", Font.PLAIN, 12));
		marcaTextField.setBounds(164, 188, 86, 20);
		contentPane.add(marcaTextField);
		marcaTextField.setColumns(10);

		JLabel catedoriaIdLabel = new JLabel("Id de producto:" + 1);
		catedoriaIdLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		catedoriaIdLabel.setBounds(40, 34, 187, 14);
		contentPane.add(catedoriaIdLabel);

		JLabel lblNewLabel = new JLabel("Descripción:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setBounds(69, 92, 85, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Precio:");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_1.setBounds(69, 136, 62, 14);
		contentPane.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Marca:");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(69, 191, 62, 14);
		contentPane.add(lblNewLabel_2);

		Button modificarButton = new Button("Modificar");
		modificarButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		modificarButton.setFont(new Font("Arial", Font.PLAIN, 12));
		modificarButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				String descripcion = null;
				String marca = null;
				Long categoria = 0L;
				float precio = 0;
				producService.ProductoService(emf);
				try {
					descripcion = nombreTextField.getText();
					categoria = categorias.get(categoriaComboBox.getSelectedIndex()).idCategoria();
					marca = marcaTextField.getText();
					precio = Float.parseFloat(precioTextField.getText());
					producService.modificarProducto(1L, descripcion, precio, marca, categoria);
					JOptionPane.showMessageDialog(null, "Los datos del producto fueron cambiados");
				} catch (DatoVacioException | CategoriaInvalidaException | OptimisticLockException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Upps!", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}

			}
		});
		modificarButton.setBounds(245, 309, 70, 22);
		contentPane.add(modificarButton);

		JLabel lblNewLabel_3 = new JLabel("Categoria:");
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(69, 239, 62, 14);
		contentPane.add(lblNewLabel_3);
	}
}
