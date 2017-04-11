package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Silla;
import vos.Usuario;

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

		String sql = "SELECT * FROM ISIS2304B221710.SILLAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			int numeroSilla = Integer.parseInt(rs.getString("NUMERO_SILLA"));
			int numeroFila = Integer.parseInt(rs.getString("NUMERO_FILA"));
			int idLocalidad = Integer.parseInt(rs.getString("ID_LOCALIDAD"));

			sillas.add(new Silla(id, numeroSilla, numeroFila, idLocalidad));
		}
		return sillas;
	}
	
	/**
	 * Busca una silla por id.
	 * @param id Id de la silla a buscar
	 * @return Silla que tiene el id igual al parametro, null de lo contrario.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error al convertir de datos a silla.
	 */
	public Silla darSilla(int id) throws SQLException, Exception {
		String sql = "SELECT * FROM ISIS2304B221710.SILLAS WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
			return null;
		
		int numeroSilla = Integer.parseInt(rs.getString("NUMERO_SILLA"));
		int numeroFila = Integer.parseInt(rs.getString("NUMERO_FILA"));
		int idLocalidad = Integer.parseInt(rs.getString("ID_LOCALIDAD"));

		Silla si = new Silla(id, numeroSilla, numeroFila, idLocalidad);
		
		return si;
	}

	/**
	 * Agrega una silla a la base de datos
	 * @param silla Silla que tiene los datos a agregar
	 * @throws SQLException Si hay error conectandose a la base de datos.
	 * @throws Exception Si hay en error al convertir de dato a silla.
	 */
	public void addSilla(Silla silla) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B221710.SILLAS VALUES (?,?,?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setInt(1, silla.getId());
		prepStmt.setInt(2, silla.getNumeroSilla());
		prepStmt.setInt(3, silla.getNumeroFila());
		prepStmt.setInt(4, silla.getIdLocalidad());

		System.out.println("SQL stmt:" + sql);
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

		String sql = "UPDATE ISIS2304B221710.SILLAS SET numero_silla = ?, numero_fila = ?, id_localidad = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setInt(1, silla.getNumeroSilla());
		prepStmt.setInt(2, silla.getNumeroFila());
		prepStmt.setInt(3, silla.getIdLocalidad());
		prepStmt.setInt(4, silla.getId());

		System.out.println("SQL stmt:" + sql);

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

		String sql = "DELETE FROM ISIS2304B221710.SILLAS";
		sql += " WHERE id = " + silla.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Da el ultimo id registrado
	 * @return Ultimo id registrado
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error conviertiendo de dato a silla.
	 */
	public int getLastId( ) throws SQLException, Exception {
		String sql = "SELECT max(ID) AS MAX FROM ISIS2304B221710.SILLAS";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		System.out.println("SQL stmt:" + sql);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
			return 0;
		
		String x = rs.getString("MAX");
		int max = (x == null) ? 0 : Integer.parseInt(x);
		return max;
	}
}
