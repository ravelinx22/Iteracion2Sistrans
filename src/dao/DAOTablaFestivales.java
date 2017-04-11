package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Festival;

public class DAOTablaFestivales {
	/**
	 * Arraylist de festivales que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Constructor DAO festivales.
	 */
	public DAOTablaFestivales() {
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
	 * Da una lista con todas los festivales en la base de datos.
	 * @return Lista con los festivales en la base de datos.
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a festival.
	 */
	public ArrayList<Festival> darFestivales() throws SQLException, Exception {
		ArrayList<Festival> festivales = new ArrayList<Festival>();

		String sql = "SELECT * FROM ISIS2304B221710.FESTIVALES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			Date fecha_inicio = rs.getDate("FECHA_INICIO");
			Date fecha_final = rs.getDate("FECHA_FINAL");
			String nombre = rs.getString("NOMBRE");
			
			festivales.add(new Festival(id, fecha_inicio, fecha_final, nombre));
		}
		return festivales;
	}
	
	/**
	 * Busca un festival por id.
	 * @param id Id del festival a buscar
	 * @return Festival que tiene el id igual al parametro, null de lo contrario.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error al convertir de datos a festival.
	 */
	public Festival darFestival(int id) throws SQLException, Exception {
		String sql = "SELECT * FROM ISIS2304B221710.FESTIVALES WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
			return null;
		
		Date fecha_inicio = rs.getDate("FECHA_INICIO");
		Date fecha_final = rs.getDate("FECHA_FINAL");
		String nombre = rs.getString("NOMBRE");

		Festival fes = new Festival(id, fecha_inicio, fecha_final, nombre);
		
		return fes;
	}

	/**
	 * Agrega un festival a la base de datos
	 * @param festival Festival que tiene los datos a agregar
	 * @throws SQLException Si hay error conectandose a la base de datos.
	 * @throws Exception Si hay en error al convertir de dato a festival.
	 */
	public void addFestival(Festival festival) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B221710.FESTIVALES VALUES (?,?,?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setInt(1, festival.getId());
		prepStmt.setDate(2, festival.getFecha_inicio());
		prepStmt.setDate(3, festival.getFecha_final());
		prepStmt.setString(4, festival.getNombre());

		System.out.println("SQL stmt:" + sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Actualiza un festival que esta en la base de datos
	 * @param festival Festival que contiene los nuevos datos.
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay enrror convirtiendo el dato a festival.
	 */
	public void updateFestival(Festival festival) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B221710.FESTIVALES SET fecha_inicio = ?, fecha_final = ?, nombre = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setDate(1, festival.getFecha_inicio());
		prepStmt.setDate(2, festival.getFecha_final());
		prepStmt.setString(3, festival.getNombre());
		prepStmt.setInt(5, festival.getId());

		System.out.println("SQL stmt:" + sql);

		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Elimina un festival de la base de datos.
	 * @param festival Festival que se quiere eliminar
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error conviertiendo de dato a festival.
	 */
	public void deleteFestival(Festival festival) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B221710.FESTIVALES";
		sql += " WHERE id = " + festival.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
