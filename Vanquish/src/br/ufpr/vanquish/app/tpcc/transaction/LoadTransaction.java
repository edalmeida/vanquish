package br.ufpr.vanquish.app.tpcc.transaction;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufpr.vanquish.app.AppHttpServlet;
import br.ufpr.vanquish.app.tpcc.iface.AppTransaction;
import br.ufpr.vanquish.util.StopWatch;


public class LoadTransaction implements AppTransaction {
	private static final Logger logger = Logger.getLogger(LoadTransaction.class.getCanonicalName());


	@Override
	public void run(AppHttpServlet bhs)
			throws IOException {
		
		Integer benchTime=0, benchTrans=0;
		try{
			benchTime = Integer.valueOf(bhs.getHttpServletRequest().getParameter("time"));
		}catch(NumberFormatException e){
			logger.log(Level.INFO, e.getMessage());
			benchTime = 0;
		}
		
		try{
			benchTrans = Integer.valueOf(bhs.getHttpServletRequest().getParameter("trans"));
		}catch(NumberFormatException e){
			logger.log(Level.INFO, e.getMessage());
			benchTrans = 0;
		}
		
		StopWatch s = new StopWatch();
		s.start();
		if(benchTime>0){
			logger.log(Level.INFO, "Benchmark time: "+benchTime);
			while(s.getElapsedTime()<benchTime){
				AppTransaction newOrder = new NewOrderTransaction();
				newOrder.run(bhs);			
			}
			
		}else if(benchTrans>0){
			logger.log(Level.INFO, "Benchmark transactions: "+benchTrans);
			while(benchTrans>0){
				AppTransaction newOrder = new NewOrderTransaction();
				newOrder.run(bhs);	
				benchTrans--;
			}
		}else{
			logger.log(Level.INFO, "Retry.");
		}
		logger.log(Level.INFO, "Benchmark time (ms): "+s.getElapsedTime());
		s.stop();
		
		bhs.getHttpServletResponse().sendRedirect("/vanquish.jsp");
	}
}
