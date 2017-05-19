package tm;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.DAOTablaAbonos;
import vos.Abono;
import vos.ListaAbonos;

public class AbonoMaster extends FestivAndesMaster {
	
	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public AbonoMaster(String contextPath) {
		super(contextPath);
	}

	// Transacciones
	
	/**
	 * Da los abonos en la base de datos
	 * @return Lista de abonos con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaAbonos darAbonos() throws Exception {
		ArrayList<Abono> abonos;
		DAOTablaAbonos daoAbonos = new DAOTablaAbonos();
		
		try {
			this.conn = darConexion();
			daoAbonos.setConnection(conn);
			abonos = daoAbonos.darAbonos();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAbonos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaAbonos(abonos);
	}
	
	/**
	 * Dar el abono con id
	 * @param id Id del abono
	 * @return Abono con el id que entra por parametro
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public Abono darAbono(int id) throws Exception {
		DAOTablaAbonos daoAbonos = new DAOTablaAbonos();
		Abono abono;
		try {
			this.conn = darConexion();
			daoAbonos.setConnection(conn);
			abono = daoAbonos.darAbono(id);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoAbonos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return abono;
	}
	
	/**
	 * Agrega un abono a la base de datos
	 * @param abono Abono a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addAbono(Abono abono) throws Exception {
		DAOTablaAbonos daoAbonos = new DAOTablaAbonos();
		try {
			this.conn = darConexion();
			comienzoTransaccion(this.conn);
			
			daoAbonos.setConnection(conn);
			daoAbonos.addAbono(abono);
			
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
				daoAbonos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza un abono de la base de datos.
	 * @param abono Abono con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateAbono(Abono abono) throws Exception {
		DAOTablaAbonos daoAbonos = new DAOTablaAbonos();
		try {
			this.conn = darConexion();
			daoAbonos.setConnection(conn);
			daoAbonos.updateAbono(abono);
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
				daoAbonos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina un abono de la base de datos.
	 * @param abono Abono a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteAbono(Abono abono, Date fechaEliminacion) throws Exception {
		DAOTablaAbonos daoAbonos = new DAOTablaAbonos();
		try {
			this.conn = darConexion();
			daoAbonos.setConnection(conn);
			daoAbonos.deleteAbono(abono, fechaEliminacion);
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
				daoAbonos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
