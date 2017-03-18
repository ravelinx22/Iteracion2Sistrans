package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Compañia {

	/**
	 * Id de la compañia
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Nombre de la compañia
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Pais de la compañia
	 */
	@JsonProperty(value="pais")
	private String pais;
	
	/**
	 * Pagina web de la compañia
	 */
	@JsonProperty(value="pagina_web")
	private String paginaWeb;
	
	/**
	 * Nombre del representante de la compañia.
	 */
	@JsonProperty(value="nombre_representante")
	private String nombreRepresentante;

	/**
	 * Constructor de la clase compañia.
	 * @param id - Id de la compañia.
	 * @param nombre - Nombre de la compañia.
	 * @param pais - Pais de la compañia.
	 * @param paginaWeb - Pagina web de la compañia.
	 * @param nombreRepresentante - Nombre del representate de la compañia.
	 */
	public Compañia(@JsonProperty(value="id") int id,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="pais") String pais,@JsonProperty(value="pagina_web") String paginaWeb,@JsonProperty(value="nombre_representante") String nombreRepresentante) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.pais = pais;
		this.paginaWeb = paginaWeb;
		this.nombreRepresentante = nombreRepresentante;
	}

	/**
	 * Da el id de la compañia
	 * @return Id de la compañia
	 */
	public int getId() {
		return id;
	}

	/**
	 * Modifica el id de la compañia
	 * @param Nuevo id de la compañia
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Da el nombre de la compañia
	 * @return Nombre de la compañia
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre de la compañia
	 * @param Nombre de la compañia
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Da el ais de la compañia
	 * @return Pais de la compañia
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * Modifica el pais de la compañia
	 * @param Nuevo pais de la compañia
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * Da la pagina web de la compañia
	 * @return Pagina web de la compañia
	 */
	public String getPaginaWeb() {
		return paginaWeb;
	}

	/**
	 * Modifica la pagina web de la compañia
	 * @param Nueva pagina web de la compañia
	 */
	public void setPaginaWeb(String paginaWeb) {
		this.paginaWeb = paginaWeb;
	}

	/**
	 * Da el nombre del representante de la compañia.
	 * @return Nombre del representante de la compañia
	 */
	public String getNombreRepresentante() {
		return nombreRepresentante;
	}

	/**
	 * Modifica el nombre del representante de la compañia.
	 * @param nombreRepresentante Nuevo nombre del representante 
	 */
	public void setNombreRepresentante(String nombreRepresentante) {
		this.nombreRepresentante = nombreRepresentante;
	}
}
