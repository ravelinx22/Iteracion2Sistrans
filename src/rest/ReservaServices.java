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
import tm.ReservaMaster;
import vos.Compañia;
import vos.ListaReservas;
import vos.Reserva;

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
	
	/**
	 * Agrega una reserva a la base de datos
	 * @param reserva Reserva a agregar
	 * @return Resultado de intentar agregar la reserva
	 */
	@PUT
	@Path("/reserva")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addReserva(Reserva reserva) {
		ReservaMaster tm = new ReservaMaster(getPath());
		try {
			tm.addReserva(reserva);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}
	
	/**
	 * Actualiza una reserva de la base de datos.
	 * @param reserva Reserva con los nuevos datos.
	 * @return Resultado de intentar actualizar la reserva
	 */
	@POST
	@Path("/reserva")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateReserva(Reserva reserva) {
		ReservaMaster tm = new ReservaMaster(getPath());
		try {
			tm.updateReserva(reserva);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}
	
	/**
	 * Elimina una reserva de la base de datos
	 * @param reserva Reserva a eliminar de la base de datos.
	 * @return Resultado de intentar eliminar la reserva
	 */
	@DELETE
	@Path("/reserva")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteReserva(Reserva reserva) {
		ReservaMaster tm = new ReservaMaster(getPath());
		try {
			tm.deleteReserva(reserva);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(reserva).build();
	}
}
