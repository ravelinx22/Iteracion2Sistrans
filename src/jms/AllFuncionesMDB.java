package jms;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.jms.DeliveryMode;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.bind.DatatypeConverter;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import com.rabbitmq.jms.admin.RMQDestination;
import dtm.FestivAndesDistributed;
import excVos.ExchangeMsg;
import vos.Funcion;
import vos.ListaFunciones;
import vos.ListaRentabilidad;
import vos.Rentabilidad;

public class AllFuncionesMDB implements MessageListener, ExceptionListener 
{
	public final static int TIME_OUT = 60;
	private final static String APP = "app1";
	
	private final static String GLOBAL_TOPIC_NAME_FUNCIONES = "java:global/RMQTopicAllFunciones";
	private final static String LOCAL_TOPIC_NAME_FUNCIONES = "java:global/RMQAllFuncionesLocal";
	private final static String GLOBAL_TOPIC_NAME_RENT = "java:global/RMQTopicRentabilidad";
	private final static String LOCAL_TOPIC_NAME_RENT = "java:global/RMQRentabilidadLocal";
	
	private final static String REQUEST_FUNCIONES = "REQUEST_FUNC";
	private final static String REQUEST_ANSWER_FUNCIONES = "REQUEST_ANSWER_FUNC";
	
	private final static String REQUEST_RENT = "REQUEST_RENT";
	private final static String REQUEST_ANSWER_RENT = "REQUEST_ANSWER_RENT";
	
	private TopicConnection topicConnection;
	private TopicSession topicSession;
	
	private Topic globalTopicFunciones;
	private Topic localTopicFunciones;
	
	private Topic globalTopicRent;
	private Topic localTopicRent;
	
	private List<Funcion> answer = new ArrayList<Funcion>();
	private List<Rentabilidad> answerRent = new ArrayList<Rentabilidad>();
	
	public AllFuncionesMDB(TopicConnectionFactory factory, InitialContext ctx) throws JMSException, NamingException 
	{	
		topicConnection = factory.createTopicConnection();
		topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		globalTopicFunciones = (RMQDestination) ctx.lookup(GLOBAL_TOPIC_NAME_FUNCIONES);
		globalTopicRent = (RMQDestination) ctx.lookup(GLOBAL_TOPIC_NAME_RENT);
		
		TopicSubscriber topicSubscriber =  topicSession.createSubscriber(globalTopicFunciones);
		topicSubscriber.setMessageListener(this);
		
		TopicSubscriber topicSubscripberRent = topicSession.createSubscriber(globalTopicRent);
		topicSubscripberRent.setMessageListener(this);
		
		localTopicFunciones = (RMQDestination) ctx.lookup(LOCAL_TOPIC_NAME_FUNCIONES);
		localTopicRent = (RMQDestination) ctx.lookup(LOCAL_TOPIC_NAME_RENT);
		
		topicSubscriber =  topicSession.createSubscriber(localTopicFunciones);
		topicSubscriber.setMessageListener(this);
		
		topicSubscripberRent = topicSession.createSubscriber(localTopicRent);
		topicSubscripberRent.setMessageListener(this);
		
		topicConnection.setExceptionListener(this);
	}
	
	public void start() throws JMSException
	{
		topicConnection.start();
	}
	
	public void close() throws JMSException
	{
		topicSession.close();
		topicConnection.close();
	}
	
	public ListaFunciones getRemoteFunciones() throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		answer.clear();
		String id = APP+""+System.currentTimeMillis();
		MessageDigest md = MessageDigest.getInstance("MD5");
		id = DatatypeConverter.printHexBinary(md.digest(id.getBytes())).substring(0, 8);
		
		sendMessage("", REQUEST_FUNCIONES, globalTopicFunciones, id, null, null, null);
		boolean waiting = true;

		int count = 0;
		while(TIME_OUT != count){
			TimeUnit.SECONDS.sleep(1);
			count++;
		}
		if(count == TIME_OUT){
			if(this.answer.isEmpty()){
				waiting = false;
				throw new NonReplyException("Time Out - No Reply");
			}
		}
		waiting = false;
		
