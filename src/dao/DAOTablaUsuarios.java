package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import vos.Boleta;
import vos.Funcion;
import vos.Silla;
import vos.Usuario;

public class DAOTablaUsuarios {

	/**
	 * Arraylist de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Constructor DAO usuarios.
	 */
	public DAOTablaUsuarios() {
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
	 * Da los usuarios que hay en la base de datos
	 * @return Lista con los usuarios.
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error conviertiendo los datos a lista.
	 */
	public ArrayList<Usuario> darUsuarios() throws SQLException, Exception {
		ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

		String sql = "SELECT * FROM ISIS2304B221710.USUARIOS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			String nombre = rs.getString("NOMBRE");
			int identificacion = Integer.parseInt(rs.getString("IDENTIFICACION"));
			String correo = rs.getString("CORREO");
			String rol = rs.getString("ROL");
			int idPreferencia = Integer.parseInt(rs.getString("ID_PREFERENCIA"));
			usuarios.add(new Usuario(id, nombre, identificacion, correo, rol, idPreferencia));
		}
		return usuarios;
	}

	/**
	 * Busca un usuario por id.
	 * @param id Id del usuario a buscar
	 * @return Usuario que tiene el id igual al parametro, null de lo contrario.
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error al convertir de datos a usuario.
	 */
	public Usuario darUsuario(int id) throws SQLException, Exception {
		String sql = "SELECT * FROM ISIS2304B221710.USUARIOS WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, id);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(!rs.next())
			return null;

		String nombre = rs.getString("NOMBRE");
		int identificacion = Integer.parseInt(rs.getString("IDENTIFICACION"));
		String correo = rs.getString("CORREO");
		String rol = rs.getString("ROL");
		int idPreferencia = Integer.parseInt(rs.getString("ID_PREFERENCIA"));
		Usuario us = new Usuario(id, nombre, identificacion, correo, rol, idPreferencia);

		return us;
	}

