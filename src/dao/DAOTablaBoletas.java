package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import vos.Boleta;
import vos.Funcion;
import vos.Usuario;

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
			double costo = rs.getDouble("COSTO");
			boolean abono = rs.getBoolean("ABONO");

			boletas.add(new Boleta(id, id_fundacion, id_usuario, id_silla, costo, abono));
		}
		return boletas;
	}
	
	/**
	 * Busca una boleta por id.
	 * @param id Id de la boleta a buscar
	 * @return Boleta que tiene el id igual al parametro, null de lo contrario.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error al convertir de datos a usuario.
	 */
	public Boleta darBoleta(int id) throws SQLException, Exception {
		String sql = "SELECT * FROM ISIS2304B221710.BOLETAS WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(!rs.next())
			return null;

		int id_funcion = Integer.parseInt(rs.getString("ID_FUNCION"));
		int id_usuario = Integer.parseInt(rs.getString("ID_USUARIO"));
		int id_silla = Integer.parseInt(rs.getString("ID_SILLA"));
		double costo = rs.getDouble("COSTO");
		boolean abono = rs.getBoolean("ABONO");
		Boleta bol = new Boleta(id, id_funcion, id_usuario, id_silla, costo, abono);

		return bol;
	}

	/**
	 * Agrega una boleta a la base de datos
	 * @param bolta Boleta a agregar
	 * @throws SQLException Si hay error conectandose a la base de datos
	 * @throws Exception Si hay error convirtiendo de dato a boleta
	 */
	public void addBoleta(Boleta boleta) throws SQLException, Exception {
		DAOTablaSillas sill = new DAOTablaSillas();
		sill.setConnection(this.conn);
		DAOTablaLocalidades local = new DAOTablaLocalidades();
		local.setConnection(this.conn);

		if(!sePuedeComprarEnLocalidadYFuncion(boleta.getId_funcion(), sill.darSilla(boleta.getId_silla()).getIdLocalidad()))
			throw new Exception("No hay mas boletas disponibles");

		if(sillaOcupada(boleta.getId_silla(), boleta.getId_funcion()))
			throw new Exception("La silla ya esta ocupada");


		double costoBase = local.darLocalidad(sill.darSilla(boleta.getId_silla()).getIdLocalidad()).getCosto();
		double costo = (boleta.getAbono()) ? costoBase*0.8 : costoBase;

		String sql = "INSERT INTO ISIS2304B221710.BOLETAS VALUES (?,?,?,?,?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, boleta.getId());
		prepStmt.setInt(2, boleta.getId_funcion());
		prepStmt.setInt(3, boleta.getId_usuario());
		prepStmt.setInt(4, boleta.getId_silla());
		prepStmt.setDouble(5, costo);
		prepStmt.setBoolean(6, boleta.getAbono());

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

		String sql = "UPDATE ISIS2304B221710.COMPAÑIAS SET id_funcion = ?,id_usuario = ?, id_silla = ?, costo = ?, abono = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setInt(1, boleta.getId_funcion());
		prepStmt.setInt(2, boleta.getId_usuario());
		prepStmt.setInt(3, boleta.getId_silla());
		prepStmt.setDouble(4, boleta.getCosto());
		prepStmt.setBoolean(5, boleta.getAbono());
		prepStmt.setInt(6, boleta.getId());

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
	public void deleteBoleta(Boleta boleta, Date fechaEliminacion) throws SQLException, Exception {

		if(!sePuedeBorrarBoleta(boleta, fechaEliminacion))
			throw new Exception("Solo se puede eliminar una boleta con anticipacion de 5 dias para la funcion");
		
		String sql = "DELETE FROM ISIS2304B221710.BOLETAS";
		sql += " WHERE id = " + boleta.getId();

		System.out.println("SQL stmt:" + sql);

		agregarBoletaCancelada(boleta.getId(), boleta.getId_usuario());
		
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
		String sql = "SELECT count(*) AS TOTAL FROM ISIS2304B221710.BOLETAS x INNER JOIN ISIS2304B221710.SILLAS y ON x.ID_SILLA = y.ID WHERE x.ID_FUNCION = ? AND y.ID_LOCALIDAD = ?";
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
	 * @param id_funcion Id de la funcion a verificar
	 * @return True si esta ocupada, false de lo contrario
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error convirtiendo los datos
	 */
	public boolean sillaOcupada(int id_silla, int id_funcion) throws SQLException, Exception {
		DAOTablaSillas sm = new DAOTablaSillas();
		sm.setConnection(this.conn);
		if(sm.darSilla(id_silla) == null)
			throw new Exception("La silla no existe");

		String sql = "SELECT * FROM ISIS2304B221710.BOLETAS WHERE ID_SILLA = ? AND ID_FUNCION = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id_silla);
		prepStmt.setInt(2, id_funcion);
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

		String sql = "SELECT count(*) AS BOLETAS FROM ISIS2304B221710.BOLETAS WHERE ID_FUNCION =" +id_funcion;
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

		String sql = "SELECT count(*) AS BOLETAS FROM ISIS2304B221710.BOLETAS x INNER JOIN ISIS2304B221710.SILLAS y ON x.ID_SILLA = y.ID WHERE y.ID_LOCALIDAD = ? AND x.ID_FUNCION = ?";
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

	/**
	 * Da las ganancias de una funcion
	 * @param id_funcion Id de la funcion.
	 * @return Ganancias de una funcion.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error convirtiendo los datos
	 */
	public double darGananciasFuncion(int id_funcion) throws SQLException, Exception {
		DAOTablaFunciones fm = new DAOTablaFunciones();
		fm.setConnection(this.conn);

		if(fm.darFuncion(id_funcion) == null)
			throw new Exception("La funcion no existe");

		String sql = "SELECT SUM(COSTO) AS COSTO FROM ISIS2304B221710.BOLETAS WHERE ID_FUNCION =" +id_funcion;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(!rs.next())
			return 0;

		double costo = rs.getDouble("COSTO");
		return costo;
	}

	/**
	 * Da las ganancias de un espectaculo 
	 * @param id_espectaculo Id del espectaculo
	 * @return Ganacias del espectaculo
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error convirtiendo los datos
	 */
	public double darGananciasEspectaculo(int id_espectaculo) throws SQLException, Exception {
		DAOTablaEspectaculos espec = new DAOTablaEspectaculos();
		espec.setConnection(this.conn);

		if(espec.darEspectaculo(id_espectaculo) == null)
			throw new Exception("No existe el espectaculo");

		double ganancias = 0.0;

		for(Funcion x : espec.darFunciones(id_espectaculo)) {
			ganancias += darGananciasFuncion(x.getId());
		}

		return ganancias;
	}

	/**
	 * Da el ultimo id de boleta usado
	 * @return Ultimo id de boleta usado.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error convirtiendo los datos
	 */
	public int getLastId() throws SQLException, Exception {
		String sql = "SELECT max(ID) AS MAX FROM BOLETAS";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(!rs.next())
			return 0;
		
		int maximo = Integer.parseInt(rs.getString("MAX"));
		return maximo;
	}
	
	/**
	 * Verifica si se puede borrar una boleta
	 * @param id Id de la boleta a verificar
	 * @return True si se puede borrar, false de lo contrario
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error convirtiendo los datos
	 */
	public boolean sePuedeBorrarBoleta(Boleta boleta, Date fechaEliminacion) throws SQLException, Exception {
		DAOTablaFunciones daoFuncion = new DAOTablaFunciones();
		daoFuncion.setConnection(this.conn);
		Date funcion = daoFuncion.darFuncion(boleta.getId_funcion()).getFecha();
		
		int days1 = (int) (funcion.getTime() / (1000*60*60*24));
		int days2 = (int) (fechaEliminacion.getTime() / (1000*60*60*24));

		
		return (days1-days2)>= 5;
	}
	
	/**
	 * Agrega boleta a canceladas
	 * @param id_boleta Id de la boleta cancelada 
	 * @param id_usuario Id del usuario que lo cancelo
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error convirtiendo los datos
	 */
	public void agregarBoletaCancelada(int id_boleta, int id_usuario) throws SQLException, Exception {
		String sql = "INSERT INTO ISIS2304B221710.BOLETACANCELADA VALUES (?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id_usuario);
		prepStmt.setInt(2, id_boleta);

		System.out.println("SQL stmt:" + sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	// Iteracion 4
	
	public ArrayList<HashMap<String, Object>> consultarCompraBoletas(int id, Date fecha1, Date fecha2) throws Exception {
		ArrayList<HashMap<String, Object>> x = new ArrayList<>();
		
		String sql = "(SELECT ID_FUNCION AS ID, NOMBRE, FECHA, COUNT(bol.ID) AS VENDIDAS, COUNT(bol.ID_USUARIO) AS CLIENTES_REGISTRADOS FROM (SELECT x.* FROM BOLETAS x INNER JOIN (SELECT x.ID FROM SILLAS x INNER JOIN LOCALIDADES y ON x.ID_LOCALIDAD = y.ID WHERE y.ID = ?) y ON x.ID = y.ID) bol INNER JOIN (SELECT FUNCIONES.ID AS IDF, ESPECTACULOS.NOMBRE AS NOMBRE, FECHA FROM FUNCIONES  INNER JOIN ESPECTACULOS ON FUNCIONES.ID_ESPECTACULO = ESPECTACULOS.ID WHERE FECHA BETWEEN ? AND ?) ON bol.ID_FUNCION = IDF group by NOMBRE, ID_FUNCION, FECHA)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);
		prepStmt.setDate(2, fecha1);
		prepStmt.setDate(3, fecha2);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while(rs.next()) {
			String id_1 = rs.getString("ID");
			String nombre = rs.getString("NOMBRE");
			String fecha = rs.getString("FECHA");
			String vendidas = rs.getString("VENDIDAS");
			String cliente_registrados = rs.getString("CLIENTES_REGISTRADOS");

			HashMap<String, Object> mapa = new HashMap<>();
			mapa.put("ID", id_1);
			mapa.put("NOMBRE", nombre);
			mapa.put("FECHA", fecha);
			mapa.put("VENDIDAS", vendidas);
			mapa.put("CLIENTES_REGISTRADOS", cliente_registrados);

			x.add(mapa);
		}
		
		return x;
		
	}
	
	/**
	 * Prueba de memoria principal
	 */
	public Usuario memoriaPrincipal() throws Exception {
		Long inicio = System.currentTimeMillis();
		
		String sql = "SELECT * FROM BOLETAS";
		String sql1 = "SELECT * FROM USUARIOS";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		ResultSet rs = prepStmt.executeQuery();
		ResultSet rs1 = prepStmt1.executeQuery();

		ArrayList<Usuario> usuario = new ArrayList<Usuario>();
		while(rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			int id_f = Integer.parseInt(rs.getString("ID_FUNCION"));
			int id_u = Integer.parseInt(rs.getString("ID_USUARIO"));
			int id_s = Integer.parseInt(rs.getString("ID_SILLA"));
			double costo = rs.getDouble("COSTO");
			boolean abono = rs.getBoolean("ABONO");
			Boleta x = new Boleta(id, id_f, id_u, id_s, costo, abono);
			
			while(rs1.next()) {
				int id1 = Integer.parseInt(rs1.getString("ID"));
				String nombre = rs1.getString("NOMBRE");
				int ident = Integer.parseInt(rs1.getString("IDENTIFICACION"));
				String correo = rs1.getString("CORREO");
				String rol = rs1.getString("ROL");
				int id_p1 = Integer.parseInt(rs1.getString("ID_PREFERENCIA"));
				
				Usuario y = new Usuario(id1, nombre, ident, correo, rol, id_p1);
				
				if(y.getId() == x.getId_usuario()) {
					usuario.add(y);
				}
			}
		}
		
		Usuario z = null;
		for(Usuario x : usuario) {
			if(x.getId() == 4882) {
				z = x;
				break;
			}
		}
		
		Long finalConsulta = System.currentTimeMillis()-inicio;
		
		return z;
	}
}
