package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Preferencia;

public class DAOTablaPreferencias {

	/**
	 * Arraylist de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Constructor DAO espectaculos.
	 */
	public DAOTablaPreferencias() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Cierra todos los recursos que estan guardados en el arreglo.
	 */
	public void cerrarRecursos(){
		for(Object ob : recursos) {
			if(ob instanceof PreparedStatement) {
				try {
					((PreparedStatement) ob).close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Modifica la conexion de la base datos.
	 * @param conn Nueva conexion a la base de datos.
	 */
	public void setConnection(Connection conn) {
		this.conn = conn;
	}

	// Transacciones
	
	/**
	 * Da una lista de preferencias que hay en la base de datos.
	 * @return Lista de preferencias que hay en la base de datos
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a base de datos.
	 */
	public ArrayList<Preferencia> darPreferencias() throws SQLException, Exception {
		ArrayList<Preferencia> preferencias = new ArrayList<Preferencia>();

		String sql = "SELECT * FROM FESTIVANDES.PREFERENCIAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			String nombre = rs.getString("NOMBRE");
					
			preferencias.add(new Preferencia(id, nombre));
		}
		return preferencias;
	}

	/**
	 * Agrega una preferencia a la base de datos.
	 * @param preferencia Preferencia a agregar a la base de datos.
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 * @throws Exception Si hay un error al covertir de dato a base de datos.
	 */
	public void addPreferencia(Preferencia preferencia) throws SQLException, Exception {

		String sql = "INSERT INTO FESTIVANDES.PREFERENCIAS VALUES (?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, preferencia.getId());
		prepStmt.setString(2, preferencia.getNombre());

		System.out.println("SQL stmt:" + sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Actualiza una preferencia de la base de datos.
	 * @param preferencia Preferencia con los nuevos datos
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a base de datos.
	 */
	public void updatePreferencia(Preferencia preferencia) throws SQLException, Exception {

		String sql = "UPDATE FESTIVANDES.PREFERENCIAS SET nombre = ? WHERE id = ? ";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setString(1, preferencia.getNombre());
		prepStmt.setInt(2, preferencia.getId());

		System.out.println("SQL stmt:" + sql);

		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Elimina una preferencia de la base de datos.
	 * @param preferencia Preferencia a eliminar de la base de datos.
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a preferencia.
	 */
	public void deletePreferencia(Preferencia preferencia) throws SQLException, Exception {

		String sql = "DELETE FROM FESTIVANDES.PREFERENCIAS";
		sql += " WHERE id = " + preferencia.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
