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
import app3.ListaRentabilidad3;
import dtm.FestivAndesDistributed;
import excVos.ExchangeMsg;
import vos.ListaRentabilidad;

public class RentabilidadMDB implements MessageListener, ExceptionListener {
	public final static int TIME_OUT = 5;
	private final static String APP = "app1";

	// TOPICS CONSTANTS

	private final static String GLOBAL_TOPIC_NAME_RENT = "java:global/RMQTopicRentabilidad";
	private final static String LOCAL_TOPIC_NAME_RENT = "java:global/RMQRentabilidadLocal";

	// REQUESTS CONSTANTS

	private final static String REQUEST_RENT = "REQUEST_RENT";
	private final static String REQUEST_ANSWER_RENT = "REQUEST_ANSWER_RENT";

	// TOPIC 

	private Topic globalTopicRent;
	private Topic localTopicRent;

	private TopicConnection topicConnection;
	private TopicSession topicSession;

	private List<Object> answerRent = new ArrayList<Object>();

	public RentabilidadMDB(TopicConnectionFactory factory, InitialContext ctx) throws JMSException, NamingException 
	{	
		topicConnection = factory.createTopicConnection();
		topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		globalTopicRent = (RMQDestination) ctx.lookup(GLOBAL_TOPIC_NAME_RENT);

		// Crear subscripber global

		TopicSubscriber topicSubscripberRent = topicSession.createSubscriber(globalTopicRent);
		topicSubscripberRent.setMessageListener(this);

		// Buscar context

		localTopicRent = (RMQDestination) ctx.lookup(LOCAL_TOPIC_NAME_RENT);

		// Crear subscriber local

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


	public ListaRentabilidad getRemoteRentabilidad(Date fechaI, Date fechaF, int idCompañia) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		answerRent.clear();
		String id = APP+""+System.currentTimeMillis();
		MessageDigest md = MessageDigest.getInstance("MD5");
		id = DatatypeConverter.printHexBinary(md.digest(id.getBytes())).substring(0, 8);

		// TODO: ARREGLO

		SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
		String fechaIS = newFormat.format(fechaI);

		String fechaFS = newFormat.format(fechaF);

		String dato = idCompañia+";;;"+fechaIS+";;;"+fechaFS;
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
		ObjectMapper mapper = new ObjectMapper();
		System.out.println(id);
		ExchangeMsg msg = new ExchangeMsg("rentabilidadgeneral", APP, payload, status, id);
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
			System.out.println("RENTABILIDADMDB POSI POSI BUENAS");
			String body = txt.getText();
			System.out.println(body);
			ObjectMapper mapper = new ObjectMapper();
			ExchangeMsg ex = mapper.readValue(body, ExchangeMsg.class);
			String id = ex.getMsgId();
			System.out.println(ex.getSender());
			System.out.println(ex.getStatus());
			
			if(!ex.getSender().equals(APP))
			{
				if(ex.getStatus().equals(REQUEST_RENT)) {
					if(ex.getSender().equals(3)) {
						FestivAndesDistributed dtm = FestivAndesDistributed.getInstance();
						String[] datos = ex.getPayload().split(";;;");
						SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

						java.util.Date parsed1 = format.parse(datos[0]);
						Date fechaI = new Date(parsed1.getTime());

						java.util.Date parsed2 = format.parse(datos[1]);
						Date fechaF = new Date(parsed2.getTime());

						Integer idCompañia = Integer.parseInt(datos[2]);

						ListaRentabilidad rent = dtm.getLocalRentabilidad(fechaI, fechaF, idCompañia);
						String payload = mapper.writeValueAsString(rent);
						Topic t = new RMQDestination("", "rentabilidad.test", ex.getRoutingKey(), "", false);
						sendMessage(payload, REQUEST_ANSWER_RENT, t, id, null, null, null);	
					} else {
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
					}
				} else if(ex.getStatus().equals(REQUEST_ANSWER_RENT)) {
					if(ex.getSender().equals(3)) {
						ListaRentabilidad3 v = mapper.readValue(ex.getPayload(), ListaRentabilidad3.class);
						answerRent.addAll(v.getRentabilidad());
					} else {
						ListaRentabilidad v = mapper.readValue(ex.getPayload(), ListaRentabilidad.class);
						answerRent.addAll(v.getRentabilidad());
					}
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
