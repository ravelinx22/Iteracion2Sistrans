package dtm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.rabbitmq.jms.admin.RMQConnectionFactory;

import jms.FuncionesMDB;
import tm.FestivAndesMaster;
import tm.FuncionMaster;
import vos.ListaFunciones;

public class FestivAndesDistributed {
	// Constantes para la conexion
	
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	private static FestivAndesDistributed instance;
	
	//TODO DECLARAR JMS
	private FuncionesMDB funcionesMQ;
	
	private static String path;
	
	// Atributos
	
	private FestivAndesMaster tm;
	private QueueConnectionFactory queueFactory;
	private TopicConnectionFactory factory;
	
	private FestivAndesDistributed() throws JMSException, Exception {
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		//TODO INICIALIZAR JMS
		funcionesMQ = new FuncionesMDB(factory, ctx);
		
		funcionesMQ.start();
	}
	
	public void stop() throws JMSException {
		//TODO 
		funcionesMQ.close();
	}
	
	public static void setPath(String p) {
		path = p;
	}
	
	public void setUpTransactionManager(FestivAndesMaster tm) {
		this.tm = tm;
	}
	
	private static FestivAndesDistributed getInst() {
		return instance;
	}
	
	public static FestivAndesDistributed getInstance(FestivAndesMaster tm) {
		if(instance == null) {
			try {
				instance = new FestivAndesDistributed();
			} catch(JMSException e) {
				e.printStackTrace();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		instance.setUpTransactionManager(tm);
		return instance;
	}
	
	public static FestivAndesDistributed getInstance() {
		if(instance == null) {
			FestivAndesMaster tm = new FestivAndesMaster(path);
			return getInstance(tm);
		}
		if(instance.tm != null) {
			return instance;
		}
		FestivAndesMaster tm = new FestivAndesMaster(path);
		return getInstance(tm);
	}
	
	// Getters de las listas locales
	
	public ListaFunciones getLocalFunciones() throws Exception
	{
		FuncionMaster x = (FuncionMaster) tm;
		return x.darFunciones();
	}
	
	// Getters de las listas remotas
	
	public ListaFunciones getRemoteAerolineas() throws JsonGenerationException, JsonMappingException, JMSException, IOException, Exception, InterruptedException, NoSuchAlgorithmException
	{
		return funcionesMQ.getRemoteFunciones();
	}
	
	
	
}
