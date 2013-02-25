package br.ufpr.vanquish.app.tpcc.iface;

import java.io.IOException;

import br.ufpr.vanquish.app.AppHttpServlet;

public interface AppTransaction {

	public void run(AppHttpServlet bhs)throws IOException;
	
}
