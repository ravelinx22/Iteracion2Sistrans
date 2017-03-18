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
	private boolean aptoDiscapacitados;

	/**
	 * Tipo de silleteria del sitio.
	 */
	@JsonProperty(value="tipo_silleteria")
	private String tipoSilleteria;

	/**
	 * Verifica si el sitio tiene cobertura
	 */
	@JsonProperty(value="tiene_cobertura")
	private boolean tieneCobertura;

	/**
	 * Disponibilidad de sitio para el lunes
	 */
	@JsonProperty(value="disponible_lunes")
	private boolean disponibleLunes;

	/**
	 * Disponibilidad de sitio para el martes
	 */
	@JsonProperty(value="disponible_martes")
	private boolean disponibleMartes;

	/**
	 * Disponibilidad de sitio para el miercoles
	 */
	@JsonProperty(value="disponible_miercoles")
	private boolean disponibleMiercoles;

	/**
	 * Disponibilidad de sitio para el jueves
	 */
	@JsonProperty(value="disponible_jueves")
	private boolean disponibleJueves;

	/**
	 * Disponibilidad de sitio para el viernes
	 */
	@JsonProperty(value="disponible_viernes")
	private boolean disponibleViernes;

	/**
	 * Disponibilidad de sitio para el sabado
	 */
	@JsonProperty(value="disponible_sabado")
	private boolean disponibleSabado;

	/**
	 * Disponibilidad de sitio para el domingo
	 */
	@JsonProperty(value="disponible_domingo")
	private boolean disponibleDomingo;

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
	 * @param disponibleSabado - Disponibilidad del sitio para el sabado
	 * @param disponibleDomingo - Disponibilidad del sitio para el domingo
	 */
	public Sitio(@JsonProperty(value="id") int id ,@JsonProperty(value="nombre") String nombre,@JsonProperty(value="capacidad") int capacidad,@JsonProperty(value="apto_discapacitados") boolean aptoDiscapacitados,@JsonProperty(value="tipo_silleteria") String tipoSilleteria,@JsonProperty(value="tiene_cobertura") boolean tieneCobertura,@JsonProperty(value="disponible_lunes") boolean disponibleLunes,@JsonProperty(value="disponible_martes") boolean disponibleMartes,@JsonProperty(value="disponible_miercoles") boolean disponibleMiercoles,@JsonProperty(value="disponible_jueves") boolean disponibleJueves,@JsonProperty(value="disponible_viernes") boolean disponibleViernes,@JsonProperty(value="disponible_sabado") boolean disponibleSabado,@JsonProperty(value="disponible_domingo") boolean disponibleDomingo) {
		this.id = id;
		this.nombre = nombre;
		this.capacidad = capacidad;
		this.aptoDiscapacitados = aptoDiscapacitados;
		this.tipoSilleteria = tipoSilleteria;
		this.tieneCobertura = tieneCobertura;
		this.disponibleLunes = disponibleLunes;
		this.disponibleMartes = disponibleMartes;
		this.disponibleMiercoles = disponibleMiercoles;
		this.disponibleJueves = disponibleJueves;
		this.disponibleViernes = disponibleViernes;
		this.disponibleSabado = disponibleSabado;
		this.disponibleDomingo = disponibleDomingo;
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
		return aptoDiscapacitados;
	}

	/**
	 * Modifica la disponibilidad del sitio para discapacitados
	 * @param aptoDiscapacitados - Nuevo disponibilidad del sitio para los discapacitados.
	 */
	public void setAptoDiscapacitados(boolean aptoDiscapacitados) {
		this.aptoDiscapacitados = aptoDiscapacitados;
	}

	/**
	 * Da el tipo de silleteria del sitio
	 * @return Tipo de silleteria del sitio
	 */
	public String getTipoSilleteria() {
		return tipoSilleteria;
	}

	/**
	 * Modifica el tipo de silleteria del sitio.
	 * @param Tipo de silleteria del sitio.
	 */
	public void setTipoSilleteria(String tipoSilleteria) {
		this.tipoSilleteria = tipoSilleteria;
	}

	/**
	 * Verifica si el sitio tiene cobertura.
	 * @return True si el sitio tiene cobertura, false de lo contrario.
	 */
	public boolean tieneCobertura() {
		return tieneCobertura;
	}

	/**
	 * Modifica el valor que verifica si el sitio tiene cobertura
	 * @param Nuevo valor que verifica si el sitio tiene cobertura
	 */
	public void setTieneCobertura(boolean tieneCobertura) {
		this.tieneCobertura = tieneCobertura;
	}

	/**
	 * Verifica si el sitio esta disponible los lunes.
	 * @return Disponibilidad del sitio los lunes
	 */
	public boolean isDisponibleLunes() {
		return disponibleLunes;
	}

	/**
	 * Modifica la disponibilidad del sitio los lunes.
	 * @param disponibleLunes Nueva disponibilidad del sitio los lunes
	 */
	public void setDisponibleLunes(boolean disponibleLunes) {
		this.disponibleLunes = disponibleLunes;
	}

	/**
	 * Verifica si el sitio esta disponible los martes.
	 * @return Disponibilidad del sitio los martes.
	 */
	public boolean isDisponibleMartes() {
		return disponibleMartes;
	}

	/**
	 * Modifica la disponibilidad del sitio los martes.
	 * @param disponibleMartes Nueva disponibilidad del sitio los martes
	 */
	public void setDisponibleMartes(boolean disponibleMartes) {
		this.disponibleMartes = disponibleMartes;
	}

	/**
	 * Verifica si el sitio esta disponible los miercoles.
	 * @return Disponibilidad del sitio los miercoles
	 */
	public boolean isDisponibleMiercoles() {
		return disponibleMiercoles;
	}

	/**
	 * Modifica la disponibilidad del sitio los jueves.
	 * @param disponibleJueves Nueva disponibilidad del sitio los jueves
	 */
	public void setDisponibleMiercoles(boolean disponibleMiercoles) {
		this.disponibleMiercoles = disponibleMiercoles;
	}

	/**
	 * Verifica si el sitio esta disponible los jueves.
	 * @return Disponibilidad del sitio los jueves
	 */
	public boolean isDisponibleJueves() {
		return disponibleJueves;
	}

	/**
	 * Modifica la disponibilidad del sitio los jueves.
	 * @param disponibleJueves Nueva disponibilidad del sitio los jueves
	 */
	public void setDisponibleJueves(boolean disponibleJueves) {
		this.disponibleJueves = disponibleJueves;
	}

	/**
	 * Verifica si el sitio esta disponible los viernes.
	 * @return Disponibilidad del sitio los viernes
	 */
	public boolean isDisponibleViernes() {
		return disponibleViernes;
	}

	/**
	 * Modifica la disponibilidad del sitio los viernes.
	 * @param disponibleViernes Nueva disponibilidad del sitio los viernes
	 */
	public void setDisponibleViernes(boolean disponibleViernes) {
		this.disponibleViernes = disponibleViernes;
	}

	/**
	 * Verifica si el sitio esta disponible los sabados.
	 * @return Disponibilidad del sitio los sabados
	 */
	public boolean isDisponibleSabado() {
		return disponibleSabado;
	}

	/**
	 * Modifica la disponibilidad del sitio los sabados.
	 * @param disponibleSabado Nueva disponibilidad del sitio los sabados
	 */
	public void setDisponibleSabado(boolean disponibleSabado) {
		this.disponibleSabado = disponibleSabado;
	}

	/**
	 * Verifica si el sitio esta disponible los domingos.
	 * @return Disponibilidad del sitio los domingos.
	 */
	public boolean isDisponibleDomingo() {
		return disponibleDomingo;
	}

	/**
	 * Modifica la disponibilidad del sitio los domingos.
	 * @param disponibleDomingo Nueva disponibilidad del sitio los domingos
	 */
	public void setDisponibleDomingo(boolean disponibleDomingo) {
		this.disponibleDomingo = disponibleDomingo;
	}
}
