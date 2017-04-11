package vos;

import java.sql.Date;
import java.util.HashMap;

import org.codehaus.jackson.annotate.JsonProperty;

public class Abono {

	/**
	 * Id del abono
	 */
	@JsonProperty(value="id")
	private int id;
	
	/**
	 * Id del usuario al que pertenece el abono.
	 */
	@JsonProperty(value="id_usuario")
	private int id_usuario;

	/**
	 * Id's de las funciones que se quieren.
	 */
	@JsonProperty(value="lista_funciones")
	private Integer[] lista_funciones;
	
	/**
	 * Id's de las localidades que se quieren.
	 */
	@JsonProperty(value="lista_localidades")
	private Integer[] lista_localidades;
	
	/**
	 * Id del festival al que pertenece el abono
	 */
	@JsonProperty(value="id_festival")
	private int id_festival;
	
	/**
	 * Fecha de compra del abono
	 */
	@JsonProperty(value="fecha_compra")
	private Date fecha_compra;
	
	/**
	 * Crea un abono
	 * @param id Id del abono
	 * @param id_usuario Id del usuario
	 * @param lista_funciones Id's de las funciones
	 * @param lista_localidades Id's de las localidades
	 * @param id_festival Id del festival
	 * @param fecha_compra Fecha de compra del abono
	 */
	public Abono(@JsonProperty(value="id") int id, @JsonProperty(value="id_usuario") int id_usuario, @JsonProperty(value="lista_funciones") Integer[] lista_funciones, @JsonProperty(value="lista_localidades") Integer[] lista_localidades,@JsonProperty(value="id_festival") int id_festival,@JsonProperty(value="fecha_compra") Date fecha_compra) {
		this.id = id;
		this.id_usuario = id_usuario;
		this.lista_funciones = lista_funciones; 
		this.lista_localidades = lista_localidades;
		this.id_festival = id_festival;
		this.fecha_compra = fecha_compra;
	}

	/**
	 * Fecha de compra del abono
	 * @return Fecha de compra del abono
	 */ 
	public Date getFecha_compra() {
		return fecha_compra;
	}

	/**
	 * Cambia la fecha de compra del abono
	 * @param fecha_compra Fecha de compra del abono
	 */
	public void setFecha_compra(Date fecha_compra) {
		this.fecha_compra = fecha_compra;
	}

	/**
	 * Da el id del festival
	 * @return Id del festival
	 */
	public int getId_festival() {
		return id_festival;
	}

	/**
	 * Cambia el id del festival
	 * @param id_festival Nuevo id del festival
	 */
	public void setId_festival(int id_festival) {
		this.id_festival = id_festival;
	}

	/**
	 * Da el id del abono
	 * @return Id del abono
	 */
	public int getId() {
		return id;
	}

	/**
	 * Cambia el id del abono
	 * @param id Nuevo id del abono
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Da el id del usuario
	 * @return Id del usuario
	 */
	public int getId_usuario() {
		return id_usuario;
	}

	/**
	 * Cambia el id del usuario
	 * @param id_usuario Nuevo id del usuario
	 */
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}

	/**
	 * Da la lista de funciones 
	 * @return Id's de las funciones
	 */
	public Integer[] getLista_funciones() {
		return lista_funciones;
	}

	/**
	 * Cambia la lista de funciones
	 * @param lista_funciones Nuevos id's de las funciones
	 */
	public void setLista_funciones(Integer[] lista_funciones) {
		this.lista_funciones = lista_funciones;
	}

	/**
	 * Da la lista de localidades
	 * @return Id's de las localidades
	 */
	public Integer[] getLista_localidades() {
		return lista_localidades;
	}

	/**
	 * Cambia la lista de localidades.
	 * @param lista_localidades Nuevos id's de las localidades,
	 */
	public void setLista_localidades(Integer[] lista_localidades) {
		this.lista_localidades = lista_localidades;
	}
	
	/**
	 * Genera factura del abono
	 * @return Mapa con la informacion del abono
	 */
	public HashMap<String, Object> darFactura() {
		HashMap<String, Object> ob = new HashMap<>();
		ob.put("Id", id);
		ob.put("Id usuario", id_usuario);
		ob.put("Funciones que se compraron", arrToString(lista_funciones));
		ob.put("Lista de localidades que se compraron", arrToString(lista_localidades));
		ob.put("Id festival", id_festival);
		ob.put("Fecha de compra", fecha_compra.toString());
		return ob;
	}
	
	// Helper
	/**
	 * Da la representacion de un arreglo de enteros a String
	 * @param arr Arreglo a representar como String
	 * @return La representacion de un arreglo de enteros a String
	 */
	public String arrToString(Integer[] arr) {
		String x = "[";

		for(int i = 0; i < arr.length; i++) {
			if(i== arr.length-1) {
				x += "y]";
			} else {
				x += "y,";
			}
		}
		
		return x;
	}
}
