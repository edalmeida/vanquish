package br.ufpr.vanquish.app;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class AppServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
	
    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    	AppHttpServlet bhs=new AppHttpServlet(req,resp);
    	new Switcher(bhs);
    }

}
