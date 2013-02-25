package br.ufpr.vanquish.app;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AppHttpServlet {
	private HttpServletRequest req;
	private HttpServletResponse resp;
	
	public AppHttpServlet(HttpServletRequest req, HttpServletResponse resp){
		this.resp=resp;
		this.req=req;		
	}
	
	public HttpServletRequest getHttpServletRequest(){
		return req;
	}
	public void setHttpServletRequest(HttpServletRequest req){
		this.req=req;
	}
	public HttpServletResponse getHttpServletResponse(){
		return resp;
	}
	public void setHttpServletResponse(HttpServletResponse resp){
		this.resp=resp;
	}

}
