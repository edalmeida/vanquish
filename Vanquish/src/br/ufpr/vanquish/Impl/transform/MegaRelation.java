package br.ufpr.vanquish.Impl.transform;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import br.ufpr.vanquish.Impl.schema.Attribute;
import br.ufpr.vanquish.Impl.schema.FunctionalDependency;
import br.ufpr.vanquish.Impl.schema.Schema;
import br.ufpr.vanquish.Impl.schema.StoredEntity;

public class MegaRelation {
	private static final Logger logger = Logger.getLogger(MegaRelation.class.getCanonicalName());
	private static  MegaRelation mr= new MegaRelation();

	private List<FunctionalDependency> fds = new ArrayList<FunctionalDependency>();
	private List<StoredEntity> entityList = new ArrayList<StoredEntity>();
	private Schema schema = new Schema();
	private StoredEntity megarelation = new StoredEntity("megarelation");

	/**
	 * This class is a singleton, since only one mega relation can be extracted from the original model.
	 * Moreover, it can be used all the way through the refactoring process (e.g, by BCNF).
	 */
	private MegaRelation(){}
	
	/**
	 * To cope with the Singleton pattern
	 * @return
	 */
	public static MegaRelation getInstance(){
		return mr;
	}
	
	public void setMegaRelation(List<StoredEntity> entityList){	
		this.entityList=entityList;
		buildMegaRelation();
	}
	
	/*
	 * Find the mega relation and the functional dependencies
	 */
	private void buildMegaRelation(){	
		for(StoredEntity entity:entityList){
			/*
			 * Assuming only one FD per entity
			 */
			FunctionalDependency fd = new FunctionalDependency();
			Attribute pKey = new Attribute(entity.getKind()+"id",true);
			megarelation.setAttribute(pKey);
			fd.setLeftHandSide(pKey);
			
			for(Attribute a:entity.getProperties()){		
				if(!megarelation.equals(a)){
					megarelation.setAttribute(a);
					fd.setRightHandSide(a);
				}else{
					fd.setLeftHandSide(a);
				}
			}
			fds.add(fd);
		}
		schema.setStoredEntity(megarelation);
	}
	
	public void printMegaRelation(){
				
		String megaAtt="";
		for(Attribute mega:megarelation.getProperties()){
			megaAtt=mega.getName()+", "+megaAtt;
		}
		logger.log(Level.INFO, "Mega relation: ("+megaAtt+")");
		
		logger.log(Level.INFO, "Functional Dependencies: ");
		for(FunctionalDependency f: fds){
			logger.log(Level.INFO, f.printLeftHandSide()+" -> "+f.printRightHandSide());
		}
	}
	
	public List<FunctionalDependency> getFDList(){
		return fds;
	}
	
	/*
	 * The only entity is returned 
	 */
	public List<Attribute> getMegaRelation(){
		return schema.getSchemaEntities().get(0).getProperties();
	}
}
