package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaSitios {
	
	/**
	 * Lista de sitios
	 */
	@JsonProperty(value="sitios")
	private List<Sitio> sitios;

	/**
	 * Constructor de una lista de sitios
	 * @param sitios - Sitios que hay disponibles para reservar
	 */
	public ListaSitios(@JsonProperty(value="sitios") List<Sitio> sitios) {
		super();
		this.sitios = sitios;
	}

	/**
	 * Da los sitios disponibles
	 * @return Sitios disponibles
	 */
	public List<Sitio> getSitios() {
		return sitios;
	}

	/**
	 * Modifica los sitios disponibles
	 * @param sitios - Lista de nuevos sitios
	 */
	public void setSitios(List<Sitio> sitios) {
		this.sitios = sitios;
	}
}
