package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Compañia;
import vos.ListaRentabilidad;
import vos.Rentabilidad;

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
	
	/**
	 * Da la rentabilidad de una compañia 
	 * @param fechaInicio Fecha de inicio del rango
	 * @param fechaFinal Fecha final del rango
	 * @throws SQLException Si hay problema conectandose con la base de datos.
	 * @throws Exception Si hay un problema manejando los datos
	 */
	public ListaRentabilidad darRentabilidadCompañias(Date fechaInicio, Date fechaFinal, int id_compañia) throws SQLException, Exception {
		String sql = "SELECT ID AS ID_COMPAÑIA, NUMERO_BOLETAS, ASISTENTES_FUNCION, VALOR_FACTURADO, PROP_ASIST_CAP FROM (SELECT x.*, (x.ASISTENTES_FUNCION / y.CAPACIDAD) AS PROP_ASIST_CAP FROM (SELECT x.ID, x.ID_ESPECTACULO,y.PUBLICO_OBJETIVO, x.ID_SITIO, x.ID_LOCALIDAD, count(ID_FUNCION) AS NUMERO_BOLETAS, sum(COSTO) AS VALOR_FACTURADO, count(ID_BOLETA) AS ASISTENTES_FUNCION FROM (SELECT x.*, y.ID_SITIO FROM (SELECT x.*, y.ID_LOCALIDAD FROM (SELECT x.*, y.ID_SILLA, y.COSTO, y.ID AS ID_BOLETA FROM (SELECT x.*, y.ID AS ID_FUNCION FROM (SELECT x.ID, y.ID_ESPECTACULO FROM COMPAÑIAS x INNER JOIN CONTRIBUIDORES y ON x.ID = y.ID_COMPAÑIA WHERE ID > 30 AND ID < 50) x INNER JOIN FUNCIONES y ON x.ID_ESPECTACULO = y.ID_ESPECTACULO WHERE y.FECHA BETWEEN ? AND ?) x INNER JOIN BOLETAS y ON x.ID_FUNCION = y.ID_FUNCION) x INNER JOIN SILLAS y ON x.ID_SILLA = y.ID) x INNER JOIN LOCALIDADES y ON x.ID_LOCALIDAD = y.ID) x INNER JOIN ESPECTACULOS y ON x.ID_ESPECTACULO = y.ID GROUP BY x.ID, x.ID_ESPECTACULO,y.PUBLICO_OBJETIVO, x.ID_SITIO, x.ID_LOCALIDAD) x INNER JOIN SITIOS y ON x.ID_SITIO = y.ID) WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setDate(1, fechaInicio);
		prepStmt.setDate(2, fechaFinal);
		prepStmt.setInt(3, id_compañia);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		ArrayList<Object> todo = new ArrayList<>();
		while(rs.next()) {
			
			int id_comp = Integer.parseInt(rs.getString("ID_COMPAÑIA"));
			int numero_boletas = Integer.parseInt(rs.getString("NUMERO_BOLETAS"));
			int asistentes_funcion = Integer.parseInt(rs.getString("ASISTENTES_FUNCION"));
			double valor_facturado = rs.getDouble("VALOR_FACTURADO");
			double prop_asist_cap = rs.getDouble("PROP_ASIST_CAP");
			
			todo.add(new Rentabilidad(id_comp, numero_boletas, asistentes_funcion, valor_facturado, prop_asist_cap));
		}
		
		return new ListaRentabilidad(todo);
	}
}
