package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Rentabilidad {

	@JsonProperty(value="id_compañia")
	private int id_compañia;
	
	@JsonProperty(value="numero_boletas")
	private int numero_boletas;
	
	@JsonProperty(value="asistentes_funcion")
	private int asistentes_funcion;
	
	@JsonProperty(value="valor_facturado")
	private double valor_facturado;
	
	@JsonProperty(value="prop_asist_cap")
	private double prop_asist_cap;
	
	public Rentabilidad(@JsonProperty(value="id_compañia") int id_compañia, @JsonProperty(value="numero_boletas") int numero_boletas, 	@JsonProperty(value="asistentes_funcion") int asistentes_funcion, 	@JsonProperty(value="valor_facturado") double valor_facturado, 	@JsonProperty(value="prop_asist_cap") double prop_asist_cap ) {
		this.id_compañia = id_compañia;
		this.numero_boletas = numero_boletas;
		this.asistentes_funcion = asistentes_funcion;
		this.valor_facturado = valor_facturado;
		this.prop_asist_cap = prop_asist_cap;
	}

	public int getId_compañia() {
		return id_compañia;
	}

	public void setId_compañia(int id_compañia) {
		this.id_compañia = id_compañia;
	}

	public int getNumero_boletas() {
		return numero_boletas;
	}

	public void setNumero_boletas(int numero_boletas) {
		this.numero_boletas = numero_boletas;
	}

	public int getAsistentes_funcion() {
		return asistentes_funcion;
	}

	public void setAsistentes_funcion(int asistentes_funcion) {
		this.asistentes_funcion = asistentes_funcion;
	}

	public double getValor_facturado() {
		return valor_facturado;
	}

	public void setValor_facturado(double valor_facturado) {
		this.valor_facturado = valor_facturado;
	}

	public double getProp_asist_cap() {
		return prop_asist_cap;
	}

	public void setProp_asist_cap(double prop_asist_cap) {
		this.prop_asist_cap = prop_asist_cap;
	}
}
