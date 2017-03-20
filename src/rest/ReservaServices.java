package rest;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import tm.ReservaMaster;
import vos.ListaReservas;

@Path("reservas")
public class ReservaServices extends FestivAndesServices {

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
	 * Da las reservas de la base de datos
	 * @return Reservas de la base de datos
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getReservas() {
		ReservaMaster tm = new ReservaMaster(getPath());
		ListaReservas reservas;
		try {
			reservas = tm.darReservas();
		} catch(Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		
		return Response.status(200).entity(reservas).build();
	}
}
