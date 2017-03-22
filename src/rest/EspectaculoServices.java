package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.CompañiaMaster;
import tm.EspectaculoMaster;
import tm.UsuarioMaster;
import vos.Compañia;
import vos.Espectaculo;
import vos.ListaEspectaculos;

@Path("espectaculos")
public class EspectaculoServices extends FestivAndesServices {

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
	 * Da los espectaculos de la base de datos
	 * @return Espectaculos de la base de datos
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEspectaculos() {
		EspectaculoMaster tm = new EspectaculoMaster(getPath());
		ListaEspectaculos espectaculos;
		try {
			espectaculos = tm.darEspectaculos();
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(espectaculos).build();
	}
	
	/**
	 * Agrega un espectaculo a la base de datos
	 * @param espectaculo Espectaculo a agregar
	 * @return Resultado de intentar agregar el espectaculo
	 */
	@PUT
	@Path("/{id}/espectaculo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEspectaculo(Espectaculo espectaculo, @PathParam("id") int idAdmin) {
		EspectaculoMaster tm = new EspectaculoMaster(getPath());
		UsuarioMaster um = new UsuarioMaster(getPath());
		try {
			if(!um.darUsuario(idAdmin).getRol().equalsIgnoreCase("Administrador"))
				throw new Exception("Tiene que crear el espectaculo en una cuenta administrador");
			
			tm.addEspectaculo(espectaculo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(espectaculo).build();
	}
	
	/**
	 * Actualiza un espectaculo de la base de datos.
	 * @param espectaculo Espectaculo con los nuevos datos.
	 * @return Resultado de intentar actualizar el espectaculo
	 */
	@POST
	@Path("/espectaculo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateEspectaculo(Espectaculo espectaculo) {
		EspectaculoMaster tm = new EspectaculoMaster(getPath());
		try {
			tm.updateEspectaculo(espectaculo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(espectaculo).build();
	}
	
	/**
	 * Elimina un espectaculo de la base de datos
	 * @param espectaculo Espectaculo a eliminar de la base de datos.
	 * @return Resultado de intentar eliminar el espectaculo
	 */
	@DELETE
	@Path("/espectaculo")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteEspectaculo(Espectaculo espectaculo) {
		EspectaculoMaster tm = new EspectaculoMaster(getPath());
		try {
			tm.deleteEspectaculo(espectaculo);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(espectaculo).build();
	}
}
