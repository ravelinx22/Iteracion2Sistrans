package dtm;

import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;

import tm.FestivAndesMaster;

public class FestivAndesDistributed {
	// Constantes para la conexion
	
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	private static FestivAndesDistributed instance;
	
	// Atributos
	
	private FestivAndesMaster tm;
	private QueueConnectionFactory queueFactory;
	private TopicConnectionFactory factory;
	
	
}
