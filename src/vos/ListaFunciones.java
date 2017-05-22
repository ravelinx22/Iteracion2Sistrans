package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaFunciones {

	/**
	 * List con los funciones
	 */
	@JsonProperty(value="funciones")
	private List<Funcion> funciones;
	
	/**
	 * Constructor de la clase ListaFunciones
	 * @param funciones - funciones para agregar al arreglo de la clase
	 */
	public ListaFunciones( @JsonProperty(value="funciones")List<Funcion> funciones){
		this.funciones = funciones;
	}

	/**
	 * Método que retorna la lista de funciones
	 * @return  List - List con los funciones
	 */
	public List<Funcion> getFunciones() {
		return funciones;
	}

	/**
	 * Método que asigna la lista de funciones que entra como parametro
	 * @param  funciones - List con los funciones ha agregar
	 */
	public void setFuncione(List<Funcion> funciones) {
		this.funciones = funciones;
	}
	
}
