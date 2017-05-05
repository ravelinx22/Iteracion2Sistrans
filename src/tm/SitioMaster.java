package tm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import dao.DAOTablaSitios;
import vos.ListaSitios;
import vos.Sitio;

public class SitioMaster extends FestivAndesMaster {

	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public SitioMaster(String contextPath) {
		super(contextPath);
	}
	
	// Transacciones
	
	/**
	 * Da los sitios en la base de datos
	 * @return Lista de sitios con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaSitios darSitios() throws Exception {
		ArrayList<Sitio> sitios;
		DAOTablaSitios daoSitios = new DAOTablaSitios();
		
		try {
			this.conn = darConexion();
			daoSitios.setConnection(conn);
			sitios = daoSitios.darSitios();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSitios.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaSitios(sitios);
	}
	
	/**
	 * Da el sitio que tiene id igual al parametro.
	 * @param id Id del sitio a buscar.
	 * @return Da el sitio que tiene id igual al parametro, null de lo contrario.
	 */
	public Sitio darSitio(int id) throws SQLException, Exception {
		DAOTablaSitios daoSitio = new DAOTablaSitios();
		Sitio si = null;
		try {
			this.conn = darConexion();
			daoSitio.setConnection(conn);
			si = daoSitio.darSitio(id);
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSitio.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return si;
	}
	
	/**
	 * Agrega un sitio a la base de datos
	 * @param sitio Sitio a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addSitio(Sitio sitio) throws Exception {
		DAOTablaSitios daoSitios = new DAOTablaSitios();
		try {
			this.conn = darConexion();
			daoSitios.setConnection(conn);
			daoSitios.addSitio(sitio);
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
				daoSitios.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza el sitio de la base de datos.
	 * @param sitio Sitio con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateSitio(Sitio sitio) throws Exception {
		DAOTablaSitios daoSitios = new DAOTablaSitios();
		try {
			this.conn = darConexion();
			daoSitios.setConnection(conn);
			daoSitios.updateSitio(sitio);
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
				daoSitios.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina el sitio de la base de datos.
	 * @param sitio Sitio a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteSitio(Sitio sitio) throws Exception {
		DAOTablaSitios daoSitios = new DAOTablaSitios();
		try {
			this.conn = darConexion();
			daoSitios.setConnection(conn);
			daoSitios.deleteSitio(sitio);
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
				daoSitios.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	public ArrayList<Map<String, String>> darInfoSitios(int id, String agrupamiento, String orden, String ordenarPor) throws Exception {
		DAOTablaSitios daoSitios = new DAOTablaSitios();
		ArrayList<Map<String, String>> objects = new ArrayList<>();
		try {
			this.conn = darConexion();
			daoSitios.setConnection(conn);
			ResultSet rs = daoSitios.darInfoSitio(id, agrupamiento, orden, ordenarPor);
			while(rs.next()) {
				Map<String, String> info = new HashMap<>();
				for(int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					String columna = rs.getMetaData().getColumnLabel(i);
					String dato = rs.getString(columna);
					info.put(columna, dato);
				}
				objects.add(info);
			}
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoSitios.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return objects;
	}
}
