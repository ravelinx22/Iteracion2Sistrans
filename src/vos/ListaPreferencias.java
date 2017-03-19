package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaPreferencias {

	/**
	 * Lista de preferencias
	 */
	@JsonProperty(value="preferencias")
	private List<Preferencia> listaPreferencias;

	/**
	 * Construye una nueva lista de preferencias
	 * @param listaPreferencias Lista de preferencias
	 */
	public ListaPreferencias(List<Preferencia> listaPreferencias) {
		super();
		this.listaPreferencias = listaPreferencias;
	}

	/**
	 * Da la lista de preferencias
	 * @return Lista de preferencias
	 */
	public List<Preferencia> getListaPreferencias() {
		return listaPreferencias;
	}

	/**
	 * Modifica la lista de preferencias.
	 * @param listaPreferencias Nueva lista de preferencias.
	 */
	public void setListaPreferencias(List<Preferencia> listaPreferencias) {
		this.listaPreferencias = listaPreferencias;
	}
}
