package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import tm.SillaMaster;
import vos.Boleta;

public class DAOTablaBoletas {
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
	public DAOTablaBoletas() {
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
	 * Da una lista de boletas que estan en la base de datos.
	 * @return Lista de boletas en la base de datos.
	 * @throws SQLException Si hay problemas conectandose con la base de datos
	 * @throws Exception Si no se puede convertir los datos a boletas 
	 */
	public ArrayList<Boleta> darBoletas() throws SQLException, Exception {
		ArrayList<Boleta> boletas = new ArrayList<Boleta>();
		
		String sql = "SELECT * FROM ISIS2304B221710.BOLETAS";		
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			int id_fundacion = Integer.parseInt(rs.getString("ID_FUNCION"));
			int id_usuario = Integer.parseInt(rs.getString("ID_USUARIO"));
			int id_silla = Integer.parseInt(rs.getString("ID_SILLA"));

			boletas.add(new Boleta(id, id_fundacion, id_usuario, id_silla));
		}
		return boletas;
	}

	/**
	 * Agrega una boleta a la base de datos
	 * @param bolta Boleta a agregar
	 * @throws SQLException Si hay error conectandose a la base de datos
	 * @throws Exception Si hay error convirtiendo de dato a boleta
	 */
	public void addBoleta(Boleta boleta) throws SQLException, Exception {
		DAOTablaSillas x = new DAOTablaSillas();
		
		if(!sePuedeComprarEnLocalidadYFuncion(boleta.getId_funcion(), x.darSilla(boleta.getId_silla()).getIdLocalidad()))
			throw new Exception("No hay mas boletas disponibles");

		if(sillaOcupada(boleta.getId_silla()))
			throw new Exception("La silla ya esta ocupada");

		String sql = "INSERT INTO ISIS2304B221710.BOLETAS VALUES (?,?,?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, boleta.getId());
		prepStmt.setInt(2, boleta.getId_funcion());
		prepStmt.setInt(3, boleta.getId_usuario());
		prepStmt.setInt(4, boleta.getId_silla());

		System.out.println("SQL stmt:" + sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Actualiza una boleta
	 * @param boleta Boleta con los nuevos datos
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error convirtiendo de dato a boleta
	 */
	public void updateBoleta(Boleta boleta) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B221710.COMPAÑIAS SET id_funcion = ?,id_usuario = ?, id_silla = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setInt(1, boleta.getId_funcion());
		prepStmt.setInt(2, boleta.getId_usuario());
		prepStmt.setInt(3, boleta.getId_silla());
		prepStmt.setInt(4, boleta.getId());

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
	public void deleteBoleta(Boleta boleta) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B221710.BOLETAS";
		sql += " WHERE id = " + boleta.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Da las boletas en localidad y funcion especifica.
	 * @param id_funcion Id de la funcion
	 * @param id_localidad Id de la localidad
	 * @return Numero de boletas que hay con las el id_funcion y id_localidad
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error conviertiendo los datos
	 */
	public int boletasEnLocalidadYFuncion(int id_funcion, int id_localidad) throws SQLException, Exception {
		String sql = "SELECT COUNT(*) AS TOTAL FROM ISIS2304B221710.BOLETAS WHERE ID_FUNCION = ? AND ID_LOCALIDAD = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setInt(1, id_funcion);
		prepStmt.setInt(2, id_localidad);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		rs.next();
		int boletas = 0;
		boletas = Integer.parseInt(rs.getString("TOTAL"));

		return boletas;
	}

	/**
	 * Verifica si quedan boletas para una funcion y localidad
	 * @param id_funcion Id de la funcion
	 * @param id_localidad Id de la localidad
	 * @return True si quedan boletas, false de lo contrario
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error convirtiendo los datos
	 */
	public boolean sePuedeComprarEnLocalidadYFuncion(int id_funcion, int id_localidad) throws SQLException, Exception {
		DAOTablaLocalidades local = new DAOTablaLocalidades();
		local.setConnection(conn);
		int capacidad_total = local.darLocalidad(id_localidad).getCapacidad();

		return capacidad_total > boletasEnLocalidadYFuncion(id_funcion, id_localidad);
	}
	
	/**
	 * Verifica si una silla esta ocupada
	 * @param id_silla Id de la silla a verificar
	 * @return True si esta ocupada, false de lo contrario
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error convirtiendo los datos
	 */
	public boolean sillaOcupada(int id_silla) throws SQLException, Exception {
		DAOTablaSillas sm = new DAOTablaSillas();
		sm.setConnection(this.conn);
		if(sm.darSilla(id_silla) == null)
			throw new Exception("La silla no existe");
		
		String sql = "SELECT * FROM BOLETAS WHERE ID_SILLA =" +id_silla;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
			return false;
			
		return true;
	}
	
	/**
	 * Da el numero de boletas compradas para una funcion 
	 * @param id_funcion Id de la funcion que se va verificar el numero de boletas
	 * @return Numero de boletas compradas para la funcion
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error convirtiendo los datos
	 */
	public int boletasCompradasFuncion(int id_funcion) throws SQLException, Exception {
		DAOTablaFunciones fm = new DAOTablaFunciones();
		fm.setConnection(this.conn);
		
		if(fm.darFuncion(id_funcion) == null)
			throw new Exception("La funcion no existe");
		
		String sql = "SELECT count(*) AS BOLETAS FROM BOLETAS WHERE ID_FUNCION =" +id_funcion;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
			return 0;
			
		int boletas = Integer.parseInt(rs.getString("BOLETAS"));
		
		return boletas;
	}
	
	/**
	 * Da el numero de boletas compradas en una funcion y localidad dada.
	 * @param id_funcion Id de la funcion
	 * @param id_localidad Id de la localidad
	 * @return Numero de boletas en una funcion y localidad dada.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error convirtiendo los datos
	 */
	public int boletasCompradasFuncionYLocalidad(int id_funcion, int id_localidad) throws SQLException, Exception {
		DAOTablaFunciones fm = new DAOTablaFunciones();
		DAOTablaLocalidades lm = new DAOTablaLocalidades();
		fm.setConnection(this.conn);
		lm.setConnection(this.conn);
		
		if(fm.darFuncion(id_funcion) == null)
			throw new Exception("La funcion no existe");
		
		if(lm.darLocalidad(id_localidad) == null)
			throw new Exception("La localidad no existe");
		
		String sql = "SELECT count(*) AS BOLETAS FROM BOLETAS x INNER JOIN SILLAS y ON x.ID_SILLA = y.ID WHERE y.ID_LOCALIDAD = ? AND x.ID_FUNCION = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id_localidad);
		prepStmt.setInt(2, id_funcion);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
			return 0;
		
		int boletas = 0;
		boletas = Integer.parseInt(rs.getString("BOLETAS"));

		return boletas;
	}
}
