package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Silla {

	/**
	 * Id de la silla
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Numero de la silla
	 */
	@JsonProperty(value="numero_silla")
	private int numeroSilla;
	
	/**
	 * Numero de la fila donde esta la silla
	 */
	@JsonProperty(value="numero_fila")
	private int numeroFila;
	
	/**
	 * Estado de la silla
	 */
	@JsonProperty(value="ocupado")
	private boolean ocupado;
	
	/**
	 * Id de la localidad a la que pertenece la silla
	 */
	@JsonProperty(value="id_localidad")
	private int idLocalidad;

	/**
	 * Constructor de la silla.
	 * @param id - Id de la silla
	 * @param numeroSilla - Numero de la silla
	 * @param numeroFila - Numero de la fila de la silla
	 * @param ocupado - Estado de la silla
	 * @param idLocalidad - Id de la localidad donde esta la silla,
	 */
	public Silla(@JsonProperty(value="id") int id,@JsonProperty(value="numero_silla") int numeroSilla,@JsonProperty(value="numero_fila") int numeroFila,@JsonProperty(value="ocupado") boolean ocupado,@JsonProperty(value="id_localidad") int idLocalidad) {
		super();
		this.id = id;
		this.numeroSilla = numeroSilla;
		this.numeroFila = numeroFila;
		this.ocupado = ocupado;
		this.idLocalidad = idLocalidad;
	}

	/**
	 * Da el id de la silla.
	 * @return El id de la silla
	 */
	public int getId() {
		return id;
	}

	/**
	 * Modifica el id de las sillas
	 * @param id Nuevo id de las sillas.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Da el numero de la silla.
	 * @return Numero de la silla
	 */
	public int getNumeroSilla() {
		return numeroSilla;
	}

	/**
	 * Modifica el numero de la silla
	 * @param numeroSilla Nuevo valor del numero de la silla.
	 */
	public void setNumeroSilla(int numeroSilla) {
		this.numeroSilla = numeroSilla;
	}

	/**
	 * Da el numero de la fila donde esta la silla.
	 * @return Numero de la fila donde esta la silla.
	 */
	public int getNumeroFila() {
		return numeroFila;
	}

	/**
	 * Modifica el numero de la fila dondes esta la silla.
	 * @param numeroFila Nuevo valor del numero de la fila donde esta la silla.
	 */
	public void setNumeroFila(int numeroFila) {
		this.numeroFila = numeroFila;
	}

	/**
	 * Da el estado de la silla.
	 * @return Estado de la silla.
	 */
	public boolean estaOcupado() {
		return ocupado;
	}

	/**
	 * Modifica el estado de la silla.
	 * @param ocupado El nuevo estado de la silla.
	 */
	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}

	/**
	 * Da el id de la localidad donde esta la silla.
	 * @return Id de la localidad donde esta la silla.
	 */
	public int getIdLocalidad() {
		return idLocalidad;
	}

	/**
	 * Modifica el id de la localidad donde esta la silla.
	 * @param idLocalidad Nuevo id de la localidad donde esta la silla.
	 */
	public void setIdLocalidad(int idLocalidad) {
		this.idLocalidad = idLocalidad;
	}
}
