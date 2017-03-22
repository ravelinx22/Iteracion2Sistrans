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
	 * Disponibilidad de sitio para el lunes
	 */
	@JsonProperty(value="disponible_lunes")
	private boolean disponible_lunes;

	/**
	 * Disponibilidad de sitio para el martes
	 */
	@JsonProperty(value="disponible_martes")
	private boolean disponible_martes;

	/**
	 * Disponibilidad de sitio para el miercoles
	 */
	@JsonProperty(value="disponible_miercoles")
	private boolean disponible_miercoles;

	/**
	 * Disponibilidad de sitio para el jueves
	 */
	@JsonProperty(value="disponible_jueves")
	private boolean disponible_jueves;

	/**
	 * Disponibilidad de sitio para el viernes
	 */
	@JsonProperty(value="disponible_viernes")
	private boolean disponible_viernes;

	/**
	 * Disponibilidad de sitio para el sabado
	 */
	@JsonProperty(value="disponible_sabados")
	private boolean disponible_sabados;

	/**
	 * Disponibilidad de sitio para el domingo
	 */
	@JsonProperty(value="disponible_domingos")
	private boolean disponible_domingos;

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
	 * @param disponibleLunes - Disponibilidad del sitio para el lunes
	 * @param disponibleMartes - Disponibilidad del sitio para el martes
	 * @param disponibleMiercoles - Disponibilidad del sitio para el miercoles
	 * @param disponibleJueves - Disponibilidad del sitio para el jueves
	 * @param disponibleViernes - Disponibilidad del sitio para el viernes
	 * @param disponibleSabados - Disponibilidad del sitio para el sabado
	 * @param disponibleDomingos - Disponibilidad del sitio para el domingo
	 * @param requerimientos - Requerimientos del sitio
	 */
	public Sitio(@JsonProperty(value="id") int id ,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="capacidad") int capacidad,@JsonProperty(value="apto_discapacitados") boolean aptoDiscapacitados,@JsonProperty(value="tipo_silleteria") String tipoSilleteria,@JsonProperty(value="tiene_cobertura") boolean tieneCobertura,@JsonProperty(value="disponible_lunes") boolean disponibleLunes,@JsonProperty(value="disponible_martes") boolean disponibleMartes,@JsonProperty(value="disponible_miercoles") boolean disponibleMiercoles,@JsonProperty(value="disponible_jueves") boolean disponibleJueves,@JsonProperty(value="disponible_viernes") boolean disponibleViernes,@JsonProperty(value="disponible_sabado") boolean disponibleSabados,@JsonProperty(value="disponible_domingo") boolean disponibleDomingos, @JsonProperty(value="requerimientos") Integer[] requerimientos) {
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.apto_discapacitados = aptoDiscapacitados;
		this.tipo_silleteria = tipoSilleteria;
		this.tiene_cobertura = tieneCobertura;
		this.disponible_lunes = disponibleLunes;
		this.disponible_martes = disponibleMartes;
		this.disponible_miercoles = disponibleMiercoles;
		this.disponible_jueves = disponibleJueves;
		this.disponible_viernes = disponibleViernes;
		this.disponible_sabados = disponibleSabados;
		this.disponible_domingos = disponibleDomingos;
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
	 * Verifica si el sitio esta disponible los lunes.
	 * @return Disponibilidad del sitio los lunes
	 */
	public boolean isDisponibleLunes() {
		return disponible_lunes;
	}

	/**
	 * Modifica la disponibilidad del sitio los lunes.
	 * @param disponibleLunes Nueva disponibilidad del sitio los lunes
	 */
	public void setDisponibleLunes(boolean disponibleLunes) {
		this.disponible_lunes = disponibleLunes;
	}

	/**
	 * Verifica si el sitio esta disponible los martes.
	 * @return Disponibilidad del sitio los martes.
	 */
	public boolean isDisponibleMartes() {
		return disponible_martes;
	}

	/**
	 * Modifica la disponibilidad del sitio los martes.
	 * @param disponibleMartes Nueva disponibilidad del sitio los martes
	 */
	public void setDisponibleMartes(boolean disponibleMartes) {
		this.disponible_martes = disponibleMartes;
	}

	/**
	 * Verifica si el sitio esta disponible los miercoles.
	 * @return Disponibilidad del sitio los miercoles
	 */
	public boolean isDisponibleMiercoles() {
		return disponible_miercoles;
	}

	/**
	 * Modifica la disponibilidad del sitio los jueves.
	 * @param disponible_jueves Nueva disponibilidad del sitio los jueves
	 */
	public void setDisponibleMiercoles(boolean disponibleMiercoles) {
		this.disponible_miercoles = disponibleMiercoles;
	}

	/**
	 * Verifica si el sitio esta disponible los jueves.
	 * @return Disponibilidad del sitio los jueves
	 */
	public boolean isDisponibleJueves() {
		return disponible_jueves;
	}

	/**
	 * Modifica la disponibilidad del sitio los jueves.
	 * @param disponibleJueves Nueva disponibilidad del sitio los jueves
	 */
	public void setDisponibleJueves(boolean disponibleJueves) {
		this.disponible_jueves = disponibleJueves;
	}

	/**
	 * Verifica si el sitio esta disponible los viernes.
	 * @return Disponibilidad del sitio los viernes
	 */
	public boolean isDisponibleViernes() {
		return disponible_viernes;
	}

	/**
	 * Modifica la disponibilidad del sitio los viernes.
	 * @param disponibleViernes Nueva disponibilidad del sitio los viernes
	 */
	public void setDisponibleViernes(boolean disponibleViernes) {
		this.disponible_viernes = disponibleViernes;
	}

	/**
	 * Verifica si el sitio esta disponible los sabados.
	 * @return Disponibilidad del sitio los sabados
	 */
	public boolean isDisponibleSabado() {
		return disponible_sabados;
	}

	/**
	 * Modifica la disponibilidad del sitio los sabados.
	 * @param disponibleSabado Nueva disponibilidad del sitio los sabados
	 */
	public void setDisponibleSabado(boolean disponibleSabado) {
		this.disponible_sabados = disponibleSabado;
	}

	/**
	 * Verifica si el sitio esta disponible los domingos.
	 * @return Disponibilidad del sitio los domingos.
	 */
	public boolean isDisponibleDomingo() {
		return disponible_domingos;
	}

	/**
	 * Modifica la disponibilidad del sitio los domingos.
	 * @param disponibleDomingo Nueva disponibilidad del sitio los domingos
	 */
	public void setDisponibleDomingo(boolean disponibleDomingo) {
		this.disponible_domingos = disponibleDomingo;
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
}
