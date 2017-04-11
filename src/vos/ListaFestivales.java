package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaFestivales {
	/**
	 * Lista de festivales
	 */
	@JsonProperty(value="festivales")
	private List<Festival> festivales;

	/**
	 * Constructor de una nueva lista de festivales
	 * @param funciones
	 */
	public ListaFestivales(List<Festival> festivales) {
		super();
		this.festivales = festivales;
	}

	/**
	 * Da la lista de festivales
	 * @return Lista de festivales
	 */
	public List<Festival> getFestivales() {
		return festivales;
	}

	/**
	 * Modifica la lista de festivales
	 * @param festivales Nueva lista de festivales
	 */
	public void setFestivale(List<Festival> festivales) {
		this.festivales = festivales;
	}
}
