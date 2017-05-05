package tm;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DAOTablaRequerimientos;
import vos.ListaRequerimientosTenicos;
import vos.RequerimientoTecnico;

public class RequerimientoMaster extends FestivAndesMaster {

	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public RequerimientoMaster(String contextPath) {
		super(contextPath);
	}
	
	// Transacciones
	
	/**
	 * Da los requerimientos en la base de datos
	 * @return Lista de requerimientos con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaRequerimientosTenicos darRequerimientos() throws Exception {
		ArrayList<RequerimientoTecnico> requerimientos;
		DAOTablaRequerimientos daoRequerimientos = new DAOTablaRequerimientos();
		
		try {
			this.conn = darConexion();
			daoRequerimientos.setConnection(conn);
			requerimientos = daoRequerimientos.darRequerimientos();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRequerimientos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaRequerimientosTenicos(requerimientos);
	}
	
	/**
	 * Agrega un requerimiento a la base de datos
	 * @param requerimiento Requerimiento a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addRequerimiento(RequerimientoTecnico requerimiento) throws Exception {
		DAOTablaRequerimientos daoRequerimientos = new DAOTablaRequerimientos();
		try {
			this.conn = darConexion();
			daoRequerimientos.setConnection(conn);
			daoRequerimientos.addRequerimiento(requerimiento);
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
				daoRequerimientos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza el requerimiento de la base de datos.
	 * @param requerimiento Requerimiento con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateRequerimiento(RequerimientoTecnico requerimiento) throws Exception {
		DAOTablaRequerimientos daoRequerimientos = new DAOTablaRequerimientos();
		try {
			this.conn = darConexion();
			daoRequerimientos.setConnection(conn);
			daoRequerimientos.updateRequerimiento(requerimiento);
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
				daoRequerimientos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina el requerimiento de la base de datos.
	 * @param requerimiento Requerimiento a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteRequerimiento(RequerimientoTecnico requerimiento) throws Exception {
		DAOTablaRequerimientos daoRequerimientos = new DAOTablaRequerimientos();
		try {
			this.conn = darConexion();
			daoRequerimientos.setConnection(conn);
			daoRequerimientos.deleteRequerimiento(requerimiento);
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
				daoRequerimientos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
