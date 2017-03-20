package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import dao.DAOTablaCompañias;
import vos.Compañia;
import vos.ListaCompañias;

public class CompañiaMaster {
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
	public CompañiaMaster(String contextPath) {
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
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
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
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
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
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
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
