package jms;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

public class FuncionesMDB implements MessageListener, ExceptionListener {

	public final static int TIME_OUT = 5;
	private final static String APP = "D03";

	private final static String GLOBAL_TOPIC_NAME = "java:global/RMQTopicFunciones";
	private final static String LOCAL_TOPIC_NAME = "java:global/RMQFuncionesLocal";

	private final static String REQUEST = "REQUEST";
	private final static String REQUEST_ANSWER = "REQUEST_ANSWER";
	
	private TopicConnection topicConnection;
	private TopicSession topicSession;
	private Topic globalTopic;
	private Topic localTopic;
	
	private List<Funcion> answer = new ArrayList<>();
	
	public FuncionesMDB(TopicConnectionFactory factory, InitialContext ctx) throws JMSException, NamingException {
		topicConnection = factory.createTopicConnection();
		topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		globalTopic = (RMQDestination) ctx.lookup(GLOBAL_TOPIC_NAME);
		TopicSubscriber topicSubscriber = topicSession.createSubscriber(globalTopic);
		topicSubscriber.setMessageListener(this);
		localTopic = (RMQDestination) ctx.lookup(LOCAL_TOPIC_NAME);
		topicSubscriber =  topicSession.createSubscriber(localTopic);
		topicSubscriber.setMessageListener(this);
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
	
	public ListaFunciones getRemoteFunciones() throws JsonGenerationException, JsonMappingException, JMSException, IOException, Exception, InterruptedException, NoSuchAlgorithmException {
		answer.clear();
		String id = APP+""+System.currentTimeMillis();
		MessageDigest md = MessageDigest.getInstance("MD5");
		id = DatatypeConverter.printHexBinary(md.digest(id.getBytes())).substring(0, 8);
		
		sendMessage("", REQUEST, globalTopic, id);
		boolean waiting = true;
		
		int count = 0;
		while(TIME_OUT != count){
			TimeUnit.SECONDS.sleep(1);
			count++;
		}
		if(count == TIME_OUT){
			if(this.answer.isEmpty()){
				waiting = false;
				throw new Exception("Time Out - No Reply");
			}
		}
		waiting = false;

		if(answer.isEmpty())
			throw new Exception("Non Response");
		
		ListaFunciones func = new ListaFunciones(answer);
		return func;
	}
	
	private void sendMessage(String payload, String status, Topic dest, String id) throws JMSException, JsonGenerationException, JsonMappingException, IOException
	{
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(id);
		ExchangeMsg msg = new ExchangeMsg("aerolineas.general.D03", APP, payload, status, id);
		TopicPublisher topicPublisher = topicSession.createPublisher(dest);
		topicPublisher.setDeliveryMode(DeliveryMode.PERSISTENT);
		TextMessage txtMsg = topicSession.createTextMessage();
		txtMsg.setJMSType("TextMessage");
		String envelope = mapper.writeValueAsString(msg);
		System.out.println(envelope);
		txtMsg.setText(envelope);
		topicPublisher.publish(txtMsg);
	}
	
	@Override
	public void onMessage(Message message) {
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
				if(ex.getStatus().equals(REQUEST))
				{
					FestivAndesDistributed dtm = FestivAndesDistributed.getInstance();

					ListaFunciones funciones = dtm.getLocalFunciones();
					String payload = mapper.writeValueAsString(funciones);
					Topic t = new RMQDestination("", "aerolineas.test", ex.getRoutingKey(), "", false);
					sendMessage(payload, REQUEST_ANSWER, t, id);
				}
				else if(ex.getStatus().equals(REQUEST_ANSWER))
				{
					ListaFunciones v = mapper.readValue(ex.getPayload(), ListaFunciones.class);
					answer.addAll(v.getFunciones());
				}
			}

		} 
		catch (JMSException e) 
		{
			e.printStackTrace();
		} 
		catch (JsonParseException e)
		{
			e.printStackTrace();
		} 
		catch (JsonMappingException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void onException(JMSException arg0) {
		System.out.println("Hubo una exception");
	}
}
