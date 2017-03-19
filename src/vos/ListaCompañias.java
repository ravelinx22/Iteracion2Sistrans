package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaCompañias {

	/**
	 * Lista de compañias
	 */
	@JsonProperty(value="compañias")
	private List<Compañia> listaCompañias;

	/**
	 * Constructor de una nueva lista de compañias
	 * @param listaCompañias Lista de compañias
	 */
	public ListaCompañias(List<Compañia> listaCompañias) {
		super();
		this.listaCompañias = listaCompañias;
	}

	/**
	 * Da la lista de compañias.
	 * @return Lista de compañias
	 */
	public List<Compañia> getListaCompañias() {
		return listaCompañias;
	}

	/**
	 * Modifica la lista de compañias.
	 * @param listaCompañias Nueva lista de compañias.
	 */
	public void setListaCompañias(List<Compañia> listaCompañias) {
		this.listaCompañias = listaCompañias;
	}
}
