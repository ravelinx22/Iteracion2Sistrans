package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Sitio;

public class DAOTablaSitios {
	
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
	public DAOTablaSitios() {
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
	 * Da todos los sitios de la base de datos
	 * @return Lista con los sitios de la base de datos.
	 * @throws SQLException Si hay error conectandose con la base de datos 
	 * @throws Exception Si hay error convirtiendo los datos a lista de sitios
	 */
	public ArrayList<Sitio> darSitios() throws SQLException, Exception {
		ArrayList<Sitio> sitios = new ArrayList<Sitio>();

		String sql = "SELECT * FROM FESTIVANDES.SITIOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			String nombre = rs.getString("NOMBRE");
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
			boolean aptoDiscapacitados = Boolean.parseBoolean(rs.getString(""));
			String tipoSilleteria = rs.getString("TIPO_SILLA");
			boolean tieneCobertura = Boolean.parseBoolean(rs.getString("TIENE_COBERTURA"));
			boolean disponibleLunes = Boolean.parseBoolean(rs.getString("DISPONIBLE_LUNES"));
			boolean disponibleMartes = Boolean.parseBoolean(rs.getString("DISPONIBLE_MARTES"));
			boolean disponibleMiercoles = Boolean.parseBoolean(rs.getString("DISPONIBLE_MIERCOLES"));
			boolean disponibleJueves = Boolean.parseBoolean(rs.getString("DISPONIBLE_JUEVES"));
			boolean disponibleViernes = Boolean.parseBoolean(rs.getString("DISPONIBLE_VIERNES"));
			boolean disponibleSabado = Boolean.parseBoolean(rs.getString("DISPONIBLE_SABADO"));
			boolean disponibleDomingo = Boolean.parseBoolean(rs.getString("DISPONIBLE_DOMINGO"));					
			sitios.add(new Sitio(id, nombre, capacidad, aptoDiscapacitados, tipoSilleteria, tieneCobertura, disponibleLunes, disponibleMartes, disponibleMiercoles, disponibleJueves, disponibleViernes, disponibleSabado, disponibleDomingo));
		}
		return sitios;
	}

	/**
	 * Agrega un sitio a la base de datos
	 * @param sitio Sitio a agregar
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error convirtiendo los datos a sitio
	 */
	public void addSitio(Sitio sitio) throws SQLException, Exception {

		String sql = "INSERT INTO FESTIVANDES.SITIOS VALUES (";
		sql += sitio.getId() + ",'";
		sql += sitio.getNombre() + "',";
		sql += sitio.getCapacidad() + ",";
		sql += sitio.esAptoDiscapacitados() + ",'";
		sql += sitio.getTipoSilleteria() + "',";
		sql += sitio.tieneCobertura() + ",";
		sql += sitio.isDisponibleLunes() + ",";
		sql += sitio.isDisponibleMartes() + ",";
		sql += sitio.isDisponibleMiercoles() + ",";
		sql += sitio.isDisponibleJueves() + ",";
		sql += sitio.isDisponibleViernes() + ",";
		sql += sitio.isDisponibleSabado() + ",";
		sql += sitio.isDisponibleDomingo() + ")";

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Actualiza un sitio
 	 * @param sitio Nuevo sitio
	 * @throws SQLException Si hay error conectandose a la base de datos.
	 * @throws Exception Si hay error conviertiendo los datos a sitio.
	 */
	public void updateSitio(Sitio sitio) throws SQLException, Exception {

		String sql = "UPDATE FESTIVANDES.SITIOS SET ";
		sql += "nombre='" + sitio.getNombre() + "',";
		sql += "capacidad=" + sitio.getCapacidad() + ",";
		sql += "apto_discapacitados=" + sitio.esAptoDiscapacitados() + ",";
		sql += "tipo_silleteria='" + sitio.getTipoSilleteria() + "',";
		sql += "tiene_cobertura=" + sitio.tieneCobertura() + ",";
		sql += "disponible_lunes=" + sitio.isDisponibleLunes() + ",";
		sql += "disponible_martes=" + sitio.isDisponibleMartes() + ",";
		sql += "disponible_miercoles=" + sitio.isDisponibleMiercoles() + ",";
		sql += "disponible_jueves=" + sitio.isDisponibleJueves() + ",";
		sql += "disponible_viernes=" + sitio.isDisponibleViernes() + ",";
		sql += "disponible_sabados=" + sitio.isDisponibleSabado() + ",";
		sql += "disponible_domingos=" + sitio.isDisponibleDomingo();
		sql += " WHERE id = " + sitio.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Elimina un sitio de la base de datos
	 * @param sitio Sitio a eliminar de la base de datos
	 * @throws SQLException Si hay error conectandose con la base de datos. 
	 * @throws Exception Si hay error convirtiendo a sitio los datos.
	 */
	public void deleteSitio(Sitio sitio) throws SQLException, Exception {

		String sql = "DELETE FROM FESTIVANDES.SITIOS";
		sql += " WHERE id = " + sitio.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
