package tm;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DAOTablaLocalidades;
import vos.ListaLocalidades;
import vos.Localidad;

public class LocalidadMaster extends FestivAndesMaster {

	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public LocalidadMaster(String contextPath) {
		super(contextPath);
	}

	// Transacciones
	
	/**
	 * Da las localidades en la base de datos
	 * @return Lista de localidades con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaLocalidades darLocalidades() throws Exception {
		ArrayList<Localidad> localidades;
		DAOTablaLocalidades daoLocalidades = new DAOTablaLocalidades();
		
		try {
			this.conn = darConexion();
			daoLocalidades.setConnection(conn);
			localidades = daoLocalidades.darLocalidades();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoLocalidades.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaLocalidades(localidades);
	}
	
	/**
	 * Agrega una localidad a la base de datos
	 * @param localidad Localidad a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addLocalidad(Localidad localidad) throws Exception {
		DAOTablaLocalidades daoLocalidades = new DAOTablaLocalidades();
		try {
			this.conn = darConexion();
			daoLocalidades.setConnection(conn);
			daoLocalidades.addLocalidad(localidad);
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
				daoLocalidades.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza la localidad de la base de datos.
	 * @param localidad Localidad con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateLocalidad(Localidad localidad) throws Exception {
		DAOTablaLocalidades daoLocalidades = new DAOTablaLocalidades();
		try {
			this.conn = darConexion();
			daoLocalidades.setConnection(conn);
			daoLocalidades.updateLocalidad(localidad);
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
				daoLocalidades.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina la localidad de la base de datos.
	 * @param localidad Localidad a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteLocalidad(Localidad localidad) throws Exception {
		DAOTablaLocalidades daoLocalidades = new DAOTablaLocalidades();
		try {
			this.conn = darConexion();
			daoLocalidades.setConnection(conn);
			daoLocalidades.deleteLocalidad(localidad);
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
				daoLocalidades.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Da la ultima fila usada.
	 * @param id_localidad Id de la localidad
	 * @return Numero de la ultima fila
	 */
	public int getLastRow(int id_localidad) throws Exception {
		DAOTablaLocalidades daoLocalidades = new DAOTablaLocalidades();
		try {
			this.conn = darConexion();
			daoLocalidades.setConnection(conn);
			return daoLocalidades.getLastRow(id_localidad);
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
				daoLocalidades.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