	/**
	 * Agrega un nuevo usuario a la base de datos
	 * @param usuario Nuevo usuario
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error conviertiendo los datos a usuario.
	 */
	public void addUsuario(Usuario usuario) throws SQLException, Exception {
		String sql = "INSERT INTO ISIS2304B221710.USUARIOS VALUES (?,?,?,?,?,?)";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setInt(1, usuario.getId());
		prepStmt.setString(2, usuario.getNombre());
		prepStmt.setInt(3, usuario.getIdentificacion());
		prepStmt.setString(4, usuario.getCorreo());
		prepStmt.setString(5, usuario.getRol());
		prepStmt.setInt(6, usuario.getIdPreferencia());

		System.out.println("SQL stmt:" + sql);

		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Actualiza un usuario
	 * @param usuario Usuario que contiene los nuevos datos.
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error conviertiendo los datos a usuario.
	 */
	public void updateUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = "UPDATE ISIS2304B221710.USUARIOS SET nombre = ?, identificacion = ?, correo = ?, rol = ?, id_preferencia = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setString(1, usuario.getNombre());
		prepStmt.setInt(2, usuario.getIdentificacion());
		prepStmt.setString(3, usuario.getCorreo());
		prepStmt.setString(4, usuario.getRol());
		prepStmt.setInt(5, usuario.getIdPreferencia());
		prepStmt.setInt(6, usuario.getId());

		System.out.println("SQL stmt:" + sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Elimina un usuario de la base de datos
	 * @param usuario Usuario que se va a eliminar.
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error conviertiendo los datos a usuario.
	 */
	public void deleteUsuario(Usuario usuario) throws SQLException, Exception {

		String sql = "DELETE FROM ISIS2304B221710.USUARIOS";
		sql += " WHERE id = " + usuario.getId();

		System.out.println("SQL stmt:" + sql);

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	//TODO RF7
	public void cambiarPreferencia(int idnuevaPreferencia, int id)  throws SQLException , Exception
	{
		String sql = "UPDATE ISIS2304B221710.USUARIOS SET id_preferencia = ? WHERE id = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, idnuevaPreferencia);
		prepStmt.setInt(2, id);

		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	// TODO RF9
	public void registrarEspectaculoTerminado(Usuario usuario, int idEspectaculo) throws SQLException , Exception
	{
		if(usuario.getRol().equals("festivAndes"))
		{
			String sql = "UPDATE ISIS2304B221710.ESPECTACULO  SET nombre = ?, duracion = ?, idioma = ?, costo = ?, descripcion = ? , publico_objetivo = ?, genero = ? WHERE id = ?";
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			prepStmt.setString(1, "TERMINADO");
			prepStmt.setInt(2,0);
			prepStmt.setString(3, "TERMINADO");
			prepStmt.setDouble(4, 0);
			prepStmt.setString(5, "TERMINADO");
			prepStmt.setString(6, "TERMINADO");
			prepStmt.setString(7, "TERMINADO");
			prepStmt.setInt(8, idEspectaculo);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}
	}
	//TODO rfc1 corregir;
	public ArrayList<Funcion> consultarEspectaculos(Usuario usuario , int pfechaInicial, int pFechaFinal, String parametroParaOrdenar) throws SQLException , Exception
	{



		ArrayList<Funcion> funciones = new ArrayList<Funcion>();


		String sql = "SELECT FROM ISIS2304B221710.ESPECTACULO FULL OUTER JOIN ISIS2304B221710.FUNCION ON ISIS2304B221710.ESPECTACULO.id = ISIS2304B221710.FUNCION.espectaculo_id ";
		sql += "WHERE fecha > ? AND WHERE fecha < ? ORDER BY ?" ;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		prepStmt.setInt(1, pfechaInicial);
		prepStmt.setInt(2, pFechaFinal);
		prepStmt.setString(3, parametroParaOrdenar);
		System.out.println("SQL stmt:" + sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();
		while (rs.next()) {
			int id = Integer.parseInt(rs.getString("ID"));
			int fecha = Integer.parseInt(rs.getString("FECHA"));
			int horaInicio = Integer.parseInt(rs.getString("HORAINICIO"));
			int boletasTotales = Integer.parseInt(rs.getString("BOLETASTOTALES"));
			int idReserva = Integer.parseInt(rs.getString("RESERVA_ID"));
			int idEspectaculo = Integer.parseInt(rs.getString("ESPECTACULO_ID"));
			int idFestival = Integer.parseInt(rs.getString("FESTIVAL_ID"));
			SimpleDateFormat originalFormat = new SimpleDateFormat("yyyyMMdd");
			Date date = (Date) originalFormat.parse(fecha + "");
			funciones.add(new Funcion(id, date, horaInicio, boletasTotales, idReserva, idEspectaculo, idFestival));
		}

		return funciones;
	}

	/**
	 * Consulta la asistencia de un cliente a funciones
	 * @param id_usuario Id del usuario
	 * @return Mapa con los datos de la consulta
	 * @throws SQLException Si hay error conectandose con la base de datos
	 * @throws Exception Si hay error conviertiendo los datos a usuario.
	 */
	public HashMap<String, Object> consultarAsistenciaCliente(int id_usuario, Date fechaConsulta) throws SQLException, Exception {
		if(darUsuario(id_usuario) == null)
			throw new Exception("Usuario no existe");

		HashMap<String, Object> registro = new HashMap<>();
		DAOTablaFunciones func = new DAOTablaFunciones();
		func.setConnection(this.conn);
		ArrayList<Funcion> yaPasaronOEnCurso = new ArrayList<>();
		ArrayList<Funcion> previstas = new ArrayList<>();

		ArrayList<Funcion> todasFunciones = func.darFuncionesCliente(id_usuario);

		for(Funcion x: todasFunciones) {
			if(x.getFecha().compareTo(fechaConsulta) == 0 || x.getFecha().compareTo(fechaConsulta) < 0)
				yaPasaronOEnCurso.add(x);
			else
				previstas.add(x);
		}


		ArrayList<Funcion> funcionesCanceladas = func.darFuncionesCanceladasCliente(id_usuario);

		// Convertir a array

		Funcion[] arrCanceladas = funcionesCanceladas.toArray(new Funcion[funcionesCanceladas.size()]);
		Funcion[] arrPasaron = yaPasaronOEnCurso.toArray(new Funcion[yaPasaronOEnCurso.size()]);
		Funcion[] arrPrevistas = previstas.toArray(new Funcion[previstas.size()]);

		// Agregar al mapa

		registro.put("Funciones que ya pasaron o en curso", arrPasaron);
		registro.put("Funciones previstas", arrPrevistas);
		registro.put("Funciones canceladas", arrCanceladas);


		return registro;
	}

	// TODO RF10
	/**
	 * compra varias boletas bajo el nombre de un solo usuario
	 * @param arr, arreglo de boletas a ingresar
	 * @return El dinero total a ser reembolsado
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error convirtiendo los datos
	 */

	public void compraMultipleDeBoletas(ArrayList<Boleta> arr)  throws SQLException , Exception
	{
		verificarMismaLocalidad(arr);
		
		DAOTablaBoletas bol = new DAOTablaBoletas();
		bol.setConnection(conn);
		Boleta agregar = null;
		for(int i = 0; i <  arr.size(); i++)
		{
			agregar = arr.get(i);
			bol.addBoleta(agregar);
		}

	}
	//TODO RF14
	/**
	 * Cancela todas las boletas relacionadas con una funciÛn y retorna la cantidad de dinero a ser retornado
	 * @param idFunciÛn Id de la funciÛn a cancerlar
	 * @return El dinero total a ser reembolsado
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error convirtiendo los datos
	 */

	public String cancerlarUnaFuncion(int idFuncion) throws SQLException, Exception
	{
		//repuesta
		String respuesta = "";
		// saldo que se debe reembolsar
		double saldoTotal = 0;
		DAOTablaBoletas bol = new DAOTablaBoletas();
		bol.setConnection(conn);
		//se obtienen todas las boletas de la tabla
		ArrayList<Boleta> arr = bol.darBoletas();

		//objeto que modelar· las boletas a eliminar durante la iteraciÛn
		Boleta borrar = null;
		// se crea un date con la fecha actual en milisegundos
		Date fecha = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha); // Configuramos la el calendario con la fecha actual
		calendar.add(Calendar.DAY_OF_YEAR, 5); // se le suman 5 dÌas a la fecha de cancelaciÛn de la resarva ya que el mÈtodo cancela reservas con 5 dÌas de anterioridad y esta vez de necesitan cancelar funciones que incluso estan en desarrollo
		fecha = (Date) calendar.getTime();
		for(int i = 0; i < arr.size();  i++)
		{ borrar = arr.get(i);
		if(borrar.getId_funcion() == idFuncion)
		{   saldoTotal += borrar.getCosto();
		bol.deleteBoleta(borrar, (Date) fecha);
		}
		}
		respuesta = saldoTotal + "";
		return respuesta;
	}
	//RFC8
	/**
	 *Retorna el estado de todas las ganancias que se le permitan ver al usuario
	 * @param idUsuario Id del usuario a Buscar
	 * @throws SQLException Si hay error conectandose con la base de datos.
	 * @throws Exception Si hay error convirtiendo los datos
	 */
	public void consultarResumenEspectaculos(int idUsuario) throws SQLException, Exception
	{ Usuario temp = darUsuario(idUsuario);
	if(temp.getRol().equals("Administrador"))
	{
		String sql = "SELECT ESPECTACULOS.ID AS idESpectaculo, LOCALIDADES.NOMBRE ,COUNT (LOCALIDADES.NOMBRE)*100/ LOCALIDADES.CAPACIDAD as PorcentajeOcupaciÛn , SUM (LOCALIDADES.COSTO) AS GANANCIA_LOCALIDAD   FROM COMPA—IAS FULL OUTER JOIN CONTRIBUIDORES ON COMPA—IAS.ID =CONTRIBUIDORES.ID_COMPA—IA FULL OUTER JOIN ESPECTACULOS ON ESPECTACULOS.ID = CONTRIBUIDORES.ID_ESPECTACULO  FUll OUTER JOIN FUNCIONES ON CONTRIBUIDORES.ID_ESPECTACULO = FUNCIONES.ID_ESPECTACULO FUll OUTER JOIN RESERVAS ON RESERVAS.ID = FUNCIONES.ID_RESERVA FULL OUTER JOIN SITIOS ON RESERVAS.ID_SITIO = SITIOS.ID FULL OUTER JOIN LOCALIDADES ON LOCALIDADES.ID_SITIO = SITIOS.ID FULL OUTER JOIN SILLAS on LOCALIDADES.ID = SILLAS.ID_LOCALIDAD WHERE COMPA—IAS.ID = 2 AND LOCALIDADES.ID = SILLAS.ID_LOCALIDAD group by LOCALIDADES.NOMBRE, ESPECTACULOS.ID, LOCALIDADES.CAPACIDAD; ";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();


	}

	if(temp.getRol().equals("Cliente"))
	{
		String sql = "SELECT ESPECTACULOS.ID AS idESpectaculo, LOCALIDADES.NOMBRE ,COUNT (LOCALIDADES.NOMBRE)*100/ LOCALIDADES.CAPACIDAD as PorcentajeOcupaciÛn , SUM (LOCALIDADES.COSTO) AS GANANCIA_LOCALIDAD   FROM COMPA—IAS FULL OUTER JOIN CONTRIBUIDORES ON COMPA—IAS.ID =CONTRIBUIDORES.ID_COMPA—IA FULL OUTER JOIN ESPECTACULOS ON ESPECTACULOS.ID = CONTRIBUIDORES.ID_ESPECTACULO  FUll OUTER JOIN FUNCIONES ON CONTRIBUIDORES.ID_ESPECTACULO = FUNCIONES.ID_ESPECTACULO FUll OUTER JOIN RESERVAS ON RESERVAS.ID = FUNCIONES.ID_RESERVA FULL OUTER JOIN SITIOS ON RESERVAS.ID_SITIO = SITIOS.ID FULL OUTER JOIN LOCALIDADES ON LOCALIDADES.ID_SITIO = SITIOS.ID FULL OUTER JOIN SILLAS on LOCALIDADES.ID = SILLAS.ID_LOCALIDAD WHERE COMPA—IAS.ID = ? AND LOCALIDADES.ID = SILLAS.ID_LOCALIDAD group by LOCALIDADES.NOMBRE, ESPECTACULOS.ID, LOCALIDADES.CAPACIDAD; ";
		PreparedStatement prepStmt = conn.prepareStatement(sql);

		prepStmt.setInt(1, idUsuario);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();




	}
	else throw new Exception();
	}
	
	public void verificarMismaLocalidad(ArrayList<Boleta> boletas) throws SQLException, Exception {
		if(boletas.isEmpty())
			throw new Exception("No hay boletas a comprar");

		DAOTablaSillas sillas = new DAOTablaSillas();
		DAOTablaLocalidades local = new DAOTablaLocalidades();
		local.setConnection(this.conn);

		sillas.setConnection(this.conn);
		Silla primera = sillas.darSilla(boletas.get(0).getId_silla());
		int localidad = primera.getIdLocalidad();
		int funcion = boletas.get(0).getId_funcion();

		for(Boleta x : boletas) {
			Silla y = sillas.darSilla(x.getId_silla());

			if(y.getIdLocalidad() != localidad)
				throw new Exception("No todas las sillas tienen misma localidad");
		}

		// Verificar misma funcion

		for(Boleta x: boletas) {
			if(x.getId_funcion() != funcion)
				throw new Exception("No todas las sillas tienen misma funcion");
		}

		// Verificar que haya sillas

		if(local.darTotalDisponibles(localidad, funcion) < boletas.size())
			throw new Exception("No hay la cantidad de sillas disponibles para las boletas");
	}
	
	// Iteracion 4
	
	public ArrayList<HashMap<String, Object>> darAsistencia(int id_compañia, Date fecha1, Date fecha2) throws Exception {
		if(fecha1.after(fecha2))
			throw new Exception("La fecha1 no puede ser despues de la fecha2");
		
		ArrayList<HashMap<String, Object>> x = null;
		
		String sql = "SELECT * FROM ISIS2304B221710.USUARIOS WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		//prepStmt.setInt(1, id);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		
		return x;
		
	}
	
	public ArrayList<HashMap<String, Object>> darAsistenciaVersion2(int id_compañia, Date fecha1, Date fecha2) throws Exception {
		if(fecha1.after(fecha2))
			throw new Exception("La fecha1 no puede ser despues de la fecha2");
		
		ArrayList<HashMap<String, Object>> x = null;
		
		String sql = "SELECT * FROM ISIS2304B221710.USUARIOS WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		//prepStmt.setInt(1, id);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		
		return x;
		
	}
	
	public ArrayList<HashMap<String, Object>> consultarBuenosClientes(int nBoletas) throws Exception {
		if(nBoletas <= 0)
			throw new Exception("nBoletas no puede ser tan pequeño");
		
		ArrayList<HashMap<String, Object>> x = null;
		
		String sql = "SELECT * FROM ISIS2304B221710.USUARIOS WHERE ID = ?";
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		//prepStmt.setInt(1, id);

		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		
		return x;
		
	}
}

