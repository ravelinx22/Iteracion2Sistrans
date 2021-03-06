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

public class AllFuncionesMDB implements MessageListener, ExceptionListener 
{
	public final static int TIME_OUT = 5;
	private final static String APP = "app1";
	
	// TOPICS CONSTANTS
	
	private final static String GLOBAL_TOPIC_NAME_FUNCIONES = "java:global/RMQTopicAllFunciones";
	private final static String LOCAL_TOPIC_NAME_FUNCIONES = "java:global/RMQAllFuncionesLocal";
	
	// REQUESTS CONSTANTS
	
	private final static String REQUEST_FUNCIONES = "REQUEST_FUNC";
	private final static String REQUEST_ANSWER_FUNCIONES = "REQUEST_ANSWER_FUNC";

	
	// TOPIC 
	
	private Topic globalTopicFunciones;
	private Topic localTopicFunciones;
	private Topic globalTopicRent;
	private Topic localTopicRent;
	private Topic globalTopicReti;
	private Topic localTopicReti;
	private Topic globalTopicAbo;
	private Topic localTopicAbo;
	
	private TopicConnection topicConnection;
	private TopicSession topicSession;
	
	private List<Object> answer = new ArrayList<Object>();
	private List<Object> answerRent = new ArrayList<Object>();
	private String answerReti = "";
	private String answerAbo = "";
	
	public AllFuncionesMDB(TopicConnectionFactory factory, InitialContext ctx) throws JMSException, NamingException 
	{	
		String x = "BUENAS BUENAS POSI POSI";
		System.out.println(x);
		
		topicConnection = factory.createTopicConnection();
		topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		globalTopicFunciones = (RMQDestination) ctx.lookup(GLOBAL_TOPIC_NAME_FUNCIONES);
		
		// Crear subscripber global
		
		TopicSubscriber topicSubscriber =  topicSession.createSubscriber(globalTopicFunciones);
		topicSubscriber.setMessageListener(this);
	
		// Buscar context
		
		localTopicFunciones = (RMQDestination) ctx.lookup(LOCAL_TOPIC_NAME_FUNCIONES);
		
		// Crear subscriber local
		
		topicSubscriber =  topicSession.createSubscriber(localTopicFunciones);
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
	
	
	private void sendMessage(String payload, String status, Topic dest, String id, Integer id_compañia, Date fechaI, Date fechaF) throws JMSException, JsonGenerationException, JsonMappingException, IOException
	{
			ObjectMapper mapper = new ObjectMapper();
			System.out.println(id);
			ExchangeMsg msg = new ExchangeMsg("funcionesgeneral", APP, payload, status, id);
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
	public void onMessage(Message message) 
	{
		TextMessage txt = (TextMessage) message;
		try 
		{
			System.out.println("ALLFUNCIONES POSI POSI BUENAS");

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
					Topic t = new RMQDestination("asdñflkajsdflñkjsdalkjasf", "funciones.test", ex.getRoutingKey(), "", false);
					sendMessage(payload, REQUEST_ANSWER_FUNCIONES, t, id, null, null, null);
				}
				else if(ex.getStatus().equals(REQUEST_ANSWER_FUNCIONES))
				{
					ListaFuncion3 v = mapper.readValue(ex.getPayload(), ListaFuncion3.class);
					answer.addAll(v.getReportes());
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
