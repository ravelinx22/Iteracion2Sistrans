package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {

	@JsonProperty(value="id")
	private int id;
	
	@JsonProperty(value="")
	private String nombre;
	
	@JsonProperty(value="")
	private int identificacion;
	
	@JsonProperty(value="")
	private String correo;
	
	@JsonProperty(value="")
	private String rol;
	
	@JsonProperty(value="")
	private int id_preferencias;
	
	
}
