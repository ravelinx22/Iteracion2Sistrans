package tm;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DAOTablaReserva;
import vos.ListaReservas;
import vos.Reserva;

public class ReservaMaster extends FestivAndesMaster {
	
	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public ReservaMaster(String contextPath) {
		super(contextPath);
	}
	
	// Transacciones
	
	/**
	 * Da las reservas en la base de datos
	 * @return Lista de reservas con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaReservas darReservas() throws Exception {
		ArrayList<Reserva> reservas;
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		
		try {
			this.conn = darConexion();
			daoReservas.setConnection(conn);
			reservas = daoReservas.darReservas();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaReservas(reservas);
	}
	
	/**
	 * Agrega una reserva a la base de datos
	 * @param reserva Reserva a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addReserva(Reserva reserva) throws Exception {
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		try {
			this.conn = darConexion();
			daoReservas.setConnection(conn);
			daoReservas.addReserva(reserva);
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
				daoReservas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza la reserva de la base de datos.
	 * @param reserva Reserva con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateReserva(Reserva reserva) throws Exception {
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		try {
			this.conn = darConexion();
			daoReservas.setConnection(conn);
			daoReservas.updateReserva(reserva);
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
				daoReservas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina la reserva de la base de datos.
	 * @param reserva Reserva a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteReserva(Reserva reserva) throws Exception {
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		try {
			this.conn = darConexion();
			daoReservas.setConnection(conn);
			daoReservas.deleteReserva(reserva);
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
				daoReservas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
