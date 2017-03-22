package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaBoletas {
	/**
	 * Lista de boletas
	 */
	@JsonProperty(value="boletas")
	private List<Boleta> listaBoletas;

	/**
	 * Constructor de una nueva lista de boletas
	 * @param listaCompañias Lista de boletas
	 */
	public ListaBoletas(List<Boleta> listaBoletas) {
		super();
		this.listaBoletas = listaBoletas;
	}

	/**
	 * Da la lista de boletas.
	 * @return Lista de boletas
	 */
	public List<Boleta> getListaBoletas() {
		return listaBoletas;
	}

	/**
	 * Modifica la lista de boletas.
	 * @param listaCompañias Nueva lista de boletsa.
	 */
	public void setListaBoletas(List<Boleta> listaBoletas) {
		this.listaBoletas = listaBoletas;
	}
}
