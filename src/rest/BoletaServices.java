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
import tm.BoletaMaster;
import vos.Boleta;
import vos.ListaBoletas;

@Path("boletas")
public class BoletaServices extends FestivAndesServices {
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
	 * Da las boletas de la base de datos
	 * @return Boletas de la base de datos
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getBoletas() {
		BoletaMaster tm = new BoletaMaster(getPath());
		ListaBoletas boletas;
		try {
			boletas = tm.darBoletas();
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(boletas).build();
	}
	
	/**
	 * Agrega una boleta a la base de datos
	 * @param boleta Boleta a agregar
	 * @return Resultado de intentar agregar la boleta
	 */
	@PUT
	@Path("/boleta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addBoleta(Boleta boleta) {
		BoletaMaster tm = new BoletaMaster(getPath());
		try {
			tm.addBoleta(boleta);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(boleta).build();
	}
	
	/**
	 * Actualiza una boleta de la base de datos.
	 * @param boleta Boleta con los nuevos datos.
	 * @return Resultado de intentar actualizar la boleta
	 */
	@POST
	@Path("/boleta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBoleta(Boleta boleta) {
		BoletaMaster tm = new BoletaMaster(getPath());
		try {
			tm.updateBoleta(boleta);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(boleta).build();
	}
	
	/**
	 * Elimina una boleta de la base de datos
	 * @param boleta Boleta a eliminar de la base de datos.
	 * @return Resultado de intentar eliminar la boleta
	 */
	@DELETE
	@Path("/boleta")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCompañia(Boleta boleta) {
		BoletaMaster tm = new BoletaMaster(getPath());
		try {
			tm.deleteBoleta(boleta);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(boleta).build();
	}
}
