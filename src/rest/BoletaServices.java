package rest;

import java.sql.Date;
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

import org.codehaus.jackson.annotate.JsonProperty;

import tm.AbonoMaster;
import tm.BoletaMaster;
import tm.UsuarioMaster;
import vos.Abono;
import vos.Boleta;
import vos.ListaBoletas;
import vos.Usuario;

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
	 * Elimina una boleta
	 * @param id Id de la boleta a borrar
	 * @return Resultado de intentar eliminar la boleta
	 */
	@DELETE
	@Path("/{id}/fecha/{fecha}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBoleta(@PathParam("id") int id, @PathParam("fecha") Date fechaEliminacion) {
		Map<String, Object> registro;
		BoletaMaster tm = new BoletaMaster(getPath());
		try {
			Boleta boleta = tm.darBoleta(id);
			registro = boleta.darFactura();
			tm.deleteBoleta(boleta, fechaEliminacion);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(registro).build();
	}

	/**
	 * Elimina una boleta con un usuario registrado
	 * @param id Id de la boleta a borrar
	 * @param id_usuario Id del usuario
	 * @param fechaEliminacion Fecha de eliminacion de la boleta
	 * @return Resultado de intentar eliminar la boleta
	 */
	@DELETE
	@Path("/{id}/usuario/{id_usuario}/fecha/{fecha}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBoleta(@PathParam("id") int id, @PathParam("fecha") Date fechaEliminacion, @PathParam("id_usuario") int id_usuario) {
		Map<String, Object> registro;
		BoletaMaster tm = new BoletaMaster(getPath());
		UsuarioMaster um = new UsuarioMaster(getPath());

		try {
			Usuario x = um.darUsuario(id_usuario);
			Boleta boleta = tm.darBoleta(id);

			if(x == null)
				throw new Exception("El usuario no existe");
			else if(x.getId() != boleta.getId_usuario())
				throw new Exception("Solo puede eliminar sus propias boletas");

			registro = boleta.darFactura();
			tm.deleteBoleta(boleta, fechaEliminacion);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(registro).build();
	}
}
