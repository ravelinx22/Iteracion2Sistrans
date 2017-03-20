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
import tm.UsuarioMaster;
import vos.Compañia;
import vos.ListaUsuarios;
import vos.Usuario;

@Path("usuarios")
public class UsuarioServices extends FestivAndesServices {

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
	 * Da los usuarios de la base de datos
	 * @return Usuarios de la base de datos
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUsuarios() {
		UsuarioMaster tm = new UsuarioMaster(getPath());
		ListaUsuarios usuarios;
		try {
			usuarios = tm.darUsuarios();
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(usuarios).build();
	}
	
	/**
	 * Agrega un usuario a la base de datos
	 * @param Usuario usuario a agregar
	 * @return Resultado de intentar agregar un usuario
	 */
	@PUT
	@Path("/usuario")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addUsuario(Usuario usuario) {
		UsuarioMaster tm = new UsuarioMaster(getPath());
		try {
			tm.addUsuario(usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuario).build();
	}
	
	/**
	 * Actualiza un usuario de la base de datos.
	 * @param usuario Usuario con los nuevos datos.
	 * @return Resultado de intentar actualizar el usuario
	 */
	@POST
	@Path("/usuario")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateUsuario(Usuario usuario) {
		UsuarioMaster tm = new UsuarioMaster(getPath());
		try {
			tm.updateUsuario(usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuario).build();
	}
	
	/**
	 * Elimina un usuario de la base de datos
	 * @param usuario Usuario a eliminar de la base de datos.
	 * @return Resultado de intentar eliminar el usuario
	 */
	@DELETE
	@Path("/usuario")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUsuario(Usuario usuario) {
		UsuarioMaster tm = new UsuarioMaster(getPath());
		try {
			tm.deleteUsuario(usuario);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(usuario).build();
	}
}
