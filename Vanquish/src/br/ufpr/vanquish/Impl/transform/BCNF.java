package br.ufpr.vanquish.Impl.transform;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufpr.vanquish.Impl.schema.Attribute;
import br.ufpr.vanquish.Impl.schema.FunctionalDependency;
import br.ufpr.vanquish.Impl.schema.Schema;
import br.ufpr.vanquish.Impl.schema.StoredEntity;
import br.ufpr.vanquish.iface.GDSTransformation;
import br.ufpr.vanquish.util.Util;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Transaction;

public class BCNF implements GDSTransformation{
	private static final Logger logger = Logger.getLogger(BCNF.class.getCanonicalName());

	private List<FunctionalDependency> fds;
	private List<Attribute> megaRelation;
	private Schema newEntities = new Schema();
	
	
	public void run(List<FunctionalDependency> fds, List<Attribute> megaRelation){
		this.fds=fds;
		this.megaRelation=megaRelation;
	}
	
	private void decompose(){
		int entity=0;

		for(FunctionalDependency fd : fds){
			StoredEntity se = new StoredEntity("entity"+entity);
			for(Attribute lhsAtt : fd.getLeftHandSide()){				
				se.setAttribute(lhsAtt);
			}
			
			for(Attribute rhsAtt : fd.getRightHandSide()){		
				se.setAttribute(rhsAtt);
				megaRelation.remove(rhsAtt);
			}
			newEntities.setStoredEntity(se);
			entity++;
		}
	}
	
	public Schema getNewSchema(){
		decompose();
		return newEntities;
	}

	@Override
	public void persist(Schema s){

		Transaction txn = Util.getDatastoreServiceInstance().beginTransaction();
		try {
			for(StoredEntity se :s.getSchemaEntities()){
				Key schemaKey = KeyFactory.createKey("schema", "schema");
				Entity schema = new Entity("schema", schemaKey);	
				logger.log(Level.INFO, "Kind: "+se.getKind());
				schema.setProperty("Entity", se.getKind());
				Util.getDatastoreServiceInstance().put(schema);
				for(Attribute a : se.getProperties()){
					Entity attribute = new Entity("attribute", schema.getKey());	
					logger.log(Level.INFO, "Attribute: "+a.getName());
					attribute.setProperty("entityid", String.valueOf(schema.getKey().getId()));
					attribute.setProperty("name", a.getName());
					attribute.setProperty("iskey", a.isKey());
					Util.getDatastoreServiceInstance().put(attribute);	
				}
			}
			
			txn.commit();
		} catch (Exception e) {
			logger.log(Level.WARNING, e.getMessage());
		} finally {
			if (txn.isActive()) {
				txn.rollback();
			}
		}
	}
}
