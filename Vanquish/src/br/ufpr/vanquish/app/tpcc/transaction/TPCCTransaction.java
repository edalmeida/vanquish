package br.ufpr.vanquish.app.tpcc.transaction;

import java.io.IOException;

import br.ufpr.vanquish.app.AppHttpServlet;
import br.ufpr.vanquish.app.tpcc.iface.AppTransaction;

public class TPCCTransaction {

	private AppTransaction delete = new DeleteTransaction();
	private AppTransaction load = new LoadTransaction();
	private AppTransaction newOrder = new NewOrderTransaction();
	
	public void run(AppHttpServlet bhs)	throws IOException {
    	if(bhs.getHttpServletRequest().getRequestURI().equals("/gdsbench")){
    		newOrder.run(bhs);
    	}else if(bhs.getHttpServletRequest().getRequestURI().equals("/load")){
    		load.run(bhs);
    	}else {
    		delete.run(bhs);
    	}
	}
}
