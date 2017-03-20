package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Silla;

public class DAOTablaSillas {
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
	public DAOTablaSillas() {
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
	 * Da una lista con todas las sillas en la base de datos.
	 * @return Lista con las sillas en la base de datos.
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a silla.
	 */
	public ArrayList<Silla> darSillas() throws SQLException, Exception {
		ArrayList<Silla> sillas = new ArrayList<Silla>();

		String sql = "SELECT * FROM FESTIVANDES.SILLAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			int numeroSilla = Integer.parseInt(rs.getString("NUMERO_SILLA"));
			int numeroFila = Integer.parseInt(rs.getString("NUMERO_FILA"));
			boolean ocupado = Boolean.parseBoolean(rs.getString("OCUPADO"));
			int idLocalidad = Integer.parseInt(rs.getString("ID_LOCALIDAD"));

			sillas.add(new Silla(id, numeroSilla, numeroFila, ocupado, idLocalidad));
		}
		return sillas;
	}

	/**
	 * Agrega una silla a la base de datos
	 * @param silla Silla que tiene los datos a agregar
	 * @throws SQLException Si hay error conectandose a la base de datos.
	 * @throws Exception Si hay en error al convertir de dato a silla.
	 */
	public void addSilla(Silla silla) throws SQLException, Exception {

		String sql = "INSERT INTO FESTIVANDES.SILLAS VALUES (";
		sql += silla.getId() + ",";
		sql += silla.getNumeroSilla() + ",";
		sql += silla.getNumeroFila() + ",";
		sql += silla.estaOcupado() + ",";
		sql += silla.getIdLocalidad() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Actualiza una silla que esta en la base de datos
	 * @param silla Silla que contiene los nuevos datos.
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay enrror convirtiendo el dato a silla-
	 */
	public void updateSilla(Silla silla) throws SQLException, Exception {

		String sql = "UPDATE FESTIVANDES.SILLAS SET ";
		sql += "numero_silla=" + silla.getNumeroSilla() + ",";
		sql += "numero_fila=" + silla.getNumeroFila() + ",";
		sql += "ocupado=" + silla.estaOcupado() + ",";
		sql += "id_localidad=" + silla.getIdLocalidad();

		sql += " WHERE id = " + silla.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Elimina una silla de la base de datos.
	 * @param silla Silla que se quiere eliminar
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error conviertiendo de dato a silla.
	 */
	public void deleteSilla(Silla silla) throws SQLException, Exception {

		String sql = "DELETE FROM FESTIVANDES.SILLAS";
		sql += " WHERE id = " + silla.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
