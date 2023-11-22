package ar.unrn.tp.modelo;

import java.util.ArrayList;
import java.util.List;

import ar.unrn.tp.exception.CarritoVacioException;
import ar.unrn.tp.exception.TarjetaInvalidaException;

public class Carrito {

	private ArrayList<Producto> productosAComprar = new ArrayList<Producto>();
	private Cliente cliente;
	private ArrayList<PromocionMarca> promocionesVigentes = new ArrayList<PromocionMarca>();
	private PromocionBancaria promocionBancaria;
	private ServicioWeb servicio;

	public Carrito(Cliente usuario, ServicioWeb servicio) {

		this.cliente = usuario;
		this.servicio = servicio;
	}

	public void actualizarPromociones(List<PromocionMarca> promociones, List<PromocionBancaria> promocionBancaria) {

		for (PromocionMarca promo : promociones) {
			if (promo.estaVigente())
				promocionesVigentes.add(promo);

		}
		this.actualizarPromoBancaria(promocionBancaria);
	}

	private void actualizarPromoBancaria(List<PromocionBancaria> promos) {
		for (PromocionBancaria promo : promos) {
			if (promo.estaVigente())
				this.promocionBancaria = promo;
		}
	}

	public void agregarProductos(List<Producto> productos) throws CarritoVacioException {

		if (productos.isEmpty())
			throw new CarritoVacioException("La lista de productos se encuentra vacia");

		this.productosAComprar.addAll(productos);
	}

	public double calcularMontoDeCompra(Long nroTarjeta) throws TarjetaInvalidaException, CarritoVacioException {

		if (!this.cliente.perteneceAlCliente(nroTarjeta))
			throw new TarjetaInvalidaException("La tarjeta no pertece al cliente comprador");
		if (this.estaVacio())
			throw new CarritoVacioException("El carrito no posee productos para comprar");

		double montoTotal = 0;

		for (Producto producto : this.productosAComprar) {

			double precioProducto = producto.precioProducto();
			for (Promocion promocion : promocionesVigentes) {

				if ((promocion.estaVigente()) && (promocion.seAplicaDescuento(producto.marcaProducto()))) {
					precioProducto = promocion.aplicarDescuento(precioProducto);
				}
			}

			montoTotal = montoTotal + precioProducto;
		}

		montoTotal = montoTotal - this.montoConPromocionBancaria(this.promocionBancaria, nroTarjeta, montoTotal);
		return montoTotal;
	}

	private double montoConPromocionBancaria(PromocionBancaria promocionBancaria, Long nroTarjeta, double montoCompra) {

		double monto = 0;

		if ((promocionBancaria.seAplicaDescuento(cliente.marcaTarjeta(nroTarjeta)))
				&& (promocionBancaria.estaVigente()))
			return monto = promocionBancaria.aplicarDescuento(montoCompra);
		else
			return 0;
	}

	public Venta realizarCompra(Long nroTarjeta) throws TarjetaInvalidaException, CarritoVacioException {

		double montoTotal = 0;

		if (!this.cliente.perteneceAlCliente(nroTarjeta))
			throw new TarjetaInvalidaException("La tarjeta no pertece al cliente comprador");
		if (this.estaVacio())
			throw new CarritoVacioException("El carrito no posee productos para comprar");
		if (!servicio.fondosSuficientes(nroTarjeta))
			throw new TarjetaInvalidaException();

		List<ProductoVendido> detallesDeCompra = new ArrayList<>();

		for (Producto producto : this.productosAComprar) {

			double precioProducto = producto.precioProducto();

			for (Promocion promocion : this.promocionesVigentes) {
				if ((promocion.estaVigente()) && (promocion.seAplicaDescuento(producto.marcaProducto()))) {
					precioProducto = promocion.aplicarDescuento(precioProducto);
				}
			}

			ProductoVendido productoVendido = new ProductoVendido(producto.codigoProducto(), precioProducto);
			System.out.println(productoVendido.toString());
			detallesDeCompra.add(productoVendido);
			montoTotal = montoTotal + precioProducto;

		}
		double bancaria = this.montoConPromocionBancaria(this.promocionBancaria, nroTarjeta, montoTotal);

		montoTotal = montoTotal - bancaria;

		Venta venta = new Venta(new ProveedorDeFecha().now(), detallesDeCompra, this.dniClienteCarrito(), montoTotal,
				bancaria);

		return venta;

	}

	public boolean perteneceAlUsuario(Long dniUsuario) throws TarjetaInvalidaException {

		if (!cliente.dniUsuario().equals(dniUsuario))
			throw new TarjetaInvalidaException("La tarjeta no pertenece al cliente");
		return true;
	}

	private Long dniClienteCarrito() {
		return this.cliente.dniUsuario();
	}

	public boolean estaVacio() {
		return this.productosAComprar.isEmpty();

	}

}
