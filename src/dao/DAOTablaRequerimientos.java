package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.RequerimientoTecnico;

public class DAOTablaRequerimientos {

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
	public DAOTablaRequerimientos() {
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
	 * Da la lista de requerimientos de la base de datos
	 * @return Lista de requerimientos de la base de datos
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay un error conviertiendo de dato a lista de requerimientos
	 */
	public ArrayList<RequerimientoTecnico> darRequerimientos() throws SQLException, Exception {
		ArrayList<RequerimientoTecnico> requerimientos = new ArrayList<RequerimientoTecnico>();

		String sql = "SELECT * FROM FESTIVANDES.REQUERIMIENTOS_TECNICOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			String nombre = rs.getString("NOMBRE");
					
			requerimientos.add(new RequerimientoTecnico(id, nombre));
		}
		return requerimientos;
	}

	/**
	 * Agrega un requerimiento a la base de datos.
	 * @param requerimiento Requerimiento a agregar
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay un error conviertiendo de dato a requerimiento
	 */
	public void addRequerimiento(RequerimientoTecnico requerimiento) throws SQLException, Exception {

		String sql = "INSERT INTO FESTIVANDES.REQUERIMIENTOS_TECNICOS VALUES (?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		prepStmt.setInt(1, requerimiento.getId());
		prepStmt.setString(2, requerimiento.getNombre());

		System.out.println("SQL stmt:" + sql);

		
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Actualiza un requerimiento de la base de datos
	 * @param requerimiento Requerimiento que tiene los nuevos datos
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 * @throws Exception Si hay un error al convertir el dato a requerimiento
	 */
	public void updateRequerimiento(RequerimientoTecnico requerimiento) throws SQLException, Exception {

		String sql = "UPDATE FESTIVANDES.REQUERIMIENTOS_TECNICOS SET nombre = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setString(1, requerimiento.getNombre());
		prepStmt.setInt(2, requerimiento.getId());

		System.out.println("SQL stmt:" + sql);

		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Elimina un requerimiento de la base de datos.
	 * @param requerimiento Requerimiento a eliminar de la base de datos
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a base de datos.
	 */
	public void deleteRequerimiento(RequerimientoTecnico requerimiento) throws SQLException, Exception {

		String sql = "DELETE FROM FESTIVANDES.REQUERIMIENTOS_TECNICOS";
		sql += " WHERE id = " + requerimiento.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
