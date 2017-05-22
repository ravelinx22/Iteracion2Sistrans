package dtm;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import javax.jms.JMSException;
import javax.jms.QueueConnectionFactory;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import com.rabbitmq.jms.admin.RMQConnectionFactory;
import jms.AllFuncionesMDB;
import jms.NonReplyException;
import tm.FestivAndesMaster;
import tm.FuncionMaster;
import vos.ListaFunciones;
import vos.ListaRentabilidad;

public class FestivAndesDistributed {
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	private static FestivAndesDistributed instance;
	
	private FestivAndesMaster tm;
	
	private QueueConnectionFactory queueFactory;
	
	private TopicConnectionFactory factory;
	
	private AllFuncionesMDB allFuncionesMQ;

	
	private static String path;


	private FestivAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		allFuncionesMQ = new AllFuncionesMDB(factory, ctx);		
		allFuncionesMQ.start();		
	}
	
	public void stop() throws JMSException
	{
		allFuncionesMQ.close();
	}
	
	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	public static void setPath(String p) {
		path = p;
	}
	
	public void setUpTransactionManager(FestivAndesMaster tm)
	{
	   this.tm = tm;
	}
	
	private static FestivAndesDistributed getInst()
	{
		return instance;
	}
	
	public static FestivAndesDistributed getInstance(FestivAndesMaster tm)
	{
		if(instance == null)
		{
			try {
				instance = new FestivAndesDistributed();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(tm);
		return instance;
	}
	
	public static FestivAndesDistributed getInstance()
	{
		if(instance == null)
		{
			FestivAndesMaster tm = new FestivAndesMaster(path);
			return getInstance(tm);
		}
		if(instance.tm != null)
		{
			return instance;
		}
		FestivAndesMaster tm = new FestivAndesMaster(path);
		return getInstance(tm);
	}
	
	// Funciones
	
	public ListaFunciones getLocalFunciones() throws Exception
	{
		return tm.darFuncionesLocal();
	}
	
	public ListaFunciones getRemoteFunciones() throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return allFuncionesMQ.getRemoteFunciones();
	}
	
	// Rentabilidad
	
	public ListaRentabilidad getLocalRentabilidad(Date fechaInicio, Date fechaFinal, int id_compañia) throws Exception
	{
		return tm.darRentabilidadLocal(fechaInicio, fechaFinal, id_compañia);
	}
	
	public ListaRentabilidad getRemoteRentabilidad(Date fechaInicio, Date fechaFinal, int id_compañia) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return allFuncionesMQ.getRemoteRentabilidad(fechaInicio, fechaFinal, id_compañia);
	}
	
	// Retirar compañia
	
	public void retirarCompañiaLocal(int id_compañia) throws Exception {
		tm.retirarCompañiaLocal(id_compañia);
	}
	
	public void retirarCompañiaRemote(int id_compañia) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException {
		
	}
}
