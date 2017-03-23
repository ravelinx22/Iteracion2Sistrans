package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Boleta {

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="id_funacion")
	private int id_funcion;
	
	@JsonProperty(value="id_usuario")
	private int id_usuario;
	
	@JsonProperty(value="id_localidad")
	private int id_localidad;
	
	@JsonProperty(value="id_silla")
	private int id_silla;

	
	
	public Boleta(@JsonProperty(value="id") int id,@JsonProperty(value="id_funcion") int id_funcion,@JsonProperty(value="id_usuario") int id_usuario,@JsonProperty(value="id_localidad") int id_localidad,@JsonProperty(value="id_silla") int id_silla) {
		super();
		this.id = id;
		this.id_funcion = id_funcion;
		this.id_usuario = id_usuario;
		this.id_localidad = id_localidad;
		this.id_silla = id_silla;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_funcion() {
		return id_funcion;
	}

	public void setId_funcion(int id_fundacion) {
		this.id_funcion = id_fundacion;
	}

	public int getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	public int getId_localidad() {
		return id_localidad;
	}

	public void setId_localidad(int id_localidad) {
		this.id_localidad = id_localidad;
	}

	public int getId_silla() {
		return id_silla;
	}

	public void setId_silla(int id_silla) {
		this.id_silla = id_silla;
	}
}
