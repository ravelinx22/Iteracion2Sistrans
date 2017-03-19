package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {

	/**
	 * Id del usuario
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Nombre del usuario
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Identificacion del usuario
	 */
	@JsonProperty(value="identificacion")
	private int identificacion;
	
	/**
	 * Correo del usuario
	 */
	@JsonProperty(value="correo")
	private String correo;
	
	/**
	 * Rol del usuario
	 */
	@JsonProperty(value="rol")
	private String rol;
	
	/**
	 * Id de la preferencia del usuario.
	 */
	@JsonProperty(value="id_preferencia")
	private int idPreferencia;

	/**
	 * Constructor de la clase usuario.
	 * @param id - Id del usuario
	 * @param nombre - Nombre del usuario
	 * @param identificacion - Identificacion del usuario
	 * @param correo - Correo del usuario
	 * @param rol - Rol del usuario
	 * @param idPreferencia - Id de la preferencia del usuario.
	 */
	public Usuario(@JsonProperty(value="id") int id,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="identificacion") int identificacion,@JsonProperty(value="correo") String correo,@JsonProperty(value="rol") String rol, @JsonProperty(value="id_preferencia") int idPreferencia ) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.identificacion = identificacion;
		this.correo = correo;
		this.rol = rol;
		this.idPreferencia = idPreferencia;
	}

	/**
	 * Da el id del usuario
	 * @return Id del usuario
	 */
	public int getId() {
		return id;
	}

	/**
	 * Modifica el id del usuario
	 * @param id - Nuevo id del usuario
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Da el nombre del usuario
	 * @return Nombre del usuario
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre del usuario
	 * @param nombre Nuevo nombre del usuario
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Da la identificacion del usuario.
	 * @return Identificacion del usuario
	 */
	public int getIdentificacion() {
		return identificacion;
	}

	/**
	 * Modifica la identificacion del usuario
	 * @param identificacion Nueva identificacion del usuario
	 */
	public void setIdentificacion(int identificacion) {
		this.identificacion = identificacion;
	}

	/**
	 * Da el correo del usuario
	 * @return Correo del usuario
	 */
	public String getCorreo() {
		return correo;
	}

	/**
	 * Modifica el correo del usuario
	 * @param correo Nuevo correo del usuario
	 */
	public void setCorreo(String correo) {
		this.correo = correo;
	}

	/**
	 * Da el rol del usuario
	 * @return Da el rol del usuario
	 */
	public String getRol() {
		return rol;
	}

	/**
	 * Modifica el rol del usuario
	 * @param rol Nuevo rol del usuario
	 */
	public void setRol(String rol) {
		this.rol = rol;
	}

	/**
	 * Da el id de la preferencia del usuario.
	 * @return Id de la prefenrencia del usuario
	 */
	public int getIdPreferencia() {
		return idPreferencia;
	}

	/**
	 * Modifica el id de la preferencia del usuario
	 * @param idPreferencia Nuevo id de la preferencia del usuario.
	 */
	public void setIdPreferencia(int idPreferencia) {
		this.idPreferencia = idPreferencia;
	}
}
