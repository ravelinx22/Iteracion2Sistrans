package vos;

import java.util.Date;

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
	private int horaInicio;
	
	/**
	 * Boletas disponibles para la funcion
	 */
	@JsonProperty(value="boletas_disponible")
	private int boletasDisponibles;

	/**
	 * Id de la reserva del sitio en donde se va a realizar la funcion
	 */
	@JsonProperty(value="id_reserva")
	private int idReserva;
	
	/**
	 * Cantidad de boletas que hay para la funcion.
	 */
	@JsonProperty(value="boletas_totales")
	private int boletasTotales;
	
	/**
	 * Id del espectaculo al que pertenece la funcion
	 */
	@JsonProperty(value="id_espectaculo")
	private int idEspectaculo;

	
	/**
	 * Constructor de la clase funcion
	 * @param id - Id de la funcion
	 * @param fecha - Fecha en la que se realiza la funcion
	 * @param horaInicion - Hora en la que se realiza la funcion
	 * @param boletasDisponibles - Boletas disponibles actualmente para la funcion
	 * @param idReserva - Id de la reserva del sitio en donde se va a realizar la funcion
	 */
	public Funcion(@JsonProperty(value="id") int id, @JsonProperty(value="fecha") Date fecha, @JsonProperty(value="hora_inicio") int horaInicio, @JsonProperty(value="boletas_disponibles") int boletasDisponibles,@JsonProperty(value="boletas_totales") int boletasTotales, @JsonProperty(value="id_reserva") int idReserva, @JsonProperty(value="id_espectaculo") int idEspectaculo) {
		this.id = id;
		this.fecha = fecha;
		this.horaInicio = horaInicio;
		this.boletasDisponibles = boletasDisponibles;
		this.boletasTotales = boletasTotales;
		this.idReserva = idReserva;
		this.idEspectaculo = idEspectaculo;
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
		return horaInicio;
	}

	/**
	 * Modifica la hora de inicio de la funcion.
	 * @param horaInicio Nueva hora de inicio de la funcion.
	 */
	public void setHoraInicio(int horaInicio) {
		this.horaInicio = horaInicio;
	}

	/**
	 * Da la cantidad de boletas disponibles para la funcion.
	 * @return La cantidad de boletas disponibles la funcion.
	 */
	public int getBoletasDisponibles() {
		return boletasDisponibles;
	}

	/**
	 * Modifica la cantidad de boletas disponibles para la funcion.
	 * @param boletasDisponibles Nueva cantidad de boletas disponibles.
	 */
	public void setBoletasDisponibles(int boletasDisponibles) {
		this.boletasDisponibles = boletasDisponibles;
	}
	
	/**
	 * Da la cantidad de boletas totales para la funcion
	 * @return Cantidad de boletas totales para la funcion
	 */
	public int getBoletasTotales() {
		return boletasTotales;
	}
	
	/**
	 * Modifica el numero de boletas totales para la funcion
	 * @param boletasTotales El nuevo valor de las boletas totales para la funcion.
	 */
	public void setBoletasTotales(int boletasTotales) {
		this.boletasTotales = boletasTotales;
	}

	/**
	 * Da el id de la reserva del sitio para la funcion.
	 * @returnel El id de la reserva del sitio para la funcion.
	 */
	public int getIdReserva() {
		return idReserva;
	}

	/**
	 * Modifica el id de la reserva para el sitio donde se realiza la funcion.
	 * @param idReserva Nuevo id de la reserva para el sition donde se realiza la funcion.
	 */
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}
	
	/**
	 * Da el id del espectaculo al que pertenece la funcion
	 * @return Id del espectaculo al que pertenece la funcion
	 */
	public int getIdEspectaculo() {
		return idEspectaculo;
	}

	/**
	 * Modifica el id del espectaculo al que pertence la funcion
	 * @param idEspectaculo Nuevo valor del id espectaculo
	 */
	public void setIdEspectaculo(int idEspectaculo) {
		this.idEspectaculo = idEspectaculo;
	}
}
