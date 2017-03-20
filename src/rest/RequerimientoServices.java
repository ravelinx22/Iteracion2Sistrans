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
import tm.RequerimientoMaster;
import vos.Compañia;
import vos.ListaRequerimientosTenicos;
import vos.RequerimientoTecnico;

@Path("requerimientos")
public class RequerimientoServices extends FestivAndesServices {

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
	 * Da los requerimientos de la base de datos
	 * @return Requerimientos de la base de datos
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getRequerimientos() {
		RequerimientoMaster tm = new RequerimientoMaster(getPath());
		ListaRequerimientosTenicos requerimientos;
		try {
			requerimientos = tm.darRequerimientos();
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(requerimientos).build();
	}
	
	/**
	 * Agrega un requerimiento a la base de datos
	 * @param requerimiento Requerimiento a agregar
	 * @return Resultado de intentar agregar un requerimiento
	 */
	@PUT
	@Path("/requerimiento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addRequerimiento(RequerimientoTecnico requerimiento) {
		RequerimientoMaster tm = new RequerimientoMaster(getPath());
		try {
			tm.addRequerimiento(requerimiento);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(requerimiento).build();
	}
	
	/**
	 * Actualiza un requerimiento de la base de datos.
	 * @param requerimiento Requerimiento con los nuevos datos.
	 * @return Resultado de intentar actualizar el requerimiento
	 */
	@POST
	@Path("/requerimiento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateRequerimiento(RequerimientoTecnico requerimiento) {
		RequerimientoMaster tm = new RequerimientoMaster(getPath());
		try {
			tm.updateRequerimiento(requerimiento);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(requerimiento).build();
	}
	
	/**
	 * Elimina un requerimiento de la base de datos
	 * @param requerimiento Requerimiento a eliminar de la base de datos.
	 * @return Resultado de intentar eliminar el requerimiento
	 */
	@DELETE
	@Path("/requerimiento")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteRequerimiento(RequerimientoTecnico requerimiento) {
		RequerimientoMaster tm = new RequerimientoMaster(getPath());
		try {
			tm.deleteRequerimiento(requerimiento);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(requerimiento).build();
	}
}
