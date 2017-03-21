package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Usuario;

public class DAOTablaUsuarios {

	/**
	 * Arraylist de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Constructor DAO usuarios.
	 */
	public DAOTablaUsuarios() {
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
	 * Da los usuarios que hay en la base de datos
	 * @return Lista con los usuarios.
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error conviertiendo los datos a lista.
	 */
	public ArrayList<Usuario> darUsuarios() throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "SELECT * FROM ISIS2304B221710.USUARIOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			String nombre = rs.getString("NOMBRE");
			int identificacion = Integer.parseInt(rs.getString("IDENTIFICACION"));
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			int idPreferencia = Integer.parseInt(rs.getString("ID_PREFERENCIA"));
			usuarios.add(new Usuario(id, nombre, identificacion, correo, rol, idPreferencia)
					);
		}
		return usuarios;
	}

	/**
	 * Agrega un nuevo usuario a la base de datos
	 * @param usuario Nuevo usuario
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error conviertiendo los datos a usuario.
	 */
	public void addUsuario(Usuario usuario) throws SQLException, Exception {
		String sql = "INSERT INTO ISIS2304B221710.USUARIOS VALUES (?,?,?,?,?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setInt(1, usuario.getId());
		prepStmt.setString(2, usuario.getNombre());
		prepStmt.setInt(3, usuario.getIdentificacion());
		prepStmt.setString(4, usuario.getCorreo());
		prepStmt.setString(5, usuario.getRol());
		prepStmt.setInt(6, usuario.getIdPreferencia());

		System.out.println("SQL stmt:" + sql);

		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Actualiza un usuario
	 * @param usuario Usuario que contiene los nuevos datos.
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error conviertiendo los datos a usuario.
	 */
	public void updateUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B221710.USUARIOS SET nombre = ?, identificacion = ?, correo = ?, rol = ?, id_preferencia = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setString(1, usuario.getNombre());
		prepStmt.setInt(2, usuario.getIdentificacion());
		prepStmt.setString(3, usuario.getCorreo());
		prepStmt.setString(4, usuario.getRol());
		prepStmt.setInt(5, usuario.getIdPreferencia());
		prepStmt.setInt(6, usuario.getId());

		System.out.println("SQL stmt:" + sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Elimina un usuario de la base de datos
	 * @param usuario Usuario que se va a eliminar.
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error conviertiendo los datos a usuario.
	 */
	public void deleteUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B221710.USUARIOS";
		sql += " WHERE id = " + usuario.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
