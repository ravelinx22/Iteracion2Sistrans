package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Localidad;
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

		String sql = "SELECT * FROM ISIS2304B221710.SITIOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			
			String req = "SELECT ID_REQUERIMIENTO_TECNICO FROM ISIS2304B221710.REQUERIMIENTOSITIO WHERE ID_SITIO = " +id;
			PreparedStatement segPrep = conn.prepareStatement(req);
			ResultSet s = segPrep.executeQuery();
			
			ArrayList<Integer> x = new ArrayList<>();
			while(s.next()) {
				int yes = Integer.parseInt(s.getString("ID_REQUERIMIENTO_TECNICO"));
				x.add(yes);
			}
			
			Integer[] requerimientos = x.toArray(new Integer[x.size()]);
			
			String nombre = rs.getString("NOMBRE");
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
			boolean aptoDiscapacitados = Boolean.parseBoolean(rs.getString("APTO_DISCAPACITADOS"));
			String tipoSilleteria = rs.getString("TIPO_SILLETERIA");
			boolean tieneCobertura = Boolean.parseBoolean(rs.getString("TIENE_COBERTURA"));				
			sitios.add(new Sitio(id, nombre, capacidad, aptoDiscapacitados, tipoSilleteria, tieneCobertura, requerimientos));
		}
		return sitios;
	}
	
	/**
	 * Busca un sitio por id.
	 * @param id Id del sitio a buscar
	 * @return Sitio que tiene el id igual al parametro, null de lo contrario.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error al convertir de datos a sitio.
	 */
	public Sitio darSitio(int id) throws SQLException, Exception {
		String sql = "SELECT * FROM ISIS2304B221710.SITIOS WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
			return null;
		
		String req = "SELECT ID_REQUERIMIENTO_TECNICO FROM ISIS2304B221710.REQUERIMIENTOSITIO WHERE ID_SITIO = " +id;
		PreparedStatement segPrep = conn.prepareStatement(req);
		ResultSet s = segPrep.executeQuery();
		
		ArrayList<Integer> x = new ArrayList<>();
		while(s.next()) {
			int yes = Integer.parseInt(s.getString("ID_REQUERIMIENTO_TECNICO"));
			x.add(yes);
		}
		
		Integer[] requerimientos = x.toArray(new Integer[x.size()]);
		
		String nombre = rs.getString("NOMBRE");
		int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
		boolean aptoDiscapacitados = Boolean.parseBoolean(rs.getString("APTO_DISCAPACITADOS"));
		String tipoSilleteria = rs.getString("TIPO_SILLETERIA");
		boolean tieneCobertura = Boolean.parseBoolean(rs.getString("TIENE_COBERTURA"));			
		Sitio es = new Sitio(id, nombre, capacidad, aptoDiscapacitados, tipoSilleteria, tieneCobertura, requerimientos);
		
		return es;
	}

	/**
	 * Agrega un sitio a la base de datos
	 * @param sitio Sitio a agregar
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error convirtiendo los datos a sitio
	 */
	public void addSitio(Sitio sitio) throws SQLException, Exception {

		String sql = "INSERT INTO ISIS2304B221710.SITIOS VALUES (?,?,?,?,?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setInt(1, sitio.getId());
		prepStmt.setString(2, sitio.getNombre());
		prepStmt.setInt(3, sitio.getCapacidad());
		prepStmt.setBoolean(4, sitio.esAptoDiscapacitados());
		prepStmt.setString(5, sitio.getTipoSilleteria());
		prepStmt.setBoolean(6, sitio.tieneCobertura());


		System.out.println("SQL stmt:" + sql);

		recursos.add(prepStmt);
		prepStmt.executeQuery();

		for(Integer x : sitio.getRequerimientos()) {
			String sql2 = "INSERT INTO ISIS2304B221710.REQUERIMIENTOSITIO VALUES (?,?)";
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			prepStmt2.setInt(1, sitio.getId());
			prepStmt2.setInt(2, x);
			prepStmt2.executeQuery();
		}
	}
	
	/**
	 * Actualiza un sitio
 	 * @param sitio Nuevo sitio
	 * @throws SQLException Si hay error conectandose a la base de datos.
	 * @throws Exception Si hay error conviertiendo los datos a sitio.
	 */
	public void updateSitio(Sitio sitio) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B221710.SITIOS SET nombre = ?, capacidad = ?, apto_discapacitados = ?, tipo_silleteria = ?, tiene_cobertura = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setString(1, sitio.getNombre());
		prepStmt.setInt(2, sitio.getCapacidad());
		prepStmt.setBoolean(3, sitio.esAptoDiscapacitados());
		prepStmt.setString(4, sitio.getTipoSilleteria());
		prepStmt.setBoolean(5, sitio.tieneCobertura());
		prepStmt.setInt(6, sitio.getId());

		System.out.println("SQL stmt:" + sql);
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

		String sql = "DELETE FROM ISIS2304B221710.SITIOS";
		sql += " WHERE id = " + sitio.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Da la informacion de los sitios
	 */
	public ResultSet darInfoSitio(int id, String agrupamiento, String orden, String ordenarPor) throws SQLException, Exception {
		String tabla = "(SELECT x.id AS ID, x.nombre AS NOMBRE_SITIO, x.capacidad AS CAPACIDAD_SITIO , x.apto_discapacitados AS APTO_DISCAPACITADOS, x.tipo_silleteria AS TIPO_SILLETERIA, x.tiene_cobertura AS TIENE_COBERTURA , y.id AS ID_LOCALIDAD, y.nombre AS NOMBRE_LOCALIDAD, y.capacidad AS CAPACIDAD_LOCALIDAD, y.costo AS COSTO_LOCALIDAD FROM ISIS2304B221710.SITIOS x INNER  JOIN ISIS2304B221710.LOCALIDADES y ON x.ID = y.ID_SITIO WHERE x.id = ?) ";
		String sql = "SELECT * FROM " +tabla;
			
		if(agrupamiento != null) {
			sql = "SELECT " +agrupamiento +" FROM" +tabla +" GROUP BY " +agrupamiento +" ";
		}
		
		if(orden != null && ordenarPor != null) {
			sql += "ORDER BY " +ordenarPor +" " +orden;
		}
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);
		recursos.add(prepStmt);
		ResultSet x = prepStmt.executeQuery();
		
		return x;
	}
	
	/**
	 * Da las localidades de un sitio en especifico.
	 * @param id_sitio Id del sitio 
	 * @return Lista con las localidades de un sitio.
	 * @throws SQLException Si hay error conectandose con la base de datos. 
	 * @throws Exception Si hay error convirtiendo a localidad los datos.
	 */
	public ArrayList<Localidad> darLocalidades(int id_sitio) throws SQLException, Exception {
		ArrayList<Localidad> localidades = new ArrayList<>();
		String sql = "SELECT y.* FROM SITIOS x INNER JOIN LOCALIDADES y ON x.ID = y.ID_SITIO WHERE y.ID_SITIO=?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id_sitio);
		
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
			return null;
		
		while(rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			String nombre = rs.getString("NOMBRE");
			int capacidad = Integer.parseInt(rs.getString("CAPACIDAD"));
			int idSitio = Integer.parseInt(rs.getString("ID_SITIO"));
			double costo = Double.parseDouble(rs.getString("COSTO"));
			localidades.add(new Localidad(id, capacidad, nombre,  idSitio, costo));
		}
		
		return localidades;
	}
}
