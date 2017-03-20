package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import dao.DAOTablaPreferencias;
import vos.ListaPreferencias;
import vos.Preferencia;

public class PreferenciaMaster {
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
	public PreferenciaMaster(String contextPath) {
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
	 * Da las preferencias en la base de datos
	 * @return Lista de preferencias con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaPreferencias darPreferencias() throws Exception {
		ArrayList<Preferencia> preferencias;
		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		
		try {
			this.conn = darConexion();
			daoPreferencias.setConnection(conn);
			preferencias = daoPreferencias.darPreferencias();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaPreferencias(preferencias);
	}
	
	/**
	 * Agrega una preferencia a la base de datos
	 * @param preferencia Preferencia a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addPreferencia(Preferencia preferencia) throws Exception {
		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		try {
			this.conn = darConexion();
			daoPreferencias.setConnection(conn);
			daoPreferencias.addPreferencia(preferencia);
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza la preferencia de la base de datos.
	 * @param preferencia Preferencia con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updatePreferencia(Preferencia preferencia) throws Exception {
		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		try {
			this.conn = darConexion();
			daoPreferencias.setConnection(conn);
			daoPreferencias.updatePreferencia(preferencia);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina la preferencia de la base de datos.
	 * @param preferencia Preferencia a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deletePreferencia(Preferencia preferencia) throws Exception {
		DAOTablaPreferencias daoPreferencias = new DAOTablaPreferencias();
		try {
			this.conn = darConexion();
			daoPreferencias.setConnection(conn);
			daoPreferencias.deletePreferencia(preferencia);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPreferencias.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
