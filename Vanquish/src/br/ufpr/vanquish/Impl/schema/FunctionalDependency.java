package br.ufpr.vanquish.Impl.schema;

import java.util.ArrayList;
import java.util.List;

public class FunctionalDependency {

	private List<Attribute> lhs= new ArrayList<Attribute>();
	private List<Attribute> rhs= new ArrayList<Attribute>();

	public void setLeftHandSide(Attribute a){
		lhs.add(a);
	}
	
	public void setRightHandSide(Attribute a){
		rhs.add(a);
	}	
	
	public List<Attribute> getLeftHandSide(){
		return lhs;
	}
	
	public List<Attribute> getRightHandSide(){
		return rhs;
	}	
	
	public String printLeftHandSide(){
		String result="";
		for(Attribute a:lhs){
			result=a.getName()+", "+result;
		}
		
		return result;
	}
	
	public String printRightHandSide(){
		String result="";
		for(Attribute a:rhs){
			result=a.getName()+", "+result;
		}
		
		return result;
	}
}
