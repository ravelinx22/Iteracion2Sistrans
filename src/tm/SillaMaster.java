package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import dao.DAOTablaSillas;
import vos.ListaSillas;
import vos.Silla;

public class SillaMaster {

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
	public SillaMaster(String contextPath) {
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
