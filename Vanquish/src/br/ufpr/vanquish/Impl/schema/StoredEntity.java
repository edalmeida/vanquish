package br.ufpr.vanquish.Impl.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.datastore.Key;


public class StoredEntity {
	String kind;
	Integer tuples=0;
	Map<String,Object> properties = new HashMap<String,Object>();
	List<Attribute> attributes = new ArrayList<Attribute>();
	Key key;
		
	public StoredEntity(String k){
		this.kind=k;
	}
	
	public void setKind(String k){
		this.kind=k;
	}
	
	public void setTuples(Integer t){
		this.tuples=t;
	}
	
	public void setProperties(Map<String,Object> props){
		properties=props;
		for(String t : properties.keySet()){
			Attribute a = new Attribute(t,false);
			if(!attributes.contains(a)){
				attributes.add(a);
			}
		}
	}
	
	public void setAttribute(Attribute a){
		attributes.add(a);
	}
	
	
	public void setKey(Key key){
		this.key=key;
	}
	
	public void incrementTuples(){
		this.tuples++;
	}
	
	
	public String getKind(){
		return kind;
	}
	
	public Integer getTuples(){
		return tuples;
	}
	
	public Key getKey(){
		return key;
	}
	
	public List<Attribute> getProperties(){
		return attributes;
	}
	
	public String toString(){
		String infos = kind+" has "+ tuples +" tuples and atributes:";
		
		for(Attribute t : attributes){
			infos=infos+" "+t.toString()+", ";		
		}	
		return infos;
	}
	
	@Override 
	public boolean equals(Object e){
		if (e instanceof StoredEntity){
			StoredEntity se = (StoredEntity) e;
			return kind.equalsIgnoreCase(se.getKind());
		}
		return false;
	}
}
