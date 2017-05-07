package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class FestivAndesMaster {
	/**
	 * Constante que contiene el path absoluto del archivo que tiene los datos de la conexión
	 */
	protected static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";
	
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
	
	/**
	 * Crea un festiv andes master
	 * @param contextPath
	 */
	public FestivAndesMaster(String contextPath) {
		try {
			//this.conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			this.connectionDataPath = contextPath + CONNECTION_DATA_FILE_NAME_REMOTE;
			initConnectionData();
			this.conn.setAutoCommit(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
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
}
