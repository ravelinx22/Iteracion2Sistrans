package ls;

import javax.jms.JMSException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import dtm.FestivAndesDistributed;

public class ContextListener implements ServletContextListener {
	
	private FestivAndesDistributed dtm;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		try {
			throw new JMSException("");
		} catch(JMSException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
	}
	
	
}
