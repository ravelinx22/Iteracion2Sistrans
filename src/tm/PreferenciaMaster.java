package tm;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DAOTablaPreferencias;
import vos.ListaPreferencias;
import vos.Preferencia;

public class PreferenciaMaster extends FestivAndesMaster {
	
	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public PreferenciaMaster(String contextPath) {
		super(contextPath);
	}
	
	// Transacciones
	
	/**
	 * Da las preferencias en la base de datos
	 * @return Lista de preferencias con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaPreferencias darPreferencias() throws Exception {
		ArrayList<Preferencia> preferencias;
		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		
		try {
			this.conn = darConexion();
			daoPreferencias.setConnection(conn);
			preferencias = daoPreferencias.darPreferencias();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaPreferencias(preferencias);
	}
	
	/**
	 * Agrega una preferencia a la base de datos
	 * @param preferencia Preferencia a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addPreferencia(Preferencia preferencia) throws Exception {
		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		try {
			this.conn = darConexion();
			daoPreferencias.setConnection(conn);
			daoPreferencias.addPreferencia(preferencia);
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
				daoPreferencias.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza la preferencia de la base de datos.
	 * @param preferencia Preferencia con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updatePreferencia(Preferencia preferencia) throws Exception {
		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		try {
			this.conn = darConexion();
			daoPreferencias.setConnection(conn);
			daoPreferencias.updatePreferencia(preferencia);
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
				daoPreferencias.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina la preferencia de la base de datos.
	 * @param preferencia Preferencia a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deletePreferencia(Preferencia preferencia) throws Exception {
		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		try {
			this.conn = darConexion();
			daoPreferencias.setConnection(conn);
			daoPreferencias.deletePreferencia(preferencia);
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
				daoPreferencias.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
