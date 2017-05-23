package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRentabilidad {

	@JsonProperty(value="rentabilidad")
	
	private List<Object> rentabilidad;
	
	public ListaRentabilidad(@JsonProperty(value="rentabilidad") List<Object> rentabilidad){
		this.rentabilidad = rentabilidad;
	}

	public List<Object> getRentabilidad() {
		return rentabilidad;
	}

	public void setRentabilidad(List<Object> rentabilidad) {
		this.rentabilidad = rentabilidad;
	}
}
