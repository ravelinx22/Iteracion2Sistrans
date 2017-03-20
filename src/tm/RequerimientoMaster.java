package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import dao.DAOTablaRequerimientos;
import vos.ListaRequerimientosTenicos;
import vos.RequerimientoTecnico;

public class RequerimientoMaster {

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
	public RequerimientoMaster(String contextPath) {
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
	 * Da los requerimientos en la base de datos
	 * @return Lista de requerimientos con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaRequerimientosTenicos darRequerimientos() throws Exception {
		ArrayList<RequerimientoTecnico> requerimientos;
		DAOTablaRequerimientos daoRequerimientos = new DAOTablaRequerimientos();
		
		try {
			this.conn = darConexion();
			daoRequerimientos.setConnection(conn);
			requerimientos = daoRequerimientos.darRequerimientos();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRequerimientos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaRequerimientosTenicos(requerimientos);
	}
	
	/**
	 * Agrega un requerimiento a la base de datos
	 * @param requerimiento Requerimiento a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addRequerimiento(RequerimientoTecnico requerimiento) throws Exception {
		DAOTablaRequerimientos daoRequerimientos = new DAOTablaRequerimientos();
		try {
			this.conn = darConexion();
			daoRequerimientos.setConnection(conn);
			daoRequerimientos.addRequerimiento(requerimiento);
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRequerimientos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza el requerimiento de la base de datos.
	 * @param requerimiento Requerimiento con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateRequerimiento(RequerimientoTecnico requerimiento) throws Exception {
		DAOTablaRequerimientos daoRequerimientos = new DAOTablaRequerimientos();
		try {
			this.conn = darConexion();
			daoRequerimientos.setConnection(conn);
			daoRequerimientos.updateRequerimiento(requerimiento);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRequerimientos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina el requerimiento de la base de datos.
	 * @param requerimiento Requerimiento a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteRequerimiento(RequerimientoTecnico requerimiento) throws Exception {
		DAOTablaRequerimientos daoRequerimientos = new DAOTablaRequerimientos();
		try {
			this.conn = darConexion();
			daoRequerimientos.setConnection(conn);
			daoRequerimientos.deleteRequerimiento(requerimiento);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRequerimientos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
