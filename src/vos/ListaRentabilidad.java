package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRentabilidad {

	@JsonProperty(value="rentabilidad")
	
	private List<Rentabilidad> rentabilidad;
	
	public ListaRentabilidad(@JsonProperty(value="rentabilidad") List<Rentabilidad> rentabilidad){
		this.rentabilidad = rentabilidad;
	}

	public List<Rentabilidad> getRentabilidad() {
		return rentabilidad;
	}

	public void setRentabilidad(List<Rentabilidad> rentabilidad) {
		this.rentabilidad = rentabilidad;
	}
}
