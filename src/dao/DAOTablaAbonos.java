package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import vos.Abono;
import vos.Boleta;
import vos.Festival;

public class DAOTablaAbonos {
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
	public DAOTablaAbonos() {
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
	 * Da una lista con todas los abonos en la base de datos.
	 * @return Lista con los abonos en la base de datos.
	 * @throws SQLException Si hay un error al conectarse con la base de datos.
	 * @throws Exception Si hay un error al convertir de dato a abono.
	 */
	public ArrayList<Abono> darAbonos() throws SQLException, Exception {
		ArrayList<Abono> abonos = new ArrayList<Abono>();

		// Primera parte

		String sql = "SELECT * FROM ISIS2304B221710.ABONOS";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		// Segunda parte
		ArrayList<Integer> id_funciones = new ArrayList<>();
		String sql2 = "SELECT ID_FUNCION FROM ISIS2304B221710.ABONO_FUNCION";
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();

		while(rs2.next()) {
			id_funciones.add(Integer.parseInt(rs2.getString("ID_FUNCION")));
		}
		Integer[] funciones = id_funciones.toArray(new Integer[id_funciones.size()]);


		// Tercera parte
		ArrayList<Integer> id_localidades = new ArrayList<>();
		String sql3 = "SELECT ID_LOCALIDAD FROM ISIS2304B221710.ABONO_FUNCION";
		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		ResultSet rs3 = prepStmt3.executeQuery();

		while(rs3.next()) {
			id_localidades.add(Integer.parseInt(rs3.getString("ID_LOCALIDAD")));
		}
		Integer[] localidades = id_localidades.toArray(new Integer[id_localidades.size()]);

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			int id_usuario = Integer.parseInt(rs.getString("ID_USUARIO"));
			int id_festival = Integer.parseInt(rs.getString("ID_FESTIVAL"));
			Date fecha_compra = rs.getDate("FECHA_COMPRA");

			abonos.add(new Abono(id, id_usuario, funciones, localidades, id_festival, fecha_compra));
		}

		return abonos;
	}

	/**
	 * Busca un abono por id.
	 * @param id Id del abono a buscar
	 * @return Abono que tiene el id igual al parametro, null de lo contrario.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error al convertir de datos a abono.
	 */
	public Abono darAbono(int id) throws SQLException, Exception {
		String sql = "SELECT * FROM ISIS2304B221710.ABONOS WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(!rs.next())
			return null;

		// Parte 2
		String sql2 = "SELECT ID_FUNCION FROM ISIS2304B221710.ABONO_FUNCION WHERE ID = ?";
		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		prepStmt2.setInt(1, id);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();

		ArrayList<Integer> id_funciones = new ArrayList<>();
		while(rs2.next()) {
			id_funciones.add(Integer.parseInt(rs2.getString("ID_FUNCION")));
		}
		Integer[] funciones = id_funciones.toArray(new Integer[id_funciones.size()]);

		// Parte 3
		String sql3 = "SELECT ID_LOCALIDAD FROM ISIS2304B221710.ABONO_FUNCION WHERE ID = ?";
		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		prepStmt3.setInt(1, id);
		recursos.add(prepStmt3);
		ResultSet rs3 = prepStmt3.executeQuery();

		ArrayList<Integer> id_localidades = new ArrayList<>();
		while(rs3.next()) {
			id_localidades.add(Integer.parseInt(rs3.getString("ID_LOCALIDAD")));
		}
		Integer[] localidades = id_localidades.toArray(new Integer[id_localidades.size()]);

		int id_usuario = Integer.parseInt(rs.getString("ID_USUARIO"));
		int id_festival = Integer.parseInt(rs.getString("ID_FESTIVAL"));
		Date fecha_compra = rs.getDate("FECHA_COMPRA");

		Abono abono = new Abono(id, id_usuario, funciones, localidades, id_festival, fecha_compra);

		return abono;
	}

