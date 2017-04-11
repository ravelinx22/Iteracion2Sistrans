package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaAbonos {
	/**
	 * Lista de abonos
	 */
	@JsonProperty(value="abonos")
	private List<Abono> listaAbonos;

	/**
	 * Constructor de una nueva lista de abonos
	 * @param listaAbonos Lista de abonos
	 */
	public ListaAbonos(List<Abono> listaAbonos) {
		super();
		this.listaAbonos = listaAbonos;
	}

	/**
	 * Da la lista de abonos.
	 * @return Lista de abonos
	 */
	public List<Abono> getListaAbonos() {
		return listaAbonos;
	}

	/**
	 * Modifica la lista de abonos.
	 * @param listaAbonos Nueva lista de abonos.
	 */
	public void setListaAbonos(List<Abono> listaAbonos) {
		this.listaAbonos = listaAbonos;
	}
}
