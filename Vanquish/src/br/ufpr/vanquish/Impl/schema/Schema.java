package br.ufpr.vanquish.Impl.schema;

import java.util.ArrayList;
import java.util.List;

public class Schema {
	private List<StoredEntity> schema = new ArrayList<StoredEntity>();
	
	public void setSchema(List<StoredEntity> schema){
		this.schema=schema;
	}
	
	public void setStoredEntity(StoredEntity e){
		schema.add(e);
	}
	
	public List<StoredEntity> getSchemaEntities(){
		return schema;
	}
}
