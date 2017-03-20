package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRequerimientosTenicos {

	/**
	 * Lista de requerimientos tecnicos
	 */
	@JsonProperty(value="requerimientos_tecnicos")
	private List<RequerimientoTecnico> listaRequerimientosTecnicos;

	/**
	 * Construye una nueva lista de requerimientos tecnicos 
	 * @param listaRequerimientosTecnicos Lista de requerimientos tecnicos
	 */
	public ListaRequerimientosTenicos(List<RequerimientoTecnico> listaRequerimientosTecnicos) {
		super();
		this.listaRequerimientosTecnicos = listaRequerimientosTecnicos;
	}

	/**
	 * Da la lista de requerimientos tecnicos
	 * @return Lista de requerimientos tecnicos
	 */
	public List<RequerimientoTecnico> getListaRequerimientosTecnicos() {
		return listaRequerimientosTecnicos;
	}

	/**
	 * Modifica la lista de requerimientos tecnicos.
	 * @param listaRequerimientosTecnicos Nueva lista de requerimientos tecnicos.
	 */
	public void setListaRequerimientosTecnicos(List<RequerimientoTecnico> listaRequerimientosTecnicos) {
		this.listaRequerimientosTecnicos = listaRequerimientosTecnicos;
	}
}
