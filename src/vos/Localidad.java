package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Localidad {

	/**
	 * Id de la localidad 
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Capacidad de la localidad
	 */
	@JsonProperty(value="capacidad")
	private int capacidad;
	
	/**
	 * Nombre de la localidad
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Id del sitio al que pertenece la localidad
	 */
	@JsonProperty(value="id_sitio")
	private int idSitio;

	/**
	 * Constructor de la clase localidad.
	 * @param id - Id de la localidad
	 * @param capacidad - Capacidad de la localidad.
	 * @param nombre - Nombre de la localidad
	 * @param id_sitio - Id del sitio al que pertenece la localidad.
	 */
	public Localidad(@JsonProperty(value="id") int id,@JsonProperty(value="capacidad") int capacidad,@JsonProperty(value="nombre") String nombre, @JsonProperty(value="id_sitio") int idSitio) {
		super();
		this.id = id;
		this.capacidad = capacidad;
		this.nombre = nombre;
		this.idSitio = idSitio;
	}

	/**
	 * Da el id de la localidad
	 * @return Id de la localidad
	 */
	public int getId() {
		return id;
	}

	/**
	 * Modifica el id de la localidad
	 * @param id Nuevo id de la localidad.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Da la capacidad de la localidad
	 * @return Capacidad de la localidad
	 */
	public int getCapacidad() {
		return capacidad;
	}

	/**
	 * Modifica la capacidad de la localidad
	 * @param capacidad Nueva capacidad de la localidad
	 */
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	/**
	 * Da el nombre de la localidad
	 * @return Nombre de la localidad
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre de la localidad
	 * @param nombre Nuevo nombre de la localidad
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Da el id del sitio al que pertenece la localidad.
	 * @return Da el id del sitio de la localidad
	 */
	public int getIdSitio() {
		return idSitio;
	}

	/**
	 * Modifica el id del sitio al que perteneces la localidad.
	 * @param idSitio Nuevo id del sitio al que pertenece la localidad.
	 */
	public void setIdSitio(int idSitio) {
		this.idSitio = idSitio;
	}
}
