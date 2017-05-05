package tm;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DAOTablaSillas;
import vos.ListaSillas;
import vos.Silla;

public class SillaMaster extends FestivAndesMaster {

	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public SillaMaster(String contextPath) {
		super(contextPath);
	}
	
	// Transacciones
	
	/**
	 * Da las sillas en la base de datos
	 * @return Lista de sillas con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaSillas darSillas() throws Exception {
		ArrayList<Silla> sillas;
		DAOTablaSillas daoSillas = new DAOTablaSillas();
		
		try {
			this.conn = darConexion();
			daoSillas.setConnection(conn);
			sillas = daoSillas.darSillas();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSillas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaSillas(sillas);
	}
	
	/**
	 * Agrega una silla a la base de datos
	 * @param silla Silla a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addSilla(Silla silla) throws Exception {
		DAOTablaSillas daoSillas = new DAOTablaSillas();
		try {
			this.conn = darConexion();
			daoSillas.setConnection(conn);
			daoSillas.addSilla(silla);
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
				daoSillas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza la silla de la base de datos.
	 * @param silla Silla con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateSilla(Silla silla) throws Exception {
		DAOTablaSillas daoSillas = new DAOTablaSillas();
		try {
			this.conn = darConexion();
			daoSillas.setConnection(conn);
			daoSillas.updateSilla(silla);
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
				daoSillas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina la silla de la base de datos.
	 * @param silla Silla a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteSilla(Silla silla) throws Exception {
		DAOTablaSillas daoSillas = new DAOTablaSillas();
		try {
			this.conn = darConexion();
			daoSillas.setConnection(conn);
			daoSillas.deleteSilla(silla);
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
				daoSillas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Da el id registrado.
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public int getLastId() throws Exception {
		DAOTablaSillas daoSillas = new DAOTablaSillas();
		try {
			this.conn = darConexion();
			daoSillas.setConnection(conn);
			return daoSillas.getLastId();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSillas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
