package br.ufpr.vanquish.app;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufpr.vanquish.Impl.schema.Schema;
import br.ufpr.vanquish.Impl.schema.SchemaExtractor;
import br.ufpr.vanquish.Impl.schema.StoredEntity;
import br.ufpr.vanquish.Impl.transform.MegaRelation;
import br.ufpr.vanquish.Impl.transform.Transformation;
import br.ufpr.vanquish.app.tpcc.transaction.TPCCTransaction;

public class Switcher {
	private static final Logger logger = Logger.getLogger(Switcher.class.getCanonicalName());
	public static AppHttpServlet bhs;
	
	
	/*
	 * May perform:
	 * - transactions
	 * - print statistics
	 * - compute the mega relation and propose a new model
	 */	
	public Switcher(AppHttpServlet bhs) throws IOException{
		Switcher.bhs=bhs;
		SchemaExtractor s = new SchemaExtractor();
		Schema actualSchema = s.extract();
		
	/**
	 * TODO: need to build the main for gdsbench cause this is an app from users
	 */
		if(bhs.getHttpServletRequest().getRequestURI().equals("/stat")){	
			/*
			 * Print statistics
			 */	
			s.print();

		}else if(bhs.getHttpServletRequest().getRequestURI().equals("/model")){	
			/*
			 * Compute the mega relation and a new model
			 */
			MegaRelation mega = MegaRelation.getInstance();
			mega.setMegaRelation(actualSchema.getSchemaEntities());
			mega.printMegaRelation();

			Transformation  newModel = new Transformation();
			newModel.run(mega.getFDList(),mega.getMegaRelation());
			logger.log(Level.INFO,"New Model: ");
			Schema newSchema = newModel.getNewSchema();
			for(StoredEntity se : newSchema.getSchemaEntities()){
				logger.log(Level.INFO,se.toString());
			}
			newModel.persist(newSchema);
			bhs.getHttpServletResponse().sendRedirect("/vanquish.jsp");
		}else{
			/*
			 * Run transactions
			 */
			TPCCTransaction trans = new TPCCTransaction();
			trans.run(bhs);
		}
	}
}
