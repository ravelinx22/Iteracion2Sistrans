package rest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;
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
import tm.UsuarioMaster;
import vos.Boleta;
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
	 * Agrega un cliente a la base de datos
	 * @param id Id del usuario administrador que registra el cliente
	 * @param usuario Cliente que se va a registrar
	 * @return Resultado de intentar agregar el usuario.
	 */
	@PUT
	@Path("/{id}/cliente")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCliente(@PathParam("id") int id, Usuario usuario) {
		UsuarioMaster tm = new UsuarioMaster(getPath());
		try {
			Usuario x = tm.darUsuario(id);

			if(!x.getRol().equalsIgnoreCase("Administrador"))
				throw new Exception("Tiene que registrar cliente desde una cuenta administrador");

			usuario.setRol("Cliente");
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

	/**
	 * Consulta la asistencia del usuario
	 * @param usuario Usuario a consultar asistencia.
	 * @return Resultado de intentar consultar registro asistencia de usuario.
	 */
	@GET
	@Path("/{id_admin}/asistencia/{id}/fecha/{fecha}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteUsuario(@PathParam("id_admin") int id_admin, @PathParam("id") int id_usuario, @PathParam("fecha") Date fecha) {
		UsuarioMaster tm = new UsuarioMaster(getPath());
		Map<String, Object> registro;
		try {
			Usuario x = tm.darUsuario(id_admin);

			if(x == null)
				throw new Exception("Usuario no existe");
			else if(!x.getRol().equalsIgnoreCase("Administrador") && id_admin != id_usuario)
				throw new Exception("Solo puede consultar otro cliente si es administrador");

			registro = tm.consultarAsistencia(id_usuario, fecha);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(registro).build();
	}

	@POST
	@Path("/variasBoletas")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response  compraMultipleDeBoletas(ArrayList<Boleta> arr)  {
		UsuarioMaster tm = new UsuarioMaster(getPath());
		try {
			tm.compraMultipleDeBoletas(arr);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(arr).build();
	}
	/**
	 * Elimina una funciÛn de la base de datos
	 * @param usuario Usuario que intenta eliminar la funciÛn eliminar de la base de datos.
	 * @return Resultado de intentar eliminar la funciÛn
	 */
	@DELETE
	@Path("/usuario/cancelarFuncion/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancerlarUnaFuncion(@PathParam("id") int idFuncion) {
		UsuarioMaster tm = new UsuarioMaster(getPath());
		try {
			tm.cancerlarUnaFuncion(idFuncion);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(idFuncion).build();
	}

	/**
	 * Consulta de la base de datos los resultados de las bases de datos
	 * @param usuario Usuario que ejecuta la consulta
	 * @return resultado de la busqueda
	 */
	@GET
	@Path("/usuario/consultaEspectaculosResumen/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response  consultarResumenEspectaculos(@PathParam("id") int id) {
		UsuarioMaster tm = new UsuarioMaster(getPath());
		try {
			tm.consultarResumenEspectaculos(id);
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

		return Response.status(200).entity(id).build();
	}

}
