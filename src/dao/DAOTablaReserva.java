package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Reserva;

public class DAOTablaReserva {
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
	public DAOTablaReserva() {
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
	 * Da una lista de reservas
	 * @return Lista de reservas
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a lista de reservas.
	 */
	public ArrayList<Reserva> darReservas() throws SQLException, Exception {
		ArrayList<Reserva> reservas = new ArrayList<Reserva>();

		String sql = "SELECT * FROM FESTIVANDES.RESERVAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			//TODO: Arreglar esta fecha
			Date fecha =  new Date(222);
			int horaReserva = Integer.parseInt(rs.getString("HORA_RESERVA"));
			int idSitio = Integer.parseInt(rs.getString("ID_SITIO"));
					
			reservas.add(new Reserva(id, fecha, horaReserva, idSitio));
		}
		return reservas;
	}

	/**
	 * Agrega una reserva a la base de datos.
	 * @param reserva Reserva a agregar 
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a reserva.
	 */
	public void addReserva(Reserva reserva) throws SQLException, Exception {

		String sql = "INSERT INTO FESTIVANDES.RESERVAS VALUES (";
		sql += reserva.getId() + ",'";
		sql += reserva.getFecha() + "',";
		sql += reserva.getHoraReserva() + ",";
		sql += reserva.getIdSitio() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Actualiza una reserva.
	 * @param reserva Reserva con los nuevos datos.
	 * @throws SQLException Si hay un error conectandose a la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a reserva.
	 */
	public void updateReserva(Reserva reserva) throws SQLException, Exception {

		String sql = "UPDATE FESTIVANDES.RESERVAS SET ";
		sql += "fecha='" + reserva.getFecha() + "',";
		sql += "hora_reserva=" +reserva.getHoraReserva() + ",";
		sql += "id_sitio=" + reserva.getIdSitio();
		sql += " WHERE id = " + reserva.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Elimina una reserva de la base de datos.
	 * @param reserva Reserva que se va a eliminar de la base de datos.
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a reserva.
	 */
	public void deleteReserva(Reserva reserva) throws SQLException, Exception {

		String sql = "DELETE FROM FESTIVANDES.RESERVAS";
		sql += " WHERE id = " + reserva.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
