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

import app3.ListaFuncion3;
import app3.ListaRentabilidad3;
import dtm.FestivAndesDistributed;
import excVos.ExchangeMsg;
import vos.Abono;
import vos.ListaFunciones;
import vos.ListaRentabilidad;

public class AbonoMDB implements MessageListener, ExceptionListener  {
	public final static int TIME_OUT = 5;
	private final static String APP = "app1";

	// TOPICS CONSTANTS

	private final static String GLOBAL_TOPIC_NAME_ABO = "java:global/RMQTopicAbono";
	private final static String LOCAL_TOPIC_NAME_ABO = "java:global/RMQRAbonoLocal";

	// REQUESTS CONSTANTS

	private final static String REQUEST_ABO = "REQUEST_ABO";
	private final static String REQUEST_ANSWER_ABO = "REQUEST_ANSWER_ABO";

	// TOPIC 

	private Topic globalTopicAbo;
	private Topic localTopicAbo;

	private TopicConnection topicConnection;
	private TopicSession topicSession;

	private String answerAbo = "";

	public AbonoMDB(TopicConnectionFactory factory, InitialContext ctx) throws JMSException, NamingException 
	{	
		topicConnection = factory.createTopicConnection();
		topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		globalTopicAbo = (RMQDestination) ctx.lookup(GLOBAL_TOPIC_NAME_ABO);

		// Crear subscripber global

		TopicSubscriber topicSubscripberAbo = topicSession.createSubscriber(globalTopicAbo);
		topicSubscripberAbo.setMessageListener(this);

		// Buscar context

		localTopicAbo = (RMQDestination) ctx.lookup(LOCAL_TOPIC_NAME_ABO);

		// Crear subscriber local

		topicSubscripberAbo = topicSession.createSubscriber(localTopicAbo);
		topicSubscripberAbo.setMessageListener(this);

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

	public void addAbonoRemote(Abono abono) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException {
		answerAbo = "";
		String id = APP+""+System.currentTimeMillis();
		MessageDigest md = MessageDigest.getInstance("MD5");
		id = DatatypeConverter.printHexBinary(md.digest(id.getBytes())).substring(0, 8);

		ObjectMapper mapper = new ObjectMapper();
		String payload = mapper.writeValueAsString(abono);

		sendMessage(payload, REQUEST_ABO, globalTopicAbo, id, null, null, null);
		boolean waiting = true;

		int count = 0;
		while(TIME_OUT != count){
			TimeUnit.SECONDS.sleep(1);
			count++;
		}
		if(count == TIME_OUT){
			if(!this.answerAbo.equalsIgnoreCase("EXITO")){
				waiting = false;
				throw new NonReplyException("Time Out - No Reply");
			}
		}
		waiting = false;
	}

	private void sendMessage(String payload, String status, Topic dest, String id, Integer id_compañia, Date fechaI, Date fechaF) throws JMSException, JsonGenerationException, JsonMappingException, IOException
	{
		if(status.equals(REQUEST_ABO) || status.equals(REQUEST_ANSWER_ABO)) {
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(id);
			ExchangeMsg msg = new ExchangeMsg("abono.general." +APP, APP, payload, status, id);
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
				if(ex.getStatus().equals(REQUEST_ABO)) {
					FestivAndesDistributed dtm = FestivAndesDistributed.getInstance();
					Abono abono = mapper.readValue(ex.getPayload(), Abono.class);
					//dtm.addAbonoLocal(abono);
					Topic t = new RMQDestination("", "abono.test", ex.getRoutingKey(), "", false);
					sendMessage("EXITO", REQUEST_ANSWER_ABO, t, id, null, null, null);
				} else if(ex.getStatus().equals(REQUEST_ANSWER_ABO)) {
					answerAbo = ex.getPayload();
					System.out.println("EXITO");
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
