package app3;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaRentabilidad3 {
	@JsonProperty(value="rentabilidad")
	private List<Rentabilidad3> rentabilidad;
	
	
	public ListaRentabilidad3(@JsonProperty(value="rentabilidad")
	List<Rentabilidad3> rentabilidad){
		this.rentabilidad = rentabilidad;
	}

	
	public List<Rentabilidad3> getRentabilidad() {
		return  rentabilidad;
	}

	
	public void setRentabilidad(List<Rentabilidad3> Localidad) {
		this.rentabilidad = Localidad;
	}
}
