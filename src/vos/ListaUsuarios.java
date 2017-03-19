package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaUsuarios {

	/**
	 * List de usuarios
	 */
	@JsonProperty(value="usuarios")
	private List<Usuario> usuarios;
	
	/**
	 * Constructor de una list de usuarios
	 * @param usuarios - List que contiene los usuarios registrados.
	 */
	public ListaUsuarios(@JsonProperty(value="usuarios") List<Usuario> usuarios){
		this.usuarios = usuarios;
	}

	/**
	 * Da la lista de usuarios.
	 * @return Lista de usuarios
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	/**
	 * Modifica la lista de usuarios
	 * @param usuarios Nueva lista de usuarios.
	 */
	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
