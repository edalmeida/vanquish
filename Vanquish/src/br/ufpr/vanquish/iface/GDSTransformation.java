package br.ufpr.vanquish.iface;

import java.util.List;

import br.ufpr.vanquish.Impl.schema.Attribute;
import br.ufpr.vanquish.Impl.schema.FunctionalDependency;
import br.ufpr.vanquish.Impl.schema.Schema;

public interface GDSTransformation {
	public void run(List<FunctionalDependency> fds, List<Attribute> megaRelation);
	public Schema getNewSchema();
	public void persist(Schema s);
}
