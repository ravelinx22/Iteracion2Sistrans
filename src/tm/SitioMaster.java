package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import dao.DAOTablaSitios;
import dao.DAOTablaUsuarios;
import vos.ListaSitios;
import vos.Sitio;
import vos.Usuario;

public class SitioMaster {

	/**
	 * Constante que contiene el path absoluto del archivo que tiene los datos de la conexión
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";
	
	/**
	 * Atributo que contiene el path absoluto del archivo que tiene los datos de la conexión
	 */
	private String connectionDataPath;
	
	/**
	 * Usuario de la base de datos.	
	 */
	private String user;
	
	/**
	 * Contraseña del usuario para conectarse a la base de datos.
	 */
	private String password;
	
	/**
	 * Url para conectarse a la base de datos.
	 */
	private String url;
	
	/**
	 * Driver que guarda los datos para conectarse a la base de datos.
	 */
	private String driver;
	
	/**
	 * Conexion a la base de datos.
	 */
	private Connection  conn;
	
	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public SitioMaster(String contextPath) {
		this.connectionDataPath = contextPath + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
	}
	
	/**
	 * Inicializa los datos para conectarse con la base datos.
	 */
	private void initConnectionData() {
		try {
			File file = new File(this.connectionDataPath);
			Properties properties = new Properties();
			FileInputStream fi = new FileInputStream(file);
			properties.load(fi);
			fi.close();
			this.url = properties.getProperty("url");
			this.user = properties.getProperty("usuario");
			this.password = properties.getProperty("clave");
			this.driver = properties.getProperty("driver");
			Class.forName(this.driver);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Da la conexion creada con el usuario, clave y url asociados.
	 * @return Conexion creada con el usuario, clave y url asociados.
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 */
	public Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
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
