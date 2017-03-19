package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaLocalidades {

	/**
	 * Lista de localidades
	 */
	@JsonProperty(value="localidades")
	private List<Localidad> listaLocalidades;

	/**
	 * Construye una lista de localidades
	 * @param listaLocalidades Lista de localidades
	 */
	public ListaLocalidades(List<Localidad> listaLocalidades) {
		super();
		this.listaLocalidades = listaLocalidades;
	}

	/**
	 * Da una lista de localidades
	 * @return Lista de localidades
	 */
	public List<Localidad> getListaLocalidades() {
		return listaLocalidades;
	}

	/**
	 * Modifica la lista de localidades
	 * @param listaLocalidades Nueva lista de localidades
	 */
	public void setListaLocalidades(List<Localidad> listaLocalidades) {
		this.listaLocalidades = listaLocalidades;
	}
}
