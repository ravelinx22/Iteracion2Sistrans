package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import dao.DAOTablaReserva;
import vos.ListaReservas;
import vos.Reserva;

public class ReservaMaster {
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
	public ReservaMaster(String contextPath) {
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
	 * Da las reservas en la base de datos
	 * @return Lista de reservas con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaReservas darReservas() throws Exception {
		ArrayList<Reserva> reservas;
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		
		try {
			this.conn = darConexion();
			daoReservas.setConnection(conn);
			reservas = daoReservas.darReservas();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
		
		return new ListaReservas(reservas);
	}
	
	/**
	 * Agrega una reserva a la base de datos
	 * @param reserva Reserva a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addReserva(Reserva reserva) throws Exception {
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		try {
			this.conn = darConexion();
			daoReservas.setConnection(conn);
			daoReservas.addReserva(reserva);
			conn.commit();
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Actualiza la reserva de la base de datos.
	 * @param reserva Reserva con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateReserva(Reserva reserva) throws Exception {
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		try {
			this.conn = darConexion();
			daoReservas.setConnection(conn);
			daoReservas.updateReserva(reserva);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
	
	/**
	 * Elimina la reserva de la base de datos.
	 * @param reserva Reserva a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteReserva(Reserva reserva) throws Exception {
		DAOTablaReserva daoReservas = new DAOTablaReserva();
		try {
			this.conn = darConexion();
			daoReservas.setConnection(conn);
			daoReservas.deleteReserva(reserva);
		} catch(SQLException e) {
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoReservas.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
