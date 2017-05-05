package tm;


import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import dao.DAOTablaBoletas;
import vos.Boleta;
import vos.ListaBoletas;

public class BoletaMaster extends FestivAndesMaster {
	
	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public BoletaMaster(String contextPath) {
		super(contextPath);
	}
	
	// Transacciones
	
	/**
	 * Da las boletas en la base de datos
	 * @return Lista de compañias con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaBoletas darBoletas() throws Exception {
		ArrayList<Boleta> boletas;
		DAOTablaBoletas daoBoletas = new DAOTablaBoletas();
		
		try {
			this.conn = darConexion();
			daoBoletas.setConnection(conn);
			boletas = daoBoletas.darBoletas();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBoletas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaBoletas(boletas);
	}
	
	/**
	 * Agrega una boleta a la base de datos
	 * @param boleta Boleta a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addBoleta(Boleta boleta) throws Exception {
		DAOTablaBoletas daoBoletas = new DAOTablaBoletas();
		try {
			this.conn = darConexion();
			daoBoletas.setConnection(conn);
			daoBoletas.addBoleta(boleta);
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
				daoBoletas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza la boleta de la base de datos.
	 * @param boleta Boleta con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateBoleta(Boleta boleta) throws Exception {
		DAOTablaBoletas daoBoletas = new DAOTablaBoletas();
		try {
			this.conn = darConexion();
			daoBoletas.setConnection(conn);
			daoBoletas.updateBoleta(boleta);
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
				daoBoletas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina la boleta de la base de datos.
	 * @param boleta Boleta a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteBoleta(Boleta boleta, Date fecha) throws Exception {
		DAOTablaBoletas daoBoletas = new DAOTablaBoletas();
		try {
			this.conn = darConexion();
			daoBoletas.setConnection(conn);
			daoBoletas.deleteBoleta(boleta, fecha);
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
				daoBoletas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Dar la boleta con id
	 * @param id Id de la boleta
	 * @return Boleta con el id que entra por parametro
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public Boleta darBoleta(int id) throws Exception {
		DAOTablaBoletas daoBoletas = new DAOTablaBoletas();
		Boleta boleta;
		try {
			this.conn = darConexion();
			daoBoletas.setConnection(conn);
			boleta = daoBoletas.darBoleta(id);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoBoletas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return boleta;
	}
}
