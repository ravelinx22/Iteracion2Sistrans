package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ExchangeMsg {

	@JsonProperty(value="queue")
	private String queue;
	
	@JsonProperty(value="payload")
	private String payload;
	
	@JsonProperty(value="status")
	private String status;
	
	public ExchangeMsg(@JsonProperty(value="queue") String queue, @JsonProperty(value="payload") String payload, @JsonProperty(value="status") String status) {
		this.queue = queue;
		this.payload = payload;
		this.status = status;
	}
	
	public String getQueue() {
		return this.queue;
	}
	
	public String getPayload() {
		return this.payload;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setQueue(String queue) {
		this.queue = queue;
	}
	
	public void setPayload(String payload) {
		this.payload = payload;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
}
