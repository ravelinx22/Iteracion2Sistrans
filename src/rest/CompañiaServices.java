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
import tm.UsuarioMaster;
import vos.Compañia;
import vos.ListaCompañias;
import vos.ListaRentabilidad;

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
	
	@POST
	@Path("/{id1}/retirar/{id2}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response retirarCompañia(@PathParam("id1") int idCompañia, @PathParam("id2") int idUsuario) {
		CompañiaMaster tm = new CompañiaMaster(getPath());
		UsuarioMaster us = new UsuarioMaster(getPath());
		
		HashMap<String, String> respuesta = new HashMap<>();

		try {
			if(!us.esAdministrador(idUsuario))
				throw new Exception("El usuario no es administrador");
			
			tm.retirarCompañia(idCompañia);
			respuesta.put("Resultado", "Realizada");
		} catch (Exception e) {
			respuesta.put("Resultado", "Rechazada");
			respuesta.put("Error", doErrorMessage(e));
			return Response.status(500).entity(respuesta).build();
		}
		return Response.status(200).entity(respuesta).build();
	}

	@GET
	@Path("/{id}/rentabilidad/{fecha1}/{fecha2}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response darRentabilidad(@PathParam("id") int id, @PathParam("fecha1") Date fecha1, @PathParam("fecha2") Date fecha2) {
		CompañiaMaster tm = new CompañiaMaster(getPath());
		ListaRentabilidad rentabilidad;
		try {
			rentabilidad = tm.darRentabilidad(fecha1, fecha2, id);
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(rentabilidad).build();
	}
}
