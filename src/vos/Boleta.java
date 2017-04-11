package vos;

import java.util.HashMap;

import org.codehaus.jackson.annotate.JsonProperty;

public class Boleta {

	/**
	 * Id de la boleta
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Id de la funcion de la boleta
	 */
	@JsonProperty(value="id_funcion")
	private int id_funcion;
	
	/**
	 * Id del usuario de la boleta
	 */
	@JsonProperty(value="id_usuario")
	private int id_usuario;
	
	/**
	 * Id de la silla de la boleta
	 */
	@JsonProperty(value="id_silla")
	private int id_silla;
	
	/**
	 * Costo de la boleta 
	 */
	@JsonProperty(value="costo")
	private double costo;
	
	/**
	 * Estado de la compra de la boleta
	 */
	@JsonProperty(value="abono")
	private boolean abono;
	
	/**
	 * Construye una nueva boleta
	 * @param id Id de la boleta
	 * @param id_funcion Id de la funcion de la boleta
	 * @param id_usuario Id del usuario de la boleta
	 * @param id_silla Id de la silla de la boleta
	 * @param costo Costo de la boleta
	 * @param abono Estado de la compra de la boleta
	 */
	public Boleta(@JsonProperty(value="id") int id,@JsonProperty(value="id_funcion") int id_funcion,@JsonProperty(value="id_usuario") int id_usuario,@JsonProperty(value="id_silla") int id_silla, @JsonProperty(value="costo") double costo, @JsonProperty(value="abono") boolean abono) {
		super();
		this.id = id;
		this.id_funcion = id_funcion;
		this.id_usuario = id_usuario;
		this.id_silla = id_silla;
		this.costo = costo;
		this.abono = abono;
	}

	/**
	 * Da el costo de la boleta
	 * @return Costo de la boleta
	 */
	public double getCosto() {
		return costo;
	}
	
	/**
	 * Cambia el costo de la boleta
	 * @param costo Nuevo costo de la boleta
	 */
	public void setCosto(double costo) {
		this.costo = costo;
	}
	
	/**
	 * Da si la boleta hace parte de un abono.
	 * @return True si la boleta hace parte de un abono, false de lo contrario.
	 */
	public boolean getAbono() {
		return abono;
	}
	
	/**
	 * Cambia el estado de abono de la boleta
	 * @param abono Cambia el estado del abono de la boleta.
	 */
	public void setAbono(boolean abono) {
		this.abono = abono;
	}
	
	/**
	 * Id de la boleta
	 * @return Id de la boleta
	 */
	public int getId() {
		return id;
	}

	/**
	 * Cambia el id de la boleta
	 * @param id Nuevo id de la boleta
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Id de la funcion de la boleta
	 * @return Id de la funcion de la boleta
	 */
	public int getId_funcion() {
		return id_funcion;
	}

	/**
	 * Cambia el id de la funcion
	 * @param id_fundacion Nuevo id de la funcion
	 */
	public void setId_funcion(int id_fundacion) {
		this.id_funcion = id_fundacion;
	}

	/**
	 * Id del usuario de la boleta
	 * @return Id del usuario de la boleta
	 */
	public int getId_usuario() {
		return id_usuario;
	}

	/**
	 * Cambia el id del usuario de la boleta
	 * @param id_usuario Nuevo id del usuario de la boleta
	 */
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	/**
	 * Id de la silla de la boleta
	 * @return Id de la silla de la boleta
	 */
	public int getId_silla() {
		return id_silla;
	}

	/**
	 * Cambia el id de la silla de la boleta
	 * @param id_silla Nuevo id de la silla de la boleta
	 */
	public void setId_silla(int id_silla) {
		this.id_silla = id_silla;
	}
	
	/**
	 * Factura de la boleta 
	 * @return Mapa con la factura de la boleta
	 */
	public HashMap<String, Object> darFactura() {
		HashMap<String, Object> map = new HashMap<>();
		map.put("Id usuario", id_usuario);
		map.put("Id funcion", id_funcion);
		map.put("Id silla", id_silla);
		map.put("Valor", costo);
		return map;
	}
}
