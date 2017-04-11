package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import vos.Funcion;
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
			usuarios.add(new Usuario(id, nombre, identificacion, correo, rol, idPreferencia));
		}
		return usuarios;
	}

	/**
	 * Busca un usuario por id.
	 * @param id Id del usuario a buscar
	 * @return Usuario que tiene el id igual al parametro, null de lo contrario.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error al convertir de datos a usuario.
	 */
	public Usuario darUsuario(int id) throws SQLException, Exception {
		String sql = "SELECT * FROM ISIS2304B221710.USUARIOS WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(!rs.next())
			return null;

		String nombre = rs.getString("NOMBRE");
		int identificacion = Integer.parseInt(rs.getString("IDENTIFICACION"));
		String correo = rs.getString("CORREO");
		String rol = rs.getString("ROL");
		int idPreferencia = Integer.parseInt(rs.getString("ID_PREFERENCIA"));
		Usuario us = new Usuario(id, nombre, identificacion, correo, rol, idPreferencia);

		return us;
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

	//TODO RF7
	public void cambiarPreferencia(int idnuevaPreferencia, int id)  throws SQLException , Exception
	{
		String sql = "UPDATE ISIS2304B221710.USUARIOS SET id_preferencia = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, idnuevaPreferencia);
		prepStmt.setInt(2, id);

		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	// TODO RF9
	public void registrarEspectaculoTerminado(Usuario usuario, int idEspectaculo) throws SQLException , Exception
	{
		if(usuario.getRol().equals("festivAndes"))
		{
			String sql = "UPDATE ISIS2304B221710.ESPECTACULO  SET nombre = ?, duracion = ?, idioma = ?, costo = ?, descripcion = ? , publico_objetivo = ?, genero = ? WHERE id = ?";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			prepStmt.setString(1, "TERMINADO");
			prepStmt.setInt(2,0);
			prepStmt.setString(3, "TERMINADO");
			prepStmt.setDouble(4, 0);
			prepStmt.setString(5, "TERMINADO");
			prepStmt.setString(6, "TERMINADO");
			prepStmt.setString(7, "TERMINADO");
			prepStmt.setInt(8, idEspectaculo);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}
	//TODO rfc1 corregir;
	public ArrayList<Funcion> consultarEspectaculos(Usuario usuario , int pfechaInicial, int pFechaFinal, String parametroParaOrdenar) throws SQLException , Exception
	{



		ArrayList<Funcion> funciones = new ArrayList<Funcion>();


		String sql = "SELECT FROM ISIS2304B221710.ESPECTACULO FULL OUTER JOIN ISIS2304B221710.FUNCION ON ISIS2304B221710.ESPECTACULO.id = ISIS2304B221710.FUNCION.espectaculo_id ";
		sql += "WHERE fecha > ? AND WHERE fecha < ? ORDER BY ?" ;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, pfechaInicial);
		prepStmt.setInt(2, pFechaFinal);
		prepStmt.setString(3, parametroParaOrdenar);
		System.out.println("SQL stmt:" + sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			int fecha = Integer.parseInt(rs.getString("FECHA"));
			int horaInicio = Integer.parseInt(rs.getString("HORAINICIO"));
			int boletasTotales = Integer.parseInt(rs.getString("BOLETASTOTALES"));
			int idReserva = Integer.parseInt(rs.getString("RESERVA_ID"));
			int idEspectaculo = Integer.parseInt(rs.getString("ESPECTACULO_ID"));
			int idFestival = Integer.parseInt(rs.getString("FESTIVAL_ID"));
			SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = (Date) originalFormat.parse(fecha + "");
			funciones.add(new Funcion(id, date, horaInicio, boletasTotales, idReserva, idEspectaculo, idFestival));
		}

		return funciones;
	}
}

