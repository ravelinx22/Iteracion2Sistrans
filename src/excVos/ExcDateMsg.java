package excVos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ExcDateMsg {
	@JsonProperty(value="routingkey")
	private String routingKey;
	
	@JsonProperty(value="sender")
	private String sender;
	
	@JsonProperty(value="payload")
	private String payload;
	
	@JsonProperty(value="status")
	private String status;
	
	@JsonProperty(value="msgId")
	private String msgId;
	
	@JsonProperty(value="id_comp")
	private int id_comp;
	
	@JsonProperty(value="fechaInicio")
	private Date fechaInicio;
	
	@JsonProperty(value="fechaFinal")
	private Date fechaFinal;
	
	public ExcDateMsg(@JsonProperty(value="routingkey") String queue, @JsonProperty(value="sender") String sender, @JsonProperty(value="payload") String payload, 
						@JsonProperty(value="status") String status, @JsonProperty(value="msgId") String msgId, @JsonProperty(value="id_comp") int id_comp, @JsonProperty(value="fechaInicio") Date fechaInicio, @JsonProperty(value="fechaFinal") Date fechaFinal) 
	{
		this.routingKey = queue;
		this.sender = sender;
		this.payload = payload;
		this.status = status;
		this.msgId = msgId;
		this.id_comp = id_comp;
		this.fechaInicio = fechaInicio;
		this.fechaFinal = fechaFinal;
	}
	

	public int getId_comp() {
		return id_comp;
	}


	public void setId_comp(int id_comp) {
		this.id_comp = id_comp;
	}


	public Date getFechaInicio() {
		return fechaInicio;
	}


	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	public Date getFechaFinal() {
		return fechaFinal;
	}


	public void setFechaFinal(Date fechaFinal) {
		this.fechaFinal = fechaFinal;
	}


	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getRoutingKey() {
		return routingKey;
	}

	public void setRoutingKey(String queue) {
		this.routingKey = queue;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getSender() {
		return sender;
	}


	public void setSender(String sender) {
		this.sender = sender;
	}


	public String getMsgId() {
		return msgId;
	}


	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
}
