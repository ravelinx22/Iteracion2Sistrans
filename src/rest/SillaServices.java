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
import tm.SillaMaster;
import vos.Compañia;
import vos.ListaSillas;
import vos.Silla;

@Path("sillas")
public class SillaServices extends FestivAndesServices {

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
	 * Da las sillas de la base de datos
	 * @return Sillas de la base de datos
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getSillas() {
		SillaMaster tm = new SillaMaster(getPath());
		ListaSillas sillas;
		try {
			sillas = tm.darSillas();
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(sillas).build();
	}
	
	/**
	 * Agrega una silla a la base de datos
	 * @param silla Silla a agregar
	 * @return Resultado de intentar agregar la silla
	 */
	@PUT
	@Path("/silla")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSilla(Silla silla) {
		SillaMaster tm = new SillaMaster(getPath());
		try {
			tm.addSilla(silla);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(silla).build();
	}
	
	/**
	 * Actualiza una silla de la base de datos.
	 * @param silla Silla con los nuevos datos.
	 * @return Resultado de intentar actualizar la silla
	 */
	@POST
	@Path("/silla")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSilla(Silla silla) {
		SillaMaster tm = new SillaMaster(getPath());
		try {
			tm.updateSilla(silla);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(silla).build();
	}
	
	/**
	 * Elimina una silla de la base de datos
	 * @param silla Silla a eliminar de la base de datos.
	 * @return Resultado de intentar eliminar la silla
	 */
	@DELETE
	@Path("/silla")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSilla(Silla silla) {
		SillaMaster tm = new SillaMaster(getPath());
		try {
			tm.deleteSilla(silla);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(silla).build();
	}
}
