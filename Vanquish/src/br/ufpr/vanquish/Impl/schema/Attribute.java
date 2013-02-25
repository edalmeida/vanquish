package br.ufpr.vanquish.Impl.schema;

public class Attribute {

	String name;
	boolean k;
	public Attribute(String name, boolean k){
		this.name=name;
		this.k=k;
	}
	public String getName(){
		return name;
	}
 
	public boolean isKey(){
		return k;
	}
	
	@Override 
	public boolean equals(Object e){
		if (e instanceof Attribute){
			Attribute a = (Attribute) e;
			return name.equalsIgnoreCase(a.getName());
		}
		return false;
	}
	
	public String toString(){
		return name;
	}
}
