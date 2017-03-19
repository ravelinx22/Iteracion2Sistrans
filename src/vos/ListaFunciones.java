package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaFunciones {

	/**
	 * Lista de funciones
	 */
	@JsonProperty(value="funciones")
	private List<Funcion> funciones;

	/**
	 * Constructor de una nueva lista de canciones
	 * @param funciones
	 */
	public ListaFunciones(List<Funcion> funciones) {
		super();
		this.funciones = funciones;
	}

	/**
	 * Da la lista de funciones
	 * @return Lista de funciones
	 */
	public List<Funcion> getFunciones() {
		return funciones;
	}

	/**
	 * Modifica la lista de funciones
	 * @param funciones Nueva lista de funciones
	 */
	public void setFunciones(List<Funcion> funciones) {
		this.funciones = funciones;
	}
}
