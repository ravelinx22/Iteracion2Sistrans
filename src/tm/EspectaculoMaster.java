package tm;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DAOTablaEspectaculos;
import vos.Espectaculo;
import vos.ListaEspectaculos;

public class EspectaculoMaster extends FestivAndesMaster {

	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public EspectaculoMaster(String contextPath) {
		super(contextPath);
	}

	// Transacciones

	/**
	 * Da los espectaculos en la base de datos
	 * @return Lista de espectaculos con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaEspectaculos darEspectaculos() throws Exception {
		ArrayList<Espectaculo> espectaculos;
		DAOTablaEspectaculos daoEspectaculos = new DAOTablaEspectaculos();

		try {
			this.conn = darConexion();
			daoEspectaculos.setConnection(conn);
			espectaculos = daoEspectaculos.darEspectaculos();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspectaculos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}

		return new ListaEspectaculos(espectaculos);
	}
	
	/**
	 * Da el espectaculo que tiene id igual al parametro.
	 * @param id Id del espectaculo a buscar.
	 * @return Da el espectaculo que tiene id igual al parametro, null de lo contrario.
	 */
	public Espectaculo darEspectaculo(int id) throws SQLException, Exception {
		DAOTablaEspectaculos daoEspectaculo = new DAOTablaEspectaculos();
		Espectaculo es = null;
		try {
			this.conn = darConexion();
			daoEspectaculo.setConnection(conn);
			es = daoEspectaculo.darEspectaculo(id);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEspectaculo.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return es;
	}

	/**
	 * Agrega un espectaculo a la base de datos
	 * @param espectaculo Espectaculo a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addEspectaculo(Espectaculo espectaculo) throws Exception {
		DAOTablaEspectaculos daoEspectaculos = new DAOTablaEspectaculos();
		try {
			this.conn = darConexion();
			daoEspectaculos.setConnection(conn);
			daoEspectaculos.addEspectaculo(espectaculo);
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
				daoEspectaculos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * Actualiza el espectaculo de la base de datos.
	 * @param espectaculo Espectaculo con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateEspectaculo(Espectaculo espectaculo) throws Exception {
		DAOTablaEspectaculos daoEspectaculos = new DAOTablaEspectaculos();
		try {
			this.conn = darConexion();
			daoEspectaculos.setConnection(conn);
			daoEspectaculos.updateEspectaculo(espectaculo);
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
				daoEspectaculos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * Elimina el espectaculo de la base de datos.
	 * @param espectaculo Espectaculo a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteEspectaculo(Espectaculo espectaculo) throws Exception {
		DAOTablaEspectaculos daoEspectaculos = new DAOTablaEspectaculos();
		try {
			this.conn = darConexion();
			daoEspectaculos.setConnection(conn);
			daoEspectaculos.deleteEspectaculo(espectaculo);
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
				daoEspectaculos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
