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
import tm.SitioMaster;
import vos.Compañia;
import vos.ListaSitios;
import vos.Sitio;

@Path("sitios")
public class SitioServices extends FestivAndesServices {

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
	 * Da los sitios de la base de datos
	 * @return Sitios de la base de datos
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getSitios() {
		SitioMaster tm = new SitioMaster(getPath());
		ListaSitios sitios;
		try {
			sitios = tm.darSitios();
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(sitios).build();
	}
	
	/**
	 * Agrega un sitio a la base de datos
	 * @param sitio Sitio a agregar
	 * @return Resultado de intentar agregar el sitio
	 */
	@PUT
	@Path("/sitio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addSitio(Sitio sitio) {
		SitioMaster tm = new SitioMaster(getPath());
		try {
			tm.addSitio(sitio);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(sitio).build();
	}
	
	/**
	 * Actualiza un sitio de la base de datos.
	 * @param sitio Sitio con los nuevos datos.
	 * @return Resultado de intentar actualizar el sitio
	 */
	@POST
	@Path("/sitio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateSitio(Sitio sitio) {
		SitioMaster tm = new SitioMaster(getPath());
		try {
			tm.updateSitio(sitio);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(sitio).build();
	}
	
	/**
	 * Elimina un sitio de la base de datos
	 * @param sitio Sitio a eliminar de la base de datos.
	 * @return Resultado de intentar eliminar el sitio
	 */
	@DELETE
	@Path("/sitio")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteSitio(Sitio sitio) {
		SitioMaster tm = new SitioMaster(getPath());
		try {
			tm.deleteSitio(sitio);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(sitio).build();
	}
}
