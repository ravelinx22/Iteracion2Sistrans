package app3;

import org.codehaus.jackson.annotate.JsonProperty;

public class Rentabilidad3 {
	
	@JsonProperty(value="id")
	private Long id;
	
	@JsonProperty(value="obra")
	private String obra;

	//obra espacio, procentaje ocupacion funcion regristrados no registrados total vendidas,ganancias
	
	@JsonProperty(value="espacio")
	private String espacio;
	
	@JsonProperty(value="porcentaje")
	private Double porcentaje;
	
	@JsonProperty(value="funcion")
	private String funcion;

	@JsonProperty(value="registrados")
	private Integer registrados;
	
	@JsonProperty(value="noRegistrados")
	private Integer noRegistrados;
	
	@JsonProperty(value="totalVendidas")
	private Integer  totalVendidas;
	
	@JsonProperty(value="ganancias")
	private Double ganancias;
	
	public Rentabilidad3(	
			@JsonProperty(value="id")Long id,
			@JsonProperty(value="obra") String obra,
			@JsonProperty(value="espacio")String espacio,
			@JsonProperty(value="porcentaje")Double porcentaje,
			@JsonProperty(value="funcion")String funcion,
			@JsonProperty(value="registrados")Integer registrados,
			@JsonProperty(value="noRegistrados")Integer noRegistrados,
			@JsonProperty(value="totalVendidas")Integer  totalVendidas,
			@JsonProperty(value="ganancias")Double ganancias)
	{
		this.id = id;
		this.obra = obra;
		this.espacio = espacio;
		this.porcentaje = porcentaje;
		this.funcion = funcion;
		this.registrados = registrados;
		this.noRegistrados = noRegistrados;
		this.totalVendidas = totalVendidas;
		this.ganancias = ganancias;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getObra() {
		return obra;
	}

	public void setObra(String obra) {
		this.obra = obra;
	}

	public String getEspacio() {
		return espacio;
	}

	public void setEspacio(String espacio) {
		this.espacio = espacio;
	}

	public Double getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(Double porcentaje) {
		this.porcentaje = porcentaje;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public Integer getRegistrados() {
		return registrados;
	}

	public void setRegistrados(Integer registrados) {
		this.registrados = registrados;
	}

	public Integer getNoRegistrados() {
		return noRegistrados;
	}

	public void setNoRegistrados(Integer noRegistrados) {
		this.noRegistrados = noRegistrados;
	}

	public Integer getTotalVendidas() {
		return totalVendidas;
	}

	public void setTotalVendidas(Integer totalVendidas) {
		this.totalVendidas = totalVendidas;
	}

	public Double getGanancias() {
		return ganancias;
	}

	public void setGanancias(Double ganancias) {
		this.ganancias = ganancias;
	}
}
