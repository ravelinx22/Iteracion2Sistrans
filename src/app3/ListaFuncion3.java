package app3;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ListaFuncion3 {

	@JsonProperty(value="reportes")
	private List<Object> reportes;

	public ListaFuncion3(
			@JsonProperty(value="reportes") List<Object> reportes) {
		this.reportes = reportes;
	}

	public List<Object> getReportes() {
		return reportes;
	}

	public void setReportes(List<Object> reportes) {
		this.reportes = reportes;
	}
}
