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
import tm.LocalidadMaster;
import tm.SillaMaster;
import vos.Compañia;
import vos.ListaLocalidades;
import vos.Localidad;
import vos.Silla;

@Path("localidades")
public class LocalidadServices extends FestivAndesServices {

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
	 * Da las localidades de la base de datos
	 * @return Localidades de la base de datos
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getLocalidades() {
		LocalidadMaster tm = new LocalidadMaster(getPath());
		ListaLocalidades localidades;
		try {
			localidades = tm.darLocalidades();
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(localidades).build();
	}
	
	/**
	 * Agrega una localidad a la base de datos
	 * @param localidad Localidad a agregar
	 * @return Resultado de intentar agregar la localidad
	 */
	@PUT
	@Path("/localidad")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addLocalidad(Localidad localidad) {
		LocalidadMaster tm = new LocalidadMaster(getPath());
		SillaMaster sm = new SillaMaster(getPath());
		try {
			tm.addLocalidad(localidad);
			int last = sm.getLastId();
			int lastFila = tm.getLastRow(localidad.getId());
			
			for(int i = 1; i <= localidad.getCapacidad(); i++) {
				Silla s = new Silla(++last, i,lastFila+1, localidad.getId());
				sm.addSilla(s);
			}
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(localidad).build();
	}
	
	/**
	 * Actualiza una localidad de la base de datos.
	 * @param localidad Localidad con los nuevos datos.
	 * @return Resultado de intentar actualizar la localidad
	 */
	@POST
	@Path("/localidad")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateLocalidad(Localidad localidad) {
		LocalidadMaster tm = new LocalidadMaster(getPath());
		try {
			tm.updateLocalidad(localidad);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(localidad).build();
	}
	
	/**
	 * Elimina una localidad de la base de datos
	 * @param localidad Localidad a eliminar de la base de datos.
	 * @return Resultado de intentar eliminar la localidad
	 */
	@DELETE
	@Path("/localidad")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteLocalidad(Localidad localidad) {
		LocalidadMaster tm = new LocalidadMaster(getPath());
		try {
			tm.deleteLocalidad(localidad);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(localidad).build();
	}
}
