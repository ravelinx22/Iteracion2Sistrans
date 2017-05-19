package tm;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import dao.DAOTablaBoletas;
import dao.DAOTablaUsuarios;
import vos.Boleta;
import vos.ListaUsuarios;
import vos.Usuario;

public class UsuarioMaster extends FestivAndesMaster {

	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public UsuarioMaster(String contextPath) {
		super(contextPath);
	}

	// Transacciones

	/**
	 * Da los usuarios en la base de datos
	 * @return Lista de usuarios con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaUsuarios darUsuarios() throws Exception {
		ArrayList<Usuario> usuarios;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();

		try {
			this.conn = darConexion();
			daoUsuarios.setConnection(conn);
			usuarios = daoUsuarios.darUsuarios();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}

		return new ListaUsuarios(usuarios);
	}

	/**
	 * Da el usuario que tiene id igual al parametro.
	 * @param id Id del usuario a buscar.
	 * @return Da el usuario que tiene id igual al parametro, null de lo contrario.
	 */
	public Usuario darUsuario(int id) throws SQLException, Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		Usuario us = null;
		try {
			this.conn = darConexion();
			daoUsuario.setConnection(conn);
			us = daoUsuario.darUsuario(id);
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}

		return us;
	}

	/**
	 * Agrega un usuario a la base de datos
	 * @param usuario Usuario a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			daoUsuario.setConnection(conn);
			daoUsuario.addUsuario(usuario);
			conn.commit();
		} catch(SQLException e) {
			this.conn.rollback();
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			this.conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * Actualiza el usuario de la base de datos.
	 * @param usuario Usuario con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			daoUsuario.setConnection(conn);
			daoUsuario.updateUsuario(usuario);
			conn.commit();
		} catch(SQLException e) {
			this.conn.rollback();
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			this.conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * Elimina el usuario de la base de datos.
	 * @param usuario Usuario a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			daoUsuario.setConnection(conn);
			daoUsuario.deleteUsuario(usuario);
			conn.commit();
		} catch(SQLException e) {
			this.conn.rollback();
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			this.conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * Consulta la asistencia de un cliente a funciones
	 * @param id_usuario Id del usuario
	 * @return Mapa con los datos de la consulta
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error conviertiendo los datos a usuario.
	 */
	public HashMap<String, Object> consultarAsistencia(int id_usuario, Date fecha) throws Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			daoUsuario.setConnection(conn);
			return daoUsuario.consultarAsistenciaCliente(id_usuario, fecha);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	public void compraMultipleDeBoletas(ArrayList<Boleta> arr) throws Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			daoUsuario.setConnection(conn);
			daoUsuario.compraMultipleDeBoletas(arr);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	public void consultarResumenEspectaculos(int idUsuario) throws SQLException, Exception{
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			daoUsuario.setConnection(conn);
			daoUsuario.consultarResumenEspectaculos(idUsuario);;
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}	

	// Iteracion 4
	
	public ArrayList<HashMap<String, Object>> darAsistencia(int id_compañia, Date fecha1, Date fecha2) throws Exception {
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		ArrayList<HashMap<String, Object>> mapa = new ArrayList<HashMap<String, Object>>();
		try {
			this.conn = darConexion();
			daoUsuarios.setConnection(conn);
			mapa = daoUsuarios.darAsistencia(id_compañia, fecha1, fecha2);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return mapa;
	}
	
	public ArrayList<HashMap<String, Object>> darAsistencia(int id_compañia, Date fecha1, Date fecha2, String group, String orden) throws Exception {
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		ArrayList<HashMap<String, Object>> mapa = new ArrayList<HashMap<String, Object>>();
		try {
			this.conn = darConexion();
			daoUsuarios.setConnection(conn);
			mapa = daoUsuarios.darAsistencia(id_compañia, fecha1, fecha2,group, orden);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return mapa;
	}
	
	public ArrayList<HashMap<String, Object>> darAsistenciaVersion2(int id_compañia, Date fecha1, Date fecha2) throws Exception {
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		ArrayList<HashMap<String, Object>> mapa = new ArrayList<HashMap<String, Object>>();
		try {
			this.conn = darConexion();
			daoUsuarios.setConnection(conn);
			mapa = daoUsuarios.darAsistenciaVersion2(id_compañia, fecha1, fecha2);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return mapa;
	}
	
	public ArrayList<HashMap<String, Object>> darAsistenciaVersion2(int id_compañia, Date fecha1, Date fecha2, String group, String orden) throws Exception {
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		ArrayList<HashMap<String, Object>> mapa = new ArrayList<HashMap<String, Object>>();
		try {
			this.conn = darConexion();
			daoUsuarios.setConnection(conn);
			mapa = daoUsuarios.darAsistenciaVersion2(id_compañia, fecha1, fecha2, group, orden);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return mapa;
	}
	
	
	public ArrayList<HashMap<String, Object>> consultarBuenosClientes(int nBoletas) throws Exception {
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		ArrayList<HashMap<String, Object>> mapa = new ArrayList<HashMap<String, Object>>();
		try {
			this.conn = darConexion();
			daoUsuarios.setConnection(conn);
			mapa = daoUsuarios.consultarBuenosClientes(nBoletas);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return mapa;
	}
}
