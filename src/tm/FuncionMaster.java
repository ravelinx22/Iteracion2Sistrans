package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import dao.DAOTablaFunciones;
import vos.Funcion;
import vos.ListaFunciones;

public class FuncionMaster {
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
	public FuncionMaster(String contextPath) {
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
	 * Da las funciones en la base de datos
	 * @return Lista de funciones con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaFunciones darFunciones() throws Exception {
		ArrayList<Funcion> funciones;
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();

		try {
			this.conn = darConexion();
			daoFunciones.setConnection(conn);
			funciones = daoFunciones.darFunciones();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}

		return new ListaFunciones(funciones);
	}

	/**
	 * Agrega una funcion a la base de datos
	 * @param funcion Funcion a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addFuncion(Funcion funcion) throws Exception {
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try {
			this.conn = darConexion();
			daoFunciones.setConnection(conn);
			daoFunciones.addFuncion(funcion);
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * Actualiza la funcion de la base de datos.
	 * @param funcion Funcion con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateFuncion(Funcion funcion) throws Exception {
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try {
			this.conn = darConexion();
			daoFunciones.setConnection(conn);
			daoFunciones.updateFuncion(funcion);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * Elimina la funcion de la base de datos.
	 * @param funcion Funcion a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteFuncion(Funcion funcion) throws Exception {
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try {
			this.conn = darConexion();
			daoFunciones.setConnection(conn);
			daoFunciones.deleteFuncion(funcion);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
