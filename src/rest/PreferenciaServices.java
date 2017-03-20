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
import tm.PreferenciaMaster;
import vos.Compañia;
import vos.ListaPreferencias;
import vos.Preferencia;

@Path("preferencias")
public class PreferenciaServices extends FestivAndesServices {

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
	 * Da las preferencias de la base de datos
	 * @return Preferencias de la base de datos
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getPreferencias() {
		PreferenciaMaster tm = new PreferenciaMaster(getPath());
		ListaPreferencias preferencias;
		try {
			preferencias = tm.darPreferencias();
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(preferencias).build();
	}
	
	/**
	 * Agrega una preferencia a la base de datos
	 * @param preferencia Preferencia a agregar
	 * @return Resultado de intentar agregar la preferencia
	 */
	@PUT
	@Path("/preferencia")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPreferencia(Preferencia preferencia) {
		PreferenciaMaster tm = new PreferenciaMaster(getPath());
		try {
			tm.addPreferencia(preferencia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencia).build();
	}
	
	/**
	 * Actualiza una preferencia de la base de datos.
	 * @param preferencia Preferencia con los nuevos datos.
	 * @return Resultado de intentar actualizar la preferencia
	 */
	@POST
	@Path("/preferencia")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCompañia(Preferencia preferencia) {
		PreferenciaMaster tm = new PreferenciaMaster(getPath());
		try {
			tm.updatePreferencia(preferencia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencia).build();
	}
	
	/**
	 * Elimina una preferencia de la base de datos
	 * @param preferencia Preferencia a eliminar de la base de datos.
	 * @return Resultado de intentar eliminar la preferencia
	 */
	@DELETE
	@Path("/preferencia")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePreferencia(Preferencia preferencia) {
		PreferenciaMaster tm = new PreferenciaMaster(getPath());
		try {
			tm.deletePreferencia(preferencia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(preferencia).build();
	}
}
