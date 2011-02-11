package org.vpac.grisu.webclient.server;


import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import grith.sibboleth.*;

import org.apache.log4j.Logger;
import org.vpac.grisu.webclient.server.serverExceptions.MyProxyCertRequestException;


/**
 * Servlet implementation class test
 */

public class MyProxyForwarderServlet extends HttpServlet {
 private static final long serialVersionUID = 1L;
public static final String USER_NAME = "userName";
public static final String USER_PASS = "userPass";
static final Logger myLogger = Logger.getLogger(GrisuClientServiceImpl.class.getName());

 /**
  * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
  */


 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  // TODO Auto-generated method stub
	 
	 
	 	String userName = "dannyd"; //request.getParameter("userName");
	 	String userPass = "jfk204$";//request.getParameter("userPass");
	 	
	 	myLogger.debug(request.getAttribute("Shib-Identity-Provider"));
	 	myLogger.debug(request.getAttribute("Shib-Session-ID"));
	 	
	
	 	
	 	
	 	HttpSession session = request.getSession();
	 	session.setAttribute(USER_NAME, userName);
	 	session.setAttribute(USER_PASS, userPass);
	 
	 	
	 	
	 	ServletContext context = getServletContext();
	     RequestDispatcher dispatcher = context.getRequestDispatcher("/org.vpac.grisu.webclient.Application/Application.html");
	     if(dispatcher != null)
	     dispatcher.forward(request,response);
 }

 /**
  * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
  */
 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 	
	 
	 	String userName = "dannyd"; //request.getParameter("userName");
	 	String userPass = "jfk204$";//request.getParameter("userPass");
	 	
	 	HttpSession session = request.getSession();
	 	
	 	session.setAttribute(USER_NAME, userName);
	 	session.setAttribute(USER_PASS, userPass);
	 	
	 	
	 	
	 	ServletContext context = getServletContext();
	     RequestDispatcher dispatcher = context.getRequestDispatcher("/org.vpac.grisu.webclient.Application/Application.html");
	     dispatcher.forward(request,response);
	 	
 }
 
 private String getMyProxyCert() throws MyProxyCertRequestException
 {
	 
	 
	 
	 
	 
	 
	 
	 return null;
 }
 
// private HttpSession getSession(HttpServletRequest request)
// {
//	return request.getSession(true);
// }

}