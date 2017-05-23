package app3;

import java.sql.Date;
import java.sql.Timestamp;

import org.codehaus.jackson.annotate.JsonProperty;

public class Funcion3 {
	@JsonProperty(value="nombre")
	private String nombre;
	@JsonProperty(value="duracion")
	private Integer duracion;
	@JsonProperty(value="intermedio")
	private Integer intermedio;
	@JsonProperty(value="idioma")
	private String idioma;
	@JsonProperty(value="traduccion")
	private Integer traduccion;
	@JsonProperty(value="synopsis")
	private String synopsis;
	@JsonProperty(value="costoRealizacion")
	private Double costoRealizacion;
	@JsonProperty(value="publicoObjetivo")
	private String publicoObjetivo;
	@JsonProperty(value="publicoEspectador")
	private Integer publicoEspectador;
	@JsonProperty(value="funcion")
	private Timestamp funcion;
	
	public Funcion3(
			@JsonProperty(value="nombre") String nombre, 
			@JsonProperty(value="duracion") Integer duracion, 
			@JsonProperty(value="intermedio") Integer intermedio, 
			@JsonProperty(value="idioma") String idioma, 
			@JsonProperty(value="traduccion") Integer traduccion,
			@JsonProperty(value="synopsis") String synopsis, 
			@JsonProperty(value="costoRealizacion") Double costoRealizacion, 
			@JsonProperty(value="publicoObjetivo") String publicoObjetivo, 
			@JsonProperty(value="publicoEspectador") Integer publicoEspectador, 
			@JsonProperty(value="funcion") Timestamp funcion) {
		super();
		this.nombre = nombre;
		this.duracion = duracion;
		this.intermedio = intermedio;
		this.idioma = idioma;
		this.traduccion = traduccion;
		this.synopsis = synopsis;
		this.costoRealizacion = costoRealizacion;
		this.publicoObjetivo = publicoObjetivo;
		this.publicoEspectador = publicoEspectador;
		this.funcion = funcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getDuracion() {
		return duracion;
	}

	public void setDuracion(Integer duracion) {
		this.duracion = duracion;
	}

	public Integer getIntermedio() {
		return intermedio;
	}

	public void setIntermedio(Integer intermedio) {
		this.intermedio = intermedio;
	}

	public String getIdioma() {
		return idioma;
	}

	public void setIdioma(String idioma) {
		this.idioma = idioma;
	}

	public Integer getTraduccion() {
		return traduccion;
	}

	public void setTraduccion(Integer traduccion) {
		this.traduccion = traduccion;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public Double getCostoRealizacion() {
		return costoRealizacion;
	}

	public void setCostoRealizacion(Double costoRealizacion) {
		this.costoRealizacion = costoRealizacion;
	}

	public String getPublicoObjetivo() {
		return publicoObjetivo;
	}

	public void setPublicoObjetivo(String publicoObjetivo) {
		this.publicoObjetivo = publicoObjetivo;
	}

	public Integer getPublicoEspectador() {
		return publicoEspectador;
	}

	public void setPublicoEspectador(Integer publicoEspectador) {
		this.publicoEspectador = publicoEspectador;
	}

	public Timestamp getFuncion() {
		return funcion;
	}

	public void setFuncion(Timestamp funcion) {
		this.funcion = funcion;
	}
}
