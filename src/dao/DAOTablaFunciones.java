package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Espectaculo;
import vos.Funcion;

public class DAOTablaFunciones {

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
	public DAOTablaFunciones() {
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
	 * Da una lista de funciones que estan en la base de datos
	 * @return Lista de funciones que esta en la base de datos.
	 * @throws SQLException Si hay un error conectandose con la base de datos
	 * @throws Exception Si hay un error conviertiendo de dato a funcion
	 */
	public ArrayList<Funcion> darFunciones() throws SQLException, Exception {
		ArrayList<Funcion> funciones = new ArrayList<Funcion>();

		String sql = "SELECT * FROM FESTIVANDES.FUNCIONES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			//TODO: Arreglar fecha
			Date fecha = new Date(222);
			int horaInicio = Integer.parseInt(rs.getString("HORA_INICO"));
			int boletasDisponibles = Integer.parseInt(rs.getString("BOLETAS_DISPONIBLES"));
			int boletasTotales = Integer.parseInt(rs.getString("BOLETAS_TOTALES"));
			int idReserva = Integer.parseInt(rs.getString("ID_RESERVA"));

			funciones.add(new Funcion(id, fecha, horaInicio, boletasDisponibles, boletasTotales, idReserva));
		}
		return funciones;
	}

	/**
	 * Agrega una funcion a la base de datos
	 * @param funcion Funcion a agregar
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay un error conviertiendo de dato a funcion.
	 */
	public void addFuncion(Funcion funcion) throws SQLException, Exception {

		String sql = "INSERT INTO FESTIVANDES.FUNCIONES VALUES (";
		sql += funcion.getId() + ",'";
		sql += funcion.getFecha() + "',";
		sql += funcion.getHoraInicio() + ",";
		sql += funcion.getBoletasDisponibles() + ",";
		sql += funcion.getBoletasTotales() + ",";
		sql += funcion.getIdReserva() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Actualiza una funcion de la base de datos.
	 * @param funcion Funcion con los nuevos datos
	 * @throws SQLException Si hay un error al connectarse con la base de datos.
	 * @throws Exception Si hay un error al convertir un dato a funcion
	 */
	public void updateFuncion(Funcion funcion) throws SQLException, Exception {

		String sql = "UPDATE FESTIVANDES.FUNCIONES SET ";
		sql += "fecha='" + funcion.getFecha() + "',";
		sql += "hora_inicio=" + funcion.getHoraInicio() + ",";
		sql += "boletas_disponibles=" + funcion.getBoletasDisponibles() + ",";
		sql += "boletas_totales=" + funcion.getBoletasTotales() + ",";
		sql += "id_reserva=" + funcion.getIdReserva();
		sql += " WHERE id = " + funcion.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Elimina una funcion de la base de datos.
	 * @param funcion Funcion a eliminar
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 * @throws Exception Si hay un error al convertir un dato a funcion.
	 */
	public void deleteFuncion(Funcion funcion) throws SQLException, Exception {

		String sql = "DELETE FROM FESTIVANDES.FUNCIONES";
		sql += " WHERE id = " + funcion.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
