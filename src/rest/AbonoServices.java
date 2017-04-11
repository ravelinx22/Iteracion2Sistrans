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

import tm.AbonoMaster;
import tm.SillaMaster;
import tm.UsuarioMaster;
import vos.Abono;
import vos.ListaAbonos;
import vos.ListaSillas;
import vos.Silla;
import vos.Usuario;

@Path("/abonos")
public class AbonoServices extends FestivAndesServices {
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
	 * Da los abonos de la base de datos
	 * @return Abonos de la base de datos
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAbonos() {
		AbonoMaster tm = new AbonoMaster(getPath());
		ListaAbonos abonos;
		try {
			abonos = tm.darAbonos();
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(abonos).build();
	}
	
	/**
	 * Agrega un abono a la base de datos
	 * @param abono Abono a agregar
	 * @param id Id del usuario que va a comprar abono
	 * @return Resultado de intentar agregar un abono
	 */
	@PUT
	@Path("/usuario/{id}/abono")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAbono(Abono abono, @PathParam("id") int id) {
		AbonoMaster tm = new AbonoMaster(getPath());
		UsuarioMaster sm = new UsuarioMaster(getPath());
		
		try {
			Usuario x = sm.darUsuario(id);
			if(x == null)
				throw new Exception("El usuario no existe");
			else if(x.getId() != abono.getId_usuario())
				throw new Exception("El abono tiene que ser personal e intransferible");
			
			tm.addAbono(abono);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(abono).build();
	}
	
	/**
	 * Agrega un abono a la base de datos
	 * @param abono Abono a agregar
	 * @return Resultado de intentar agregar un abono
	 */
	@PUT
	@Path("/abono")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAbono(Abono abono) {
		AbonoMaster tm = new AbonoMaster(getPath());
		
		try {
			tm.addAbono(abono);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(abono).build();
	}
	
	
	/**
	 * Actualiza un abono de la base de datos.
	 * @param abono Abno con los nuevos datos.
	 * @return Resultado de intentar actualizar un abono
	 */
	@POST
	@Path("/abono")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateAbono(Abono abono) {
		AbonoMaster tm = new AbonoMaster(getPath());
		try {
			tm.updateAbono(abono);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(abono).build();
	}
	
	/**
	 * Elimina un abono de la base de datos
	 * @param abono Abono a eliminar de la base de datos.
	 * @return Resultado de intentar eliminar un abono
	 */
	@DELETE
	@Path("/abono")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAbono(Abono abono) {
		AbonoMaster tm = new AbonoMaster(getPath());
		try {
			tm.deleteAbono(abono);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(abono).build();
	}
}
