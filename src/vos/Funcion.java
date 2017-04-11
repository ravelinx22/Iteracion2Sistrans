package vos;



import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Funcion {

	/**
	 * Id de la funcion
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Fecha en la que se realiza la funcion
	 */
	@JsonProperty(value="fecha")
	private Date fecha;
	
	/**
	 * Hora a la que se realiza al funcion
	 */
	@JsonProperty(value="hora_inicio")
	private int hora_inicio;

	/**
	 * Id de la reserva del sitio en donde se va a realizar la funcion
	 */
	@JsonProperty(value="id_reserva")
	private int id_reserva;
	
	/**
	 * Cantidad de boletas que hay para la funcion.
	 */
	@JsonProperty(value="boletas_totales")
	private int boletas_totales;
	
	/**
	 * Id del espectaculo al que pertenece la funcion
	 */
	@JsonProperty(value="id_espectaculo")
	private int id_espectaculo;

	/**
	 * Id del festival al que pertence la funcion
	 */
	@JsonProperty(value="id_festival")
	private int id_festival;
	
	/**
	 * Constructor de la clase funcion
	 * @param id - Id de la funcion
	 * @param fecha - Fecha en la que se realiza la funcion
	 * @param horaInicion - Hora en la que se realiza la funcion
	 * @param boletasDisponibles - Boletas disponibles actualmente para la funcion
	 * @param idReserva - Id de la reserva del sitio en donde se va a realizar la funcion
	 */
	public Funcion(@JsonProperty(value="id") int id, @JsonProperty(value="fecha") Date fecha, @JsonProperty(value="hora_inicio") int horaInicio, @JsonProperty(value="boletas_totales") int boletasTotales, @JsonProperty(value="id_reserva") int idReserva, @JsonProperty(value="id_espectaculo") int idEspectaculo, @JsonProperty(value="id_festival") int id_festival) {
		this.id = id;
		this.fecha = fecha;
		this.hora_inicio = horaInicio;
		this.boletas_totales = boletasTotales;
		this.id_reserva = idReserva;
		this.id_espectaculo = idEspectaculo;
		this.id_festival = id_festival;
	}

	/**
	 * Da el id del festival
	 * @return Id del festival
	 */
	public int getIdFestival() {
		return id_festival;
	}
	
	/**
	 * Cambia el id del festival
	 * @param id_festival Nuevo id del festival
	 */
	public void setIdFestival(int id_festival) {
		this.id_festival = id_festival;
	}
	
	/**
	 * Da el id de la funcion.
	 * @return El id de la funcion.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Modifica el id de la funcion
	 * @param id - Nuevo valor del id de la funcion.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Da la fecha de la funcion.
	 * @return La fecha de la funcion.
	 */
	public Date getFecha() {
		return fecha;
	}

	/**
	 * Modifica la fecha de la funcion.
	 * @param fecha Nueva fecha de la funcion.
	 */
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	/**
	 * Da la hora de inicio de la funcion.
	 * @return La hora de inicio de la funcion.
	 */
	public int getHoraInicio() {
		return hora_inicio;
	}

	/**
	 * Modifica la hora de inicio de la funcion.
	 * @param horaInicio Nueva hora de inicio de la funcion.
	 */
	public void setHoraInicio(int horaInicio) {
		this.hora_inicio = horaInicio;
	}
	
	/**
	 * Da la cantidad de boletas totales para la funcion
	 * @return Cantidad de boletas totales para la funcion
	 */
	public int getBoletasTotales() {
		return boletas_totales;
	}
	
	/**
	 * Modifica el numero de boletas totales para la funcion
	 * @param boletasTotales El nuevo valor de las boletas totales para la funcion.
	 */
	public void setBoletasTotales(int boletasTotales) {
		this.boletas_totales = boletasTotales;
	}

	/**
	 * Da el id de la reserva del sitio para la funcion.
	 * @returnel El id de la reserva del sitio para la funcion.
	 */
	public int getIdReserva() {
		return id_reserva;
	}

	/**
	 * Modifica el id de la reserva para el sitio donde se realiza la funcion.
	 * @param idReserva Nuevo id de la reserva para el sition donde se realiza la funcion.
	 */
	public void setIdReserva(int idReserva) {
		this.id_reserva = idReserva;
	}
	
	/**
	 * Da el id del espectaculo al que pertenece la funcion
	 * @return Id del espectaculo al que pertenece la funcion
	 */
	public int getIdEspectaculo() {
		return id_espectaculo;
	}

	/**
	 * Modifica el id del espectaculo al que pertence la funcion
	 * @param idEspectaculo Nuevo valor del id espectaculo
	 */
	public void setIdEspectaculo(int idEspectaculo) {
		this.id_espectaculo = idEspectaculo;
	}
}
