package tm;

import java.sql.SQLException;
import java.util.ArrayList;
import dao.DAOTablaBoletas;
import dao.DAOTablaFunciones;
import dtm.FestivAndesDistributed;
import jms.NonReplyException;
import vos.Funcion;
import vos.ListaFunciones;

public class FuncionMaster extends FestivAndesMaster {
		
	private FestivAndesDistributed dtm;
	
	/**
	 * Método constructor de la clase FestivAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * @param contextPath path absoluto en el servidor del contexto del deploy actual.
	 */
	public FuncionMaster(String contextPath) {
		super(contextPath);
		System.out.println("Instancing DTM...");
		dtm = FestivAndesDistributed.getInstance(this);
		System.out.println("Done!");
	}

	// Transacciones

	/**
	 * Da las funciones en la base de datos
	 * @return Lista de funciones con la base de datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public ListaFunciones darFunciones() throws Exception {
		ListaFunciones remL = darFuncionesLocal();
		try
		{
			ListaFunciones resp = dtm.getRemoteFunciones();
			System.out.println(resp.getFunciones().size());
			remL.getFunciones().addAll(resp.getFunciones());
		}
		catch(NonReplyException e)
		{
			System.out.println(e.getMessage());
		}
		return remL;
	}
	
	public ListaFunciones darFuncionesLocal() throws Exception {
		ArrayList<Funcion> funciones;
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try 
		{
			//////Transacción
			this.conn = darConexion();
			daoFunciones.setConnection(this.conn);
			funciones = daoFunciones.darFunciones();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return new ListaFunciones(funciones);
	}

	/**
	 * Agrega una funcion a la base de datos
	 * @param funcion Funcion a agregar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void addFuncion(Funcion funcion) throws Exception {
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try {
			this.conn = darConexion();
			daoFunciones.setConnection(conn);
			daoFunciones.addFuncion(funcion);
			conn.commit();
		} catch(SQLException e) {
			this.conn.rollback();
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			this.conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * Actualiza la funcion de la base de datos.
	 * @param funcion Funcion con los nuevos datos
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void updateFuncion(Funcion funcion) throws Exception {
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		try {
			this.conn = darConexion();
			daoFunciones.setConnection(conn);
			daoFunciones.updateFuncion(funcion);
			conn.commit();
		} catch(SQLException e) {
			this.conn.rollback();
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			this.conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	// ITERACION 5
	
	/**
	 * Elimina la funcion de la base de datos.
	 * @param funcion Funcion a eliminar
	 * @throws Exception Si hay problema conectandose con la base de datos.
	 */
	public void deleteFuncion(Funcion funcion) throws Exception {
		DAOTablaFunciones daoFunciones = new DAOTablaFunciones();
		DAOTablaBoletas daoBoletas = new DAOTablaBoletas();
		try {
			this.conn = darConexion();
			comienzoTransaccion(this.conn);
			daoFunciones.setConnection(conn);
			daoBoletas.setConnection(conn);
			
			daoFunciones.deleteFuncion(funcion);
			daoBoletas.deleteBoletasDeFuncion(funcion.getId());
			
			finalTransaccion(this.conn);
		} catch(SQLException e) {
			this.conn.rollback();
			e.printStackTrace();
			throw e;
		} catch(Exception e) {
			this.conn.rollback();
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoFunciones.cerrarRecursos();
				if(this.conn != null)
					this.conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
				throw e;
			}
		}
	}
}
