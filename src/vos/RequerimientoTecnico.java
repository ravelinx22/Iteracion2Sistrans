package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RequerimientoTecnico {

	/**
	 * Id del requerimiento
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Nombre del requerimiento
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * Constructor del requerimiento
	 * @param id Id del requerimiento
	 * @param nombre Nombre del requerimiento
	 */
	public RequerimientoTecnico(@JsonProperty(value="id") int id,@JsonProperty(value="nombre") String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}

	/**
	 * Da el id del requerimiento
	 * @return Id del requerimiento
	 */
	public int getId() {
		return id;
	}

	/**
	 * Modifica el id del requerimiento
	 * @param id Nuevo id del requerimiento
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Da el nombre del requerimiento
	 * @return Nombre del requerimiento
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre del requerimiento.
	 * @param nombre Nuevo nombre del requerimiento.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}