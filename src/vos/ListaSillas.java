package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaSillas {

	/**
	 * Lista de sillas en un sitio
	 */
	@JsonProperty(value="sillas")
	private List<Silla> sillas;

	/**
	 * Construye una nueva lista de sillas
	 * @param sillas Sillas de un sitio
	 */
	public ListaSillas(List<Silla> sillas) {
		super();
		this.sillas = sillas;
	}

	/**
	 * Da las sillas de un sitio
	 * @return Sillas de un sitio
	 */
	public List<Silla> getSillas() {
		return sillas;
	}

	/**
	 * Modifica la lista de sillas de un sitio
	 * @param sillas Nueva lista de sillas.
	 */
	public void setSillas(List<Silla> sillas) {
		this.sillas = sillas;
	}
}
