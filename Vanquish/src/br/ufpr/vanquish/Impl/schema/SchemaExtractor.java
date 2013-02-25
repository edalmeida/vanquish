package br.ufpr.vanquish.Impl.schema;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufpr.vanquish.util.Util;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Query;

public class SchemaExtractor {
	private static final Logger logger = Logger.getLogger(SchemaExtractor.class.getCanonicalName());
	private Schema schema = new Schema();
	
	public Schema extract(){
		DatastoreService ds = Util.getDatastoreServiceInstance();
		Query q = new Query();
		
		// Set the chunk size for data fetching due to performance issues 
		Iterable<Entity> pq = ds.prepare(q).asIterable(FetchOptions.Builder.withChunkSize(10000));
		
		// Iterate over the data
		for (Entity result : pq) {
			StoredEntity se = new StoredEntity(result.getKind());
			
			if(!schema.getSchemaEntities().contains(se)){
				se.setProperties(result.getProperties());
				se.setKey(result.getKey());
				schema.getSchemaEntities().add(se);
			}else{
				int ix = schema.getSchemaEntities().indexOf(se);
				schema.getSchemaEntities().get(ix).incrementTuples();
			}
		}
		return schema;
	}
	
	public List<String> print(){
		logger.log(Level.INFO, "Statistics: ");
		List<String> stats = new ArrayList<String>();
		for(StoredEntity entity:schema.getSchemaEntities()){
			stats.add(entity.toString());
			logger.log(Level.INFO, entity.toString());
		}
		return stats;
	}

}