		if(answer.isEmpty())
			throw new NonReplyException("Non Response");
		ListaFunciones res = new ListaFunciones(answer);
        return res;
	}
	
	public ListaRentabilidad getRemoteRentabilidad(Date fechaI, Date fechaF, int idCompañia) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		answerRent.clear();
		String id = APP+""+System.currentTimeMillis();
		MessageDigest md = MessageDigest.getInstance("MD5");
		id = DatatypeConverter.printHexBinary(md.digest(id.getBytes())).substring(0, 8);
		
		String dato = fechaI.toString()+";;;"+fechaF.toString()+";;;"+idCompañia;
		sendMessage(dato, REQUEST_RENT, globalTopicRent, id, idCompañia, fechaI, fechaF);
		boolean waiting = true;

		int count = 0;
		while(TIME_OUT != count){
			TimeUnit.SECONDS.sleep(1);
			count++;
		}
		if(count == TIME_OUT){
			if(this.answerRent.isEmpty()){
				waiting = false;
				throw new NonReplyException("Time Out - No Reply");
			}
		}
		waiting = false;
		
		if(answerRent.isEmpty())
			throw new NonReplyException("Non Response");
		ListaRentabilidad res = new ListaRentabilidad(answerRent);
        return res;
	}
	
	
	private void sendMessage(String payload, String status, Topic dest, String id, Integer id_compañia, Date fechaI, Date fechaF) throws JMSException, JsonGenerationException, JsonMappingException, IOException
	{
		if(status.equals(REQUEST_FUNCIONES) || status.equals(REQUEST_ANSWER_FUNCIONES)) {
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(id);
			ExchangeMsg msg = new ExchangeMsg("funciones.general.app1", APP, payload, status, id);
			TopicPublisher topicPublisher = topicSession.createPublisher(dest);
			topicPublisher.setDeliveryMode(DeliveryMode.PERSISTENT);
			TextMessage txtMsg = topicSession.createTextMessage();
			txtMsg.setJMSType("TextMessage");
			String envelope = mapper.writeValueAsString(msg);
			System.out.println(envelope);
			txtMsg.setText(envelope);
			topicPublisher.publish(txtMsg);
		} else if(status.equals(REQUEST_RENT) || status.equals(REQUEST_ANSWER_RENT)) {
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(id);
			ExchangeMsg msg = new ExchangeMsg("rentabilidad.general.app1", APP, payload, status, id);
			TopicPublisher topicPublisher = topicSession.createPublisher(dest);
			topicPublisher.setDeliveryMode(DeliveryMode.PERSISTENT);
			TextMessage txtMsg = topicSession.createTextMessage();
			txtMsg.setJMSType("TextMessage");
			String envelope = mapper.writeValueAsString(msg);
			System.out.println(envelope);
			txtMsg.setText(envelope);
			topicPublisher.publish(txtMsg);
		}
	}
	
	@Override
	public void onMessage(Message message) 
	{
		TextMessage txt = (TextMessage) message;
		try 
		{
			String body = txt.getText();
			System.out.println(body);
			ObjectMapper mapper = new ObjectMapper();
			ExchangeMsg ex = mapper.readValue(body, ExchangeMsg.class);
			String id = ex.getMsgId();
			System.out.println(ex.getSender());
			System.out.println(ex.getStatus());
			if(!ex.getSender().equals(APP))
			{
				if(ex.getStatus().equals(REQUEST_FUNCIONES))
				{
					FestivAndesDistributed dtm = FestivAndesDistributed.getInstance();
					ListaFunciones funciones = dtm.getLocalFunciones();
					String payload = mapper.writeValueAsString(funciones);
					Topic t = new RMQDestination("", "funciones.test", ex.getRoutingKey(), "", false);
					sendMessage(payload, REQUEST_ANSWER_FUNCIONES, t, id, null, null, null);
				}
				else if(ex.getStatus().equals(REQUEST_ANSWER_FUNCIONES))
				{
					ListaFunciones v = mapper.readValue(ex.getPayload(), ListaFunciones.class);
					answer.addAll(v.getFunciones());
				} else if(ex.getStatus().equals(REQUEST_RENT)) {
					FestivAndesDistributed dtm = FestivAndesDistributed.getInstance();
					String[] datos = ex.getPayload().split(";;;");
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					
					java.util.Date parsed1 = format.parse(datos[0]);
					Date fechaI = new Date(parsed1.getTime());
					
					java.util.Date parsed2 = format.parse(datos[1]);
					Date fechaF = new Date(parsed2.getTime());
					
					Integer idCompañia = Integer.parseInt(datos[2]);
					
					ListaRentabilidad rent = dtm.getLocalRentabilidad(fechaI, fechaF, idCompañia);
					String payload = mapper.writeValueAsString(rent);
					Topic t = new RMQDestination("", "rentabilidad.test", ex.getRoutingKey(), "", false);
					sendMessage(payload, REQUEST_ANSWER_RENT, t, id, null, null, null);			
				} else if(ex.getStatus().equals(REQUEST_ANSWER_RENT)) {
					ListaRentabilidad v = mapper.readValue(ex.getPayload(), ListaRentabilidad.class);
					answerRent.addAll(v.getRentabilidad());
				}
			}
			
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onException(JMSException exception) 
	{
		System.out.println(exception);
	}
}
