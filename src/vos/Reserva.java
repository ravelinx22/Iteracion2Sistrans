package vos;


import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Reserva {

	/**
	 * Id de la reserva
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Fecha de la reserva
	 */
	@JsonProperty(value="fecha")
	private Date fecha;
	
	/**
	 * Hora de la reserva
	 */
	@JsonProperty(value="hora_reserva")
	private int hora_reserva;
	
	/**
	 * Id del sitio al que se le realiza la reserva
	 */
	@JsonProperty(value="id_sitio")
	private int id_sitio;
	
	/**
	 * Constructor de la clase reserva.
	 * @param id - Id de la reserva
	 * @param fecha - Fecha de la reserva.
	 * @param horaReserva - Hora de la reserva-
	 * @param idSitio - Id del sitio al que se le realiza la reserva.
	 */
	public Reserva(@JsonProperty(value="id") int id, @JsonProperty(value="fecha") Date fecha, @JsonProperty(value="hora_reserva") int horaReserva, @JsonProperty(value="id_sitio") int idSitio) {
		this.id = id;
		this.fecha = fecha;
		this.hora_reserva = horaReserva;
		this.id_sitio = idSitio;
	}

	/**
	 * Da el id de la reserva.
	 * @return Id de la reserva
	 */
	public int getId() {
		return id;
	}

	/**
	 * Modifica el id de la reserva.
	 * @param id - Nuevo valor de la reserva.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Da la fecha de la reserva.
	 * @return Fecha de la reserva
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Modifica la fecha de la reseva.
	 * @param fecha - Nuevo valor de la reserva.
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Da la hora de la reserva.
	 * @return Hora de la reserva.
	 */
	public int getHoraReserva() {
		return hora_reserva;
	}

	/**
	 * Modifica la hora de la reserva.
	 * @param horaReserva - Nuevo valor de la hora de la reserva.
	 */
	public void setHoraReserva(int horaReserva) {
		this.hora_reserva = horaReserva;
	}

	/**
	 * Da el id del sitio donde se realiza la reserva.
	 * @return Id del sitio donde se realiza la reserva.
	 */
	public int getIdSitio() {
		return id_sitio;
	}

	/**
	 * Modifica id del sitio donde se realiza la reserva.
	 * @param idSitio - Nuevo valor del id del sitio donde se realiza la reseva.
	 */
	public void setIdSitio(int idSitio) {
		this.id_sitio = idSitio;
	}
}
