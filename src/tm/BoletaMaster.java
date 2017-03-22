package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import dao.DAOBoletas;
import dao.DAOTablaCompañias;
import vos.Boleta;
import vos.Compañia;
import vos.ListaBoletas;
import vos.ListaCompañias;

public class BoletaMaster {
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
	public BoletaMaster(String contextPath) {
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
	 * Da las boletas en la base de datos
	 * @return Lista de compañias con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaBoletas darBoletas() throws Exception {
		ArrayList<Boleta> boletas;
		DAOBoletas daoBoletas = new DAOBoletas();
		
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
		DAOBoletas daoBoletas = new DAOBoletas();
		try {
			this.conn = darConexion();
			daoBoletas.setConnection(conn);
			daoBoletas.addBoleta(boleta);
			conn.commit();
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
	}
	
	/**
	 * Actualiza la boleta de la base de datos.
	 * @param boleta Boleta con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateBoleta(Boleta boleta) throws Exception {
		DAOBoletas daoBoletas = new DAOBoletas();
		try {
			this.conn = darConexion();
			daoBoletas.setConnection(conn);
			daoBoletas.updateBoleta(boleta);
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
	}
	
	/**
	 * Elimina la boleta de la base de datos.
	 * @param boleta Boleta a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteBoleta(Boleta boleta) throws Exception {
		DAOBoletas daoBoletas = new DAOBoletas();
		try {
			this.conn = darConexion();
			daoBoletas.setConnection(conn);
			daoBoletas.deleteBoleta(boleta);
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
	}
}
