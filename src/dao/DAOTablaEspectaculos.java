package dao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Espectaculo;
import vos.Funcion;
import vos.RequerimientoTecnico;
import vos.Usuario;

public class DAOTablaEspectaculos {
	
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
	public DAOTablaEspectaculos() {
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
	 * Da una lista de espectaculos que hay en la base de datos.
	 * @return Lista de espectaculos que hay en la base de datos
	 * @throws SQLException Si hay un error conectandose con la base de datos
	 * @throws Exception Si hay un error al convertir de dato a espectaculo
	 */
	public ArrayList<Espectaculo> darEspectaculos() throws SQLException, Exception {
		ArrayList<Espectaculo> espectaculos = new ArrayList<Espectaculo>();

		String sql = "SELECT * FROM ISIS2304B221710.ESPECTACULOS";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			
			String req = "SELECT ID_REQUERIMIENTO_TECNICO FROM ISIS2304B221710.REQUERIMIENTOESPECTACULO WHERE ID_ESPECTACULO = " +id;
			PreparedStatement segPrep = conn.prepareStatement(req);
			ResultSet s = segPrep.executeQuery();
			
			ArrayList<Integer> x = new ArrayList<>();
			while(s.next()) {
				int yes = Integer.parseInt(s.getString("ID_REQUERIMIENTO_TECNICO"));
				x.add(yes);
			}
			
			Integer[] requerimientos = x.toArray(new Integer[x.size()]);
			
			String nombre = rs.getString("NOMBRE");
			int duracion = Integer.parseInt(rs.getString("DURACION"));
			String idioma = rs.getString("IDIOMA");
			String descripcion = rs.getString("DESCRIPCION");
			String publicoObjetivo = rs.getString("PUBLICO_OBJETIVO");
			String genero = rs.getString("GENERO");		    
			espectaculos.add(new Espectaculo(id, nombre, duracion, idioma, descripcion, publicoObjetivo, genero,requerimientos));
		}
		return espectaculos;
	}
	
	/**
	 * Busca un espectaculo por id.
	 * @param id Id del espectaculo a buscar
	 * @return Espectaculo que tiene el id igual al parametro, null de lo contrario.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error al convertir de datos a espectaculo.
	 */
	public Espectaculo darEspectaculo(int id) throws SQLException, Exception {
		String sql = "SELECT * FROM ISIS2304B221710.ESPECTACULOS WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		
		if(!rs.next())
			return null;
		
		String req = "SELECT ID_REQUERIMIENTO_TECNICO FROM ISIS2304B221710.REQUERIMIENTOESPECTACULO WHERE ID_ESPECTACULO = " +id;
		PreparedStatement segPrep = conn.prepareStatement(req);
		ResultSet s = segPrep.executeQuery();
		
		ArrayList<Integer> x = new ArrayList<>();
		while(s.next()) {
			int yes = Integer.parseInt(s.getString("ID_REQUERIMIENTO_TECNICO"));
			x.add(yes);
		}
		
		Integer[] requerimientos = x.toArray(new Integer[x.size()]);
		
		String nombre = rs.getString("NOMBRE");
		int duracion = Integer.parseInt(rs.getString("DURACION"));
		String idioma = rs.getString("IDIOMA");
		String descripcion = rs.getString("DESCRIPCION");
		String publicoObjetivo = rs.getString("PUBLICO_OBJETIVO");
		String genero = rs.getString("GENERO");	
		Espectaculo es = new Espectaculo(id, nombre, duracion, idioma, descripcion, publicoObjetivo, genero, requerimientos);
		
		return es;
	}

	/**
	 * Agrega un espectaculo a la base de datos.
	 * @param espec Espectaculo a agregar a la base de datos
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a espectaculo
	 */
	public void addEspectaculo(Espectaculo espec) throws SQLException, Exception {
		String sql = "INSERT INTO ISIS2304B221710.ESPECTACULOS VALUES (?,?,?,?,?,?,?)";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);		
		prepStmt.setInt(1, espec.getId());
		prepStmt.setString(2, espec.getNombre());
		prepStmt.setInt(3, espec.getDuracion());
		prepStmt.setString(4, espec.getIdioma());
		prepStmt.setString(5, espec.getDescripcion());
		prepStmt.setString(6, espec.getPublicoObjetivo());
		prepStmt.setString(7, espec.getGenero());		
				
		System.out.println("SQL stmt:" + sql);

		recursos.add(prepStmt);
		prepStmt.executeQuery();
		
		for(Integer x : espec.getRequerimientos()) {
			String sql2 = "INSERT INTO ISIS2304B221710.REQUERIMIENTOESPECTACULO VALUES (?,?)";
			PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
			prepStmt2.setInt(1, espec.getId());
			prepStmt2.setInt(2, x);
			prepStmt2.executeQuery();
		}
	}
	
	/**
	 * Actualiza un espectaculo de la base de datos
	 * @param espec Espectaculo que tiene los nuevos datos.
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a espectaculo.
	 */
	public void updateEspectaculo(Espectaculo espec) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B221710.ESPECTACULOS SET nombre = ?, duracion = ?, idioma = ?, descripcion = ? , publico_objetivo = ?, genero = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setString(1, espec.getNombre());
		prepStmt.setInt(2, espec.getDuracion());
		prepStmt.setString(3, espec.getIdioma());
		prepStmt.setString(4, espec.getDescripcion());
		prepStmt.setString(5, espec.getPublicoObjetivo());
		prepStmt.setString(6, espec.getGenero());
		prepStmt.setInt(8, espec.getId());

		System.out.println("SQL stmt:" + sql);

		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	/**
	 * Da una lista de funciones del espectaculo
	 * @param id_espectaculo Id del espectaculo
	 * @return Lista con las funciones del espectaculo
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a espectaculo.
	 */
	public ArrayList<Funcion> darFunciones(int id_espectaculo) throws SQLException, Exception {
		ArrayList<Funcion> funciones = new ArrayList<>();
		String sql = "SELECT * FROM FUNCIONES WHERE ID_ESPECTACULO=" +id_espectaculo;
		PreparedStatement prepStmt = conn.prepareStatement(sql);

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
	 * Elimina un espectaculo de la base de datos.
	 * @param espec Espectaculo a eliminar de la base de datos.
	 * @throws SQLException Si hay un error conectandose con la base de datos
	 * @throws Exception Si hay un error al convertir de dato a espectaculo.
	 */
	public void deleteEspectaculo(Espectaculo espec) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B221710.ESPECTACULOS";
		sql += " WHERE id = " + espec.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}
