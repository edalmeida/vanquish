package br.ufpr.vanquish.app.tpcc.transaction;

import java.io.IOException;

import br.ufpr.vanquish.app.AppHttpServlet;
import br.ufpr.vanquish.app.tpcc.iface.AppTransaction;
import br.ufpr.vanquish.util.Util;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Transaction;

public class DeleteTransaction implements AppTransaction {

	/*
	 * We are truncating the DB, therefore, we drop cascade on delete issues
	 * 
	 */
	@Override
	public void run(AppHttpServlet bhs)
			throws IOException {

		DatastoreService ds = Util.getDatastoreServiceInstance();
		Query q = new Query();
		PreparedQuery pq = ds.prepare(q);
		// Iterate the date
		for (Entity result : pq.asIterable()) {
			Transaction txn = ds.beginTransaction();
			try {
				ds.delete(result.getKey());
				txn.commit();
			} finally {
				if (txn.isActive()) {
					txn.rollback();
				}
			} 
			bhs.getHttpServletResponse().sendRedirect("/vanquish.jsp");
		}
	}
}
