package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import dao.DAOTablaUsuarios;
import vos.ListaUsuarios;
import vos.Usuario;

public class UsuarioMaster {

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
	public UsuarioMaster(String contextPath) {
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
	 * Da los usuarios en la base de datos
	 * @return Lista de usuarios con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaUsuarios darUsuarios() throws Exception {
		ArrayList<Usuario> usuarios;
		DAOTablaUsuarios daoUsuarios = new DAOTablaUsuarios();
		
		try {
			this.conn = darConexion();
			daoUsuarios.setConnection(conn);
			usuarios = daoUsuarios.darUsuarios();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaUsuarios(usuarios);
	}
	
	/**
	 * Da el usuario que tiene id igual al parametro.
	 * @param id Id del usuario a buscar.
	 * @return Da el usuario que tiene id igual al parametro, null de lo contrario.
	 */
	public Usuario darUsuario(int id) throws SQLException, Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		Usuario us = null;
		try {
			this.conn = darConexion();
			daoUsuario.setConnection(conn);
			us = daoUsuario.darUsuario(id);
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return us;
	}
	
	/**
	 * Agrega un usuario a la base de datos
	 * @param usuario Usuario a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			daoUsuario.setConnection(conn);
			daoUsuario.addUsuario(usuario);
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza el usuario de la base de datos.
	 * @param usuario Usuario con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			daoUsuario.setConnection(conn);
			daoUsuario.updateUsuario(usuario);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina el usuario de la base de datos.
	 * @param usuario Usuario a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuarios daoUsuario = new DAOTablaUsuarios();
		try {
			this.conn = darConexion();
			daoUsuario.setConnection(conn);
			daoUsuario.deleteUsuario(usuario);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
