package tm;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DAOTablaFestivales;
import vos.Festival;
import vos.ListaFestivales;

public class FestivalMaster extends FestivAndesMaster {
	
	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public FestivalMaster(String contextPath) {
		super(contextPath);
	}
	
	// Transacciones
	
	/**
	 * Da los festivales en la base de datos
	 * @return Lista de festivales con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaFestivales darFestivales() throws Exception {
		ArrayList<Festival> festivales;
		DAOTablaFestivales daoFestivales = new DAOTablaFestivales();
		
		try {
			this.conn = darConexion();
			daoFestivales.setConnection(conn);
			festivales = daoFestivales.darFestivales();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFestivales.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaFestivales(festivales);
	}
	
	/**
	 * Agrega un festival a la base de datos
	 * @param festival Festival a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addFestival(Festival festival) throws Exception {
		DAOTablaFestivales daoFestivales = new DAOTablaFestivales();
		try {
			this.conn = darConexion();
			daoFestivales.setConnection(conn);
			daoFestivales.addFestival(festival);
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
				daoFestivales.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza el festival de la base de datos.
	 * @param festival Festival con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateFestival(Festival festival) throws Exception {
		DAOTablaFestivales daoFestivales = new DAOTablaFestivales();
		try {
			this.conn = darConexion();
			daoFestivales.setConnection(conn);
			daoFestivales.updateFestival(festival);
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
				daoFestivales.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina el festival de la base de datos.
	 * @param festival Festival a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteFestival(Festival festival) throws Exception {
		DAOTablaFestivales daoFestivales = new DAOTablaFestivales();
		try {
			this.conn = darConexion();
			daoFestivales.setConnection(conn);
			daoFestivales.deleteFestival(festival);
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
				daoFestivales.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
