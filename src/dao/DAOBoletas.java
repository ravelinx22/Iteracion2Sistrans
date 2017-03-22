package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Boleta;

public class DAOBoletas {
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
	public DAOBoletas() {
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
	 * Da una lista de boletas que estan en la base de datos.
	 * @return Lista de boletas en la base de datos.
	 * @throws SQLException Si hay problemas conectandose con la base de datos
	 * @throws Exception Si no se puede convertir los datos a boletas 
	 */
	public ArrayList<Boleta> darBoletas() throws SQLException, Exception {
		ArrayList<Boleta> boletas = new ArrayList<Boleta>();

		String sql = "SELECT * FROM ISIS2304B221710.BOLETAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			int id_fundacion = Integer.parseInt(rs.getString("ID_FUNDACION"));
			int id_usuario = Integer.parseInt(rs.getString("ID_USUARIO"));
			int id_localidad = Integer.parseInt(rs.getString("ID_LOCALIDAD"));
			int id_silla = Integer.parseInt(rs.getString("ID_SILLA"));
			
			boletas.add(new Boleta(id, id_fundacion, id_usuario, id_localidad, id_silla));
		}
		return boletas;
	}

	/**
	 * Agrega una boleta a la base de datos
	 * @param bolta Boleta a agregar
	 * @throws SQLException Si hay error conectandose a la base de datos
	 * @throws Exception Si hay error convirtiendo de dato a boleta
	 */
	public void addBoleta(Boleta boleta) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B221710.BOLETAS VALUES (?,?,?,?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, boleta.getId());
		prepStmt.setInt(2, boleta.getId_fundacion());
		prepStmt.setInt(3, boleta.getId_usuario());
		prepStmt.setInt(4, boleta.getId_localidad());
		prepStmt.setInt(5, boleta.getId_silla());
		
		System.out.println("SQL stmt:" + sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Actualiza una boleta
	 * @param boleta Boleta con los nuevos datos
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error convirtiendo de dato a boleta
	 */
	public void updateBoleta(Boleta boleta) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B221710.COMPAÑIAS SET id_fundacion = ?,id_usuario = ?, id_localidad = ?, id_silla = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setInt(1, boleta.getId_fundacion());
		prepStmt.setInt(2, boleta.getId_usuario());
		prepStmt.setInt(3, boleta.getId_localidad());
		prepStmt.setInt(4, boleta.getId_silla());
		prepStmt.setInt(5, boleta.getId());

		System.out.println("SQL stmt:" + sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Elimina una compañia de la base de datos
	 * @param compañia Compañia a eliminar de la base de datos
	 * @throws SQLException Si hay un error conectandose a la base de datos.
	 * @throws Exception Si hay un error convirtiendo de dato a compañia
	 */
	public void deleteBoleta(Boleta boleta) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B221710.BOLETAS";
		sql += " WHERE id = " + boleta.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
