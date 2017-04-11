package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Festival {

	/**
	 * Id del festival
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Fecha de inicio del festival
	 */
	@JsonProperty(value="fecha_inicio")
	private Date fecha_inicio;
	
	/**
	 * Fecha final del festival
	 */
	@JsonProperty(value="fecha_final")
	private Date fecha_final;

	/**
	 * Nombre del festival
	 */
	@JsonProperty(value="nombre")
	private String nombre;
	
	/**
	 * Crea un nuevo festival
	 * @param id Id del festival
	 * @param fecha_inicio Fecha de inicio del festival
	 * @param fecha_final Fecha final del festival
	 * @param nombre Nombre del festival
	 */
	public Festival(@JsonProperty(value="id") int id, @JsonProperty(value="fecha_inicio") Date fecha_inicio, @JsonProperty(value="fecha_final") Date fecha_final, @JsonProperty(value="nombre") String nombre) {
		this.id = id;
		this.fecha_inicio = fecha_inicio;
		this.fecha_final = fecha_final;
		this.nombre = nombre;
	}

	/**
	 * Da el id del festival
	 * @return Id del festival
	 */
	public int getId() {
		return id;
	}

	/**
	 * Cambia el id del festival
	 * @param id Nuevo id del festival.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Da la fecha de inicio del festival
	 * @return Fecha de inicio del festival
	 */
	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	/**
	 * Cambia la fecha de inicio del festival
	 * @param fecha_inicio Nueva fecha de inicio del festival de inicio
	 */
	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	/**
	 * Da la fecha final del festival
	 * @return Fecha final del festival
	 */
	public Date getFecha_final() {
		return fecha_final;
	}

	/**
	 * Cambia la fecha final del festival
	 * @param fecha_final Nueva fecha final del festival
	 */
	public void setFecha_final(Date fecha_final) {
		this.fecha_final = fecha_final;
	}

	/**
	 * Da el nombre del festival
	 * @return Nombre del festival
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Cambia el nombre del festival
	 * @param nombre Nuevo nombre del festival
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}
