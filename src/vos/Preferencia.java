package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Preferencia {

	/**
	 * Id de la preferencia.
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Nombre de la preferencia
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * Constructor de la preferencia
	 * @param id Id de la preferencia
	 * @param nombre Nombre de la preferencia
	 */
	public Preferencia(@JsonProperty(value="id")int id,@JsonProperty(value="nombre") String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	/**
	 * Da el id de la preferencia
	 * @return Id de la preferencia
	 */
	public int getId() {
		return id;
	}

	/**
	 * Modifica el id de la preferencia
	 * @param id Nuevo id de la preferencia
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Da el nombre de la preferencia
	 * @return Nombre de la preferencia
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre de la preferencia
	 * @param nombre Nuevo nombre de la preferencia.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
