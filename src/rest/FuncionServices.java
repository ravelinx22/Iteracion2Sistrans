package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.CompañiaMaster;
import tm.FuncionMaster;
import vos.Compañia;
import vos.Funcion;
import vos.ListaFunciones;

@Path("funciones")
public class FuncionServices extends FestivAndesServices {

	/**
	 * Atributo que usa la anotación @Context para tener el ServletContext de la conexión actual.
	 */
	@Context
	private ServletContext context;
	
	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	// Rest
	
	/**
	 * Da las funciones de la base de datos
	 * @return Funciones de la base de datos.
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getFunciones() {
		FuncionMaster tm = new FuncionMaster(getPath());
		ListaFunciones funciones;
		try {
			funciones = tm.darFunciones();
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(funciones).build();
	}
	
	/**
	 * Agrega una funcion a la base de datos
	 * @param funcion Funcion a agregar
	 * @return Resultado de intentar agregar la funcion 
	 */
	@PUT
	@Path("/funcion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFuncion(Funcion funcion) {
		FuncionMaster tm = new FuncionMaster(getPath());
		try {
			tm.addFuncion(funcion);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(funcion).build();
	}
	
	/**
	 * Actualiza una funcion de la base de datos.
	 * @param funcion Funcion con los nuevos datos.
	 * @return Resultado de intentar actualizar la funcion
	 */
	@POST
	@Path("/funcion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateFuncion(Funcion funcion) {
		FuncionMaster tm = new FuncionMaster(getPath());
		try {
			tm.updateFuncion(funcion);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(funcion).build();
	}
	
	/**
	 * Elimina una funcion de la base de datos
	 * @param funcion Funcion a eliminar de la base de datos.
	 * @return Resultado de intentar eliminar la funcion
	 */
	@DELETE
	@Path("/funcion")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFuncion(Funcion funcion) {
		FuncionMaster tm = new FuncionMaster(getPath());
		try {
			tm.deleteFuncion(funcion);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(funcion).build();
	}
}
