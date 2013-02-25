package br.ufpr.vanquish.app.tpcc.transaction;

import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufpr.vanquish.app.AppHttpServlet;
import br.ufpr.vanquish.app.tpcc.iface.AppTransaction;
import br.ufpr.vanquish.util.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

public class NewOrderTransaction implements AppTransaction {
	private static final Logger logger = Logger.getLogger(NewOrderTransaction.class.getCanonicalName());

	@Override
	public void run(AppHttpServlet bhs)
			throws IOException {

		Transaction txn = Util.getDatastoreServiceInstance().beginTransaction();
		try {
			Key customerkey = KeyFactory.createKey("TPCC", "customer");
			Entity  customer = new Entity("customer", customerkey);	
			customer.setProperty("name", "Any Name");
			Util.getDatastoreServiceInstance().put(customer);

			Entity order = new Entity("order", customer.getKey());
			order.setProperty("customerid", String.valueOf((customer.getKey().getId())));		
			order.setProperty("status", "Processed");
			Util.getDatastoreServiceInstance().put(order);
			
			/*
			 * Complying to the TPC-C cardinality 1 Order -> [5, 15] OrderLine
			 */
			Random r = new Random();
			int olQty= r.nextInt(10)+5;
			for(int i=0;i<olQty;i++){
			
				Entity lineItem = new Entity("orderline", order.getKey());
				// key to string can be inserted instead of name, a better option
				lineItem.setProperty("orderid", String.valueOf(order.getKey().getId()));
				int qty= r.nextInt(99);
				lineItem.setProperty("quantity", String.valueOf(qty));
				lineItem.setProperty("amount", String.valueOf(qty*100.00));
				Util.getDatastoreServiceInstance().put(lineItem);
			}
			
			txn.commit();
		} catch (Exception e) {
			logger.log(Level.INFO, e.getMessage());
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
		bhs.getHttpServletResponse().sendRedirect("/vanquish.jsp");

	}

}
