package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Localidad;
import vos.Usuario;

public class DAOTablaLocalidades {
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
	public DAOTablaLocalidades() {
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
	 * Da la lista con las localidades en la base de datos
	 * @return Lista con las localidades registradas. 
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a localidad.
	 */
	public ArrayList<Localidad> darLocalidades() throws SQLException, Exception {
		ArrayList<Localidad> localidades = new ArrayList<Localidad>();

		String sql = "SELECT * FROM ISIS2304B221710.LOCALIDADES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			String nombre = rs.getString("NOMBRE");
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
			int idSitio = Integer.parseInt(rs.getString("ID_SITIO"));
			double costo = Double.parseDouble(rs.getString("COSTO"));
			localidades.add(new Localidad(id, capacidad, nombre,  idSitio, costo));
		}
		return localidades;
	}
	
	/**
	 * Busca una localidad por id.
	 * @param id Id de la localidad a buscar
	 * @return Localidad que tiene el id igual al parametro, null de lo contrario.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error al convertir de datos a localidades.
	 */
	public Localidad darLocalidad(int id) throws SQLException, Exception {
		String sql = "SELECT * FROM ISIS2304B221710.LOCALIDADES WHERE ID = ?";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
			return null;
		
		String nombre = rs.getString("NOMBRE");
		int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
		int idSitio = Integer.parseInt(rs.getString("ID_SITIO"));
		double costo = Double.parseDouble(rs.getString("COSTO"));		
		
		Localidad loc = new Localidad(id, capacidad, nombre,  idSitio,costo);
		
		return loc;
	}

	/**
	 * Agrega una localidad a la base de datos.
	 * @param localidad Localidad que se va a agregar
	 * @throws SQLException Si hay un error conectandose con la base de datos
	 * @throws Exception Si hay un error al convertir de dato a localidad
	 */
	public void addLocalidad(Localidad localidad) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B221710.LOCALIDADES VALUES (?,?,?,?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, localidad.getId());
		prepStmt.setString(2, localidad.getNombre());
		prepStmt.setInt(3, localidad.getCapacidad());
		prepStmt.setInt(4, localidad.getIdSitio());
		prepStmt.setDouble(5, localidad.getCosto());
		
		System.out.println("SQL stmt:" + sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Actualiza una localidad de la base de datos
	 * @param localidad Localidad con los nuevos datos.
	 * @throws SQLException Si hay un eror conectandose con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a localidad.
	 */
	public void updateLocalidad(Localidad localidad) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B221710.LOCALIDADES SET nombre = ?, capacidad = ?, id_sitio = ?, costo = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setString(1, localidad.getNombre());
		prepStmt.setInt(2, localidad.getCapacidad());
		prepStmt.setInt(3, localidad.getIdSitio());
		prepStmt.setDouble(4,  localidad.getCosto());

		prepStmt.setInt(5, localidad.getId());

		System.out.println("SQL stmt:" + sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Elimina una localidad de la base de datos.
	 * @param localidad Localidad a eliminar
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a localidad.
	 */
	public void deleteLocalidad(Localidad localidad) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B221710.LOCALIDADES";
		sql += " WHERE id = " + localidad.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
