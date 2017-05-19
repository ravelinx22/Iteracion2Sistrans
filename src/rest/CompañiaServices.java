package rest;

import java.sql.Date;
import java.util.HashMap;

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
import vos.Compañia;
import vos.ListaCompañias;

@Path("compañias")
public class CompañiaServices extends FestivAndesServices {


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
	 * Da las compañias de la base de datos
	 * @return Compañias de la base de datos
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getCompañias() {
		CompañiaMaster tm = new CompañiaMaster(getPath());
		ListaCompañias compañias;
		try {
			compañias = tm.darCompañias();
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}

		return Response.status(200).entity(compañias).build();
	}

	/**
	 * Agrega una compañia a la base de datos
	 * @param compañia Compañia a agregar
	 * @return Resultado de intentar agregar la compañia
	 */
	@PUT
	@Path("/compañia")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCompañia(Compañia compañia) {
		CompañiaMaster tm = new CompañiaMaster(getPath());
		try {
			tm.addCompañia(compañia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(compañia).build();
	}

	/**
	 * Actualiza una compañia de la base de datos.
	 * @param compañia Compañia con los nuevos datos.
	 * @return Resultado de intentar actualizar la compañia
	 */
	@POST
	@Path("/compañia")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateCompañia(Compañia compañia) {
		CompañiaMaster tm = new CompañiaMaster(getPath());
		try {
			tm.updateCompañia(compañia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(compañia).build();
	}

	/**
	 * Elimina una compañia de la base de datos
	 * @param compañia Compañia a eliminar de la base de datos.
	 * @return Resultado de intentar eliminar la compañia
	 */
	@DELETE
	@Path("/compañia")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCompañia(Compañia compañia) {
		CompañiaMaster tm = new CompañiaMaster(getPath());
		try {
			tm.deleteCompañia(compañia);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(compañia).build();
	}

	// ITERACION 5

	@DELETE
	@Path("/{idCompañia}/retirar/usuario/{idUsuario}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retirarCompañia(@PathParam("idCompañia") int idCompañia, @PathParam("idUsuario") int idUsuario) {
		CompañiaMaster tm = new CompañiaMaster(getPath());
		HashMap<String, String> respuesta = new HashMap<>();

		try {
			tm.retirarCompañia(idCompañia, idUsuario);
			respuesta.put("Resultado", "Realizada");
		} catch (Exception e) {
			respuesta.put("Resultado", "Rechazada");
			respuesta.put("Error", doErrorMessage(e));
			return Response.status(500).entity(respuesta).build();
		}
		return Response.status(200).entity(respuesta).build();
	}
	
	@GET
	@Path("/{idCompañia}/rentabilidad/inicio/{fecha1}/final/{fecha2}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response darRentabilidad(@PathParam("idCompañia") int idCompañia, @PathParam("fecha1") Date inicioF, @PathParam("fecha2") Date finalF) {
		CompañiaMaster tm = new CompañiaMaster(getPath());
		
		return null;
	}
}
