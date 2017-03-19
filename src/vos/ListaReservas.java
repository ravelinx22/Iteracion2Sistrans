package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaReservas {

	/**
	 * Lista de reservas de un sitio.
	 */
	@JsonProperty(value="reservas")
	private List<Reserva> reservas;

	/**
	 * COnstruye una nueva lista de reservas para un sitio
	 * @param reservas Lista de reservas de un sitio
	 */
	public ListaReservas(List<Reserva> reservas) {
		super();
		this.reservas = reservas;
	}

	/**
	 * Da las reservas de un sitio.
	 * @return Lista de reservas de un sitio
	 */
	public List<Reserva> getReservas() {
		return reservas;
	}

	/**
	 * Modifica las reservas de un sitio.
	 * @param reservas Nueva lista de reservas de un sitio.
	 */
	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}
}
