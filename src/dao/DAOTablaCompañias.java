package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Compañia;

public class DAOTablaCompañias {
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
	public DAOTablaCompañias() {
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
	 * Da una lista de compañias que estan en la base de datos.
	 * @return Lista de compañias en la base de datos.
	 * @throws SQLException Si hay problemas conectandose con la base de datos
	 * @throws Exception Si no se puede convertir los datos a compañias 
	 */
	public ArrayList<Compañia> darCompañias() throws SQLException, Exception {
		ArrayList<Compañia> compañias = new ArrayList<Compañia>();

		String sql = "SELECT * FROM ISIS2304B221710.COMPAÑIAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			String nombre = rs.getString("NOMBRE");
			String pais = rs.getString("PAIS");
			String paginaWeb = rs.getString("PAGINA_WEB");
			String nombreRepresentante = rs.getString("NOMBRE_REPRESENTANTE");
			
			compañias.add(new Compañia(id, nombre, pais, paginaWeb, nombreRepresentante));
		}
		return compañias;
	}

	/**
	 * Agrega una compañia a la base de datos
	 * @param compañia Compañia a agregar
	 * @throws SQLException Si hay error conectandose a la base de datos
	 * @throws Exception Si hay error convirtiendo de dato a compañia
	 */
	public void addCompañia(Compañia compañia) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B221710.COMPAÑIAS VALUES (?,?,?,?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, compañia.getId());
		prepStmt.setString(2, compañia.getNombre());
		prepStmt.setString(3, compañia.getPais());
		prepStmt.setString(4, compañia.getPaginaWeb());
		prepStmt.setString(5, compañia.getNombreRepresentante());
		
		System.out.println("SQL stmt:" + sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Actualiza una compañia
	 * @param compañia Compañia con los nuevos datos
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error convirtiendo de dato a compañia
	 */
	public void updateCompañia(Compañia compañia) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B221710.COMPAÑIAS SET nombre = ?,pais = ?, pagina_web = ?, nombre_representante = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setString(1, compañia.getNombre());
		prepStmt.setString(2, compañia.getPais());
		prepStmt.setString(3, compañia.getPaginaWeb());
		prepStmt.setString(4, compañia.getNombreRepresentante());
		prepStmt.setInt(5, compañia.getId());

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
	public void deleteCompañia(Compañia compañia) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B221710.COMPAÑIAS";
		sql += " WHERE id = " + compañia.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	// ITERACION 5
	
	/**
	 * Retira la compañia del festival
	 * @param id_compañia Id de la compañia 
	 * @throws SQLException Si hay problema conectandose con la base de datos.
	 * @throws Exception Si hay un problema manejando los datos
	 */
	public void retirarCompañia(int id_compañia) throws SQLException, Exception {
		DAOTablaFunciones func = new DAOTablaFunciones();
		func.setConnection(this.conn);
		func.deleteFuncionesDeCompañia(id_compañia);
	}
}
