package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Reserva;
import vos.Usuario;

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

		String sql = "SELECT * FROM ISIS2304B221710.RESERVAS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			Date fecha =  rs.getDate("FECHA");
			int horaReserva = Integer.parseInt(rs.getString("HORA_RESERVA"));
			int idSitio = Integer.parseInt(rs.getString("ID_SITIO"));
			int duracion = Integer.parseInt(rs.getString("DURACION"));
					
			reservas.add(new Reserva(id, fecha, horaReserva, idSitio, duracion));
		}
		return reservas;
	}
	
	/**
	 * Busca una reserva por id.
	 * @param id Id de la reserva a buscar
	 * @return Reserva que tiene el id igual al parametro, null de lo contrario.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error al convertir de datos a reserva.
	 */
	public Reserva darReserva(int id) throws SQLException, Exception {
		String sql = "SELECT * FROM ISIS2304B221710.RESERVAS WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
			return null;
		
		Date fecha = rs.getDate("FECHA");
		int horaReserva = Integer.parseInt(rs.getString("HORA_RESERVA"));
		int idSitio = Integer.parseInt(rs.getString("ID_SITIO"));
		int duracion = Integer.parseInt(rs.getString("DURACION"));
		Reserva res = new Reserva(id, fecha, horaReserva, idSitio, duracion);
		
		return res;
	}

	/**
	 * Agrega una reserva a la base de datos.
	 * @param reserva Reserva a agregar 
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a reserva.
	 */
	public void addReserva(Reserva reserva) throws SQLException, Exception {

		if(!sePuedeReservar(reserva.getIdSitio(), reserva.getFecha(), reserva.getHoraReserva()))
			throw new Exception("El horario no esta disponible, intente otro dia o a otra hora");
		
		String sql = "INSERT INTO ISIS2304B221710.RESERVAS VALUES (?,?,?,?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		prepStmt.setInt(1, reserva.getId());
		prepStmt.setDate(2, reserva.getFecha());
		prepStmt.setInt(3, reserva.getHoraReserva());
		prepStmt.setInt(4, reserva.getIdSitio());
		prepStmt.setInt(5, reserva.getDuracion());

		System.out.println("SQL stmt:" + sql);
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

		String sql = "UPDATE ISIS2304B221710.RESERVAS SET fecha = ?, hora_reserva = ?, id_sitio = ?, duracion = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setDate(1, reserva.getFecha());
		prepStmt.setInt(2, reserva.getHoraReserva());
		prepStmt.setInt(3, reserva.getIdSitio());
		prepStmt.setInt(5, reserva.getDuracion());
		prepStmt.setInt(5, reserva.getId());

		System.out.println("SQL stmt:" + sql);
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

		String sql = "DELETE FROM ISIS2304B221710.RESERVAS";
		sql += " WHERE id = " + reserva.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Verifica si se puede reservar en un sitio especifico a una hora especifica
	 * @param idSitio Id del sitio al que se quiere reservar
	 * @param fecha Fecha del sitio al que se quiere reservas
	 * @param horaInicio Hora de inicio de la funcion
	 * @return True si se puede reservar, false de lo contrario
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error al convertir de dato a reserva.
	 */
	public boolean sePuedeReservar(int idSitio, Date fecha, int horaInicio) throws SQLException, Exception {
		for(Reserva x : darReservas()) {
			int horaFinalReserva = x.getHoraReserva()+x.getDuracion();
			
			if(x.getIdSitio() == idSitio && fecha.toString().equalsIgnoreCase(x.getFecha().toString()) && horaInicio >= x.getHoraReserva() && horaInicio <= horaFinalReserva) {
				return false;
			}
		}
		return true;
	}
}
