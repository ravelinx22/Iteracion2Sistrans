package vos;



import org.codehaus.jackson.annotate.JsonProperty;

public class Espectaculo {

	/**
	 * Id del espectaculo
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Nombre del espectaculo
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Duracion del espectaculo en minutos
	 */
	@JsonProperty(value="duracion")
	private int duracion;
	
	/**
	 * Idioma del espectaculo
	 */
	@JsonProperty(value="idioma")
	private String idioma;
	
	/**
	 * Descripcion del espectaculo
	 */
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	/**
	 * Publico objetivo del espectaculo
	 */
	@JsonProperty(value="publico_objetivo")
	private String publico_objetivo;
	
	/**
	 * Genero del espectaculo
	 */
	@JsonProperty(value="genero")
	private String genero;
	
	/**
	 * Requerimientos del espectaculo
	 */
	@JsonProperty(value="requerimientos")
	private Integer[] requerimientos;
	
	/**
	 * Constructor encargado de crear un nuevo espectaculo
	 * @param id - Id del espectaculo
	 * @param nombre - Nombre del espectaculo
	 * @param duracion - Duracion del espectaculo
	 * @param idioma - Idioma del espectaculo.
	 * @param costo - Costo del espectaculo
	 * @param descripcion - Descripcion del espectaculo
	 * @param publicoObjetivo - Publico objetivo del espectaculo
	 * @param genero - Genero del espectaculo
	 * @param requerimientos - Requerimientos del espectaculo
	 */
	public Espectaculo(@JsonProperty(value="id") int id, @JsonProperty(value="nombre") String nombre, @JsonProperty(value="duracion") int duracion, @JsonProperty(value="idioma") String idioma, @JsonProperty(value="descripcion") String descripcion, @JsonProperty(value="publico_objetivo") String publicoObjetivo, @JsonProperty(value="genero") String genero, @JsonProperty(value="requerimientos") Integer[] requerimientos) {
		this.id = id;
		this.nombre = nombre;
		this.duracion = duracion;
		this.idioma = idioma;
		this.descripcion = descripcion;
		this.publico_objetivo = publicoObjetivo;
		this.genero = genero;
		this.requerimientos = requerimientos;
	}

	/**
	 * Da el id del espectaculo
	 * @return Id del espectaculo
	 */
	public int getId() {
		return id;
	}

	/**
	 * Modifica el valor del espectaculo
	 * @param id Nuevo id del espectaculo
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Da el nombre del espectaculo
	 * @return Nombre del espectaculo
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre del espectaculo
	 * @param nombre Nuevo Nombre del espectaculo
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Da la duracion del espectaculo
	 * @return Duracion del espectaculo
	 */
	public int getDuracion() {
		return duracion;
	}

	/**
	 * Modifica la duracion del espectaculo
	 * @param duracion Nueva duracion del espectaculo
	 */
	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	/**
	 * Da el idioma del espectaculo
	 * @return Idioma del espectaculo
	 */
	public String getIdioma() {
		return idioma;
	}

	/**
	 * Modifica el idioma del espectaculo
	 * @param idioma Nuevo Idioma del espectaculo
	 */
	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	/**
	 * Da la descripcion del espectaculo
	 * @return Descripcion del espectaculo
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * Modifica la descripcion del espectaculo
	 * @param descripcion Nueva descripcion del espectaculo
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * Da el publico objetivo del espectaculo
	 * @return Publico objectivo del espectaculo
	 */
	public String getPublicoObjetivo() {
		return publico_objetivo;
	}

	/**
	 * Modifica el publico objetivo del espectaculo
	 * @param publicoObjetibo Nuevo publico objetivo del espectaculo
	 */
	public void setPublicoObjetivo(String publicoObjetivo) {
		this.publico_objetivo = publicoObjetivo;
	}

	/**
	 * Da el genero del espectaculo
	 * @return Genero del espectaculo
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * Modifica el genero del espectaculo
	 * @param genero Nuevo genero del espectaculo
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}	
	
	/**
	 * Da los requerimientos del espectaculo
	 * @return Requerimientos del espectaculo
	 */
	public Integer[] getRequerimientos() {
		return this.requerimientos;
	}
	
	/**
	 * Modifica los requerimientos del espectaculo.
	 * @param requerimientos - Nuevos requerimientos 
	 */
	public void setRequerimiento(Integer[] requerimientos) {
		this.requerimientos= requerimientos;
	}
}
