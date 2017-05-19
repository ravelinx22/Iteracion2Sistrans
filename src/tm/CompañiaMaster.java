package tm;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DAOTablaCompañias;
import vos.Compañia;
import vos.ListaCompañias;

public class CompañiaMaster extends FestivAndesMaster {
	
	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public CompañiaMaster(String contextPath) {
		super(contextPath);
	}
	
	// Transacciones
	
	/**
	 * Da las compañias en la base de datos
	 * @return Lista de compañias con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaCompañias darCompañias() throws Exception {
		ArrayList<Compañia> compañias;
		DAOTablaCompañias daoCompañias = new DAOTablaCompañias();
		
		try {
			this.conn = darConexion();
			daoCompañias.setConnection(conn);
			compañias = daoCompañias.darCompañias();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCompañias.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaCompañias(compañias);
	}
	
	/**
	 * Agrega una compañia a la base de datos
	 * @param compañia Compañia a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addCompañia(Compañia compañia) throws Exception {
		DAOTablaCompañias daoCompañia = new DAOTablaCompañias();
		try {
			this.conn = darConexion();
			daoCompañia.setConnection(conn);
			daoCompañia.addCompañia(compañia);
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
				daoCompañia.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza la compañia de la base de datos.
	 * @param compañia Compañia con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateCompañia(Compañia compañia) throws Exception {
		DAOTablaCompañias daoCompañia = new DAOTablaCompañias();
		try {
			this.conn = darConexion();
			daoCompañia.setConnection(conn);
			daoCompañia.updateCompañia(compañia);
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
				daoCompañia.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina la compañia de la base de datos.
	 * @param compañia Compañia a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteCompañia(Compañia compañia) throws Exception {
		DAOTablaCompañias daoCompañia = new DAOTablaCompañias();
		try {
			this.conn = darConexion();
			daoCompañia.setConnection(conn);
			daoCompañia.deleteCompañia(compañia);
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
				daoCompañia.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	// ITERACION 5
	
	/**
	 * Retira la compañia del festival
	 * @param id_compañia Id de la compañia 
	 * @throws SQLException Si hay problema conectandose con la base de datos.
	 * @throws Exception Si hay un problema manejando los datos
	 */
	public void retirarCompañia(int id_compañia) throws SQLException, Exception {
		DAOTablaCompañias daoCompañia = new DAOTablaCompañias();
		try {
			this.conn = darConexion();
			daoCompañia.setConnection(conn);
			comienzoTransaccion(this.conn);
			
			daoCompañia.retirarCompañia(id_compañia);
			
			finalTransaccion(this.conn);
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
				daoCompañia.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