	/**
	 * Agrega un abono a la base de datos
	 * @param abono Abono que tiene los datos a agregar
	 * @throws SQLException Si hay error conectandose a la base de datos.
	 * @throws Exception Si hay en error al convertir de dato a abono.
	 */
	public void addAbono(Abono abono) throws SQLException, Exception {
		DAOTablaBoletas db = new DAOTablaBoletas();
		db.setConnection(this.conn);
		DAOTablaLocalidades lc = new DAOTablaLocalidades();
		lc.setConnection(this.conn);

		int ultimaBoletaId = db.getLastId();

		if(!comproAbonoATiempo(abono))
			throw new Exception("Tiene que comprar el abono con 3 semanas de anticipacion");
		
		if(sePuedeComprarAbono(abono.getLista_localidades(), abono.getLista_funciones())) {
			for(int i = 0; i < abono.getLista_funciones().length; i++) {
				int idSilla = lc.darSillaDisponible(abono.getLista_localidades()[i], abono.getLista_funciones()[i]);
				db.addBoleta(new Boleta(++ultimaBoletaId, abono.getLista_funciones()[i], abono.getId_usuario(), idSilla, 0.0, true));
			}

			String sql = "INSERT INTO ISIS2304B221710.ABONOS VALUES (?,?,?,?)";
			PreparedStatement prepStmt = conn.prepareStatement(sql);

			prepStmt.setInt(1, abono.getId());
			prepStmt.setInt(2, abono.getId_usuario());
			prepStmt.setInt(3, abono.getId_festival());
			prepStmt.setDate(4, abono.getFecha_compra());


			if(abono.getLista_funciones().length != abono.getLista_localidades().length)
				throw new Exception("El numero de localidades debe ser igual al de las funciones");

			System.out.println("SQL stmt:" + sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();

			for(int i = 0; i < abono.getLista_funciones().length; i++) {
				String sql2 = "INSERT INTO ISIS2304B221710.ABONO_FUNCION VALUES (?,?,?)";
				PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
				prepStmt2.setInt(1, abono.getId());
				prepStmt2.setInt(2, abono.getLista_funciones()[i]);
				prepStmt2.setInt(3, abono.getLista_localidades()[i]);
				prepStmt2.executeQuery();
			}
		}
	}

	/**
	 * Actualiza un abono que esta en la base de datos
	 * @param abono Abono que contiene los nuevos datos.
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay enrror convirtiendo el dato a abono
	 */
	public void updateAbono(Abono abono) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B221710.ABONOS SET id_usuario = ?, id_festival = ?, fecha_compra = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setInt(1, abono.getId_usuario());
		prepStmt.setInt(2, abono.getId_festival());
		prepStmt.setDate(3, abono.getFecha_compra());
		prepStmt.setInt(4, abono.getId());

		System.out.println("SQL stmt:" + sql);

		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Elimina un abono de la base de datos.
	 * @param abono Abono que se quiere eliminar
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error conviertiendo de dato a abono.
	 */
	public void deleteAbono(Abono abono) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B221710.ABONOS";
		sql += " WHERE id = " + abono.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Mira si se puede comprar un abono con las lista de localidades y funciones
	 * @param lista_localidades Lista de localidades a abonar
	 * @param lista_funciones Lista de funciones a abonar
	 * @return True si se puede abonar, false de lo contrario
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay enrror convirtiendo el dato a abono
	 */
	public boolean sePuedeComprarAbono(Integer[] lista_localidades, Integer[] lista_funciones) throws SQLException, Exception {
		DAOTablaLocalidades loc = new DAOTablaLocalidades();
		loc.setConnection(this.conn);

		boolean sePuede = false;
		for(int i = 0; i < lista_funciones.length; i++) {
			loc.darSillaDisponible(lista_localidades[i], lista_funciones[i]);
		}
		sePuede = true;

		return sePuede;
	}

	/**
	 * Verifica si se compro el abono con 3 semanas de anticipacion.
	 * @param abono Abono que se quiere verificar
	 * @param festival Festival donde se va a comprar el abono
	 * @return True si se compra a tiempo, false de lo contrario.
	 * @throws SQLException Si hay un error conectandose con la base de datos.
	 * @throws Exception Si hay enrror convirtiendo el dato a abono
	 */
	public boolean comproAbonoATiempo(Abono abono) throws SQLException, Exception {
		DAOTablaFestivales festiv = new DAOTablaFestivales();
		festiv.setConnection(this.conn);
		Festival festival = festiv.darFestival(abono.getId_festival());

		if(festival == null)
			throw new Exception("Festival no existe");

		int compra = (int) (abono.getFecha_compra().getTime() / (1000*60*60*24*7));
		int comienzoFestival = (int) (festival.getFecha_inicio().getTime() / (1000*60*60*24*7));

		
		return (comienzoFestival-compra)>=3;

	}
}
