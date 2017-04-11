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

import tm.FestivalMaster;
import tm.FuncionMaster;
import vos.Festival;
import vos.Funcion;
import vos.ListaFestivales;
import vos.ListaFunciones;

@Path("festivales")
public class FestivalServices extends FestivAndesServices {
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
	 * Da los festivales de la base de datos
	 * @return Festivales de la base de datos.
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getFunciones() {
		FestivalMaster tm = new FestivalMaster(getPath());
		ListaFestivales festivales;
		try {
			festivales = tm.darFestivales();
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(festivales).build();
	}
	
	/**
	 * Agrega un festival a la base de datos
	 * @param festival Festival a agregar
	 * @return Resultado de intentar agregar el festival 
	 */
	@PUT
	@Path("/festival")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addFestival(Festival festival) {
		FestivalMaster tm = new FestivalMaster(getPath());
		try {
			tm.addFestival (festival);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(festival).build();
	}
	
	/**
	 * Actualiza un festival de la base de datos.
	 * @param festival Festival con los nuevos datos.
	 * @return Resultado de intentar actualizar el festival
	 */
	@POST
	@Path("/festival")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateFuncion(Festival festival) {
		FestivalMaster tm = new FestivalMaster(getPath());
		try {
			tm.updateFestival(festival);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(festival).build();
	}
	
	/**
	 * Elimina un festival de la base de datos
	 * @param festival Festival a eliminar de la base de datos.
	 * @return Resultado de intentar eliminar el festival
	 */
	@DELETE
	@Path("/festival")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteFestival(Festival festival) {
		FestivalMaster tm = new FestivalMaster(getPath());
		try {
			tm.deleteFestival(festival);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(festival).build();
	}
}
