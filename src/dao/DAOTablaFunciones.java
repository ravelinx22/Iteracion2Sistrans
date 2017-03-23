package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

		String sql = "SELECT * FROM ISIS2304B221710.FUNCIONES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			Date fecha = rs.getDate("FECHA");
			int horaInicio = Integer.parseInt(rs.getString("HORA_INICIO"));
			int boletasDisponibles = Integer.parseInt(rs.getString("BOLETAS_DISPONIBLES"));
			int boletasTotales = Integer.parseInt(rs.getString("BOLETAS_TOTALES"));
			int idReserva = Integer.parseInt(rs.getString("ID_RESERVA"));
			int idEspectaculo = Integer.parseInt(rs.getString("ID_ESPECTACULO"));

			funciones.add(new Funcion(id, fecha, horaInicio, boletasDisponibles, boletasTotales, idReserva, idEspectaculo));
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

		String sql = "INSERT INTO ISIS2304B221710.FUNCIONES VALUES (?,?,?,?,?,?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		
		DAOTablaSitios daoSitio = new DAOTablaSitios();
		daoSitio.setConnection(conn);
		
		DAOTablaReserva daoReserva = new DAOTablaReserva();
		daoReserva.setConnection(conn);
		
		DAOTablaEspectaculos daoEspectaculo = new DAOTablaEspectaculos();
		daoEspectaculo.setConnection(conn);

		if(!daoSitio.darSitio(daoReserva.darReserva(funcion.getIdReserva()).getIdSitio()).cumpleRequerimientos(daoEspectaculo.darEspectaculo(funcion.getIdEspectaculo()).getRequerimientos()))
			throw new Exception("El sitio no cumple con los requerimientos de la funcion");
		
		prepStmt.setInt(1, funcion.getId());
		prepStmt.setDate(2, funcion.getFecha());
		prepStmt.setInt(3, funcion.getHoraInicio());
		prepStmt.setInt(4, funcion.getBoletasDisponibles());
		prepStmt.setInt(5, funcion.getBoletasTotales());
		prepStmt.setInt(6, funcion.getIdReserva());
		prepStmt.setInt(7, funcion.getIdEspectaculo());
		System.out.println("SQL stmt:" + sql);

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

		String sql = "UPDATE ISIS2304B221710.FUNCIONES SET fecha = ?, hora_inicio = ?, boletas_disponibles = ?, boletas_totales = ?, id_reserva = ?, id_espectaculo = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setDate(1, funcion.getFecha());
		prepStmt.setInt(2, funcion.getHoraInicio());
		prepStmt.setInt(3, funcion.getBoletasDisponibles());
		prepStmt.setInt(4, funcion.getBoletasTotales());
		prepStmt.setInt(5, funcion.getIdReserva());
		prepStmt.setInt(6, funcion.getIdEspectaculo());
		prepStmt.setInt(7, funcion.getId());

		System.out.println("SQL stmt:" + sql);
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

		String sql = "DELETE FROM ISIS2304B221710.FUNCIONES";
		sql += " WHERE id = " + funcion.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
