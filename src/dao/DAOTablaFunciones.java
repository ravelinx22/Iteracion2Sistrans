package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Boleta;
import vos.Funcion;
import vos.Localidad;
import vos.Reserva;
import vos.Silla;

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

		String sql = "SELECT * FROM ISIS2304B221710.FUNCIONES WHERE ID < 30";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			Date fecha = rs.getDate("FECHA");
			int horaInicio = Integer.parseInt(rs.getString("HORA_INICIO"));
			int boletasTotales = Integer.parseInt(rs.getString("BOLETAS_TOTALES"));
			int idReserva = Integer.parseInt(rs.getString("ID_RESERVA"));
			int idEspectaculo = Integer.parseInt(rs.getString("ID_ESPECTACULO"));
			int idFestival = Integer.parseInt(rs.getString("ID_FESTIVAL"));

			funciones.add(new Funcion(id, fecha, horaInicio, boletasTotales, idReserva, idEspectaculo, idFestival));
		}
		return funciones;
	}

	/**
	 * Busca una funcion por id.
	 * @param id Id de la funcion a buscar
	 * @return Funcion que tiene el id igual al parametro, null de lo contrario.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error al convertir de datos a silla.
	 */
	public Funcion darFuncion(int id) throws SQLException, Exception {
		String sql = "SELECT * FROM ISIS2304B221710.FUNCIONES WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(!rs.next())
			return null;

		Date fecha = rs.getDate("FECHA");
		int horaInicio = Integer.parseInt(rs.getString("HORA_INICIO"));
		int boletasTotales = Integer.parseInt(rs.getString("BOLETAS_TOTALES"));
		int idReserva = Integer.parseInt(rs.getString("ID_RESERVA"));
		int idEspectaculo = Integer.parseInt(rs.getString("ID_ESPECTACULO"));
		int idFestival = Integer.parseInt(rs.getString("ID_FESTIVAL"));

		Funcion fnc = new Funcion(id, fecha, horaInicio, boletasTotales, idReserva, idEspectaculo, idFestival);

		return fnc;
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
		prepStmt.setInt(4, funcion.getBoletasTotales());
		prepStmt.setInt(5, funcion.getIdReserva());
		prepStmt.setInt(6, funcion.getIdEspectaculo());
		prepStmt.setInt(7, funcion.getIdFestival());
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

		String sql = "UPDATE ISIS2304B221710.FUNCIONES SET fecha = ?, hora_inicio = ?, boletas_totales = ?, id_reserva = ?, id_espectaculo = ?, id_festival = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setDate(1, funcion.getFecha());
		prepStmt.setInt(2, funcion.getHoraInicio());
		prepStmt.setInt(3, funcion.getBoletasTotales());
		prepStmt.setInt(4, funcion.getIdReserva());
		prepStmt.setInt(5, funcion.getIdEspectaculo());
		prepStmt.setInt(6, funcion.getIdFestival());
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


	//TODO RFC3
	public void generaReporte(int pid) throws SQLException, Exception 
	{

		String sql = "SELECT reservaISIS2304B221710.LOCALIDAD.nombre as nombre, (COUNT(reservaISIS2304B221710.LOCALIDAD.id))* reservaISIS2304B221710.LOCALIDAD.precio  FROM ((ISIS2304B221710.FUNCIONES FULL OUTER JOIN  ISIS2304B221710.RESERVA on ISIS2304B221710.FUNCIONES.reserva_id = reservaISIS2304B221710.RESERVA.id) FULL OUTER JOIN "
				+ "reservaISIS2304B221710.RESERVA.SITIO on "
				+ "reservaISIS2304B221710.RESERVA.sitio_id = reservaISIS2304B221710.SITIO.id) FULL OUTER JOIN "
				+ "reservaISIS2304B221710.LOCALIDAD on reservaISIS2304B221710.SITIO.id = reservaISIS2304B221710.LOCALIDAD.sitio_id  ";
		sql += " WHERE ISIS2304B221710.FUNCIONES.id = "  + pid + " GROUP BY ( reservaISIS2304B221710.LOCALIDAD.nombre) ";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Da las funciones de un cliente
	 * @param id Id del cliente
	 * @return Lista con las funciones del cliente
	 * @throws SQLException Si hay un error al connectarse con la base de datos.
	 * @throws Exception Si hay un error al convertir un dato a funcion
	 */
	public ArrayList<Funcion> darFuncionesCliente(int id_usuario) throws SQLException, Exception {
		ArrayList<Funcion> funciones = new ArrayList<>();

		String sql = "SELECT y.* FROM (SELECT * FROM BOLETAS WHERE ID_USUARIO = ?) x INNER JOIN FUNCIONES y ON x.ID_FUNCION = y.ID";
		System.out.println("SQL stmt:" + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id_usuario);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			Date fecha = rs.getDate("FECHA");
			int horaInicio = Integer.parseInt(rs.getString("HORA_INICIO"));
			int boletasTotales = Integer.parseInt(rs.getString("BOLETAS_TOTALES"));
			int idReserva = Integer.parseInt(rs.getString("ID_RESERVA"));
			int idEspectaculo = Integer.parseInt(rs.getString("ID_ESPECTACULO"));
			int idFestival = Integer.parseInt(rs.getString("ID_FESTIVAL"));

			funciones.add(new Funcion(id, fecha, horaInicio, boletasTotales, idReserva, idEspectaculo, idFestival));
		}

		return funciones;
	}

	/**
	 * Da las funciones canceladas de un cliente
	 * @param id Id del cliente
	 * @return Lista con las funciones canceladas del cliente
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<Funcion> darFuncionesCanceladasCliente(int id_usuario) throws SQLException, Exception {
		ArrayList<Funcion> funciones = new ArrayList<>();

		String sql = "SELECT y.* FROM (SELECT * FROM BOLETACANCELADA WHERE ID_USUARIO = ?) x INNER JOIN FUNCIONES y ON x.ID_FUNCION = y.ID";
		System.out.println("SQL stmt:" + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id_usuario);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			Date fecha = rs.getDate("FECHA");
			int horaInicio = Integer.parseInt(rs.getString("HORA_INICIO"));
			int boletasTotales = Integer.parseInt(rs.getString("BOLETAS_TOTALES"));
			int idReserva = Integer.parseInt(rs.getString("ID_RESERVA"));
			int idEspectaculo = Integer.parseInt(rs.getString("ID_ESPECTACULO"));
			int idFestival = Integer.parseInt(rs.getString("ID_FESTIVAL"));

			funciones.add(new Funcion(id, fecha, horaInicio, boletasTotales, idReserva, idEspectaculo, idFestival));
		}

		return funciones;	
	}

	// ITERACION 5

	/**
	 * Da las funciones de una compañia en especifico.
	 * @param id_compañia Id de la compañia
	 * @throws SQLException Si hay un error conectandose a la base de datos.
	 * @throws Exception Si hay un error convirtiendo de dato a funcion.
	 */
	public ArrayList<Funcion> darFuncionesDeCompañia(int id_compañia) throws SQLException, Exception {
		String sql = "SELECT y.* FROM (SELECT * FROM COMPAÑIAS x INNER JOIN CONTRIBUIDORES y ON x.ID = y.ID_COMPAÑIA WHERE x.ID = ?) x INNER JOIN FUNCIONES y ON x.ID_ESPECTACULO = y.ID_ESPECTACULO";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id_compañia);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		ArrayList<Funcion> funciones = new ArrayList<Funcion>();
		while(rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			Date fecha = rs.getDate("FECHA");
			int horaInicio = Integer.parseInt(rs.getString("HORA_INICIO"));
			int boletasTotales = Integer.parseInt(rs.getString("BOLETAS_TOTALES"));
			int idReserva = Integer.parseInt(rs.getString("ID_RESERVA"));
			int idEspectaculo = Integer.parseInt(rs.getString("ID_ESPECTACULO"));
			int idFestival = Integer.parseInt(rs.getString("ID_FESTIVAL"));

			funciones.add(new Funcion(id, fecha, horaInicio, boletasTotales, idReserva, idEspectaculo, idFestival));
		}
		
		return funciones;
	}
	
	/**
	 * Elimina una funcion en especifico.
	 * @param id_funcion Id de la funcion a eliminar.
	 * @throws SQLException Si hay un error conectandose a la base de datos.
	 * @throws Exception Si hay un error convirtiendo de dato a funcion.
	 */
	public void deleteFuncion(int id_funcion) throws SQLException, Exception {
		Funcion funcion = darFuncion(id_funcion);
		
		if(funcion == null)
			throw new Exception("La funcion que esta intentando eliminar no existe");
		
		String sql = "DELETE FROM ISIS2304B221710.FUNCIONES WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id_funcion);
		
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Elimina las funciones de una compañia.
	 * @param id_compañia Id de la compañia a eliminar.
	 * @throws SQLException Si hay un error conectandose a la base de datos.
	 * @throws Exception Si hay un error convirtiendo de dato a funcion.
	 */
	public void deleteFuncionesDeCompañia(int id_compañia) throws SQLException, Exception {
		ArrayList<Funcion> funciones = darFuncionesDeCompañia(id_compañia);
		
		for(Funcion f : funciones) {
			deleteFuncion(f.getId());
		}
	}
}
