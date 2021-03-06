package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dao.DAOTablaAbonos;
import dao.DAOTablaCompañias;
import dao.DAOTablaFunciones;
import dao.DAOTablaUsuarios;
import dtm.FestivAndesDistributed;
import jms.NonReplyException;
import vos.Abono;
import vos.Funcion;
import vos.ListaFunciones;
import vos.ListaRentabilidad;

public class FestivAndesMaster {
	/**
	 * Constante que contiene el path absoluto del archivo que tiene los datos de la conexión
	 */
	protected static final String CONNECTION_DATA_FILE_NAME_REMOTE = "conexion.properties";

	/**
	 * Atributo que contiene el path absoluto del archivo que tiene los datos de la conexión
	 */
	protected String connectionDataPath;

	/**
	 * Usuario de la base de datos.	
	 */
	protected String user;

	/**
	 * Contraseña del usuario para conectarse a la base de datos.
	 */
	protected String password;

	/**
	 * Url para conectarse a la base de datos.
	 */
	protected String url;

	/**
	 * Driver que guarda los datos para conectarse a la base de datos.
	 */
	protected String driver;

	/**
	 * Conexion a la base de datos.
	 */
	protected Connection  conn;

	private FestivAndesDistributed dtm;


	/**
	 * Crea un festiv andes master
	 * @param contextPath
	 */
	public FestivAndesMaster(String contextPath) {
		try {
			this.connectionDataPath = contextPath + CONNECTION_DATA_FILE_NAME_REMOTE;
			initConnectionData();
			System.out.println("Instancing DTM...");
			dtm = FestivAndesDistributed.getInstance(this);
			System.out.println("Done!");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Inicializa los datos para conectarse con la base datos.
	 */
	private void initConnectionData() {
		try {
			//			File file = new File(this.connectionDataPath);
			//			Properties properties = new Properties();
			//			FileInputStream fi = new FileInputStream(file);
			//			properties.load(fi);
			//			fi.close();
			this.url = "jdbc:oracle:thin:@fn3.oracle.virtual.uniandes.edu.co:1521:prod";
			this.user = "ISIS2304B221710";
			this.password = "simp37odDLy";
			this.driver = "oracle.jdbc.driver.OracleDriver";
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

	// ITERACION 5

	/** 
	 * Alista la conexion para una transaccion
	 * @param connection Conexion donde se va a realizar la transaccion
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 * @throws Exception Si hay error al manejar los datos
	 */
	public void comienzoTransaccion(Connection connection) throws SQLException, Exception {
		connection.setAutoCommit(false);
		connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
	}

	/**
	 * Reinicia el estado de la conexion una vez se ha realizado la transaccion.
	 * @param connection Conexion donde se va a realizar la transaccion.
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 * @throws Exception Si hay error al manejar los datos
	 */
	public void finalTransaccion(Connection connection) throws SQLException, Exception {
		connection.commit();
		connection.setAutoCommit(true);
		connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
	}

	// Transacciones

	// Dar funciones

	/**
	 * Da las funciones en la base de datos
	 * @return Lista de funciones con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaFunciones darFunciones() throws Exception {
		ListaFunciones remL = darFuncionesLocal();
		try
		{
			ListaFunciones resp = dtm.getRemoteFunciones();
			System.out.println(resp.getFunciones().size());
			remL.getFunciones().addAll(resp.getFunciones());
		}
		catch(NonReplyException e)
		{
			System.out.println(e.getMessage());
		}
		return remL;
	}

	public ListaFunciones darFuncionesLocal() throws Exception {
		List funciones;
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try
		{
			this.conn = darConexion();
			daoFunciones.setConnection(this.conn);
			funciones = daoFunciones.darFunciones();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFunciones(funciones);
	}

	// Dar rentabilidad

	public ListaRentabilidad darRentabilidad(Date fechaInicio, Date fechaFinal, int id_compañia) throws Exception {
		ListaRentabilidad remL = darRentabilidadLocal(fechaInicio, fechaFinal, id_compañia);
		try
		{
			ListaRentabilidad resp = dtm.getRemoteRentabilidad(fechaInicio, fechaFinal, id_compañia);
			System.out.println(resp.getRentabilidad().size());
			remL.getRentabilidad().addAll(resp.getRentabilidad());
		}
		catch(NonReplyException e)
		{
			System.out.println(e.getMessage());
		}
		return remL;
	}

	public ListaRentabilidad darRentabilidadLocal(Date fechaInicio, Date fechaFinal, int id_compañia) throws Exception {
		ListaRentabilidad rentabilidad;
		DAOTablaCompañias daoCompañia = new DAOTablaCompañias();
		try 
		{
			this.conn = darConexion();
			daoCompañia.setConnection(this.conn);
			rentabilidad = daoCompañia.darRentabilidadCompañias(fechaInicio, fechaFinal, id_compañia);
		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCompañia.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rentabilidad;
	}

	// Retirar compañia

	public void retirarCompañia(int id_compañia) throws Exception {
		retirarCompañiaLocal(id_compañia);
		try
		{
			//dtm.retirarCompañiaRemote(id_compañia);
			throw new NonReplyException("");
		}
		catch(NonReplyException e)
		{
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Retira la compañia del festival local
	 * @param id_compañia Id de la compañia
	 * @param id_usuario Id del usuario administrador que va a hacer la transaccion 
	 * @throws SQLException Si hay problema conectandose con la base de datos.
	 * @throws Exception Si hay un problema manejando los datos
	 */
	public void retirarCompañiaLocal(int id_compañia) throws SQLException, Exception {
		DAOTablaCompañias daoCompañia = new DAOTablaCompañias();		
		try {
			this.conn = darConexion();
			daoCompañia.setConnection(conn);	
			// Quitar auto commit 

			daoCompañia.retirarCompañia(id_compañia);			
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
				daoCompañia.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	// Comprar abono

	public void addAbono(Abono abono) throws Exception {
		try
		{
			//dtm.addAbonoRemote(abono);
			addAbonoLocal(abono);
		}
		catch(NonReplyException e)
		{
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Agrega un abono a la base de datos
	 * @param abono Abono a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addAbonoLocal(Abono abono) throws Exception {
		DAOTablaAbonos daoAbonos = new DAOTablaAbonos();
		try {
			this.conn = darConexion();
			daoAbonos.setConnection(conn);
			// Mirar autocommit

			daoAbonos.addAbono(abono);
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
				daoAbonos.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
