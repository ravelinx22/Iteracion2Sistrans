package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Sitio {

	/**
	 * Id del sitio
	 */
	@JsonProperty(value="id")
	private int id;

	/**
	 * Nombre del sitio
	 */
	@JsonProperty(value="nombre")
	private String nombre;

	/**
	 * Capacidad del sitio
	 */
	@JsonProperty(value="capacidad")
	private int capacidad;

	/**
	 * Disponibilidad del sitio para discapacitados
	 */
	@JsonProperty(value="apto_discapacitados")
	private boolean apto_discapacitados;

	/**
	 * Tipo de silleteria del sitio.
	 */
	@JsonProperty(value="tipo_silleteria")
	private String tipo_silleteria;

	/**
	 * Verifica si el sitio tiene cobertura
	 */
	@JsonProperty(value="tiene_cobertura")
	private boolean tiene_cobertura;

	/**
	 * Requerimientos del espectaculo
	 */
	@JsonProperty(value="requerimientos")
	private Integer[] requerimientos;
	
	/**
	 * Constructor de la clase sitio.
	 * @param id - Id del sitio. 
	 * @param nombre - Nombre del sitio
	 * @param capacidad - Capacidad del sitio.
	 * @param aptoDiscapacitados - Representa si el sitio es apto para discapacitados
	 * @param tipoSilleteria  - Tipo de silleteria del sitio
	 * @param tieneCobertura -  Tipo de cobertura del sitio
	 * @param requerimientos - Requerimientos del sitio
	 */
	public Sitio(@JsonProperty(value="id") int id ,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="capacidad") int capacidad,@JsonProperty(value="apto_discapacitados") boolean aptoDiscapacitados,@JsonProperty(value="tipo_silleteria") String tipoSilleteria,@JsonProperty(value="tiene_cobertura") boolean tieneCobertura, @JsonProperty(value="requerimientos") Integer[] requerimientos) {
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.apto_discapacitados = aptoDiscapacitados;
		this.tipo_silleteria = tipoSilleteria;
		this.tiene_cobertura = tieneCobertura;
		this.requerimientos = requerimientos;
	}

	/**
	 * Da el id del sitio
	 * @return Id del sitio
	 */
	public int getId() {
		return id;
	}

	/**
	 * Modifica el id del sitio
	 * @param id Nuevo id del sitio
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Da el nombre del sitio
	 * @return Nombre del sitio.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Modifica el nombre del sitio
	 * @param nombre Nuevo nombre del sitio.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Da la capacidad del sitio.
	 * @return Capacidad del sitio
	 */
	public int getCapacidad() {
		return capacidad;
	}

	/**
	 * Modifica la capacidad del sitio.
	 * @param Capacidad del sitio
	 */
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	/**
	 * Da la disponibilidad del sitio para discapacitados.
	 * @return Disponibilidad del sitio para discapacitados.
	 */
	public boolean esAptoDiscapacitados() {
		return apto_discapacitados;
	}

	/**
	 * Modifica la disponibilidad del sitio para discapacitados
	 * @param aptoDiscapacitados - Nuevo disponibilidad del sitio para los discapacitados.
	 */
	public void setAptoDiscapacitados(boolean aptoDiscapacitados) {
		this.apto_discapacitados = aptoDiscapacitados;
	}

	/**
	 * Da el tipo de silleteria del sitio
	 * @return Tipo de silleteria del sitio
	 */
	public String getTipoSilleteria() {
		return tipo_silleteria;
	}

	/**
	 * Modifica el tipo de silleteria del sitio.
	 * @param Tipo de silleteria del sitio.
	 */
	public void setTipoSilleteria(String tipoSilleteria) {
		this.tipo_silleteria = tipoSilleteria;
	}

	/**
	 * Verifica si el sitio tiene cobertura.
	 * @return True si el sitio tiene cobertura, false de lo contrario.
	 */
	public boolean tieneCobertura() {
		return tiene_cobertura;
	}

	/**
	 * Modifica el valor que verifica si el sitio tiene cobertura
	 * @param Nuevo valor que verifica si el sitio tiene cobertura
	 */
	public void setTieneCobertura(boolean tieneCobertura) {
		this.tiene_cobertura = tieneCobertura;
	}
	
	/**
	 * Da los requerimientos del sitio
	 * @return Requerimientos del sitio
	 */
	public Integer[] getRequerimientos() {
		return this.requerimientos;
	}
	
	/**
	 * Modifica los requerimientos del sitio.
	 * @param requerimientos - Nuevos requerimientos 
	 */
	public void setRequerimiento(Integer[] requerimientos) {
		this.requerimientos= requerimientos;
	}
	
	/**
	 * Verifica si el sitio cumple con los requerimientos que entran por parametro.
	 * @param requerimientosEspec Requerimientos que se quieren cumplir
	 * @return true si cumple todos los requisitos, false de lo contrario.
	 */
	public boolean cumpleRequerimientos(Integer[] requerimientosEspec) {
		int count = 0;
		
		for(Integer x : requerimientosEspec) {
			for(Integer y : this.requerimientos) {
				if(x == y)
					count++;
			}
		}
		
		return count == requerimientosEspec.length;
	}
}
