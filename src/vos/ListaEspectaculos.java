package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaEspectaculos {

	/**
	 * Lista de espectaculos
	 */
	@JsonProperty(value="espectaculos")
	private List<Espectaculo> listaEspectaculos;

	/**
	 * Constructor de una nueva lista de espectaculos.
	 * @param listaEspectaculos Lista de espectaclulos.
	 */
	public ListaEspectaculos(List<Espectaculo> listaEspectaculos) {
		super();
		this.listaEspectaculos = listaEspectaculos;
	}

	/**
	 * Da una lista de espectaculos.
	 * @return Lista de espectaculos.
	 */
	public List<Espectaculo> getListaEspectaculos() {
		return listaEspectaculos;
	}

	/**
	 * Modifica la lista de espectaculos.
	 * @param listaEspectaculos Nueva lista de espectaculos.
	 */
	public void setListaEspectaculos(List<Espectaculo> listaEspectaculos) {
		this.listaEspectaculos = listaEspectaculos;
	}
}
