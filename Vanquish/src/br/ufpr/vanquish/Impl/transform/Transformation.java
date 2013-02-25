package br.ufpr.vanquish.Impl.transform;

import java.util.List;

import br.ufpr.vanquish.Impl.schema.Attribute;
import br.ufpr.vanquish.Impl.schema.FunctionalDependency;
import br.ufpr.vanquish.Impl.schema.Schema;
import br.ufpr.vanquish.iface.GDSTransformation;

public class Transformation {
	
	private GDSTransformation trans = new BCNF();
	
	public void run(List<FunctionalDependency> fds, List<Attribute> megaRelation){
		trans.run(fds, megaRelation);
	}

		
	public Schema getNewSchema(){
		return trans.getNewSchema();
	}

	public void persist(Schema s){
		trans.persist(s);
	}
}
